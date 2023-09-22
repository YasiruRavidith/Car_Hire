package lk.ijse.CarHire.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Rent {
    private String rentid;
    private String from;
    private String to;
    private String advanced;
    private String refundable;
    private String isreturn;
    private String perdayrent;
    private String total;
    private String balance;
    private String custid;
    private String carid;
    private String extra;

}
