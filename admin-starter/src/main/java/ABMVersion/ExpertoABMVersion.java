package ABMVersion;

import ABMVersion.dtos.DTODatosVersionH;
import ABMVersion.dtos.DTODatosVersionIn;
import ABMVersion.dtos.DTOEstadoDestinoIN;
import ABMVersion.dtos.DTOEstadoOrigenIN;
import ABMVersion.dtos.DTOVersionM;
import ABMVersion.dtos.DTOEstado;
import ABMVersion.dtos.DTOTipoTramiteVersion;
import ABMVersion.dtos.DTOVersionH;
import ABMVersion.exceptions.VersionException;
import entidades.ConfTipoTramiteEstadoTramite;
import entidades.EstadoTramite;
import entidades.TipoTramite;
import entidades.Version;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import org.omnifaces.util.Messages;
import utils.DTOCriterio;
import utils.FachadaPersistencia;

public class ExpertoABMVersion {

    public List<DTOTipoTramiteVersion> mostrarVersion(int codTipoTramite, String nombreTipoTramite) {
        // Inicia la transacción
        FachadaPersistencia.getInstance().iniciarTransaccion();

        // Busca todos los tipos de trámite
        List<DTOCriterio> criterioTipoTramite = new ArrayList<>();
        if (codTipoTramite > 0) {
            DTOCriterio criterioTipo = new DTOCriterio();
            criterioTipo.setAtributo("codTipoTramite");
            criterioTipo.setOperacion("=");
            criterioTipo.setValor(codTipoTramite);
            criterioTipoTramite.add(criterioTipo);
        }

        // Agregar criterio para nombreTipoTramite
        if (nombreTipoTramite != null && !nombreTipoTramite.trim().isEmpty()) {
            DTOCriterio criterioNombre = new DTOCriterio();
            criterioNombre.setAtributo("nombreTipoTramite");
            criterioNombre.setOperacion("like");
            criterioNombre.setValor(nombreTipoTramite);
            criterioTipoTramite.add(criterioNombre);
        }

        List<Object> lTiposTramite = FachadaPersistencia.getInstance().buscar("TipoTramite", criterioTipoTramite);

        // Crea una lista para devolver los DTOs
        List<DTOTipoTramiteVersion> dtoVersiones = new ArrayList<>();

        // Itera sobre los tipos de trámite
        for (Object objTipoTramite : lTiposTramite) {
            TipoTramite tipoTramite = (TipoTramite) objTipoTramite;

            // Crea los criterios para buscar solo las versiones activas para el tipo de trámite actual
            List<DTOCriterio> criterioVersion = new ArrayList<>();

            DTOCriterio criterioTipo = new DTOCriterio();
            criterioTipo.setAtributo("tipoTramite");
            criterioTipo.setOperacion("=");
            criterioTipo.setValor(tipoTramite);
            criterioVersion.add(criterioTipo);

            DTOCriterio criterioFechaBaja = new DTOCriterio();
            criterioFechaBaja.setAtributo("fechaBajaVersion");
            criterioFechaBaja.setOperacion("=");
            criterioFechaBaja.setValor(null);  // Solo versiones sin fecha de baja
            criterioVersion.add(criterioFechaBaja);

            // Busca las versiones activas del tipo de trámite actual
            List<Object> lVersiones = FachadaPersistencia.getInstance().buscar("Version", criterioVersion);

            // Si no hay versiones, agregar un DTO con valores por defecto
            if (lVersiones.isEmpty()) {
                DTOTipoTramiteVersion dtoSinVersion = new DTOTipoTramiteVersion();
                dtoSinVersion.setCodTipoTramite(tipoTramite.getCodTipoTramite());
                dtoSinVersion.setNombreTipoTramite(tipoTramite.getNombreTipoTramite());
                dtoSinVersion.setNroVersion(0);  // Código de versión 0 indica que no hay versiones
                dtoSinVersion.setFechaDesdeVersion(null);
                dtoSinVersion.setFechaHastaVersion(null);
                dtoVersiones.add(dtoSinVersion);
            } else {
                // Si hay versiones, agregar el DTO con la versión más reciente
                for (Object objVersion : lVersiones) {
                    Version version = (Version) objVersion;

                    DTOTipoTramiteVersion dto = new DTOTipoTramiteVersion();
                    dto.setNroVersion(version.getNroVersion());
                    dto.setFechaDesdeVersion(version.getFechaDesdeVersion());
                    dto.setFechaHastaVersion(version.getFechaHastaVersion());
                    dto.setNombreTipoTramite(version.getTipoTramite().getNombreTipoTramite());
                    dto.setCodTipoTramite(version.getTipoTramite().getCodTipoTramite());

                    dtoVersiones.add(dto);
                }
            }
        }

        // Finaliza la transacción
        FachadaPersistencia.getInstance().finalizarTransaccion();

        // Devuelve la lista de versiones como DTOs
        return dtoVersiones;
    }

