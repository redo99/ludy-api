package fr.maif.ludy.service;

import fr.maif.ludy.service.dto.LudyUserDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link fr.maif.ludy.domain.LudyUser}.
 */
public interface LudyUserService {

    /**
     * Save a ludyUser.
     *
     * @param ludyUserDTO the entity to save.
     * @return the persisted entity.
     */
    LudyUserDTO save(LudyUserDTO ludyUserDTO);

    /**
     * Get all the ludyUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LudyUserDTO> findAll(Pageable pageable);

    /**
     * Get the "id" ludyUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LudyUserDTO> findOne(Long id);

    /**
     * Delete the "id" ludyUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
