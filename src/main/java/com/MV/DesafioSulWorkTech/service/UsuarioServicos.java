package com.MV.DesafioSulWorkTech.service;

import com.MV.DesafioSulWorkTech.entity.Usuario;
import com.MV.DesafioSulWorkTech.repository.UsuarioRepo;
import jakarta.persistence.Id;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServicos {

    @Autowired
    private UsuarioRepo usuarioRepo;

    @Transactional
    public Usuario criarUsuario(Usuario usuario) {
        return usuarioRepo.save(usuario);
    }
    public Optional<Usuario> buscarUsuario(String cpf) {return usuarioRepo.findById(cpf);}
}
