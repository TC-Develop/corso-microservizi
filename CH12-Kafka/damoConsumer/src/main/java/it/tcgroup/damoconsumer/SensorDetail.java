package it.tcgroup.damoconsumer;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Data
@Component
public class SensorDetail {

    private Map<String, Dati> record = new HashMap<>();

    @Data
    public static class Dati{

        private Integer min;
        private Integer max;
        private Integer last;

    }

}
