package com.sturdy.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A DungeonFloors.
 */
@Entity
@Table(name = "dungeon_floors")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DungeonFloors implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = { "dungeonFloors" }, allowSetters = true)
    private Dungeon dungeon;

    @ManyToOne
    @JsonIgnoreProperties(value = { "dungeonFloors", "floorRooms" }, allowSetters = true)
    private Floor floor;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DungeonFloors id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Dungeon getDungeon() {
        return this.dungeon;
    }

    public void setDungeon(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    public DungeonFloors dungeon(Dungeon dungeon) {
        this.setDungeon(dungeon);
        return this;
    }

    public Floor getFloor() {
        return this.floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }

    public DungeonFloors floor(Floor floor) {
        this.setFloor(floor);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DungeonFloors)) {
            return false;
        }
        return id != null && id.equals(((DungeonFloors) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DungeonFloors{" +
            "id=" + getId() +
            "}";
    }
}
