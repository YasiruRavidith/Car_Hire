package lk.ijse.CarHire.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Register {
    private String id;
    private String username;
    private String name;
    private String password;
    private String mobile;
    private String email;

}
