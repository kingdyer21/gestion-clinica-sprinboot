package com.gestionturnos.turnos.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@Service
public class ApiConsumerService {

    private final RestTemplate restTemplate = new RestTemplate();

    // Método para buscar enfermedades por término
    public String buscarEnfermedades(String termino) {
        String url = "https://clinicaltables.nlm.nih.gov/api/disease_names/v3/search?terms=" + termino;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }
}
