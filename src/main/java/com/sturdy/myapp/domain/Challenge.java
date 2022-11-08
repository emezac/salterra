package com.sturdy.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sturdy.myapp.domain.enumeration.Difficulty;
import com.sturdy.myapp.domain.enumeration.TypesOfChallenge;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Challenge.
 */
@Entity
@Table(name = "challenge")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Challenge implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "success_text", nullable = false)
    private String successText;

    @NotNull
    @Column(name = "failure_text", nullable = false)
    private String failureText;

    @NotNull
    @Column(name = "challenge_text", nullable = false)
    private String challengeText;

    @Enumerated(EnumType.STRING)
    @Column(name = "challenge_name")
    private TypesOfChallenge challengeName;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty")
    private Difficulty difficulty;

    @ManyToOne
    @JsonIgnoreProperties(value = { "floorRooms", "challenges", "prizes" }, allowSetters = true)
    private Room room;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Challenge id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSuccessText() {
        return this.successText;
    }

    public Challenge successText(String successText) {
        this.setSuccessText(successText);
        return this;
    }

    public void setSuccessText(String successText) {
        this.successText = successText;
    }

    public String getFailureText() {
        return this.failureText;
    }

    public Challenge failureText(String failureText) {
        this.setFailureText(failureText);
        return this;
    }

    public void setFailureText(String failureText) {
        this.failureText = failureText;
    }

    public String getChallengeText() {
        return this.challengeText;
    }

    public Challenge challengeText(String challengeText) {
        this.setChallengeText(challengeText);
        return this;
    }

    public void setChallengeText(String challengeText) {
        this.challengeText = challengeText;
    }

    public TypesOfChallenge getChallengeName() {
        return this.challengeName;
    }

    public Challenge challengeName(TypesOfChallenge challengeName) {
        this.setChallengeName(challengeName);
        return this;
    }

    public void setChallengeName(TypesOfChallenge challengeName) {
        this.challengeName = challengeName;
    }

    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    public Challenge difficulty(Difficulty difficulty) {
        this.setDifficulty(difficulty);
        return this;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Room getRoom() {
        return this.room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Challenge room(Room room) {
        this.setRoom(room);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Challenge)) {
            return false;
        }
        return id != null && id.equals(((Challenge) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Challenge{" +
            "id=" + getId() +
            ", successText='" + getSuccessText() + "'" +
            ", failureText='" + getFailureText() + "'" +
            ", challengeText='" + getChallengeText() + "'" +
            ", challengeName='" + getChallengeName() + "'" +
            ", difficulty='" + getDifficulty() + "'" +
            "}";
    }
}
