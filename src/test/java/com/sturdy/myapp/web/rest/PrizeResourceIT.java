package com.sturdy.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sturdy.myapp.IntegrationTest;
import com.sturdy.myapp.domain.Prize;
import com.sturdy.myapp.repository.PrizeRepository;
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
 * Integration tests for the {@link PrizeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PrizeResourceIT {

    private static final String DEFAULT_OPTIONS = "AAAAAAAAAA";
    private static final String UPDATED_OPTIONS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/prizes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PrizeRepository prizeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPrizeMockMvc;

    private Prize prize;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prize createEntity(EntityManager em) {
        Prize prize = new Prize().options(DEFAULT_OPTIONS);
        return prize;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prize createUpdatedEntity(EntityManager em) {
        Prize prize = new Prize().options(UPDATED_OPTIONS);
        return prize;
    }

    @BeforeEach
    public void initTest() {
        prize = createEntity(em);
    }

    @Test
    @Transactional
    void createPrize() throws Exception {
        int databaseSizeBeforeCreate = prizeRepository.findAll().size();
        // Create the Prize
        restPrizeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(prize)))
            .andExpect(status().isCreated());

        // Validate the Prize in the database
        List<Prize> prizeList = prizeRepository.findAll();
        assertThat(prizeList).hasSize(databaseSizeBeforeCreate + 1);
        Prize testPrize = prizeList.get(prizeList.size() - 1);
        assertThat(testPrize.getOptions()).isEqualTo(DEFAULT_OPTIONS);
    }

    @Test
    @Transactional
    void createPrizeWithExistingId() throws Exception {
        // Create the Prize with an existing ID
        prize.setId(1L);

        int databaseSizeBeforeCreate = prizeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrizeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(prize)))
            .andExpect(status().isBadRequest());

        // Validate the Prize in the database
        List<Prize> prizeList = prizeRepository.findAll();
        assertThat(prizeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPrizes() throws Exception {
        // Initialize the database
        prizeRepository.saveAndFlush(prize);

        // Get all the prizeList
        restPrizeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prize.getId().intValue())))
            .andExpect(jsonPath("$.[*].options").value(hasItem(DEFAULT_OPTIONS)));
    }

    @Test
    @Transactional
    void getPrize() throws Exception {
        // Initialize the database
        prizeRepository.saveAndFlush(prize);

        // Get the prize
        restPrizeMockMvc
            .perform(get(ENTITY_API_URL_ID, prize.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(prize.getId().intValue()))
            .andExpect(jsonPath("$.options").value(DEFAULT_OPTIONS));
    }

    @Test
    @Transactional
    void getNonExistingPrize() throws Exception {
        // Get the prize
        restPrizeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPrize() throws Exception {
        // Initialize the database
        prizeRepository.saveAndFlush(prize);

        int databaseSizeBeforeUpdate = prizeRepository.findAll().size();

        // Update the prize
        Prize updatedPrize = prizeRepository.findById(prize.getId()).get();
        // Disconnect from session so that the updates on updatedPrize are not directly saved in db
        em.detach(updatedPrize);
        updatedPrize.options(UPDATED_OPTIONS);

        restPrizeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPrize.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPrize))
            )
            .andExpect(status().isOk());

        // Validate the Prize in the database
        List<Prize> prizeList = prizeRepository.findAll();
        assertThat(prizeList).hasSize(databaseSizeBeforeUpdate);
        Prize testPrize = prizeList.get(prizeList.size() - 1);
        assertThat(testPrize.getOptions()).isEqualTo(UPDATED_OPTIONS);
    }

    @Test
    @Transactional
    void putNonExistingPrize() throws Exception {
        int databaseSizeBeforeUpdate = prizeRepository.findAll().size();
        prize.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrizeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, prize.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(prize))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prize in the database
        List<Prize> prizeList = prizeRepository.findAll();
        assertThat(prizeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPrize() throws Exception {
        int databaseSizeBeforeUpdate = prizeRepository.findAll().size();
        prize.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrizeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(prize))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prize in the database
        List<Prize> prizeList = prizeRepository.findAll();
        assertThat(prizeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPrize() throws Exception {
        int databaseSizeBeforeUpdate = prizeRepository.findAll().size();
        prize.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrizeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(prize)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Prize in the database
        List<Prize> prizeList = prizeRepository.findAll();
        assertThat(prizeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePrizeWithPatch() throws Exception {
        // Initialize the database
        prizeRepository.saveAndFlush(prize);

        int databaseSizeBeforeUpdate = prizeRepository.findAll().size();

        // Update the prize using partial update
        Prize partialUpdatedPrize = new Prize();
        partialUpdatedPrize.setId(prize.getId());

        partialUpdatedPrize.options(UPDATED_OPTIONS);

        restPrizeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrize.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrize))
            )
            .andExpect(status().isOk());

        // Validate the Prize in the database
        List<Prize> prizeList = prizeRepository.findAll();
        assertThat(prizeList).hasSize(databaseSizeBeforeUpdate);
        Prize testPrize = prizeList.get(prizeList.size() - 1);
        assertThat(testPrize.getOptions()).isEqualTo(UPDATED_OPTIONS);
    }

    @Test
    @Transactional
    void fullUpdatePrizeWithPatch() throws Exception {
        // Initialize the database
        prizeRepository.saveAndFlush(prize);

        int databaseSizeBeforeUpdate = prizeRepository.findAll().size();

        // Update the prize using partial update
        Prize partialUpdatedPrize = new Prize();
        partialUpdatedPrize.setId(prize.getId());

        partialUpdatedPrize.options(UPDATED_OPTIONS);

        restPrizeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrize.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrize))
            )
            .andExpect(status().isOk());

        // Validate the Prize in the database
        List<Prize> prizeList = prizeRepository.findAll();
        assertThat(prizeList).hasSize(databaseSizeBeforeUpdate);
        Prize testPrize = prizeList.get(prizeList.size() - 1);
        assertThat(testPrize.getOptions()).isEqualTo(UPDATED_OPTIONS);
    }

    @Test
    @Transactional
    void patchNonExistingPrize() throws Exception {
        int databaseSizeBeforeUpdate = prizeRepository.findAll().size();
        prize.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrizeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, prize.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(prize))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prize in the database
        List<Prize> prizeList = prizeRepository.findAll();
        assertThat(prizeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPrize() throws Exception {
        int databaseSizeBeforeUpdate = prizeRepository.findAll().size();
        prize.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrizeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(prize))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prize in the database
        List<Prize> prizeList = prizeRepository.findAll();
        assertThat(prizeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPrize() throws Exception {
        int databaseSizeBeforeUpdate = prizeRepository.findAll().size();
        prize.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrizeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(prize)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Prize in the database
        List<Prize> prizeList = prizeRepository.findAll();
        assertThat(prizeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePrize() throws Exception {
        // Initialize the database
        prizeRepository.saveAndFlush(prize);

        int databaseSizeBeforeDelete = prizeRepository.findAll().size();

        // Delete the prize
        restPrizeMockMvc
            .perform(delete(ENTITY_API_URL_ID, prize.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Prize> prizeList = prizeRepository.findAll();
        assertThat(prizeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
