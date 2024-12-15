package api.lp2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import api.lp2.models.Carteira;

public interface CarteiraRepository extends JpaRepository<Carteira, Long>{
    
}
