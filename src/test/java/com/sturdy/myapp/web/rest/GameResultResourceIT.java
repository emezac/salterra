package com.sturdy.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sturdy.myapp.IntegrationTest;
import com.sturdy.myapp.domain.GameResult;
import com.sturdy.myapp.repository.GameResultRepository;
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
 * Integration tests for the {@link GameResultResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GameResultResourceIT {

    private static final Boolean DEFAULT_WON = false;
    private static final Boolean UPDATED_WON = true;

    private static final Boolean DEFAULT_LOST = false;
    private static final Boolean UPDATED_LOST = true;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/game-results";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GameResultRepository gameResultRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGameResultMockMvc;

    private GameResult gameResult;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GameResult createEntity(EntityManager em) {
        GameResult gameResult = new GameResult().won(DEFAULT_WON).lost(DEFAULT_LOST).createdAt(DEFAULT_CREATED_AT);
        return gameResult;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GameResult createUpdatedEntity(EntityManager em) {
        GameResult gameResult = new GameResult().won(UPDATED_WON).lost(UPDATED_LOST).createdAt(UPDATED_CREATED_AT);
        return gameResult;
    }

    @BeforeEach
    public void initTest() {
        gameResult = createEntity(em);
    }

    @Test
    @Transactional
    void createGameResult() throws Exception {
        int databaseSizeBeforeCreate = gameResultRepository.findAll().size();
        // Create the GameResult
        restGameResultMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gameResult)))
            .andExpect(status().isCreated());

        // Validate the GameResult in the database
        List<GameResult> gameResultList = gameResultRepository.findAll();
        assertThat(gameResultList).hasSize(databaseSizeBeforeCreate + 1);
        GameResult testGameResult = gameResultList.get(gameResultList.size() - 1);
        assertThat(testGameResult.getWon()).isEqualTo(DEFAULT_WON);
        assertThat(testGameResult.getLost()).isEqualTo(DEFAULT_LOST);
        assertThat(testGameResult.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
    }

    @Test
    @Transactional
    void createGameResultWithExistingId() throws Exception {
        // Create the GameResult with an existing ID
        gameResult.setId(1L);

        int databaseSizeBeforeCreate = gameResultRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGameResultMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gameResult)))
            .andExpect(status().isBadRequest());

        // Validate the GameResult in the database
        List<GameResult> gameResultList = gameResultRepository.findAll();
        assertThat(gameResultList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGameResults() throws Exception {
        // Initialize the database
        gameResultRepository.saveAndFlush(gameResult);

        // Get all the gameResultList
        restGameResultMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gameResult.getId().intValue())))
            .andExpect(jsonPath("$.[*].won").value(hasItem(DEFAULT_WON.booleanValue())))
            .andExpect(jsonPath("$.[*].lost").value(hasItem(DEFAULT_LOST.booleanValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));
    }

    @Test
    @Transactional
    void getGameResult() throws Exception {
        // Initialize the database
        gameResultRepository.saveAndFlush(gameResult);

        // Get the gameResult
        restGameResultMockMvc
            .perform(get(ENTITY_API_URL_ID, gameResult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gameResult.getId().intValue()))
            .andExpect(jsonPath("$.won").value(DEFAULT_WON.booleanValue()))
            .andExpect(jsonPath("$.lost").value(DEFAULT_LOST.booleanValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingGameResult() throws Exception {
        // Get the gameResult
        restGameResultMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGameResult() throws Exception {
        // Initialize the database
        gameResultRepository.saveAndFlush(gameResult);

        int databaseSizeBeforeUpdate = gameResultRepository.findAll().size();

        // Update the gameResult
        GameResult updatedGameResult = gameResultRepository.findById(gameResult.getId()).get();
        // Disconnect from session so that the updates on updatedGameResult are not directly saved in db
        em.detach(updatedGameResult);
        updatedGameResult.won(UPDATED_WON).lost(UPDATED_LOST).createdAt(UPDATED_CREATED_AT);

        restGameResultMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGameResult.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedGameResult))
            )
            .andExpect(status().isOk());

        // Validate the GameResult in the database
        List<GameResult> gameResultList = gameResultRepository.findAll();
        assertThat(gameResultList).hasSize(databaseSizeBeforeUpdate);
        GameResult testGameResult = gameResultList.get(gameResultList.size() - 1);
        assertThat(testGameResult.getWon()).isEqualTo(UPDATED_WON);
        assertThat(testGameResult.getLost()).isEqualTo(UPDATED_LOST);
        assertThat(testGameResult.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingGameResult() throws Exception {
        int databaseSizeBeforeUpdate = gameResultRepository.findAll().size();
        gameResult.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGameResultMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gameResult.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gameResult))
            )
            .andExpect(status().isBadRequest());

        // Validate the GameResult in the database
        List<GameResult> gameResultList = gameResultRepository.findAll();
        assertThat(gameResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGameResult() throws Exception {
        int databaseSizeBeforeUpdate = gameResultRepository.findAll().size();
        gameResult.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGameResultMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gameResult))
            )
            .andExpect(status().isBadRequest());

        // Validate the GameResult in the database
        List<GameResult> gameResultList = gameResultRepository.findAll();
        assertThat(gameResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGameResult() throws Exception {
        int databaseSizeBeforeUpdate = gameResultRepository.findAll().size();
        gameResult.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGameResultMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gameResult)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GameResult in the database
        List<GameResult> gameResultList = gameResultRepository.findAll();
        assertThat(gameResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGameResultWithPatch() throws Exception {
        // Initialize the database
        gameResultRepository.saveAndFlush(gameResult);

        int databaseSizeBeforeUpdate = gameResultRepository.findAll().size();

        // Update the gameResult using partial update
        GameResult partialUpdatedGameResult = new GameResult();
        partialUpdatedGameResult.setId(gameResult.getId());

        partialUpdatedGameResult.won(UPDATED_WON).lost(UPDATED_LOST);

        restGameResultMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGameResult.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGameResult))
            )
            .andExpect(status().isOk());

        // Validate the GameResult in the database
        List<GameResult> gameResultList = gameResultRepository.findAll();
        assertThat(gameResultList).hasSize(databaseSizeBeforeUpdate);
        GameResult testGameResult = gameResultList.get(gameResultList.size() - 1);
        assertThat(testGameResult.getWon()).isEqualTo(UPDATED_WON);
        assertThat(testGameResult.getLost()).isEqualTo(UPDATED_LOST);
        assertThat(testGameResult.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateGameResultWithPatch() throws Exception {
        // Initialize the database
        gameResultRepository.saveAndFlush(gameResult);

        int databaseSizeBeforeUpdate = gameResultRepository.findAll().size();

        // Update the gameResult using partial update
        GameResult partialUpdatedGameResult = new GameResult();
        partialUpdatedGameResult.setId(gameResult.getId());

        partialUpdatedGameResult.won(UPDATED_WON).lost(UPDATED_LOST).createdAt(UPDATED_CREATED_AT);

        restGameResultMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGameResult.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGameResult))
            )
            .andExpect(status().isOk());

        // Validate the GameResult in the database
        List<GameResult> gameResultList = gameResultRepository.findAll();
        assertThat(gameResultList).hasSize(databaseSizeBeforeUpdate);
        GameResult testGameResult = gameResultList.get(gameResultList.size() - 1);
        assertThat(testGameResult.getWon()).isEqualTo(UPDATED_WON);
        assertThat(testGameResult.getLost()).isEqualTo(UPDATED_LOST);
        assertThat(testGameResult.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingGameResult() throws Exception {
        int databaseSizeBeforeUpdate = gameResultRepository.findAll().size();
        gameResult.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGameResultMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, gameResult.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gameResult))
            )
            .andExpect(status().isBadRequest());

        // Validate the GameResult in the database
        List<GameResult> gameResultList = gameResultRepository.findAll();
        assertThat(gameResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGameResult() throws Exception {
        int databaseSizeBeforeUpdate = gameResultRepository.findAll().size();
        gameResult.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGameResultMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gameResult))
            )
            .andExpect(status().isBadRequest());

        // Validate the GameResult in the database
        List<GameResult> gameResultList = gameResultRepository.findAll();
        assertThat(gameResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGameResult() throws Exception {
        int databaseSizeBeforeUpdate = gameResultRepository.findAll().size();
        gameResult.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGameResultMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(gameResult))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GameResult in the database
        List<GameResult> gameResultList = gameResultRepository.findAll();
        assertThat(gameResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGameResult() throws Exception {
        // Initialize the database
        gameResultRepository.saveAndFlush(gameResult);

        int databaseSizeBeforeDelete = gameResultRepository.findAll().size();

        // Delete the gameResult
        restGameResultMockMvc
            .perform(delete(ENTITY_API_URL_ID, gameResult.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GameResult> gameResultList = gameResultRepository.findAll();
        assertThat(gameResultList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
