/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.sql.Timestamp;

/**
 *
 * @author licciardi
 */
public class Usuario extends Entidad {
    private String username;
    private String password;
    private Timestamp fechaHoraBajaUsuario;
    private Rol rol; 


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getFechaHoraBajaUsuario() {
        return fechaHoraBajaUsuario;
    }

    public void setFechaHoraBajaUsuario(Timestamp fechaHoraBajaUsuario) {
        this.fechaHoraBajaUsuario = fechaHoraBajaUsuario;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
