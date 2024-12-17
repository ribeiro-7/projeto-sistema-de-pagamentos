package api.lp2.controllers.Requests;

import java.math.BigDecimal;

import api.lp2.models.Carteira;
import lombok.Data;


@Data
public class TransacaoRequest {
    
    private Carteira carteira;
    private BigDecimal valor;
    private String metodoDePagamento;
}
