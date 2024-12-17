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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import api.lp2.controllers.Requests.ProcessarTransacaoRequest;
import api.lp2.controllers.Requests.TransacaoRequest;
import api.lp2.models.Transacao;
import api.lp2.models.enums.StatusTransacao;
import api.lp2.services.TransacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;



@Controller
@RequestMapping("/transacao")
@Validated
public class TransacaoController {
    
    @Autowired
    private TransacaoService transacaoService;


    @Operation(description = "Encontrar usuário da transação por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário de transação encontrado."),
        @ApiResponse(responseCode = "417", description = "Erro de validação."), 
        @ApiResponse(responseCode = "500", description = "Erro interno.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Transacao> findById(@PathVariable Long id){

        Transacao transacao = this.transacaoService.findById(id);
        return ResponseEntity.ok().body(transacao);

    }


    @Operation(description = "Consultar status da transação por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Status de transação encontrado."),
        @ApiResponse(responseCode = "417", description = "Erro de validação."), 
        @ApiResponse(responseCode = "500", description = "Erro interno.")
    })
    @GetMapping("/{id}/status")
    public ResponseEntity<String> consultarStatus(@PathVariable Long id){

        try{
            StatusTransacao status = transacaoService.consultarStatus(id);
            return ResponseEntity.ok().body("Status da transação: " + status + " ID: " + id);
        }catch(Exception e){
            throw new RuntimeException("Não foi possível verificar o status da transação.");
        }

    }

    
    @Operation(description = "Realizar transação")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Transação realizada com sucesso."),
        @ApiResponse(responseCode = "417", description = "Erro de validação."), 
        @ApiResponse(responseCode = "500", description = "Erro interno.")
    })
    @PostMapping
    public ResponseEntity<Void> createTransacao (@RequestBody TransacaoRequest request) {
        try {
            Transacao transacao = this.transacaoService.realizarTransacao(request.getCarteira().getId(), request.getValor(), request.getMetodoDePagamento());
    
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(transacao.getId()).toUri();
            return ResponseEntity.created(uri).build();
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível fazer essa transação: " + e.getMessage(), e);
        }
    }



    @Operation(description = "Validar uma transação")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Transação proecssada."),
        @ApiResponse(responseCode = "417", description = "Erro de validação."), 
        @ApiResponse(responseCode = "500", description = "Erro interno.")
    })
    @PostMapping("/processar")
    public ResponseEntity<Void> updateTransacao(@RequestBody ProcessarTransacaoRequest request){

        try{
            Transacao transacao = this.transacaoService.processarTransacao(request.getId(), request.isConcluido());
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(transacao.getId()).toUri();
            return ResponseEntity.created(uri).build();
        }catch(Exception e){
            throw new RuntimeException("Não foi possível realizar o processamento da transação de ID: " + request.getId() + ".");
        }

    }

    
    @Operation(description = "Realizar reembolso")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Processo de reembolso realizado."),
        @ApiResponse(responseCode = "417", description = "Erro de validação."), 
        @ApiResponse(responseCode = "500", description = "Erro interno.")
    })
    @PostMapping("/reembolso/{id}")
    public ResponseEntity<Void> realizarReembolso(@PathVariable Long id){

        try{
            transacaoService.realizarReembolso(id);
            return ResponseEntity.noContent().build();
        }catch(Exception e){
            throw new RuntimeException("Não foi possível realizar o reembolso.");
        }

    }


    @Operation(description = "Deletar transação")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Transação deletada com sucesso."),
        @ApiResponse(responseCode = "417", description = "Erro de validação."), 
        @ApiResponse(responseCode = "500", description = "Erro interno.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransacao(@PathVariable Long id){

        try{
            this.transacaoService.delete(id);
            return ResponseEntity.noContent().build();
        }catch(Exception e){
            throw new RuntimeException("Não foi possível deletar essa transação.");
        }

    }

}