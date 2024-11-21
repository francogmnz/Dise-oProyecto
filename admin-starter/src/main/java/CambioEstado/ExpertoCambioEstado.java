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
            Version versionTramite = tramite.getVersion();

            List<ConfTipoTramiteEstadoTramite> confVersion = versionTramite.getConfTipoTramiteEstadoTramite();
            List<ConfTipoTramiteEstadoTramite> confiVersion = versionTramite.getConfTipoTramiteEstadoTramite();

            boolean esFinal = false;
            

            EstadoTramite estadoActual = tramite.getEstadoTramite();

            List<EstadoTramite> estadosFinales = new ArrayList<>();

            
            for (ConfTipoTramiteEstadoTramite confi : confiVersion) {

                if (confi.getEstadoTramiteDestino().isEmpty()) {

                    estadosFinales.add(confi.getEstadoTramiteOrigen());

                }
            }
            if (!estadosFinales.isEmpty()) {
                System.out.println("Estados finales encontrados:");
                for (EstadoTramite estadoFinal : estadosFinales) {
                    System.out.println(estadoFinal);
                }
            } else {
                System.out.println("No se encontraron estados finales.");
            }

            
           

            // Obtener el historial de estados del trámite
            List<TramiteEstadoTramite> tetList = tramite.getTramiteEstadoTramite();

            // Calcular el nuevo valor del contador
            int nuevoContador = tetList.isEmpty()
                    ? 1 // Si no hay historial, inicializamos en 1
                    : tetList.stream()
                            .mapToInt(TramiteEstadoTramite::getContadorTET)
                            .max()
                            .orElse(0) + 1; // Si hay historial, incrementamos el contador

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
            Timestamp fechaDesde = fechaHoraActual.obtenerFechaHoraActual();

            

            // Asignar el contador y las fechas
            tramiteEstadoTramite.setContadorTET(nuevoContador);
            tramiteEstadoTramite.setFechaDesdeTET(fechaDesde);
            tramiteEstadoTramite.setEstadoTramite(estadoDestino);

            // Actualizar el estado actual del trámite
            tramite.setEstadoTramite(estadoDestino);
            
            for(EstadoTramite estadofinal : estadosFinales){
            
                if(estadoDestino.getCodEstadoTramite() == estadofinal.getCodEstadoTramite()){
                
                    esFinal = true;
                }
            }

            
            if (esFinal == true) {
                tramite.setFechaFinTramite(fechaHoraActual.obtenerFechaHoraActual());
            }
            
            tramite.addTramiteEstadoTramite(tramiteEstadoTramite);

            // Guardar los cambios
            FachadaPersistencia.getInstance().guardar(tramiteEstadoTramite);
            FachadaPersistencia.getInstance().guardar(tramite);

            // Finalizar la transacción
            FachadaPersistencia.getInstance().finalizarTransaccion();

            System.out.println("Cambio de estado realizado con éxito. Nuevo contador: " + nuevoContador);
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
        System.out.println("Iniciando deshacerUltimoCambio para trámite nro: " + nroTramite);

        FachadaPersistencia.getInstance().iniciarTransaccion();

        try {
            // Obtener el trámite por número
            Tramite tramite = obtenerTramitePorNumero(nroTramite);
            if (tramite == null) {
                throw new CambioEstadoException("Trámite no encontrado.");
            }

            // Obtener el historial de estados del trámite
            List<TramiteEstadoTramite> historial = tramite.getTramiteEstadoTramite();
            if (historial == null || historial.isEmpty()) {
                throw new CambioEstadoException("El trámite no tiene historial de estados.");
            }

            // Encontrar el estado con el mayor contador (último estado)
            TramiteEstadoTramite ultimoCambio = historial.stream()
                    .max(Comparator.comparing(TramiteEstadoTramite::getContadorTET))
                    .orElseThrow(() -> new CambioEstadoException("No se encontró el último estado del trámite."));

            // Encontrar el estado anterior (contadorTET menor al del último)
            TramiteEstadoTramite estadoAnterior = historial.stream()
                    .filter(e -> e.getContadorTET() < ultimoCambio.getContadorTET())
                    .max(Comparator.comparing(TramiteEstadoTramite::getContadorTET))
                    .orElse(null);

            if (estadoAnterior == null) {
                throw new CambioEstadoException("No hay estado anterior para deshacer el cambio.");
            }

            // Actualizar las fechas
            Timestamp fechaActual = fechaHoraActual.obtenerFechaHoraActual();
            ultimoCambio.setFechaHastaTET(fechaActual); // Marcar el último estado como cerrado
            estadoAnterior.setFechaHastaTET(null); // Marcar el estado anterior como el actual

            // Actualizar el estado actual del trámite
            tramite.setEstadoTramite(estadoAnterior.getEstadoTramite());

            // Recalcular los contadores después de revertir
            recalcularContadores(historial);

            // Guardar los cambios
            FachadaPersistencia.getInstance().guardar(ultimoCambio); // Guardar el último estado con fecha de cierre
            FachadaPersistencia.getInstance().guardar(estadoAnterior); // Guardar el estado anterior como actual
            FachadaPersistencia.getInstance().guardar(tramite); // Guardar el trámite con el nuevo estado actual

            // Finalizar la transacción
            FachadaPersistencia.getInstance().finalizarTransaccion();

            // Mensaje de éxito
            Messages.create("Éxito")
                    .detail("Se ha deshecho el último cambio. El estado actual del trámite es: "
                            + estadoAnterior.getEstadoTramite().getNombreEstadoTramite())
                    .add();

        } catch (CambioEstadoException e) {
            // Error específico
            System.err.println("Error específico al deshacer cambio: " + e.getMessage());
            FachadaPersistencia.getInstance().finalizarTransaccion();
            throw e;
        } catch (Exception e) {
            // Error genérico
            System.err.println("Error al deshacer cambio: " + e.getMessage());
            FachadaPersistencia.getInstance().finalizarTransaccion();
            throw new CambioEstadoException("Error al deshacer cambio: " + e.getMessage());
        }
    }

    
    private void recalcularContadores(List<TramiteEstadoTramite> historial) {
        if (historial == null || historial.isEmpty()) {
            return; // No hay nada que recalcular
        }

        // Ordenar por el campo `contadorTET` ascendente
        historial.sort(Comparator.comparingInt(TramiteEstadoTramite::getContadorTET));

        // Recalcular los contadores desde 1 hacia adelante
        int contador = 1;
        for (TramiteEstadoTramite estado : historial) {
            estado.setContadorTET(contador); // Ajustar el contador
            contador++;
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

        return (dtoHistorialEstados);
    }

}
