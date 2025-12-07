package com.farmacia.altocusto.farmacia_api.controller;

import com.farmacia.altocusto.farmacia_api.model.Medicamento;
import com.farmacia.altocusto.farmacia_api.service.MedicamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/medicamentos")
@CrossOrigin(origins = "*")
public class MedicamentoController {

    private final MedicamentoService medicamentoService;

    public MedicamentoController(MedicamentoService medicamentoService) {
        this.medicamentoService = medicamentoService;
    }

    @PostMapping
    public ResponseEntity<Medicamento> criar(@RequestBody Medicamento medicamento) {
        return ResponseEntity.ok(medicamentoService.criar(medicamento));
    }

    @GetMapping
    public ResponseEntity<List<Medicamento>> listarTodos() {
        return ResponseEntity.ok(medicamentoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medicamento> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(medicamentoService.buscarPorId(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Medicamento>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(medicamentoService.buscarPorNome(nome));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        medicamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
