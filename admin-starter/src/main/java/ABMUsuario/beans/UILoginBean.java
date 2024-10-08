/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ABMUsuario.beans;

import ABMUsuario.ControladorABMUsuario;
import ABMUsuario.dtos.UsuarioDTO;
import ABMUsuario.exceptions.UsuarioException;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.IOException;
import java.io.Serializable;

/**
 *
 * @author licciardi
 */
@Named("uiLoginBean")
@SessionScoped
public class UILoginBean implements Serializable {
    private ControladorABMUsuario controladorABMUsuario = new ControladorABMUsuario();

    private String username;
    private String password;
    private UsuarioDTO usuarioLogueado;

    

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

    public UsuarioDTO getUsuarioLogueado() {
        return usuarioLogueado;
    }

/*
public void iniciarSesion() {
    try {
        usuarioLogueado = controladorABMUsuario.iniciarSesion(username, password);


        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido", "Inicio de sesión exitoso"));


        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);


        String contexto = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        String paginaRedireccion = "";

        if ("Admin".equals(usuarioLogueado.getRolNombre())) {
            paginaRedireccion = contexto + "/admin/index.xhtml";
        } else if ("Consultor".equals(usuarioLogueado.getRolNombre())) {
            paginaRedireccion = contexto + "/consultor/index.xhtml";
        } else if ("Recepcionista".equals(usuarioLogueado.getRolNombre())) {
            paginaRedireccion = contexto + "/recepcionista/index.xhtml";
        } else if ("Cliente".equals(usuarioLogueado.getRolNombre())) {
            paginaRedireccion = contexto + "/admin/index.xhtml";
        } else {

            paginaRedireccion = contexto + "/acceso-denegado.xhtml";
        }

        // Realizar la redirección
        FacesContext.getCurrentInstance().getExternalContext().redirect(paginaRedireccion);

    } catch (UsuarioException e) {
        // Agregar mensaje de error
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
    } catch (IOException e) {
        // Manejar excepción de redirección
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al redirigir"));
    }
}

*/
    
    public String cerrarSesion() {
        usuarioLogueado = null;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login?faces-redirect=true";
    }

    public boolean isLoggedIn() {
        return usuarioLogueado != null;
    }
    
  
}
