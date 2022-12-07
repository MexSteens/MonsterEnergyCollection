package fontys.s3.MonsterEnergyCollection.Business;

import fontys.s3.MonsterEnergyCollection.Business.Entity.OriginEntity;

import java.util.List;
import java.util.Optional;

public interface OriginCrud {
    List<OriginEntity> GetAllOrigins();

    Optional<OriginEntity> GetOriginById(String name);

    void DeleteOrigin(String name);
}
