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
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
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

        // Verificar si existe una agenda para la semana actual
        if (resultados != null && !resultados.isEmpty()) {
            // Obtener la AgendaConsultor existente
            AgendaConsultor agendaConsultor = (AgendaConsultor) resultados.get(0);
            AgendaDTO agendaDTO = new AgendaDTO();

            // Asignar la semana
            agendaDTO.setSemAgendaConsultor(semana);

            // Obtener los consultores asociados a la agenda
            for (Consultor consultor : agendaConsultor.getConsultores()) {
                DTOConsultor dtoConsultor = new DTOConsultor();

                // Asignar los datos del consultor a dtoConsultor
                dtoConsultor.setLegajoConsultor(consultor.getLegajoConsultor());
                dtoConsultor.setNombreConsultor(consultor.getNombreConsultor());

                // Agregar el DTOConsultor a la lista de consultores de AgendaDTO
                agendaDTO.addDTOConsultor(dtoConsultor);
            }

            // Agregar el objeto AgendaDTO con sus consultores a dtoDatosIni
            dtoDatosIni.addAgendaDTO(agendaDTO);

        } else {
            // Crear una AgendaDTO vacía para la semana si no existe en la base de datos
            AgendaDTO agendaDTO = new AgendaDTO();
            agendaDTO.setSemAgendaConsultor(semana);

            // Agregar la agenda vacía al dtoDatosIni
            dtoDatosIni.addAgendaDTO(agendaDTO);

        }
    }

    return dtoDatosIni;
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
        DTOConsultorListaIzq dtoConsultorListaIzq = new DTOConsultorListaIzq();
        dtoConsultorListaIzq.setLegajoConsultor(consultor.getLegajoConsultor());
        dtoConsultorListaIzq.setNombreConsultor(consultor.getNombreConsultor());
        consultoresActivosDTO.add(dtoConsultorListaIzq);
    }
    return consultoresActivosDTO;
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
        
        Collections.sort(datosInicialesAgendaIn.getAgendaDTO(), new Comparator<AgendaDTOIn>() {
        @Override
        public int compare(AgendaDTOIn a1, AgendaDTOIn a2) {
            return Integer.compare(a1.getSemAgendaConsultor(), a2.getSemAgendaConsultor());
            }
        });
        try {
            // Iniciar la transacción
            FachadaPersistencia.getInstance().iniciarTransaccion();
            
            // Iterar sobre cada AgendaDTOIn en la lista de DTODatosInicialesAgendaIn
            for (AgendaDTOIn agendaDTOIn : datosInicialesAgendaIn.getAgendaDTO()) {
                
                // Validar si la semana pertenece al pasado o al presente
                validarSemana(agendaDTOIn.getAñoAgendaConsultor(), agendaDTOIn.getSemAgendaConsultor());

                // Crear o encontrar la entidad AgendaConsultor para la semana
                AgendaConsultor agendaConsultor = encontrarOcrearAgendaConsultor(agendaDTOIn);

                // Limpia la lista de consultores actual para evitar duplicados
                agendaConsultor.getConsultores().clear();

                // Asignar los consultores de la lista DTO a la entidad AgendaConsultor
                for (DTOConsultorIn dtoConsultor : agendaDTOIn.getConsultores()) {
                    Consultor consultor = buscarConsultorPorLegajo(dtoConsultor.getLegajoConsultor());
                    
                    if (consultor != null) {
                        // Validar si el consultor ya está en la agenda
                        if (!consultorYaAsignado(agendaConsultor, consultor.getLegajoConsultor())) {
                            agendaConsultor.addConsultor(consultor);
                        } else {
                            throw new AgendaException("El consultor con legajo " + consultor.getLegajoConsultor() + " ya está asignado a la agenda.");
                        }
                    }
                }

                // Persistir la entidad AgendaConsultor
                guardarAgendaConsultor(agendaConsultor);
            }

            // Finalizar la transacción
            FachadaPersistencia.getInstance().finalizarTransaccion();

        } catch (AgendaException e) {
               throw new AgendaException(e.getMessage());
        }
    }

    private void validarSemana(int anio, int semana) throws AgendaException {
        // Obtener la fecha actual y la semana actual
        LocalDate fechaActual = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int semanaActual = fechaActual.get(weekFields.weekOfYear());
        int anioActual = fechaActual.getYear();

        // Validar si la agenda pertenece a una semana pasada o la semana actual
        if (anio < anioActual || (anio == anioActual && semana <= semanaActual)) {
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
        List <Object> consultor = FachadaPersistencia.getInstance().buscar("Consultor", criterios);

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

    private void guardarAgendaConsultor(AgendaConsultor agendaConsultor) {
        FachadaPersistencia.getInstance().guardar(agendaConsultor);
    }
}