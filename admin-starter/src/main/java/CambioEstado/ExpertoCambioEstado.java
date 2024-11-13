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
import java.util.Calendar;
import java.util.List;
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
                throw new RuntimeException("No se encontró un consultor con el legajo especificado: " + legajoConsultor);
            }
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("legajoConsultor", legajoConsultor);

            Consultor consultorEncontrado = (Consultor) lConsultor.get(0);
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

            dtoTramitesVigentesList.add(dtoTramitesVigentes); // Agregar DTO a la lista final

            // Finalizar la transacción exitosamente
            FachadaPersistencia.getInstance().finalizarTransaccion();
        } catch (Exception e) {
            // En caso de error, revertir la transacción
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

        // Obtener el último cambio de estado para el contador
        List<TramiteEstadoTramite> tetList = tramite.getTramiteEstadoTramite();
        int contador = tetList.isEmpty() ? 0 : tetList.get(tetList.size() - 1).getContadorTET();

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
        Timestamp fechaHasta = new Timestamp(fechaDesde.getTime()); // Inicializar la fecha hasta con la misma fecha que desde

        // Verificamos si las fechas son iguales y ajustamos la fecha hasta
        if (fechaDesde.equals(fechaHasta)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(fechaHasta.getTime());
            calendar.add(Calendar.MINUTE, 1);  // Sumar 1 minuto a la fecha hasta
            fechaHasta = new Timestamp(calendar.getTimeInMillis());
        }

        // Asignar las fechas al estado
        tramiteEstadoTramite.setFechaDesdeTET(fechaDesde);
        tramiteEstadoTramite.setFechaHastaTET(fechaHasta);
        tramiteEstadoTramite.setEstadoTramite(estadoDestino);
      //  tramiteEstadoTramite.setObservaciones();
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
        FachadaPersistencia.getInstance().iniciarTransaccion();

        try {
            Tramite tramite = obtenerTramitePorNumero(nroTramite);
            if (tramite == null) {
                throw new CambioEstadoException("Trámite no encontrado.");
            }

            List<TramiteEstadoTramite> historial = tramite.getTramiteEstadoTramite();
            if (historial == null || historial.size() < 2) {
                throw new CambioEstadoException("No hay suficiente historial para deshacer el cambio.");
            }

            // Obtener el último cambio y el penúltimo
            TramiteEstadoTramite ultimoCambio = historial.get(historial.size() - 1); //yo pongo que esta en pendiente
            TramiteEstadoTramite estadoAnterior = historial.get(historial.size() - 2); // aca volveria al iniciado

            // Restaurar el estado anterior como estado actual
            tramite.setEstadoTramite(estadoAnterior.getEstadoTramite());
            ultimoCambio.setFechaHastaTET(fechaHoraActual.obtenerFechaHoraActual());

            FachadaPersistencia.getInstance().guardar(tramite);
            FachadaPersistencia.getInstance().finalizarTransaccion();
        } catch (Exception e) {
            FachadaPersistencia.getInstance().finalizarTransaccion();
            throw new CambioEstadoException("Error al deshacer el cambio de estado.");
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
            dtoHistorialEstado.setContadorTET(tet.getContadorTET());
            dtoHistorialEstados.add(dtoHistorialEstado);
        }

        return dtoHistorialEstados;
    }

}
