/**
 * 
 */
package com.example.parking.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.parking.model.entity.Estancia;
import com.example.parking.model.entity.Residente;
import com.example.parking.model.entity.Vehiculo;
import com.example.parking.model.enums.TipoVehiculo;
import com.example.parking.repository.EstanciaRepository;
import com.example.parking.repository.NoResidenteRepository;
import com.example.parking.repository.OficialRepository;
import com.example.parking.repository.ResidenteRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 */
@Service
@Slf4j
public class EstanciaServiceImpl implements EstanciaService {
	
	private OficialServiceImpl oficialService;
    private ResidenteServiceImpl residenteService;
    private NoResidenteServiceImpl noResidenteService;

    private ResidenteRepository residenteRepository;
    private OficialRepository oficialRepository;
    private NoResidenteRepository noResidenteRepository;
    private EstanciaRepository estanciaRepository;

	@Override
	public void registrarEntrada(String placa) {
		VehiculoService servicio = seleccionarServicio(placa);
        servicio.registrarEntrada(placa);
	}

	@Override
	public void registrarSalida(String placa) {
		VehiculoService servicio = seleccionarServicio(placa);
        servicio.registrarSalida(placa);
	}

	@Override
	public void darDeAltaVehiculo(String placa, String tipo) {
		TipoVehiculo tipoVehiculo;
        try {
            tipoVehiculo = TipoVehiculo.valueOf(tipo.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Tipo de vehículo no válido: " + tipo);
        }

        switch (tipoVehiculo) {
            case OFICIAL -> oficialService.registrarAlta(placa);
            case RESIDENTE -> residenteService.registrarAlta(placa);
            case NO_RESIDENTE -> noResidenteService.registrarAlta(placa);
            default -> throw new RuntimeException("Tipo de vehículo no soportado");
        }
	}

	@Override
	@Transactional
	public void reiniciarMes() {
		log.info("Reiniciando datos para nuevo mes...");
		
		// 1. Resetear tiempo acumulado de residentes
	    List<Residente> residentes = residenteRepository.findAll();
	    for (Residente r : residentes) {
	        r.setTiempoEstacionado(0);
	    }
	    residenteRepository.saveAll(residentes);
	    log.info("Tiempo acumulado de residentes reseteado.");

	    // 2. Cerrar estancias abiertas de oficiales
	    List<Estancia> estanciasAbiertas = estanciaRepository.findByHoraSalidaIsNull();
	    for (Estancia e : estanciasAbiertas) {
	        if (e.getVehiculo() instanceof Vehiculo) {
	            e.setHoraSalida(LocalDateTime.now());
	        }
	    }
	    estanciaRepository.saveAll(estanciasAbiertas);
	    log.info("Estancias abiertas de oficiales cerradas.");

	    log.info("Reinicio de mes completado correctamente.");
		
	}
	
	private VehiculoService seleccionarServicio(String placa) {
        if (oficialRepository.existsById(placa)) return oficialService;
        if (residenteRepository.existsById(placa)) return residenteService;
        if (noResidenteRepository.existsById(placa)) return noResidenteService;
        throw new RuntimeException("Vehículo no registrado con placa: " + placa);
    }

}
