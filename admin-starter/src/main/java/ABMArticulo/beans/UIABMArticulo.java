/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ABMArticulo.beans;

import ABMArticulo.ControladorABMArticulo;
import ABMArticulo.dtos.ModificarArticuloDTO;
import ABMArticulo.dtos.ModificarArticuloDTOIn;
import ABMArticulo.dtos.NuevoArticuloDTO;
import ABMArticulo.exceptions.ArticuloException;
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
import utils.DTOCriterio;
import utils.FachadaPersistencia;

/**
 *
 * @author matis
 */
@Named("uiabmArticulo")
@ViewScoped
public class UIABMArticulo implements Serializable {

    private ControladorABMArticulo controladorABMArticulo = new ControladorABMArticulo();

    private boolean insert;
    private String nombre;
    private int codigo;
    private String estadoSeleccionado;

    private List<Estado> estadosDisponibles;

    public String getEstadoSeleccionado() {
        return estadoSeleccionado;
    }

    public void setEstadoSeleccionado(String estadoSeleccionado) {
        this.estadoSeleccionado = estadoSeleccionado;
    }

    public boolean isInsert() {
        return insert;
    }

    public void setInsert(boolean insert) {
        this.insert = insert;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
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

    public UIABMArticulo() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

        int codigo = Integer.parseInt(request.getParameter("codigo"));
        insert = true;

        if (codigo > 0) {
            insert = false;
            ModificarArticuloDTO modificarArticuloDTO = controladorABMArticulo.buscarArticuloAModificar(codigo);
            setNombre(modificarArticuloDTO.getNombre());
            setCodigo(modificarArticuloDTO.getCodigo());
            setEstadoSeleccionado(modificarArticuloDTO.getEstado().getOID());

        }
    }

    public String agregarArticulo() {
        try {
            Estado estado = buscarEstadoPorOID(estadoSeleccionado);
            if (!insert) {
                ModificarArticuloDTOIn modificarArticuloDTOIn = new ModificarArticuloDTOIn();
                modificarArticuloDTOIn.setNombre(getNombre());
                modificarArticuloDTOIn.setCodigo(getCodigo());
                modificarArticuloDTOIn.setEstado(estado);
                controladorABMArticulo.modificarArticulo(modificarArticuloDTOIn);
                return BeansUtils.redirectToPreviousPage();
            } else {
                NuevoArticuloDTO nuevoArticuloDTO = new NuevoArticuloDTO();
                nuevoArticuloDTO.setNombre(getNombre());
                nuevoArticuloDTO.setCodigo(getCodigo());
                nuevoArticuloDTO.setEstado(estado);
                controladorABMArticulo.agregarArticulo(nuevoArticuloDTO);
            }
            return BeansUtils.redirectToPreviousPage();

        } catch (ArticuloException e) {
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
