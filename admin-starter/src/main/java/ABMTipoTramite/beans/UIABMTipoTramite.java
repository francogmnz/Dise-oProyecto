/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ABMTipoTramite.beans;
//import ABMCategoriaTipoTramite.dtos.CategoriaTipoTramiteDTO;
import ABMTipoTramite.dtos.CategoriaTipoTramiteDTO;
//import ABMDocumentacion.dtos.DocumentacionDTO;
import ABMTipoTramite.dtos.DocumentacionDTO;
import ABMTipoTramite.beans.*;
import utils.BeansUtils;
import ABMTipoTramite.ControladorABMTipoTramite;
import ABMTipoTramite.dtos.ModificarTipoTramiteDTO;
import ABMTipoTramite.dtos.ModificarTipoTramiteDTOIn;
import ABMTipoTramite.dtos.NuevoTipoTramiteDTO;
import ABMTipoTramite.exceptions.TipoTramiteException;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.omnifaces.util.Messages;

/**
 *
 * @author licciardi
 */

        
@Named("uiabmTipoTramite")
@ViewScoped
public class UIABMTipoTramite implements Serializable {

    private ControladorABMTipoTramite controladorABMTipoTramite = new ControladorABMTipoTramite();

    private boolean insert;
    private int codTipoTramite;
    private String nombreTipoTramite;
    private String descripcionTipoTramite;
    private String descripcionWebTipoTramite;
    private int plazoEntregaDocumentacionTT;
    private int codCategoriaTipoTramite;
    private List<CategoriaTipoTramiteDTO> categoriasTipoTramiteActivas;
    //private List<Integer> documentacionesSeleccionadas;
    private List<Integer> documentacionesSeleccionadas;
    private List<DocumentacionDTO> documentacionesActivas;

    
   
    public boolean isInsert() {
        return insert;
    }

    public void setInsert(boolean insert) {
        this.insert = insert;
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

    public String getDescripcionTipoTramite() {
        return descripcionTipoTramite;
    }

    public void setDescripcionTipoTramite(String descripcionTipoTramite) {
        this.descripcionTipoTramite = descripcionTipoTramite;
    }

    public String getDescripcionWebTipoTramite() {
        return descripcionWebTipoTramite;
    }

    public void setDescripcionWebTipoTramite(String descripcionWebTipoTramite) {
        this.descripcionWebTipoTramite = descripcionWebTipoTramite;
    }

    public int getPlazoEntregaDocumentacionTT() {
        return plazoEntregaDocumentacionTT;
    }

    public void setPlazoEntregaDocumentacionTT(int plazoEntregaDocumentacionTT) {
        this.plazoEntregaDocumentacionTT = plazoEntregaDocumentacionTT;
    }

    public int getCodCategoriaTipoTramite() {
        return codCategoriaTipoTramite;
    }

    public void setCodCategoriaTipoTramite(int codCategoriaTipoTramite) {
        this.codCategoriaTipoTramite = codCategoriaTipoTramite;
    }

    public List<CategoriaTipoTramiteDTO> getCategoriasTipoTramiteActivas() {
        return categoriasTipoTramiteActivas;
    }

    public void setCategoriasTipoTramiteActivas(List<CategoriaTipoTramiteDTO> categoriasTipoTramiteActivas) {
        this.categoriasTipoTramiteActivas = categoriasTipoTramiteActivas;
    }

    public List<Integer> getDocumentacionesSeleccionadas() {
        return documentacionesSeleccionadas;
    }

    public void setDocumentacionesSeleccionadas(List<Integer> documentacionesSeleccionadas) {
        this.documentacionesSeleccionadas = documentacionesSeleccionadas;
    }

    public List<DocumentacionDTO> getDocumentacionesActivas() {
        return documentacionesActivas;
    }

    public void setDocumentacionesActivas(List<DocumentacionDTO> documentacionesActivas) {
        this.documentacionesActivas = documentacionesActivas;
    }

    public UIABMTipoTramite() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

        int codTipoTramite = Integer.parseInt(request.getParameter("codTipoTramite")); 
        //categoriasTipoTramiteActivas = controladorABMTipoTramite.obtenerCategoriasTipoTramiteActivas();
        try {
        categoriasTipoTramiteActivas = controladorABMTipoTramite.obtenerCategoriasTipoTramiteActivas();
        } catch (TipoTramiteException e) {
            Messages.create("Error").error().detail(e.getMessage()).add();
            categoriasTipoTramiteActivas = new ArrayList<>();
        }    
        //documentacionesActivas = controladorABMTipoTramite.obtenerDocumentacionesActivas();
        try {
        documentacionesActivas = controladorABMTipoTramite.obtenerDocumentacionesActivas();
        } catch (TipoTramiteException e) {
            Messages.create("Error").error().detail(e.getMessage()).add();
            documentacionesActivas = new ArrayList<>();
        }    
        
        insert = true;

        if (codTipoTramite > 0) {
            insert = false;
            ModificarTipoTramiteDTO modificarTipoTramiteDTO = controladorABMTipoTramite.buscarTipoTramiteAModificar(codTipoTramite);

            setCodTipoTramite(modificarTipoTramiteDTO.getCodTipoTramite()); 
            setNombreTipoTramite(modificarTipoTramiteDTO.getNombreTipoTramite());
            setDescripcionTipoTramite(modificarTipoTramiteDTO.getDescripcionTipoTramite());
            setDescripcionWebTipoTramite(modificarTipoTramiteDTO.getDescripcionWebTipoTramite());
            setPlazoEntregaDocumentacionTT(modificarTipoTramiteDTO.getPlazoEntregaDocumentacionTT());
            
            setCodCategoriaTipoTramite(modificarTipoTramiteDTO.getCodCategoriaTipoTramite());
            
            documentacionesSeleccionadas = new ArrayList<>();
            for (DocumentacionDTO doc : modificarTipoTramiteDTO.getDocumentacionesDTO()) {
                documentacionesSeleccionadas.add(doc.getCodDocumentacion());
            }
        
            
        }
    }

