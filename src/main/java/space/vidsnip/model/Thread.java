package space.vidsnip.model;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import space.vidsnip.model.snip.Snip;

@Entity(name = "Thread")
public class Thread {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY (16)")
    private UUID id;

    @Column(name = "`index`")
    private Integer index;

    @ManyToOne
    private Snip snip;

    protected Thread() {}
}
