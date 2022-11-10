package com.sturdy.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sturdy.myapp.IntegrationTest;
import com.sturdy.myapp.domain.Card;
import com.sturdy.myapp.repository.CardRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link CardResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CardResourceIT {

    private static final byte[] DEFAULT_THUMBNAI_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_THUMBNAI_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_THUMBNAI_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_THUMBNAI_IMAGE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_HIGHRES_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_HIGHRES_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_HIGHRES_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_HIGHRES_IMAGE_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/cards";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCardMockMvc;

    private Card card;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Card createEntity(EntityManager em) {
        Card card = new Card()
            .thumbnaiImage(DEFAULT_THUMBNAI_IMAGE)
            .thumbnaiImageContentType(DEFAULT_THUMBNAI_IMAGE_CONTENT_TYPE)
            .highresImage(DEFAULT_HIGHRES_IMAGE)
            .highresImageContentType(DEFAULT_HIGHRES_IMAGE_CONTENT_TYPE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .deletedAt(DEFAULT_DELETED_AT);
        return card;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Card createUpdatedEntity(EntityManager em) {
        Card card = new Card()
            .thumbnaiImage(UPDATED_THUMBNAI_IMAGE)
            .thumbnaiImageContentType(UPDATED_THUMBNAI_IMAGE_CONTENT_TYPE)
            .highresImage(UPDATED_HIGHRES_IMAGE)
            .highresImageContentType(UPDATED_HIGHRES_IMAGE_CONTENT_TYPE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT);
        return card;
    }

    @BeforeEach
    public void initTest() {
        card = createEntity(em);
    }

    @Test
    @Transactional
    void createCard() throws Exception {
        int databaseSizeBeforeCreate = cardRepository.findAll().size();
        // Create the Card
        restCardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(card)))
            .andExpect(status().isCreated());

        // Validate the Card in the database
        List<Card> cardList = cardRepository.findAll();
        assertThat(cardList).hasSize(databaseSizeBeforeCreate + 1);
        Card testCard = cardList.get(cardList.size() - 1);
        assertThat(testCard.getThumbnaiImage()).isEqualTo(DEFAULT_THUMBNAI_IMAGE);
        assertThat(testCard.getThumbnaiImageContentType()).isEqualTo(DEFAULT_THUMBNAI_IMAGE_CONTENT_TYPE);
        assertThat(testCard.getHighresImage()).isEqualTo(DEFAULT_HIGHRES_IMAGE);
        assertThat(testCard.getHighresImageContentType()).isEqualTo(DEFAULT_HIGHRES_IMAGE_CONTENT_TYPE);
        assertThat(testCard.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testCard.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testCard.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
    }

    @Test
    @Transactional
    void createCardWithExistingId() throws Exception {
        // Create the Card with an existing ID
        card.setId(1L);

        int databaseSizeBeforeCreate = cardRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(card)))
            .andExpect(status().isBadRequest());

        // Validate the Card in the database
        List<Card> cardList = cardRepository.findAll();
        assertThat(cardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCards() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList
        restCardMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(card.getId().intValue())))
            .andExpect(jsonPath("$.[*].thumbnaiImageContentType").value(hasItem(DEFAULT_THUMBNAI_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].thumbnaiImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_THUMBNAI_IMAGE))))
            .andExpect(jsonPath("$.[*].highresImageContentType").value(hasItem(DEFAULT_HIGHRES_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].highresImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_HIGHRES_IMAGE))))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())));
    }

    @Test
    @Transactional
    void getCard() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get the card
        restCardMockMvc
            .perform(get(ENTITY_API_URL_ID, card.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(card.getId().intValue()))
            .andExpect(jsonPath("$.thumbnaiImageContentType").value(DEFAULT_THUMBNAI_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.thumbnaiImage").value(Base64Utils.encodeToString(DEFAULT_THUMBNAI_IMAGE)))
            .andExpect(jsonPath("$.highresImageContentType").value(DEFAULT_HIGHRES_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.highresImage").value(Base64Utils.encodeToString(DEFAULT_HIGHRES_IMAGE)))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCard() throws Exception {
        // Get the card
        restCardMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCard() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        int databaseSizeBeforeUpdate = cardRepository.findAll().size();

        // Update the card
        Card updatedCard = cardRepository.findById(card.getId()).get();
        // Disconnect from session so that the updates on updatedCard are not directly saved in db
        em.detach(updatedCard);
        updatedCard
            .thumbnaiImage(UPDATED_THUMBNAI_IMAGE)
            .thumbnaiImageContentType(UPDATED_THUMBNAI_IMAGE_CONTENT_TYPE)
            .highresImage(UPDATED_HIGHRES_IMAGE)
            .highresImageContentType(UPDATED_HIGHRES_IMAGE_CONTENT_TYPE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT);

        restCardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCard.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCard))
            )
            .andExpect(status().isOk());

        // Validate the Card in the database
        List<Card> cardList = cardRepository.findAll();
        assertThat(cardList).hasSize(databaseSizeBeforeUpdate);
        Card testCard = cardList.get(cardList.size() - 1);
        assertThat(testCard.getThumbnaiImage()).isEqualTo(UPDATED_THUMBNAI_IMAGE);
        assertThat(testCard.getThumbnaiImageContentType()).isEqualTo(UPDATED_THUMBNAI_IMAGE_CONTENT_TYPE);
        assertThat(testCard.getHighresImage()).isEqualTo(UPDATED_HIGHRES_IMAGE);
        assertThat(testCard.getHighresImageContentType()).isEqualTo(UPDATED_HIGHRES_IMAGE_CONTENT_TYPE);
        assertThat(testCard.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testCard.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testCard.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void putNonExistingCard() throws Exception {
        int databaseSizeBeforeUpdate = cardRepository.findAll().size();
        card.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, card.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(card))
            )
            .andExpect(status().isBadRequest());

        // Validate the Card in the database
        List<Card> cardList = cardRepository.findAll();
        assertThat(cardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCard() throws Exception {
        int databaseSizeBeforeUpdate = cardRepository.findAll().size();
        card.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(card))
            )
            .andExpect(status().isBadRequest());

        // Validate the Card in the database
        List<Card> cardList = cardRepository.findAll();
        assertThat(cardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCard() throws Exception {
        int databaseSizeBeforeUpdate = cardRepository.findAll().size();
        card.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCardMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(card)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Card in the database
        List<Card> cardList = cardRepository.findAll();
        assertThat(cardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCardWithPatch() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        int databaseSizeBeforeUpdate = cardRepository.findAll().size();

        // Update the card using partial update
        Card partialUpdatedCard = new Card();
        partialUpdatedCard.setId(card.getId());

        partialUpdatedCard
            .thumbnaiImage(UPDATED_THUMBNAI_IMAGE)
            .thumbnaiImageContentType(UPDATED_THUMBNAI_IMAGE_CONTENT_TYPE)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT);

        restCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCard.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCard))
            )
            .andExpect(status().isOk());

        // Validate the Card in the database
        List<Card> cardList = cardRepository.findAll();
        assertThat(cardList).hasSize(databaseSizeBeforeUpdate);
        Card testCard = cardList.get(cardList.size() - 1);
        assertThat(testCard.getThumbnaiImage()).isEqualTo(UPDATED_THUMBNAI_IMAGE);
        assertThat(testCard.getThumbnaiImageContentType()).isEqualTo(UPDATED_THUMBNAI_IMAGE_CONTENT_TYPE);
        assertThat(testCard.getHighresImage()).isEqualTo(DEFAULT_HIGHRES_IMAGE);
        assertThat(testCard.getHighresImageContentType()).isEqualTo(DEFAULT_HIGHRES_IMAGE_CONTENT_TYPE);
        assertThat(testCard.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testCard.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testCard.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void fullUpdateCardWithPatch() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        int databaseSizeBeforeUpdate = cardRepository.findAll().size();

        // Update the card using partial update
        Card partialUpdatedCard = new Card();
        partialUpdatedCard.setId(card.getId());

        partialUpdatedCard
            .thumbnaiImage(UPDATED_THUMBNAI_IMAGE)
            .thumbnaiImageContentType(UPDATED_THUMBNAI_IMAGE_CONTENT_TYPE)
            .highresImage(UPDATED_HIGHRES_IMAGE)
            .highresImageContentType(UPDATED_HIGHRES_IMAGE_CONTENT_TYPE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT);

        restCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCard.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCard))
            )
            .andExpect(status().isOk());

        // Validate the Card in the database
        List<Card> cardList = cardRepository.findAll();
        assertThat(cardList).hasSize(databaseSizeBeforeUpdate);
        Card testCard = cardList.get(cardList.size() - 1);
        assertThat(testCard.getThumbnaiImage()).isEqualTo(UPDATED_THUMBNAI_IMAGE);
        assertThat(testCard.getThumbnaiImageContentType()).isEqualTo(UPDATED_THUMBNAI_IMAGE_CONTENT_TYPE);
        assertThat(testCard.getHighresImage()).isEqualTo(UPDATED_HIGHRES_IMAGE);
        assertThat(testCard.getHighresImageContentType()).isEqualTo(UPDATED_HIGHRES_IMAGE_CONTENT_TYPE);
        assertThat(testCard.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testCard.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testCard.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingCard() throws Exception {
        int databaseSizeBeforeUpdate = cardRepository.findAll().size();
        card.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, card.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(card))
            )
            .andExpect(status().isBadRequest());

        // Validate the Card in the database
        List<Card> cardList = cardRepository.findAll();
        assertThat(cardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCard() throws Exception {
        int databaseSizeBeforeUpdate = cardRepository.findAll().size();
        card.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(card))
            )
            .andExpect(status().isBadRequest());

        // Validate the Card in the database
        List<Card> cardList = cardRepository.findAll();
        assertThat(cardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCard() throws Exception {
        int databaseSizeBeforeUpdate = cardRepository.findAll().size();
        card.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCardMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(card)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Card in the database
        List<Card> cardList = cardRepository.findAll();
        assertThat(cardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCard() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        int databaseSizeBeforeDelete = cardRepository.findAll().size();

        // Delete the card
        restCardMockMvc
            .perform(delete(ENTITY_API_URL_ID, card.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Card> cardList = cardRepository.findAll();
        assertThat(cardList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
