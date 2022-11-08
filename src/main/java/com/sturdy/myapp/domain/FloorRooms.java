package com.sturdy.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A FloorRooms.
 */
@Entity
@Table(name = "floor_rooms")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FloorRooms implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = { "dungeonFloors", "floorRooms" }, allowSetters = true)
    private Floor floor;

    @ManyToOne
    @JsonIgnoreProperties(value = { "floorRooms", "challenges", "prizes" }, allowSetters = true)
    private Room room;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FloorRooms id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Floor getFloor() {
        return this.floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }

    public FloorRooms floor(Floor floor) {
        this.setFloor(floor);
        return this;
    }

    public Room getRoom() {
        return this.room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public FloorRooms room(Room room) {
        this.setRoom(room);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FloorRooms)) {
            return false;
        }
        return id != null && id.equals(((FloorRooms) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FloorRooms{" +
            "id=" + getId() +
            "}";
    }
}
