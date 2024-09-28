package ABMVersion.beans;

import ABMVersion.ControladorABMVersion;
import ABMVersion.dtos.DTODatosVersionIn;
import ABMVersion.dtos.DTOEstadoDestinoIN;
import ABMVersion.dtos.DTOEstadoOrigenIN;
import ABMVersion.dtos.DTOVersionM;
import ABMVersion.dtos.DTOEstado;
import Version.beans.NodoIU;
import Version.beans.NodoMenuIU;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.gson.Gson;
import entidades.EstadoTramite;
import entidades.TipoTramite;
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
     // Métodos de actualización de estados y tipos de trámite
    public void actualizarEstadosTramite() {
        try {
            this.listaEstadosTramite = controladorABMVersion.obtenerEstadosTramiteActivos();
        } catch (Exception e) {
            Messages.create("Error al cargar los estados de trámite: " + e.getMessage()).fatal().add();
        }
    }

    // Constructor
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
    DTOVersionM dv=controladorABMVersion.modificar(codTipoTramite);
    
               titulo = "Versión 1";
        editable = true;
        
        Gson gson = new Gson();
        List<NodoMenuIU> lestadosP = new ArrayList<NodoMenuIU>();
        for(DTOEstado de: dv.getDtoEstado())
        {
            NodoMenuIU unEP = new NodoMenuIU();
            unEP.setCodigo(de.getCodEstadoTramite());
            unEP.setNombre(de.getNombreEstadoTramite());
            unEP.setXpos(de.getXpos());
            unEP.setYpos(de.getYpos()); //con esto me traigo las posiciones en el dibujo :D
            lestadosP.add(unEP);
        }
        
                
        nodosPosibles = gson.toJson(lestadosP);
 
        
        //Cargo dibujo
        List<NodoIU> lestados = new ArrayList<NodoIU>();
        if(dv.getDtoEstadoOrigenOut().size()==0)//Primera Version
        {
                for(DTOEstado de: dv.getDtoEstado())
                {
                    if(de.getCodEstadoTramite()==1)
                    {
                    NodoIU unE = new NodoIU();
                    unE.setCodigo(de.getCodEstadoTramite());
                    unE.setNombre(de.getNombreEstadoTramite());
                    unE.setXpos(2);
                    unE.setYpos(80);
                    lestados.add(unE);
                    }
                }

        }
        else
        {
            //crear nodos de acuerdo al dtoorigen con sus destinos bien bonito!!!! :)
        }
        cargarJSON = gson.toJson(lestados);
        
    /*
    // Manejo del parámetro nroVersion
    String nroVersionStr = request.getParameter("nroVersion");
    int nroVersion = 0; // Inicializa en 0 o en un valor por defecto
    if (nroVersionStr != null && !nroVersionStr.isEmpty()) {
        try {
            nroVersion = Integer.parseInt(nroVersionStr);
        } catch (NumberFormatException e) {
            // Manejo de error: asigna un valor por defecto o registra el error
            nroVersion = 0; // O un valor por defecto
            // Podrías registrar el error o lanzar una excepción personalizada
            System.err.println("Error al parsear nroVersion: " + e.getMessage());
        }
    }

    insert = true;
    if (nroVersion > 0) {
        insert = false;
        DTOVersionM dtoVersionM = controladorABMVersion.buscarVersionAModificar(nroVersion);

        // Establecer los valores de la versión obtenida
        if (dtoVersionM != null) { // Asegúrate de que dtoVersionM no sea nulo
            setDescripcionVersion(dtoVersionM.getDescripcionVersion());
            setCodTipoTramite(dtoVersionM.getCodTipoTramite());
            setFechaDesdeVersion(dtoVersionM.getFechaDesdeVersion()); // Asegúrate de tener este método
            setFechaHastaVersion(dtoVersionM.getFechaHastaVersion()); // Asegúrate de tener este método
        } else {
            // Manejo de error si no se encuentra la versión
            System.err.println("No se encontró la versión para nroVersion: " + nroVersion);
        }
    }
    
    //actualizarTiposTramite();

    // Preparar nodos después de cargar los datos*/
    
}

    // Método para preparar los nodos del diagrama
    public void prepararNodos() {
  // Obtener los estados de trámite
    actualizarEstadosTramite();
        List<NodoIU> lestados = new ArrayList<NodoIU>();

        //Nodos Ejemplo
        NodoIU unE = new NodoIU();
        unE.setCodigo(1);
        unE.setNombre("ESTADO INICIAL");
        unE.setXpos(80);
        unE.setYpos(80);
        unE.addDestino(2);
        unE.addDestino(3);
        lestados.add(unE);

        unE = new NodoIU();
        unE.setCodigo(2);
        unE.setNombre("ESTADO DOS");
        unE.setXpos(391);
        unE.setYpos(39);
        unE.addDestino(3);
        lestados.add(unE);

        unE = new NodoIU();
        unE.setCodigo(3);
        unE.setNombre("ESTADO TRES");
        unE.setXpos(522);
        unE.setYpos(170);
        lestados.add(unE);
        //
        Gson gson = new Gson();
        cargarJSON = gson.toJson(lestados);
        titulo = "Versión 1";
        editable = true;

        //Estados Posibles
        //Ejemplo
        List<NodoMenuIU> lestadosP = new ArrayList<NodoMenuIU>();
        NodoMenuIU unEP = new NodoMenuIU();
        unEP.setCodigo(1);
        unEP.setNombre("ESTADO INICIAL");
        lestadosP.add(unEP);

        unEP = new NodoMenuIU();
        unEP.setCodigo(2);
        unEP.setNombre("ESTADO DOS");
        lestadosP.add(unEP);

        unEP = new NodoMenuIU();
        unEP.setCodigo(3);
        unEP.setNombre("ESTADO TRES");
        lestadosP.add(unEP);

        unEP = new NodoMenuIU();
        unEP.setCodigo(4);
        unEP.setNombre("ESTADO CUATRO");
        lestadosP.add(unEP);

        nodosPosibles = gson.toJson(lestadosP);
        
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

    public String irAgregarVersion() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Flash flash = externalContext.getFlash();

        // Guarda el codTipoTramite en Flash para que esté disponible en la página destino
        flash.put("codTipoTramite", getCodTipoTramite());

        // Redirige a la página drawVersion.xhtml
        NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
        navigationHandler.handleNavigation(facesContext, null, "drawIU.xhtml?faces-redirect=true");

        return null; // El redireccionamiento se maneja a través del NavigationHandler
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

    public void confirmar() {

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
                ori.addDtoEstadoDeastinoList(des);

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

}
