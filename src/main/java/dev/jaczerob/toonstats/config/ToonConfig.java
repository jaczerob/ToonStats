package dev.jaczerob.toonstats.config;

import dev.jaczerob.toonstats.services.toons.locators.ToonLocator;
import dev.jaczerob.toonstats.services.toons.locators.toonhq.ToonHQToonLocator;
import org.springframework.context.annotation.Bean;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;

@Dependent
public class ToonConfig {
    @Produces
    public ToonLocator getToonLocator() {
        return new ToonHQToonLocator();
    }
}
