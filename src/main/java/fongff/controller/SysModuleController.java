package fongff.controller;

import fongff.model.SysModule;
import fongff.service.SysModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sys")
public class SysModuleController {
    @Autowired
    private SysModuleService sysModuleService;

    @GetMapping("/sysModule")
    public ResponseEntity<Map<String, Object>> getSysModule() {
        Map<String, Object> respResult = new LinkedHashMap<>();
        List<SysModule> sysModuleList = sysModuleService.findAll();
        respResult.put("data", sysModuleList);
        return ResponseEntity.ok(respResult);
    }

    @DeleteMapping("/sysModule/{id}")
    public ResponseEntity<Map<String, Object>> deleteSysModule(@PathVariable String id) {
        Map<String, Object> respResult = new LinkedHashMap<>();
        SysModule sysModule = new SysModule();
        sysModuleService.delete(id);
        return ResponseEntity.ok(respResult);
    }

    @PostMapping("/sysModule")
    public ResponseEntity<Map<String, Object>> addSysModule(@RequestBody SysModule sysModule) {
        Map<String, Object> respResult = new LinkedHashMap<>();
        List<String> validateErrors = new ArrayList<>();
        if (sysModule.id.isEmpty()) {
            validateErrors.add("id is empty");
        }
        if (sysModule.cname.isEmpty()) {
            validateErrors.add("cname is empty");
        }
        if (sysModule.ename.isEmpty()) {
            validateErrors.add("ename is empty");
        }
        if (!validateErrors.isEmpty()) {
            Map<String, Object> errorMsg = new LinkedHashMap<>();
            errorMsg.put("validateErrors", validateErrors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMsg);
        }

        sysModuleService.save(sysModule);
        respResult.put("data", sysModule);
        return ResponseEntity.ok(respResult);
    }
}
