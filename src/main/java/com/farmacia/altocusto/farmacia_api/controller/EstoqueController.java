package com.farmacia.altocusto.farmacia_api.controller;

import com.farmacia.altocusto.farmacia_api.model.Estoque;
import com.farmacia.altocusto.farmacia_api.service.EstoqueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/estoque")
@CrossOrigin(origins = "*")
public class EstoqueController {

    private final EstoqueService estoqueService;

    public EstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    // POST - adiciona estoque a uma farmácia
    @PostMapping
    public ResponseEntity<Estoque> adicionar(
            @RequestParam Long farmaciaId,
            @RequestParam Long medicamentoId,
            @RequestParam Integer quantidade) {

        return ResponseEntity.ok(
                estoqueService.adicionar(farmaciaId, medicamentoId, quantidade)
        );
    }

    // GET - listar estoques de um medicamento
    @GetMapping("/por-medicamento/{id}")
    public ResponseEntity<List<Estoque>> porMedicamento(@PathVariable Long id) {
        return ResponseEntity.ok(estoqueService.listarPorMedicamento(id));
    }

    // GET - listar estoques de uma farmácia
    @GetMapping("/por-farmacia/{id}")
    public ResponseEntity<List<Estoque>> porFarmacia(@PathVariable Long id) {
        return ResponseEntity.ok(estoqueService.listarPorFarmacia(id));
    }
}
