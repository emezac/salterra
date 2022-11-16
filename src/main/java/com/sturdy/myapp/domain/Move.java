package com.sturdy.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
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

    @JsonIgnoreProperties(value = { "choices", "move", "activity" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private ActivityMoves activityMoves;

    @ManyToOne
    @JsonIgnoreProperties(value = { "profile", "moves" }, allowSetters = true)
    private GameStatus gameStatus;

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

    public ActivityMoves getActivityMoves() {
        return this.activityMoves;
    }

    public void setActivityMoves(ActivityMoves activityMoves) {
        this.activityMoves = activityMoves;
    }

    public Move activityMoves(ActivityMoves activityMoves) {
        this.setActivityMoves(activityMoves);
        return this;
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
