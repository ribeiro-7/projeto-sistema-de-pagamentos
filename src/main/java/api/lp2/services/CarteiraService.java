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
    public Carteira create(Carteira obj) {
        if(obj.getUser() == null){
            throw new IllegalArgumentException("Erro: O usuário está nulo no serviço.");
        }
        User user = this.userService.findById(obj.getUser().getId());
        obj.setId(null);
        obj.setUser(user);
        obj = this.carteiraRepository.save(obj);
        return obj;

    }



    @Transactional
    public Carteira update(Carteira carteira) {
        if (carteira == null || carteira.getId() == null) {
            throw new RuntimeException("Carteira inválida para atualização.");
        }

        Carteira carteiraExistente = carteiraRepository.findById(carteira.getId())
                .orElseThrow(() -> new RuntimeException("Carteira não encontrada."));

        if (carteira.getUser() == null) {
            carteira.setUser(carteiraExistente.getUser());
        }

        carteira.setId(carteiraExistente.getId());
        return carteiraRepository.save(carteira);
    }
    


}
