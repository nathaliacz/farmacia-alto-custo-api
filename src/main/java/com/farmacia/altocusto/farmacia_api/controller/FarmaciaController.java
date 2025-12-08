package com.farmacia.altocusto.farmacia_api.controller;

import com.farmacia.altocusto.farmacia_api.dto.LoginRequest;
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

    // CADASTRAR FARMÁCIA
    @PostMapping
    public ResponseEntity<Farmacia> criar(@RequestBody Farmacia farmacia) {
        return ResponseEntity.ok(farmaciaService.criar(farmacia));
    }

    // ✅ LOGIN FARMÁCIA USANDO DTO (email + senha)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest credenciais) {
        try {
            Farmacia farmacia = farmaciaService.login(
                    credenciais.getEmail(),
                    credenciais.getSenha()   // senha pura que veio do front
            );
            return ResponseEntity.ok(farmacia);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Farmacia> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(farmaciaService.buscar(id));
    }

    @GetMapping
    public ResponseEntity<List<Farmacia>> listarTodas() {
        List<Farmacia> farmacias = farmaciaService.listarTodas();
        return ResponseEntity.ok(farmacias);
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

    @GetMapping("/proximas-por-medicamento")
    public ResponseEntity<List<Farmacia>> buscarProximasComMedicamento(
            @RequestParam(name = "cep") String cepOuEndereco,
            @RequestParam(name = "raio") double raioKm,
            @RequestParam(name = "medicamento") String nomeMedicamento) {

        List<Farmacia> proximas =
                farmaciaService.buscarProximasComMedicamento(cepOuEndereco, raioKm, nomeMedicamento);

        return ResponseEntity.ok(proximas);
    }
}
