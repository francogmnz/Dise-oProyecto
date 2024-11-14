package CambioEstado;

import CambioEstado.dtos.DTOEstadoDestinoCE;
import CambioEstado.dtos.DTOEstadoOrigenCE;
import CambioEstado.dtos.DTOHistorialEstado;
import CambioEstado.dtos.DTOTramitesVigentes;
import CambioEstado.dtos.TramiteDTO;
import CambioEstado.exceptions.CambioEstadoException;
import entidades.ConfTipoTramiteEstadoTramite;
import entidades.Consultor;
import entidades.EstadoTramite;
import entidades.Tramite;
import entidades.TramiteEstadoTramite;
import entidades.Version;
import jakarta.faces.context.FacesContext;
import java.sql.Timestamp;
import utils.FachadaPersistencia;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.omnifaces.util.Messages;
import utils.DTOCriterio;
import utils.fechaHoraActual;

public class ExpertoCambioEstado {

    public List<DTOTramitesVigentes> buscarTramites(int legajoConsultor) {
        // Iniciar transacción
        FachadaPersistencia.getInstance().iniciarTransaccion();
        List<DTOTramitesVigentes> dtoTramitesVigentesList = new ArrayList<>();

        try {
            List<DTOCriterio> criterioList = new ArrayList<>();
            DTOCriterio dto = new DTOCriterio();
            dto.setAtributo("legajoConsultor");
            dto.setOperacion("=");
            dto.setValor(legajoConsultor);

            criterioList.add(dto);
            List<Object> lConsultor = FachadaPersistencia.getInstance().buscar("Consultor", criterioList);

            if (lConsultor.isEmpty()) {
                // Si no se encuentra el consultor, lanzar una excepción con un mensaje de error
                Messages.create("Error").detail("El consultor no existe, intente nuevamente").error().add();
            } else {
                // Obtener el consultor encontrado
                Consultor consultorEncontrado = (Consultor) lConsultor.get(0);

                // Verificar si el consultor está dado de baja (si la fecha de baja es no nula)
                if (consultorEncontrado.getFechaHoraBajaConsultor() != null) {
                    // El consultor está dado de baja
                    Messages.create("Error").detail("El consultor está dado de baja. No se pueden buscar trámites.").error().add();
                    FachadaPersistencia.getInstance().finalizarTransaccion();
                    return dtoTramitesVigentesList; // Salir del método si el consultor está dado de baja
                }

                // Guardar el legajoConsultor en la sesión
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("legajoConsultor", legajoConsultor);

                criterioList.clear();

                // Crear criterio para buscar trámites del consultor
                DTOCriterio criterio1 = new DTOCriterio();
                criterio1.setAtributo("consultor");
                criterio1.setOperacion("=");
                criterio1.setValor(consultorEncontrado);

                criterioList.add(criterio1);

                // Criterio para buscar trámites vigentes (fechaFinTramite es null)
                // Buscar trámites del consultor
                List<Object> lTramites = FachadaPersistencia.getInstance().buscar("Tramite", criterioList);

                // Verificar si el consultor no tiene trámites
                if (lTramites.isEmpty()) {
                    Messages.create("Información").detail("El consultor no tiene trámites asociados.").error().add();
                    FachadaPersistencia.getInstance().finalizarTransaccion();
                    return dtoTramitesVigentesList; // Salir del método si no hay trámites
                }

                // Crear el DTO para trámites vigentes
                DTOTramitesVigentes dtoTramitesVigentes = new DTOTramitesVigentes();
                dtoTramitesVigentes.setCodConsultor(legajoConsultor); // Asignar código del consultor

                // Agregar cada trámite encontrado al DTOTramitesVigentes
                for (Object tramiteObj : lTramites) {
                    Tramite tramite = (Tramite) tramiteObj;
                    TramiteDTO dtoTramite = new TramiteDTO();
                    dtoTramite.setNombreConsultor(tramite.getConsultor().getNombreConsultor());
                    dtoTramite.setNroTramite(tramite.getNroTramite());
                    dtoTramite.setFechaInicioTramite(tramite.getFechaInicioTramite());
                    dtoTramite.setFechaRecepcionTramite(tramite.getFechaRecepcionTramite());
                    dtoTramite.setEstadoTramite(tramite.getEstadoTramite());
                    dtoTramite.setNombreEstadoTramite(tramite.getEstadoTramite().getNombreEstadoTramite());

                    // Agregar el trámite al DTO
                    dtoTramitesVigentes.addTramite(dtoTramite);
                }

                // Agregar DTO a la lista final
                dtoTramitesVigentesList.add(dtoTramitesVigentes);

                // Finalizar la transacción exitosamente
                FachadaPersistencia.getInstance().finalizarTransaccion();
            }
        } catch (Exception e) {
            // En caso de error, revertir la transacción
            FachadaPersistencia.getInstance().finalizarTransaccion(); // Asegurarse de finalizar la transacción
            throw e; // Volver a lanzar la excepción
        }

        // Retornar la lista final de trámites vigentes
        return dtoTramitesVigentesList;
    }

