package it.tcgroup.sensor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorData implements Serializable {
    private String value;
    private String location;
    private Map<String,Object> options;
}
