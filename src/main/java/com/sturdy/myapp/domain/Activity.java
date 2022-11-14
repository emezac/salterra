package com.sturdy.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sturdy.myapp.domain.enumeration.Choice;
import com.sturdy.myapp.domain.enumeration.Difficulty;
import com.sturdy.myapp.domain.enumeration.TypesOfChallenge;
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
    @Column(name = "intro_text", nullable = false)
    private String introText;

    @NotNull
    @Column(name = "success_text", nullable = false)
    private String successText;

    @NotNull
    @Column(name = "failure_text", nullable = false)
    private String failureText;

    @Column(name = "skip_text")
    private String skipText;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_challenge")
    private Choice typeChallenge;

    @Enumerated(EnumType.STRING)
    @Column(name = "challenge_name")
    private TypesOfChallenge challengeName;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty")
    private Difficulty difficulty;

    @Enumerated(EnumType.STRING)
    @Column(name = "challange_rating")
    private Difficulty challangeRating;

    @Column(name = "sacrifice_number")
    private String sacrificeNumber;

    @Column(name = "skip_result")
    private String skipResult;

    @OneToMany(mappedBy = "activity")
    @JsonIgnoreProperties(value = { "activity" }, allowSetters = true)
    private Set<ChallengeOption> challengeOptions = new HashSet<>();

    @OneToMany(mappedBy = "activity")
    @JsonIgnoreProperties(value = { "prizes", "activity" }, allowSetters = true)
    private Set<PrizePool> prizePools = new HashSet<>();

    @OneToMany(mappedBy = "activity")
    @JsonIgnoreProperties(value = { "activity", "move" }, allowSetters = true)
    private Set<ActivityMoves> activityMoves = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "activities", "prizes", "roomConnections" }, allowSetters = true)
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

    public String getIntroText() {
        return this.introText;
    }

    public Activity introText(String introText) {
        this.setIntroText(introText);
        return this;
    }

    public void setIntroText(String introText) {
        this.introText = introText;
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

    public Choice getTypeChallenge() {
        return this.typeChallenge;
    }

    public Activity typeChallenge(Choice typeChallenge) {
        this.setTypeChallenge(typeChallenge);
        return this;
    }

    public void setTypeChallenge(Choice typeChallenge) {
        this.typeChallenge = typeChallenge;
    }

    public TypesOfChallenge getChallengeName() {
        return this.challengeName;
    }

    public Activity challengeName(TypesOfChallenge challengeName) {
        this.setChallengeName(challengeName);
        return this;
    }

    public void setChallengeName(TypesOfChallenge challengeName) {
        this.challengeName = challengeName;
    }

    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    public Activity difficulty(Difficulty difficulty) {
        this.setDifficulty(difficulty);
        return this;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Difficulty getChallangeRating() {
        return this.challangeRating;
    }

    public Activity challangeRating(Difficulty challangeRating) {
        this.setChallangeRating(challangeRating);
        return this;
    }

    public void setChallangeRating(Difficulty challangeRating) {
        this.challangeRating = challangeRating;
    }

    public String getSacrificeNumber() {
        return this.sacrificeNumber;
    }

    public Activity sacrificeNumber(String sacrificeNumber) {
        this.setSacrificeNumber(sacrificeNumber);
        return this;
    }

    public void setSacrificeNumber(String sacrificeNumber) {
        this.sacrificeNumber = sacrificeNumber;
    }

    public String getSkipResult() {
        return this.skipResult;
    }

    public Activity skipResult(String skipResult) {
        this.setSkipResult(skipResult);
        return this;
    }

    public void setSkipResult(String skipResult) {
        this.skipResult = skipResult;
    }

    public Set<ChallengeOption> getChallengeOptions() {
        return this.challengeOptions;
    }

    public void setChallengeOptions(Set<ChallengeOption> challengeOptions) {
        if (this.challengeOptions != null) {
            this.challengeOptions.forEach(i -> i.setActivity(null));
        }
        if (challengeOptions != null) {
            challengeOptions.forEach(i -> i.setActivity(this));
        }
        this.challengeOptions = challengeOptions;
    }

    public Activity challengeOptions(Set<ChallengeOption> challengeOptions) {
        this.setChallengeOptions(challengeOptions);
        return this;
    }

    public Activity addChallengeOption(ChallengeOption challengeOption) {
        this.challengeOptions.add(challengeOption);
        challengeOption.setActivity(this);
        return this;
    }

    public Activity removeChallengeOption(ChallengeOption challengeOption) {
        this.challengeOptions.remove(challengeOption);
        challengeOption.setActivity(null);
        return this;
    }

    public Set<PrizePool> getPrizePools() {
        return this.prizePools;
    }

    public void setPrizePools(Set<PrizePool> prizePools) {
        if (this.prizePools != null) {
            this.prizePools.forEach(i -> i.setActivity(null));
        }
        if (prizePools != null) {
            prizePools.forEach(i -> i.setActivity(this));
        }
        this.prizePools = prizePools;
    }

    public Activity prizePools(Set<PrizePool> prizePools) {
        this.setPrizePools(prizePools);
        return this;
    }

    public Activity addPrizePool(PrizePool prizePool) {
        this.prizePools.add(prizePool);
        prizePool.setActivity(this);
        return this;
    }

    public Activity removePrizePool(PrizePool prizePool) {
        this.prizePools.remove(prizePool);
        prizePool.setActivity(null);
        return this;
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
            ", introText='" + getIntroText() + "'" +
            ", successText='" + getSuccessText() + "'" +
            ", failureText='" + getFailureText() + "'" +
            ", skipText='" + getSkipText() + "'" +
            ", typeChallenge='" + getTypeChallenge() + "'" +
            ", challengeName='" + getChallengeName() + "'" +
            ", difficulty='" + getDifficulty() + "'" +
            ", challangeRating='" + getChallangeRating() + "'" +
            ", sacrificeNumber='" + getSacrificeNumber() + "'" +
            ", skipResult='" + getSkipResult() + "'" +
            "}";
    }
}
