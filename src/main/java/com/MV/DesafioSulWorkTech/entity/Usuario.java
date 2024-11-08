package com.MV.DesafioSulWorkTech.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "cpf", nullable = false, length = 14, unique = true)
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}|\\d{11}",
            message = "O cpf deve estar no formato 999.999.999-99 ou como 11 dígitos")
    private String cpf;
    @Column(name = "nomeColaborador", nullable = false, length = 50)
    @Size(min = 3, max = 50)
    private String nomeColaborador;
    @Column(name = "cafeDaManha", nullable = false, length = 50, unique = true)
    private String cafeDaManha;
    @Column(name = "dataCafe", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataCafe;
}