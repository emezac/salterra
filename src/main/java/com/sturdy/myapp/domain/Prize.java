package com.sturdy.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JsonIgnoreProperties(value = { "prizes", "gameStatus" }, allowSetters = true)
    private Profile profile;

    @ManyToOne
    @JsonIgnoreProperties(value = { "prizes" }, allowSetters = true)
    private Card card;

    @ManyToMany(mappedBy = "prizes")
    @JsonIgnoreProperties(value = { "activities", "prizes" }, allowSetters = true)
    private Set<Room> rooms = new HashSet<>();

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

    public String getDescription() {
        return this.description;
    }

    public Prize description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Set<Room> getRooms() {
        return this.rooms;
    }

    public void setRooms(Set<Room> rooms) {
        if (this.rooms != null) {
            this.rooms.forEach(i -> i.removePrize(this));
        }
        if (rooms != null) {
            rooms.forEach(i -> i.addPrize(this));
        }
        this.rooms = rooms;
    }

    public Prize rooms(Set<Room> rooms) {
        this.setRooms(rooms);
        return this;
    }

    public Prize addRoom(Room room) {
        this.rooms.add(room);
        room.getPrizes().add(this);
        return this;
    }

    public Prize removeRoom(Room room) {
        this.rooms.remove(room);
        room.getPrizes().remove(this);
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
            ", description='" + getDescription() + "'" +
            "}";
    }
}
