package com.sturdy.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A ApplicationUser.
 */
@Entity
@Table(name = "application_user")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApplicationUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @Column(name = "polygon_address")
    private String polygonAddress;

    @Column(name = "public_url")
    private String publicUrl;

    @OneToMany(mappedBy = "applicationUser")
    @JsonIgnoreProperties(value = { "certificates", "applicationUser" }, allowSetters = true)
    private Set<Course> courses = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_application_user__event",
        joinColumns = @JoinColumn(name = "application_user_id"),
        inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    @JsonIgnoreProperties(value = { "applicationUsers" }, allowSetters = true)
    private Set<Event> events = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ApplicationUser id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return this.fullName;
    }

    public ApplicationUser fullName(String fullName) {
        this.setFullName(fullName);
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return this.email;
    }

    public ApplicationUser email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public ApplicationUser password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return this.role;
    }

    public ApplicationUser role(String role) {
        this.setRole(role);
        return this;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPolygonAddress() {
        return this.polygonAddress;
    }

    public ApplicationUser polygonAddress(String polygonAddress) {
        this.setPolygonAddress(polygonAddress);
        return this;
    }

    public void setPolygonAddress(String polygonAddress) {
        this.polygonAddress = polygonAddress;
    }

    public String getPublicUrl() {
        return this.publicUrl;
    }

    public ApplicationUser publicUrl(String publicUrl) {
        this.setPublicUrl(publicUrl);
        return this;
    }

    public void setPublicUrl(String publicUrl) {
        this.publicUrl = publicUrl;
    }

    public Set<Course> getCourses() {
        return this.courses;
    }

    public void setCourses(Set<Course> courses) {
        if (this.courses != null) {
            this.courses.forEach(i -> i.setApplicationUser(null));
        }
        if (courses != null) {
            courses.forEach(i -> i.setApplicationUser(this));
        }
        this.courses = courses;
    }

    public ApplicationUser courses(Set<Course> courses) {
        this.setCourses(courses);
        return this;
    }

    public ApplicationUser addCourse(Course course) {
        this.courses.add(course);
        course.setApplicationUser(this);
        return this;
    }

    public ApplicationUser removeCourse(Course course) {
        this.courses.remove(course);
        course.setApplicationUser(null);
        return this;
    }

    public Set<Event> getEvents() {
        return this.events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public ApplicationUser events(Set<Event> events) {
        this.setEvents(events);
        return this;
    }

    public ApplicationUser addEvent(Event event) {
        this.events.add(event);
        event.getApplicationUsers().add(this);
        return this;
    }

    public ApplicationUser removeEvent(Event event) {
        this.events.remove(event);
        event.getApplicationUsers().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicationUser)) {
            return false;
        }
        return id != null && id.equals(((ApplicationUser) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicationUser{" +
            "id=" + getId() +
            ", fullName='" + getFullName() + "'" +
            ", email='" + getEmail() + "'" +
            ", password='" + getPassword() + "'" +
            ", role='" + getRole() + "'" +
            ", polygonAddress='" + getPolygonAddress() + "'" +
            ", publicUrl='" + getPublicUrl() + "'" +
            "}";
    }
}
