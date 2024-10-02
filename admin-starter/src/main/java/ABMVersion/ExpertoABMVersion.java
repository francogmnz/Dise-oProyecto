package ABMVersion;

import ABMVersion.ControladorABMVersion;
import ABMVersion.dtos.DTODatosVersion;
import ABMVersion.dtos.DTODatosVersionIn;
import ABMVersion.dtos.DTOEstadoDestinoIN;
import ABMVersion.dtos.DTOEstadoOrigenIN;
import ABMVersion.dtos.DTOVersionM;
import ABMVersion.dtos.VersionDTO;
import ABMVersion.dtos.DTOEstado;
import ABMVersion.dtos.DTOEstadoDestinoOUT;
import ABMVersion.dtos.DTOEstadoOrigenOUT;
import ABMVersion.exceptions.VersionException;
import entidades.ConfTipoTramiteEstadoTramite;
import entidades.EstadoTramite;
import entidades.TipoTramite;
import entidades.Version;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import utils.DTOCriterio;
import utils.FachadaPersistencia;

public class ExpertoABMVersion {

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

    // Método para buscar una versión para modificar
    public DTOVersionM buscarVersionAModificar(int nroVersion) {
        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();

        dto.setAtributo("nroVersion");
        dto.setOperacion("=");
        dto.setValor(nroVersion);

        criterioList.add(dto);

        List<Object> lVersion = FachadaPersistencia.getInstance().buscar("Version", criterioList);

        if (lVersion.isEmpty()) {
            return null; // Manejo adecuado si no se encuentra la versión
        }

        Version versionEncontrada = (Version) lVersion.get(0);

        DTOVersionM dtoVersionM = new DTOVersionM();
        dtoVersionM.setNroVersion(versionEncontrada.getNroVersion()); // Asegúrate de incluir el nroVersion
        dtoVersionM.setDescripcionVersion(versionEncontrada.getDescripcionVersion());
        dtoVersionM.setFechaDesdeVersion(versionEncontrada.getFechaDesdeVersion());
        dtoVersionM.setFechaHastaVersion(versionEncontrada.getFechaHastaVersion());
        dtoVersionM.setDibujo(versionEncontrada.getDibujo());

        return dtoVersionM;
    }

