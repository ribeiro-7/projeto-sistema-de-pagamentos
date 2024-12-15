package api.lp2.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.lp2.models.Transacao;
import api.lp2.models.enums.StatusTransacao;
import api.lp2.repositories.TransacaoRepository;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private UserService userService;


    public Transacao findById(Long id){
        Optional<Transacao> transacao = this.transacaoRepository.findById(id);
        return transacao.orElseThrow(() -> new RuntimeException("Transação não encontrada! ID inválido."));
    }

    public Transacao realizarTransacao(Long id, BigDecimal valor, String metodoDePagamento){
        if (!userService.existsByid(id)) {
            throw new RuntimeException("Usuário não encontrado! ID inválido.");
        }

        Transacao transacao = new Transacao();
        transacao.setUser(userService.findById(id));
        transacao.setValor(valor);
        transacao.setMetodoDePagamento(metodoDePagamento);
        transacao.setStatus(StatusTransacao.PENDENTE);
        transacao.setData(LocalDateTime.now());

        return transacaoRepository.save(transacao);
    }

    public Transacao processarTransacao(Long id, boolean concluido){

        Transacao transacao = new Transacao();

        if(concluido){
            transacao.setStatus(StatusTransacao.APROVADO);
        }
        else{
            transacao.setStatus(StatusTransacao.REJEITADO);
        }

        return transacaoRepository.save(transacao);

    }

    public Transacao realizarReembolso(Long id){

        Transacao transacao = findById(id);

        if(!transacao.getStatus().equals(StatusTransacao.APROVADO)){
            throw new RuntimeException("Transação inválida para realizar reembolso.");
        }

        transacao.setStatus(StatusTransacao.REEMBOLSADO);
        transacaoRepository.save(transacao);


        Transacao reembolso = new Transacao();
        reembolso.setUser(transacao.getUser());
        reembolso.setValor(transacao.getValor().negate());
        reembolso.setMetodoDePagamento(transacao.getMetodoDePagamento());
        reembolso.setStatus(StatusTransacao.REEMBOLSADO);
        reembolso.setData(LocalDateTime.now());


        transacaoRepository.save(reembolso);

        return reembolso;

    }


    public StatusTransacao consultarStatus(Long id){
        try{
            Transacao transacao = findById(id);
            return transacao.getStatus();
        }
        catch(Exception e){
            throw new RuntimeException("ID de transação inválido.");
        }

    }
}
