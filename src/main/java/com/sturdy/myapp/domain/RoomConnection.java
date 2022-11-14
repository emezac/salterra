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

    @Column(name = "room_1")
    private Long room1;

    @Column(name = "room_2")
    private Long room2;

    @Column(name = "room_3")
    private Long room3;

    @Column(name = "room_4")
    private Long room4;

    @Column(name = "room_5")
    private Long room5;

    @Column(name = "room_6")
    private Long room6;

    @Column(name = "room_7")
    private Long room7;

    @Column(name = "room_8")
    private Long room8;

    @Column(name = "room_9")
    private Long room9;

    @Column(name = "room_10")
    private Long room10;

    @Column(name = "room_11")
    private Long room11;

    @Column(name = "room_12")
    private Long room12;

    @Column(name = "room_13")
    private Long room13;

    @Column(name = "room_14")
    private Long room14;

    @Column(name = "room_15")
    private Long room15;

    @Column(name = "room_16")
    private Long room16;

    @Column(name = "room_17")
    private Long room17;

    @Column(name = "room_18")
    private Long room18;

    @Column(name = "room_19")
    private Long room19;

    @Column(name = "room_20")
    private Long room20;

    @Column(name = "room_21")
    private Long room21;

    @Column(name = "room_22")
    private Long room22;

    @Column(name = "room_23")
    private Long room23;

    @Column(name = "room_24")
    private Long room24;

    @Column(name = "room_25")
    private Long room25;

    @Column(name = "room_26")
    private Long room26;

    @Column(name = "room_27")
    private Long room27;

    @Column(name = "room_28")
    private Long room28;

    @Column(name = "room_29")
    private Long room29;

    @Column(name = "room_30")
    private Long room30;

    @Column(name = "room_31")
    private Long room31;

    @Column(name = "room_32")
    private Long room32;

    @Column(name = "room_33")
    private Long room33;

    @Column(name = "room_34")
    private Long room34;

    @Column(name = "room_35")
    private Long room35;

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

    public Long getRoom1() {
        return this.room1;
    }

    public RoomConnection room1(Long room1) {
        this.setRoom1(room1);
        return this;
    }

    public void setRoom1(Long room1) {
        this.room1 = room1;
    }

    public Long getRoom2() {
        return this.room2;
    }

    public RoomConnection room2(Long room2) {
        this.setRoom2(room2);
        return this;
    }

    public void setRoom2(Long room2) {
        this.room2 = room2;
    }

    public Long getRoom3() {
        return this.room3;
    }

    public RoomConnection room3(Long room3) {
        this.setRoom3(room3);
        return this;
    }

    public void setRoom3(Long room3) {
        this.room3 = room3;
    }

    public Long getRoom4() {
        return this.room4;
    }

    public RoomConnection room4(Long room4) {
        this.setRoom4(room4);
        return this;
    }

    public void setRoom4(Long room4) {
        this.room4 = room4;
    }

    public Long getRoom5() {
        return this.room5;
    }

    public RoomConnection room5(Long room5) {
        this.setRoom5(room5);
        return this;
    }

    public void setRoom5(Long room5) {
        this.room5 = room5;
    }

    public Long getRoom6() {
        return this.room6;
    }

    public RoomConnection room6(Long room6) {
        this.setRoom6(room6);
        return this;
    }

    public void setRoom6(Long room6) {
        this.room6 = room6;
    }

    public Long getRoom7() {
        return this.room7;
    }

    public RoomConnection room7(Long room7) {
        this.setRoom7(room7);
        return this;
    }

    public void setRoom7(Long room7) {
        this.room7 = room7;
    }

    public Long getRoom8() {
        return this.room8;
    }

    public RoomConnection room8(Long room8) {
        this.setRoom8(room8);
        return this;
    }

    public void setRoom8(Long room8) {
        this.room8 = room8;
    }

    public Long getRoom9() {
        return this.room9;
    }

    public RoomConnection room9(Long room9) {
        this.setRoom9(room9);
        return this;
    }

    public void setRoom9(Long room9) {
        this.room9 = room9;
    }

    public Long getRoom10() {
        return this.room10;
    }

    public RoomConnection room10(Long room10) {
        this.setRoom10(room10);
        return this;
    }

    public void setRoom10(Long room10) {
        this.room10 = room10;
    }

    public Long getRoom11() {
        return this.room11;
    }

    public RoomConnection room11(Long room11) {
        this.setRoom11(room11);
        return this;
    }

    public void setRoom11(Long room11) {
        this.room11 = room11;
    }

    public Long getRoom12() {
        return this.room12;
    }

    public RoomConnection room12(Long room12) {
        this.setRoom12(room12);
        return this;
    }

    public void setRoom12(Long room12) {
        this.room12 = room12;
    }

    public Long getRoom13() {
        return this.room13;
    }

    public RoomConnection room13(Long room13) {
        this.setRoom13(room13);
        return this;
    }

    public void setRoom13(Long room13) {
        this.room13 = room13;
    }

    public Long getRoom14() {
        return this.room14;
    }

    public RoomConnection room14(Long room14) {
        this.setRoom14(room14);
        return this;
    }

    public void setRoom14(Long room14) {
        this.room14 = room14;
    }

    public Long getRoom15() {
        return this.room15;
    }

    public RoomConnection room15(Long room15) {
        this.setRoom15(room15);
        return this;
    }

    public void setRoom15(Long room15) {
        this.room15 = room15;
    }

    public Long getRoom16() {
        return this.room16;
    }

    public RoomConnection room16(Long room16) {
        this.setRoom16(room16);
        return this;
    }

    public void setRoom16(Long room16) {
        this.room16 = room16;
    }

    public Long getRoom17() {
        return this.room17;
    }

    public RoomConnection room17(Long room17) {
        this.setRoom17(room17);
        return this;
    }

    public void setRoom17(Long room17) {
        this.room17 = room17;
    }

    public Long getRoom18() {
        return this.room18;
    }

    public RoomConnection room18(Long room18) {
        this.setRoom18(room18);
        return this;
    }

    public void setRoom18(Long room18) {
        this.room18 = room18;
    }

    public Long getRoom19() {
        return this.room19;
    }

    public RoomConnection room19(Long room19) {
        this.setRoom19(room19);
        return this;
    }

    public void setRoom19(Long room19) {
        this.room19 = room19;
    }

    public Long getRoom20() {
        return this.room20;
    }

    public RoomConnection room20(Long room20) {
        this.setRoom20(room20);
        return this;
    }

    public void setRoom20(Long room20) {
        this.room20 = room20;
    }

    public Long getRoom21() {
        return this.room21;
    }

    public RoomConnection room21(Long room21) {
        this.setRoom21(room21);
        return this;
    }

    public void setRoom21(Long room21) {
        this.room21 = room21;
    }

    public Long getRoom22() {
        return this.room22;
    }

    public RoomConnection room22(Long room22) {
        this.setRoom22(room22);
        return this;
    }

    public void setRoom22(Long room22) {
        this.room22 = room22;
    }

    public Long getRoom23() {
        return this.room23;
    }

    public RoomConnection room23(Long room23) {
        this.setRoom23(room23);
        return this;
    }

    public void setRoom23(Long room23) {
        this.room23 = room23;
    }

    public Long getRoom24() {
        return this.room24;
    }

    public RoomConnection room24(Long room24) {
        this.setRoom24(room24);
        return this;
    }

    public void setRoom24(Long room24) {
        this.room24 = room24;
    }

    public Long getRoom25() {
        return this.room25;
    }

    public RoomConnection room25(Long room25) {
        this.setRoom25(room25);
        return this;
    }

    public void setRoom25(Long room25) {
        this.room25 = room25;
    }

    public Long getRoom26() {
        return this.room26;
    }

    public RoomConnection room26(Long room26) {
        this.setRoom26(room26);
        return this;
    }

    public void setRoom26(Long room26) {
        this.room26 = room26;
    }

    public Long getRoom27() {
        return this.room27;
    }

    public RoomConnection room27(Long room27) {
        this.setRoom27(room27);
        return this;
    }

    public void setRoom27(Long room27) {
        this.room27 = room27;
    }

    public Long getRoom28() {
        return this.room28;
    }

    public RoomConnection room28(Long room28) {
        this.setRoom28(room28);
        return this;
    }

    public void setRoom28(Long room28) {
        this.room28 = room28;
    }

    public Long getRoom29() {
        return this.room29;
    }

    public RoomConnection room29(Long room29) {
        this.setRoom29(room29);
        return this;
    }

    public void setRoom29(Long room29) {
        this.room29 = room29;
    }

    public Long getRoom30() {
        return this.room30;
    }

    public RoomConnection room30(Long room30) {
        this.setRoom30(room30);
        return this;
    }

    public void setRoom30(Long room30) {
        this.room30 = room30;
    }

    public Long getRoom31() {
        return this.room31;
    }

    public RoomConnection room31(Long room31) {
        this.setRoom31(room31);
        return this;
    }

    public void setRoom31(Long room31) {
        this.room31 = room31;
    }

    public Long getRoom32() {
        return this.room32;
    }

    public RoomConnection room32(Long room32) {
        this.setRoom32(room32);
        return this;
    }

    public void setRoom32(Long room32) {
        this.room32 = room32;
    }

    public Long getRoom33() {
        return this.room33;
    }

    public RoomConnection room33(Long room33) {
        this.setRoom33(room33);
        return this;
    }

    public void setRoom33(Long room33) {
        this.room33 = room33;
    }

    public Long getRoom34() {
        return this.room34;
    }

    public RoomConnection room34(Long room34) {
        this.setRoom34(room34);
        return this;
    }

    public void setRoom34(Long room34) {
        this.room34 = room34;
    }

    public Long getRoom35() {
        return this.room35;
    }

    public RoomConnection room35(Long room35) {
        this.setRoom35(room35);
        return this;
    }

    public void setRoom35(Long room35) {
        this.room35 = room35;
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
            ", room1=" + getRoom1() +
            ", room2=" + getRoom2() +
            ", room3=" + getRoom3() +
            ", room4=" + getRoom4() +
            ", room5=" + getRoom5() +
            ", room6=" + getRoom6() +
            ", room7=" + getRoom7() +
            ", room8=" + getRoom8() +
            ", room9=" + getRoom9() +
            ", room10=" + getRoom10() +
            ", room11=" + getRoom11() +
            ", room12=" + getRoom12() +
            ", room13=" + getRoom13() +
            ", room14=" + getRoom14() +
            ", room15=" + getRoom15() +
            ", room16=" + getRoom16() +
            ", room17=" + getRoom17() +
            ", room18=" + getRoom18() +
            ", room19=" + getRoom19() +
            ", room20=" + getRoom20() +
            ", room21=" + getRoom21() +
            ", room22=" + getRoom22() +
            ", room23=" + getRoom23() +
            ", room24=" + getRoom24() +
            ", room25=" + getRoom25() +
            ", room26=" + getRoom26() +
            ", room27=" + getRoom27() +
            ", room28=" + getRoom28() +
            ", room29=" + getRoom29() +
            ", room30=" + getRoom30() +
            ", room31=" + getRoom31() +
            ", room32=" + getRoom32() +
            ", room33=" + getRoom33() +
            ", room34=" + getRoom34() +
            ", room35=" + getRoom35() +
            "}";
    }
}
