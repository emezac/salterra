package com.sturdy.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;

/**
 * A GameStatus.
 */
@Entity
@Table(name = "game_status")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GameStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "move_date")
    private Instant moveDate;

    @Column(name = "last_room_visited")
    private Long lastRoomVisited;

    @JsonIgnoreProperties(value = { "prizes", "gameStatus" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Profile profile;

    @OneToMany(mappedBy = "gameStatus")
    @JsonIgnoreProperties(value = { "challengeMoves", "gameStatus" }, allowSetters = true)
    private Set<Move> moves = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public GameStatus id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getMoveDate() {
        return this.moveDate;
    }

    public GameStatus moveDate(Instant moveDate) {
        this.setMoveDate(moveDate);
        return this;
    }

    public void setMoveDate(Instant moveDate) {
        this.moveDate = moveDate;
    }

    public Long getLastRoomVisited() {
        return this.lastRoomVisited;
    }

    public GameStatus lastRoomVisited(Long lastRoomVisited) {
        this.setLastRoomVisited(lastRoomVisited);
        return this;
    }

    public void setLastRoomVisited(Long lastRoomVisited) {
        this.lastRoomVisited = lastRoomVisited;
    }

    public Profile getProfile() {
        return this.profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public GameStatus profile(Profile profile) {
        this.setProfile(profile);
        return this;
    }

    public Set<Move> getMoves() {
        return this.moves;
    }

    public void setMoves(Set<Move> moves) {
        if (this.moves != null) {
            this.moves.forEach(i -> i.setGameStatus(null));
        }
        if (moves != null) {
            moves.forEach(i -> i.setGameStatus(this));
        }
        this.moves = moves;
    }

    public GameStatus moves(Set<Move> moves) {
        this.setMoves(moves);
        return this;
    }

    public GameStatus addMove(Move move) {
        this.moves.add(move);
        move.setGameStatus(this);
        return this;
    }

    public GameStatus removeMove(Move move) {
        this.moves.remove(move);
        move.setGameStatus(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GameStatus)) {
            return false;
        }
        return id != null && id.equals(((GameStatus) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GameStatus{" +
            "id=" + getId() +
            ", moveDate='" + getMoveDate() + "'" +
            ", lastRoomVisited=" + getLastRoomVisited() +
            "}";
    }
}
