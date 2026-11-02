/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra.service;

import cr.ac.una.proyectoprogra.model.Instrumento;
import cr.ac.una.proyectoprogra.model.PartePago;
import cr.ac.una.proyectoprogra.util.Request;
import cr.ac.una.proyectoprogra.util.Respuesta;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vjjcu
 */
public class PartePagoService {

    public Respuesta registrarPago(Long bufeteId, PartePago pago) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("bufeteId", bufeteId);

            Request request = new Request("Pagos/pago", parametros);
            request.post(pago);
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            Instrumento instrumento = (Instrumento) request.readEntity(Instrumento.class);
            return new Respuesta(true, "", "", "Instrumento", instrumento);
        } catch (Exception ex) {
            Logger.getLogger(PartePagoService.class.getName()).log(Level.SEVERE, "Error registrando el pago.", ex);
            return new Respuesta(false, "Error registrando el pago.", "registrarPago " + ex.getMessage());
        }
    }

    public Respuesta eliminarPago(Long bufeteId, Long id) {
        try {
            Map<String, Object> pathParams = new HashMap<>();
            pathParams.put("id", id);
            Map<String, Object> queryParams = new HashMap<>();
            queryParams.put("bufeteId", bufeteId);

            Request request = new Request("Pagos", "/{id}", pathParams, queryParams);
            request.delete();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            return new Respuesta(true, "", "");
        } catch (Exception ex) {
            Logger.getLogger(PartePagoService.class.getName()).log(Level.SEVERE, "Error eliminando el pago.", ex);
            return new Respuesta(false, "Error eliminando el pago.", "eliminarPago " + ex.getMessage());
        }
    }
}