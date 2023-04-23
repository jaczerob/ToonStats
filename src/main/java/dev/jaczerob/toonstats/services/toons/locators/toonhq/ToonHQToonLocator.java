package dev.jaczerob.toonstats.services.toons.locators.toonhq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.jaczerob.toonstats.dto.ToonDTO;
import dev.jaczerob.toonstats.services.toons.locators.ToonLocator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ToonHQToonLocator implements ToonLocator {
    private static final Logger LOGGER = LoggerFactory.getLogger(ToonHQToonLocator.class);
    private static final Pattern TOON_PATTERN = Pattern.compile("\"toon\": (.+?})", Pattern.DOTALL);
    private static final String TOON_HQ_URL = "https://toonhq.org/groups";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public List<ToonDTO> findToons() {
        final Document document;

        try {
            document = Jsoup.connect(TOON_HQ_URL).get();
        } catch (final IOException exc) {
            LOGGER.error("Could not connect to ToonHQ at: {}", TOON_HQ_URL, exc);
            return List.of();
        }

        final Matcher toonMatcher = TOON_PATTERN.matcher(document.html());

        final List<ToonDTO> toons = new ArrayList<>();

        while (toonMatcher.find()) {
            final String toonJson = toonMatcher.group(1);

            final ToonHQToon toonHQToon;

            try {
                toonHQToon = OBJECT_MAPPER.readValue(toonJson, ToonHQToon.class);
            } catch (final JsonProcessingException exc) {
                LOGGER.error("Could not parse ToonHQ Toon: {}", toonJson, exc);
                continue;
            }

            if (toonHQToon.getId() == null) {
                LOGGER.warn("ToonHQ Toon has no ID: {}", toonJson);
                continue;
            } else if (toonHQToon.getPhoto() == null) {
                LOGGER.warn("ToonHQ Toon has no photo, potentially unsynced: {}", toonJson);
                continue;
            } else if (toonHQToon.getGame() == null || toonHQToon.getGame() != 1) {
                LOGGER.warn("ToonHQ Toon is not a TTR toon: {}", toonJson);
                continue;
            }

            toons.add(toonHQToon.toToonDTO());
        }

        return toons;
    }
}
