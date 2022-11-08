package com.sturdy.myapp.web.rest;

import com.sturdy.myapp.domain.ProfileGameStatus;
import com.sturdy.myapp.repository.ProfileGameStatusRepository;
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
 * REST controller for managing {@link com.sturdy.myapp.domain.ProfileGameStatus}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProfileGameStatusResource {

    private final Logger log = LoggerFactory.getLogger(ProfileGameStatusResource.class);

    private static final String ENTITY_NAME = "profileGameStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProfileGameStatusRepository profileGameStatusRepository;

    public ProfileGameStatusResource(ProfileGameStatusRepository profileGameStatusRepository) {
        this.profileGameStatusRepository = profileGameStatusRepository;
    }

    /**
     * {@code POST  /profile-game-statuses} : Create a new profileGameStatus.
     *
     * @param profileGameStatus the profileGameStatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new profileGameStatus, or with status {@code 400 (Bad Request)} if the profileGameStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/profile-game-statuses")
    public ResponseEntity<ProfileGameStatus> createProfileGameStatus(@RequestBody ProfileGameStatus profileGameStatus)
        throws URISyntaxException {
        log.debug("REST request to save ProfileGameStatus : {}", profileGameStatus);
        if (profileGameStatus.getId() != null) {
            throw new BadRequestAlertException("A new profileGameStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProfileGameStatus result = profileGameStatusRepository.save(profileGameStatus);
        return ResponseEntity
            .created(new URI("/api/profile-game-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /profile-game-statuses/:id} : Updates an existing profileGameStatus.
     *
     * @param id the id of the profileGameStatus to save.
     * @param profileGameStatus the profileGameStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated profileGameStatus,
     * or with status {@code 400 (Bad Request)} if the profileGameStatus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the profileGameStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/profile-game-statuses/{id}")
    public ResponseEntity<ProfileGameStatus> updateProfileGameStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProfileGameStatus profileGameStatus
    ) throws URISyntaxException {
        log.debug("REST request to update ProfileGameStatus : {}, {}", id, profileGameStatus);
        if (profileGameStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, profileGameStatus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!profileGameStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProfileGameStatus result = profileGameStatusRepository.save(profileGameStatus);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, profileGameStatus.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /profile-game-statuses/:id} : Partial updates given fields of an existing profileGameStatus, field will ignore if it is null
     *
     * @param id the id of the profileGameStatus to save.
     * @param profileGameStatus the profileGameStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated profileGameStatus,
     * or with status {@code 400 (Bad Request)} if the profileGameStatus is not valid,
     * or with status {@code 404 (Not Found)} if the profileGameStatus is not found,
     * or with status {@code 500 (Internal Server Error)} if the profileGameStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/profile-game-statuses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProfileGameStatus> partialUpdateProfileGameStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProfileGameStatus profileGameStatus
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProfileGameStatus partially : {}, {}", id, profileGameStatus);
        if (profileGameStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, profileGameStatus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!profileGameStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProfileGameStatus> result = profileGameStatusRepository
            .findById(profileGameStatus.getId())
            .map(existingProfileGameStatus -> {
                if (profileGameStatus.getMoveDate() != null) {
                    existingProfileGameStatus.setMoveDate(profileGameStatus.getMoveDate());
                }
                if (profileGameStatus.getLastRoomVisited() != null) {
                    existingProfileGameStatus.setLastRoomVisited(profileGameStatus.getLastRoomVisited());
                }

                return existingProfileGameStatus;
            })
            .map(profileGameStatusRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, profileGameStatus.getId().toString())
        );
    }

    /**
     * {@code GET  /profile-game-statuses} : get all the profileGameStatuses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of profileGameStatuses in body.
     */
    @GetMapping("/profile-game-statuses")
    public List<ProfileGameStatus> getAllProfileGameStatuses() {
        log.debug("REST request to get all ProfileGameStatuses");
        return profileGameStatusRepository.findAll();
    }

    /**
     * {@code GET  /profile-game-statuses/:id} : get the "id" profileGameStatus.
     *
     * @param id the id of the profileGameStatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the profileGameStatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/profile-game-statuses/{id}")
    public ResponseEntity<ProfileGameStatus> getProfileGameStatus(@PathVariable Long id) {
        log.debug("REST request to get ProfileGameStatus : {}", id);
        Optional<ProfileGameStatus> profileGameStatus = profileGameStatusRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(profileGameStatus);
    }

    /**
     * {@code DELETE  /profile-game-statuses/:id} : delete the "id" profileGameStatus.
     *
     * @param id the id of the profileGameStatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/profile-game-statuses/{id}")
    public ResponseEntity<Void> deleteProfileGameStatus(@PathVariable Long id) {
        log.debug("REST request to delete ProfileGameStatus : {}", id);
        profileGameStatusRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
