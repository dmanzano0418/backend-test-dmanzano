/**
 * 
 */
package com.example.parking.converter;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración global de Spring MVC para el proyecto.
 * Registra convertidores personalizados para facilitar la conversión
 * de parámetros en endpoints REST.
 * 
 * @author Daniel Manzano Borja
 * @since 2025-08-10
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final TipoVehiculoConverter tipoVehiculoConverter;

    public WebConfig(TipoVehiculoConverter tipoVehiculoConverter) {
        this.tipoVehiculoConverter = tipoVehiculoConverter;
    }
    
    /**
     * Registra el convertidor personalizado para el enum {@link com.ejemplo.estacionamiento.model.enums.TipoVehiculo}.
     *
     * @param registry el registro de formateadores/converters de Spring MVC
     * @author Daniel Manzano Borja
     * @since 2025-08-10
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(tipoVehiculoConverter);
    }

}
