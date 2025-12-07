package com.farmacia.altocusto.farmacia_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "enderecos")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // relacionamento N:1 -> vários endereços para um usuário
    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false, length = 100)
    private String apelido; // "Casa", "Trabalho", etc.

    @Column(nullable = false, length = 9)
    private String cep;

    @Column(nullable = false, length = 150)
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
    private String estado; // SP, RJ, etc.

    @Column(nullable = false)
    private boolean principal = false;

    public Endereco() {}

    // Getters e setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public String getApelido() { return apelido; }
    public void setApelido(String apelido) { this.apelido = apelido; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

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

    public boolean isPrincipal() { return principal; }
    public void setPrincipal(boolean principal) { this.principal = principal; }
}
