package it.tcgroup.damoconsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("sensor/api/v1")
public class Controller {
    @Autowired
    private SensorDetail sensorDetail;

    @GetMapping
    public ResponseEntity<Map<String,SensorDetail.Dati>> getMedia(){
        return ResponseEntity.ok(sensorDetail.getRecord());
    }

}
