package ABCListaPrecios.beans;

import ABCListaPrecios.ControladorABCListaPrecios;
import ABCListaPrecios.dtos.DetalleListaPreciosDTO;
import ABCListaPrecios.dtos.NuevaListaPreciosDTO;
import ABCListaPrecios.exceptions.ListaPreciosException;
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
import utils.Errores;
import utils.fechaHoraActual;

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
    private Errores err = new Errores();

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
        try {
            cod = Integer.parseInt(request.getParameter("codLP"));
        } catch (NumberFormatException e) {
            externalContext.redirect(externalContext.getRequestContextPath() + "/ABCListaPrecios/abmListaPreciosLista.jsf");
            return;
        }
        try {
            // Verificar si el código es válido, y si no lo es, redirigir a la URL anterior
            if (request.getParameter("codLP") == null || !(request.getParameter("codLP").matches("\\d+")) || Integer.parseInt(request.getParameter("codLP")) < 0 || Integer.parseInt(request.getParameter("codLP")) != 0) {

                throw new ListaPreciosException("Codigo no valido");
            } else {
                setCodListaPrecios(cod);
                setFechaHoraDesdeListaPrecios(new Date());
                setFechaHoraHastaListaPrecios(null);
            }
        } catch (ListaPreciosException e) {
            externalContext.redirect(externalContext.getRequestContextPath() + "/ABCListaPrecios/abmListaPreciosLista.jsf");
            return;

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
            int iRow = 2;
            Row row = sheet.getRow(iRow);
            while (row != null) {
                DetalleListaPreciosDTO detalle = new DetalleListaPreciosDTO();
                Cell cell = row.getCell(0);
                Cell cell2 = row.getCell(4);

                if ((cell == null || cell.getCellType() == CellType.BLANK) && (cell2 == null || cell2.getCellType() == CellType.BLANK)) {
                    break;
                }

                if (cell != null && cell.getCellType() == CellType.NUMERIC) {
                    int codTipoTramite = (int) cell.getNumericCellValue();
                    detalle.setCodTipoTramite(codTipoTramite);
                   // Messages.create("Fila " + iRow + " CodListaPrecios:").detail(String.valueOf(codTipoTramite)).add();
                }

                if (cell2 != null && cell2.getCellType() == CellType.NUMERIC) {
                    double nuevoPrecioTipoTramite = cell2.getNumericCellValue();
                    detalle.setNuevoPrecioTipoTramite(nuevoPrecioTipoTramite);
                   // Messages.create("Fila " + iRow + " NuevoPrecioTipoTramite:").detail(String.valueOf(nuevoPrecioTipoTramite)).add();
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
        // VALIDACIONES
        if (tags == null || tags.isEmpty()) {
            err.agregarError("Debe subir una Lista Precios.");
        }
        if (String.valueOf(getCodListaPrecios()).isEmpty() || getCodListaPrecios() <= 0) {
            err.agregarError("El Código debe ser un entero mayor o igual a 0.");
        }
        if (getFechaHoraDesdeListaPrecios() == null) {
            err.agregarError("Debe ingresar una FechaDesde");
        } else {
            if (getFechaHoraDesdeListaPrecios().before(new Date(fechaHoraActual.obtenerFechaHoraActual().getTime() - 600000))) {
                err.agregarError("FechaDesde no puede ser menor a la Fecha Actual. Intente nuevamente.");
            }
        }
        if (getFechaHoraHastaListaPrecios() == null) {
            err.agregarError("Debe Ingresar una FechaHasta.");
        } else {
            if (getFechaHoraHastaListaPrecios().before(getFechaHoraDesdeListaPrecios())) {
                err.agregarError("La Fecha Hasta debe ser mayor a la Fecha Desde.");
            }
        }
        if (getFechaHoraHastaListaPrecios() == getFechaHoraDesdeListaPrecios()) {
            err.agregarError("Las Fechas Desde y Hasta son iguales.");
        }

        // Verificamos si hay errores antes de continuar
        if (!err.getErrores().isEmpty()) {
            err.mostrarErrores();
            return ""; // Salimos si ya hay errores
        }

        try {

            // Si no hay errores, procedemos a crear la nueva lista de precios
            NuevaListaPreciosDTO nuevaListaPrecios = new NuevaListaPreciosDTO();
            nuevaListaPrecios.setCodListaPrecios(getCodListaPrecios());
            nuevaListaPrecios.setFechaHoraDesdeListaPrecios(new Timestamp(getFechaHoraDesdeListaPrecios().getTime()));
            nuevaListaPrecios.setFechaHoraHastaListaPrecios(new Timestamp(getFechaHoraHastaListaPrecios().getTime()));
            nuevaListaPrecios.setDetalles(getDetalles());
            controladorABCListaPrecios.agregarListaPrecios(nuevaListaPrecios);
            return BeansUtils.redirectToPreviousPage();

        } catch (ListaPreciosException e) {
            Messages.create(e.getMessage()).fatal().add();
        }
        // MANEJO DE EXCEPCIÓN DE FORMATO DE FECHA

        // Mostramos errores si existen
        if (!err.getErrores().isEmpty()) {
            err.mostrarErrores();
        }
        return "";
    }
}
