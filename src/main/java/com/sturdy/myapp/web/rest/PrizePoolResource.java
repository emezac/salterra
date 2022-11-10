package com.sturdy.myapp.web.rest;

import com.sturdy.myapp.domain.PrizePool;
import com.sturdy.myapp.repository.PrizePoolRepository;
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
 * REST controller for managing {@link com.sturdy.myapp.domain.PrizePool}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PrizePoolResource {

    private final Logger log = LoggerFactory.getLogger(PrizePoolResource.class);

    private static final String ENTITY_NAME = "prizePool";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrizePoolRepository prizePoolRepository;

    public PrizePoolResource(PrizePoolRepository prizePoolRepository) {
        this.prizePoolRepository = prizePoolRepository;
    }

    /**
     * {@code POST  /prize-pools} : Create a new prizePool.
     *
     * @param prizePool the prizePool to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prizePool, or with status {@code 400 (Bad Request)} if the prizePool has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/prize-pools")
    public ResponseEntity<PrizePool> createPrizePool(@RequestBody PrizePool prizePool) throws URISyntaxException {
        log.debug("REST request to save PrizePool : {}", prizePool);
        if (prizePool.getId() != null) {
            throw new BadRequestAlertException("A new prizePool cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrizePool result = prizePoolRepository.save(prizePool);
        return ResponseEntity
            .created(new URI("/api/prize-pools/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /prize-pools/:id} : Updates an existing prizePool.
     *
     * @param id the id of the prizePool to save.
     * @param prizePool the prizePool to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prizePool,
     * or with status {@code 400 (Bad Request)} if the prizePool is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prizePool couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/prize-pools/{id}")
    public ResponseEntity<PrizePool> updatePrizePool(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PrizePool prizePool
    ) throws URISyntaxException {
        log.debug("REST request to update PrizePool : {}, {}", id, prizePool);
        if (prizePool.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, prizePool.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!prizePoolRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PrizePool result = prizePoolRepository.save(prizePool);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, prizePool.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /prize-pools/:id} : Partial updates given fields of an existing prizePool, field will ignore if it is null
     *
     * @param id the id of the prizePool to save.
     * @param prizePool the prizePool to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prizePool,
     * or with status {@code 400 (Bad Request)} if the prizePool is not valid,
     * or with status {@code 404 (Not Found)} if the prizePool is not found,
     * or with status {@code 500 (Internal Server Error)} if the prizePool couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/prize-pools/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PrizePool> partialUpdatePrizePool(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PrizePool prizePool
    ) throws URISyntaxException {
        log.debug("REST request to partial update PrizePool partially : {}, {}", id, prizePool);
        if (prizePool.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, prizePool.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!prizePoolRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PrizePool> result = prizePoolRepository
            .findById(prizePool.getId())
            .map(existingPrizePool -> {
                if (prizePool.getPrizeName() != null) {
                    existingPrizePool.setPrizeName(prizePool.getPrizeName());
                }

                return existingPrizePool;
            })
            .map(prizePoolRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, prizePool.getId().toString())
        );
    }

    /**
     * {@code GET  /prize-pools} : get all the prizePools.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prizePools in body.
     */
    @GetMapping("/prize-pools")
    public List<PrizePool> getAllPrizePools() {
        log.debug("REST request to get all PrizePools");
        return prizePoolRepository.findAll();
    }

    /**
     * {@code GET  /prize-pools/:id} : get the "id" prizePool.
     *
     * @param id the id of the prizePool to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prizePool, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/prize-pools/{id}")
    public ResponseEntity<PrizePool> getPrizePool(@PathVariable Long id) {
        log.debug("REST request to get PrizePool : {}", id);
        Optional<PrizePool> prizePool = prizePoolRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(prizePool);
    }

    /**
     * {@code DELETE  /prize-pools/:id} : delete the "id" prizePool.
     *
     * @param id the id of the prizePool to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/prize-pools/{id}")
    public ResponseEntity<Void> deletePrizePool(@PathVariable Long id) {
        log.debug("REST request to delete PrizePool : {}", id);
        prizePoolRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
