/**
 * 
 */
package com.example.parking.controller;

import java.io.ByteArrayInputStream;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.parking.service.ReporteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controlador para generación de reportes (PDF).
 *
 * Autor: Daniel Manzano Borja
 * Fecha: 2025-08-09
 */
@RestController
@RequestMapping("/api/reportes")
//@RequiredArgsConstructor
@Tag(name = "Reportes", description = "Operaciones relacionadas con la generación de reportes PDF")
public class ReporteController {
	
	private ReporteService reporteService;
	
	public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

	/**
     * Genera un reporte PDF con los pagos de los vehículos residentes.
     *
     * @return PDF file.
     */
	@Operation(summary = "Generar reporte de pagos de residentes",
            description = "Genera un archivo PDF con los pagos de los vehículos de tipo RESIDENTE",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reporte generado correctamente"),
                    @ApiResponse(responseCode = "500", description = "Error al generar el reporte")
            })
    @GetMapping(value = "/reportes/pagos-residentes", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generarReportePagosResidentes() {
        ByteArrayInputStream bis = reporteService.generarReportePagosResidentes();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=pagos_residentes.pdf")
                .body(bis.readAllBytes());
    }

}
