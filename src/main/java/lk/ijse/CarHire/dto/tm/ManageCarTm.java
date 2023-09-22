package lk.ijse.CarHire.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class ManageCarTm {
    private String idCar;
    private String Brand;
    private String Model;
    private String Vehicle_Number;

    private String Price_per_day;
    private String category_id;
    private String availability;
    private String year;
}
