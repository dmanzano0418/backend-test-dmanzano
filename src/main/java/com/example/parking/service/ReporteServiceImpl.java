/**
 * 
 */
package com.example.parking.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.parking.exception.ReporteGeneracionException;
import com.example.parking.model.entity.Vehiculo;
import com.example.parking.model.enums.TipoVehiculo;
import com.example.parking.repository.VehiculoRepository;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;

/**
 * Implementación de {@link ReporteService} que genera PDFs con iText
 *
 * Autor: Daniel Manzano Borja
 * Fecha: 2025-08-09
 */
@Service
public class ReporteServiceImpl implements ReporteService {
	
	private final VehiculoRepository vehiculoRepository;
    
    /**
	 * Constructor that injects the vehiculoRepository interface.
	 * 
	 * @param vehiculoRepository | EstanciaRepository JPA-interface.
	 * @param vehiculoRepository | VehiculoRepository JPA-interface.
	 * @param estanciaMapper    | EstanciaMapper.
	 */
    public ReporteServiceImpl(VehiculoRepository vehiculoRepository) {
    	this.vehiculoRepository = vehiculoRepository;
    }

    @Override
    public ByteArrayInputStream generarReportePagosResidentes() {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            PdfDocument pdfDoc = new PdfDocument(new PdfWriter(out));
            Document document = new Document(pdfDoc);

            document.add(new Paragraph("Reporte de Pagos de Residentes")
                    .setFont(PdfFontFactory.createFont())
//                    .setBold()
                    .setFontSize(14)
                    .setTextAlignment(TextAlignment.CENTER));

            float[] columnWidths = {200f, 200f, 200f};
            Table table = new Table(columnWidths);

            // Encabezados
            table.addHeaderCell(new Cell().add("Núm. placa").setBackgroundColor(ColorConstants.LIGHT_GRAY));
            table.addHeaderCell(new Cell().add("Tiempo estacionado (min.)").setBackgroundColor(ColorConstants.LIGHT_GRAY));
            table.addHeaderCell(new Cell().add("Cantidad a pagar (MXN)").setBackgroundColor(ColorConstants.LIGHT_GRAY));

            List<Vehiculo> residentes = vehiculoRepository.findByTipo(TipoVehiculo.RESIDENTE);

            double totalTiempo = 0;
            double totalMonto = 0;

            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("es", "MX"));

            for (Vehiculo v : residentes) {
                double tiempo = v.getTiempoAcumulado();
                double monto = tiempo * 0.05; // Ejemplo: $0.05 por minuto

                totalTiempo += tiempo;
                totalMonto += monto;

                table.addCell(v.getPlaca());
                table.addCell(String.valueOf((long) tiempo));
                table.addCell(currencyFormat.format(monto));
            }

            // Totales
            table.addCell(new Cell().add("TOTAL").setBold());
            table.addCell(String.valueOf((long) totalTiempo));
            table.addCell(currencyFormat.format(totalMonto));

            document.add(table);
            document.close();

            return new ByteArrayInputStream(out.toByteArray());

        } catch (Exception e) {
            throw new ReporteGeneracionException("No se pudo generar el reporte de pagos de residentes.", e);
        }
    }

}
