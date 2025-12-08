package com.farmacia.altocusto.farmacia_api.service;

import com.farmacia.altocusto.farmacia_api.model.EnderecoFarmacia;
import com.farmacia.altocusto.farmacia_api.model.Farmacia;
import com.farmacia.altocusto.farmacia_api.repository.EstoqueRepository;
import com.farmacia.altocusto.farmacia_api.repository.FarmaciaRepository;
import com.farmacia.altocusto.farmacia_api.service.externos.GoogleMapsService;
import com.farmacia.altocusto.farmacia_api.util.HashUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class FarmaciaService {

    private final FarmaciaRepository farmaciaRepository;
    private final GoogleMapsService googleMapsService;
    private final EstoqueRepository estoqueRepository;

    public FarmaciaService(FarmaciaRepository farmaciaRepository,
                           GoogleMapsService googleMapsService,
                           EstoqueRepository estoqueRepository) {
        this.farmaciaRepository = farmaciaRepository;
        this.googleMapsService = googleMapsService;
        this.estoqueRepository = estoqueRepository;
    }

    // LISTAR TODAS
    public List<Farmacia> listarTodas() {
        return farmaciaRepository.findAll();
    }

    // 🔹 Farmácias próximas (independente de medicamento)
    public List<Farmacia> buscarProximas(String cepOuEndereco, double raioKm) {

        // 1. Obter lat/long do usuário
        double[] latlngUser = googleMapsService.obterLatLong(cepOuEndereco);
        double latUser = latlngUser[0];
        double lngUser = latlngUser[1];

        // 2. Listar todas as farmácias cadastradas
        List<Farmacia> todas = farmaciaRepository.findAll();
        List<Farmacia> proximas = new ArrayList<>();

        // 3. Calcular distância Haversine para cada farmácia
        for (Farmacia f : todas) {
            if (f.getLatitude() != null && f.getLongitude() != null) {
                double distancia = calcularDistanciaKm(
                        latUser, lngUser,
                        f.getLatitude(), f.getLongitude()
                );

                // Se estiver dentro do raio, adiciona na lista
                if (distancia <= raioKm) {
                    f.setDistanciaKm(distancia); // guarda a distância na entidade
                    proximas.add(f);
                }
            }
        }

        // 4. Ordena da mais perto pra mais longe
        proximas.sort(Comparator.comparing(Farmacia::getDistanciaKm));

        return proximas;
    }

    // 🔹 Farmácias próximas que têm um medicamento específico
    public List<Farmacia> buscarProximasComMedicamento(String cepOuEndereco,
                                                       double raioKm,
                                                       String nomeMedicamento) {

        // 1. Obter lat/long do usuário
        double[] latlngUser = googleMapsService.obterLatLong(cepOuEndereco);
        double latUser = latlngUser[0];
        double lngUser = latlngUser[1];

        // 2. Buscar farmácias que têm o medicamento em estoque
        List<Farmacia> farmaciasComMedicamento =
                estoqueRepository.buscarFarmaciasQueTemMedicamentoPorNome(nomeMedicamento);

        // Remove duplicadas, se vier repetido
        List<Farmacia> distintas = farmaciasComMedicamento.stream()
                .distinct()
                .toList();

        // 3. Filtrar por raio
        List<Farmacia> proximas = new ArrayList<>();

        for (Farmacia f : distintas) {
            if (f.getLatitude() != null && f.getLongitude() != null) {
                double distancia = calcularDistanciaKm(
                        latUser, lngUser,
                        f.getLatitude(), f.getLongitude()
                );

                if (distancia <= raioKm) {
                    f.setDistanciaKm(distancia);
                    proximas.add(f);
                }
            }
        }

        // Ordena da mais perto pra mais longe
        proximas.sort(Comparator.comparing(Farmacia::getDistanciaKm));

        return proximas;
    }

    // Fórmula de Haversine
    private double calcularDistanciaKm(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371; // Raio da Terra em km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }

    // 🔹 CRIAR FARMÁCIA (GERA HASH DA SENHA E LAT/LONG)
    public Farmacia criar(Farmacia farmacia) {

        if (farmaciaRepository.existsByEmail(farmacia.getEmail())) {
            throw new RuntimeException("Já existe farmácia cadastrada com esse email.");
        }

        if (farmaciaRepository.existsByCnpj(farmacia.getCnpj())) {
            throw new RuntimeException("Já existe farmácia cadastrada com esse CNPJ.");
        }

        // senha enviada em senhaHash é a senha pura
        String senhaPura = farmacia.getSenhaHash();
        if (senhaPura == null || senhaPura.isBlank()) {
            throw new RuntimeException("Senha é obrigatória.");
        }
        String senhaHash = HashUtil.sha256(senhaPura);
        farmacia.setSenhaHash(senhaHash);

        if (farmacia.getDataCadastro() == null) {
            farmacia.setDataCadastro(LocalDateTime.now());
        }

        // 👉 Pega o EnderecoFarmacia associado
        EnderecoFarmacia end = farmacia.getEnderecoFarmacia();

        if (end != null) {
            // garante o lado dono do relacionamento
            end.setFarmacia(farmacia);

            // monta um endereço completo para enviar pro Google
            String enderecoCompleto =
                    end.getLogradouro() + ", " + end.getNumero() + " - " +
                            end.getBairro() + ", " + end.getCidade() + " - " +
                            end.getEstado() + ", " + end.getCep();

            // consulta no Google e preenche latitude/longitude
            double[] latlng = googleMapsService.obterLatLong(enderecoCompleto);
            farmacia.setLatitude(latlng[0]);
            farmacia.setLongitude(latlng[1]);
        }

        return farmaciaRepository.save(farmacia);
    }

    // 🔹 LOGIN DA FARMÁCIA (aceita senha antiga em texto puro e nova com hash)
    public Farmacia login(String email, String senhaPura) {

        Optional<Farmacia> opt = farmaciaRepository.findByEmail(email);
        if (opt.isEmpty()) {
            throw new RuntimeException("Farmácia não encontrada.");
        }

        Farmacia farmacia = opt.get();

        String senhaBanco = farmacia.getSenhaHash();
        String senhaHashCalculada = HashUtil.sha256(senhaPura);

        boolean senhaConfere = false;

        // 1) Caso novo: senha já está em hash no banco
        if (senhaBanco != null && senhaBanco.equals(senhaHashCalculada)) {
            senhaConfere = true;
        }

        // 2) Caso antigo: senha no banco ainda é texto puro (ex.: "123456")
        else if (senhaBanco != null && senhaBanco.equals(senhaPura)) {
            senhaConfere = true;

            // Atualiza para hash no banco para não ficar senha pura
            farmacia.setSenhaHash(senhaHashCalculada);
            farmaciaRepository.save(farmacia);
        }

        if (!senhaConfere) {
            throw new RuntimeException("Senha incorreta.");
        }

        // monta um objeto de resposta SEM a senha
        Farmacia resposta = new Farmacia();
        resposta.setId(farmacia.getId());
        resposta.setNomeFantasia(farmacia.getNomeFantasia());
        resposta.setRazaoSocial(farmacia.getRazaoSocial());
        resposta.setCnpj(farmacia.getCnpj());
        resposta.setEmail(farmacia.getEmail());
        resposta.setTelefone(farmacia.getTelefone());
        resposta.setLatitude(farmacia.getLatitude());
        resposta.setLongitude(farmacia.getLongitude());
        resposta.setDataCadastro(farmacia.getDataCadastro());
        resposta.setEnderecoFarmacia(farmacia.getEnderecoFarmacia());
        resposta.setDistanciaKm(farmacia.getDistanciaKm());

        return resposta;
    }

    // BUSCAR / DELETAR
    public Farmacia buscar(Long id) {
        return farmaciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Farmácia não encontrada"));
    }

    public void deletar(Long id) {
        farmaciaRepository.deleteById(id);
    }
}
