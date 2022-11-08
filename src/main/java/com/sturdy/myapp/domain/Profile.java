package com.sturdy.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Profile.
 */
@Entity
@Table(name = "profile")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "auth_0_user_id", nullable = false)
    private String auth0UserId;

    @Column(name = "social_network")
    private String socialNetwork;

    @Lob
    @Column(name = "profile_image")
    private byte[] profileImage;

    @Column(name = "profile_image_content_type")
    private String profileImageContentType;

    @Column(name = "suspended")
    private Boolean suspended;

    @Column(name = "suspended_count")
    private Integer suspendedCount;

    @Column(name = "banned")
    private Boolean banned;

    @Column(name = "acl_setup")
    private String aclSetup;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @OneToMany(mappedBy = "profile")
    @JsonIgnoreProperties(value = { "profile", "card", "room" }, allowSetters = true)
    private Set<Prize> prizes = new HashSet<>();

    @JsonIgnoreProperties(value = { "profile", "dungeons" }, allowSetters = true)
    @OneToOne(mappedBy = "profile")
    private ProfileGameStatus profileGameStatus;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Profile id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Profile name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuth0UserId() {
        return this.auth0UserId;
    }

    public Profile auth0UserId(String auth0UserId) {
        this.setAuth0UserId(auth0UserId);
        return this;
    }

    public void setAuth0UserId(String auth0UserId) {
        this.auth0UserId = auth0UserId;
    }

    public String getSocialNetwork() {
        return this.socialNetwork;
    }

    public Profile socialNetwork(String socialNetwork) {
        this.setSocialNetwork(socialNetwork);
        return this;
    }

    public void setSocialNetwork(String socialNetwork) {
        this.socialNetwork = socialNetwork;
    }

    public byte[] getProfileImage() {
        return this.profileImage;
    }

    public Profile profileImage(byte[] profileImage) {
        this.setProfileImage(profileImage);
        return this;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    public String getProfileImageContentType() {
        return this.profileImageContentType;
    }

    public Profile profileImageContentType(String profileImageContentType) {
        this.profileImageContentType = profileImageContentType;
        return this;
    }

    public void setProfileImageContentType(String profileImageContentType) {
        this.profileImageContentType = profileImageContentType;
    }

    public Boolean getSuspended() {
        return this.suspended;
    }

    public Profile suspended(Boolean suspended) {
        this.setSuspended(suspended);
        return this;
    }

    public void setSuspended(Boolean suspended) {
        this.suspended = suspended;
    }

    public Integer getSuspendedCount() {
        return this.suspendedCount;
    }

    public Profile suspendedCount(Integer suspendedCount) {
        this.setSuspendedCount(suspendedCount);
        return this;
    }

    public void setSuspendedCount(Integer suspendedCount) {
        this.suspendedCount = suspendedCount;
    }

    public Boolean getBanned() {
        return this.banned;
    }

    public Profile banned(Boolean banned) {
        this.setBanned(banned);
        return this;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public String getAclSetup() {
        return this.aclSetup;
    }

    public Profile aclSetup(String aclSetup) {
        this.setAclSetup(aclSetup);
        return this;
    }

    public void setAclSetup(String aclSetup) {
        this.aclSetup = aclSetup;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Profile createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public Profile updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return this.deletedAt;
    }

    public Profile deletedAt(Instant deletedAt) {
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
            this.prizes.forEach(i -> i.setProfile(null));
        }
        if (prizes != null) {
            prizes.forEach(i -> i.setProfile(this));
        }
        this.prizes = prizes;
    }

    public Profile prizes(Set<Prize> prizes) {
        this.setPrizes(prizes);
        return this;
    }

    public Profile addPrize(Prize prize) {
        this.prizes.add(prize);
        prize.setProfile(this);
        return this;
    }

    public Profile removePrize(Prize prize) {
        this.prizes.remove(prize);
        prize.setProfile(null);
        return this;
    }

    public ProfileGameStatus getProfileGameStatus() {
        return this.profileGameStatus;
    }

    public void setProfileGameStatus(ProfileGameStatus profileGameStatus) {
        if (this.profileGameStatus != null) {
            this.profileGameStatus.setProfile(null);
        }
        if (profileGameStatus != null) {
            profileGameStatus.setProfile(this);
        }
        this.profileGameStatus = profileGameStatus;
    }

    public Profile profileGameStatus(ProfileGameStatus profileGameStatus) {
        this.setProfileGameStatus(profileGameStatus);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Profile)) {
            return false;
        }
        return id != null && id.equals(((Profile) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Profile{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", auth0UserId='" + getAuth0UserId() + "'" +
            ", socialNetwork='" + getSocialNetwork() + "'" +
            ", profileImage='" + getProfileImage() + "'" +
            ", profileImageContentType='" + getProfileImageContentType() + "'" +
            ", suspended='" + getSuspended() + "'" +
            ", suspendedCount=" + getSuspendedCount() +
            ", banned='" + getBanned() + "'" +
            ", aclSetup='" + getAclSetup() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            "}";
    }
}
