package fontys.s3.MonsterEnergyCollection.DataTransferObject;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnergyCanDTO {
    private String name;
    private String description;
    private String category;
    private Integer barcode;
}
