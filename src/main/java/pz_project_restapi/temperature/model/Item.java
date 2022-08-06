package pz_project_restapi.temperature.model;

import java.time.*;
import java.util.*;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "measurement")

public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //(yyyy-MM-dd HH:mm)
    @Column(name = "dateandtime")
    private String dateAndTime;


    @Column(name = "temperature")
    private float temperature;
}
