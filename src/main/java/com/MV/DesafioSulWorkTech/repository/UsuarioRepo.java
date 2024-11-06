package com.MV.DesafioSulWorkTech.repository;

import com.MV.DesafioSulWorkTech.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepo extends JpaRepository<Usuario, String> {
}
