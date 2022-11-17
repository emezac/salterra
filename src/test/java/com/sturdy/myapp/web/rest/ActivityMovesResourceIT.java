package com.sturdy.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sturdy.myapp.IntegrationTest;
import com.sturdy.myapp.domain.ActivityMoves;
import com.sturdy.myapp.repository.ActivityMovesRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ActivityMovesResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ActivityMovesResourceIT {

    private static final String ENTITY_API_URL = "/api/activity-moves";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ActivityMovesRepository activityMovesRepository;

    @Mock
    private ActivityMovesRepository activityMovesRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restActivityMovesMockMvc;

    private ActivityMoves activityMoves;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActivityMoves createEntity(EntityManager em) {
        ActivityMoves activityMoves = new ActivityMoves();
        return activityMoves;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActivityMoves createUpdatedEntity(EntityManager em) {
        ActivityMoves activityMoves = new ActivityMoves();
        return activityMoves;
    }

    @BeforeEach
    public void initTest() {
        activityMoves = createEntity(em);
    }

    @Test
    @Transactional
    void createActivityMoves() throws Exception {
        int databaseSizeBeforeCreate = activityMovesRepository.findAll().size();
        // Create the ActivityMoves
        restActivityMovesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activityMoves)))
            .andExpect(status().isCreated());

        // Validate the ActivityMoves in the database
        List<ActivityMoves> activityMovesList = activityMovesRepository.findAll();
        assertThat(activityMovesList).hasSize(databaseSizeBeforeCreate + 1);
        ActivityMoves testActivityMoves = activityMovesList.get(activityMovesList.size() - 1);
    }

    @Test
    @Transactional
    void createActivityMovesWithExistingId() throws Exception {
        // Create the ActivityMoves with an existing ID
        activityMoves.setId(1L);

        int databaseSizeBeforeCreate = activityMovesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restActivityMovesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activityMoves)))
            .andExpect(status().isBadRequest());

        // Validate the ActivityMoves in the database
        List<ActivityMoves> activityMovesList = activityMovesRepository.findAll();
        assertThat(activityMovesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllActivityMoves() throws Exception {
        // Initialize the database
        activityMovesRepository.saveAndFlush(activityMoves);

        // Get all the activityMovesList
        restActivityMovesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activityMoves.getId().intValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllActivityMovesWithEagerRelationshipsIsEnabled() throws Exception {
        when(activityMovesRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restActivityMovesMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(activityMovesRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllActivityMovesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(activityMovesRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restActivityMovesMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(activityMovesRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getActivityMoves() throws Exception {
        // Initialize the database
        activityMovesRepository.saveAndFlush(activityMoves);

        // Get the activityMoves
        restActivityMovesMockMvc
            .perform(get(ENTITY_API_URL_ID, activityMoves.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(activityMoves.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingActivityMoves() throws Exception {
        // Get the activityMoves
        restActivityMovesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingActivityMoves() throws Exception {
        // Initialize the database
        activityMovesRepository.saveAndFlush(activityMoves);

        int databaseSizeBeforeUpdate = activityMovesRepository.findAll().size();

        // Update the activityMoves
        ActivityMoves updatedActivityMoves = activityMovesRepository.findById(activityMoves.getId()).get();
        // Disconnect from session so that the updates on updatedActivityMoves are not directly saved in db
        em.detach(updatedActivityMoves);

        restActivityMovesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedActivityMoves.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedActivityMoves))
            )
            .andExpect(status().isOk());

        // Validate the ActivityMoves in the database
        List<ActivityMoves> activityMovesList = activityMovesRepository.findAll();
        assertThat(activityMovesList).hasSize(databaseSizeBeforeUpdate);
        ActivityMoves testActivityMoves = activityMovesList.get(activityMovesList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingActivityMoves() throws Exception {
        int databaseSizeBeforeUpdate = activityMovesRepository.findAll().size();
        activityMoves.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActivityMovesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, activityMoves.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(activityMoves))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActivityMoves in the database
        List<ActivityMoves> activityMovesList = activityMovesRepository.findAll();
        assertThat(activityMovesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchActivityMoves() throws Exception {
        int databaseSizeBeforeUpdate = activityMovesRepository.findAll().size();
        activityMoves.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivityMovesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(activityMoves))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActivityMoves in the database
        List<ActivityMoves> activityMovesList = activityMovesRepository.findAll();
        assertThat(activityMovesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamActivityMoves() throws Exception {
        int databaseSizeBeforeUpdate = activityMovesRepository.findAll().size();
        activityMoves.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivityMovesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activityMoves)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ActivityMoves in the database
        List<ActivityMoves> activityMovesList = activityMovesRepository.findAll();
        assertThat(activityMovesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateActivityMovesWithPatch() throws Exception {
        // Initialize the database
        activityMovesRepository.saveAndFlush(activityMoves);

        int databaseSizeBeforeUpdate = activityMovesRepository.findAll().size();

        // Update the activityMoves using partial update
        ActivityMoves partialUpdatedActivityMoves = new ActivityMoves();
        partialUpdatedActivityMoves.setId(activityMoves.getId());

        restActivityMovesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActivityMoves.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedActivityMoves))
            )
            .andExpect(status().isOk());

        // Validate the ActivityMoves in the database
        List<ActivityMoves> activityMovesList = activityMovesRepository.findAll();
        assertThat(activityMovesList).hasSize(databaseSizeBeforeUpdate);
        ActivityMoves testActivityMoves = activityMovesList.get(activityMovesList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateActivityMovesWithPatch() throws Exception {
        // Initialize the database
        activityMovesRepository.saveAndFlush(activityMoves);

        int databaseSizeBeforeUpdate = activityMovesRepository.findAll().size();

        // Update the activityMoves using partial update
        ActivityMoves partialUpdatedActivityMoves = new ActivityMoves();
        partialUpdatedActivityMoves.setId(activityMoves.getId());

        restActivityMovesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActivityMoves.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedActivityMoves))
            )
            .andExpect(status().isOk());

        // Validate the ActivityMoves in the database
        List<ActivityMoves> activityMovesList = activityMovesRepository.findAll();
        assertThat(activityMovesList).hasSize(databaseSizeBeforeUpdate);
        ActivityMoves testActivityMoves = activityMovesList.get(activityMovesList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingActivityMoves() throws Exception {
        int databaseSizeBeforeUpdate = activityMovesRepository.findAll().size();
        activityMoves.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActivityMovesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, activityMoves.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(activityMoves))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActivityMoves in the database
        List<ActivityMoves> activityMovesList = activityMovesRepository.findAll();
        assertThat(activityMovesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchActivityMoves() throws Exception {
        int databaseSizeBeforeUpdate = activityMovesRepository.findAll().size();
        activityMoves.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivityMovesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(activityMoves))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActivityMoves in the database
        List<ActivityMoves> activityMovesList = activityMovesRepository.findAll();
        assertThat(activityMovesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamActivityMoves() throws Exception {
        int databaseSizeBeforeUpdate = activityMovesRepository.findAll().size();
        activityMoves.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivityMovesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(activityMoves))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ActivityMoves in the database
        List<ActivityMoves> activityMovesList = activityMovesRepository.findAll();
        assertThat(activityMovesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteActivityMoves() throws Exception {
        // Initialize the database
        activityMovesRepository.saveAndFlush(activityMoves);

        int databaseSizeBeforeDelete = activityMovesRepository.findAll().size();

        // Delete the activityMoves
        restActivityMovesMockMvc
            .perform(delete(ENTITY_API_URL_ID, activityMoves.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ActivityMoves> activityMovesList = activityMovesRepository.findAll();
        assertThat(activityMovesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
