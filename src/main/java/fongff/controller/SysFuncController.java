package fongff.controller;

import fongff.dto.SysFuncDto;
import fongff.mapper.SysFuncMapper;
import fongff.model.SysFunc;
import fongff.model.SysLog;
import fongff.service.SysFuncService;
import fongff.service.SysLogService;
import fongff.util.CommonUtil;
import fongff.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/func")
public class SysFuncController {
    @Autowired
    private SysFuncService sysFuncService;
    @Autowired
    private UploadUtil uploadUtil;

    @Autowired
    private SysLogService sysLogService;

    @Value("${news.defaultPath}")
    private String newsDefultImg;

    @GetMapping("/count")
    public ResponseEntity<Map<String, Object>> getCount() {
        Map<String, Object> respResult = new LinkedHashMap<>();

        List<SysLog> daily = sysLogService.getDailyCount();
        List<SysLog> total = sysLogService.getTotalCount();
        respResult.put("dailyCount", daily.size());
        respResult.put("totalCount", total.size());

        return ResponseEntity.ok(respResult);
    }

    @GetMapping("/content")
    public ResponseEntity<Map<String, Object>> getFunc() {
        Map<String, Object> respResult = new LinkedHashMap<>();
        List<SysFunc> sysFuncList = sysFuncService.findAll();
        respResult.put("data", sysFuncList);
        return ResponseEntity.ok(respResult);
    }

    @GetMapping("/content/module/{module}")
    public ResponseEntity<Map<String, Object>> getFuncByModule(HttpServletRequest request, @PathVariable String module) throws Exception {
        Map<String, Object> respResult = new LinkedHashMap<>();
        List<SysFunc> sysFuncList = sysFuncService.findByModuleAndStates(module);
        respResult.put("data", sysFuncList);

        if(module.equals("A03")){
            String userIp = CommonUtil.getIP(request);

            List<SysLog> sysLogs = sysLogService.getDailyCount();
            String ip = CommonUtil.getIP(request);
            SysLog sysLog = new SysLog();
            sysLog.setPath(request.getServletPath());
            sysLog.setCreateTime(new Date());
            sysLog.setIp(ip);
            if (sysLogService.findByIp(ip) == null) {
                sysLogService.save(sysLog);
            }
        }
        return ResponseEntity.ok(respResult);
    }

    @GetMapping("/content/module/{module}/{category}")
    public ResponseEntity<Map<String, Object>> getFuncByModuleAndCategory(@PathVariable String module,@PathVariable String category) {
        Map<String, Object> respResult = new LinkedHashMap<>();
        List<SysFunc> sysFuncList = sysFuncService.findByModuleAndCategory(module,category);
        respResult.put("data", sysFuncList);
        return ResponseEntity.ok(respResult);
    }

    @GetMapping("/content/{indexR}")
    public ResponseEntity<Map<String, Object>> getOne(@PathVariable Integer indexR) {
        Map<String, Object> respResult = new LinkedHashMap<>();
        SysFunc sysFunc = sysFuncService.findOne(indexR);
        respResult.put("data", sysFunc);
        return ResponseEntity.ok(respResult);
    }


    @PostMapping("/content")
    public ResponseEntity<Map<String, Object>> addContent
            (Principal principal, @RequestParam(value = "file", required = false) MultipartFile file, @ModelAttribute SysFunc sysFunc) throws IOException {
        Map<String, Object> respResult = new LinkedHashMap<>();

        Integer indexR = sysFunc.getIndexR();
        String module = sysFunc.getModule();
        String category = sysFunc.getCategory();
        String title = sysFunc.getTitle();
        String content = sysFunc.getContent();
        String url = sysFunc.getUrl();
        if (file != null) {
            /* 檔案上傳 */
            String filePath = uploadUtil.uploadFile(indexR, module, file);
            sysFunc.setModule(module);
            sysFunc.setIndexR(indexR);
            sysFunc.setImage(filePath);
        } else {
            uploadUtil.deleteFile(indexR, module);
        }

        sysFunc.setUrl(url);
        sysFunc.setTitle(title);
        sysFunc.setContent(content);
        sysFunc.setAuth(principal.getName());
        sysFunc.setPostDate(new Date());
        sysFunc.setCategory(category);
        sysFuncService.save(sysFunc);

        respResult.put("data", sysFunc);
        return ResponseEntity.ok(respResult);
    }

    @PutMapping("/content")
    public ResponseEntity<Map<String, Object>> updateContent(Principal principal, @RequestParam(value = "file", required = false) MultipartFile file, @ModelAttribute SysFuncDto sysFuncDto) throws IOException {
        Map<String, Object> respResult = new LinkedHashMap<>();
        SysFunc sysFunc = SysFuncMapper.INSTANCE.sysFuncDtoToSysFunc(sysFuncDto);

        Integer indexR = sysFuncDto.getIndexR();
        String module = sysFunc.getModule();
        String filePath = "";
        if (file != null) {
            /* 檔案上傳 */
            filePath = uploadUtil.uploadFile(indexR, module, file);
        } else {
            /* 如果前端沒有上傳file就使用前一張圖 */
            SysFunc sysFuncOld = sysFuncService.findOne(indexR);
            if (sysFuncDto.getCategory().equals("news")) {
                filePath = CommonUtil.isNull(sysFuncOld.getImage()) ? null : newsDefultImg;
            } else {
                filePath = CommonUtil.isNull(sysFuncOld.getImage()) ? null : sysFuncOld.getImage();
            }
        }

        sysFunc.setImage(filePath);
        sysFunc.setPostDate(new Date());
        sysFunc.setAuth(principal.getName());
        sysFuncService.save(sysFunc);

        sysFuncDto = SysFuncMapper.INSTANCE.sysFuncToSysFuncDto(sysFuncService.findOne(indexR));
        respResult.put("data", sysFuncDto);
        return ResponseEntity.ok(respResult);
    }


}
