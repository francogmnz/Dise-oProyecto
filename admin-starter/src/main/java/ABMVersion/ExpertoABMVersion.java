package ABMVersion;

import ABMVersion.beans.VersionGrillaUI;
import ABMVersion.dtos.DTODatosVersionIn;
import ABMVersion.dtos.DTOEstadoDestinoIN;
import ABMVersion.dtos.DTOEstadoOrigenIN;
import ABMVersion.dtos.DTOVersionM;
import ABMVersion.dtos.VersionDTO;
import ABMVersion.dtos.DTOEstado;
import ABMVersion.dtos.DTOTipoTramiteVersion;
import ABMVersion.exceptions.VersionException;
import entidades.ConfTipoTramiteEstadoTramite;
import entidades.EstadoTramite;
import entidades.TipoTramite;
import entidades.Version;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.omnifaces.util.Messages;
import utils.DTOCriterio;
import utils.FachadaPersistencia;

public class ExpertoABMVersion {

    public boolean estadoOrigenExiste(int codEstadoDestino, List<DTOEstadoOrigenIN> listaEstadosOrigen) {
        for (DTOEstadoOrigenIN estadoOrigen : listaEstadosOrigen) {
            if (estadoOrigen.getCodEstadoTramite() == codEstadoDestino) {
                return true;
            }
        }
        return false;
    }

    // Método para buscar versiones
    public List<VersionDTO> buscarVersion(int nroVersion, int codTipoTramite, String nombreTipoTramite) {
        FachadaPersistencia.getInstance().iniciarTransaccion();
        List<DTOCriterio> primerCriterio = new ArrayList<>();

        if (nroVersion > 0) {
            DTOCriterio criterio1 = new DTOCriterio();
            criterio1.setAtributo("nroVersion");
            criterio1.setOperacion("=");
            criterio1.setValor(nroVersion);
            primerCriterio.add(criterio1);
        }

        if (codTipoTramite > 0) {
            DTOCriterio criterio2 = new DTOCriterio();
            criterio2.setAtributo("codTipoTramite");
            criterio2.setOperacion("=");
            criterio2.setValor(codTipoTramite);
            primerCriterio.add(criterio2);
        }

        if (nombreTipoTramite != null && !nombreTipoTramite.trim().isEmpty()) {
            DTOCriterio criterio3 = new DTOCriterio();
            criterio3.setAtributo("nombreTipoTramite");
            criterio3.setOperacion("like");
            criterio3.setValor(nombreTipoTramite);
            primerCriterio.add(criterio3);
        }

        List<Object> objetoList = FachadaPersistencia.getInstance().buscar("Version", primerCriterio);
        List<VersionDTO> versionResultado = new ArrayList<>();

        for (Object x : objetoList) {
            if (x instanceof Version) {
                Version version = (Version) x;
                VersionDTO versionDTO = new VersionDTO();
                versionDTO.setNroVersion(version.getNroVersion());
                versionDTO.setDescripcionVersion(version.getDescripcionVersion());
                versionDTO.setFechaDesdeVersion(version.getFechaDesdeVersion());
                versionDTO.setFechaHastaVersion(version.getFechaHastaVersion());
                versionDTO.setFechaBajaVersion(version.getFechaBajaVersion());
                versionResultado.add(versionDTO);
            }
        }
        FachadaPersistencia.getInstance().finalizarTransaccion();
        return versionResultado;
    }

    public void darDeBajaVersion(int codTipoTramite) throws VersionException {
    try {
        FachadaPersistencia.getInstance().iniciarTransaccion();

        // Crear el criterio de búsqueda para encontrar la versión
        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();
        dto.setAtributo("tipoTramite");
        dto.setOperacion("=");
        dto.setValor(codTipoTramite);
        criterioList.add(dto);

        // Buscar las versiones con el criterio dado
        List<Object> lVersion = FachadaPersistencia.getInstance().buscar("Version", criterioList);

        if (lVersion.isEmpty()) {
            throw new VersionException("No hay versiones disponibles para este tipo de trámite.");
        }

        // Obtener la versión encontrada y actualizar la fecha de baja
        Version versionEncontrada = (Version) lVersion.get(lVersion.size() - 1); // Asumimos que la última versión es la más reciente
        if (versionEncontrada.getFechaBajaVersion() != null) {
            throw new VersionException("La versión ya está dada de baja.");
        }
        
        versionEncontrada.setFechaBajaVersion(new Timestamp(System.currentTimeMillis()));
        // Guardar los cambios
        FachadaPersistencia.getInstance().guardar(versionEncontrada);

        // Establecer la versión anterior como activa
        if (lVersion.size() > 1) {
            Version versionAnterior = (Version) lVersion.get(lVersion.size() - 2);
            versionAnterior.setFechaBajaVersion(null); // Asegúrate de que la fecha de baja esté en null
            FachadaPersistencia.getInstance().guardar(versionAnterior);
        }

        // Finalizar la transacción
        FachadaPersistencia.getInstance().finalizarTransaccion();
    } catch (Exception e) {
        FachadaPersistencia.getInstance().finalizarTransaccion();
        throw new VersionException("Error al dar de baja la versión: " + e.getMessage(), e);
    }
}
    
