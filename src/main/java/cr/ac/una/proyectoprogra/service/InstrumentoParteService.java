/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra.service;

import cr.ac.una.proyectoprogra.model.InstrumentoParte;
import cr.ac.una.proyectoprogra.util.Request;
import cr.ac.una.proyectoprogra.util.Respuesta;
import jakarta.ws.rs.core.GenericType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vjjcu
 */
public class InstrumentoParteService {

    public Respuesta getPartes(Long bufeteId, Long instrumentoId) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("bufeteId", bufeteId);
            parametros.put("instrumentoId", instrumentoId);

            Request request = new Request("InstrumentoPartes", parametros);
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            List<InstrumentoParte> partes = (List<InstrumentoParte>) request.readEntity(new GenericType<List<InstrumentoParte>>() {});
            return new Respuesta(true, "", "", "Partes", partes);
        } catch (Exception ex) {
            Logger.getLogger(InstrumentoParteService.class.getName()).log(Level.SEVERE, "Error obteniendo las partes.", ex);
            return new Respuesta(false, "Error obteniendo las partes.", "getPartes " + ex.getMessage());
        }
    }

    public Respuesta guardarParte(Long bufeteId, InstrumentoParte parte) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("bufeteId", bufeteId);

            Request request = new Request("InstrumentoPartes/parte", parametros);
            request.post(parte);
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            InstrumentoParte guardada = (InstrumentoParte) request.readEntity(InstrumentoParte.class);
            return new Respuesta(true, "", "", "Parte", guardada);
        } catch (Exception ex) {
            Logger.getLogger(InstrumentoParteService.class.getName()).log(Level.SEVERE, "Error guardando la parte.", ex);
            return new Respuesta(false, "Error guardando la parte.", "guardarParte " + ex.getMessage());
        }
    }

    public Respuesta eliminarParte(Long bufeteId, Long id) {
        try {
            Map<String, Object> pathParams = new HashMap<>();
            pathParams.put("id", id);
            Map<String, Object> queryParams = new HashMap<>();
            queryParams.put("bufeteId", bufeteId);

            Request request = new Request("InstrumentoPartes", "/{id}", pathParams, queryParams);
            request.delete();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            return new Respuesta(true, "", "");
        } catch (Exception ex) {
            Logger.getLogger(InstrumentoParteService.class.getName()).log(Level.SEVERE, "Error eliminando la parte.", ex);
            return new Respuesta(false, "Error eliminando la parte.", "eliminarParte " + ex.getMessage());
        }
    }
}