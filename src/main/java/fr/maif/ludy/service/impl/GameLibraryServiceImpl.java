package fr.maif.ludy.service.impl;

import fr.maif.ludy.service.GameLibraryService;
import fr.maif.ludy.domain.GameLibrary;
import fr.maif.ludy.repository.GameLibraryRepository;
import fr.maif.ludy.service.dto.GameLibraryDTO;
import fr.maif.ludy.service.mapper.GameLibraryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link GameLibrary}.
 */
@Service
@Transactional
public class GameLibraryServiceImpl implements GameLibraryService {

    private final Logger log = LoggerFactory.getLogger(GameLibraryServiceImpl.class);

    private final GameLibraryRepository gameLibraryRepository;

    private final GameLibraryMapper gameLibraryMapper;

    public GameLibraryServiceImpl(GameLibraryRepository gameLibraryRepository, GameLibraryMapper gameLibraryMapper) {
        this.gameLibraryRepository = gameLibraryRepository;
        this.gameLibraryMapper = gameLibraryMapper;
    }

    /**
     * Save a gameLibrary.
     *
     * @param gameLibraryDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public GameLibraryDTO save(GameLibraryDTO gameLibraryDTO) {
        log.debug("Request to save GameLibrary : {}", gameLibraryDTO);
        GameLibrary gameLibrary = gameLibraryMapper.toEntity(gameLibraryDTO);
        gameLibrary = gameLibraryRepository.save(gameLibrary);
        return gameLibraryMapper.toDto(gameLibrary);
    }

    /**
     * Get all the gameLibraries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GameLibraryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GameLibraries");
        return gameLibraryRepository.findAll(pageable)
            .map(gameLibraryMapper::toDto);
    }

    /**
     * Get one gameLibrary by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<GameLibraryDTO> findOne(Long id) {
        log.debug("Request to get GameLibrary : {}", id);
        return gameLibraryRepository.findById(id)
            .map(gameLibraryMapper::toDto);
    }

    /**
     * Delete the gameLibrary by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete GameLibrary : {}", id);
        gameLibraryRepository.deleteById(id);
    }
}
