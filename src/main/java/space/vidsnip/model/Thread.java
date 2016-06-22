package space.vidsnip.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import space.vidsnip.model.snip.Snip;

@Entity(name = "Thread")
public class Thread {
    @Id
    private long id;

    @Column(name = "`index`")
    private Integer index;

    @ManyToOne
    private Snip snip;

    protected Thread() {}
}
