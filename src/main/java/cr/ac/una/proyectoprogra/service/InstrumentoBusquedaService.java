/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra.service;

import cr.ac.una.proyectoprogra.model.BusquedaInstrumento;
import cr.ac.una.proyectoprogra.model.ResultadoInstrumento;
import cr.ac.una.proyectoprogra.util.Request;
import cr.ac.una.proyectoprogra.util.Respuesta;
import jakarta.ws.rs.core.GenericType;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author alond
 */
public class InstrumentoBusquedaService {
    
    public Respuesta buscarInstrumentos(Long bufeteId, List<BusquedaInstrumento> filtros) {
        try {
            Request request = new Request("BusquedaInstrumentos?bufeteId=" + bufeteId);
            request.post(filtros);
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            List<ResultadoInstrumento> resultados = (List<ResultadoInstrumento>) request.readEntity(new GenericType<List<ResultadoInstrumento>>() {});
            return new Respuesta(true, "", "", "Resultados", resultados);
        } catch (Exception ex) {
            Logger.getLogger(InstrumentoBusquedaService.class.getName()).log(Level.SEVERE, "Error buscando los instrumentos.", ex);
            return new Respuesta(false, "Error buscando los instrumentos.", "buscarInstrumentos " + ex.getMessage());
        }
    }
    
    public Respuesta generarExcel(Long bufeteId, List<BusquedaInstrumento> filtros) {
    try {
        Request request = new Request("BusquedaInstrumentos/excel?bufeteId=" + bufeteId);
        request.setHeader("Accept", "application/octet-stream");
        request.post(filtros);
        if (request.isError()) {
            return new Respuesta(false, request.getError(), "");
        }
        byte[] excel = (byte[]) request.readEntity(byte[].class);
        return new Respuesta(true, "", "", "Excel", excel);
    } catch (Exception ex) {
        Logger.getLogger(InstrumentoBusquedaService.class.getName()).log(Level.SEVERE, "Error generando el Excel.", ex);
        return new Respuesta(false, "Error generando el Excel.", "generarExcel " + ex.getMessage());
    }
}
    
}
