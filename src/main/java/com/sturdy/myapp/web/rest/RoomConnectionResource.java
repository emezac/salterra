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
                if (roomConnection.getFromId() != null) {
                    existingRoomConnection.setFromId(roomConnection.getFromId());
                }
                if (roomConnection.getToId() != null) {
                    existingRoomConnection.setToId(roomConnection.getToId());
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
