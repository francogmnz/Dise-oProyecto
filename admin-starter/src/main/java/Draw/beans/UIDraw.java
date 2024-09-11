package Draw.beans;

import Draw.ControladorDibujoEstados;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.gson.Gson;
import entidades.EstadoTramite;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.omnifaces.util.Messages;

@Named("uiDraw")
@ViewScoped

public class UIDraw implements Serializable {

    private ControladorDibujoEstados controladorDibujoEstados = new ControladorDibujoEstados();

    public ControladorDibujoEstados getControladorDibujoEstados() {
        return controladorDibujoEstados;
    }

    public void setControladorDibujoEstados(ControladorDibujoEstados controladorDibujoEstados) {
        this.controladorDibujoEstados = controladorDibujoEstados;
    }

    private String guardarJSON = "";

    private String cargarJSON = "";
    private String titulo = "";
    private boolean editable;
    private String nodosPosibles = "";
    private List<EstadoTramite> listaEstadosTramite = new ArrayList<>();

    public List<EstadoTramite> getListaEstadosTramite() {
        return listaEstadosTramite;
    }

    public String getNodosPosibles() {
        return nodosPosibles;
    }

    public void setNodosPosibles(String nodosPosibles) {
        this.nodosPosibles = nodosPosibles;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
// Método para actualizar los estados de trámite

    public void actualizarEstadosTramite() {
        try {
            this.listaEstadosTramite = controladorDibujoEstados.obtenerEstadosTramiteActivos();
        } catch (Exception e) {
            Messages.create("Error al cargar los estados de trámite: " + e.getMessage()).fatal().add();
        }
    }

    public UIDraw() {
    // Cargar estados dinámicamente
    actualizarEstadosTramite();

    // Convertir lista de EstadoTramite a NodoIU para los nodos del diagrama
    List<NodoIU> lestados = new ArrayList<>();
    
    for (EstadoTramite estado : listaEstadosTramite) {
        NodoIU nodo = new NodoIU();
        nodo.setCodigo(estado.getCodEstadoTramite()); // Ajusta estos métodos según tu entidad
        nodo.setNombre(estado.getNombreEstadoTramite());
    


       lestados.add(nodo);
    }

    // Convertir a JSON
    Gson gson = new Gson();
    cargarJSON = gson.toJson(lestados);

    // Actualizar título y estado editable
    titulo = "Versión Dinámica";
    editable = true;

    // Estados posibles dinámicos (menú)
    List<NodoMenuIU> lestadosP = new ArrayList<>();
    for (EstadoTramite estado : listaEstadosTramite) {
        NodoMenuIU nodoMenu = new NodoMenuIU();
        nodoMenu.setCodigo(estado.getCodEstadoTramite());
        nodoMenu.setNombre(estado.getNombreEstadoTramite());
        lestadosP.add(nodoMenu);
    }

    nodosPosibles = gson.toJson(lestadosP);
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

    public String getGuardarJSON() {
        return guardarJSON;
    }

    public void setGuardarJSON(String guardarJSON) {
        this.guardarJSON = guardarJSON;
    }

    public void guardar() {
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
            Logger.getLogger(UIDraw.class.getName()).log(Level.SEVERE, null, ex);
            Messages.create(ex.getMessage()).error().add();
        }

        //listaNodo tiene los nodos
        //para comprobar
        String jsonArray = "";

        Gson gson = new Gson();
        cargarJSON = gson.toJson(listaNodo);
        System.out.println(jsonArray);
        Messages.create("Guardar 2").detail(this.guardarJSON).add();
    }

}