        public String agregarTipoTramite(){
        try {
            
            if(!insert)
            {
                ModificarTipoTramiteDTOIn modificarTipoTramiteDTOIn = new ModificarTipoTramiteDTOIn();
                modificarTipoTramiteDTOIn.setCodTipoTramite(getCodTipoTramite());
                modificarTipoTramiteDTOIn.setNombreTipoTramite(getNombreTipoTramite());
                modificarTipoTramiteDTOIn.setDescripcionTipoTramite(getDescripcionTipoTramite());
                modificarTipoTramiteDTOIn.setDescripcionWebTipoTramite(getDescripcionWebTipoTramite());
                modificarTipoTramiteDTOIn.setPlazoEntregaDocumentacionTT(getPlazoEntregaDocumentacionTT());
                
                modificarTipoTramiteDTOIn.setCodCategoriaTipoTramite(getCodCategoriaTipoTramite());
                
                List<DocumentacionDTO> documentacionesSeleccionadasDTO = new ArrayList<>();
                for(Integer codDocumentacion: documentacionesSeleccionadas){
                    for(DocumentacionDTO documentacionDTO: documentacionesActivas){
                        if(documentacionDTO.getCodDocumentacion() == codDocumentacion){
                            documentacionesSeleccionadasDTO.add(documentacionDTO);
                            break;
                        }
                    }
                }
                
                controladorABMTipoTramite.modificarTipoTramite(modificarTipoTramiteDTOIn,documentacionesSeleccionadasDTO);
                Messages.create("Exito").detail("Tipo de tramite modificado correctamente.").add();
                return BeansUtils.redirectToPreviousPage();
            }
            else
            {
                NuevoTipoTramiteDTO nuevoTipoTramiteDTO = new NuevoTipoTramiteDTO();
                nuevoTipoTramiteDTO.setCodTipoTramite(getCodTipoTramite());
                nuevoTipoTramiteDTO.setNombreTipoTramite(getNombreTipoTramite());
                nuevoTipoTramiteDTO.setDescripcionTipoTramite(getDescripcionTipoTramite());
                nuevoTipoTramiteDTO.setDescripcionWebTipoTramite(getDescripcionWebTipoTramite());    
                nuevoTipoTramiteDTO.setPlazoEntregaDocumentacionTT(getPlazoEntregaDocumentacionTT());
                
                nuevoTipoTramiteDTO.setCodCategoriaTipoTramite(getCodCategoriaTipoTramite());
                
                List<DocumentacionDTO> documentacionesSeleccionadasDTO = new ArrayList<>();
                for(Integer codDocumentacion: documentacionesSeleccionadas){
                    for(DocumentacionDTO documentacionDTO: documentacionesActivas){
                        if(documentacionDTO.getCodDocumentacion() == codDocumentacion){
                            documentacionesSeleccionadasDTO.add(documentacionDTO);
                            break;
                        }
                    }
                }
                        
                controladorABMTipoTramite.agregarTipoTramite(nuevoTipoTramiteDTO,documentacionesSeleccionadasDTO);
                Messages.create("Exito").detail("Tipo de tramite agregado correctamente.").add();
                return BeansUtils.redirectToPreviousPage();
            }
            
        }catch (TipoTramiteException e) {
            Messages.create("Error").error().detail(e.getMessage()).add();
            return null;
        

         }
    }

}

    

