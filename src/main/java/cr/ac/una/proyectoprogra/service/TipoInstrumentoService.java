/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra.service;

import cr.ac.una.proyectoprogra.model.Comodin;
import cr.ac.una.proyectoprogra.model.MachoteDto;
import cr.ac.una.proyectoprogra.model.TipoInstrumento;
import cr.ac.una.proyectoprogra.util.Request;
import cr.ac.una.proyectoprogra.util.Respuesta;
import jakarta.ws.rs.core.GenericType;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vjjcu
 */
public class TipoInstrumentoService {

    public Respuesta getTipoInstrumento(Long id) {

        try {

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("TipoInstrumentos", "/{id}", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            TipoInstrumento tipoInstrumento = (TipoInstrumento) request.readEntity(TipoInstrumento.class);
            return new Respuesta(true, "", "", "TipoInstrumento", tipoInstrumento);

        } catch (Exception ex) {
            Logger.getLogger(TipoInstrumento.class.getName()).log(Level.SEVERE, "Error obteniendo el tipoInstrumento [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo el tipoInstrumento", "getTipoInstrumento" + ex.getMessage());
        }

    }

    public Respuesta getTipoInstrumentos(String nombre, Boolean activo, Long bufeteId) {
        try {

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("nombre", nombre);
            parametros.put("activo", activo);
            parametros.put("bufeteId", bufeteId);
            //aqui los parametros que tienen que coincidir con los queryparam del controllador TipoInstrumento del WS

            Request request = new Request("TipoInstrumentos", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            List<TipoInstrumento> tipoInstrumentos = (List<TipoInstrumento>) request.readEntity(new GenericType<List<TipoInstrumento>>() {
            });

            return new Respuesta(true, "", "", "TipoInstrumentos", tipoInstrumentos);
        } catch (Exception ex) {
            Logger.getLogger(TipoInstrumento.class.getName()).log(Level.SEVERE, "Error obteniendo tipoInstrumentos", ex);
            return new Respuesta(false, "Error obteniendo lista tipoInstrumentos", "getTipoInstrumentos" + ex.getMessage());

        }
    }

    public Respuesta guardarTipoInstrumento(TipoInstrumento tipoInstrumento) {
        try {
            Request request = new Request("TipoInstrumentos/tipoInstrumento");
            request.post(tipoInstrumento);
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            TipoInstrumento tipoInstrumentoGuardado = (TipoInstrumento) request.readEntity(TipoInstrumento.class);

            return new Respuesta(true, "", "", "TipoInstrumentos", tipoInstrumentoGuardado);
        } catch (Exception ex) {
            Logger.getLogger(TipoInstrumentoService.class.getName()).log(Level.SEVERE, "Ocurrió un error al guardar el tipoInstrumento.", ex);
            return new Respuesta(false, "Ocurrió un error al guardar el tipoInstrumento.", "guardarTipoInstrumento " + ex.getMessage());
        }
    }

    public Respuesta eliminarTipoInstrumento(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);

            Request request = new Request("TipoInstrumentos", "/{id}", parametros);
            request.delete();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            return new Respuesta(true, "", "");

        } catch (Exception ex) {
            Logger.getLogger(TipoInstrumentoService.class.getName()).log(Level.SEVERE, "Error eliminando el tipoInstrumento.", ex);
            return new Respuesta(false, "Error eliminando el tipoInstrumento.", "eliminarTipoInstrumento " + ex.getMessage());
        }

    }

    public Respuesta subirMachote(Long tinId, String nombreArchivo, byte[] contenido) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", tinId);
            Request request = new Request("TipoInstrumentos", "/{id}/machote", parametros);

            // el archivo viaja como texto Base64 dentro del JSON
            String contenidoBase64 = Base64.getEncoder().encodeToString(contenido);
            request.post(new MachoteDto(nombreArchivo, contenidoBase64));

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            TipoInstrumento tipoActualizado = (TipoInstrumento) request.readEntity(TipoInstrumento.class);
            return new Respuesta(true, "", "", "TipoInstrumento", tipoActualizado);

        } catch (Exception ex) {
            Logger.getLogger(TipoInstrumentoService.class.getName()).log(Level.SEVERE, "Error subiendo el machote.", ex);
            return new Respuesta(false, "Error subiendo el machote.", "subirMachote " + ex.getMessage());
        }
    }

    public Respuesta getComodines(Long tinId) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", tinId);
            Request request = new Request("TipoInstrumentos", "/{id}/comodines", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            List<Comodin> comodines = (List<Comodin>) request.readEntity(new GenericType<List<Comodin>>() {});
            return new Respuesta(true, "", "", "Comodines", comodines);

        } catch (Exception ex) {
            Logger.getLogger(TipoInstrumentoService.class.getName()).log(Level.SEVERE, "Error obteniendo los comodines.", ex);
            return new Respuesta(false, "Error obteniendo los comodines.", "getComodines " + ex.getMessage());
        }
    }

    public Respuesta getComodinesDisponibles() {
    try {
        Request request = new Request("TipoInstrumentos/comodines");
        request.get();

        if (request.isError()) {
            return new Respuesta(false, request.getError(), "");
        }

        List<Comodin> comodines = (List<Comodin>) request.readEntity(new GenericType<List<Comodin>>() {});
        return new Respuesta(true, "", "", "Comodines", comodines);

    } catch (Exception ex) {
        Logger.getLogger(TipoInstrumentoService.class.getName()).log(Level.SEVERE, "Error obteniendo los comodines disponibles.", ex);
        return new Respuesta(false, "Error obteniendo los comodines disponibles.", "getComodinesDisponibles " + ex.getMessage());
    }
}

}
