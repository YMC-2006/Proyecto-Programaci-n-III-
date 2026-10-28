/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra.service;

import cr.ac.una.proyectoprogra.model.ArchivoDto;
import cr.ac.una.proyectoprogra.util.Request;
import cr.ac.una.proyectoprogra.util.Respuesta;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vjjcu
 */
public class ReporteService {
    
    public Respuesta getReportePrueba(Long abogadoId) {
    try {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("abogadoId", abogadoId);
        Request request = new Request("Reportes", "/prueba/{abogadoId}", parametros);
        request.get();

        if (request.isError()) {
            return new Respuesta(false, request.getError(), "");
        }

        ArchivoDto archivo = (ArchivoDto) request.readEntity(ArchivoDto.class);
        return new Respuesta(true, "", "", "Reporte", archivo);

    } catch (Exception ex) {
        Logger.getLogger(ReporteService.class.getName()).log(Level.SEVERE, "Error obteniendo el reporte.", ex);
        return new Respuesta(false, "Error obteniendo el reporte.", "getReportePrueba " + ex.getMessage());
    }
    
  
}

    
    public Respuesta generarReporteInstrumento(){
         return new Respuesta();
    }
    
    public Respuesta generarReporteCliente(){
         return new Respuesta();
    }
    
    
    //incluir indicadores como ingresos mensuales por instrumentos y varios, gastos mensuales x instrumentos y varios
    // ademas de un promedio historico de esos datos usar graficos para este reporte
    public Respuesta generarReporteResumenContable(){
        return new Respuesta();
    }
    
    // TO-DO preguntarle al profe sobre el tercer reporte, presentar idea
    
}
