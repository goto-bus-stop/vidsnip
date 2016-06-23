package space.vidsnip.model.graphuser;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Marijn on 16/06/2016.
 */
@NodeEntity
public class GraphUser {
    @GraphId
    private Long id;

    @Property
    private String username;

    @Relationship(type = "WATCHES")
    private Set<GraphUser> watches;

    private GraphUser() {}

    public GraphUser(String username) {
        this.username = username;
    }

    public void addWatch(GraphUser watch) {
        this.getWatches().add(watch);
    }

    public void removeWatch(GraphUser watch) {
        this.getWatches().remove(watch);
    }

    public boolean isWatching(GraphUser user) {
        return this.getWatches().contains(user);
    }

    public String getName() {
        return this.username;
    }

    public void setName(String name) {
        this.username = name;
    }

    public Set<GraphUser> getWatches() {
        if (this.watches == null) {
            this.watches = new HashSet<>();
        }
        return this.watches;
    }

    /**
     * Users with the same username are considered the same.
     */
    public int hashCode() {
        return this.username.hashCode();
    }
}
