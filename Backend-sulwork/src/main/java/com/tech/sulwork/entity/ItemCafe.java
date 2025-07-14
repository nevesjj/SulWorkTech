package com.tech.sulwork.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "itens_cafe", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"descricao", "data_cafe"})
})
public class ItemCafe implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idItem;

    @Column(name = "descricao", nullable = false, length = 40)
    private String descricao;

    @Column(name = "data_cafe", nullable = false)
    private LocalDate dataCafe;

    @Column(name = "entregue")
    private Boolean entregue = false;

    @ManyToOne
    @JoinColumn(name = "colaborador_id", nullable = false)
    private Colaborador colaborador;
}
