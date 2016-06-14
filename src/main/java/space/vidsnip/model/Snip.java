package space.vidsnip.model;

import java.util.Date;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "Snip")
public class Snip {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Embedded
    private Video video;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    protected Snip() {}

    public Snip(User author, Video video) {
        this.author = author;
        this.video = video;
    }

    public long getId() {
        return this.id;
    }

    public User getAuthor() {
        return this.author;
    }

    public Video getVideo() {
        return this.video;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }
}
