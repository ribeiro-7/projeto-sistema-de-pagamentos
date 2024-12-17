package api.lp2.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.lp2.models.Carteira;
import api.lp2.models.Transacao;
import api.lp2.models.enums.StatusTransacao;
import api.lp2.repositories.TransacaoRepository;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private CarteiraService carteiraService;


    public Transacao findById(Long id){
        Optional<Transacao> transacao = this.transacaoRepository.findById(id);
        return transacao.orElseThrow(() -> new RuntimeException("Transação não encontrada! ID inválido."));
    }

    public Transacao realizarTransacao(Long id, BigDecimal valor, String metodoDePagamento){
        if (!carteiraService.existsByid(id)) {
            throw new RuntimeException("Usuário não encontrado! ID inválido.");
        }


        Carteira carteira = carteiraService.findById(id);

        if (carteira.getSaldo().compareTo(valor) < 0) {
            throw new RuntimeException("Saldo insuficiente!");
        }
        
        Transacao transacao = new Transacao();
        transacao.setCarteira(carteira);
        transacao.setValor(valor);
        transacao.setMetodoDePagamento(metodoDePagamento);
        transacao.setStatus(StatusTransacao.PENDENTE);
        transacao.setData(LocalDateTime.now());

        transacaoRepository.save(transacao);

        carteira.setSaldo(carteira.getSaldo().subtract(valor));
        carteiraService.update(carteira);

        return transacao;
    }


    public Transacao processarTransacao(Long id, boolean concluido){
        try{
            Transacao transacao = findById(id);

            if(concluido){
                transacao.setStatus(StatusTransacao.APROVADO);
            }
            else{
                transacao.setStatus(StatusTransacao.REJEITADO);
            }

            return transacaoRepository.save(transacao);
        }catch(Exception e){
            throw new RuntimeException("Transação de ID " + id + " não encontrada!");
        }

    }

    public Transacao realizarReembolso(Long id){

        Transacao transacao = findById(id);

        if(!transacao.getStatus().equals(StatusTransacao.APROVADO)){
            throw new RuntimeException("Transação inválida para realizar reembolso.");
        }

        transacao.setStatus(StatusTransacao.REEMBOLSADO);
        transacaoRepository.save(transacao);


        Transacao reembolso = new Transacao();
        reembolso.setCarteira(transacao.getCarteira());
        reembolso.setValor(transacao.getValor().negate());
        reembolso.setMetodoDePagamento(transacao.getMetodoDePagamento());
        reembolso.setStatus(StatusTransacao.REEMBOLSADO);
        reembolso.setData(LocalDateTime.now());


        transacao.getCarteira().setSaldo(transacao.getCarteira().getSaldo().add(transacao.getValor()));

        carteiraService.update(transacao.getCarteira());

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


    public void delete(Long id) {
        findById(id);
        try {
            this.transacaoRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Não é possível excluir pois há entidades relacionadas!");
        }
    }

}
