package ABCListaPrecios;

import ABCListaPrecios.beans.UIABCListaPreciosLista;
import ABCListaPrecios.dtos.DetalleListaPreciosDTO;
import ABCListaPrecios.dtos.ListaPreciosDTO;
import ABCListaPrecios.dtos.NuevaListaPreciosDTO;
import ABCListaPrecios.exceptions.ListaPreciosException;
import entidades.ListaPrecios;
import entidades.TipoTramite;
import entidades.TipoTramiteListaPrecios;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.omnifaces.util.Messages;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import utils.DTOCriterio;
import utils.Errores;
import utils.FachadaPersistencia;
import utils.fechaHoraActual;

public class ExpertoABCListaPrecios {

    private StreamedContent fileD;
    private Errores err = new Errores();

    public List<ListaPreciosDTO> mostrarListasPrecios(Timestamp fechaHoraHastaListaPreciosFiltro) throws ListaPreciosException {
        List<DTOCriterio> lCriterio = new ArrayList<DTOCriterio>();

        if (fechaHoraHastaListaPreciosFiltro != null) {
            DTOCriterio unCriterio = new DTOCriterio();
            unCriterio.setAtributo("fechaHoraHastaListaPrecios");
            unCriterio.setOperacion(">=");
            unCriterio.setValor(fechaHoraHastaListaPreciosFiltro);
            lCriterio.add(unCriterio);
        } else {
            throw new ListaPreciosException("La FechaHasta ingresada no es correcta intente nuevamente.");
        }
        List objetoList = FachadaPersistencia.getInstance().buscar("ListaPrecios", lCriterio);
        List<ListaPreciosDTO> listasPreciosResultado = new ArrayList<>();
        for (Object x : objetoList) {
            ListaPrecios listaPrecios = (ListaPrecios) x;
            ListaPreciosDTO listaPreciosDTO = new ListaPreciosDTO();
            listaPreciosDTO.setCodListaPrecios(listaPrecios.getCodListaPrecios());
            listaPreciosDTO.setFechaHoraDesdeListaPrecios(listaPrecios.getFechaHoraDesdeListaPrecios());
            listaPreciosDTO.setFechaHoraHastaListaPrecios(listaPrecios.getFechaHoraHastaListaPrecios());
            listaPreciosDTO.setFechaHoraBajaListaPrecios(listaPrecios.getFechaHoraBajaListaPrecios());
            listasPreciosResultado.add(listaPreciosDTO);
        }
        return listasPreciosResultado;
    }

    public ListaPrecios buscarUltimaListaNoNula() {
//    BUSCA ULTIMA LISTA Y LA DEVUELVE
        List<DTOCriterio> lCriterio = new ArrayList<DTOCriterio>();
        DTOCriterio unCriterio = new DTOCriterio();

        unCriterio.setAtributo("fechaHoraBajaListaPrecios");
        unCriterio.setOperacion("=");
        unCriterio.setValor(null);
        lCriterio.add(unCriterio);

        List objetoList = FachadaPersistencia.getInstance().buscar("ListaPrecios", lCriterio);
        ListaPrecios ultimaListaPrecios = null;
        for (Object x : objetoList) {
            ListaPrecios listaPrecios = (ListaPrecios) x;
            if (ultimaListaPrecios == null || listaPrecios.getFechaHoraDesdeListaPrecios().after(ultimaListaPrecios.getFechaHoraDesdeListaPrecios())) {
                ultimaListaPrecios = listaPrecios;
            }
        }
        lCriterio.clear();
        return ultimaListaPrecios;
    }

