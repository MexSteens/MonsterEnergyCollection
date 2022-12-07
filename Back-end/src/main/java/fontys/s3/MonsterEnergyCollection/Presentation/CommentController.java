package fontys.s3.MonsterEnergyCollection.Presentation;

import fontys.s3.MonsterEnergyCollection.Business.CommentCrud;
import fontys.s3.MonsterEnergyCollection.Business.Entity.CommentEntity;
import fontys.s3.MonsterEnergyCollection.Business.Entity.EnergyCanEntity;
import fontys.s3.MonsterEnergyCollection.Business.Entity.UserEntity;
import fontys.s3.MonsterEnergyCollection.DataTransferObject.CommentDTO;
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
public class CommentController {
    private final CommentCrud commentCrud;

    public CommentController(CommentCrud commentCrud){
        this.commentCrud = commentCrud;
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_USER", "ROLE_MODERATOR"})
    @GetMapping("/comment")
    public ResponseEntity<List<CommentEntity>> getAllComments(@RequestParam(required = false)Long energyCanId, Long userId, Boolean approved) {
        try {
            List<CommentEntity> commentEntities = new ArrayList<CommentEntity>();
            if (energyCanId == null && userId == null && approved == null)
                commentEntities = commentCrud.GetAllComments();
            else if (energyCanId == null && userId == null && approved == false) {
                commentEntities = commentCrud.findByApprove(approved);
            }
            else if (energyCanId != null && userId == null && approved == true) {
                commentEntities = commentCrud.findByCan(energyCanId, approved);
            } else if (energyCanId != null && userId != null) {
                commentEntities.add(commentCrud.findByCanAndUser(energyCanId, userId));
            }

            if (commentEntities.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(commentEntities, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_USER", "ROLE_MODERATOR"})
    @GetMapping("/comment/{id}")
    public ResponseEntity<CommentEntity> getCommentById(@PathVariable("id") long id) {
        Optional<CommentEntity> commentData = commentCrud.GetCommentById(id);
        if (commentData.isPresent()) {
            return new ResponseEntity<>(commentData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_USER"})
    @PostMapping("/comment")
    public ResponseEntity<CommentEntity> createComment(@RequestBody CommentDTO commentDTO) {
        try {
            CommentEntity _commentEntity = commentCrud.CreateComment(new CommentEntity(commentDTO.getComment(), commentDTO.getApproved(), commentDTO.getDeleted(), commentDTO.getUserEntity(), commentDTO.getEnergyCanEntity(), commentDTO.getReviewEntity()));
            return new ResponseEntity<>(_commentEntity, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_USER"})
    @PutMapping("/comment/{id}")
    public ResponseEntity<CommentEntity> updateComment(@PathVariable("id") long id, @RequestBody CommentDTO commentDTO) {
        return new ResponseEntity<>(commentCrud.UpdateComment(id, new CommentEntity(commentDTO.getComment(), commentDTO.getApproved(), commentDTO.getDeleted(), commentDTO.getUserEntity(), commentDTO.getEnergyCanEntity(), commentDTO.getReviewEntity())), HttpStatus.OK);
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_MODERATOR"})
    @PutMapping("/comment/approve/{id}")
    public ResponseEntity<CommentEntity> updateApproveComment(@PathVariable("id") long id) {
        return new ResponseEntity<>(commentCrud.UpdateApproveComment(id), HttpStatus.OK);
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_USER", "ROLE_MODERATOR"})
    @DeleteMapping("/comment/{id}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("id") long id) {
        try {
            commentCrud.DeleteComment(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
