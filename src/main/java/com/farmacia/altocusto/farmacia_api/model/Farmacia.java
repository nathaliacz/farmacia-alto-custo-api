package com.farmacia.altocusto.farmacia_api.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import jakarta.persistence.Transient;

@Entity
@Table(name = "farmacias")
public class Farmacia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nomeFantasia;

    @Column(nullable = false, length = 150)
    private String razaoSocial;

    @Column(nullable = false, unique = true, length = 18)
    private String cnpj;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String senhaHash;

    @Column(nullable = false, length = 15)
    private String telefone;

    @Column(nullable = true)
    private Double latitude;

    @Column(nullable = true)
    private Double longitude;

    @Column(nullable = false)
    private LocalDateTime dataCadastro = LocalDateTime.now();

    @OneToOne(mappedBy = "farmacia", cascade = CascadeType.ALL)
    private EnderecoFarmacia enderecoFarmacia;

    @Transient
    private Double distanciaKm;


    public Farmacia() {}

    // GETTERS e SETTERS
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomeFantasia() { return nomeFantasia; }
    public void setNomeFantasia(String nomeFantasia) { this.nomeFantasia = nomeFantasia; }

    public String getRazaoSocial() { return razaoSocial; }
    public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenhaHash() { return senhaHash; }
    public void setSenhaHash(String senhaHash) { this.senhaHash = senhaHash; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public LocalDateTime getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(LocalDateTime dataCadastro) { this.dataCadastro = dataCadastro; }

    public EnderecoFarmacia getEnderecoFarmacia() {return enderecoFarmacia; }
    public void setEnderecoFarmacia(EnderecoFarmacia enderecoFarmacia) {this.enderecoFarmacia = enderecoFarmacia;}

    public Double getDistanciaKm() {return distanciaKm; }
    public void setDistanciaKm(Double distanciaKm) {this.distanciaKm = distanciaKm; }

}

