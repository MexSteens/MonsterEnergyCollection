package fontys.s3.MonsterEnergyCollection.Presentation;

import fontys.s3.MonsterEnergyCollection.Business.OriginCrud;
import fontys.s3.MonsterEnergyCollection.Business.Entity.OriginEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class OriginController {
    private final OriginCrud originCrud;

    public OriginController(OriginCrud originCrud){
        this.originCrud = originCrud;
    }

    @GetMapping("/origin")
    public ResponseEntity<List<OriginEntity>> getAllOrigins() {
        try {
            List<OriginEntity> originEntities = originCrud.GetAllOrigins();
            if (originEntities.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(originEntities, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/origin/{id}")
    public ResponseEntity<OriginEntity> getOriginById(@PathVariable("id") String name) {
        Optional<OriginEntity> originData = originCrud.GetOriginById(name);
        if (originData.isPresent()) {
            return new ResponseEntity<>(originData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/origin/{id}")
    public ResponseEntity<HttpStatus> deleteOrigin(@PathVariable("id") String name) {
        try {
            originCrud.DeleteOrigin(name);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
