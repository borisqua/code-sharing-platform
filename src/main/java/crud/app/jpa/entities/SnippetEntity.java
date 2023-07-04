package crud.app.jpa.entities;

import crud.app.services.SharedCode;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@SuppressWarnings({"JpaDataSourceORMInspection"})

@Log4j2

@Data
@Entity
@Table(name = "snippets")
@EntityListeners(SnippetEntityEventsAuditor.class)
public class SnippetEntity {
    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
    @Column(columnDefinition = "UUID PRIMARY KEY", updatable = false, nullable = false)
    private UUID id;
    //    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
    private String code;
    private int timeLimit;
    private int viewsLimit;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id"/*, columnDefinition = "UUID FOREIGN INDEX"*/)
    private User owner;
    
    public SnippetEntity() {
    }
    
    public SnippetEntity(SharedCode code) {
        this.date = code.getDate();
        this.code = code.getCode();
        this.timeLimit = code.getTime();
        this.viewsLimit = code.getViews();
    }
    
    @PrePersist
    public void logNewSnippetSaveAttempt() {
        log.info("Attempting to add new code snippet: " + code);
    }
    
    @PostPersist
    public void logNewSnippetSaved() {
        log.info("Snippet added '" + date + "' with ID: " + id);
    }
    
    @PreRemove
    public void logSnippetRemovalAttempt() {
        log.info("Attempting to delete snippet: " + id);
    }
    
    @PostRemove
    public void logSnippetRemoval() {
        log.info("Snippet " + id + " deleted");
    }
    
    @PreUpdate
    public void logSnippetUpdateAttempt() {
        log.info("Attempting to update snippet: " + id);
    }
    
    @PostUpdate
    public void logSnippetUpdate() {
        log.info("Updated snippet: " + id);
    }
    
    @PostLoad
    public void logSnippetLoaded() {
        log.info("Snippet " + id + " loaded");
    }
    
}
