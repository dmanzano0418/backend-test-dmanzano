/**
 * 
 */
package com.example.parking.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.parking.model.entity.Estancia;
import com.example.parking.model.entity.Residente;
import com.example.parking.model.entity.Vehiculo;
import com.example.parking.repository.EstanciaRepository;
import com.example.parking.repository.ResidenteRepository;

import jakarta.transaction.Transactional;

/**
 * 
 */
@Service
public class ResidenteServiceImpl implements VehiculoService {
	
	private ResidenteRepository residenteRepository;
    private EstanciaRepository estanciaRepository;

    @Override
    public void registrarAlta(String placa) {
        if (!residenteRepository.existsById(placa)) {
            Residente residente = new Residente();
            residente.setNumeroPlaca(placa);
            residente.setTiempoEstacionado(0);
            residenteRepository.save(residente);
        }
    }

    @Override
    @Transactional
    public void registrarEntrada(String placa) {
        Residente residente = residenteRepository.findById(placa)
                .orElseThrow(() -> new RuntimeException("Residente no encontrado"));
        Estancia estancia = new Estancia();
        estancia.setHoraEntrada(LocalDateTime.now());
        estancia.setVehiculo(residente);
        estanciaRepository.save(estancia);
    }

    @Override
    @Transactional
    public void registrarSalida(String placa) {
        Residente residente = residenteRepository.findById(placa)
                .orElseThrow(() -> new RuntimeException("Residente no encontrado"));
        Estancia estancia = obtenerUltimaEstanciaActiva(residente);
        estancia.setHoraSalida(LocalDateTime.now());
        estanciaRepository.save(estancia);
        residente.setTiempoEstacionado(residente.getTiempoEstacionado() + estancia.calcularMinutos());
        residenteRepository.save(residente);
    }

    private Estancia obtenerUltimaEstanciaActiva(Vehiculo vehiculo) {
        return estanciaRepository.findByVehiculo_NumeroPlaca(vehiculo.getNumeroPlaca()).stream()
                .filter(e -> e.getHoraSalida() == null)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No hay estancia activa"));
    }

    @Override
    public Vehiculo obtenerVehiculo(String placa) {
        return residenteRepository.findById(placa).orElse(null);
    }

}
