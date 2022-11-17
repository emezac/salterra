package com.sturdy.myapp.web.rest;

import com.sturdy.myapp.domain.Choices;
import com.sturdy.myapp.repository.ChoicesRepository;
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
 * REST controller for managing {@link com.sturdy.myapp.domain.Choices}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ChoicesResource {

    private final Logger log = LoggerFactory.getLogger(ChoicesResource.class);

    private static final String ENTITY_NAME = "choices";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChoicesRepository choicesRepository;

    public ChoicesResource(ChoicesRepository choicesRepository) {
        this.choicesRepository = choicesRepository;
    }

    /**
     * {@code POST  /choices} : Create a new choices.
     *
     * @param choices the choices to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new choices, or with status {@code 400 (Bad Request)} if the choices has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/choices")
    public ResponseEntity<Choices> createChoices(@RequestBody Choices choices) throws URISyntaxException {
        log.debug("REST request to save Choices : {}", choices);
        if (choices.getId() != null) {
            throw new BadRequestAlertException("A new choices cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Choices result = choicesRepository.save(choices);
        return ResponseEntity
            .created(new URI("/api/choices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /choices/:id} : Updates an existing choices.
     *
     * @param id the id of the choices to save.
     * @param choices the choices to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated choices,
     * or with status {@code 400 (Bad Request)} if the choices is not valid,
     * or with status {@code 500 (Internal Server Error)} if the choices couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/choices/{id}")
    public ResponseEntity<Choices> updateChoices(@PathVariable(value = "id", required = false) final Long id, @RequestBody Choices choices)
        throws URISyntaxException {
        log.debug("REST request to update Choices : {}, {}", id, choices);
        if (choices.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, choices.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!choicesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Choices result = choicesRepository.save(choices);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, choices.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /choices/:id} : Partial updates given fields of an existing choices, field will ignore if it is null
     *
     * @param id the id of the choices to save.
     * @param choices the choices to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated choices,
     * or with status {@code 400 (Bad Request)} if the choices is not valid,
     * or with status {@code 404 (Not Found)} if the choices is not found,
     * or with status {@code 500 (Internal Server Error)} if the choices couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/choices/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Choices> partialUpdateChoices(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Choices choices
    ) throws URISyntaxException {
        log.debug("REST request to partial update Choices partially : {}, {}", id, choices);
        if (choices.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, choices.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!choicesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Choices> result = choicesRepository
            .findById(choices.getId())
            .map(existingChoices -> {
                if (choices.getText() != null) {
                    existingChoices.setText(choices.getText());
                }
                if (choices.getAction() != null) {
                    existingChoices.setAction(choices.getAction());
                }

                return existingChoices;
            })
            .map(choicesRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, choices.getId().toString())
        );
    }

    /**
     * {@code GET  /choices} : get all the choices.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of choices in body.
     */
    @GetMapping("/choices")
    public List<Choices> getAllChoices() {
        log.debug("REST request to get all Choices");
        return choicesRepository.findAll();
    }

    /**
     * {@code GET  /choices/:id} : get the "id" choices.
     *
     * @param id the id of the choices to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the choices, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/choices/{id}")
    public ResponseEntity<Choices> getChoices(@PathVariable Long id) {
        log.debug("REST request to get Choices : {}", id);
        Optional<Choices> choices = choicesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(choices);
    }

    /**
     * {@code DELETE  /choices/:id} : delete the "id" choices.
     *
     * @param id the id of the choices to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/choices/{id}")
    public ResponseEntity<Void> deleteChoices(@PathVariable Long id) {
        log.debug("REST request to delete Choices : {}", id);
        choicesRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
