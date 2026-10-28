/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra.service;

import cr.ac.una.proyectoprogra.model.MovimientoDto;
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
public class MovimientoService {
    public Respuesta getMovimiento(Long id){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("Movimientos", "/{id}", parametros);
            request.get();

            if(request.isError()){
                return new Respuesta(false, request.getError(), "");
            }

            MovimientoDto movimientoDto = (MovimientoDto) request.readEntity(MovimientoDto.class);
            return new Respuesta(true, "", "", "Movimiento", movimientoDto);
        }catch(Exception ex){
            Logger.getLogger(SociedadService.class.getName()).log(Level.SEVERE, "Error obteniendo el movimiento: "+id, ex);
            return new Respuesta(false, "Error obteniendo el movimiento", "getMovimiento" + ex.getMessage());
        }
    }
    
    public Respuesta getMovimientos(Long bufeteId){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("bufeteId", bufeteId);
            Request request = new Request("Movimientos", parametros);
            request.get();

            if(request.isError()){
                return new Respuesta(false, request.getError(), "");
            }

            List<MovimientoDto> movimientos = (List<MovimientoDto>) request.readEntity(new GenericType<List<MovimientoDto>>(){});
            return new Respuesta(true, "", "", "Movimientos", movimientos);
        }catch(Exception ex){
            Logger.getLogger(SociedadService.class.getName()).log(Level.SEVERE, "Error obteniendo el movimientos", ex);
            return new Respuesta(false, "Error obteniendo el movimientos", "getMovimientos" + ex.getMessage());
        }
    }
    
    public Respuesta guardarMovimiento(MovimientoDto movDto){
        try{
            Request request = new Request("Movimientos/movimiento");
            request.post(movDto);

            if(request.isError()){
                return new Respuesta(false, request.getError(), "");
            }

            MovimientoDto sociedadDto = (MovimientoDto) request.readEntity(MovimientoDto.class);
            return new Respuesta(true, "", "", "Movimiento", sociedadDto);
        }catch(Exception ex){
            Logger.getLogger(SociedadService.class.getName()).log(Level.SEVERE, "Error guardando el movimiento", ex);
            return new Respuesta(false, "Error guardando el movimiento", "guardarMovimiento" + ex.getMessage());
        }
    }
    
    public Respuesta eliminarMovimiento(Long id){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("Movimiento", "/{id}", parametros);
            request.delete();

            if(request.isError()){
                return new Respuesta(false, request.getError(), "");
            }

            return new Respuesta(true, "", "");
        }catch(Exception ex){
            Logger.getLogger(SociedadService.class.getName()).log(Level.SEVERE, "Error eliminando el movimiento", ex);
            return new Respuesta(false, "Error eliminando el movimiento", "eliminarMovimiento" + ex.getMessage());
        }
    }
}
