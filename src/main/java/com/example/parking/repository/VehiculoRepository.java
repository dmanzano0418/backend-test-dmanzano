/**
 * 
 */
package com.example.parking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.parking.model.entity.Vehiculo;
import com.example.parking.model.enums.TipoVehiculo;

/**
 * Repositorio para la entidad Vehiculo.
 * 
 * @author Daniel Manzano Borja
 * @since 08-AGO-2025
 * 
 */
@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
	
	/**
     * Devuelve Optional si existe un vehículo con la placa.
     */
	Optional<Vehiculo> findByPlaca(String placa);

	/**
     * Lista todos los vehículos de un tipo específico.
     *
     * @param tipo tipo de vehículo.
     * @return lista de vehículos filtrados.
     */
    @Query("SELECT v FROM Vehiculo v WHERE v.tipo = :tipo")
    List<Vehiculo> findByTipo(TipoVehiculo tipo);
	
    
	@Modifying
	@Transactional
    @Query("UPDATE Vehiculo v SET v.tiempoAcumulado = 0 WHERE v.tipo = :tipo")
    void resetTiempoAcumuladoByTipo(@Param("tipo") TipoVehiculo tipo);

}
