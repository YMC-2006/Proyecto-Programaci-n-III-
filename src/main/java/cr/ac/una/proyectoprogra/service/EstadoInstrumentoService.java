/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra.service;

import cr.ac.una.proyectoprogra.model.EstadoInstrumento;
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
public class EstadoInstrumentoService {

    public Respuesta getEstadoInstrumento(Long id) {

        try {

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("EstadoInstrumento", "/{id}", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            EstadoInstrumento estadoInstrumento = (EstadoInstrumento) request.readEntity(EstadoInstrumento.class);
            return new Respuesta(true, "", "", "EstadoInstrumento", estadoInstrumento);

        } catch (Exception ex) {
            Logger.getLogger(EstadoInstrumento.class.getName()).log(Level.SEVERE, "Error obteniendo el estadoInstrumento [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo el estadoInstrumento", "getEstadoInstrumento" + ex.getMessage());
        }

    }

    public Respuesta getEstadoInstrumentos(Boolean activo, Boolean notifica, Boolean esFinal, Long bufeteId) {
        try {

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("activo", activo);
            parametros.put("notifica", notifica);
            parametros.put("esFinal", esFinal);
            parametros.put("bufeteId", bufeteId);
            //aqui los parametros que tienen que coincidir con los queryparam del controllador EstadoInstrumentoController del WS

            Request request = new Request("EstadosInstrumentos", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            List<EstadoInstrumento> estadosInstrumentos = (List<EstadoInstrumento>) request.readEntity(new GenericType<List<EstadoInstrumento>>() {
            });

             return new Respuesta(true,"","","EstadosInstrumentos",estadosInstrumentos);
        } catch (Exception ex) {
            Logger.getLogger(EstadoInstrumento.class.getName()).log(Level.SEVERE, "Error obteniendo estadosInstrumentos", ex);
            return new Respuesta(false, "Error obteniendo lista estadoInstrumento", "getEstadosInstrumentos" + ex.getMessage());

        }
    }

    // conversor?
    public Respuesta guardarEstadoInstrumento(EstadoInstrumento estadoInstrumento) {
       try {
            Request request = new Request("EstadosInstrumentos/estadoInstrumento");
            request.post(estadoInstrumento);
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            EstadoInstrumento estadoInstrumentoGuardado = (EstadoInstrumento) request.readEntity(EstadoInstrumento.class);

            return new Respuesta(true,"","","Abogado",estadoInstrumentoGuardado);
        } catch (Exception ex) {
            Logger.getLogger(AbogadoService.class.getName()).log(Level.SEVERE,"Ocurrió un error al guardar el estadoInstrumento.",ex);
            return new Respuesta(false,"Ocurrió un error al guardar el estadoInstrumento.","guardarestadoInstrumento " + ex.getMessage());
        }
    }

    public Respuesta eliminarEstadoInstrumento(Long id) {
       try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);

            // aqui hacemos una peticion al endpoint /EstadoInstrumento
            Request request = new Request("EstadoInstrumento", "/{id}", parametros);
            request.delete();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            return new Respuesta(true, "", "");

        } catch (Exception ex) {
            Logger.getLogger(AbogadoService.class.getName()).log(Level.SEVERE,"Error eliminando el estadoInstrumento.",ex);
            return new Respuesta(false,"Error eliminando el estadoInstrumento.","eliminarEstadoInstrumento " + ex.getMessage());
        }

    }
}
