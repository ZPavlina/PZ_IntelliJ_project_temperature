package pz_project_restapi.temperature.model;

import javax.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TemperatureForm {

    private float temperatureA;
    private float temperatureB;
    private String timeX;
    private String timeY;


}
