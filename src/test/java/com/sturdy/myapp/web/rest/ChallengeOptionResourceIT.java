package com.sturdy.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sturdy.myapp.IntegrationTest;
import com.sturdy.myapp.domain.ChallengeOption;
import com.sturdy.myapp.repository.ChallengeOptionRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ChallengeOptionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ChallengeOptionResourceIT {

    private static final String DEFAULT_OPTION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_OPTION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_OPTION = "AAAAAAAAAA";
    private static final String UPDATED_OPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/challenge-options";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ChallengeOptionRepository challengeOptionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChallengeOptionMockMvc;

    private ChallengeOption challengeOption;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChallengeOption createEntity(EntityManager em) {
        ChallengeOption challengeOption = new ChallengeOption().optionName(DEFAULT_OPTION_NAME).option(DEFAULT_OPTION);
        return challengeOption;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChallengeOption createUpdatedEntity(EntityManager em) {
        ChallengeOption challengeOption = new ChallengeOption().optionName(UPDATED_OPTION_NAME).option(UPDATED_OPTION);
        return challengeOption;
    }

    @BeforeEach
    public void initTest() {
        challengeOption = createEntity(em);
    }

    @Test
    @Transactional
    void createChallengeOption() throws Exception {
        int databaseSizeBeforeCreate = challengeOptionRepository.findAll().size();
        // Create the ChallengeOption
        restChallengeOptionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(challengeOption))
            )
            .andExpect(status().isCreated());

        // Validate the ChallengeOption in the database
        List<ChallengeOption> challengeOptionList = challengeOptionRepository.findAll();
        assertThat(challengeOptionList).hasSize(databaseSizeBeforeCreate + 1);
        ChallengeOption testChallengeOption = challengeOptionList.get(challengeOptionList.size() - 1);
        assertThat(testChallengeOption.getOptionName()).isEqualTo(DEFAULT_OPTION_NAME);
        assertThat(testChallengeOption.getOption()).isEqualTo(DEFAULT_OPTION);
    }

    @Test
    @Transactional
    void createChallengeOptionWithExistingId() throws Exception {
        // Create the ChallengeOption with an existing ID
        challengeOption.setId(1L);

        int databaseSizeBeforeCreate = challengeOptionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restChallengeOptionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(challengeOption))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChallengeOption in the database
        List<ChallengeOption> challengeOptionList = challengeOptionRepository.findAll();
        assertThat(challengeOptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllChallengeOptions() throws Exception {
        // Initialize the database
        challengeOptionRepository.saveAndFlush(challengeOption);

        // Get all the challengeOptionList
        restChallengeOptionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(challengeOption.getId().intValue())))
            .andExpect(jsonPath("$.[*].optionName").value(hasItem(DEFAULT_OPTION_NAME)))
            .andExpect(jsonPath("$.[*].option").value(hasItem(DEFAULT_OPTION)));
    }

    @Test
    @Transactional
    void getChallengeOption() throws Exception {
        // Initialize the database
        challengeOptionRepository.saveAndFlush(challengeOption);

        // Get the challengeOption
        restChallengeOptionMockMvc
            .perform(get(ENTITY_API_URL_ID, challengeOption.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(challengeOption.getId().intValue()))
            .andExpect(jsonPath("$.optionName").value(DEFAULT_OPTION_NAME))
            .andExpect(jsonPath("$.option").value(DEFAULT_OPTION));
    }

    @Test
    @Transactional
    void getNonExistingChallengeOption() throws Exception {
        // Get the challengeOption
        restChallengeOptionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingChallengeOption() throws Exception {
        // Initialize the database
        challengeOptionRepository.saveAndFlush(challengeOption);

        int databaseSizeBeforeUpdate = challengeOptionRepository.findAll().size();

        // Update the challengeOption
        ChallengeOption updatedChallengeOption = challengeOptionRepository.findById(challengeOption.getId()).get();
        // Disconnect from session so that the updates on updatedChallengeOption are not directly saved in db
        em.detach(updatedChallengeOption);
        updatedChallengeOption.optionName(UPDATED_OPTION_NAME).option(UPDATED_OPTION);

        restChallengeOptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedChallengeOption.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedChallengeOption))
            )
            .andExpect(status().isOk());

        // Validate the ChallengeOption in the database
        List<ChallengeOption> challengeOptionList = challengeOptionRepository.findAll();
        assertThat(challengeOptionList).hasSize(databaseSizeBeforeUpdate);
        ChallengeOption testChallengeOption = challengeOptionList.get(challengeOptionList.size() - 1);
        assertThat(testChallengeOption.getOptionName()).isEqualTo(UPDATED_OPTION_NAME);
        assertThat(testChallengeOption.getOption()).isEqualTo(UPDATED_OPTION);
    }

    @Test
    @Transactional
    void putNonExistingChallengeOption() throws Exception {
        int databaseSizeBeforeUpdate = challengeOptionRepository.findAll().size();
        challengeOption.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChallengeOptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, challengeOption.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(challengeOption))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChallengeOption in the database
        List<ChallengeOption> challengeOptionList = challengeOptionRepository.findAll();
        assertThat(challengeOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchChallengeOption() throws Exception {
        int databaseSizeBeforeUpdate = challengeOptionRepository.findAll().size();
        challengeOption.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChallengeOptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(challengeOption))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChallengeOption in the database
        List<ChallengeOption> challengeOptionList = challengeOptionRepository.findAll();
        assertThat(challengeOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamChallengeOption() throws Exception {
        int databaseSizeBeforeUpdate = challengeOptionRepository.findAll().size();
        challengeOption.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChallengeOptionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(challengeOption))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChallengeOption in the database
        List<ChallengeOption> challengeOptionList = challengeOptionRepository.findAll();
        assertThat(challengeOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateChallengeOptionWithPatch() throws Exception {
        // Initialize the database
        challengeOptionRepository.saveAndFlush(challengeOption);

        int databaseSizeBeforeUpdate = challengeOptionRepository.findAll().size();

        // Update the challengeOption using partial update
        ChallengeOption partialUpdatedChallengeOption = new ChallengeOption();
        partialUpdatedChallengeOption.setId(challengeOption.getId());

        partialUpdatedChallengeOption.optionName(UPDATED_OPTION_NAME).option(UPDATED_OPTION);

        restChallengeOptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChallengeOption.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChallengeOption))
            )
            .andExpect(status().isOk());

        // Validate the ChallengeOption in the database
        List<ChallengeOption> challengeOptionList = challengeOptionRepository.findAll();
        assertThat(challengeOptionList).hasSize(databaseSizeBeforeUpdate);
        ChallengeOption testChallengeOption = challengeOptionList.get(challengeOptionList.size() - 1);
        assertThat(testChallengeOption.getOptionName()).isEqualTo(UPDATED_OPTION_NAME);
        assertThat(testChallengeOption.getOption()).isEqualTo(UPDATED_OPTION);
    }

    @Test
    @Transactional
    void fullUpdateChallengeOptionWithPatch() throws Exception {
        // Initialize the database
        challengeOptionRepository.saveAndFlush(challengeOption);

        int databaseSizeBeforeUpdate = challengeOptionRepository.findAll().size();

        // Update the challengeOption using partial update
        ChallengeOption partialUpdatedChallengeOption = new ChallengeOption();
        partialUpdatedChallengeOption.setId(challengeOption.getId());

        partialUpdatedChallengeOption.optionName(UPDATED_OPTION_NAME).option(UPDATED_OPTION);

        restChallengeOptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChallengeOption.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChallengeOption))
            )
            .andExpect(status().isOk());

        // Validate the ChallengeOption in the database
        List<ChallengeOption> challengeOptionList = challengeOptionRepository.findAll();
        assertThat(challengeOptionList).hasSize(databaseSizeBeforeUpdate);
        ChallengeOption testChallengeOption = challengeOptionList.get(challengeOptionList.size() - 1);
        assertThat(testChallengeOption.getOptionName()).isEqualTo(UPDATED_OPTION_NAME);
        assertThat(testChallengeOption.getOption()).isEqualTo(UPDATED_OPTION);
    }

    @Test
    @Transactional
    void patchNonExistingChallengeOption() throws Exception {
        int databaseSizeBeforeUpdate = challengeOptionRepository.findAll().size();
        challengeOption.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChallengeOptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, challengeOption.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(challengeOption))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChallengeOption in the database
        List<ChallengeOption> challengeOptionList = challengeOptionRepository.findAll();
        assertThat(challengeOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchChallengeOption() throws Exception {
        int databaseSizeBeforeUpdate = challengeOptionRepository.findAll().size();
        challengeOption.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChallengeOptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(challengeOption))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChallengeOption in the database
        List<ChallengeOption> challengeOptionList = challengeOptionRepository.findAll();
        assertThat(challengeOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamChallengeOption() throws Exception {
        int databaseSizeBeforeUpdate = challengeOptionRepository.findAll().size();
        challengeOption.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChallengeOptionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(challengeOption))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChallengeOption in the database
        List<ChallengeOption> challengeOptionList = challengeOptionRepository.findAll();
        assertThat(challengeOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteChallengeOption() throws Exception {
        // Initialize the database
        challengeOptionRepository.saveAndFlush(challengeOption);

        int databaseSizeBeforeDelete = challengeOptionRepository.findAll().size();

        // Delete the challengeOption
        restChallengeOptionMockMvc
            .perform(delete(ENTITY_API_URL_ID, challengeOption.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ChallengeOption> challengeOptionList = challengeOptionRepository.findAll();
        assertThat(challengeOptionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
