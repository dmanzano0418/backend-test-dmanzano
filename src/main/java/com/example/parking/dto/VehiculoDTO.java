/**
 * 
 */
package com.example.parking.dto;

import com.example.parking.model.enums.TipoVehiculo;

/**
 * DTO para transferir información de un vehículo.
 * 
 * @param id Identificador interno del vehículo.
 * @param placa Número de placa único.
 * @param tipo Tipo de vehículo (OFICIAL, RESIDENTE, NO_RESIDENTE, etc.)
 * @param tiempoAcumulado Tiempo acumulado en minutos para residentes
 * 
 * @author Daniel Manzano Borja
 * @since 2025-08-08
 */
public record VehiculoDTO(Long id, String placa, TipoVehiculo tipo, Long tiempoAcumulado) {
}
