/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra.service;

import cr.ac.una.proyectoprogra.model.CambioClaveDto;
import cr.ac.una.proyectoprogra.model.UsuarioDto;
import cr.ac.una.proyectoprogra.util.Request;
import cr.ac.una.proyectoprogra.util.Respuesta;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alond
 */
public class UsuarioService {
    
    public Respuesta getUsuario(Long bufeteId, String usuario, String clave) {
        try {
            Request request = new Request("Usuarios/usuario/" + bufeteId + "/" + usuario + "/" + clave);
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            UsuarioDto usuarioDto = (UsuarioDto) request.readEntity(UsuarioDto.class);
            return new Respuesta(true, "", "", "Usuario", usuarioDto);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Error obteniendo el usuario.", ex);
            return new Respuesta(false, "Error obteniendo el usuario.", "getUsuario " + ex.getMessage());
        }
    }
    
    public Respuesta cambiarClave(CambioClaveDto cambioClave) {
        try {
            Request request = new Request("Usuarios/cambiarClave");
            request.post(cambioClave);
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            return new Respuesta(true, "", "");
        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Error cambiando la clave.", ex);
            return new Respuesta(false, "Error cambiando la clave.", "cambiarClave " + ex.getMessage());
        }
    }
    
    public Respuesta recuperarClave(Long bufeteId, String correo) {
        try {
            Request request = new Request("Usuarios/recuperar/" + bufeteId + "/" + correo);
            request.post(null);
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            return new Respuesta(true, "", "");
        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Error recuperando la clave.", ex);
            return new Respuesta(false, "Error recuperando la clave.", "recuperarClave " + ex.getMessage());
        }
    }
    
    public Respuesta reenviarActivacion(Long bufeteId, String usuario) {
        try {
            Request request = new Request("Usuarios/reenviar/" + bufeteId + "/" + usuario);
            request.post(null);
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            return new Respuesta(true, "", "");
        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Error reenviando la activación.", ex);
            return new Respuesta(false, "Error reenviando la activación.", "reenviarActivacion " + ex.getMessage());
        }
    }
    
    public Respuesta estaPendiente(Long bufeteId, String usuario) {
        try {
            Request request = new Request("Usuarios/pendiente/" + bufeteId + "/" + usuario);
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            Boolean pendiente = (Boolean) request.readEntity(Boolean.class);
            return new Respuesta(true, "", "", "Pendiente", pendiente);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Error consultando la activación.", ex);
            return new Respuesta(false, "Error consultando la activación.", "estaPendiente " + ex.getMessage());
        }
    }
}
