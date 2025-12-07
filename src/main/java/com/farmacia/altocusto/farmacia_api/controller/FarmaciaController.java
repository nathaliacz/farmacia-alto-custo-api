package com.farmacia.altocusto.farmacia_api.controller;

import com.farmacia.altocusto.farmacia_api.model.Farmacia;
import com.farmacia.altocusto.farmacia_api.service.EstoqueService;
import com.farmacia.altocusto.farmacia_api.service.FarmaciaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/farmacias")
@CrossOrigin(origins = "*")
public class FarmaciaController {

    private final FarmaciaService farmaciaService;
    private final EstoqueService estoqueService;

    public FarmaciaController(FarmaciaService farmaciaService,
                              EstoqueService estoqueService) {
        this.farmaciaService = farmaciaService;
        this.estoqueService = estoqueService;
    }

    @PostMapping
    public ResponseEntity<Farmacia> criar(@RequestBody Farmacia farmacia) {
        return ResponseEntity.ok(farmaciaService.criar(farmacia));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Farmacia> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(farmaciaService.buscar(id));
    }

    @GetMapping("/por-medicamento")
    public ResponseEntity<List<Farmacia>> buscarPorMedicamento(@RequestParam String nome) {
        return ResponseEntity.ok(
                estoqueService.listarFarmaciasQueTemMedicamentoPorNome(nome)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        farmaciaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/proximas")
    public ResponseEntity<?> buscarProximas(@RequestParam String cep,
                                            @RequestParam double raio) {
        try {
            List<Farmacia> proximas = farmaciaService.buscarProximas(cep, raio);
            return ResponseEntity.ok(proximas);
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno ao consultar farmácias próximas.");
        }
    }


}


