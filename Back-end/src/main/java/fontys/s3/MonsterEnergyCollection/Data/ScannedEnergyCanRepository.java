package fontys.s3.MonsterEnergyCollection.Data;

import fontys.s3.MonsterEnergyCollection.Business.Entity.ScannedEnergyCanEntity;
import fontys.s3.MonsterEnergyCollection.Business.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScannedEnergyCanRepository extends JpaRepository<ScannedEnergyCanEntity, Long> {
    List<ScannedEnergyCanEntity> findScannedEnergyCanEntityByUserEntity(Optional<UserEntity> userEntity);
}
