package com.sturdy.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sturdy.myapp.IntegrationTest;
import com.sturdy.myapp.domain.Activity;
import com.sturdy.myapp.domain.enumeration.Difficulty;
import com.sturdy.myapp.domain.enumeration.FailureResult;
import com.sturdy.myapp.domain.enumeration.SkipResult;
import com.sturdy.myapp.domain.enumeration.SuccessResult;
import com.sturdy.myapp.domain.enumeration.TypeOfChallenge;
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

    private static final String DEFAULT_SUCCESS_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_SUCCESS_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_FAILURE_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_FAILURE_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_SKIP_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_SKIP_TEXT = "BBBBBBBBBB";

    private static final Difficulty DEFAULT_CHALLANGE_DIFFICULTY = Difficulty.EASY;
    private static final Difficulty UPDATED_CHALLANGE_DIFFICULTY = Difficulty.MEDIUM;

    private static final TypeOfChallenge DEFAULT_CHALLENGE_TYPE = TypeOfChallenge.STRONG;
    private static final TypeOfChallenge UPDATED_CHALLENGE_TYPE = TypeOfChallenge.SOURCEROUS;

    private static final FailureResult DEFAULT_FAILURE_RESULT = FailureResult.ONE_MOVE;
    private static final FailureResult UPDATED_FAILURE_RESULT = FailureResult.NULL;

    private static final SuccessResult DEFAULT_SUCCESS_RESULT = SuccessResult.ONE_MOVE;
    private static final SuccessResult UPDATED_SUCCESS_RESULT = SuccessResult.ONE_B;

    private static final SkipResult DEFAULT_SKIP_RESULT = SkipResult.ONE_MOVE;
    private static final SkipResult UPDATED_SKIP_RESULT = SkipResult.NULL;

    private static final String DEFAULT_S_W = "AAAAAAAAAA";
    private static final String UPDATED_S_W = "BBBBBBBBBB";

    private static final String DEFAULT_E = "AAAAAAAAAA";
    private static final String UPDATED_E = "BBBBBBBBBB";

    private static final String DEFAULT_N_W = "AAAAAAAAAA";
    private static final String UPDATED_N_W = "BBBBBBBBBB";

    private static final String DEFAULT_N = "AAAAAAAAAA";
    private static final String UPDATED_N = "BBBBBBBBBB";

    private static final String DEFAULT_S_E = "AAAAAAAAAA";
    private static final String UPDATED_S_E = "BBBBBBBBBB";

    private static final String DEFAULT_S = "AAAAAAAAAA";
    private static final String UPDATED_S = "BBBBBBBBBB";

    private static final String DEFAULT_N_E = "AAAAAAAAAA";
    private static final String UPDATED_N_E = "BBBBBBBBBB";

    private static final String DEFAULT_W = "AAAAAAAAAA";
    private static final String UPDATED_W = "BBBBBBBBBB";

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
            .successText(DEFAULT_SUCCESS_TEXT)
            .failureText(DEFAULT_FAILURE_TEXT)
            .skipText(DEFAULT_SKIP_TEXT)
            .challangeDifficulty(DEFAULT_CHALLANGE_DIFFICULTY)
            .challengeType(DEFAULT_CHALLENGE_TYPE)
            .failureResult(DEFAULT_FAILURE_RESULT)
            .successResult(DEFAULT_SUCCESS_RESULT)
            .skipResult(DEFAULT_SKIP_RESULT)
            .sW(DEFAULT_S_W)
            .e(DEFAULT_E)
            .nW(DEFAULT_N_W)
            .n(DEFAULT_N)
            .sE(DEFAULT_S_E)
            .s(DEFAULT_S)
            .nE(DEFAULT_N_E)
            .w(DEFAULT_W);
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
            .successText(UPDATED_SUCCESS_TEXT)
            .failureText(UPDATED_FAILURE_TEXT)
            .skipText(UPDATED_SKIP_TEXT)
            .challangeDifficulty(UPDATED_CHALLANGE_DIFFICULTY)
            .challengeType(UPDATED_CHALLENGE_TYPE)
            .failureResult(UPDATED_FAILURE_RESULT)
            .successResult(UPDATED_SUCCESS_RESULT)
            .skipResult(UPDATED_SKIP_RESULT)
            .sW(UPDATED_S_W)
            .e(UPDATED_E)
            .nW(UPDATED_N_W)
            .n(UPDATED_N)
            .sE(UPDATED_S_E)
            .s(UPDATED_S)
            .nE(UPDATED_N_E)
            .w(UPDATED_W);
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
        assertThat(testActivity.getSuccessText()).isEqualTo(DEFAULT_SUCCESS_TEXT);
        assertThat(testActivity.getFailureText()).isEqualTo(DEFAULT_FAILURE_TEXT);
        assertThat(testActivity.getSkipText()).isEqualTo(DEFAULT_SKIP_TEXT);
        assertThat(testActivity.getChallangeDifficulty()).isEqualTo(DEFAULT_CHALLANGE_DIFFICULTY);
        assertThat(testActivity.getChallengeType()).isEqualTo(DEFAULT_CHALLENGE_TYPE);
        assertThat(testActivity.getFailureResult()).isEqualTo(DEFAULT_FAILURE_RESULT);
        assertThat(testActivity.getSuccessResult()).isEqualTo(DEFAULT_SUCCESS_RESULT);
        assertThat(testActivity.getSkipResult()).isEqualTo(DEFAULT_SKIP_RESULT);
        assertThat(testActivity.getsW()).isEqualTo(DEFAULT_S_W);
        assertThat(testActivity.getE()).isEqualTo(DEFAULT_E);
        assertThat(testActivity.getnW()).isEqualTo(DEFAULT_N_W);
        assertThat(testActivity.getN()).isEqualTo(DEFAULT_N);
        assertThat(testActivity.getsE()).isEqualTo(DEFAULT_S_E);
        assertThat(testActivity.getS()).isEqualTo(DEFAULT_S);
        assertThat(testActivity.getnE()).isEqualTo(DEFAULT_N_E);
        assertThat(testActivity.getW()).isEqualTo(DEFAULT_W);
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
            .andExpect(jsonPath("$.[*].successText").value(hasItem(DEFAULT_SUCCESS_TEXT)))
            .andExpect(jsonPath("$.[*].failureText").value(hasItem(DEFAULT_FAILURE_TEXT)))
            .andExpect(jsonPath("$.[*].skipText").value(hasItem(DEFAULT_SKIP_TEXT)))
            .andExpect(jsonPath("$.[*].challangeDifficulty").value(hasItem(DEFAULT_CHALLANGE_DIFFICULTY.toString())))
            .andExpect(jsonPath("$.[*].challengeType").value(hasItem(DEFAULT_CHALLENGE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].failureResult").value(hasItem(DEFAULT_FAILURE_RESULT.toString())))
            .andExpect(jsonPath("$.[*].successResult").value(hasItem(DEFAULT_SUCCESS_RESULT.toString())))
            .andExpect(jsonPath("$.[*].skipResult").value(hasItem(DEFAULT_SKIP_RESULT.toString())))
            .andExpect(jsonPath("$.[*].sW").value(hasItem(DEFAULT_S_W)))
            .andExpect(jsonPath("$.[*].e").value(hasItem(DEFAULT_E)))
            .andExpect(jsonPath("$.[*].nW").value(hasItem(DEFAULT_N_W)))
            .andExpect(jsonPath("$.[*].n").value(hasItem(DEFAULT_N)))
            .andExpect(jsonPath("$.[*].sE").value(hasItem(DEFAULT_S_E)))
            .andExpect(jsonPath("$.[*].s").value(hasItem(DEFAULT_S)))
            .andExpect(jsonPath("$.[*].nE").value(hasItem(DEFAULT_N_E)))
            .andExpect(jsonPath("$.[*].w").value(hasItem(DEFAULT_W)));
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
            .andExpect(jsonPath("$.successText").value(DEFAULT_SUCCESS_TEXT))
            .andExpect(jsonPath("$.failureText").value(DEFAULT_FAILURE_TEXT))
            .andExpect(jsonPath("$.skipText").value(DEFAULT_SKIP_TEXT))
            .andExpect(jsonPath("$.challangeDifficulty").value(DEFAULT_CHALLANGE_DIFFICULTY.toString()))
            .andExpect(jsonPath("$.challengeType").value(DEFAULT_CHALLENGE_TYPE.toString()))
            .andExpect(jsonPath("$.failureResult").value(DEFAULT_FAILURE_RESULT.toString()))
            .andExpect(jsonPath("$.successResult").value(DEFAULT_SUCCESS_RESULT.toString()))
            .andExpect(jsonPath("$.skipResult").value(DEFAULT_SKIP_RESULT.toString()))
            .andExpect(jsonPath("$.sW").value(DEFAULT_S_W))
            .andExpect(jsonPath("$.e").value(DEFAULT_E))
            .andExpect(jsonPath("$.nW").value(DEFAULT_N_W))
            .andExpect(jsonPath("$.n").value(DEFAULT_N))
            .andExpect(jsonPath("$.sE").value(DEFAULT_S_E))
            .andExpect(jsonPath("$.s").value(DEFAULT_S))
            .andExpect(jsonPath("$.nE").value(DEFAULT_N_E))
            .andExpect(jsonPath("$.w").value(DEFAULT_W));
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
            .successText(UPDATED_SUCCESS_TEXT)
            .failureText(UPDATED_FAILURE_TEXT)
            .skipText(UPDATED_SKIP_TEXT)
            .challangeDifficulty(UPDATED_CHALLANGE_DIFFICULTY)
            .challengeType(UPDATED_CHALLENGE_TYPE)
            .failureResult(UPDATED_FAILURE_RESULT)
            .successResult(UPDATED_SUCCESS_RESULT)
            .skipResult(UPDATED_SKIP_RESULT)
            .sW(UPDATED_S_W)
            .e(UPDATED_E)
            .nW(UPDATED_N_W)
            .n(UPDATED_N)
            .sE(UPDATED_S_E)
            .s(UPDATED_S)
            .nE(UPDATED_N_E)
            .w(UPDATED_W);

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
        assertThat(testActivity.getSuccessText()).isEqualTo(UPDATED_SUCCESS_TEXT);
        assertThat(testActivity.getFailureText()).isEqualTo(UPDATED_FAILURE_TEXT);
        assertThat(testActivity.getSkipText()).isEqualTo(UPDATED_SKIP_TEXT);
        assertThat(testActivity.getChallangeDifficulty()).isEqualTo(UPDATED_CHALLANGE_DIFFICULTY);
        assertThat(testActivity.getChallengeType()).isEqualTo(UPDATED_CHALLENGE_TYPE);
        assertThat(testActivity.getFailureResult()).isEqualTo(UPDATED_FAILURE_RESULT);
        assertThat(testActivity.getSuccessResult()).isEqualTo(UPDATED_SUCCESS_RESULT);
        assertThat(testActivity.getSkipResult()).isEqualTo(UPDATED_SKIP_RESULT);
        assertThat(testActivity.getsW()).isEqualTo(UPDATED_S_W);
        assertThat(testActivity.getE()).isEqualTo(UPDATED_E);
        assertThat(testActivity.getnW()).isEqualTo(UPDATED_N_W);
        assertThat(testActivity.getN()).isEqualTo(UPDATED_N);
        assertThat(testActivity.getsE()).isEqualTo(UPDATED_S_E);
        assertThat(testActivity.getS()).isEqualTo(UPDATED_S);
        assertThat(testActivity.getnE()).isEqualTo(UPDATED_N_E);
        assertThat(testActivity.getW()).isEqualTo(UPDATED_W);
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
            .successText(UPDATED_SUCCESS_TEXT)
            .challangeDifficulty(UPDATED_CHALLANGE_DIFFICULTY)
            .challengeType(UPDATED_CHALLENGE_TYPE)
            .successResult(UPDATED_SUCCESS_RESULT)
            .sW(UPDATED_S_W)
            .nW(UPDATED_N_W)
            .n(UPDATED_N)
            .nE(UPDATED_N_E);

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
        assertThat(testActivity.getSuccessText()).isEqualTo(UPDATED_SUCCESS_TEXT);
        assertThat(testActivity.getFailureText()).isEqualTo(DEFAULT_FAILURE_TEXT);
        assertThat(testActivity.getSkipText()).isEqualTo(DEFAULT_SKIP_TEXT);
        assertThat(testActivity.getChallangeDifficulty()).isEqualTo(UPDATED_CHALLANGE_DIFFICULTY);
        assertThat(testActivity.getChallengeType()).isEqualTo(UPDATED_CHALLENGE_TYPE);
        assertThat(testActivity.getFailureResult()).isEqualTo(DEFAULT_FAILURE_RESULT);
        assertThat(testActivity.getSuccessResult()).isEqualTo(UPDATED_SUCCESS_RESULT);
        assertThat(testActivity.getSkipResult()).isEqualTo(DEFAULT_SKIP_RESULT);
        assertThat(testActivity.getsW()).isEqualTo(UPDATED_S_W);
        assertThat(testActivity.getE()).isEqualTo(DEFAULT_E);
        assertThat(testActivity.getnW()).isEqualTo(UPDATED_N_W);
        assertThat(testActivity.getN()).isEqualTo(UPDATED_N);
        assertThat(testActivity.getsE()).isEqualTo(DEFAULT_S_E);
        assertThat(testActivity.getS()).isEqualTo(DEFAULT_S);
        assertThat(testActivity.getnE()).isEqualTo(UPDATED_N_E);
        assertThat(testActivity.getW()).isEqualTo(DEFAULT_W);
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
            .successText(UPDATED_SUCCESS_TEXT)
            .failureText(UPDATED_FAILURE_TEXT)
            .skipText(UPDATED_SKIP_TEXT)
            .challangeDifficulty(UPDATED_CHALLANGE_DIFFICULTY)
            .challengeType(UPDATED_CHALLENGE_TYPE)
            .failureResult(UPDATED_FAILURE_RESULT)
            .successResult(UPDATED_SUCCESS_RESULT)
            .skipResult(UPDATED_SKIP_RESULT)
            .sW(UPDATED_S_W)
            .e(UPDATED_E)
            .nW(UPDATED_N_W)
            .n(UPDATED_N)
            .sE(UPDATED_S_E)
            .s(UPDATED_S)
            .nE(UPDATED_N_E)
            .w(UPDATED_W);

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
        assertThat(testActivity.getSuccessText()).isEqualTo(UPDATED_SUCCESS_TEXT);
        assertThat(testActivity.getFailureText()).isEqualTo(UPDATED_FAILURE_TEXT);
        assertThat(testActivity.getSkipText()).isEqualTo(UPDATED_SKIP_TEXT);
        assertThat(testActivity.getChallangeDifficulty()).isEqualTo(UPDATED_CHALLANGE_DIFFICULTY);
        assertThat(testActivity.getChallengeType()).isEqualTo(UPDATED_CHALLENGE_TYPE);
        assertThat(testActivity.getFailureResult()).isEqualTo(UPDATED_FAILURE_RESULT);
        assertThat(testActivity.getSuccessResult()).isEqualTo(UPDATED_SUCCESS_RESULT);
        assertThat(testActivity.getSkipResult()).isEqualTo(UPDATED_SKIP_RESULT);
        assertThat(testActivity.getsW()).isEqualTo(UPDATED_S_W);
        assertThat(testActivity.getE()).isEqualTo(UPDATED_E);
        assertThat(testActivity.getnW()).isEqualTo(UPDATED_N_W);
        assertThat(testActivity.getN()).isEqualTo(UPDATED_N);
        assertThat(testActivity.getsE()).isEqualTo(UPDATED_S_E);
        assertThat(testActivity.getS()).isEqualTo(UPDATED_S);
        assertThat(testActivity.getnE()).isEqualTo(UPDATED_N_E);
        assertThat(testActivity.getW()).isEqualTo(UPDATED_W);
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
