package fr.maif.ludy.service;

import fr.maif.ludy.service.dto.GameLibraryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link fr.maif.ludy.domain.GameLibrary}.
 */
public interface GameLibraryService {

    /**
     * Save a gameLibrary.
     *
     * @param gameLibraryDTO the entity to save.
     * @return the persisted entity.
     */
    GameLibraryDTO save(GameLibraryDTO gameLibraryDTO);

    /**
     * Get all the gameLibraries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GameLibraryDTO> findAll(Pageable pageable);

    /**
     * Get the "id" gameLibrary.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GameLibraryDTO> findOne(Long id);

    /**
     * Delete the "id" gameLibrary.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
