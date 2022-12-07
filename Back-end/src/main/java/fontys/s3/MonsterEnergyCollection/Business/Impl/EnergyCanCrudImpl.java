package fontys.s3.MonsterEnergyCollection.Business.Impl;

import fontys.s3.MonsterEnergyCollection.Business.EnergyCanCrud;
import fontys.s3.MonsterEnergyCollection.Business.Entity.EnergyCanEntity;
import fontys.s3.MonsterEnergyCollection.Data.EnergyCanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnergyCanCrudImpl implements EnergyCanCrud {
    private EnergyCanRepository energyCanRepository;

    public EnergyCanCrudImpl(EnergyCanRepository energyCanRepository){
        this.energyCanRepository = energyCanRepository;
    }

    public List<EnergyCanEntity> GetAllCans() {
        List<EnergyCanEntity> allCans = energyCanRepository.findAll();
        return allCans;
    }

    public Optional<EnergyCanEntity> GetEnergyCanById(Long id){
        Optional<EnergyCanEntity> energyCanEntity = energyCanRepository.findById(id);
        return energyCanEntity;
    }

    public EnergyCanEntity UpdateEnergyCan(Long id, EnergyCanEntity energyCanEntity){

        return energyCanRepository.findById(id)
                .map(energyCan -> {
                    energyCan.setName(energyCanEntity.getName());
                    energyCan.setDescription(energyCanEntity.getDescription());
                    energyCan.setCategory(energyCanEntity.getCategory());
                    return energyCanRepository.save(energyCan);
                })
                .orElseGet(() -> {
                   return null;
                });
    }



    public EnergyCanEntity CreateEnergyCan(EnergyCanEntity energyCanEntity){
        return energyCanRepository.save(energyCanEntity);
    }

    public void DeleteEnergyCan(Long id) {
        energyCanRepository.deleteById(id);
    }

}
