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



@Controller
@RequestMapping("/transacao")
@Validated
public class TransacaoController {
    
    @Autowired
    private TransacaoService transacaoService;


    @GetMapping("/{id}")
    public ResponseEntity<Transacao> findById(@PathVariable Long id){

        Transacao transacao = this.transacaoService.findById(id);
        return ResponseEntity.ok().body(transacao);

    }

    
    @GetMapping("/{id}/status")
    public ResponseEntity<String> consultarStatus(@PathVariable Long id){

        try{
            StatusTransacao status = transacaoService.consultarStatus(id);
            return ResponseEntity.ok().body("Status da transação: " + status + "ID: " + id);
        }catch(Exception e){
            throw new RuntimeException("Não foi possível verificar o status da transação.");
        }

    }

    
    @PostMapping
    public ResponseEntity<Void> createTransacao (@RequestBody TransacaoRequest request) {
        try {
            Transacao transacao = this.transacaoService.realizarTransacao(request.getUser().getId(), request.getValor(), request.getMetodoDePagamento());
    
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(transacao.getId()).toUri();
            return ResponseEntity.created(uri).build();
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível fazer essa transação: " + e.getMessage(), e);
        }
    }



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

    
    @PostMapping("/reembolso/{id}")
    public ResponseEntity<Void> realizarReembolso(@PathVariable Long id){

        try{
            transacaoService.realizarReembolso(id);
            return ResponseEntity.noContent().build();
        }catch(Exception e){
            throw new RuntimeException("Não foi possível realizar o reembolso.");
        }

    }


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