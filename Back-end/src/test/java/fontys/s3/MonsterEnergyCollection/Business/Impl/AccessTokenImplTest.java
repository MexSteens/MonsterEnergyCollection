package fontys.s3.MonsterEnergyCollection.Business.Impl;

import fontys.s3.MonsterEnergyCollection.Domain.AccessToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Value;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.Key;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)

class AccessTokenImplTest {
    @Mock
    private AccessTokenImpl accessToken;

    @BeforeEach
    void setUp()
    {
        this.accessToken = new AccessTokenImpl("E91E158E4C6656F68B1B5D1C316766DE98D2AD6EF3BFB44F78E9CFCDF5");
    }


    @Test
    public void Encode_JWT_ReturnTrue() {
        //Arrange
        AccessToken accessToken1 = new AccessToken("subject", List.of("ADMIN"), Long.valueOf(12));
        byte[] keyBytes = Decoders.BASE64.decode("E91E158E4C6656F68B1B5D1C316766DE98D2AD6EF3BFB44F78E9CFCDF5");
        Key key = Keys.hmacShaKeyFor(keyBytes);


        //Act
        String actualResult = accessToken.Encode(accessToken1);
        Jwt jwt = Jwts.parserBuilder().setSigningKey(key).build().parse(actualResult);
        Claims claims = (Claims) jwt.getBody();

        //Assert
        assertEquals(accessToken1.getSubject(), claims.getSubject());
    }

    @Test
    public void Decode_JWT_ReturnTrue() {
        //Arrange
        AccessToken accessToken1 = new AccessToken("subject", List.of("ADMIN"), Long.valueOf(12));
        String encodedAccessToken = accessToken.Encode(accessToken1);
        byte[] keyBytes = Decoders.BASE64.decode("E91E158E4C6656F68B1B5D1C316766DE98D2AD6EF3BFB44F78E9CFCDF5");
        Key key = Keys.hmacShaKeyFor(keyBytes);


        //Act
        AccessToken decodedAccesToken = accessToken.Decode(encodedAccessToken);

        //Assert
        assertEquals(accessToken1.getSubject(), decodedAccesToken.getSubject());
    }
}