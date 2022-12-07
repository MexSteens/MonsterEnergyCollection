package fontys.s3.MonsterEnergyCollection.Business.Impl;

import fontys.s3.MonsterEnergyCollection.Business.Entity.EnergyCanEntity;
import fontys.s3.MonsterEnergyCollection.Business.Entity.RoleEnum;
import fontys.s3.MonsterEnergyCollection.Business.Entity.UserEntity;
import fontys.s3.MonsterEnergyCollection.Business.Exception.UnauthorizedDataAccessException;
import fontys.s3.MonsterEnergyCollection.Business.ReviewCrud;
import fontys.s3.MonsterEnergyCollection.Business.Entity.ReviewEntity;
import fontys.s3.MonsterEnergyCollection.Data.EnergyCanRepository;
import fontys.s3.MonsterEnergyCollection.Data.ReviewRepository;
import fontys.s3.MonsterEnergyCollection.Data.UserRepository;
import fontys.s3.MonsterEnergyCollection.Domain.AccessToken;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReviewCrudImpl implements ReviewCrud {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final EnergyCanRepository energyCanRepository;

    private AccessToken requestAccessToken;

    public ReviewCrudImpl(ReviewRepository reviewRepository, UserRepository userRepository, EnergyCanRepository energyCanRepository, AccessToken accessToken){
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.energyCanRepository = energyCanRepository;
        this.requestAccessToken = accessToken;
    }

    public List<ReviewEntity> GetAllReviews() {
        List<ReviewEntity> allCans = reviewRepository.findAll();
        return allCans;
    }

    public Optional<ReviewEntity> GetReviewById(Long id){
        Optional<ReviewEntity> reviewEntity = reviewRepository.findById(id);
        return reviewEntity;
    }

    public ReviewEntity UpdateReview(Long id, ReviewEntity reviewEntity){
        if (!requestAccessToken.hasRole(RoleEnum.ADMIN.name())) {
            if (!requestAccessToken.hasRole(RoleEnum.MODERATOR.name())) {
                if (!Objects.equals(requestAccessToken.getUserId(), reviewEntity.getUserEntity().getId())) {
                    throw new
                            UnauthorizedDataAccessException("USER_ID_NOT_FROM_LOGGED_IN_USER");
                }
            }
        }

        return reviewRepository.findById(id)
                .map(review -> {
                    review.setRating(reviewEntity.getRating());
                    review.setUserEntity(reviewEntity.getUserEntity());
                    review.setEnergyCanEntity(reviewEntity.getEnergyCanEntity());
                    return reviewRepository.save(review);
                })
                .orElseGet(() -> {
                    return null;
                });
    }



    public ReviewEntity CreateReview(ReviewEntity reviewEntity){
        EnergyCanEntity energyCanEntity = energyCanRepository.findById(reviewEntity.getEnergyCanEntity().getId()).orElse(null);
        if (energyCanEntity != null) {
            Double runningRating = energyCanEntity.getRunningRating();
            Integer timesRated = energyCanEntity.getTimesRated();
            Double finalRunningRating = ((runningRating * timesRated) + reviewEntity.getRating()) / (timesRated + 1);
            energyCanEntity.setTimesRated(timesRated + 1);
            energyCanEntity.setRunningRating(finalRunningRating);
            energyCanRepository.save(energyCanEntity);
        }
        return reviewRepository.save(reviewEntity);
    }

    public void DeleteReview(Long id) {
        ReviewEntity reviewEntity = reviewRepository.findById(id).orElse(null);
        if (!requestAccessToken.hasRole(RoleEnum.ADMIN.name())) {
            if (!requestAccessToken.hasRole(RoleEnum.MODERATOR.name())) {
                if (reviewEntity == null || !Objects.equals(requestAccessToken.getUserId(), reviewEntity.getUserEntity().getId())) {
                    throw new
                            UnauthorizedDataAccessException("USER_ID_NOT_FROM_LOGGED_IN_USER");
                }
            }
        }
        reviewRepository.deleteById(id);
    }

    public ReviewEntity findByCanAndUser(Long energyCanId, Long userId){
        Optional<EnergyCanEntity> energyCanEntity = energyCanRepository.findById(energyCanId);
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        return reviewRepository.findByEnergyCanEntityAndUserEntity(energyCanEntity, userEntity);
    }
}
