/**
 * 
 */
package com.example.parking.service;

import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import com.example.parking.model.entity.Residente;
import com.example.parking.repository.ResidenteRepository;

import lombok.RequiredArgsConstructor;

/**
 * 
 */
@Service
@RequiredArgsConstructor
public class ReporteServiceImpl implements ReporteService {
	
	private ResidenteRepository residenteRepository;
	
    private static final double TARIFA_MINUTO = 0.05;
    private static final DecimalFormat df = new DecimalFormat("#0.00");

    @Override
    public ByteArrayResource generarReporteResidentes() {
        List<Residente> residentes = residenteRepository.findAll();
        
        StringBuilder sb = new StringBuilder();
        sb.append("Núm. placa,Tiempo estacionado (min.),Cantidad a pagar\n");

        for (Residente r : residentes) {
            double totalPagar = r.getTiempoEstacionado() * TARIFA_MINUTO;
            sb.append(r.getNumeroPlaca())
              .append(",")
              .append(r.getTiempoEstacionado())
              .append(",")
              .append(df.format(totalPagar))
              .append("\n");
        }
        
        byte[] data = sb.toString().getBytes(StandardCharsets.UTF_8);
        return new ByteArrayResource(data);
        
    }

}
