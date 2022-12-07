package fontys.s3.MonsterEnergyCollection.Presentation;

import fontys.s3.MonsterEnergyCollection.Business.Login;
import fontys.s3.MonsterEnergyCollection.Domain.LoginRequest;
import fontys.s3.MonsterEnergyCollection.Domain.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {

    private final Login login;


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody(required = true) LoginRequest loginRequest) {
        LoginResponse loginResponse = login.login(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }
}