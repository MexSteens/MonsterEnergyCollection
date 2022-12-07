package fontys.s3.MonsterEnergyCollection.Business;

import fontys.s3.MonsterEnergyCollection.Business.Entity.ScannedEnergyCanEntity;

import java.util.List;
import java.util.Optional;

public interface ScannedEnergyCanCrud {
//    List<ScannedEnergyCanEntity> GetAllScannedEnergyCans();

    Optional<ScannedEnergyCanEntity> GetScannedEnergyCanById(Long id);

    ScannedEnergyCanEntity UpdateScannedEnergyCan(Long id, ScannedEnergyCanEntity scannedEnergyCanEntity);

    ScannedEnergyCanEntity CreateScannedEnergyCan(ScannedEnergyCanEntity scannedEnergyCanEntity);

//    void DeleteScannedEnergyCan(Long id);

    List<ScannedEnergyCanEntity> FindByUserId(Long userId);
}
