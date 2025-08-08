/**
 * 
 */
package com.example.parking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.parking.service.NoResidenteServiceImpl;

/**
 * 
 */
@RestController
@RequestMapping("/api/no-residentes")
public class NoResidenteController {
	
	private NoResidenteServiceImpl noResidenteService;

    @PostMapping("/alta")
    public ResponseEntity<String> alta(@RequestParam String placa) {
        noResidenteService.registrarAlta(placa);
        return ResponseEntity.ok("Vehículo no residente registrado: " + placa);
    }

    @PostMapping("/entrada")
    public ResponseEntity<String> entrada(@RequestParam String placa) {
        noResidenteService.registrarEntrada(placa);
        return ResponseEntity.ok("Entrada registrada para no residente: " + placa);
    }

    @PostMapping("/salida")
    public ResponseEntity<String> salida(@RequestParam String placa) {
        noResidenteService.registrarSalida(placa);
        return ResponseEntity.ok("Salida registrada para no residente: " + placa);
    }

}
