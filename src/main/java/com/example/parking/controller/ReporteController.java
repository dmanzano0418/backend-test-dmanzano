/**
 * 
 */
package com.example.parking.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.parking.service.ReporteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 
 */
@RestController
@RequestMapping("/api/reportes")
@Tag(name = "Reportes", description = "Operaciones relacionadas con reportes del estacionamiento")
public class ReporteController {
	
	private ReporteService reporteService;

	@Operation(
	        summary = "Generar reporte de residentes",
	        description = "Genera un archivo CSV con el tiempo estacionado y el monto a pagar por cada residente."
	    )
	    @ApiResponses(value = {
	        @ApiResponse(
	            responseCode = "200",
	            description = "Reporte generado exitosamente",
	            content = @Content(
	                mediaType = "text/csv",
	                schema = @Schema(type = "string", format = "binary")
	            )
	        ),
	        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
	    })
	@GetMapping("/residentes")
    public ResponseEntity<ByteArrayResource> descargarReporteResidentes() {
        ByteArrayResource recurso = reporteService.generarReporteResidentes();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_residentes.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .contentLength(recurso.contentLength())
                .body(recurso);
    }

}
