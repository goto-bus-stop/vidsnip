package space.vidsnip.model.snip;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import space.vidsnip.model.user.User;

@RepositoryRestResource(collectionResourceRel = "snips", path = "snips")
public interface SnipRepository extends PagingAndSortingRepository<Snip, Long> {
    Page<Snip> findByAuthorUsername(@Param("user") String username, Pageable pageable);
    Page<Snip> findByAuthor(User user, Pageable pageable);
}
