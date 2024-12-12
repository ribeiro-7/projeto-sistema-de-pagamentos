package api.lp2.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


// Tabela do usuário "user"

@Entity
@Table(name = User.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    public static final String TABLE_NAME = "user";

    public interface createUser{}

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //"Auto-increment"
    @Column(name = "id", unique=true) 
    private long id;


    @Column(name = "username", length = 50, nullable = false, unique = true)
    @NotNull(groups = createUser.class)
    @NotEmpty(groups = createUser.class)
    @Size(groups = createUser.class, min = 5, max = 50)
    private String username;

    
    @NotNull(groups = createUser.class)
    @NotEmpty(groups = createUser.class)
    @Size(groups = createUser.class, min = 8, max = 12)
    @Column(name = "password", length = 12, nullable = false)
    private String password;
    



}