    public int obtenerUltimoNumeroVersion(int codTipoTramite) {
        FachadaPersistencia f = FachadaPersistencia.getInstance();

        f.iniciarTransaccion();

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
            // Crear el criterio de búsqueda para obtener el último número de versión
            lc = new ArrayList<>();
            DTOCriterio cTipoTramite = new DTOCriterio();
            cTipoTramite.setAtributo("tipoTramite");
            cTipoTramite.setOperacion("=");
            cTipoTramite.setValor(t);
            lc.add(cTipoTramite);

            // Filtrar por fechaBajaVersion nula
            DTOCriterio cFechaBaja = new DTOCriterio();
            cFechaBaja.setAtributo("fechaBajaVersion");
            cFechaBaja.setOperacion("=");
            cFechaBaja.setValor(null);
            lc.add(cFechaBaja);

            List<Object> versiones = f.buscar("Version", lc);

            System.out.println("Versiones encontradas: " + versiones.size()); // Depuración

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
            e.printStackTrace();
        } finally {
            f.finalizarTransaccion();
        }

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
        // Configurar los demás atributos de la versión
        nueva.setDescripcionVersion(dtoDatosVersion.getDescripcionVersion());
// Configura la zona horaria deseada (por ejemplo, "America/Argentina/Buenos_Aires")
        ZoneId zonaHorariaArgentina = ZoneId.of("America/Argentina/Buenos_Aires");

// Convertir FechaDesdeVersion a la zona horaria de Argentina y guardar como Timestamp
        ZonedDateTime fechaDesdeArgentina = dtoDatosVersion.getFechaDesdeVersion().toInstant().atZone(zonaHorariaArgentina);
        nueva.setFechaDesdeVersion(Timestamp.valueOf(fechaDesdeArgentina.toLocalDateTime()));

// Convertir FechaHastaVersion a la zona horaria de Argentina y guardar como Timestamp
        ZonedDateTime fechaHastaArgentina = dtoDatosVersion.getFechaHastaVersion().toInstant().atZone(zonaHorariaArgentina);
        nueva.setFechaHastaVersion(Timestamp.valueOf(fechaHastaArgentina.toLocalDateTime()));
        // Debug: Imprimir las fechas
        System.out.println("Fecha Desde: " + nueva.getFechaDesdeVersion());
        System.out.println("Fecha Hasta: " + nueva.getFechaHastaVersion());

        if (dtoDatosVersion.getFechaDesdeVersion() == null) {
            Messages.create("Error").detail("La fecha desde no puede estar vacia").error().add();
            return false;
        }

        if (dtoDatosVersion.getFechaHastaVersion() == null) {
            Messages.create("Error").detail("La fecha hasta no puede estar vacia").error().add();
            return false;
        }
        // Verificar que la fecha hasta no sea menor que la fecha desde
        if (nueva.getFechaHastaVersion().before(nueva.getFechaDesdeVersion())) {
            Messages.create("Error").detail("La fecha hasta no puede ser menor que la fecha desde.").error().add();
            return false;

        }

