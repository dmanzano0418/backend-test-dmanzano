/**
 * 
 */
package com.example.parking.service;

import org.springframework.stereotype.Component;

import com.example.parking.model.entity.Vehiculo;

/**
 * Interface where the signature of the business methods are declared.
 * 
 * @author Daniel Manzano Borja
 * @since 07-AGO-2025
 * 
 */
@Component
public interface VehiculoService {
	
	void registrarEntrada(String placa);
    void registrarSalida(String placa);
    void registrarAlta(String placa);
    Vehiculo obtenerVehiculo(String placa);

}
