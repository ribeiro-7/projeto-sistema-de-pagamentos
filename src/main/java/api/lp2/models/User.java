package api.lp2.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

    public static final String TABLE_NAME = "users";

    public interface createUser{}

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //"Auto-increment"
    @Column(name = "id", unique=true) 
    private Long id;


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
    
    @OneToMany(mappedBy = "user")
    private List<Transacao> transacoes = new ArrayList<Transacao>();


}
