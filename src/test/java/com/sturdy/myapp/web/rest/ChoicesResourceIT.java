package com.sturdy.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sturdy.myapp.IntegrationTest;
import com.sturdy.myapp.domain.Choices;
import com.sturdy.myapp.domain.enumeration.Action;
import com.sturdy.myapp.repository.ChoicesRepository;
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
 * Integration tests for the {@link ChoicesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ChoicesResourceIT {

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final Action DEFAULT_ACTION = Action.MOVE;
    private static final Action UPDATED_ACTION = Action.PLAY;

    private static final String ENTITY_API_URL = "/api/choices";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ChoicesRepository choicesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChoicesMockMvc;

    private Choices choices;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Choices createEntity(EntityManager em) {
        Choices choices = new Choices().text(DEFAULT_TEXT).action(DEFAULT_ACTION);
        return choices;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Choices createUpdatedEntity(EntityManager em) {
        Choices choices = new Choices().text(UPDATED_TEXT).action(UPDATED_ACTION);
        return choices;
    }

    @BeforeEach
    public void initTest() {
        choices = createEntity(em);
    }

    @Test
    @Transactional
    void createChoices() throws Exception {
        int databaseSizeBeforeCreate = choicesRepository.findAll().size();
        // Create the Choices
        restChoicesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(choices)))
            .andExpect(status().isCreated());

        // Validate the Choices in the database
        List<Choices> choicesList = choicesRepository.findAll();
        assertThat(choicesList).hasSize(databaseSizeBeforeCreate + 1);
        Choices testChoices = choicesList.get(choicesList.size() - 1);
        assertThat(testChoices.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testChoices.getAction()).isEqualTo(DEFAULT_ACTION);
    }

    @Test
    @Transactional
    void createChoicesWithExistingId() throws Exception {
        // Create the Choices with an existing ID
        choices.setId(1L);

        int databaseSizeBeforeCreate = choicesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restChoicesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(choices)))
            .andExpect(status().isBadRequest());

        // Validate the Choices in the database
        List<Choices> choicesList = choicesRepository.findAll();
        assertThat(choicesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllChoices() throws Exception {
        // Initialize the database
        choicesRepository.saveAndFlush(choices);

        // Get all the choicesList
        restChoicesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(choices.getId().intValue())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION.toString())));
    }

    @Test
    @Transactional
    void getChoices() throws Exception {
        // Initialize the database
        choicesRepository.saveAndFlush(choices);

        // Get the choices
        restChoicesMockMvc
            .perform(get(ENTITY_API_URL_ID, choices.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(choices.getId().intValue()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION.toString()));
    }

    @Test
    @Transactional
    void getNonExistingChoices() throws Exception {
        // Get the choices
        restChoicesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingChoices() throws Exception {
        // Initialize the database
        choicesRepository.saveAndFlush(choices);

        int databaseSizeBeforeUpdate = choicesRepository.findAll().size();

        // Update the choices
        Choices updatedChoices = choicesRepository.findById(choices.getId()).get();
        // Disconnect from session so that the updates on updatedChoices are not directly saved in db
        em.detach(updatedChoices);
        updatedChoices.text(UPDATED_TEXT).action(UPDATED_ACTION);

        restChoicesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedChoices.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedChoices))
            )
            .andExpect(status().isOk());

        // Validate the Choices in the database
        List<Choices> choicesList = choicesRepository.findAll();
        assertThat(choicesList).hasSize(databaseSizeBeforeUpdate);
        Choices testChoices = choicesList.get(choicesList.size() - 1);
        assertThat(testChoices.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testChoices.getAction()).isEqualTo(UPDATED_ACTION);
    }

    @Test
    @Transactional
    void putNonExistingChoices() throws Exception {
        int databaseSizeBeforeUpdate = choicesRepository.findAll().size();
        choices.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChoicesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, choices.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(choices))
            )
            .andExpect(status().isBadRequest());

        // Validate the Choices in the database
        List<Choices> choicesList = choicesRepository.findAll();
        assertThat(choicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchChoices() throws Exception {
        int databaseSizeBeforeUpdate = choicesRepository.findAll().size();
        choices.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChoicesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(choices))
            )
            .andExpect(status().isBadRequest());

        // Validate the Choices in the database
        List<Choices> choicesList = choicesRepository.findAll();
        assertThat(choicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamChoices() throws Exception {
        int databaseSizeBeforeUpdate = choicesRepository.findAll().size();
        choices.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChoicesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(choices)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Choices in the database
        List<Choices> choicesList = choicesRepository.findAll();
        assertThat(choicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateChoicesWithPatch() throws Exception {
        // Initialize the database
        choicesRepository.saveAndFlush(choices);

        int databaseSizeBeforeUpdate = choicesRepository.findAll().size();

        // Update the choices using partial update
        Choices partialUpdatedChoices = new Choices();
        partialUpdatedChoices.setId(choices.getId());

        partialUpdatedChoices.text(UPDATED_TEXT).action(UPDATED_ACTION);

        restChoicesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChoices.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChoices))
            )
            .andExpect(status().isOk());

        // Validate the Choices in the database
        List<Choices> choicesList = choicesRepository.findAll();
        assertThat(choicesList).hasSize(databaseSizeBeforeUpdate);
        Choices testChoices = choicesList.get(choicesList.size() - 1);
        assertThat(testChoices.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testChoices.getAction()).isEqualTo(UPDATED_ACTION);
    }

    @Test
    @Transactional
    void fullUpdateChoicesWithPatch() throws Exception {
        // Initialize the database
        choicesRepository.saveAndFlush(choices);

        int databaseSizeBeforeUpdate = choicesRepository.findAll().size();

        // Update the choices using partial update
        Choices partialUpdatedChoices = new Choices();
        partialUpdatedChoices.setId(choices.getId());

        partialUpdatedChoices.text(UPDATED_TEXT).action(UPDATED_ACTION);

        restChoicesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChoices.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChoices))
            )
            .andExpect(status().isOk());

        // Validate the Choices in the database
        List<Choices> choicesList = choicesRepository.findAll();
        assertThat(choicesList).hasSize(databaseSizeBeforeUpdate);
        Choices testChoices = choicesList.get(choicesList.size() - 1);
        assertThat(testChoices.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testChoices.getAction()).isEqualTo(UPDATED_ACTION);
    }

    @Test
    @Transactional
    void patchNonExistingChoices() throws Exception {
        int databaseSizeBeforeUpdate = choicesRepository.findAll().size();
        choices.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChoicesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, choices.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(choices))
            )
            .andExpect(status().isBadRequest());

        // Validate the Choices in the database
        List<Choices> choicesList = choicesRepository.findAll();
        assertThat(choicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchChoices() throws Exception {
        int databaseSizeBeforeUpdate = choicesRepository.findAll().size();
        choices.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChoicesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(choices))
            )
            .andExpect(status().isBadRequest());

        // Validate the Choices in the database
        List<Choices> choicesList = choicesRepository.findAll();
        assertThat(choicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamChoices() throws Exception {
        int databaseSizeBeforeUpdate = choicesRepository.findAll().size();
        choices.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChoicesMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(choices)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Choices in the database
        List<Choices> choicesList = choicesRepository.findAll();
        assertThat(choicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteChoices() throws Exception {
        // Initialize the database
        choicesRepository.saveAndFlush(choices);

        int databaseSizeBeforeDelete = choicesRepository.findAll().size();

        // Delete the choices
        restChoicesMockMvc
            .perform(delete(ENTITY_API_URL_ID, choices.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Choices> choicesList = choicesRepository.findAll();
        assertThat(choicesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
