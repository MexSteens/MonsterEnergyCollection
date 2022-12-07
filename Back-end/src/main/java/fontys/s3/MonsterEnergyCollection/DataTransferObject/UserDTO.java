package fontys.s3.MonsterEnergyCollection.DataTransferObject;

import fontys.s3.MonsterEnergyCollection.Security.PasswordValidation.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {
    private String name;
    private String username;
    @ValidPassword
    private String password;
}
