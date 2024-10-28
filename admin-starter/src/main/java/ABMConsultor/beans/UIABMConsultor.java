package ABMConsultor.beans;

import ABMConsultor.ControladorABMConsultor;
import ABMConsultor.dtos.DTOIngresoDatos;
import ABMConsultor.dtos.DTOModificacionDatos;
import ABMConsultor.dtos.DTOModificacionDatosIn;
import ABMConsultor.exceptions.ConsultorException;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import utils.BeansUtils;
// import utils.Errores;

@Named("uiabmConsultor")
@ViewScoped

public class UIABMConsultor implements Serializable {

    private ControladorABMConsultor controladorABMConsultor = new ControladorABMConsultor();
    private boolean insert;
    private String nombreConsultor;
    private int legajoConsultor;
    private int numMaximoTramites;
 //   private Errores err = new Errores();

    public ControladorABMConsultor getControladorABMConsultor() {
        return controladorABMConsultor;
    }

    public void setControladorABMConsultor(ControladorABMConsultor controladorABMConsultor) {
        this.controladorABMConsultor = controladorABMConsultor;
    }

    public boolean isInsert() {
        return insert;
    }

    public void setInsert(boolean insert) {
        this.insert = insert;
    }

    public String getNombreConsultor() {
        return nombreConsultor;
    }

    public void setNombreConsultor(String nombreConsultor) {
        this.nombreConsultor = nombreConsultor;
    }

    public int getLegajoConsultor() {
        return legajoConsultor;
    }

    public void setLegajoConsultor(int legajoConsultor) {
        this.legajoConsultor = legajoConsultor;
    }

    public int getNumMaximoTramites() {
        return numMaximoTramites;
    }

    public void setNumMaximoTramites(int numMaximoTramites) {
        this.numMaximoTramites = numMaximoTramites;
    }

    public UIABMConsultor() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        int legajo = 0;
        // Verificar si el código es válido, y si no lo es, redirigir a la URL anterior
        if (request.getParameter("legajo") == null || !(request.getParameter("legajo").matches("\\d+")) || Integer.parseInt(request.getParameter("legajo")) < 0) {
            // Redirigir a la URL anterior si el código no es válido
            externalContext.redirect(externalContext.getRequestContextPath() + "/ABMConsultor/abmConsultorLista.jsf");

            return;
        }
        try {
            legajo = Integer.parseInt(request.getParameter("legajo"));
        } catch (NumberFormatException e) {
            externalContext.redirect(externalContext.getRequestContextPath() + "/ABMConsultor/abmConsultorLista.jsf");
            return;

        }

        insert = true;
        if (legajo > 0) {
            insert = false;
            DTOModificacionDatos dtoModificacionDatos = controladorABMConsultor.buscarConsultorAModificar(legajo);
            setNombreConsultor(dtoModificacionDatos.getNombreConsultor());
            setLegajoConsultor(dtoModificacionDatos.getLegajoConsultor());
            setNumMaximoTramites(dtoModificacionDatos.getNumMaximoTramites());
        }
        if (legajo == 0) {
            setNombreConsultor("");
            setLegajoConsultor(0);
            setNumMaximoTramites(0);
        }

    }

   /* public void agregarConsultor() throws ConsultorException {

        if (getLegajoConsultor() < 0) {
            err.agregarError("El Legajo debe ser un entero mayor a 0.");
        }
        if (getNombreConsultor().isEmpty()) {
            err.agregarError("El Nombre no puede quedar vacío.");
        }
        if (err.getErrores().isEmpty() || err.getErrores().size() == 0) {
            try {
                if (!insert) {

                    DTOModificacionDatosIn dtoModificacionDatosIn = new DTOModificacionDatosIn();
                    dtoModificacionDatosIn.setNombreConsultor(getNombreConsultor());
                    dtoModificacionDatosIn.setLegajoConsultor(getLegajoConsultor());
                    dtoModificacionDatosIn.setNumMaximoTramites(getNumMaximoTramites());
                    controladorABMConsultor.modificarConsultor(dtoModificacionDatosIn);
                } else {

                    DTOIngresoDatos nuevoConsultorDTO = new DTOIngresoDatos();
                    nuevoConsultorDTO.setNombreConsultor(getNombreConsultor());
                    nuevoConsultorDTO.setLegajoConsultor(getLegajoConsultor());
                    nuevoConsultorDTO.setNumMaximoTramites(getNumMaximoTramites());
                    controladorABMConsultor.agregarConsultor(nuevoConsultorDTO);
                }
                BeansUtils.redirectToPreviousPage();
            } catch (ConsultorException e) {
                err.agregarError(e.toString().split(": ")[1]);
            }
        } else {
            err.mostrarErrores();
        }
        err.mostrarErrores();
    }
*/
}
