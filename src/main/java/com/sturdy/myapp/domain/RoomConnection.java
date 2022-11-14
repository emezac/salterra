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

    @Column(name = "r_1")
    private Long r1;

    @Column(name = "r_2")
    private Long r2;

    @Column(name = "r_3")
    private Long r3;

    @Column(name = "r_4")
    private Long r4;

    @Column(name = "r_5")
    private Long r5;

    @Column(name = "r_6")
    private Long r6;

    @Column(name = "r_7")
    private Long r7;

    @Column(name = "r_8")
    private Long r8;

    @Column(name = "r_9")
    private Long r9;

    @Column(name = "r_10")
    private Long r10;

    @Column(name = "r_11")
    private Long r11;

    @Column(name = "r_12")
    private Long r12;

    @Column(name = "r_13")
    private Long r13;

    @Column(name = "r_14")
    private Long r14;

    @Column(name = "r_15")
    private Long r15;

    @Column(name = "r_16")
    private Long r16;

    @Column(name = "r_17")
    private Long r17;

    @Column(name = "r_18")
    private Long r18;

    @Column(name = "r_19")
    private Long r19;

    @Column(name = "r_20")
    private Long r20;

    @Column(name = "r_21")
    private Long r21;

    @Column(name = "r_22")
    private Long r22;

    @Column(name = "r_23")
    private Long r23;

    @Column(name = "r_24")
    private Long r24;

    @Column(name = "r_25")
    private Long r25;

    @Column(name = "r_26")
    private Long r26;

    @Column(name = "r_27")
    private Long r27;

    @Column(name = "r_28")
    private Long r28;

    @Column(name = "r_29")
    private Long r29;

    @Column(name = "r_30")
    private Long r30;

    @Column(name = "r_31")
    private Long r31;

    @Column(name = "r_32")
    private Long r32;

    @Column(name = "r_33")
    private Long r33;

    @Column(name = "r_34")
    private Long r34;

    @Column(name = "r_35")
    private Long r35;

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

    public Long getr1() {
        return this.r1;
    }

    public RoomConnection r1(Long r1) {
        this.setr1(r1);
        return this;
    }

    public void setr1(Long r1) {
        this.r1 = r1;
    }

    public Long getr2() {
        return this.r2;
    }

    public RoomConnection r2(Long r2) {
        this.setr2(r2);
        return this;
    }

    public void setr2(Long r2) {
        this.r2 = r2;
    }

    public Long getr3() {
        return this.r3;
    }

    public RoomConnection r3(Long r3) {
        this.setr3(r3);
        return this;
    }

    public void setr3(Long r3) {
        this.r3 = r3;
    }

    public Long getr4() {
        return this.r4;
    }

    public RoomConnection r4(Long r4) {
        this.setr4(r4);
        return this;
    }

    public void setr4(Long r4) {
        this.r4 = r4;
    }

    public Long getr5() {
        return this.r5;
    }

    public RoomConnection r5(Long r5) {
        this.setr5(r5);
        return this;
    }

    public void setr5(Long r5) {
        this.r5 = r5;
    }

    public Long getr6() {
        return this.r6;
    }

    public RoomConnection r6(Long r6) {
        this.setr6(r6);
        return this;
    }

    public void setr6(Long r6) {
        this.r6 = r6;
    }

    public Long getr7() {
        return this.r7;
    }

    public RoomConnection r7(Long r7) {
        this.setr7(r7);
        return this;
    }

    public void setr7(Long r7) {
        this.r7 = r7;
    }

    public Long getr8() {
        return this.r8;
    }

    public RoomConnection r8(Long r8) {
        this.setr8(r8);
        return this;
    }

    public void setr8(Long r8) {
        this.r8 = r8;
    }

    public Long getr9() {
        return this.r9;
    }

    public RoomConnection r9(Long r9) {
        this.setr9(r9);
        return this;
    }

    public void setr9(Long r9) {
        this.r9 = r9;
    }

    public Long getr10() {
        return this.r10;
    }

    public RoomConnection r10(Long r10) {
        this.setr10(r10);
        return this;
    }

    public void setr10(Long r10) {
        this.r10 = r10;
    }

    public Long getr11() {
        return this.r11;
    }

    public RoomConnection r11(Long r11) {
        this.setr11(r11);
        return this;
    }

    public void setr11(Long r11) {
        this.r11 = r11;
    }

    public Long getr12() {
        return this.r12;
    }

    public RoomConnection r12(Long r12) {
        this.setr12(r12);
        return this;
    }

    public void setr12(Long r12) {
        this.r12 = r12;
    }

    public Long getr13() {
        return this.r13;
    }

    public RoomConnection r13(Long r13) {
        this.setr13(r13);
        return this;
    }

    public void setr13(Long r13) {
        this.r13 = r13;
    }

    public Long getr14() {
        return this.r14;
    }

    public RoomConnection r14(Long r14) {
        this.setr14(r14);
        return this;
    }

    public void setr14(Long r14) {
        this.r14 = r14;
    }

    public Long getr15() {
        return this.r15;
    }

    public RoomConnection r15(Long r15) {
        this.setr15(r15);
        return this;
    }

    public void setr15(Long r15) {
        this.r15 = r15;
    }

    public Long getr16() {
        return this.r16;
    }

    public RoomConnection r16(Long r16) {
        this.setr16(r16);
        return this;
    }

    public void setr16(Long r16) {
        this.r16 = r16;
    }

    public Long getr17() {
        return this.r17;
    }

    public RoomConnection r17(Long r17) {
        this.setr17(r17);
        return this;
    }

    public void setr17(Long r17) {
        this.r17 = r17;
    }

    public Long getr18() {
        return this.r18;
    }

    public RoomConnection r18(Long r18) {
        this.setr18(r18);
        return this;
    }

    public void setr18(Long r18) {
        this.r18 = r18;
    }

    public Long getr19() {
        return this.r19;
    }

    public RoomConnection r19(Long r19) {
        this.setr19(r19);
        return this;
    }

    public void setr19(Long r19) {
        this.r19 = r19;
    }

    public Long getr20() {
        return this.r20;
    }

    public RoomConnection r20(Long r20) {
        this.setr20(r20);
        return this;
    }

    public void setr20(Long r20) {
        this.r20 = r20;
    }

    public Long getr21() {
        return this.r21;
    }

    public RoomConnection r21(Long r21) {
        this.setr21(r21);
        return this;
    }

    public void setr21(Long r21) {
        this.r21 = r21;
    }

    public Long getr22() {
        return this.r22;
    }

    public RoomConnection r22(Long r22) {
        this.setr22(r22);
        return this;
    }

    public void setr22(Long r22) {
        this.r22 = r22;
    }

    public Long getr23() {
        return this.r23;
    }

    public RoomConnection r23(Long r23) {
        this.setr23(r23);
        return this;
    }

    public void setr23(Long r23) {
        this.r23 = r23;
    }

    public Long getr24() {
        return this.r24;
    }

    public RoomConnection r24(Long r24) {
        this.setr24(r24);
        return this;
    }

    public void setr24(Long r24) {
        this.r24 = r24;
    }

    public Long getr25() {
        return this.r25;
    }

    public RoomConnection r25(Long r25) {
        this.setr25(r25);
        return this;
    }

    public void setr25(Long r25) {
        this.r25 = r25;
    }

    public Long getr26() {
        return this.r26;
    }

    public RoomConnection r26(Long r26) {
        this.setr26(r26);
        return this;
    }

    public void setr26(Long r26) {
        this.r26 = r26;
    }

    public Long getr27() {
        return this.r27;
    }

    public RoomConnection r27(Long r27) {
        this.setr27(r27);
        return this;
    }

    public void setr27(Long r27) {
        this.r27 = r27;
    }

    public Long getr28() {
        return this.r28;
    }

    public RoomConnection r28(Long r28) {
        this.setr28(r28);
        return this;
    }

    public void setr28(Long r28) {
        this.r28 = r28;
    }

    public Long getr29() {
        return this.r29;
    }

    public RoomConnection r29(Long r29) {
        this.setr29(r29);
        return this;
    }

    public void setr29(Long r29) {
        this.r29 = r29;
    }

    public Long getr30() {
        return this.r30;
    }

    public RoomConnection r30(Long r30) {
        this.setr30(r30);
        return this;
    }

    public void setr30(Long r30) {
        this.r30 = r30;
    }

    public Long getr31() {
        return this.r31;
    }

    public RoomConnection r31(Long r31) {
        this.setr31(r31);
        return this;
    }

    public void setr31(Long r31) {
        this.r31 = r31;
    }

    public Long getr32() {
        return this.r32;
    }

    public RoomConnection r32(Long r32) {
        this.setr32(r32);
        return this;
    }

    public void setr32(Long r32) {
        this.r32 = r32;
    }

    public Long getr33() {
        return this.r33;
    }

    public RoomConnection r33(Long r33) {
        this.setr33(r33);
        return this;
    }

    public void setr33(Long r33) {
        this.r33 = r33;
    }

    public Long getr34() {
        return this.r34;
    }

    public RoomConnection r34(Long r34) {
        this.setr34(r34);
        return this;
    }

    public void setr34(Long r34) {
        this.r34 = r34;
    }

    public Long getr35() {
        return this.r35;
    }

    public RoomConnection r35(Long r35) {
        this.setr35(r35);
        return this;
    }

    public void setr35(Long r35) {
        this.r35 = r35;
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
            ", r1=" + getr1() +
            ", r2=" + getr2() +
            ", r3=" + getr3() +
            ", r4=" + getr4() +
            ", r5=" + getr5() +
            ", r6=" + getr6() +
            ", r7=" + getr7() +
            ", r8=" + getr8() +
            ", r9=" + getr9() +
            ", r10=" + getr10() +
            ", r11=" + getr11() +
            ", r12=" + getr12() +
            ", r13=" + getr13() +
            ", r14=" + getr14() +
            ", r15=" + getr15() +
            ", r16=" + getr16() +
            ", r17=" + getr17() +
            ", r18=" + getr18() +
            ", r19=" + getr19() +
            ", r20=" + getr20() +
            ", r21=" + getr21() +
            ", r22=" + getr22() +
            ", r23=" + getr23() +
            ", r24=" + getr24() +
            ", r25=" + getr25() +
            ", r26=" + getr26() +
            ", r27=" + getr27() +
            ", r28=" + getr28() +
            ", r29=" + getr29() +
            ", r30=" + getr30() +
            ", r31=" + getr31() +
            ", r32=" + getr32() +
            ", r33=" + getr33() +
            ", r34=" + getr34() +
            ", r35=" + getr35() +
            "}";
    }
}
