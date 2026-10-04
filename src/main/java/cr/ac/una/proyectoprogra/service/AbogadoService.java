package cr.ac.una.proyectoprogra.service;

import cr.ac.una.proyectoprogra.model.Abogado;
import cr.ac.una.proyectoprogra.util.Request;
import cr.ac.una.proyectoprogra.util.Respuesta;
import jakarta.ws.rs.core.GenericType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


//necesite el Request del prof carranza y sea agregaron dependencias que hay q preguntar -> json y jakarta
public class AbogadoService {

    public Respuesta getAbogado(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("Abogados", "/{id}", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            Abogado abogado = (Abogado) request.readEntity(Abogado.class);
            return new Respuesta(true,"","","Abogado",abogado);

        } catch (Exception ex) {
            Logger.getLogger(AbogadoService.class.getName()).log(Level.SEVERE, "Error obteniendo el abogado [" + id + "]", ex);

            return new Respuesta(false,"Error obteniendo el abogado.","getAbogado " + ex.getMessage());
        }
    }

    public Respuesta getAbogados(String nombre,String direccion,String cedula,Boolean propietario,Boolean notario,Long bufeteId) {

        try {

            Map<String, Object> parametros = new HashMap<>();

            // estos tiene que coincidir con los queryparam que espra el controller del WS
            parametros.put("nombre", nombre);
            parametros.put("direccion", direccion);
            parametros.put("cedula", cedula);
            parametros.put("propietario", propietario);
            parametros.put("notario", notario);
            parametros.put("bufeteId", bufeteId);

            Request request = new Request("Abogados","",parametros);

            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            List<Abogado> abogados =(List<Abogado>) request.readEntity(new GenericType<List<Abogado>>() {});

            return new Respuesta(true,"","","Abogados",abogados);

        } catch (Exception ex) {

            Logger.getLogger(AbogadoService.class.getName()).log(Level.SEVERE, "Error obteniendo abogados.", ex);

            return new Respuesta(false,"Error obteniendo abogados.","getAbogados " + ex.getMessage());
        }
    }

    public Respuesta guardarAbogado(Abogado abogado) {
        try {
            Request request = new Request("Abogados/abogado");
            request.post(abogado);
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            Abogado abogadoGuardado = (Abogado) request.readEntity(Abogado.class);

            return new Respuesta(true,"","","Abogado",abogadoGuardado);
        } catch (Exception ex) {
            Logger.getLogger(AbogadoService.class.getName()).log(Level.SEVERE,"Ocurrió un error al guardar el abogado.",ex);
            return new Respuesta(false,"Ocurrió un error al guardar el abogado.","guardarAbogado " + ex.getMessage());
        }
    }

    public Respuesta eliminarAbogado(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);

            // aqui hacemos una peticion al endpoint /Abogados
            // esto contruye algo como /Abogados/15 (si id es 15)
            Request request = new Request("Abogados", "/{id}", parametros);
            request.delete();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            return new Respuesta(true, "", "");

        } catch (Exception ex) {
            Logger.getLogger(AbogadoService.class.getName()).log(Level.SEVERE,"Error eliminando el abogado.",ex);
            return new Respuesta(false,"Error eliminando el abogado.","eliminarAbogado " + ex.getMessage());
        }
    }
}