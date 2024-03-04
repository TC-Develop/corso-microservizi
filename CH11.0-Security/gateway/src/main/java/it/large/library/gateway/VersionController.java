package it.large.library.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/version")
public class VersionController {

    @Value("${app.name}")
    public String appName;

    @Value("${app.version}")
    public String appVersion;

    @Value("${app.description}")
    public String appDescription;

    @GetMapping("")
    public ResponseEntity<Map<String,Object>> version(){
        Map<String, Object> result = new HashMap<>();

        result.put("appName", appName);
        result.put("appDescription", appDescription);
        result.put("appVersion", appVersion);

        return ResponseEntity.ok(result);
    }

}
