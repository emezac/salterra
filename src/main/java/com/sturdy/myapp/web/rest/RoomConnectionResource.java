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
                if (roomConnection.getRoom1() != null) {
                    existingRoomConnection.setRoom1(roomConnection.getRoom1());
                }
                if (roomConnection.getRoom2() != null) {
                    existingRoomConnection.setRoom2(roomConnection.getRoom2());
                }
                if (roomConnection.getRoom3() != null) {
                    existingRoomConnection.setRoom3(roomConnection.getRoom3());
                }
                if (roomConnection.getRoom4() != null) {
                    existingRoomConnection.setRoom4(roomConnection.getRoom4());
                }
                if (roomConnection.getRoom5() != null) {
                    existingRoomConnection.setRoom5(roomConnection.getRoom5());
                }
                if (roomConnection.getRoom6() != null) {
                    existingRoomConnection.setRoom6(roomConnection.getRoom6());
                }
                if (roomConnection.getRoom7() != null) {
                    existingRoomConnection.setRoom7(roomConnection.getRoom7());
                }
                if (roomConnection.getRoom8() != null) {
                    existingRoomConnection.setRoom8(roomConnection.getRoom8());
                }
                if (roomConnection.getRoom9() != null) {
                    existingRoomConnection.setRoom9(roomConnection.getRoom9());
                }
                if (roomConnection.getRoom10() != null) {
                    existingRoomConnection.setRoom10(roomConnection.getRoom10());
                }
                if (roomConnection.getRoom11() != null) {
                    existingRoomConnection.setRoom11(roomConnection.getRoom11());
                }
                if (roomConnection.getRoom12() != null) {
                    existingRoomConnection.setRoom12(roomConnection.getRoom12());
                }
                if (roomConnection.getRoom13() != null) {
                    existingRoomConnection.setRoom13(roomConnection.getRoom13());
                }
                if (roomConnection.getRoom14() != null) {
                    existingRoomConnection.setRoom14(roomConnection.getRoom14());
                }
                if (roomConnection.getRoom15() != null) {
                    existingRoomConnection.setRoom15(roomConnection.getRoom15());
                }
                if (roomConnection.getRoom16() != null) {
                    existingRoomConnection.setRoom16(roomConnection.getRoom16());
                }
                if (roomConnection.getRoom17() != null) {
                    existingRoomConnection.setRoom17(roomConnection.getRoom17());
                }
                if (roomConnection.getRoom18() != null) {
                    existingRoomConnection.setRoom18(roomConnection.getRoom18());
                }
                if (roomConnection.getRoom19() != null) {
                    existingRoomConnection.setRoom19(roomConnection.getRoom19());
                }
                if (roomConnection.getRoom20() != null) {
                    existingRoomConnection.setRoom20(roomConnection.getRoom20());
                }
                if (roomConnection.getRoom21() != null) {
                    existingRoomConnection.setRoom21(roomConnection.getRoom21());
                }
                if (roomConnection.getRoom22() != null) {
                    existingRoomConnection.setRoom22(roomConnection.getRoom22());
                }
                if (roomConnection.getRoom23() != null) {
                    existingRoomConnection.setRoom23(roomConnection.getRoom23());
                }
                if (roomConnection.getRoom24() != null) {
                    existingRoomConnection.setRoom24(roomConnection.getRoom24());
                }
                if (roomConnection.getRoom25() != null) {
                    existingRoomConnection.setRoom25(roomConnection.getRoom25());
                }
                if (roomConnection.getRoom26() != null) {
                    existingRoomConnection.setRoom26(roomConnection.getRoom26());
                }
                if (roomConnection.getRoom27() != null) {
                    existingRoomConnection.setRoom27(roomConnection.getRoom27());
                }
                if (roomConnection.getRoom28() != null) {
                    existingRoomConnection.setRoom28(roomConnection.getRoom28());
                }
                if (roomConnection.getRoom29() != null) {
                    existingRoomConnection.setRoom29(roomConnection.getRoom29());
                }
                if (roomConnection.getRoom30() != null) {
                    existingRoomConnection.setRoom30(roomConnection.getRoom30());
                }
                if (roomConnection.getRoom31() != null) {
                    existingRoomConnection.setRoom31(roomConnection.getRoom31());
                }
                if (roomConnection.getRoom32() != null) {
                    existingRoomConnection.setRoom32(roomConnection.getRoom32());
                }
                if (roomConnection.getRoom33() != null) {
                    existingRoomConnection.setRoom33(roomConnection.getRoom33());
                }
                if (roomConnection.getRoom34() != null) {
                    existingRoomConnection.setRoom34(roomConnection.getRoom34());
                }
                if (roomConnection.getRoom35() != null) {
                    existingRoomConnection.setRoom35(roomConnection.getRoom35());
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
