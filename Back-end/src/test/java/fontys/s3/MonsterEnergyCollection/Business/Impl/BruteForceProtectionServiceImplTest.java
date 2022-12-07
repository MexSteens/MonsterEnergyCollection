package fontys.s3.MonsterEnergyCollection.Business.Impl;

import fontys.s3.MonsterEnergyCollection.Business.Entity.LoginAttemptEntity;
import fontys.s3.MonsterEnergyCollection.Data.LoginAttemptRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BruteForceProtectionServiceImplTest {

    @Mock
    private LoginAttemptRepository loginAttemptRepository;

    @InjectMocks
    private BruteForceProtectionServiceImpl bruteForceProtectionService;

    @BeforeEach
    void setUp()
    {
        this.bruteForceProtectionService = new BruteForceProtectionServiceImpl(this.loginAttemptRepository);
    }

    @Test
    void loginSucceeded() {
        //Arrange
        LoginAttemptEntity loginAttemptEntity = new LoginAttemptEntity("0.0.0.0", 0, false, null);

        //Act
        when(loginAttemptRepository.findById("0.0.0.0")).thenReturn(Optional.of(loginAttemptEntity));
        bruteForceProtectionService.LoginSucceeded("0.0.0.0");

        //Assert
        verify(loginAttemptRepository).deleteById("0.0.0.0");

    }

    @Test
    void loginFailed() {
        //Arrange
        LoginAttemptEntity loginAttemptEntity = new LoginAttemptEntity("0.0.0.0", 0, false, null);

        //Act
        when(loginAttemptRepository.findById("0.0.0.0")).thenReturn(Optional.of(loginAttemptEntity));
        when(loginAttemptRepository.save(Mockito.any())).thenReturn(loginAttemptEntity);
        boolean actualResult = bruteForceProtectionService.LoginFailed("0.0.0.0");

        //Assert
        assertEquals(true, actualResult);

    }

    @Test
    void loginFailed_save() {
        //Arrange
        LoginAttemptEntity loginAttemptEntity2 = new LoginAttemptEntity("0.0.0.0", 1, false, null);

        //Act
        when(loginAttemptRepository.findById("0.0.0.0")).thenReturn(Optional.empty());
        when(loginAttemptRepository.save(Mockito.any())).thenReturn(loginAttemptEntity2);
        boolean actualResult = bruteForceProtectionService.LoginFailed("0.0.0.0");

        //Assert
        assertEquals(true, actualResult);

    }

    @Test
    void loginFailed_block() {
        //Arrange
        LoginAttemptEntity loginAttemptEntity = new LoginAttemptEntity("0.0.0.0", 9, false, null);

        //Act
        when(loginAttemptRepository.findById("0.0.0.0")).thenReturn(Optional.of(loginAttemptEntity));
        when(loginAttemptRepository.save(Mockito.any())).thenReturn(loginAttemptEntity);
        boolean actualResult = bruteForceProtectionService.LoginFailed("0.0.0.0");

        //Assert
        assertEquals(false, actualResult);

    }
}