/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Email;

import java.sql.Timestamp;

/**
 *
 * @author Admin
 */
class DTOUsuario {

    private int dni;
    private String usuario;
    private String contrasena;
    private String nombre;
    private String apellido;
    private String email;
    private Timestamp fechaHoraBajaUsuario;


    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getFechaHoraBajaUsuario() {
        return fechaHoraBajaUsuario;
    }

    public void setFechaHoraBajaUsuario(Timestamp fechaHoraBajaUsuario) {
        this.fechaHoraBajaUsuario = fechaHoraBajaUsuario;
    }
}
