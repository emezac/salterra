package com.sturdy.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.*;

/**
 * A Prize.
 */
@Entity
@Table(name = "prize")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Prize implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "options")
    private String options;

    @ManyToOne
    @JsonIgnoreProperties(value = { "prizes", "gameStatus" }, allowSetters = true)
    private Profile profile;

    @ManyToOne
    @JsonIgnoreProperties(value = { "prizes" }, allowSetters = true)
    private Card card;

    @ManyToOne
    @JsonIgnoreProperties(value = { "challenges", "prizes" }, allowSetters = true)
    private Room room;

    @ManyToOne
    @JsonIgnoreProperties(value = { "prizes", "challenge" }, allowSetters = true)
    private PrizePool prizePool;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Prize id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOptions() {
        return this.options;
    }

    public Prize options(String options) {
        this.setOptions(options);
        return this;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public Profile getProfile() {
        return this.profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Prize profile(Profile profile) {
        this.setProfile(profile);
        return this;
    }

    public Card getCard() {
        return this.card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Prize card(Card card) {
        this.setCard(card);
        return this;
    }

    public Room getRoom() {
        return this.room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Prize room(Room room) {
        this.setRoom(room);
        return this;
    }

    public PrizePool getPrizePool() {
        return this.prizePool;
    }

    public void setPrizePool(PrizePool prizePool) {
        this.prizePool = prizePool;
    }

    public Prize prizePool(PrizePool prizePool) {
        this.setPrizePool(prizePool);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Prize)) {
            return false;
        }
        return id != null && id.equals(((Prize) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Prize{" +
            "id=" + getId() +
            ", options='" + getOptions() + "'" +
            "}";
    }
}
