package crud.app.jpa.entities;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;

@SuppressWarnings("JpaDataSourceORMInspection")
@Data
@Entity
@Table(name = "users")
public class User {
    
    @Id
    @Column(/*columnDefinition = "UUID PRIMARY KEY", */updatable = false, nullable = false)
    @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
    private long id;
    
    @NonNull
    private String name;
    
    @NonNull
    private String password;
    
    public User() {
    
    }
    
}
