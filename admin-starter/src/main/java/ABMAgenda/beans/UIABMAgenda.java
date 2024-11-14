package ABMAgenda.beans;

import ABMAgenda.ControladorABMAgenda;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.GregorianCalendar;
import ABMAgenda.dtos.AgendaDTO;
import ABMAgenda.dtos.AgendaDTOIn;
import ABMAgenda.dtos.DTOConsultor;
import ABMAgenda.dtos.DTOConsultorIn;
import ABMAgenda.dtos.DTOConsultorListaIzq;
import ABMAgenda.dtos.DTODatosInicialesAgenda;
import ABMAgenda.dtos.DTODatosInicialesAgendaIn;
import ABMAgenda.exceptions.AgendaException;
import jakarta.faces.context.FacesContext;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import java.util.Objects;

@Named("uiABMAgendaBean")
@ViewScoped
public class UIABMAgenda implements Serializable {

    private int mes;
    private int anio;
    private int semanaActual;
    private int anioActual;
    private int mesActual;
    private ControladorABMAgenda cont = new ControladorABMAgenda();
    private List<SemanaIU> semanasLi = new ArrayList<>();
    private List<DTOConsultorListaIzq> consultores = new ArrayList<>();

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public List<SemanaIU> getSemanasLi() {
        return semanasLi;
    }

    public void setSemanasLi(List<SemanaIU> semanasLi) {
        this.semanasLi = semanasLi;
    }

    public int getSemanaActual() {
        return semanaActual;
    }

    public void setSemanaActual(int semana) {
        this.semanaActual = semana;
    }

    public int getAnioActual() {
        return anioActual;
    }

    public void setAnioActual(int anioActual) {
        this.anioActual = anioActual;
    }

    public int getMesActual() {
        return mesActual;
    }

    public void setMesActual(int mesActual) {
        this.mesActual = mesActual;
    }
    
    
    public UIABMAgenda() throws AgendaException, Exception {
        consultoresPorSemana.clear();
        // Obtener mes y año desde los parámetros de la URL
        cargarConsultoresActivos();
        this.semanaActual = cont.calcularSemanaActual();
        this.anioActual = cont.calcularAnioActual();
        this.mesActual = cont.calcularMesActual();

        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        String mesParam = params.get("mes");
        String anioParam = params.get("anio");

        if (mesParam != null && anioParam != null) {
            this.mes = Integer.parseInt(mesParam);
            this.anio = Integer.parseInt(anioParam);
        } else {
            // Valores por defecto si no se envían parámetros
            Calendar calendario = new GregorianCalendar();
            this.mes = calendario.get(Calendar.MONTH) + 1;
            this.anio = calendario.get(Calendar.YEAR);

        }
        cargarSemanas();
        getConsultoresPorSemanaJson();
    }

    public void cargarSemanas() throws AgendaException {
        semanasLi.clear();
        consultoresPorSemana.clear(); // Limpiamos para evitar datos duplicados

        // Obtener los datos iniciales de la agenda para el mes y año actuales
        DTODatosInicialesAgenda dtoDatosIni = cont.obtenerAgendaConsultor(mes, anio);

        for (AgendaDTO agendaDTO : dtoDatosIni.getAgendaDTO()) {
            SemanaIU semana = new SemanaIU();
            semana.setNroSemana(agendaDTO.getSemAgendaConsultor());
            semana.setMes(mes);
            semana.setAnio(anio);

            // Asignar fechaDesdeSemana y fechaHastaSemana directamente desde agendaDTO
            semana.setFechaDesdeSemana(agendaDTO.getFechaDesdeSemana());
            semana.setFechaHastaSemana(agendaDTO.getFechaHastaSemana());

            // Añadir cada semana a la lista de semanas
            semanasLi.add(semana);

            // Cargar los consultores asignados a cada semana en el mapa `consultoresPorSemana`
            List<DTOConsultor> consultoresEnSemana = new ArrayList<>(agendaDTO.getConsultores() != null ? agendaDTO.getConsultores() : new ArrayList<>());
            consultoresPorSemana.put(agendaDTO.getSemAgendaConsultor(), consultoresEnSemana);
        }
    }

    public String getConsultoresPorSemanaJson() {
        // Convierte el objeto `consultoresPorSemana` a JSON
        String jsonString = new Gson().toJson(consultoresPorSemana);

        // Imprimir el JSON generado para verificar que se generó correctamente
        return jsonString;
    }
    
        public boolean esFechaPasada() {
        if (anio < anioActual) {
            return true;
        } else if (anio == anioActual && mes < mesActual) {
            return true;
        }
        return false;
    }

