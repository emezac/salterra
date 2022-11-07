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

    @Column(name = "dg_id")
    private Long dgId;

    @Column(name = "fl_id")
    private Long flId;

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

    public Long getDgId() {
        return this.dgId;
    }

    public DungeonFloors dgId(Long dgId) {
        this.setDgId(dgId);
        return this;
    }

    public void setDgId(Long dgId) {
        this.dgId = dgId;
    }

    public Long getFlId() {
        return this.flId;
    }

    public DungeonFloors flId(Long flId) {
        this.setFlId(flId);
        return this;
    }

    public void setFlId(Long flId) {
        this.flId = flId;
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
            ", dgId=" + getDgId() +
            ", flId=" + getFlId() +
            "}";
    }
}
