//package com.ecuador.ecuadorCuentaMov.domains.entities;
//
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.OneToMany;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class Cliente {
//    @Id
//    private Long clienteId;
//    private String contrasena;
//    private Boolean estado;
//
//    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
//    private List<Cuenta> cuentas = new ArrayList<>();
//
//}
