package com.ecuador.ecuadorCuentaMov.domains.repositories;


import com.ecuador.ecuadorCuentaMov.domains.entities.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    
    List<Movimiento> findByCuentaId(Long cuentaId);
    
    List<Movimiento> findByCuentaIdAndFechaBetweenOrderByFechaDesc(
            Long cuentaId, LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    List<Movimiento> findByCuentaClienteIdAndFechaBetweenOrderByFechaDesc(
            Long clienteId, LocalDateTime fechaInicio, LocalDateTime fechaFin);

    List<Movimiento> findByCuentaIdOrderByFechaDesc(Long cuentaId);
}