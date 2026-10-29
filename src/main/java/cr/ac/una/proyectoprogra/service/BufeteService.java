/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra.service;

import cr.ac.una.proyectoprogra.model.BufeteDto;
import cr.ac.una.proyectoprogra.model.RegistroDto;
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
public class BufeteService {
    
    public Respuesta getBufetesUsuario(String usuario) {
        try {
            Request request = new Request("Bufetes/usuario/" + usuario);
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            List<BufeteDto> bufetes = (List<BufeteDto>) request.readEntity(new GenericType<List<BufeteDto>>() {});
            return new Respuesta(true, "", "", "Bufetes", bufetes);
        } catch (Exception ex) {
            Logger.getLogger(BufeteService.class.getName()).log(Level.SEVERE, "Error obteniendo los bufetes del usuario.", ex);
            return new Respuesta(false, "Error obteniendo los bufetes del usuario.", "getBufetesUsuario " + ex.getMessage());
        }
    }
    
    public Respuesta registrarBufete(RegistroDto registro) {
        try {
            Request request = new Request("Bufetes/registro");
            request.post(registro);
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            return new Respuesta(true, "", "");
        } catch (Exception ex) {
            Logger.getLogger(BufeteService.class.getName()).log(Level.SEVERE, "Error registrando el bufete.", ex);
            return new Respuesta(false, "Error registrando el bufete.", "registrarBufete " + ex.getMessage());
        }
    }
    
}
