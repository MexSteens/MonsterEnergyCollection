package fontys.s3.MonsterEnergyCollection.Presentation;

import fontys.s3.MonsterEnergyCollection.Business.EnergyCanCrud;
import fontys.s3.MonsterEnergyCollection.Business.Entity.EnergyCanEntity;
import fontys.s3.MonsterEnergyCollection.Business.Impl.EnergyCanCrudImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class EnergyCanControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnergyCanCrud energyCanCrud;

//    @InjectMocks
//    private EnergyCanController energyCanController;
//
//    @BeforeEach
//    void setUp()
//    {
//        this.energyCanController = new EnergyCanController(this.energyCanCrud);
//    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void getAllEnergyCans() throws Exception {
        //Arrange
        List<EnergyCanEntity> energyCanEntities = List.of(new EnergyCanEntity("green", "description", "category", 2), new EnergyCanEntity("blue", "description", "category", 2));

        //Act
        when(energyCanCrud.GetAllCans()).thenReturn(energyCanEntities);
        mockMvc.perform(get("/api/can"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type",
                        APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                [{"id":null,"name":"green","category":"category","description":"description","runningRating":null,"timesRated":null,"created":null,"imagePath":null,"barcode":2}, {"id":null,"name":"blue","category":"category","description":"description","runningRating":null,"timesRated":null,"created":null,"imagePath":null,"barcode":2}]
                """));

        //Assert
        verify(energyCanCrud).GetAllCans();


    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void getEnergyCanById() throws Exception {
        //Arrange
        Optional<EnergyCanEntity> energyCanEntity = Optional.of(new EnergyCanEntity("green", "description", "category", 2));


        //Act
        when(energyCanCrud.GetEnergyCanById(1L)).thenReturn(energyCanEntity);
        mockMvc.perform(get("/api/can/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type",
                        APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                {"id":null,"name":"green","category":"category","description":"description","runningRating":null,"timesRated":null,"created":null,"imagePath":null,"barcode":2}
                """));

        //Assert
        verify(energyCanCrud).GetEnergyCanById(1L);

    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void createEnergyCan() throws Exception {
        //Arrange
        EnergyCanEntity energyCanEntity = new EnergyCanEntity("green", "description", "category", 2);

        //Act
        when(energyCanCrud.CreateEnergyCan(Mockito.any())).thenReturn(energyCanEntity);
        mockMvc.perform(post("/api/can")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                        {
                        "name": "purple",
                        "description": "desc",
                        "category": "blba",                        
                        "barcode": "2"    
                        }
                        """))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Content-Type",
                        APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                {"id":null,"name":"green","category":"category","description":"description","runningRating":null,"timesRated":null,"created":null,"imagePath":null,"barcode":2}
                """));

        //Assert
        verify(energyCanCrud).CreateEnergyCan(Mockito.any());


    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void updateEnergyCan() throws Exception {
        //Arrange
        EnergyCanEntity energyCanEntity = new EnergyCanEntity("green", "description", "category", 2);

        //Act


        //Assert
    }

    @Test
    @WithMockUser(username = "user", roles = {"MODERATOR"})
    void deleteEnergyCan() throws Exception {
        //Arrange
        EnergyCanEntity energyCanEntity = new EnergyCanEntity("green", "description", "category", 2);

        //Act

        //Assert
    }
}