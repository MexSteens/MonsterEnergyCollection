package fontys.s3.MonsterEnergyCollection.Business.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Comments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private Long id;

    @Column(name = "description")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String comment;

    @Column(name = "created")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Timestamp created;

    @Column(name = "modified")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Timestamp modified;

    @Column(name = "approved")
    @JsonFormat(shape = JsonFormat.Shape.BOOLEAN)
    private Boolean approved;

    @Column(name = "deleted")
    @JsonFormat(shape = JsonFormat.Shape.BOOLEAN)
    private Boolean deleted;

    @ManyToOne
    @JoinColumn(name="userId")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name="energyCanId")
    private EnergyCanEntity energyCanEntity;

    @OneToOne
    @JoinColumn(name = "reviewId")
    private ReviewEntity reviewEntity;

//    public CommentEntity() {}

    public String getComment() { return comment; }

    public Boolean getApproved(){
        return this.approved;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public UserEntity getUserEntity() { return this.userEntity; }

    public EnergyCanEntity getEnergyCanEntity() {
        return energyCanEntity;
    }

    public ReviewEntity getReviewEntity() {
        return reviewEntity;
    }

    public void setComment(String comment) { this.comment = comment; }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public void setEnergyCanEntity(EnergyCanEntity energyCanEntity) {
        this.energyCanEntity = energyCanEntity;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public void setReviewEntity(ReviewEntity reviewEntity) {
        this.reviewEntity = reviewEntity;
    }

    public CommentEntity(String comment, Boolean approved, Boolean deleted, UserEntity userEntity, EnergyCanEntity energyCanEntity, ReviewEntity reviewEntity){
        this.comment = comment;
        this.approved = approved;
        this.deleted = deleted;
        this.userEntity = userEntity;
        this.energyCanEntity = energyCanEntity;
        this.reviewEntity = reviewEntity;
    }
}
