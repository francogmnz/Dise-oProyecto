import entidades.EstadoTramite;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

public class EstadoTramiteService {

    @PersistenceContext
    private EntityManager entityManager;

    // Método para obtener todos los estados de trámite
    public List<EstadoTramite> obtenerTodosEstados() {
        return entityManager.createQuery("SELECT e FROM EstadoTramite e", EstadoTramite.class).getResultList();
    }

    // Método para encontrar un estado de trámite por ID
    public EstadoTramite obtenerEstadoPorId(int id) {
        return entityManager.find(EstadoTramite.class, id);
    }

    // Método para guardar un nuevo estado de trámite
    @Transactional
    public void agregarEstado(EstadoTramite estadoTramite) {
        if (estadoTramite.getCodEstadoTramite()== 0) {
            entityManager.persist(estadoTramite);
        } else {
            entityManager.merge(estadoTramite);
        }
    }

    // Método para actualizar un estado de trámite existente
    @Transactional
    public void actualizarEstado(EstadoTramite estadoTramite) {
        entityManager.merge(estadoTramite);
    }

    // Método para eliminar un estado de trámite por ID
    @Transactional
    public void eliminarEstado(int id) {
        EstadoTramite estadoTramite = obtenerEstadoPorId(id);
        if (estadoTramite != null) {
            entityManager.remove(estadoTramite);
        }
    }
}
