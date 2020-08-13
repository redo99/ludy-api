package fr.maif.ludy.web.rest;

import fr.maif.ludy.LudyApp;
import fr.maif.ludy.domain.GameLibrary;
import fr.maif.ludy.repository.GameLibraryRepository;
import fr.maif.ludy.service.GameLibraryService;
import fr.maif.ludy.service.dto.GameLibraryDTO;
import fr.maif.ludy.service.mapper.GameLibraryMapper;
import fr.maif.ludy.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static fr.maif.ludy.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link GameLibraryResource} REST controller.
 */
@SpringBootTest(classes = LudyApp.class)
public class GameLibraryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private GameLibraryRepository gameLibraryRepository;

    @Autowired
    private GameLibraryMapper gameLibraryMapper;

    @Autowired
    private GameLibraryService gameLibraryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restGameLibraryMockMvc;

    private GameLibrary gameLibrary;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GameLibraryResource gameLibraryResource = new GameLibraryResource(gameLibraryService);
        this.restGameLibraryMockMvc = MockMvcBuilders.standaloneSetup(gameLibraryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GameLibrary createEntity(EntityManager em) {
        GameLibrary gameLibrary = new GameLibrary()
            .name(DEFAULT_NAME);
        return gameLibrary;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GameLibrary createUpdatedEntity(EntityManager em) {
        GameLibrary gameLibrary = new GameLibrary()
            .name(UPDATED_NAME);
        return gameLibrary;
    }

    @BeforeEach
    public void initTest() {
        gameLibrary = createEntity(em);
    }

    @Test
    @Transactional
    public void createGameLibrary() throws Exception {
        int databaseSizeBeforeCreate = gameLibraryRepository.findAll().size();

        // Create the GameLibrary
        GameLibraryDTO gameLibraryDTO = gameLibraryMapper.toDto(gameLibrary);
        restGameLibraryMockMvc.perform(post("/api/game-libraries")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gameLibraryDTO)))
            .andExpect(status().isCreated());

        // Validate the GameLibrary in the database
        List<GameLibrary> gameLibraryList = gameLibraryRepository.findAll();
        assertThat(gameLibraryList).hasSize(databaseSizeBeforeCreate + 1);
        GameLibrary testGameLibrary = gameLibraryList.get(gameLibraryList.size() - 1);
        assertThat(testGameLibrary.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createGameLibraryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gameLibraryRepository.findAll().size();

        // Create the GameLibrary with an existing ID
        gameLibrary.setId(1L);
        GameLibraryDTO gameLibraryDTO = gameLibraryMapper.toDto(gameLibrary);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGameLibraryMockMvc.perform(post("/api/game-libraries")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gameLibraryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GameLibrary in the database
        List<GameLibrary> gameLibraryList = gameLibraryRepository.findAll();
        assertThat(gameLibraryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGameLibraries() throws Exception {
        // Initialize the database
        gameLibraryRepository.saveAndFlush(gameLibrary);

        // Get all the gameLibraryList
        restGameLibraryMockMvc.perform(get("/api/game-libraries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gameLibrary.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getGameLibrary() throws Exception {
        // Initialize the database
        gameLibraryRepository.saveAndFlush(gameLibrary);

        // Get the gameLibrary
        restGameLibraryMockMvc.perform(get("/api/game-libraries/{id}", gameLibrary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gameLibrary.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingGameLibrary() throws Exception {
        // Get the gameLibrary
        restGameLibraryMockMvc.perform(get("/api/game-libraries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGameLibrary() throws Exception {
        // Initialize the database
        gameLibraryRepository.saveAndFlush(gameLibrary);

        int databaseSizeBeforeUpdate = gameLibraryRepository.findAll().size();

        // Update the gameLibrary
        GameLibrary updatedGameLibrary = gameLibraryRepository.findById(gameLibrary.getId()).get();
        // Disconnect from session so that the updates on updatedGameLibrary are not directly saved in db
        em.detach(updatedGameLibrary);
        updatedGameLibrary
            .name(UPDATED_NAME);
        GameLibraryDTO gameLibraryDTO = gameLibraryMapper.toDto(updatedGameLibrary);

        restGameLibraryMockMvc.perform(put("/api/game-libraries")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gameLibraryDTO)))
            .andExpect(status().isOk());

        // Validate the GameLibrary in the database
        List<GameLibrary> gameLibraryList = gameLibraryRepository.findAll();
        assertThat(gameLibraryList).hasSize(databaseSizeBeforeUpdate);
        GameLibrary testGameLibrary = gameLibraryList.get(gameLibraryList.size() - 1);
        assertThat(testGameLibrary.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingGameLibrary() throws Exception {
        int databaseSizeBeforeUpdate = gameLibraryRepository.findAll().size();

        // Create the GameLibrary
        GameLibraryDTO gameLibraryDTO = gameLibraryMapper.toDto(gameLibrary);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGameLibraryMockMvc.perform(put("/api/game-libraries")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gameLibraryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GameLibrary in the database
        List<GameLibrary> gameLibraryList = gameLibraryRepository.findAll();
        assertThat(gameLibraryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGameLibrary() throws Exception {
        // Initialize the database
        gameLibraryRepository.saveAndFlush(gameLibrary);

        int databaseSizeBeforeDelete = gameLibraryRepository.findAll().size();

        // Delete the gameLibrary
        restGameLibraryMockMvc.perform(delete("/api/game-libraries/{id}", gameLibrary.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GameLibrary> gameLibraryList = gameLibraryRepository.findAll();
        assertThat(gameLibraryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
