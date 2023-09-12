package lk.ijse.CarHire.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Customer {
    private String idCustomer;
    private String name;
    private String nic;
    private String adress;
    private String phone;
}
