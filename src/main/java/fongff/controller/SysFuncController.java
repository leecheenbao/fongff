package fongff.controller;

import fongff.dto.SysFuncDto;
import fongff.mapper.SysFuncMapper;
import fongff.model.SysFunc;
import fongff.service.SysFuncService;
import fongff.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/content")
    public ResponseEntity<Map<String, Object>> getFunc() {
        Map<String, Object> respResult = new LinkedHashMap<>();
        List<SysFunc> sysFuncList = sysFuncService.findAll();
        respResult.put("data", sysFuncList);
        return ResponseEntity.ok(respResult);
    }

    @GetMapping("/content/module/{module}")
    public ResponseEntity<Map<String, Object>> getFuncByModule(@PathVariable String module) {
        Map<String, Object> respResult = new LinkedHashMap<>();
        List<SysFunc> sysFuncList = sysFuncService.findByModule(module);
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
            filePath = sysFuncService.findOne(indexR).getImage();
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
