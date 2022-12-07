package fontys.s3.MonsterEnergyCollection.Business.Impl;

import fontys.s3.MonsterEnergyCollection.Business.Entity.CommentEntity;
import fontys.s3.MonsterEnergyCollection.Business.Entity.EnergyCanEntity;
import fontys.s3.MonsterEnergyCollection.Business.Entity.ReviewEntity;
import fontys.s3.MonsterEnergyCollection.Business.Entity.UserEntity;
import fontys.s3.MonsterEnergyCollection.Business.Exception.InvalidCredentialsException;
import fontys.s3.MonsterEnergyCollection.Business.Exception.UnauthorizedDataAccessException;
import fontys.s3.MonsterEnergyCollection.Data.CommentRepository;
import fontys.s3.MonsterEnergyCollection.Data.EnergyCanRepository;
import fontys.s3.MonsterEnergyCollection.Data.UserRepository;
import fontys.s3.MonsterEnergyCollection.Domain.AccessToken;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentCrudImplTest {
    @Mock
    private CommentRepository commentRepository;

    @Mock
    private EnergyCanRepository energyCanRepository;

    @Mock
    private UserRepository userRepository;

    AccessToken accessToken1 = new AccessToken("subject", List.of("ADMIN"), 2L);


    @InjectMocks
    private CommentCrudImpl commentCrud;

    @BeforeEach
    void setUp()
    {
        this.commentCrud = new CommentCrudImpl(this.commentRepository, this.userRepository, this.energyCanRepository, this.accessToken1);
    }

    @Test
    public void GetAllComments_FindAll_ReturnTrue() {
        //Arrange
        EnergyCanEntity energyCan1 = new EnergyCanEntity("Green", "Description", "Monster Green", 12);
        UserEntity user1 = new UserEntity("name", "username");
        ReviewEntity review1 = new ReviewEntity(12.0, user1, energyCan1);

        List<CommentEntity> expectedResult = new ArrayList<>();
        CommentEntity comment1 = new CommentEntity("comment1", true, false, user1, energyCan1, review1);
        CommentEntity comment2 = new CommentEntity("comment2", true, false, user1, energyCan1, review1);
        expectedResult.add(comment1);
        expectedResult.add(comment2);

        //Act
        when(commentRepository.findAll()).thenReturn(List.of(comment1, comment2));
        List<CommentEntity> actualResult = commentCrud.GetAllComments();

        //Assert
        assertEquals(expectedResult, actualResult);
        verify(commentRepository).findAll();
    }

    @Test
    public void CreateComment_PostComment_ReturnTrue() {
        //Arrange
        EnergyCanEntity energyCan1 = new EnergyCanEntity("Green", "Description", "Monster Green", 12);
        UserEntity user1 = new UserEntity("name", "username");
        ReviewEntity review1 = new ReviewEntity(12.0, user1, energyCan1);

        CommentEntity comment1 = new CommentEntity("comment1", true, false, user1, energyCan1, review1);
        CommentEntity comment2 = new CommentEntity("comment2", true, false, user1, energyCan1, review1);

        //Act
        when(commentRepository.save(comment1)).thenReturn(comment2);
        CommentEntity actualResult = commentCrud.CreateComment(comment1);

        //Assert
        assertEquals(comment2, actualResult);
        verify(commentRepository).save(comment1);
    }

    @Test
    public void GetCommentById_GetById_ReturnTrue() {
        //Arrange
        Long id = Long.valueOf(2);
        EnergyCanEntity energyCan1 = new EnergyCanEntity("Green", "Description", "Monster Green", 12);
        UserEntity user1 = new UserEntity("name", "username");
        ReviewEntity review1 = new ReviewEntity(12.0, user1, energyCan1);

        CommentEntity commentEntity = new CommentEntity("comment1", true, false, user1, energyCan1, review1);
        Optional<CommentEntity> comment1 = Optional.of(commentEntity);

        //Act
        when(commentRepository.findById(id)).thenReturn(comment1);
        Optional<CommentEntity> actualResult = commentCrud.GetCommentById(id);

        //Assert
        assertEquals(comment1, actualResult);
        verify(commentRepository).findById(id);
    }

    @Test
    public void UpdateComment_UpdateComment_ReturnTrue() {
        //Arrange
        EnergyCanEntity energyCan1 = new EnergyCanEntity("Green", "Description", "Monster Green", 12);
        UserEntity user1 = new UserEntity("name", "username");
        ReviewEntity review1 = new ReviewEntity(12.0, user1, energyCan1);

        CommentEntity commentEntity = new CommentEntity("comment1", true, false, user1, energyCan1, review1);
        Long id = Long.valueOf(2);

        //Act
        when(commentRepository.findById(id)).thenReturn(Optional.of(commentEntity));
        when(commentRepository.save(commentEntity)).thenReturn(commentEntity);
        CommentEntity actualResult = commentCrud.UpdateComment(id, commentEntity);

        //Assert
        assertEquals(commentEntity, actualResult);
        verify(commentRepository).save(commentEntity);
    }

    @Test
    public void UpdateApproveComment_UpdateComment_ReturnTrue() {
        //Arrange
        EnergyCanEntity energyCan1 = new EnergyCanEntity("Green", "Description", "Monster Green", 12);
        UserEntity user1 = new UserEntity("name", "username");
        ReviewEntity review1 = new ReviewEntity(12.0, user1, energyCan1);

        CommentEntity commentEntity = new CommentEntity("comment1", true, false, user1, energyCan1, review1);
        Long id = Long.valueOf(2);

        //Act
        when(commentRepository.findById(id)).thenReturn(Optional.of(commentEntity));
        when(commentRepository.save(Mockito.any())).thenReturn(commentEntity);
        CommentEntity actualResult = commentCrud.UpdateApproveComment(id);

        //Assert
        assertEquals(commentEntity, actualResult);
        verify(commentRepository).save(commentEntity);
    }

    @Test
    public void DeleteComment_DeleteComment_ReturnTrue() {
        //Arrange
        EnergyCanEntity energyCan1 = new EnergyCanEntity("Green", "Description", "Monster Green", 12);
        UserEntity user1 = new UserEntity("name", "username");
        ReviewEntity review1 = new ReviewEntity(12.0, user1, energyCan1);

        CommentEntity commentEntity = new CommentEntity("comment1", true, false, user1, energyCan1, review1);
        Long id = Long.valueOf(2);

        //Act
        when(commentRepository.findById(id)).thenReturn((Optional.of(commentEntity)));
        commentCrud.DeleteComment(id);

        //Assert
        verify(commentRepository).deleteById(id);
    }

    @Test
    public void findByCan_findByEnergyCanEntityAndApproved_ReturnTrue() {
        //Arrange
        Long id = Long.valueOf(2);
        EnergyCanEntity energyCan1 = new EnergyCanEntity("Green", "Description", "Monster Green", 12);
        UserEntity user1 = new UserEntity("name", "username");
        ReviewEntity review1 = new ReviewEntity(12.0, user1, energyCan1);

        CommentEntity commentEntity = new CommentEntity("comment1", true, false, user1, energyCan1, review1);
        Optional<EnergyCanEntity> energyCanEntity1 = Optional.of(energyCan1);

        //Act
        when(energyCanRepository.findById(id)).thenReturn(energyCanEntity1);
        when(commentRepository.findByEnergyCanEntityAndApproved(energyCanEntity1, true)).thenReturn(List.of(commentEntity));
        List<CommentEntity> actualResult = commentCrud.findByCan(id, true);

        //Assert
        assertEquals(List.of(commentEntity), actualResult);
        verify(commentRepository).findByEnergyCanEntityAndApproved(energyCanEntity1, true);
    }

    @Test
    public void findByCanAndUser_findByEnergyCanEntityAndUserEntity_ReturnTrue() {
        //Arrange
        Long id = Long.valueOf(2);
        EnergyCanEntity energyCan1 = new EnergyCanEntity("Green", "Description", "Monster Green", 12);
        UserEntity user1 = new UserEntity("name", "username");
        ReviewEntity review1 = new ReviewEntity(12.0, user1, energyCan1);

        CommentEntity commentEntity = new CommentEntity("comment1", true, false, user1, energyCan1, review1);
        Optional<EnergyCanEntity> energyCan = Optional.of(energyCan1);
        Optional<UserEntity> userEntity = Optional.of(user1);

        //Act
        when(energyCanRepository.findById(id)).thenReturn(energyCan);
        when(userRepository.findById(id)).thenReturn(userEntity);
        when(commentRepository.findByEnergyCanEntityAndUserEntity(energyCan, userEntity)).thenReturn(commentEntity);
        CommentEntity actualResult = commentCrud.findByCanAndUser(id, id);

        //Assert
        assertEquals(commentEntity, actualResult);
        verify(commentRepository).findByEnergyCanEntityAndUserEntity(energyCan, userEntity);
    }

    @Test
    public void findByApprove_findByApproved_ReturnTrue() {
        //Arrange
        Long id = Long.valueOf(2);
        EnergyCanEntity energyCan1 = new EnergyCanEntity("Green", "Description", "Monster Green", 12);
        UserEntity user1 = new UserEntity("name", "username");
        ReviewEntity review1 = new ReviewEntity(12.0, user1, energyCan1);

        CommentEntity commentEntity = new CommentEntity("comment1", true, false, user1, energyCan1, review1);
        Optional<CommentEntity> comment1 = Optional.of(commentEntity);

        //Act
        when(commentRepository.findByApproved(true)).thenReturn(List.of(commentEntity));
        List<CommentEntity> actualResult = commentCrud.findByApprove(true);

        //Assert
        assertEquals(List.of(commentEntity), actualResult);
        verify(commentRepository).findByApproved(true);
    }
}