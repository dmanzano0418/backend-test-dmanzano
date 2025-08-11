/**
 * 
 */
package com.example.parking.dto.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.parking.dto.EstanciaDTO;
import com.example.parking.model.entity.Estancia;

/**
 * Mapper para transformar entre entidad {@link Estancia} y {@link EstanciaDTO}.
 * Usa MapStruct para generación automática del código.
 * 
 * @author Daniel Manzano Borja
 * @since 2025-08-08
 */
@Mapper(componentModel = "spring", config = IgnoreUnmappedMapperConfig.class)
public interface EstanciaMapper {
	
	/**
     * Convierte una entidad {@link Estancia} a su DTO {@link EstanciaDTO}.
     * Mapea placa y tipo desde la entidad Vehiculo relacionada.
     * 
     * @param estancia entidad a convertir
     * @return DTO resultante
     */
	@Mapping(source = "vehiculo.placa", target = "placa")
    @Mapping(source = "vehiculo.tipo", target = "tipo")
    @Mapping(source = "horaEntrada", target = "horaEntrada")
    @Mapping(source = "horaSalida", target = "horaSalida")
    @Mapping(source = "importePago", target = "importePago")
	EstanciaDTO toDTO(Estancia estancia);
	
	/**
     * Convierte una lista de entidades {@link Estancia} a una lista de DTOs {@link EstanciaDTO}.
     * 
     * @param estancias lista de entidades a convertir
     * @return lista de DTOs correspondientes
     */
	List<EstanciaDTO> toDTOList(List<Estancia> estancias);

	/**
     * Convierte un DTO a entidad Estancia.
     * Nota: El campo vehiculo debe asignarse manualmente en el servicio
     * tras buscar por placa, no se mapea automáticamente aquí.
     * 
     * @param dto DTO a convertir
     * @return entidad Estancia
     */
	@Mapping(source = "placa", target = "vehiculo.placa")
    @Mapping(source = "tipo", target = "vehiculo.tipo")
	@Mapping(target = "vehiculo", ignore = true)
    Estancia toEntity(EstanciaDTO dto);

}
