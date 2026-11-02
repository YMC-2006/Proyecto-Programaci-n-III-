package cr.ac.una.proyectoprogra.model;

public class MachoteDto {

    String nombreArchivo;
    String contenido;

    public MachoteDto(String nombreArchivo, String contenido) {
        this.nombreArchivo = nombreArchivo;
        this.contenido = contenido;
    }

    public MachoteDto() {

    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

}
