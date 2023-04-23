package dev.jaczerob.toonstats.config;

import dev.jaczerob.toonstats.dto.ToonDTO;
import dev.jaczerob.toonstats.entities.ToonEntity;
import dev.jaczerob.toonstats.services.toons.ToonService;
import dev.jaczerob.toonstats.services.toons.locators.ToonLocator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration
public class CronConfig {
    private static final Logger LOGGER = LogManager.getLogger(CronConfig.class);

    private final ToonLocator toonLocator;
    private final ToonService toonService;

    public CronConfig(final ToonLocator toonLocator, final ToonService toonService) {
        this.toonLocator = toonLocator;
        this.toonService = toonService;
    }

    @Scheduled(cron = "0 0/10 * * * *")
    public void updateToons() {
        LOGGER.info("Running cron job to update toons...");

        final List<ToonDTO> toons = toonLocator.findToons();
        if (toons.isEmpty()) {
            LOGGER.warn("No toons found! Could be an error or there are no toons to update.");
            return;
        }

        toonService.updateToons(toons);
        LOGGER.info("Found and updated %s toons".formatted(toons.size()));

        LOGGER.info("Cron job finished");
    }
}
