package com.ecuador.ecuadorCuentaMov.utils;

import org.springframework.stereotype.Component;

@Component
public class Utils {

    // Método auxiliar para generar números de cuenta
    public String generarNumeroCuenta() {
        long timestamp = System.currentTimeMillis();
        return String.valueOf(100000 + (timestamp % 900000));
    }
}
