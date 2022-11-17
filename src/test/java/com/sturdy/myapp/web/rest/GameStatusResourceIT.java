package com.sturdy.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sturdy.myapp.IntegrationTest;
import com.sturdy.myapp.domain.GameStatus;
import com.sturdy.myapp.repository.GameStatusRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.UUID;
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
 * Integration tests for the {@link GameStatusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GameStatusResourceIT {

    private static final Instant DEFAULT_MOVE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MOVE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_LAST_ROOM_VISITED = 1L;
    private static final Long UPDATED_LAST_ROOM_VISITED = 2L;

    private static final String ENTITY_API_URL = "/api/game-statuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GameStatusRepository gameStatusRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGameStatusMockMvc;

    private GameStatus gameStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GameStatus createEntity(EntityManager em) {
        GameStatus gameStatus = new GameStatus().moveDate(DEFAULT_MOVE_DATE).lastRoomVisited(DEFAULT_LAST_ROOM_VISITED);
        return gameStatus;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GameStatus createUpdatedEntity(EntityManager em) {
        GameStatus gameStatus = new GameStatus().moveDate(UPDATED_MOVE_DATE).lastRoomVisited(UPDATED_LAST_ROOM_VISITED);
        return gameStatus;
    }

    @BeforeEach
    public void initTest() {
        gameStatus = createEntity(em);
    }

    @Test
    @Transactional
    void createGameStatus() throws Exception {
        int databaseSizeBeforeCreate = gameStatusRepository.findAll().size();
        // Create the GameStatus
        restGameStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gameStatus)))
            .andExpect(status().isCreated());

        // Validate the GameStatus in the database
        List<GameStatus> gameStatusList = gameStatusRepository.findAll();
        assertThat(gameStatusList).hasSize(databaseSizeBeforeCreate + 1);
        GameStatus testGameStatus = gameStatusList.get(gameStatusList.size() - 1);
        assertThat(testGameStatus.getMoveDate()).isEqualTo(DEFAULT_MOVE_DATE);
        assertThat(testGameStatus.getLastRoomVisited()).isEqualTo(DEFAULT_LAST_ROOM_VISITED);
    }

    @Test
    @Transactional
    void createGameStatusWithExistingId() throws Exception {
        // Create the GameStatus with an existing ID
        gameStatus.setId(1L);

        int databaseSizeBeforeCreate = gameStatusRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGameStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gameStatus)))
            .andExpect(status().isBadRequest());

        // Validate the GameStatus in the database
        List<GameStatus> gameStatusList = gameStatusRepository.findAll();
        assertThat(gameStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGameStatuses() throws Exception {
        // Initialize the database
        gameStatusRepository.saveAndFlush(gameStatus);

        // Get all the gameStatusList
        restGameStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gameStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].moveDate").value(hasItem(DEFAULT_MOVE_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastRoomVisited").value(hasItem(DEFAULT_LAST_ROOM_VISITED.intValue())));
    }

    @Test
    @Transactional
    void getGameStatus() throws Exception {
        // Initialize the database
        gameStatusRepository.saveAndFlush(gameStatus);

        // Get the gameStatus
        restGameStatusMockMvc
            .perform(get(ENTITY_API_URL_ID, gameStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gameStatus.getId().intValue()))
            .andExpect(jsonPath("$.moveDate").value(DEFAULT_MOVE_DATE.toString()))
            .andExpect(jsonPath("$.lastRoomVisited").value(DEFAULT_LAST_ROOM_VISITED.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingGameStatus() throws Exception {
        // Get the gameStatus
        restGameStatusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGameStatus() throws Exception {
        // Initialize the database
        gameStatusRepository.saveAndFlush(gameStatus);

        int databaseSizeBeforeUpdate = gameStatusRepository.findAll().size();

        // Update the gameStatus
        GameStatus updatedGameStatus = gameStatusRepository.findById(gameStatus.getId()).get();
        // Disconnect from session so that the updates on updatedGameStatus are not directly saved in db
        em.detach(updatedGameStatus);
        updatedGameStatus.moveDate(UPDATED_MOVE_DATE).lastRoomVisited(UPDATED_LAST_ROOM_VISITED);

        restGameStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGameStatus.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedGameStatus))
            )
            .andExpect(status().isOk());

        // Validate the GameStatus in the database
        List<GameStatus> gameStatusList = gameStatusRepository.findAll();
        assertThat(gameStatusList).hasSize(databaseSizeBeforeUpdate);
        GameStatus testGameStatus = gameStatusList.get(gameStatusList.size() - 1);
        assertThat(testGameStatus.getMoveDate()).isEqualTo(UPDATED_MOVE_DATE);
        assertThat(testGameStatus.getLastRoomVisited()).isEqualTo(UPDATED_LAST_ROOM_VISITED);
    }

    @Test
    @Transactional
    void putNonExistingGameStatus() throws Exception {
        int databaseSizeBeforeUpdate = gameStatusRepository.findAll().size();
        gameStatus.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGameStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gameStatus.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gameStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the GameStatus in the database
        List<GameStatus> gameStatusList = gameStatusRepository.findAll();
        assertThat(gameStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGameStatus() throws Exception {
        int databaseSizeBeforeUpdate = gameStatusRepository.findAll().size();
        gameStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGameStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gameStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the GameStatus in the database
        List<GameStatus> gameStatusList = gameStatusRepository.findAll();
        assertThat(gameStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGameStatus() throws Exception {
        int databaseSizeBeforeUpdate = gameStatusRepository.findAll().size();
        gameStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGameStatusMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gameStatus)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GameStatus in the database
        List<GameStatus> gameStatusList = gameStatusRepository.findAll();
        assertThat(gameStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGameStatusWithPatch() throws Exception {
        // Initialize the database
        gameStatusRepository.saveAndFlush(gameStatus);

        int databaseSizeBeforeUpdate = gameStatusRepository.findAll().size();

        // Update the gameStatus using partial update
        GameStatus partialUpdatedGameStatus = new GameStatus();
        partialUpdatedGameStatus.setId(gameStatus.getId());

        restGameStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGameStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGameStatus))
            )
            .andExpect(status().isOk());

        // Validate the GameStatus in the database
        List<GameStatus> gameStatusList = gameStatusRepository.findAll();
        assertThat(gameStatusList).hasSize(databaseSizeBeforeUpdate);
        GameStatus testGameStatus = gameStatusList.get(gameStatusList.size() - 1);
        assertThat(testGameStatus.getMoveDate()).isEqualTo(DEFAULT_MOVE_DATE);
        assertThat(testGameStatus.getLastRoomVisited()).isEqualTo(DEFAULT_LAST_ROOM_VISITED);
    }

    @Test
    @Transactional
    void fullUpdateGameStatusWithPatch() throws Exception {
        // Initialize the database
        gameStatusRepository.saveAndFlush(gameStatus);

        int databaseSizeBeforeUpdate = gameStatusRepository.findAll().size();

        // Update the gameStatus using partial update
        GameStatus partialUpdatedGameStatus = new GameStatus();
        partialUpdatedGameStatus.setId(gameStatus.getId());

        partialUpdatedGameStatus.moveDate(UPDATED_MOVE_DATE).lastRoomVisited(UPDATED_LAST_ROOM_VISITED);

        restGameStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGameStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGameStatus))
            )
            .andExpect(status().isOk());

        // Validate the GameStatus in the database
        List<GameStatus> gameStatusList = gameStatusRepository.findAll();
        assertThat(gameStatusList).hasSize(databaseSizeBeforeUpdate);
        GameStatus testGameStatus = gameStatusList.get(gameStatusList.size() - 1);
        assertThat(testGameStatus.getMoveDate()).isEqualTo(UPDATED_MOVE_DATE);
        assertThat(testGameStatus.getLastRoomVisited()).isEqualTo(UPDATED_LAST_ROOM_VISITED);
    }

    @Test
    @Transactional
    void patchNonExistingGameStatus() throws Exception {
        int databaseSizeBeforeUpdate = gameStatusRepository.findAll().size();
        gameStatus.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGameStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, gameStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gameStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the GameStatus in the database
        List<GameStatus> gameStatusList = gameStatusRepository.findAll();
        assertThat(gameStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGameStatus() throws Exception {
        int databaseSizeBeforeUpdate = gameStatusRepository.findAll().size();
        gameStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGameStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gameStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the GameStatus in the database
        List<GameStatus> gameStatusList = gameStatusRepository.findAll();
        assertThat(gameStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGameStatus() throws Exception {
        int databaseSizeBeforeUpdate = gameStatusRepository.findAll().size();
        gameStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGameStatusMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(gameStatus))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GameStatus in the database
        List<GameStatus> gameStatusList = gameStatusRepository.findAll();
        assertThat(gameStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGameStatus() throws Exception {
        // Initialize the database
        gameStatusRepository.saveAndFlush(gameStatus);

        int databaseSizeBeforeDelete = gameStatusRepository.findAll().size();

        // Delete the gameStatus
        restGameStatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, gameStatus.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GameStatus> gameStatusList = gameStatusRepository.findAll();
        assertThat(gameStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
