package fontys.s3.MonsterEnergyCollection.DataTransferObject;

import fontys.s3.MonsterEnergyCollection.Business.Entity.EnergyCanEntity;
import fontys.s3.MonsterEnergyCollection.Business.Entity.ReviewEntity;
import fontys.s3.MonsterEnergyCollection.Business.Entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {
    private String comment;
    private Boolean approved;
    private Boolean deleted;
    private UserEntity userEntity;
    private EnergyCanEntity energyCanEntity;
    private ReviewEntity reviewEntity;
}
