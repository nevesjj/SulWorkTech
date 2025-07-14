package com.tech.sulwork.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "colaboradores")
public class Colaborador implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idColaborador;

    @Column(name = "cpf", unique = true, nullable = false)
    private String cpf;

    @Column(name = "nome", unique = true, nullable = false)
    private String nome;

    @OneToMany(mappedBy = "colaborador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCafe> itens;
}
