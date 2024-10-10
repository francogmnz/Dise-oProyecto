package com.github.adminfaces.starter.infra.security;

import ABMUsuario.ControladorABMUsuario;
import ABMUsuario.dtos.UsuarioDTO;
import ABMUsuario.exceptions.UsuarioException;
import com.github.adminfaces.template.session.AdminSession;
import org.omnifaces.util.Faces;

import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.inject.Specializes;
import jakarta.inject.Named;
import java.io.IOException;
import java.io.Serializable;

import static com.github.adminfaces.starter.util.Utils.addDetailMessage;
import com.github.adminfaces.template.config.AdminConfig;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Corregido en 2024 por los pibes de Synapsis on 8/10/24.
 *
 * This is just a login example.
 *
 * AdminSession uses isLoggedIn to determine if user must be redirect to login
 * page or not. By default AdminSession isLoggedIn always resolves to true so it
 * will not try to redirect user.
 *
 * If you already have your authorization mechanism which controls when user
 * must be redirect to initial page or logon you can skip this class.
 */
@Named
@SessionScoped
@Specializes
public class LogonMB extends AdminSession implements Serializable {

    private String currentUser;
    private String email;
    private String password;
    private boolean remember;

    @Inject
    private AdminConfig adminConfig;

    private ControladorABMUsuario controladorABMUsuario = new ControladorABMUsuario();

    private UsuarioDTO usuarioLogueado;

    public void login() throws IOException, UsuarioException {
        try {

            usuarioLogueado = controladorABMUsuario.iniciarSesion(email, password);
            currentUser = usuarioLogueado.getUsername();

            addDetailMessage("Inicio de sesión exitoso como <b>" + currentUser + "</b>");
            Faces.getExternalContext().getFlash().setKeepMessages(true);

            // Redirigir según el rol del usuario
            // Redirigir o hacer un solo Index con cosas visibles segun el rol
            String paginaRedireccion = adminConfig.getIndexPage(); // Página por defecto

            if ("Admin".equals(usuarioLogueado.getRolNombre())) {
                paginaRedireccion = "/admin/index.xhtml"; // configurar dependiendo del rol
            } else if ("Consultor".equals(usuarioLogueado.getRolNombre())) {
                paginaRedireccion = "/admin/index.xhtml";// configurar dependiendo del rol
            } else if ("Recepcionista".equals(usuarioLogueado.getRolNombre())) {
                paginaRedireccion = "/admin/index.xhtml";// configurar dependiendo del rol
            } else if ("Cliente".equals(usuarioLogueado.getRolNombre())) {
                paginaRedireccion = "/admin/index.xhtml";// configurar dependiendo del rol
            } else {

                paginaRedireccion = "/acceso-denegado.xhtml";
            }

            Faces.redirect(paginaRedireccion);

        } catch (UsuarioException e) {

            addDetailMessage("Error: " + e.getMessage());
            Faces.getExternalContext().getFlash().setKeepMessages(true);
        }
    }

    @Override
    public boolean isLoggedIn() {

        return currentUser != null;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRemember() {
        return remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public UsuarioDTO getUsuarioLogueado() {
        return usuarioLogueado;
    }
}
