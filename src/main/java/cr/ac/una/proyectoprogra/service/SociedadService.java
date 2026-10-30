/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra.service;

import cr.ac.una.proyectoprogra.model.SociedadDto;
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
 * @author Nikole
 */
public class SociedadService {
    public Respuesta getSociedad(Long id){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("Sociedades", "/{id}", parametros);
            request.get();

            if(request.isError()){
                return new Respuesta(false, request.getError(), "");
            }

            SociedadDto sociedadDto = (SociedadDto) request.readEntity(SociedadDto.class);
            return new Respuesta(true, "", "", "Sociedad", sociedadDto);
        }catch(Exception ex){
            Logger.getLogger(SociedadService.class.getName()).log(Level.SEVERE, "Error obteniendo la sociedad: "+id, ex);
            return new Respuesta(false, "Error obteniendo la sociedad", "getSociedad" + ex.getMessage());
        }
    }
    
    public Respuesta getSociedades(Long bufeteId, String nombre, String cedula, String domicilio, String tipoAcciones){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("bufeteId", bufeteId);
            parametros.put("nombre", nombre);
            parametros.put("cedula", cedula);
            parametros.put("domicilio", domicilio);
            parametros.put("tipoAcciones", tipoAcciones);
            Request request = new Request("Sociedades", parametros);
            request.get();

            if(request.isError()){
                return new Respuesta(false, request.getError(), "");
            }

            List<SociedadDto> sociedadesDto = (List<SociedadDto>) request.readEntity(new GenericType<List<SociedadDto>>(){});
            return new Respuesta(true, "", "", "Sociedades", sociedadesDto);
        }catch(Exception ex){
            Logger.getLogger(SociedadService.class.getName()).log(Level.SEVERE, "Error obteniendo la sociedades", ex);
            return new Respuesta(false, "Error obteniendo las sociedades", "getSociedades" + ex.getMessage());
        }
    }
    
    public Respuesta guardarSociedad(SociedadDto socDto){
        try{
            Request request = new Request("Sociedades/sociedad");
            request.post(socDto);

            if(request.isError()){
                return new Respuesta(false, request.getError(), "");
            }

            SociedadDto sociedadDto = (SociedadDto) request.readEntity(SociedadDto.class);
            return new Respuesta(true, "", "", "Sociedad", sociedadDto);
        }catch(Exception ex){
            Logger.getLogger(SociedadService.class.getName()).log(Level.SEVERE, "Error guardando la sociedad", ex);
            return new Respuesta(false, "Error guardando la sociedad", "guardarSociedad" + ex.getMessage());
        }
    }
    
    public Respuesta eliminarSociedad(Long id){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("Sociedades", "/{id}", parametros);
            request.delete();

            if(request.isError()){
                return new Respuesta(false, request.getError(), "");
            }

            return new Respuesta(true, "", "");
        }catch(Exception ex){
            Logger.getLogger(SociedadService.class.getName()).log(Level.SEVERE, "Error eliminando la sociedad", ex);
            return new Respuesta(false, "Error eliminando la sociedad", "eliminarSociedad" + ex.getMessage());
        }
    }
}
