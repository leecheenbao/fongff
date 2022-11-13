package fongff.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/ga")
public class AnalyticsController {

    @GetMapping("dataInfo")
    public ResponseEntity<Map<String, Object>> getDataInfo(){
        Map<String,Object> respResult = new LinkedHashMap<>();
        return ResponseEntity.ok(respResult);

    }
}