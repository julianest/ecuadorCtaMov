package com.ecuador.ecuadorCuentaMov.exceptions;

public class SaldoNoDisponibleException extends RuntimeException {
    
    public SaldoNoDisponibleException(String message) {
        super(message);
    }
}