package MCMisDatos.beans;

import MCMisDatos.ControladorMCMisDatos;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;

import jakarta.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import utils.BeansUtils;

@Named("misDatos")
@ViewScoped
public class UIMisDatos implements Serializable {

    private ControladorMCMisDatos controladorMisDatos;

    private boolean desHabilitarGrilla = true;

    private int dni = 39768619;
    private String nom = "Fabrizio";
    private String ape = "Azeglio";
    private String usuario = "Fabros96";
    private String contrasena = "123456PtoElQueLee";
    private String email = "fabrizioaz96@gmail.com";
    private String login;
    private String lgjAdm = "legajoAdmin";
    private String lgjCon = "legajoConsultor";
    private String lgjRcp = "legajoRecepcionista";
    private boolean mostrarContrasena = false;
//    private int dni;
//    private String nom;
//    private String ape;
//    private String usuario;
//    private String contrasena;
//    private String email;

    public ControladorMCMisDatos getControladorMisDatos() {
        return controladorMisDatos;
    }

    public void setControladorMisDatos(ControladorMCMisDatos controladorMisDatos) {
        this.controladorMisDatos = controladorMisDatos;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getApe() {
        return ape;
    }

    public void setApe(String ape) {
        this.ape = ape;
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

    public String getLgjAdm() {
        return lgjAdm;
    }

    public void setLgjAdm(String lgjAdm) {
        this.lgjAdm = lgjAdm;
    }

    public String getLgjCon() {
        return lgjCon;
    }

    public void setLgjCon(String lgjCon) {
        this.lgjCon = lgjCon;
    }

    public String getLgjRcp() {
        return lgjRcp;
    }

    public void setLgjRcp(String lgjRcp) {
        this.lgjRcp = lgjRcp;
    }

    public boolean isMostrarContrasena() {
        return mostrarContrasena;
    }

    public void setMostrarContrasena(boolean mostrarContrasena) {
        this.mostrarContrasena = mostrarContrasena;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean isDesHabilitarGrilla() {
        return desHabilitarGrilla;
    }

    public void setDesHabilitarGrilla(boolean desHabilitarGrilla) {
        this.desHabilitarGrilla = desHabilitarGrilla;
    }

//        CONSTRUCTOR
    public UIMisDatos() {

//        MCMisDatos.dtos.DTOMisDatos dto = controladorMisDatos.buscarMisDatos(email);
//        setNom(dto.getNombre());
//        setApe(dto.getApellido());
//        setDni(dto.getDni());
//        setEmail(dto.getEmail());
    }

    public String irAMisDatos() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        BeansUtils.guardarUrlAnterior();
        externalContext.redirect(externalContext.getRequestContextPath() + "/misDatos.xhtml");
        System.out.println(getLogin());
        return "";
    }

    public void btnModSav() {
        desHabilitarGrilla = !desHabilitarGrilla;
    }

    public void btnContr() {
        mostrarContrasena = !mostrarContrasena;
    }
}
