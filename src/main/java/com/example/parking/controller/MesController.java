/**
 * 
 */
package com.example.parking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.parking.service.EstanciaService;

import io.swagger.v3.oas.annotations.Operation;

/**
 * 
 */
@RestController
@RequestMapping("/api/mes")
public class MesController {
	
	private EstanciaService estanciaService;

    @Operation(summary = "Reiniciar datos de nuevo mes")
    @PostMapping("/reiniciar")
    public ResponseEntity<String> reiniciarMes() {
        estanciaService.reiniciarMes();
        return ResponseEntity.ok("Datos reiniciados para nuevo mes.");
    }

}
