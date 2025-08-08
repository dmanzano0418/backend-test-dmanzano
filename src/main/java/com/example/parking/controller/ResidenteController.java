/**
 * 
 */
package com.example.parking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.parking.service.ResidenteServiceImpl;

/**
 * 
 */
@RestController
@RequestMapping("/api/residentes")
public class ResidenteController {
	
	private ResidenteServiceImpl residenteService;

    @PostMapping("/alta")
    public ResponseEntity<String> alta(@RequestParam String placa) {
        residenteService.registrarAlta(placa);
        return ResponseEntity.ok("Vehículo residente registrado: " + placa);
    }

    @PostMapping("/entrada")
    public ResponseEntity<String> entrada(@RequestParam String placa) {
        residenteService.registrarEntrada(placa);
        return ResponseEntity.ok("Entrada registrada para residente: " + placa);
    }

    @PostMapping("/salida")
    public ResponseEntity<String> salida(@RequestParam String placa) {
        residenteService.registrarSalida(placa);
        return ResponseEntity.ok("Salida registrada para residente: " + placa);
    }

}
