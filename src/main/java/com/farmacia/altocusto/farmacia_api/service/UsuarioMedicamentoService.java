package com.farmacia.altocusto.farmacia_api.service;

import com.farmacia.altocusto.farmacia_api.model.Medicamento;
import com.farmacia.altocusto.farmacia_api.model.Usuario;
import com.farmacia.altocusto.farmacia_api.model.UsuarioMedicamento;
import com.farmacia.altocusto.farmacia_api.repository.MedicamentoRepository;
import com.farmacia.altocusto.farmacia_api.repository.UsuarioMedicamentoRepository;
import com.farmacia.altocusto.farmacia_api.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioMedicamentoService {

    private final UsuarioMedicamentoRepository usuarioMedicamentoRepository;
    private final UsuarioRepository usuarioRepository;
    private final MedicamentoRepository medicamentoRepository;

    public UsuarioMedicamentoService(UsuarioMedicamentoRepository usuarioMedicamentoRepository,
                                     UsuarioRepository usuarioRepository,
                                     MedicamentoRepository medicamentoRepository) {
        this.usuarioMedicamentoRepository = usuarioMedicamentoRepository;
        this.usuarioRepository = usuarioRepository;
        this.medicamentoRepository = medicamentoRepository;
    }

    public UsuarioMedicamento adicionarMedicamento(Long usuarioId, Long medicamentoId, String observacoes) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Medicamento medicamento = medicamentoRepository.findById(medicamentoId)
                .orElseThrow(() -> new RuntimeException("Medicamento não encontrado"));

        UsuarioMedicamento um = new UsuarioMedicamento();
        um.setUsuario(usuario);
        um.setMedicamento(medicamento);
        um.setObservacoes(observacoes);

        return usuarioMedicamentoRepository.save(um);
    }

    public List<UsuarioMedicamento> listarPorUsuario(Long usuarioId) {
        return usuarioMedicamentoRepository.findByUsuarioId(usuarioId);
    }

    public void remover(Long usuarioId, Long usuarioMedicamentoId) {
        UsuarioMedicamento um = usuarioMedicamentoRepository.findById(usuarioMedicamentoId)
                .orElseThrow(() -> new RuntimeException("Registro de usuário-medicamento não encontrado"));

        // segurança básica: garante que esse registro é mesmo do usuário informado
        if (!um.getUsuario().getId().equals(usuarioId)) {
            throw new RuntimeException("Esse medicamento não pertence a este usuário");
        }

        usuarioMedicamentoRepository.delete(um);
    }
}
