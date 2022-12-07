package fontys.s3.MonsterEnergyCollection.Business.Impl;

import fontys.s3.MonsterEnergyCollection.Business.AccessToken;
import fontys.s3.MonsterEnergyCollection.Business.BruteForceProtectionService;
import fontys.s3.MonsterEnergyCollection.Business.Entity.*;
import fontys.s3.MonsterEnergyCollection.Business.Exception.InvalidCredentialsException;
import fontys.s3.MonsterEnergyCollection.Data.EnergyCanRepository;
import fontys.s3.MonsterEnergyCollection.Data.LoginAttemptRepository;
import fontys.s3.MonsterEnergyCollection.Data.UserRepository;
import fontys.s3.MonsterEnergyCollection.Domain.LoginRequest;
import fontys.s3.MonsterEnergyCollection.Domain.LoginResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class LoginImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AccessToken accessToken;

    @Mock
    private HttpServletRequest request;

    @Mock
    private LoginAttemptRepository loginAttemptRepository;

    @Mock
    private BruteForceProtectionService bruteForceProtectionService;

    @InjectMocks
    private LoginImpl login;

    @BeforeEach
    void setUp()
    {
        this.login = new LoginImpl(this.userRepository, this.passwordEncoder, this.accessToken, this.bruteForceProtectionService, this.request, this.loginAttemptRepository);
    }

    @Test
    public void Login_Login_ReturnTrue(){
        //Arrange
        Optional<UserEntity> user = Optional.of(new UserEntity("name", "username", "password"));
        UserEntity newUser = UserEntity.builder()
                .username("username")
                .name("name")
                .password("password")
                .build();

        newUser.setRoles(Set.of(
                RoleEntity.builder()
                        .id(Long.valueOf(1))
                        .userEntity(newUser)
                        .role(RoleEnum.USER)
                        .build()));

        LoginAttemptEntity loginAttemptEntity = new LoginAttemptEntity("0.0.0.0", 0, false, null);

        //Act
        when(request.getHeader("X-Forwarded-For")).thenReturn(null);
        when(request.getRemoteAddr()).thenReturn("0.0.0.0");
        when(loginAttemptRepository.findById("0.0.0.0")).thenReturn(Optional.of(loginAttemptEntity));
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(newUser));
        when(passwordEncoder.matches(Mockito.any(), Mockito.any())).thenReturn(true);
        when(accessToken.Encode(Mockito.any())).thenReturn("accestoken");
        LoginResponse actualResult = login.login(new LoginRequest("username", "password"));

        //Assert
        assertEquals(user.get().getId(), actualResult.getUserId());
        verify(userRepository).findByUsername("username");
    }

    @Test
    public void Login_MatchesPassword_returnException() {
        //Arrange
        Optional<UserEntity> user = Optional.of(new UserEntity("name", "username", "password"));
        Optional<UserEntity> user2 = Optional.of(new UserEntity("name", "username", "passwordWrong"));
        LoginRequest loginRequest = new LoginRequest("username", "password");


        //Act
        when(userRepository.findByUsername("username")).thenReturn(user2);
        when(passwordEncoder.matches(Mockito.any(), Mockito.any())).thenReturn(false);

//        try{
//            login.login(new LoginRequest("username", "password"));
//        } catch (InvalidCredentialsException e){
//            System.out.println("HERE");
////            assertEquals(InvalidCredentialsException.class, e.getClass());
//           // e.printStackTrace();
//        }
        //Assert
        //assertThrows(InvalidCredentialsException.class, (Executable) login.login(new LoginRequest("username", "password")));
        assertThrows(InvalidCredentialsException.class, () -> {
            login.login(loginRequest);
        });


    }
}