/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra.service;

import cr.ac.una.proyectoprogra.model.CambioClaveDto;
import cr.ac.una.proyectoprogra.model.UsuarioDto;
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
    
    public Respuesta getUsuarios(Long bufeteId) {
        try {
            Request request = new Request("Usuarios?bufeteId=" + bufeteId);
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            List<UsuarioDto> usuarios = (List<UsuarioDto>) request.readEntity(new GenericType<List<UsuarioDto>>() {});
            return new Respuesta(true, "", "", "Usuarios", usuarios);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Error obteniendo los usuarios.", ex);
            return new Respuesta(false, "Error obteniendo los usuarios.", "getUsuarios " + ex.getMessage());
        }
    }

    public Respuesta desactivarUsuario(Long bufeteId, Long usrId) {
        try {
            Request request = new Request("Usuarios/" + usrId + "/desactivar?bufeteId=" + bufeteId);
            request.post(null);
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            return new Respuesta(true, "", "");
        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Error desactivando el usuario.", ex);
            return new Respuesta(false, "Error desactivando el usuario.", "desactivarUsuario " + ex.getMessage());
        }
    }
    
    public Respuesta guardarUsuario(UsuarioDto usuario) {
        try {
            Request request = new Request("Usuarios/usuario");
            request.post(usuario);
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            UsuarioDto usuarioGuardado = (UsuarioDto) request.readEntity(UsuarioDto.class);
            return new Respuesta(true, "", "", "Usuario", usuarioGuardado);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Error guardando el usuario.", ex);
            return new Respuesta(false, "Error guardando el usuario.", "guardarUsuario " + ex.getMessage());
        }
    }
    
    public Respuesta renovarToken() {
        try {
            Request request = new Request("Usuarios/renovar");
            request.getRenewal();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            String token = (String) request.readEntity(String.class);
            return new Respuesta(true, "", "", "Token", token);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Error renovando el token.", ex);
            return new Respuesta(false, "Error renovando el token.", "renovarToken " + ex.getMessage());
        }
    }
}
