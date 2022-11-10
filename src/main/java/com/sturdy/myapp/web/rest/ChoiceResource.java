package com.sturdy.myapp.web.rest;

import com.sturdy.myapp.domain.Choice;
import com.sturdy.myapp.repository.ChoiceRepository;
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
 * REST controller for managing {@link com.sturdy.myapp.domain.Choice}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ChoiceResource {

    private final Logger log = LoggerFactory.getLogger(ChoiceResource.class);

    private static final String ENTITY_NAME = "choice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChoiceRepository choiceRepository;

    public ChoiceResource(ChoiceRepository choiceRepository) {
        this.choiceRepository = choiceRepository;
    }

    /**
     * {@code POST  /choices} : Create a new choice.
     *
     * @param choice the choice to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new choice, or with status {@code 400 (Bad Request)} if the choice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/choices")
    public ResponseEntity<Choice> createChoice(@RequestBody Choice choice) throws URISyntaxException {
        log.debug("REST request to save Choice : {}", choice);
        if (choice.getId() != null) {
            throw new BadRequestAlertException("A new choice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Choice result = choiceRepository.save(choice);
        return ResponseEntity
            .created(new URI("/api/choices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /choices/:id} : Updates an existing choice.
     *
     * @param id the id of the choice to save.
     * @param choice the choice to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated choice,
     * or with status {@code 400 (Bad Request)} if the choice is not valid,
     * or with status {@code 500 (Internal Server Error)} if the choice couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/choices/{id}")
    public ResponseEntity<Choice> updateChoice(@PathVariable(value = "id", required = false) final Long id, @RequestBody Choice choice)
        throws URISyntaxException {
        log.debug("REST request to update Choice : {}, {}", id, choice);
        if (choice.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, choice.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!choiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Choice result = choiceRepository.save(choice);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, choice.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /choices/:id} : Partial updates given fields of an existing choice, field will ignore if it is null
     *
     * @param id the id of the choice to save.
     * @param choice the choice to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated choice,
     * or with status {@code 400 (Bad Request)} if the choice is not valid,
     * or with status {@code 404 (Not Found)} if the choice is not found,
     * or with status {@code 500 (Internal Server Error)} if the choice couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/choices/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Choice> partialUpdateChoice(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Choice choice
    ) throws URISyntaxException {
        log.debug("REST request to partial update Choice partially : {}, {}", id, choice);
        if (choice.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, choice.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!choiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Choice> result = choiceRepository
            .findById(choice.getId())
            .map(existingChoice -> {
                if (choice.getChoiceName() != null) {
                    existingChoice.setChoiceName(choice.getChoiceName());
                }
                if (choice.getAction() != null) {
                    existingChoice.setAction(choice.getAction());
                }
                if (choice.getText() != null) {
                    existingChoice.setText(choice.getText());
                }

                return existingChoice;
            })
            .map(choiceRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, choice.getId().toString())
        );
    }

    /**
     * {@code GET  /choices} : get all the choices.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of choices in body.
     */
    @GetMapping("/choices")
    public List<Choice> getAllChoices() {
        log.debug("REST request to get all Choices");
        return choiceRepository.findAll();
    }

    /**
     * {@code GET  /choices/:id} : get the "id" choice.
     *
     * @param id the id of the choice to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the choice, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/choices/{id}")
    public ResponseEntity<Choice> getChoice(@PathVariable Long id) {
        log.debug("REST request to get Choice : {}", id);
        Optional<Choice> choice = choiceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(choice);
    }

    /**
     * {@code DELETE  /choices/:id} : delete the "id" choice.
     *
     * @param id the id of the choice to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/choices/{id}")
    public ResponseEntity<Void> deleteChoice(@PathVariable Long id) {
        log.debug("REST request to delete Choice : {}", id);
        choiceRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
