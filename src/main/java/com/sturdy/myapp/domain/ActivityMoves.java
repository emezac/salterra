package com.sturdy.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A ActivityMoves.
 */
@Entity
@Table(name = "activity_moves")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ActivityMoves implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "move_date")
    private Instant moveDate;

    @ManyToOne
    @JsonIgnoreProperties(value = { "choices", "prizePools", "activityMoves", "room" }, allowSetters = true)
    private Activity activity;

    @ManyToOne
    @JsonIgnoreProperties(value = { "activityMoves", "gameStatus" }, allowSetters = true)
    private Move move;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ActivityMoves id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getMoveDate() {
        return this.moveDate;
    }

    public ActivityMoves moveDate(Instant moveDate) {
        this.setMoveDate(moveDate);
        return this;
    }

    public void setMoveDate(Instant moveDate) {
        this.moveDate = moveDate;
    }

    public Activity getActivity() {
        return this.activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public ActivityMoves activity(Activity activity) {
        this.setActivity(activity);
        return this;
    }

    public Move getMove() {
        return this.move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public ActivityMoves move(Move move) {
        this.setMove(move);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ActivityMoves)) {
            return false;
        }
        return id != null && id.equals(((ActivityMoves) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ActivityMoves{" +
            "id=" + getId() +
            ", moveDate='" + getMoveDate() + "'" +
            "}";
    }
}
