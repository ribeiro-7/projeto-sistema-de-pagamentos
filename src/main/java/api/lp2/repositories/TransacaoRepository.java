package api.lp2.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import api.lp2.models.Transacao;


public interface TransacaoRepository extends JpaRepository<Transacao, Long>{

}
