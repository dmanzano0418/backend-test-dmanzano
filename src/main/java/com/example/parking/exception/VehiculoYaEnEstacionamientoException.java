/**
 * 
 */
package com.example.parking.exception;

/** 
 * Se intenta registrar una entrada para un vehículo que ya está en el estacionamiento
 */
public class VehiculoYaEnEstacionamientoException extends RuntimeException {
	
    private static final long serialVersionUID = -707009150381380133L;

	public VehiculoYaEnEstacionamientoException(String placa) {
        super("El vehículo con placa " + placa + " ya tiene una estancia activa.");
    }

}
