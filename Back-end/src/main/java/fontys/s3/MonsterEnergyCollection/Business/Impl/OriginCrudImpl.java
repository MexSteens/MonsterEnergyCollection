package fontys.s3.MonsterEnergyCollection.Business.Impl;

import fontys.s3.MonsterEnergyCollection.Business.OriginCrud;
import fontys.s3.MonsterEnergyCollection.Business.Entity.OriginEntity;
import fontys.s3.MonsterEnergyCollection.Business.OriginCrud;
import fontys.s3.MonsterEnergyCollection.Data.OriginRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OriginCrudImpl implements OriginCrud {
    private final OriginRepository originRepository;

    public OriginCrudImpl(OriginRepository originRepository){
        this.originRepository = originRepository;
    }

    public List<OriginEntity> GetAllOrigins() {
        List<OriginEntity> allCans = originRepository.findAll();
        return allCans;
    }

    public Optional<OriginEntity> GetOriginById(String name){
        Optional<OriginEntity> originEntity = originRepository.findById(name);
        return originEntity;
    }

    public void DeleteOrigin(String name) {
        originRepository.deleteById(name);
    }
}
