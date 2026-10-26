package cr.ac.una.proyectoprogra.model;

public class MachoteDto {

    String nombre;
    String contenido;

    public MachoteDto(String nombre, String contenido) {
        this.nombre = nombre;
        this.contenido = contenido;
    }

    public MachoteDto() {

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

}
