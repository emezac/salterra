package com.sturdy.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sturdy.myapp.IntegrationTest;
import com.sturdy.myapp.domain.Challenge;
import com.sturdy.myapp.domain.enumeration.Difficulty;
import com.sturdy.myapp.domain.enumeration.TypesOfChallenge;
import com.sturdy.myapp.repository.ChallengeRepository;
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
 * Integration tests for the {@link ChallengeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ChallengeResourceIT {

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

    private static final String ENTITY_API_URL = "/api/challenges";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ChallengeRepository challengeRepository;

    @Mock
    private ChallengeRepository challengeRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChallengeMockMvc;

    private Challenge challenge;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Challenge createEntity(EntityManager em) {
        Challenge challenge = new Challenge()
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
        return challenge;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Challenge createUpdatedEntity(EntityManager em) {
        Challenge challenge = new Challenge()
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
        return challenge;
    }

    @BeforeEach
    public void initTest() {
        challenge = createEntity(em);
    }

    @Test
    @Transactional
    void createChallenge() throws Exception {
        int databaseSizeBeforeCreate = challengeRepository.findAll().size();
        // Create the Challenge
        restChallengeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(challenge)))
            .andExpect(status().isCreated());

        // Validate the Challenge in the database
        List<Challenge> challengeList = challengeRepository.findAll();
        assertThat(challengeList).hasSize(databaseSizeBeforeCreate + 1);
        Challenge testChallenge = challengeList.get(challengeList.size() - 1);
        assertThat(testChallenge.getIntroText()).isEqualTo(DEFAULT_INTRO_TEXT);
        assertThat(testChallenge.getSuccessText()).isEqualTo(DEFAULT_SUCCESS_TEXT);
        assertThat(testChallenge.getFailureText()).isEqualTo(DEFAULT_FAILURE_TEXT);
        assertThat(testChallenge.getSkipText()).isEqualTo(DEFAULT_SKIP_TEXT);
        assertThat(testChallenge.getChallengeName()).isEqualTo(DEFAULT_CHALLENGE_NAME);
        assertThat(testChallenge.getDifficulty()).isEqualTo(DEFAULT_DIFFICULTY);
        assertThat(testChallenge.getPrizeNumber()).isEqualTo(DEFAULT_PRIZE_NUMBER);
        assertThat(testChallenge.getChallangeRating()).isEqualTo(DEFAULT_CHALLANGE_RATING);
        assertThat(testChallenge.getSacrificeNumber()).isEqualTo(DEFAULT_SACRIFICE_NUMBER);
        assertThat(testChallenge.getSkipResult()).isEqualTo(DEFAULT_SKIP_RESULT);
    }

    @Test
    @Transactional
    void createChallengeWithExistingId() throws Exception {
        // Create the Challenge with an existing ID
        challenge.setId(1L);

        int databaseSizeBeforeCreate = challengeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restChallengeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(challenge)))
            .andExpect(status().isBadRequest());

        // Validate the Challenge in the database
        List<Challenge> challengeList = challengeRepository.findAll();
        assertThat(challengeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIntroTextIsRequired() throws Exception {
        int databaseSizeBeforeTest = challengeRepository.findAll().size();
        // set the field null
        challenge.setIntroText(null);

        // Create the Challenge, which fails.

        restChallengeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(challenge)))
            .andExpect(status().isBadRequest());

        List<Challenge> challengeList = challengeRepository.findAll();
        assertThat(challengeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSuccessTextIsRequired() throws Exception {
        int databaseSizeBeforeTest = challengeRepository.findAll().size();
        // set the field null
        challenge.setSuccessText(null);

        // Create the Challenge, which fails.

        restChallengeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(challenge)))
            .andExpect(status().isBadRequest());

        List<Challenge> challengeList = challengeRepository.findAll();
        assertThat(challengeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFailureTextIsRequired() throws Exception {
        int databaseSizeBeforeTest = challengeRepository.findAll().size();
        // set the field null
        challenge.setFailureText(null);

        // Create the Challenge, which fails.

        restChallengeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(challenge)))
            .andExpect(status().isBadRequest());

        List<Challenge> challengeList = challengeRepository.findAll();
        assertThat(challengeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllChallenges() throws Exception {
        // Initialize the database
        challengeRepository.saveAndFlush(challenge);

        // Get all the challengeList
        restChallengeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(challenge.getId().intValue())))
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

    @SuppressWarnings({ "unchecked" })
    void getAllChallengesWithEagerRelationshipsIsEnabled() throws Exception {
        when(challengeRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restChallengeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(challengeRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllChallengesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(challengeRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restChallengeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(challengeRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getChallenge() throws Exception {
        // Initialize the database
        challengeRepository.saveAndFlush(challenge);

        // Get the challenge
        restChallengeMockMvc
            .perform(get(ENTITY_API_URL_ID, challenge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(challenge.getId().intValue()))
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
    void getNonExistingChallenge() throws Exception {
        // Get the challenge
        restChallengeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingChallenge() throws Exception {
        // Initialize the database
        challengeRepository.saveAndFlush(challenge);

        int databaseSizeBeforeUpdate = challengeRepository.findAll().size();

        // Update the challenge
        Challenge updatedChallenge = challengeRepository.findById(challenge.getId()).get();
        // Disconnect from session so that the updates on updatedChallenge are not directly saved in db
        em.detach(updatedChallenge);
        updatedChallenge
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

        restChallengeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedChallenge.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedChallenge))
            )
            .andExpect(status().isOk());

        // Validate the Challenge in the database
        List<Challenge> challengeList = challengeRepository.findAll();
        assertThat(challengeList).hasSize(databaseSizeBeforeUpdate);
        Challenge testChallenge = challengeList.get(challengeList.size() - 1);
        assertThat(testChallenge.getIntroText()).isEqualTo(UPDATED_INTRO_TEXT);
        assertThat(testChallenge.getSuccessText()).isEqualTo(UPDATED_SUCCESS_TEXT);
        assertThat(testChallenge.getFailureText()).isEqualTo(UPDATED_FAILURE_TEXT);
        assertThat(testChallenge.getSkipText()).isEqualTo(UPDATED_SKIP_TEXT);
        assertThat(testChallenge.getChallengeName()).isEqualTo(UPDATED_CHALLENGE_NAME);
        assertThat(testChallenge.getDifficulty()).isEqualTo(UPDATED_DIFFICULTY);
        assertThat(testChallenge.getPrizeNumber()).isEqualTo(UPDATED_PRIZE_NUMBER);
        assertThat(testChallenge.getChallangeRating()).isEqualTo(UPDATED_CHALLANGE_RATING);
        assertThat(testChallenge.getSacrificeNumber()).isEqualTo(UPDATED_SACRIFICE_NUMBER);
        assertThat(testChallenge.getSkipResult()).isEqualTo(UPDATED_SKIP_RESULT);
    }

    @Test
    @Transactional
    void putNonExistingChallenge() throws Exception {
        int databaseSizeBeforeUpdate = challengeRepository.findAll().size();
        challenge.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChallengeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, challenge.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(challenge))
            )
            .andExpect(status().isBadRequest());

        // Validate the Challenge in the database
        List<Challenge> challengeList = challengeRepository.findAll();
        assertThat(challengeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchChallenge() throws Exception {
        int databaseSizeBeforeUpdate = challengeRepository.findAll().size();
        challenge.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChallengeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(challenge))
            )
            .andExpect(status().isBadRequest());

        // Validate the Challenge in the database
        List<Challenge> challengeList = challengeRepository.findAll();
        assertThat(challengeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamChallenge() throws Exception {
        int databaseSizeBeforeUpdate = challengeRepository.findAll().size();
        challenge.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChallengeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(challenge)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Challenge in the database
        List<Challenge> challengeList = challengeRepository.findAll();
        assertThat(challengeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateChallengeWithPatch() throws Exception {
        // Initialize the database
        challengeRepository.saveAndFlush(challenge);

        int databaseSizeBeforeUpdate = challengeRepository.findAll().size();

        // Update the challenge using partial update
        Challenge partialUpdatedChallenge = new Challenge();
        partialUpdatedChallenge.setId(challenge.getId());

        partialUpdatedChallenge
            .successText(UPDATED_SUCCESS_TEXT)
            .challengeName(UPDATED_CHALLENGE_NAME)
            .challangeRating(UPDATED_CHALLANGE_RATING)
            .skipResult(UPDATED_SKIP_RESULT);

        restChallengeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChallenge.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChallenge))
            )
            .andExpect(status().isOk());

        // Validate the Challenge in the database
        List<Challenge> challengeList = challengeRepository.findAll();
        assertThat(challengeList).hasSize(databaseSizeBeforeUpdate);
        Challenge testChallenge = challengeList.get(challengeList.size() - 1);
        assertThat(testChallenge.getIntroText()).isEqualTo(DEFAULT_INTRO_TEXT);
        assertThat(testChallenge.getSuccessText()).isEqualTo(UPDATED_SUCCESS_TEXT);
        assertThat(testChallenge.getFailureText()).isEqualTo(DEFAULT_FAILURE_TEXT);
        assertThat(testChallenge.getSkipText()).isEqualTo(DEFAULT_SKIP_TEXT);
        assertThat(testChallenge.getChallengeName()).isEqualTo(UPDATED_CHALLENGE_NAME);
        assertThat(testChallenge.getDifficulty()).isEqualTo(DEFAULT_DIFFICULTY);
        assertThat(testChallenge.getPrizeNumber()).isEqualTo(DEFAULT_PRIZE_NUMBER);
        assertThat(testChallenge.getChallangeRating()).isEqualTo(UPDATED_CHALLANGE_RATING);
        assertThat(testChallenge.getSacrificeNumber()).isEqualTo(DEFAULT_SACRIFICE_NUMBER);
        assertThat(testChallenge.getSkipResult()).isEqualTo(UPDATED_SKIP_RESULT);
    }

    @Test
    @Transactional
    void fullUpdateChallengeWithPatch() throws Exception {
        // Initialize the database
        challengeRepository.saveAndFlush(challenge);

        int databaseSizeBeforeUpdate = challengeRepository.findAll().size();

        // Update the challenge using partial update
        Challenge partialUpdatedChallenge = new Challenge();
        partialUpdatedChallenge.setId(challenge.getId());

        partialUpdatedChallenge
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

        restChallengeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChallenge.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChallenge))
            )
            .andExpect(status().isOk());

        // Validate the Challenge in the database
        List<Challenge> challengeList = challengeRepository.findAll();
        assertThat(challengeList).hasSize(databaseSizeBeforeUpdate);
        Challenge testChallenge = challengeList.get(challengeList.size() - 1);
        assertThat(testChallenge.getIntroText()).isEqualTo(UPDATED_INTRO_TEXT);
        assertThat(testChallenge.getSuccessText()).isEqualTo(UPDATED_SUCCESS_TEXT);
        assertThat(testChallenge.getFailureText()).isEqualTo(UPDATED_FAILURE_TEXT);
        assertThat(testChallenge.getSkipText()).isEqualTo(UPDATED_SKIP_TEXT);
        assertThat(testChallenge.getChallengeName()).isEqualTo(UPDATED_CHALLENGE_NAME);
        assertThat(testChallenge.getDifficulty()).isEqualTo(UPDATED_DIFFICULTY);
        assertThat(testChallenge.getPrizeNumber()).isEqualTo(UPDATED_PRIZE_NUMBER);
        assertThat(testChallenge.getChallangeRating()).isEqualTo(UPDATED_CHALLANGE_RATING);
        assertThat(testChallenge.getSacrificeNumber()).isEqualTo(UPDATED_SACRIFICE_NUMBER);
        assertThat(testChallenge.getSkipResult()).isEqualTo(UPDATED_SKIP_RESULT);
    }

    @Test
    @Transactional
    void patchNonExistingChallenge() throws Exception {
        int databaseSizeBeforeUpdate = challengeRepository.findAll().size();
        challenge.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChallengeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, challenge.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(challenge))
            )
            .andExpect(status().isBadRequest());

        // Validate the Challenge in the database
        List<Challenge> challengeList = challengeRepository.findAll();
        assertThat(challengeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchChallenge() throws Exception {
        int databaseSizeBeforeUpdate = challengeRepository.findAll().size();
        challenge.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChallengeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(challenge))
            )
            .andExpect(status().isBadRequest());

        // Validate the Challenge in the database
        List<Challenge> challengeList = challengeRepository.findAll();
        assertThat(challengeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamChallenge() throws Exception {
        int databaseSizeBeforeUpdate = challengeRepository.findAll().size();
        challenge.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChallengeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(challenge))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Challenge in the database
        List<Challenge> challengeList = challengeRepository.findAll();
        assertThat(challengeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteChallenge() throws Exception {
        // Initialize the database
        challengeRepository.saveAndFlush(challenge);

        int databaseSizeBeforeDelete = challengeRepository.findAll().size();

        // Delete the challenge
        restChallengeMockMvc
            .perform(delete(ENTITY_API_URL_ID, challenge.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Challenge> challengeList = challengeRepository.findAll();
        assertThat(challengeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
