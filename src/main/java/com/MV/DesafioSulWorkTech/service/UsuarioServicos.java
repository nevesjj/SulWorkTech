package com.MV.DesafioSulWorkTech.service;

import com.MV.DesafioSulWorkTech.entity.Usuario;
import com.MV.DesafioSulWorkTech.exception.ResourceNotFoundException;
import com.MV.DesafioSulWorkTech.repository.UsuarioRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServicos {

    @Autowired
    private UsuarioRepo usuarioRepo;

    @Transactional
    public Usuario criarUsuario(Usuario usuario) {
        return usuarioRepo.save(usuario);
    }

    public Optional<Usuario> buscarUsuario(String cpf) {
        return usuarioRepo.findByCpf(cpf);
    }

    @Transactional
    public Usuario atualizarUsuario(String cpf, Usuario usuarioAtualizado) {
        return usuarioRepo.findByCpf(cpf).map(usuario -> {
            usuario.setNomeColaborador(usuarioAtualizado.getNomeColaborador());
            usuario.setCafeDaManha(usuarioAtualizado.getCafeDaManha());
            usuario.setDataCafe(usuarioAtualizado.getDataCafe());
            return usuarioRepo.save(usuario);
        }).orElseThrow(() -> new ResourceNotFoundException("Cpf não encontrado: " + cpf));
    }

    @Transactional
    public void removerUsuario(String cpf) {
        usuarioRepo.deleteByCpf(cpf);
    }

    public List<Usuario> buscarTodosUsuarios() {
        return usuarioRepo.findAll();
    }

}