    public void agregarListaPrecios(NuevaListaPreciosDTO nuevaListaPreciosDTO) throws ListaPreciosException {
        err.getErrores().clear();
        FachadaPersistencia.getInstance().iniciarTransaccion();

        // Buscar la última lista de precios
        ListaPrecios ultimaListaPrecios = buscarUltimaListaNoNula();

        // Ajustar las fechas de la nueva lista de precios
        Timestamp nuevaFechaHoraDesde = nuevaListaPreciosDTO.getFechaHoraDesdeListaPrecios();
        Timestamp nuevaFechaHoraHasta = nuevaListaPreciosDTO.getFechaHoraHastaListaPrecios();

        // Verificar si ya existe una lista de precios con el mismo código
        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();
        dto.setAtributo("codListaPrecios");
        dto.setOperacion("=");
        dto.setValor(nuevaListaPreciosDTO.getCodListaPrecios());
        criterioList.add(dto);

        List lListaPrecios = FachadaPersistencia.getInstance().buscar("ListaPrecios", criterioList);
        if (lListaPrecios.size() > 0) {
            throw new ListaPreciosException("El código ya existe");
        }

        // Crear la nueva lista de precios
        ListaPrecios nuevaListaPrecios = new ListaPrecios();
        nuevaListaPrecios.setCodListaPrecios(nuevaListaPreciosDTO.getCodListaPrecios());
        nuevaListaPrecios.setFechaHoraDesdeListaPrecios(nuevaListaPreciosDTO.getFechaHoraDesdeListaPrecios());
        nuevaListaPrecios.setFechaHoraHastaListaPrecios(nuevaListaPreciosDTO.getFechaHoraHastaListaPrecios());

        // Procesar los precios de los trámites en base a la nueva lista o la última lista existente
        criterioList.clear();
        dto = new DTOCriterio();
        dto.setAtributo("fechaHoraBajaTipoTramite");
        dto.setOperacion("=");
        dto.setValor(null);
        criterioList.add(dto);

        List objetoList2 = FachadaPersistencia.getInstance().buscar("TipoTramite", criterioList);
        if (objetoList2.isEmpty()) {
            throw new ListaPreciosException("No hay tipos tramites activos actualmente");
        }
        // Verificar si es la primera lista de precios (no hay lista previa)
        if (ultimaListaPrecios == null) {
            // Setear directamente los precios del detalle en la nueva lista
            for (DetalleListaPreciosDTO detalle : nuevaListaPreciosDTO.getDetalles()) {
                // Crear el criterio para buscar el tipo de trámite
                List<DTOCriterio> criterioTipoTramite = new ArrayList<>();
                DTOCriterio criterio = new DTOCriterio();
                criterio.setAtributo("codTipoTramite");
                criterio.setOperacion("=");
                criterio.setValor(detalle.getCodTipoTramite());
                criterioTipoTramite.add(criterio);

                DTOCriterio dto2 = new DTOCriterio();
                dto2.setAtributo("fechaHoraBajaTipoTramite");
                dto2.setOperacion("=");
                dto2.setValor(null);
                criterioTipoTramite.add(dto2);

                // Buscar el tipo de trámite
                List tipoTramiteList = FachadaPersistencia.getInstance().buscar("TipoTramite", criterioTipoTramite);
                if (!tipoTramiteList.isEmpty()) {
                    TipoTramite tipoTramite = (TipoTramite) tipoTramiteList.get(0);

                    TipoTramiteListaPrecios nuevoTipoTramiteListaPrecios = new TipoTramiteListaPrecios();
                    nuevoTipoTramiteListaPrecios.setTipoTramite(tipoTramite);
                    nuevoTipoTramiteListaPrecios.setPrecioTipoTramite(detalle.getNuevoPrecioTipoTramite());

                    // Guardar el nuevo tipo de trámite y asociarlo a la nueva lista
                    FachadaPersistencia.getInstance().guardar(nuevoTipoTramiteListaPrecios);
                    nuevaListaPrecios.addTipoTramiteListaPrecios(nuevoTipoTramiteListaPrecios);
                }
            }
        } else {
            // Si ya hay una lista anterior, ajustar la fecha de fin de la última lista

            if (!(nuevaFechaHoraDesde.after(ultimaListaPrecios.getFechaHoraDesdeListaPrecios()))) {
                throw new ListaPreciosException("La Fecha Desde debe ser mayor a la Fecha Desde de la última lista");
            }

            if (nuevaFechaHoraDesde.after(nuevaFechaHoraHasta) || nuevaFechaHoraDesde.before(nuevaFechaHoraHasta)) {
                ultimaListaPrecios.setFechaHoraHastaListaPrecios(nuevaFechaHoraDesde);
            }
            FachadaPersistencia.getInstance().guardar(ultimaListaPrecios);

            for (Object x : objetoList2) {
                TipoTramite tipoTramite = (TipoTramite) x;
                TipoTramiteListaPrecios nuevoTipoTramiteListaPrecios = new TipoTramiteListaPrecios();
                nuevoTipoTramiteListaPrecios.setTipoTramite(tipoTramite);
                nuevoTipoTramiteListaPrecios.setPrecioTipoTramite(0);

                int codTipoTramite = tipoTramite.getCodTipoTramite();
                List<DetalleListaPreciosDTO> detalles = nuevaListaPreciosDTO.getDetalles();

                boolean isEncontrado = false;
                for (DetalleListaPreciosDTO detalle : detalles) {
                    double precioTT = detalle.getNuevoPrecioTipoTramite();
                    int codigoTT = detalle.getCodTipoTramite();
                    if (!(precioTT < 0) && codigoTT == codTipoTramite) {
                        nuevoTipoTramiteListaPrecios.setPrecioTipoTramite(detalle.getNuevoPrecioTipoTramite());
                        isEncontrado = true;
                        break;
                    }
                }

                // Si no se encuentra en los detalles de la nueva lista, setear el precio de la última lista
                if (!isEncontrado) {
                    List<TipoTramiteListaPrecios> tipoTramiteListaPrecios = ultimaListaPrecios.getTipoTramiteListaPrecios();
                    for (TipoTramiteListaPrecios tipoTramiteListaPrecio : tipoTramiteListaPrecios) {
                        TipoTramite tipoTramite1 = tipoTramiteListaPrecio.getTipoTramite();
                        int codTipoTramite1 = tipoTramite1.getCodTipoTramite();
                        if (codTipoTramite1 == codTipoTramite) {
                            nuevoTipoTramiteListaPrecios.setPrecioTipoTramite(tipoTramiteListaPrecio.getPrecioTipoTramite());
                            break;
                        }
                    }
                }

                // Guardar el nuevo tipo de trámite y asociarlo a la nueva lista
                FachadaPersistencia.getInstance().guardar(nuevoTipoTramiteListaPrecios);
                nuevaListaPrecios.addTipoTramiteListaPrecios(nuevoTipoTramiteListaPrecios);
            }
        }

        // Guardar la nueva lista de precios y finalizar la transacción
        FachadaPersistencia.getInstance().guardar(nuevaListaPrecios);
        FachadaPersistencia.getInstance().finalizarTransaccion();
    }

