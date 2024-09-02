package utils;


import entidades.*;


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
        crearElementos();
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

    /**
     * Busca un articulo a traves de su codigo (en este caso deberia ser uno por ser unico).
     */
    void busquedaDeUnElementoPorAtributo() {
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
