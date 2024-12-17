package api.lp2.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import api.lp2.models.Carteira;
import api.lp2.services.CarteiraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/carteira")
@Validated
public class CarteiraController {
    
    @Autowired
    private CarteiraService carteiraService;


    @Operation(description = "Encontrar carteira por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário da carteira encontrado."),
        @ApiResponse(responseCode = "417", description = "Erro de validação."), 
        @ApiResponse(responseCode = "500", description = "Erro interno.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Carteira> findById(@PathVariable Long id){

        Carteira carteira = this.carteiraService.findById(id);
        return ResponseEntity.ok().body(carteira);

    }

    
    @Operation(description = "Criar carteira para usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Carteira criada com sucesso."),
        @ApiResponse(responseCode = "417", description = "Erro de validação."), 
        @ApiResponse(responseCode = "500", description = "Erro interno.")
    })
    @PostMapping
    @Validated
    public ResponseEntity<Void> createCarteira(@Valid @RequestBody Carteira obj){

        this.carteiraService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();

    }


    @Operation(description = "Atualizar carteira")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Carteira atualizada com sucesso."),
        @ApiResponse(responseCode = "417", description = "Erro de validação."), 
        @ApiResponse(responseCode = "500", description = "Erro interno.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCarteira(@RequestBody Carteira carteira, @PathVariable Long id){

        carteira.setId(id);
        this.carteiraService.update(carteira);
        return ResponseEntity.noContent().build();

    }


}
