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

        //aca manejo por parametro el nro de la version igual q arriba con el cod tramite
        String nroVersionStr = request.getParameter("nroVersion");
        if (nroVersionStr != null && !nroVersionStr.isEmpty()) {
            nroVersion = Integer.parseInt(nroVersionStr);
        } else {
            nroVersion = 1; // Valor por defecto o manejo de error
        }

        int nroVersion = Integer.parseInt(request.getParameter("nroVersion"));

        if (nroVersion > 0) {
            DTOVersionM dtoVersionM = controladorABMVersion.buscarVersionAModificar(nroVersion);
            setNroVersion(dtoVersionM.getNroVersion());
            setDescripcionVersion(dtoVersionM.getDescripcionVersion());
            setFechaDesdeVersion(dtoVersionM.getFechaDesdeVersion());
            setFechaHastaVersion(dtoVersionM.getFechaHastaVersion());
            // Cargar los estados de la versión anterior como nodos
            List<NodoIU> lestados = new ArrayList<>();

            // Cargar estados de origen
            for (DTOEstadoOrigenOUT estadoOrigen : dtoVersionM.getDtoEstadoOrigenOut()) {
                NodoIU unE = new NodoIU();
                unE.setCodigo(estadoOrigen.getCodEstadoTramite());
                unE.setNombre(estadoOrigen.getnombreEstadoOrigen()); // Asegúrate que este método esté bien escrito
                unE.setXpos(estadoOrigen.getXpos()); // Usar la posición ya existente
                unE.setYpos(estadoOrigen.getYpos()); // Usar la posición ya existente

                lestados.add(unE);

                // Cargar los destinos para cada estado de origen
                if (estadoOrigen.getDtoEstadoDestinoList() != null) {
                    for (DTOEstadoDestinoOUT destino : estadoOrigen.getDtoEstadoDestinoList()) {
                        NodoIU nodoDestino = new NodoIU();
                        nodoDestino.setCodigo(destino.getCodEstadoTramite());
                        nodoDestino.setNombre(destino.getNombreEstadoDestino());
                        nodoDestino.setXpos(destino.getXpos()); // Posición de destino
                        nodoDestino.setYpos(destino.getYpos()); // Posición de destino

                        lestados.add(nodoDestino);
                        unE.addDestino(destino.getCodEstadoTramite()); // Relación de origen a destino
                    }
                }
            }

            // Convertir la lista a JSON para el uso en el dibujo
            cargarJSON = new Gson().toJson(lestados);

        }

        //aca llamo al controlador para traerme todos los estados cargados en el abm
        DTOVersionM dv = controladorABMVersion.modificarVersion(codTipoTramite);

        if (dv != null && dv.getNroVersion() != 0) {
            this.nroVersion = dv.getNroVersion();  // Asignación desde el DTO para el nroVersion
        } else {
            this.nroVersion = 1;  // Si no hay un número de versión, comienza desde 1
        }

        titulo = "Versión";
        editable = true;
        Gson gson = new Gson();
        //aca preparo los nodos

        List<NodoMenuIU> lestadosP = new ArrayList<NodoMenuIU>();
        for (DTOEstado de : dv.getDtoEstado()) {
            NodoMenuIU unEP = new NodoMenuIU();
            unEP.setCodigo(de.getCodEstadoTramite());
            unEP.setNombre(de.getNombreEstadoTramite());
            unEP.setXpos(de.getXpos());
            unEP.setYpos(de.getYpos()); //con esto me traigo las posiciones en el dibujo :D
            lestadosP.add(unEP);
        }
        nodosPosibles = gson.toJson(lestadosP);

        // Cargo dibujo
        List<NodoIU> lestados = new ArrayList<NodoIU>();
        if (dv.getDtoEstadoOrigenOut().isEmpty()) { // Primera versión
            for (DTOEstado de : dv.getDtoEstado()) {
                NodoIU unE = new NodoIU();
                unE.setCodigo(de.getCodEstadoTramite());
                unE.setNombre(de.getNombreEstadoTramite());

                // Asignación de posiciones basadas en el código del estado de trámite
                switch (de.getCodEstadoTramite()) {
                    case 1:
                        unE.setXpos(20);
                        unE.setYpos(80);
                        break;
                    case 2:
                        unE.setXpos(100);
                        unE.setYpos(100);
                        break;
                    // Agrega más casos según sea necesario
                    default:
                        unE.setXpos(50); // Posición predeterminada
                        unE.setYpos(50); // Posición predeterminada
                        break;
                }

                lestados.add(unE);
            }
        } else {
            // Manejo para las versiones posteriores
            for (DTOEstadoOrigenOUT estadoOrigen : dv.getDtoEstadoOrigenOut()) {
                NodoIU unE = new NodoIU();
                unE.setCodigo(estadoOrigen.getCodEstadoTramite());
                unE.setNombre(estadoOrigen.getnombreEstadoOrigen());
                unE.setXpos(estadoOrigen.getXpos()); // Usar la posición ya existente
                unE.setYpos(estadoOrigen.getYpos()); // Usar la posición ya existente

                lestados.add(unE);

                // Cargar los destinos para cada estado de origen
                for (DTOEstadoDestinoOUT destino : estadoOrigen.getDtoEstadoDestinoList()) {
                    NodoIU nodoDestino = new NodoIU();
                    nodoDestino.setCodigo(destino.getCodEstadoTramite());
                    nodoDestino.setNombre(destino.getNombreEstadoDestino());
                    nodoDestino.setXpos(destino.getXpos()); // Posición de destino
                    nodoDestino.setYpos(destino.getYpos()); // Posición de destino

                    lestados.add(nodoDestino);
                    unE.addDestino(destino.getCodEstadoTramite()); // Relación de origen a destino
                }
            }
        }

// Convertir la lista a JSON
        cargarJSON = gson.toJson(lestados);
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
