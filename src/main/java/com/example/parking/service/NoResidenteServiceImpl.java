/**
 * 
 */
package com.example.parking.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.parking.model.entity.Estancia;
import com.example.parking.model.entity.NoResidente;
import com.example.parking.model.entity.Vehiculo;
import com.example.parking.repository.EstanciaRepository;
import com.example.parking.repository.NoResidenteRepository;

import jakarta.transaction.Transactional;

/**
 * 
 */
@Service
public class NoResidenteServiceImpl implements VehiculoService {
	
	private NoResidenteRepository noResidenteRepository;
    private EstanciaRepository estanciaRepository;

    @Override
    public void registrarAlta(String placa) {
        if (!noResidenteRepository.existsById(placa)) {
            NoResidente noResidente = new NoResidente();
            noResidente.setNumeroPlaca(placa);
            noResidenteRepository.save(noResidente);
        }
    }

    @Override
    @Transactional
    public void registrarEntrada(String placa) {
        NoResidente vehiculo = noResidenteRepository.findById(placa)
                .orElseThrow(() -> new RuntimeException("No residente no encontrado"));
        Estancia estancia = new Estancia();
        estancia.setHoraEntrada(LocalDateTime.now());
        estancia.setVehiculo(vehiculo);
        estanciaRepository.save(estancia);
    }

    @Override
    @Transactional
    public void registrarSalida(String placa) {
        NoResidente vehiculo = noResidenteRepository.findById(placa)
                .orElseThrow(() -> new RuntimeException("No residente no encontrado"));
        Estancia estancia = obtenerUltimaEstanciaActiva(vehiculo);
        estancia.setHoraSalida(LocalDateTime.now());
        estanciaRepository.save(estancia);

        int minutos = estancia.calcularMinutos();
        double monto = minutos * 0.5;
        System.out.printf("Vehículo %s debe pagar: MXN$%.2f%n", placa, monto);
        // Puedes devolverlo como DTO o respuesta HTTP en controlador
    }

    private Estancia obtenerUltimaEstanciaActiva(Vehiculo vehiculo) {
        return estanciaRepository.findByVehiculo_NumeroPlaca(vehiculo.getNumeroPlaca()).stream()
                .filter(e -> e.getHoraSalida() == null)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No hay estancia activa"));
    }

    @Override
    public Vehiculo obtenerVehiculo(String placa) {
        return noResidenteRepository.findById(placa).orElse(null);
    }

}
