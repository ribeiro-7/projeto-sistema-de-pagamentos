package api.lp2.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.lp2.models.Carteira;
import api.lp2.models.User;
import api.lp2.repositories.CarteiraRepository;
import jakarta.transaction.Transactional;


@Service
public class CarteiraService {
    

    @Autowired
    private CarteiraRepository carteiraRepository;

    @Autowired
    private UserService userService;


    public boolean existsByid(Long id){
        try{
            return carteiraRepository.existsById(id);
        }catch(Exception e){
            throw new RuntimeException("Não foi possível encontrar a carteira com ID: " + id);
        }
        
    }

    public Carteira findById(Long id){
        Optional<Carteira> carteira = this.carteiraRepository.findById(id);
        return carteira.orElseThrow(() -> new RuntimeException("Usuário não existe! ID inválido."));
    }

    @Transactional
    public Carteira create(Carteira obj){
        
        try{
            User user = this.userService.findById(obj.getUser().getId());
            obj.setId(null);
            obj.setUser(user);
            obj = this.carteiraRepository.save(obj);
            return obj;
        }catch(Exception e){
            throw new RuntimeException("Não foi possível criar a carteira.");
        }

        
    }


    @Transactional
    public Carteira update(Carteira carteira) {
        if (carteira == null || carteira.getId() == null) {
            throw new RuntimeException("Carteira inválida para atualização.");
        }
        return carteiraRepository.save(carteira);
    }

    public void delete(Long id) {
        findById(id);
        try {
            this.carteiraRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Não é possível excluir pois há entidades relacionadas!");
        }
    }


}
