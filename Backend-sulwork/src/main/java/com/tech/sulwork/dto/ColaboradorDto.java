package com.tech.sulwork.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class ColaboradorDto {

    @NotNull(message = "O Cpf é obrigatório")
    private String cpf;

    @NotNull(message = "O nome é obrigatório")
    private String nome;

    public ColaboradorDto() {
        this.cpf = cpf;
        this.nome = nome;
    }
}
