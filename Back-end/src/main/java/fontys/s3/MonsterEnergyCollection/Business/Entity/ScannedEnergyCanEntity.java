package fontys.s3.MonsterEnergyCollection.Business.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "ScannedEnergyCan")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScannedEnergyCanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private Long id;

    @ManyToOne
    @JoinColumn(name="userId")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name="energyCanId")
    private EnergyCanEntity energyCanEntity;

    @Column(name = "created")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Timestamp created;

//    public ScannedEnergyCanEntity(){};

    public EnergyCanEntity getEnergyCanEntity() {
        return energyCanEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setEnergyCanEntity(EnergyCanEntity energyCanEntity) {
        this.energyCanEntity = energyCanEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public ScannedEnergyCanEntity(EnergyCanEntity energyCanEntity, UserEntity userEntity){
        this.energyCanEntity = energyCanEntity;
        this.userEntity = userEntity;
    }
}

