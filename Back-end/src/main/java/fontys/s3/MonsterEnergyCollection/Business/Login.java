package fontys.s3.MonsterEnergyCollection.Business;

import fontys.s3.MonsterEnergyCollection.Domain.LoginRequest;
import fontys.s3.MonsterEnergyCollection.Domain.LoginResponse;

public interface Login {
    LoginResponse login(LoginRequest loginRequest);
}
