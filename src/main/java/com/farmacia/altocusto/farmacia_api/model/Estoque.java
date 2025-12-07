package com.farmacia.altocusto.farmacia_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "estoques")
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Muitas entradas de estoque para UMA farmácia
    @ManyToOne
    @JoinColumn(name = "farmacia_id", nullable = false)
    private Farmacia farmacia;

    // Muitas entradas de estoque para UM medicamento
    @ManyToOne
    @JoinColumn(name = "medicamento_id", nullable = false)
    private Medicamento medicamento;

    @Column(nullable = false)
    private Integer quantidade;

    public Estoque() {}

    // GETTERS E SETTERS

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Farmacia getFarmacia() { return farmacia; }
    public void setFarmacia(Farmacia farmacia) { this.farmacia = farmacia; }

    public Medicamento getMedicamento() { return medicamento; }
    public void setMedicamento(Medicamento medicamento) { this.medicamento = medicamento; }

    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
}

