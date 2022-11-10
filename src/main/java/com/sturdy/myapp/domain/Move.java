package com.sturdy.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Move.
 */
@Entity
@Table(name = "move")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Move implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "move_number")
    private String moveNumber;

    @ManyToOne
    @JsonIgnoreProperties(value = { "profile", "moves" }, allowSetters = true)
    private GameStatus gameStatus;

    @ManyToMany(mappedBy = "moves")
    @JsonIgnoreProperties(value = { "choices", "prizePools", "moves", "room" }, allowSetters = true)
    private Set<Challenge> challenges = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Move id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMoveNumber() {
        return this.moveNumber;
    }

    public Move moveNumber(String moveNumber) {
        this.setMoveNumber(moveNumber);
        return this;
    }

    public void setMoveNumber(String moveNumber) {
        this.moveNumber = moveNumber;
    }

    public GameStatus getGameStatus() {
        return this.gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public Move gameStatus(GameStatus gameStatus) {
        this.setGameStatus(gameStatus);
        return this;
    }

    public Set<Challenge> getChallenges() {
        return this.challenges;
    }

    public void setChallenges(Set<Challenge> challenges) {
        if (this.challenges != null) {
            this.challenges.forEach(i -> i.removeMove(this));
        }
        if (challenges != null) {
            challenges.forEach(i -> i.addMove(this));
        }
        this.challenges = challenges;
    }

    public Move challenges(Set<Challenge> challenges) {
        this.setChallenges(challenges);
        return this;
    }

    public Move addChallenge(Challenge challenge) {
        this.challenges.add(challenge);
        challenge.getMoves().add(this);
        return this;
    }

    public Move removeChallenge(Challenge challenge) {
        this.challenges.remove(challenge);
        challenge.getMoves().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Move)) {
            return false;
        }
        return id != null && id.equals(((Move) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Move{" +
            "id=" + getId() +
            ", moveNumber='" + getMoveNumber() + "'" +
            "}";
    }
}
