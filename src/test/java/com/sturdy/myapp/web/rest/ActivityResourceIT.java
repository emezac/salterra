package com.sturdy.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sturdy.myapp.IntegrationTest;
import com.sturdy.myapp.domain.Activity;
import com.sturdy.myapp.domain.enumeration.Difficulty;
import com.sturdy.myapp.domain.enumeration.TypesOfChallenge;
import com.sturdy.myapp.repository.ActivityRepository;
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
 * Integration tests for the {@link ActivityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ActivityResourceIT {

    private static final String DEFAULT_INTRO_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_INTRO_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_SUCCESS_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_SUCCESS_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_FAILURE_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_FAILURE_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_SKIP_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_SKIP_TEXT = "BBBBBBBBBB";

    private static final TypesOfChallenge DEFAULT_CHALLENGE_NAME = TypesOfChallenge.STRONG;
    private static final TypesOfChallenge UPDATED_CHALLENGE_NAME = TypesOfChallenge.SOURCEROUS;

    private static final Difficulty DEFAULT_DIFFICULTY = Difficulty.LOW;
    private static final Difficulty UPDATED_DIFFICULTY = Difficulty.MEDIUM;

    private static final String DEFAULT_PRIZE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PRIZE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CHALLANGE_RATING = "AAAAAAAAAA";
    private static final String UPDATED_CHALLANGE_RATING = "BBBBBBBBBB";

    private static final String DEFAULT_SACRIFICE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SACRIFICE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_SKIP_RESULT = "AAAAAAAAAA";
    private static final String UPDATED_SKIP_RESULT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/activities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restActivityMockMvc;

    private Activity activity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Activity createEntity(EntityManager em) {
        Activity activity = new Activity()
            .introText(DEFAULT_INTRO_TEXT)
            .successText(DEFAULT_SUCCESS_TEXT)
            .failureText(DEFAULT_FAILURE_TEXT)
            .skipText(DEFAULT_SKIP_TEXT)
            .challengeName(DEFAULT_CHALLENGE_NAME)
            .difficulty(DEFAULT_DIFFICULTY)
            .prizeNumber(DEFAULT_PRIZE_NUMBER)
            .challangeRating(DEFAULT_CHALLANGE_RATING)
            .sacrificeNumber(DEFAULT_SACRIFICE_NUMBER)
            .skipResult(DEFAULT_SKIP_RESULT);
        return activity;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Activity createUpdatedEntity(EntityManager em) {
        Activity activity = new Activity()
            .introText(UPDATED_INTRO_TEXT)
            .successText(UPDATED_SUCCESS_TEXT)
            .failureText(UPDATED_FAILURE_TEXT)
            .skipText(UPDATED_SKIP_TEXT)
            .challengeName(UPDATED_CHALLENGE_NAME)
            .difficulty(UPDATED_DIFFICULTY)
            .prizeNumber(UPDATED_PRIZE_NUMBER)
            .challangeRating(UPDATED_CHALLANGE_RATING)
            .sacrificeNumber(UPDATED_SACRIFICE_NUMBER)
            .skipResult(UPDATED_SKIP_RESULT);
        return activity;
    }

    @BeforeEach
    public void initTest() {
        activity = createEntity(em);
    }

    @Test
    @Transactional
    void createActivity() throws Exception {
        int databaseSizeBeforeCreate = activityRepository.findAll().size();
        // Create the Activity
        restActivityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activity)))
            .andExpect(status().isCreated());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeCreate + 1);
        Activity testActivity = activityList.get(activityList.size() - 1);
        assertThat(testActivity.getIntroText()).isEqualTo(DEFAULT_INTRO_TEXT);
        assertThat(testActivity.getSuccessText()).isEqualTo(DEFAULT_SUCCESS_TEXT);
        assertThat(testActivity.getFailureText()).isEqualTo(DEFAULT_FAILURE_TEXT);
        assertThat(testActivity.getSkipText()).isEqualTo(DEFAULT_SKIP_TEXT);
        assertThat(testActivity.getChallengeName()).isEqualTo(DEFAULT_CHALLENGE_NAME);
        assertThat(testActivity.getDifficulty()).isEqualTo(DEFAULT_DIFFICULTY);
        assertThat(testActivity.getPrizeNumber()).isEqualTo(DEFAULT_PRIZE_NUMBER);
        assertThat(testActivity.getChallangeRating()).isEqualTo(DEFAULT_CHALLANGE_RATING);
        assertThat(testActivity.getSacrificeNumber()).isEqualTo(DEFAULT_SACRIFICE_NUMBER);
        assertThat(testActivity.getSkipResult()).isEqualTo(DEFAULT_SKIP_RESULT);
    }

    @Test
    @Transactional
    void createActivityWithExistingId() throws Exception {
        // Create the Activity with an existing ID
        activity.setId(1L);

        int databaseSizeBeforeCreate = activityRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restActivityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activity)))
            .andExpect(status().isBadRequest());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIntroTextIsRequired() throws Exception {
        int databaseSizeBeforeTest = activityRepository.findAll().size();
        // set the field null
        activity.setIntroText(null);

        // Create the Activity, which fails.

        restActivityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activity)))
            .andExpect(status().isBadRequest());

        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSuccessTextIsRequired() throws Exception {
        int databaseSizeBeforeTest = activityRepository.findAll().size();
        // set the field null
        activity.setSuccessText(null);

        // Create the Activity, which fails.

        restActivityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activity)))
            .andExpect(status().isBadRequest());

        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFailureTextIsRequired() throws Exception {
        int databaseSizeBeforeTest = activityRepository.findAll().size();
        // set the field null
        activity.setFailureText(null);

        // Create the Activity, which fails.

        restActivityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activity)))
            .andExpect(status().isBadRequest());

        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllActivities() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList
        restActivityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activity.getId().intValue())))
            .andExpect(jsonPath("$.[*].introText").value(hasItem(DEFAULT_INTRO_TEXT)))
            .andExpect(jsonPath("$.[*].successText").value(hasItem(DEFAULT_SUCCESS_TEXT)))
            .andExpect(jsonPath("$.[*].failureText").value(hasItem(DEFAULT_FAILURE_TEXT)))
            .andExpect(jsonPath("$.[*].skipText").value(hasItem(DEFAULT_SKIP_TEXT)))
            .andExpect(jsonPath("$.[*].challengeName").value(hasItem(DEFAULT_CHALLENGE_NAME.toString())))
            .andExpect(jsonPath("$.[*].difficulty").value(hasItem(DEFAULT_DIFFICULTY.toString())))
            .andExpect(jsonPath("$.[*].prizeNumber").value(hasItem(DEFAULT_PRIZE_NUMBER)))
            .andExpect(jsonPath("$.[*].challangeRating").value(hasItem(DEFAULT_CHALLANGE_RATING)))
            .andExpect(jsonPath("$.[*].sacrificeNumber").value(hasItem(DEFAULT_SACRIFICE_NUMBER)))
            .andExpect(jsonPath("$.[*].skipResult").value(hasItem(DEFAULT_SKIP_RESULT)));
    }

    @Test
    @Transactional
    void getActivity() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get the activity
        restActivityMockMvc
            .perform(get(ENTITY_API_URL_ID, activity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(activity.getId().intValue()))
            .andExpect(jsonPath("$.introText").value(DEFAULT_INTRO_TEXT))
            .andExpect(jsonPath("$.successText").value(DEFAULT_SUCCESS_TEXT))
            .andExpect(jsonPath("$.failureText").value(DEFAULT_FAILURE_TEXT))
            .andExpect(jsonPath("$.skipText").value(DEFAULT_SKIP_TEXT))
            .andExpect(jsonPath("$.challengeName").value(DEFAULT_CHALLENGE_NAME.toString()))
            .andExpect(jsonPath("$.difficulty").value(DEFAULT_DIFFICULTY.toString()))
            .andExpect(jsonPath("$.prizeNumber").value(DEFAULT_PRIZE_NUMBER))
            .andExpect(jsonPath("$.challangeRating").value(DEFAULT_CHALLANGE_RATING))
            .andExpect(jsonPath("$.sacrificeNumber").value(DEFAULT_SACRIFICE_NUMBER))
            .andExpect(jsonPath("$.skipResult").value(DEFAULT_SKIP_RESULT));
    }

    @Test
    @Transactional
    void getNonExistingActivity() throws Exception {
        // Get the activity
        restActivityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingActivity() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        int databaseSizeBeforeUpdate = activityRepository.findAll().size();

        // Update the activity
        Activity updatedActivity = activityRepository.findById(activity.getId()).get();
        // Disconnect from session so that the updates on updatedActivity are not directly saved in db
        em.detach(updatedActivity);
        updatedActivity
            .introText(UPDATED_INTRO_TEXT)
            .successText(UPDATED_SUCCESS_TEXT)
            .failureText(UPDATED_FAILURE_TEXT)
            .skipText(UPDATED_SKIP_TEXT)
            .challengeName(UPDATED_CHALLENGE_NAME)
            .difficulty(UPDATED_DIFFICULTY)
            .prizeNumber(UPDATED_PRIZE_NUMBER)
            .challangeRating(UPDATED_CHALLANGE_RATING)
            .sacrificeNumber(UPDATED_SACRIFICE_NUMBER)
            .skipResult(UPDATED_SKIP_RESULT);

        restActivityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedActivity.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedActivity))
            )
            .andExpect(status().isOk());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
        Activity testActivity = activityList.get(activityList.size() - 1);
        assertThat(testActivity.getIntroText()).isEqualTo(UPDATED_INTRO_TEXT);
        assertThat(testActivity.getSuccessText()).isEqualTo(UPDATED_SUCCESS_TEXT);
        assertThat(testActivity.getFailureText()).isEqualTo(UPDATED_FAILURE_TEXT);
        assertThat(testActivity.getSkipText()).isEqualTo(UPDATED_SKIP_TEXT);
        assertThat(testActivity.getChallengeName()).isEqualTo(UPDATED_CHALLENGE_NAME);
        assertThat(testActivity.getDifficulty()).isEqualTo(UPDATED_DIFFICULTY);
        assertThat(testActivity.getPrizeNumber()).isEqualTo(UPDATED_PRIZE_NUMBER);
        assertThat(testActivity.getChallangeRating()).isEqualTo(UPDATED_CHALLANGE_RATING);
        assertThat(testActivity.getSacrificeNumber()).isEqualTo(UPDATED_SACRIFICE_NUMBER);
        assertThat(testActivity.getSkipResult()).isEqualTo(UPDATED_SKIP_RESULT);
    }

    @Test
    @Transactional
    void putNonExistingActivity() throws Exception {
        int databaseSizeBeforeUpdate = activityRepository.findAll().size();
        activity.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActivityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, activity.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(activity))
            )
            .andExpect(status().isBadRequest());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchActivity() throws Exception {
        int databaseSizeBeforeUpdate = activityRepository.findAll().size();
        activity.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(activity))
            )
            .andExpect(status().isBadRequest());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamActivity() throws Exception {
        int databaseSizeBeforeUpdate = activityRepository.findAll().size();
        activity.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivityMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activity)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateActivityWithPatch() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        int databaseSizeBeforeUpdate = activityRepository.findAll().size();

        // Update the activity using partial update
        Activity partialUpdatedActivity = new Activity();
        partialUpdatedActivity.setId(activity.getId());

        partialUpdatedActivity
            .introText(UPDATED_INTRO_TEXT)
            .skipText(UPDATED_SKIP_TEXT)
            .challengeName(UPDATED_CHALLENGE_NAME)
            .prizeNumber(UPDATED_PRIZE_NUMBER)
            .sacrificeNumber(UPDATED_SACRIFICE_NUMBER);

        restActivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActivity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedActivity))
            )
            .andExpect(status().isOk());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
        Activity testActivity = activityList.get(activityList.size() - 1);
        assertThat(testActivity.getIntroText()).isEqualTo(UPDATED_INTRO_TEXT);
        assertThat(testActivity.getSuccessText()).isEqualTo(DEFAULT_SUCCESS_TEXT);
        assertThat(testActivity.getFailureText()).isEqualTo(DEFAULT_FAILURE_TEXT);
        assertThat(testActivity.getSkipText()).isEqualTo(UPDATED_SKIP_TEXT);
        assertThat(testActivity.getChallengeName()).isEqualTo(UPDATED_CHALLENGE_NAME);
        assertThat(testActivity.getDifficulty()).isEqualTo(DEFAULT_DIFFICULTY);
        assertThat(testActivity.getPrizeNumber()).isEqualTo(UPDATED_PRIZE_NUMBER);
        assertThat(testActivity.getChallangeRating()).isEqualTo(DEFAULT_CHALLANGE_RATING);
        assertThat(testActivity.getSacrificeNumber()).isEqualTo(UPDATED_SACRIFICE_NUMBER);
        assertThat(testActivity.getSkipResult()).isEqualTo(DEFAULT_SKIP_RESULT);
    }

    @Test
    @Transactional
    void fullUpdateActivityWithPatch() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        int databaseSizeBeforeUpdate = activityRepository.findAll().size();

        // Update the activity using partial update
        Activity partialUpdatedActivity = new Activity();
        partialUpdatedActivity.setId(activity.getId());

        partialUpdatedActivity
            .introText(UPDATED_INTRO_TEXT)
            .successText(UPDATED_SUCCESS_TEXT)
            .failureText(UPDATED_FAILURE_TEXT)
            .skipText(UPDATED_SKIP_TEXT)
            .challengeName(UPDATED_CHALLENGE_NAME)
            .difficulty(UPDATED_DIFFICULTY)
            .prizeNumber(UPDATED_PRIZE_NUMBER)
            .challangeRating(UPDATED_CHALLANGE_RATING)
            .sacrificeNumber(UPDATED_SACRIFICE_NUMBER)
            .skipResult(UPDATED_SKIP_RESULT);

        restActivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActivity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedActivity))
            )
            .andExpect(status().isOk());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
        Activity testActivity = activityList.get(activityList.size() - 1);
        assertThat(testActivity.getIntroText()).isEqualTo(UPDATED_INTRO_TEXT);
        assertThat(testActivity.getSuccessText()).isEqualTo(UPDATED_SUCCESS_TEXT);
        assertThat(testActivity.getFailureText()).isEqualTo(UPDATED_FAILURE_TEXT);
        assertThat(testActivity.getSkipText()).isEqualTo(UPDATED_SKIP_TEXT);
        assertThat(testActivity.getChallengeName()).isEqualTo(UPDATED_CHALLENGE_NAME);
        assertThat(testActivity.getDifficulty()).isEqualTo(UPDATED_DIFFICULTY);
        assertThat(testActivity.getPrizeNumber()).isEqualTo(UPDATED_PRIZE_NUMBER);
        assertThat(testActivity.getChallangeRating()).isEqualTo(UPDATED_CHALLANGE_RATING);
        assertThat(testActivity.getSacrificeNumber()).isEqualTo(UPDATED_SACRIFICE_NUMBER);
        assertThat(testActivity.getSkipResult()).isEqualTo(UPDATED_SKIP_RESULT);
    }

    @Test
    @Transactional
    void patchNonExistingActivity() throws Exception {
        int databaseSizeBeforeUpdate = activityRepository.findAll().size();
        activity.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, activity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(activity))
            )
            .andExpect(status().isBadRequest());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchActivity() throws Exception {
        int databaseSizeBeforeUpdate = activityRepository.findAll().size();
        activity.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(activity))
            )
            .andExpect(status().isBadRequest());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamActivity() throws Exception {
        int databaseSizeBeforeUpdate = activityRepository.findAll().size();
        activity.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivityMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(activity)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteActivity() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        int databaseSizeBeforeDelete = activityRepository.findAll().size();

        // Delete the activity
        restActivityMockMvc
            .perform(delete(ENTITY_API_URL_ID, activity.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
