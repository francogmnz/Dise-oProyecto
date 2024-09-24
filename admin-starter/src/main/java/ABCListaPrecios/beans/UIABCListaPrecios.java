package ABCListaPrecios.beans;

import ABCListaPrecios.ControladorABCListaPrecios;
import static ABCListaPrecios.beans.UIABCListaPreciosLista.sumarMinuto;
import ABCListaPrecios.dtos.DetalleListaPreciosDTO;
import ABCListaPrecios.dtos.NuevaListaPreciosDTO;
import ABCListaPrecios.exceptions.ListaPreciosException;
import entidades.ListaPrecios;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.omnifaces.util.Messages;
import org.primefaces.event.FileUploadEvent;
import utils.BeansUtils;

@Named("uiabmListaPrecios")
@ViewScoped
public class UIABCListaPrecios implements Serializable {

    private ControladorABCListaPrecios controladorABCListaPrecios = new ControladorABCListaPrecios();
    private boolean insert;
    private int codListaPrecios;
    private Date fechaHoraDesdeListaPrecios;
    private Date fechaHoraHastaListaPrecios;
    private List<DetalleListaPreciosDTO> detalles = new ArrayList<>();
    private List<String> tags = new ArrayList<>();

// GETTERS y SETTERS
    public ControladorABCListaPrecios getControladorABCListaPrecios() {
        return controladorABCListaPrecios;
    }

    public void setControladorABCListaPrecios(ControladorABCListaPrecios controladorABCListaPrecios) {
        this.controladorABCListaPrecios = controladorABCListaPrecios;
    }

    public boolean isInsert() {
        return insert;
    }

    public void setInsert(boolean insert) {
        this.insert = insert;
    }

    public int getCodListaPrecios() {
        return codListaPrecios;
    }

    public void setCodListaPrecios(int codListaPrecios) {
        this.codListaPrecios = codListaPrecios;
    }

    public Date getFechaHoraDesdeListaPrecios() {
        return fechaHoraDesdeListaPrecios;
    }

    public void setFechaHoraDesdeListaPrecios(Date fechaHoraDesdeListaPrecios) {
        this.fechaHoraDesdeListaPrecios = fechaHoraDesdeListaPrecios;
    }

    public Date getFechaHoraHastaListaPrecios() {
        return fechaHoraHastaListaPrecios;
    }

    public void setFechaHoraHastaListaPrecios(Date fechaHoraHastaListaPrecios) {
        this.fechaHoraHastaListaPrecios = fechaHoraHastaListaPrecios;
    }

    public List<DetalleListaPreciosDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleListaPreciosDTO> detalles) {
        this.detalles = detalles;
    }

    public List<String> getTags() {
        if (!(tags.isEmpty() || tags == null)) {
            return tags;
        } else {
            List<String> a = new ArrayList<>();
            return a;
        }
    }

    public void setTags(List<String> tags) {
        if (!(tags.isEmpty() || tags == null)) {
            this.tags = tags;

        } else {
            List<String> a = new ArrayList<>();
            this.tags = a;
        }
    }

// GETTERS y SETTERS
//        CONSTRUCTOR
    public UIABCListaPrecios() throws IOException {

//        CON ESTO RECIBE LOS PARAMETROS ENVIADOS EN LA URL
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

//        VALIDACION CODIGO NUEVA LP
        int cod = 0;
        // Verificar si el código es válido, y si no lo es, redirigir a la URL anterior
        if (request.getParameter("codLP") == null || !(request.getParameter("codLP").matches("\\d+")) || Integer.parseInt(request.getParameter("codLP")) <= 0) {
            // Redirigir a la URL anterior si el código no es válido
            externalContext.redirect(externalContext.getRequestContextPath() + "/ABCListaPrecios/abmListaPreciosLista.jsf");
            return;
        } else {

            cod = Integer.parseInt(request.getParameter("codLP"));
        }

        String fd = request.getParameter("fDesde");

//        SETEAR POR DEFECTO LOS VALORES EN LOS CAMPOS
        if (cod > 0) {
            setCodListaPrecios(cod);
            setFechaHoraDesdeListaPrecios(StringToTimestamp(fd));
            setFechaHoraHastaListaPrecios(null);
        }
    }

