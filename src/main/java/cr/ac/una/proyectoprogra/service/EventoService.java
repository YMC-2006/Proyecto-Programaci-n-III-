/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra.service;

import cr.ac.una.proyectoprogra.model.Evento;
import cr.ac.una.proyectoprogra.util.Mensaje;
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
public class EventoService {

    public Respuesta getEvento(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("Eventos", "/{id}", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            Evento evento = (Evento) request.readEntity(Evento.class);
            return new Respuesta(true, "", "", "Evento", evento);

        } catch (Exception ex) {
            Logger.getLogger(EventoService.class.getName()).log(Level.SEVERE, "Error obteniendo el evento [" + id + "]", ex);

            return new Respuesta(false, "Error obteniendo el evento.", "getEvento " + ex.getMessage());
        }
    }

    public Respuesta getEventos(Long bufeteId, Long instrumentoId, Boolean atendido, String desde, String hasta) {

        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("bufeteId", bufeteId);
            parametros.put("instrumentoId", instrumentoId);
            parametros.put("atendido", atendido);
            parametros.put("desde", desde);
            parametros.put("hasta", hasta);

            Request request = new Request("Eventos", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            List<Evento> eventos = (List<Evento>) request.readEntity(new GenericType<List<Evento>>() {
            });
            return new Respuesta(true, "", "", "Eventos", eventos);

        } catch (Exception ex) {

            Logger.getLogger(EventoService.class.getName()).log(Level.SEVERE, "Error obteniendo eventos.", ex);
            return new Respuesta(false, "Error obteniendo eventos.", "getEventos " + ex.getMessage());

        }

    }

    public Respuesta guardarEvento(Long bufeteId, Evento evento) {
        try {

            Map<String, Object> queryParams = new HashMap<>();
            queryParams.put("bufeteId", bufeteId);
            
            Request request = new Request("Eventos/evento", queryParams);
            request.post(evento);
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            Evento eventoGuardado = (Evento) request.readEntity(Evento.class);

            return new Respuesta(true, "", "", "Evento", eventoGuardado);
        } catch (Exception ex) {
            Logger.getLogger(EventoService.class.getName()).log(Level.SEVERE, "Ocurrió un error al guardar el evento.", ex);
            return new Respuesta(false, "Ocurrió un error al guardar el evento.", "guardarEvento " + ex.getMessage());
        }
    }

    public Respuesta eliminarEvento(Long bufeteId, Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("bufeteId", bufeteId);
            parametros.put("id", id);

            Request request = new Request("Eventos", "/{id}", parametros);
            request.delete();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            return new Respuesta(true, "", "");

        } catch (Exception ex) {
            Logger.getLogger(EventoService.class.getName()).log(Level.SEVERE, "Error eliminando el evento.", ex);
            return new Respuesta(false, "Error eliminando el evento.", "eliminarEvento " + ex.getMessage());
        }
    }

}