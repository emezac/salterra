package com.sturdy.myapp.web.rest;

import com.sturdy.myapp.domain.GameStatus;
import com.sturdy.myapp.repository.GameStatusRepository;
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
 * REST controller for managing {@link com.sturdy.myapp.domain.GameStatus}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GameStatusResource {

    private final Logger log = LoggerFactory.getLogger(GameStatusResource.class);

    private static final String ENTITY_NAME = "gameStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GameStatusRepository gameStatusRepository;

    public GameStatusResource(GameStatusRepository gameStatusRepository) {
        this.gameStatusRepository = gameStatusRepository;
    }

    /**
     * {@code POST  /game-statuses} : Create a new gameStatus.
     *
     * @param gameStatus the gameStatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gameStatus, or with status {@code 400 (Bad Request)} if the gameStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/game-statuses")
    public ResponseEntity<GameStatus> createGameStatus(@RequestBody GameStatus gameStatus) throws URISyntaxException {
        log.debug("REST request to save GameStatus : {}", gameStatus);
        if (gameStatus.getId() != null) {
            throw new BadRequestAlertException("A new gameStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GameStatus result = gameStatusRepository.save(gameStatus);
        return ResponseEntity
            .created(new URI("/api/game-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /game-statuses/:id} : Updates an existing gameStatus.
     *
     * @param id the id of the gameStatus to save.
     * @param gameStatus the gameStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gameStatus,
     * or with status {@code 400 (Bad Request)} if the gameStatus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gameStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/game-statuses/{id}")
    public ResponseEntity<GameStatus> updateGameStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GameStatus gameStatus
    ) throws URISyntaxException {
        log.debug("REST request to update GameStatus : {}, {}", id, gameStatus);
        if (gameStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gameStatus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gameStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GameStatus result = gameStatusRepository.save(gameStatus);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gameStatus.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /game-statuses/:id} : Partial updates given fields of an existing gameStatus, field will ignore if it is null
     *
     * @param id the id of the gameStatus to save.
     * @param gameStatus the gameStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gameStatus,
     * or with status {@code 400 (Bad Request)} if the gameStatus is not valid,
     * or with status {@code 404 (Not Found)} if the gameStatus is not found,
     * or with status {@code 500 (Internal Server Error)} if the gameStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/game-statuses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GameStatus> partialUpdateGameStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GameStatus gameStatus
    ) throws URISyntaxException {
        log.debug("REST request to partial update GameStatus partially : {}, {}", id, gameStatus);
        if (gameStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gameStatus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gameStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GameStatus> result = gameStatusRepository
            .findById(gameStatus.getId())
            .map(existingGameStatus -> {
                if (gameStatus.getMoveDate() != null) {
                    existingGameStatus.setMoveDate(gameStatus.getMoveDate());
                }
                if (gameStatus.getLastRoomVisited() != null) {
                    existingGameStatus.setLastRoomVisited(gameStatus.getLastRoomVisited());
                }

                return existingGameStatus;
            })
            .map(gameStatusRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gameStatus.getId().toString())
        );
    }

    /**
     * {@code GET  /game-statuses} : get all the gameStatuses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gameStatuses in body.
     */
    @GetMapping("/game-statuses")
    public List<GameStatus> getAllGameStatuses() {
        log.debug("REST request to get all GameStatuses");
        return gameStatusRepository.findAll();
    }

    /**
     * {@code GET  /game-statuses/:id} : get the "id" gameStatus.
     *
     * @param id the id of the gameStatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gameStatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/game-statuses/{id}")
    public ResponseEntity<GameStatus> getGameStatus(@PathVariable Long id) {
        log.debug("REST request to get GameStatus : {}", id);
        Optional<GameStatus> gameStatus = gameStatusRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(gameStatus);
    }

    /**
     * {@code DELETE  /game-statuses/:id} : delete the "id" gameStatus.
     *
     * @param id the id of the gameStatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/game-statuses/{id}")
    public ResponseEntity<Void> deleteGameStatus(@PathVariable Long id) {
        log.debug("REST request to delete GameStatus : {}", id);
        gameStatusRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
