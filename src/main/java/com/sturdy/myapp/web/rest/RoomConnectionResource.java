package com.sturdy.myapp.web.rest;

import com.sturdy.myapp.domain.RoomConnection;
import com.sturdy.myapp.repository.RoomConnectionRepository;
import com.sturdy.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.sturdy.myapp.domain.RoomConnection}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RoomConnectionResource {

    private final Logger log = LoggerFactory.getLogger(RoomConnectionResource.class);

    private static final String ENTITY_NAME = "roomConnection";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RoomConnectionRepository roomConnectionRepository;

    public RoomConnectionResource(RoomConnectionRepository roomConnectionRepository) {
        this.roomConnectionRepository = roomConnectionRepository;
    }

    /**
     * {@code POST  /room-connections} : Create a new roomConnection.
     *
     * @param roomConnection the roomConnection to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new roomConnection, or with status {@code 400 (Bad Request)} if the roomConnection has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/room-connections")
    public ResponseEntity<RoomConnection> createRoomConnection(@RequestBody RoomConnection roomConnection) throws URISyntaxException {
        log.debug("REST request to save RoomConnection : {}", roomConnection);
        if (roomConnection.getId() != null) {
            throw new BadRequestAlertException("A new roomConnection cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RoomConnection result = roomConnectionRepository.save(roomConnection);
        return ResponseEntity
            .created(new URI("/api/room-connections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /room-connections/:id} : Updates an existing roomConnection.
     *
     * @param id the id of the roomConnection to save.
     * @param roomConnection the roomConnection to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roomConnection,
     * or with status {@code 400 (Bad Request)} if the roomConnection is not valid,
     * or with status {@code 500 (Internal Server Error)} if the roomConnection couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/room-connections/{id}")
    public ResponseEntity<RoomConnection> updateRoomConnection(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RoomConnection roomConnection
    ) throws URISyntaxException {
        log.debug("REST request to update RoomConnection : {}, {}", id, roomConnection);
        if (roomConnection.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, roomConnection.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roomConnectionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RoomConnection result = roomConnectionRepository.save(roomConnection);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, roomConnection.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /room-connections/:id} : Partial updates given fields of an existing roomConnection, field will ignore if it is null
     *
     * @param id the id of the roomConnection to save.
     * @param roomConnection the roomConnection to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roomConnection,
     * or with status {@code 400 (Bad Request)} if the roomConnection is not valid,
     * or with status {@code 404 (Not Found)} if the roomConnection is not found,
     * or with status {@code 500 (Internal Server Error)} if the roomConnection couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/room-connections/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RoomConnection> partialUpdateRoomConnection(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RoomConnection roomConnection
    ) throws URISyntaxException {
        log.debug("REST request to partial update RoomConnection partially : {}, {}", id, roomConnection);
        if (roomConnection.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, roomConnection.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roomConnectionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RoomConnection> result = roomConnectionRepository
            .findById(roomConnection.getId())
            .map(existingRoomConnection -> {
                if (roomConnection.getr1() != null) {
                    existingRoomConnection.setr1(roomConnection.getr1());
                }
                if (roomConnection.getr2() != null) {
                    existingRoomConnection.setr2(roomConnection.getr2());
                }
                if (roomConnection.getr3() != null) {
                    existingRoomConnection.setr3(roomConnection.getr3());
                }
                if (roomConnection.getr4() != null) {
                    existingRoomConnection.setr4(roomConnection.getr4());
                }
                if (roomConnection.getr5() != null) {
                    existingRoomConnection.setr5(roomConnection.getr5());
                }
                if (roomConnection.getr6() != null) {
                    existingRoomConnection.setr6(roomConnection.getr6());
                }
                if (roomConnection.getr7() != null) {
                    existingRoomConnection.setr7(roomConnection.getr7());
                }
                if (roomConnection.getr8() != null) {
                    existingRoomConnection.setr8(roomConnection.getr8());
                }
                if (roomConnection.getr9() != null) {
                    existingRoomConnection.setr9(roomConnection.getr9());
                }
                if (roomConnection.getr10() != null) {
                    existingRoomConnection.setr10(roomConnection.getr10());
                }
                if (roomConnection.getr11() != null) {
                    existingRoomConnection.setr11(roomConnection.getr11());
                }
                if (roomConnection.getr12() != null) {
                    existingRoomConnection.setr12(roomConnection.getr12());
                }
                if (roomConnection.getr13() != null) {
                    existingRoomConnection.setr13(roomConnection.getr13());
                }
                if (roomConnection.getr14() != null) {
                    existingRoomConnection.setr14(roomConnection.getr14());
                }
                if (roomConnection.getr15() != null) {
                    existingRoomConnection.setr15(roomConnection.getr15());
                }
                if (roomConnection.getr16() != null) {
                    existingRoomConnection.setr16(roomConnection.getr16());
                }
                if (roomConnection.getr17() != null) {
                    existingRoomConnection.setr17(roomConnection.getr17());
                }
                if (roomConnection.getr18() != null) {
                    existingRoomConnection.setr18(roomConnection.getr18());
                }
                if (roomConnection.getr19() != null) {
                    existingRoomConnection.setr19(roomConnection.getr19());
                }
                if (roomConnection.getr20() != null) {
                    existingRoomConnection.setr20(roomConnection.getr20());
                }
                if (roomConnection.getr21() != null) {
                    existingRoomConnection.setr21(roomConnection.getr21());
                }
                if (roomConnection.getr22() != null) {
                    existingRoomConnection.setr22(roomConnection.getr22());
                }
                if (roomConnection.getr23() != null) {
                    existingRoomConnection.setr23(roomConnection.getr23());
                }
                if (roomConnection.getr24() != null) {
                    existingRoomConnection.setr24(roomConnection.getr24());
                }
                if (roomConnection.getr25() != null) {
                    existingRoomConnection.setr25(roomConnection.getr25());
                }
                if (roomConnection.getr26() != null) {
                    existingRoomConnection.setr26(roomConnection.getr26());
                }
                if (roomConnection.getr27() != null) {
                    existingRoomConnection.setr27(roomConnection.getr27());
                }
                if (roomConnection.getr28() != null) {
                    existingRoomConnection.setr28(roomConnection.getr28());
                }
                if (roomConnection.getr29() != null) {
                    existingRoomConnection.setr29(roomConnection.getr29());
                }
                if (roomConnection.getr30() != null) {
                    existingRoomConnection.setr30(roomConnection.getr30());
                }
                if (roomConnection.getr31() != null) {
                    existingRoomConnection.setr31(roomConnection.getr31());
                }
                if (roomConnection.getr32() != null) {
                    existingRoomConnection.setr32(roomConnection.getr32());
                }
                if (roomConnection.getr33() != null) {
                    existingRoomConnection.setr33(roomConnection.getr33());
                }
                if (roomConnection.getr34() != null) {
                    existingRoomConnection.setr34(roomConnection.getr34());
                }
                if (roomConnection.getr35() != null) {
                    existingRoomConnection.setr35(roomConnection.getr35());
                }

                return existingRoomConnection;
            })
            .map(roomConnectionRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, roomConnection.getId().toString())
        );
    }

    /**
     * {@code GET  /room-connections} : get all the roomConnections.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of roomConnections in body.
     */
    @GetMapping("/room-connections")
    public List<RoomConnection> getAllRoomConnections() {
        log.debug("REST request to get all RoomConnections");
        return roomConnectionRepository.findAll();
    }

    /**
     * {@code GET  /room-connections/:id} : get the "id" roomConnection.
     *
     * @param id the id of the roomConnection to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the roomConnection, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/room-connections/{id}")
    public ResponseEntity<RoomConnection> getRoomConnection(@PathVariable Long id) {
        log.debug("REST request to get RoomConnection : {}", id);
        Optional<RoomConnection> roomConnection = roomConnectionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(roomConnection);
    }

    /**
     * {@code DELETE  /room-connections/:id} : delete the "id" roomConnection.
     *
     * @param id the id of the roomConnection to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/room-connections/{id}")
    public ResponseEntity<Void> deleteRoomConnection(@PathVariable Long id) {
        log.debug("REST request to delete RoomConnection : {}", id);
        roomConnectionRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