    public StreamedContent exportarListaPrecios(int codigo) {

        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();
        dto.setAtributo("codListaPrecios");
        dto.setOperacion("=");
        dto.setValor(codigo);

        criterioList.add(dto);

        ListaPrecios listaPreciosEncontrada = (ListaPrecios) FachadaPersistencia.getInstance().buscar("ListaPrecios", criterioList).get(0);

        try {
            Workbook libro = new XSSFWorkbook();
            final String nombreArchivo = "./tmp.xlsx";
            Sheet hoja = libro.createSheet("Hoja 1");

            // Crear estilo de centrado
            CellStyle cellStyleCentrado = libro.createCellStyle();
            cellStyleCentrado.setAlignment(HorizontalAlignment.CENTER);
            cellStyleCentrado.setVerticalAlignment(VerticalAlignment.CENTER);

            //Estilo encabezado Exportacion
            CellStyle headerStyleEEx = libro.createCellStyle();
            headerStyleEEx.setAlignment(HorizontalAlignment.CENTER);
            headerStyleEEx.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyleEEx.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyleEEx.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Crear fuente blanca para los encabezados
            Font headerFontEEx = libro.createFont();
            headerFontEEx.setColor(IndexedColors.WHITE.getIndex());
            headerStyleEEx.setFont(headerFontEEx);

            // Estilo para encabezados (fondo azul marino y letras blancas)
            CellStyle headerStyle = libro.createCellStyle();
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Crear fuente blanca para los encabezados
            Font headerFont = libro.createFont();
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            headerStyle.setFont(headerFont);

            // Estilo para celdas de datos (fondo gris claro y letras negras)
            CellStyle dataStyle = libro.createCellStyle();
            dataStyle.setAlignment(HorizontalAlignment.CENTER);
            dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            dataStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            dataStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Crear fuente negra para los datos
            Font dataFont = libro.createFont();
            dataFont.setColor(IndexedColors.BLACK.getIndex());
            dataStyle.setFont(dataFont);

            // Crear encabezados
            Row row = hoja.createRow(0);

            // Crear una celda en la fila
            Cell cell = row.createCell(0);
            cell.setCellValue("Lista Precios N° " + listaPreciosEncontrada.getCodListaPrecios() + " - Desde: " + listaPreciosEncontrada.getFechaHoraDesdeListaPrecios() + " , Hasta: " + listaPreciosEncontrada.getFechaHoraHastaListaPrecios());

            // Unir celdas desde la celda (0,0) hasta la celda (0,3)
            hoja.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
            cell.setCellStyle(headerStyleEEx);

            Row headerRow = hoja.createRow(1);
            String[] headers = {"codTipoTramite", "nombreTipoTramite", "descripcionTipoTramite", "precioTipoTramite", "nuevoPrecioTipoTramite"};

            for (int i = 0; i < headers.length; i++) {
                Cell headerCell = headerRow.createCell(i);
                headerCell.setCellValue(headers[i]);
                headerCell.setCellStyle(headerStyle);
            }

//            List<TipoTramiteListaPrecios> detalles = listaPreciosEncontrada.getTipoTramiteListaPrecios();
            // Procesar los precios de los trámites en base a la nueva lista o la última lista existente
            criterioList.clear();
            dto = new DTOCriterio();
            dto.setAtributo("fechaHoraBajaTipoTramite");
            dto.setOperacion("=");
            dto.setValor(null);
            criterioList.add(dto);

            List objetoList2 = FachadaPersistencia.getInstance().buscar("TipoTramite", criterioList);
            List<TipoTramiteListaPrecios> detalles = listaPreciosEncontrada.getTipoTramiteListaPrecios();
            List<TipoTramiteListaPrecios> detallesNuevosTT = new ArrayList<>();

            for (Object x : objetoList2) {
                TipoTramite tipoTramite = (TipoTramite) x;
                int codTipoTramite = tipoTramite.getCodTipoTramite();

                // Buscar si el TipoTramite ya existe en detalles
                boolean encontrado = false;
                for (TipoTramiteListaPrecios detalle : detalles) {
                    if (detalle.getTipoTramite().getCodTipoTramite() == codTipoTramite) {
                        encontrado = true;
                        detallesNuevosTT.add(detalle);
                        break;
                    }
                }

                // Si no se encontró, creamos un nuevo detalle con precio 0
                if (!encontrado) {
                    TipoTramiteListaPrecios detalleNuevo = new TipoTramiteListaPrecios();
                    detalleNuevo.setTipoTramite(tipoTramite);
                    detallesNuevosTT.add(detalleNuevo);
                }
            }

            listaPreciosEncontrada.setTipoTramiteListaPrecios(detallesNuevosTT);

            listaPreciosEncontrada.getTipoTramiteListaPrecios().sort((tt1, tt2) -> {
                return Integer.compare(tt1.getTipoTramite().getCodTipoTramite(), tt2.getTipoTramite().getCodTipoTramite());
            }); //Para ordenar el excel por Codigo de manera Asc

            // Crear filas con datos
            for (int j = 0; j < detallesNuevosTT.size(); j++) {
                Row dataRow = hoja.createRow(j + 2);

                Cell cell1 = dataRow.createCell(0);
                cell1.setCellValue(detallesNuevosTT.get(j).getTipoTramite().getCodTipoTramite());
                cell1.setCellStyle(dataStyle);

                Cell cell2 = dataRow.createCell(1);
                cell2.setCellValue(detallesNuevosTT.get(j).getTipoTramite().getNombreTipoTramite());
                cell2.setCellStyle(dataStyle);

                Cell cell3 = dataRow.createCell(2);
                cell3.setCellValue(detallesNuevosTT.get(j).getTipoTramite().getDescripcionTipoTramite());
                cell3.setCellStyle(dataStyle);

                Cell cell4 = dataRow.createCell(3);
                cell4.setCellValue(detallesNuevosTT.get(j).getPrecioTipoTramite());
                cell4.setCellStyle(dataStyle);

                Cell cell5 = dataRow.createCell(4);
                cell5.setCellStyle(dataStyle);
            }

            // Ajustar el ancho de las columnas excepto la columna de descripción
            for (int i = 0; i < headers.length; i++) {
                if (i == 2) {  // Fijar el ancho de la columna de descripción
                    hoja.setColumnWidth(i, 40 * 256); // 200px en unidades de POI
                } else {
                    hoja.setColumnWidth(i, 22 * 256);
                }
            }

            FileOutputStream outputStream = new FileOutputStream(nombreArchivo);
            libro.write(outputStream);
            libro.close();
            InputStream ie = new FileInputStream(nombreArchivo);
            fileD = DefaultStreamedContent.builder()
                    .name("ListaPrecios" + listaPreciosEncontrada.getCodListaPrecios() + ".xlsx")
                    .contentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                    .stream(() -> ie)
                    .build();

            FachadaPersistencia.getInstance().finalizarTransaccion();
        } catch (IOException ex) {
            Logger.getLogger(UIABCListaPreciosLista.class.getName()).log(Level.SEVERE, null, ex);
            Messages.create(ex.getMessage()).error().add();
        }
        return fileD;
    }

