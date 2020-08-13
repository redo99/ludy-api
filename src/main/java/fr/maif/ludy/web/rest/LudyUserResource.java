package fr.maif.ludy.web.rest;

import fr.maif.ludy.service.LudyUserService;
import fr.maif.ludy.web.rest.errors.BadRequestAlertException;
import fr.maif.ludy.service.dto.LudyUserDTO;

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
 * REST controller for managing {@link fr.maif.ludy.domain.LudyUser}.
 */
@RestController
@RequestMapping("/api")
public class LudyUserResource {

    private final Logger log = LoggerFactory.getLogger(LudyUserResource.class);

    private static final String ENTITY_NAME = "ludyUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LudyUserService ludyUserService;

    public LudyUserResource(LudyUserService ludyUserService) {
        this.ludyUserService = ludyUserService;
    }

    /**
     * {@code POST  /ludy-users} : Create a new ludyUser.
     *
     * @param ludyUserDTO the ludyUserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ludyUserDTO, or with status {@code 400 (Bad Request)} if the ludyUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ludy-users")
    public ResponseEntity<LudyUserDTO> createLudyUser(@RequestBody LudyUserDTO ludyUserDTO) throws URISyntaxException {
        log.debug("REST request to save LudyUser : {}", ludyUserDTO);
        if (ludyUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new ludyUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LudyUserDTO result = ludyUserService.save(ludyUserDTO);
        return ResponseEntity.created(new URI("/api/ludy-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ludy-users} : Updates an existing ludyUser.
     *
     * @param ludyUserDTO the ludyUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ludyUserDTO,
     * or with status {@code 400 (Bad Request)} if the ludyUserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ludyUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ludy-users")
    public ResponseEntity<LudyUserDTO> updateLudyUser(@RequestBody LudyUserDTO ludyUserDTO) throws URISyntaxException {
        log.debug("REST request to update LudyUser : {}", ludyUserDTO);
        if (ludyUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LudyUserDTO result = ludyUserService.save(ludyUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ludyUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ludy-users} : get all the ludyUsers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ludyUsers in body.
     */
    @GetMapping("/ludy-users")
    public ResponseEntity<List<LudyUserDTO>> getAllLudyUsers(Pageable pageable) {
        log.debug("REST request to get a page of LudyUsers");
        Page<LudyUserDTO> page = ludyUserService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ludy-users/:id} : get the "id" ludyUser.
     *
     * @param id the id of the ludyUserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ludyUserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ludy-users/{id}")
    public ResponseEntity<LudyUserDTO> getLudyUser(@PathVariable Long id) {
        log.debug("REST request to get LudyUser : {}", id);
        Optional<LudyUserDTO> ludyUserDTO = ludyUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ludyUserDTO);
    }

    /**
     * {@code DELETE  /ludy-users/:id} : delete the "id" ludyUser.
     *
     * @param id the id of the ludyUserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ludy-users/{id}")
    public ResponseEntity<Void> deleteLudyUser(@PathVariable Long id) {
        log.debug("REST request to delete LudyUser : {}", id);
        ludyUserService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
