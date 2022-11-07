package com.sturdy.myapp.web.rest;

import com.sturdy.myapp.domain.DungeonFloors;
import com.sturdy.myapp.repository.DungeonFloorsRepository;
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
 * REST controller for managing {@link com.sturdy.myapp.domain.DungeonFloors}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DungeonFloorsResource {

    private final Logger log = LoggerFactory.getLogger(DungeonFloorsResource.class);

    private static final String ENTITY_NAME = "dungeonFloors";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DungeonFloorsRepository dungeonFloorsRepository;

    public DungeonFloorsResource(DungeonFloorsRepository dungeonFloorsRepository) {
        this.dungeonFloorsRepository = dungeonFloorsRepository;
    }

    /**
     * {@code POST  /dungeon-floors} : Create a new dungeonFloors.
     *
     * @param dungeonFloors the dungeonFloors to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dungeonFloors, or with status {@code 400 (Bad Request)} if the dungeonFloors has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dungeon-floors")
    public ResponseEntity<DungeonFloors> createDungeonFloors(@RequestBody DungeonFloors dungeonFloors) throws URISyntaxException {
        log.debug("REST request to save DungeonFloors : {}", dungeonFloors);
        if (dungeonFloors.getId() != null) {
            throw new BadRequestAlertException("A new dungeonFloors cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DungeonFloors result = dungeonFloorsRepository.save(dungeonFloors);
        return ResponseEntity
            .created(new URI("/api/dungeon-floors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dungeon-floors/:id} : Updates an existing dungeonFloors.
     *
     * @param id the id of the dungeonFloors to save.
     * @param dungeonFloors the dungeonFloors to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dungeonFloors,
     * or with status {@code 400 (Bad Request)} if the dungeonFloors is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dungeonFloors couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dungeon-floors/{id}")
    public ResponseEntity<DungeonFloors> updateDungeonFloors(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DungeonFloors dungeonFloors
    ) throws URISyntaxException {
        log.debug("REST request to update DungeonFloors : {}, {}", id, dungeonFloors);
        if (dungeonFloors.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dungeonFloors.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dungeonFloorsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DungeonFloors result = dungeonFloorsRepository.save(dungeonFloors);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dungeonFloors.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /dungeon-floors/:id} : Partial updates given fields of an existing dungeonFloors, field will ignore if it is null
     *
     * @param id the id of the dungeonFloors to save.
     * @param dungeonFloors the dungeonFloors to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dungeonFloors,
     * or with status {@code 400 (Bad Request)} if the dungeonFloors is not valid,
     * or with status {@code 404 (Not Found)} if the dungeonFloors is not found,
     * or with status {@code 500 (Internal Server Error)} if the dungeonFloors couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/dungeon-floors/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DungeonFloors> partialUpdateDungeonFloors(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DungeonFloors dungeonFloors
    ) throws URISyntaxException {
        log.debug("REST request to partial update DungeonFloors partially : {}, {}", id, dungeonFloors);
        if (dungeonFloors.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dungeonFloors.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dungeonFloorsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DungeonFloors> result = dungeonFloorsRepository
            .findById(dungeonFloors.getId())
            .map(existingDungeonFloors -> {
                if (dungeonFloors.getDgId() != null) {
                    existingDungeonFloors.setDgId(dungeonFloors.getDgId());
                }
                if (dungeonFloors.getFlId() != null) {
                    existingDungeonFloors.setFlId(dungeonFloors.getFlId());
                }

                return existingDungeonFloors;
            })
            .map(dungeonFloorsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dungeonFloors.getId().toString())
        );
    }

    /**
     * {@code GET  /dungeon-floors} : get all the dungeonFloors.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dungeonFloors in body.
     */
    @GetMapping("/dungeon-floors")
    public List<DungeonFloors> getAllDungeonFloors() {
        log.debug("REST request to get all DungeonFloors");
        return dungeonFloorsRepository.findAll();
    }

    /**
     * {@code GET  /dungeon-floors/:id} : get the "id" dungeonFloors.
     *
     * @param id the id of the dungeonFloors to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dungeonFloors, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dungeon-floors/{id}")
    public ResponseEntity<DungeonFloors> getDungeonFloors(@PathVariable Long id) {
        log.debug("REST request to get DungeonFloors : {}", id);
        Optional<DungeonFloors> dungeonFloors = dungeonFloorsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(dungeonFloors);
    }

    /**
     * {@code DELETE  /dungeon-floors/:id} : delete the "id" dungeonFloors.
     *
     * @param id the id of the dungeonFloors to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dungeon-floors/{id}")
    public ResponseEntity<Void> deleteDungeonFloors(@PathVariable Long id) {
        log.debug("REST request to delete DungeonFloors : {}", id);
        dungeonFloorsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
