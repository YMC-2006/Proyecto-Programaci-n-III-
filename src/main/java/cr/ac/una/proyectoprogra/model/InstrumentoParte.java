/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vjjcu
 */
public class InstrumentoParte {
    
    private Long ipaId;
    private String ipaRol;
    private Boolean ipaDebePagar = Boolean.FALSE;
    private BigDecimal ipaMonto = BigDecimal.ZERO;
    private List<PartePago> partePagoList = new ArrayList<>();
    private Long ipaClienteId;
    private Long ipaSociedadId;
    private Long ipaInstrumentoId;
    private String ipaNombre;
    private String ipaIdentificacion;
    private BigDecimal ipaTotalPagado = BigDecimal.ZERO;
    private BigDecimal ipaSaldo = BigDecimal.ZERO;

    public InstrumentoParte() {
    }
    
    
}
