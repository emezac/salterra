package com.sturdy.myapp.web.rest;

import com.sturdy.myapp.domain.FloorRooms;
import com.sturdy.myapp.repository.FloorRoomsRepository;
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
 * REST controller for managing {@link com.sturdy.myapp.domain.FloorRooms}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FloorRoomsResource {

    private final Logger log = LoggerFactory.getLogger(FloorRoomsResource.class);

    private static final String ENTITY_NAME = "floorRooms";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FloorRoomsRepository floorRoomsRepository;

    public FloorRoomsResource(FloorRoomsRepository floorRoomsRepository) {
        this.floorRoomsRepository = floorRoomsRepository;
    }

    /**
     * {@code POST  /floor-rooms} : Create a new floorRooms.
     *
     * @param floorRooms the floorRooms to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new floorRooms, or with status {@code 400 (Bad Request)} if the floorRooms has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/floor-rooms")
    public ResponseEntity<FloorRooms> createFloorRooms(@RequestBody FloorRooms floorRooms) throws URISyntaxException {
        log.debug("REST request to save FloorRooms : {}", floorRooms);
        if (floorRooms.getId() != null) {
            throw new BadRequestAlertException("A new floorRooms cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FloorRooms result = floorRoomsRepository.save(floorRooms);
        return ResponseEntity
            .created(new URI("/api/floor-rooms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /floor-rooms/:id} : Updates an existing floorRooms.
     *
     * @param id the id of the floorRooms to save.
     * @param floorRooms the floorRooms to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated floorRooms,
     * or with status {@code 400 (Bad Request)} if the floorRooms is not valid,
     * or with status {@code 500 (Internal Server Error)} if the floorRooms couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/floor-rooms/{id}")
    public ResponseEntity<FloorRooms> updateFloorRooms(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FloorRooms floorRooms
    ) throws URISyntaxException {
        log.debug("REST request to update FloorRooms : {}, {}", id, floorRooms);
        if (floorRooms.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, floorRooms.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!floorRoomsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FloorRooms result = floorRoomsRepository.save(floorRooms);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, floorRooms.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /floor-rooms/:id} : Partial updates given fields of an existing floorRooms, field will ignore if it is null
     *
     * @param id the id of the floorRooms to save.
     * @param floorRooms the floorRooms to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated floorRooms,
     * or with status {@code 400 (Bad Request)} if the floorRooms is not valid,
     * or with status {@code 404 (Not Found)} if the floorRooms is not found,
     * or with status {@code 500 (Internal Server Error)} if the floorRooms couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/floor-rooms/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FloorRooms> partialUpdateFloorRooms(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FloorRooms floorRooms
    ) throws URISyntaxException {
        log.debug("REST request to partial update FloorRooms partially : {}, {}", id, floorRooms);
        if (floorRooms.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, floorRooms.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!floorRoomsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FloorRooms> result = floorRoomsRepository
            .findById(floorRooms.getId())
            .map(existingFloorRooms -> {
                return existingFloorRooms;
            })
            .map(floorRoomsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, floorRooms.getId().toString())
        );
    }

    /**
     * {@code GET  /floor-rooms} : get all the floorRooms.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of floorRooms in body.
     */
    @GetMapping("/floor-rooms")
    public List<FloorRooms> getAllFloorRooms() {
        log.debug("REST request to get all FloorRooms");
        return floorRoomsRepository.findAll();
    }

    /**
     * {@code GET  /floor-rooms/:id} : get the "id" floorRooms.
     *
     * @param id the id of the floorRooms to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the floorRooms, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/floor-rooms/{id}")
    public ResponseEntity<FloorRooms> getFloorRooms(@PathVariable Long id) {
        log.debug("REST request to get FloorRooms : {}", id);
        Optional<FloorRooms> floorRooms = floorRoomsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(floorRooms);
    }

    /**
     * {@code DELETE  /floor-rooms/:id} : delete the "id" floorRooms.
     *
     * @param id the id of the floorRooms to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/floor-rooms/{id}")
    public ResponseEntity<Void> deleteFloorRooms(@PathVariable Long id) {
        log.debug("REST request to delete FloorRooms : {}", id);
        floorRoomsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
