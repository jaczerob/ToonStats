package dev.jaczerob.toonstats.services.toons;

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

    public void updateToons(final List<ToonEntity> toonEntities) {
        toonRepository.saveAll(toonEntities);
    }

    public List<ToonEntity> getToons() {
        return toonRepository.findAll();
    }
}
