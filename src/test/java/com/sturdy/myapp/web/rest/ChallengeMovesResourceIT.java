package com.sturdy.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sturdy.myapp.IntegrationTest;
import com.sturdy.myapp.domain.ChallengeMoves;
import com.sturdy.myapp.repository.ChallengeMovesRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link ChallengeMovesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ChallengeMovesResourceIT {

    private static final Instant DEFAULT_MOVE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MOVE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/challenge-moves";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ChallengeMovesRepository challengeMovesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChallengeMovesMockMvc;

    private ChallengeMoves challengeMoves;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChallengeMoves createEntity(EntityManager em) {
        ChallengeMoves challengeMoves = new ChallengeMoves().moveDate(DEFAULT_MOVE_DATE);
        return challengeMoves;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChallengeMoves createUpdatedEntity(EntityManager em) {
        ChallengeMoves challengeMoves = new ChallengeMoves().moveDate(UPDATED_MOVE_DATE);
        return challengeMoves;
    }

    @BeforeEach
    public void initTest() {
        challengeMoves = createEntity(em);
    }

    @Test
    @Transactional
    void createChallengeMoves() throws Exception {
        int databaseSizeBeforeCreate = challengeMovesRepository.findAll().size();
        // Create the ChallengeMoves
        restChallengeMovesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(challengeMoves))
            )
            .andExpect(status().isCreated());

        // Validate the ChallengeMoves in the database
        List<ChallengeMoves> challengeMovesList = challengeMovesRepository.findAll();
        assertThat(challengeMovesList).hasSize(databaseSizeBeforeCreate + 1);
        ChallengeMoves testChallengeMoves = challengeMovesList.get(challengeMovesList.size() - 1);
        assertThat(testChallengeMoves.getMoveDate()).isEqualTo(DEFAULT_MOVE_DATE);
    }

    @Test
    @Transactional
    void createChallengeMovesWithExistingId() throws Exception {
        // Create the ChallengeMoves with an existing ID
        challengeMoves.setId(1L);

        int databaseSizeBeforeCreate = challengeMovesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restChallengeMovesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(challengeMoves))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChallengeMoves in the database
        List<ChallengeMoves> challengeMovesList = challengeMovesRepository.findAll();
        assertThat(challengeMovesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllChallengeMoves() throws Exception {
        // Initialize the database
        challengeMovesRepository.saveAndFlush(challengeMoves);

        // Get all the challengeMovesList
        restChallengeMovesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(challengeMoves.getId().intValue())))
            .andExpect(jsonPath("$.[*].moveDate").value(hasItem(DEFAULT_MOVE_DATE.toString())));
    }

    @Test
    @Transactional
    void getChallengeMoves() throws Exception {
        // Initialize the database
        challengeMovesRepository.saveAndFlush(challengeMoves);

        // Get the challengeMoves
        restChallengeMovesMockMvc
            .perform(get(ENTITY_API_URL_ID, challengeMoves.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(challengeMoves.getId().intValue()))
            .andExpect(jsonPath("$.moveDate").value(DEFAULT_MOVE_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingChallengeMoves() throws Exception {
        // Get the challengeMoves
        restChallengeMovesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingChallengeMoves() throws Exception {
        // Initialize the database
        challengeMovesRepository.saveAndFlush(challengeMoves);

        int databaseSizeBeforeUpdate = challengeMovesRepository.findAll().size();

        // Update the challengeMoves
        ChallengeMoves updatedChallengeMoves = challengeMovesRepository.findById(challengeMoves.getId()).get();
        // Disconnect from session so that the updates on updatedChallengeMoves are not directly saved in db
        em.detach(updatedChallengeMoves);
        updatedChallengeMoves.moveDate(UPDATED_MOVE_DATE);

        restChallengeMovesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedChallengeMoves.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedChallengeMoves))
            )
            .andExpect(status().isOk());

        // Validate the ChallengeMoves in the database
        List<ChallengeMoves> challengeMovesList = challengeMovesRepository.findAll();
        assertThat(challengeMovesList).hasSize(databaseSizeBeforeUpdate);
        ChallengeMoves testChallengeMoves = challengeMovesList.get(challengeMovesList.size() - 1);
        assertThat(testChallengeMoves.getMoveDate()).isEqualTo(UPDATED_MOVE_DATE);
    }

    @Test
    @Transactional
    void putNonExistingChallengeMoves() throws Exception {
        int databaseSizeBeforeUpdate = challengeMovesRepository.findAll().size();
        challengeMoves.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChallengeMovesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, challengeMoves.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(challengeMoves))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChallengeMoves in the database
        List<ChallengeMoves> challengeMovesList = challengeMovesRepository.findAll();
        assertThat(challengeMovesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchChallengeMoves() throws Exception {
        int databaseSizeBeforeUpdate = challengeMovesRepository.findAll().size();
        challengeMoves.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChallengeMovesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(challengeMoves))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChallengeMoves in the database
        List<ChallengeMoves> challengeMovesList = challengeMovesRepository.findAll();
        assertThat(challengeMovesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamChallengeMoves() throws Exception {
        int databaseSizeBeforeUpdate = challengeMovesRepository.findAll().size();
        challengeMoves.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChallengeMovesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(challengeMoves)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChallengeMoves in the database
        List<ChallengeMoves> challengeMovesList = challengeMovesRepository.findAll();
        assertThat(challengeMovesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateChallengeMovesWithPatch() throws Exception {
        // Initialize the database
        challengeMovesRepository.saveAndFlush(challengeMoves);

        int databaseSizeBeforeUpdate = challengeMovesRepository.findAll().size();

        // Update the challengeMoves using partial update
        ChallengeMoves partialUpdatedChallengeMoves = new ChallengeMoves();
        partialUpdatedChallengeMoves.setId(challengeMoves.getId());

        restChallengeMovesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChallengeMoves.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChallengeMoves))
            )
            .andExpect(status().isOk());

        // Validate the ChallengeMoves in the database
        List<ChallengeMoves> challengeMovesList = challengeMovesRepository.findAll();
        assertThat(challengeMovesList).hasSize(databaseSizeBeforeUpdate);
        ChallengeMoves testChallengeMoves = challengeMovesList.get(challengeMovesList.size() - 1);
        assertThat(testChallengeMoves.getMoveDate()).isEqualTo(DEFAULT_MOVE_DATE);
    }

    @Test
    @Transactional
    void fullUpdateChallengeMovesWithPatch() throws Exception {
        // Initialize the database
        challengeMovesRepository.saveAndFlush(challengeMoves);

        int databaseSizeBeforeUpdate = challengeMovesRepository.findAll().size();

        // Update the challengeMoves using partial update
        ChallengeMoves partialUpdatedChallengeMoves = new ChallengeMoves();
        partialUpdatedChallengeMoves.setId(challengeMoves.getId());

        partialUpdatedChallengeMoves.moveDate(UPDATED_MOVE_DATE);

        restChallengeMovesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChallengeMoves.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChallengeMoves))
            )
            .andExpect(status().isOk());

        // Validate the ChallengeMoves in the database
        List<ChallengeMoves> challengeMovesList = challengeMovesRepository.findAll();
        assertThat(challengeMovesList).hasSize(databaseSizeBeforeUpdate);
        ChallengeMoves testChallengeMoves = challengeMovesList.get(challengeMovesList.size() - 1);
        assertThat(testChallengeMoves.getMoveDate()).isEqualTo(UPDATED_MOVE_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingChallengeMoves() throws Exception {
        int databaseSizeBeforeUpdate = challengeMovesRepository.findAll().size();
        challengeMoves.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChallengeMovesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, challengeMoves.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(challengeMoves))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChallengeMoves in the database
        List<ChallengeMoves> challengeMovesList = challengeMovesRepository.findAll();
        assertThat(challengeMovesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchChallengeMoves() throws Exception {
        int databaseSizeBeforeUpdate = challengeMovesRepository.findAll().size();
        challengeMoves.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChallengeMovesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(challengeMoves))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChallengeMoves in the database
        List<ChallengeMoves> challengeMovesList = challengeMovesRepository.findAll();
        assertThat(challengeMovesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamChallengeMoves() throws Exception {
        int databaseSizeBeforeUpdate = challengeMovesRepository.findAll().size();
        challengeMoves.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChallengeMovesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(challengeMoves))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChallengeMoves in the database
        List<ChallengeMoves> challengeMovesList = challengeMovesRepository.findAll();
        assertThat(challengeMovesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteChallengeMoves() throws Exception {
        // Initialize the database
        challengeMovesRepository.saveAndFlush(challengeMoves);

        int databaseSizeBeforeDelete = challengeMovesRepository.findAll().size();

        // Delete the challengeMoves
        restChallengeMovesMockMvc
            .perform(delete(ENTITY_API_URL_ID, challengeMoves.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ChallengeMoves> challengeMovesList = challengeMovesRepository.findAll();
        assertThat(challengeMovesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
