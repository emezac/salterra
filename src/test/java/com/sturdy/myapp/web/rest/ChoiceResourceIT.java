package com.sturdy.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sturdy.myapp.IntegrationTest;
import com.sturdy.myapp.domain.Choice;
import com.sturdy.myapp.repository.ChoiceRepository;
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
 * Integration tests for the {@link ChoiceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ChoiceResourceIT {

    private static final String DEFAULT_CHOICE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CHOICE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_ACTION = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/choices";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ChoiceRepository choiceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChoiceMockMvc;

    private Choice choice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Choice createEntity(EntityManager em) {
        Choice choice = new Choice().choiceName(DEFAULT_CHOICE_NAME).action(DEFAULT_ACTION).text(DEFAULT_TEXT);
        return choice;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Choice createUpdatedEntity(EntityManager em) {
        Choice choice = new Choice().choiceName(UPDATED_CHOICE_NAME).action(UPDATED_ACTION).text(UPDATED_TEXT);
        return choice;
    }

    @BeforeEach
    public void initTest() {
        choice = createEntity(em);
    }

    @Test
    @Transactional
    void createChoice() throws Exception {
        int databaseSizeBeforeCreate = choiceRepository.findAll().size();
        // Create the Choice
        restChoiceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(choice)))
            .andExpect(status().isCreated());

        // Validate the Choice in the database
        List<Choice> choiceList = choiceRepository.findAll();
        assertThat(choiceList).hasSize(databaseSizeBeforeCreate + 1);
        Choice testChoice = choiceList.get(choiceList.size() - 1);
        assertThat(testChoice.getChoiceName()).isEqualTo(DEFAULT_CHOICE_NAME);
        assertThat(testChoice.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testChoice.getText()).isEqualTo(DEFAULT_TEXT);
    }

    @Test
    @Transactional
    void createChoiceWithExistingId() throws Exception {
        // Create the Choice with an existing ID
        choice.setId(1L);

        int databaseSizeBeforeCreate = choiceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restChoiceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(choice)))
            .andExpect(status().isBadRequest());

        // Validate the Choice in the database
        List<Choice> choiceList = choiceRepository.findAll();
        assertThat(choiceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllChoices() throws Exception {
        // Initialize the database
        choiceRepository.saveAndFlush(choice);

        // Get all the choiceList
        restChoiceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(choice.getId().intValue())))
            .andExpect(jsonPath("$.[*].choiceName").value(hasItem(DEFAULT_CHOICE_NAME)))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION)))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)));
    }

    @Test
    @Transactional
    void getChoice() throws Exception {
        // Initialize the database
        choiceRepository.saveAndFlush(choice);

        // Get the choice
        restChoiceMockMvc
            .perform(get(ENTITY_API_URL_ID, choice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(choice.getId().intValue()))
            .andExpect(jsonPath("$.choiceName").value(DEFAULT_CHOICE_NAME))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT));
    }

    @Test
    @Transactional
    void getNonExistingChoice() throws Exception {
        // Get the choice
        restChoiceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingChoice() throws Exception {
        // Initialize the database
        choiceRepository.saveAndFlush(choice);

        int databaseSizeBeforeUpdate = choiceRepository.findAll().size();

        // Update the choice
        Choice updatedChoice = choiceRepository.findById(choice.getId()).get();
        // Disconnect from session so that the updates on updatedChoice are not directly saved in db
        em.detach(updatedChoice);
        updatedChoice.choiceName(UPDATED_CHOICE_NAME).action(UPDATED_ACTION).text(UPDATED_TEXT);

        restChoiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedChoice.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedChoice))
            )
            .andExpect(status().isOk());

        // Validate the Choice in the database
        List<Choice> choiceList = choiceRepository.findAll();
        assertThat(choiceList).hasSize(databaseSizeBeforeUpdate);
        Choice testChoice = choiceList.get(choiceList.size() - 1);
        assertThat(testChoice.getChoiceName()).isEqualTo(UPDATED_CHOICE_NAME);
        assertThat(testChoice.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testChoice.getText()).isEqualTo(UPDATED_TEXT);
    }

    @Test
    @Transactional
    void putNonExistingChoice() throws Exception {
        int databaseSizeBeforeUpdate = choiceRepository.findAll().size();
        choice.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChoiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, choice.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(choice))
            )
            .andExpect(status().isBadRequest());

        // Validate the Choice in the database
        List<Choice> choiceList = choiceRepository.findAll();
        assertThat(choiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchChoice() throws Exception {
        int databaseSizeBeforeUpdate = choiceRepository.findAll().size();
        choice.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChoiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(choice))
            )
            .andExpect(status().isBadRequest());

        // Validate the Choice in the database
        List<Choice> choiceList = choiceRepository.findAll();
        assertThat(choiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamChoice() throws Exception {
        int databaseSizeBeforeUpdate = choiceRepository.findAll().size();
        choice.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChoiceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(choice)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Choice in the database
        List<Choice> choiceList = choiceRepository.findAll();
        assertThat(choiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateChoiceWithPatch() throws Exception {
        // Initialize the database
        choiceRepository.saveAndFlush(choice);

        int databaseSizeBeforeUpdate = choiceRepository.findAll().size();

        // Update the choice using partial update
        Choice partialUpdatedChoice = new Choice();
        partialUpdatedChoice.setId(choice.getId());

        partialUpdatedChoice.choiceName(UPDATED_CHOICE_NAME).action(UPDATED_ACTION).text(UPDATED_TEXT);

        restChoiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChoice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChoice))
            )
            .andExpect(status().isOk());

        // Validate the Choice in the database
        List<Choice> choiceList = choiceRepository.findAll();
        assertThat(choiceList).hasSize(databaseSizeBeforeUpdate);
        Choice testChoice = choiceList.get(choiceList.size() - 1);
        assertThat(testChoice.getChoiceName()).isEqualTo(UPDATED_CHOICE_NAME);
        assertThat(testChoice.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testChoice.getText()).isEqualTo(UPDATED_TEXT);
    }

    @Test
    @Transactional
    void fullUpdateChoiceWithPatch() throws Exception {
        // Initialize the database
        choiceRepository.saveAndFlush(choice);

        int databaseSizeBeforeUpdate = choiceRepository.findAll().size();

        // Update the choice using partial update
        Choice partialUpdatedChoice = new Choice();
        partialUpdatedChoice.setId(choice.getId());

        partialUpdatedChoice.choiceName(UPDATED_CHOICE_NAME).action(UPDATED_ACTION).text(UPDATED_TEXT);

        restChoiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChoice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChoice))
            )
            .andExpect(status().isOk());

        // Validate the Choice in the database
        List<Choice> choiceList = choiceRepository.findAll();
        assertThat(choiceList).hasSize(databaseSizeBeforeUpdate);
        Choice testChoice = choiceList.get(choiceList.size() - 1);
        assertThat(testChoice.getChoiceName()).isEqualTo(UPDATED_CHOICE_NAME);
        assertThat(testChoice.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testChoice.getText()).isEqualTo(UPDATED_TEXT);
    }

    @Test
    @Transactional
    void patchNonExistingChoice() throws Exception {
        int databaseSizeBeforeUpdate = choiceRepository.findAll().size();
        choice.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChoiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, choice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(choice))
            )
            .andExpect(status().isBadRequest());

        // Validate the Choice in the database
        List<Choice> choiceList = choiceRepository.findAll();
        assertThat(choiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchChoice() throws Exception {
        int databaseSizeBeforeUpdate = choiceRepository.findAll().size();
        choice.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChoiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(choice))
            )
            .andExpect(status().isBadRequest());

        // Validate the Choice in the database
        List<Choice> choiceList = choiceRepository.findAll();
        assertThat(choiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamChoice() throws Exception {
        int databaseSizeBeforeUpdate = choiceRepository.findAll().size();
        choice.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChoiceMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(choice)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Choice in the database
        List<Choice> choiceList = choiceRepository.findAll();
        assertThat(choiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteChoice() throws Exception {
        // Initialize the database
        choiceRepository.saveAndFlush(choice);

        int databaseSizeBeforeDelete = choiceRepository.findAll().size();

        // Delete the choice
        restChoiceMockMvc
            .perform(delete(ENTITY_API_URL_ID, choice.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Choice> choiceList = choiceRepository.findAll();
        assertThat(choiceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
