package com.MV.DesafioSulWorkTech.repository;

import com.MV.DesafioSulWorkTech.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UsuarioRepo extends JpaRepository<Usuario, String> {
    Optional<Usuario> findByCpf(String cpf);

    void deleteByCpf(String cpf);
}