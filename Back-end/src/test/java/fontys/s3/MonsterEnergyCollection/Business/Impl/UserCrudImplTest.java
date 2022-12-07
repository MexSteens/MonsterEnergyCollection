package fontys.s3.MonsterEnergyCollection.Business.Impl;

import fontys.s3.MonsterEnergyCollection.Business.Entity.*;
import fontys.s3.MonsterEnergyCollection.Data.EnergyCanRepository;
import fontys.s3.MonsterEnergyCollection.Data.RoleRepository;
import fontys.s3.MonsterEnergyCollection.Data.UserRepository;
import fontys.s3.MonsterEnergyCollection.Domain.AccessToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class UserCrudImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepository roleRepository;

    AccessToken accessToken = new AccessToken("subject", List.of("ADMIN"), 2L);


    @InjectMocks
    private UserCrudImpl userCrud;

    @BeforeEach
    void setUp()
    {
        this.userCrud = new UserCrudImpl(this.userRepository, this.passwordEncoder, this.roleRepository, accessToken);
    }

    @Test
    public void GetAllCans_FindAll_ReturnTrue(){
        //Arrange
        List<UserEntity> expected = new ArrayList<>();
        UserEntity userEntity1 = new UserEntity("name", "username");
        UserEntity userEntity2 = new UserEntity("name2", "username2");
        expected.add(userEntity1);
        expected.add(userEntity2);

        //Act
        when(userRepository.findAll()).thenReturn(List.of(userEntity1, userEntity2));
        List<UserEntity> actualResult = userCrud.GetAllUsers();

        //Assert
        assertEquals(expected, actualResult);
        verify(userRepository).findAll();
    }

    @Test
    public void CreateUser_PostUser_ReturnTrue() {
        //Arrange
        UserEntity newUser = UserEntity.builder()
                .username("username")
                .name("name")
                .password("password")
                .build();

        UserEntity user1 = new UserEntity("name", "username");

        //Act
        when(passwordEncoder.encode(Mockito.any())).thenReturn("password");
        UserEntity actualResult = userCrud.CreateUser(newUser);

        //Assert
        assertEquals(user1.getName(), actualResult.getName());
        verify(userRepository).save(Mockito.any());
    }

    @Test
    public void GetUserById_GetById_ReturnTrue() {
        //Arrange
        Long id = Long.valueOf(2);
        UserEntity userEntity = new UserEntity("name", "username");
        Optional<UserEntity> user1 = Optional.of(userEntity);

        //Act
        when(userRepository.findById(id)).thenReturn(user1);
        Optional<UserEntity> actualResult = userCrud.GetUserById(id);

        //Assert
        assertEquals(user1, actualResult);
        verify(userRepository).findById(id);
    }

    @Test
    public void UpdateUser_UpdateUser_ReturnTrue() {
        //Arrange
        UserEntity userEntity = new UserEntity("name", "username");
        Long id = Long.valueOf(2);

        //Act
        when(userRepository.findById(id)).thenReturn(Optional.of(userEntity));
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        UserEntity actualResult = userCrud.UpdateUser(id, userEntity);

        //Assert
        assertEquals(userEntity, actualResult);
        verify(userRepository).save(userEntity);
    }

    @Test
    public void DeleteUser_DeleteUser_ReturnTrue() {
        //Arrange
        Long id = Long.valueOf(2);

        //Act
        userCrud.DeleteUser(id);

        //Assert
        verify(userRepository).deleteById(id);
    }

    @Test
    void getUserByUsername() {
        //Arrange
        UserEntity user1 = new UserEntity("name", "username");

        //Act
        when(userRepository.findByUsername("some name")).thenReturn(Optional.of(user1));
        Optional<UserEntity> actualResult = userCrud.GetUserByUsername("some name");

        //Assert
        assertEquals(Optional.of(user1), actualResult);
        verify(userRepository).findByUsername("some name");
    }

    @Test
    void updateUserRole() {
        //Arrange
        Long id = Long.valueOf(2);
        String roleEnum = "USER";
        UserEntity newUser = UserEntity.builder()
                .username("username")
                .name("name")
                .password("password")
                .build();

        newUser.setRoles(Set.of(
                RoleEntity.builder()
                        .id(id)
                        .userEntity(newUser)
                        .role(RoleEnum.USER)
                        .build()));

        RoleEntity roleEntity = new RoleEntity(id, RoleEnum.USER, newUser);

        //Act
        when(userRepository.findById(id)).thenReturn(Optional.of(newUser));
        when(roleRepository.save(Mockito.any())).thenReturn(roleEntity);
        UserEntity actualResult = userCrud.UpdateUserRole(id, roleEnum);

        //Assert
        assertEquals(newUser, actualResult);
        verify(roleRepository).save(Mockito.any());
        verify(userRepository).findById(id);
    }
}