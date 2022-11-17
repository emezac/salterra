package com.sturdy.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sturdy.myapp.domain.enumeration.Action;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Choices.
 */
@Entity
@Table(name = "choices")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Choices implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "text")
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(name = "action")
    private Action action;

    @ManyToMany(mappedBy = "choices")
    @JsonIgnoreProperties(value = { "choices", "move", "activity" }, allowSetters = true)
    private Set<ActivityMoves> activityMoves = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Choices id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public Choices text(String text) {
        this.setText(text);
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Action getAction() {
        return this.action;
    }

    public Choices action(Action action) {
        this.setAction(action);
        return this;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Set<ActivityMoves> getActivityMoves() {
        return this.activityMoves;
    }

    public void setActivityMoves(Set<ActivityMoves> activityMoves) {
        if (this.activityMoves != null) {
            this.activityMoves.forEach(i -> i.removeChoices(this));
        }
        if (activityMoves != null) {
            activityMoves.forEach(i -> i.addChoices(this));
        }
        this.activityMoves = activityMoves;
    }

    public Choices activityMoves(Set<ActivityMoves> activityMoves) {
        this.setActivityMoves(activityMoves);
        return this;
    }

    public Choices addActivityMoves(ActivityMoves activityMoves) {
        this.activityMoves.add(activityMoves);
        activityMoves.getChoices().add(this);
        return this;
    }

    public Choices removeActivityMoves(ActivityMoves activityMoves) {
        this.activityMoves.remove(activityMoves);
        activityMoves.getChoices().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Choices)) {
            return false;
        }
        return id != null && id.equals(((Choices) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Choices{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", action='" + getAction() + "'" +
            "}";
    }
}
