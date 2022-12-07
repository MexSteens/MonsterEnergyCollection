package fontys.s3.MonsterEnergyCollection.Presentation;

import fontys.s3.MonsterEnergyCollection.Business.ReviewCrud;
import fontys.s3.MonsterEnergyCollection.Business.Entity.ReviewEntity;
import fontys.s3.MonsterEnergyCollection.DataTransferObject.ReviewDTO;
import fontys.s3.MonsterEnergyCollection.Security.IsAuthenticated.IsAuthenticated;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/api")
public class ReviewController {
    private final ReviewCrud reviewCrud;

    public ReviewController(ReviewCrud reviewCrud){
        this.reviewCrud = reviewCrud;
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_USER", "ROLE_MODERATOR"})
    @GetMapping("/review")
    public ResponseEntity<List<ReviewEntity>> getAllReviews(@RequestParam(required = false)Long energyCanId, Long userId) {
        try {
            List<ReviewEntity> reviewEntities = new ArrayList<>();
            if (energyCanId == null && userId == null)
                reviewEntities = reviewCrud.GetAllReviews();
            else if (energyCanId != null && userId != null) {
                reviewEntities.add(reviewCrud.findByCanAndUser(energyCanId, userId));
            }

            if (reviewEntities.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(reviewEntities, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_USER", "ROLE_MODERATOR"})
    @GetMapping("/review/{id}")
    public ResponseEntity<ReviewEntity> getReviewById(@PathVariable("id") long id) {
        Optional<ReviewEntity> reviewData = reviewCrud.GetReviewById(id);
        if (reviewData.isPresent()) {
            return new ResponseEntity<>(reviewData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_USER"})
    @PostMapping("/review")
    public ResponseEntity<ReviewEntity> createReview(@RequestBody ReviewDTO reviewDTO) {
        try {
            ReviewEntity _reviewEntity = reviewCrud.CreateReview(new ReviewEntity(reviewDTO.getRating(), reviewDTO.getUserEntity(), reviewDTO.getEnergyCanEntity()));
            return new ResponseEntity<>(_reviewEntity, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_USER"})
    @PutMapping("/review/{id}")
    public ResponseEntity<ReviewEntity> updateReview(@PathVariable("id") long id, @RequestBody ReviewDTO reviewDTO) {
        return new ResponseEntity<>(reviewCrud.UpdateReview(id, new ReviewEntity(reviewDTO.getRating(), reviewDTO.getUserEntity(), reviewDTO.getEnergyCanEntity())), HttpStatus.OK);
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_USER", "ROLE_MODERATOR"})
    @DeleteMapping("/review/{id}")
    public ResponseEntity<HttpStatus> deleteReview(@PathVariable("id") long id) {
        try {
            reviewCrud.DeleteReview(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
