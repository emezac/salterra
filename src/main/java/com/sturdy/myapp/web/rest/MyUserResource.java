package com.sturdy.myapp.web.rest;

import com.sturdy.myapp.domain.MyUser;
import com.sturdy.myapp.repository.MyUserRepository;
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
 * REST controller for managing {@link com.sturdy.myapp.domain.MyUser}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MyUserResource {

    private final Logger log = LoggerFactory.getLogger(MyUserResource.class);

    private static final String ENTITY_NAME = "myUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MyUserRepository myUserRepository;

    public MyUserResource(MyUserRepository myUserRepository) {
        this.myUserRepository = myUserRepository;
    }

    /**
     * {@code POST  /my-users} : Create a new myUser.
     *
     * @param myUser the myUser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new myUser, or with status {@code 400 (Bad Request)} if the myUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/my-users")
    public ResponseEntity<MyUser> createMyUser(@RequestBody MyUser myUser) throws URISyntaxException {
        log.debug("REST request to save MyUser : {}", myUser);
        if (myUser.getId() != null) {
            throw new BadRequestAlertException("A new myUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MyUser result = myUserRepository.save(myUser);
        return ResponseEntity
            .created(new URI("/api/my-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /my-users/:id} : Updates an existing myUser.
     *
     * @param id the id of the myUser to save.
     * @param myUser the myUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated myUser,
     * or with status {@code 400 (Bad Request)} if the myUser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the myUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/my-users/{id}")
    public ResponseEntity<MyUser> updateMyUser(@PathVariable(value = "id", required = false) final Long id, @RequestBody MyUser myUser)
        throws URISyntaxException {
        log.debug("REST request to update MyUser : {}, {}", id, myUser);
        if (myUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, myUser.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!myUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MyUser result = myUserRepository.save(myUser);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, myUser.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /my-users/:id} : Partial updates given fields of an existing myUser, field will ignore if it is null
     *
     * @param id the id of the myUser to save.
     * @param myUser the myUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated myUser,
     * or with status {@code 400 (Bad Request)} if the myUser is not valid,
     * or with status {@code 404 (Not Found)} if the myUser is not found,
     * or with status {@code 500 (Internal Server Error)} if the myUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/my-users/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MyUser> partialUpdateMyUser(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MyUser myUser
    ) throws URISyntaxException {
        log.debug("REST request to partial update MyUser partially : {}, {}", id, myUser);
        if (myUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, myUser.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!myUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MyUser> result = myUserRepository
            .findById(myUser.getId())
            .map(existingMyUser -> {
                if (myUser.getWalletAddress() != null) {
                    existingMyUser.setWalletAddress(myUser.getWalletAddress());
                }
                if (myUser.getCreatedAt() != null) {
                    existingMyUser.setCreatedAt(myUser.getCreatedAt());
                }
                if (myUser.getUpdatedAt() != null) {
                    existingMyUser.setUpdatedAt(myUser.getUpdatedAt());
                }

                return existingMyUser;
            })
            .map(myUserRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, myUser.getId().toString())
        );
    }

    /**
     * {@code GET  /my-users} : get all the myUsers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of myUsers in body.
     */
    @GetMapping("/my-users")
    public List<MyUser> getAllMyUsers() {
        log.debug("REST request to get all MyUsers");
        return myUserRepository.findAll();
    }

    /**
     * {@code GET  /my-users/:id} : get the "id" myUser.
     *
     * @param id the id of the myUser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the myUser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/my-users/{id}")
    public ResponseEntity<MyUser> getMyUser(@PathVariable Long id) {
        log.debug("REST request to get MyUser : {}", id);
        Optional<MyUser> myUser = myUserRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(myUser);
    }

    /**
     * {@code DELETE  /my-users/:id} : delete the "id" myUser.
     *
     * @param id the id of the myUser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/my-users/{id}")
    public ResponseEntity<Void> deleteMyUser(@PathVariable Long id) {
        log.debug("REST request to delete MyUser : {}", id);
        myUserRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
