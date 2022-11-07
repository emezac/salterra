package com.sturdy.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sturdy.myapp.IntegrationTest;
import com.sturdy.myapp.domain.DungeonFloors;
import com.sturdy.myapp.repository.DungeonFloorsRepository;
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
 * Integration tests for the {@link DungeonFloorsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DungeonFloorsResourceIT {

    private static final Long DEFAULT_DG_ID = 1L;
    private static final Long UPDATED_DG_ID = 2L;

    private static final Long DEFAULT_FL_ID = 1L;
    private static final Long UPDATED_FL_ID = 2L;

    private static final String ENTITY_API_URL = "/api/dungeon-floors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DungeonFloorsRepository dungeonFloorsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDungeonFloorsMockMvc;

    private DungeonFloors dungeonFloors;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DungeonFloors createEntity(EntityManager em) {
        DungeonFloors dungeonFloors = new DungeonFloors().dgId(DEFAULT_DG_ID).flId(DEFAULT_FL_ID);
        return dungeonFloors;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DungeonFloors createUpdatedEntity(EntityManager em) {
        DungeonFloors dungeonFloors = new DungeonFloors().dgId(UPDATED_DG_ID).flId(UPDATED_FL_ID);
        return dungeonFloors;
    }

    @BeforeEach
    public void initTest() {
        dungeonFloors = createEntity(em);
    }

    @Test
    @Transactional
    void createDungeonFloors() throws Exception {
        int databaseSizeBeforeCreate = dungeonFloorsRepository.findAll().size();
        // Create the DungeonFloors
        restDungeonFloorsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dungeonFloors)))
            .andExpect(status().isCreated());

        // Validate the DungeonFloors in the database
        List<DungeonFloors> dungeonFloorsList = dungeonFloorsRepository.findAll();
        assertThat(dungeonFloorsList).hasSize(databaseSizeBeforeCreate + 1);
        DungeonFloors testDungeonFloors = dungeonFloorsList.get(dungeonFloorsList.size() - 1);
        assertThat(testDungeonFloors.getDgId()).isEqualTo(DEFAULT_DG_ID);
        assertThat(testDungeonFloors.getFlId()).isEqualTo(DEFAULT_FL_ID);
    }

    @Test
    @Transactional
    void createDungeonFloorsWithExistingId() throws Exception {
        // Create the DungeonFloors with an existing ID
        dungeonFloors.setId(1L);

        int databaseSizeBeforeCreate = dungeonFloorsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDungeonFloorsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dungeonFloors)))
            .andExpect(status().isBadRequest());

        // Validate the DungeonFloors in the database
        List<DungeonFloors> dungeonFloorsList = dungeonFloorsRepository.findAll();
        assertThat(dungeonFloorsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDungeonFloors() throws Exception {
        // Initialize the database
        dungeonFloorsRepository.saveAndFlush(dungeonFloors);

        // Get all the dungeonFloorsList
        restDungeonFloorsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dungeonFloors.getId().intValue())))
            .andExpect(jsonPath("$.[*].dgId").value(hasItem(DEFAULT_DG_ID.intValue())))
            .andExpect(jsonPath("$.[*].flId").value(hasItem(DEFAULT_FL_ID.intValue())));
    }

    @Test
    @Transactional
    void getDungeonFloors() throws Exception {
        // Initialize the database
        dungeonFloorsRepository.saveAndFlush(dungeonFloors);

        // Get the dungeonFloors
        restDungeonFloorsMockMvc
            .perform(get(ENTITY_API_URL_ID, dungeonFloors.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dungeonFloors.getId().intValue()))
            .andExpect(jsonPath("$.dgId").value(DEFAULT_DG_ID.intValue()))
            .andExpect(jsonPath("$.flId").value(DEFAULT_FL_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingDungeonFloors() throws Exception {
        // Get the dungeonFloors
        restDungeonFloorsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDungeonFloors() throws Exception {
        // Initialize the database
        dungeonFloorsRepository.saveAndFlush(dungeonFloors);

        int databaseSizeBeforeUpdate = dungeonFloorsRepository.findAll().size();

        // Update the dungeonFloors
        DungeonFloors updatedDungeonFloors = dungeonFloorsRepository.findById(dungeonFloors.getId()).get();
        // Disconnect from session so that the updates on updatedDungeonFloors are not directly saved in db
        em.detach(updatedDungeonFloors);
        updatedDungeonFloors.dgId(UPDATED_DG_ID).flId(UPDATED_FL_ID);

        restDungeonFloorsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDungeonFloors.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDungeonFloors))
            )
            .andExpect(status().isOk());

        // Validate the DungeonFloors in the database
        List<DungeonFloors> dungeonFloorsList = dungeonFloorsRepository.findAll();
        assertThat(dungeonFloorsList).hasSize(databaseSizeBeforeUpdate);
        DungeonFloors testDungeonFloors = dungeonFloorsList.get(dungeonFloorsList.size() - 1);
        assertThat(testDungeonFloors.getDgId()).isEqualTo(UPDATED_DG_ID);
        assertThat(testDungeonFloors.getFlId()).isEqualTo(UPDATED_FL_ID);
    }

    @Test
    @Transactional
    void putNonExistingDungeonFloors() throws Exception {
        int databaseSizeBeforeUpdate = dungeonFloorsRepository.findAll().size();
        dungeonFloors.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDungeonFloorsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dungeonFloors.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dungeonFloors))
            )
            .andExpect(status().isBadRequest());

        // Validate the DungeonFloors in the database
        List<DungeonFloors> dungeonFloorsList = dungeonFloorsRepository.findAll();
        assertThat(dungeonFloorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDungeonFloors() throws Exception {
        int databaseSizeBeforeUpdate = dungeonFloorsRepository.findAll().size();
        dungeonFloors.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDungeonFloorsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dungeonFloors))
            )
            .andExpect(status().isBadRequest());

        // Validate the DungeonFloors in the database
        List<DungeonFloors> dungeonFloorsList = dungeonFloorsRepository.findAll();
        assertThat(dungeonFloorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDungeonFloors() throws Exception {
        int databaseSizeBeforeUpdate = dungeonFloorsRepository.findAll().size();
        dungeonFloors.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDungeonFloorsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dungeonFloors)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DungeonFloors in the database
        List<DungeonFloors> dungeonFloorsList = dungeonFloorsRepository.findAll();
        assertThat(dungeonFloorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDungeonFloorsWithPatch() throws Exception {
        // Initialize the database
        dungeonFloorsRepository.saveAndFlush(dungeonFloors);

        int databaseSizeBeforeUpdate = dungeonFloorsRepository.findAll().size();

        // Update the dungeonFloors using partial update
        DungeonFloors partialUpdatedDungeonFloors = new DungeonFloors();
        partialUpdatedDungeonFloors.setId(dungeonFloors.getId());

        partialUpdatedDungeonFloors.dgId(UPDATED_DG_ID);

        restDungeonFloorsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDungeonFloors.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDungeonFloors))
            )
            .andExpect(status().isOk());

        // Validate the DungeonFloors in the database
        List<DungeonFloors> dungeonFloorsList = dungeonFloorsRepository.findAll();
        assertThat(dungeonFloorsList).hasSize(databaseSizeBeforeUpdate);
        DungeonFloors testDungeonFloors = dungeonFloorsList.get(dungeonFloorsList.size() - 1);
        assertThat(testDungeonFloors.getDgId()).isEqualTo(UPDATED_DG_ID);
        assertThat(testDungeonFloors.getFlId()).isEqualTo(DEFAULT_FL_ID);
    }

    @Test
    @Transactional
    void fullUpdateDungeonFloorsWithPatch() throws Exception {
        // Initialize the database
        dungeonFloorsRepository.saveAndFlush(dungeonFloors);

        int databaseSizeBeforeUpdate = dungeonFloorsRepository.findAll().size();

        // Update the dungeonFloors using partial update
        DungeonFloors partialUpdatedDungeonFloors = new DungeonFloors();
        partialUpdatedDungeonFloors.setId(dungeonFloors.getId());

        partialUpdatedDungeonFloors.dgId(UPDATED_DG_ID).flId(UPDATED_FL_ID);

        restDungeonFloorsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDungeonFloors.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDungeonFloors))
            )
            .andExpect(status().isOk());

        // Validate the DungeonFloors in the database
        List<DungeonFloors> dungeonFloorsList = dungeonFloorsRepository.findAll();
        assertThat(dungeonFloorsList).hasSize(databaseSizeBeforeUpdate);
        DungeonFloors testDungeonFloors = dungeonFloorsList.get(dungeonFloorsList.size() - 1);
        assertThat(testDungeonFloors.getDgId()).isEqualTo(UPDATED_DG_ID);
        assertThat(testDungeonFloors.getFlId()).isEqualTo(UPDATED_FL_ID);
    }

    @Test
    @Transactional
    void patchNonExistingDungeonFloors() throws Exception {
        int databaseSizeBeforeUpdate = dungeonFloorsRepository.findAll().size();
        dungeonFloors.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDungeonFloorsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dungeonFloors.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dungeonFloors))
            )
            .andExpect(status().isBadRequest());

        // Validate the DungeonFloors in the database
        List<DungeonFloors> dungeonFloorsList = dungeonFloorsRepository.findAll();
        assertThat(dungeonFloorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDungeonFloors() throws Exception {
        int databaseSizeBeforeUpdate = dungeonFloorsRepository.findAll().size();
        dungeonFloors.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDungeonFloorsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dungeonFloors))
            )
            .andExpect(status().isBadRequest());

        // Validate the DungeonFloors in the database
        List<DungeonFloors> dungeonFloorsList = dungeonFloorsRepository.findAll();
        assertThat(dungeonFloorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDungeonFloors() throws Exception {
        int databaseSizeBeforeUpdate = dungeonFloorsRepository.findAll().size();
        dungeonFloors.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDungeonFloorsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dungeonFloors))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DungeonFloors in the database
        List<DungeonFloors> dungeonFloorsList = dungeonFloorsRepository.findAll();
        assertThat(dungeonFloorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDungeonFloors() throws Exception {
        // Initialize the database
        dungeonFloorsRepository.saveAndFlush(dungeonFloors);

        int databaseSizeBeforeDelete = dungeonFloorsRepository.findAll().size();

        // Delete the dungeonFloors
        restDungeonFloorsMockMvc
            .perform(delete(ENTITY_API_URL_ID, dungeonFloors.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DungeonFloors> dungeonFloorsList = dungeonFloorsRepository.findAll();
        assertThat(dungeonFloorsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
