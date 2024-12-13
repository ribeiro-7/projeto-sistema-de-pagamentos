package api.lp2.models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;




@Entity
@Table(name = RegistroDeAutoria.TABLE_NAME)
public class RegistroDeAutoria {

    public static final String TABLE_NAME = "registro_de_auditoria";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;


    //Ação realizada, Por exemplo: Reembolso processado, Pagamento Realizado
    @Column(name = "acao", length = 30, nullable = false)
    private String acao;

    
    @Column(name = "entidade_alvo", nullable = false)
    private String entidadeAlvo;

    
    @Column(name = "id_alvo", nullable = false)
    private Long idAlvo;


    @Column(name = "data_da_acao", nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dataDaAcao;


}
