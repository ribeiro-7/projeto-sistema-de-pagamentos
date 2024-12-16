package api.lp2.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.lp2.models.Carteira;
import api.lp2.repositories.CarteiraRepository;
import jakarta.transaction.Transactional;

@Service
public class CarteiraService {
    

    @Autowired
    private CarteiraRepository carteiraRepository;


    public boolean existsByid(Long id){
        return carteiraRepository.existsById(id);
    }

    public Carteira findById(Long id){
        Optional<Carteira> carteira = this.carteiraRepository.findById(id);
        return carteira.orElseThrow(() -> new RuntimeException("Usuário não existe! ID inválido."));
    }


    @Transactional
    public Carteira update(Carteira carteira) {
        if (carteira == null || carteira.getId() == null) {
            throw new RuntimeException("Carteira inválida para atualização.");
        }
        return carteiraRepository.save(carteira);
    }


}
