package com.ecuador.ecuadorCuentaMov.controllers;

import com.ecuador.ecuadorCuentaMov.domains.dtos.CuentaDTO;
import com.ecuador.ecuadorCuentaMov.domains.dtos.ReporteCuentaDTO;
import com.ecuador.ecuadorCuentaMov.domains.services.CuentaService;
import com.ecuador.ecuadorCuentaMov.utils.TipoCuenta;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cuentas")
@AllArgsConstructor
public class CuentaController {

    private final CuentaService cuentaService;

    @PostMapping("crear")
    public ResponseEntity<?> crearCuentas(@RequestBody List<Map<String, Object>> requestList) {
        try {
            List<ReporteCuentaDTO> cuentasCreadas = requestList.stream().map(request -> {
                String nombreCliente = (String) request.get("nombreCliente");
                String identificacion = (String) request.get("identificacion");
                Double saldoInicial = Double.valueOf(request.get("saldoInicial").toString());
                TipoCuenta tipoCuenta = TipoCuenta.valueOf(request.get("tipoCuenta").toString().toUpperCase());

                return cuentaService.crearCuentaParaCliente(identificacion, nombreCliente, tipoCuenta, saldoInicial);
            }).collect(Collectors.toList());

            return ResponseEntity.ok(cuentasCreadas);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Tipo de cuenta inválido. Valores permitidos: AHORRO, CORRIENTE.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al crear cuentas.");
        }
    }

    @GetMapping("/{numeroCuenta}")
    public ResponseEntity<?> obtenerCuentaPorNumero(@PathVariable String numeroCuenta) {
        try {
            ReporteCuentaDTO cuenta = cuentaService.obtenerCuentaPorNumero(numeroCuenta);
            return ResponseEntity.ok(cuenta);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{numeroCuenta}")
    public ResponseEntity<?> eliminarCuenta(@PathVariable String numeroCuenta) {
        try {
            cuentaService.eliminarCuentaPorNumero(numeroCuenta);
            return ResponseEntity.ok("Cuenta eliminada exitosamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("No se encontró la cuenta con número: " + numeroCuenta);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al eliminar la cuenta.");
        }
    }

    @PutMapping("/update/{numeroCuenta}")
    public ResponseEntity<?> actualizarCuenta(
            @PathVariable String numeroCuenta,
            @RequestBody Map<String, Object> request) {
        try {
            TipoCuenta tipoCuenta = TipoCuenta.valueOf(request.get("tipoCuenta").toString().toUpperCase());
            Double saldoInicial = Double.valueOf(request.get("saldoInicial").toString());
            Boolean estado = Boolean.valueOf(request.get("estado").toString());

            ReporteCuentaDTO cuentaActualizada = cuentaService.actualizarCuenta(numeroCuenta, tipoCuenta, saldoInicial, estado);
            return ResponseEntity.ok(cuentaActualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al actualizar la cuenta.");
        }
    }
}
