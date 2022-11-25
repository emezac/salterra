package com.sturdy.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sturdy.myapp.IntegrationTest;
import com.sturdy.myapp.domain.MyUser;
import com.sturdy.myapp.repository.MyUserRepository;
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

/**
 * Integration tests for the {@link MyUserResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MyUserResourceIT {

    private static final String DEFAULT_WALLET_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_WALLET_ADDRESS = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/my-users";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MyUserRepository myUserRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMyUserMockMvc;

    private MyUser myUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MyUser createEntity(EntityManager em) {
        MyUser myUser = new MyUser().walletAddress(DEFAULT_WALLET_ADDRESS).createdAt(DEFAULT_CREATED_AT).updatedAt(DEFAULT_UPDATED_AT);
        return myUser;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MyUser createUpdatedEntity(EntityManager em) {
        MyUser myUser = new MyUser().walletAddress(UPDATED_WALLET_ADDRESS).createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT);
        return myUser;
    }

    @BeforeEach
    public void initTest() {
        myUser = createEntity(em);
    }

    @Test
    @Transactional
    void createMyUser() throws Exception {
        int databaseSizeBeforeCreate = myUserRepository.findAll().size();
        // Create the MyUser
        restMyUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(myUser)))
            .andExpect(status().isCreated());

        // Validate the MyUser in the database
        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeCreate + 1);
        MyUser testMyUser = myUserList.get(myUserList.size() - 1);
        assertThat(testMyUser.getWalletAddress()).isEqualTo(DEFAULT_WALLET_ADDRESS);
        assertThat(testMyUser.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testMyUser.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createMyUserWithExistingId() throws Exception {
        // Create the MyUser with an existing ID
        myUser.setId(1L);

        int databaseSizeBeforeCreate = myUserRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMyUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(myUser)))
            .andExpect(status().isBadRequest());

        // Validate the MyUser in the database
        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMyUsers() throws Exception {
        // Initialize the database
        myUserRepository.saveAndFlush(myUser);

        // Get all the myUserList
        restMyUserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(myUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].walletAddress").value(hasItem(DEFAULT_WALLET_ADDRESS)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getMyUser() throws Exception {
        // Initialize the database
        myUserRepository.saveAndFlush(myUser);

        // Get the myUser
        restMyUserMockMvc
            .perform(get(ENTITY_API_URL_ID, myUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(myUser.getId().intValue()))
            .andExpect(jsonPath("$.walletAddress").value(DEFAULT_WALLET_ADDRESS))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMyUser() throws Exception {
        // Get the myUser
        restMyUserMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMyUser() throws Exception {
        // Initialize the database
        myUserRepository.saveAndFlush(myUser);

        int databaseSizeBeforeUpdate = myUserRepository.findAll().size();

        // Update the myUser
        MyUser updatedMyUser = myUserRepository.findById(myUser.getId()).get();
        // Disconnect from session so that the updates on updatedMyUser are not directly saved in db
        em.detach(updatedMyUser);
        updatedMyUser.walletAddress(UPDATED_WALLET_ADDRESS).createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT);

        restMyUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMyUser.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMyUser))
            )
            .andExpect(status().isOk());

        // Validate the MyUser in the database
        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeUpdate);
        MyUser testMyUser = myUserList.get(myUserList.size() - 1);
        assertThat(testMyUser.getWalletAddress()).isEqualTo(UPDATED_WALLET_ADDRESS);
        assertThat(testMyUser.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testMyUser.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingMyUser() throws Exception {
        int databaseSizeBeforeUpdate = myUserRepository.findAll().size();
        myUser.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMyUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, myUser.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(myUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the MyUser in the database
        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMyUser() throws Exception {
        int databaseSizeBeforeUpdate = myUserRepository.findAll().size();
        myUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMyUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(myUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the MyUser in the database
        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMyUser() throws Exception {
        int databaseSizeBeforeUpdate = myUserRepository.findAll().size();
        myUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMyUserMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(myUser)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MyUser in the database
        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMyUserWithPatch() throws Exception {
        // Initialize the database
        myUserRepository.saveAndFlush(myUser);

        int databaseSizeBeforeUpdate = myUserRepository.findAll().size();

        // Update the myUser using partial update
        MyUser partialUpdatedMyUser = new MyUser();
        partialUpdatedMyUser.setId(myUser.getId());

        partialUpdatedMyUser.createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT);

        restMyUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMyUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMyUser))
            )
            .andExpect(status().isOk());

        // Validate the MyUser in the database
        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeUpdate);
        MyUser testMyUser = myUserList.get(myUserList.size() - 1);
        assertThat(testMyUser.getWalletAddress()).isEqualTo(DEFAULT_WALLET_ADDRESS);
        assertThat(testMyUser.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testMyUser.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateMyUserWithPatch() throws Exception {
        // Initialize the database
        myUserRepository.saveAndFlush(myUser);

        int databaseSizeBeforeUpdate = myUserRepository.findAll().size();

        // Update the myUser using partial update
        MyUser partialUpdatedMyUser = new MyUser();
        partialUpdatedMyUser.setId(myUser.getId());

        partialUpdatedMyUser.walletAddress(UPDATED_WALLET_ADDRESS).createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT);

        restMyUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMyUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMyUser))
            )
            .andExpect(status().isOk());

        // Validate the MyUser in the database
        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeUpdate);
        MyUser testMyUser = myUserList.get(myUserList.size() - 1);
        assertThat(testMyUser.getWalletAddress()).isEqualTo(UPDATED_WALLET_ADDRESS);
        assertThat(testMyUser.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testMyUser.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingMyUser() throws Exception {
        int databaseSizeBeforeUpdate = myUserRepository.findAll().size();
        myUser.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMyUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, myUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(myUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the MyUser in the database
        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMyUser() throws Exception {
        int databaseSizeBeforeUpdate = myUserRepository.findAll().size();
        myUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMyUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(myUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the MyUser in the database
        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMyUser() throws Exception {
        int databaseSizeBeforeUpdate = myUserRepository.findAll().size();
        myUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMyUserMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(myUser)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MyUser in the database
        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMyUser() throws Exception {
        // Initialize the database
        myUserRepository.saveAndFlush(myUser);

        int databaseSizeBeforeDelete = myUserRepository.findAll().size();

        // Delete the myUser
        restMyUserMockMvc
            .perform(delete(ENTITY_API_URL_ID, myUser.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
