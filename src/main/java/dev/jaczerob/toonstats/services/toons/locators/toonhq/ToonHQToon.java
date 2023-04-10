package dev.jaczerob.toonstats.services.toons.locators.toonhq;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.jaczerob.toonstats.entities.ToonEntity;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ToonHQToon {
    private static final Map<String, Integer> ORGANIC_LOOKUP = Map.of(
            "toonup",  1,
            "trap",  2,
            "lure",  3,
            "sound",  4,
            "throw",  5,
            "squirt",  6,
            "drop",  7
    );
    
    private Integer id;
    private Integer game;
    private String photo;
    private Integer species;
    private Integer laff;
    private Integer toonup;
    private Integer trap;
    private Integer lure;
    private Integer sound;

    @JsonProperty("throw")
    private Integer throwGag;
    private Integer squirt;
    private Integer drop;
    private List<String> prestiges;
    private Integer sellbot;
    private Integer cashbot;
    private Integer lawbot;
    private Integer bossbot;

    public ToonEntity toToonEntity() {
        final ToonEntity toonEntity = new ToonEntity();

        toonEntity.setId(id);
        toonEntity.setSpecies(species);
        toonEntity.setLaff(laff);
        toonEntity.setGagToonup(toonup);
        toonEntity.setGagTrap(trap);
        toonEntity.setGagLure(lure);
        toonEntity.setGagSound(sound);
        toonEntity.setGagThrow(throwGag);
        toonEntity.setGagSquirt(squirt);
        toonEntity.setGagDrop(drop);
        toonEntity.setSellbot(sellbot);
        toonEntity.setCashbot(cashbot);
        toonEntity.setLawbot(lawbot);
        toonEntity.setBossbot(bossbot);

        if (this.getPrestiges().isEmpty()) {
            toonEntity.setOrganic(0);
        } else {
            toonEntity.setOrganic(ORGANIC_LOOKUP.getOrDefault(this.getPrestiges().get(0), 0));
        }
        
        return toonEntity;
    }
}
