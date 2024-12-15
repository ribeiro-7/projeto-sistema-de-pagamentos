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
import jakarta.validation.constraints.NotEmpty;
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
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany
    private List<Transacao> transacoes = new ArrayList<Transacao>();

    @Column(name = "saldo", nullable = false)
    @NotNull
    @NotEmpty
    private BigDecimal saldo = BigDecimal.ZERO;

}
