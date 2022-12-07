package fontys.s3.MonsterEnergyCollection.Presentation;

import fontys.s3.MonsterEnergyCollection.Business.Entity.ScannedEnergyCanEntity;
import fontys.s3.MonsterEnergyCollection.Business.ScannedEnergyCanCrud;
import fontys.s3.MonsterEnergyCollection.DataTransferObject.ScannedEnergyCanDTO;
import fontys.s3.MonsterEnergyCollection.Security.IsAuthenticated.IsAuthenticated;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/api")
public class ScannedEnergyCanController {
    private final ScannedEnergyCanCrud scannedEnergyCanCrud;

    public ScannedEnergyCanController(ScannedEnergyCanCrud scannedEnergyCanCrud){
        this.scannedEnergyCanCrud = scannedEnergyCanCrud;
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_USER"})
    @GetMapping("/scannedEnergyCan")
    public ResponseEntity<List<ScannedEnergyCanEntity>> getAllScannedEnergyCans(@RequestParam(required = true) Long userId) {
        try {
            List<ScannedEnergyCanEntity> scannedEnergyCanEntities = new ArrayList<>();
            if (userId != null) {
                scannedEnergyCanEntities = scannedEnergyCanCrud.FindByUserId(userId);
            }

            if (scannedEnergyCanEntities.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(scannedEnergyCanEntities, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_USER"})
    @GetMapping("/scannedEnergyCan/{id}")
    public ResponseEntity<ScannedEnergyCanEntity> getScannedEnergyCanById(@PathVariable("id") long id) {
        Optional<ScannedEnergyCanEntity> scannedEnergyCanData = scannedEnergyCanCrud.GetScannedEnergyCanById(id);
        if (scannedEnergyCanData.isPresent()) {
            return new ResponseEntity<>(scannedEnergyCanData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_USER"})
    @PostMapping("/scannedEnergyCan")
    public ResponseEntity<ScannedEnergyCanEntity> createScannedEnergyCan(@RequestBody ScannedEnergyCanDTO scannedEnergyCanDTO) {
        try {
            ScannedEnergyCanEntity _scannedEnergyCanEntity = scannedEnergyCanCrud.CreateScannedEnergyCan(new ScannedEnergyCanEntity(scannedEnergyCanDTO.getEnergyCanEntity(), scannedEnergyCanDTO.getUserEntity()));
            return new ResponseEntity<>(_scannedEnergyCanEntity, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
