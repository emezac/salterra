package com.sturdy.myapp.web.rest;

import com.sturdy.myapp.domain.Move;
import com.sturdy.myapp.repository.MoveRepository;
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
 * REST controller for managing {@link com.sturdy.myapp.domain.Move}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MoveResource {

    private final Logger log = LoggerFactory.getLogger(MoveResource.class);

    private static final String ENTITY_NAME = "move";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MoveRepository moveRepository;

    public MoveResource(MoveRepository moveRepository) {
        this.moveRepository = moveRepository;
    }

    /**
     * {@code POST  /moves} : Create a new move.
     *
     * @param move the move to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new move, or with status {@code 400 (Bad Request)} if the move has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/moves")
    public ResponseEntity<Move> createMove(@RequestBody Move move) throws URISyntaxException {
        log.debug("REST request to save Move : {}", move);
        if (move.getId() != null) {
            throw new BadRequestAlertException("A new move cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Move result = moveRepository.save(move);
        return ResponseEntity
            .created(new URI("/api/moves/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /moves/:id} : Updates an existing move.
     *
     * @param id the id of the move to save.
     * @param move the move to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated move,
     * or with status {@code 400 (Bad Request)} if the move is not valid,
     * or with status {@code 500 (Internal Server Error)} if the move couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/moves/{id}")
    public ResponseEntity<Move> updateMove(@PathVariable(value = "id", required = false) final Long id, @RequestBody Move move)
        throws URISyntaxException {
        log.debug("REST request to update Move : {}, {}", id, move);
        if (move.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, move.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!moveRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Move result = moveRepository.save(move);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, move.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /moves/:id} : Partial updates given fields of an existing move, field will ignore if it is null
     *
     * @param id the id of the move to save.
     * @param move the move to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated move,
     * or with status {@code 400 (Bad Request)} if the move is not valid,
     * or with status {@code 404 (Not Found)} if the move is not found,
     * or with status {@code 500 (Internal Server Error)} if the move couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/moves/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Move> partialUpdateMove(@PathVariable(value = "id", required = false) final Long id, @RequestBody Move move)
        throws URISyntaxException {
        log.debug("REST request to partial update Move partially : {}, {}", id, move);
        if (move.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, move.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!moveRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Move> result = moveRepository
            .findById(move.getId())
            .map(existingMove -> {
                if (move.getMoveNumber() != null) {
                    existingMove.setMoveNumber(move.getMoveNumber());
                }
                if (move.getMoveDate() != null) {
                    existingMove.setMoveDate(move.getMoveDate());
                }

                return existingMove;
            })
            .map(moveRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, move.getId().toString())
        );
    }

    /**
     * {@code GET  /moves} : get all the moves.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of moves in body.
     */
    @GetMapping("/moves")
    public List<Move> getAllMoves() {
        log.debug("REST request to get all Moves");
        return moveRepository.findAll();
    }

    /**
     * {@code GET  /moves/:id} : get the "id" move.
     *
     * @param id the id of the move to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the move, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/moves/{id}")
    public ResponseEntity<Move> getMove(@PathVariable Long id) {
        log.debug("REST request to get Move : {}", id);
        Optional<Move> move = moveRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(move);
    }

    /**
     * {@code DELETE  /moves/:id} : delete the "id" move.
     *
     * @param id the id of the move to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/moves/{id}")
    public ResponseEntity<Void> deleteMove(@PathVariable Long id) {
        log.debug("REST request to delete Move : {}", id);
        moveRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
