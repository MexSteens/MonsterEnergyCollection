package fontys.s3.MonsterEnergyCollection.Business.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BruteForceException extends ResponseStatusException {
    public BruteForceException() {
        super(HttpStatus.BAD_REQUEST, "TOO MANY REQUEST, BLOCKED FOR 24 HOURS");
    }
}
