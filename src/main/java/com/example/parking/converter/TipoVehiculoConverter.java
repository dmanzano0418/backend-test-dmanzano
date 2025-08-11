/**
 * 
 */
package com.example.parking.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.parking.model.enums.TipoVehiculo;

/**
 * Convertidor personalizado para convertir cadenas de texto en valores del enum {@link TipoVehiculo}.
 * Realiza conversión case-insensitive y lanza IllegalArgumentException si el valor no es válido.
 * 
 * Esto permite que Spring MVC convierta automáticamente parámetros String en TipoVehiculo en los controladores.
 * 
 * @author Daniel Manzano Borja
 * @since 2025-08-10
 */
@Component
public class TipoVehiculoConverter implements Converter<String, TipoVehiculo>{
	
	/**
     * Convierte una cadena de texto en un valor {@link TipoVehiculo}.
     * La comparación es insensible a mayúsculas/minúsculas.
     *
     * @param source cadena con el nombre del tipo de vehículo
     * @return valor correspondiente del enum {@link TipoVehiculo}
     * @throws IllegalArgumentException si el valor no corresponde a ningún tipo válido
     * @author Daniel Manzano Borja
     * @since 2025-08-
     */
    @Override
    public TipoVehiculo convert(String source) {
        if (source == null || source.isBlank()) {
            throw new IllegalArgumentException("El tipo de vehículo no puede ser nulo ni vacío");
        }
        try {
            return TipoVehiculo.valueOf(source.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de vehículo inválido: " + source);
        }
    }

}
