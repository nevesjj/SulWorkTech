package com.tech.sulwork.controller;

import com.tech.sulwork.dto.ColaboradorDto;
import com.tech.sulwork.dto.ItemCafeDto;
import com.tech.sulwork.service.CafeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/cafe")
@Tag(name = "Café da Manhã", description = "Gerenciamento de colaboradores e itens")
public class CafeController {

    private final CafeService service;

    public CafeController(CafeService service) {
        this.service = service;
    }

    @PostMapping("/colaborador")
    @Operation(summary = "Cadastrar colaborador")
    public ResponseEntity<String> cadastrarColaborador(@RequestBody ColaboradorDto dto) {
        try {
            service.cadastrarColaborador(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Colaborador cadastrado.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/item")
    @Operation(summary = "Adicionar item ao café")
    public ResponseEntity<String> adicionarItem(@RequestBody ItemCafeDto dto) {
        try {
            service.adicionarItem(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Item adicionado.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/itens")
    @Operation(summary = "Listar itens por data (dd/MM/yyyy)")
    public ResponseEntity<List<ItemCafeDto>> getItensPorData(@RequestParam String data) {
        try {
            List<ItemCafeDto> itens = service.listarItensPorData(data);
            return ResponseEntity.ok(itens);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/item/{id}/entregue")
    @Operation(summary = "Atualizar status de entrega do item")
    public ResponseEntity<String> atualizarEntrega(@PathVariable("id") Long id, @RequestParam Boolean entregue) {
        try {
            service.atualizarEntrega(id, entregue);
            return ResponseEntity.ok("Status de entrega atualizado com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
