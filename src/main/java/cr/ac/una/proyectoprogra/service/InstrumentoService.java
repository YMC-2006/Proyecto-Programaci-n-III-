/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra.service;

import cr.ac.una.proyectoprogra.model.CambioEstado;
import cr.ac.una.proyectoprogra.model.Instrumento;
import cr.ac.una.proyectoprogra.model.InstrumentoRequisito;
import cr.ac.una.proyectoprogra.util.Request;
import cr.ac.una.proyectoprogra.util.Respuesta;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vjjcu
 */
public class InstrumentoService {

    public Respuesta getInstrumento(Long id) {

        try {

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("Instrumentos", "/{id}", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            Instrumento instrumento = (Instrumento) request.readEntity(Instrumento.class);
            return new Respuesta(true, "", "", "Instrumento", instrumento);

        } catch (Exception ex) {
            Logger.getLogger(Instrumento.class.getName()).log(Level.SEVERE, "Error obteniendo el Instrumento [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo el instrumento", "getInstrumento" + ex.getMessage());
        }

    }

    public Respuesta cambiarEstado(Long id, Long bufeteId, CambioEstado cambioEstado) {
        try {
            Map<String, Object> pathParams = new HashMap<>();
            pathParams.put("id", id);
            Map<String, Object> queryParams = new HashMap<>();
            queryParams.put("bufeteId", bufeteId);

            // el nuevo constructo de Request lo usamos justo aca porque necesitamos pasar parametros de ruta y query
            Request request = new Request("Instrumentos", "/{id}/estado", pathParams, queryParams);
            request.post(cambioEstado);

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            Instrumento instrumento = (Instrumento) request.readEntity(Instrumento.class);
            return new Respuesta(true, "", "", "Instrumento", instrumento);

        } catch (Exception ex) {
            Logger.getLogger(InstrumentoService.class.getName()).log(Level.SEVERE, "Error cambiando el estado del instrumento [" + id + "]", ex);
            return new Respuesta(false, "Error cambiando el estado del instrumento.", "cambiarEstado " + ex.getMessage());
        }
    }

    public Respuesta getInstrumentos(Long bufeteId, Long abogadoId, Long tipoId, Long estadoId) {
        try {

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("bufeteId", bufeteId);
            parametros.put("abogadoId", abogadoId);
            parametros.put("tipoId", tipoId);
            parametros.put("estadoId", estadoId);
            //aqui los parametros que tienen que coincidir con los queryparam del controllador Instrumento del WS

            Request request = new Request("Instrumentos", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            List<Instrumento> instrumentos = (List<Instrumento>) request.readEntity(new GenericType<List<Instrumento>>() {
            });

            return new Respuesta(true, "", "", "Instrumentos", instrumentos);
        } catch (Exception ex) {
            Logger.getLogger(Instrumento.class.getName()).log(Level.SEVERE, "Error obteniendo instrumento", ex);
            return new Respuesta(false, "Error obteniendo lista instrumentos", "getInstrumentos" + ex.getMessage());

        }
    }

    public Respuesta actualizarRequisitos(Long id, Long bufeteId, List<InstrumentoRequisito> requisitos) {
        try {
            Map<String, Object> pathParams = new HashMap<>();
            pathParams.put("id", id);
            Map<String, Object> queryParams = new HashMap<>();
            queryParams.put("bufeteId", bufeteId);

            Request request = new Request("Instrumentos", "/{id}/requisitos", pathParams, queryParams);
            request.post(requisitos);

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            Instrumento instrumento = (Instrumento) request.readEntity(Instrumento.class);
            return new Respuesta(true, "", "", "Instrumento", instrumento);

        } catch (Exception ex) {
            Logger.getLogger(InstrumentoService.class.getName()).log(Level.SEVERE, "Error actualizando los requisitos del instrumento [" + id + "]", ex);
            return new Respuesta(false, "Error actualizando los requisitos.", "actualizarRequisitos " + ex.getMessage());
        }
    }

    public Respuesta guardarInstrumento(Instrumento instrumento) {
        try {
            Request request = new Request("Instrumentos/instrumento");
            request.post(instrumento);
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            Instrumento instrumentoGuardado = (Instrumento) request.readEntity(Instrumento.class);

            return new Respuesta(true, "", "", "Instrumento", instrumentoGuardado);
        } catch (Exception ex) {
            Logger.getLogger(Instrumento.class.getName()).log(Level.SEVERE, "Ocurrió un error al guardar el instrumento.", ex);
            return new Respuesta(false, "Ocurrió un error al guardar el instrumento.", "guardarInstrumento " + ex.getMessage());
        }
    }

    public Respuesta eliminarInstrumento(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);

            Request request = new Request("Instrumentos", "/{id}", parametros);
            request.delete();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            return new Respuesta(true, "", "");

        } catch (Exception ex) {
            Logger.getLogger(InstrumentoService.class.getName()).log(Level.SEVERE, "Error eliminando el instrumento.", ex);
            return new Respuesta(false, "Error eliminando el instrumento.", "eliminarInstrumento " + ex.getMessage());
        }
    }
}
