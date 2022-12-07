package fontys.s3.MonsterEnergyCollection.Business;

import fontys.s3.MonsterEnergyCollection.Business.Entity.RoleEntity;
import fontys.s3.MonsterEnergyCollection.Business.Entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserCrud {
    List<UserEntity> GetAllUsers();

    Optional<UserEntity> GetUserById(Long id);

    Optional<UserEntity> GetUserByUsername(String username);

    UserEntity UpdateUser(Long id, UserEntity userEntity);

    UserEntity UpdateUserRole(Long id, String role);

    UserEntity CreateUser(UserEntity userEntity);

    void DeleteUser(Long id);
}
