package com.tech.sulwork.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ItemCafeDto {

    private Long idItem;

    public String descricao;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    public LocalDate dataCafe;
    public Boolean entregue;
    public String cpfColaborador;
    public String nomeColaborador;

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

    public ItemCafeDto(String descricao, LocalDate dataCafe, Boolean entregue, String cpfColaborador, String nomeColaborador) {
        this.descricao = descricao;
        this.dataCafe = dataCafe;
        this.entregue = entregue;
        this.cpfColaborador = cpfColaborador;
        this.nomeColaborador = nomeColaborador;
    }
}
