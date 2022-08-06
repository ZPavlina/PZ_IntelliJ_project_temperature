package pz_project_restapi.temperature.model;

import java.time.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemLDT {

    private Long id;
    private LocalDateTime localDateTime;
    private float temperatureLDT;

}
