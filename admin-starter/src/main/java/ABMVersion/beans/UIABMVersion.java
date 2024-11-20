package ABMVersion.beans;

import ABMVersion.ControladorABMVersion;
import ABMVersion.dtos.DTODatosVersionIn;
import ABMVersion.dtos.DTOEstadoDestinoIN;
import ABMVersion.dtos.DTOEstadoOrigenIN;
import ABMVersion.dtos.DTOVersionM;
import ABMVersion.dtos.DTOEstado;
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
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import org.omnifaces.util.Messages;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.primefaces.PrimeFaces;
import utils.fechaHoraActual;

@Named("uiabmVersion")
@ViewScoped
//constructor
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
            lestadosP.add(unEP);
        }
        nodosPosibles = gson.toJson(lestadosP);

        // Cargo el dibujo
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
                    unE.setXpos(80);
                    unE.setYpos(80);
                    lestados.add(unE);
                }

            }
            cargarJSON = gson.toJson(lestados);
        }

    }

    //metodo para confirmar los datos
    public void confirmar() throws VersionException {

        if (fechaHastaVersion == null) {
            Messages.create("Error").detail("La fecha hasta no puede estar vacía.").error().add();
            return;
        }
        if (fechaDesdeVersion == null) {
            Messages.create("Error").detail("La fecha desde no puede estar vacía.").error().add();
            return;
        }
        if (descripcionVersion.isEmpty()) {
            Messages.create("Error").detail("La descripcion no puede estar vacía.").error().add();
            return;
        }

        // La fecha hasta no puede ser menor que la fecha desde
        if (fechaHastaVersion.before(fechaDesdeVersion)) {
            Messages.create("Error").detail("FechaDesde no puede ser mayor a FechaHasta.").error().add();
            return;
        }

        if (fechaDesdeVersion.before(fechaDesdeVersion)) {
            Messages.create("Error").detail("FechaDesde no puede ser mayor a FechaHasta.").error().add();
            return;
        }

        // Verificar que no se guarde una fecha anterior a hoy
        Timestamp fechaActual = fechaHoraActual.obtenerFechaHoraActual();

        if (fechaDesdeVersion.before(fechaActual)) {
            Messages.create("Error").detail("No se puede guardar una version con una fecha anterior a hoy.").error().add();
            return;
        }

        ZoneId zonaHoraria = ZoneId.of("America/Argentina/Buenos_Aires");

        // Crear el DTO
        DTODatosVersionIn dto = new DTODatosVersionIn();
        dto.setCodTipoTramite(codTipoTramite);
        dto.setDescripcionVersion(descripcionVersion);
        // Asegurarse de que las fechas estén en la zona horaria de Buenos Aires
        ZoneId zonaBuenosAires = ZoneId.of("America/Argentina/Buenos_Aires");

// Convertir fechaDesdeVersion y fechaHastaVersion usando la zona horaria específica
        ZonedDateTime fechaDesdeBA = fechaDesdeVersion.toInstant().atZone(zonaBuenosAires);
        ZonedDateTime fechaHastaBA = fechaHastaVersion.toInstant().atZone(zonaBuenosAires);

// Guardar las fechas convertidas en el DTO
        dto.setFechaDesdeVersion(Timestamp.from(fechaDesdeBA.toInstant()));
        dto.setFechaHastaVersion(Timestamp.from(fechaHastaBA.toInstant()));
        ObjectMapper objectMapper = new ObjectMapper();
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<NodoIU> listaNodo = new ArrayList<>();
        dto.setDibujo(this.guardarJSON);

        try {
            listaNodo = objectMapper.readValue(this.guardarJSON, typeFactory.constructCollectionType(List.class, NodoIU.class));
            System.out.println(listaNodo.toString());
        } catch (JsonProcessingException ex) {
            Logger.getLogger(UIABMVersion.class.getName()).log(Level.SEVERE, null, ex);
            Messages.create(ex.getMessage()).error().add();
            return;  // Si hay un error en el procesamiento del JSON, no continuar
        }

        if (listaNodo.size() <= 1) {
            Messages.create("Error").detail("La version debe tener más de un estado para continuar").error().add();
            return;

        }
        // Validar que los nodos tienen estados de destino conectados

// Paso 1: Crear una lista de todos los nodos que son destinos (es decir, que están referenciados por otros nodos)
        Set<Integer> destinos = new HashSet<>();
        for (NodoIU unNodo : listaNodo) {
            // Recorremos los destinos de cada nodo
            for (Integer destino : unNodo.getDestinos()) {
                destinos.add(destino); // Añadir cada destino a la lista
            }
        }
// Paso 2: Verificar si cada nodo tiene al menos un origen (es decir, si está en la lista de destinos)
        boolean esFinal = false;
        for (NodoIU unNodo : listaNodo) {
            if (unNodo.getCodigo() != 1) { // Excluir el nodo con código 1 (Iniciado)
                // Verificar si el nodo actual está presente como destino
                if (!destinos.contains(unNodo.getCodigo())) {
                    // Si el código del nodo no está en la lista de destinos, el nodo no tiene origen
                    Messages.create("Error").detail("El estado " + unNodo.getNombre() + " no tiene un origen, está aislado.").error().add();
                    return;  // Detener el proceso, ya que hay un nodo sin origen
                }
                if (unNodo.getDestinos().isEmpty()) {
                   esFinal = true;
                   
                }
            }
        }

// Si todos los nodos tienen un origen, continuar con el resto del proceso
        for (NodoIU unNodo : listaNodo) {
            DTOEstadoOrigenIN ori = new DTOEstadoOrigenIN();
            ori.setCodEstadoTramite(unNodo.getCodigo());

            // Añadir los destinos al DTO de origen
            for (Integer i : unNodo.getDestinos()) {
                DTOEstadoDestinoIN des = new DTOEstadoDestinoIN();
                des.setCodEstadoTramite(i.intValue());
                ori.addDtoEstadoDestinoList(des);
            }
            if(esFinal == false){
            Messages.create("Error").detail("Debe haber un estado sin destino como minimo").error().add();
                    return;  
            }
            dto.addDtoEstadoOrigenList(ori);
        }

        // Intentar guardar los datos a través del controlador
        boolean guardadoExitoso = controladorABMVersion.confirmacion(dto);

        // Mostrar mensaje solo si se guardó exitosamente
        if (guardadoExitoso) {
            Gson gson = new Gson();
            cargarJSON = gson.toJson(dto);
            System.out.println(cargarJSON);
            Messages.create("Guardar").detail("Guardado exitoso").add();
            PrimeFaces.current().executeScript("setTimeout(function(){ window.history.back(); }, 1500);");

        } else {
            Messages.create("Error").detail("Error al guardar los datos").error().add();
        }
    }

    public void volverPantallaVersion() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("admin/ABMVersion/abmVersionLista.jsf");
    }

}
