/**
 * 
 */
package com.example.parking.exception;

/**
 * Intento de dar de alta un vehículo que ya está registrado
 */
public class VehiculoYaRegistradoException extends RuntimeException {
	
	private static final long serialVersionUID = 7615240168464461171L;

	public VehiculoYaRegistradoException(String message) {
        super(message);
    }

}
