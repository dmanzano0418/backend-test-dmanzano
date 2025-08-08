/**
 * 
 */
package com.example.parking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.parking.service.OficialServiceImpl;

/**
 * 
 */
@RestController
@RequestMapping("/api/oficiales")
public class OficialController {
	
	private OficialServiceImpl oficialService;

    @PostMapping("/alta")
    public ResponseEntity<String> alta(@RequestParam String placa) {
        oficialService.registrarAlta(placa);
        return ResponseEntity.ok("Vehículo oficial registrado: " + placa);
    }

    @PostMapping("/entrada")
    public ResponseEntity<String> entrada(@RequestParam String placa) {
        oficialService.registrarEntrada(placa);
        return ResponseEntity.ok("Entrada registrada para oficial: " + placa);
    }

    @PostMapping("/salida")
    public ResponseEntity<String> salida(@RequestParam String placa) {
        oficialService.registrarSalida(placa);
        return ResponseEntity.ok("Salida registrada para oficial: " + placa);
    }

}
