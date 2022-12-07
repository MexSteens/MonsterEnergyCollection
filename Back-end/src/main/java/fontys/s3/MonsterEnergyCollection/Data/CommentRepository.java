package fontys.s3.MonsterEnergyCollection.Data;

import fontys.s3.MonsterEnergyCollection.Business.Entity.CommentEntity;
import fontys.s3.MonsterEnergyCollection.Business.Entity.EnergyCanEntity;
import fontys.s3.MonsterEnergyCollection.Business.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByEnergyCanEntityAndApproved(Optional<EnergyCanEntity> energyCanEntity, Boolean approved);

    List<CommentEntity> findByApproved(Boolean approved);
    CommentEntity findByEnergyCanEntityAndUserEntity(Optional<EnergyCanEntity> energyCanEntity, Optional<UserEntity> userEntity);
}
