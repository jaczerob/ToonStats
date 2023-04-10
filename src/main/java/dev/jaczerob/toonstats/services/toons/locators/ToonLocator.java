package dev.jaczerob.toonstats.services.toons.locators;

import dev.jaczerob.toonstats.entities.ToonEntity;

import java.util.List;

public interface ToonLocator {
    List<ToonEntity> findToons();
}
