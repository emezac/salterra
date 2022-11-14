package com.sturdy.myapp.web.rest;

import com.sturdy.myapp.domain.ChallengeOption;
import com.sturdy.myapp.repository.ChallengeOptionRepository;
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
 * REST controller for managing {@link com.sturdy.myapp.domain.ChallengeOption}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ChallengeOptionResource {

    private final Logger log = LoggerFactory.getLogger(ChallengeOptionResource.class);

    private static final String ENTITY_NAME = "challengeOption";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChallengeOptionRepository challengeOptionRepository;

    public ChallengeOptionResource(ChallengeOptionRepository challengeOptionRepository) {
        this.challengeOptionRepository = challengeOptionRepository;
    }

    /**
     * {@code POST  /challenge-options} : Create a new challengeOption.
     *
     * @param challengeOption the challengeOption to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new challengeOption, or with status {@code 400 (Bad Request)} if the challengeOption has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/challenge-options")
    public ResponseEntity<ChallengeOption> createChallengeOption(@RequestBody ChallengeOption challengeOption) throws URISyntaxException {
        log.debug("REST request to save ChallengeOption : {}", challengeOption);
        if (challengeOption.getId() != null) {
            throw new BadRequestAlertException("A new challengeOption cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChallengeOption result = challengeOptionRepository.save(challengeOption);
        return ResponseEntity
            .created(new URI("/api/challenge-options/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /challenge-options/:id} : Updates an existing challengeOption.
     *
     * @param id the id of the challengeOption to save.
     * @param challengeOption the challengeOption to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated challengeOption,
     * or with status {@code 400 (Bad Request)} if the challengeOption is not valid,
     * or with status {@code 500 (Internal Server Error)} if the challengeOption couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/challenge-options/{id}")
    public ResponseEntity<ChallengeOption> updateChallengeOption(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ChallengeOption challengeOption
    ) throws URISyntaxException {
        log.debug("REST request to update ChallengeOption : {}, {}", id, challengeOption);
        if (challengeOption.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, challengeOption.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!challengeOptionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ChallengeOption result = challengeOptionRepository.save(challengeOption);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, challengeOption.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /challenge-options/:id} : Partial updates given fields of an existing challengeOption, field will ignore if it is null
     *
     * @param id the id of the challengeOption to save.
     * @param challengeOption the challengeOption to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated challengeOption,
     * or with status {@code 400 (Bad Request)} if the challengeOption is not valid,
     * or with status {@code 404 (Not Found)} if the challengeOption is not found,
     * or with status {@code 500 (Internal Server Error)} if the challengeOption couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/challenge-options/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ChallengeOption> partialUpdateChallengeOption(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ChallengeOption challengeOption
    ) throws URISyntaxException {
        log.debug("REST request to partial update ChallengeOption partially : {}, {}", id, challengeOption);
        if (challengeOption.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, challengeOption.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!challengeOptionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ChallengeOption> result = challengeOptionRepository
            .findById(challengeOption.getId())
            .map(existingChallengeOption -> {
                if (challengeOption.getOptionName() != null) {
                    existingChallengeOption.setOptionName(challengeOption.getOptionName());
                }
                if (challengeOption.getOption() != null) {
                    existingChallengeOption.setOption(challengeOption.getOption());
                }

                return existingChallengeOption;
            })
            .map(challengeOptionRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, challengeOption.getId().toString())
        );
    }

    /**
     * {@code GET  /challenge-options} : get all the challengeOptions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of challengeOptions in body.
     */
    @GetMapping("/challenge-options")
    public List<ChallengeOption> getAllChallengeOptions() {
        log.debug("REST request to get all ChallengeOptions");
        return challengeOptionRepository.findAll();
    }

    /**
     * {@code GET  /challenge-options/:id} : get the "id" challengeOption.
     *
     * @param id the id of the challengeOption to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the challengeOption, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/challenge-options/{id}")
    public ResponseEntity<ChallengeOption> getChallengeOption(@PathVariable Long id) {
        log.debug("REST request to get ChallengeOption : {}", id);
        Optional<ChallengeOption> challengeOption = challengeOptionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(challengeOption);
    }

    /**
     * {@code DELETE  /challenge-options/:id} : delete the "id" challengeOption.
     *
     * @param id the id of the challengeOption to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/challenge-options/{id}")
    public ResponseEntity<Void> deleteChallengeOption(@PathVariable Long id) {
        log.debug("REST request to delete ChallengeOption : {}", id);
        challengeOptionRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
