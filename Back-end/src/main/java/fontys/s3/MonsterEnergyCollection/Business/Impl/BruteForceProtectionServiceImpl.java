package fontys.s3.MonsterEnergyCollection.Business.Impl;


import fontys.s3.MonsterEnergyCollection.Business.BruteForceProtectionService;
import fontys.s3.MonsterEnergyCollection.Business.Entity.LoginAttemptEntity;
import fontys.s3.MonsterEnergyCollection.Data.LoginAttemptRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static java.util.Optional.empty;

@Service
public class BruteForceProtectionServiceImpl implements BruteForceProtectionService {

    private final LoginAttemptRepository loginAttemptRepository;

    public BruteForceProtectionServiceImpl(LoginAttemptRepository loginAttemptRepository) {
        this.loginAttemptRepository = loginAttemptRepository;
    }

    private final Integer MAX_ATTEMPT = 10;

    public void LoginSucceeded(String ipAddress) {
        if (loginAttemptRepository.findById(ipAddress).orElse(null) != null){
            loginAttemptRepository.deleteById(ipAddress);
        }
    }

    public Boolean LoginFailed(String ipAddress) {
        Optional<LoginAttemptEntity> test = Optional.empty();
        LoginAttemptEntity loginAttemptEntity = loginAttemptRepository.findById(ipAddress).orElse(loginAttemptRepository.save(new LoginAttemptEntity(ipAddress, 0, false, null)));
        if (loginAttemptEntity.getAttempt() + 1 != MAX_ATTEMPT) {
            if (loginAttemptRepository.findById(ipAddress) == test){
                loginAttemptEntity.setAttempt(1);
                loginAttemptRepository.save(loginAttemptEntity);
                return true;
            }
            else {
                LoginAttemptEntity loginAttemptEntity4 = loginAttemptRepository.findById(ipAddress)
                    .map(loginAttemptEntity1 -> {
                        loginAttemptEntity1.setIpAddress(loginAttemptEntity.getIpAddress());
                        loginAttemptEntity1.setBlocked(false);
                        loginAttemptEntity1.setBlockedAt(null);
                        loginAttemptEntity1.setAttempt(loginAttemptEntity.getAttempt() + 1);
                        return loginAttemptRepository.save(loginAttemptEntity1);
                    }).orElseGet(() -> {return null;});
                return true;
            }
        }
        else {
            LoginAttemptEntity loginAttemptEntity2 = loginAttemptRepository.findById(ipAddress)
                    .map(loginAttemptEntity1 -> {
                        loginAttemptEntity1.setIpAddress(loginAttemptEntity.getIpAddress());
                        loginAttemptEntity1.setBlocked(true);
                        loginAttemptEntity1.setBlockedAt(new Timestamp(System.currentTimeMillis()));
                        loginAttemptEntity1.setAttempt(loginAttemptEntity.getAttempt() + 1);
                        return loginAttemptRepository.save(loginAttemptEntity1);
                    }).orElseGet(() -> {return null;});
            return false;
        }
    }
}
