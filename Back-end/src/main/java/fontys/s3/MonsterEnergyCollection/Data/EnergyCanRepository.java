package fontys.s3.MonsterEnergyCollection.Data;

import fontys.s3.MonsterEnergyCollection.Business.Entity.EnergyCanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnergyCanRepository extends JpaRepository<EnergyCanEntity, Long> {
}
