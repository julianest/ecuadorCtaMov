package com.ecuador.ecuadorCuentaMov.utils;

import com.ecuador.ecuadorCuentaMov.domains.dtos.ClienteDTO;
import com.ecuador.ecuadorCuentaMov.domains.dtos.CuentaDTO;
import com.ecuador.ecuadorCuentaMov.domains.dtos.MovimientoDTO;
import com.ecuador.ecuadorCuentaMov.domains.entities.Cliente;
import com.ecuador.ecuadorCuentaMov.domains.entities.Cuenta;
import com.ecuador.ecuadorCuentaMov.domains.entities.Movimiento;

public class MapperUtils {

    // Métodos para Cliente (los originales)
    public static Cliente convertirDtoACliente(ClienteDTO dto) {
        return Cliente.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .genero(dto.getGenero())
                .edad(dto.getEdad())
                .identificacion(dto.getIdentificacion())
                .direccion(dto.getDireccion())
                .telefono(dto.getTelefono())
                .clienteId(dto.getClienteId())
                .contraseña(dto.getContraseña())
                .estado(dto.getEstado() != null ? dto.getEstado() : true)
                .build();
    }

    public static ClienteDTO convertirClienteADto(Cliente entidad) {
        return ClienteDTO.builder()
                .id(entidad.getId())
                .nombre(entidad.getNombre())
                .genero(entidad.getGenero())
                .edad(entidad.getEdad())
                .identificacion(entidad.getIdentificacion())
                .direccion(entidad.getDireccion())
                .telefono(entidad.getTelefono())
                .clienteId(entidad.getClienteId())
                .contraseña(entidad.getContraseña())
                .estado(entidad.getEstado())
                .build();
    }

    public static void actualizarClienteDesdeDTO(Cliente cliente, ClienteDTO dto) {
        if (dto.getNombre() != null) {
            cliente.setNombre(dto.getNombre());
        }
        if (dto.getGenero() != null) {
            cliente.setGenero(dto.getGenero());
        }
        if (dto.getEdad() > 0) {
            cliente.setEdad(dto.getEdad());
        }
        if (dto.getIdentificacion() != null) {
            cliente.setIdentificacion(dto.getIdentificacion());
        }
        if (dto.getDireccion() != null) {
            cliente.setDireccion(dto.getDireccion());
        }
        if (dto.getTelefono() != null) {
            cliente.setTelefono(dto.getTelefono());
        }
        if (dto.getClienteId() != null) {
            cliente.setClienteId(dto.getClienteId());
        }
        if (dto.getContraseña() != null) {
            cliente.setContraseña(dto.getContraseña());
        }
        if (dto.getEstado() != null) {
            cliente.setEstado(dto.getEstado());
        }
    }

    // Métodos para Cuenta
    public static Cuenta convertirDtoACuenta(CuentaDTO dto, Cliente cliente) {
        return Cuenta.builder()
                .id(dto.getId())
                .numeroCuenta(dto.getNumeroCuenta())
                .tipoCuenta(dto.getTipoCuenta())
                .saldoInicial(dto.getSaldoInicial())
                .estado(dto.getEstado() != null ? dto.getEstado() : true)
                .cliente(cliente)
                .build();
    }

    public static CuentaDTO convertirCuentaADto(Cuenta entidad) {
        return CuentaDTO.builder()
                .id(entidad.getId())
                .numeroCuenta(entidad.getNumeroCuenta())
                .tipoCuenta(entidad.getTipoCuenta())
                .saldoInicial(entidad.getSaldoInicial())
                .estado(entidad.getEstado())
                .clienteId(entidad.getCliente().getId())
                .clienteNombre(entidad.getCliente().getNombre())
                .build();
    }

    public static void actualizarCuentaDesdeDTO(Cuenta cuenta, CuentaDTO dto, Cliente cliente) {
        if (dto.getNumeroCuenta() != null) {
            cuenta.setNumeroCuenta(dto.getNumeroCuenta());
        }
        if (dto.getTipoCuenta() != null) {
            cuenta.setTipoCuenta(dto.getTipoCuenta());
        }
        if (dto.getSaldoInicial() != null) {
            cuenta.setSaldoInicial(dto.getSaldoInicial());
        }
        if (dto.getEstado() != null) {
            cuenta.setEstado(dto.getEstado());
        }
        if (cliente != null) {
            cuenta.setCliente(cliente);
        }
    }

    // Métodos para Movimiento
    public static MovimientoDTO convertirMovimientoADto(Movimiento entidad) {
        return MovimientoDTO.builder()
                .id(entidad.getId())
                .fecha(entidad.getFecha())
                .tipoMovimiento(entidad.getTipoMovimiento())
                .valor(entidad.getValor())
                .saldo(entidad.getSaldo())
                .cuentaId(entidad.getCuenta().getId())
                .numeroCuenta(entidad.getCuenta().getNumeroCuenta())
                .build();
    }
}