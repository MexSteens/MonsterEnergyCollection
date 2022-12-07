package fontys.s3.MonsterEnergyCollection.Business.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Review")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private Long id;

    @Column(name = "rating")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT)
    private Double rating;

    @Column(name = "rated")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Timestamp rated;

    @ManyToOne
    @JoinColumn(name="userId")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name="energyCanId")
    private EnergyCanEntity energyCanEntity;

//    public ReviewEntity() {
//
//    }

    public Long getId() {
        return id;
    }

    public Double getRating(){
        return this.rating;
    }

    public UserEntity getUserEntity() { return this.userEntity; }

    public EnergyCanEntity getEnergyCanEntity() {
        return energyCanEntity;
    }

    public void setRating(Double newRating) {
        this.rating = newRating;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public void setEnergyCanEntity(EnergyCanEntity energyCanEntity) {
        this.energyCanEntity = energyCanEntity;
    }

    public ReviewEntity(Double rating, UserEntity userEntity, EnergyCanEntity energyCanEntity){
        this.rating = rating;
        this.userEntity = userEntity;
        this.energyCanEntity = energyCanEntity;
    }
}
