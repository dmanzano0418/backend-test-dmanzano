/**
 * 
 */
package com.example.parking.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;

/**
 * 
 */
@Component
public interface ReporteService {
	
	ByteArrayResource generarReporteResidentes();

}
