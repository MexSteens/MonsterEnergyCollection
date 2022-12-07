package fontys.s3.MonsterEnergyCollection.Business.Impl;

import fontys.s3.MonsterEnergyCollection.Business.Entity.RoleEntity;
import fontys.s3.MonsterEnergyCollection.Business.Entity.RoleEnum;
import fontys.s3.MonsterEnergyCollection.Business.Entity.UserEntity;
import fontys.s3.MonsterEnergyCollection.Business.Exception.UnauthorizedDataAccessException;
import fontys.s3.MonsterEnergyCollection.Business.UserCrud;
import fontys.s3.MonsterEnergyCollection.Data.RoleRepository;
import fontys.s3.MonsterEnergyCollection.Data.UserRepository;
import fontys.s3.MonsterEnergyCollection.Domain.AccessToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class UserCrudImpl implements UserCrud {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private AccessToken requestAccessToken;


    public UserCrudImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, AccessToken accessToken){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.requestAccessToken = accessToken;
    }

    public List<UserEntity> GetAllUsers() {
        List<UserEntity> allCans = userRepository.findAll();
        return allCans;
    }

    public Optional<UserEntity> GetUserById(Long id){
        if (!requestAccessToken.hasRole(RoleEnum.ADMIN.name())) {
            if (!requestAccessToken.hasRole(RoleEnum.MODERATOR.name())) {
                if (!Objects.equals(requestAccessToken.getUserId(), id)) {
                    throw new
                            UnauthorizedDataAccessException("USER_ID_NOT_FROM_LOGGED_IN_USER");
                }
            }
        }
        Optional<UserEntity> userEntity = userRepository.findById(id);
        return userEntity;
    }

    public Optional<UserEntity> GetUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public UserEntity UpdateUser(Long id, UserEntity userEntity){

        UserEntity userEntity1 = userRepository.findById(id)
                .map(user -> {
                    user.setName(userEntity.getName());
                    return userRepository.save(user);
                })
                .orElseGet(() -> {
                    return null;
                });
        userEntity1.setPassword(null);
        return userEntity1;
    }

    @Transactional
    @Override
    public UserEntity UpdateUserRole(Long id, String roleEnum){
        RoleEnum role = RoleEnum.USER;
        if (roleEnum.equals("\"MODERATOR\"")) {
            role = RoleEnum.MODERATOR;
        } else if (roleEnum.equals("\"ADMIN\"")) {
            role = RoleEnum.ADMIN;
        }

        RoleEnum finalRole = role;

        UserEntity foundUser = userRepository.findById(id)
                .orElseGet(() -> {
                    return null;
                });

        RoleEntity userRole = foundUser.getRoles().stream().findFirst().orElse(null);
        if (userRole != null) {
            roleRepository.save(RoleEntity.builder()
                    .id(userRole.getId())
                    .userEntity(foundUser)
                    .role(finalRole)
                    .build());
        }

        foundUser.setPassword(null);
        return foundUser;
    }


    @Transactional
    @Override
    public UserEntity CreateUser(UserEntity userEntity){
        String encodedPassword = passwordEncoder.encode(userEntity.getPassword());
        UserEntity newUser = UserEntity.builder()
                .username(userEntity.getUsername())
                .name(userEntity.getName())
                .password(encodedPassword)
                .build();

        newUser.setRoles(Set.of(
                RoleEntity.builder()
                        .userEntity(newUser)
                        .role(RoleEnum.USER)
                        .build()));

        UserEntity returnUser = UserEntity.builder()
                .name(userEntity.getName())
                .username(userEntity.getUsername())
                .build();

        userRepository.save(newUser);
        return returnUser;
    }

    public void DeleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