    public int obtenerUltimoNumeroVersion(int codTipoTramite) {
        // Obtener la instancia de FachadaPersistencia
        FachadaPersistencia f = FachadaPersistencia.getInstance();
        f.iniciarTransaccion();
        // Búsqueda del TipoTramite
        List<DTOCriterio> lc = new ArrayList<>();
        DTOCriterio c = new DTOCriterio();
        c.setAtributo("codTipoTramite");
        c.setOperacion("=");
        c.setValor(codTipoTramite);
        lc.add(c);

        List<Object> o = f.buscar("TipoTramite", lc);
        TipoTramite t = null;
        if (!o.isEmpty()) {
            t = (TipoTramite) o.get(0);

        } else {
            System.out.println("TipoTramite no encontrado.");
            return 0;
        }
        try {
            // Crear el criterio de búsqueda para obtener el último número de versión del tipo de trámite
            lc = new ArrayList<>();

            // Criterio para filtrar por el código de tipo de trámite
            DTOCriterio cTipoTramite = new DTOCriterio();
            cTipoTramite.setAtributo("tipoTramite");
            cTipoTramite.setOperacion("=");
            cTipoTramite.setValor(t);

            lc.add(cTipoTramite);

            // Criterio para ordenar por número de versión de manera descendente
            // Buscar versiones con los criterios establecidos
            List<Object> versiones = f.buscar("Version", lc);

            // Verificar si se encontraron versiones
            if (!versiones.isEmpty()) {
                int ultimo = 0;
                for (Object ob : versiones) {
                    Version ultimaVersion = (Version) ob;
                    if (ultimaVersion.getNroVersion() > ultimo) {
                        ultimo = ultimaVersion.getNroVersion();
                    }
                }
                return ultimo;
            }
        } catch (Exception e) {
            // Manejo de excepciones (opcional: registrar el error)
            e.printStackTrace();
        } finally {
            f.finalizarTransaccion();
        }

        // Si no hay versiones, retornar 1
        return 0;
    }

