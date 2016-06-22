package space.vidsnip.model.user;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import space.vidsnip.model.snip.Snip;

@Entity(name = "User")
public class User {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY (16)")
    private UUID id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    @Column(length = 60)
    private String password;

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

    public UUID getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEncryptedPassword() {
        return this.password;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.password = encryptedPassword;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
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
