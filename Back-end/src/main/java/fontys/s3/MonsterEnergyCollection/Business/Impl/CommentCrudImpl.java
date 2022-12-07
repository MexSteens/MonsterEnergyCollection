package fontys.s3.MonsterEnergyCollection.Business.Impl;

import fontys.s3.MonsterEnergyCollection.Business.CommentCrud;
import fontys.s3.MonsterEnergyCollection.Business.Entity.CommentEntity;
import fontys.s3.MonsterEnergyCollection.Business.Entity.EnergyCanEntity;
import fontys.s3.MonsterEnergyCollection.Business.Entity.RoleEnum;
import fontys.s3.MonsterEnergyCollection.Business.Entity.UserEntity;
import fontys.s3.MonsterEnergyCollection.Business.Exception.UnauthorizedDataAccessException;
import fontys.s3.MonsterEnergyCollection.Data.CommentRepository;
import fontys.s3.MonsterEnergyCollection.Data.EnergyCanRepository;
import fontys.s3.MonsterEnergyCollection.Data.UserRepository;
import fontys.s3.MonsterEnergyCollection.Domain.AccessToken;
import fontys.s3.MonsterEnergyCollection.WebSocket.GreetingController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CommentCrudImpl implements CommentCrud {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EnergyCanRepository energyCanRepository;

    private AccessToken requestAccessToken;

    public CommentCrudImpl(CommentRepository commentRepository, UserRepository userRepository, EnergyCanRepository energyCanRepository, AccessToken accessToken){
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.energyCanRepository = energyCanRepository;
        this.requestAccessToken = accessToken;
    }

    public List<CommentEntity> GetAllComments() {
        List<CommentEntity> allCans = commentRepository.findAll();
        return allCans;
    }

    public Optional<CommentEntity> GetCommentById(Long id){
        Optional<CommentEntity> commentEntity = commentRepository.findById(id);
        return commentEntity;
    }


    public CommentEntity UpdateComment(Long id, CommentEntity commentEntity){
        if (!requestAccessToken.hasRole(RoleEnum.ADMIN.name())) {
            if (!requestAccessToken.hasRole(RoleEnum.MODERATOR.name())) {
                if (!Objects.equals(requestAccessToken.getUserId(), commentEntity.getUserEntity().getId())) {
                    throw new
                            UnauthorizedDataAccessException("USER_ID_NOT_FROM_LOGGED_IN_USER");
                }
            }
        }
        return commentRepository.findById(id)
                .map(comment -> {
                    comment.setComment(commentEntity.getComment());
                    comment.setApproved(false);
                    comment.setDeleted(false);
                    comment.setUserEntity(commentEntity.getUserEntity());
                    comment.setEnergyCanEntity(commentEntity.getEnergyCanEntity());
                    comment.setReviewEntity(commentEntity.getReviewEntity());
                    return commentRepository.save(comment);
                })
                .orElseGet(() -> {
                    return null;
                });
    }

    public CommentEntity UpdateApproveComment(Long id){
        return commentRepository.findById(id)
                .map(comment -> {
                    comment.setComment(comment.getComment());
                    comment.setApproved(true);
                    comment.setDeleted(false);
                    comment.setUserEntity(comment.getUserEntity());
                    comment.setEnergyCanEntity(comment.getEnergyCanEntity());
                    comment.setReviewEntity(comment.getReviewEntity());
                    return commentRepository.save(comment);
                })
                .orElseGet(() -> {
                    return null;
                });
    }


    public CommentEntity CreateComment(CommentEntity commentEntity){
        return commentRepository.save(commentEntity);
    }

    public void DeleteComment(Long id) {
        CommentEntity commentEntity = commentRepository.findById(id).orElse(null);
        if (!requestAccessToken.hasRole(RoleEnum.ADMIN.name())) {
            if (!requestAccessToken.hasRole(RoleEnum.MODERATOR.name())) {
                if (commentEntity == null || !Objects.equals(requestAccessToken.getUserId(), commentEntity.getUserEntity().getId())) {
                    throw new
                            UnauthorizedDataAccessException("USER_ID_NOT_FROM_LOGGED_IN_USER");
                }
            }
        }
        commentRepository.deleteById(id);
    }

    public List<CommentEntity> findByCan(Long energyCanId, Boolean approved){
        Optional<EnergyCanEntity> energyCanEntity = energyCanRepository.findById(energyCanId);
        return commentRepository.findByEnergyCanEntityAndApproved(energyCanEntity, approved);
    }

    public List<CommentEntity> findByApprove(Boolean approved){
        return commentRepository.findByApproved(approved);
    }
    public CommentEntity findByCanAndUser(Long energyCanId, Long userId){
        Optional<EnergyCanEntity> energyCanEntity = energyCanRepository.findById(energyCanId);
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        return commentRepository.findByEnergyCanEntityAndUserEntity(energyCanEntity, userEntity);
    }

}
