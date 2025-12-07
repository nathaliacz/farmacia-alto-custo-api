package com.farmacia.altocusto.farmacia_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "medicamentos")
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, length = 150)
    private String principioAtivo;

    @Column(nullable = false, length = 100)
    private String fabricante;

    @Column(nullable = true, length = 50)
    private String tarja;

    @Column(nullable = true, length = 255)
    private String indicacao;

    @Column(nullable = true, length = 255)
    private String observacoes;

    public Medicamento() {}

    // GETTERS E SETTERS

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getPrincipioAtivo() { return principioAtivo; }
    public void setPrincipioAtivo(String principioAtivo) { this.principioAtivo = principioAtivo; }

    public String getFabricante() { return fabricante; }
    public void setFabricante(String fabricante) { this.fabricante = fabricante; }

    public String getTarja() { return tarja; }
    public void setTarja(String tarja) { this.tarja = tarja; }

    public String getIndicacao() { return indicacao; }
    public void setIndicacao(String indicacao) { this.indicacao = indicacao; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
}

