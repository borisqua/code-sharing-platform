package crud.app.services;

import java.util.UUID;

public class SharedCodeCreationResponse {
    final UUID id;
    
    SharedCodeCreationResponse(UUID id) {
        this.id = id;
    }
    
    public UUID getId() {
        return id;
    }
    
}
