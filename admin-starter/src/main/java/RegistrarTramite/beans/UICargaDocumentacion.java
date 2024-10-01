/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RegistrarTramite.beans;

import RegistrarTramite.ControladorRegistrarTramite;
import RegistrarTramite.dtos.DTOFile;
import entidades.Tramite;
import entidades.TramiteDocumentacion;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.omnifaces.util.Messages;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.shaded.commons.io.IOUtils;
import utils.BeansUtils;
import utils.DTOCriterio;
import utils.FachadaPersistencia;

@Named("cargadocumentacion")
@ViewScoped
public class UICargaDocumentacion implements Serializable {

    private UploadedFile file; // para manejar la subida de archivos
    private int codTD = 0; // codTD para identificar el TD
    private int nroTramite = 0;
    private DTOFile fileEjemplo = new DTOFile();
    private DefaultStreamedContent fileD; // para manejar la descarga de archivos
    ControladorRegistrarTramite controladorRegistrarTramite = new ControladorRegistrarTramite();

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public int getCodTD() {
        return codTD;
    }

    public void setCodTD(int codTD) {
        this.codTD = codTD;
    }

    public int getNroTramite() {
        return nroTramite;
    }

    public void setNroTramite(int nroTramite) {
        this.nroTramite = nroTramite;
    }

    public DTOFile getFileEjemplo() {
        return fileEjemplo;
    }

    public void setFileEjemplo(DTOFile fileEjemplo) {
        this.fileEjemplo = fileEjemplo;
    }

    public UICargaDocumentacion() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

        int codigo = Integer.parseInt(request.getParameter("codTD"));
        int numTramite = Integer.parseInt(request.getParameter("nroTramite"));
        codTD = codigo;
        nroTramite = numTramite;
        System.out.println("codigo " + codigo);
        System.out.println("nroTramite" + numTramite);

    }
    private List<DTOFile> archivosSubidos = new ArrayList<>(); // Lista para almacenar archivos subidos
    // Getter para archivosSubidos

    public List<DTOFile> getArchivosSubidos() {
        return archivosSubidos;
    }

    
    // Método para manejar la subida del archivo
    public void handleFileUpload(FileUploadEvent event) {
        try {
            FacesMessage message = new FacesMessage("Exitoso", event.getFile().getFileName() + " subido.");
            FacesContext.getCurrentInstance().addMessage(null, message);

            //Convierto el archivo subido en Base64
            byte[] sourceBytes = IOUtils.toByteArray(event.getFile().getInputStream());
            String encodedString = Base64.getEncoder().encodeToString(sourceBytes);

            //Usar DTOFile para almacenar el archivo subido
            fileEjemplo.setNombre(event.getFile().getFileName());
            fileEjemplo.setContenidoB64(encodedString);

            //System.out.println("encriptado =" + fileEjemplo.getContenidoB64());
            //llamo a la funcion registrarDocumentacion una vez cargado el archivo
            controladorRegistrarTramite.registrarDocumentacion(codTD, fileEjemplo, nroTramite);

        } catch (IOException ex) {
            Logger.getLogger(UICargaDocumentacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Método para manejar la descarga del archivo
    public StreamedContent getFileD() {
        // Verifica si el archivo no es nulo y tiene contenido en Base64
        if (fileEjemplo != null && fileEjemplo.getContenidoB64() != null) {
            try {
                // Decodifica el contenido Base64 a un arreglo de bytes
                byte[] data = Base64.getDecoder().decode(fileEjemplo.getContenidoB64());

                // Crea un InputStream a partir del arreglo de bytes decodificado
                InputStream inputStream = new ByteArrayInputStream(data);

                // Construye el StreamedContent, asignando nombre, tipo de contenido y flujo
                fileD = DefaultStreamedContent.builder()
                        .name(fileEjemplo.getNombre()) // Asigna el nombre del archivo descargado
                        .contentType("application/octet-stream") // Tipo genérico para archivos binarios
                        .stream(() -> inputStream) // Proporciona el flujo de datos del archivo
                        .build();
            } catch (Exception ex) {
                Logger.getLogger(UICargaDocumentacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return fileD;
    }

}
