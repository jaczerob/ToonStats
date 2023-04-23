package dev.jaczerob.toonstats.config;

import dev.jaczerob.toonstats.services.toons.locators.ToonLocator;
import dev.jaczerob.toonstats.services.toons.locators.toonhq.ToonHQToonLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ToonConfig {
    @Bean
    public ToonLocator getToonLocator() {
        return new ToonHQToonLocator();
    }
}
