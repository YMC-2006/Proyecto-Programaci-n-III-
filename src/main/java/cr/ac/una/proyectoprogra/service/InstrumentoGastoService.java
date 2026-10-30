/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra.service;

import cr.ac.una.proyectoprogra.model.InstrumentoGasto;
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
public class InstrumentoGastoService {

    public Respuesta getGastos(Long bufeteId, Long instrumentoId, String desde, String hasta) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("bufeteId", bufeteId);
            parametros.put("instrumentoId", instrumentoId);
            parametros.put("desde", desde);
            parametros.put("hasta", hasta);

            Request request = new Request("InstrumentosGastos", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            List<InstrumentoGasto> instrumentos = (List<InstrumentoGasto>) request.readEntity(new GenericType<List<InstrumentoGasto>>() {
            });

            return new Respuesta(true, "", "", "InstrumentosGastos", instrumentos);
        } catch (Exception ex) {
            Logger.getLogger(InstrumentoGasto.class.getName()).log(Level.SEVERE, "Error obteniendo instrumentoGasto", ex);
            return new Respuesta(false, "Error obteniendo lista instrumentosGastos", "getInstrumentosGastos" + ex.getMessage());

        }
    }

    public Respuesta guardarGasto(Long bufeteId, InstrumentoGasto gasto) {
        try {

            Map<String, Object> queryParams = new HashMap<>();
            queryParams.put("bufeteId", bufeteId);

            Request request = new Request("InstrumentosGastos/gasto", queryParams);
            request.post(gasto);
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            InstrumentoGasto instrumentoGastoGuardado = (InstrumentoGasto) request.readEntity(InstrumentoGasto.class);

            return new Respuesta(true, "", "", "InstrumentosGastos", instrumentoGastoGuardado);
        } catch (Exception ex) {
            Logger.getLogger(InstrumentoGasto.class.getName()).log(Level.SEVERE, "Ocurrió un error al guardar el gasto del instrumento.", ex);
            return new Respuesta(false, "Ocurrió un error al guardar el gasto del instrumento.", "guardarGasto " + ex.getMessage());
        }
    }

    public Respuesta eliminarGasto(Long bufeteId, Long id) {
        try {
            Map<String, Object> pathParams = new HashMap<>();
            pathParams.put("id", id);
            Map<String, Object> queryParams = new HashMap<>();
            queryParams.put("bufeteId", bufeteId);

            Request request = new Request("InstrumentosGastos", "/{id}", pathParams, queryParams); // constructor de 4 parametros del request justo para cuando hay que usar paht y query params :)
            request.delete();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            return new Respuesta(true, "", "");

        } catch (Exception ex) {
            Logger.getLogger(InstrumentoGastoService.class.getName()).log(Level.SEVERE, "Error eliminando el instrumentoGasto.", ex);
            return new Respuesta(false, "Error eliminando el instrumentoGasto.", "eliminarInstrumentoGasto " + ex.getMessage());
        }
    }

}
