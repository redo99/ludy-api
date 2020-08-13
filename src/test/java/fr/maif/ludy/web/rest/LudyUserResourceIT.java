package fr.maif.ludy.web.rest;

import fr.maif.ludy.LudyApp;
import fr.maif.ludy.domain.LudyUser;
import fr.maif.ludy.repository.LudyUserRepository;
import fr.maif.ludy.service.LudyUserService;
import fr.maif.ludy.service.dto.LudyUserDTO;
import fr.maif.ludy.service.mapper.LudyUserMapper;
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
 * Integration tests for the {@link LudyUserResource} REST controller.
 */
@SpringBootTest(classes = LudyApp.class)
public class LudyUserResourceIT {

    private static final Long DEFAULT_SCORE = 1L;
    private static final Long UPDATED_SCORE = 2L;

    private static final Long DEFAULT_GEMS = 1L;
    private static final Long UPDATED_GEMS = 2L;

    @Autowired
    private LudyUserRepository ludyUserRepository;

    @Autowired
    private LudyUserMapper ludyUserMapper;

    @Autowired
    private LudyUserService ludyUserService;

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

    private MockMvc restLudyUserMockMvc;

    private LudyUser ludyUser;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LudyUserResource ludyUserResource = new LudyUserResource(ludyUserService);
        this.restLudyUserMockMvc = MockMvcBuilders.standaloneSetup(ludyUserResource)
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
    public static LudyUser createEntity(EntityManager em) {
        LudyUser ludyUser = new LudyUser()
            .score(DEFAULT_SCORE)
            .gems(DEFAULT_GEMS);
        return ludyUser;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LudyUser createUpdatedEntity(EntityManager em) {
        LudyUser ludyUser = new LudyUser()
            .score(UPDATED_SCORE)
            .gems(UPDATED_GEMS);
        return ludyUser;
    }

    @BeforeEach
    public void initTest() {
        ludyUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createLudyUser() throws Exception {
        int databaseSizeBeforeCreate = ludyUserRepository.findAll().size();

        // Create the LudyUser
        LudyUserDTO ludyUserDTO = ludyUserMapper.toDto(ludyUser);
        restLudyUserMockMvc.perform(post("/api/ludy-users")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ludyUserDTO)))
            .andExpect(status().isCreated());

        // Validate the LudyUser in the database
        List<LudyUser> ludyUserList = ludyUserRepository.findAll();
        assertThat(ludyUserList).hasSize(databaseSizeBeforeCreate + 1);
        LudyUser testLudyUser = ludyUserList.get(ludyUserList.size() - 1);
        assertThat(testLudyUser.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testLudyUser.getGems()).isEqualTo(DEFAULT_GEMS);
    }

    @Test
    @Transactional
    public void createLudyUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ludyUserRepository.findAll().size();

        // Create the LudyUser with an existing ID
        ludyUser.setId(1L);
        LudyUserDTO ludyUserDTO = ludyUserMapper.toDto(ludyUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLudyUserMockMvc.perform(post("/api/ludy-users")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ludyUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LudyUser in the database
        List<LudyUser> ludyUserList = ludyUserRepository.findAll();
        assertThat(ludyUserList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLudyUsers() throws Exception {
        // Initialize the database
        ludyUserRepository.saveAndFlush(ludyUser);

        // Get all the ludyUserList
        restLudyUserMockMvc.perform(get("/api/ludy-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ludyUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE.intValue())))
            .andExpect(jsonPath("$.[*].gems").value(hasItem(DEFAULT_GEMS.intValue())));
    }
    
    @Test
    @Transactional
    public void getLudyUser() throws Exception {
        // Initialize the database
        ludyUserRepository.saveAndFlush(ludyUser);

        // Get the ludyUser
        restLudyUserMockMvc.perform(get("/api/ludy-users/{id}", ludyUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ludyUser.getId().intValue()))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE.intValue()))
            .andExpect(jsonPath("$.gems").value(DEFAULT_GEMS.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLudyUser() throws Exception {
        // Get the ludyUser
        restLudyUserMockMvc.perform(get("/api/ludy-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLudyUser() throws Exception {
        // Initialize the database
        ludyUserRepository.saveAndFlush(ludyUser);

        int databaseSizeBeforeUpdate = ludyUserRepository.findAll().size();

        // Update the ludyUser
        LudyUser updatedLudyUser = ludyUserRepository.findById(ludyUser.getId()).get();
        // Disconnect from session so that the updates on updatedLudyUser are not directly saved in db
        em.detach(updatedLudyUser);
        updatedLudyUser
            .score(UPDATED_SCORE)
            .gems(UPDATED_GEMS);
        LudyUserDTO ludyUserDTO = ludyUserMapper.toDto(updatedLudyUser);

        restLudyUserMockMvc.perform(put("/api/ludy-users")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ludyUserDTO)))
            .andExpect(status().isOk());

        // Validate the LudyUser in the database
        List<LudyUser> ludyUserList = ludyUserRepository.findAll();
        assertThat(ludyUserList).hasSize(databaseSizeBeforeUpdate);
        LudyUser testLudyUser = ludyUserList.get(ludyUserList.size() - 1);
        assertThat(testLudyUser.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testLudyUser.getGems()).isEqualTo(UPDATED_GEMS);
    }

    @Test
    @Transactional
    public void updateNonExistingLudyUser() throws Exception {
        int databaseSizeBeforeUpdate = ludyUserRepository.findAll().size();

        // Create the LudyUser
        LudyUserDTO ludyUserDTO = ludyUserMapper.toDto(ludyUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLudyUserMockMvc.perform(put("/api/ludy-users")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ludyUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LudyUser in the database
        List<LudyUser> ludyUserList = ludyUserRepository.findAll();
        assertThat(ludyUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLudyUser() throws Exception {
        // Initialize the database
        ludyUserRepository.saveAndFlush(ludyUser);

        int databaseSizeBeforeDelete = ludyUserRepository.findAll().size();

        // Delete the ludyUser
        restLudyUserMockMvc.perform(delete("/api/ludy-users/{id}", ludyUser.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LudyUser> ludyUserList = ludyUserRepository.findAll();
        assertThat(ludyUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
