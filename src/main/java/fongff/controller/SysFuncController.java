package fongff.controller;

import fongff.model.SysFunc;
import fongff.model.SysFuncId;
import fongff.service.SysFuncService;
import fongff.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        List<SysFunc> sysFuncs = sysFuncService.findAll();
        respResult.put("data", sysFuncs);
        return ResponseEntity.ok(respResult);
    }

    @GetMapping("/content/{module}")
    public ResponseEntity<Map<String, Object>> getFuncByModule(@PathVariable String module) {
        Map<String, Object> respResult = new LinkedHashMap<>();
        List<SysFunc> sysFuncs = sysFuncService.findByModule(module);
        respResult.put("data", sysFuncs);
        return ResponseEntity.ok(respResult);
    }

    @GetMapping("/content/{module}/{indexR}")
    public ResponseEntity<Map<String, Object>> getOne(@PathVariable String module, @PathVariable String indexR) {
        Map<String, Object> respResult = new LinkedHashMap<>();
        SysFuncId sysFuncId = new SysFuncId();
        sysFuncId.setModule(module);
        sysFuncId.setIndexR(indexR);
        SysFunc sysFunc = new SysFunc();
        sysFunc = sysFuncService.findOne(sysFuncId);
        respResult.put("data", sysFunc);
        return ResponseEntity.ok(respResult);
    }


    @PostMapping("/content")
    public ResponseEntity<Map<String, Object>> uplaod
            (Principal principal, @RequestParam(value = "file", required = false) MultipartFile file, @ModelAttribute SysFunc sysFunc) throws IOException {//1. 接受上傳的檔案 @RequestParam("file") MultipartFile file
        Map<String, Object> respResult = new LinkedHashMap<>();

        String module = sysFunc.getSysFuncId().getModule();
        String indexR = sysFunc.getSysFuncId().getIndexR();
        String image = sysFunc.getImage();
        String title = sysFunc.getTitle();
        String content = sysFunc.getContent();
        String url = sysFunc.getUrl();
        if (file != null) {
            /* 檔案上傳 */
            String filePath = uploadUtil.uploadFile(sysFunc.getSysFuncId(), file);
            sysFunc.getSysFuncId().setModule(module);
            sysFunc.getSysFuncId().setIndexR(indexR);
            sysFunc.setImage(filePath);
        }else{
            uploadUtil.deleteFile(sysFunc.getSysFuncId());
        }

        sysFunc.setUrl(url);
        sysFunc.setTitle(title);
        sysFunc.setContent(content);
        sysFunc.setAuth(principal.getName());
        sysFunc.setPostDate(new Date());
        sysFuncService.save(sysFunc);

        respResult.put("message", HttpStatus.OK);
        respResult.put("data", sysFunc);
        return ResponseEntity.ok(respResult);
    }

}
