package com.sturdy.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sturdy.myapp.IntegrationTest;
import com.sturdy.myapp.domain.RoomConnection;
import com.sturdy.myapp.repository.RoomConnectionRepository;
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
 * Integration tests for the {@link RoomConnectionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RoomConnectionResourceIT {

    private static final Long DEFAULT_FROM_ID = 1L;
    private static final Long UPDATED_FROM_ID = 2L;

    private static final Long DEFAULT_TO_ID = 1L;
    private static final Long UPDATED_TO_ID = 2L;

    private static final String ENTITY_API_URL = "/api/room-connections";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RoomConnectionRepository roomConnectionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRoomConnectionMockMvc;

    private RoomConnection roomConnection;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RoomConnection createEntity(EntityManager em) {
        RoomConnection roomConnection = new RoomConnection().fromId(DEFAULT_FROM_ID).toId(DEFAULT_TO_ID);
        return roomConnection;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RoomConnection createUpdatedEntity(EntityManager em) {
        RoomConnection roomConnection = new RoomConnection().fromId(UPDATED_FROM_ID).toId(UPDATED_TO_ID);
        return roomConnection;
    }

    @BeforeEach
    public void initTest() {
        roomConnection = createEntity(em);
    }

    @Test
    @Transactional
    void createRoomConnection() throws Exception {
        int databaseSizeBeforeCreate = roomConnectionRepository.findAll().size();
        // Create the RoomConnection
        restRoomConnectionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roomConnection))
            )
            .andExpect(status().isCreated());

        // Validate the RoomConnection in the database
        List<RoomConnection> roomConnectionList = roomConnectionRepository.findAll();
        assertThat(roomConnectionList).hasSize(databaseSizeBeforeCreate + 1);
        RoomConnection testRoomConnection = roomConnectionList.get(roomConnectionList.size() - 1);
        assertThat(testRoomConnection.getFromId()).isEqualTo(DEFAULT_FROM_ID);
        assertThat(testRoomConnection.getToId()).isEqualTo(DEFAULT_TO_ID);
    }

    @Test
    @Transactional
    void createRoomConnectionWithExistingId() throws Exception {
        // Create the RoomConnection with an existing ID
        roomConnection.setId(1L);

        int databaseSizeBeforeCreate = roomConnectionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoomConnectionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roomConnection))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoomConnection in the database
        List<RoomConnection> roomConnectionList = roomConnectionRepository.findAll();
        assertThat(roomConnectionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRoomConnections() throws Exception {
        // Initialize the database
        roomConnectionRepository.saveAndFlush(roomConnection);

        // Get all the roomConnectionList
        restRoomConnectionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roomConnection.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromId").value(hasItem(DEFAULT_FROM_ID.intValue())))
            .andExpect(jsonPath("$.[*].toId").value(hasItem(DEFAULT_TO_ID.intValue())));
    }

    @Test
    @Transactional
    void getRoomConnection() throws Exception {
        // Initialize the database
        roomConnectionRepository.saveAndFlush(roomConnection);

        // Get the roomConnection
        restRoomConnectionMockMvc
            .perform(get(ENTITY_API_URL_ID, roomConnection.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(roomConnection.getId().intValue()))
            .andExpect(jsonPath("$.fromId").value(DEFAULT_FROM_ID.intValue()))
            .andExpect(jsonPath("$.toId").value(DEFAULT_TO_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingRoomConnection() throws Exception {
        // Get the roomConnection
        restRoomConnectionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRoomConnection() throws Exception {
        // Initialize the database
        roomConnectionRepository.saveAndFlush(roomConnection);

        int databaseSizeBeforeUpdate = roomConnectionRepository.findAll().size();

        // Update the roomConnection
        RoomConnection updatedRoomConnection = roomConnectionRepository.findById(roomConnection.getId()).get();
        // Disconnect from session so that the updates on updatedRoomConnection are not directly saved in db
        em.detach(updatedRoomConnection);
        updatedRoomConnection.fromId(UPDATED_FROM_ID).toId(UPDATED_TO_ID);

        restRoomConnectionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRoomConnection.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRoomConnection))
            )
            .andExpect(status().isOk());

        // Validate the RoomConnection in the database
        List<RoomConnection> roomConnectionList = roomConnectionRepository.findAll();
        assertThat(roomConnectionList).hasSize(databaseSizeBeforeUpdate);
        RoomConnection testRoomConnection = roomConnectionList.get(roomConnectionList.size() - 1);
        assertThat(testRoomConnection.getFromId()).isEqualTo(UPDATED_FROM_ID);
        assertThat(testRoomConnection.getToId()).isEqualTo(UPDATED_TO_ID);
    }

    @Test
    @Transactional
    void putNonExistingRoomConnection() throws Exception {
        int databaseSizeBeforeUpdate = roomConnectionRepository.findAll().size();
        roomConnection.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoomConnectionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, roomConnection.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomConnection))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoomConnection in the database
        List<RoomConnection> roomConnectionList = roomConnectionRepository.findAll();
        assertThat(roomConnectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRoomConnection() throws Exception {
        int databaseSizeBeforeUpdate = roomConnectionRepository.findAll().size();
        roomConnection.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomConnectionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomConnection))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoomConnection in the database
        List<RoomConnection> roomConnectionList = roomConnectionRepository.findAll();
        assertThat(roomConnectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRoomConnection() throws Exception {
        int databaseSizeBeforeUpdate = roomConnectionRepository.findAll().size();
        roomConnection.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomConnectionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roomConnection)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RoomConnection in the database
        List<RoomConnection> roomConnectionList = roomConnectionRepository.findAll();
        assertThat(roomConnectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRoomConnectionWithPatch() throws Exception {
        // Initialize the database
        roomConnectionRepository.saveAndFlush(roomConnection);

        int databaseSizeBeforeUpdate = roomConnectionRepository.findAll().size();

        // Update the roomConnection using partial update
        RoomConnection partialUpdatedRoomConnection = new RoomConnection();
        partialUpdatedRoomConnection.setId(roomConnection.getId());

        partialUpdatedRoomConnection.fromId(UPDATED_FROM_ID).toId(UPDATED_TO_ID);

        restRoomConnectionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoomConnection.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoomConnection))
            )
            .andExpect(status().isOk());

        // Validate the RoomConnection in the database
        List<RoomConnection> roomConnectionList = roomConnectionRepository.findAll();
        assertThat(roomConnectionList).hasSize(databaseSizeBeforeUpdate);
        RoomConnection testRoomConnection = roomConnectionList.get(roomConnectionList.size() - 1);
        assertThat(testRoomConnection.getFromId()).isEqualTo(UPDATED_FROM_ID);
        assertThat(testRoomConnection.getToId()).isEqualTo(UPDATED_TO_ID);
    }

    @Test
    @Transactional
    void fullUpdateRoomConnectionWithPatch() throws Exception {
        // Initialize the database
        roomConnectionRepository.saveAndFlush(roomConnection);

        int databaseSizeBeforeUpdate = roomConnectionRepository.findAll().size();

        // Update the roomConnection using partial update
        RoomConnection partialUpdatedRoomConnection = new RoomConnection();
        partialUpdatedRoomConnection.setId(roomConnection.getId());

        partialUpdatedRoomConnection.fromId(UPDATED_FROM_ID).toId(UPDATED_TO_ID);

        restRoomConnectionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoomConnection.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoomConnection))
            )
            .andExpect(status().isOk());

        // Validate the RoomConnection in the database
        List<RoomConnection> roomConnectionList = roomConnectionRepository.findAll();
        assertThat(roomConnectionList).hasSize(databaseSizeBeforeUpdate);
        RoomConnection testRoomConnection = roomConnectionList.get(roomConnectionList.size() - 1);
        assertThat(testRoomConnection.getFromId()).isEqualTo(UPDATED_FROM_ID);
        assertThat(testRoomConnection.getToId()).isEqualTo(UPDATED_TO_ID);
    }

    @Test
    @Transactional
    void patchNonExistingRoomConnection() throws Exception {
        int databaseSizeBeforeUpdate = roomConnectionRepository.findAll().size();
        roomConnection.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoomConnectionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, roomConnection.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roomConnection))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoomConnection in the database
        List<RoomConnection> roomConnectionList = roomConnectionRepository.findAll();
        assertThat(roomConnectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRoomConnection() throws Exception {
        int databaseSizeBeforeUpdate = roomConnectionRepository.findAll().size();
        roomConnection.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomConnectionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roomConnection))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoomConnection in the database
        List<RoomConnection> roomConnectionList = roomConnectionRepository.findAll();
        assertThat(roomConnectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRoomConnection() throws Exception {
        int databaseSizeBeforeUpdate = roomConnectionRepository.findAll().size();
        roomConnection.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomConnectionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(roomConnection))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RoomConnection in the database
        List<RoomConnection> roomConnectionList = roomConnectionRepository.findAll();
        assertThat(roomConnectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRoomConnection() throws Exception {
        // Initialize the database
        roomConnectionRepository.saveAndFlush(roomConnection);

        int databaseSizeBeforeDelete = roomConnectionRepository.findAll().size();

        // Delete the roomConnection
        restRoomConnectionMockMvc
            .perform(delete(ENTITY_API_URL_ID, roomConnection.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RoomConnection> roomConnectionList = roomConnectionRepository.findAll();
        assertThat(roomConnectionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
