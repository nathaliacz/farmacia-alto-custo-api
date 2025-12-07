package com.farmacia.altocusto.farmacia_api.service;

import com.farmacia.altocusto.farmacia_api.model.Medicamento;
import com.farmacia.altocusto.farmacia_api.repository.MedicamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicamentoService {

    private final MedicamentoRepository medicamentoRepository;

    public MedicamentoService(MedicamentoRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }

    public Medicamento criar(Medicamento medicamento) {
        return medicamentoRepository.save(medicamento);
    }

    public List<Medicamento> listarTodos() {
        return medicamentoRepository.findAll();
    }

    public Medicamento buscarPorId(Long id) {
        return medicamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicamento não encontrado"));
    }

    public List<Medicamento> buscarPorNome(String nome) {
        return medicamentoRepository.findByNomeContainingIgnoreCase(nome);
    }

    public void deletar(Long id) {
        medicamentoRepository.deleteById(id);
    }
}
