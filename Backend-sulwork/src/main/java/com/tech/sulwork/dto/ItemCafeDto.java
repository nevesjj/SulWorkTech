package com.tech.sulwork.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ItemCafeDto {

    private Long idItem;
    private String descricao;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataCafe;
    private Boolean entregue;
    private String cpfColaborador;
    private String nomeColaborador;

    public ItemCafeDto(Long idItem, String descricao, LocalDate dataCafe, Boolean entregue, String cpfColaborador) {
        this.idItem = idItem;
        this.descricao = descricao;
        this.dataCafe = dataCafe;
        this.entregue = entregue;
        this.cpfColaborador = cpfColaborador;
    }

    public ItemCafeDto(Long idItem, String descricao, LocalDate dataCafe, Boolean entregue, String cpfColaborador, String nomeColaborador) {
        this.idItem = idItem;
        this.descricao = descricao;
        this.dataCafe = dataCafe;
        this.entregue = entregue;
        this.cpfColaborador = cpfColaborador;
        this.nomeColaborador = nomeColaborador;
    }

    public ItemCafeDto() {
    }

    public ItemCafeDto(String descricao, LocalDate dataCafe, Boolean entregue, String cpfColaborador) {
        this.descricao = descricao;
        this.dataCafe = dataCafe;
        this.entregue = entregue;
        this.cpfColaborador = cpfColaborador;
    }
}