    public void cargarConsultoresActivos() throws Exception {
        try {
            List<DTOConsultorListaIzq> consultoresActivos = cont.obtenerConsultoresActivos();
            List<UIListaIzq> consultoresUI = new ArrayList<>();

            for (DTOConsultorListaIzq dto : consultoresActivos) {
                UIListaIzq consultorUI = new UIListaIzq();
                consultorUI.setLegajoConsultor(dto.getLegajoConsultor());
                consultorUI.setNombreConsultor(dto.getNombreConsultor());
                consultoresUI.add(consultorUI); // Agregar a la lista de consultores de la UI

            }

            // Asignar la lista de consultores a la propiedad consultores
            this.consultores = consultoresActivos;

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void actualizar() throws Exception {
        cargarConsultoresActivos();
        // Otras acciones de actualización si es necesario
    }

    public List<DTOConsultorListaIzq> getConsultores() {
        return consultores;
    }

    public void setConsultores(List<DTOConsultorListaIzq> consultores) {
        this.consultores = consultores;
    }

    private Map<Integer, List<DTOConsultor>> consultoresPorSemana = new HashMap<>();

    public Map<Integer, List<DTOConsultor>> getConsultoresPorSemana() {
        return consultoresPorSemana;
    }

    public DTOConsultor obtenerConsultorDeLista(int consultorId) {
        for (DTOConsultorListaIzq dtoConsultorListaIzq : consultores) {
            // Comparar int con Integer usando el método intValue()
            if (dtoConsultorListaIzq.getLegajoConsultor() == consultorId) {
                // Si encuentra el consultor, crea un DTOConsultor a partir de DTOConsultorListaIzq
                DTOConsultor consultor = new DTOConsultor();
                consultor.setLegajoConsultor(dtoConsultorListaIzq.getLegajoConsultor());
                consultor.setNombreConsultor(dtoConsultorListaIzq.getNombreConsultor());
                return consultor;
            }
        }
        // Si no se encuentra, devuelve null
        return null;
    }

    public void guardarAsignaciones() throws Exception {
        consultoresPorSemana.clear();
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String assignmentsData = params.get("assignmentsData");

        if (assignmentsData != null && !assignmentsData.isEmpty()) {
            Gson gson = new Gson();
            Map<String, List<String>> asignaciones = gson.fromJson(assignmentsData, Map.class);

            // Procesar y actualizar directamente `consultoresPorSemana`
            if (asignaciones != null && !asignaciones.isEmpty()) {
                asignaciones.forEach((semanaIdStr, consultoresIds) -> {
                    int semanaId = Integer.parseInt(semanaIdStr);

                    // Obtener o inicializar la lista de consultores de la semana
                    List<DTOConsultor> consultoresEnSemana = consultoresPorSemana.computeIfAbsent(semanaId, k -> new ArrayList<>());

                    // Procesar consultores en una sola operación usando Stream
                    consultoresIds.stream()
                            .map(Integer::parseInt)
                            .map(this::obtenerConsultorDeLista)
                            .filter(Objects::nonNull) // Solo agregar consultores válidos
                            .forEach(consultoresEnSemana::add);
                });

                // Persistir las asignaciones procesadas
                persistirAsignaciones();
            }
        }
    }

    private void persistirAsignaciones() throws Exception {
        try {
            DTODatosInicialesAgendaIn datosInicialesAgendain = new DTODatosInicialesAgendaIn();

            for (Map.Entry<Integer, List<DTOConsultor>> entry : consultoresPorSemana.entrySet()) {
                int nroSemana = entry.getKey();
                List<DTOConsultor> consultores = entry.getValue();

                AgendaDTOIn agendaDTOIn = new AgendaDTOIn();
                agendaDTOIn.setSemAgendaConsultor(nroSemana);
                agendaDTOIn.setMesAgendaConsultor(mes);
                agendaDTOIn.setAñoAgendaConsultor(anio);

                // Obtener la instancia de `SemanaIU` correspondiente desde `semanasLi`
                SemanaIU semana = semanasLi.stream()
                        .filter(s -> s.getNroSemana() == nroSemana)
                        .findFirst()
                        .orElse(null);

                if (semana != null) {
                    // Asignar fechaDesdeSemana y fechaHastaSemana
                    agendaDTOIn.setFechaDesdeSemana(semana.getFechaDesdeSemana());
                    agendaDTOIn.setFechaHastaSemana(semana.getFechaHastaSemana());
                }

                // Establecer la lista de consultores asociados a la semana
                List<DTOConsultorIn> consultoresDTO = new ArrayList<>();
                for (DTOConsultor consultor : consultores) {
                    DTOConsultorIn dtoConsultorIn = new DTOConsultorIn();
                    dtoConsultorIn.setLegajoConsultor(consultor.getLegajoConsultor());
                    dtoConsultorIn.setNombreConsultor(consultor.getNombreConsultor());
                    consultoresDTO.add(dtoConsultorIn);
                }
                agendaDTOIn.setConsultores(consultoresDTO);

                // Agregar `AgendaDTOIn` a `DTODatosInicialesAgendaIn`
                datosInicialesAgendain.addAgendaDTO(agendaDTOIn);
            }

            // Llamar al controlador para persistir `DTODatosInicialesAgendaIn`
            cont.persistirDatosInicialesAgenda(datosInicialesAgendain);

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void asignarConsultorASemana(int semana, DTOConsultor consultor) {
        if (!consultoresPorSemana.containsKey(semana)) {
            consultoresPorSemana.put(semana, new ArrayList<>());
        }
        List<DTOConsultor> consultoresEnSemana = consultoresPorSemana.get(semana);

        // Verificar si el consultor ya está asignado a la semana
        for (DTOConsultor c : consultoresEnSemana) {
            if (c.getLegajoConsultor() == consultor.getLegajoConsultor()) {
                return;
            }
        }
        // Si no está asignado, lo agrega a la semana
        consultoresEnSemana.add(consultor);
    }

}
