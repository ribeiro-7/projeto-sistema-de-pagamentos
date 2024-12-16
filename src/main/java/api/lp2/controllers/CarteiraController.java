package api.lp2.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import api.lp2.models.Carteira;
import api.lp2.services.CarteiraService;
import jakarta.validation.Valid;


//Vai retornar o saldo do usuário
@Controller
@RequestMapping("/carteira")
@Validated
public class CarteiraController {
    
    @Autowired
    private CarteiraService carteiraService;


    @GetMapping("/{id}")
    public ResponseEntity<Carteira> findById(@PathVariable Long id){

        Carteira carteira = this.carteiraService.findById(id);
        return ResponseEntity.ok().body(carteira);

    }

    
    @PostMapping
    @Validated
    public ResponseEntity<Void> createCarteira(@Valid @RequestBody Carteira carteira){

        this.carteiraService.create(carteira);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(carteira.getId()).toUri();
        return ResponseEntity.created(uri).build();
        
    }


    @PutMapping("/{id}")
    @Validated
    public ResponseEntity<Void> updateCarteira(@Valid @RequestBody Carteira carteira, @PathVariable Long id){

        carteira.setId(id);
        this.carteiraService.update(carteira);
        return ResponseEntity.noContent().build();

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarteira(@PathVariable Long id){

        this.carteiraService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
