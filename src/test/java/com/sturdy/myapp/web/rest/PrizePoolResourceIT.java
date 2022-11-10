package com.sturdy.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sturdy.myapp.IntegrationTest;
import com.sturdy.myapp.domain.PrizePool;
import com.sturdy.myapp.repository.PrizePoolRepository;
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
 * Integration tests for the {@link PrizePoolResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PrizePoolResourceIT {

    private static final String DEFAULT_PRIZE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRIZE_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/prize-pools";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PrizePoolRepository prizePoolRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPrizePoolMockMvc;

    private PrizePool prizePool;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrizePool createEntity(EntityManager em) {
        PrizePool prizePool = new PrizePool().prizeName(DEFAULT_PRIZE_NAME);
        return prizePool;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrizePool createUpdatedEntity(EntityManager em) {
        PrizePool prizePool = new PrizePool().prizeName(UPDATED_PRIZE_NAME);
        return prizePool;
    }

    @BeforeEach
    public void initTest() {
        prizePool = createEntity(em);
    }

    @Test
    @Transactional
    void createPrizePool() throws Exception {
        int databaseSizeBeforeCreate = prizePoolRepository.findAll().size();
        // Create the PrizePool
        restPrizePoolMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(prizePool)))
            .andExpect(status().isCreated());

        // Validate the PrizePool in the database
        List<PrizePool> prizePoolList = prizePoolRepository.findAll();
        assertThat(prizePoolList).hasSize(databaseSizeBeforeCreate + 1);
        PrizePool testPrizePool = prizePoolList.get(prizePoolList.size() - 1);
        assertThat(testPrizePool.getPrizeName()).isEqualTo(DEFAULT_PRIZE_NAME);
    }

    @Test
    @Transactional
    void createPrizePoolWithExistingId() throws Exception {
        // Create the PrizePool with an existing ID
        prizePool.setId(1L);

        int databaseSizeBeforeCreate = prizePoolRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrizePoolMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(prizePool)))
            .andExpect(status().isBadRequest());

        // Validate the PrizePool in the database
        List<PrizePool> prizePoolList = prizePoolRepository.findAll();
        assertThat(prizePoolList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPrizePools() throws Exception {
        // Initialize the database
        prizePoolRepository.saveAndFlush(prizePool);

        // Get all the prizePoolList
        restPrizePoolMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prizePool.getId().intValue())))
            .andExpect(jsonPath("$.[*].prizeName").value(hasItem(DEFAULT_PRIZE_NAME)));
    }

    @Test
    @Transactional
    void getPrizePool() throws Exception {
        // Initialize the database
        prizePoolRepository.saveAndFlush(prizePool);

        // Get the prizePool
        restPrizePoolMockMvc
            .perform(get(ENTITY_API_URL_ID, prizePool.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(prizePool.getId().intValue()))
            .andExpect(jsonPath("$.prizeName").value(DEFAULT_PRIZE_NAME));
    }

    @Test
    @Transactional
    void getNonExistingPrizePool() throws Exception {
        // Get the prizePool
        restPrizePoolMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPrizePool() throws Exception {
        // Initialize the database
        prizePoolRepository.saveAndFlush(prizePool);

        int databaseSizeBeforeUpdate = prizePoolRepository.findAll().size();

        // Update the prizePool
        PrizePool updatedPrizePool = prizePoolRepository.findById(prizePool.getId()).get();
        // Disconnect from session so that the updates on updatedPrizePool are not directly saved in db
        em.detach(updatedPrizePool);
        updatedPrizePool.prizeName(UPDATED_PRIZE_NAME);

        restPrizePoolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPrizePool.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPrizePool))
            )
            .andExpect(status().isOk());

        // Validate the PrizePool in the database
        List<PrizePool> prizePoolList = prizePoolRepository.findAll();
        assertThat(prizePoolList).hasSize(databaseSizeBeforeUpdate);
        PrizePool testPrizePool = prizePoolList.get(prizePoolList.size() - 1);
        assertThat(testPrizePool.getPrizeName()).isEqualTo(UPDATED_PRIZE_NAME);
    }

    @Test
    @Transactional
    void putNonExistingPrizePool() throws Exception {
        int databaseSizeBeforeUpdate = prizePoolRepository.findAll().size();
        prizePool.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrizePoolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, prizePool.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(prizePool))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrizePool in the database
        List<PrizePool> prizePoolList = prizePoolRepository.findAll();
        assertThat(prizePoolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPrizePool() throws Exception {
        int databaseSizeBeforeUpdate = prizePoolRepository.findAll().size();
        prizePool.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrizePoolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(prizePool))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrizePool in the database
        List<PrizePool> prizePoolList = prizePoolRepository.findAll();
        assertThat(prizePoolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPrizePool() throws Exception {
        int databaseSizeBeforeUpdate = prizePoolRepository.findAll().size();
        prizePool.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrizePoolMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(prizePool)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PrizePool in the database
        List<PrizePool> prizePoolList = prizePoolRepository.findAll();
        assertThat(prizePoolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePrizePoolWithPatch() throws Exception {
        // Initialize the database
        prizePoolRepository.saveAndFlush(prizePool);

        int databaseSizeBeforeUpdate = prizePoolRepository.findAll().size();

        // Update the prizePool using partial update
        PrizePool partialUpdatedPrizePool = new PrizePool();
        partialUpdatedPrizePool.setId(prizePool.getId());

        restPrizePoolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrizePool.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrizePool))
            )
            .andExpect(status().isOk());

        // Validate the PrizePool in the database
        List<PrizePool> prizePoolList = prizePoolRepository.findAll();
        assertThat(prizePoolList).hasSize(databaseSizeBeforeUpdate);
        PrizePool testPrizePool = prizePoolList.get(prizePoolList.size() - 1);
        assertThat(testPrizePool.getPrizeName()).isEqualTo(DEFAULT_PRIZE_NAME);
    }

    @Test
    @Transactional
    void fullUpdatePrizePoolWithPatch() throws Exception {
        // Initialize the database
        prizePoolRepository.saveAndFlush(prizePool);

        int databaseSizeBeforeUpdate = prizePoolRepository.findAll().size();

        // Update the prizePool using partial update
        PrizePool partialUpdatedPrizePool = new PrizePool();
        partialUpdatedPrizePool.setId(prizePool.getId());

        partialUpdatedPrizePool.prizeName(UPDATED_PRIZE_NAME);

        restPrizePoolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrizePool.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrizePool))
            )
            .andExpect(status().isOk());

        // Validate the PrizePool in the database
        List<PrizePool> prizePoolList = prizePoolRepository.findAll();
        assertThat(prizePoolList).hasSize(databaseSizeBeforeUpdate);
        PrizePool testPrizePool = prizePoolList.get(prizePoolList.size() - 1);
        assertThat(testPrizePool.getPrizeName()).isEqualTo(UPDATED_PRIZE_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingPrizePool() throws Exception {
        int databaseSizeBeforeUpdate = prizePoolRepository.findAll().size();
        prizePool.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrizePoolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, prizePool.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(prizePool))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrizePool in the database
        List<PrizePool> prizePoolList = prizePoolRepository.findAll();
        assertThat(prizePoolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPrizePool() throws Exception {
        int databaseSizeBeforeUpdate = prizePoolRepository.findAll().size();
        prizePool.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrizePoolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(prizePool))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrizePool in the database
        List<PrizePool> prizePoolList = prizePoolRepository.findAll();
        assertThat(prizePoolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPrizePool() throws Exception {
        int databaseSizeBeforeUpdate = prizePoolRepository.findAll().size();
        prizePool.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrizePoolMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(prizePool))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PrizePool in the database
        List<PrizePool> prizePoolList = prizePoolRepository.findAll();
        assertThat(prizePoolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePrizePool() throws Exception {
        // Initialize the database
        prizePoolRepository.saveAndFlush(prizePool);

        int databaseSizeBeforeDelete = prizePoolRepository.findAll().size();

        // Delete the prizePool
        restPrizePoolMockMvc
            .perform(delete(ENTITY_API_URL_ID, prizePool.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PrizePool> prizePoolList = prizePoolRepository.findAll();
        assertThat(prizePoolList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
