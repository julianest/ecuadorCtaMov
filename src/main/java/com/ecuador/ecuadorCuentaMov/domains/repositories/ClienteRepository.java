package com.ecuador.ecuadorCuentaMov.domains.repositories;

import com.ecuador.ecuadorCuentaMov.domains.entities.Cliente;

public interface ClienteRepository {
    Cliente findByNombre(String nombre);
    Cliente findByI(Long nombre);
}