        // Verificar que las fechas no sean iguales
        if (nueva.getFechaHastaVersion().before(nueva.getFechaDesdeVersion())) {
            Messages.create("Error").detail("La fecha hasta no puede ser igual que la fecha desde.").error().add();
            return false;
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
                    cttet.setEtapaOrigen(i);
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
                        int d = 1;
                        d++;
                        cttet.setEtapaDestino(d);// Asignar etapa de origen
                        List<Object> resultadoDestino = f.buscar("EstadoTramite", lcDestino);
                        if (!resultadoDestino.isEmpty()) {
                            EstadoTramite eD = (EstadoTramite) resultadoDestino.get(0);
                            System.out.println("Nombre Estado Destino: " + eD.getNombreEstadoTramite());
                            listaEstadoTramiteDestino.add(eD);
                        } else {
                            System.out.println("Estado de Destino no encontrado: " + dtoD.getCodEstadoTramite());
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
                    f.guardar(cttet);
                } else {
                    System.out.println("Estado de Origen no encontrado: " + dtoO.getCodEstadoTramite());
                }
            }

            // Método para verificar huecos entre versiones
            lc.clear();

            List<DTOCriterio> criterioList = new ArrayList<>();
            DTOCriterio tipoTramiteCriterio = new DTOCriterio();
            tipoTramiteCriterio.setAtributo("codTipoTramite");
            tipoTramiteCriterio.setOperacion("=");
            tipoTramiteCriterio.setValor(codTipoTramite);
            criterioList.add(tipoTramiteCriterio);

            TipoTramite tipoTramiteEncontrado = (TipoTramite) FachadaPersistencia.getInstance().buscar("TipoTramite", criterioList).get(0);

            criterioList.clear();

// Criterio para el código de tipo de trámite
            DTOCriterio tipoTramiteCriterio1 = new DTOCriterio();
            tipoTramiteCriterio1.setAtributo("tipoTramite");
            tipoTramiteCriterio1.setOperacion("=");
            tipoTramiteCriterio1.setValor(tipoTramiteEncontrado);
            criterioList.add(tipoTramiteCriterio1);

// Criterio para filtrar versiones activas (sin fecha de baja)
            DTOCriterio fechaVersion = new DTOCriterio();
            fechaVersion.setAtributo("fechaBajaVersion");
            fechaVersion.setOperacion("=");
            fechaVersion.setValor(null);  // Solo versiones activas
            criterioList.add(fechaVersion);

            List<Object> lVersion = FachadaPersistencia.getInstance().buscar("Version", criterioList);
            List<Version> listVersion = new ArrayList<>();
            for (Object obj : lVersion) {
                if (obj instanceof Version) {
                    listVersion.add((Version) obj);
                } else {
                    System.out.println("Elemento encontrado en lVersion no es de tipo Version: " + obj);
                }
            }

// Obtener la última versión
            Version ultVersion = null;
            for (Version version : listVersion) {
                if (ultVersion == null || ultVersion.getFechaDesdeVersion().compareTo(version.getFechaDesdeVersion()) < 0) {
                    ultVersion = version;
                }
            }
            Version vultima = (Version) ultVersion;

            //Voy comparardo y tiro error fatal
            Timestamp FDNv = nueva.getFechaDesdeVersion();

            if (vultima != null) {
                Timestamp FDUv = vultima.getFechaDesdeVersion();
                if (FDNv.before(FDUv)) {
                    Messages.create("Error").detail("La versión nueva está antes que la última versión. Existen versiones futuras.").error().add();
                    return false;  // Asegúrate de detener la ejecución si hay un error
                }
                Timestamp FHUv = vultima.getFechaHastaVersion();
                if (FDNv.after(FHUv)) {
                    Messages.create("Error").detail("La versión nueva está después de la última versión. No puede haber huecos entre versiones.").error().add();
                    return false;
                }
                //Significa que fechaHoraDesdeVersion(ÚltimaVersion)< fechaHoraDesdeVersion(NuevaVersion)<=fechaHoraHastaVersion(ÚltimaVersion)        
                if (FDNv.equals(FDUv)) {
                    Messages.create("Error").detail("La nueva versión tiene la misma fecha de inicio que la última versión. Debe anular la versión anterior antes de crear una nueva.").error().add();
                    return false;
                }
            }

