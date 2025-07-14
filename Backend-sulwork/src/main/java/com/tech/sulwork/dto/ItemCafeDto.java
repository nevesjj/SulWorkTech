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

    public ItemCafeDto(Long idItem, String descricao, LocalDate dataCafe, Boolean entregue, String cpfColaborador) {
        this.idItem = idItem;
        this.descricao = descricao;
        this.dataCafe = dataCafe;
        this.entregue = entregue;
        this.cpfColaborador = cpfColaborador;
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
