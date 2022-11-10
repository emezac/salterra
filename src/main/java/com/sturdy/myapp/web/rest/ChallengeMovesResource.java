package com.sturdy.myapp.web.rest;

import com.sturdy.myapp.domain.ChallengeMoves;
import com.sturdy.myapp.repository.ChallengeMovesRepository;
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
 * REST controller for managing {@link com.sturdy.myapp.domain.ChallengeMoves}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ChallengeMovesResource {

    private final Logger log = LoggerFactory.getLogger(ChallengeMovesResource.class);

    private static final String ENTITY_NAME = "challengeMoves";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChallengeMovesRepository challengeMovesRepository;

    public ChallengeMovesResource(ChallengeMovesRepository challengeMovesRepository) {
        this.challengeMovesRepository = challengeMovesRepository;
    }

    /**
     * {@code POST  /challenge-moves} : Create a new challengeMoves.
     *
     * @param challengeMoves the challengeMoves to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new challengeMoves, or with status {@code 400 (Bad Request)} if the challengeMoves has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/challenge-moves")
    public ResponseEntity<ChallengeMoves> createChallengeMoves(@RequestBody ChallengeMoves challengeMoves) throws URISyntaxException {
        log.debug("REST request to save ChallengeMoves : {}", challengeMoves);
        if (challengeMoves.getId() != null) {
            throw new BadRequestAlertException("A new challengeMoves cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChallengeMoves result = challengeMovesRepository.save(challengeMoves);
        return ResponseEntity
            .created(new URI("/api/challenge-moves/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /challenge-moves/:id} : Updates an existing challengeMoves.
     *
     * @param id the id of the challengeMoves to save.
     * @param challengeMoves the challengeMoves to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated challengeMoves,
     * or with status {@code 400 (Bad Request)} if the challengeMoves is not valid,
     * or with status {@code 500 (Internal Server Error)} if the challengeMoves couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/challenge-moves/{id}")
    public ResponseEntity<ChallengeMoves> updateChallengeMoves(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ChallengeMoves challengeMoves
    ) throws URISyntaxException {
        log.debug("REST request to update ChallengeMoves : {}, {}", id, challengeMoves);
        if (challengeMoves.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, challengeMoves.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!challengeMovesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ChallengeMoves result = challengeMovesRepository.save(challengeMoves);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, challengeMoves.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /challenge-moves/:id} : Partial updates given fields of an existing challengeMoves, field will ignore if it is null
     *
     * @param id the id of the challengeMoves to save.
     * @param challengeMoves the challengeMoves to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated challengeMoves,
     * or with status {@code 400 (Bad Request)} if the challengeMoves is not valid,
     * or with status {@code 404 (Not Found)} if the challengeMoves is not found,
     * or with status {@code 500 (Internal Server Error)} if the challengeMoves couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/challenge-moves/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ChallengeMoves> partialUpdateChallengeMoves(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ChallengeMoves challengeMoves
    ) throws URISyntaxException {
        log.debug("REST request to partial update ChallengeMoves partially : {}, {}", id, challengeMoves);
        if (challengeMoves.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, challengeMoves.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!challengeMovesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ChallengeMoves> result = challengeMovesRepository
            .findById(challengeMoves.getId())
            .map(existingChallengeMoves -> {
                if (challengeMoves.getMoveDate() != null) {
                    existingChallengeMoves.setMoveDate(challengeMoves.getMoveDate());
                }

                return existingChallengeMoves;
            })
            .map(challengeMovesRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, challengeMoves.getId().toString())
        );
    }

    /**
     * {@code GET  /challenge-moves} : get all the challengeMoves.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of challengeMoves in body.
     */
    @GetMapping("/challenge-moves")
    public List<ChallengeMoves> getAllChallengeMoves() {
        log.debug("REST request to get all ChallengeMoves");
        return challengeMovesRepository.findAll();
    }

    /**
     * {@code GET  /challenge-moves/:id} : get the "id" challengeMoves.
     *
     * @param id the id of the challengeMoves to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the challengeMoves, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/challenge-moves/{id}")
    public ResponseEntity<ChallengeMoves> getChallengeMoves(@PathVariable Long id) {
        log.debug("REST request to get ChallengeMoves : {}", id);
        Optional<ChallengeMoves> challengeMoves = challengeMovesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(challengeMoves);
    }

    /**
     * {@code DELETE  /challenge-moves/:id} : delete the "id" challengeMoves.
     *
     * @param id the id of the challengeMoves to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/challenge-moves/{id}")
    public ResponseEntity<Void> deleteChallengeMoves(@PathVariable Long id) {
        log.debug("REST request to delete ChallengeMoves : {}", id);
        challengeMovesRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
