package fontys.s3.MonsterEnergyCollection.Domain;

import fontys.s3.MonsterEnergyCollection.Business.Entity.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
public class LoginResponse {
    private String accessToken;

    private Long userId;

    private Collection<RoleEntity> role;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public LoginResponse(String accessToken, Long userId, Collection<RoleEntity> role){
        this.accessToken = accessToken;
        this.userId = userId;
        this.role = role;
    }
}
