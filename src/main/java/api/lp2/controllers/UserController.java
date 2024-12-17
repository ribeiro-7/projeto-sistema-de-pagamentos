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

import api.lp2.models.User;
import api.lp2.models.User.createUser;
import api.lp2.models.User.updateUser;
import api.lp2.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/user")
@Validated
public class UserController {
    

    @Autowired
    private UserService userService;


    @Operation(description = "Encontrar usuário por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário encontrado."),
        @ApiResponse(responseCode = "417", description = "Erro de validação."), 
        @ApiResponse(responseCode = "500", description = "Erro interno.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id){
        
        User user = this.userService.findById(id);
        return ResponseEntity.ok().body(user);

    }

    @Operation(description = "Criar um usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuário Criado com sucesso."),
        @ApiResponse(responseCode = "417", description = "Erro de validação."), 
        @ApiResponse(responseCode = "500", description = "Erro interno.")
    })
    @PostMapping
    @Validated(createUser.class)
    public ResponseEntity<Void> create(@Valid @RequestBody User obj){
        this.userService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @Operation(description = "Atualizar a senha de um usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuário atualizado com sucesso."),
        @ApiResponse(responseCode = "417", description = "Erro de validação."), 
        @ApiResponse(responseCode = "500", description = "Erro interno.")
    })
    @PutMapping("/{id}")
    @Validated(updateUser.class)
    public ResponseEntity<Void> update(@Valid @RequestBody User obj, @PathVariable Long id){
        obj.setId(id);
        obj = this.userService.update(obj);
        return ResponseEntity.noContent().build();
    }

    @Operation(description = "Deletar um usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso."),
        @ApiResponse(responseCode = "417", description = "Erro de validação."), 
        @ApiResponse(responseCode = "500", description = "Erro interno.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.userService.delete(id);
        return ResponseEntity.noContent().build();
    }   

}
