package fontys.s3.MonsterEnergyCollection.Data;

import fontys.s3.MonsterEnergyCollection.Business.Entity.LoginAttemptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginAttemptRepository extends JpaRepository<LoginAttemptEntity, String> {

}
