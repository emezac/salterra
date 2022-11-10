package com.sturdy.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sturdy.myapp.domain.enumeration.Difficulty;
import com.sturdy.myapp.domain.enumeration.TypesOfChallenge;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Challenge.
 */
@Entity
@Table(name = "challenge")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Challenge implements Serializable {

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
    @Column(name = "challenge_name")
    private TypesOfChallenge challengeName;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty")
    private Difficulty difficulty;

    @Column(name = "prize_number")
    private String prizeNumber;

    @Column(name = "challange_rating")
    private String challangeRating;

    @Column(name = "sacrifice_number")
    private String sacrificeNumber;

    @Column(name = "skip_result")
    private String skipResult;

    @OneToMany(mappedBy = "challenge")
    @JsonIgnoreProperties(value = { "options", "challenge" }, allowSetters = true)
    private Set<Choice> choices = new HashSet<>();

    @OneToMany(mappedBy = "challenge")
    @JsonIgnoreProperties(value = { "prizes", "challenge" }, allowSetters = true)
    private Set<PrizePool> prizePools = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_challenge__move",
        joinColumns = @JoinColumn(name = "challenge_id"),
        inverseJoinColumns = @JoinColumn(name = "move_id")
    )
    @JsonIgnoreProperties(value = { "gameStatus", "challenges" }, allowSetters = true)
    private Set<Move> moves = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "challenges", "prizes" }, allowSetters = true)
    private Room room;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Challenge id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIntroText() {
        return this.introText;
    }

    public Challenge introText(String introText) {
        this.setIntroText(introText);
        return this;
    }

    public void setIntroText(String introText) {
        this.introText = introText;
    }

    public String getSuccessText() {
        return this.successText;
    }

    public Challenge successText(String successText) {
        this.setSuccessText(successText);
        return this;
    }

    public void setSuccessText(String successText) {
        this.successText = successText;
    }

    public String getFailureText() {
        return this.failureText;
    }

    public Challenge failureText(String failureText) {
        this.setFailureText(failureText);
        return this;
    }

    public void setFailureText(String failureText) {
        this.failureText = failureText;
    }

    public String getSkipText() {
        return this.skipText;
    }

    public Challenge skipText(String skipText) {
        this.setSkipText(skipText);
        return this;
    }

    public void setSkipText(String skipText) {
        this.skipText = skipText;
    }

    public TypesOfChallenge getChallengeName() {
        return this.challengeName;
    }

    public Challenge challengeName(TypesOfChallenge challengeName) {
        this.setChallengeName(challengeName);
        return this;
    }

    public void setChallengeName(TypesOfChallenge challengeName) {
        this.challengeName = challengeName;
    }

    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    public Challenge difficulty(Difficulty difficulty) {
        this.setDifficulty(difficulty);
        return this;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public String getPrizeNumber() {
        return this.prizeNumber;
    }

    public Challenge prizeNumber(String prizeNumber) {
        this.setPrizeNumber(prizeNumber);
        return this;
    }

    public void setPrizeNumber(String prizeNumber) {
        this.prizeNumber = prizeNumber;
    }

    public String getChallangeRating() {
        return this.challangeRating;
    }

    public Challenge challangeRating(String challangeRating) {
        this.setChallangeRating(challangeRating);
        return this;
    }

    public void setChallangeRating(String challangeRating) {
        this.challangeRating = challangeRating;
    }

    public String getSacrificeNumber() {
        return this.sacrificeNumber;
    }

    public Challenge sacrificeNumber(String sacrificeNumber) {
        this.setSacrificeNumber(sacrificeNumber);
        return this;
    }

    public void setSacrificeNumber(String sacrificeNumber) {
        this.sacrificeNumber = sacrificeNumber;
    }

    public String getSkipResult() {
        return this.skipResult;
    }

    public Challenge skipResult(String skipResult) {
        this.setSkipResult(skipResult);
        return this;
    }

    public void setSkipResult(String skipResult) {
        this.skipResult = skipResult;
    }

    public Set<Choice> getChoices() {
        return this.choices;
    }

    public void setChoices(Set<Choice> choices) {
        if (this.choices != null) {
            this.choices.forEach(i -> i.setChallenge(null));
        }
        if (choices != null) {
            choices.forEach(i -> i.setChallenge(this));
        }
        this.choices = choices;
    }

    public Challenge choices(Set<Choice> choices) {
        this.setChoices(choices);
        return this;
    }

    public Challenge addChoice(Choice choice) {
        this.choices.add(choice);
        choice.setChallenge(this);
        return this;
    }

    public Challenge removeChoice(Choice choice) {
        this.choices.remove(choice);
        choice.setChallenge(null);
        return this;
    }

    public Set<PrizePool> getPrizePools() {
        return this.prizePools;
    }

    public void setPrizePools(Set<PrizePool> prizePools) {
        if (this.prizePools != null) {
            this.prizePools.forEach(i -> i.setChallenge(null));
        }
        if (prizePools != null) {
            prizePools.forEach(i -> i.setChallenge(this));
        }
        this.prizePools = prizePools;
    }

    public Challenge prizePools(Set<PrizePool> prizePools) {
        this.setPrizePools(prizePools);
        return this;
    }

    public Challenge addPrizePool(PrizePool prizePool) {
        this.prizePools.add(prizePool);
        prizePool.setChallenge(this);
        return this;
    }

    public Challenge removePrizePool(PrizePool prizePool) {
        this.prizePools.remove(prizePool);
        prizePool.setChallenge(null);
        return this;
    }

    public Set<Move> getMoves() {
        return this.moves;
    }

    public void setMoves(Set<Move> moves) {
        this.moves = moves;
    }

    public Challenge moves(Set<Move> moves) {
        this.setMoves(moves);
        return this;
    }

    public Challenge addMove(Move move) {
        this.moves.add(move);
        move.getChallenges().add(this);
        return this;
    }

    public Challenge removeMove(Move move) {
        this.moves.remove(move);
        move.getChallenges().remove(this);
        return this;
    }

    public Room getRoom() {
        return this.room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Challenge room(Room room) {
        this.setRoom(room);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Challenge)) {
            return false;
        }
        return id != null && id.equals(((Challenge) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Challenge{" +
            "id=" + getId() +
            ", introText='" + getIntroText() + "'" +
            ", successText='" + getSuccessText() + "'" +
            ", failureText='" + getFailureText() + "'" +
            ", skipText='" + getSkipText() + "'" +
            ", challengeName='" + getChallengeName() + "'" +
            ", difficulty='" + getDifficulty() + "'" +
            ", prizeNumber='" + getPrizeNumber() + "'" +
            ", challangeRating='" + getChallangeRating() + "'" +
            ", sacrificeNumber='" + getSacrificeNumber() + "'" +
            ", skipResult='" + getSkipResult() + "'" +
            "}";
    }
}
