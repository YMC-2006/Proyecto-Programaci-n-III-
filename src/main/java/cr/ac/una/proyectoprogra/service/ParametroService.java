package cr.ac.una.proyectoprogra.service;

import cr.ac.una.proyectoprogra.model.Parametro;
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
public class ParametroService {

    public Respuesta getParametro(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("Parametros", "/{id}", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            Parametro parametro = (Parametro) request.readEntity(Parametro.class);
            return new Respuesta(true, "", "", "Parametro", parametro);

        } catch (Exception ex) {
            Logger.getLogger(ParametroService.class.getName()).log(Level.SEVERE, "Error obteniendo el parámetro [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo el parámetro.", "getParametro " + ex.getMessage());
        }
    }

    public Respuesta getParametros(Long bufeteId) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            // tiene que coincidir con el queryparam del controller del WS
            parametros.put("bufeteId", bufeteId);

            Request request = new Request("Parametros", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            List<Parametro> lista = (List<Parametro>) request.readEntity(new GenericType<List<Parametro>>() {});
            return new Respuesta(true, "", "", "Parametros", lista);

        } catch (Exception ex) {
            Logger.getLogger(ParametroService.class.getName()).log(Level.SEVERE, "Error obteniendo los parámetros.", ex);
            return new Respuesta(false, "Error obteniendo los parámetros.", "getParametros " + ex.getMessage());
        }
    }

    public Respuesta guardarParametro(Parametro parametro) {
        try {
            Request request = new Request("Parametros/parametro");
            request.post(parametro);

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            Parametro parametroGuardado = (Parametro) request.readEntity(Parametro.class);
            return new Respuesta(true, "", "", "Parametro", parametroGuardado);

        } catch (Exception ex) {
            Logger.getLogger(ParametroService.class.getName()).log(Level.SEVERE, "Ocurrió un error al guardar el parámetro.", ex);
            return new Respuesta(false, "Ocurrió un error al guardar el parámetro.", "guardarParametro " + ex.getMessage());
        }
    }

    public Respuesta eliminarParametro(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);

            // construye /Parametros/15 (si id es 15)
            Request request = new Request("Parametros", "/{id}", parametros);
            request.delete();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            return new Respuesta(true, "", "");

        } catch (Exception ex) {
            Logger.getLogger(ParametroService.class.getName()).log(Level.SEVERE, "Error eliminando el parámetro.", ex);
            return new Respuesta(false, "Error eliminando el parámetro.", "eliminarParametro " + ex.getMessage());
        }
    }
}