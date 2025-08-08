/**
 * 
 */
package com.example.parking.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.parking.model.entity.Estancia;
import com.example.parking.model.entity.Oficial;
import com.example.parking.model.entity.Vehiculo;
import com.example.parking.repository.EstanciaRepository;
import com.example.parking.repository.OficialRepository;

import jakarta.transaction.Transactional;

/**
 * Service class where the business methods of parking operations are
 * implemented.
 *
 * @author Daniel Manzano Borja
 * @since 07-AGO-2025
 */
@Service
public class OficialServiceImpl implements VehiculoService {
	
	private OficialRepository oficialRepository;
    private EstanciaRepository estanciaRepository;
    
    /**
	 * Constructor that injects the oficialRepository and estanciaRepository interfaces.
	 * 
	 * @param oficialRepository | OficialRepository JPA-interface.
	 * @param estanciaRepository    | EstanciaRepository JPA-interface.
	 * @param iEmployeeMapper     | Activates the generation of a implementation of
	 *                            that type via MapStruct.
	 */
    public OficialServiceImpl(OficialRepository oficialRepository, EstanciaRepository estanciaRepository) {
    	this.oficialRepository = oficialRepository;
    	this.estanciaRepository = estanciaRepository;
    }

    @Override
    @Transactional
    public void registrarEntrada(String placa) {
        Oficial oficial = oficialRepository.findById(placa)
                .orElseThrow(() -> new RuntimeException("Vehículo oficial no encontrado"));
        Estancia estancia = new Estancia();
        estancia.setHoraEntrada(LocalDateTime.now());
        estancia.setVehiculo(oficial);
        estanciaRepository.save(estancia);
    }

    @Override
    @Transactional
    public void registrarSalida(String placa) {
        Oficial oficial = oficialRepository.findById(placa)
                .orElseThrow(() -> new RuntimeException("Vehículo oficial no encontrado"));
        Estancia ultima = obtenerUltimaEstanciaActiva(oficial);
        ultima.setHoraSalida(LocalDateTime.now());
        estanciaRepository.save(ultima);
    }
    
    private Estancia obtenerUltimaEstanciaActiva(Vehiculo vehiculo) {
        return estanciaRepository.findByVehiculo_NumeroPlaca(vehiculo.getNumeroPlaca()).stream()
                .filter(e -> e.getHoraSalida() == null)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No hay estancia activa"));
    }

	@Override
    public void registrarAlta(String placa) {
        if (!oficialRepository.existsById(placa)) {
            Oficial oficial = new Oficial();
            oficial.setNumeroPlaca(placa);
            oficialRepository.save(oficial);
        }
    }

	@Override
    public Vehiculo obtenerVehiculo(String placa) {
        return oficialRepository.findById(placa).orElse(null);
    }

}
