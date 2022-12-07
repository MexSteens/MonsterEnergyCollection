package fontys.s3.MonsterEnergyCollection.Business.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "LoginAttempt")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginAttemptEntity {
    @Id
    @NotNull
    @Column(name = "ipAddress")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String ipAddress;

    @Column(name = "attempt")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private Integer attempt;

    @Column(name = "blocked")
    @JsonFormat(shape = JsonFormat.Shape.BOOLEAN)
    private Boolean blocked;

    @Column(name = "blockedAt")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Timestamp blockedAt;
}
