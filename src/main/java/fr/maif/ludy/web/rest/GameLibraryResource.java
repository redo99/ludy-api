package fr.maif.ludy.web.rest;

import fr.maif.ludy.service.GameLibraryService;
import fr.maif.ludy.web.rest.errors.BadRequestAlertException;
import fr.maif.ludy.service.dto.GameLibraryDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link fr.maif.ludy.domain.GameLibrary}.
 */
@RestController
@RequestMapping("/api")
public class GameLibraryResource {

    private final Logger log = LoggerFactory.getLogger(GameLibraryResource.class);

    private static final String ENTITY_NAME = "gameLibrary";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GameLibraryService gameLibraryService;

    public GameLibraryResource(GameLibraryService gameLibraryService) {
        this.gameLibraryService = gameLibraryService;
    }

    /**
     * {@code POST  /game-libraries} : Create a new gameLibrary.
     *
     * @param gameLibraryDTO the gameLibraryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gameLibraryDTO, or with status {@code 400 (Bad Request)} if the gameLibrary has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/game-libraries")
    public ResponseEntity<GameLibraryDTO> createGameLibrary(@RequestBody GameLibraryDTO gameLibraryDTO) throws URISyntaxException {
        log.debug("REST request to save GameLibrary : {}", gameLibraryDTO);
        if (gameLibraryDTO.getId() != null) {
            throw new BadRequestAlertException("A new gameLibrary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GameLibraryDTO result = gameLibraryService.save(gameLibraryDTO);
        return ResponseEntity.created(new URI("/api/game-libraries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /game-libraries} : Updates an existing gameLibrary.
     *
     * @param gameLibraryDTO the gameLibraryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gameLibraryDTO,
     * or with status {@code 400 (Bad Request)} if the gameLibraryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gameLibraryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/game-libraries")
    public ResponseEntity<GameLibraryDTO> updateGameLibrary(@RequestBody GameLibraryDTO gameLibraryDTO) throws URISyntaxException {
        log.debug("REST request to update GameLibrary : {}", gameLibraryDTO);
        if (gameLibraryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GameLibraryDTO result = gameLibraryService.save(gameLibraryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gameLibraryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /game-libraries} : get all the gameLibraries.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gameLibraries in body.
     */
    @GetMapping("/game-libraries")
    public ResponseEntity<List<GameLibraryDTO>> getAllGameLibraries(Pageable pageable) {
        log.debug("REST request to get a page of GameLibraries");
        Page<GameLibraryDTO> page = gameLibraryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /game-libraries/:id} : get the "id" gameLibrary.
     *
     * @param id the id of the gameLibraryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gameLibraryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/game-libraries/{id}")
    public ResponseEntity<GameLibraryDTO> getGameLibrary(@PathVariable Long id) {
        log.debug("REST request to get GameLibrary : {}", id);
        Optional<GameLibraryDTO> gameLibraryDTO = gameLibraryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gameLibraryDTO);
    }

    /**
     * {@code DELETE  /game-libraries/:id} : delete the "id" gameLibrary.
     *
     * @param id the id of the gameLibraryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/game-libraries/{id}")
    public ResponseEntity<Void> deleteGameLibrary(@PathVariable Long id) {
        log.debug("REST request to delete GameLibrary : {}", id);
        gameLibraryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
