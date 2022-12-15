package crud.app.jpa.repositories;

import crud.app.jpa.entities.SnippetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
//@NoRepositoryBean // annotate like this all intermediate repositories: e.g. basic domain specific repositories
@RepositoryRestResource(path = "snippets", collectionResourceRel = "snippets")
public interface UsersRepository extends JpaRepository<SnippetEntity, UUID> {
}
