package dev.jaczerob.toonstats.config;

import dev.jaczerob.toonstats.entities.ToonEntity;
import dev.jaczerob.toonstats.repositories.ToonRepository;
import dev.jaczerob.toonstats.services.toons.locators.ToonLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration
@EnableScheduling
public class CronConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(CronConfig.class);

    private final ToonLocator toonLocator;
    private final ToonRepository toonRepository;

    public CronConfig(final ToonLocator toonLocator, final ToonRepository toonRepository) {
        this.toonLocator = toonLocator;
        this.toonRepository = toonRepository;
    }

    // run every 10 minutes
    @Scheduled(fixedRate = 1000 * 60 * 10)
    public void updateToons() {
        LOGGER.info("Running cron job to update toons...");

        final List<ToonEntity> toons = toonLocator.findToons();
        if (toons.isEmpty()) {
            LOGGER.warn("No toons found! Could be an error or there are no toons to update.");
            return;
        }

        LOGGER.info("Found {} toons", toons.size());

        final List<ToonEntity> savedToons = toonRepository.saveAll(toons);
        if (savedToons.isEmpty()) {
            LOGGER.warn("No toons saved!");
            return;
        }

        LOGGER.info("Saved {} toons", savedToons.size());
        LOGGER.info("Cron job finished");
    }
}
