package dev.jaczerob.toonstats.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

@Entity(name = "toons")
@Data
public class ToonEntity {
    @Id
    private int id;
    private int species;
    private int laff;

    @Column(name = "gag_toonup")
    private int gagToonup;

    @Column(name = "gag_trap")
    private int gagTrap;

    @Column(name = "gag_lure")
    private int gagLure;

    @Column(name = "gag_sound")
    private int gagSound;

    @Column(name = "gag_throw")
    private int gagThrow;

    @Column(name = "gag_squirt")
    private int gagSquirt;

    @Column(name = "gag_drop")
    private int gagDrop;

    private int organic;
    private int sellbot;
    private int cashbot;
    private int lawbot;
    private int bossbot;
}
