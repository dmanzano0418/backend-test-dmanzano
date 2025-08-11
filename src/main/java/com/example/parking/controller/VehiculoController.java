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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.parking.dto.VehiculoDTO;
import com.example.parking.model.enums.TipoVehiculo;
import com.example.parking.service.VehiculoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controlador que maneja la creación/listado/reinicio de vehículos
 * 
 * @author Daniel Manzano Borja
 * @since 08-AGO-2025
 */
@RestController
@RequestMapping("/api/vehiculos")
@Tag(name = "Vehículos", description = "Operaciones relacionadas con la gestión de vehículos")
public class VehiculoController {
	
	private VehiculoService vehiculoService;

	/**
	 * Constructor that injects the vehiculoService interface.
	 * 
	 * @param vehiculoService | VehiculoService interface.
	 */
    public VehiculoController(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    @Operation(summary = "Registrar vehículo", description = "Agrega un nuevo vehículo al sistema")
    @PostMapping
    public ResponseEntity<VehiculoDTO> registrar(
            @Parameter(description = "Datos del vehículo a registrar", required = true)
            @RequestBody VehiculoDTO vehiculoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vehiculoService.registrarVehiculo(vehiculoDTO));
    }
    
    @Operation(summary = "Listar vehículos", description = "Obtiene todos los vehículos registrados")
    @GetMapping
    public ResponseEntity<List<VehiculoDTO>> listarTodos() {
        return ResponseEntity.ok(vehiculoService.listarVehiculos());
    }
    
    @Operation(summary = "Listar vehículos por tipo", description = "Obtiene los vehículos filtrados por tipo")
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<VehiculoDTO>> listarPorTipo(
            @Parameter(description = "Tipo de vehículo (OFICIAL, RESIDENTE, NO_RESIDENTE)", example = "RESIDENTE")
            @PathVariable TipoVehiculo tipo) {
        return ResponseEntity.ok(vehiculoService.listarVehiculosPorTipo(tipo));
    }
    
    @Operation(summary = "Iniciar nuevo mes", description = "Reinicia el tiempo acumulado de residentes y elimina oficiales")
    @PostMapping("/nuevo-mes")
    public ResponseEntity<Void> comenzarNuevoMes() {
        vehiculoService.comenzarNuevoMes();
        return ResponseEntity.noContent().build();
    }

}
