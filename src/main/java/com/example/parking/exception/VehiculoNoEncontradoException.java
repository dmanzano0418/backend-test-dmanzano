/**
 * 
 */
package com.example.parking.exception;

/**
 * 
 */
public class VehiculoNoEncontradoException extends RuntimeException {
	
	private static final long serialVersionUID = -1769712377892793191L;

	public VehiculoNoEncontradoException(String placa) {
        super("Vehículo con placa " + placa + " no encontrado.");
    }

}
