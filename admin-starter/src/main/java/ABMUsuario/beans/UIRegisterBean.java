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
@Named("uiRegisterBean")
@ViewScoped
public class UIRegisterBean implements Serializable {
    private ControladorABMUsuario controladorABMUsuario = new ControladorABMUsuario();

    private String username;
    private String password;
    private String confirmPassword;
    private String rolNombre;


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
}
