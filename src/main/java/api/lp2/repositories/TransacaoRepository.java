package api.lp2.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import api.lp2.enums.StatusTransacao;
import api.lp2.models.Transacao;
import java.time.LocalDateTime;


public interface TransacaoRepository extends JpaRepository<Transacao, Long>{
    
    List<Transacao> findByUser_Id(Long id);

    List<Transacao> findByValor(double valor);

    List<Transacao> findByStatus(StatusTransacao status);

    List<Transacao> findByData(LocalDateTime data);

    List<Transacao> findByMetodoDePagamento(String metodoDePagamento);



}
