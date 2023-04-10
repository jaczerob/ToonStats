package dev.jaczerob.toonstats.repositories;

import dev.jaczerob.toonstats.entities.ToonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToonRepository extends JpaRepository<ToonEntity, Integer> {
}
