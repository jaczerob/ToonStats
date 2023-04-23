package dev.jaczerob.toonstats.dto;

import dev.jaczerob.toonstats.entities.ToonEntity;
import lombok.Data;

@Data
public class ToonDTO {
    private int id;
    private int species;
    private int laff;
    private int gagToonup;
    private int gagTrap;
    private int gagLure;
    private int gagSound;
    private int gagThrow;
    private int gagSquirt;
    private int gagDrop;
    private int organic;
    private int sellbot;
    private int cashbot;
    private int lawbot;
    private int bossbot;

    public static ToonDTO fromToonEntity(final ToonEntity toonEntity) {
        final ToonDTO toonDTO = new ToonDTO();
        toonDTO.setId(toonEntity.getId());
        toonDTO.setSpecies(toonEntity.getSpecies());
        toonDTO.setLaff(toonEntity.getLaff());
        toonDTO.setGagToonup(toonEntity.getGagToonup());
        toonDTO.setGagTrap(toonEntity.getGagTrap());
        toonDTO.setGagLure(toonEntity.getGagLure());
        toonDTO.setGagSound(toonEntity.getGagSound());
        toonDTO.setGagThrow(toonEntity.getGagThrow());
        toonDTO.setGagSquirt(toonEntity.getGagSquirt());
        toonDTO.setGagDrop(toonEntity.getGagDrop());
        toonDTO.setOrganic(toonEntity.getOrganic());
        toonDTO.setSellbot(toonEntity.getSellbot());
        toonDTO.setCashbot(toonEntity.getCashbot());
        toonDTO.setLawbot(toonEntity.getLawbot());
        toonDTO.setBossbot(toonEntity.getBossbot());
        return toonDTO;
    }

    public ToonEntity toToonEntity() {
        final ToonEntity toonEntity = new ToonEntity();
        toonEntity.setId(id);
        toonEntity.setSpecies(species);
        toonEntity.setLaff(laff);
        toonEntity.setGagToonup(gagToonup);
        toonEntity.setGagTrap(gagTrap);
        toonEntity.setGagLure(gagLure);
        toonEntity.setGagSound(gagSound);
        toonEntity.setGagThrow(gagThrow);
        toonEntity.setGagSquirt(gagSquirt);
        toonEntity.setGagDrop(gagDrop);
        toonEntity.setOrganic(organic);
        toonEntity.setSellbot(sellbot);
        toonEntity.setCashbot(cashbot);
        toonEntity.setLawbot(lawbot);
        toonEntity.setBossbot(bossbot);
        return toonEntity;
    }
}
