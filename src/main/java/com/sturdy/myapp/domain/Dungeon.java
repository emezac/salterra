package com.sturdy.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Dungeon.
 */
@Entity
@Table(name = "dungeon")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Dungeon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @OneToMany(mappedBy = "dungeon")
    @JsonIgnoreProperties(value = { "dungeon", "floor" }, allowSetters = true)
    private Set<DungeonFloors> dungeonFloors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Dungeon id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartDate() {
        return this.startDate;
    }

    public Dungeon startDate(Instant startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public Dungeon endDate(Instant endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Set<DungeonFloors> getDungeonFloors() {
        return this.dungeonFloors;
    }

    public void setDungeonFloors(Set<DungeonFloors> dungeonFloors) {
        if (this.dungeonFloors != null) {
            this.dungeonFloors.forEach(i -> i.setDungeon(null));
        }
        if (dungeonFloors != null) {
            dungeonFloors.forEach(i -> i.setDungeon(this));
        }
        this.dungeonFloors = dungeonFloors;
    }

    public Dungeon dungeonFloors(Set<DungeonFloors> dungeonFloors) {
        this.setDungeonFloors(dungeonFloors);
        return this;
    }

    public Dungeon addDungeonFloors(DungeonFloors dungeonFloors) {
        this.dungeonFloors.add(dungeonFloors);
        dungeonFloors.setDungeon(this);
        return this;
    }

    public Dungeon removeDungeonFloors(DungeonFloors dungeonFloors) {
        this.dungeonFloors.remove(dungeonFloors);
        dungeonFloors.setDungeon(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dungeon)) {
            return false;
        }
        return id != null && id.equals(((Dungeon) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Dungeon{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            "}";
    }
}