//    MANEJA LA IMPORTACION DEL ARCHIVO
    public void handleFileUpload(FileUploadEvent event) {

        if (tags.size() == 0) {
            tags.add(event.getFile().getFileName());
        } else {
            tags.remove(0);
            tags.add(event.getFile().getFileName());
        }

//        MUESTRA EL MENSAJE DE SUBIDA EXITOSA DEL ARCHIVO
        FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded. Size (KB): " + event.getFile().getSize() / 1024f);
        FacesContext.getCurrentInstance().addMessage(null, message);

//        CREA UN WORKBOOK -> CREA UNA PAGINA ->  CREA LAS FILAS Y COLUMNAS, LES SETEA LOS VALORES
        try (InputStream inp = event.getFile().getInputStream()) {
            Workbook wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0);
            int iRow = 1;
            Row row = sheet.getRow(iRow);
            while (row != null) {
                DetalleListaPreciosDTO detalle = new DetalleListaPreciosDTO();
                Cell cell = row.getCell(0);
                Cell cell2 = row.getCell(1);

                if ((cell == null || cell.getCellType() == CellType.BLANK) && (cell2 == null || cell2.getCellType() == CellType.BLANK)) {
                    break;
                }

                if (cell != null && cell.getCellType() == CellType.NUMERIC) {
                    int codTipoTramite = (int) cell.getNumericCellValue();
                    detalle.setCodTipoTramite(codTipoTramite);
                    Messages.create("Fila " + iRow + " CodListaPrecios:").detail(String.valueOf(codTipoTramite)).add();
                }

                if (cell2 != null && cell2.getCellType() == CellType.NUMERIC) {
                    double nuevoPrecioTipoTramite = cell2.getNumericCellValue();
                    detalle.setNuevoPrecioTipoTramite(nuevoPrecioTipoTramite);
                    Messages.create("Fila " + iRow + " NuevoPrecioTipoTramite:").detail(String.valueOf(nuevoPrecioTipoTramite)).add();
                }

                detalles.add(detalle);
                iRow++;
                row = sheet.getRow(iRow);

            }
        } catch (Exception ex) {
            Logger.getLogger(UIABCListaPrecios.class
                    .getName()).log(Level.SEVERE, null, ex);
            Messages.create(ex.getMessage()).error().add();
        }
    }

    public String agregarListaPrecios() {
        if (tags == null || tags.isEmpty() || tags.size() == 0) {
            Messages.create("Error!").error().detail("Debe subir una Lista Precios para confirmar la accion.").add();
            return "";
        }
        if (getFechaHoraHastaListaPrecios() == null || getFechaHoraDesdeListaPrecios() == null) {
            Messages.create("Error!").error().detail("FechaDesde y/o FechaHasta esta/n vacía/s. Por favor complete los datos para confirmar la accion.").add();
            return "";
        } else {
            try {
                NuevaListaPreciosDTO nuevaListaPrecios = new NuevaListaPreciosDTO();
                nuevaListaPrecios.setCodListaPrecios(getCodListaPrecios());
                nuevaListaPrecios.setFechaHoraDesdeListaPrecios(new Timestamp(getFechaHoraDesdeListaPrecios().getTime()));
                nuevaListaPrecios.setFechaHoraHastaListaPrecios(new Timestamp(getFechaHoraHastaListaPrecios().getTime()));
                nuevaListaPrecios.setDetalles(getDetalles());
                controladorABCListaPrecios.agregarListaPrecios(nuevaListaPrecios);
                return BeansUtils.redirectToPreviousPage();
            } catch (ListaPreciosException e) {
                Messages.create(e.getMessage()).fatal().add();
                return "";
            }
        }
    }

//        SIRVE PARA CONVERTIR UN STRING A TIMESTAMP
    public static Timestamp StringToTimestamp(String s) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp t = null;

        try {
            // Convertir String a Date
            Date date = dateFormat.parse(s);
            // Convertir Date a Timestamp
            t = new Timestamp(date.getTime());
            return t;
        } catch (Exception e) {
            e.printStackTrace(); // Manejar el caso en que el formato es incorrecto
        }
        return t;
    }

}
