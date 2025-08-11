/**
 * 
 */
package com.example.parking.service;

import java.io.ByteArrayInputStream;

import org.springframework.stereotype.Component;

/**
 * Servicio para generación de reportes (PDF) relacionados con el estacionamiento.
 * 
 * Autor: Daniel Manzano Borja
 * Fecha: 2025-08-09
 */
@Component
public interface ReporteService {
	
	/**
     * Genera un reporte en PDF con los pagos de residentes.
     *
     * @return PDF en flujo de bytes.
     */
    ByteArrayInputStream generarReportePagosResidentes();

}
