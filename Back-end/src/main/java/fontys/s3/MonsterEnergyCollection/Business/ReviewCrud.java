package fontys.s3.MonsterEnergyCollection.Business;

import fontys.s3.MonsterEnergyCollection.Business.Entity.ReviewEntity;

import java.util.List;
import java.util.Optional;

public interface ReviewCrud {
    List<ReviewEntity> GetAllReviews();

    Optional<ReviewEntity> GetReviewById(Long id);

    ReviewEntity UpdateReview(Long id, ReviewEntity reviewEntity);

    ReviewEntity CreateReview(ReviewEntity reviewEntity);

    void DeleteReview(Long id);

    ReviewEntity findByCanAndUser(Long energyCanId, Long userId);
}
