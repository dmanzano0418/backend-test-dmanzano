package com.example.parking.dto;

import java.time.LocalDateTime;

import com.example.parking.model.enums.TipoVehiculo;

/**
 * DTO para transferir información de estancia.
 * 
 * @param id          identificador único de la estancia
 * @param placa       placa del vehículo asociado
 * @param tipo        tipo de vehículo
 * @param horaEntrada fecha y hora de entrada
 * @param horaSalida  fecha y hora de salida
 * @param importePago importe cobrado por la estancia
 * 
 * @author Daniel Manzano Borja
 * @since 2025-08-08
 */
public record EstanciaDTO(Long id, String placa, TipoVehiculo tipo, LocalDateTime horaEntrada,
		LocalDateTime horaSalida, Double importePago) {
}
