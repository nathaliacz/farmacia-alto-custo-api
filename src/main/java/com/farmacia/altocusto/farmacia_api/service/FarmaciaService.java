package com.farmacia.altocusto.farmacia_api.service;

import com.farmacia.altocusto.farmacia_api.model.Farmacia;
import com.farmacia.altocusto.farmacia_api.repository.FarmaciaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import com.farmacia.altocusto.farmacia_api.service.externos.GoogleMapsService;

@Service
public class FarmaciaService {

    private final FarmaciaRepository farmaciaRepository;

    @Autowired
    private GoogleMapsService googleMapsService;

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
                    f.setDistanciaKm(distancia); // aqui guarda a distância
                    proximas.add(f);
                }
            }
        }

        // 4. Ordena da mais perto pra mais longe
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

    public FarmaciaService(FarmaciaRepository farmaciaRepository) {
        this.farmaciaRepository = farmaciaRepository;
    }

    public Farmacia criar(Farmacia farmacia) {

        if (farmaciaRepository.existsByEmail(farmacia.getEmail())) {
            throw new RuntimeException("Já existe farmácia cadastrada com esse email.");
        }

        if (farmaciaRepository.existsByCnpj(farmacia.getCnpj())) {
            throw new RuntimeException("Já existe farmácia cadastrada com esse CNPJ.");
        }

        return farmaciaRepository.save(farmacia);
    }

    public Farmacia buscar(Long id) {
        return farmaciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Farmácia não encontrada"));
    }

    public void deletar(Long id) {
        farmaciaRepository.deleteById(id);
    }

}

