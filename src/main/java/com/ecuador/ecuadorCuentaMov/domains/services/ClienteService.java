package com.ecuador.ecuadorCuentaMov.domains.services;

import com.ecuador.ecuadorCuentaMov.domains.dtos.ClienteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ClienteService {
    
    private final RestTemplate restTemplate;
    private final String clienteServiceUrl = "http://localhost:8080/api/clientes";
    
    @Autowired
    public ClienteService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    public ClienteDTO buscarClientePorNombre(String nombre) {
        String url = clienteServiceUrl + "/nombre/" + nombre;
        try {
            return restTemplate.getForObject(url, ClienteDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener cliente: " + nombre +" "+ e.getMessage());
        }
    }

    public ClienteDTO buscarClientePorIdentificacion(String identificacion) {
        String url = clienteServiceUrl + "/identificacion/" + identificacion;
        try {
            return restTemplate.getForObject(url, ClienteDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener cliente: " + identificacion +" "+ e.getMessage());
        }
    }
    
    public ClienteDTO buscarClientePorId(Long id) {
        String url = clienteServiceUrl + "/id/" + id;
        try {
            return restTemplate.getForObject(url, ClienteDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener cliente: " + e.getMessage());
        }
    }
}