    public StreamedContent plantillaListaPrecios() {

        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();
        dto.setAtributo("fechaHoraBajaTipoTramite");
        dto.setOperacion("=");
        dto.setValor(null);

        criterioList.add(dto);

        List objectList = FachadaPersistencia.getInstance().buscar("TipoTramite", criterioList);
        List<TipoTramite> tiposTramites = new ArrayList<>();

        for (Object x : objectList) {
            TipoTramite tipoTramite = (TipoTramite) x;
            tiposTramites.add(tipoTramite);
        }

        tiposTramites.sort((tt1, tt2) -> {
            return Integer.compare(tt1.getCodTipoTramite(), tt2.getCodTipoTramite());
        }); //Para ordenar el excel por Codigo de manera Asc

        try {
            Workbook libro = new XSSFWorkbook();
            final String nombreArchivo = "./tmp.xlsx";
            Sheet hoja = libro.createSheet("Hoja 1");

            // Crear estilo de centrado
            CellStyle cellStyleCentrado = libro.createCellStyle();
            cellStyleCentrado.setAlignment(HorizontalAlignment.CENTER);
            cellStyleCentrado.setVerticalAlignment(VerticalAlignment.CENTER);

            //Estilo encabezado Exportacion
            CellStyle headerStyleEEx = libro.createCellStyle();
            headerStyleEEx.setAlignment(HorizontalAlignment.CENTER);
            headerStyleEEx.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyleEEx.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyleEEx.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Crear fuente blanca para los encabezados
            Font headerFontEEx = libro.createFont();
            headerFontEEx.setColor(IndexedColors.WHITE.getIndex());
            headerStyleEEx.setFont(headerFontEEx);

            // Estilo para encabezados (fondo azul marino y letras blancas)
            CellStyle headerStyle = libro.createCellStyle();
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Crear fuente blanca para los encabezados
            Font headerFont = libro.createFont();
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            headerStyle.setFont(headerFont);

            // Estilo para celdas de datos (fondo gris claro y letras negras)
            CellStyle dataStyle = libro.createCellStyle();
            dataStyle.setAlignment(HorizontalAlignment.CENTER);
            dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            dataStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            dataStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Crear fuente negra para los datos
            Font dataFont = libro.createFont();
            dataFont.setColor(IndexedColors.BLACK.getIndex());
            dataStyle.setFont(dataFont);

            // Crear encabezados
//            Row encabezadoLPExportada = hoja.createRow(0);
//            Cell headerCellEncabezado = encabezadoLPExportada.createCell(0);
            Row row = hoja.createRow(0);

            // Crear una celda en la fila
            Cell cell = row.createCell(0);
            cell.setCellValue("Plantilla Lista Precios");

            // Unir celdas desde la celda (0,0) hasta la celda (0,3)
            hoja.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
            cell.setCellStyle(headerStyleEEx);

            Row headerRow = hoja.createRow(1);
            String[] headers = {"codTipoTramite", "nombreTipoTramite", "descripcionTipoTramite", "precioTipoTramite", "nuevoPrecioTipoTramite"};

            for (int i = 0; i < headers.length; i++) {
                Cell headerCell = headerRow.createCell(i);
                headerCell.setCellValue(headers[i]);
                headerCell.setCellStyle(headerStyle);
            }

            // Crear filas con datos
            for (int j = 0; j < tiposTramites.size(); j++) {
                Row dataRow = hoja.createRow(j + 2);

                Cell cell1 = dataRow.createCell(0);
                cell1.setCellValue(tiposTramites.get(j).getCodTipoTramite());
                cell1.setCellStyle(dataStyle);

                Cell cell2 = dataRow.createCell(1);
                cell2.setCellValue(tiposTramites.get(j).getNombreTipoTramite());
                cell2.setCellStyle(dataStyle);

                Cell cell3 = dataRow.createCell(2);
                cell3.setCellValue(tiposTramites.get(j).getDescripcionTipoTramite());
                cell3.setCellStyle(dataStyle);

                Cell cell4 = dataRow.createCell(3);
                cell4.setCellValue(0);
                cell4.setCellStyle(dataStyle);

                Cell cell5 = dataRow.createCell(4);
                cell5.setCellStyle(dataStyle);
            }

            // Ajustar el ancho de las columnas excepto la columna de descripción
            for (int i = 0; i < headers.length; i++) {
                if (i == 2) {  // Fijar el ancho de la columna de descripción
                    hoja.setColumnWidth(i, 40 * 256); // 200px en unidades de POI
                } else {
                    hoja.setColumnWidth(i, 22 * 256);
                }
            }

            FileOutputStream outputStream = new FileOutputStream(nombreArchivo);
            libro.write(outputStream);
            libro.close();
            InputStream ie = new FileInputStream(nombreArchivo);
            fileD = DefaultStreamedContent.builder()
                    .name("PlantillaListaPrecios.xlsx")
                    .contentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                    .stream(() -> ie)
                    .build();

        } catch (IOException ex) {
            Logger.getLogger(UIABCListaPreciosLista.class.getName()).log(Level.SEVERE, null, ex);
            Messages.create(ex.getMessage()).error().add();
        }
        return fileD;

    }

