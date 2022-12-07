package fontys.s3.MonsterEnergyCollection.Business.Entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Timestamp;

@Entity
@Table(name = "ENERGYCAN")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnergyCanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private Long id;

    @NotNull
    @Column(name = "name")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String name;

    @NotNull
    @Column(name = "category")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String category;

    @Column(name = "description")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String description;

    @Column(name = "runningRating")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT)
    private Double runningRating;

    @Column(name = "timesRated")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private Integer timesRated;

    @Column(name = "created")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Timestamp created;

    @Column(name = "imagePath")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String imagePath;

    @Column(name = "barcode")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private Integer barcode;

    public Long getId() {
        return id;
    }

    public String getName(){
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getCategory() { return category; }

    public Double getRunningRating() {
        return runningRating;
    }

    public Integer getTimesRated() {
        return timesRated;
    }

    public Integer getBarcode() {
        return barcode;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void setDescription(String newDescription) {
        this.description = newDescription;
    }

    public void setCategory(String category) { this.category = category; }

    public void setRunningRating(Double runningRating) {
        this.runningRating = runningRating;
    }

    public void setTimesRated(Integer timesRated) {
        this.timesRated = timesRated;
    }

    public void setBarcode(Integer barcode) {
        this.barcode = barcode;
    }

    public EnergyCanEntity(String name, String description, String category, Integer barcode){
        this.name = name;
        this.category = category;
        this.description = description;
        this.barcode = barcode;
    }
}
