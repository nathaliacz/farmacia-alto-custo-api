package com.farmacia.altocusto.farmacia_api.service.externos;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;
import org.json.JSONArray;

@Service
public class GoogleMapsService {

    @Value("${google.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    // Obter latitude e longitude a partir de um endereço
    public double[] obterLatLong(String endereco) {

        try {
            String url =
                    "https://maps.googleapis.com/maps/api/geocode/json?address=" +
                            endereco.replace(" ", "+") +
                            "&key=" + apiKey;

            String resposta = restTemplate.getForObject(url, String.class);

            JSONObject json = new JSONObject(resposta);

            JSONArray results = json.getJSONArray("results");

            if (results.isEmpty()) {
                throw new IllegalArgumentException("Endereço ou CEP não encontrado no Google Maps.");
            }

            JSONObject location = results
                    .getJSONObject(0)
                    .getJSONObject("geometry")
                    .getJSONObject("location");

            double lat = location.getDouble("lat");
            double lng = location.getDouble("lng");

            return new double[]{lat, lng};

        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar Google Maps: " + e.getMessage());
        }
    }
}
