package fontys.s3.MonsterEnergyCollection.Business.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "MonsterUser")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private Long id;

    @NotNull
    @Column(name = "name")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String name;

    @Column(name = "username")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String username;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private Set<RoleEntity> roles;

    @Column(name = "created")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Timestamp created;

    public UserEntity(Long id, String name, String username, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
    }

//    public UserEntity() {
//    }

    public String getName(){
        return this.name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Collection<RoleEntity> getRoles() {
        return roles;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserEntity(String name, String username){
        this.name = name;
        this.username = username;
    }

    public UserEntity(String name, String username, String password){
        this.name = name;
        this.username = username;
        this.password = password;
    }
}
