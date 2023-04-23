package dev.jaczerob.toonstats.services.toons;

import dev.jaczerob.toonstats.dto.ToonDTO;
import dev.jaczerob.toonstats.entities.ToonEntity;
import dev.jaczerob.toonstats.repositories.ToonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToonService {
    private final ToonRepository toonRepository;

    public ToonService(final ToonRepository toonRepository) {
        this.toonRepository = toonRepository;
    }

    public void updateToons(final List<ToonDTO> toons) {
        toonRepository.saveAll(toons.stream().map(ToonDTO::toToonEntity).toList());
    }

    public List<ToonDTO> getToons() {
        return toonRepository.findAll().stream().map(ToonDTO::fromToonEntity).toList();
    }
}
