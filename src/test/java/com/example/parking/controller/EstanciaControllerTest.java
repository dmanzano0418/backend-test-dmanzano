package com.example.parking.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.parking.dto.EstanciaDTO;
import com.example.parking.model.enums.TipoVehiculo;
import com.example.parking.service.EstanciaService;

/**
 * Tests unitarios para EstanciaController.
 * @author Daniel Manzano Borja
 * @since 2025-08-09
 */
@WebMvcTest(EstanciaController.class)
@Import(EstanciaService.class) // Importa la implementación real o la configuración necesaria
class EstanciaControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("removal")
	@MockBean
    private EstanciaService estanciaService;

    @Test
    @DisplayName("POST /api/estancias/entrada/{placa} - registrar entrada éxito")
    void testRegistrarEntrada() throws Exception {
        EstanciaDTO dto = new EstanciaDTO(
            1L,                      // id
            "ABC123",
            TipoVehiculo.RESIDENTE,
            LocalDateTime.now(),
            null,
            Double.valueOf(0.0)
        );

        when(estanciaService.registrarEntrada(anyString())).thenReturn(dto);

        mockMvc.perform(post("/api/estancias/entrada/ABC123")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.placa").value("ABC123"))
            .andExpect(jsonPath("$.tipo").value("RESIDENTE"));
    }

    @Test
    @DisplayName("POST /api/estancias/salida/{placa} - registrar salida éxito")
    void testRegistrarSalida() throws Exception {
        EstanciaDTO dto = new EstanciaDTO(
            1L,
            "ABC123",
            TipoVehiculo.RESIDENTE,
            LocalDateTime.now().minusHours(2),
            LocalDateTime.now(),
            Double.valueOf(120.0 * 0.05)
        );

        when(estanciaService.registrarSalida(anyString())).thenReturn(dto);

        mockMvc.perform(post("/api/estancias/salida/ABC123")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.placa").value("ABC123"))
            .andExpect(jsonPath("$.importePago").value(dto.importePago()));
    }

    @Test
    @DisplayName("GET /api/estancias - listar todas las estancias")
    void testListarTodas() throws Exception {
        EstanciaDTO dto1 = new EstanciaDTO(
            1L,
            "ABC123",
            TipoVehiculo.RESIDENTE,
            LocalDateTime.now().minusHours(3),
            LocalDateTime.now().minusHours(2),
            Double.valueOf(6.0)
        );
        EstanciaDTO dto2 = new EstanciaDTO(
            2L,
            "XYZ789",
            TipoVehiculo.NO_RESIDENTE,
            LocalDateTime.now().minusHours(1),
            null,
            Double.valueOf(0.0)
        );

        when(estanciaService.listarEstancias()).thenReturn(List.of(dto1, dto2));

        mockMvc.perform(get("/api/estancias")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].placa").value("ABC123"))
            .andExpect(jsonPath("$[1].placa").value("XYZ789"));
    }

    @Test
    @DisplayName("GET /api/estancias/tipo/{tipo} - listar estancias por tipo")
    void testListarPorTipo() throws Exception {
        EstanciaDTO dto1 = new EstanciaDTO(
            1L,
            "ABC123",
            TipoVehiculo.RESIDENTE,
            LocalDateTime.now().minusHours(3),
            LocalDateTime.now().minusHours(2),
            Double.valueOf(6.0)
        );

        when(estanciaService.listarEstanciasPorTipo(TipoVehiculo.RESIDENTE)).thenReturn(List.of(dto1));

        mockMvc.perform(get("/api/estancias/tipo/RESIDENTE")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$[0].tipo").value("RESIDENTE"));
    }

}
