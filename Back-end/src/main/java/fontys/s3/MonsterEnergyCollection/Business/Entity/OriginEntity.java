package fontys.s3.MonsterEnergyCollection.Business.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Origin")
@Getter
@Setter
@Builder
public class OriginEntity {
    @Id
    @NotNull
    @Column(name = "country")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String country;

    @Column(name = "continent")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String continent;

    public OriginEntity(){}

    public String getCountry(){
        return this.country;
    }

    public String getContinent() {
        return this.continent;
    }

    public void setCountry(String newCountry) {
        this.country = newCountry;
    }

    public void setContinent(String newContinent) {
        this.continent = newContinent;
    }

    public OriginEntity(String country, String continent){
        this.country = country;
        this.continent = continent;
    }
}