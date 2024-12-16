package api.lp2.models;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = Carteira.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Carteira {

    public static final String TABLE_NAME = "carteiras";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "saldo", nullable = false)
    @NotNull
    private BigDecimal saldo = BigDecimal.ZERO;


    @OneToMany(mappedBy = "carteira")
    private List<Transacao> transacoes = new ArrayList<Transacao>();

}
