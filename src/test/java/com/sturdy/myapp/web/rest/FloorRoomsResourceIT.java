package com.sturdy.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sturdy.myapp.IntegrationTest;
import com.sturdy.myapp.domain.FloorRooms;
import com.sturdy.myapp.repository.FloorRoomsRepository;
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
 * Integration tests for the {@link FloorRoomsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FloorRoomsResourceIT {

    private static final String ENTITY_API_URL = "/api/floor-rooms";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FloorRoomsRepository floorRoomsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFloorRoomsMockMvc;

    private FloorRooms floorRooms;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FloorRooms createEntity(EntityManager em) {
        FloorRooms floorRooms = new FloorRooms();
        return floorRooms;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FloorRooms createUpdatedEntity(EntityManager em) {
        FloorRooms floorRooms = new FloorRooms();
        return floorRooms;
    }

    @BeforeEach
    public void initTest() {
        floorRooms = createEntity(em);
    }

    @Test
    @Transactional
    void createFloorRooms() throws Exception {
        int databaseSizeBeforeCreate = floorRoomsRepository.findAll().size();
        // Create the FloorRooms
        restFloorRoomsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(floorRooms)))
            .andExpect(status().isCreated());

        // Validate the FloorRooms in the database
        List<FloorRooms> floorRoomsList = floorRoomsRepository.findAll();
        assertThat(floorRoomsList).hasSize(databaseSizeBeforeCreate + 1);
        FloorRooms testFloorRooms = floorRoomsList.get(floorRoomsList.size() - 1);
    }

    @Test
    @Transactional
    void createFloorRoomsWithExistingId() throws Exception {
        // Create the FloorRooms with an existing ID
        floorRooms.setId(1L);

        int databaseSizeBeforeCreate = floorRoomsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFloorRoomsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(floorRooms)))
            .andExpect(status().isBadRequest());

        // Validate the FloorRooms in the database
        List<FloorRooms> floorRoomsList = floorRoomsRepository.findAll();
        assertThat(floorRoomsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFloorRooms() throws Exception {
        // Initialize the database
        floorRoomsRepository.saveAndFlush(floorRooms);

        // Get all the floorRoomsList
        restFloorRoomsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(floorRooms.getId().intValue())));
    }

    @Test
    @Transactional
    void getFloorRooms() throws Exception {
        // Initialize the database
        floorRoomsRepository.saveAndFlush(floorRooms);

        // Get the floorRooms
        restFloorRoomsMockMvc
            .perform(get(ENTITY_API_URL_ID, floorRooms.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(floorRooms.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingFloorRooms() throws Exception {
        // Get the floorRooms
        restFloorRoomsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFloorRooms() throws Exception {
        // Initialize the database
        floorRoomsRepository.saveAndFlush(floorRooms);

        int databaseSizeBeforeUpdate = floorRoomsRepository.findAll().size();

        // Update the floorRooms
        FloorRooms updatedFloorRooms = floorRoomsRepository.findById(floorRooms.getId()).get();
        // Disconnect from session so that the updates on updatedFloorRooms are not directly saved in db
        em.detach(updatedFloorRooms);

        restFloorRoomsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFloorRooms.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFloorRooms))
            )
            .andExpect(status().isOk());

        // Validate the FloorRooms in the database
        List<FloorRooms> floorRoomsList = floorRoomsRepository.findAll();
        assertThat(floorRoomsList).hasSize(databaseSizeBeforeUpdate);
        FloorRooms testFloorRooms = floorRoomsList.get(floorRoomsList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingFloorRooms() throws Exception {
        int databaseSizeBeforeUpdate = floorRoomsRepository.findAll().size();
        floorRooms.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFloorRoomsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, floorRooms.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(floorRooms))
            )
            .andExpect(status().isBadRequest());

        // Validate the FloorRooms in the database
        List<FloorRooms> floorRoomsList = floorRoomsRepository.findAll();
        assertThat(floorRoomsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFloorRooms() throws Exception {
        int databaseSizeBeforeUpdate = floorRoomsRepository.findAll().size();
        floorRooms.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFloorRoomsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(floorRooms))
            )
            .andExpect(status().isBadRequest());

        // Validate the FloorRooms in the database
        List<FloorRooms> floorRoomsList = floorRoomsRepository.findAll();
        assertThat(floorRoomsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFloorRooms() throws Exception {
        int databaseSizeBeforeUpdate = floorRoomsRepository.findAll().size();
        floorRooms.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFloorRoomsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(floorRooms)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FloorRooms in the database
        List<FloorRooms> floorRoomsList = floorRoomsRepository.findAll();
        assertThat(floorRoomsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFloorRoomsWithPatch() throws Exception {
        // Initialize the database
        floorRoomsRepository.saveAndFlush(floorRooms);

        int databaseSizeBeforeUpdate = floorRoomsRepository.findAll().size();

        // Update the floorRooms using partial update
        FloorRooms partialUpdatedFloorRooms = new FloorRooms();
        partialUpdatedFloorRooms.setId(floorRooms.getId());

        restFloorRoomsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFloorRooms.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFloorRooms))
            )
            .andExpect(status().isOk());

        // Validate the FloorRooms in the database
        List<FloorRooms> floorRoomsList = floorRoomsRepository.findAll();
        assertThat(floorRoomsList).hasSize(databaseSizeBeforeUpdate);
        FloorRooms testFloorRooms = floorRoomsList.get(floorRoomsList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateFloorRoomsWithPatch() throws Exception {
        // Initialize the database
        floorRoomsRepository.saveAndFlush(floorRooms);

        int databaseSizeBeforeUpdate = floorRoomsRepository.findAll().size();

        // Update the floorRooms using partial update
        FloorRooms partialUpdatedFloorRooms = new FloorRooms();
        partialUpdatedFloorRooms.setId(floorRooms.getId());

        restFloorRoomsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFloorRooms.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFloorRooms))
            )
            .andExpect(status().isOk());

        // Validate the FloorRooms in the database
        List<FloorRooms> floorRoomsList = floorRoomsRepository.findAll();
        assertThat(floorRoomsList).hasSize(databaseSizeBeforeUpdate);
        FloorRooms testFloorRooms = floorRoomsList.get(floorRoomsList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingFloorRooms() throws Exception {
        int databaseSizeBeforeUpdate = floorRoomsRepository.findAll().size();
        floorRooms.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFloorRoomsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, floorRooms.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(floorRooms))
            )
            .andExpect(status().isBadRequest());

        // Validate the FloorRooms in the database
        List<FloorRooms> floorRoomsList = floorRoomsRepository.findAll();
        assertThat(floorRoomsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFloorRooms() throws Exception {
        int databaseSizeBeforeUpdate = floorRoomsRepository.findAll().size();
        floorRooms.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFloorRoomsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(floorRooms))
            )
            .andExpect(status().isBadRequest());

        // Validate the FloorRooms in the database
        List<FloorRooms> floorRoomsList = floorRoomsRepository.findAll();
        assertThat(floorRoomsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFloorRooms() throws Exception {
        int databaseSizeBeforeUpdate = floorRoomsRepository.findAll().size();
        floorRooms.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFloorRoomsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(floorRooms))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FloorRooms in the database
        List<FloorRooms> floorRoomsList = floorRoomsRepository.findAll();
        assertThat(floorRoomsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFloorRooms() throws Exception {
        // Initialize the database
        floorRoomsRepository.saveAndFlush(floorRooms);

        int databaseSizeBeforeDelete = floorRoomsRepository.findAll().size();

        // Delete the floorRooms
        restFloorRoomsMockMvc
            .perform(delete(ENTITY_API_URL_ID, floorRooms.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FloorRooms> floorRoomsList = floorRoomsRepository.findAll();
        assertThat(floorRoomsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
