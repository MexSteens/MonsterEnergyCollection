package fontys.s3.MonsterEnergyCollection.Data;

import fontys.s3.MonsterEnergyCollection.Business.Entity.EnergyCanEntity;
import fontys.s3.MonsterEnergyCollection.Business.Entity.ReviewEntity;
import fontys.s3.MonsterEnergyCollection.Business.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    ReviewEntity findByEnergyCanEntityAndUserEntity(Optional<EnergyCanEntity> energyCanEntity, Optional<UserEntity> userEntity);
}
