package fontys.s3.MonsterEnergyCollection.Presentation;

import fontys.s3.MonsterEnergyCollection.Business.EnergyCanCrud;
import fontys.s3.MonsterEnergyCollection.Business.Entity.EnergyCanEntity;
import fontys.s3.MonsterEnergyCollection.DataTransferObject.EnergyCanDTO;
import fontys.s3.MonsterEnergyCollection.Security.IsAuthenticated.IsAuthenticated;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/api")
public class EnergyCanController {
    private final EnergyCanCrud energyCanCrud;

    public EnergyCanController(EnergyCanCrud energyCanCrud){
        this.energyCanCrud = energyCanCrud;
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_USER"})
    @GetMapping("/can")
    public ResponseEntity<List<EnergyCanEntity>> getAllEnergyCans() {
        try {
            List<EnergyCanEntity> energyCanEntities = energyCanCrud.GetAllCans();
            if (energyCanEntities.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(energyCanEntities, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_USER", "ROLE_MODERATOR"})
    @GetMapping("/can/{id}")
    public ResponseEntity<EnergyCanEntity> getEnergyCanById(@PathVariable("id") long id) {
        Optional<EnergyCanEntity> enrgyCanData = energyCanCrud.GetEnergyCanById(id);
        if (enrgyCanData.isPresent()) {
            return new ResponseEntity<>(enrgyCanData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_USER", "ROLE_MODERATOR"})
    @PostMapping("/can")
    public ResponseEntity<EnergyCanEntity> createEnergyCan(@RequestBody EnergyCanDTO energyCanDTO) {
        try {
            EnergyCanEntity _energyCanEntity = energyCanCrud.CreateEnergyCan(new EnergyCanEntity(energyCanDTO.getName(), energyCanDTO.getDescription(), energyCanDTO.getCategory(), energyCanDTO.getBarcode()));
            return new ResponseEntity<>(_energyCanEntity, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_MODERATOR"})
    @PutMapping("/can/{id}")
    public ResponseEntity<EnergyCanEntity> updateEnergyCan(@PathVariable("id") long id, @RequestBody EnergyCanDTO energyCanDTO) {
        return new ResponseEntity<>(energyCanCrud.UpdateEnergyCan(id, new EnergyCanEntity(energyCanDTO.getName(), energyCanDTO.getDescription(), energyCanDTO.getCategory(), energyCanDTO.getBarcode())), HttpStatus.OK);
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_MODERATOR"})
    @DeleteMapping("/can/{id}")
    public ResponseEntity<HttpStatus> deleteEnergyCan(@PathVariable("id") long id) {
        try {
            energyCanCrud.DeleteEnergyCan(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
