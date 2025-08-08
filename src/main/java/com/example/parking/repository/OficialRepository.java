/**
 * 
 */
package com.example.parking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.parking.model.entity.Oficial;

/**
 * JPA-interface that contains the API oficial Repository.
 * 
 * @author Daniel Manzano Borja
 * @since 07-AGO-2025
 * 
 */
@Repository
public interface OficialRepository extends JpaRepository<Oficial, String> {

}
