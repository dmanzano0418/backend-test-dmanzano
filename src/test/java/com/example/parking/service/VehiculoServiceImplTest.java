/**
 * 
 */
package com.example.parking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.parking.dto.VehiculoDTO;
import com.example.parking.dto.mapper.VehiculoMapper;
import com.example.parking.exception.VehiculoYaRegistradoException;
import com.example.parking.model.entity.Vehiculo;
import com.example.parking.model.enums.TipoVehiculo;
import com.example.parking.repository.EstanciaRepository;
import com.example.parking.repository.VehiculoRepository;

/**
 * Unit tests for {@link VehiculoServiceImpl}.
 * 
 * @author Daniel Manzano
 * @since 2025-08-09
 */
@ExtendWith(MockitoExtension.class)
public class VehiculoServiceImplTest {
	
	@Mock
    private VehiculoRepository vehiculoRepository;

    @Mock
    private EstanciaRepository estanciaRepository;

    @Mock
    private VehiculoMapper vehiculoMapper;

    @InjectMocks
    private VehiculoServiceImpl vehiculoService;

    private Vehiculo vehiculoEntity;
    private VehiculoDTO vehiculoDTO;

    @BeforeEach
    void setUp() {
        vehiculoEntity = new Vehiculo();
        vehiculoEntity.setId(1L);
        vehiculoEntity.setPlaca("XYZ123");
        vehiculoEntity.setTipo(TipoVehiculo.NO_RESIDENTE);
        vehiculoEntity.setTiempoAcumulado(0L);

        vehiculoDTO = new VehiculoDTO(
            vehiculoEntity.getId(),
            vehiculoEntity.getPlaca(),
            vehiculoEntity.getTipo(),
            vehiculoEntity.getTiempoAcumulado()
        );
    }

    /**
     * Test para registrar un vehículo nuevo correctamente.
     */
    @Test
    void registrarVehiculo_ShouldSaveAndReturnDTO() {
        when(vehiculoRepository.findByPlaca(vehiculoDTO.placa())).thenReturn(Optional.empty());
        when(vehiculoMapper.toEntity(vehiculoDTO)).thenReturn(vehiculoEntity);
        when(vehiculoRepository.save(vehiculoEntity)).thenReturn(vehiculoEntity);
        when(vehiculoMapper.toDTO(vehiculoEntity)).thenReturn(vehiculoDTO);

        VehiculoDTO result = vehiculoService.registrarVehiculo(vehiculoDTO);

        assertNotNull(result);
        assertEquals("XYZ123", result.placa());
        verify(vehiculoRepository).findByPlaca(vehiculoDTO.placa());
        verify(vehiculoRepository).save(vehiculoEntity);
        verify(vehiculoMapper).toEntity(vehiculoDTO);
        verify(vehiculoMapper).toDTO(vehiculoEntity);
    }

    /**
     * Test para verificar que registrar un vehículo ya existente lanza excepción.
     */
    @Test
    void registrarVehiculo_ShouldThrowException_WhenVehiculoExists() {
        when(vehiculoRepository.findByPlaca(vehiculoDTO.placa())).thenReturn(Optional.of(vehiculoEntity));

        VehiculoYaRegistradoException ex = assertThrows(VehiculoYaRegistradoException.class, () ->
            vehiculoService.registrarVehiculo(vehiculoDTO)
        );

        assertTrue(ex.getMessage().contains("Ya existe un vehículo con la placa"));
        verify(vehiculoRepository).findByPlaca(vehiculoDTO.placa());
        verify(vehiculoRepository, never()).save(any());
    }

    /**
     * Test para listar todos los vehículos.
     */
    @Test
    void listarVehiculos_ShouldReturnDTOList() {
        when(vehiculoRepository.findAll()).thenReturn(List.of(vehiculoEntity));
        when(vehiculoMapper.toDTOList(anyList())).thenReturn(List.of(vehiculoDTO));

        List<VehiculoDTO> result = vehiculoService.listarVehiculos();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(vehiculoRepository).findAll();
        verify(vehiculoMapper).toDTOList(anyList());
    }

    /**
     * Test para listar vehículos por tipo.
     */
    @Test
    void listarVehiculosPorTipo_ShouldReturnDTOList() {
        when(vehiculoRepository.findByTipo(TipoVehiculo.NO_RESIDENTE)).thenReturn(List.of(vehiculoEntity));
        when(vehiculoMapper.toDTOList(anyList())).thenReturn(List.of(vehiculoDTO));

        List<VehiculoDTO> result = vehiculoService.listarVehiculosPorTipo(TipoVehiculo.NO_RESIDENTE);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(vehiculoRepository).findByTipo(TipoVehiculo.NO_RESIDENTE);
        verify(vehiculoMapper).toDTOList(anyList());
    }

    /**
     * Test para el método comenzarNuevoMes:
     * - Se elimina las estancias de vehículos oficiales.
     * - Se reinicia el tiempo acumulado de vehículos residentes.
     */
    @Test
    void comenzarNuevoMes_ShouldResetDatosCorrectamente() {
        doNothing().when(estanciaRepository).deleteByVehiculoTipo(TipoVehiculo.OFICIAL);
        doNothing().when(vehiculoRepository).resetTiempoAcumuladoByTipo(TipoVehiculo.RESIDENTE);

        vehiculoService.comenzarNuevoMes();

        verify(estanciaRepository, times(1)).deleteByVehiculoTipo(TipoVehiculo.OFICIAL);
        verify(vehiculoRepository, times(1)).resetTiempoAcumuladoByTipo(TipoVehiculo.RESIDENTE);
    }

}
