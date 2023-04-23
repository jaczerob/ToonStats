package dev.jaczerob.toonstats.controllers;

import dev.jaczerob.toonstats.dto.ToonDTO;
import dev.jaczerob.toonstats.services.toons.ToonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/toons")
public class ToonController {
    private final ToonService toonService;

    public ToonController(final ToonService toonService) {
        this.toonService = toonService;
    }

    @GetMapping
    public List<ToonDTO> getToons(
            @RequestParam(required = false, defaultValue = "15") int laff,
            @RequestParam(required = false, defaultValue = "-1") int species,
            @RequestParam(required = false, defaultValue = "-1") int organic,
            @RequestParam(required = false, defaultValue = "-1") int sellbot,
            @RequestParam(required = false, defaultValue = "-1") int cashbot,
            @RequestParam(required = false, defaultValue = "-1") int lawbot,
            @RequestParam(required = false, defaultValue = "-1") int bossbot
    ) {
        return toonService.getToons()
                .stream()
                .filter(toon -> toon.getLaff() >= laff)
                .filter(toon -> species == -1 || toon.getSpecies() == species)
                .filter(toon -> organic == -1 || toon.getOrganic() == organic)
                .filter(toon -> sellbot == -1 || toon.getSellbot() == sellbot)
                .filter(toon -> cashbot == -1 || toon.getCashbot() == cashbot)
                .filter(toon -> lawbot == -1 || toon.getLawbot() == lawbot)
                .filter(toon -> bossbot == -1 || toon.getBossbot() == bossbot)
                .toList();
    }
}
