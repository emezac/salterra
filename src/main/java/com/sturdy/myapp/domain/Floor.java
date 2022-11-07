package com.sturdy.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Floor.
 */
@Entity
@Table(name = "floor")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Floor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "floor")
    @JsonIgnoreProperties(value = { "dungeon", "floor" }, allowSetters = true)
    private Set<DungeonFloors> dungeonFloors = new HashSet<>();

    @OneToMany(mappedBy = "floor")
    @JsonIgnoreProperties(value = { "floor", "room" }, allowSetters = true)
    private Set<FloorRooms> floorRooms = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Floor id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<DungeonFloors> getDungeonFloors() {
        return this.dungeonFloors;
    }

    public void setDungeonFloors(Set<DungeonFloors> dungeonFloors) {
        if (this.dungeonFloors != null) {
            this.dungeonFloors.forEach(i -> i.setFloor(null));
        }
        if (dungeonFloors != null) {
            dungeonFloors.forEach(i -> i.setFloor(this));
        }
        this.dungeonFloors = dungeonFloors;
    }

    public Floor dungeonFloors(Set<DungeonFloors> dungeonFloors) {
        this.setDungeonFloors(dungeonFloors);
        return this;
    }

    public Floor addDungeonFloors(DungeonFloors dungeonFloors) {
        this.dungeonFloors.add(dungeonFloors);
        dungeonFloors.setFloor(this);
        return this;
    }

    public Floor removeDungeonFloors(DungeonFloors dungeonFloors) {
        this.dungeonFloors.remove(dungeonFloors);
        dungeonFloors.setFloor(null);
        return this;
    }

    public Set<FloorRooms> getFloorRooms() {
        return this.floorRooms;
    }

    public void setFloorRooms(Set<FloorRooms> floorRooms) {
        if (this.floorRooms != null) {
            this.floorRooms.forEach(i -> i.setFloor(null));
        }
        if (floorRooms != null) {
            floorRooms.forEach(i -> i.setFloor(this));
        }
        this.floorRooms = floorRooms;
    }

    public Floor floorRooms(Set<FloorRooms> floorRooms) {
        this.setFloorRooms(floorRooms);
        return this;
    }

    public Floor addFloorRooms(FloorRooms floorRooms) {
        this.floorRooms.add(floorRooms);
        floorRooms.setFloor(this);
        return this;
    }

    public Floor removeFloorRooms(FloorRooms floorRooms) {
        this.floorRooms.remove(floorRooms);
        floorRooms.setFloor(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Floor)) {
            return false;
        }
        return id != null && id.equals(((Floor) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Floor{" +
            "id=" + getId() +
            "}";
    }
}
