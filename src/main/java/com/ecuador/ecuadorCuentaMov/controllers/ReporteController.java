package com.ecuador.ecuadorCuentaMov.controllers;

import com.ecuador.ecuadorCuentaMov.domains.dtos.ReporteCuentaDTO;
import com.ecuador.ecuadorCuentaMov.domains.dtos.ReporteMovimientoDTO;
import com.ecuador.ecuadorCuentaMov.domains.services.CuentaService;
import com.ecuador.ecuadorCuentaMov.domains.services.MovimientoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reportes")
@AllArgsConstructor
public class ReporteController {

    private final CuentaService cuentaService;
    private final MovimientoService movimientoService;

    @GetMapping("/cuentas")
    public ResponseEntity<?> reporteCuentas() {
        List<ReporteCuentaDTO> cuentas = cuentaService.generarReporteCuentas();
        if (cuentas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reporte de cuentas no encontrado.");
        }
        return ResponseEntity.ok(cuentas);
    }

    @GetMapping("/movimientos/{numeroCuenta}")
    public ResponseEntity<?> reporteMovimientos(@PathVariable String numeroCuenta) {
        List<ReporteMovimientoDTO> movimientos = movimientoService.listarMovimientosPorNumeroCuenta(numeroCuenta);
        if (movimientos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reporte de movimientos no encontrado para la cuenta: " + numeroCuenta);
        }
        return ResponseEntity.ok(movimientos);
    }
}