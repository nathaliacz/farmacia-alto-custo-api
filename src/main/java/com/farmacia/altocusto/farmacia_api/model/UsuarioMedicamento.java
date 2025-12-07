package com.farmacia.altocusto.farmacia_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios_medicamentos")
public class UsuarioMedicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // MUITOS UsuarioMedicamento para 1 Usuario
    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // MUITOS UsuarioMedicamento para 1 Medicamento
    @ManyToOne(optional = false)
    @JoinColumn(name = "medicamento_id", nullable = false)
    private Medicamento medicamento;

    @Column(length = 255)
    private String observacoes; // "uso contínuo", "2x ao dia", etc.

    public UsuarioMedicamento() {}

    // Getters e Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Medicamento getMedicamento() { return medicamento; }
    public void setMedicamento(Medicamento medicamento) { this.medicamento = medicamento; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
}