    public boolean confirmacion(DTODatosVersionIn dtoDatosVersion) {
        FachadaPersistencia f = FachadaPersistencia.getInstance();
        f.iniciarTransaccion();
        Version nueva = new Version();

        // Establecer la versión de forma autoincremental
        int codTipoTramite = dtoDatosVersion.getCodTipoTramite();
        int ultimoNumeroVersion = obtenerUltimoNumeroVersion(codTipoTramite);
        int nuevoNumeroVersion = ultimoNumeroVersion + 1;
        nueva.setNroVersion(nuevoNumeroVersion);

        /* // Verificar si ya existe una versión con este número si esta duplicado no se guarda
        if (existeVersionConNumero(f, nuevoNumeroVersion)) {
            System.out.println("Ya existe una versión con el número: " + nuevoNumeroVersion);
            return false; // Puedes retornar false o manejar de otra manera
        }

        nueva.setNroVersion(nuevoNumeroVersion);*/
        // Configurar los demás atributos de la versión
        nueva.setDescripcionVersion(dtoDatosVersion.getDescripcionVersion());
        // Obtener la fecha actual
        nueva.setFechaDesdeVersion(new Timestamp(System.currentTimeMillis())); // Traer fecha actual

        // Sumar un mes a la fecha actual para fecha hasta
         LocalDateTime fechaHasta = LocalDateTime.now().plusMonths(1); // Sumar 1 mes
        nueva.setFechaHastaVersion(Timestamp.valueOf(fechaHasta));
         
        // Debug: Imprimir las fechas
        System.out.println("Fecha Desde: " + nueva.getFechaDesdeVersion());
        System.out.println("Fecha Hasta: " + nueva.getFechaHastaVersion());

          // Verificar que la fecha hasta no sea menor que la fecha desde
        if (nueva.getFechaHastaVersion().before(nueva.getFechaDesdeVersion())) {
            JOptionPane.showMessageDialog(null, "La fecha hasta no puede ser menor que la fecha desde.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;  // No continuar si las fechas no son válidas
        }
         
        // Verificar que las fechas no sean iguales
        if (nueva.getFechaHastaVersion().before(nueva.getFechaDesdeVersion())) {
            JOptionPane.showMessageDialog(null, "La fecha desde no puede ser igual a la fecha hasta.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;  // No continuar si las fechas son iguales
        }

        // Búsqueda del TipoTramite
        List<DTOCriterio> lc = new ArrayList<>();
        DTOCriterio c = new DTOCriterio();
        c.setAtributo("codTipoTramite");
        c.setOperacion("=");
        c.setValor(codTipoTramite);
        lc.add(c);

        List<Object> o = f.buscar("TipoTramite", lc);

        if (!o.isEmpty()) {
            TipoTramite t = (TipoTramite) o.get(0);
            nueva.setTipoTramite(t);
        } else {
            System.out.println("TipoTramite no encontrado.");
            return false;
        }

        try {
            // Inicializar los contadores
            int i = 1;  // Para los estados de origen/destino
            int cont = 1;  //cont para el contador de la config

            // Procesar Estados de Origen
            for (DTOEstadoOrigenIN dtoO : dtoDatosVersion.getDtoEstadoOrigenList()) {

                // Búsqueda del Estado de Origen
                List<DTOCriterio> lcEstado = new ArrayList<>();
                DTOCriterio criterioEstado = new DTOCriterio();
                criterioEstado.setAtributo("codEstadoTramite");
                criterioEstado.setOperacion("=");
                criterioEstado.setValor(dtoO.getCodEstadoTramite());
                lcEstado.add(criterioEstado);

                List<Object> resultadoEstado = f.buscar("EstadoTramite", lcEstado);

                if (!resultadoEstado.isEmpty()) {
                    EstadoTramite eO = (EstadoTramite) resultadoEstado.get(0);
                    System.out.println("Nombre Estado Origen: " + eO.getNombreEstadoTramite());

                    // Crear nueva configuración de TipoTramite y EstadoTramite
                    ConfTipoTramiteEstadoTramite cttet = new ConfTipoTramiteEstadoTramite();
                    cttet.setEtapaOrigen(i);  // Asignar etapa de origen
                    cttet.setEstadoTramiteOrigen(eO);
                    cttet.setContadorConfigTTET(cont);  // asigna el valor actual del contador
                    cont++;  // Incrementa el contador después de cada configuración guardada

                    // Lista para almacenar estados de destino
                    List<EstadoTramite> listaEstadoTramiteDestino = new ArrayList<>();

                    // Procesar Estados de Destino
                    for (DTOEstadoDestinoIN dtoD : dtoO.getDtoEstadoDestinoList()) {
                        // Búsqueda del Estado de Destino
                        List<DTOCriterio> lcDestino = new ArrayList<>();
                        DTOCriterio criterioDestino = new DTOCriterio();
                        criterioDestino.setAtributo("codEstadoTramite");
                        criterioDestino.setOperacion("=");
                        criterioDestino.setValor(dtoD.getCodEstadoTramite());
                        lcDestino.add(criterioDestino);

                        List<Object> resultadoDestino = f.buscar("EstadoTramite", lcDestino);
                        if (!resultadoDestino.isEmpty()) {
                            EstadoTramite eD = (EstadoTramite) resultadoDestino.get(0);
                            System.out.println("Nombre Estado Destino: " + eD.getNombreEstadoTramite());
                            listaEstadoTramiteDestino.add(eD);
                        } else {
                            System.out.println("Estado de Destino no encontrado: " + dtoD.getCodEstadoTramite());
                        }

                        if (!estadoOrigenExiste(dtoD.getCodEstadoTramite(), dtoDatosVersion.getDtoEstadoOrigenList())) {
                            JOptionPane.showMessageDialog(null, "El estado de destino " + dtoD.getCodEstadoTramite() + " no tiene un estado de origen correspondiente.", "Error", JOptionPane.ERROR_MESSAGE);
                            return false;  // No continuar si hay estados de destino sin origen
                        }

                    }

                    // Asignar la lista de estados de destino
                    cttet.setEstadoTramiteDestino(listaEstadoTramiteDestino);

                    // Incrementar el contador para el siguiente estado de origen
                    i++;

                    // Añadir configuración a la versión
                    nueva.addConfTipoTramiteEstadoTramite(cttet);
                    nueva.setDibujo(dtoDatosVersion.getDibujo());
                    // Guardar la configuración en la base de datos

                    //probando comprobacion de estado origen q no quede suelto 
                    if (dtoO.getDtoEstadoDestinoList().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "El estado de origen " + dtoO.getCodEstadoTramite() + " no tiene estados de destino conectados.", "Error", JOptionPane.ERROR_MESSAGE);
                        return false;  // No continuar si algún estado está desconectado
                    }

                    f.guardar(cttet);
                } else {
                    System.out.println("Estado de Origen no encontrado: " + dtoO.getCodEstadoTramite());
                }
            }

            // Verificar que la fecha hasta no sea menor que la fecha desde
            // Guardar la versión
            f.guardar(nueva);
            f.finalizarTransaccion();

            System.out.println("Versión guardada correctamente.");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public DTOVersionM modificarVersion(int codTipoTramite) {

        DTOVersionM dtoVersionM = new DTOVersionM();

        // Iniciar la transacción
        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();
        int v = obtenerUltimoNumeroVersion(codTipoTramite);
        dto.setAtributo("nroVersion");
        dto.setOperacion("=");
        dto.setValor(v);
        criterioList.add(dto);

        List<Object> lVersion = FachadaPersistencia.getInstance().buscar("Version", criterioList);

        if (lVersion.isEmpty()) {

        } else {
            Version versionEncontrada = (Version) lVersion.get(0);

            dtoVersionM.setNroVersion(versionEncontrada.getNroVersion());
            dtoVersionM.setDescripcionVersion(versionEncontrada.getDescripcionVersion());
            dtoVersionM.setFechaDesdeVersion(versionEncontrada.getFechaDesdeVersion());
            dtoVersionM.setFechaHastaVersion(versionEncontrada.getFechaHastaVersion());
            dtoVersionM.setDibujo(versionEncontrada.getDibujo());

        }

        // Buscar los EstadosTramite activos (que no están dados de baja)
        criterioList = new ArrayList<>();
        dto = new DTOCriterio();
        dto.setAtributo("fechaHoraBajaEstadoTramite");
        dto.setOperacion("=");
        dto.setValor(null);
        criterioList.add(dto);

        List<Object> lEstadoTramite = FachadaPersistencia.getInstance().buscar("EstadoTramite", criterioList);
        for (Object o : lEstadoTramite) {
            EstadoTramite et = (EstadoTramite) o;
            DTOEstado de = new DTOEstado();
            de.setCodEstadoTramite(et.getCodEstadoTramite());
            de.setNombreEstadoTramite(et.getNombreEstadoTramite());
            dtoVersionM.addDTOEstado(de);
        }

        return dtoVersionM;
    }

    public List<DTOTipoTramiteVersion> mostrarVersion() {
    // Inicia la transacción
    FachadaPersistencia.getInstance().iniciarTransaccion();
    
    // Crea la lista de criterios si quieres agregar filtros, o déjala vacía para obtener todas las versiones
    List<DTOCriterio> criterioList = new ArrayList<>();
    
    // Busca todas las versiones en la base de datos
    List<Object> lVersiones = FachadaPersistencia.getInstance().buscar("Version", criterioList);
    
    // Crea una lista para devolver los DTOs
    List<DTOTipoTramiteVersion> dtoVersiones = new ArrayList<>();
    
    // Itera sobre los resultados y mapea los atributos al DTO
    for (Object obj : lVersiones) {
        Version version = (Version) obj;
        
        DTOTipoTramiteVersion dto = new DTOTipoTramiteVersion();
        dto.setNroVersion(version.getNroVersion());
        dto.setFechaDesdeVersion(version.getFechaDesdeVersion());
        dto.setFechaHastaVersion(version.getFechaHastaVersion());
        dto.setNombreTipoTramite(version.getTipoTramite().getNombreTipoTramite());
        dto.setCodTipoTramite(version.getTipoTramite().getCodTipoTramite());
        
        // Agrega el DTO a la lista
        dtoVersiones.add(dto);
    }
    
    // Finaliza la transacción
    FachadaPersistencia.getInstance().finalizarTransaccion();
    
    // Devuelve la lista de versiones como DTOs
    return dtoVersiones;
}
}
