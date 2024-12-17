package api.lp2.controllers.Requests;

import java.math.BigDecimal;

import api.lp2.models.User;
import lombok.Data;


@Data
public class TransacaoRequest {
    
    private User user; 
    private BigDecimal valor;
    private String metodoDePagamento;
}
