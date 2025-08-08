/**
 * 
 */
package com.example.parking.exception;

/**
 * Error al generar un reporte
 */
public class ReporteGeneracionException extends RuntimeException {
	
	private static final long serialVersionUID = 8856969918901750025L;

	public ReporteGeneracionException(String mensaje) {
        super("Error al generar el reporte: " + mensaje);
    }

}
