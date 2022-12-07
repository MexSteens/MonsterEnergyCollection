package fontys.s3.MonsterEnergyCollection.Business.Impl;

import fontys.s3.MonsterEnergyCollection.Business.Entity.EnergyCanEntity;
import fontys.s3.MonsterEnergyCollection.Data.EnergyCanRepository;
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

public class EnergyCanCrudImplTest {


    @Mock
    private EnergyCanRepository energyCanRepository;

    @InjectMocks
    private EnergyCanCrudImpl energyCanCrud;

    @BeforeEach
    void setUp()
    {
        this.energyCanCrud = new EnergyCanCrudImpl(this.energyCanRepository);
    }

    @Test
    public void GetAllCans_FindAll_ReturnTrue(){
        //Arrange
        List<EnergyCanEntity> expected = new ArrayList<>();
        EnergyCanEntity energyCanEntity1 = new EnergyCanEntity("Blue", "mockedCan", "Monster Energy", 2);
        EnergyCanEntity energyCanEntity2 = new EnergyCanEntity("Green", "test1", "Monster Energy", 2);
        expected.add(energyCanEntity1);
        expected.add(energyCanEntity2);

        //Act
        when(energyCanRepository.findAll()).thenReturn(List.of(energyCanEntity1, energyCanEntity2));
        List<EnergyCanEntity> actualResult = energyCanCrud.GetAllCans();

        //Assert
        assertEquals(expected, actualResult);
        verify(energyCanRepository).findAll();
    }

    @Test
    public void CreateEnergyCan_PostCan_ReturnTrue() {
        //Arrange
        EnergyCanEntity energyCanEntity1 = new EnergyCanEntity("Blue", "test1", "Monster Energy", 2);
        EnergyCanEntity energyCanEntity2 = new EnergyCanEntity("Green", "test1", "Monster Energy", 2);

        //Act
        when(energyCanRepository.save(energyCanEntity1)).thenReturn(energyCanEntity2);
        EnergyCanEntity actualResult = energyCanCrud.CreateEnergyCan(energyCanEntity1);

        //Assert
        assertEquals(energyCanEntity2, actualResult);
        verify(energyCanRepository).save(energyCanEntity1);
    }

    @Test
    public void GetEnergyCanById_GetById_ReturnTrue() {
        //Arrange
        Long id = Long.valueOf(2);
        EnergyCanEntity energyCanEntity = new EnergyCanEntity("Blue", "test1", "Monster Energy", 2);
        Optional<EnergyCanEntity> energyCan1 = Optional.of(energyCanEntity);

        //Act
        when(energyCanRepository.findById(id)).thenReturn(energyCan1);
        Optional<EnergyCanEntity> actualResult = energyCanCrud.GetEnergyCanById(id);

        //Assert
        assertEquals(energyCan1, actualResult);
        verify(energyCanRepository).findById(id);
    }

    @Test
    public void UpdateEnergyCan_UpdateCan_ReturnTrue() {
        //Arrange
        EnergyCanEntity energyCanEntity = new EnergyCanEntity("Green", "test1", "Monster Energy", 2);
        Long id = Long.valueOf(2);

        //Act
        when(energyCanRepository.findById(id)).thenReturn(Optional.of(energyCanEntity));
        when(energyCanRepository.save(energyCanEntity)).thenReturn(energyCanEntity);
        EnergyCanEntity actualResult = energyCanCrud.UpdateEnergyCan(id, energyCanEntity);

        //Assert
        assertEquals(energyCanEntity, actualResult);
        verify(energyCanRepository).save(energyCanEntity);
    }

    @Test
    public void DeleteEnergyCan_DeleteCan_ReturnTrue() {
        //Arrange
        Long id = Long.valueOf(2);

        //Act
        energyCanCrud.DeleteEnergyCan(id);

        //Assert
        verify(energyCanRepository).deleteById(id);
    }
}