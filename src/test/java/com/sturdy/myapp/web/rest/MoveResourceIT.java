package com.sturdy.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sturdy.myapp.IntegrationTest;
import com.sturdy.myapp.domain.Move;
import com.sturdy.myapp.repository.MoveRepository;
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
 * Integration tests for the {@link MoveResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MoveResourceIT {

    private static final String DEFAULT_MOVE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_MOVE_NUMBER = "BBBBBBBBBB";

    private static final Instant DEFAULT_MOVE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MOVE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/moves";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MoveRepository moveRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMoveMockMvc;

    private Move move;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Move createEntity(EntityManager em) {
        Move move = new Move().moveNumber(DEFAULT_MOVE_NUMBER).moveDate(DEFAULT_MOVE_DATE);
        return move;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Move createUpdatedEntity(EntityManager em) {
        Move move = new Move().moveNumber(UPDATED_MOVE_NUMBER).moveDate(UPDATED_MOVE_DATE);
        return move;
    }

    @BeforeEach
    public void initTest() {
        move = createEntity(em);
    }

    @Test
    @Transactional
    void createMove() throws Exception {
        int databaseSizeBeforeCreate = moveRepository.findAll().size();
        // Create the Move
        restMoveMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(move)))
            .andExpect(status().isCreated());

        // Validate the Move in the database
        List<Move> moveList = moveRepository.findAll();
        assertThat(moveList).hasSize(databaseSizeBeforeCreate + 1);
        Move testMove = moveList.get(moveList.size() - 1);
        assertThat(testMove.getMoveNumber()).isEqualTo(DEFAULT_MOVE_NUMBER);
        assertThat(testMove.getMoveDate()).isEqualTo(DEFAULT_MOVE_DATE);
    }

    @Test
    @Transactional
    void createMoveWithExistingId() throws Exception {
        // Create the Move with an existing ID
        move.setId(1L);

        int databaseSizeBeforeCreate = moveRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMoveMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(move)))
            .andExpect(status().isBadRequest());

        // Validate the Move in the database
        List<Move> moveList = moveRepository.findAll();
        assertThat(moveList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMoves() throws Exception {
        // Initialize the database
        moveRepository.saveAndFlush(move);

        // Get all the moveList
        restMoveMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(move.getId().intValue())))
            .andExpect(jsonPath("$.[*].moveNumber").value(hasItem(DEFAULT_MOVE_NUMBER)))
            .andExpect(jsonPath("$.[*].moveDate").value(hasItem(DEFAULT_MOVE_DATE.toString())));
    }

    @Test
    @Transactional
    void getMove() throws Exception {
        // Initialize the database
        moveRepository.saveAndFlush(move);

        // Get the move
        restMoveMockMvc
            .perform(get(ENTITY_API_URL_ID, move.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(move.getId().intValue()))
            .andExpect(jsonPath("$.moveNumber").value(DEFAULT_MOVE_NUMBER))
            .andExpect(jsonPath("$.moveDate").value(DEFAULT_MOVE_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMove() throws Exception {
        // Get the move
        restMoveMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMove() throws Exception {
        // Initialize the database
        moveRepository.saveAndFlush(move);

        int databaseSizeBeforeUpdate = moveRepository.findAll().size();

        // Update the move
        Move updatedMove = moveRepository.findById(move.getId()).get();
        // Disconnect from session so that the updates on updatedMove are not directly saved in db
        em.detach(updatedMove);
        updatedMove.moveNumber(UPDATED_MOVE_NUMBER).moveDate(UPDATED_MOVE_DATE);

        restMoveMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMove.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMove))
            )
            .andExpect(status().isOk());

        // Validate the Move in the database
        List<Move> moveList = moveRepository.findAll();
        assertThat(moveList).hasSize(databaseSizeBeforeUpdate);
        Move testMove = moveList.get(moveList.size() - 1);
        assertThat(testMove.getMoveNumber()).isEqualTo(UPDATED_MOVE_NUMBER);
        assertThat(testMove.getMoveDate()).isEqualTo(UPDATED_MOVE_DATE);
    }

    @Test
    @Transactional
    void putNonExistingMove() throws Exception {
        int databaseSizeBeforeUpdate = moveRepository.findAll().size();
        move.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMoveMockMvc
            .perform(
                put(ENTITY_API_URL_ID, move.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(move))
            )
            .andExpect(status().isBadRequest());

        // Validate the Move in the database
        List<Move> moveList = moveRepository.findAll();
        assertThat(moveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMove() throws Exception {
        int databaseSizeBeforeUpdate = moveRepository.findAll().size();
        move.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMoveMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(move))
            )
            .andExpect(status().isBadRequest());

        // Validate the Move in the database
        List<Move> moveList = moveRepository.findAll();
        assertThat(moveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMove() throws Exception {
        int databaseSizeBeforeUpdate = moveRepository.findAll().size();
        move.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMoveMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(move)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Move in the database
        List<Move> moveList = moveRepository.findAll();
        assertThat(moveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMoveWithPatch() throws Exception {
        // Initialize the database
        moveRepository.saveAndFlush(move);

        int databaseSizeBeforeUpdate = moveRepository.findAll().size();

        // Update the move using partial update
        Move partialUpdatedMove = new Move();
        partialUpdatedMove.setId(move.getId());

        partialUpdatedMove.moveNumber(UPDATED_MOVE_NUMBER);

        restMoveMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMove.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMove))
            )
            .andExpect(status().isOk());

        // Validate the Move in the database
        List<Move> moveList = moveRepository.findAll();
        assertThat(moveList).hasSize(databaseSizeBeforeUpdate);
        Move testMove = moveList.get(moveList.size() - 1);
        assertThat(testMove.getMoveNumber()).isEqualTo(UPDATED_MOVE_NUMBER);
        assertThat(testMove.getMoveDate()).isEqualTo(DEFAULT_MOVE_DATE);
    }

    @Test
    @Transactional
    void fullUpdateMoveWithPatch() throws Exception {
        // Initialize the database
        moveRepository.saveAndFlush(move);

        int databaseSizeBeforeUpdate = moveRepository.findAll().size();

        // Update the move using partial update
        Move partialUpdatedMove = new Move();
        partialUpdatedMove.setId(move.getId());

        partialUpdatedMove.moveNumber(UPDATED_MOVE_NUMBER).moveDate(UPDATED_MOVE_DATE);

        restMoveMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMove.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMove))
            )
            .andExpect(status().isOk());

        // Validate the Move in the database
        List<Move> moveList = moveRepository.findAll();
        assertThat(moveList).hasSize(databaseSizeBeforeUpdate);
        Move testMove = moveList.get(moveList.size() - 1);
        assertThat(testMove.getMoveNumber()).isEqualTo(UPDATED_MOVE_NUMBER);
        assertThat(testMove.getMoveDate()).isEqualTo(UPDATED_MOVE_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingMove() throws Exception {
        int databaseSizeBeforeUpdate = moveRepository.findAll().size();
        move.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMoveMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, move.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(move))
            )
            .andExpect(status().isBadRequest());

        // Validate the Move in the database
        List<Move> moveList = moveRepository.findAll();
        assertThat(moveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMove() throws Exception {
        int databaseSizeBeforeUpdate = moveRepository.findAll().size();
        move.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMoveMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(move))
            )
            .andExpect(status().isBadRequest());

        // Validate the Move in the database
        List<Move> moveList = moveRepository.findAll();
        assertThat(moveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMove() throws Exception {
        int databaseSizeBeforeUpdate = moveRepository.findAll().size();
        move.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMoveMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(move)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Move in the database
        List<Move> moveList = moveRepository.findAll();
        assertThat(moveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMove() throws Exception {
        // Initialize the database
        moveRepository.saveAndFlush(move);

        int databaseSizeBeforeDelete = moveRepository.findAll().size();

        // Delete the move
        restMoveMockMvc
            .perform(delete(ENTITY_API_URL_ID, move.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Move> moveList = moveRepository.findAll();
        assertThat(moveList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
