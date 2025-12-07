package com.farmacia.altocusto.farmacia_api.controller;

import com.farmacia.altocusto.farmacia_api.model.EnderecoFarmacia;
import com.farmacia.altocusto.farmacia_api.service.EnderecoFarmaciaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/farmacias/{farmaciaId}/endereco")
@CrossOrigin(origins = "*")
public class EnderecoFarmaciaController {

    private final EnderecoFarmaciaService enderecoFarmaciaService;

    public EnderecoFarmaciaController(EnderecoFarmaciaService enderecoFarmaciaService) {
        this.enderecoFarmaciaService = enderecoFarmaciaService;
    }

    // POST ou PUT: criar/atualizar endereço da farmácia
    @PostMapping
    public ResponseEntity<EnderecoFarmacia> salvar(
            @PathVariable Long farmaciaId,
            @RequestBody EnderecoFarmacia endereco) {

        EnderecoFarmacia salvo = enderecoFarmaciaService.salvar(farmaciaId, endereco);
        return ResponseEntity.ok(salvo);
    }

    // GET: buscar endereço da farmácia
    @GetMapping
    public ResponseEntity<EnderecoFarmacia> buscar(@PathVariable Long farmaciaId) {
        return ResponseEntity.ok(enderecoFarmaciaService.buscarPorFarmacia(farmaciaId));
    }

    // DELETE: excluir endereço da farmácia
    @DeleteMapping
    public ResponseEntity<Void> deletar(@PathVariable Long farmaciaId) {
        enderecoFarmaciaService.deletarPorFarmacia(farmaciaId);
        return ResponseEntity.noContent().build();
    }
}

