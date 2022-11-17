package com.sturdy.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sturdy.myapp.domain.enumeration.Difficulty;
import com.sturdy.myapp.domain.enumeration.FailureResult;
import com.sturdy.myapp.domain.enumeration.SkipResult;
import com.sturdy.myapp.domain.enumeration.SuccessResult;
import com.sturdy.myapp.domain.enumeration.TypeOfChallenge;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Activity.
 */
@Entity
@Table(name = "activity")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "success_text", nullable = false)
    private String successText;

    @NotNull
    @Column(name = "failure_text", nullable = false)
    private String failureText;

    @Column(name = "skip_text")
    private String skipText;

    @Enumerated(EnumType.STRING)
    @Column(name = "challange_difficulty")
    private Difficulty challangeDifficulty;

    @Enumerated(EnumType.STRING)
    @Column(name = "challenge_type")
    private TypeOfChallenge challengeType;

    @Enumerated(EnumType.STRING)
    @Column(name = "failure_result")
    private FailureResult failureResult;

    @Enumerated(EnumType.STRING)
    @Column(name = "success_result")
    private SuccessResult successResult;

    @Enumerated(EnumType.STRING)
    @Column(name = "skip_result")
    private SkipResult skipResult;

    @Column(name = "s_w")
    private String sW;

    @Column(name = "e")
    private String e;

    @Column(name = "n_w")
    private String nW;

    @Column(name = "n")
    private String n;

    @Column(name = "s_e")
    private String sE;

    @Column(name = "s")
    private String s;

    @Column(name = "n_e")
    private String nE;

    @Column(name = "w")
    private String w;

    @OneToMany(mappedBy = "activity")
    @JsonIgnoreProperties(value = { "choices", "move", "activity" }, allowSetters = true)
    private Set<ActivityMoves> activityMoves = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "activities", "prizes" }, allowSetters = true)
    private Room room;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Activity id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSuccessText() {
        return this.successText;
    }

    public Activity successText(String successText) {
        this.setSuccessText(successText);
        return this;
    }

    public void setSuccessText(String successText) {
        this.successText = successText;
    }

    public String getFailureText() {
        return this.failureText;
    }

    public Activity failureText(String failureText) {
        this.setFailureText(failureText);
        return this;
    }

    public void setFailureText(String failureText) {
        this.failureText = failureText;
    }

    public String getSkipText() {
        return this.skipText;
    }

    public Activity skipText(String skipText) {
        this.setSkipText(skipText);
        return this;
    }

    public void setSkipText(String skipText) {
        this.skipText = skipText;
    }

    public Difficulty getChallangeDifficulty() {
        return this.challangeDifficulty;
    }

    public Activity challangeDifficulty(Difficulty challangeDifficulty) {
        this.setChallangeDifficulty(challangeDifficulty);
        return this;
    }

    public void setChallangeDifficulty(Difficulty challangeDifficulty) {
        this.challangeDifficulty = challangeDifficulty;
    }

    public TypeOfChallenge getChallengeType() {
        return this.challengeType;
    }

    public Activity challengeType(TypeOfChallenge challengeType) {
        this.setChallengeType(challengeType);
        return this;
    }

    public void setChallengeType(TypeOfChallenge challengeType) {
        this.challengeType = challengeType;
    }

    public FailureResult getFailureResult() {
        return this.failureResult;
    }

    public Activity failureResult(FailureResult failureResult) {
        this.setFailureResult(failureResult);
        return this;
    }

    public void setFailureResult(FailureResult failureResult) {
        this.failureResult = failureResult;
    }

    public SuccessResult getSuccessResult() {
        return this.successResult;
    }

    public Activity successResult(SuccessResult successResult) {
        this.setSuccessResult(successResult);
        return this;
    }

    public void setSuccessResult(SuccessResult successResult) {
        this.successResult = successResult;
    }

    public SkipResult getSkipResult() {
        return this.skipResult;
    }

    public Activity skipResult(SkipResult skipResult) {
        this.setSkipResult(skipResult);
        return this;
    }

    public void setSkipResult(SkipResult skipResult) {
        this.skipResult = skipResult;
    }

    public String getsW() {
        return this.sW;
    }

    public Activity sW(String sW) {
        this.setsW(sW);
        return this;
    }

    public void setsW(String sW) {
        this.sW = sW;
    }

    public String getE() {
        return this.e;
    }

    public Activity e(String e) {
        this.setE(e);
        return this;
    }

    public void setE(String e) {
        this.e = e;
    }

    public String getnW() {
        return this.nW;
    }

    public Activity nW(String nW) {
        this.setnW(nW);
        return this;
    }

    public void setnW(String nW) {
        this.nW = nW;
    }

    public String getN() {
        return this.n;
    }

    public Activity n(String n) {
        this.setN(n);
        return this;
    }

    public void setN(String n) {
        this.n = n;
    }

    public String getsE() {
        return this.sE;
    }

    public Activity sE(String sE) {
        this.setsE(sE);
        return this;
    }

    public void setsE(String sE) {
        this.sE = sE;
    }

    public String getS() {
        return this.s;
    }

    public Activity s(String s) {
        this.setS(s);
        return this;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getnE() {
        return this.nE;
    }

    public Activity nE(String nE) {
        this.setnE(nE);
        return this;
    }

    public void setnE(String nE) {
        this.nE = nE;
    }

    public String getW() {
        return this.w;
    }

    public Activity w(String w) {
        this.setW(w);
        return this;
    }

    public void setW(String w) {
        this.w = w;
    }

    public Set<ActivityMoves> getActivityMoves() {
        return this.activityMoves;
    }

    public void setActivityMoves(Set<ActivityMoves> activityMoves) {
        if (this.activityMoves != null) {
            this.activityMoves.forEach(i -> i.setActivity(null));
        }
        if (activityMoves != null) {
            activityMoves.forEach(i -> i.setActivity(this));
        }
        this.activityMoves = activityMoves;
    }

    public Activity activityMoves(Set<ActivityMoves> activityMoves) {
        this.setActivityMoves(activityMoves);
        return this;
    }

    public Activity addActivityMoves(ActivityMoves activityMoves) {
        this.activityMoves.add(activityMoves);
        activityMoves.setActivity(this);
        return this;
    }

    public Activity removeActivityMoves(ActivityMoves activityMoves) {
        this.activityMoves.remove(activityMoves);
        activityMoves.setActivity(null);
        return this;
    }

    public Room getRoom() {
        return this.room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Activity room(Room room) {
        this.setRoom(room);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Activity)) {
            return false;
        }
        return id != null && id.equals(((Activity) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Activity{" +
            "id=" + getId() +
            ", successText='" + getSuccessText() + "'" +
            ", failureText='" + getFailureText() + "'" +
            ", skipText='" + getSkipText() + "'" +
            ", challangeDifficulty='" + getChallangeDifficulty() + "'" +
            ", challengeType='" + getChallengeType() + "'" +
            ", failureResult='" + getFailureResult() + "'" +
            ", successResult='" + getSuccessResult() + "'" +
            ", skipResult='" + getSkipResult() + "'" +
            ", sW='" + getsW() + "'" +
            ", e='" + getE() + "'" +
            ", nW='" + getnW() + "'" +
            ", n='" + getN() + "'" +
            ", sE='" + getsE() + "'" +
            ", s='" + getS() + "'" +
            ", nE='" + getnE() + "'" +
            ", w='" + getW() + "'" +
            "}";
    }
}
