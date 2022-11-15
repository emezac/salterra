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

    @Column(name = "gwy_door_1")
    private Integer gwyDoor1;

    @Column(name = "gwy_door_2")
    private Integer gwyDoor2;

    @Column(name = "gwy_door_3")
    private Integer gwyDoor3;

    @Column(name = "gwy_door_4")
    private Integer gwyDoor4;

    @Column(name = "gwy_door_5")
    private Integer gwyDoor5;

    @Column(name = "gwy_door_6")
    private Integer gwyDoor6;

    @OneToMany(mappedBy = "room")
    @JsonIgnoreProperties(value = { "challengeOptions", "prizePools", "activityMoves", "room" }, allowSetters = true)
    private Set<Activity> activities = new HashSet<>();

    @OneToMany(mappedBy = "room")
    @JsonIgnoreProperties(value = { "profile", "card", "room", "prizePool" }, allowSetters = true)
    private Set<Prize> prizes = new HashSet<>();

    @OneToMany(mappedBy = "room")
    @JsonIgnoreProperties(value = { "room" }, allowSetters = true)
    private Set<RoomConnection> roomConnections = new HashSet<>();

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

    public Integer getGwyDoor1() {
        return this.gwyDoor1;
    }

    public Room gwyDoor1(Integer gwyDoor1) {
        this.setGwyDoor1(gwyDoor1);
        return this;
    }

    public void setGwyDoor1(Integer gwyDoor1) {
        this.gwyDoor1 = gwyDoor1;
    }

    public Integer getGwyDoor2() {
        return this.gwyDoor2;
    }

    public Room gwyDoor2(Integer gwyDoor2) {
        this.setGwyDoor2(gwyDoor2);
        return this;
    }

    public void setGwyDoor2(Integer gwyDoor2) {
        this.gwyDoor2 = gwyDoor2;
    }

    public Integer getGwyDoor3() {
        return this.gwyDoor3;
    }

    public Room gwyDoor3(Integer gwyDoor3) {
        this.setGwyDoor3(gwyDoor3);
        return this;
    }

    public void setGwyDoor3(Integer gwyDoor3) {
        this.gwyDoor3 = gwyDoor3;
    }

    public Integer getGwyDoor4() {
        return this.gwyDoor4;
    }

    public Room gwyDoor4(Integer gwyDoor4) {
        this.setGwyDoor4(gwyDoor4);
        return this;
    }

    public void setGwyDoor4(Integer gwyDoor4) {
        this.gwyDoor4 = gwyDoor4;
    }

    public Integer getGwyDoor5() {
        return this.gwyDoor5;
    }

    public Room gwyDoor5(Integer gwyDoor5) {
        this.setGwyDoor5(gwyDoor5);
        return this;
    }

    public void setGwyDoor5(Integer gwyDoor5) {
        this.gwyDoor5 = gwyDoor5;
    }

    public Integer getGwyDoor6() {
        return this.gwyDoor6;
    }

    public Room gwyDoor6(Integer gwyDoor6) {
        this.setGwyDoor6(gwyDoor6);
        return this;
    }

    public void setGwyDoor6(Integer gwyDoor6) {
        this.gwyDoor6 = gwyDoor6;
    }

    public Set<Activity> getActivities() {
        return this.activities;
    }

    public void setActivities(Set<Activity> activities) {
        if (this.activities != null) {
            this.activities.forEach(i -> i.setRoom(null));
        }
        if (activities != null) {
            activities.forEach(i -> i.setRoom(this));
        }
        this.activities = activities;
    }

    public Room activities(Set<Activity> activities) {
        this.setActivities(activities);
        return this;
    }

    public Room addActivity(Activity activity) {
        this.activities.add(activity);
        activity.setRoom(this);
        return this;
    }

    public Room removeActivity(Activity activity) {
        this.activities.remove(activity);
        activity.setRoom(null);
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

    public Set<RoomConnection> getRoomConnections() {
        return this.roomConnections;
    }

    public void setRoomConnections(Set<RoomConnection> roomConnections) {
        if (this.roomConnections != null) {
            this.roomConnections.forEach(i -> i.setRoom(null));
        }
        if (roomConnections != null) {
            roomConnections.forEach(i -> i.setRoom(this));
        }
        this.roomConnections = roomConnections;
    }

    public Room roomConnections(Set<RoomConnection> roomConnections) {
        this.setRoomConnections(roomConnections);
        return this;
    }

    public Room addRoomConnection(RoomConnection roomConnection) {
        this.roomConnections.add(roomConnection);
        roomConnection.setRoom(this);
        return this;
    }

    public Room removeRoomConnection(RoomConnection roomConnection) {
        this.roomConnections.remove(roomConnection);
        roomConnection.setRoom(null);
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
            ", gwyDoor1=" + getGwyDoor1() +
            ", gwyDoor2=" + getGwyDoor2() +
            ", gwyDoor3=" + getGwyDoor3() +
            ", gwyDoor4=" + getGwyDoor4() +
            ", gwyDoor5=" + getGwyDoor5() +
            ", gwyDoor6=" + getGwyDoor6() +
            "}";
    }
}
