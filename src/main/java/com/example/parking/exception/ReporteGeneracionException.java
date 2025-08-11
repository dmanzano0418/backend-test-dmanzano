/**
 * 
 */
package com.example.parking.exception;

/**
 * Excepción lanzada cuando ocurre un error durante la generación del reporte PDF.
 * 
 * Autor: Daniel Manzano Borja
 * Fecha: 2025-08-09
 */
public class ReporteGeneracionException extends RuntimeException {
	
	private static final long serialVersionUID = 8856969918901750025L;

	public ReporteGeneracionException(String message) {
        super(message);
    }

    public ReporteGeneracionException(String message, Throwable cause) {
        super(message, cause);
    }

}
