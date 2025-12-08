package com.farmacia.altocusto.farmacia_api.service;

import com.farmacia.altocusto.farmacia_api.model.Estoque;
import com.farmacia.altocusto.farmacia_api.model.Farmacia;
import com.farmacia.altocusto.farmacia_api.model.Medicamento;
import com.farmacia.altocusto.farmacia_api.repository.EstoqueRepository;
import com.farmacia.altocusto.farmacia_api.repository.FarmaciaRepository;
import com.farmacia.altocusto.farmacia_api.repository.MedicamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;
    private final FarmaciaRepository farmaciaRepository;
    private final MedicamentoRepository medicamentoRepository;

    public EstoqueService(EstoqueRepository estoqueRepository,
                          FarmaciaRepository farmaciaRepository,
                          MedicamentoRepository medicamentoRepository) {
        this.estoqueRepository = estoqueRepository;
        this.farmaciaRepository = farmaciaRepository;
        this.medicamentoRepository = medicamentoRepository;
    }

    // ADICIONAR ITEM AO ESTOQUE DE UMA FARMÁCIA
    public Estoque adicionar(Long farmaciaId, Long medicamentoId, Integer quantidade) {

        if (quantidade == null || quantidade <= 0) {
            throw new RuntimeException("A quantidade deve ser maior que zero.");
        }

        // Busca a farmácia pelo ID
        Farmacia farmacia = farmaciaRepository.findById(farmaciaId)
                .orElseThrow(() -> new RuntimeException("Farmácia não encontrada"));

        // Busca o medicamento pelo ID
        Medicamento medicamento = medicamentoRepository.findById(medicamentoId)
                .orElseThrow(() -> new RuntimeException("Medicamento não encontrado"));

        // Cria um novo registro de estoque
        Estoque estoque = new Estoque();
        estoque.setFarmacia(farmacia);
        estoque.setMedicamento(medicamento);
        estoque.setQuantidade(quantidade);

        return estoqueRepository.save(estoque);
    }

    // LISTAR ESTOQUE POR MEDICAMENTO
    public List<Estoque> listarPorMedicamento(Long medicamentoId) {
        return estoqueRepository.findByMedicamentoId(medicamentoId);
    }

    // LISTAR ESTOQUE POR FARMÁCIA
    public List<Estoque> listarPorFarmacia(Long farmaciaId) {
        return estoqueRepository.findByFarmaciaId(farmaciaId);
    }

    // BUSCA DE FARMÁCIAS POR MEDICAMENTO
    // Query customizada
    public List<Farmacia> listarFarmaciasQueTemMedicamentoPorNome(String nome) {
        return estoqueRepository.buscarFarmaciasQueTemMedicamentoPorNome(nome);
    }
}
