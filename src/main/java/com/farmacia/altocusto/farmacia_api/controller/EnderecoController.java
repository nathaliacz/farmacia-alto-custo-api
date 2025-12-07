package com.farmacia.altocusto.farmacia_api.controller;

import com.farmacia.altocusto.farmacia_api.model.Endereco;
import com.farmacia.altocusto.farmacia_api.service.EnderecoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class EnderecoController {

    private final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    // POST /api/v1/usuarios/{usuarioId}/enderecos
    @PostMapping("/usuarios/{usuarioId}/enderecos")
    public ResponseEntity<Endereco> criar(
            @PathVariable Long usuarioId,
            @RequestBody Endereco endereco) {

        Endereco salvo = enderecoService.criar(usuarioId, endereco);
        return ResponseEntity.ok(salvo);
    }

    // GET /api/v1/usuarios/{usuarioId}/enderecos
    @GetMapping("/usuarios/{usuarioId}/enderecos")
    public ResponseEntity<List<Endereco>> listar(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(enderecoService.listarPorUsuario(usuarioId));
    }

    // DELETE /api/v1/enderecos/{id}
    @DeleteMapping("/enderecos/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        enderecoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}