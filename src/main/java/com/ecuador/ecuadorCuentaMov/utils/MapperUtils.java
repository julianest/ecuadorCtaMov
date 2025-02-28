package com.ecuador.ecuadorCuentaMov.utils;

import com.ecuador.ecuadorCuentaMov.domains.dtos.ClienteDTO;
import com.ecuador.ecuadorCuentaMov.domains.dtos.CuentaDTO;
import com.ecuador.ecuadorCuentaMov.domains.dtos.MovimientoDTO;
import com.ecuador.ecuadorCuentaMov.domains.dtos.ReporteCuentaDTO;
import com.ecuador.ecuadorCuentaMov.domains.entities.Cuenta;
import com.ecuador.ecuadorCuentaMov.domains.entities.Movimiento;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MapperUtils {

public Cuenta builderCuenta(String numeroCuenta,String tipoCuenta, Double saldoInicial, ClienteDTO clienteDTO){
    return Cuenta.builder()
            .numeroCuenta(numeroCuenta)
            .tipoCuenta(tipoCuenta)
            .saldoInicial(saldoInicial)
            .estado(true)
            .clienteId(clienteDTO.getId())
            .clienteNombre(clienteDTO.getNombre())
            .build();
}

public ReporteCuentaDTO builderReporteCuentaDTO(Cuenta cuentaGuardada){
    return ReporteCuentaDTO.builder()
            .numeroCuenta(cuentaGuardada.getNumeroCuenta())
            .tipo(cuentaGuardada.getTipoCuenta())
            .saldoInicial(cuentaGuardada.getSaldoInicial())
            .estado(cuentaGuardada.getEstado())
            .cliente(cuentaGuardada.getClienteNombre())
            .build();
}

public Movimiento buildMovimiento(MovimientoDTO movimientoDTO, Cuenta cuenta){
    return Movimiento.builder()
            .fecha(LocalDateTime.now())
            .tipoMovimiento(movimientoDTO.getTipoMovimiento())
            .valor(movimientoDTO.getValor())
            .saldo(movimientoDTO.getSaldo())
            .cuenta(cuenta)
            .build();
}

public MovimientoDTO buildMovimientoDTO(String numeroCuenta,Double valor,TipoMov tipoMovimiento){
    return MovimientoDTO.builder()
            .numeroCuenta(numeroCuenta)
            .fecha(LocalDateTime.now())
            .tipoMovimiento(tipoMovimiento)
            .valor(valor)
            .build();
}

//    public static Cliente convertirDtoACliente(ClienteDTO dto) {
//        return Cliente.builder()
//                .clienteId(dto.getId())
//                .nombre(dto.getNombre())
//                .genero(dto.getGenero())
//                .edad(dto.getEdad())
//                .identificacion(dto.getIdentificacion())
//                .direccion(dto.getDireccion())
//                .telefono(dto.getTelefono())
//                .clienteId(dto.getClienteId())
//                .contraseña(dto.getContraseña())
//                .estado(dto.getEstado() != null ? dto.getEstado() : true)
//                .build();
//    }

//    public static ClienteDTO convertirClienteADto(Cliente entidad) {
//        return ClienteDTO.builder()
//                .id(entidad.getId())
//                .nombre(entidad.getNombre())
//                .genero(entidad.getGenero())
//                .edad(entidad.getEdad())
//                .identificacion(entidad.getIdentificacion())
//                .direccion(entidad.getDireccion())
//                .telefono(entidad.getTelefono())
//                .clienteId(entidad.getClienteId())
//                .contraseña(entidad.getContraseña())
//                .estado(entidad.getEstado())
//                .build();
//    }

//    public static void actualizarClienteDesdeDTO(Cliente cliente, ClienteDTO dto) {
//        if (dto.getNombre() != null) {
//            cliente.setNombre(dto.getNombre());
//        }
//        if (dto.getGenero() != null) {
//            cliente.setGenero(dto.getGenero());
//        }
//        if (dto.getEdad() > 0) {
//            cliente.setEdad(dto.getEdad());
//        }
//        if (dto.getIdentificacion() != null) {
//            cliente.setIdentificacion(dto.getIdentificacion());
//        }
//        if (dto.getDireccion() != null) {
//            cliente.setDireccion(dto.getDireccion());
//        }
//        if (dto.getTelefono() != null) {
//            cliente.setTelefono(dto.getTelefono());
//        }
//        if (dto.getClienteId() != null) {
//            cliente.setClienteId(dto.getClienteId());
//        }
//        if (dto.getContraseña() != null) {
//            cliente.setContraseña(dto.getContraseña());
//        }
//        if (dto.getEstado() != null) {
//            cliente.setEstado(dto.getEstado());
//        }
//    }

    // Métodos para Cuenta
//    public static Cuenta convertirDtoACuenta(CuentaDTO dto, Cuenta cuenta) {
//        return Cuenta.builder()
//                .id(dto.getId())
//                .numeroCuenta(dto.getNumeroCuenta())
//                .tipoCuenta(dto.getTipoCuenta())
//                .saldoInicial(dto.getSaldoInicial())
//                .estado(dto.getEstado() != null ? dto.getEstado() : true)
//                .clienteId((cliente)
//                .build();
//    }

//    public static CuentaDTO convertirCuentaADto(Cuenta entidad) {
//        return CuentaDTO.builder()
//                .id(entidad.getId())
//                .numeroCuenta(entidad.getNumeroCuenta())
//                .tipoCuenta(entidad.getTipoCuenta())
//                .saldoInicial(entidad.getSaldoInicial())
//                .estado(entidad.getEstado())
//                .clienteId(entidad.getCliente().getId())
//                .clienteNombre(entidad.getCliente().getNombre())
//                .build();
//    }

//    public static void actualizarCuentaDesdeDTO(Cuenta cuenta, CuentaDTO dto, Cliente cliente) {
//        if (dto.getNumeroCuenta() != null) {
//            cuenta.setNumeroCuenta(dto.getNumeroCuenta());
//        }
//        if (dto.getTipoCuenta() != null) {
//            cuenta.setTipoCuenta(dto.getTipoCuenta());
//        }
//        if (dto.getSaldoInicial() != null) {
//            cuenta.setSaldoInicial(dto.getSaldoInicial());
//        }
//        if (dto.getEstado() != null) {
//            cuenta.setEstado(dto.getEstado());
//        }
//        if (cliente != null) {
//            cuenta.setCliente(cliente);
//        }
//    }

    // Métodos para Movimiento
    public static MovimientoDTO convertirMovimientoADto(Movimiento movimiento) {
        return MovimientoDTO.builder()
                .id(movimiento.getId())
                .fecha(movimiento.getFecha())
                .tipoMovimiento(movimiento.getTipoMovimiento())
                .valor(movimiento.getValor())
                .saldo(movimiento.getSaldo())
                //.cuentaId(movimiento.getCuenta().getId())
                .numeroCuenta(movimiento.getCuenta().getNumeroCuenta())
                .build();
    }
}