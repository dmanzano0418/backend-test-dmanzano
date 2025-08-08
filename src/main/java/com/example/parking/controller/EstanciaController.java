/**
 * 
 */
package com.example.parking.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.parking.service.EstanciaService;

/**
 * 
 */
@RestController
@RequestMapping("/estancias")
public class EstanciaController {
	
	private final EstanciaService estanciaService;
    private final EstanciaMapper estanciaMapper;

    @PostMapping("/entrada/{placa}")
    public ResponseEntity<EstanciaDTO> registrarEntrada(@PathVariable String placa) {
        Estancia estancia = estanciaService.registrarEntrada(placa);
        return ResponseEntity.ok(estanciaMapper.toDTO(estancia));
    }

    @PostMapping("/salida/{placa}")
    public ResponseEntity<EstanciaDTO> registrarSalida(@PathVariable String placa) {
        Estancia estancia = estanciaService.registrarSalida(placa);
        return ResponseEntity.ok(estanciaMapper.toDTO(estancia));
    }

    @GetMapping
    public ResponseEntity<List<EstanciaDTO>> listarEstancias() {
        List<EstanciaDTO> lista = estanciaService.listarEstancias()
                .stream()
                .map(estanciaMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

}
