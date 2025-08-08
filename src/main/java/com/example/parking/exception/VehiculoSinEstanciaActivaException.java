/**
 * 
 */
package com.example.parking.exception;

/**
 * Se intenta registrar una salida para un vehículo que no tiene estancia activa
 */
public class VehiculoSinEstanciaActivaException extends RuntimeException {
	
	private static final long serialVersionUID = 6453583145470950216L;

	public VehiculoSinEstanciaActivaException(String placa) {
        super("El vehículo con placa " + placa + " no tiene una estancia activa.");
    }

}