    public DTOEstadoOrigenCE mostrarEstadosPosibles(int nroTramite) throws CambioEstadoException {

        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();
        dto.setAtributo("nroTramite");
        dto.setOperacion("=");
        dto.setValor(nroTramite);

        criterioList.add(dto);

        List tramites = FachadaPersistencia.getInstance().buscar("Tramite", criterioList);

        if (tramites == null || tramites.isEmpty()) {
            throw new CambioEstadoException("El tramite no existe");
        }

        Tramite tramiteElegido = (Tramite) tramites.get(0);
        System.out.println("NroTramite: " + tramiteElegido.getNroTramite() + " - " + tramiteElegido.getEstadoTramite().getNombreEstadoTramite());
        EstadoTramite estadoOrigen = tramiteElegido.getEstadoTramite();

        System.out.println("Estado de Origen: " + estadoOrigen.getCodEstadoTramite() + " - " + estadoOrigen.getNombreEstadoTramite());

        DTOEstadoOrigenCE estadoOrigenDTO = new DTOEstadoOrigenCE();
        estadoOrigenDTO.setCodEstadoOrigen(estadoOrigen.getCodEstadoTramite());
        estadoOrigenDTO.setNombreEstadoOrigen(estadoOrigen.getNombreEstadoTramite());

        Version versionTramite = tramiteElegido.getVersion();
        List<ConfTipoTramiteEstadoTramite> listaConfiguraciones = versionTramite.getConfTipoTramiteEstadoTramite();

        List<DTOEstadoDestinoCE> estadosDestinoList = new ArrayList<>();

        for (ConfTipoTramiteEstadoTramite config : listaConfiguraciones) {
            if (config.getEstadoTramiteOrigen().getCodEstadoTramite() == estadoOrigen.getCodEstadoTramite()) {
                List<EstadoTramite> estadosDestinos = config.getEstadoTramiteDestino();
                for (EstadoTramite estado : estadosDestinos) {
                    if (estado.getFechaHoraBajaEstadoTramite() == null) {
                        DTOEstadoDestinoCE estadoDestinoDTO = new DTOEstadoDestinoCE();
                        estadoDestinoDTO.setCodEstadoDestino(estado.getCodEstadoTramite());
                        estadoDestinoDTO.setNombreEstadoDestino(estado.getNombreEstadoTramite());
                        estadosDestinoList.add(estadoDestinoDTO);
                        System.out.println("Estado destino añadido: " + estado.getCodEstadoTramite() + " - " + estado.getNombreEstadoTramite());
                    }
                }
            }
        }

        estadoOrigenDTO.addEstadosDestinos(estadosDestinoList);
        return estadoOrigenDTO;
    }

