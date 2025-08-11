/**
 * 
 */
package com.example.parking.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.parking.dto.EstanciaDTO;
import com.example.parking.exception.EstanciaNoEncontradaException;
import com.example.parking.exception.VehiculoNoEncontradoException;
import com.example.parking.model.enums.TipoVehiculo;

/**
 * Interfaz de servicio para la gestión de estancias de vehículos en el estacionamiento.
 * 
 * @author Daniel Manzano Borja
 * @since 2025-08-08
 */
@Component
public interface EstanciaService {
	
	/**
     * Registra la entrada de un vehículo creando una nueva estancia.
     * Busca el vehículo por placa para asociar la estancia.
     * 
     * @param placa placa del vehículo que ingresa
     * @return DTO actualizado con datos guardados
     * @throws VehiculoNoEncontradoException si el vehículo no existe
     */
	EstanciaDTO registrarEntrada(String placa);
	
    /**
     * Registra la salida de un vehículo, calcula importe y acumula tiempo si aplica.
     * 
     * @param placa placa del vehículo que sale
     * @return DTO actualizado con importe pago calculado
     * @throws EstanciaNoEncontradaException si no hay estancia activa para la placa
     */
	EstanciaDTO registrarSalida(String placa);
	
    /**
     * Lista todas las estancias registradas.
     * 
     * @return lista de DTOs de estancias
     */
	List<EstanciaDTO> listarEstancias();
	
    /**
     * Lista las estancias filtradas por tipo de vehículo.
     * @param tipo tipo del vehículo (OFICIAL, RESIDENTE, NO_RESIDENTE).
     * @return lista de DTOs de estancias filtradas.
     */
	List<EstanciaDTO> listarEstanciasPorTipo(TipoVehiculo tipo);

}
