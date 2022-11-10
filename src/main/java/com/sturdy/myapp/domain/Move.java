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
    @ManyToMany(mappedBy = "challenges")
    @JsonIgnoreProperties(value = { "profile", "moves" }, allowSetters = true)
    private GameStatus move;

    @ManyToOne
    @ManyToMany(mappedBy = "challenges")
    @JsonIgnoreProperties(value = { "choices", "challenges", "room" }, allowSetters = true)
    private Set<Challenge> moves = new HashSet<>();

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

    public GameStatus getMove() {
        return this.move;
    }

    public void setMove(GameStatus gameStatus) {
        this.move = gameStatus;
    }

    public Move move(GameStatus gameStatus) {
        this.setMove(gameStatus);
        return this;
    }

    public Set<Challenge> getMoves() {
        return this.moves;
    }

    public void setMoves(Set<Challenge> challenges) {
        if (this.moves != null) {
            this.moves.forEach(i -> i.removeChallenge(this));
        }
        if (challenges != null) {
            challenges.forEach(i -> i.addChallenge(this));
        }
        this.moves = challenges;
    }

    public Move moves(Set<Challenge> challenges) {
        this.setMoves(challenges);
        return this;
    }

    public Move addMove(Challenge challenge) {
        this.moves.add(challenge);
        challenge.getChallenges().add(this);
        return this;
    }

    public Move removeMove(Challenge challenge) {
        this.moves.remove(challenge);
        challenge.getChallenges().remove(this);
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
