package com.farmacia.altocusto.farmacia_api.service;

import com.farmacia.altocusto.farmacia_api.model.EnderecoFarmacia;
import com.farmacia.altocusto.farmacia_api.model.Farmacia;
import com.farmacia.altocusto.farmacia_api.repository.EnderecoFarmaciaRepository;
import com.farmacia.altocusto.farmacia_api.repository.FarmaciaRepository;
import org.springframework.stereotype.Service;

@Service
public class EnderecoFarmaciaService {

    private final EnderecoFarmaciaRepository enderecoFarmaciaRepository;
    private final FarmaciaRepository farmaciaRepository;

    public EnderecoFarmaciaService(EnderecoFarmaciaRepository enderecoFarmaciaRepository,
                                   FarmaciaRepository farmaciaRepository) {
        this.enderecoFarmaciaRepository = enderecoFarmaciaRepository;
        this.farmaciaRepository = farmaciaRepository;
    }

    public EnderecoFarmacia salvar(Long farmaciaId, EnderecoFarmacia endereco) {
        Farmacia farmacia = farmaciaRepository.findById(farmaciaId)
                .orElseThrow(() -> new RuntimeException("Farmácia não encontrada"));

        // amarra as duas pontas da relação
        endereco.setFarmacia(farmacia);
        farmacia.setEnderecoFarmacia(endereco);

        return enderecoFarmaciaRepository.save(endereco);
    }

    public EnderecoFarmacia buscarPorFarmacia(Long farmaciaId) {
        return enderecoFarmaciaRepository.findByFarmaciaId(farmaciaId)
                .orElseThrow(() -> new RuntimeException("Endereço da farmácia não encontrado"));
    }

    public void deletarPorFarmacia(Long farmaciaId) {
        EnderecoFarmacia endereco = buscarPorFarmacia(farmaciaId);
        enderecoFarmaciaRepository.delete(endereco);
    }
}
