/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RegistrarTramite.beans;

import RegistrarTramite.dtos.DTOFile;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.omnifaces.util.Messages;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.shaded.commons.io.IOUtils;
import utils.BeansUtils;

@Named("cargadocumentacion") // Nombre explícito
@ViewScoped
public class UICargaDocumentacion implements Serializable {

    private UploadedFile file;
    private int codTD = 0;
    private DTOFile fileEjemplo = null;
    private DefaultStreamedContent fileD;

    public int getCodTD() {
        return codTD;
    }

    public void setCodTD(int codTD) {
        this.codTD = codTD;
    }

    public UICargaDocumentacion() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

        int codigo = Integer.parseInt(request.getParameter("codTD"));
        codTD = codigo;
        System.out.println("codigo " + codigo);
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String handleFileUpload(FileUploadEvent event) {
        try {
            FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded. Size (KB): " + event.getFile().getSize() / 1024f);
            FacesContext.getCurrentInstance().addMessage(null, message);

            byte[] sourceBytes = IOUtils.toByteArray(event.getFile().getInputStream());

            String encodedString = Base64.getEncoder().encodeToString(sourceBytes);
            System.out.println(encodedString);
            DTOFile dto = new DTOFile();
            dto.setContenidoB64(encodedString);
            dto.setNombre(event.getFile().getFileName());
            fileEjemplo = dto;

        } catch (IOException ex) {
            Logger.getLogger(UICargaDocumentacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";//BeansUtils.redirectToPreviousPage();
    }

    public StreamedContent getFileD() {

        byte[] data = Base64.getDecoder().decode(fileEjemplo.getContenidoB64());
        String path = "./" + fileEjemplo.getNombre().trim();
        File file = new File(path);
        OutputStream outputStream = null;
        try {
            outputStream = new BufferedOutputStream(new FileOutputStream(file));

            outputStream.write(data);

            InputStream ie = new FileInputStream(path);

            fileD = DefaultStreamedContent.builder()
                    .name(fileEjemplo.getNombre())
                    .contentType("application/force-download")
                    .stream(() -> ie)
                    .build();
        } catch (IOException ex) {
            Logger.getLogger(UICargaDocumentacion.class.getName()).log(Level.SEVERE, null, ex);
            Messages.create(ex.getMessage()).error().add();
        }
        return fileD;
    }
}
