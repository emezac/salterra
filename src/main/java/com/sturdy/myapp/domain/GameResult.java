package com.sturdy.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A GameResult.
 */
@Entity
@Table(name = "game_result")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GameResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "won")
    private Boolean won;

    @Column(name = "lost")
    private Boolean lost;

    @Column(name = "created_at")
    private Instant createdAt;

    @ManyToOne
    @JsonIgnoreProperties(value = { "gameResults" }, allowSetters = true)
    private MyUser myUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public GameResult id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getWon() {
        return this.won;
    }

    public GameResult won(Boolean won) {
        this.setWon(won);
        return this;
    }

    public void setWon(Boolean won) {
        this.won = won;
    }

    public Boolean getLost() {
        return this.lost;
    }

    public GameResult lost(Boolean lost) {
        this.setLost(lost);
        return this;
    }

    public void setLost(Boolean lost) {
        this.lost = lost;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public GameResult createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public MyUser getMyUser() {
        return this.myUser;
    }

    public void setMyUser(MyUser myUser) {
        this.myUser = myUser;
    }

    public GameResult myUser(MyUser myUser) {
        this.setMyUser(myUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GameResult)) {
            return false;
        }
        return id != null && id.equals(((GameResult) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GameResult{" +
            "id=" + getId() +
            ", won='" + getWon() + "'" +
            ", lost='" + getLost() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
