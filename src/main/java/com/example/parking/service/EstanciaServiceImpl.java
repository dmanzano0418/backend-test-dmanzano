/**
 * 
 */
package com.example.parking.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.parking.dto.EstanciaDTO;
import com.example.parking.dto.mapper.EstanciaMapper;
import com.example.parking.exception.EstanciaNoEncontradaException;
import com.example.parking.exception.VehiculoNoEncontradoException;
import com.example.parking.model.entity.Estancia;
import com.example.parking.model.entity.Vehiculo;
import com.example.parking.model.enums.TipoVehiculo;
import com.example.parking.repository.EstanciaRepository;
import com.example.parking.repository.VehiculoRepository;

import lombok.RequiredArgsConstructor;

/**
 * Implementación del servicio {@link EstanciaService} para la gestión de estancias.
 * Se encarga de mapear DTOs, validar existencia de vehículos y gestionar pagos.
 * 
 * @author Daniel Manzano Borja
 * @since 2025-08-10
 */
@Service
@RequiredArgsConstructor
public class EstanciaServiceImpl implements EstanciaService {
	
    private EstanciaRepository estanciaRepository;
    
    private VehiculoRepository vehiculoRepository;
    
    private EstanciaMapper estanciaMapper;
    
    /**
	 * Constructor that injects the estanciaRepository, vehiculoRepository and estanciaMapper interfaces.
	 * 
	 * @param estanciaRepository | EstanciaRepository JPA-interface.
	 * @param vehiculoRepository | VehiculoRepository JPA-interface.
	 * @param estanciaMapper    | EstanciaMapper.
	 */
    public EstanciaServiceImpl(EstanciaRepository estanciaRepository, VehiculoRepository vehiculoRepository, EstanciaMapper estanciaMapper) {
    	this.estanciaRepository = estanciaRepository;
    	this.vehiculoRepository = vehiculoRepository;
    	this.estanciaMapper = estanciaMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EstanciaDTO registrarEntrada(String placa) {
    	Vehiculo vehiculo = vehiculoRepository.findByPlaca(placa)
                .orElseThrow(() -> new VehiculoNoEncontradoException("Vehículo no encontrado con placa: " + placa));

        Estancia estancia = Estancia.builder()
        		.vehiculo(vehiculo)
        		.horaEntrada(LocalDateTime.now())
        		.horaSalida(null)
        		.importePago(0.0)
        		.build();
        
        Estancia estanciaGuardada = estanciaRepository.save(estancia);
        return estanciaMapper.toDTO(estanciaGuardada);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public EstanciaDTO registrarSalida(String placa) {
    	Estancia estancia = estanciaRepository.findEstanciaActivaPorPlaca(placa)
                .orElseThrow(() -> new EstanciaNoEncontradaException("No se encontró estancia activa para el vehículo con matricula: " + placa));

        estancia.setHoraSalida(LocalDateTime.now());
        
        // calculamos importePago antes de guardar
        double importe = estancia.calcularImportePago();
        estancia.setImportePago(importe);
        
        // si es residente, acumular minutos en vehiculo
        Vehiculo vehiculo = estancia.getVehiculo();
        if (vehiculo.getTipo() == TipoVehiculo.RESIDENTE) {
            long minutos = estancia.getDuracionMinutos();
            vehiculo.acumularTiempo(minutos);
            vehiculoRepository.save(vehiculo);
        }
        
        Estancia actualizada = estanciaRepository.save(estancia);
        return estanciaMapper.toDTO(actualizada);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<EstanciaDTO> listarEstancias() {
        return estanciaMapper.toDTOList(estanciaRepository.findAll());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<EstanciaDTO> listarEstanciasPorTipo(TipoVehiculo tipo) {
        return estanciaMapper.toDTOList(estanciaRepository.findByVehiculo_Tipo(tipo));
    }

}
