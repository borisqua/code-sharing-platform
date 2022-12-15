package crud.app.jpa.entities;

import lombok.extern.log4j.Log4j2;

import javax.persistence.*;

@Log4j2
public class SnippetEntityEventsAuditor {
    
    @PrePersist
    @PreUpdate
    @PreRemove
    private void beforeAnyUpdate(SnippetEntity snippetEntity) {
        if (snippetEntity.getId() == null) {
            log.info("[SNIPPET AUDIT] About to add a new snippet");
        } else {
            log.info("[SNIPPET AUDIT] About to update/delete snippet: " + snippetEntity.getId());
        }
    }
    
    @PostPersist
    @PostUpdate
    @PostRemove
    private void afterAnyUpdate(SnippetEntity snippetEntity) {
        log.info("[SNIPPET AUDIT] add/update/delete complete for snippet: " + snippetEntity.getId());
    }
    
    @PostLoad
    private void afterLoad(SnippetEntity snippetEntity) {
        log.info("[SNIPPET AUDIT] snippet loaded from database: " + snippetEntity.getId());
    }
}