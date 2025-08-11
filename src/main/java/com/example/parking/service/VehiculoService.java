/**
 * 
 */
package com.example.parking.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.parking.dto.VehiculoDTO;
import com.example.parking.model.enums.TipoVehiculo;

/**
 * Interfaz de servicio para la gestión de vehículos en el sistema de estacionamiento.
 * 
 * @author Daniel Manzano Borja
 * @since 2025-08-08
 */
@Component
public interface VehiculoService {

	/**
     * Registra un nuevo vehículo en el sistema.
     *
     * @param dto Datos del vehículo a registrar.
     * @return DTO del vehículo registrado.
     */
	VehiculoDTO registrarVehiculo(VehiculoDTO dto);

	/**
     * Lista todos los vehículos registrados.
     *
     * @return Lista de DTOs de vehículos.
     */
    List<VehiculoDTO> listarVehiculos();
    
    /**
     * Lista vehículos filtrados por tipo.
     *
     * @param tipo Tipo del vehículo.
     * @return Lista de DTOs de vehículos filtrados por tipo.
     */
    List<VehiculoDTO> listarVehiculosPorTipo(TipoVehiculo tipo);
    
    /**
     * Inicia un nuevo mes reiniciando el tiempo acumulado de residentes y elimina estancias de oficiales.
     */
    void comenzarNuevoMes();

}
