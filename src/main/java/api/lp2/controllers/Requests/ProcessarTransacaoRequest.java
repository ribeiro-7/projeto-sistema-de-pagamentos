package api.lp2.controllers.Requests;

import lombok.Data;

@Data
public class ProcessarTransacaoRequest {
    
    private Long id;
    private boolean concluido;

    public boolean isConcluido() {
        return concluido;
    }

    public void setConcluido(boolean concluido) {
        this.concluido = concluido;
    }
}