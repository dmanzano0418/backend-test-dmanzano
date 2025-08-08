/**
 * 
 */
package com.example.parking.model.entity;

import com.example.parking.model.enums.TipoVehiculo;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Employee class represents the data model for noresidente
 * 
 * @author Daniel Manzano Borja
 * @since 07-AGO-2025	
 *
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class NoResidente extends Vehiculo {
	
	@Override
    public TipoVehiculo getTipo() {
        return TipoVehiculo.NO_RESIDENTE;
    }

}
