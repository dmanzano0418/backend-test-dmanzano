/**
 * 
 */
package com.example.parking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.parking.service.EstanciaService;

/**
 * 
 */
@RestController
@RequestMapping("/api")
public class EstacionamientoController {
	
	private EstanciaService estanciaService;

    // 3. Da de alta un vehículo (oficial, residente o no residente)
    @PostMapping("/vehiculos")
    public ResponseEntity<String> registrarVehiculo(
            @RequestParam String placa,
            @RequestParam String tipo) {
        estanciaService.darDeAltaVehiculo(placa, tipo);
        return ResponseEntity.ok("Vehículo registrado como " + tipo.toUpperCase());
    }

    // 1. Registrar entrada
    @PostMapping("/entradas")
    public ResponseEntity<String> registrarEntrada(@RequestParam String placa) {
        estanciaService.registrarEntrada(placa);
        return ResponseEntity.ok("Entrada registrada para placa: " + placa);
    }

    // 2. Registrar salida
    @PostMapping("/salidas")
    public ResponseEntity<String> registrarSalida(@RequestParam String placa) {
        estanciaService.registrarSalida(placa);
        return ResponseEntity.ok("Salida registrada para placa: " + placa);
    }

    // 5. Comenzar mes
    @PostMapping("/comenzar-mes")
    public ResponseEntity<String> comenzarMes() {
        estanciaService.comenzarMes();
        return ResponseEntity.ok("Mes reiniciado: estancias de oficiales eliminadas, tiempo de residentes reiniciado.");
    }

}
