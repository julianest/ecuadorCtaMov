package com.ecuador.ecuadorCuentaMov.controllers;

import com.ecuador.ecuadorCuentaMov.domains.dtos.MovimientoDTO;
import com.ecuador.ecuadorCuentaMov.domains.dtos.ReporteMovimientoDTO;
import com.ecuador.ecuadorCuentaMov.domains.services.MovimientoService;
import com.ecuador.ecuadorCuentaMov.exceptions.SaldoNoDisponibleException;
import com.ecuador.ecuadorCuentaMov.utils.MapperUtils;
import com.ecuador.ecuadorCuentaMov.utils.TipoMov;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/movimientos")
@AllArgsConstructor
public class MovimientoController {

    private final MovimientoService movimientoService;

    @Autowired
    private MapperUtils mapperUtils;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarMovimiento(@RequestBody List<Map<String, Object>> requestList) {
        try {
            List<MovimientoDTO> movimientosProcesados = requestList.stream()
                    .map(request -> {
                        String numeroCuenta = (String) request.get("numeroCuenta");
                        Double valor = Double.valueOf(request.get("valor").toString());
                        TipoMov tipoMovimiento = valor < 0 ? TipoMov.RETIRO : TipoMov.DEPOSITO;
                        MovimientoDTO movimientoDTO = mapperUtils.buildMovimientoDTO(numeroCuenta, valor, tipoMovimiento);
                        return movimientoService.registrarMovimiento(movimientoDTO);
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(movimientosProcesados);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error en los datos enviados: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al registrar movimientos.");
        }
    }

}
