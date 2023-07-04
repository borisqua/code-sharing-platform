package crud.app.services;

import com.fasterxml.jackson.annotation.JsonFormat;
import crud.app.jpa.entities.SnippetEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@SuppressWarnings("unused")
public class SharedCode {
    
//    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    private String code = "Empty. No such code.";
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date = LocalDateTime.now();
    private UUID id;
    private int time;
    private int views;
    
    SharedCode() {}
    
    SharedCode(SnippetEntity snippet) {
        this.id = snippet.getId();
        this.code = snippet.getCode();
        this.date = snippet.getDate();
        this.time = snippet.getTimeLimit();
        this.views = snippet.getViewsLimit();
    }

    public UUID getId() {
        return id;
    }

    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public LocalDateTime getDate() {
        return date;
    }
    
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    
    public int getTime() {
        return time;
    }
    
    public void setTime(int time) {
        this.time = time;
    }
    
    public int getViews() {
        return views;
    }
    
    public void setViews(int views) {
        this.views = views;
    }
    
    @Override
    public String toString() {
        return code;
    }
    
}
