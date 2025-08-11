/**
 * 
 */
package com.example.parking.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.parking.dto.VehiculoDTO;
import com.example.parking.dto.mapper.VehiculoMapper;
import com.example.parking.exception.VehiculoYaRegistradoException;
import com.example.parking.model.entity.Vehiculo;
import com.example.parking.model.enums.TipoVehiculo;
import com.example.parking.repository.EstanciaRepository;
import com.example.parking.repository.VehiculoRepository;

import io.swagger.v3.oas.annotations.Operation;

/**
 * Implementación de {@link VehiculoService} para la gestión de vehículos.
 *
 * @author Daniel Manzano
 * @since 2025-08-08
 */
@Service
public class VehiculoServiceImpl implements VehiculoService {
	
	private VehiculoRepository vehiculoRepository;
	private EstanciaRepository estanciaRepository;
    private VehiculoMapper vehiculoMapper;

    public VehiculoServiceImpl(VehiculoRepository vehiculoRepository, EstanciaRepository estanciaRepository, VehiculoMapper vehiculoMapper) {
        this.vehiculoRepository = vehiculoRepository;
        this.estanciaRepository = estanciaRepository;
        this.vehiculoMapper = vehiculoMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public VehiculoDTO registrarVehiculo(VehiculoDTO dto) {
    	vehiculoRepository.findByPlaca(dto.placa())
        .ifPresent(v -> {
            throw new VehiculoYaRegistradoException("Ya existe un vehículo con la placa: " + dto.placa());
        });

    	Vehiculo vehiculo = vehiculoMapper.toEntity(dto);
        return vehiculoMapper.toDTO(vehiculoRepository.save(vehiculo));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<VehiculoDTO> listarVehiculos() {
    	return vehiculoMapper.toDTOList(vehiculoRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    @Operation(summary = "Listar vehículos por tipo")
    public List<VehiculoDTO> listarVehiculosPorTipo(TipoVehiculo tipo) {
    	return vehiculoMapper.toDTOList(vehiculoRepository.findByTipo(tipo));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void comenzarNuevoMes() {
    	// Eliminar estancias de vehículos oficiales
        estanciaRepository.deleteByVehiculoTipo(TipoVehiculo.OFICIAL);

        // Resetear tiempo acumulado de vehículos residentes
        vehiculoRepository.resetTiempoAcumuladoByTipo(TipoVehiculo.RESIDENTE);
    }

}
