package lk.ijse.CarHire.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class ManageCar {
    private String idCar;
    private String Brand;
    private String Model;
    private String Vehicle_Number;
    private String year;
    private String Price_per_day;
    private String category_id;
    private String availability;
    }