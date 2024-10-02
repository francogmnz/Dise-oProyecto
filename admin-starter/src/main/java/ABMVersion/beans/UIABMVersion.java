package ABMVersion.beans;

import ABMVersion.ControladorABMVersion;
import ABMVersion.dtos.DTODatosVersionIn;
import ABMVersion.dtos.DTOEstadoDestinoIN;
import ABMVersion.dtos.DTOEstadoOrigenIN;
import ABMVersion.dtos.DTOVersionM;
import ABMVersion.dtos.DTOEstado;
import ABMVersion.dtos.DTOEstadoDestinoOUT;
import ABMVersion.dtos.DTOEstadoOrigenOUT;
import ABMVersion.exceptions.VersionException;
import Version.beans.NodoIU;
import Version.beans.NodoMenuIU;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.gson.Gson;
import entidades.EstadoTramite;
import entidades.TipoTramite;
import entidades.Version;
import jakarta.faces.application.NavigationHandler;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import org.omnifaces.util.Messages;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.BeansUtils;

@Named("uiabmVersion")
@ViewScoped
public class UIABMVersion implements Serializable {

    private static final long serialVersionUID = 1L;
    private ControladorABMVersion controladorABMVersion = new ControladorABMVersion();
    private boolean insert;
    private int nroVersion;
    private String descripcionVersion;
    private int codTipoTramite;
    private String nombreTipoTramite;
    private Date fechaBajaVersion;
    private Date fechaDesdeVersion;
    private Date fechaHastaVersion;
    private List<EstadoTramite> listaEstadosTramite = new ArrayList<>();
    private List<TipoTramite> listaTiposTramite = new ArrayList<>();
    private String estadoSeleccionado;
    private String tipoTramiteSeleccionado;
    // Variables para la interfaz del diagrama
    private String guardarJSON = "";
    private String cargarJSON = "";
    private String titulo = "";
    private boolean editable;
    private String nodosPosibles = "";

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    // Getters y setters

    public ControladorABMVersion getControladorABMVersion() {
        return controladorABMVersion;
    }

    public void setControladorABMVersion(ControladorABMVersion controladorABMVersion) {
        this.controladorABMVersion = controladorABMVersion;
    }

    public List<TipoTramite> getListaTiposTramite() {
        return listaTiposTramite;
    }

    public List<EstadoTramite> getListaEstadosTramite() {
        return listaEstadosTramite;
    }

    public String getEstadoSeleccionado() {
        return estadoSeleccionado;
    }

    public void setEstadoSeleccionado(String estadoSeleccionado) {
        this.estadoSeleccionado = estadoSeleccionado;
    }

    public String getTipoTramiteSeleccionado() {
        return tipoTramiteSeleccionado;
    }

    public void setTipoTramiteSeleccionado(String tipoTramiteSeleccionado) {
        this.tipoTramiteSeleccionado = tipoTramiteSeleccionado;
    }

    public Date getFechaDesdeVersion() {
        return fechaDesdeVersion;
    }

    public void setFechaDesdeVersion(Date fechaDesdeVersion) {
        this.fechaDesdeVersion = fechaDesdeVersion;
    }

    public Date getFechaHastaVersion() {
        return fechaHastaVersion;
    }

    public void setFechaHastaVersion(Date fechaHastaVersion) {
        this.fechaHastaVersion = fechaHastaVersion;
    }

    public Date getFechaBajaVersion() {
        return fechaBajaVersion;
    }

    public void setFechaBajaVersion(Date fechaBajaVersion) {
        this.fechaBajaVersion = fechaBajaVersion;
    }

    public boolean isInsert() {
        return insert;
    }

    public void setInsert(boolean insert) {
        this.insert = insert;
    }

    public int getNroVersion() {
        return nroVersion;
    }

    public void setNroVersion(int nroVersion) {
        this.nroVersion = nroVersion;
    }

    public String getDescripcionVersion() {
        return descripcionVersion;
    }

    public void setDescripcionVersion(String descripcionVersion) {
        this.descripcionVersion = descripcionVersion;
    }

    public int getCodTipoTramite() {
        return codTipoTramite;
    }

    public void setCodTipoTramite(int codTipoTramite) {
        this.codTipoTramite = codTipoTramite;
    }

    public String getNombreTipoTramite() {
        return nombreTipoTramite;
    }

    public void setNombreTipoTramite(String nombreTipoTramite) {
        this.nombreTipoTramite = nombreTipoTramite;
    }
    // Métodos relacionados con el manejo de nodos del diagrama

    public String getGuardarJSON() {
        return guardarJSON;
    }

    public void setGuardarJSON(String guardarJSON) {
        this.guardarJSON = guardarJSON;
    }

    public String getCargarJSON() {
        return cargarJSON;
    }

