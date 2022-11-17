package com.sturdy.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Card.
 */
@Entity
@Table(name = "card")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Card implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "thumbnai_image")
    private byte[] thumbnaiImage;

    @Column(name = "thumbnai_image_content_type")
    private String thumbnaiImageContentType;

    @Lob
    @Column(name = "highres_image")
    private byte[] highresImage;

    @Column(name = "highres_image_content_type")
    private String highresImageContentType;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @OneToMany(mappedBy = "card")
    @JsonIgnoreProperties(value = { "profile", "card", "rooms" }, allowSetters = true)
    private Set<Prize> prizes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Card id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getThumbnaiImage() {
        return this.thumbnaiImage;
    }

    public Card thumbnaiImage(byte[] thumbnaiImage) {
        this.setThumbnaiImage(thumbnaiImage);
        return this;
    }

    public void setThumbnaiImage(byte[] thumbnaiImage) {
        this.thumbnaiImage = thumbnaiImage;
    }

    public String getThumbnaiImageContentType() {
        return this.thumbnaiImageContentType;
    }

    public Card thumbnaiImageContentType(String thumbnaiImageContentType) {
        this.thumbnaiImageContentType = thumbnaiImageContentType;
        return this;
    }

    public void setThumbnaiImageContentType(String thumbnaiImageContentType) {
        this.thumbnaiImageContentType = thumbnaiImageContentType;
    }

    public byte[] getHighresImage() {
        return this.highresImage;
    }

    public Card highresImage(byte[] highresImage) {
        this.setHighresImage(highresImage);
        return this;
    }

    public void setHighresImage(byte[] highresImage) {
        this.highresImage = highresImage;
    }

    public String getHighresImageContentType() {
        return this.highresImageContentType;
    }

    public Card highresImageContentType(String highresImageContentType) {
        this.highresImageContentType = highresImageContentType;
        return this;
    }

    public void setHighresImageContentType(String highresImageContentType) {
        this.highresImageContentType = highresImageContentType;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Card createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public Card updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return this.deletedAt;
    }

    public Card deletedAt(Instant deletedAt) {
        this.setDeletedAt(deletedAt);
        return this;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Set<Prize> getPrizes() {
        return this.prizes;
    }

    public void setPrizes(Set<Prize> prizes) {
        if (this.prizes != null) {
            this.prizes.forEach(i -> i.setCard(null));
        }
        if (prizes != null) {
            prizes.forEach(i -> i.setCard(this));
        }
        this.prizes = prizes;
    }

    public Card prizes(Set<Prize> prizes) {
        this.setPrizes(prizes);
        return this;
    }

    public Card addPrize(Prize prize) {
        this.prizes.add(prize);
        prize.setCard(this);
        return this;
    }

    public Card removePrize(Prize prize) {
        this.prizes.remove(prize);
        prize.setCard(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Card)) {
            return false;
        }
        return id != null && id.equals(((Card) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Card{" +
            "id=" + getId() +
            ", thumbnaiImage='" + getThumbnaiImage() + "'" +
            ", thumbnaiImageContentType='" + getThumbnaiImageContentType() + "'" +
            ", highresImage='" + getHighresImage() + "'" +
            ", highresImageContentType='" + getHighresImageContentType() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            "}";
    }
}
