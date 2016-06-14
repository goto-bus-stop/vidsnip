package space.vidsnip.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
