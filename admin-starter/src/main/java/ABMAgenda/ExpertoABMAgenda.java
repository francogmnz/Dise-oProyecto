package ABMAgenda;

import ABMAgenda.dtos.AgendaDTO;
import ABMAgenda.dtos.AgendaDTOIn;
import ABMAgenda.dtos.DTOConsultor;
import ABMAgenda.dtos.DTOConsultorIn;
import ABMAgenda.dtos.DTOConsultorListaIzq;
import ABMAgenda.dtos.DTODatosInicialesAgenda;
import ABMAgenda.dtos.DTODatosInicialesAgendaIn;
import entidades.Consultor;
import ABMAgenda.exceptions.AgendaException;
import entidades.AgendaConsultor;
import entidades.Tramite;
import utils.DTOCriterio;
import utils.FachadaPersistencia;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.hibernate.Hibernate;

public class ExpertoABMAgenda {

    public DTODatosInicialesAgenda obtenerAgendaConsultor(int mes, int anio) throws AgendaException {
        DTODatosInicialesAgenda dtoDatosIni = new DTODatosInicialesAgenda();

        // Obtener la lista de consultores activos y setear en el dtoDatosIni
        List<DTOConsultorListaIzq> consultoresActivos = buscarConsultoresActivos();
        dtoDatosIni.setDtoConsultorListaIzq(consultoresActivos);

        // Crear una lista para almacenar los números de semana
        List<Integer> semanas = new ArrayList<>();

        // Obtener la fecha del primer día del mes
        LocalDate firstDayOfMonth = LocalDate.of(anio, mes, 1);

        // Obtener la fecha del primer lunes del mes
        LocalDate firstMonday = firstDayOfMonth.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));

        // Asegurarnos de que las semanas comiencen desde la primera semana del mes
        if (firstMonday.getMonthValue() != mes) {
            firstMonday = firstDayOfMonth.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
        }

        // Iterar a través de las semanas del mes
        LocalDate currentMonday = firstMonday;
        WeekFields weekFields = WeekFields.of(Locale.getDefault());

        // Mientras el currentMonday esté en el mes actual
        while (currentMonday.getMonthValue() == mes) {
            int weekNumber = currentMonday.get(weekFields.weekOfYear());
            if (!semanas.contains(weekNumber)) { // Evitar duplicados
                semanas.add(weekNumber);
            }
            // Pasar al siguiente lunes
            currentMonday = currentMonday.plusWeeks(1);
        }

        // Ahora que tenemos los números de semana, iteramos para asignarlos a la agenda
        for (Integer semana : semanas) {
            // Calcular fecha de inicio y fin de la semana
            LocalDate inicioSemana = firstDayOfMonth.with(weekFields.weekOfYear(), semana).with(DayOfWeek.MONDAY);
            LocalDate finSemana = inicioSemana.plusDays(6);

            // Buscar si existe una AgendaConsultor para la semana, mes y año actual
            List<DTOCriterio> criterios = new ArrayList<>();

            // Criterio para el mes
            DTOCriterio criterioMes = new DTOCriterio();
            criterioMes.setAtributo("mesAgendaConsultor");
            criterioMes.setOperacion("=");
            criterioMes.setValor(mes);
            criterios.add(criterioMes);

            // Criterio para el año
            DTOCriterio criterioAnio = new DTOCriterio();
            criterioAnio.setAtributo("añoAgendaConsultor");
            criterioAnio.setOperacion("=");
            criterioAnio.setValor(anio);
            criterios.add(criterioAnio);

            // Criterio para la semana
            DTOCriterio criterioSemana = new DTOCriterio();
            criterioSemana.setAtributo("semAgendaConsultor");
            criterioSemana.setOperacion("=");
            criterioSemana.setValor(semana);
            criterios.add(criterioSemana);

            // Realizar la búsqueda en la base de datos
            List<Object> resultados = FachadaPersistencia.getInstance().buscar("AgendaConsultor", criterios);

            AgendaDTO agendaDTO = new AgendaDTO();

            // Verificar si existe una agenda para la semana actual
            if (resultados != null && !resultados.isEmpty()) {
                // Obtener la AgendaConsultor existente
                AgendaConsultor agendaConsultor = (AgendaConsultor) resultados.get(0);

                // Asignar la semana y fechas desde la agenda existente
                agendaDTO.setSemAgendaConsultor(semana);
                agendaDTO.setFechaDesdeSemana(agendaConsultor.getFechaDesdeSemana());
                agendaDTO.setFechaHastaSemana(agendaConsultor.getFechaHastaSemana());

                // Obtener los consultores asociados a la agenda
                for (Consultor consultor : agendaConsultor.getConsultores()) {
                    DTOConsultor dtoConsultor = new DTOConsultor();
                    dtoConsultor.setLegajoConsultor(consultor.getLegajoConsultor());
                    dtoConsultor.setNombreConsultor(consultor.getNombreConsultor());
                    agendaDTO.addDTOConsultor(dtoConsultor);
                }

            } else {
                // Asignar la semana y fechas calculadas en caso de que no exista la agenda
                agendaDTO.setSemAgendaConsultor(semana);
                agendaDTO.setFechaDesdeSemana(java.sql.Date.valueOf(inicioSemana));
                agendaDTO.setFechaHastaSemana(java.sql.Date.valueOf(finSemana));
            }

            // Agregar el objeto AgendaDTO a dtoDatosIni
            dtoDatosIni.addAgendaDTO(agendaDTO);
        }

        return dtoDatosIni;
    }

    public int calcularSemanaActual() {
        // Obtener la fecha de hoy
        LocalDate hoy = LocalDate.now();

        // Obtener el lunes de la semana actual (o el lunes más cercano)
        LocalDate lunesActual = hoy.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        // Obtener el número de semana en base a WeekFields
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int semanaActual = lunesActual.get(weekFields.weekOfYear());

        return semanaActual;
    }

    public int calcularAnioActual() {
        // Obtener la fecha de hoy
        LocalDate hoy = LocalDate.now();

        // Extraer el año de la fecha actual
        int anioActual = hoy.getYear();

        return anioActual;
    }
        public int calcularMesActual() {
        // Obtener la fecha de hoy
        LocalDate hoy = LocalDate.now();

        // Extraer el mes de la fecha actual
        int mesActual = hoy.getMonthValue();

        return mesActual;
    }


    public List<DTOConsultorListaIzq> buscarConsultoresActivos() throws AgendaException {

        // Crear el criterio para buscar consultores activos
        List<DTOCriterio> lcriterioConsultores = new ArrayList<>();
        DTOCriterio criterioConsultores = new DTOCriterio();
        criterioConsultores.setAtributo("fechaHoraBajaConsultor"); // Campo que indica si el consultor está activo
        criterioConsultores.setOperacion("=");
        criterioConsultores.setValor(null); // Consultores activos no tienen fecha de baja
        lcriterioConsultores.add(criterioConsultores);

        // Buscar los consultores activos en la base de datos
        List consultoresActivos = FachadaPersistencia.getInstance().buscar("Consultor", lcriterioConsultores);

        // Convertir los resultados a DTOConsultorListaIzq
        List<DTOConsultorListaIzq> consultoresActivosDTO = new ArrayList<>();
        for (Object objConsultor : consultoresActivos) {
            Consultor consultor = (Consultor) objConsultor;

            // Verificar si el consultor ha alcanzado su número máximo de trámites
            int numTramitesActuales = contarTramites(consultor.getLegajoConsultor()); // Nuevo método para contar trámites
            if (numTramitesActuales >= consultor.getNumMaximoTramites()) {
                // Saltar este consultor si ha alcanzado su número máximo de trámites
                continue;
            }

            DTOConsultorListaIzq dtoConsultorListaIzq = new DTOConsultorListaIzq();
            dtoConsultorListaIzq.setLegajoConsultor(consultor.getLegajoConsultor());
            dtoConsultorListaIzq.setNombreConsultor(consultor.getNombreConsultor());

            // Settear el nroMaximoTramites en el DTO
            dtoConsultorListaIzq.setNumMaximoTramites(consultor.getNumMaximoTramites());

            consultoresActivosDTO.add(dtoConsultorListaIzq);
        }
        return consultoresActivosDTO;
    }

    private int contarTramites(int legajoConsultor) throws AgendaException {
        // Crear los criterios de búsqueda para obtener todos los trámites activos (fechaFinTramite y fechaAnulacionTramite son null)
        List<DTOCriterio> criterios = new ArrayList<>();

        DTOCriterio criterioFechaFinNula = new DTOCriterio();
        criterioFechaFinNula.setAtributo("fechaFinTramite");
        criterioFechaFinNula.setOperacion("null");  // Solo trámites activos
        criterios.add(criterioFechaFinNula);

        DTOCriterio criterioFechaAnulacionNula = new DTOCriterio();
        criterioFechaAnulacionNula.setAtributo("fechaAnulacionTramite");
        criterioFechaAnulacionNula.setOperacion("null");  // Solo trámites no anulados
        criterios.add(criterioFechaAnulacionNula);

        // Buscar todos los trámites activos en la base de datos
        List<Object> tramitesActivos = FachadaPersistencia.getInstance().buscar("Tramite", criterios);

        // Contador de trámites para el consultor específico
        int contadorTramites = 0;

        // Filtrar trámites por el consultor con el legajo especificado
        for (Object tramiteObj : tramitesActivos) {
            Tramite tramite = (Tramite) tramiteObj;
            Consultor consultorTramite = tramite.getConsultor();

            // Si el consultor asociado al trámite tiene el mismo legajo, incrementamos el contador
            if (consultorTramite != null && consultorTramite.getLegajoConsultor() == legajoConsultor) {
                contadorTramites++;
            }
        }

        return contadorTramites;
    }

    public Map<String, Date> obtenerPrimerYUltimoDiaDelMes(int mes, int anio) {
        Map<String, Date> fechas = new HashMap<>();

        // Calcular el primer día del mes
        Calendar primerDia = new GregorianCalendar(anio, mes - 1, 1);
        fechas.put("primerDia", primerDia.getTime());

        // Calcular el último día del mes
        int ultimoDiaMes = primerDia.getActualMaximum(Calendar.DAY_OF_MONTH);
        Calendar ultimoDia = new GregorianCalendar(anio, mes - 1, ultimoDiaMes);
        fechas.put("ultimoDia", ultimoDia.getTime());

        return fechas;
    }

    public boolean existeAgendaParaMesYAnio(int mes, int anio) {

        List<DTOCriterio> criterios = new ArrayList<>();

        // Crear el criterio para buscar por mes
        DTOCriterio criterioMes = new DTOCriterio();
        criterioMes.setAtributo("mesAgendaConsultor");
        criterioMes.setOperacion("=");
        criterioMes.setValor(mes);
        criterios.add(criterioMes);

        // Crear el criterio para buscar por año
        DTOCriterio criterioAnio = new DTOCriterio();
        criterioAnio.setAtributo("añoAgendaConsultor");
        criterioAnio.setOperacion("=");
        criterioAnio.setValor(anio);
        criterios.add(criterioAnio);

        // Realizar la búsqueda en la base de datos
        List<Object> resultados = FachadaPersistencia.getInstance().buscar("AgendaConsultor", criterios);

        // Si la lista de resultados no está vacía, significa que hay al menos una agenda existente
        return resultados != null && !resultados.isEmpty();
    }

    public void persistirDatosInicialesAgenda(DTODatosInicialesAgendaIn datosInicialesAgendaIn) throws AgendaException {
        // Ordenar los datos iniciales de la agenda por semana
        Collections.sort(datosInicialesAgendaIn.getAgendaDTO(), Comparator.comparingInt(AgendaDTOIn::getSemAgendaConsultor));

        try {
            // Iniciar la transacción
            FachadaPersistencia.getInstance().iniciarTransaccion();

            // Iterar sobre cada AgendaDTOIn en la lista de DTODatosInicialesAgendaIn
            for (AgendaDTOIn agendaDTOIn : datosInicialesAgendaIn.getAgendaDTO()) {

                // Validar si la semana pertenece al pasado o al presente
                validarSemana(agendaDTOIn.getAñoAgendaConsultor(), agendaDTOIn.getSemAgendaConsultor());

                // Crear o encontrar la entidad AgendaConsultor para la semana
                AgendaConsultor agendaConsultor = encontrarOcrearAgendaConsultor(agendaDTOIn);

                // Validar y actualizar consultores
                agendaConsultor = validarYActualizarConsultores(agendaConsultor, agendaDTOIn.getConsultores());

                // Persistir la entidad AgendaConsultor
                guardarAgendaConsultor(agendaConsultor);
            }

            // Finalizar la transacción
            FachadaPersistencia.getInstance().finalizarTransaccion();

        } catch (AgendaException e) {
            // Revertir la transacción si hay una excepción
            FachadaPersistencia.getInstance().finalizarTransaccion();
            throw new AgendaException(e.getMessage());
        }
    }

    private void guardarAgendaConsultor(AgendaConsultor agendaConsultor) {
        FachadaPersistencia.getInstance().guardar(agendaConsultor);
    }

    public AgendaConsultor validarYActualizarConsultores(AgendaConsultor agendaConsultor, List<DTOConsultorIn> consultoresDTO) throws AgendaException {
        // Limpia los consultores en agendaConsultor según las reglas de tramitación y duplicidad
        limpiarConsultoresInnecesarios(agendaConsultor, consultoresDTO);

        // Agrega consultores nuevos desde consultoresDTO sin duplicar
        agregarConsultoresDesdeDTO(agendaConsultor, consultoresDTO);

        // Retornar la entidad actualizada sin persistir
        return agendaConsultor;
    }

    private void limpiarConsultoresInnecesarios(AgendaConsultor agendaConsultor, List<DTOConsultorIn> consultoresDTO) throws AgendaException {
        Set<Integer> legajosDTO = consultoresDTO.stream()
                .map(DTOConsultorIn::getLegajoConsultor)
                .collect(Collectors.toSet());

        List<Consultor> consultoresAsignados = new ArrayList<>(agendaConsultor.getConsultores());

        for (Consultor consultor : consultoresAsignados) {
            if (!legajosDTO.contains(consultor.getLegajoConsultor())) {
                if (tieneTramites(consultor.getLegajoConsultor(), agendaConsultor.getFechaDesdeSemana(), agendaConsultor.getFechaHastaSemana())) {
                    // Consultor tiene trámites; se mantiene en la lista de agendaConsultor
                    continue;
                } else {
                    // Consultor no tiene trámites; se elimina de agendaConsultor
                    agendaConsultor.getConsultores().remove(consultor);
                }
            }
        }
    }

    private void agregarConsultoresDesdeDTO(AgendaConsultor agendaConsultor, List<DTOConsultorIn> consultoresDTO) throws AgendaException {
        List<Consultor> consultoresAsignados = new ArrayList<>(agendaConsultor.getConsultores());

        for (DTOConsultorIn consultorDTO : consultoresDTO) {
            boolean existe = consultoresAsignados.stream()
                    .anyMatch(c -> c.getLegajoConsultor() == consultorDTO.getLegajoConsultor());

            if (!existe) {
                Consultor nuevoConsultor = buscarConsultorPorLegajo(consultorDTO.getLegajoConsultor());
                if (nuevoConsultor != null) {
                    agendaConsultor.getConsultores().add(nuevoConsultor);
                }
            }
        }
    }

    public boolean tieneTramites(int legajoConsultor, Date fechaDesdeSemana, Date fechaHastaSemana) throws AgendaException {
        // Buscar el consultor por legajo para verificar si existe
        Consultor consultor = buscarConsultorPorLegajo(legajoConsultor);

        if (consultor == null) {
            System.out.println("No se encontró un consultor activo con el legajo " + legajoConsultor);
            return false;
        }

        // Preparar los criterios de búsqueda para los trámites en el rango de fechas y sin fecha de finalización
        List<DTOCriterio> criterios = new ArrayList<>();

        DTOCriterio criterioFechaFinNula = new DTOCriterio();
        criterioFechaFinNula.setAtributo("fechaFinTramite");
        criterioFechaFinNula.setOperacion("null");
        criterios.add(criterioFechaFinNula);

        DTOCriterio criterioFechaInicioDesde = new DTOCriterio();
        criterioFechaInicioDesde.setAtributo("fechaInicioTramite");
        criterioFechaInicioDesde.setOperacion(">=");
        criterioFechaInicioDesde.setValor(fechaDesdeSemana);
        criterios.add(criterioFechaInicioDesde);

        DTOCriterio criterioFechaInicioHasta = new DTOCriterio();
        criterioFechaInicioHasta.setAtributo("fechaInicioTramite");
        criterioFechaInicioHasta.setOperacion("<=");
        criterioFechaInicioHasta.setValor(fechaHastaSemana);
        criterios.add(criterioFechaInicioHasta);

        // Realizar la búsqueda de trámites en la base de datos según los criterios establecidos
        List<Object> tramites = FachadaPersistencia.getInstance().buscar("Tramite", criterios);

        // Revisar cada trámite para ver si está relacionado con el consultor que tiene el mismo legajo
        for (Object tramiteObj : tramites) {
            Tramite tramite = (Tramite) tramiteObj;

            // Obtener el consultor asociado al trámite
            Consultor consultorTramite = tramite.getConsultor();

            // Verificar si el consultor del trámite tiene el mismo legajo que el consultor del método
            if (consultorTramite != null && consultorTramite.getLegajoConsultor() == legajoConsultor) {
                throw new AgendaException("El consultor con legajo " + legajoConsultor + " tiene trámites asignados en la semana especificada.");
            }
        }

        // Retornar false si no se encontraron trámites para el consultor con el legajo especificado
        return false;
    }

    private void validarSemana(int anio, int semana) throws AgendaException {
        // Obtener la fecha actual y la semana actual
        LocalDate fechaActual = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int semanaActual = fechaActual.get(weekFields.weekOfYear());
        int anioActual = fechaActual.getYear();

        // Validar si la agenda pertenece a una semana pasada o la semana actual
        if (anio < anioActual || (anio == anioActual && semana < semanaActual)) {
            throw new AgendaException("No se pueden modificar agendas de semanas pasadas o la semana actual.");
        }
    }

    private AgendaConsultor encontrarOcrearAgendaConsultor(AgendaDTOIn agendaDTOIn) {
        List<DTOCriterio> criterios = new ArrayList<>();

        // Crear el criterio para buscar por año
        DTOCriterio criterioAnio = new DTOCriterio();
        criterioAnio.setAtributo("añoAgendaConsultor");
        criterioAnio.setOperacion("=");
        criterioAnio.setValor(agendaDTOIn.getAñoAgendaConsultor());
        criterios.add(criterioAnio);

        // Crear el criterio para buscar por semana
        DTOCriterio criterioSemana = new DTOCriterio();
        criterioSemana.setAtributo("semAgendaConsultor");
        criterioSemana.setOperacion("=");
        criterioSemana.setValor(agendaDTOIn.getSemAgendaConsultor());
        criterios.add(criterioSemana);

        // Buscar la agenda en la base de datos
        List agenda = FachadaPersistencia.getInstance().buscar("AgendaConsultor", criterios);

        if (agenda != null && !agenda.isEmpty()) {
            // Usar la agenda existente
            AgendaConsultor agendaExistente = (AgendaConsultor) agenda.get(0);
            Hibernate.initialize(agendaExistente.getConsultores());
            return agendaExistente;
        } else {
            // Si no se encuentra una agenda, se crea una nueva
            AgendaConsultor nuevaAgenda = new AgendaConsultor();
            nuevaAgenda.setAñoAgendaConsultor(agendaDTOIn.getAñoAgendaConsultor());
            nuevaAgenda.setSemAgendaConsultor(agendaDTOIn.getSemAgendaConsultor());
            nuevaAgenda.setMesAgendaConsultor(agendaDTOIn.getMesAgendaConsultor());

            // Calcular fechas de inicio y fin de la semana
            Calendar calendar = Calendar.getInstance();
            calendar.clear();
            calendar.set(Calendar.WEEK_OF_YEAR, agendaDTOIn.getSemAgendaConsultor());
            calendar.set(Calendar.YEAR, agendaDTOIn.getAñoAgendaConsultor());
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // Primer día de la semana (Lunes)

            Date fechaDesdeSemana = calendar.getTime();
            nuevaAgenda.setFechaDesdeSemana(fechaDesdeSemana);

            // Configurar el último día de la semana (domingo)
            calendar.add(Calendar.DAY_OF_WEEK, 6); // Sumar 6 días a la fecha inicial
            Date fechaHastaSemana = calendar.getTime();
            nuevaAgenda.setFechaHastaSemana(fechaHastaSemana);

            return nuevaAgenda;
        }
    }

    private Consultor buscarConsultorPorLegajo(int legajoConsultor) {
        List<DTOCriterio> criterios = new ArrayList<>();

        // Criterio para buscar el consultor por legajo
        DTOCriterio criterioLegajo = new DTOCriterio();
        criterioLegajo.setAtributo("legajoConsultor");
        criterioLegajo.setOperacion("=");
        criterioLegajo.setValor(legajoConsultor);
        criterios.add(criterioLegajo);

        // Criterio para asegurarse de que el consultor no esté dado de baja
        DTOCriterio criterioActivo = new DTOCriterio();
        criterioActivo.setAtributo("fechaHoraBajaConsultor");
        criterioActivo.setOperacion("=");
        criterioActivo.setValor(null);
        criterios.add(criterioActivo);

        // Buscar el consultor en la base de datos
        List<Object> consultor = FachadaPersistencia.getInstance().buscar("Consultor", criterios);

        if (consultor != null && !consultor.isEmpty()) {
            return (Consultor) consultor.get(0);
        } else {
            return null;
        }
    }

    private boolean consultorYaAsignado(AgendaConsultor agenda, int legajoConsultor) {
        for (Consultor consultor : agenda.getConsultores()) {
            if (consultor.getLegajoConsultor() == legajoConsultor) {
                return true;
            }
        }
        return false;
    }

}
