package crud.app.jpa.repositories;

import crud.app.jpa.entities.SnippetEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
//@NoRepositoryBean // annotate like this all intermediate repositories: like base domain
// specific repositories
@RepositoryRestResource(path = "snippets", collectionResourceRel = "snippets")
public interface SnippetsRepository extends JpaRepository<SnippetEntity, UUID> {
//    List<SnippetEntity> findTop10ByTimeLimitAndViewsLimitOrderByDateDesc(int timeLimit, int viewsLimit);
    Page<SnippetEntity> findAllByTimeLimitAndViewsLimit(int timeLimit, int viewsLimit, Pageable pageable);
}
