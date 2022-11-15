package com.sturdy.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A ChallengeOption.
 */
@Entity
@Table(name = "challenge_option")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ChallengeOption implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "option_name")
    private String optionName;

    @Column(name = "option")
    private String option;

    @ManyToOne
    @JsonIgnoreProperties(value = { "challengeOptions", "prizePools", "activityMoves", "room" }, allowSetters = true)
    private Activity activity;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ChallengeOption id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOptionName() {
        return this.optionName;
    }

    public ChallengeOption optionName(String optionName) {
        this.setOptionName(optionName);
        return this;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public String getOption() {
        return this.option;
    }

    public ChallengeOption option(String option) {
        this.setOption(option);
        return this;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public Activity getActivity() {
        return this.activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public ChallengeOption activity(Activity activity) {
        this.setActivity(activity);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChallengeOption)) {
            return false;
        }
        return id != null && id.equals(((ChallengeOption) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChallengeOption{" +
            "id=" + getId() +
            ", optionName='" + getOptionName() + "'" +
            ", option='" + getOption() + "'" +
            "}";
    }
}
