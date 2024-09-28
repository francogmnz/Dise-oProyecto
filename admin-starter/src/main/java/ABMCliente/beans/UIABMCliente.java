package ABMCliente.beans;

import ABMCliente.ControladorABMCliente;
import ABMCliente.dtos.DTOIngresoDatos;
import ABMCliente.dtos.DTOModificacionDatos;
import ABMCliente.dtos.DTOModificacionDatosIn;
import ABMCliente.exceptions.ClienteException;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import org.omnifaces.util.Messages;
import utils.BeansUtils;
import utils.Errores;

@Named("uiabmCliente")
@ViewScoped

public class UIABMCliente implements Serializable {

    private ControladorABMCliente controladorABMCliente = new ControladorABMCliente();
    private boolean insert;
    private int dniCliente;
    private String nombreCliente;
    private String apellidoCliente;
    private String mailCliente;
    private Errores err = new Errores();

    public boolean isInsert() {
        return insert;
    }

    public void setInsert(boolean insert) {
        this.insert = insert;
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

    public UIABMCliente() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

        int dni = Integer.parseInt(request.getParameter("dni"));
        insert = true;
        if (dni > 0) {
            insert = false;
            DTOModificacionDatos dtoModificacionDatos = controladorABMCliente.buscarClienteAModificar(dni);

            setNombreCliente(dtoModificacionDatos.getNombreCliente());
            setDniCliente(dtoModificacionDatos.getDniCliente());
            setApellidoCliente(dtoModificacionDatos.getApellidoCliente());
            setMailCliente(dtoModificacionDatos.getMailCliente());
        }

    }

    public String agregarCliente() {
//        VALIDACIONES
        if (String.valueOf(getDniCliente()).isEmpty() || getDniCliente() <= 0) {
            err.agregarError("El DNI debe ser mayor a 0.");
        }
        if (getNombreCliente().isEmpty()) {
            err.agregarError("El Nombre no puede quedar vacío.");
        }
        if (getApellidoCliente().isEmpty()) {
            err.agregarError("El Apellido no puede quedar vacío.");
        }
        if (getMailCliente().isEmpty()) {
            err.agregarError("El Correo no puede quedar vacío.");
        }
        if (err.getErrores().isEmpty() || err.getErrores().size() == 0) {

            try {

                if (!insert) {

                    DTOModificacionDatosIn dtoModificacionDatosIn = new DTOModificacionDatosIn();
                    dtoModificacionDatosIn.setNombreCliente(getNombreCliente());
                    dtoModificacionDatosIn.setDniCliente(getDniCliente());
                    dtoModificacionDatosIn.setApellidoCliente(getApellidoCliente());
                    dtoModificacionDatosIn.setMailCliente(getMailCliente());
                    controladorABMCliente.modificarCliente(dtoModificacionDatosIn);
                    return BeansUtils.redirectToPreviousPage();
                } else {
                    DTOIngresoDatos nuevoClienteDTO = new DTOIngresoDatos();
                    nuevoClienteDTO.setNombreCliente(getNombreCliente());
                    nuevoClienteDTO.setDniCliente(getDniCliente());
                    nuevoClienteDTO.setApellidoCliente(getApellidoCliente());
                    nuevoClienteDTO.setMailCliente(getMailCliente());

                    controladorABMCliente.agregarCliente(nuevoClienteDTO);

                }
                return BeansUtils.redirectToPreviousPage();
            } catch (ClienteException e) {
                Messages.create(e.getMessage()).fatal().add();
                return "";
            }
        } else {

            err.mostrarErrores();
        }
        return "";
    }
}
