/**
 * 
 */
package com.example.parking.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.parking.dto.EstanciaDTO;
import com.example.parking.model.enums.TipoVehiculo;
import com.example.parking.service.EstanciaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controlador REST para la gestión de estancias en el estacionamiento.
 * Proporciona endpoints para registrar entradas, registrar salidas,
 * así como listar estancias filtradas por tipo de vehículo.
 * 
 * @author Daniel Manzano Borja
 * @since 2025-08-10
 */
@RestController
@RequestMapping("/api/estancias")
@Tag(name = "Estancias", description = "Operaciones relacionadas con la gestión de estancias de vehículos")
public class EstanciaController {
	
	private EstanciaService estanciaService;

	public EstanciaController(EstanciaService estanciaService) {
        this.estanciaService = estanciaService;
    }
	
	/**
     * Registra la entrada de un vehículo al estacionamiento.
     *
     * @param placa Placa del vehículo que ingresa.
     * @return Información de la estancia registrada.
     */
	@Operation(
            summary = "Registrar entrada de vehículo",
            description = "Registra una nueva estancia para el vehículo especificado por su placa.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Entrada registrada correctamente",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EstanciaDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Vehículo no encontrado"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos")
            }
    )
    @PostMapping("/entrada/{placa}")
	public ResponseEntity<EstanciaDTO> registrarEntrada(@PathVariable String placa) {
    	return ResponseEntity.status(HttpStatus.CREATED).body(estanciaService.registrarEntrada(placa));
    }
    
    /**
     * Registra la salida de un vehículo del estacionamiento.
     *
     * @param placa Placa del vehículo que sale.
     * @return DTO de la estancia actualizada con la hora de salida e importe.
     */
	@Operation(
            summary = "Registrar salida de vehículo",
            description = "Registra la salida para la estancia activa de un vehículo.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Salida registrada correctamente",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EstanciaDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Estancia activa no encontrada")
            }
    )
    @PostMapping("/salida/{placa}")
    public ResponseEntity<EstanciaDTO> registrarSalida(
    		@Parameter(description = "Placa del vehículo", required = true)
            @PathVariable String placa) {
    	return ResponseEntity.status(HttpStatus.OK).body(estanciaService.registrarSalida(placa));
    }
	
    /**
     * Lista todas las estancias registradas.
     *
     * @return lista de estancias
     */
	@Operation(
            summary = "Listar todas las estancias",
            description = "Obtiene todas las estancias registradas en el sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EstanciaDTO.class)))
            }
    )
    @GetMapping
    public ResponseEntity<List<EstanciaDTO>> listarTodas() {
    	return ResponseEntity.status(HttpStatus.OK).body(estanciaService.listarEstancias());
    }
    
	
	/**
     * Lista todas las estancias filtradas por tipo de vehículo.
     *
     * @param tipo Tipo de vehículo (OFICIAL, RESIDENTE, NO_RESIDENTE).
     * @return Lista filtrada de estancias.
     */
    @Operation(
            summary = "Listar estancias por tipo de vehículo",
            description = "Obtiene todas las estancias filtradas según el tipo de vehículo.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista filtrada obtenida correctamente",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EstanciaDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Tipo de vehículo inválido")
            }
    )
    @GetMapping("/tipo/{tipo}")
	public ResponseEntity<List<EstanciaDTO>> listarPorTipo(
			@Parameter(description = "Tipo de vehículo (OFICIAL, RESIDENTE, NO_RESIDENTE)", required = true)
            @PathVariable TipoVehiculo tipo) {
		return ResponseEntity.status(HttpStatus.OK).body(estanciaService.listarEstanciasPorTipo(tipo));
        
    }

}
