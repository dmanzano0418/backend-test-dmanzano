/**
 * 
 */
package com.example.parking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.parking.dto.EstanciaDTO;
import com.example.parking.dto.mapper.EstanciaMapper;
import com.example.parking.dto.mapper.VehiculoMapper;
import com.example.parking.exception.EstanciaNoEncontradaException;
import com.example.parking.model.entity.Estancia;
import com.example.parking.model.entity.Vehiculo;
import com.example.parking.model.enums.TipoVehiculo;
import com.example.parking.repository.EstanciaRepository;
import com.example.parking.repository.VehiculoRepository;

/**
 * Unit tests for {@link EstanciaServiceImpl}.
 * 
 * @author Daniel Manzano
 * @since 2025-08-09
 */
@ExtendWith(MockitoExtension.class)
public class EstanciaServiceImplTest {
	
	@Mock
    private EstanciaRepository estanciaRepository;

    @Mock
    private VehiculoRepository vehiculoRepository;

    @Mock
    private EstanciaMapper estanciaMapper;

    @Mock
    private VehiculoMapper vehiculoMapper;

    @InjectMocks
    private EstanciaServiceImpl estanciaService;

    private Estancia estanciaEntity;
    private EstanciaDTO estanciaDTO;
    private Vehiculo vehiculoEntity;

    @BeforeEach
    void setUp() {
        vehiculoEntity = new Vehiculo();
        vehiculoEntity.setPlaca("ABC123");
        vehiculoEntity.setTipo(TipoVehiculo.RESIDENTE); // <-- Inicializamos el tipo para evitar NPE

        estanciaEntity = new Estancia();
        estanciaEntity.setId(1L);
        estanciaEntity.setVehiculo(vehiculoEntity);
        estanciaEntity.setHoraEntrada(LocalDateTime.now().minusHours(1));
        estanciaEntity.setHoraSalida(null);
        estanciaEntity.setImportePago(0.0);

        estanciaDTO = new EstanciaDTO(
            estanciaEntity.getId(),
            vehiculoEntity.getPlaca(),
            vehiculoEntity.getTipo(),
            estanciaEntity.getHoraEntrada(),
            estanciaEntity.getHoraSalida(),
            estanciaEntity.getImportePago()
        );
    }

    @Test
    void listarEstancias_ShouldReturnListOfDTO() {
        when(estanciaRepository.findAll()).thenReturn(List.of(estanciaEntity));
        when(estanciaMapper.toDTOList(anyList())).thenReturn(List.of(estanciaDTO));

        List<EstanciaDTO> result = estanciaService.listarEstancias();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(estanciaRepository).findAll();
        verify(estanciaMapper).toDTOList(anyList());
    }

    @Test
    void listarEstanciasPorTipo_ShouldReturnFilteredList() {
        when(estanciaRepository.findByVehiculo_Tipo(any())).thenReturn(List.of(estanciaEntity));
        when(estanciaMapper.toDTOList(anyList())).thenReturn(List.of(estanciaDTO));

        List<EstanciaDTO> result = estanciaService.listarEstanciasPorTipo(vehiculoEntity.getTipo());

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(estanciaRepository).findByVehiculo_Tipo(vehiculoEntity.getTipo());
        verify(estanciaMapper).toDTOList(anyList());
    }

    @Test
    void registrarSalida_ShouldUpdateEstanciaAndReturnDTO() {
        estanciaEntity.setHoraSalida(null);
        when(estanciaRepository.findEstanciaActivaPorPlaca("ABC123")).thenReturn(Optional.of(estanciaEntity));
        when(vehiculoRepository.save(any(Vehiculo.class))).thenReturn(vehiculoEntity);
        when(estanciaRepository.save(any(Estancia.class))).thenReturn(estanciaEntity);
        when(estanciaMapper.toDTO(any(Estancia.class))).thenReturn(estanciaDTO);

        EstanciaDTO result = estanciaService.registrarSalida("ABC123");

        assertNotNull(result);
        assertEquals("ABC123", result.placa());
        verify(estanciaRepository).findEstanciaActivaPorPlaca("ABC123");
        verify(vehiculoRepository).save(any(Vehiculo.class));
        verify(estanciaRepository).save(any(Estancia.class));
        verify(estanciaMapper).toDTO(any(Estancia.class));
    }

    @Test
    void registrarSalida_ShouldThrowException_WhenEstanciaNoEncontrada() {
        when(estanciaRepository.findEstanciaActivaPorPlaca("XYZ999")).thenReturn(Optional.empty());

        assertThrows(EstanciaNoEncontradaException.class, () -> estanciaService.registrarSalida("XYZ999"));
        verify(estanciaRepository).findEstanciaActivaPorPlaca("XYZ999");
    }

}
