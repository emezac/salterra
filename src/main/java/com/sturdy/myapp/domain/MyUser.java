package com.sturdy.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A MyUser.
 */
@Entity
@Table(name = "my_user")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MyUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "wallet_address")
    private String walletAddress;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @OneToMany(mappedBy = "myUser")
    @JsonIgnoreProperties(value = { "myUser" }, allowSetters = true)
    private Set<GameResult> gameResults = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MyUser id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWalletAddress() {
        return this.walletAddress;
    }

    public MyUser walletAddress(String walletAddress) {
        this.setWalletAddress(walletAddress);
        return this;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public MyUser createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public MyUser updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<GameResult> getGameResults() {
        return this.gameResults;
    }

    public void setGameResults(Set<GameResult> gameResults) {
        if (this.gameResults != null) {
            this.gameResults.forEach(i -> i.setMyUser(null));
        }
        if (gameResults != null) {
            gameResults.forEach(i -> i.setMyUser(this));
        }
        this.gameResults = gameResults;
    }

    public MyUser gameResults(Set<GameResult> gameResults) {
        this.setGameResults(gameResults);
        return this;
    }

    public MyUser addGameResult(GameResult gameResult) {
        this.gameResults.add(gameResult);
        gameResult.setMyUser(this);
        return this;
    }

    public MyUser removeGameResult(GameResult gameResult) {
        this.gameResults.remove(gameResult);
        gameResult.setMyUser(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MyUser)) {
            return false;
        }
        return id != null && id.equals(((MyUser) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MyUser{" +
            "id=" + getId() +
            ", walletAddress='" + getWalletAddress() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
