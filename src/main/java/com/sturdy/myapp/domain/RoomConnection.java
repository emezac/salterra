package com.sturdy.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A RoomConnection.
 */
@Entity
@Table(name = "room_connection")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RoomConnection implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "from_id")
    private Long fromId;

    @Column(name = "to_id")
    private Long toId;

    @ManyToOne
    @JsonIgnoreProperties(value = { "activities", "prizes", "roomConnections" }, allowSetters = true)
    private Room room;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RoomConnection id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFromId() {
        return this.fromId;
    }

    public RoomConnection fromId(Long fromId) {
        this.setFromId(fromId);
        return this;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public Long getToId() {
        return this.toId;
    }

    public RoomConnection toId(Long toId) {
        this.setToId(toId);
        return this;
    }

    public void setToId(Long toId) {
        this.toId = toId;
    }

    public Room getRoom() {
        return this.room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public RoomConnection room(Room room) {
        this.setRoom(room);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoomConnection)) {
            return false;
        }
        return id != null && id.equals(((RoomConnection) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RoomConnection{" +
            "id=" + getId() +
            ", fromId=" + getFromId() +
            ", toId=" + getToId() +
            "}";
    }
}
