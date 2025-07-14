package com.tech.sulwork.repository;

import com.tech.sulwork.entity.Colaborador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ColaboradorRepository extends JpaRepository<Colaborador, Long> {

    @Query(value = "SELECT * FROM colaboradores WHERE cpf = :cpf", nativeQuery = true)
    Colaborador findByCpf(@Param("cpf") String cpf);

    @Query(value = "SELECT * FROM colaboradores WHERE nome = :nome", nativeQuery = true)
    Colaborador findByNome(@Param("nome") String nome);
}
