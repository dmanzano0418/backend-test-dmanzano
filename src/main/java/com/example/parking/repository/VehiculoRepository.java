/**
 * 
 */
package com.example.parking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.parking.model.entity.Vehiculo;

/**
 * JPA-interface that contains the API vehiculo Repository.
 * 
 * @author Daniel Manzano Borja
 * @since 07-AGO-2025
 * 
 */
@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, String> {
	
	boolean existsByNumeroPlaca(String numeroPlaca);

}
