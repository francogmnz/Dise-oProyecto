
package ABCListaPrecios.beans;

import ABCListaPrecios.ControladorABCListaPrecios;
import ABCListaPrecios.dtos.ListaPreciosDTO;
import ABCListaPrecios.exceptions.ListaPreciosException;
import entidades.ListaPrecios;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.omnifaces.util.Messages;
import org.primefaces.model.StreamedContent;
import utils.BeansUtils;

@Named("uiabmListaPreciosLista")
@ViewScoped
public class UIABCListaPreciosLista implements Serializable {
    
    private ControladorABCListaPrecios controladorABCListaPrecios = new ControladorABCListaPrecios();
    private Date fechaHoraHastaListaPreciosFiltro = new Date();

    public ControladorABCListaPrecios getControladorABCListaPrecios() {
        return controladorABCListaPrecios;
    }

    public void setControladorABCListaPrecios(ControladorABCListaPrecios controladorABCListaPrecios) {
        this.controladorABCListaPrecios = controladorABCListaPrecios;
    }

    public Date getFechaHoraHastaListaPreciosFiltro() {
        return fechaHoraHastaListaPreciosFiltro;
    }

    public void setFechaHoraHastaListaPreciosFiltro(Date fechaHoraHastaListaPreciosFiltro) {
        this.fechaHoraHastaListaPreciosFiltro = fechaHoraHastaListaPreciosFiltro;
    }
    
    public void filtrar(){}
    
    public List<ListaPreciosGrillaUI> buscarListasPrecios() {
        System.out.println(fechaHoraHastaListaPreciosFiltro);
        List<ListaPreciosGrillaUI> listasPreciosGrilla = new ArrayList<>();
        List<ListaPreciosDTO> listasPreciosDTO = controladorABCListaPrecios.buscarListasPrecios(new Timestamp(fechaHoraHastaListaPreciosFiltro.getTime()));
        for (ListaPreciosDTO listaPreciosDTO : listasPreciosDTO) {
            ListaPreciosGrillaUI listaPreciosGrilla = new ListaPreciosGrillaUI();
            listaPreciosGrilla.setCodListaPrecios(listaPreciosDTO.getCodListaPrecios());
            listaPreciosGrilla.setFechaHoraDesdeListaPrecios(listaPreciosDTO.getFechaHoraDesdeListaPrecios());
            listaPreciosGrilla.setFechaHoraHastaListaPrecios(listaPreciosDTO.getFechaHoraHastaListaPrecios());
            listaPreciosGrilla.setFechaHoraBajaListaPrecios(listaPreciosDTO.getFechaHoraBajaListaPrecios());
            listasPreciosGrilla.add(listaPreciosGrilla);
        }
        return listasPreciosGrilla;
    }
    
    public String irAgregarListaPrecios(){
        BeansUtils.guardarUrlAnterior();
        return "abmListaPrecios?faces-redirect=true&codigo=0";
    }
    
    public StreamedContent exportarListaPrecios(int codigo) throws ListaPreciosException {
        return controladorABCListaPrecios.exportarListaPrecios(codigo);
    }
    
    public void darDeBajaListaPrecios(int codigo){
        try {
            controladorABCListaPrecios.darDeBajaListaPrecios(codigo);
            Messages.create("Anulado").detail("Anulado").add();
        } catch (ListaPreciosException e) {
            Messages.create("Error!").error().detail("AdminFaces Error message.").add();
        }
    }
    
    public boolean habilitarBotonDarDeBaja(ListaPrecios listaPrecios){
        
        return !listaPrecios.getFechaHoraDesdeListaPrecios().after(new Date());
        
    }
    
}
