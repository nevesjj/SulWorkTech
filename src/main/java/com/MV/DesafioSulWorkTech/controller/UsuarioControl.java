package com.MV.DesafioSulWorkTech.controller;

import com.MV.DesafioSulWorkTech.entity.Usuario;
import com.MV.DesafioSulWorkTech.exception.ResourceNotFoundException;
import com.MV.DesafioSulWorkTech.service.UsuarioServicos;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/usuario")
public class UsuarioControl {

    private final UsuarioServicos usuarioServicos;

    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        Usuario salvarUsuario = usuarioServicos.criarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.OK).body(salvarUsuario);
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Usuario> buscarUsuario(@PathVariable String cpf) {
        Optional<Usuario> usuario = usuarioServicos.buscarUsuario(cpf);
        return ResponseEntity.status(HttpStatus.OK).body(usuario.get());
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable String cpf, @RequestBody Usuario usuario) {
        try {
            Usuario atualizandoUsuario = usuarioServicos.atualizarUsuario(cpf, usuario);
            return ResponseEntity.status(HttpStatus.OK).body(atualizandoUsuario);
        } catch (ResourceNotFoundException e) {
            throw e;
        }
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable String cpf) {
        usuarioServicos.removerUsuario(cpf);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Usuario>> buscarTodosUsuarios(String todos) {
        List<Usuario> usuarios = usuarioServicos.buscarTodosUsuarios();
        return ResponseEntity.status(HttpStatus.OK).body(usuarios);
    }
}