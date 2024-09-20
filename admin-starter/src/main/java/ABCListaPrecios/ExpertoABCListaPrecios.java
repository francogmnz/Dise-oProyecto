package ABCListaPrecios;

import ABCListaPrecios.beans.UIABCListaPreciosLista;
import ABCListaPrecios.dtos.DetalleListaPreciosDTO;
import ABCListaPrecios.dtos.ListaPreciosDTO;
import ABCListaPrecios.dtos.NuevaListaPreciosDTO;
import ABCListaPrecios.exceptions.ListaPreciosException;
import Excel.beans.ExcelFileUI;
import entidades.ListaPrecios;
import entidades.TipoTramite;
import entidades.TipoTramiteListaPrecios;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.omnifaces.util.Messages;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import utils.DTOCriterio;
import utils.FachadaPersistencia;

public class ExpertoABCListaPrecios {

    private StreamedContent fileD;
    

    public List<ListaPreciosDTO> buscarListasPrecios(Timestamp fechaHoraHastaListaPreciosFiltro) {
    public List<ListaPreciosDTO> mostrarListasPrecios(Timestamp fechaHoraHastaListaPreciosFiltro) {
        List<DTOCriterio> lCriterio = new ArrayList<DTOCriterio>();
        if (fechaHoraHastaListaPreciosFiltro != null) {
            DTOCriterio unCriterio = new DTOCriterio();
            unCriterio.setAtributo("fechaHoraHastaListaPrecios");
            unCriterio.setOperacion(">=");
            unCriterio.setValor(fechaHoraHastaListaPreciosFiltro);
            lCriterio.add(unCriterio);
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

    public List<ListaPrecios> buscarListasPreciosSinBaja() {
        List<DTOCriterio> lCriterio = new ArrayList<DTOCriterio>();

        DTOCriterio unCriterio = new DTOCriterio();
        unCriterio.setAtributo("fechaHoraBajaListaPrecios");
        unCriterio.setOperacion("isNull");
        lCriterio.add(unCriterio);

        List listasPreciosResultado = FachadaPersistencia.getInstance().buscar("ListaPrecios", lCriterio);

        return listasPreciosResultado;
    }

    public ListaPrecios buscarUltimaLista() {
        List<DTOCriterio> lCriterio = new ArrayList<DTOCriterio>();

        DTOCriterio unCriterio = new DTOCriterio();
        unCriterio.setAtributo("fechaHoraBajaListaPrecios");
        unCriterio.setOperacion("isNull");
        lCriterio.add(unCriterio);
        return listasPreciosResultado;
    }

    public ListaPrecios buscarUltimaLista(String crit) {
//    BUSCA ULTIMA LISTA Y LA DEVUELVE
        List<DTOCriterio> lCriterio = new ArrayList<DTOCriterio>();
        DTOCriterio unCriterio = new DTOCriterio();
        if (crit == "noNulas") {
            unCriterio.setAtributo("fechaHoraBajaListaPrecios");
            unCriterio.setOperacion("=");
            unCriterio.setValor(null);
            lCriterio.add(unCriterio);
        }

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

        FachadaPersistencia.getInstance().iniciarTransaccion();
        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();

        dto.setAtributo("fechaHoraBajaListaPrecios");
        dto.setOperacion("=");
        dto.setValor(null);

        criterioList.add(dto);
        List objetoList = FachadaPersistencia.getInstance().buscar("ListaPrecios", criterioList);
        ListaPrecios ultimaListaPrecios = null;
        for (Object x : objetoList) {
            ListaPrecios listaPrecios = (ListaPrecios) x;
            if (ultimaListaPrecios == null || listaPrecios.getFechaHoraDesdeListaPrecios().after(ultimaListaPrecios.getFechaHoraDesdeListaPrecios())) {
                ultimaListaPrecios = listaPrecios;
            }
        }

        Timestamp ultimaFechaHoraDesde = ultimaListaPrecios.getFechaHoraDesdeListaPrecios();
        Timestamp nuevaFechaHoraDesde = nuevaListaPreciosDTO.getFechaHoraDesdeListaPrecios();
        Timestamp nuevaFechaHoraHasta = nuevaListaPreciosDTO.getFechaHoraHastaListaPrecios();

        if (nuevaFechaHoraHasta.before(nuevaFechaHoraDesde)) {
            throw new ListaPreciosException("La fecha hasta ingresada una fecha hasta menor a una fecha desde. Intente nuevamente.");
        }
        if (nuevaFechaHoraDesde.before(new Date())) {
            throw new ListaPreciosException("Las fecha desde ingresada es menor a la actual. Intentelo nuevamente.");
            throw new ListaPreciosException("Las fecha desde ingresada es menor a la fecha actual. Intentelo nuevamente.");
        }
        if (nuevaFechaHoraDesde.before(ultimaListaPrecios.getFechaHoraDesdeListaPrecios())) {
            throw new ListaPreciosException("Las fecha desde ingresada es menor a la ultima fecha desde. Intentelo nuevamente.");
        }

        Timestamp FechaHoraHasta = ultimaListaPrecios.getFechaHoraHastaListaPrecios();
        if (nuevaFechaHoraDesde.after(FechaHoraHasta) || nuevaFechaHoraDesde.before(FechaHoraHasta)) {
            ultimaListaPrecios.setFechaHoraHastaListaPrecios(nuevaFechaHoraDesde);
        }

        FachadaPersistencia.getInstance().guardar(ultimaListaPrecios);

        criterioList.clear();
        dto = new DTOCriterio();

        dto.setAtributo("codListaPrecios");
        dto.setOperacion("=");
        dto.setValor(nuevaListaPreciosDTO.getCodListaPrecios());

        criterioList.add(dto);
        List lListaPrecios = FachadaPersistencia.getInstance().buscar("ListaPrecios", criterioList);

        if (lListaPrecios.size() > 0) {
            throw new ListaPreciosException("El código de estado ya existe");
            throw new ListaPreciosException("El código ya existe");
        } else {
            ListaPrecios nuevaListaPrecios = new ListaPrecios();
            nuevaListaPrecios.setCodListaPrecios(nuevaListaPreciosDTO.getCodListaPrecios());
            nuevaListaPrecios.setFechaHoraDesdeListaPrecios(nuevaListaPreciosDTO.getFechaHoraDesdeListaPrecios());
            nuevaListaPrecios.setFechaHoraHastaListaPrecios(nuevaListaPreciosDTO.getFechaHoraHastaListaPrecios());

            criterioList.clear();
            dto = new DTOCriterio();

            dto.setAtributo("fechaHoraBajaTipoTramite");
            dto.setOperacion("=");
            dto.setValor(null);

            criterioList.add(dto);
            List objetoList2 = FachadaPersistencia.getInstance().buscar("TipoTramite", criterioList);

            for (Object x : objetoList2) {
                TipoTramite tipoTramite = (TipoTramite) x;
                TipoTramiteListaPrecios nuevoTipoTramiteListaPrecios = new TipoTramiteListaPrecios();
                nuevoTipoTramiteListaPrecios.setTipoTramite(tipoTramite);
                nuevoTipoTramiteListaPrecios.setPrecioTipoTramite(0);
                int codTipoTramite = tipoTramite.getCodTipoTramite();
                List<DetalleListaPreciosDTO> detalles = nuevaListaPreciosDTO.getDetalles();

//                isENCONTRADO ES PARA SABER SI SE PONE EL PRECIO DE LA ULTIMA LISTA (CUANDO NO HAY PRECIO EN LA IMPORTADA) O PONER EL PRECIO DE LA IMPORTADA
                boolean isEncontrado = false;
                for (DetalleListaPreciosDTO detalle : detalles) {
                    int codigoTT = detalle.getCodTipoTramite();
                    if (codTipoTramite == codigoTT) {
                        nuevoTipoTramiteListaPrecios.setPrecioTipoTramite(detalle.getNuevoPrecioTipoTramite());
                        isEncontrado = true;
                        break;
                    }
                }
                if (!isEncontrado) {
                    List<TipoTramiteListaPrecios> tipoTramiteListaPrecios = ultimaListaPrecios.getTipoTramiteListaPrecios();
                    for (TipoTramiteListaPrecios tipoTramiteListaPrecio : tipoTramiteListaPrecios) {
                        TipoTramite tipoTramite1 = tipoTramiteListaPrecio.getTipoTramite();
                        int codTipoTramite1 = tipoTramite1.getCodTipoTramite();
                        if (codTipoTramite1 == codTipoTramite) {
                            nuevoTipoTramiteListaPrecios.setPrecioTipoTramite(tipoTramiteListaPrecio.getPrecioTipoTramite());
                        }
                    }
                }
                FachadaPersistencia.getInstance().guardar(nuevoTipoTramiteListaPrecios);
                nuevaListaPrecios.addTipoTramiteListaPrecios(nuevoTipoTramiteListaPrecios);
            }

            FachadaPersistencia.getInstance().guardar(nuevaListaPrecios);
            FachadaPersistencia.getInstance().finalizarTransaccion();

        }

    }

    public void darDeBajaListaPrecios(int codigo) {
        FachadaPersistencia.getInstance().iniciarTransaccion();

        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();

        dto.setAtributo("codListaPrecios");
        dto.setOperacion("=");
        dto.setValor(codigo);

        criterioList.add(dto);

        ListaPrecios listaPreciosEncontrada = (ListaPrecios) FachadaPersistencia.getInstance().buscar("ListaPrecios", criterioList).get(0);
        ListaPrecios ultiLP = buscarUltimaLista();
        System.out.println(ultiLP.getCodListaPrecios());
        if (codigo == ultiLP.getCodListaPrecios()) {
            listaPreciosEncontrada.setFechaHoraBajaListaPrecios(new Timestamp(System.currentTimeMillis()));
        }
        Timestamp fd = listaPreciosEncontrada.getFechaHoraDesdeListaPrecios();
        Timestamp fh = listaPreciosEncontrada.getFechaHoraHastaListaPrecios();
        FachadaPersistencia.getInstance().iniciarTransaccion();
        FachadaPersistencia.getInstance().guardar(listaPreciosEncontrada);

        ListaPrecios ultiLP2 = buscarUltimaLista();
        System.out.println(ultiLP2.getCodListaPrecios());

        ultiLP2.setFechaHoraHastaListaPrecios(fh);
        FachadaPersistencia.getInstance().guardar(ultiLP2);
        FachadaPersistencia.getInstance().finalizarTransaccion();

            FachadaPersistencia.getInstance().guardar(nuevaListaPrecios);
            FachadaPersistencia.getInstance().finalizarTransaccion();
        }
    }

    public StreamedContent exportarListaPrecios(int codigo) {

        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();

//    BUSCA LA LISTA DE PRECIOS POR EL CODIGO EN PARAMETRO
        dto.setAtributo("codListaPrecios");
        dto.setOperacion("=");
        dto.setValor(codigo);

        criterioList.add(dto);

        ListaPrecios listaPreciosEncontrada = (ListaPrecios) FachadaPersistencia.getInstance().buscar("ListaPrecios", criterioList).get(0);

        try {
//            CREA EL ARCHIVO DE EXCEL SETEANDOLE LOS DATOS DE LA LISTA ENCONTRADA
            Workbook libro = new XSSFWorkbook();
            final String nombreArchivo = "./tmp.xlsx";
            Sheet hoja = libro.createSheet("Hoja 1");

            Row headerRow = hoja.createRow(0);
            Cell headerCell1 = headerRow.createCell(0);
            headerCell1.setCellValue("codTipoTramite");
            Cell headerCell2 = headerRow.createCell(1);
            headerCell2.setCellValue("nombreTipoTramite");
            Cell headerCell3 = headerRow.createCell(2);
            headerCell3.setCellValue("descripcionTipoTramite");
            Cell headerCell4 = headerRow.createCell(3);
            headerCell4.setCellValue("precioTipoTramite");

            List<TipoTramiteListaPrecios> detalles = listaPreciosEncontrada.getTipoTramiteListaPrecios();

            for (int j = 0; j < detalles.size(); j++) {
                Row dataRow = hoja.createRow(j + 1);
                Cell cell1 = dataRow.createCell(0);
                cell1.setCellValue(detalles.get(j).getTipoTramite().getCodTipoTramite());

                Cell cell2 = dataRow.createCell(1);
                cell2.setCellValue(detalles.get(j).getTipoTramite().getNombreTipoTramite());

                Cell cell3 = dataRow.createCell(2);
                cell3.setCellValue(detalles.get(j).getTipoTramite().getDescripcionTipoTramite());

                Cell cell4 = dataRow.createCell(3);
                cell4.setCellValue(detalles.get(j).getPrecioTipoTramite());

            }

            FileOutputStream outputStream;
            outputStream = new FileOutputStream(nombreArchivo);
            libro.write(outputStream);
            libro.close();
            InputStream ie = new FileInputStream(nombreArchivo);
            fileD = DefaultStreamedContent.builder()
                    .name("ListaPrecios" + listaPreciosEncontrada.getCodListaPrecios() + ".xlsx")
                    .contentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                    .stream(() -> ie)
                    .build();

        } catch (IOException ex) {
            Logger.getLogger(ExcelFileUI.class
            Logger.getLogger(UIABCListaPreciosLista.class
                    .getName()).log(Level.SEVERE, null, ex);
            Messages.create(ex.getMessage()).error().add();
        }
        return fileD;
    }

    public void darDeBajaListaPrecios(int codigo) {
        FachadaPersistencia.getInstance().iniciarTransaccion();

        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();

//    BUSCA LA LISTA DE PRECIOS POR EL CODIGO EN PARAMETRO
        dto.setAtributo("codListaPrecios");
        dto.setOperacion("=");
        dto.setValor(codigo);

        criterioList.add(dto);

        ListaPrecios listaPreciosEncontrada = (ListaPrecios) FachadaPersistencia.getInstance().buscar("ListaPrecios", criterioList).get(0);
        ListaPrecios ultiLP = buscarUltimaLista("noNulas");
//        VERIFICA SI ES LA LISTA ENCONTRADA ES LA ULTIMA LISTA DE PRECIOS
        if (codigo == ultiLP.getCodListaPrecios()) {
//            LE SETEA LA FECHAHORABAJA
            listaPreciosEncontrada.setFechaHoraBajaListaPrecios(new Timestamp(System.currentTimeMillis()));
        }

        Timestamp fh = listaPreciosEncontrada.getFechaHoraHastaListaPrecios();
        FachadaPersistencia.getInstance().iniciarTransaccion();
        FachadaPersistencia.getInstance().guardar(listaPreciosEncontrada);

//        VUELVE A BUSCAR LA ULTIMA LISTA PARA SETEARLE LA FECHAHORAHASTA IGUAL A FECHAHORAHASTA DE LA LISTA QUE DIMOS DE BAJA
        ListaPrecios ultiLP2 = buscarUltimaLista("noNulas");

        ultiLP2.setFechaHoraHastaListaPrecios(fh);
        FachadaPersistencia.getInstance().guardar(ultiLP2);
        FachadaPersistencia.getInstance().finalizarTransaccion();

    }

}
