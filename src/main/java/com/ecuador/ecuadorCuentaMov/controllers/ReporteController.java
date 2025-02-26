package com.ecuador.ecuadorCuentaMov.controllers;

import com.ecuador.ecuadorCuentaMov.domains.dtos.ReporteCuentaDTO;
import com.ecuador.ecuadorCuentaMov.domains.dtos.ReporteMovimientoDTO;
import com.ecuador.ecuadorCuentaMov.domains.services.CuentaService;
import com.ecuador.ecuadorCuentaMov.domains.services.MovimientoService;
import lombok.AllArgsConstructor;
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
    public ResponseEntity<List<ReporteCuentaDTO>> reporteCuentas() {
        return ResponseEntity.ok(cuentaService.generarReporteCuentas());
    }
    
    @PostMapping("/cuentas")
    public ResponseEntity<ReporteCuentaDTO> crearCuenta(@RequestBody Map<String, Object> request) {
        String nombreCliente = (String) request.get("nombreCliente");
        String tipoCuenta = (String) request.get("tipoCuenta");
        Double saldoInicial = Double.valueOf(request.get("saldoInicial").toString());
        
        ReporteCuentaDTO cuenta = cuentaService.crearCuentaParaCliente(nombreCliente, tipoCuenta, saldoInicial);
        return ResponseEntity.ok(cuenta);
    }
    
    @PostMapping("/movimientos")
    public ResponseEntity<ReporteMovimientoDTO> realizarMovimiento(@RequestBody Map<String, Object> request) {
        String numeroCuenta = (String) request.get("numeroCuenta");
        Double valor = Double.valueOf(request.get("valor").toString());
        
        ReporteMovimientoDTO movimiento = movimientoService.registrarMovimientoReporte(numeroCuenta, valor);
        return ResponseEntity.ok(movimiento);
    }
    
    @GetMapping("/movimientos/{numeroCuenta}")
    public ResponseEntity<List<ReporteMovimientoDTO>> reporteMovimientos(@PathVariable String numeroCuenta) {
        return ResponseEntity.ok(movimientoService.listarMovimientosPorNumeroCuenta(numeroCuenta));
    }
}