    public void darDeBajaVersion(int nroVersion) throws VersionException {
        FachadaPersistencia.getInstance().iniciarTransaccion();

        // Crear el criterio de búsqueda para encontrar la versión
        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();

        dto.setAtributo("nroVersion");
        dto.setOperacion("=");
        dto.setValor(nroVersion);

        criterioList.add(dto);

        // Buscar la versión con el criterio dado
        List<Object> lVersion = FachadaPersistencia.getInstance().buscar("Version", criterioList);

        if (lVersion.isEmpty()) {
            // Lanzar una excepción si la versión no se encuentra
            throw new VersionException("La versión no existe");
        }

        // Obtener la versión encontrada y actualizar la fecha de baja
        Version versionEncontrada = (Version) lVersion.get(0);
        versionEncontrada.setFechaBajaVersion(new Timestamp(System.currentTimeMillis()));

        // Guardar los cambios y finalizar la transacción
        FachadaPersistencia.getInstance().guardar(versionEncontrada);
        FachadaPersistencia.getInstance().finalizarTransaccion();
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
        TipoTramite t=null;
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
                int ultimo=0;
                for(Object ob:versiones)
                {
                    Version ultimaVersion = (Version) ob;
                    if(ultimaVersion.getNroVersion() > ultimo)
                    {
                        ultimo=ultimaVersion.getNroVersion();
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

    private boolean versionEncontrada(int nroVersion) {
        FachadaPersistencia f = FachadaPersistencia.getInstance(); // Instancia de FachadaPersistencia
        try {
            f.iniciarTransaccion(); // Inicia la transacción

            // Crear el criterio de búsqueda
            List<DTOCriterio> lc = new ArrayList<>();
            DTOCriterio c = new DTOCriterio();
            c.setAtributo("nroVersion");
            c.setOperacion("=");
            c.setValor(nroVersion); // Busco la versión con el número dado
            lc.add(c);

            // Hago la búsqueda en la base de datos
            List<Object> versiones = f.buscar("Version", lc);

            // Si la lista no está vacía, significa que ya existe una versión con ese número
            return !versiones.isEmpty();
        } finally {
            f.finalizarTransaccion(); // Finaliza la transacción
        }
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
        nueva.setFechaDesdeVersion(new Timestamp(dtoDatosVersion.getFechaDesdeVersion().getTime()));
        nueva.setFechaHastaVersion(new Timestamp(dtoDatosVersion.getFechaHastaVersion().getTime()));
        //funciona este control de fecha!
        if (nueva.getFechaHastaVersion().before(nueva.getFechaDesdeVersion())) {
            JOptionPane.showMessageDialog(null, "La fecha hasta no puede ser menor que la fecha desde.", "Error", JOptionPane.ERROR_MESSAGE);
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
            int cont = 1;  // Mover la declaración de `cont` fuera del ciclo

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
                    cttet.setContadorConfigTTET(cont);  // Asignar el valor actual del contador
                    cttet.setXpos(dtoO.getXpos()); // Establecer posición X
                    cttet.setYpos(dtoO.getYpos()); // Establecer posición Y
                    cont++;  // Incrementar el contador después de cada configuración guardada

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
                    }

                    // Asignar la lista de estados de destino
                    cttet.setEstadoTramiteDestino(listaEstadoTramiteDestino);

                    // Incrementar el contador para el siguiente estado de origen
                    i++;

                    // Añadir configuración a la versión
                    nueva.addConfTipoTramiteEstadoTramite(cttet);
                    nueva.setDibujo(dtoDatosVersion.getDibujo());
                    // Guardar la configuración en la base de datos
   

                    f.guardar(cttet);
                } else {
                    System.out.println("Estado de Origen no encontrado: " + dtoO.getCodEstadoTramite());
                }
            }

            // Guardar la versión
            // Verificar que la fecha hasta no sea menor que la fecha desde
            f.guardar(nueva);
            f.finalizarTransaccion();

            System.out.println("Versión guardada correctamente.");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* public void modificarVersion(int codTipoTramite, DTOVersionM dtoVersionm) throws VersionException {
        FachadaPersistencia fachada = FachadaPersistencia.getInstance();

        // Iniciar la transacción
        fachada.iniciarTransaccion();

        try {
            // Imprimir el codTipoTramite
            System.out.println("Buscando versión para codTipoTramite: " + codTipoTramite);

            // Crear criterio para buscar la versión por codTipoTramite
            List<DTOCriterio> criterioList = new ArrayList<>();
            DTOCriterio dtoTipo = new DTOCriterio();
            dtoTipo.setAtributo("codTipoTramite");
            dtoTipo.setOperacion("=");
            dtoTipo.setValor(codTipoTramite);
            criterioList.add(dtoTipo);

            // Buscar la versión
            List<Object> listaVersiones = fachada.buscar("Version", criterioList);

            if (listaVersiones.isEmpty()) {
                throw new VersionException("No se encontró la versión para el tipo de trámite especificado.");
            }

            // Tomar la primera versión encontrada
            Version versionEncontrada = (Version) listaVersiones.get(0);

            // Modificar los atributos de la versión con los datos del DTO
            versionEncontrada.setNroVersion(dtoVersionm.getNroVersion());
            versionEncontrada.setDescripcionVersion(dtoVersionm.getDescripcionVersion());

            // Establecer fechas desde y hasta, según lo que venga en el DTO
            if (dtoVersionm.getFechaDesdeVersion() != null) {
                versionEncontrada.setFechaDesdeVersion(new Timestamp(dtoVersionm.getFechaDesdeVersion().getTime()));
            }

            if (dtoVersionm.getFechaHastaVersion() != null) {
                versionEncontrada.setFechaHastaVersion(new Timestamp(dtoVersionm.getFechaHastaVersion().getTime()));
            }

            // Guardar los cambios
            fachada.guardar(versionEncontrada);

            // Finalizar la transacción
            fachada.finalizarTransaccion();

        } catch (Exception e) {
            try {
                // Rollback en caso de error
                fachada.finalizarTransaccion();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            throw new VersionException("Error al modificar la versión: " + e.getMessage(), e);
        }
    }
     */
    public DTOVersionM modificarVersion(int codTipoTramite) {
        DTOVersionM dtoVersionM = new DTOVersionM();
        // Iniciar la transacción
               List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();
        int v=obtenerUltimoNumeroVersion(codTipoTramite);
        dto.setAtributo("nroVersion");
        dto.setOperacion("=");
        dto.setValor(v);

        criterioList.add(dto);

        List<Object> lVersion = FachadaPersistencia.getInstance().buscar("Version", criterioList);
        
        if (lVersion.isEmpty()) {
            
        }
        else
        {
        Version versionEncontrada = (Version) lVersion.get(0);

        
        dtoVersionM.setNroVersion(versionEncontrada.getNroVersion()); // Asegúrate de incluir el nroVersion
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

}