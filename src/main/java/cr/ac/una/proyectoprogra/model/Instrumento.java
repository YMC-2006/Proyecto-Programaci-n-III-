/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vjjcu
 */
public class Instrumento {
    
    private Long insId;
    private LocalDateTime insFechaEstado;
    private String insObsEstado;
    private String insDetalle;
    private Boolean insIndiceNotarial = Boolean.FALSE;
    private LocalDateTime insFechaHora;
    private String insBoleta;
    private String insTomo;
    private String insEscritura;
    private String insFolio;
    private String insTomoAsiento;
    private String insObservaciones;
    
    private String insRutaDocumento;
    private String insRutaDocFirmado; 
    private Boolean insTributa = Boolean.FALSE;
    
    private String insNumeroRecibo;
    private BigDecimal insMontoReal = BigDecimal.ZERO; // ? lo dejo como big decimal o mejor lo cambio?
    private BigDecimal insMontoFacturado = BigDecimal.ZERO;
    private Long insBufeteId;
    private Long insAbogadoId;
    private Long insTipoId;
    private Long insEstadoId;
    
    
    private List<InstrumentoParte> instrumentoParteList = new ArrayList<>();
    private List<InstrumentoParte> instrumentoParteEliminadosList = new ArrayList<>();
    private List<InstrumentoRequisito> instrumentoRequisitoList = new ArrayList<>();
    private List<Evento> eventoList = new ArrayList<>();
    
    private BigDecimal insTotalPartes = BigDecimal.ZERO;
    private BigDecimal insTotalPagado = BigDecimal.ZERO;
    private BigDecimal insSaldo = BigDecimal.ZERO;
    
    private String insAbogadoNombre;
    private String insTipoNombre;
    private String insEstadoDescripcion;  
    private Boolean insEstadoFinal = Boolean.FALSE;
    
    public Instrumento(){
        
    }
    
    
    
}
