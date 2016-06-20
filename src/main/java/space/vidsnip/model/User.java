package space.vidsnip.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true)
    private String username;

    @Column(name = "real_name")
    private String realName;

    @OneToOne
    @JoinColumn(name = "bio_id")
    private Snip bio;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "author")
    private List<Snip> snips;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    protected User() {}

    public User(String username) {
        this.username = username;
        this.realName = username;
    }
    public User(String username, String realName) {
        this.username = username;
        this.realName = realName;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealName() {
        return this.realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Optional<Snip> getBio() {
        return Optional.ofNullable(this.bio);
    }

    public void setBio(Snip bio) {
        this.bio = bio;
    }

    public List<Snip> getSnips() {
        return this.snips;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }
}
