package fontys.s3.MonsterEnergyCollection.Business.Impl;


import fontys.s3.MonsterEnergyCollection.Business.Entity.CommentEntity;
import fontys.s3.MonsterEnergyCollection.Business.Entity.RoleEnum;
import fontys.s3.MonsterEnergyCollection.Business.Entity.ScannedEnergyCanEntity;
import fontys.s3.MonsterEnergyCollection.Business.Entity.UserEntity;
import fontys.s3.MonsterEnergyCollection.Business.Exception.UnauthorizedDataAccessException;
import fontys.s3.MonsterEnergyCollection.Business.ScannedEnergyCanCrud;
import fontys.s3.MonsterEnergyCollection.Data.EnergyCanRepository;
import fontys.s3.MonsterEnergyCollection.Data.ScannedEnergyCanRepository;
import fontys.s3.MonsterEnergyCollection.Data.UserRepository;
import fontys.s3.MonsterEnergyCollection.Domain.AccessToken;
import fontys.s3.MonsterEnergyCollection.Security.IsAuthenticated.IsAuthenticated;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ScannedEnergyCanCrudImpl implements ScannedEnergyCanCrud {
    private final ScannedEnergyCanRepository scannedEnergyCanRepository;
    private final UserRepository userRepository;

    private AccessToken requestAccessToken;

    public ScannedEnergyCanCrudImpl(ScannedEnergyCanRepository scannedEnergyCanRepository, UserRepository userRepository, AccessToken accessToken){
        this.scannedEnergyCanRepository = scannedEnergyCanRepository;
        this.userRepository = userRepository;
        this.requestAccessToken = accessToken;
    }

//    public List<ScannedEnergyCanEntity> GetAllScannedEnergyCans() {
//        List<ScannedEnergyCanEntity> allCans = scannedEnergyCanRepository.findAll();
//        return allCans;
//    }

    public Optional<ScannedEnergyCanEntity> GetScannedEnergyCanById(Long id){
        Optional<ScannedEnergyCanEntity> scannedEnergyCanEntity = scannedEnergyCanRepository.findById(id);
        if (scannedEnergyCanEntity.isPresent()) {
            Authorization(scannedEnergyCanEntity.get().getUserEntity().getId());
        }
        return scannedEnergyCanEntity;
    }

    public ScannedEnergyCanEntity UpdateScannedEnergyCan(Long id, ScannedEnergyCanEntity scannedEnergyCanEntity){
        Authorization(scannedEnergyCanEntity.getUserEntity().getId());

        return scannedEnergyCanRepository.findById(id)
                .map(scannedEnergyCan -> {
                    scannedEnergyCan.setUserEntity(scannedEnergyCanEntity.getUserEntity());
                    scannedEnergyCan.setEnergyCanEntity(scannedEnergyCanEntity.getEnergyCanEntity());
                    return scannedEnergyCanRepository.save(scannedEnergyCan);
                })
                .orElseGet(() -> {
                    return null;
                });
    }



    public ScannedEnergyCanEntity CreateScannedEnergyCan(ScannedEnergyCanEntity scannedEnergyCanEntity){
        Authorization(scannedEnergyCanEntity.getUserEntity().getId());
        return scannedEnergyCanRepository.save(scannedEnergyCanEntity);
    }

//    public void DeleteScannedEnergyCan(Long id) {
//        scannedEnergyCanRepository.deleteById(id);
//    }

    public List<ScannedEnergyCanEntity> FindByUserId(Long userId){
        Authorization(userId);
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        return scannedEnergyCanRepository.findScannedEnergyCanEntityByUserEntity(userEntity);
    }

    private void Authorization(Long userId) {
        if (!requestAccessToken.hasRole(RoleEnum.ADMIN.name())) {
            if (!requestAccessToken.hasRole(RoleEnum.MODERATOR.name())) {
                if (!Objects.equals(requestAccessToken.getUserId(), userId)) {
                    throw new
                            UnauthorizedDataAccessException("USER_ID_NOT_FROM_LOGGED_IN_USER");
                }
            }
        }
    }
}