            Timestamp FHNv = nueva.getFechaHastaVersion();
            if (FHNv.equals(FDNv)) {
                Messages.create("Error").detail("La versión nueva debe tener fechas 'desde' y 'hasta' diferentes.").error().add();
                return false;
            }

            if (FHNv.before(FDNv)) {
                Messages.create("Error").detail("La versión nueva no puede tener una fecha 'desde' mayor que la fecha 'hasta'.").error().add();
                return false;
            }
//cambio la ultima version:
            if (vultima != null) {
                vultima.setFechaHastaVersion(FDNv);
            }
// Guardar la nueva versión
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

        // Obtener el número de la última versión del tipo de trámite
        int v = obtenerUltimoNumeroVersion(codTipoTramite);

        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio tipoTramiteCriterio = new DTOCriterio();
        tipoTramiteCriterio.setAtributo("codTipoTramite");
        tipoTramiteCriterio.setOperacion("=");
        tipoTramiteCriterio.setValor(codTipoTramite);
        criterioList.add(tipoTramiteCriterio);

        TipoTramite tipoTramiteEncontrado = (TipoTramite) FachadaPersistencia.getInstance().buscar("TipoTramite", criterioList).get(0);

        criterioList.clear();

// Criterio para el código de tipo de trámite
        DTOCriterio tipoTramiteCriterio1 = new DTOCriterio();
        tipoTramiteCriterio1.setAtributo("tipoTramite");
        tipoTramiteCriterio1.setOperacion("=");
        tipoTramiteCriterio1.setValor(tipoTramiteEncontrado);
        criterioList.add(tipoTramiteCriterio1);

// Criterio para el número de versión
        DTOCriterio versionCriterio = new DTOCriterio();
        versionCriterio.setAtributo("nroVersion");
        versionCriterio.setOperacion("=");
        versionCriterio.setValor(v);
        criterioList.add(versionCriterio);

// Criterio para filtrar versiones activas (sin fecha de baja)
        DTOCriterio fechaVersion = new DTOCriterio();
        fechaVersion.setAtributo("fechaBajaVersion");
        fechaVersion.setOperacion("=");
        fechaVersion.setValor(null);  // Solo versiones activas
        criterioList.add(fechaVersion);

        // Buscar la versión con el criterio dado
        List<Object> lVersion = FachadaPersistencia.getInstance().buscar("Version", criterioList);

        if (lVersion.isEmpty()) {
            // No se encontró la versión
        } else {
            // Obtener la versión encontrada y mapear a DTO
            Version versionEncontrada = (Version) lVersion.get(0);

            dtoVersionM.setNroVersion(versionEncontrada.getNroVersion());
            dtoVersionM.setDescripcionVersion(versionEncontrada.getDescripcionVersion());
            dtoVersionM.setFechaDesdeVersion(versionEncontrada.getFechaHastaVersion());
//            dtoVersionM.setFechaHastaVersion(versionEncontrada.getFechaHastaVersion());
            dtoVersionM.setDibujo(versionEncontrada.getDibujo());
        }

        // Buscar los EstadosTramite activos (que no están dados de baja)
        criterioList = new ArrayList<>();
        DTOCriterio estadoTramiteCriterio = new DTOCriterio();
        estadoTramiteCriterio.setAtributo("fechaHoraBajaEstadoTramite");
        estadoTramiteCriterio.setOperacion("=");
        estadoTramiteCriterio.setValor(null);  // Solo estados activos
        criterioList.add(estadoTramiteCriterio);

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

