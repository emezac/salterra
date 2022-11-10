package com.sturdy.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A Option.
 */
@Entity
@Table(name = "option")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Option implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "option_name")
    private String optionName;

    @ManyToOne
    @JsonIgnoreProperties(value = { "options", "challenge" }, allowSetters = true)
    private Choice option;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Option id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOptionName() {
        return this.optionName;
    }

    public Option optionName(String optionName) {
        this.setOptionName(optionName);
        return this;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public Choice getOption() {
        return this.option;
    }

    public void setOption(Choice choice) {
        this.option = choice;
    }

    public Option option(Choice choice) {
        this.setOption(choice);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Option)) {
            return false;
        }
        return id != null && id.equals(((Option) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Option{" +
            "id=" + getId() +
            ", optionName='" + getOptionName() + "'" +
            "}";
    }
}
