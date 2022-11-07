package com.sturdy.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Room.
 */
@Entity
@Table(name = "room")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "intro_text")
    private String introText;

    @Column(name = "have_gateway_door")
    private Boolean haveGatewayDoor;

    @Column(name = "gateway_door")
    private Integer gatewayDoor;

    @OneToMany(mappedBy = "room")
    @JsonIgnoreProperties(value = { "floor", "room" }, allowSetters = true)
    private Set<FloorRooms> floorRooms = new HashSet<>();

    @OneToMany(mappedBy = "room")
    @JsonIgnoreProperties(value = { "room" }, allowSetters = true)
    private Set<Challenge> challenges = new HashSet<>();

    @OneToMany(mappedBy = "room")
    @JsonIgnoreProperties(value = { "profile", "card", "room" }, allowSetters = true)
    private Set<Prize> prizes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Room id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIntroText() {
        return this.introText;
    }

    public Room introText(String introText) {
        this.setIntroText(introText);
        return this;
    }

    public void setIntroText(String introText) {
        this.introText = introText;
    }

    public Boolean getHaveGatewayDoor() {
        return this.haveGatewayDoor;
    }

    public Room haveGatewayDoor(Boolean haveGatewayDoor) {
        this.setHaveGatewayDoor(haveGatewayDoor);
        return this;
    }

    public void setHaveGatewayDoor(Boolean haveGatewayDoor) {
        this.haveGatewayDoor = haveGatewayDoor;
    }

    public Integer getGatewayDoor() {
        return this.gatewayDoor;
    }

    public Room gatewayDoor(Integer gatewayDoor) {
        this.setGatewayDoor(gatewayDoor);
        return this;
    }

    public void setGatewayDoor(Integer gatewayDoor) {
        this.gatewayDoor = gatewayDoor;
    }

    public Set<FloorRooms> getFloorRooms() {
        return this.floorRooms;
    }

    public void setFloorRooms(Set<FloorRooms> floorRooms) {
        if (this.floorRooms != null) {
            this.floorRooms.forEach(i -> i.setRoom(null));
        }
        if (floorRooms != null) {
            floorRooms.forEach(i -> i.setRoom(this));
        }
        this.floorRooms = floorRooms;
    }

    public Room floorRooms(Set<FloorRooms> floorRooms) {
        this.setFloorRooms(floorRooms);
        return this;
    }

    public Room addFloorRooms(FloorRooms floorRooms) {
        this.floorRooms.add(floorRooms);
        floorRooms.setRoom(this);
        return this;
    }

    public Room removeFloorRooms(FloorRooms floorRooms) {
        this.floorRooms.remove(floorRooms);
        floorRooms.setRoom(null);
        return this;
    }

    public Set<Challenge> getChallenges() {
        return this.challenges;
    }

    public void setChallenges(Set<Challenge> challenges) {
        if (this.challenges != null) {
            this.challenges.forEach(i -> i.setRoom(null));
        }
        if (challenges != null) {
            challenges.forEach(i -> i.setRoom(this));
        }
        this.challenges = challenges;
    }

    public Room challenges(Set<Challenge> challenges) {
        this.setChallenges(challenges);
        return this;
    }

    public Room addChallenge(Challenge challenge) {
        this.challenges.add(challenge);
        challenge.setRoom(this);
        return this;
    }

    public Room removeChallenge(Challenge challenge) {
        this.challenges.remove(challenge);
        challenge.setRoom(null);
        return this;
    }

    public Set<Prize> getPrizes() {
        return this.prizes;
    }

    public void setPrizes(Set<Prize> prizes) {
        if (this.prizes != null) {
            this.prizes.forEach(i -> i.setRoom(null));
        }
        if (prizes != null) {
            prizes.forEach(i -> i.setRoom(this));
        }
        this.prizes = prizes;
    }

    public Room prizes(Set<Prize> prizes) {
        this.setPrizes(prizes);
        return this;
    }

    public Room addPrize(Prize prize) {
        this.prizes.add(prize);
        prize.setRoom(this);
        return this;
    }

    public Room removePrize(Prize prize) {
        this.prizes.remove(prize);
        prize.setRoom(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Room)) {
            return false;
        }
        return id != null && id.equals(((Room) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Room{" +
            "id=" + getId() +
            ", introText='" + getIntroText() + "'" +
            ", haveGatewayDoor='" + getHaveGatewayDoor() + "'" +
            ", gatewayDoor=" + getGatewayDoor() +
            "}";
    }
}