    /*   public DTOVersionM VerVersion(int codTipoTramite, int nroVersion) {

        DTOVersionM dtoVersionM = new DTOVersionM();

        // Obtener el número de la última versión del tipo de trámite
        int v = obtenerUltimoNumeroVersion(codTipoTramite);

        List<DTOCriterio> criterioList = new ArrayList<>();        

        DTOCriterio versionCriterio = new DTOCriterio();
        versionCriterio.setAtributo("nroVersion");
        versionCriterio.setOperacion("=");
        versionCriterio.setValor(v);
        criterioList.add(versionCriterio);

        // Buscar la versión con el criterio dado
        List<Object> lVersion = FachadaPersistencia.getInstance().buscar("Version", criterioList);

        if (lVersion.isEmpty()) {
            // No se encontró la versión
        } else {
            // Obtener la versión encontrada y mapear a DTO
            Version versionEncontrada = (Version) lVersion.get(0);

            dtoVersionM.setNroVersion(versionEncontrada.getNroVersion());
            dtoVersionM.setDescripcionVersion(versionEncontrada.getDescripcionVersion());
            dtoVersionM.setFechaDesdeVersion(versionEncontrada.getFechaHastaVersion());
//            dtoVersionM.setFechaHastaVersion(versionEncontrada.getFechaHastaVersion());
            dtoVersionM.setDibujo(versionEncontrada.getDibujo());
        }
        return dtoVersionM;
    }
     */
    public void anularVersion(int codTipoTramite, int nroVersion) throws VersionException {
        // Iniciar transacción
        FachadaPersistencia.getInstance().iniciarTransaccion();

        // Verificar existencia del TipoTramite el primer buscar
        List<DTOCriterio> lCriterio = new ArrayList<>();

        DTOCriterio criterioTipoTramite = new DTOCriterio();

        DTOCriterio criterioFechaBaja = new DTOCriterio();
        criterioFechaBaja.setAtributo("fechaBajaVersion");
        criterioFechaBaja.setOperacion("=");
        criterioFechaBaja.setValor(null);
        lCriterio.add(criterioFechaBaja);

        DTOCriterio criterioNroVersion = new DTOCriterio();
        criterioNroVersion.setAtributo("nroVersion");
        criterioNroVersion.setOperacion("=");
        criterioNroVersion.setValor(nroVersion);
        lCriterio.add(criterioNroVersion);

        List<Object> objetoList2 = FachadaPersistencia.getInstance().buscar("Version", lCriterio);

        if (objetoList2.isEmpty()) {
            throw new VersionException("La versión no se puede anular");
        }
        Version versionAnula = (Version) objetoList2.get(0);//transformo el primer elemento a version

        // Buscar última versión válida
        lCriterio.clear();

        DTOCriterio criterioFechaBajaUltima = new DTOCriterio();
        criterioFechaBajaUltima.setAtributo("fechaBajaVersion");
        criterioFechaBajaUltima.setOperacion("=");
        criterioFechaBajaUltima.setValor(null);
        lCriterio.add(criterioFechaBajaUltima);

        List<Object> objetoList3 = FachadaPersistencia.getInstance().buscar("Version", lCriterio);
        //aca recorro todas las ultimas versiones sin fecha de baja
        Version ultVersion = null;
        for (Object obj : objetoList3) {
            Version version = (Version) obj;  // aca convierto  el Object en Version pq sino da error
            // verifico si es la primera versión o si la versión actual tiene una fecha de inicio más reciente
            if (ultVersion == null || version.getFechaDesdeVersion().compareTo(ultVersion.getFechaDesdeVersion()) > 0) {
                ultVersion = version; //aca establesco la version ult como null para q sea
            }
        }

        //Verifica si no se encontró ninguna versión activa o si la última versión activa no es la que se intenta anular
        if (ultVersion == null || ultVersion.getNroVersion() != versionAnula.getNroVersion()) {
            throw new VersionException("Existen versiones futuras. Solo se puede anular la última futura.");
        }

        //Verificacion para que no anule si es activa
        Timestamp fechaActual = new Timestamp(System.currentTimeMillis());

        if (ultVersion.getFechaDesdeVersion().before(fechaActual)
                && (ultVersion.getFechaHastaVersion() == null || ultVersion.getFechaHastaVersion().after(fechaActual))) {
            throw new VersionException("No se puede anular la versión porque es una versión vigente");

        }

        // Anular la versión estableciendo la fecha de baja a la fecha y hora actuales
        ultVersion.setFechaBajaVersion(new Timestamp(System.currentTimeMillis()));
        FachadaPersistencia.getInstance().guardar(ultVersion);

        // Actualizar la penúltima versión
        // Inicializar la variable para almacenar la penúltima versión
        Version penultVersion = null;
        for (Object obj : objetoList3) {
            Version version = (Version) obj;  // Convertir el objeto actual a tipo Version

            // Comparar con la última versión para encontrar la penúltima
            if (penultVersion == null || version.getFechaDesdeVersion().compareTo(penultVersion.getFechaDesdeVersion()) > 0) {
                // Asegurarse de que la versión no sea la última
                if (version.getNroVersion() != ultVersion.getNroVersion()) {
                    penultVersion = version;
                }
            }
        }
        // Si se encontró una penúltima versión, actualizar su fecha de finalización
        if (penultVersion != null) {
            penultVersion.setFechaHastaVersion(ultVersion.getFechaHastaVersion());  // Establecer la fecha de finalización
            FachadaPersistencia.getInstance().guardar(penultVersion);
        }

        // Finalizar transacción
        FachadaPersistencia.getInstance().finalizarTransaccion();
    }

