package Excel.beans;

import ABCListaPrecios.dtos.DetalleListaPreciosDTO;
import ABCListaPrecios.dtos.ListaAExportarDTO;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import jakarta.enterprise.inject.Model;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.omnifaces.util.Messages;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@Named("uiExcelFile")
@ViewScoped
@Model
public class ExcelFileUI implements Serializable {

    private UploadedFile file;

    private StreamedContent fileD;
    
    private List<ListaAExportarDTO> detalles = new ArrayList<>();
    private List<DetalleListaPreciosDTO> detalles2 = new ArrayList<>();

    public ExcelFileUI() {

    }

    public StreamedContent getFileD() {
        try {
            Workbook libro = new XSSFWorkbook();
            final String nombreArchivo = "./tmp.xlsx";
            Sheet hoja = libro.createSheet("Hoja 1");

            Row headerRow = hoja.createRow(0);
            Cell headerCell1 = headerRow.createCell(0);
            headerCell1.setCellValue("codListaPrecios");
            Cell headerCell2 = headerRow.createCell(1);
            headerCell2.setCellValue("nombreTipoTramite");
            Cell headerCell3 = headerRow.createCell(2);
            headerCell3.setCellValue("descripcionTipoTramite");
            Cell headerCell4 = headerRow.createCell(3);
            headerCell4.setCellValue("precioTipoTramite");
            

            for (int j = 0; j < detalles.size(); j++) {
                Row dataRow = hoja.createRow(j + 1); 
                Cell cell1 = dataRow.createCell(0);
                cell1.setCellValue(detalles.get(j).getCodTipoTramite());
                
                Cell cell2 = dataRow.createCell(1);
                cell2.setCellValue(detalles.get(j).getNombreTipoTramite());

                Cell cell3 = dataRow.createCell(2);
                cell3.setCellValue(detalles.get(j).getDescripcionTipoTramite());
                
                Cell cell4 = dataRow.createCell(3);
                cell4.setCellValue(detalles.get(j).getPrecioTipoTramite());
                
            }
            
            FileOutputStream outputStream;
            outputStream = new FileOutputStream(nombreArchivo);
            libro.write(outputStream);
            libro.close();
            InputStream ie = new FileInputStream(nombreArchivo);
            fileD = DefaultStreamedContent.builder()
                    .name("ListaPrecios"+ LocalDate.now() +".xlsx")
                    .contentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                    .stream(() -> ie)
                    .build();
        } catch (IOException ex) {
            Logger.getLogger(ExcelFileUI.class.getName()).log(Level.SEVERE, null, ex);
            Messages.create(ex.getMessage()).error().add();
        }
        return fileD;
    }

    public void setFileD(StreamedContent fileD) {
        this.fileD = fileD;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void handleFileUpload(FileUploadEvent event) {
        FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded. Size (KB): " + event.getFile().getSize() / 1024f);
        FacesContext.getCurrentInstance().addMessage(null, message);
        try {
            InputStream inp = event.getFile().getInputStream();
            Workbook wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0);
            int iRow = 1;
            Row row = sheet.getRow(iRow); //En qué fila empezar ya dependerá también de si tenemos, por ejemplo, el título de cada columna en la primera fila
            while (row != null) {
                DetalleListaPreciosDTO detalle = new DetalleListaPreciosDTO();
                Cell cell = row.getCell(0);
                Cell cell2 = row.getCell(1);
                if ((cell == null || cell.getCellType() == CellType.BLANK) && (cell2 == null || cell2.getCellType() == CellType.BLANK)) {
                    break; // Salimos del bucle si la fila está vacía
                }
                if (cell != null && cell.getCellType() == CellType.NUMERIC) {
                    int codListaPrecios = (int)cell.getNumericCellValue();
                    detalle.setCodListaPrecios(codListaPrecios);
                    Messages.create("Fila "+ iRow + " CodListaPrecios:").detail(String.valueOf(codListaPrecios)).add();
                }
                if (cell2 != null && cell2.getCellType() == CellType.NUMERIC) {
                    double nuevoPrecioTipoTramite = cell2.getNumericCellValue();
                    detalle.setNuevoPrecioTipoTramite(nuevoPrecioTipoTramite);
                    Messages.create("Fila "+ iRow + " NuevoPrecioTipoTramite:").detail(String.valueOf(nuevoPrecioTipoTramite)).add();
                }
                detalles2.add(detalle);
                iRow++;
                row = sheet.getRow(iRow);
            }
        } catch (Exception ex) {
            Logger.getLogger(ExcelFileUI.class.getName()).log(Level.SEVERE, null, ex);
            Messages.create(ex.getMessage()).error().add();
        }
    }
}
