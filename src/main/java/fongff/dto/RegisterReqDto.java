package fongff.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class RegisterReqDto {
    String userName;
    String userPassword;
    String userRole;

}
