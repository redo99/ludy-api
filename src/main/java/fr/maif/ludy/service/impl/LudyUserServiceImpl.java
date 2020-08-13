package fr.maif.ludy.service.impl;

import fr.maif.ludy.service.LudyUserService;
import fr.maif.ludy.domain.LudyUser;
import fr.maif.ludy.repository.LudyUserRepository;
import fr.maif.ludy.service.dto.LudyUserDTO;
import fr.maif.ludy.service.mapper.LudyUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link LudyUser}.
 */
@Service
@Transactional
public class LudyUserServiceImpl implements LudyUserService {

    private final Logger log = LoggerFactory.getLogger(LudyUserServiceImpl.class);

    private final LudyUserRepository ludyUserRepository;

    private final LudyUserMapper ludyUserMapper;

    public LudyUserServiceImpl(LudyUserRepository ludyUserRepository, LudyUserMapper ludyUserMapper) {
        this.ludyUserRepository = ludyUserRepository;
        this.ludyUserMapper = ludyUserMapper;
    }

    /**
     * Save a ludyUser.
     *
     * @param ludyUserDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public LudyUserDTO save(LudyUserDTO ludyUserDTO) {
        log.debug("Request to save LudyUser : {}", ludyUserDTO);
        LudyUser ludyUser = ludyUserMapper.toEntity(ludyUserDTO);
        ludyUser = ludyUserRepository.save(ludyUser);
        return ludyUserMapper.toDto(ludyUser);
    }

    /**
     * Get all the ludyUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LudyUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LudyUsers");
        return ludyUserRepository.findAll(pageable)
            .map(ludyUserMapper::toDto);
    }

    /**
     * Get one ludyUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LudyUserDTO> findOne(Long id) {
        log.debug("Request to get LudyUser : {}", id);
        return ludyUserRepository.findById(id)
            .map(ludyUserMapper::toDto);
    }

    /**
     * Delete the ludyUser by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LudyUser : {}", id);
        ludyUserRepository.deleteById(id);
    }
}
