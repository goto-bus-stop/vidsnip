package space.vidsnip.model.graphuser;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;


/**
 * Created by Marijn on 22/06/2016.
 */
@Repository
public interface GraphUserRepository extends GraphRepository<GraphUser> {
    GraphUser findByUsername(String username);

}