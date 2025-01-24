package lecturedependency.projectjdbc.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Car {
    private Long id;
    private String brand;
    private String model;
    private double engineVolume;
    private int maxSpeed;
}
