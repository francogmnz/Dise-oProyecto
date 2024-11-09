// Obtener el JSON de consultoresPorSemana desde el campo input oculto y parsearlo
const consultoresPorSemanaInput = document.getElementById("consultoresPorSemanaJson");
const consultoresPorSemana = consultoresPorSemanaInput ? JSON.parse(consultoresPorSemanaInput.value) : {};
const asignaciones = {};
let semanaActual = 0;
let anioActual = 0;
let anioAgenda = 0;

document.addEventListener("DOMContentLoaded", function () {
    // Seleccionar el input oculto cuyo id termina en 'semanaActual' y obtener su valor
    const semanaActualElement = document.querySelector("input[id$='semanaActual']");
    semanaActual = semanaActualElement ? parseInt(semanaActualElement.value) : 0;
    console.log("Semana actual:", semanaActual);

    // Obtener el año actual desde el campo oculto cuyo id termina en 'anioActual' y su valor
    const anioActualElement = document.querySelector("input[id$='anioActual']");
    anioActual = anioActualElement ? parseInt(anioActualElement.value) : 0;
    console.log("Año actual:", anioActual);

    // Obtener el año de la agenda desde el campo oculto cuyo id termina en 'anioAgenda' y su valor
    const anioAgendaElement = document.querySelector("input[id$='anioAgenda']");
    anioAgenda = anioAgendaElement ? parseInt(anioAgendaElement.value) : 0;
    console.log("Año de la agenda:", anioAgenda);
});




// Inicializar asignaciones usando los datos de consultoresPorSemana desde el backend
Object.keys(consultoresPorSemana).forEach(semanaId => {
    asignaciones[semanaId] = consultoresPorSemana[semanaId].map(consultor => consultor.legajoConsultor.toString());
});
console.log("Asignaciones inicializadas con consultores desde BD:", asignaciones);


function esSemanaFutura(semana) {
    if (anioAgenda < anioActual) {
        return false; // No se puede modificar años anteriores
    }
    if (anioAgenda === anioActual) {
        return semana >= semanaActual; // Permitir semanas actuales o futuras
    }
    return true; // Permitir años futuros
}


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

function prepareAssignmentsData() {
    let preventSave = false;

    // Eliminar semanas pasadas antes de preparar los datos para enviar
    Object.keys(asignaciones).forEach(semanaId => {
        const semanaNumber = parseInt(semanaId, 10);
        if (!esSemanaFutura(semanaNumber)) {
            delete asignaciones[semanaId];
            console.log(`Semana ${semanaId} eliminada de asignaciones porque es pasada.`);
        }
    });

    // Proceder con la preparación de datos si todas las semanas son futuras
    const jsonAssignments = JSON.stringify(asignaciones);
    let inputHidden = document.getElementById('assignmentsData');
    if (!inputHidden) {
        inputHidden = document.createElement('input');
        inputHidden.type = 'hidden';
        inputHidden.name = 'assignmentsData';
        inputHidden.id = 'assignmentsData';
        document.getElementById('agendaForm').appendChild(inputHidden);
    }
    inputHidden.value = jsonAssignments;

    return true; // Permite el envío si no hay restricciones
}




function sendAssignmentsToServer(data) {
    if (data.status === 'begin') {
        // Eliminar semanas pasadas antes de preparar los datos para enviar
        Object.keys(asignaciones).forEach(semanaId => {
            const semanaNumber = parseInt(semanaId, 10);
            if (!esSemanaFutura(semanaNumber)) {
                delete asignaciones[semanaId];
                console.log(`Semana ${semanaId} eliminada de asignaciones porque es pasada.`);
            }
        });

        const jsonAssignments = JSON.stringify(asignaciones);
        const inputHidden = document.createElement('input');
        inputHidden.type = 'hidden';
        inputHidden.name = 'assignmentsData';
        inputHidden.value = jsonAssignments;
        document.getElementById('agendaForm').appendChild(inputHidden);
    }
}

function handleFormSubmission(data) {
    // Verificar el estado del formulario: 'begin', 'complete', o 'success'
    if (data.status === 'success') {
        console.log('Formulario enviado con éxito. Redirigiendo a abmAgendaLista.xhtml...');
        // Redirigir al usuario a abmAgendaLista.xhtml
        window.location.href = 'abmAgendaLista.xhtml';
    }
}

function drop(event) {
    event.preventDefault();
    const consultorId = event.dataTransfer.getData('text/plain');
    const consultorElement = document.getElementById(consultorId);
    const dropzone = event.target.closest('.dropzone');

    if (!dropzone) {
        console.log('No dropzone found');
        return;
    }

    const semanaId = dropzone.getAttribute('data-semana');
    const semanaNumber = parseInt(semanaId);

    // Validar si la semana es futura antes de proceder
    if (!esSemanaFutura(semanaNumber)) {
        alert('No se pueden agregar consultores a semanas pasadas.');
        return;
    }

    // Verificar si el consultor ya está asignado en esta semana en el objeto 'asignaciones'
    const consultorStringId = consultorId.replace("consultor-", "");
    if (asignaciones[semanaId] && asignaciones[semanaId].includes(consultorStringId)) {
        alert('Este consultor ya ha sido agregado a esta semana.');
        console.log('Consultor ya agregado desde BD o previamente asignado:', consultorStringId);
        return;
    }

    // Proceder con el drop si la semana es futura y no hay duplicados
    dropzone.classList.remove('over');
    console.log('Dropped on:', dropzone.id);

    if (consultorElement) {
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

        // Inicializar la semana en asignaciones si aún no existe y agregar el consultor
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

//               // Si no quedan consultores en la semana, elimina la semana de `asignaciones`
//               if (asignaciones[semanaId].length === 0) {
//                   delete asignaciones[semanaId];
//                    console.log(`Semana ${semanaId} eliminada de asignaciones porque no tiene consultores.`);
//                }
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
