package utils;


import entidades.*;
import java.sql.Date;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class EjemplosPersistencia {

    /**
     * Esto es solo para que se use de ejemplo.
     *
     * Hay que tener en cuenta que la mayoria de los metodos se basan en un camino feliz en el que los objetos
     * ya se encuentran persistidos en la base de datos y no se hace una comprobacion previa.
     *
     * Se recomienda llamar a crearElementos() para que se creen todos los objetos necesarios para los siguientes
     * metodos.
     *
     * Cualquier cosa, consultar a: minellinh@gmail.com
     */
    public void Metodo() {
        // Reemplazar la siguiente linea con el metodo que se desea probar.
        crearElementos2();
    }

    /**
     * Crea un estado, un articulo con dicho estado, una reposicion con dicho estado y un detalle para esa reposicion.
     */
    public void crearElementos() {
        FachadaPersistencia.getInstance().iniciarTransaccion();
        
        
        

        Estado creada = new Estado();
        creada.setCodigo(1);
        creada.setNombre("creada");
        creada.setFechaAlta(new Timestamp(System.currentTimeMillis()));

        FachadaPersistencia.getInstance().guardar(creada);

        Estado oferta = new Estado();
        oferta.setCodigo(2);
        oferta.setNombre("oferta");
        oferta.setFechaAlta(new Timestamp(System.currentTimeMillis()));

        FachadaPersistencia.getInstance().guardar(oferta);

        Articulo articulo = new Articulo();
        articulo.setCodigo(1);
        articulo.setEstado(creada);
        articulo.setNombre("Agua");
        articulo.setFechaAlta(new Timestamp(System.currentTimeMillis()));

        FachadaPersistencia.getInstance().guardar(articulo);

        Articulo articuloAlfajor = new Articulo();
        articuloAlfajor.setCodigo(6);
        articuloAlfajor.setEstado(oferta);
        articuloAlfajor.setNombre("Alfajor");
        articuloAlfajor.setFechaAlta(new Timestamp(System.currentTimeMillis()));

        FachadaPersistencia.getInstance().guardar(articuloAlfajor);

        Articulo articuloDoritos = new Articulo();
        articuloDoritos.setCodigo(9);
        articuloDoritos.setEstado(creada);
        articuloDoritos.setNombre("Doritos");
        articuloDoritos.setFechaAlta(new Timestamp(System.currentTimeMillis()));

        FachadaPersistencia.getInstance().guardar(articuloDoritos);

        DetalleReposicion detalle = new DetalleReposicion();
        detalle.setArticulo(articulo);
        detalle.setCantidad(1);

        DetalleReposicion detalle2 = new DetalleReposicion();
        detalle2.setArticulo(articuloAlfajor);
        detalle2.setCantidad(2);


        Reposicion repo = new Reposicion();
        repo.setNumero(1);

        repo.addDetalleReposicionList(detalle);
        repo.addDetalleReposicionList(detalle2);

        FachadaPersistencia.getInstance().guardar(repo);
        
        FachadaPersistencia.getInstance().finalizarTransaccion();
        
    }
    
    public void crearElementos2(){
        
        

        
        try{
            
        FachadaPersistencia.getInstance().iniciarTransaccion();
        
        EstadoTramite etapaUno = new EstadoTramite();
        etapaUno.setCodEstadoTramite(1);
        etapaUno.setNombreEstadoTramite("Nombre1");
        etapaUno.setDescripcionEstadoTramite("Pendiente Doc");
        etapaUno.setFechaHoraAltaEstadoTramite(new Timestamp(System.currentTimeMillis()));
        etapaUno.setFechaHoraBajaEstadoTramite(null);
        
        FachadaPersistencia.getInstance().guardar(etapaUno);

        
        EstadoTramite etapaDos = new EstadoTramite();
        etapaDos.setCodEstadoTramite(2);
        etapaDos.setNombreEstadoTramite("Nombre2");
        etapaDos.setDescripcionEstadoTramite("Pendiente Consultor");
        etapaDos.setFechaHoraAltaEstadoTramite(new Timestamp(System.currentTimeMillis()));
        etapaDos.setFechaHoraBajaEstadoTramite(null);
        
        FachadaPersistencia.getInstance().guardar(etapaDos);

        
            
    CategoriaTipoTramite categoriaA = new CategoriaTipoTramite();
    categoriaA.setCodCategoriaTipoTramite(1);
    categoriaA.setNombreCategoriaTipoTramite("Categoría A");
    categoriaA.setDescripcionCategoriaTipoTramite("Trámites generales");
    categoriaA.setDescripcionWebCategoriaTipoTramite("Categoria donde se resuelven problemas");
    categoriaA.setFechaHoraBajaCategoriaTipoTramite(null);
    
    FachadaPersistencia.getInstance().guardar(categoriaA);


    CategoriaTipoTramite categoriaB = new CategoriaTipoTramite();
    categoriaB.setCodCategoriaTipoTramite(2);
    categoriaB.setNombreCategoriaTipoTramite("Categoría B");
    categoriaB.setDescripcionCategoriaTipoTramite("Trámites especializados");
    categoriaB.setDescripcionWebCategoriaTipoTramite("Categoria donde se resuelven problemas especiales");
    categoriaB.setFechaHoraBajaCategoriaTipoTramite(null);
    
    FachadaPersistencia.getInstance().guardar(categoriaB);

    

    Documentacion doc1 = new Documentacion();
    doc1.setCodDocumentacion(1);
    doc1.setNombreDocumentacion("DNI");
    doc1.setDescripcionDocumentacion("Documento Nacional de Identidad");
    doc1.setFechaHoraBajaDocumentacion(null);
    


    FachadaPersistencia.getInstance().guardar(doc1);


 
    Documentacion doc2 = new Documentacion();
    doc2.setCodDocumentacion(2);
    doc2.setNombreDocumentacion("Constancia de CUIL");
    doc2.setDescripcionDocumentacion("Constancia del Código Único de Identificación Laboral");
    doc2.setFechaHoraBajaDocumentacion(null);

 
    FachadaPersistencia.getInstance().guardar(doc2);
    
    Cliente cliente1 = new Cliente();
    cliente1.setNombreCliente("Juan");
    cliente1.setApellidoCliente("Pérez");
    cliente1.setDniCliente(12345678);
    cliente1.setMailCliente("juan.perez@example.com");
    cliente1.setFechaHoraBajaCliente(null);
    


    FachadaPersistencia.getInstance().guardar(cliente1);



    Cliente cliente2 = new Cliente();
    cliente2.setNombreCliente("María");
    cliente2.setApellidoCliente("González");
    cliente2.setDniCliente(87654321);
    cliente2.setMailCliente("maria.gonzalez@example.com");
    cliente2.setFechaHoraBajaCliente(null);



    FachadaPersistencia.getInstance().guardar(cliente2);

    

    Consultor consultor1 = new Consultor();
    consultor1.setLegajoConsultor(1001);
    consultor1.setNombreConsultor("Carlos Fernández");
    consultor1.setNroMaximoTramites(5);
    consultor1.setFechaHoraBajaConsultor(null);
    

 
    FachadaPersistencia.getInstance().guardar(consultor1);
 


    Consultor consultor2 = new Consultor();
    consultor2.setLegajoConsultor(1002);
    consultor2.setNombreConsultor("Ana López");
    consultor2.setNroMaximoTramites(3);
    consultor2.setFechaHoraBajaConsultor(null);
    


    FachadaPersistencia.getInstance().guardar(consultor2);
    
    AgendaConsultor agendaConsultor1 = new AgendaConsultor();
    
    agendaConsultor1.setCodAgendaConsultor(1);
    agendaConsultor1.setFechaAgenda(new Timestamp(System.currentTimeMillis()));
    agendaConsultor1.setFechaBajaAgendaConsultor(null);
    agendaConsultor1.setFechaAltaAgendaConsultor(new Timestamp(System.currentTimeMillis()));
    
    agendaConsultor1.addConsultor(consultor1);
    
    FachadaPersistencia.getInstance().guardar(agendaConsultor1);
    
    

    /*
    TipoTramiteDocumentacion ttd1 = new TipoTramiteDocumentacion();
    ttd1.setFechaDesdeTTD(new Timestamp(System.currentTimeMillis()));
    ttd1.setFechaHastaTTD(new Timestamp(System.currentTimeMillis()));
    ttd1.setFechaHoraBajaTTD(null);
    
    ttd1.setDocumentacion(doc1);

    // Guardar en la base de datos
    FachadaPersistencia.getInstance().guardar(ttd1);
    
        

    
    

    // Ejemplo 2 - TipoTramiteDocumentacion
    TipoTramiteDocumentacion ttd2 = new TipoTramiteDocumentacion();
    ttd2.setFechaDesdeTTD(new Timestamp(System.currentTimeMillis()));
    ttd2.setFechaHastaTTD(new Timestamp(System.currentTimeMillis()));
    ttd2.setFechaHoraBajaTTD(null);
 
    ttd2.setDocumentacion(doc2);

    // Guardar en la base de datos
    FachadaPersistencia.getInstance().guardar(ttd2);
    
    TipoTramite tipoTramiteA = new TipoTramite();
    tipoTramiteA.setCodTipoTramite(1);
    tipoTramiteA.setDescripcionTipoTramite("TipoTramite de prueba 1");
    tipoTramiteA.setDescripcionWebTipoTramite("TipoTramite de prueba web 1");
    tipoTramiteA.setPlazoEntregaDocumentacionTT(10);
    tipoTramiteA.setNombreTipoTramite("TipoTramite A");
    tipoTramiteA.setCategoriaTipoTramite(categoriaA);
    
     
    tipoTramiteA.addTipoTramiteDocumentacion(ttd1);
    
    FachadaPersistencia.getInstance().guardar(tipoTramiteA);

    
    TipoTramite tipoTramiteB = new TipoTramite();
    tipoTramiteB.setCodTipoTramite(2);
    tipoTramiteB.setDescripcionTipoTramite("TipoTramite de prueba 2");
    tipoTramiteB.setDescripcionWebTipoTramite("TipoTramite de prueba web 2");
    tipoTramiteB.setPlazoEntregaDocumentacionTT(10);
    tipoTramiteB.setNombreTipoTramite("TipoTramite B");
    tipoTramiteB.setCategoriaTipoTramite(categoriaB);
    
    
    tipoTramiteB.addTipoTramiteDocumentacion(ttd2);
    
    FachadaPersistencia.getInstance().guardar(tipoTramiteB);
    
    

    
    
    



    


     
  
    TipoTramiteListaPrecios tipoTramiteListaPrecios1 = new TipoTramiteListaPrecios();
    tipoTramiteListaPrecios1.setPrecioTipoTramite(500); 


    tipoTramiteListaPrecios1.setTipoTramite(tipoTramiteA);


    FachadaPersistencia.getInstance().guardar(tipoTramiteListaPrecios1);



    TipoTramiteListaPrecios tipoTramiteListaPrecios2 = new TipoTramiteListaPrecios();
    tipoTramiteListaPrecios2.setPrecioTipoTramite(700); 


    tipoTramiteListaPrecios2.setTipoTramite(tipoTramiteB);


    FachadaPersistencia.getInstance().guardar(tipoTramiteListaPrecios2);






    ListaPrecios listaPrecios1 = new ListaPrecios();
    listaPrecios1.setCodListaPrecios(1);
    listaPrecios1.setFechaHoraDesdeListaPrecios(new Timestamp(System.currentTimeMillis()));
    listaPrecios1.setFechaHoraHastaListaPrecios(new Timestamp(System.currentTimeMillis()));
    listaPrecios1.setFechaHoraBajaListaPrecios(null);
    
    listaPrecios1.addTipoTramiteListaPrecios(tipoTramiteListaPrecios1);



    FachadaPersistencia.getInstance().guardar(listaPrecios1);



    ListaPrecios listaPrecios2 = new ListaPrecios();
    listaPrecios2.setCodListaPrecios(2);
    listaPrecios2.setFechaHoraDesdeListaPrecios(new Timestamp(System.currentTimeMillis()));
    listaPrecios2.setFechaHoraHastaListaPrecios(new Timestamp(System.currentTimeMillis()));
    listaPrecios2.setFechaHoraBajaListaPrecios(null);
    
    listaPrecios2.addTipoTramiteListaPrecios(tipoTramiteListaPrecios2);
    

    FachadaPersistencia.getInstance().guardar(listaPrecios2);

    
    

    TramiteDocumentacion tramiteDoc1 = new TramiteDocumentacion();
    tramiteDoc1.setCodTD(1);
    tramiteDoc1.setFechaEntregaTD(null);
    tramiteDoc1.setArchivoTD("excel");
    
    tramiteDoc1.setDocumentacion(doc1);



    FachadaPersistencia.getInstance().guardar(tramiteDoc1);



    TramiteDocumentacion tramiteDoc2 = new TramiteDocumentacion();
    tramiteDoc2.setCodTD(2);
    tramiteDoc2.setFechaEntregaTD(null);
    tramiteDoc2.setArchivoTD("Word");
    tramiteDoc2.setDocumentacion(doc2);



    FachadaPersistencia.getInstance().guardar(tramiteDoc2);

    



    

    TramiteEstadoTramite tet1 = new TramiteEstadoTramite();
    tet1.setFechaHoraAltaTET(new Timestamp(System.currentTimeMillis()));
    tet1.setFechaHoraBajaTET(null);  // Que no esten dados de baja
    tet1.setEstadoTramite(etapaUno);


    FachadaPersistencia.getInstance().guardar(tet1);



    TramiteEstadoTramite tet2 = new TramiteEstadoTramite();
    tet2.setFechaHoraAltaTET(new Timestamp(System.currentTimeMillis()));
    tet2.setFechaHoraBajaTET(null);  // Que no esten dados de baja
    tet2.setEstadoTramite(etapaDos);


    FachadaPersistencia.getInstance().guardar(tet2);

    

    ConfTipoTramiteEstadoTramite conf1 = new ConfTipoTramiteEstadoTramite();
    conf1.setContadorConfigTTET(1);
    conf1.setEtapaOrigen(1);  // Etapa 1 como origen
    conf1.setEtapaDestino(2);  // Etapa 2 como destino
    
    conf1.addEstadoTramiteDestino(etapaDos);
    conf1.addEstadoTramiteOrigen(etapaUno);


    FachadaPersistencia.getInstance().guardar(conf1);



    ConfTipoTramiteEstadoTramite conf2 = new ConfTipoTramiteEstadoTramite();
    conf2.setContadorConfigTTET(2);
    conf2.setEtapaOrigen(2);  // Etapa 2 como origen
    conf2.setEtapaDestino(3);  // Etapa 3 como destino
    
    conf2.addEstadoTramiteDestino(etapaDos);
    conf2.addEstadoTramiteOrigen(etapaUno);


    FachadaPersistencia.getInstance().guardar(conf2);

    

    Version version1 = new Version();
    version1.setNroVersion(1);
    version1.setDescripcionVersion("Versión inicial");
    version1.setFechaDesdeVersion(new Timestamp(System.currentTimeMillis()));
    version1.setFechaHastaVersion(new Timestamp(System.currentTimeMillis()));
    version1.setFechaBajaVersion(null);  // Versión activa sin fecha de baja
    
    version1.setTipoTramite(tipoTramiteA);
    version1.addConfTipoTramiteEstadoTramite(conf1);


    FachadaPersistencia.getInstance().guardar(version1);



    Version version2 = new Version();
    version2.setNroVersion(2);
    version2.setDescripcionVersion("Actualización de mitad de año");
    version2.setFechaDesdeVersion(new Timestamp(System.currentTimeMillis()));
    version2.setFechaHastaVersion(new Timestamp(System.currentTimeMillis()));
    version2.setFechaBajaVersion(null);  // Versión activa sin fecha de baja
    
    version2.setTipoTramite(tipoTramiteB);
    version2.addConfTipoTramiteEstadoTramite(conf2);


    FachadaPersistencia.getInstance().guardar(version2);

    
   
    Tramite tramite1 = new Tramite();
    tramite1.setNroTramite(1001);
    tramite1.setFechaInicioTramite(new Timestamp(System.currentTimeMillis()));
    tramite1.setFechaFinTramite(new Timestamp(System.currentTimeMillis()));
    tramite1.setFechaPresentacionTotalDocumentacion(new Timestamp(System.currentTimeMillis()));
    tramite1.setFechaRecepcionTramite(new Timestamp(System.currentTimeMillis()));
    tramite1.setFechaAnulacionTramite(null);  // Trámite no anulado
    tramite1.setPrecioTramite(1500);
    
    tramite1.addTramiteDocumentacion(tramiteDoc1);
    tramite1.addTramiteEstadoTramite(tet1);
    tramite1.setCliente(cliente1);
    tramite1.setConsultor(consultor1);
    tramite1.setTipoTramite(tipoTramiteA);
    tramite1.setVersion(version1);
    tramite1.setEstadoTramite(etapaUno);
    


    FachadaPersistencia.getInstance().guardar(tramite1);



    Tramite tramite2 = new Tramite();
    tramite2.setNroTramite(1002);
    tramite2.setFechaInicioTramite(new Timestamp(System.currentTimeMillis()));
    tramite2.setFechaFinTramite(new Timestamp(System.currentTimeMillis()));
    tramite2.setFechaPresentacionTotalDocumentacion(new Timestamp(System.currentTimeMillis()));
    tramite2.setFechaRecepcionTramite(new Timestamp(System.currentTimeMillis()));
    tramite2.setFechaAnulacionTramite(null);  // Trámite no anulado
    tramite2.setPrecioTramite(2000);
    
    tramite2.addTramiteDocumentacion(tramiteDoc2);
    tramite2.addTramiteEstadoTramite(tet2);
    tramite2.setCliente(cliente2);
    tramite2.setConsultor(consultor2);
    tramite2.setTipoTramite(tipoTramiteB);
    tramite2.setVersion(version2);
    tramite2.setEstadoTramite(etapaDos);


    FachadaPersistencia.getInstance().guardar(tramite2);

    */
    
    FachadaPersistencia.getInstance().finalizarTransaccion();
    
        }
       
        catch (Exception e) {
        Exception nuevaExcepcion = new Exception("Ocurrió un error en el método crearElementos2", e);
        System.out.println(nuevaExcepcion);
             
        
       
    }
       



    }
    
    /**
     * Busca un articulo a traves de su codigo (en este caso deberia ser uno por ser unico).
     */
    public void busquedaDeUnElementoPorAtributo() {
        Articulo articulo;
        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();

        dto.setAtributo("codigo");
        dto.setOperacion("=");
        dto.setValor(1);

        criterioList.add(dto);

        try {

            articulo = (Articulo) FachadaPersistencia.getInstance().buscar("Articulo", criterioList).get(0);

            System.out.println("Mostrando articulo:");
            System.out.println("\tOID: "    + articulo.getOID());
            System.out.println("\tCodigo: " + articulo.getCodigo());
            System.out.println("\tNombre: " + articulo.getNombre());
        } catch (Exception e) {
            System.out.println("No se pudo recuperar el articulo --- " + e.toString());
        }
    }

    /**
     * Busca objetos con un atributo nulo.
     */
    void busquedaDeUnElementoPorAtributoNulo() {
        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();

        dto.setAtributo("fechaBaja");
        dto.setOperacion("=");
        dto.setValor(null);

        criterioList.add(dto);

        List articulos = FachadaPersistencia.getInstance().buscar("Articulo", criterioList);

        for (Object x : articulos) {
            Articulo articulo = (Articulo) x;
            System.out.println("Mostrando reposicion:");
            System.out.println("\tOID: "    + articulo.getOID());
            System.out.println("\tNumero: " + articulo.getCodigo());
            System.out.println("\tEstado: " + articulo.getEstado().getNombre());
            System.out.println("\tFecha: "  + articulo.getNombre());
        }
    }

    /**
     * Busca todos los objetos de una clase.
     */
    void buscarTodosLosObjetosDeUnaClase() {
        List objetoList = FachadaPersistencia.getInstance().buscar("Articulo", null);

        for (Object x : objetoList) {
            Articulo articulo = (Articulo) x;
            System.out.println("Mostrando articulo:");
            System.out.println("\tOID: "    + articulo.getOID());
            System.out.println("\tCodigo: " + articulo.getCodigo());
            System.out.println("\tNombre: " + articulo.getNombre());
            System.out.println("\tEstado: " + articulo.getEstado().getNombre());
        }
    }

    /**
     * Busca una reposicion con estado creada.
     */
    void buscarUnObjetoPorRelacion() {
        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();

        // Primero busco el estado

        dto.setAtributo("nombre");
        dto.setOperacion("=");
        dto.setValor("creada");

        criterioList.add(dto);

        Estado estadoCreada = (Estado) FachadaPersistencia.getInstance().buscar("Estado", criterioList).get(0);

        // Busco la reposicion

        criterioList.clear();

        dto.setAtributo("estado");
        dto.setOperacion("=");
        dto.setValor(estadoCreada);

        criterioList.add(dto);

        List articulos = FachadaPersistencia.getInstance().buscar("Articulo", criterioList);

        for (Object x : articulos) {
            Articulo articulo = (Articulo) x;
            System.out.println("Mostrando reposicion:");
            System.out.println("\tOID: "    + articulo.getOID());
            System.out.println("\tNumero: " + articulo.getCodigo());
            System.out.println("\tEstado: " + articulo.getEstado().getNombre());
            System.out.println("\tFecha: "  + articulo.getNombre());
        }
    }

    /**
     * Busco una reposicion que contiene una reposicion detalle en particular.
     */
    void buscoUnObjetoQueContieneAOtroObjeto() {
        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();

        // Primero busco el articulo relacionado al detalle

        dto.setAtributo("codigo");
        dto.setOperacion("=");
        dto.setValor(1);

        criterioList.add(dto);

        Articulo articulo = (Articulo) FachadaPersistencia.getInstance().buscar("Articulo", criterioList).get(0);

        // Busco la reposicion detalle

        criterioList.clear();

        dto.setAtributo("articulo");
        dto.setOperacion("=");
        dto.setValor(articulo);

        criterioList.add(dto);

        DetalleReposicion detalleReposicion = (DetalleReposicion) FachadaPersistencia.getInstance().buscar("DetalleReposicion", criterioList).get(0);

        // Busco la reposicion que contiene al detalle

        criterioList.clear();

        dto.setAtributo("detalleReposicionList");
        dto.setOperacion("contains");
        dto.setValor(detalleReposicion);

        criterioList.add(dto);

        Reposicion reposicion = (Reposicion) FachadaPersistencia.getInstance().buscar("Reposicion", criterioList).get(0);

        System.out.println("Mostrando reposicion:");
        System.out.println("\tOID: "    + reposicion.getOID());
        System.out.println("\tNumero: " + reposicion.getNumero());
        System.out.println("\tEstado: " + reposicion.getEstado());
        System.out.println("\tFecha: "  + reposicion.getFecha());
    }
    
}
