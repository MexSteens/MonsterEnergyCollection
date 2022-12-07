package fontys.s3.MonsterEnergyCollection.Business;

import fontys.s3.MonsterEnergyCollection.Business.Entity.EnergyCanEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface EnergyCanCrud {
    List<EnergyCanEntity> GetAllCans();

    Optional<EnergyCanEntity> GetEnergyCanById(Long id);

    EnergyCanEntity UpdateEnergyCan(Long id, EnergyCanEntity energyCanEntity);

    EnergyCanEntity CreateEnergyCan(EnergyCanEntity energyCanEntity);

    void DeleteEnergyCan(Long id);
}
