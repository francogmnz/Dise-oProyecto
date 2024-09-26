package RegistrarTramite.dtos;

public class DTOFile {
  private String nombre;
  private String contenidoB64;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContenidoB64() {
        return contenidoB64;
    }

    public void setContenidoB64(String contenidoB64) {
        this.contenidoB64 = contenidoB64;
    }
}
