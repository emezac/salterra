package com.sturdy.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sturdy.myapp.IntegrationTest;
import com.sturdy.myapp.domain.ProfileGameStatus;
import com.sturdy.myapp.repository.ProfileGameStatusRepository;
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
 * Integration tests for the {@link ProfileGameStatusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProfileGameStatusResourceIT {

    private static final Instant DEFAULT_MOVE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MOVE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_LAST_ROOM_VISITED = 1L;
    private static final Long UPDATED_LAST_ROOM_VISITED = 2L;

    private static final String ENTITY_API_URL = "/api/profile-game-statuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProfileGameStatusRepository profileGameStatusRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProfileGameStatusMockMvc;

    private ProfileGameStatus profileGameStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProfileGameStatus createEntity(EntityManager em) {
        ProfileGameStatus profileGameStatus = new ProfileGameStatus()
            .moveDate(DEFAULT_MOVE_DATE)
            .lastRoomVisited(DEFAULT_LAST_ROOM_VISITED);
        return profileGameStatus;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProfileGameStatus createUpdatedEntity(EntityManager em) {
        ProfileGameStatus profileGameStatus = new ProfileGameStatus()
            .moveDate(UPDATED_MOVE_DATE)
            .lastRoomVisited(UPDATED_LAST_ROOM_VISITED);
        return profileGameStatus;
    }

    @BeforeEach
    public void initTest() {
        profileGameStatus = createEntity(em);
    }

    @Test
    @Transactional
    void createProfileGameStatus() throws Exception {
        int databaseSizeBeforeCreate = profileGameStatusRepository.findAll().size();
        // Create the ProfileGameStatus
        restProfileGameStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profileGameStatus))
            )
            .andExpect(status().isCreated());

        // Validate the ProfileGameStatus in the database
        List<ProfileGameStatus> profileGameStatusList = profileGameStatusRepository.findAll();
        assertThat(profileGameStatusList).hasSize(databaseSizeBeforeCreate + 1);
        ProfileGameStatus testProfileGameStatus = profileGameStatusList.get(profileGameStatusList.size() - 1);
        assertThat(testProfileGameStatus.getMoveDate()).isEqualTo(DEFAULT_MOVE_DATE);
        assertThat(testProfileGameStatus.getLastRoomVisited()).isEqualTo(DEFAULT_LAST_ROOM_VISITED);
    }

    @Test
    @Transactional
    void createProfileGameStatusWithExistingId() throws Exception {
        // Create the ProfileGameStatus with an existing ID
        profileGameStatus.setId(1L);

        int databaseSizeBeforeCreate = profileGameStatusRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfileGameStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profileGameStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProfileGameStatus in the database
        List<ProfileGameStatus> profileGameStatusList = profileGameStatusRepository.findAll();
        assertThat(profileGameStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProfileGameStatuses() throws Exception {
        // Initialize the database
        profileGameStatusRepository.saveAndFlush(profileGameStatus);

        // Get all the profileGameStatusList
        restProfileGameStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profileGameStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].moveDate").value(hasItem(DEFAULT_MOVE_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastRoomVisited").value(hasItem(DEFAULT_LAST_ROOM_VISITED.intValue())));
    }

    @Test
    @Transactional
    void getProfileGameStatus() throws Exception {
        // Initialize the database
        profileGameStatusRepository.saveAndFlush(profileGameStatus);

        // Get the profileGameStatus
        restProfileGameStatusMockMvc
            .perform(get(ENTITY_API_URL_ID, profileGameStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(profileGameStatus.getId().intValue()))
            .andExpect(jsonPath("$.moveDate").value(DEFAULT_MOVE_DATE.toString()))
            .andExpect(jsonPath("$.lastRoomVisited").value(DEFAULT_LAST_ROOM_VISITED.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingProfileGameStatus() throws Exception {
        // Get the profileGameStatus
        restProfileGameStatusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProfileGameStatus() throws Exception {
        // Initialize the database
        profileGameStatusRepository.saveAndFlush(profileGameStatus);

        int databaseSizeBeforeUpdate = profileGameStatusRepository.findAll().size();

        // Update the profileGameStatus
        ProfileGameStatus updatedProfileGameStatus = profileGameStatusRepository.findById(profileGameStatus.getId()).get();
        // Disconnect from session so that the updates on updatedProfileGameStatus are not directly saved in db
        em.detach(updatedProfileGameStatus);
        updatedProfileGameStatus.moveDate(UPDATED_MOVE_DATE).lastRoomVisited(UPDATED_LAST_ROOM_VISITED);

        restProfileGameStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProfileGameStatus.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProfileGameStatus))
            )
            .andExpect(status().isOk());

        // Validate the ProfileGameStatus in the database
        List<ProfileGameStatus> profileGameStatusList = profileGameStatusRepository.findAll();
        assertThat(profileGameStatusList).hasSize(databaseSizeBeforeUpdate);
        ProfileGameStatus testProfileGameStatus = profileGameStatusList.get(profileGameStatusList.size() - 1);
        assertThat(testProfileGameStatus.getMoveDate()).isEqualTo(UPDATED_MOVE_DATE);
        assertThat(testProfileGameStatus.getLastRoomVisited()).isEqualTo(UPDATED_LAST_ROOM_VISITED);
    }

    @Test
    @Transactional
    void putNonExistingProfileGameStatus() throws Exception {
        int databaseSizeBeforeUpdate = profileGameStatusRepository.findAll().size();
        profileGameStatus.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfileGameStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, profileGameStatus.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(profileGameStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProfileGameStatus in the database
        List<ProfileGameStatus> profileGameStatusList = profileGameStatusRepository.findAll();
        assertThat(profileGameStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProfileGameStatus() throws Exception {
        int databaseSizeBeforeUpdate = profileGameStatusRepository.findAll().size();
        profileGameStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfileGameStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(profileGameStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProfileGameStatus in the database
        List<ProfileGameStatus> profileGameStatusList = profileGameStatusRepository.findAll();
        assertThat(profileGameStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProfileGameStatus() throws Exception {
        int databaseSizeBeforeUpdate = profileGameStatusRepository.findAll().size();
        profileGameStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfileGameStatusMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profileGameStatus))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProfileGameStatus in the database
        List<ProfileGameStatus> profileGameStatusList = profileGameStatusRepository.findAll();
        assertThat(profileGameStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProfileGameStatusWithPatch() throws Exception {
        // Initialize the database
        profileGameStatusRepository.saveAndFlush(profileGameStatus);

        int databaseSizeBeforeUpdate = profileGameStatusRepository.findAll().size();

        // Update the profileGameStatus using partial update
        ProfileGameStatus partialUpdatedProfileGameStatus = new ProfileGameStatus();
        partialUpdatedProfileGameStatus.setId(profileGameStatus.getId());

        partialUpdatedProfileGameStatus.lastRoomVisited(UPDATED_LAST_ROOM_VISITED);

        restProfileGameStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProfileGameStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProfileGameStatus))
            )
            .andExpect(status().isOk());

        // Validate the ProfileGameStatus in the database
        List<ProfileGameStatus> profileGameStatusList = profileGameStatusRepository.findAll();
        assertThat(profileGameStatusList).hasSize(databaseSizeBeforeUpdate);
        ProfileGameStatus testProfileGameStatus = profileGameStatusList.get(profileGameStatusList.size() - 1);
        assertThat(testProfileGameStatus.getMoveDate()).isEqualTo(DEFAULT_MOVE_DATE);
        assertThat(testProfileGameStatus.getLastRoomVisited()).isEqualTo(UPDATED_LAST_ROOM_VISITED);
    }

    @Test
    @Transactional
    void fullUpdateProfileGameStatusWithPatch() throws Exception {
        // Initialize the database
        profileGameStatusRepository.saveAndFlush(profileGameStatus);

        int databaseSizeBeforeUpdate = profileGameStatusRepository.findAll().size();

        // Update the profileGameStatus using partial update
        ProfileGameStatus partialUpdatedProfileGameStatus = new ProfileGameStatus();
        partialUpdatedProfileGameStatus.setId(profileGameStatus.getId());

        partialUpdatedProfileGameStatus.moveDate(UPDATED_MOVE_DATE).lastRoomVisited(UPDATED_LAST_ROOM_VISITED);

        restProfileGameStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProfileGameStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProfileGameStatus))
            )
            .andExpect(status().isOk());

        // Validate the ProfileGameStatus in the database
        List<ProfileGameStatus> profileGameStatusList = profileGameStatusRepository.findAll();
        assertThat(profileGameStatusList).hasSize(databaseSizeBeforeUpdate);
        ProfileGameStatus testProfileGameStatus = profileGameStatusList.get(profileGameStatusList.size() - 1);
        assertThat(testProfileGameStatus.getMoveDate()).isEqualTo(UPDATED_MOVE_DATE);
        assertThat(testProfileGameStatus.getLastRoomVisited()).isEqualTo(UPDATED_LAST_ROOM_VISITED);
    }

    @Test
    @Transactional
    void patchNonExistingProfileGameStatus() throws Exception {
        int databaseSizeBeforeUpdate = profileGameStatusRepository.findAll().size();
        profileGameStatus.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfileGameStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, profileGameStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(profileGameStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProfileGameStatus in the database
        List<ProfileGameStatus> profileGameStatusList = profileGameStatusRepository.findAll();
        assertThat(profileGameStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProfileGameStatus() throws Exception {
        int databaseSizeBeforeUpdate = profileGameStatusRepository.findAll().size();
        profileGameStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfileGameStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(profileGameStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProfileGameStatus in the database
        List<ProfileGameStatus> profileGameStatusList = profileGameStatusRepository.findAll();
        assertThat(profileGameStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProfileGameStatus() throws Exception {
        int databaseSizeBeforeUpdate = profileGameStatusRepository.findAll().size();
        profileGameStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfileGameStatusMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(profileGameStatus))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProfileGameStatus in the database
        List<ProfileGameStatus> profileGameStatusList = profileGameStatusRepository.findAll();
        assertThat(profileGameStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProfileGameStatus() throws Exception {
        // Initialize the database
        profileGameStatusRepository.saveAndFlush(profileGameStatus);

        int databaseSizeBeforeDelete = profileGameStatusRepository.findAll().size();

        // Delete the profileGameStatus
        restProfileGameStatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, profileGameStatus.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProfileGameStatus> profileGameStatusList = profileGameStatusRepository.findAll();
        assertThat(profileGameStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
