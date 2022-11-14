package com.sturdy.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sturdy.myapp.IntegrationTest;
import com.sturdy.myapp.domain.RoomConnection;
import com.sturdy.myapp.repository.RoomConnectionRepository;
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
 * Integration tests for the {@link RoomConnectionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RoomConnectionResourceIT {

    private static final Long DEFAULT_ROOM_1 = 1L;
    private static final Long UPDATED_ROOM_1 = 2L;

    private static final Long DEFAULT_ROOM_2 = 1L;
    private static final Long UPDATED_ROOM_2 = 2L;

    private static final Long DEFAULT_ROOM_3 = 1L;
    private static final Long UPDATED_ROOM_3 = 2L;

    private static final Long DEFAULT_ROOM_4 = 1L;
    private static final Long UPDATED_ROOM_4 = 2L;

    private static final Long DEFAULT_ROOM_5 = 1L;
    private static final Long UPDATED_ROOM_5 = 2L;

    private static final Long DEFAULT_ROOM_6 = 1L;
    private static final Long UPDATED_ROOM_6 = 2L;

    private static final Long DEFAULT_ROOM_7 = 1L;
    private static final Long UPDATED_ROOM_7 = 2L;

    private static final Long DEFAULT_ROOM_8 = 1L;
    private static final Long UPDATED_ROOM_8 = 2L;

    private static final Long DEFAULT_ROOM_9 = 1L;
    private static final Long UPDATED_ROOM_9 = 2L;

    private static final Long DEFAULT_ROOM_10 = 1L;
    private static final Long UPDATED_ROOM_10 = 2L;

    private static final Long DEFAULT_ROOM_11 = 1L;
    private static final Long UPDATED_ROOM_11 = 2L;

    private static final Long DEFAULT_ROOM_12 = 1L;
    private static final Long UPDATED_ROOM_12 = 2L;

    private static final Long DEFAULT_ROOM_13 = 1L;
    private static final Long UPDATED_ROOM_13 = 2L;

    private static final Long DEFAULT_ROOM_14 = 1L;
    private static final Long UPDATED_ROOM_14 = 2L;

    private static final Long DEFAULT_ROOM_15 = 1L;
    private static final Long UPDATED_ROOM_15 = 2L;

    private static final Long DEFAULT_ROOM_16 = 1L;
    private static final Long UPDATED_ROOM_16 = 2L;

    private static final Long DEFAULT_ROOM_17 = 1L;
    private static final Long UPDATED_ROOM_17 = 2L;

    private static final Long DEFAULT_ROOM_18 = 1L;
    private static final Long UPDATED_ROOM_18 = 2L;

    private static final Long DEFAULT_ROOM_19 = 1L;
    private static final Long UPDATED_ROOM_19 = 2L;

    private static final Long DEFAULT_ROOM_20 = 1L;
    private static final Long UPDATED_ROOM_20 = 2L;

    private static final Long DEFAULT_ROOM_21 = 1L;
    private static final Long UPDATED_ROOM_21 = 2L;

    private static final Long DEFAULT_ROOM_22 = 1L;
    private static final Long UPDATED_ROOM_22 = 2L;

    private static final Long DEFAULT_ROOM_23 = 1L;
    private static final Long UPDATED_ROOM_23 = 2L;

    private static final Long DEFAULT_ROOM_24 = 1L;
    private static final Long UPDATED_ROOM_24 = 2L;

    private static final Long DEFAULT_ROOM_25 = 1L;
    private static final Long UPDATED_ROOM_25 = 2L;

    private static final Long DEFAULT_ROOM_26 = 1L;
    private static final Long UPDATED_ROOM_26 = 2L;

    private static final Long DEFAULT_ROOM_27 = 1L;
    private static final Long UPDATED_ROOM_27 = 2L;

    private static final Long DEFAULT_ROOM_28 = 1L;
    private static final Long UPDATED_ROOM_28 = 2L;

    private static final Long DEFAULT_ROOM_29 = 1L;
    private static final Long UPDATED_ROOM_29 = 2L;

    private static final Long DEFAULT_ROOM_30 = 1L;
    private static final Long UPDATED_ROOM_30 = 2L;

    private static final Long DEFAULT_ROOM_31 = 1L;
    private static final Long UPDATED_ROOM_31 = 2L;

    private static final Long DEFAULT_ROOM_32 = 1L;
    private static final Long UPDATED_ROOM_32 = 2L;

    private static final Long DEFAULT_ROOM_33 = 1L;
    private static final Long UPDATED_ROOM_33 = 2L;

    private static final Long DEFAULT_ROOM_34 = 1L;
    private static final Long UPDATED_ROOM_34 = 2L;

    private static final Long DEFAULT_ROOM_35 = 1L;
    private static final Long UPDATED_ROOM_35 = 2L;

    private static final String ENTITY_API_URL = "/api/room-connections";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RoomConnectionRepository roomConnectionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRoomConnectionMockMvc;

    private RoomConnection roomConnection;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RoomConnection createEntity(EntityManager em) {
        RoomConnection roomConnection = new RoomConnection()
            .room1(DEFAULT_ROOM_1)
            .room2(DEFAULT_ROOM_2)
            .room3(DEFAULT_ROOM_3)
            .room4(DEFAULT_ROOM_4)
            .room5(DEFAULT_ROOM_5)
            .room6(DEFAULT_ROOM_6)
            .room7(DEFAULT_ROOM_7)
            .room8(DEFAULT_ROOM_8)
            .room9(DEFAULT_ROOM_9)
            .room10(DEFAULT_ROOM_10)
            .room11(DEFAULT_ROOM_11)
            .room12(DEFAULT_ROOM_12)
            .room13(DEFAULT_ROOM_13)
            .room14(DEFAULT_ROOM_14)
            .room15(DEFAULT_ROOM_15)
            .room16(DEFAULT_ROOM_16)
            .room17(DEFAULT_ROOM_17)
            .room18(DEFAULT_ROOM_18)
            .room19(DEFAULT_ROOM_19)
            .room20(DEFAULT_ROOM_20)
            .room21(DEFAULT_ROOM_21)
            .room22(DEFAULT_ROOM_22)
            .room23(DEFAULT_ROOM_23)
            .room24(DEFAULT_ROOM_24)
            .room25(DEFAULT_ROOM_25)
            .room26(DEFAULT_ROOM_26)
            .room27(DEFAULT_ROOM_27)
            .room28(DEFAULT_ROOM_28)
            .room29(DEFAULT_ROOM_29)
            .room30(DEFAULT_ROOM_30)
            .room31(DEFAULT_ROOM_31)
            .room32(DEFAULT_ROOM_32)
            .room33(DEFAULT_ROOM_33)
            .room34(DEFAULT_ROOM_34)
            .room35(DEFAULT_ROOM_35);
        return roomConnection;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RoomConnection createUpdatedEntity(EntityManager em) {
        RoomConnection roomConnection = new RoomConnection()
            .room1(UPDATED_ROOM_1)
            .room2(UPDATED_ROOM_2)
            .room3(UPDATED_ROOM_3)
            .room4(UPDATED_ROOM_4)
            .room5(UPDATED_ROOM_5)
            .room6(UPDATED_ROOM_6)
            .room7(UPDATED_ROOM_7)
            .room8(UPDATED_ROOM_8)
            .room9(UPDATED_ROOM_9)
            .room10(UPDATED_ROOM_10)
            .room11(UPDATED_ROOM_11)
            .room12(UPDATED_ROOM_12)
            .room13(UPDATED_ROOM_13)
            .room14(UPDATED_ROOM_14)
            .room15(UPDATED_ROOM_15)
            .room16(UPDATED_ROOM_16)
            .room17(UPDATED_ROOM_17)
            .room18(UPDATED_ROOM_18)
            .room19(UPDATED_ROOM_19)
            .room20(UPDATED_ROOM_20)
            .room21(UPDATED_ROOM_21)
            .room22(UPDATED_ROOM_22)
            .room23(UPDATED_ROOM_23)
            .room24(UPDATED_ROOM_24)
            .room25(UPDATED_ROOM_25)
            .room26(UPDATED_ROOM_26)
            .room27(UPDATED_ROOM_27)
            .room28(UPDATED_ROOM_28)
            .room29(UPDATED_ROOM_29)
            .room30(UPDATED_ROOM_30)
            .room31(UPDATED_ROOM_31)
            .room32(UPDATED_ROOM_32)
            .room33(UPDATED_ROOM_33)
            .room34(UPDATED_ROOM_34)
            .room35(UPDATED_ROOM_35);
        return roomConnection;
    }

    @BeforeEach
    public void initTest() {
        roomConnection = createEntity(em);
    }

    @Test
    @Transactional
    void createRoomConnection() throws Exception {
        int databaseSizeBeforeCreate = roomConnectionRepository.findAll().size();
        // Create the RoomConnection
        restRoomConnectionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roomConnection))
            )
            .andExpect(status().isCreated());

        // Validate the RoomConnection in the database
        List<RoomConnection> roomConnectionList = roomConnectionRepository.findAll();
        assertThat(roomConnectionList).hasSize(databaseSizeBeforeCreate + 1);
        RoomConnection testRoomConnection = roomConnectionList.get(roomConnectionList.size() - 1);
        assertThat(testRoomConnection.getRoom1()).isEqualTo(DEFAULT_ROOM_1);
        assertThat(testRoomConnection.getRoom2()).isEqualTo(DEFAULT_ROOM_2);
        assertThat(testRoomConnection.getRoom3()).isEqualTo(DEFAULT_ROOM_3);
        assertThat(testRoomConnection.getRoom4()).isEqualTo(DEFAULT_ROOM_4);
        assertThat(testRoomConnection.getRoom5()).isEqualTo(DEFAULT_ROOM_5);
        assertThat(testRoomConnection.getRoom6()).isEqualTo(DEFAULT_ROOM_6);
        assertThat(testRoomConnection.getRoom7()).isEqualTo(DEFAULT_ROOM_7);
        assertThat(testRoomConnection.getRoom8()).isEqualTo(DEFAULT_ROOM_8);
        assertThat(testRoomConnection.getRoom9()).isEqualTo(DEFAULT_ROOM_9);
        assertThat(testRoomConnection.getRoom10()).isEqualTo(DEFAULT_ROOM_10);
        assertThat(testRoomConnection.getRoom11()).isEqualTo(DEFAULT_ROOM_11);
        assertThat(testRoomConnection.getRoom12()).isEqualTo(DEFAULT_ROOM_12);
        assertThat(testRoomConnection.getRoom13()).isEqualTo(DEFAULT_ROOM_13);
        assertThat(testRoomConnection.getRoom14()).isEqualTo(DEFAULT_ROOM_14);
        assertThat(testRoomConnection.getRoom15()).isEqualTo(DEFAULT_ROOM_15);
        assertThat(testRoomConnection.getRoom16()).isEqualTo(DEFAULT_ROOM_16);
        assertThat(testRoomConnection.getRoom17()).isEqualTo(DEFAULT_ROOM_17);
        assertThat(testRoomConnection.getRoom18()).isEqualTo(DEFAULT_ROOM_18);
        assertThat(testRoomConnection.getRoom19()).isEqualTo(DEFAULT_ROOM_19);
        assertThat(testRoomConnection.getRoom20()).isEqualTo(DEFAULT_ROOM_20);
        assertThat(testRoomConnection.getRoom21()).isEqualTo(DEFAULT_ROOM_21);
        assertThat(testRoomConnection.getRoom22()).isEqualTo(DEFAULT_ROOM_22);
        assertThat(testRoomConnection.getRoom23()).isEqualTo(DEFAULT_ROOM_23);
        assertThat(testRoomConnection.getRoom24()).isEqualTo(DEFAULT_ROOM_24);
        assertThat(testRoomConnection.getRoom25()).isEqualTo(DEFAULT_ROOM_25);
        assertThat(testRoomConnection.getRoom26()).isEqualTo(DEFAULT_ROOM_26);
        assertThat(testRoomConnection.getRoom27()).isEqualTo(DEFAULT_ROOM_27);
        assertThat(testRoomConnection.getRoom28()).isEqualTo(DEFAULT_ROOM_28);
        assertThat(testRoomConnection.getRoom29()).isEqualTo(DEFAULT_ROOM_29);
        assertThat(testRoomConnection.getRoom30()).isEqualTo(DEFAULT_ROOM_30);
        assertThat(testRoomConnection.getRoom31()).isEqualTo(DEFAULT_ROOM_31);
        assertThat(testRoomConnection.getRoom32()).isEqualTo(DEFAULT_ROOM_32);
        assertThat(testRoomConnection.getRoom33()).isEqualTo(DEFAULT_ROOM_33);
        assertThat(testRoomConnection.getRoom34()).isEqualTo(DEFAULT_ROOM_34);
        assertThat(testRoomConnection.getRoom35()).isEqualTo(DEFAULT_ROOM_35);
    }

    @Test
    @Transactional
    void createRoomConnectionWithExistingId() throws Exception {
        // Create the RoomConnection with an existing ID
        roomConnection.setId(1L);

        int databaseSizeBeforeCreate = roomConnectionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoomConnectionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roomConnection))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoomConnection in the database
        List<RoomConnection> roomConnectionList = roomConnectionRepository.findAll();
        assertThat(roomConnectionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRoomConnections() throws Exception {
        // Initialize the database
        roomConnectionRepository.saveAndFlush(roomConnection);

        // Get all the roomConnectionList
        restRoomConnectionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roomConnection.getId().intValue())))
            .andExpect(jsonPath("$.[*].room1").value(hasItem(DEFAULT_ROOM_1.intValue())))
            .andExpect(jsonPath("$.[*].room2").value(hasItem(DEFAULT_ROOM_2.intValue())))
            .andExpect(jsonPath("$.[*].room3").value(hasItem(DEFAULT_ROOM_3.intValue())))
            .andExpect(jsonPath("$.[*].room4").value(hasItem(DEFAULT_ROOM_4.intValue())))
            .andExpect(jsonPath("$.[*].room5").value(hasItem(DEFAULT_ROOM_5.intValue())))
            .andExpect(jsonPath("$.[*].room6").value(hasItem(DEFAULT_ROOM_6.intValue())))
            .andExpect(jsonPath("$.[*].room7").value(hasItem(DEFAULT_ROOM_7.intValue())))
            .andExpect(jsonPath("$.[*].room8").value(hasItem(DEFAULT_ROOM_8.intValue())))
            .andExpect(jsonPath("$.[*].room9").value(hasItem(DEFAULT_ROOM_9.intValue())))
            .andExpect(jsonPath("$.[*].room10").value(hasItem(DEFAULT_ROOM_10.intValue())))
            .andExpect(jsonPath("$.[*].room11").value(hasItem(DEFAULT_ROOM_11.intValue())))
            .andExpect(jsonPath("$.[*].room12").value(hasItem(DEFAULT_ROOM_12.intValue())))
            .andExpect(jsonPath("$.[*].room13").value(hasItem(DEFAULT_ROOM_13.intValue())))
            .andExpect(jsonPath("$.[*].room14").value(hasItem(DEFAULT_ROOM_14.intValue())))
            .andExpect(jsonPath("$.[*].room15").value(hasItem(DEFAULT_ROOM_15.intValue())))
            .andExpect(jsonPath("$.[*].room16").value(hasItem(DEFAULT_ROOM_16.intValue())))
            .andExpect(jsonPath("$.[*].room17").value(hasItem(DEFAULT_ROOM_17.intValue())))
            .andExpect(jsonPath("$.[*].room18").value(hasItem(DEFAULT_ROOM_18.intValue())))
            .andExpect(jsonPath("$.[*].room19").value(hasItem(DEFAULT_ROOM_19.intValue())))
            .andExpect(jsonPath("$.[*].room20").value(hasItem(DEFAULT_ROOM_20.intValue())))
            .andExpect(jsonPath("$.[*].room21").value(hasItem(DEFAULT_ROOM_21.intValue())))
            .andExpect(jsonPath("$.[*].room22").value(hasItem(DEFAULT_ROOM_22.intValue())))
            .andExpect(jsonPath("$.[*].room23").value(hasItem(DEFAULT_ROOM_23.intValue())))
            .andExpect(jsonPath("$.[*].room24").value(hasItem(DEFAULT_ROOM_24.intValue())))
            .andExpect(jsonPath("$.[*].room25").value(hasItem(DEFAULT_ROOM_25.intValue())))
            .andExpect(jsonPath("$.[*].room26").value(hasItem(DEFAULT_ROOM_26.intValue())))
            .andExpect(jsonPath("$.[*].room27").value(hasItem(DEFAULT_ROOM_27.intValue())))
            .andExpect(jsonPath("$.[*].room28").value(hasItem(DEFAULT_ROOM_28.intValue())))
            .andExpect(jsonPath("$.[*].room29").value(hasItem(DEFAULT_ROOM_29.intValue())))
            .andExpect(jsonPath("$.[*].room30").value(hasItem(DEFAULT_ROOM_30.intValue())))
            .andExpect(jsonPath("$.[*].room31").value(hasItem(DEFAULT_ROOM_31.intValue())))
            .andExpect(jsonPath("$.[*].room32").value(hasItem(DEFAULT_ROOM_32.intValue())))
            .andExpect(jsonPath("$.[*].room33").value(hasItem(DEFAULT_ROOM_33.intValue())))
            .andExpect(jsonPath("$.[*].room34").value(hasItem(DEFAULT_ROOM_34.intValue())))
            .andExpect(jsonPath("$.[*].room35").value(hasItem(DEFAULT_ROOM_35.intValue())));
    }

    @Test
    @Transactional
    void getRoomConnection() throws Exception {
        // Initialize the database
        roomConnectionRepository.saveAndFlush(roomConnection);

        // Get the roomConnection
        restRoomConnectionMockMvc
            .perform(get(ENTITY_API_URL_ID, roomConnection.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(roomConnection.getId().intValue()))
            .andExpect(jsonPath("$.room1").value(DEFAULT_ROOM_1.intValue()))
            .andExpect(jsonPath("$.room2").value(DEFAULT_ROOM_2.intValue()))
            .andExpect(jsonPath("$.room3").value(DEFAULT_ROOM_3.intValue()))
            .andExpect(jsonPath("$.room4").value(DEFAULT_ROOM_4.intValue()))
            .andExpect(jsonPath("$.room5").value(DEFAULT_ROOM_5.intValue()))
            .andExpect(jsonPath("$.room6").value(DEFAULT_ROOM_6.intValue()))
            .andExpect(jsonPath("$.room7").value(DEFAULT_ROOM_7.intValue()))
            .andExpect(jsonPath("$.room8").value(DEFAULT_ROOM_8.intValue()))
            .andExpect(jsonPath("$.room9").value(DEFAULT_ROOM_9.intValue()))
            .andExpect(jsonPath("$.room10").value(DEFAULT_ROOM_10.intValue()))
            .andExpect(jsonPath("$.room11").value(DEFAULT_ROOM_11.intValue()))
            .andExpect(jsonPath("$.room12").value(DEFAULT_ROOM_12.intValue()))
            .andExpect(jsonPath("$.room13").value(DEFAULT_ROOM_13.intValue()))
            .andExpect(jsonPath("$.room14").value(DEFAULT_ROOM_14.intValue()))
            .andExpect(jsonPath("$.room15").value(DEFAULT_ROOM_15.intValue()))
            .andExpect(jsonPath("$.room16").value(DEFAULT_ROOM_16.intValue()))
            .andExpect(jsonPath("$.room17").value(DEFAULT_ROOM_17.intValue()))
            .andExpect(jsonPath("$.room18").value(DEFAULT_ROOM_18.intValue()))
            .andExpect(jsonPath("$.room19").value(DEFAULT_ROOM_19.intValue()))
            .andExpect(jsonPath("$.room20").value(DEFAULT_ROOM_20.intValue()))
            .andExpect(jsonPath("$.room21").value(DEFAULT_ROOM_21.intValue()))
            .andExpect(jsonPath("$.room22").value(DEFAULT_ROOM_22.intValue()))
            .andExpect(jsonPath("$.room23").value(DEFAULT_ROOM_23.intValue()))
            .andExpect(jsonPath("$.room24").value(DEFAULT_ROOM_24.intValue()))
            .andExpect(jsonPath("$.room25").value(DEFAULT_ROOM_25.intValue()))
            .andExpect(jsonPath("$.room26").value(DEFAULT_ROOM_26.intValue()))
            .andExpect(jsonPath("$.room27").value(DEFAULT_ROOM_27.intValue()))
            .andExpect(jsonPath("$.room28").value(DEFAULT_ROOM_28.intValue()))
            .andExpect(jsonPath("$.room29").value(DEFAULT_ROOM_29.intValue()))
            .andExpect(jsonPath("$.room30").value(DEFAULT_ROOM_30.intValue()))
            .andExpect(jsonPath("$.room31").value(DEFAULT_ROOM_31.intValue()))
            .andExpect(jsonPath("$.room32").value(DEFAULT_ROOM_32.intValue()))
            .andExpect(jsonPath("$.room33").value(DEFAULT_ROOM_33.intValue()))
            .andExpect(jsonPath("$.room34").value(DEFAULT_ROOM_34.intValue()))
            .andExpect(jsonPath("$.room35").value(DEFAULT_ROOM_35.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingRoomConnection() throws Exception {
        // Get the roomConnection
        restRoomConnectionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRoomConnection() throws Exception {
        // Initialize the database
        roomConnectionRepository.saveAndFlush(roomConnection);

        int databaseSizeBeforeUpdate = roomConnectionRepository.findAll().size();

        // Update the roomConnection
        RoomConnection updatedRoomConnection = roomConnectionRepository.findById(roomConnection.getId()).get();
        // Disconnect from session so that the updates on updatedRoomConnection are not directly saved in db
        em.detach(updatedRoomConnection);
        updatedRoomConnection
            .room1(UPDATED_ROOM_1)
            .room2(UPDATED_ROOM_2)
            .room3(UPDATED_ROOM_3)
            .room4(UPDATED_ROOM_4)
            .room5(UPDATED_ROOM_5)
            .room6(UPDATED_ROOM_6)
            .room7(UPDATED_ROOM_7)
            .room8(UPDATED_ROOM_8)
            .room9(UPDATED_ROOM_9)
            .room10(UPDATED_ROOM_10)
            .room11(UPDATED_ROOM_11)
            .room12(UPDATED_ROOM_12)
            .room13(UPDATED_ROOM_13)
            .room14(UPDATED_ROOM_14)
            .room15(UPDATED_ROOM_15)
            .room16(UPDATED_ROOM_16)
            .room17(UPDATED_ROOM_17)
            .room18(UPDATED_ROOM_18)
            .room19(UPDATED_ROOM_19)
            .room20(UPDATED_ROOM_20)
            .room21(UPDATED_ROOM_21)
            .room22(UPDATED_ROOM_22)
            .room23(UPDATED_ROOM_23)
            .room24(UPDATED_ROOM_24)
            .room25(UPDATED_ROOM_25)
            .room26(UPDATED_ROOM_26)
            .room27(UPDATED_ROOM_27)
            .room28(UPDATED_ROOM_28)
            .room29(UPDATED_ROOM_29)
            .room30(UPDATED_ROOM_30)
            .room31(UPDATED_ROOM_31)
            .room32(UPDATED_ROOM_32)
            .room33(UPDATED_ROOM_33)
            .room34(UPDATED_ROOM_34)
            .room35(UPDATED_ROOM_35);

        restRoomConnectionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRoomConnection.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRoomConnection))
            )
            .andExpect(status().isOk());

        // Validate the RoomConnection in the database
        List<RoomConnection> roomConnectionList = roomConnectionRepository.findAll();
        assertThat(roomConnectionList).hasSize(databaseSizeBeforeUpdate);
        RoomConnection testRoomConnection = roomConnectionList.get(roomConnectionList.size() - 1);
        assertThat(testRoomConnection.getRoom1()).isEqualTo(UPDATED_ROOM_1);
        assertThat(testRoomConnection.getRoom2()).isEqualTo(UPDATED_ROOM_2);
        assertThat(testRoomConnection.getRoom3()).isEqualTo(UPDATED_ROOM_3);
        assertThat(testRoomConnection.getRoom4()).isEqualTo(UPDATED_ROOM_4);
        assertThat(testRoomConnection.getRoom5()).isEqualTo(UPDATED_ROOM_5);
        assertThat(testRoomConnection.getRoom6()).isEqualTo(UPDATED_ROOM_6);
        assertThat(testRoomConnection.getRoom7()).isEqualTo(UPDATED_ROOM_7);
        assertThat(testRoomConnection.getRoom8()).isEqualTo(UPDATED_ROOM_8);
        assertThat(testRoomConnection.getRoom9()).isEqualTo(UPDATED_ROOM_9);
        assertThat(testRoomConnection.getRoom10()).isEqualTo(UPDATED_ROOM_10);
        assertThat(testRoomConnection.getRoom11()).isEqualTo(UPDATED_ROOM_11);
        assertThat(testRoomConnection.getRoom12()).isEqualTo(UPDATED_ROOM_12);
        assertThat(testRoomConnection.getRoom13()).isEqualTo(UPDATED_ROOM_13);
        assertThat(testRoomConnection.getRoom14()).isEqualTo(UPDATED_ROOM_14);
        assertThat(testRoomConnection.getRoom15()).isEqualTo(UPDATED_ROOM_15);
        assertThat(testRoomConnection.getRoom16()).isEqualTo(UPDATED_ROOM_16);
        assertThat(testRoomConnection.getRoom17()).isEqualTo(UPDATED_ROOM_17);
        assertThat(testRoomConnection.getRoom18()).isEqualTo(UPDATED_ROOM_18);
        assertThat(testRoomConnection.getRoom19()).isEqualTo(UPDATED_ROOM_19);
        assertThat(testRoomConnection.getRoom20()).isEqualTo(UPDATED_ROOM_20);
        assertThat(testRoomConnection.getRoom21()).isEqualTo(UPDATED_ROOM_21);
        assertThat(testRoomConnection.getRoom22()).isEqualTo(UPDATED_ROOM_22);
        assertThat(testRoomConnection.getRoom23()).isEqualTo(UPDATED_ROOM_23);
        assertThat(testRoomConnection.getRoom24()).isEqualTo(UPDATED_ROOM_24);
        assertThat(testRoomConnection.getRoom25()).isEqualTo(UPDATED_ROOM_25);
        assertThat(testRoomConnection.getRoom26()).isEqualTo(UPDATED_ROOM_26);
        assertThat(testRoomConnection.getRoom27()).isEqualTo(UPDATED_ROOM_27);
        assertThat(testRoomConnection.getRoom28()).isEqualTo(UPDATED_ROOM_28);
        assertThat(testRoomConnection.getRoom29()).isEqualTo(UPDATED_ROOM_29);
        assertThat(testRoomConnection.getRoom30()).isEqualTo(UPDATED_ROOM_30);
        assertThat(testRoomConnection.getRoom31()).isEqualTo(UPDATED_ROOM_31);
        assertThat(testRoomConnection.getRoom32()).isEqualTo(UPDATED_ROOM_32);
        assertThat(testRoomConnection.getRoom33()).isEqualTo(UPDATED_ROOM_33);
        assertThat(testRoomConnection.getRoom34()).isEqualTo(UPDATED_ROOM_34);
        assertThat(testRoomConnection.getRoom35()).isEqualTo(UPDATED_ROOM_35);
    }

    @Test
    @Transactional
    void putNonExistingRoomConnection() throws Exception {
        int databaseSizeBeforeUpdate = roomConnectionRepository.findAll().size();
        roomConnection.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoomConnectionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, roomConnection.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomConnection))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoomConnection in the database
        List<RoomConnection> roomConnectionList = roomConnectionRepository.findAll();
        assertThat(roomConnectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRoomConnection() throws Exception {
        int databaseSizeBeforeUpdate = roomConnectionRepository.findAll().size();
        roomConnection.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomConnectionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomConnection))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoomConnection in the database
        List<RoomConnection> roomConnectionList = roomConnectionRepository.findAll();
        assertThat(roomConnectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRoomConnection() throws Exception {
        int databaseSizeBeforeUpdate = roomConnectionRepository.findAll().size();
        roomConnection.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomConnectionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roomConnection)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RoomConnection in the database
        List<RoomConnection> roomConnectionList = roomConnectionRepository.findAll();
        assertThat(roomConnectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRoomConnectionWithPatch() throws Exception {
        // Initialize the database
        roomConnectionRepository.saveAndFlush(roomConnection);

        int databaseSizeBeforeUpdate = roomConnectionRepository.findAll().size();

        // Update the roomConnection using partial update
        RoomConnection partialUpdatedRoomConnection = new RoomConnection();
        partialUpdatedRoomConnection.setId(roomConnection.getId());

        partialUpdatedRoomConnection
            .room1(UPDATED_ROOM_1)
            .room2(UPDATED_ROOM_2)
            .room7(UPDATED_ROOM_7)
            .room8(UPDATED_ROOM_8)
            .room9(UPDATED_ROOM_9)
            .room11(UPDATED_ROOM_11)
            .room19(UPDATED_ROOM_19)
            .room20(UPDATED_ROOM_20)
            .room22(UPDATED_ROOM_22)
            .room27(UPDATED_ROOM_27)
            .room29(UPDATED_ROOM_29)
            .room30(UPDATED_ROOM_30)
            .room31(UPDATED_ROOM_31)
            .room32(UPDATED_ROOM_32)
            .room33(UPDATED_ROOM_33)
            .room35(UPDATED_ROOM_35);

        restRoomConnectionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoomConnection.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoomConnection))
            )
            .andExpect(status().isOk());

        // Validate the RoomConnection in the database
        List<RoomConnection> roomConnectionList = roomConnectionRepository.findAll();
        assertThat(roomConnectionList).hasSize(databaseSizeBeforeUpdate);
        RoomConnection testRoomConnection = roomConnectionList.get(roomConnectionList.size() - 1);
        assertThat(testRoomConnection.getRoom1()).isEqualTo(UPDATED_ROOM_1);
        assertThat(testRoomConnection.getRoom2()).isEqualTo(UPDATED_ROOM_2);
        assertThat(testRoomConnection.getRoom3()).isEqualTo(DEFAULT_ROOM_3);
        assertThat(testRoomConnection.getRoom4()).isEqualTo(DEFAULT_ROOM_4);
        assertThat(testRoomConnection.getRoom5()).isEqualTo(DEFAULT_ROOM_5);
        assertThat(testRoomConnection.getRoom6()).isEqualTo(DEFAULT_ROOM_6);
        assertThat(testRoomConnection.getRoom7()).isEqualTo(UPDATED_ROOM_7);
        assertThat(testRoomConnection.getRoom8()).isEqualTo(UPDATED_ROOM_8);
        assertThat(testRoomConnection.getRoom9()).isEqualTo(UPDATED_ROOM_9);
        assertThat(testRoomConnection.getRoom10()).isEqualTo(DEFAULT_ROOM_10);
        assertThat(testRoomConnection.getRoom11()).isEqualTo(UPDATED_ROOM_11);
        assertThat(testRoomConnection.getRoom12()).isEqualTo(DEFAULT_ROOM_12);
        assertThat(testRoomConnection.getRoom13()).isEqualTo(DEFAULT_ROOM_13);
        assertThat(testRoomConnection.getRoom14()).isEqualTo(DEFAULT_ROOM_14);
        assertThat(testRoomConnection.getRoom15()).isEqualTo(DEFAULT_ROOM_15);
        assertThat(testRoomConnection.getRoom16()).isEqualTo(DEFAULT_ROOM_16);
        assertThat(testRoomConnection.getRoom17()).isEqualTo(DEFAULT_ROOM_17);
        assertThat(testRoomConnection.getRoom18()).isEqualTo(DEFAULT_ROOM_18);
        assertThat(testRoomConnection.getRoom19()).isEqualTo(UPDATED_ROOM_19);
        assertThat(testRoomConnection.getRoom20()).isEqualTo(UPDATED_ROOM_20);
        assertThat(testRoomConnection.getRoom21()).isEqualTo(DEFAULT_ROOM_21);
        assertThat(testRoomConnection.getRoom22()).isEqualTo(UPDATED_ROOM_22);
        assertThat(testRoomConnection.getRoom23()).isEqualTo(DEFAULT_ROOM_23);
        assertThat(testRoomConnection.getRoom24()).isEqualTo(DEFAULT_ROOM_24);
        assertThat(testRoomConnection.getRoom25()).isEqualTo(DEFAULT_ROOM_25);
        assertThat(testRoomConnection.getRoom26()).isEqualTo(DEFAULT_ROOM_26);
        assertThat(testRoomConnection.getRoom27()).isEqualTo(UPDATED_ROOM_27);
        assertThat(testRoomConnection.getRoom28()).isEqualTo(DEFAULT_ROOM_28);
        assertThat(testRoomConnection.getRoom29()).isEqualTo(UPDATED_ROOM_29);
        assertThat(testRoomConnection.getRoom30()).isEqualTo(UPDATED_ROOM_30);
        assertThat(testRoomConnection.getRoom31()).isEqualTo(UPDATED_ROOM_31);
        assertThat(testRoomConnection.getRoom32()).isEqualTo(UPDATED_ROOM_32);
        assertThat(testRoomConnection.getRoom33()).isEqualTo(UPDATED_ROOM_33);
        assertThat(testRoomConnection.getRoom34()).isEqualTo(DEFAULT_ROOM_34);
        assertThat(testRoomConnection.getRoom35()).isEqualTo(UPDATED_ROOM_35);
    }

    @Test
    @Transactional
    void fullUpdateRoomConnectionWithPatch() throws Exception {
        // Initialize the database
        roomConnectionRepository.saveAndFlush(roomConnection);

        int databaseSizeBeforeUpdate = roomConnectionRepository.findAll().size();

        // Update the roomConnection using partial update
        RoomConnection partialUpdatedRoomConnection = new RoomConnection();
        partialUpdatedRoomConnection.setId(roomConnection.getId());

        partialUpdatedRoomConnection
            .room1(UPDATED_ROOM_1)
            .room2(UPDATED_ROOM_2)
            .room3(UPDATED_ROOM_3)
            .room4(UPDATED_ROOM_4)
            .room5(UPDATED_ROOM_5)
            .room6(UPDATED_ROOM_6)
            .room7(UPDATED_ROOM_7)
            .room8(UPDATED_ROOM_8)
            .room9(UPDATED_ROOM_9)
            .room10(UPDATED_ROOM_10)
            .room11(UPDATED_ROOM_11)
            .room12(UPDATED_ROOM_12)
            .room13(UPDATED_ROOM_13)
            .room14(UPDATED_ROOM_14)
            .room15(UPDATED_ROOM_15)
            .room16(UPDATED_ROOM_16)
            .room17(UPDATED_ROOM_17)
            .room18(UPDATED_ROOM_18)
            .room19(UPDATED_ROOM_19)
            .room20(UPDATED_ROOM_20)
            .room21(UPDATED_ROOM_21)
            .room22(UPDATED_ROOM_22)
            .room23(UPDATED_ROOM_23)
            .room24(UPDATED_ROOM_24)
            .room25(UPDATED_ROOM_25)
            .room26(UPDATED_ROOM_26)
            .room27(UPDATED_ROOM_27)
            .room28(UPDATED_ROOM_28)
            .room29(UPDATED_ROOM_29)
            .room30(UPDATED_ROOM_30)
            .room31(UPDATED_ROOM_31)
            .room32(UPDATED_ROOM_32)
            .room33(UPDATED_ROOM_33)
            .room34(UPDATED_ROOM_34)
            .room35(UPDATED_ROOM_35);

        restRoomConnectionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoomConnection.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoomConnection))
            )
            .andExpect(status().isOk());

        // Validate the RoomConnection in the database
        List<RoomConnection> roomConnectionList = roomConnectionRepository.findAll();
        assertThat(roomConnectionList).hasSize(databaseSizeBeforeUpdate);
        RoomConnection testRoomConnection = roomConnectionList.get(roomConnectionList.size() - 1);
        assertThat(testRoomConnection.getRoom1()).isEqualTo(UPDATED_ROOM_1);
        assertThat(testRoomConnection.getRoom2()).isEqualTo(UPDATED_ROOM_2);
        assertThat(testRoomConnection.getRoom3()).isEqualTo(UPDATED_ROOM_3);
        assertThat(testRoomConnection.getRoom4()).isEqualTo(UPDATED_ROOM_4);
        assertThat(testRoomConnection.getRoom5()).isEqualTo(UPDATED_ROOM_5);
        assertThat(testRoomConnection.getRoom6()).isEqualTo(UPDATED_ROOM_6);
        assertThat(testRoomConnection.getRoom7()).isEqualTo(UPDATED_ROOM_7);
        assertThat(testRoomConnection.getRoom8()).isEqualTo(UPDATED_ROOM_8);
        assertThat(testRoomConnection.getRoom9()).isEqualTo(UPDATED_ROOM_9);
        assertThat(testRoomConnection.getRoom10()).isEqualTo(UPDATED_ROOM_10);
        assertThat(testRoomConnection.getRoom11()).isEqualTo(UPDATED_ROOM_11);
        assertThat(testRoomConnection.getRoom12()).isEqualTo(UPDATED_ROOM_12);
        assertThat(testRoomConnection.getRoom13()).isEqualTo(UPDATED_ROOM_13);
        assertThat(testRoomConnection.getRoom14()).isEqualTo(UPDATED_ROOM_14);
        assertThat(testRoomConnection.getRoom15()).isEqualTo(UPDATED_ROOM_15);
        assertThat(testRoomConnection.getRoom16()).isEqualTo(UPDATED_ROOM_16);
        assertThat(testRoomConnection.getRoom17()).isEqualTo(UPDATED_ROOM_17);
        assertThat(testRoomConnection.getRoom18()).isEqualTo(UPDATED_ROOM_18);
        assertThat(testRoomConnection.getRoom19()).isEqualTo(UPDATED_ROOM_19);
        assertThat(testRoomConnection.getRoom20()).isEqualTo(UPDATED_ROOM_20);
        assertThat(testRoomConnection.getRoom21()).isEqualTo(UPDATED_ROOM_21);
        assertThat(testRoomConnection.getRoom22()).isEqualTo(UPDATED_ROOM_22);
        assertThat(testRoomConnection.getRoom23()).isEqualTo(UPDATED_ROOM_23);
        assertThat(testRoomConnection.getRoom24()).isEqualTo(UPDATED_ROOM_24);
        assertThat(testRoomConnection.getRoom25()).isEqualTo(UPDATED_ROOM_25);
        assertThat(testRoomConnection.getRoom26()).isEqualTo(UPDATED_ROOM_26);
        assertThat(testRoomConnection.getRoom27()).isEqualTo(UPDATED_ROOM_27);
        assertThat(testRoomConnection.getRoom28()).isEqualTo(UPDATED_ROOM_28);
        assertThat(testRoomConnection.getRoom29()).isEqualTo(UPDATED_ROOM_29);
        assertThat(testRoomConnection.getRoom30()).isEqualTo(UPDATED_ROOM_30);
        assertThat(testRoomConnection.getRoom31()).isEqualTo(UPDATED_ROOM_31);
        assertThat(testRoomConnection.getRoom32()).isEqualTo(UPDATED_ROOM_32);
        assertThat(testRoomConnection.getRoom33()).isEqualTo(UPDATED_ROOM_33);
        assertThat(testRoomConnection.getRoom34()).isEqualTo(UPDATED_ROOM_34);
        assertThat(testRoomConnection.getRoom35()).isEqualTo(UPDATED_ROOM_35);
    }

    @Test
    @Transactional
    void patchNonExistingRoomConnection() throws Exception {
        int databaseSizeBeforeUpdate = roomConnectionRepository.findAll().size();
        roomConnection.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoomConnectionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, roomConnection.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roomConnection))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoomConnection in the database
        List<RoomConnection> roomConnectionList = roomConnectionRepository.findAll();
        assertThat(roomConnectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRoomConnection() throws Exception {
        int databaseSizeBeforeUpdate = roomConnectionRepository.findAll().size();
        roomConnection.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomConnectionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roomConnection))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoomConnection in the database
        List<RoomConnection> roomConnectionList = roomConnectionRepository.findAll();
        assertThat(roomConnectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRoomConnection() throws Exception {
        int databaseSizeBeforeUpdate = roomConnectionRepository.findAll().size();
        roomConnection.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomConnectionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(roomConnection))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RoomConnection in the database
        List<RoomConnection> roomConnectionList = roomConnectionRepository.findAll();
        assertThat(roomConnectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRoomConnection() throws Exception {
        // Initialize the database
        roomConnectionRepository.saveAndFlush(roomConnection);

        int databaseSizeBeforeDelete = roomConnectionRepository.findAll().size();

        // Delete the roomConnection
        restRoomConnectionMockMvc
            .perform(delete(ENTITY_API_URL_ID, roomConnection.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RoomConnection> roomConnectionList = roomConnectionRepository.findAll();
        assertThat(roomConnectionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
