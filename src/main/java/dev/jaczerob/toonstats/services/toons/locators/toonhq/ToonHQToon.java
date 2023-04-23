package dev.jaczerob.toonstats.services.toons.locators.toonhq;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.jaczerob.toonstats.dto.ToonDTO;
import dev.jaczerob.toonstats.entities.ToonEntity;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ToonHQToon {
    private static final Map<String, Integer> ORGANIC_LOOKUP = Map.of(
            "toonup", 1,
            "trap", 2,
            "lure", 3,
            "sound", 4,
            "throw", 5,
            "squirt", 6,
            "drop", 7
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

    public ToonDTO toToonDTO() {
        final ToonDTO toonDTO = new ToonDTO();

        toonDTO.setId(id);
        toonDTO.setSpecies(species);
        toonDTO.setLaff(laff);
        toonDTO.setGagToonup(toonup);
        toonDTO.setGagTrap(trap);
        toonDTO.setGagLure(lure);
        toonDTO.setGagSound(sound);
        toonDTO.setGagThrow(throwGag);
        toonDTO.setGagSquirt(squirt);
        toonDTO.setGagDrop(drop);
        toonDTO.setSellbot(sellbot);
        toonDTO.setCashbot(cashbot);
        toonDTO.setLawbot(lawbot);
        toonDTO.setBossbot(bossbot);

        if (this.getPrestiges().isEmpty()) {
            toonDTO.setOrganic(0);
        } else {
            toonDTO.setOrganic(ORGANIC_LOOKUP.getOrDefault(this.getPrestiges().get(0), 0));
        }

        return toonDTO;
    }
}
