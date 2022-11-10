package com.sturdy.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A PrizePool.
 */
@Entity
@Table(name = "prize_pool")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PrizePool implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "prizePool")
    @JsonIgnoreProperties(value = { "profile", "card", "room", "prizePool" }, allowSetters = true)
    private Set<Prize> prizes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PrizePool id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Prize> getPrizes() {
        return this.prizes;
    }

    public void setPrizes(Set<Prize> prizes) {
        if (this.prizes != null) {
            this.prizes.forEach(i -> i.setPrizePool(null));
        }
        if (prizes != null) {
            prizes.forEach(i -> i.setPrizePool(this));
        }
        this.prizes = prizes;
    }

    public PrizePool prizes(Set<Prize> prizes) {
        this.setPrizes(prizes);
        return this;
    }

    public PrizePool addPrize(Prize prize) {
        this.prizes.add(prize);
        prize.setPrizePool(this);
        return this;
    }

    public PrizePool removePrize(Prize prize) {
        this.prizes.remove(prize);
        prize.setPrizePool(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrizePool)) {
            return false;
        }
        return id != null && id.equals(((PrizePool) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PrizePool{" +
            "id=" + getId() +
            "}";
    }
}
