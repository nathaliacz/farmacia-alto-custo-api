package com.farmacia.altocusto.farmacia_api.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "enderecos_farmacia")
public class EnderecoFarmacia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // RELACIONAMENTO 1:1 COM FARMÁCIA
    @OneToOne
    @JoinColumn(name = "farmacia_id", nullable = false, unique = true)
    @JsonIgnore  // evita loop na hora de serializar para JSON
    private Farmacia farmacia;

    @Column(nullable = false, length = 100)
    private String logradouro;

    @Column(nullable = false, length = 10)
    private String numero;

    @Column(length = 60)
    private String complemento;

    @Column(nullable = false, length = 80)
    private String bairro;

    @Column(nullable = false, length = 80)
    private String cidade;

    @Column(nullable = false, length = 2)
    private String estado; // SP, RJ...

    @Column(nullable = false, length = 9)
    private String cep;

    public EnderecoFarmacia() {}

    // Getters e setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Farmacia getFarmacia() { return farmacia; }
    public void setFarmacia(Farmacia farmacia) { this.farmacia = farmacia; }

    public String getLogradouro() { return logradouro; }
    public void setLogradouro(String logradouro) { this.logradouro = logradouro; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getComplemento() { return complemento; }
    public void setComplemento(String complemento) { this.complemento = complemento; }

    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }
}

