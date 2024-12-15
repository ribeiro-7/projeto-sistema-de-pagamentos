package api.lp2.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import api.lp2.models.enums.StatusTransacao;



//Tabela de transação

@Entity
@Table(name = Transacao.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Transacao {

    public static final String TABLE_NAME = "transacoes";   

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;


    @Positive
    @NotNull
    @NotEmpty
    @Column(name = "valor", nullable = false)
    private BigDecimal valor;


    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    @NotEmpty
    private StatusTransacao status;


    @NotNull
    @NotEmpty
    @Column(name = "data", nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime data;

    @NotNull
    @NotEmpty
    @Column(name = "metodo_de_pagamento", nullable = false)
    private String metodoDePagamento;



}
