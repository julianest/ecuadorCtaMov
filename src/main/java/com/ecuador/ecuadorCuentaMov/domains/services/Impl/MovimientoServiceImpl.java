package com.ecuador.ecuadorCuentaMov.domains.services.Impl;

import com.ecuador.ecuadorCuentaMov.domains.dtos.ClienteDTO;
import com.ecuador.ecuadorCuentaMov.domains.dtos.CuentaDTO;
import com.ecuador.ecuadorCuentaMov.domains.dtos.EstadoCuentaDTO;
import com.ecuador.ecuadorCuentaMov.domains.dtos.MovimientoDTO;
import com.ecuador.ecuadorCuentaMov.domains.entities.Cliente;
import com.ecuador.ecuadorCuentaMov.domains.entities.Cuenta;
import com.ecuador.ecuadorCuentaMov.domains.entities.Movimiento;
import com.ecuador.ecuadorCuentaMov.domains.repositories.ClienteRepository;
import com.ecuador.ecuadorCuentaMov.domains.repositories.CuentaRepository;
import com.ecuador.ecuadorCuentaMov.domains.repositories.MovimientoRepository;
import com.ecuador.ecuadorCuentaMov.domains.services.MovimientoService;
import com.ecuador.ecuadorCuentaMov.exceptions.SaldoNoDisponibleException;
import com.ecuador.ecuadorCuentaMov.utils.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovimientoServiceImpl implements MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final CuentaRepository cuentaRepository;
    private final ClienteRepository clienteRepository;

    @Autowired
    public MovimientoServiceImpl(
            MovimientoRepository movimientoRepository,
            CuentaRepository cuentaRepository,
            ClienteRepository clienteRepository) {
        this.movimientoRepository = movimientoRepository;
        this.cuentaRepository = cuentaRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    @Transactional
    public MovimientoDTO registrarMovimiento(MovimientoDTO movimientoDTO) {
        // Obtener la cuenta
        Cuenta cuenta = cuentaRepository.findById(movimientoDTO.getCuentaId())
                .orElseThrow(() -> new IllegalArgumentException("La cuenta con ID " + 
                        movimientoDTO.getCuentaId() + " no existe"));
        
        // Verificar si es un movimiento de retiro y si hay saldo suficiente
        double saldoActual = cuenta.getSaldoInicial();
        
        // Obtener el último movimiento para tener el saldo más actualizado
        List<Movimiento> movimientosRecientes = movimientoRepository.findByCuentaId(cuenta.getId());
        if (!movimientosRecientes.isEmpty()) {
            // Ordenar por fecha descendente y tomar el más reciente
            saldoActual = movimientosRecientes.stream()
                    .sorted((m1, m2) -> m2.getFecha().compareTo(m1.getFecha()))
                    .findFirst()
                    .get()
                    .getSaldo();
        }
        
        // Calcular nuevo saldo
        double valorMovimiento = movimientoDTO.getValor();
        double nuevoSaldo = saldoActual + valorMovimiento;
        
        // Verificar si hay saldo suficiente para retiros (valores negativos)
        if (valorMovimiento < 0 && nuevoSaldo < 0) {
            throw new SaldoNoDisponibleException("Saldo no disponible");
        }
        
        // Crear y guardar el movimiento
        Movimiento movimiento = Movimiento.builder()
                .fecha(LocalDateTime.now())
                .tipoMovimiento(valorMovimiento > 0 ? "DEPOSITO" : "RETIRO")
                .valor(valorMovimiento)
                .saldo(nuevoSaldo)
                .cuenta(cuenta)
                .build();
        
        Movimiento movimientoGuardado = movimientoRepository.save(movimiento);
        return MapperUtils.convertirMovimientoADto(movimientoGuardado);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MovimientoDTO> obtenerMovimientoPorId(Long id) {
        return movimientoRepository.findById(id)
                .map(MapperUtils::convertirMovimientoADto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoDTO> listarTodosLosMovimientos() {
        return movimientoRepository.findAll().stream()
                .map(MapperUtils::convertirMovimientoADto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoDTO> listarMovimientosPorCuentaId(Long cuentaId) {
        return movimientoRepository.findByCuentaId(cuentaId).stream()
                .map(MapperUtils::convertirMovimientoADto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoDTO> listarMovimientosPorFechas(Long cuentaId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return movimientoRepository.findByCuentaIdAndFechaBetweenOrderByFechaDesc(cuentaId, fechaInicio, fechaFin).stream()
                .map(MapperUtils::convertirMovimientoADto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Optional<MovimientoDTO> actualizarMovimiento(Long id, MovimientoDTO movimientoDTO) {
        // En este caso, por la naturaleza de los movimientos bancarios, 
        // solo permitimos actualizar ciertos campos no críticos
        return movimientoRepository.findById(id)
                .map(movimientoExistente -> {
                    // Solo actualizamos el tipo de movimiento si es necesario
                    if (movimientoDTO.getTipoMovimiento() != null) {
                        movimientoExistente.setTipoMovimiento(movimientoDTO.getTipoMovimiento());
                    }
                    
                    Movimiento movimientoActualizado = movimientoRepository.save(movimientoExistente);
                    return MapperUtils.convertirMovimientoADto(movimientoActualizado);
                });
    }

    @Override
    @Transactional
    public boolean eliminarMovimiento(Long id) {
        // En un sistema real, probablemente querríamos restricciones 
        // adicionales antes de permitir eliminar un movimiento
        if (movimientoRepository.existsById(id)) {
            movimientoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public EstadoCuentaDTO generarEstadoCuenta(Long clienteId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        // Obtener cliente
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("El cliente con ID " + 
                        clienteId + " no existe"));
                
        // Obtener cuentas del cliente
        List<CuentaDTO> cuentas = cuentaRepository.findByClienteId(clienteId).stream()
                .map(MapperUtils::convertirCuentaADto)
                .collect(Collectors.toList());
                
        // Obtener movimientos en el rango de fechas
        List<MovimientoDTO> movimientos = movimientoRepository
                .findByCuentaClienteIdAndFechaBetweenOrderByFechaDesc(clienteId, fechaInicio, fechaFin).stream()
                .map(MapperUtils::convertirMovimientoADto)
                .collect(Collectors.toList());
                
        // Calcular saldo total
        double saldoTotal = cuentas.stream()
                .mapToDouble(cuenta -> cuenta.getSaldoInicial())
                .sum();
                
        // Crear DTO de cliente
        ClienteDTO clienteDTO = MapperUtils.convertirClienteADto(cliente);
                
        return EstadoCuentaDTO.builder()
                .cliente(clienteDTO)
                .cuentas(cuentas)
                .movimientos(movimientos)
                .fechaInicio(fechaInicio)
                .fechaFin(fechaFin)
                .saldoTotal(saldoTotal)
                .build();
    }
}