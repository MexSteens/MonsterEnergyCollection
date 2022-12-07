package fontys.s3.MonsterEnergyCollection.Business.Impl;

import fontys.s3.MonsterEnergyCollection.Business.AccessToken;
import fontys.s3.MonsterEnergyCollection.Business.BruteForceProtectionService;
import fontys.s3.MonsterEnergyCollection.Business.Entity.LoginAttemptEntity;
import fontys.s3.MonsterEnergyCollection.Business.Entity.UserEntity;
import fontys.s3.MonsterEnergyCollection.Business.Exception.BruteForceException;
import fontys.s3.MonsterEnergyCollection.Business.Exception.InvalidCredentialsException;
import fontys.s3.MonsterEnergyCollection.Business.Login;
import fontys.s3.MonsterEnergyCollection.Data.LoginAttemptRepository;
import fontys.s3.MonsterEnergyCollection.Data.UserRepository;
import fontys.s3.MonsterEnergyCollection.Domain.LoginRequest;
import fontys.s3.MonsterEnergyCollection.Domain.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.temporal.Temporal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginImpl implements Login {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessToken accessTokenEncoder;

    private final BruteForceProtectionService bruteForceProtectionService;

    private final HttpServletRequest request;

    private final LoginAttemptRepository loginAttemptRepository;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Optional<UserEntity> user = userRepository.findByUsername(loginRequest.getUsername());
        UserEntity user2 = new UserEntity();
        if (user.isEmpty()) {
            throw new InvalidCredentialsException();
        }
        else {
            user2 = user.orElse(null);
        }

        final String xfHeader = request.getHeader("X-Forwarded-For");
        String ipAddress;
        if (xfHeader == null) {
            ipAddress = request.getRemoteAddr();
        } else {
            ipAddress = xfHeader.split(",")[0];
        }

        LoginAttemptEntity getBlocked = loginAttemptRepository.findById(ipAddress).orElse(new LoginAttemptEntity("ip", null, false, null));

        if (getBlocked.getBlocked()) {
            if (Duration.between(getBlocked.getBlockedAt().toInstant(), new Timestamp(System.currentTimeMillis()).toInstant()).compareTo( Duration.ofHours( 24 )) >= 0){
                loginAttemptRepository.deleteById(ipAddress);
            }
            else {
                throw new BruteForceException();
            }
        }

        if (!matchesPassword(loginRequest.getPassword(), user2.getPassword())) {
            bruteForceProtectionService.LoginFailed(ipAddress);
            throw new InvalidCredentialsException();
        }

        bruteForceProtectionService.LoginSucceeded(ipAddress);
        String accessToken = generateAccessToken(user2);
        return LoginResponse.builder().accessToken(accessToken).userId(user2.getId()).role(user2.getRoles()).build();
    }

    private boolean matchesPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    private String generateAccessToken(UserEntity user) {
        List<String> roles = user.getRoles().stream()
                .map(userRole -> userRole.getRole().toString())
                .toList();

        return accessTokenEncoder.Encode(
                fontys.s3.MonsterEnergyCollection.Domain.AccessToken.builder()
                        .subject(user.getUsername())
                        .roles(roles)
                        .userId(user.getId())
                        .build());
    }

}