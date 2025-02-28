package com.ecuador.ecuadorCuentaMov.utils;

import com.ecuador.ecuadorCuentaMov.domains.dtos.*;
import com.ecuador.ecuadorCuentaMov.domains.entities.Cuenta;
import com.ecuador.ecuadorCuentaMov.domains.entities.Movimiento;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

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
    String movimientoDetalle = movimientoDTO.getTipoMovimiento() +" de "+ movimientoDTO.getValor();
    return Movimiento.builder()
            .fecha(LocalDateTime.now())
            .tipoCuenta(TipoCuenta.valueOf(cuenta.getTipoCuenta()))
            .tipoMovimiento(movimientoDTO.getTipoMovimiento())
            .valor(Math.abs(movimientoDTO.getValor()))
            .saldo(movimientoDTO.getSaldo())
            .estado(true)
            .cuenta(cuenta)
            .numeroCuenta(movimientoDTO.getNumeroCuenta())
            .movimientoDetalle(movimientoDetalle)
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

    public static CuentaDTO convertirCuentaADto(Cuenta entidad) {
        return CuentaDTO.builder()
                .id(entidad.getId())
                .numeroCuenta(entidad.getNumeroCuenta())
                .tipoCuenta(entidad.getTipoCuenta())
                .saldoInicial(entidad.getSaldoInicial())
                .estado(entidad.getEstado())
                //.clienteId(entidad.getCliente().getId())
                .clienteNombre(entidad.getClienteNombre())
                .build();
    }

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
                .tipoCuenta(movimiento.getTipoCuenta())
                .valor(movimiento.getValor())
                .saldo(movimiento.getSaldo())
                .cuentaId(movimiento.getCuenta().getId())
                .numeroCuenta(movimiento.getCuenta().getNumeroCuenta())
                .build();
    }

    public static ReporteMovimientoDTO convertirMovimientoADtoReporte(Movimiento movimiento, Double saldoInicial) {
        return ReporteMovimientoDTO.builder()
                .numeroCuenta(movimiento.getNumeroCuenta())
                .tipo(String.valueOf(movimiento.getTipoCuenta()))
                .saldoInicial(saldoInicial) // Se usa el saldo acumulado antes de este movimiento
                .saldoDisponible(saldoInicial + movimiento.getValor()) // Se actualiza el saldo
                .estado(movimiento.getEstado())
                .movimiento(movimiento.getMovimientoDetalle())
                .fecha(movimiento.getFecha())
                .build();
    }
}