    public DTOVersionH mostrarHistoricoVersion(int codTipoTramite) throws VersionException {

        // Inicia la transacción
        FachadaPersistencia.getInstance().iniciarTransaccion();

        List<DTOCriterio> lc = new ArrayList<>();
        DTOCriterio criterioTipoTramite = new DTOCriterio();
        criterioTipoTramite.setAtributo("codTipoTramite");
        criterioTipoTramite.setOperacion("=");
        criterioTipoTramite.setValor(codTipoTramite);
        lc.add(criterioTipoTramite);

        TipoTramite tipoTramite = (TipoTramite) FachadaPersistencia.getInstance().buscar("TipoTramite", lc).get(0);

        if (tipoTramite == null) {
            throw new VersionException("Tipo de trámite no encontrado.");
        }

        List<DTOCriterio> criteriosVersion = new ArrayList<>();
        DTOCriterio criterioVersion = new DTOCriterio();
        criterioVersion.setAtributo("tipoTramite");
        criterioVersion.setOperacion("=");
        criterioVersion.setValor(tipoTramite);
        criteriosVersion.add(criterioVersion);

        List<Object> lVersiones = FachadaPersistencia.getInstance().buscar("Version", criteriosVersion);

        DTOVersionH dtoVersionH = new DTOVersionH();
        dtoVersionH.setCodTipoTramite(tipoTramite.getCodTipoTramite());
        dtoVersionH.setNombreTipoTramite(tipoTramite.getNombreTipoTramite());  // Suponiendo que existe un campo nombre

        // Crea la lista para los datos de las versiones
        List<DTODatosVersionH> listaDatosVersionH = new ArrayList<>();

        // Itera sobre los resultados y mapea los atributos al DTO
        for (Object obj : lVersiones) {
            Version version = (Version) obj;

            // Crea un nuevo DTO para los datos de la versión
            DTODatosVersionH datosVersionH = new DTODatosVersionH();
            datosVersionH.setNroVersion(version.getNroVersion());
            datosVersionH.setFechaDesdeVersion(version.getFechaDesdeVersion());
            datosVersionH.setFechaHastaVersion(version.getFechaHastaVersion());
            datosVersionH.setDescripcionVersion(version.getDescripcionVersion());
            datosVersionH.setFechaBajaVersion(version.getFechaBajaVersion());
            // Agrega el DTO a la lista
            listaDatosVersionH.add(datosVersionH);
        }

        // Asigna la lista de datos de versiones al DTO principal
        dtoVersionH.setDtoDatosVersionH(listaDatosVersionH);

        // Devuelve el DTO con las versiones históricas
        return dtoVersionH;
    }

}
