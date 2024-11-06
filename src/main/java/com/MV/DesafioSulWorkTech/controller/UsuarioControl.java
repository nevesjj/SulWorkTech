package com.MV.DesafioSulWorkTech.controller;

import com.MV.DesafioSulWorkTech.entity.Usuario;
import com.MV.DesafioSulWorkTech.service.UsuarioServicos;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/usuario")
public class UsuarioControl {

    private final UsuarioServicos usuarioServicos;

    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        Usuario salvarUsuario = usuarioServicos.criarUsuario(usuario);
        return ResponseEntity.status(201).body(salvarUsuario);
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Usuario> buscarUsuario(@PathVariable String cpf) {
        Optional<Usuario> usuario = usuarioServicos.buscarUsuario(cpf);
        return ResponseEntity.status(200).body(usuario.get());
    }
}