    public void cambiarEstado(int nroTramite, int codEstadoDestino) throws CambioEstadoException {
        FachadaPersistencia.getInstance().iniciarTransaccion();

        try {
            // Validar si el trámite existe
            List<DTOCriterio> criterioList = new ArrayList<>();
            DTOCriterio dto = new DTOCriterio();
            dto.setAtributo("nroTramite");
            dto.setOperacion("=");
            dto.setValor(nroTramite);
            criterioList.add(dto);

            List tramites = FachadaPersistencia.getInstance().buscar("Tramite", criterioList);

            if (tramites == null || tramites.isEmpty()) {
                throw new CambioEstadoException("El trámite no existe");
            }

            Tramite tramite = (Tramite) tramites.get(0);
            // Verificar si el trámite ya está en el estado "Terminado"
            if (tramite.getEstadoTramite() != null && "Terminado".equals(tramite.getEstadoTramite().getNombreEstadoTramite())) {
                Messages.create("Error")
                        .detail("No se puede cambiar el estado porque el trámite ya está en estado 'Terminado'.")
                        .error()
                        .add();
                return; // Detener el flujo aquí para evitar que se continúe con el cambio de estado
            }
            // Obtener el último cambio de estado para el contador
            List<TramiteEstadoTramite> tetList = tramite.getTramiteEstadoTramite();
            // Validar el estado destino
            criterioList.clear();
            dto = new DTOCriterio();
            dto.setAtributo("codEstadoTramite");
            dto.setOperacion("=");
            dto.setValor(codEstadoDestino);
            criterioList.add(dto);

            List estados = FachadaPersistencia.getInstance().buscar("EstadoTramite", criterioList);
            if (estados == null || estados.isEmpty()) {
                throw new CambioEstadoException("El estado destino no existe");
            }

            EstadoTramite estadoDestino = (EstadoTramite) estados.get(0);

            // Configurar el nuevo cambio de estado
            TramiteEstadoTramite tramiteEstadoTramite = new TramiteEstadoTramite();
            Timestamp fechaDesde = fechaHoraActual.obtenerFechaHoraActual(); // Obtener la fecha y hora actuales

            // Si la fecha hasta es igual a la fecha desde, sumamos 1 minuto
            Timestamp fechaHasta = new Timestamp(fechaDesde.getTime() + 60000); // Inicializar la fecha hasta con la misma fecha que desde
//
//            // Verificamos si las fechas son iguales y ajustamos la fecha hasta
//            if (fechaDesde.equals(fechaHasta)) {
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTimeInMillis(fechaHasta.getTime());
//                calendar.add(Calendar.MINUTE, 1);  // Sumar 1 minuto a la fecha hasta
//                fechaHasta = new Timestamp(calendar.getTimeInMillis());
//            }

            // Asignar las fechas al estado
            tramiteEstadoTramite.setFechaDesdeTET(fechaDesde);
            tramiteEstadoTramite.setFechaHastaTET(fechaHasta);
            tramiteEstadoTramite.setEstadoTramite(estadoDestino);
            //  tramiteEstadoTramite.setObservaciones();

            tramite.setEstadoTramite(estadoDestino);
            tramite.addTramiteEstadoTramite(tramiteEstadoTramite);

            // Verificar si es el último estado y actualizar fecha de fin
            Version versionUltimoTramite = tramite.getVersion();
            List<ConfTipoTramiteEstadoTramite> listaConfig = versionUltimoTramite.getConfTipoTramiteEstadoTramite();
            boolean tieneEtapasDestino = false;
            for (ConfTipoTramiteEstadoTramite config : listaConfig) {
                if (config.getEtapaDestino() != 0) {
                    tieneEtapasDestino = true;
                    break;
                }
            }
            if (!tieneEtapasDestino) {
                tramite.setFechaFinTramite(fechaHoraActual.obtenerFechaHoraActual());
            }

            // Guardar el nuevo estado y finalizar la transacción
            FachadaPersistencia.getInstance().guardar(tramiteEstadoTramite);
            FachadaPersistencia.getInstance().guardar(tramite);
            FachadaPersistencia.getInstance().finalizarTransaccion();

        } catch (Exception e) {
            FachadaPersistencia.getInstance().finalizarTransaccion();
            e.printStackTrace();
            throw new CambioEstadoException("Error al cambiar el estado del trámite");
        }
    }
    // Método para obtener el trámite por número

    private Tramite obtenerTramitePorNumero(int nroTramite) {
        List<DTOCriterio> criterioList = new ArrayList<>();

        DTOCriterio dto = new DTOCriterio();
        dto.setAtributo("nroTramite");
        dto.setOperacion("=");
        dto.setValor(nroTramite);
        criterioList.add(dto);

        List<?> result = FachadaPersistencia.getInstance().buscar("Tramite", criterioList);

        return result.stream()
                .filter(obj -> obj instanceof Tramite)
                .map(obj -> (Tramite) obj)
                .findFirst()
                .orElse(null);  // Devuelve null si no se encuentra el trámite
    }

