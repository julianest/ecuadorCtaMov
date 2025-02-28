package com.ecuador.ecuadorCuentaMov.domains.services.Impl;

import com.ecuador.ecuadorCuentaMov.domains.dtos.ClienteDTO;
import com.ecuador.ecuadorCuentaMov.domains.dtos.CuentaDTO;
import com.ecuador.ecuadorCuentaMov.domains.dtos.EstadoCuentaDTO;
import com.ecuador.ecuadorCuentaMov.domains.dtos.MovimientoDTO;
//import com.ecuador.ecuadorCuentaMov.domains.entities.Cliente;
import com.ecuador.ecuadorCuentaMov.domains.entities.Cuenta;
import com.ecuador.ecuadorCuentaMov.domains.entities.Movimiento;
//import com.ecuador.ecuadorCuentaMov.domains.repositories.ClienteRepository;
import com.ecuador.ecuadorCuentaMov.domains.repositories.CuentaRepository;
import com.ecuador.ecuadorCuentaMov.domains.repositories.MovimientoRepository;
import com.ecuador.ecuadorCuentaMov.domains.services.ClienteService;
import com.ecuador.ecuadorCuentaMov.domains.services.ReporteService;
import com.ecuador.ecuadorCuentaMov.utils.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReporteServiceImpl implements ReporteService {

//    private final ClienteRepository clienteRepository;
    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;
    private final ClienteService clienteService;

    @Autowired
    public ReporteServiceImpl(
//            ClienteRepository clienteRepository,
            ClienteService clienteService,
            CuentaRepository cuentaRepository,
            MovimientoRepository movimientoRepository) {
//        this.clienteRepository = clienteRepository;
        this.clienteService = clienteService;
        this.cuentaRepository = cuentaRepository;
        this.movimientoRepository = movimientoRepository;
    }

}