package com.ecuador.ecuadorCuentaMov.domains.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Cuenta")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_cuenta", nullable = false, unique = true, length = 20)
    private String numeroCuenta;

    @Column(name = "tipo_cuenta", nullable = false, length = 20)
    private String tipoCuenta;

    @Column(name = "saldo_inicial", nullable = false)
    private Double saldoInicial;

    @Column(nullable = false)
    private Boolean estado;

    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    @Column(name = "cliente_nombre", nullable = false)
    private String clienteNombre;

}
