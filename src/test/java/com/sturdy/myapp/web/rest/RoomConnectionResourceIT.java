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

    private static final Long DEFAULT_R_1 = 1L;
    private static final Long UPDATED_R_1 = 2L;

    private static final Long DEFAULT_R_2 = 1L;
    private static final Long UPDATED_R_2 = 2L;

    private static final Long DEFAULT_R_3 = 1L;
    private static final Long UPDATED_R_3 = 2L;

    private static final Long DEFAULT_R_4 = 1L;
    private static final Long UPDATED_R_4 = 2L;

    private static final Long DEFAULT_R_5 = 1L;
    private static final Long UPDATED_R_5 = 2L;

    private static final Long DEFAULT_R_6 = 1L;
    private static final Long UPDATED_R_6 = 2L;

    private static final Long DEFAULT_R_7 = 1L;
    private static final Long UPDATED_R_7 = 2L;

    private static final Long DEFAULT_R_8 = 1L;
    private static final Long UPDATED_R_8 = 2L;

    private static final Long DEFAULT_R_9 = 1L;
    private static final Long UPDATED_R_9 = 2L;

    private static final Long DEFAULT_R_10 = 1L;
    private static final Long UPDATED_R_10 = 2L;

    private static final Long DEFAULT_R_11 = 1L;
    private static final Long UPDATED_R_11 = 2L;

    private static final Long DEFAULT_R_12 = 1L;
    private static final Long UPDATED_R_12 = 2L;

    private static final Long DEFAULT_R_13 = 1L;
    private static final Long UPDATED_R_13 = 2L;

    private static final Long DEFAULT_R_14 = 1L;
    private static final Long UPDATED_R_14 = 2L;

    private static final Long DEFAULT_R_15 = 1L;
    private static final Long UPDATED_R_15 = 2L;

    private static final Long DEFAULT_R_16 = 1L;
    private static final Long UPDATED_R_16 = 2L;

    private static final Long DEFAULT_R_17 = 1L;
    private static final Long UPDATED_R_17 = 2L;

    private static final Long DEFAULT_R_18 = 1L;
    private static final Long UPDATED_R_18 = 2L;

    private static final Long DEFAULT_R_19 = 1L;
    private static final Long UPDATED_R_19 = 2L;

    private static final Long DEFAULT_R_20 = 1L;
    private static final Long UPDATED_R_20 = 2L;

    private static final Long DEFAULT_R_21 = 1L;
    private static final Long UPDATED_R_21 = 2L;

    private static final Long DEFAULT_R_22 = 1L;
    private static final Long UPDATED_R_22 = 2L;

    private static final Long DEFAULT_R_23 = 1L;
    private static final Long UPDATED_R_23 = 2L;

    private static final Long DEFAULT_R_24 = 1L;
    private static final Long UPDATED_R_24 = 2L;

    private static final Long DEFAULT_R_25 = 1L;
    private static final Long UPDATED_R_25 = 2L;

    private static final Long DEFAULT_R_26 = 1L;
    private static final Long UPDATED_R_26 = 2L;

    private static final Long DEFAULT_R_27 = 1L;
    private static final Long UPDATED_R_27 = 2L;

    private static final Long DEFAULT_R_28 = 1L;
    private static final Long UPDATED_R_28 = 2L;

    private static final Long DEFAULT_R_29 = 1L;
    private static final Long UPDATED_R_29 = 2L;

    private static final Long DEFAULT_R_30 = 1L;
    private static final Long UPDATED_R_30 = 2L;

    private static final Long DEFAULT_R_31 = 1L;
    private static final Long UPDATED_R_31 = 2L;

    private static final Long DEFAULT_R_32 = 1L;
    private static final Long UPDATED_R_32 = 2L;

    private static final Long DEFAULT_R_33 = 1L;
    private static final Long UPDATED_R_33 = 2L;

    private static final Long DEFAULT_R_34 = 1L;
    private static final Long UPDATED_R_34 = 2L;

    private static final Long DEFAULT_R_35 = 1L;
    private static final Long UPDATED_R_35 = 2L;

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
            .r1(DEFAULT_R_1)
            .r2(DEFAULT_R_2)
            .r3(DEFAULT_R_3)
            .r4(DEFAULT_R_4)
            .r5(DEFAULT_R_5)
            .r6(DEFAULT_R_6)
            .r7(DEFAULT_R_7)
            .r8(DEFAULT_R_8)
            .r9(DEFAULT_R_9)
            .r10(DEFAULT_R_10)
            .r11(DEFAULT_R_11)
            .r12(DEFAULT_R_12)
            .r13(DEFAULT_R_13)
            .r14(DEFAULT_R_14)
            .r15(DEFAULT_R_15)
            .r16(DEFAULT_R_16)
            .r17(DEFAULT_R_17)
            .r18(DEFAULT_R_18)
            .r19(DEFAULT_R_19)
            .r20(DEFAULT_R_20)
            .r21(DEFAULT_R_21)
            .r22(DEFAULT_R_22)
            .r23(DEFAULT_R_23)
            .r24(DEFAULT_R_24)
            .r25(DEFAULT_R_25)
            .r26(DEFAULT_R_26)
            .r27(DEFAULT_R_27)
            .r28(DEFAULT_R_28)
            .r29(DEFAULT_R_29)
            .r30(DEFAULT_R_30)
            .r31(DEFAULT_R_31)
            .r32(DEFAULT_R_32)
            .r33(DEFAULT_R_33)
            .r34(DEFAULT_R_34)
            .r35(DEFAULT_R_35);
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
            .r1(UPDATED_R_1)
            .r2(UPDATED_R_2)
            .r3(UPDATED_R_3)
            .r4(UPDATED_R_4)
            .r5(UPDATED_R_5)
            .r6(UPDATED_R_6)
            .r7(UPDATED_R_7)
            .r8(UPDATED_R_8)
            .r9(UPDATED_R_9)
            .r10(UPDATED_R_10)
            .r11(UPDATED_R_11)
            .r12(UPDATED_R_12)
            .r13(UPDATED_R_13)
            .r14(UPDATED_R_14)
            .r15(UPDATED_R_15)
            .r16(UPDATED_R_16)
            .r17(UPDATED_R_17)
            .r18(UPDATED_R_18)
            .r19(UPDATED_R_19)
            .r20(UPDATED_R_20)
            .r21(UPDATED_R_21)
            .r22(UPDATED_R_22)
            .r23(UPDATED_R_23)
            .r24(UPDATED_R_24)
            .r25(UPDATED_R_25)
            .r26(UPDATED_R_26)
            .r27(UPDATED_R_27)
            .r28(UPDATED_R_28)
            .r29(UPDATED_R_29)
            .r30(UPDATED_R_30)
            .r31(UPDATED_R_31)
            .r32(UPDATED_R_32)
            .r33(UPDATED_R_33)
            .r34(UPDATED_R_34)
            .r35(UPDATED_R_35);
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
        assertThat(testRoomConnection.getr1()).isEqualTo(DEFAULT_R_1);
        assertThat(testRoomConnection.getr2()).isEqualTo(DEFAULT_R_2);
        assertThat(testRoomConnection.getr3()).isEqualTo(DEFAULT_R_3);
        assertThat(testRoomConnection.getr4()).isEqualTo(DEFAULT_R_4);
        assertThat(testRoomConnection.getr5()).isEqualTo(DEFAULT_R_5);
        assertThat(testRoomConnection.getr6()).isEqualTo(DEFAULT_R_6);
        assertThat(testRoomConnection.getr7()).isEqualTo(DEFAULT_R_7);
        assertThat(testRoomConnection.getr8()).isEqualTo(DEFAULT_R_8);
        assertThat(testRoomConnection.getr9()).isEqualTo(DEFAULT_R_9);
        assertThat(testRoomConnection.getr10()).isEqualTo(DEFAULT_R_10);
        assertThat(testRoomConnection.getr11()).isEqualTo(DEFAULT_R_11);
        assertThat(testRoomConnection.getr12()).isEqualTo(DEFAULT_R_12);
        assertThat(testRoomConnection.getr13()).isEqualTo(DEFAULT_R_13);
        assertThat(testRoomConnection.getr14()).isEqualTo(DEFAULT_R_14);
        assertThat(testRoomConnection.getr15()).isEqualTo(DEFAULT_R_15);
        assertThat(testRoomConnection.getr16()).isEqualTo(DEFAULT_R_16);
        assertThat(testRoomConnection.getr17()).isEqualTo(DEFAULT_R_17);
        assertThat(testRoomConnection.getr18()).isEqualTo(DEFAULT_R_18);
        assertThat(testRoomConnection.getr19()).isEqualTo(DEFAULT_R_19);
        assertThat(testRoomConnection.getr20()).isEqualTo(DEFAULT_R_20);
        assertThat(testRoomConnection.getr21()).isEqualTo(DEFAULT_R_21);
        assertThat(testRoomConnection.getr22()).isEqualTo(DEFAULT_R_22);
        assertThat(testRoomConnection.getr23()).isEqualTo(DEFAULT_R_23);
        assertThat(testRoomConnection.getr24()).isEqualTo(DEFAULT_R_24);
        assertThat(testRoomConnection.getr25()).isEqualTo(DEFAULT_R_25);
        assertThat(testRoomConnection.getr26()).isEqualTo(DEFAULT_R_26);
        assertThat(testRoomConnection.getr27()).isEqualTo(DEFAULT_R_27);
        assertThat(testRoomConnection.getr28()).isEqualTo(DEFAULT_R_28);
        assertThat(testRoomConnection.getr29()).isEqualTo(DEFAULT_R_29);
        assertThat(testRoomConnection.getr30()).isEqualTo(DEFAULT_R_30);
        assertThat(testRoomConnection.getr31()).isEqualTo(DEFAULT_R_31);
        assertThat(testRoomConnection.getr32()).isEqualTo(DEFAULT_R_32);
        assertThat(testRoomConnection.getr33()).isEqualTo(DEFAULT_R_33);
        assertThat(testRoomConnection.getr34()).isEqualTo(DEFAULT_R_34);
        assertThat(testRoomConnection.getr35()).isEqualTo(DEFAULT_R_35);
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
            .andExpect(jsonPath("$.[*].r1").value(hasItem(DEFAULT_R_1.intValue())))
            .andExpect(jsonPath("$.[*].r2").value(hasItem(DEFAULT_R_2.intValue())))
            .andExpect(jsonPath("$.[*].r3").value(hasItem(DEFAULT_R_3.intValue())))
            .andExpect(jsonPath("$.[*].r4").value(hasItem(DEFAULT_R_4.intValue())))
            .andExpect(jsonPath("$.[*].r5").value(hasItem(DEFAULT_R_5.intValue())))
            .andExpect(jsonPath("$.[*].r6").value(hasItem(DEFAULT_R_6.intValue())))
            .andExpect(jsonPath("$.[*].r7").value(hasItem(DEFAULT_R_7.intValue())))
            .andExpect(jsonPath("$.[*].r8").value(hasItem(DEFAULT_R_8.intValue())))
            .andExpect(jsonPath("$.[*].r9").value(hasItem(DEFAULT_R_9.intValue())))
            .andExpect(jsonPath("$.[*].r10").value(hasItem(DEFAULT_R_10.intValue())))
            .andExpect(jsonPath("$.[*].r11").value(hasItem(DEFAULT_R_11.intValue())))
            .andExpect(jsonPath("$.[*].r12").value(hasItem(DEFAULT_R_12.intValue())))
            .andExpect(jsonPath("$.[*].r13").value(hasItem(DEFAULT_R_13.intValue())))
            .andExpect(jsonPath("$.[*].r14").value(hasItem(DEFAULT_R_14.intValue())))
            .andExpect(jsonPath("$.[*].r15").value(hasItem(DEFAULT_R_15.intValue())))
            .andExpect(jsonPath("$.[*].r16").value(hasItem(DEFAULT_R_16.intValue())))
            .andExpect(jsonPath("$.[*].r17").value(hasItem(DEFAULT_R_17.intValue())))
            .andExpect(jsonPath("$.[*].r18").value(hasItem(DEFAULT_R_18.intValue())))
            .andExpect(jsonPath("$.[*].r19").value(hasItem(DEFAULT_R_19.intValue())))
            .andExpect(jsonPath("$.[*].r20").value(hasItem(DEFAULT_R_20.intValue())))
            .andExpect(jsonPath("$.[*].r21").value(hasItem(DEFAULT_R_21.intValue())))
            .andExpect(jsonPath("$.[*].r22").value(hasItem(DEFAULT_R_22.intValue())))
            .andExpect(jsonPath("$.[*].r23").value(hasItem(DEFAULT_R_23.intValue())))
            .andExpect(jsonPath("$.[*].r24").value(hasItem(DEFAULT_R_24.intValue())))
            .andExpect(jsonPath("$.[*].r25").value(hasItem(DEFAULT_R_25.intValue())))
            .andExpect(jsonPath("$.[*].r26").value(hasItem(DEFAULT_R_26.intValue())))
            .andExpect(jsonPath("$.[*].r27").value(hasItem(DEFAULT_R_27.intValue())))
            .andExpect(jsonPath("$.[*].r28").value(hasItem(DEFAULT_R_28.intValue())))
            .andExpect(jsonPath("$.[*].r29").value(hasItem(DEFAULT_R_29.intValue())))
            .andExpect(jsonPath("$.[*].r30").value(hasItem(DEFAULT_R_30.intValue())))
            .andExpect(jsonPath("$.[*].r31").value(hasItem(DEFAULT_R_31.intValue())))
            .andExpect(jsonPath("$.[*].r32").value(hasItem(DEFAULT_R_32.intValue())))
            .andExpect(jsonPath("$.[*].r33").value(hasItem(DEFAULT_R_33.intValue())))
            .andExpect(jsonPath("$.[*].r34").value(hasItem(DEFAULT_R_34.intValue())))
            .andExpect(jsonPath("$.[*].r35").value(hasItem(DEFAULT_R_35.intValue())));
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
            .andExpect(jsonPath("$.r1").value(DEFAULT_R_1.intValue()))
            .andExpect(jsonPath("$.r2").value(DEFAULT_R_2.intValue()))
            .andExpect(jsonPath("$.r3").value(DEFAULT_R_3.intValue()))
            .andExpect(jsonPath("$.r4").value(DEFAULT_R_4.intValue()))
            .andExpect(jsonPath("$.r5").value(DEFAULT_R_5.intValue()))
            .andExpect(jsonPath("$.r6").value(DEFAULT_R_6.intValue()))
            .andExpect(jsonPath("$.r7").value(DEFAULT_R_7.intValue()))
            .andExpect(jsonPath("$.r8").value(DEFAULT_R_8.intValue()))
            .andExpect(jsonPath("$.r9").value(DEFAULT_R_9.intValue()))
            .andExpect(jsonPath("$.r10").value(DEFAULT_R_10.intValue()))
            .andExpect(jsonPath("$.r11").value(DEFAULT_R_11.intValue()))
            .andExpect(jsonPath("$.r12").value(DEFAULT_R_12.intValue()))
            .andExpect(jsonPath("$.r13").value(DEFAULT_R_13.intValue()))
            .andExpect(jsonPath("$.r14").value(DEFAULT_R_14.intValue()))
            .andExpect(jsonPath("$.r15").value(DEFAULT_R_15.intValue()))
            .andExpect(jsonPath("$.r16").value(DEFAULT_R_16.intValue()))
            .andExpect(jsonPath("$.r17").value(DEFAULT_R_17.intValue()))
            .andExpect(jsonPath("$.r18").value(DEFAULT_R_18.intValue()))
            .andExpect(jsonPath("$.r19").value(DEFAULT_R_19.intValue()))
            .andExpect(jsonPath("$.r20").value(DEFAULT_R_20.intValue()))
            .andExpect(jsonPath("$.r21").value(DEFAULT_R_21.intValue()))
            .andExpect(jsonPath("$.r22").value(DEFAULT_R_22.intValue()))
            .andExpect(jsonPath("$.r23").value(DEFAULT_R_23.intValue()))
            .andExpect(jsonPath("$.r24").value(DEFAULT_R_24.intValue()))
            .andExpect(jsonPath("$.r25").value(DEFAULT_R_25.intValue()))
            .andExpect(jsonPath("$.r26").value(DEFAULT_R_26.intValue()))
            .andExpect(jsonPath("$.r27").value(DEFAULT_R_27.intValue()))
            .andExpect(jsonPath("$.r28").value(DEFAULT_R_28.intValue()))
            .andExpect(jsonPath("$.r29").value(DEFAULT_R_29.intValue()))
            .andExpect(jsonPath("$.r30").value(DEFAULT_R_30.intValue()))
            .andExpect(jsonPath("$.r31").value(DEFAULT_R_31.intValue()))
            .andExpect(jsonPath("$.r32").value(DEFAULT_R_32.intValue()))
            .andExpect(jsonPath("$.r33").value(DEFAULT_R_33.intValue()))
            .andExpect(jsonPath("$.r34").value(DEFAULT_R_34.intValue()))
            .andExpect(jsonPath("$.r35").value(DEFAULT_R_35.intValue()));
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
            .r1(UPDATED_R_1)
            .r2(UPDATED_R_2)
            .r3(UPDATED_R_3)
            .r4(UPDATED_R_4)
            .r5(UPDATED_R_5)
            .r6(UPDATED_R_6)
            .r7(UPDATED_R_7)
            .r8(UPDATED_R_8)
            .r9(UPDATED_R_9)
            .r10(UPDATED_R_10)
            .r11(UPDATED_R_11)
            .r12(UPDATED_R_12)
            .r13(UPDATED_R_13)
            .r14(UPDATED_R_14)
            .r15(UPDATED_R_15)
            .r16(UPDATED_R_16)
            .r17(UPDATED_R_17)
            .r18(UPDATED_R_18)
            .r19(UPDATED_R_19)
            .r20(UPDATED_R_20)
            .r21(UPDATED_R_21)
            .r22(UPDATED_R_22)
            .r23(UPDATED_R_23)
            .r24(UPDATED_R_24)
            .r25(UPDATED_R_25)
            .r26(UPDATED_R_26)
            .r27(UPDATED_R_27)
            .r28(UPDATED_R_28)
            .r29(UPDATED_R_29)
            .r30(UPDATED_R_30)
            .r31(UPDATED_R_31)
            .r32(UPDATED_R_32)
            .r33(UPDATED_R_33)
            .r34(UPDATED_R_34)
            .r35(UPDATED_R_35);

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
        assertThat(testRoomConnection.getr1()).isEqualTo(UPDATED_R_1);
        assertThat(testRoomConnection.getr2()).isEqualTo(UPDATED_R_2);
        assertThat(testRoomConnection.getr3()).isEqualTo(UPDATED_R_3);
        assertThat(testRoomConnection.getr4()).isEqualTo(UPDATED_R_4);
        assertThat(testRoomConnection.getr5()).isEqualTo(UPDATED_R_5);
        assertThat(testRoomConnection.getr6()).isEqualTo(UPDATED_R_6);
        assertThat(testRoomConnection.getr7()).isEqualTo(UPDATED_R_7);
        assertThat(testRoomConnection.getr8()).isEqualTo(UPDATED_R_8);
        assertThat(testRoomConnection.getr9()).isEqualTo(UPDATED_R_9);
        assertThat(testRoomConnection.getr10()).isEqualTo(UPDATED_R_10);
        assertThat(testRoomConnection.getr11()).isEqualTo(UPDATED_R_11);
        assertThat(testRoomConnection.getr12()).isEqualTo(UPDATED_R_12);
        assertThat(testRoomConnection.getr13()).isEqualTo(UPDATED_R_13);
        assertThat(testRoomConnection.getr14()).isEqualTo(UPDATED_R_14);
        assertThat(testRoomConnection.getr15()).isEqualTo(UPDATED_R_15);
        assertThat(testRoomConnection.getr16()).isEqualTo(UPDATED_R_16);
        assertThat(testRoomConnection.getr17()).isEqualTo(UPDATED_R_17);
        assertThat(testRoomConnection.getr18()).isEqualTo(UPDATED_R_18);
        assertThat(testRoomConnection.getr19()).isEqualTo(UPDATED_R_19);
        assertThat(testRoomConnection.getr20()).isEqualTo(UPDATED_R_20);
        assertThat(testRoomConnection.getr21()).isEqualTo(UPDATED_R_21);
        assertThat(testRoomConnection.getr22()).isEqualTo(UPDATED_R_22);
        assertThat(testRoomConnection.getr23()).isEqualTo(UPDATED_R_23);
        assertThat(testRoomConnection.getr24()).isEqualTo(UPDATED_R_24);
        assertThat(testRoomConnection.getr25()).isEqualTo(UPDATED_R_25);
        assertThat(testRoomConnection.getr26()).isEqualTo(UPDATED_R_26);
        assertThat(testRoomConnection.getr27()).isEqualTo(UPDATED_R_27);
        assertThat(testRoomConnection.getr28()).isEqualTo(UPDATED_R_28);
        assertThat(testRoomConnection.getr29()).isEqualTo(UPDATED_R_29);
        assertThat(testRoomConnection.getr30()).isEqualTo(UPDATED_R_30);
        assertThat(testRoomConnection.getr31()).isEqualTo(UPDATED_R_31);
        assertThat(testRoomConnection.getr32()).isEqualTo(UPDATED_R_32);
        assertThat(testRoomConnection.getr33()).isEqualTo(UPDATED_R_33);
        assertThat(testRoomConnection.getr34()).isEqualTo(UPDATED_R_34);
        assertThat(testRoomConnection.getr35()).isEqualTo(UPDATED_R_35);
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
            .r1(UPDATED_R_1)
            .r2(UPDATED_R_2)
            .r7(UPDATED_R_7)
            .r8(UPDATED_R_8)
            .r9(UPDATED_R_9)
            .r11(UPDATED_R_11)
            .r19(UPDATED_R_19)
            .r20(UPDATED_R_20)
            .r22(UPDATED_R_22)
            .r27(UPDATED_R_27)
            .r29(UPDATED_R_29)
            .r30(UPDATED_R_30)
            .r31(UPDATED_R_31)
            .r32(UPDATED_R_32)
            .r33(UPDATED_R_33)
            .r35(UPDATED_R_35);

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
        assertThat(testRoomConnection.getr1()).isEqualTo(UPDATED_R_1);
        assertThat(testRoomConnection.getr2()).isEqualTo(UPDATED_R_2);
        assertThat(testRoomConnection.getr3()).isEqualTo(DEFAULT_R_3);
        assertThat(testRoomConnection.getr4()).isEqualTo(DEFAULT_R_4);
        assertThat(testRoomConnection.getr5()).isEqualTo(DEFAULT_R_5);
        assertThat(testRoomConnection.getr6()).isEqualTo(DEFAULT_R_6);
        assertThat(testRoomConnection.getr7()).isEqualTo(UPDATED_R_7);
        assertThat(testRoomConnection.getr8()).isEqualTo(UPDATED_R_8);
        assertThat(testRoomConnection.getr9()).isEqualTo(UPDATED_R_9);
        assertThat(testRoomConnection.getr10()).isEqualTo(DEFAULT_R_10);
        assertThat(testRoomConnection.getr11()).isEqualTo(UPDATED_R_11);
        assertThat(testRoomConnection.getr12()).isEqualTo(DEFAULT_R_12);
        assertThat(testRoomConnection.getr13()).isEqualTo(DEFAULT_R_13);
        assertThat(testRoomConnection.getr14()).isEqualTo(DEFAULT_R_14);
        assertThat(testRoomConnection.getr15()).isEqualTo(DEFAULT_R_15);
        assertThat(testRoomConnection.getr16()).isEqualTo(DEFAULT_R_16);
        assertThat(testRoomConnection.getr17()).isEqualTo(DEFAULT_R_17);
        assertThat(testRoomConnection.getr18()).isEqualTo(DEFAULT_R_18);
        assertThat(testRoomConnection.getr19()).isEqualTo(UPDATED_R_19);
        assertThat(testRoomConnection.getr20()).isEqualTo(UPDATED_R_20);
        assertThat(testRoomConnection.getr21()).isEqualTo(DEFAULT_R_21);
        assertThat(testRoomConnection.getr22()).isEqualTo(UPDATED_R_22);
        assertThat(testRoomConnection.getr23()).isEqualTo(DEFAULT_R_23);
        assertThat(testRoomConnection.getr24()).isEqualTo(DEFAULT_R_24);
        assertThat(testRoomConnection.getr25()).isEqualTo(DEFAULT_R_25);
        assertThat(testRoomConnection.getr26()).isEqualTo(DEFAULT_R_26);
        assertThat(testRoomConnection.getr27()).isEqualTo(UPDATED_R_27);
        assertThat(testRoomConnection.getr28()).isEqualTo(DEFAULT_R_28);
        assertThat(testRoomConnection.getr29()).isEqualTo(UPDATED_R_29);
        assertThat(testRoomConnection.getr30()).isEqualTo(UPDATED_R_30);
        assertThat(testRoomConnection.getr31()).isEqualTo(UPDATED_R_31);
        assertThat(testRoomConnection.getr32()).isEqualTo(UPDATED_R_32);
        assertThat(testRoomConnection.getr33()).isEqualTo(UPDATED_R_33);
        assertThat(testRoomConnection.getr34()).isEqualTo(DEFAULT_R_34);
        assertThat(testRoomConnection.getr35()).isEqualTo(UPDATED_R_35);
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
            .r1(UPDATED_R_1)
            .r2(UPDATED_R_2)
            .r3(UPDATED_R_3)
            .r4(UPDATED_R_4)
            .r5(UPDATED_R_5)
            .r6(UPDATED_R_6)
            .r7(UPDATED_R_7)
            .r8(UPDATED_R_8)
            .r9(UPDATED_R_9)
            .r10(UPDATED_R_10)
            .r11(UPDATED_R_11)
            .r12(UPDATED_R_12)
            .r13(UPDATED_R_13)
            .r14(UPDATED_R_14)
            .r15(UPDATED_R_15)
            .r16(UPDATED_R_16)
            .r17(UPDATED_R_17)
            .r18(UPDATED_R_18)
            .r19(UPDATED_R_19)
            .r20(UPDATED_R_20)
            .r21(UPDATED_R_21)
            .r22(UPDATED_R_22)
            .r23(UPDATED_R_23)
            .r24(UPDATED_R_24)
            .r25(UPDATED_R_25)
            .r26(UPDATED_R_26)
            .r27(UPDATED_R_27)
            .r28(UPDATED_R_28)
            .r29(UPDATED_R_29)
            .r30(UPDATED_R_30)
            .r31(UPDATED_R_31)
            .r32(UPDATED_R_32)
            .r33(UPDATED_R_33)
            .r34(UPDATED_R_34)
            .r35(UPDATED_R_35);

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
        assertThat(testRoomConnection.getr1()).isEqualTo(UPDATED_R_1);
        assertThat(testRoomConnection.getr2()).isEqualTo(UPDATED_R_2);
        assertThat(testRoomConnection.getr3()).isEqualTo(UPDATED_R_3);
        assertThat(testRoomConnection.getr4()).isEqualTo(UPDATED_R_4);
        assertThat(testRoomConnection.getr5()).isEqualTo(UPDATED_R_5);
        assertThat(testRoomConnection.getr6()).isEqualTo(UPDATED_R_6);
        assertThat(testRoomConnection.getr7()).isEqualTo(UPDATED_R_7);
        assertThat(testRoomConnection.getr8()).isEqualTo(UPDATED_R_8);
        assertThat(testRoomConnection.getr9()).isEqualTo(UPDATED_R_9);
        assertThat(testRoomConnection.getr10()).isEqualTo(UPDATED_R_10);
        assertThat(testRoomConnection.getr11()).isEqualTo(UPDATED_R_11);
        assertThat(testRoomConnection.getr12()).isEqualTo(UPDATED_R_12);
        assertThat(testRoomConnection.getr13()).isEqualTo(UPDATED_R_13);
        assertThat(testRoomConnection.getr14()).isEqualTo(UPDATED_R_14);
        assertThat(testRoomConnection.getr15()).isEqualTo(UPDATED_R_15);
        assertThat(testRoomConnection.getr16()).isEqualTo(UPDATED_R_16);
        assertThat(testRoomConnection.getr17()).isEqualTo(UPDATED_R_17);
        assertThat(testRoomConnection.getr18()).isEqualTo(UPDATED_R_18);
        assertThat(testRoomConnection.getr19()).isEqualTo(UPDATED_R_19);
        assertThat(testRoomConnection.getr20()).isEqualTo(UPDATED_R_20);
        assertThat(testRoomConnection.getr21()).isEqualTo(UPDATED_R_21);
        assertThat(testRoomConnection.getr22()).isEqualTo(UPDATED_R_22);
        assertThat(testRoomConnection.getr23()).isEqualTo(UPDATED_R_23);
        assertThat(testRoomConnection.getr24()).isEqualTo(UPDATED_R_24);
        assertThat(testRoomConnection.getr25()).isEqualTo(UPDATED_R_25);
        assertThat(testRoomConnection.getr26()).isEqualTo(UPDATED_R_26);
        assertThat(testRoomConnection.getr27()).isEqualTo(UPDATED_R_27);
        assertThat(testRoomConnection.getr28()).isEqualTo(UPDATED_R_28);
        assertThat(testRoomConnection.getr29()).isEqualTo(UPDATED_R_29);
        assertThat(testRoomConnection.getr30()).isEqualTo(UPDATED_R_30);
        assertThat(testRoomConnection.getr31()).isEqualTo(UPDATED_R_31);
        assertThat(testRoomConnection.getr32()).isEqualTo(UPDATED_R_32);
        assertThat(testRoomConnection.getr33()).isEqualTo(UPDATED_R_33);
        assertThat(testRoomConnection.getr34()).isEqualTo(UPDATED_R_34);
        assertThat(testRoomConnection.getr35()).isEqualTo(UPDATED_R_35);
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
