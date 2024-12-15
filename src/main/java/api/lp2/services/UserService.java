package api.lp2.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import api.lp2.models.User;
import api.lp2.repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    
    public User findById(Long id){
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new RuntimeException("Usuário não existe! ID inválido."));
    }


    public boolean existsByid(Long id){
        return userRepository.existsById(id);
    }


    @Transactional
    public User create(User obj){
        obj.setId(null);
        obj = this.userRepository.save(obj);
        return obj;
    }

    
    @Transactional
    public User update(User obj){
        User newObj = findById(obj.getId());
        newObj.setPassword(obj.getPassword());
        return this.userRepository.save(newObj);
    }


    public void delete(Long id){
        findById(id);
        try{
            this.userRepository.deleteById(id);
        }
        catch(Exception e){
            throw new RuntimeException("Não é possível excluir, pois há entidades relacionadas!");
        }

    }

}