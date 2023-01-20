package com.sturdy.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Course.
 */
@Entity
@Table(name = "course")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "tags")
    private String tags;

    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Lob
    @Column(name = "image_bar_code")
    private byte[] imageBarCode;

    @Column(name = "image_bar_code_content_type")
    private String imageBarCodeContentType;

    @Lob
    @Column(name = "certificate_image_template")
    private byte[] certificateImageTemplate;

    @Column(name = "certificate_image_template_content_type")
    private String certificateImageTemplateContentType;

    @OneToMany(mappedBy = "course")
    @JsonIgnoreProperties(value = { "course" }, allowSetters = true)
    private Set<Certificate> certificates = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "courses", "events" }, allowSetters = true)
    private ApplicationUser applicationUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Course id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Course name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTags() {
        return this.tags;
    }

    public Course tags(String tags) {
        this.setTags(tags);
        return this;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getDescription() {
        return this.description;
    }

    public Course description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return this.image;
    }

    public Course image(byte[] image) {
        this.setImage(image);
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return this.imageContentType;
    }

    public Course imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public byte[] getImageBarCode() {
        return this.imageBarCode;
    }

    public Course imageBarCode(byte[] imageBarCode) {
        this.setImageBarCode(imageBarCode);
        return this;
    }

    public void setImageBarCode(byte[] imageBarCode) {
        this.imageBarCode = imageBarCode;
    }

    public String getImageBarCodeContentType() {
        return this.imageBarCodeContentType;
    }

    public Course imageBarCodeContentType(String imageBarCodeContentType) {
        this.imageBarCodeContentType = imageBarCodeContentType;
        return this;
    }

    public void setImageBarCodeContentType(String imageBarCodeContentType) {
        this.imageBarCodeContentType = imageBarCodeContentType;
    }

    public byte[] getCertificateImageTemplate() {
        return this.certificateImageTemplate;
    }

    public Course certificateImageTemplate(byte[] certificateImageTemplate) {
        this.setCertificateImageTemplate(certificateImageTemplate);
        return this;
    }

    public void setCertificateImageTemplate(byte[] certificateImageTemplate) {
        this.certificateImageTemplate = certificateImageTemplate;
    }

    public String getCertificateImageTemplateContentType() {
        return this.certificateImageTemplateContentType;
    }

    public Course certificateImageTemplateContentType(String certificateImageTemplateContentType) {
        this.certificateImageTemplateContentType = certificateImageTemplateContentType;
        return this;
    }

    public void setCertificateImageTemplateContentType(String certificateImageTemplateContentType) {
        this.certificateImageTemplateContentType = certificateImageTemplateContentType;
    }

    public Set<Certificate> getCertificates() {
        return this.certificates;
    }

    public void setCertificates(Set<Certificate> certificates) {
        if (this.certificates != null) {
            this.certificates.forEach(i -> i.setCourse(null));
        }
        if (certificates != null) {
            certificates.forEach(i -> i.setCourse(this));
        }
        this.certificates = certificates;
    }

    public Course certificates(Set<Certificate> certificates) {
        this.setCertificates(certificates);
        return this;
    }

    public Course addCertificate(Certificate certificate) {
        this.certificates.add(certificate);
        certificate.setCourse(this);
        return this;
    }

    public Course removeCertificate(Certificate certificate) {
        this.certificates.remove(certificate);
        certificate.setCourse(null);
        return this;
    }

    public ApplicationUser getApplicationUser() {
        return this.applicationUser;
    }

    public void setApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }

    public Course applicationUser(ApplicationUser applicationUser) {
        this.setApplicationUser(applicationUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Course)) {
            return false;
        }
        return id != null && id.equals(((Course) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Course{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", tags='" + getTags() + "'" +
            ", description='" + getDescription() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", imageBarCode='" + getImageBarCode() + "'" +
            ", imageBarCodeContentType='" + getImageBarCodeContentType() + "'" +
            ", certificateImageTemplate='" + getCertificateImageTemplate() + "'" +
            ", certificateImageTemplateContentType='" + getCertificateImageTemplateContentType() + "'" +
            "}";
    }
}