    public void darDeBajaListaPrecios(int codigo) throws ListaPreciosException {
        FachadaPersistencia.getInstance().iniciarTransaccion();

        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();

//    BUSCA LA LISTA DE PRECIOS POR EL CODIGO EN PARAMETRO
        dto.setAtributo("codListaPrecios");
        dto.setOperacion("=");
        dto.setValor(codigo);

        criterioList.add(dto);

        ListaPrecios listaPreciosEncontrada = (ListaPrecios) FachadaPersistencia.getInstance().buscar("ListaPrecios", criterioList).get(0);
        ListaPrecios ultiLP = buscarUltimaListaNoNula();
//        VERIFICA SI LA LISTA SELECCIONADA ES LA VIGENTE
        if (listaPreciosEncontrada.getFechaHoraDesdeListaPrecios().before(fechaHoraActual.obtenerFechaHoraActual()) && listaPreciosEncontrada.getFechaHoraHastaListaPrecios().after(fechaHoraActual.obtenerFechaHoraActual())) {
            throw new ListaPreciosException("La Lista de Precios seleccionada está vigente y no se puede dar de Baja.");
        }
//        VERIFICA SI LA LISTA SELECCIONADA ES PASADA
        if (listaPreciosEncontrada.getFechaHoraHastaListaPrecios().after(fechaHoraActual.obtenerFechaHoraActual())) {
            throw new ListaPreciosException("La Lista de Precios seleccionada no se puede dar de Baja, ya que no es la última.");
        }
//        VERIFICA SI LA LISTA SELECCIONADA YA ESTA DADA DE BAJA
        if (listaPreciosEncontrada.getFechaHoraBajaListaPrecios() != null) {
            throw new ListaPreciosException("La Lista de Precios seleccionada ya se encuentra dada de Baja.");
        }
//        VERIFICA SI NO HAY OTRA LISTA Y LA DA DE BAJA
        if (ultiLP == null) {
            listaPreciosEncontrada.setFechaHoraBajaListaPrecios(fechaHoraActual.obtenerFechaHoraActual());
            FachadaPersistencia.getInstance().guardar(listaPreciosEncontrada);
        } else {
//        VERIFICA SI ES LA LISTA ENCONTRADA ES LA ULTIMA LISTA DE PRECIOS
            if (codigo == ultiLP.getCodListaPrecios()) {
//            LE SETEA LA FECHAHORABAJA
                listaPreciosEncontrada.setFechaHoraBajaListaPrecios(fechaHoraActual.obtenerFechaHoraActual());
            } else {
                throw new ListaPreciosException("La lista seleccionada no es la última lista.");
            }

            Timestamp fh = listaPreciosEncontrada.getFechaHoraHastaListaPrecios();
            FachadaPersistencia.getInstance().iniciarTransaccion();
            FachadaPersistencia.getInstance().guardar(listaPreciosEncontrada);

//        VUELVE A BUSCAR LA ULTIMA LISTA PARA SETEARLE LA FECHAHORAHASTA IGUAL A FECHAHORAHASTA DE LA LISTA QUE DIMOS DE BAJA
            ListaPrecios ultiLP2 = buscarUltimaListaNoNula();

            if (ultiLP2 != null) {
                ultiLP2.setFechaHoraHastaListaPrecios(fh);
                FachadaPersistencia.getInstance().guardar(ultiLP2);
            }

            FachadaPersistencia.getInstance().finalizarTransaccion();

        }
    }
}
