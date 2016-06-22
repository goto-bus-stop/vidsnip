package space.vidsnip.model.graphuser;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
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

    private String name;


    @Relationship(type = "Watches")
    private Set<GraphUser> watches;

    private GraphUser(){}

    public GraphUser(String name){
        this.name = name;
    }



    public void addWatch(GraphUser watch) {
        if(watches == null){
            watches =  new HashSet<>();
        }
        watches.add(watch);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}