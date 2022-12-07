package fontys.s3.MonsterEnergyCollection.Business;

import fontys.s3.MonsterEnergyCollection.Business.Entity.CommentEntity;
import fontys.s3.MonsterEnergyCollection.Business.Entity.EnergyCanEntity;
import fontys.s3.MonsterEnergyCollection.Business.Entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface CommentCrud {
    List<CommentEntity> GetAllComments();

    Optional<CommentEntity> GetCommentById(Long id);

    CommentEntity UpdateComment(Long id, CommentEntity commentEntity);

    CommentEntity UpdateApproveComment(Long id);

    CommentEntity CreateComment(CommentEntity commentEntity);

    void DeleteComment(Long id);

    List<CommentEntity> findByCan(Long energyCanId, Boolean approved);

    List<CommentEntity> findByApprove(Boolean approved);
    CommentEntity findByCanAndUser(Long energyCanId, Long userId);
}
