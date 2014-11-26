package be.example.backend.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by vanbosb on 25/11/14.
 */
@Entity
@Table(name = "person")
public class CachePerson extends ResourceSupport implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Transient
    private Long fetchTime;

    @Version
    @Column(name = "version")
    private int version;

    /**
     * Default constructor.
     */
    public CachePerson() {
    }

    public Long getCachePersonId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @JsonProperty
    public Long getFetchTime() {
        return fetchTime;
    }

    public void setFetchTime(Long fetchTime) {
        this.fetchTime = fetchTime;
    }

    public int getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "CachePerson{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", version=" + version +
                '}';
    }
}
