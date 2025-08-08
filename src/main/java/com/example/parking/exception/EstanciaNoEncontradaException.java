/**
 * 
 */
package com.example.parking.exception;

/**
 * 
 */
public class EstanciaNoEncontradaException extends RuntimeException {
	
	private static final long serialVersionUID = 6368888890888714753L;

	public EstanciaNoEncontradaException(Long id) {
        super("Estancia con ID " + id + " no encontrada.");
    }

}
