package fontys.s3.MonsterEnergyCollection.Business.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "UserRole")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private Long id;

    @NotNull
    @Column(name = "roleName")
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity userEntity;

//    public RoleEntity(){};

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }
}
