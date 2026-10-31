/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra.model;

import cr.ac.una.proyectoprogra.util.AppContext;
import jakarta.json.bind.annotation.JsonbTransient;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nikole
 */
public class SociedadDto {
    private Long socId;
    private String socCedulaJuridica;
    private String socNombre;
    private String socTelefono;
    private String socCorreo;
    private String socDomicilio;
    private String socTomo;
    private String socFolio;
    private String socAsiento;
    private LocalDate socFechaInscripcion;
    private String socTipoAcciones = "C";
    private Long socCantidadTitulos;
    private BigDecimal socValorTitulo;
    private Long socBufeteId;
    
    private List<SociedadRepresentanteDto> sociedadRepresentanteList = new ArrayList<>();
    private List<SociedadRepresentanteDto> sociedadRepresentanteEliminadosList = new ArrayList<>();

    public SociedadDto() {
    }

    public SociedadDto(Long socId, String socCedulaJuridica, String socNombre, String socTelefono, String socCorreo, String socDomicilio, String socTomo, String socFolio, String socAsiento, LocalDate socFechaInscripcion, Long socCantidadTitulos, BigDecimal socValorTitulo, Long socBufeteId) {
        this.socId = socId;
        this.socCedulaJuridica = socCedulaJuridica;
        this.socNombre = socNombre;
        this.socTelefono = socTelefono;
        this.socCorreo = socCorreo;
        this.socDomicilio = socDomicilio;
        this.socTomo = socTomo;
        this.socFolio = socFolio;
        this.socAsiento = socAsiento;
        this.socFechaInscripcion = socFechaInscripcion;
        this.socCantidadTitulos = socCantidadTitulos;
        this.socValorTitulo = socValorTitulo;
        this.socBufeteId = bufeteCarga();//momentaneo
    }
    
    private Long bufeteCarga() {
        UsuarioDto usuarioSesion = (UsuarioDto) AppContext.getInstance().get("Usuario");
        return usuarioSesion.getUsrBufeteId();
    }

    public Long getSocId() {
        return socId;
    }

    public void setSocId(Long socId) {
        this.socId = socId;
    }

    public String getSocCedulaJuridica() {
        return socCedulaJuridica;
    }

    public void setSocCedulaJuridica(String socCedulaJuridica) {
        this.socCedulaJuridica = socCedulaJuridica;
    }

    public String getSocNombre() {
        return socNombre;
    }

    public void setSocNombre(String socNombre) {
        this.socNombre = socNombre;
    }

    public String getSocTelefono() {
        return socTelefono;
    }

    public void setSocTelefono(String socTelefono) {
        this.socTelefono = socTelefono;
    }

    public String getSocCorreo() {
        return socCorreo;
    }

    public void setSocCorreo(String socCorreo) {
        this.socCorreo = socCorreo;
    }

    public String getSocDomicilio() {
        return socDomicilio;
    }

    public void setSocDomicilio(String socDomicilio) {
        this.socDomicilio = socDomicilio;
    }

    public String getSocTomo() {
        return socTomo;
    }

    public void setSocTomo(String socTomo) {
        this.socTomo = socTomo;
    }

    public String getSocFolio() {
        return socFolio;
    }

    public void setSocFolio(String socFolio) {
        this.socFolio = socFolio;
    }

    public String getSocAsiento() {
        return socAsiento;
    }

    public void setSocAsiento(String socAsiento) {
        this.socAsiento = socAsiento;
    }

    public LocalDate getSocFechaInscripcion() {
        return socFechaInscripcion;
    }

    public void setSocFechaInscripcion(LocalDate socFechaInscripcion) {
        this.socFechaInscripcion = socFechaInscripcion;
    }

    public String getSocTipoAcciones() {
        return socTipoAcciones;
    }

    public void setSocTipoAcciones(String socTipoAcciones) {
        this.socTipoAcciones = socTipoAcciones;
    }

    public Long getSocCantidadTitulos() {
        return socCantidadTitulos;
    }

    public void setSocCantidadTitulos(Long socCantidadTitulos) {
        this.socCantidadTitulos = socCantidadTitulos;
    }

    public BigDecimal getSocValorTitulo() {
        return socValorTitulo;
    }

    public void setSocValorTitulo(BigDecimal socValorTitulo) {
        this.socValorTitulo = socValorTitulo;
    }

    public Long getSocBufeteId() {
        return socBufeteId;
    }

    public void setSocBufeteId(Long socBufeteId) {
        this.socBufeteId = socBufeteId;
    }

    public List<SociedadRepresentanteDto> getSociedadRepresentanteList() {
        return sociedadRepresentanteList;
    }

    public void setSociedadRepresentanteList(List<SociedadRepresentanteDto> sociedadRepresentanteList) {
        this.sociedadRepresentanteList = sociedadRepresentanteList;
    }

    public List<SociedadRepresentanteDto> getSociedadRepresentanteEliminadosList() {
        return sociedadRepresentanteEliminadosList;
    }

    public void setSociedadRepresentanteEliminadosList(List<SociedadRepresentanteDto> sociedadRepresentanteEliminadosList) {
        this.sociedadRepresentanteEliminadosList = sociedadRepresentanteEliminadosList;
    }
    
    @JsonbTransient
    public BigDecimal getCapital(){
        if(socCantidadTitulos == null | socValorTitulo == null){
            return BigDecimal.ZERO;
        }
        return socValorTitulo.multiply(BigDecimal.valueOf(socCantidadTitulos));
    }
}
