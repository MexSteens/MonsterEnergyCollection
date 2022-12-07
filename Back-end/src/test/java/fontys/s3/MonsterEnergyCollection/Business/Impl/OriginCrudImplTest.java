package fontys.s3.MonsterEnergyCollection.Business.Impl;

import fontys.s3.MonsterEnergyCollection.Business.Entity.EnergyCanEntity;
import fontys.s3.MonsterEnergyCollection.Business.Entity.OriginEntity;
import fontys.s3.MonsterEnergyCollection.Data.EnergyCanRepository;
import fontys.s3.MonsterEnergyCollection.Data.OriginRepository;
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

class OriginCrudImplTest {
    @Mock
    private OriginRepository originRepository;

    @InjectMocks
    private OriginCrudImpl originCrud;

    @BeforeEach
    void setUp()
    {
        this.originCrud = new OriginCrudImpl(this.originRepository);
    }

    @Test
    public void GetAllOrigins_FindAll_ReturnTrue(){
        //Arrange
        List<OriginEntity> expected = new ArrayList<>();
        OriginEntity originEntity1 = new OriginEntity("Belgium", "Europe");
        OriginEntity originEntity2 = new OriginEntity("Belgium", "Europe");
        expected.add(originEntity1);
        expected.add(originEntity2);

        //Act
        when(originRepository.findAll()).thenReturn(List.of(originEntity1, originEntity2));
        List<OriginEntity> actualResult = originCrud.GetAllOrigins();

        //Assert
        assertEquals(expected, actualResult);
        verify(originRepository).findAll();
    }

    @Test
    public void GetOriginById_GetById_ReturnTrue() {
        //Arrange
        OriginEntity originEntity = new OriginEntity("Belgium", "Europe");
        Optional<OriginEntity> origin1 = Optional.of(originEntity);

        //Act
        when(originRepository.findById("Belgium")).thenReturn(origin1);
        Optional<OriginEntity> actualResult = originCrud.GetOriginById("Belgium");

        //Assert
        assertEquals(origin1, actualResult);
        verify(originRepository).findById("Belgium");
    }


    @Test
    public void DeleteOrigin_DeleteOrigin_ReturnTrue() {
        //Arrange
        String id = "Belgium";

        //Act
        originCrud.DeleteOrigin(id);

        //Assert
        verify(originRepository).deleteById(id);
    }

}