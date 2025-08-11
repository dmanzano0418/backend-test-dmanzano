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

import com.example.parking.model.entity.Estancia;
import com.example.parking.model.enums.TipoVehiculo;

/**
 * JPA-interface that contains the API estancia Repository.
 * 
 * @author Daniel Manzano Borja
 * @since 08-AGO-2025
 * 
 */
@Repository
public interface EstanciaRepository extends JpaRepository<Estancia, Long> {
	
	/**
     * Busca una estancia activa (sin hora de salida) para una placa específica.
     */
    @Query("SELECT e FROM Estancia e WHERE e.vehiculo.placa = :placa AND e.horaSalida IS NULL")
    Optional<Estancia> findEstanciaActivaPorPlaca(@Param("placa") String placa);
	
    /**
     * Lista todas las estancias por tipo de vehículo.
     * findByVehiculo_Tipo corresponde a e.vehiculo.tipo = :tipo en JPQL.
     */
    List<Estancia> findByVehiculo_Tipo(TipoVehiculo tipo);

    /**
     * Elimina todas las estancias asociadas a un tipo de vehículo específico.
     *
     * @param tipo tipo de vehículo (OFICIAL, RESIDENTE, NO_RESIDENTE)
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM Estancia e WHERE e.vehiculo.tipo = :tipo")
    void deleteByVehiculoTipo(@Param("tipo") TipoVehiculo tipo);

}
