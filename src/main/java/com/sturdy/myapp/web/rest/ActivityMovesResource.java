package com.sturdy.myapp.web.rest;

import com.sturdy.myapp.domain.ActivityMoves;
import com.sturdy.myapp.repository.ActivityMovesRepository;
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
 * REST controller for managing {@link com.sturdy.myapp.domain.ActivityMoves}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ActivityMovesResource {

    private final Logger log = LoggerFactory.getLogger(ActivityMovesResource.class);

    private static final String ENTITY_NAME = "activityMoves";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ActivityMovesRepository activityMovesRepository;

    public ActivityMovesResource(ActivityMovesRepository activityMovesRepository) {
        this.activityMovesRepository = activityMovesRepository;
    }

    /**
     * {@code POST  /activity-moves} : Create a new activityMoves.
     *
     * @param activityMoves the activityMoves to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new activityMoves, or with status {@code 400 (Bad Request)} if the activityMoves has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/activity-moves")
    public ResponseEntity<ActivityMoves> createActivityMoves(@RequestBody ActivityMoves activityMoves) throws URISyntaxException {
        log.debug("REST request to save ActivityMoves : {}", activityMoves);
        if (activityMoves.getId() != null) {
            throw new BadRequestAlertException("A new activityMoves cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActivityMoves result = activityMovesRepository.save(activityMoves);
        return ResponseEntity
            .created(new URI("/api/activity-moves/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /activity-moves/:id} : Updates an existing activityMoves.
     *
     * @param id the id of the activityMoves to save.
     * @param activityMoves the activityMoves to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated activityMoves,
     * or with status {@code 400 (Bad Request)} if the activityMoves is not valid,
     * or with status {@code 500 (Internal Server Error)} if the activityMoves couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/activity-moves/{id}")
    public ResponseEntity<ActivityMoves> updateActivityMoves(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ActivityMoves activityMoves
    ) throws URISyntaxException {
        log.debug("REST request to update ActivityMoves : {}, {}", id, activityMoves);
        if (activityMoves.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, activityMoves.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!activityMovesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ActivityMoves result = activityMovesRepository.save(activityMoves);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, activityMoves.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /activity-moves/:id} : Partial updates given fields of an existing activityMoves, field will ignore if it is null
     *
     * @param id the id of the activityMoves to save.
     * @param activityMoves the activityMoves to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated activityMoves,
     * or with status {@code 400 (Bad Request)} if the activityMoves is not valid,
     * or with status {@code 404 (Not Found)} if the activityMoves is not found,
     * or with status {@code 500 (Internal Server Error)} if the activityMoves couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/activity-moves/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ActivityMoves> partialUpdateActivityMoves(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ActivityMoves activityMoves
    ) throws URISyntaxException {
        log.debug("REST request to partial update ActivityMoves partially : {}, {}", id, activityMoves);
        if (activityMoves.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, activityMoves.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!activityMovesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ActivityMoves> result = activityMovesRepository
            .findById(activityMoves.getId())
            .map(existingActivityMoves -> {
                return existingActivityMoves;
            })
            .map(activityMovesRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, activityMoves.getId().toString())
        );
    }

    /**
     * {@code GET  /activity-moves} : get all the activityMoves.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of activityMoves in body.
     */
    @GetMapping("/activity-moves")
    public List<ActivityMoves> getAllActivityMoves(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all ActivityMoves");
        if (eagerload) {
            return activityMovesRepository.findAllWithEagerRelationships();
        } else {
            return activityMovesRepository.findAll();
        }
    }

    /**
     * {@code GET  /activity-moves/:id} : get the "id" activityMoves.
     *
     * @param id the id of the activityMoves to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the activityMoves, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/activity-moves/{id}")
    public ResponseEntity<ActivityMoves> getActivityMoves(@PathVariable Long id) {
        log.debug("REST request to get ActivityMoves : {}", id);
        Optional<ActivityMoves> activityMoves = activityMovesRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(activityMoves);
    }

    /**
     * {@code DELETE  /activity-moves/:id} : delete the "id" activityMoves.
     *
     * @param id the id of the activityMoves to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/activity-moves/{id}")
    public ResponseEntity<Void> deleteActivityMoves(@PathVariable Long id) {
        log.debug("REST request to delete ActivityMoves : {}", id);
        activityMovesRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
