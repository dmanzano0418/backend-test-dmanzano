package com.example.parking.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.parking.dto.VehiculoDTO;
import com.example.parking.model.enums.TipoVehiculo;
import com.example.parking.service.EstanciaService;
import com.example.parking.service.VehiculoService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Tests unitarios para VehiculoController.
 * 
 * @author Daniel Manzano Borja
 * @since 2025-08-09
 */
@WebMvcTest(VehiculoController.class)
class VehiculoControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

	@SuppressWarnings("removal")
    @MockBean
    private VehiculoService vehiculoService;
	
	@SuppressWarnings("removal")
	@MockBean
    private EstanciaService estanciaService;

    private VehiculoDTO vehiculoDTO;

    @BeforeEach
    void setUp() {
        vehiculoDTO = new VehiculoDTO(1L,"ABC123", TipoVehiculo.RESIDENTE, 0L);
    }

    /**
     * Test para registrar un nuevo vehículo.
     * Endpoint: POST /vehiculos
     */
    @Test
    void testRegistrarVehiculo() throws Exception {
        when(vehiculoService.registrarVehiculo(any(VehiculoDTO.class))).thenReturn(vehiculoDTO);

        mockMvc.perform(post("/vehiculos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vehiculoDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.placa").value("ABC123"))
                .andExpect(jsonPath("$.tipo").value("RESIDENTE"))
                .andExpect(jsonPath("$.tiempoAcumulado").value(0));

        verify(vehiculoService).registrarVehiculo(any(VehiculoDTO.class));
    }

    /**
     * Test para listar todos los vehículos.
     * Endpoint: GET /vehiculos
     */
    @Test
    void testListarTodosVehiculos() throws Exception {
        List<VehiculoDTO> lista = List.of(vehiculoDTO);
        when(vehiculoService.listarVehiculos()).thenReturn(lista);

        mockMvc.perform(get("/vehiculos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].placa").value("ABC123"))
                .andExpect(jsonPath("$[0].tipo").value("RESIDENTE"))
                .andExpect(jsonPath("$[0].tiempoAcumulado").value(0));

        verify(vehiculoService).listarVehiculos();
    }

    /**
     * Test para listar vehículos por tipo.
     * Endpoint: GET /vehiculos/tipo/{tipo}
     */
    @Test
    void testListarVehiculosPorTipo() throws Exception {
        List<VehiculoDTO> lista = List.of(vehiculoDTO);
        when(vehiculoService.listarVehiculosPorTipo(eq(TipoVehiculo.RESIDENTE))).thenReturn(lista);

        mockMvc.perform(get("/vehiculos/tipo/RESIDENTE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].placa").value("ABC123"))
                .andExpect(jsonPath("$[0].tipo").value("RESIDENTE"))
                .andExpect(jsonPath("$[0].tiempoAcumulado").value(0));

        verify(vehiculoService).listarVehiculosPorTipo(TipoVehiculo.RESIDENTE);
    }

    /**
     * Test para reiniciar mes de vehículos.
     * Endpoint: POST /vehiculos/mes/resetear
     */
    @Test
    @DisplayName("Comenzar nuevo mes debe devolver 204 No Content")
    void testComenzarNuevoMes() throws Exception {
    	doNothing().when(vehiculoService).comenzarNuevoMes();

        mockMvc.perform(post("/vehiculos/mes/resetear")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        verify(vehiculoService).comenzarNuevoMes();
    }

}
