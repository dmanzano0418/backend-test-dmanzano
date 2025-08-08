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
 * 
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Residente extends Vehiculo {
	
	private int tiempoEstacionado = 0; // en minutos

    @Override
    public TipoVehiculo getTipo() {
        return TipoVehiculo.RESIDENTE;
    }

}
