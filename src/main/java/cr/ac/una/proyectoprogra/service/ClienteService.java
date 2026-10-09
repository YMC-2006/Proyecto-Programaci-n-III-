/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra.service;

import cr.ac.una.proyectoprogra.model.Cliente;
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
 * @author alond
 */
public class ClienteService {
    
    public Respuesta getCliente(Long id) {
        
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("Clientes", "/{id}", parametros);
            request.get();
 
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
 
            Cliente cliente = (Cliente) request.readEntity(Cliente.class);
            return new Respuesta(true,"","","Cliente",cliente);
 
        } catch (Exception ex) {
            
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, "Error obteniendo el cliente [" + id + "]", ex);
            return new Respuesta(false,"Error obteniendo el cliente.","getCliente " + ex.getMessage());
        }
    }
 
    public Respuesta getClientes(String nombre,String apellido,String identificacion,String celular,String correo,Long bufeteId) {
 
        try {
 
            Map<String, Object> parametros = new HashMap<>();
 
            parametros.put("nombre", nombre);
            parametros.put("apellido", apellido);
            parametros.put("identificacion", identificacion);
            parametros.put("celular", celular);
            parametros.put("correo", correo);
            parametros.put("bufeteId", bufeteId);
 
            Request request = new Request("Clientes",parametros);
 
            request.get();
 
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
 
            List<Cliente> clientes =(List<Cliente>) request.readEntity(new GenericType<List<Cliente>>() {});
 
            return new Respuesta(true,"","","Clientes",clientes);
 
        } catch (Exception ex) {
 
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, "Error obteniendo clientes.", ex);
 
            return new Respuesta(false,"Error obteniendo clientes.","getClientes " + ex.getMessage());
        }
    }
 
    public Respuesta guardarCliente(Cliente cliente) {
        try {
            Request request = new Request("Clientes/cliente");
            request.post(cliente);
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
 
            Cliente clienteGuardado = (Cliente) request.readEntity(Cliente.class);
 
            return new Respuesta(true,"","","Cliente",clienteGuardado);
        } catch (Exception ex) {
            
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE,"Ocurrió un error al guardar el cliente.",ex);
            return new Respuesta(false,"Ocurrió un error al guardar el cliente.","guardarCliente " + ex.getMessage());
        }
    }
 
    public Respuesta eliminarCliente(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
 
            Request request = new Request("Clientes", "/{id}", parametros);
            request.delete();
 
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
 
            return new Respuesta(true, "", "");
 
        } catch (Exception ex) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE,"Error eliminando el cliente.",ex);
            return new Respuesta(false,"Error eliminando el cliente.","eliminarCliente " + ex.getMessage());
        }
    }
}
 