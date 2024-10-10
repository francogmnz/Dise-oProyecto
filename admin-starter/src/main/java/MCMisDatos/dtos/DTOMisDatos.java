package MCMisDatos.dtos;

import java.util.List;

public class DTOMisDatos {

    private int dni;
    private String nombre;
    private String apellido;
    private String usuario;
    private String contrasena;
    private String email;
    private List<String> legajos;

    public DTOMisDatos() {
    }

    
    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getLegajos() {
        return legajos;
    }

    public void setLegajos(List<String> legajos) {
        this.legajos = legajos;
    }
    
    
    
    
}
