package CambioEstado.beans;

import ABMArticulo.exceptions.CambioEstadoException;
import CambioEstado.ControladorCambioEstado;
import CambioEstado.dtos.CambioEstadoDTO;
import entidades.Estado;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.omnifaces.util.Messages;
import utils.BeansUtils;
import utils.FachadaPersistencia;

@Named("uiCambioEstado")
@ViewScoped
public class UICambioEstado implements Serializable {

    private ControladorCambioEstado controladorCambioEstado = new ControladorCambioEstado();

    private boolean insert;
    private int idTramite;
    private String estadoSeleccionado;
    private String descripcion;

    private List<Estado> estadosDisponibles;

    // Getters y Setters

    public boolean isInsert() {
        return insert;
    }

    public void setInsert(boolean insert) {
        this.insert = insert;
    }

    public int getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(int idTramite) {
        this.idTramite = idTramite;
    }

    public String getEstadoSeleccionado() {
        return estadoSeleccionado;
    }

    public void setEstadoSeleccionado(String estadoSeleccionado) {
        this.estadoSeleccionado = estadoSeleccionado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Estado> getEstadosDisponibles() {
        if (estadosDisponibles == null) {
            cargarEstadosDisponibles();
        }
        return estadosDisponibles;
    }

    private void cargarEstadosDisponibles() {
        List<Object> resultado = FachadaPersistencia.getInstance().buscar("Estado", new ArrayList<>());
        estadosDisponibles = resultado.stream()
                .map(obj -> (Estado) obj)
                .collect(Collectors.toList());
    }

    public UICambioEstado() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

        // Verificar si el parámetro está presente
        String idTramiteParam = request.getParameter("idTramite");
        if (idTramiteParam != null) {
            idTramite = Integer.parseInt(idTramiteParam);
            insert = false;
            // Aquí podrías cargar el estado actual del trámite si es necesario
        } else {
            insert = true;
        }
    }

    public String cambiarEstado() {
        try {
            Estado estado = buscarEstadoPorOID(estadoSeleccionado);
            if (estado == null) {
                throw new CambioEstadoException("El estado seleccionado no es válido.");
            }

            CambioEstadoDTO cambioEstadoDTO = new CambioEstadoDTO(idTramite, estado.getNombre(), descripcion);
            controladorCambioEstado.cambiarEstado(cambioEstadoDTO);
            return BeansUtils.redirectToPreviousPage();
        } catch (CambioEstadoException e) {
            Messages.create(e.getMessage()).fatal().add();
            return "";
        }
    }

    private Estado buscarEstadoPorOID(String oid) {
        for (Estado estado : estadosDisponibles) {
            if (estado.getOID().equals(oid)) {
                return estado;
            }
        }
        return null;
    }
}
