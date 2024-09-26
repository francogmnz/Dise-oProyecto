package ABMVersion.beans;

import ABMVersion.ControladorABMVersion;
import ABMVersion.dtos.ModificarVersionDTO;
import ABMVersion.dtos.DTODatosVersion;
import ABMVersion.dtos.DTODatosVersionIn;
import ABMVersion.dtos.DTOEstadoDestinoIN;
import ABMVersion.dtos.DTOEstadoOrigenIN;
import ABMVersion.dtos.DTOVersionM;
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
import jakarta.faces.application.NavigationHandler;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import org.omnifaces.util.Messages;
import utils.BeansUtils;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
    
    // Constructor
    public UIABMVersion() {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    ExternalContext externalContext = facesContext.getExternalContext();
    HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
    
    // Obtener el código del tipo de trámite desde la solicitud
    codTipoTramite = Integer.parseInt(request.getParameter("codTipoTramite"));

    try {
        String nroVersionParam = request.getParameter("nroVersion");
        if (nroVersionParam != null) {
            int nroVersion = Integer.parseInt(nroVersionParam);
            this.nroVersion = nroVersion;
            insert = true; // Determina que se va a insertar una nueva versión

            if (nroVersion > 0) {
                // Llama al método del controlador para obtener la versión por número
                ModificarVersionDTO modificarVersionDTO = controladorABMVersion.obtenerVersionPorNumero(nroVersion);
                if (modificarVersionDTO != null) {
                    // Establecer los valores de la versión obtenida
                    setDescripcionVersion(modificarVersionDTO.getDescripcionVersion());
                    setCodTipoTramite(modificarVersionDTO.getCodTipoTramite());
                    setFechaDesdeVersion(modificarVersionDTO.getFechaDesdeVersion()); // Asegúrate de tener este método
                    setFechaHastaVersion(modificarVersionDTO.getFechaHastaVersion()); // Asegúrate de tener este método
                    
                }
            }
        } else {
            insert = true; // Agregar nueva versión si no hay número de versión
        }

        // Cargar los estados de trámite y tipos de trámite
       // actualizarEstadosTramite();
      //  actualizarTiposTramite();

        // Preparar nodos después de cargar los datos
        prepararNodos();
        
    } catch (NumberFormatException e) {
        // Manejo de errores en caso de que el formato del número no sea válido
        e.printStackTrace(); // O maneja el error como desees
    } catch (Exception e) {
        // Manejo de errores generales
        e.printStackTrace(); // O maneja el error como desees
    }
}

    // Método para preparar los nodos del diagrama
    public void prepararNodos() {
     
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

    // Métodos de actualización de estados y tipos de trámite
    public void actualizarEstadosTramite() {
        try {
            this.listaEstadosTramite = controladorABMVersion.obtenerEstadosTramiteActivos();
        } catch (Exception e) {
            Messages.create("Error al cargar los estados de trámite: " + e.getMessage()).fatal().add();
        }
    }

    public void actualizarTiposTramite() {
        try {
            this.listaTiposTramite = controladorABMVersion.obtenerTiposTramitesActivos();
        } catch (Exception e) {
            Messages.create("Error al cargar los tipos de trámite: " + e.getMessage()).fatal().add();
        }
    }

    // Método para agregar o modificar una versión
    public String modificarVersion() {
        try {
            if (!insert) {
                DTOVersionM dtoVersionM = new DTOVersionM();
                dtoVersionM.setCodTipoTramite(getCodTipoTramite());
                dtoVersionM.setDescripcionVersion(getDescripcionVersion());
             
                controladorABMVersion.modificarVersion(dtoVersionM);
            } else {
                DTOVersionM dtoVersionM = new DTOVersionM();
                dtoVersionM.setNroVersion(getNroVersion());
                dtoVersionM.setDescripcionVersion(getDescripcionVersion());
                dtoVersionM.setCodTipoTramite(getCodTipoTramite());
                dtoVersionM.setFechaDesdeVersion(new Timestamp(getFechaDesdeVersion().getTime()));
                dtoVersionM.setFechaHastaVersion(new Timestamp(getFechaHastaVersion().getTime()));
                controladorABMVersion.modificarVersion(dtoVersionM);
            }
            return BeansUtils.redirectToPreviousPage();
        } catch (VersionException e) {
            Messages.create("Error al guardar la versión: " + e.getMessage()).fatal().add();
            return "";
        }
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
          
          
        DTODatosVersionIn dto= new DTODatosVersionIn();
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
            listaNodo = objectMapper.readValue(this.guardarJSON, typeFactory.constructCollectionType(List.class, NodoIU.class));
            System.out.println(listaNodo.toString());
        } catch (JsonProcessingException ex) {
            Logger.getLogger(UIABMVersion.class.getName()).log(Level.SEVERE, null, ex);
            Messages.create(ex.getMessage()).error().add();
        }
        for(NodoIU unNodo:listaNodo)
        {
            DTOEstadoOrigenIN ori=new DTOEstadoOrigenIN();
            ori.setCodEstadoTramite(unNodo.getCodigo());
            for(Integer i : unNodo.getDestinos())
            {
                DTOEstadoDestinoIN des=new DTOEstadoDestinoIN();
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