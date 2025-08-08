/**
 * 
 */
package com.example.parking.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.io.ByteArrayResource;

import com.example.parking.model.entity.Residente;
import com.example.parking.repository.ResidenteRepository;

/**
 * 
 */
public class ReporteServiceImplTest {
	
	private ResidenteRepository repo;
    private ReporteServiceImpl service;

    @BeforeEach
    void setUp() {
        repo = Mockito.mock(ResidenteRepository.class);
        service = new ReporteServiceImpl(repo);
    }

    @Test
    void testGenerarReporteResidentes() {
        Residente r1 = new Residente();
        r1.setNumeroPlaca("ABC123");
        r1.setTiempoEstacionado(100);

        Residente r2 = new Residente();
        r2.setNumeroPlaca("XYZ789");
        r2.setTiempoEstacionado(200);

        when(repo.findAll()).thenReturn(Arrays.asList(r1, r2));

        ByteArrayResource resource = service.generarReporteResidentes();
        String csv = new String(resource.getByteArray(), StandardCharsets.UTF_8);

        assertTrue(csv.contains("ABC123"));
        assertTrue(csv.contains("XYZ789"));
        assertTrue(csv.contains("Tiempo estacionado"));
        assertTrue(csv.contains("Cantidad a pagar"));
    }

}
