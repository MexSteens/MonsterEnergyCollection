package fontys.s3.MonsterEnergyCollection.DataTransferObject;

import fontys.s3.MonsterEnergyCollection.Business.Entity.EnergyCanEntity;
import fontys.s3.MonsterEnergyCollection.Business.Entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScannedEnergyCanDTO {
    private UserEntity userEntity;
    private EnergyCanEntity energyCanEntity;
}