  public void deshacerUltimoCambio(int nroTramite) throws CambioEstadoException {
    System.out.println("Iniciando deshacerUltimoCambio para tramite nro: " + nroTramite);

    // Iniciar transacción
    FachadaPersistencia.getInstance().iniciarTransaccion();

    try {
        // Verificar si el trámite existe
        Tramite tramite = obtenerTramitePorNumero(nroTramite);
        if (tramite == null) {
            throw new CambioEstadoException("Trámite no encontrado.");
        }

        // Obtener historial de estados del trámite
        List<TramiteEstadoTramite> historial = tramite.getTramiteEstadoTramite();
        if (historial == null || historial.size() < 2) { // Se requiere al menos 2 estados para deshacer
            throw new CambioEstadoException("No hay suficiente historial para deshacer el cambio.");
        }

        // Ordenar el historial de estados por fecha 'fechaDesdeTET' en orden ascendente (más antiguo primero)
        historial.sort(Comparator.comparing(TramiteEstadoTramite::getFechaDesdeTET));

        // Obtener el último estado y el estado anterior
        TramiteEstadoTramite ultimoCambio = historial.get(historial.size() - 1);
        TramiteEstadoTramite estadoAnterior = historial.get(historial.size() - 2);

        // Verificar si el trámite ya está en el estado anterior
        if (tramite.getEstadoTramite().equals(estadoAnterior.getEstadoTramite())) {
            throw new CambioEstadoException("El trámite ya está en el estado anterior.");
        }

        // Verificar si el último cambio de estado corresponde al estado actual del trámite
        if (!tramite.getEstadoTramite().equals(ultimoCambio.getEstadoTramite())) {
            throw new CambioEstadoException("No es posible deshacer porque el estado actual no es el último cambio.");
        }

        System.out.println("Último cambio de estado: " + ultimoCambio.getEstadoTramite().getNombreEstadoTramite());
        System.out.println("Estado anterior: " + estadoAnterior.getEstadoTramite().getNombreEstadoTramite());

        // Establecer el estado anterior como el estado actual
        tramite.setEstadoTramite(estadoAnterior.getEstadoTramite());

        // Obtener la fecha actual para establecerla como fecha "hasta" del último estado (estado actual)
        Timestamp fechaHasta = fechaHoraActual.obtenerFechaHoraActual();

        // Añadir un margen de tiempo de 1 minuto para evitar que las fechas sean demasiado cercanas
        Timestamp nuevaFechaHasta = new Timestamp(fechaHasta.getTime() + 1 * 60 * 1000); // Añadir 1 minuto

        // Actualizar la fecha "hasta" del último estado (estado actual) con la nueva fecha
        ultimoCambio.setFechaHastaTET(nuevaFechaHasta);

        // Guardar los cambios en el sistema
        FachadaPersistencia.getInstance().guardar(ultimoCambio); // Guardamos el cambio "hasta"
        FachadaPersistencia.getInstance().guardar(tramite); // Guardamos el trámite con el nuevo estado

        // Finalizar la transacción
        FachadaPersistencia.getInstance().finalizarTransaccion();

        // Mensaje de éxito
        Messages.create("Éxito")
                .detail("Se ha deshecho el último cambio. El estado actual del trámite es: " + estadoAnterior.getEstadoTramite().getNombreEstadoTramite())
                .add();

    } catch (CambioEstadoException e) {
        // En caso de error específico, mostramos el error
        System.err.println("Error específico al deshacer cambio de estado: " + e.getMessage());
        FachadaPersistencia.getInstance().finalizarTransaccion();
        throw e; // Re-lanza el mensaje de error específico
    } catch (Exception e) {
        // En caso de error genérico, mostramos el error
        System.err.println("Error al deshacer cambio de estado: " + e.getMessage());
        FachadaPersistencia.getInstance().finalizarTransaccion();
        throw new CambioEstadoException("Error al deshacer el cambio de estado: " + e.getMessage());
    }
}

    public List<DTOHistorialEstado> obtenerHistorialEstados(int nroTramite) throws CambioEstadoException {

        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();
        dto.setAtributo("nroTramite");
        dto.setOperacion("=");
        dto.setValor(nroTramite);
        criterioList.add(dto);

        List tramites = FachadaPersistencia.getInstance().buscar("Tramite", criterioList);

        if (tramites == null || tramites.isEmpty()) {
            throw new CambioEstadoException("El trámite no existe");
        }

        Tramite tramite = (Tramite) tramites.get(0);

        List<TramiteEstadoTramite> historialEstados = tramite.getTramiteEstadoTramite();

        if (historialEstados == null || historialEstados.isEmpty()) {
            throw new CambioEstadoException("No hay historial de estados para el trámite");
        }

        List<DTOHistorialEstado> dtoHistorialEstados = new ArrayList<>();
        for (TramiteEstadoTramite tet : historialEstados) {

            DTOHistorialEstado dtoHistorialEstado = new DTOHistorialEstado();
            dtoHistorialEstado.setNombreEstadoTramite(tet.getEstadoTramite().getNombreEstadoTramite());
            dtoHistorialEstado.setFechaDesdeTET(tet.getFechaDesdeTET());
            dtoHistorialEstado.setFechaHastaTET(tet.getFechaHastaTET());
            dtoHistorialEstados.add(dtoHistorialEstado);
        }

        return (dtoHistorialEstados);
    }

}
