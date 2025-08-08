/**
 * 
 */
package com.example.parking.service;

import org.springframework.stereotype.Component;

/**
 * 
 */
@Component
public interface EstanciaService {
	
	void registrarEntrada(String placa);
    void registrarSalida(String placa);
    void darDeAltaVehiculo(String placa, String tipo);
    void reiniciarMes();

}
