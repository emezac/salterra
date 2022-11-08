package com.sturdy.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;

/**
 * A ProfileGameStatus.
 */
@Entity
@Table(name = "profile_game_status")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProfileGameStatus implements Serializable {

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

    @JsonIgnoreProperties(value = { "prizes", "profileGameStatus" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Profile profile;

    @OneToMany(mappedBy = "profileGameStatus")
    @JsonIgnoreProperties(value = { "dungeonFloors", "profileGameStatus" }, allowSetters = true)
    private Set<Dungeon> dungeons = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProfileGameStatus id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getMoveDate() {
        return this.moveDate;
    }

    public ProfileGameStatus moveDate(Instant moveDate) {
        this.setMoveDate(moveDate);
        return this;
    }

    public void setMoveDate(Instant moveDate) {
        this.moveDate = moveDate;
    }

    public Long getLastRoomVisited() {
        return this.lastRoomVisited;
    }

    public ProfileGameStatus lastRoomVisited(Long lastRoomVisited) {
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

    public ProfileGameStatus profile(Profile profile) {
        this.setProfile(profile);
        return this;
    }

    public Set<Dungeon> getDungeons() {
        return this.dungeons;
    }

    public void setDungeons(Set<Dungeon> dungeons) {
        if (this.dungeons != null) {
            this.dungeons.forEach(i -> i.setProfileGameStatus(null));
        }
        if (dungeons != null) {
            dungeons.forEach(i -> i.setProfileGameStatus(this));
        }
        this.dungeons = dungeons;
    }

    public ProfileGameStatus dungeons(Set<Dungeon> dungeons) {
        this.setDungeons(dungeons);
        return this;
    }

    public ProfileGameStatus addDungeon(Dungeon dungeon) {
        this.dungeons.add(dungeon);
        dungeon.setProfileGameStatus(this);
        return this;
    }

    public ProfileGameStatus removeDungeon(Dungeon dungeon) {
        this.dungeons.remove(dungeon);
        dungeon.setProfileGameStatus(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProfileGameStatus)) {
            return false;
        }
        return id != null && id.equals(((ProfileGameStatus) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProfileGameStatus{" +
            "id=" + getId() +
            ", moveDate='" + getMoveDate() + "'" +
            ", lastRoomVisited=" + getLastRoomVisited() +
            "}";
    }
}
