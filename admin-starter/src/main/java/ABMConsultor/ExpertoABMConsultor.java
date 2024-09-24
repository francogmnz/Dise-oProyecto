package ABMConsultor;

import ABMConsultor.dtos.DTOConsultor;
import ABMConsultor.dtos.DTOIngresoDatos;
import ABMConsultor.dtos.DTOModificacionDatos;
import ABMConsultor.dtos.DTOModificacionDatosIn;
import ABMConsultor.exceptions.ConsultorException;
import entidades.Consultor;
import entidades.Tramite;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import utils.DTOCriterio;
import utils.FachadaPersistencia;

public class ExpertoABMConsultor {

    public List<DTOConsultor> buscarConsultores(int legajoConsultor, String nombreConsultor, int numMaximoTramites) {
        List<DTOCriterio> lCriterio = new ArrayList<DTOCriterio>();
        if (legajoConsultor > 0) {
            DTOCriterio unCriterio = new DTOCriterio();
            unCriterio.setAtributo("legajoConsultor");
            unCriterio.setOperacion("=");
            unCriterio.setValor(legajoConsultor);
            lCriterio.add(unCriterio);
        }
        if (nombreConsultor.trim().length() > 0) {
            DTOCriterio unCriterio = new DTOCriterio();
            unCriterio.setAtributo("nombreConsultor");
            unCriterio.setOperacion("like");
            unCriterio.setValor(nombreConsultor);
            lCriterio.add(unCriterio);
        }

        List objetoList = FachadaPersistencia.getInstance().buscar("Consultor", lCriterio);
        List<DTOConsultor> consultoresResultado = new ArrayList<>();
        for (Object x : objetoList) {
            Consultor consultor = (Consultor) x;
            DTOConsultor dtoConsultor = new DTOConsultor();
            dtoConsultor.setLegajoConsultor(consultor.getLegajoConsultor());
            dtoConsultor.setNombreConsultor(consultor.getNombreConsultor());
            dtoConsultor.setNumMaximoTramites(consultor.getNumMaximoTramites());
            dtoConsultor.setFechaHoraBajaConsultor(consultor.getFechaHoraBajaConsultor());
            consultoresResultado.add(dtoConsultor);
        }
        return consultoresResultado;
    }

    public void agregarConsultor(DTOIngresoDatos nuevoConsultorDTO) throws ConsultorException {
        FachadaPersistencia.getInstance().iniciarTransaccion();

        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();

        dto.setAtributo("legajoConsultor");
        dto.setOperacion("=");
        dto.setValor(nuevoConsultorDTO.getLegajoConsultor());

        criterioList.add(dto);
        List lConsultor = FachadaPersistencia.getInstance().buscar("Consultor", criterioList);

        if (lConsultor.size() > 0) {
            throw new ConsultorException("El código de consultor ya existe");
        } else {
            Consultor consultor = new Consultor();
            consultor.setLegajoConsultor(nuevoConsultorDTO.getLegajoConsultor());
            consultor.setNombreConsultor(nuevoConsultorDTO.getNombreConsultor());
            consultor.setNumMaximoTramites(nuevoConsultorDTO.getNumMaximoTramites());

            FachadaPersistencia.getInstance().guardar(consultor);
            FachadaPersistencia.getInstance().finalizarTransaccion();
        }
    }

    public DTOModificacionDatos buscarConsultorAModificar(int legajoConsultor) {
        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();

        dto.setAtributo("legajoConsultor");
        dto.setOperacion("=");
        dto.setValor(legajoConsultor);
        

        criterioList.add(dto);

        Consultor consultorEncontrado = (Consultor) FachadaPersistencia.getInstance().buscar("Consultor", criterioList).get(0);

        DTOModificacionDatos dtoModificacionDatos = new DTOModificacionDatos();
        dtoModificacionDatos.setNombreConsultor(consultorEncontrado.getNombreConsultor());
        dtoModificacionDatos.setLegajoConsultor(consultorEncontrado.getLegajoConsultor());
        dtoModificacionDatos.setNumMaximoTramites(consultorEncontrado.getNumMaximoTramites());
        return dtoModificacionDatos;
    }

    public void modificarConsultor(DTOModificacionDatosIn dtoModificacionDatosIn) {
        FachadaPersistencia.getInstance().iniciarTransaccion();

        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();

        dto.setAtributo("legajoConsultor");
        dto.setOperacion("=");
        dto.setValor(dtoModificacionDatosIn.getLegajoConsultor());

        criterioList.add(dto);

        Consultor consultorEncontrado = (Consultor) FachadaPersistencia.getInstance().buscar("Consultor", criterioList).get(0);

        consultorEncontrado.setLegajoConsultor(dtoModificacionDatosIn.getLegajoConsultor());
        consultorEncontrado.setNombreConsultor(dtoModificacionDatosIn.getNombreConsultor());
        consultorEncontrado.setNumMaximoTramites(dtoModificacionDatosIn.getNumMaximoTramites());

        FachadaPersistencia.getInstance().guardar(consultorEncontrado);
        FachadaPersistencia.getInstance().finalizarTransaccion();
    }

    public void darDeBajaConsultor(int legajoConsultor) throws ConsultorException {
        FachadaPersistencia.getInstance().iniciarTransaccion();

        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();

        dto.setAtributo("legajoConsultor");
        dto.setOperacion("=");
        dto.setValor(legajoConsultor);

        criterioList.add(dto);
        DTOCriterio dto2 = new DTOCriterio();

        dto2.setAtributo("fechaHoraBajaConsultor");
        dto2.setOperacion("=");
        dto2.setValor(null);

        criterioList.add(dto2);
        Consultor consultorEncontrado = (Consultor) FachadaPersistencia.getInstance().buscar("Consultor", criterioList).get(0);
        int legajoEncontrado = consultorEncontrado.getLegajoConsultor();

        criterioList.clear();

        dto = new DTOCriterio();

        dto.setAtributo("fechaFinTramite");
        dto.setOperacion("!=");
        dto.setValor(null);

        criterioList.add(dto);

        List objetoList = FachadaPersistencia.getInstance().buscar("Tramite", criterioList);

        for (Object x : objetoList) {

            Tramite tramite = (Tramite) x;
            Consultor consultor = tramite.getConsultor();
            int legajo = consultor.getLegajoConsultor();

            if (legajoEncontrado == legajo) {
                throw new ConsultorException("Consultor no puede darse de baja por estar asignado en al menos a un tramite");
            }

        }

        consultorEncontrado.setFechaHoraBajaConsultor(new Timestamp(System.currentTimeMillis()));

        FachadaPersistencia.getInstance().guardar(consultorEncontrado);
        FachadaPersistencia.getInstance().finalizarTransaccion();
    }
}
