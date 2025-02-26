package com.ecuador.ecuadorCuentaMov.domains.repositories;


import com.ecuador.ecuadorCuentaMov.domains.entities.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    
    List<Cuenta> findByClienteId(Long clienteId);
    
    Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);
    
    boolean existsByNumeroCuenta(String numeroCuenta);

}