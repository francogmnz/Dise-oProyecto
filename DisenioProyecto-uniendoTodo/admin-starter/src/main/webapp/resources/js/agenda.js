// Obtener el JSON de consultoresPorSemana desde el campo input oculto y parsearlo
const consultoresPorSemanaInput = document.getElementById("consultoresPorSemanaJson");
const consultoresPorSemana = consultoresPorSemanaInput ? JSON.parse(consultoresPorSemanaInput.value) : {};
const asignaciones = {};

// Inicializar asignaciones usando los datos de consultoresPorSemana desde el backend
Object.keys(consultoresPorSemana).forEach(semanaId => {
    asignaciones[semanaId] = consultoresPorSemana[semanaId].map(consultor => consultor.legajoConsultor.toString());
});
console.log("Asignaciones inicializadas con consultores desde BD:", asignaciones);

// Función dragStart
function dragStart(event) {
    event.dataTransfer.setData('text/plain', event.target.id);
    console.log('Dragging:', event.target.id);
}

// Función allowDrop
function allowDrop(event) {
    event.preventDefault();
    const dropzone = event.target.closest('.dropzone');
    if (dropzone) {
        dropzone.classList.add('over');
        console.log('Allow drop on:', dropzone.id);
    }
}

// Función dragLeave
function dragLeave(event) {
    const dropzone = event.target.closest('.dropzone');
    if (dropzone) {
        dropzone.classList.remove('over');
        console.log('Drag left dropzone:', dropzone.id);
    }
}

// Función prepareAssignmentsData sin conversión a números
function prepareAssignmentsData() {
    const jsonAssignments = JSON.stringify(asignaciones);
    console.log("Assignments JSON:", jsonAssignments);

    let inputHidden = document.getElementById('assignmentsData');
    if (!inputHidden) {
        inputHidden = document.createElement('input');
        inputHidden.type = 'hidden';
        inputHidden.name = 'assignmentsData';
        inputHidden.id = 'assignmentsData';
        document.getElementById('agendaForm').appendChild(inputHidden);
    }

    inputHidden.value = jsonAssignments;
}

// Función sendAssignmentsToServer
function sendAssignmentsToServer(data) {
    if (data.status === 'begin') {
        const jsonAssignments = JSON.stringify(asignaciones);
        const inputHidden = document.createElement('input');
        inputHidden.type = 'hidden';
        inputHidden.name = 'assignmentsData';
        inputHidden.value = jsonAssignments;
        document.getElementById('agendaForm').appendChild(inputHidden);
    }
}

// Función drop para agregar consultores a semanas específicas
function drop(event) {
    event.preventDefault();
    const consultorId = event.dataTransfer.getData('text/plain');
    const consultorElement = document.getElementById(consultorId);
    const dropzone = event.target.closest('.dropzone');

    if (!dropzone) {
        console.log('No dropzone found');
        return;
    }

    dropzone.classList.remove('over');
    console.log('Dropped on:', dropzone.id);

    if (consultorElement) {
        const semanaId = dropzone.getAttribute('data-semana');
        const consultoresEnSemana = dropzone.querySelectorAll('li');
        const consultorYaAgregado = Array.from(consultoresEnSemana).some(consultor =>
            consultor.id === consultorId
        );

        if (consultorYaAgregado) {
            alert('Este consultor ya ha sido agregado a esta semana.');
            console.log('Consultor ya agregado:', consultorId);
            return;
        }

        const consultorStringId = consultorId.replace("consultor-", "");
        const clone = consultorElement.cloneNode(true);
        clone.id = `consultor-${consultorStringId}-asignado`;
        clone.draggable = true;
        clone.setAttribute('data-semana', semanaId);
        clone.ondragstart = dragStart;

        const removeBtn = document.createElement('button');
        removeBtn.innerText = 'x';
        removeBtn.classList.add('remove-btn');
        removeBtn.addEventListener('click', () => {
            removerConsultorDeSemana(semanaId, consultorStringId);
        });

        clone.appendChild(removeBtn);
        dropzone.querySelector('ul').appendChild(clone);

        if (!asignaciones[semanaId]) {
            asignaciones[semanaId] = [];
        }
        asignaciones[semanaId].push(consultorStringId);
        console.log('Consultor added to week:', semanaId);
        console.log('Current assignments:', asignaciones);
        console.log(JSON.stringify(asignaciones, null, 2));
    } else {
        console.log('Consultor element not found:', consultorId);
    }
}

// Función para eliminar consultor de la semana en DOM y en asignaciones
function removerConsultorDeSemana(semanaId, consultorId) {
    console.log('Intentando eliminar consultor:', consultorId, 'de la semana:', semanaId);

    const semanaElement = document.querySelector(`.dropzone[data-semana="${semanaId}"]`);

    if (semanaElement) {
        const consultorElement = semanaElement.querySelector(`#consultor-${consultorId}-asignado`);

        if (consultorElement) {
            consultorElement.remove();
            console.log(`Consultor con ID ${consultorId} eliminado del DOM en la semana ${semanaId}`);
        }

        if (asignaciones[semanaId]) {
            // Filtrar sin convertir a número
            asignaciones[semanaId] = asignaciones[semanaId].filter(id => id !== consultorId);
            console.log('Consultor removido de las asignaciones:', consultorId);

//            // Si no quedan consultores en la semana, elimina la semana de `asignaciones`
//            if (asignaciones[semanaId].length === 0) {
//                delete asignaciones[semanaId];
//                console.log(`Semana ${semanaId} eliminada de asignaciones porque no tiene consultores.`);
//            }
        }
    }

    // Mostrar el estado actual de `asignaciones` después de cada eliminación
    console.log('Estado actual de asignaciones:', JSON.stringify(asignaciones, null, 2));
}

// Exponer funciones al ámbito global
window.prepareAssignmentsData = prepareAssignmentsData;
window.sendAssignmentsToServer = sendAssignmentsToServer;
window.dragStart = dragStart;
window.allowDrop = allowDrop;
window.dragLeave = dragLeave;
window.drop = drop;
window.removerConsultorDeSemana = removerConsultorDeSemana;
