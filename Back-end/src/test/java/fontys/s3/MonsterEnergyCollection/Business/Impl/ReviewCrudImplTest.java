package fontys.s3.MonsterEnergyCollection.Business.Impl;

import fontys.s3.MonsterEnergyCollection.Business.Entity.EnergyCanEntity;
import fontys.s3.MonsterEnergyCollection.Business.Entity.ReviewEntity;
import fontys.s3.MonsterEnergyCollection.Business.Entity.UserEntity;
import fontys.s3.MonsterEnergyCollection.Business.ReviewCrud;
import fontys.s3.MonsterEnergyCollection.Data.EnergyCanRepository;
import fontys.s3.MonsterEnergyCollection.Data.ReviewRepository;
import fontys.s3.MonsterEnergyCollection.Data.UserRepository;
import fontys.s3.MonsterEnergyCollection.Domain.AccessToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.OpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class ReviewCrudImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EnergyCanRepository energyCanRepository;
    @Mock
    private ReviewRepository reviewRepository;

    AccessToken accessToken = new AccessToken("subject", List.of("ADMIN"), 2L);

    @InjectMocks
    private ReviewCrudImpl reviewCrud;

    @BeforeEach
    void setUp()
    {
        this.reviewCrud = new ReviewCrudImpl(this.reviewRepository, this.userRepository, this.energyCanRepository, accessToken);
    }

    @Test
    public void GetAllReviews_FindAll_ReturnTrue(){
        //Arrange
        EnergyCanEntity energyCan1 = new EnergyCanEntity("Green", "Description", "Monster Green", 12);
        UserEntity user1 = new UserEntity("name", "username");
        List<ReviewEntity> expected = new ArrayList<>();
        ReviewEntity reviewEntity1 = new ReviewEntity(4.0, user1, energyCan1);
        ReviewEntity reviewEntity2 = new ReviewEntity(5.0, user1, energyCan1);
        expected.add(reviewEntity1);
        expected.add(reviewEntity2);

        //Act
        when(reviewRepository.findAll()).thenReturn(List.of(reviewEntity1, reviewEntity2));
        List<ReviewEntity> actualResult = reviewCrud.GetAllReviews();

        //Assert
        assertEquals(expected, actualResult);
        verify(reviewRepository).findAll();
    }

    @Test
    public void CreateReview_PostReview_ReturnTrue() {
        //Arrange
        Long id = Long.valueOf(1);
        EnergyCanEntity energyCan1 = new EnergyCanEntity(id, "Green", "Description", "Monster Green", 4.0, 0, null, "img", 12);
        UserEntity user1 = new UserEntity(id, "name", "username", "password");
        ReviewEntity reviewEntity = new ReviewEntity(4.0, user1, energyCan1);

        //Act
        when(energyCanRepository.findById(id)).thenReturn(Optional.of(energyCan1));
        when(energyCanRepository.save(energyCan1)).thenReturn(energyCan1);
        when(reviewRepository.save(reviewEntity)).thenReturn(reviewEntity);
        ReviewEntity actualResult = reviewCrud.CreateReview(reviewEntity);

        //Assert
        assertEquals(reviewEntity, actualResult);
        verify(reviewRepository).save(reviewEntity);
    }

    @Test
    public void GetReviewById_GetById_ReturnTrue() {
        //Arrange
        Long id = Long.valueOf(2);
        EnergyCanEntity energyCan1 = new EnergyCanEntity("Green", "Description", "Monster Green", 12);
        UserEntity user1 = new UserEntity("name", "username");
        ReviewEntity reviewEntity = new ReviewEntity(4.0, user1, energyCan1);
        Optional<ReviewEntity> review1 = Optional.of(reviewEntity);

        //Act
        when(reviewRepository.findById(id)).thenReturn(review1);
        Optional<ReviewEntity> actualResult = reviewCrud.GetReviewById(id);

        //Assert
        assertEquals(review1, actualResult);
        verify(reviewRepository).findById(id);
    }

    @Test
    public void UpdateReview_UpdateReview_ReturnTrue() {
        //Arrange
        EnergyCanEntity energyCan1 = new EnergyCanEntity("Green", "Description", "Monster Green", 12);
        UserEntity user1 = new UserEntity("name", "username");
        ReviewEntity reviewEntity = new ReviewEntity(4.0, user1, energyCan1);
        Long id = Long.valueOf(2);

        //Act
        when(reviewRepository.findById(id)).thenReturn(Optional.of(reviewEntity));
        when(reviewRepository.save(reviewEntity)).thenReturn(reviewEntity);
        ReviewEntity actualResult = reviewCrud.UpdateReview(id, reviewEntity);

        //Assert
        assertEquals(reviewEntity, actualResult);
        verify(reviewRepository).save(reviewEntity);
    }

    @Test
    public void DeleteReview_DeleteReview_ReturnTrue() {
        //Arrange
        Long id = Long.valueOf(2);

        //Act
        reviewCrud.DeleteReview(id);

        //Assert
        verify(reviewRepository).deleteById(id);
    }

    @Test
    void findByCanAndUser() {
        //Arrange
        EnergyCanEntity energyCan1 = new EnergyCanEntity("Green", "Description", "Monster Green", 12);
        UserEntity user1 = new UserEntity("name", "username");
        ReviewEntity reviewEntity = new ReviewEntity(4.0, user1, energyCan1);

        //Act
        when(energyCanRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(energyCan1));
        when(userRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(user1));
        when(reviewRepository.findByEnergyCanEntityAndUserEntity(Optional.of(energyCan1), Optional.of(user1))).thenReturn(reviewEntity);
        ReviewEntity actualResult = reviewCrud.findByCanAndUser(Long.valueOf(1), Long.valueOf(1));

        //Assert
        assertEquals(reviewEntity, actualResult);
    }
}