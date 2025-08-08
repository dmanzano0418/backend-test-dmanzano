/**
 * 
 */
package com.example.parking.model.entity;

import java.util.List;

import com.example.parking.model.enums.TipoVehiculo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Employee class represents the data model for vehiculo
 * 
 * @author Daniel Manzano Borja
 * @since 07-AGO-2025	
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_vehiculo")
@Getter @Setter @NoArgsConstructor
public abstract class Vehiculo {
	
	@Id
    @Column(name = "numero_placa", nullable = false, unique = true)
    private String numeroPlaca;

    @OneToMany(mappedBy = "vehiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Estancia> estancias;

    public abstract TipoVehiculo getTipo();

}
