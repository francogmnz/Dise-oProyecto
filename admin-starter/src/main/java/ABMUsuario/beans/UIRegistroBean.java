/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ABMUsuario.beans;

import ABMUsuario.ControladorABMUsuario;
import ABMUsuario.dtos.NuevoUsuarioDTO;
import ABMUsuario.exceptions.UsuarioException;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 *
 * @author licciardi
 */
@Named("uiRegistroBean")
@ViewScoped
public class UIRegistroBean implements Serializable {
    private ControladorABMUsuario controladorABMUsuario = new ControladorABMUsuario();

    private String username;
    private String password;
    private String confirmPassword;
    private String rolNombre;
    
    private int dniCliente;
    private String nombreCliente;
    private String apellidoCliente;
    private String mailCliente;


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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getRolNombre() {
        return rolNombre;
    }

    public void setRolNombre(String rolNombre) {
        this.rolNombre = rolNombre;
    }

    public int getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(int dniCliente) {
        this.dniCliente = dniCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getApellidoCliente() {
        return apellidoCliente;
    }

    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }

    public String getMailCliente() {
        return mailCliente;
    }

    public void setMailCliente(String mailCliente) {
        this.mailCliente = mailCliente;
    }

    
    
    public String registrarUsuario() {
        if (!password.equals(confirmPassword)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Las contraseñas no coinciden"));
            return null;
        }

        try {
            NuevoUsuarioDTO nuevoUsuarioDTO = new NuevoUsuarioDTO();
            nuevoUsuarioDTO.setUsername(username);
            nuevoUsuarioDTO.setPassword(password);
            nuevoUsuarioDTO.setRolNombre("Cliente"); 

            
            nuevoUsuarioDTO.setDniCliente(dniCliente);
            nuevoUsuarioDTO.setNombreCliente(nombreCliente);
            nuevoUsuarioDTO.setApellidoCliente(apellidoCliente);
            nuevoUsuarioDTO.setMailCliente(mailCliente);

            controladorABMUsuario.registrarUsuario(nuevoUsuarioDTO);
            return "/login?faces-redirect=true";
        } catch (UsuarioException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
            return null;
        }
    }    
    /*
    public String registrarUsuario() {
        if (!password.equals(confirmPassword)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Las contraseñas no coinciden"));
            return null;
        }

        try {
            NuevoUsuarioDTO nuevoUsuarioDTO = new NuevoUsuarioDTO();
            nuevoUsuarioDTO.setUsername(username);
            nuevoUsuarioDTO.setPassword(password);
            nuevoUsuarioDTO.setRolNombre(rolNombre);

            controladorABMUsuario.registrarUsuario(nuevoUsuarioDTO);
            return "/login?faces-redirect=true";
        } catch (UsuarioException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
            return null;
        }
    }
    */
}
