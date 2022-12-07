package fontys.s3.MonsterEnergyCollection.Business.Impl;

import fontys.s3.MonsterEnergyCollection.Business.Entity.EnergyCanEntity;
import fontys.s3.MonsterEnergyCollection.Business.Entity.ReviewEntity;
import fontys.s3.MonsterEnergyCollection.Business.Entity.ScannedEnergyCanEntity;
import fontys.s3.MonsterEnergyCollection.Business.Entity.UserEntity;
import fontys.s3.MonsterEnergyCollection.Data.ScannedEnergyCanRepository;
import fontys.s3.MonsterEnergyCollection.Data.UserRepository;
import fontys.s3.MonsterEnergyCollection.Domain.AccessToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class ScannedEnergyCanCrudImplTest {
    @Mock
    private ScannedEnergyCanRepository scannedEnergyCanRepository;

    @Mock
    private UserRepository userRepository;

    AccessToken accessToken = new AccessToken("subject", List.of("ADMIN"), 2L);


    @InjectMocks
    private ScannedEnergyCanCrudImpl scannedEnergyCanCrud;

//    @Test
//    public void GetAllScannedEnergyCans_FindAll_ReturnTrue(){
//        //Arrange
//        EnergyCanEntity energyCan1 = new EnergyCanEntity("Green", "Description", "Monster Green", 12);
//        UserEntity user1 = new UserEntity("name", "username");
//        List<ScannedEnergyCanEntity> expected = new ArrayList<>();
//        ScannedEnergyCanEntity scannedEnergyCanEntity1 = new ScannedEnergyCanEntity(energyCan1, user1);
//        ScannedEnergyCanEntity scannedEnergyCanEntity2 = new ScannedEnergyCanEntity(energyCan1, user1);
//        expected.add(scannedEnergyCanEntity1);
//        expected.add(scannedEnergyCanEntity2);
//
//        //Act
//        when(scannedEnergyCanRepository.findAll()).thenReturn(List.of(scannedEnergyCanEntity1, scannedEnergyCanEntity2));
//        List<ScannedEnergyCanEntity> actualResult = scannedEnergyCanCrud.GetAllScannedEnergyCans();
//
//        //Assert
//        assertEquals(expected, actualResult);
//        verify(scannedEnergyCanRepository).findAll();
//    }

    @Test
    public void GetScannedEnergyCanById_AccessTokenMODERATOR_ReturnEqual() {
        //Arrange
        AccessToken accessTokenModerator = new AccessToken("subject", List.of("MODERATOR"), 2L);
        this.scannedEnergyCanCrud = new ScannedEnergyCanCrudImpl(this.scannedEnergyCanRepository, this.userRepository, accessTokenModerator);
        Long id = Long.valueOf(2);
        EnergyCanEntity energyCan1 = new EnergyCanEntity("Green", "Description", "Monster Green", 12);
        UserEntity user1 = new UserEntity("name", "username");
        ScannedEnergyCanEntity scannedEnergyCanEntity = new ScannedEnergyCanEntity(energyCan1, user1);
        Optional<ScannedEnergyCanEntity> scannedEnergyCan1 = Optional.of(scannedEnergyCanEntity);

        //Act
        when(scannedEnergyCanRepository.findById(id)).thenReturn(scannedEnergyCan1);
        Optional<ScannedEnergyCanEntity> actualResult = scannedEnergyCanCrud.GetScannedEnergyCanById(id);

        //Assert
        assertEquals(scannedEnergyCan1, actualResult);
        verify(scannedEnergyCanRepository).findById(id);
    }

    @Test
    public void GetScannedEnergyCanById_AccessTokenSameUserId_ReturnEqual() {
        //Arrange
        AccessToken accessTokenModerator = new AccessToken("subject", List.of("USER"), 2L);
        this.scannedEnergyCanCrud = new ScannedEnergyCanCrudImpl(this.scannedEnergyCanRepository, this.userRepository, accessTokenModerator);
        Long id = Long.valueOf(2);
        EnergyCanEntity energyCan1 = new EnergyCanEntity("Green", "Description", "Monster Green", 12);
        UserEntity user1 = UserEntity.builder()
                .id(2L)
                .username("username")
                .name("name")
                .build();
        ScannedEnergyCanEntity scannedEnergyCanEntity = new ScannedEnergyCanEntity(energyCan1, user1);
        Optional<ScannedEnergyCanEntity> scannedEnergyCan1 = Optional.of(scannedEnergyCanEntity);

        //Act
        when(scannedEnergyCanRepository.findById(id)).thenReturn(scannedEnergyCan1);
        Optional<ScannedEnergyCanEntity> actualResult = scannedEnergyCanCrud.GetScannedEnergyCanById(id);

        //Assert
        assertEquals(scannedEnergyCan1, actualResult);
        verify(scannedEnergyCanRepository).findById(id);
    }

    @Test
    public void GetScannedEnergyCanById_AccessTokenSameDifferentUser_ReturnEqual() {
        //Arrange
        AccessToken accessTokenModerator = new AccessToken("subject", List.of("MODERATOR"), 2L);
        this.scannedEnergyCanCrud = new ScannedEnergyCanCrudImpl(this.scannedEnergyCanRepository, this.userRepository, accessTokenModerator);
        Long id = Long.valueOf(2);
        EnergyCanEntity energyCan1 = new EnergyCanEntity("Green", "Description", "Monster Green", 12);
        UserEntity user1 = UserEntity.builder()
                .id(4L)
                .username("username")
                .name("name")
                .build();
        ScannedEnergyCanEntity scannedEnergyCanEntity = new ScannedEnergyCanEntity(energyCan1, user1);
        Optional<ScannedEnergyCanEntity> scannedEnergyCan1 = Optional.of(scannedEnergyCanEntity);

        //Act
        when(scannedEnergyCanRepository.findById(id)).thenReturn(scannedEnergyCan1);
        Optional<ScannedEnergyCanEntity> actualResult = scannedEnergyCanCrud.GetScannedEnergyCanById(id);

        //Assert
        assertEquals(scannedEnergyCan1, actualResult);
        verify(scannedEnergyCanRepository).findById(id);
    }

    @Test
    public void CreateScannedEnergyCan_PostScannedEnergyCan_ReturnTrue() {
        //Arrange
        this.scannedEnergyCanCrud = new ScannedEnergyCanCrudImpl(this.scannedEnergyCanRepository, this.userRepository, accessToken);
        EnergyCanEntity energyCan1 = new EnergyCanEntity("Green", "Description", "Monster Green", 12);
        UserEntity user1 = new UserEntity("name", "username");
        ScannedEnergyCanEntity scannedEnergyCanEntity1 = new ScannedEnergyCanEntity(energyCan1, user1);

        //Act
        when(scannedEnergyCanRepository.save(scannedEnergyCanEntity1)).thenReturn(scannedEnergyCanEntity1);
        ScannedEnergyCanEntity actualResult = scannedEnergyCanCrud.CreateScannedEnergyCan(scannedEnergyCanEntity1);

        //Assert
        assertEquals(scannedEnergyCanEntity1, actualResult);
        verify(scannedEnergyCanRepository).save(scannedEnergyCanEntity1);
    }

    @Test
    public void GetScannedEnergyCanById_GetById_ReturnTrue() {
        //Arrange
        this.scannedEnergyCanCrud = new ScannedEnergyCanCrudImpl(this.scannedEnergyCanRepository, this.userRepository, accessToken);
        Long id = Long.valueOf(2);
        EnergyCanEntity energyCan1 = new EnergyCanEntity("Green", "Description", "Monster Green", 12);
        UserEntity user1 = new UserEntity("name", "username");
        ScannedEnergyCanEntity scannedEnergyCanEntity = new ScannedEnergyCanEntity(energyCan1, user1);
        Optional<ScannedEnergyCanEntity> scannedEnergyCan1 = Optional.of(scannedEnergyCanEntity);

        //Act
        when(scannedEnergyCanRepository.findById(id)).thenReturn(scannedEnergyCan1);
        Optional<ScannedEnergyCanEntity> actualResult = scannedEnergyCanCrud.GetScannedEnergyCanById(id);

        //Assert
        assertEquals(scannedEnergyCan1, actualResult);
        verify(scannedEnergyCanRepository).findById(id);
    }

    @Test
    public void UpdateScannedEnergyCan_UpdateCan_ReturnTrue() {
        //Arrange
        this.scannedEnergyCanCrud = new ScannedEnergyCanCrudImpl(this.scannedEnergyCanRepository, this.userRepository, accessToken);
        EnergyCanEntity energyCan1 = new EnergyCanEntity("Green", "Description", "Monster Green", 12);
        UserEntity user1 = new UserEntity("name", "username");
        ScannedEnergyCanEntity scannedEnergyCanEntity = new ScannedEnergyCanEntity(energyCan1, user1);
        Long id = Long.valueOf(2);

        //Act
        when(scannedEnergyCanRepository.findById(id)).thenReturn(Optional.of(scannedEnergyCanEntity));
        when(scannedEnergyCanRepository.save(scannedEnergyCanEntity)).thenReturn(scannedEnergyCanEntity);
        ScannedEnergyCanEntity actualResult = scannedEnergyCanCrud.UpdateScannedEnergyCan(id, scannedEnergyCanEntity);

        //Assert
        assertEquals(scannedEnergyCanEntity, actualResult);
        verify(scannedEnergyCanRepository).save(scannedEnergyCanEntity);
    }

//    @Test
//    public void DeleteScannedEnergyCan_DeleteCan_ReturnTrue() {
//        //Arrange
//        Long id = Long.valueOf(2);
//
//        //Act
//        scannedEnergyCanCrud.DeleteScannedEnergyCan(id);
//
//        //Assert
//        verify(scannedEnergyCanRepository).deleteById(id);
//    }

    @Test
    void findByUserId() {
        //Arrange
        this.scannedEnergyCanCrud = new ScannedEnergyCanCrudImpl(this.scannedEnergyCanRepository, this.userRepository, accessToken);
        EnergyCanEntity energyCan1 = new EnergyCanEntity("Green", "Description", "Monster Green", 12);
        UserEntity user1 = new UserEntity("name", "username");
        ScannedEnergyCanEntity scannedEnergyCanEntity = new ScannedEnergyCanEntity(energyCan1, user1);

        //Act
        when(userRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(user1));
        when(scannedEnergyCanRepository.findScannedEnergyCanEntityByUserEntity(Optional.of(user1))).thenReturn(List.of(scannedEnergyCanEntity));
        List<ScannedEnergyCanEntity> actualResult = scannedEnergyCanCrud.FindByUserId(Long.valueOf(1));

        //Assert
        assertEquals(List.of(scannedEnergyCanEntity), actualResult);
    }

}