    public void setCargarJSON(String cargarJSON) {
        this.cargarJSON = cargarJSON;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public String getNodosPosibles() {
        return nodosPosibles;
    }

    public void setNodosPosibles(String nodosPosibles) {
        this.nodosPosibles = nodosPosibles;
    }

    public UIABMVersion() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        // Manejo del parámetro codTipoTramite
        String codTipoTramiteStr = request.getParameter("codTipoTramite");
        if (codTipoTramiteStr != null && !codTipoTramiteStr.isEmpty()) {
            codTipoTramite = Integer.parseInt(codTipoTramiteStr);
        } else {
            codTipoTramite = 0; // Valor por defecto o manejo de error
        }

        DTOVersionM dtoVersionM = controladorABMVersion.modificarVersion(codTipoTramite);

        titulo = "Versión";
        editable = true;
        Gson gson = new Gson();
        //aca preparo los nodos

        List<NodoMenuIU> lestadosP = new ArrayList<NodoMenuIU>();
        for (DTOEstado de : dtoVersionM.getDtoEstado()) {
            NodoMenuIU unEP = new NodoMenuIU();
            unEP.setCodigo(de.getCodEstadoTramite());
            unEP.setNombre(de.getNombreEstadoTramite());
            unEP.setXpos(de.getXpos());
            unEP.setYpos(de.getYpos()); //con esto me traigo las posiciones en el dibujo :D
            lestadosP.add(unEP);
        }
        nodosPosibles = gson.toJson(lestadosP);

        // Cargo dibujo
        // Convertir la lista a JSON
        if (dtoVersionM.getDibujo() != null && dtoVersionM.getDibujo().trim().length() > 0) {
            setNroVersion(dtoVersionM.getNroVersion());
            setDescripcionVersion(dtoVersionM.getDescripcionVersion());
            setFechaDesdeVersion(dtoVersionM.getFechaDesdeVersion());
            setFechaHastaVersion(dtoVersionM.getFechaHastaVersion());
            cargarJSON = dtoVersionM.getDibujo();
        } else {
            List<NodoIU> lestados = new ArrayList<NodoIU>();

            for (DTOEstado de : dtoVersionM.getDtoEstado()) {
                if (de.getCodEstadoTramite() == 1) {
                    NodoIU unE = new NodoIU();
                    unE.setCodigo(de.getCodEstadoTramite());
                    unE.setNombre(de.getNombreEstadoTramite());
                    unE.setXpos(2);
                    unE.setYpos(80);
                    lestados.add(unE);
                }
            }
            cargarJSON = gson.toJson(lestados);
        }

    }

    public void confirmar() throws VersionException {

        DTODatosVersionIn dto = new DTODatosVersionIn();
        dto.setCodTipoTramite(codTipoTramite);
        dto.setDescripcionVersion(descripcionVersion);
        dto.setFechaDesdeVersion(new Timestamp(fechaDesdeVersion.getTime()));
        dto.setFechaHastaVersion(new Timestamp(fechaHastaVersion.getTime()));

        System.out.println(this.guardarJSON);
        Messages.create("Guardar").detail(this.guardarJSON).add();
        ObjectMapper objectMapper = new ObjectMapper();
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<NodoIU> listaNodo = new ArrayList();
        dto.setDibujo(this.guardarJSON);

        try {
            listaNodo = objectMapper.readValue(this.guardarJSON, typeFactory.constructCollectionType(List.class,
                    NodoIU.class
            ));
            System.out.println(listaNodo.toString());

        } catch (JsonProcessingException ex) {
            Logger.getLogger(UIABMVersion.class
                    .getName()).log(Level.SEVERE, null, ex);
            Messages.create(ex.getMessage()).error().add();
        }
        for (NodoIU unNodo : listaNodo) {
            DTOEstadoOrigenIN ori = new DTOEstadoOrigenIN();
            ori.setCodEstadoTramite(unNodo.getCodigo());
            for (Integer i : unNodo.getDestinos()) {
                DTOEstadoDestinoIN des = new DTOEstadoDestinoIN();
                des.setCodEstadoTramite(i.intValue());
                ori.addDtoEstadoDestinoList(des);

            }
            dto.addDtoEstadoOrigenList(ori);
        }
        Gson gson = new Gson();
        cargarJSON = gson.toJson(dto);
        System.out.println(cargarJSON);
        Messages.create("Guardar 2").detail(cargarJSON).add();

        controladorABMVersion.confirmacion(dto);

        /*
        //listaNodo tiene los nodos
        //para comprobar
        String jsonArray = "";

        Gson gson = new Gson();
        cargarJSON = gson.toJson(listaNodo);
        System.out.println(jsonArray);
        Messages.create("Guardar 2").detail(this.guardarJSON).add();
         */
    }
    /* public void modificarVersion(int codTipoTramite){
        ControladorABMVersion controlador = new ControladorABMVersion();
        Version versionAnterior = controlador.buscarVersionAModificar(codTìpoTramite, );
    } */
}
