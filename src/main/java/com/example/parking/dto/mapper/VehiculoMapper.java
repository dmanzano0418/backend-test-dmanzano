/**
 * 
 */
package com.example.parking.dto.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.parking.dto.VehiculoDTO;
import com.example.parking.model.entity.Vehiculo;

/**
 * Mapper para transformar entre entidad {@link Vehiculo} y {@link VehiculoDTO}.
 * Usa MapStruct para generación automática del código.
 * 
 * @author Daniel Manzano Borja
 * @since 2025-08-08
 */
@Mapper(componentModel = "spring", config = IgnoreUnmappedMapperConfig.class)
public interface VehiculoMapper {
	
	/**
     * Convierte una entidad {@link Vehiculo} a su DTO {@link VehiculoDTO}.
     * 
     * @param vehiculo entidad a convertir
     * @return DTO correspondiente
     */
    VehiculoDTO toDTO(Vehiculo vehiculo);

    /**
     * Convierte un DTO {@link VehiculoDTO} a su entidad {@link Vehiculo}.
     * 
     * @param dto DTO a convertir
     * @return entidad correspondiente
     */
    Vehiculo toEntity(VehiculoDTO dto);
    
    /**
     * Convierte una lista de entidades {@link Vehiculo} a una lista de DTOs {@link VehiculoDTO}.
     * 
     * @param vehiculos lista de entidades a convertir
     * @return lista de DTOs correspondientes
     */
    List<VehiculoDTO> toDTOList(List<Vehiculo> vehiculos);

}
