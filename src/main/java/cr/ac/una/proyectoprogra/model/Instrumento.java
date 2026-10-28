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
    
    // para la parte contable si un instrumento tributa generamos el numero de recibo
    private String insRutaDocumento;
    private String insRutaDocFirmado; 
    private Boolean insTributa = Boolean.FALSE;
    private String insNumeroRecibo;
    
    private BigDecimal insMontoReal = BigDecimal.ZERO; 
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

    public Instrumento(Long insId, LocalDateTime insFechaEstado, String insObsEstado, String insDetalle, LocalDateTime insFechaHora, String insBoleta, String insTomo, String insEscritura, String insFolio, String insTomoAsiento, String insObservaciones, String insRutaDocumento, String insRutaDocFirmado, String insNumeroRecibo, Long insBufeteId, Long insAbogadoId, Long insTipoId, Long insEstadoId, String insAbogadoNombre, String insTipoNombre, String insEstadoDescripcion) {
        this.insId = insId;
        this.insFechaEstado = insFechaEstado;
        this.insObsEstado = insObsEstado;
        this.insDetalle = insDetalle;
        this.insFechaHora = insFechaHora;
        this.insBoleta = insBoleta;
        this.insTomo = insTomo;
        this.insEscritura = insEscritura;
        this.insFolio = insFolio;
        this.insTomoAsiento = insTomoAsiento;
        this.insObservaciones = insObservaciones;
        this.insRutaDocumento = insRutaDocumento;
        this.insRutaDocFirmado = insRutaDocFirmado;
        this.insNumeroRecibo = insNumeroRecibo;
        this.insBufeteId = insBufeteId;
        this.insAbogadoId = insAbogadoId;
        this.insTipoId = insTipoId;
        this.insEstadoId = insEstadoId;
        this.insAbogadoNombre = insAbogadoNombre;
        this.insTipoNombre = insTipoNombre;
        this.insEstadoDescripcion = insEstadoDescripcion;
    }
    
    public Instrumento(){
        
    }

    public Long getInsId() {
        return insId;
    }

    public void setInsId(Long insId) {
        this.insId = insId;
    }

    public LocalDateTime getInsFechaEstado() {
        return insFechaEstado;
    }

    public void setInsFechaEstado(LocalDateTime insFechaEstado) {
        this.insFechaEstado = insFechaEstado;
    }

    public String getInsObsEstado() {
        return insObsEstado;
    }

    public void setInsObsEstado(String insObsEstado) {
        this.insObsEstado = insObsEstado;
    }

    public String getInsDetalle() {
        return insDetalle;
    }

    public void setInsDetalle(String insDetalle) {
        this.insDetalle = insDetalle;
    }

    public Boolean getInsIndiceNotarial() {
        return insIndiceNotarial;
    }

    public void setInsIndiceNotarial(Boolean insIndiceNotarial) {
        this.insIndiceNotarial = insIndiceNotarial;
    }

    public LocalDateTime getInsFechaHora() {
        return insFechaHora;
    }

    public void setInsFechaHora(LocalDateTime insFechaHora) {
        this.insFechaHora = insFechaHora;
    }

    public String getInsBoleta() {
        return insBoleta;
    }

    public void setInsBoleta(String insBoleta) {
        this.insBoleta = insBoleta;
    }

    public String getInsTomo() {
        return insTomo;
    }

    public void setInsTomo(String insTomo) {
        this.insTomo = insTomo;
    }

    public String getInsEscritura() {
        return insEscritura;
    }

    public void setInsEscritura(String insEscritura) {
        this.insEscritura = insEscritura;
    }

    public String getInsFolio() {
        return insFolio;
    }

    public void setInsFolio(String insFolio) {
        this.insFolio = insFolio;
    }

    public String getInsTomoAsiento() {
        return insTomoAsiento;
    }

    public void setInsTomoAsiento(String insTomoAsiento) {
        this.insTomoAsiento = insTomoAsiento;
    }

    public String getInsObservaciones() {
        return insObservaciones;
    }

    public void setInsObservaciones(String insObservaciones) {
        this.insObservaciones = insObservaciones;
    }

    public String getInsRutaDocumento() {
        return insRutaDocumento;
    }

    public void setInsRutaDocumento(String insRutaDocumento) {
        this.insRutaDocumento = insRutaDocumento;
    }

    public String getInsRutaDocFirmado() {
        return insRutaDocFirmado;
    }

    public void setInsRutaDocFirmado(String insRutaDocFirmado) {
        this.insRutaDocFirmado = insRutaDocFirmado;
    }

    public Boolean getInsTributa() {
        return insTributa;
    }

    public void setInsTributa(Boolean insTributa) {
        this.insTributa = insTributa;
    }

    public String getInsNumeroRecibo() {
        return insNumeroRecibo;
    }

    public void setInsNumeroRecibo(String insNumeroRecibo) {
        this.insNumeroRecibo = insNumeroRecibo;
    }

    public BigDecimal getInsMontoReal() {
        return insMontoReal;
    }

    public void setInsMontoReal(BigDecimal insMontoReal) {
        this.insMontoReal = insMontoReal;
    }

    public BigDecimal getInsMontoFacturado() {
        return insMontoFacturado;
    }

    public void setInsMontoFacturado(BigDecimal insMontoFacturado) {
        this.insMontoFacturado = insMontoFacturado;
    }

    public Long getInsBufeteId() {
        return insBufeteId;
    }

    public void setInsBufeteId(Long insBufeteId) {
        this.insBufeteId = insBufeteId;
    }

    public Long getInsAbogadoId() {
        return insAbogadoId;
    }

    public void setInsAbogadoId(Long insAbogadoId) {
        this.insAbogadoId = insAbogadoId;
    }

    public Long getInsTipoId() {
        return insTipoId;
    }

    public void setInsTipoId(Long insTipoId) {
        this.insTipoId = insTipoId;
    }

    public Long getInsEstadoId() {
        return insEstadoId;
    }

    public void setInsEstadoId(Long insEstadoId) {
        this.insEstadoId = insEstadoId;
    }

    public List<InstrumentoParte> getInstrumentoParteList() {
        return instrumentoParteList;
    }

    public void setInstrumentoParteList(List<InstrumentoParte> instrumentoParteList) {
        this.instrumentoParteList = instrumentoParteList;
    }

    public List<InstrumentoParte> getInstrumentoParteEliminadosList() {
        return instrumentoParteEliminadosList;
    }

    public void setInstrumentoParteEliminadosList(List<InstrumentoParte> instrumentoParteEliminadosList) {
        this.instrumentoParteEliminadosList = instrumentoParteEliminadosList;
    }

    public List<InstrumentoRequisito> getInstrumentoRequisitoList() {
        return instrumentoRequisitoList;
    }

    public void setInstrumentoRequisitoList(List<InstrumentoRequisito> instrumentoRequisitoList) {
        this.instrumentoRequisitoList = instrumentoRequisitoList;
    }

    public List<Evento> getEventoList() {
        return eventoList;
    }

    public void setEventoList(List<Evento> eventoList) {
        this.eventoList = eventoList;
    }

    public BigDecimal getInsTotalPartes() {
        return insTotalPartes;
    }

    public void setInsTotalPartes(BigDecimal insTotalPartes) {
        this.insTotalPartes = insTotalPartes;
    }

    public BigDecimal getInsTotalPagado() {
        return insTotalPagado;
    }

    public void setInsTotalPagado(BigDecimal insTotalPagado) {
        this.insTotalPagado = insTotalPagado;
    }

    public BigDecimal getInsSaldo() {
        return insSaldo;
    }

    public void setInsSaldo(BigDecimal insSaldo) {
        this.insSaldo = insSaldo;
    }

    public String getInsAbogadoNombre() {
        return insAbogadoNombre;
    }

    public void setInsAbogadoNombre(String insAbogadoNombre) {
        this.insAbogadoNombre = insAbogadoNombre;
    }

    public String getInsTipoNombre() {
        return insTipoNombre;
    }

    public void setInsTipoNombre(String insTipoNombre) {
        this.insTipoNombre = insTipoNombre;
    }

    public String getInsEstadoDescripcion() {
        return insEstadoDescripcion;
    }

    public void setInsEstadoDescripcion(String insEstadoDescripcion) {
        this.insEstadoDescripcion = insEstadoDescripcion;
    }

    public Boolean getInsEstadoFinal() {
        return insEstadoFinal;
    }

    public void setInsEstadoFinal(Boolean insEstadoFinal) {
        this.insEstadoFinal = insEstadoFinal;
    }
    
    
    
    
  
    
}
