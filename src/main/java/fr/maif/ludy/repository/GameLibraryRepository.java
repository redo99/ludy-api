package fr.maif.ludy.repository;

import fr.maif.ludy.domain.GameLibrary;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GameLibrary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GameLibraryRepository extends JpaRepository<GameLibrary, Long> {

}
