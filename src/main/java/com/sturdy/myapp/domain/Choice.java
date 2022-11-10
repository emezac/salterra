package com.sturdy.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Choice.
 */
@Entity
@Table(name = "choice")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Choice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "choice_name")
    private String choiceName;

    @Column(name = "action")
    private String action;

    @Column(name = "text")
    private String text;

    @OneToMany(mappedBy = "option")
    @JsonIgnoreProperties(value = { "option" }, allowSetters = true)
    private Set<Option> options = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "choices", "challenges", "room" }, allowSetters = true)
    private Challenge challenge;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Choice id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChoiceName() {
        return this.choiceName;
    }

    public Choice choiceName(String choiceName) {
        this.setChoiceName(choiceName);
        return this;
    }

    public void setChoiceName(String choiceName) {
        this.choiceName = choiceName;
    }

    public String getAction() {
        return this.action;
    }

    public Choice action(String action) {
        this.setAction(action);
        return this;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getText() {
        return this.text;
    }

    public Choice text(String text) {
        this.setText(text);
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Set<Option> getOptions() {
        return this.options;
    }

    public void setOptions(Set<Option> options) {
        if (this.options != null) {
            this.options.forEach(i -> i.setOption(null));
        }
        if (options != null) {
            options.forEach(i -> i.setOption(this));
        }
        this.options = options;
    }

    public Choice options(Set<Option> options) {
        this.setOptions(options);
        return this;
    }

    public Choice addOption(Option option) {
        this.options.add(option);
        option.setOption(this);
        return this;
    }

    public Choice removeOption(Option option) {
        this.options.remove(option);
        option.setOption(null);
        return this;
    }

    public Challenge getChallenge() {
        return this.challenge;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    public Choice challenge(Challenge challenge) {
        this.setChallenge(challenge);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Choice)) {
            return false;
        }
        return id != null && id.equals(((Choice) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Choice{" +
            "id=" + getId() +
            ", choiceName='" + getChoiceName() + "'" +
            ", action='" + getAction() + "'" +
            ", text='" + getText() + "'" +
            "}";
    }
}
