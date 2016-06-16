package space.vidsnip;

import org.springframework.data.repository.CrudRepository;

import space.vidsnip.model.GraphUser;

/**
 * Created by Marijn on 16/06/2016.
 */
public interface GraphUserRepository extends CrudRepository<GraphUser, String> {

    GraphUser findByName(String name);
}
