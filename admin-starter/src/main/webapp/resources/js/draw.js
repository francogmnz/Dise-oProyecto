/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

    function addNodo(n)
    {
        nodo="<div class='title-box'><b>"+n.codigo+"-"+n.nombre+"</b></div>";
        data={"codigo":n.codigo,"nombre":n.nombre}
        if(n.codigo==1)
        {
         editor.addNode("e"+n.codigo, 0, 1, n.xpos, n.ypos,"title-box",data, nodo );
        }
        else
        {
            editor.addNode("e"+n.codigo, 1, 1, n.xpos, n.ypos,"title-box",data, nodo );
        }
    }
    function addEnlace(o,d)
    {
        ori=editor.getNodesFromName("e"+o)[0]
        des=editor.getNodesFromName("e"+d)[0]
        editor.addConnection(ori,des,'output_1','input_1')
    }

    var isSelE1=false;
    var nameE1="";
    var eposibles = window.parent.document.getElementById("eposibles");
    j=eposibles.getHTML();
    var titulo = window.parent.document.getElementById("titulo").getHTML();
    document.getElementById('liTitulo').innerText=titulo
    console.log(j);
    const obj = JSON.parse(j);
    var seleccion=document.getElementById("seleccion");
    var editable=window.parent.document.getElementById("editable").getHTML();
    var html="";
    

    
    var id = document.getElementById("drawflow");
    const editor = new Drawflow(id);
    editor.reroute = true;
    //const dataToImport =JSON.parse('{"drawflow":{"Home":{"data":{"1":{"id":1,"name":"e1","data":{},"class":"e1","html":"<div class=\\"title-box\\"><b>1-ESTADO INICIAL</b></div>","typenode":false,"inputs":{},"outputs":{"output_1":{"connections":[]}},"pos_x":87,"pos_y":80}}}}}');

    editor.start();
    editor.addModule("Home")
    editor.changeModule('Home')
    //editor.import(dataToImport);
    nombreE1=""
    nameE1="e1"
    codigoE1=0
    if (editable=="true")
    {
        for(i=0;i<obj.length;i++)
        {

            if("e"+obj[i].codigo==nameE1)
            {
                nombreE1=obj[i].nombre
                codigoE1=obj[i].codigo
            }
            html+="<div class='drag-drawflow' draggable='true' ondragstart='drag(event)' data-node='e"+obj[i].codigo+"'>\n" +
                                    "<span><b>"+obj[i].codigo+"-"+obj[i].nombre+"</b></span> </div> "   ;


        }
        editor.editor_mode='edit';
    }
    else{
        
        document.getElementById("btn-export").remove();
        seleccion.remove()
        //document.getElementById("drawflow").style.width="100vw";
        editor.editor_mode='view'
    }
    seleccion.innerHTML=html;
    var eCargarJson = window.parent.document.getElementById("ecargarJson");
    var eCargar=JSON.parse(eCargarJson.getHTML());
    for(no in eCargar)
    {
        addNodo(eCargar[no]);
    }
    for(no in eCargar)
    {
        for(con in eCargar[no].destinos)
        {
            addEnlace(eCargar[no].codigo,eCargar[no].destinos[con]);
        }
    }
    



    // Events!
    editor.on('nodeCreated', function(id) {
      console.log("Node created " + id);
    })

    editor.on('nodeRemoved', function(id) {
      console.log("Node removed " + id);
      
      if(isSelE1)
      { 
            unodo={}
            unodo.codigo=codigoE1;
            unodo.nombre=nombreE1;
            unodo.xpos=80;
            unodo.ypos=80;
            addNodo(unodo)
      }
    })
    
    editor.on('nodeSelected', function(id) {
      console.log("Node selected " + id);
      isSelE1=false;
      if(editor.getNodeFromId(id).name==nameE1)
      {
          isSelE1=true;
      }
    })

    editor.on('moduleCreated', function(name) {
      console.log("Module Created " + name);

    })

    editor.on('moduleChanged', function(name) {
      console.log("Module Changed " + name);
    })

    editor.on('connectionCreated', function(connection) {
      console.log('Connection created');
      console.log(connection);
    })

    editor.on('connectionRemoved', function(connection) {
      console.log('Connection removed');
      console.log(connection);
    })

    editor.on('mouseMove', function(position) {
      console.log('Position mouse x:' + position.x + ' y:'+ position.y);
    })

    editor.on('nodeMoved', function(id) {
      console.log("Node moved " + id);
    })

    editor.on('zoom', function(zoom) {
      console.log('Zoom level ' + zoom);
    })

    editor.on('translate', function(position) {
      console.log('Translate x:' + position.x + ' y:'+ position.y);
    })

    editor.on('addReroute', function(id) {
      console.log("Reroute added " + id);
    })

    editor.on('removeReroute', function(id) {
      console.log("Reroute removed " + id);
    })

    /* DRAG EVENT */

    /* Mouse and Touch Actions */

    var elements = document.getElementsByClassName('drag-drawflow');
    for (var i = 0; i < elements.length; i++) {
      elements[i].addEventListener('touchend', drop, false);
      elements[i].addEventListener('touchmove', positionMobile, false);
      elements[i].addEventListener('touchstart', drag, false );
    }

    var mobile_item_selec = '';
    var mobile_last_move = null;
   function positionMobile(ev) {
     mobile_last_move = ev;
   }

   function allowDrop(ev) {
      ev.preventDefault();
    }

    function drag(ev) {
      if (ev.type === "touchstart") {
        mobile_item_selec = ev.target.closest(".drag-drawflow").getAttribute('data-node');
      } else {
      ev.dataTransfer.setData("node", ev.target.getAttribute('data-node'));
      }
    }

    function drop(ev) {
      if (ev.type === "touchend") {
        var parentdrawflow = document.elementFromPoint( mobile_last_move.touches[0].clientX, mobile_last_move.touches[0].clientY).closest("#drawflow");
        if(parentdrawflow != null) {
          addNodeToDrawFlow(mobile_item_selec, mobile_last_move.touches[0].clientX, mobile_last_move.touches[0].clientY);
        }
        mobile_item_selec = '';
      } else {
        ev.preventDefault();
        var data = ev.dataTransfer.getData("node");
        addNodeToDrawFlow(data, ev.clientX, ev.clientY);
      }

    }

    function addNodeToDrawFlow(name, pos_x, pos_y) {
      if(editor.editor_mode === 'fixed') {
        return false;
      }
      pos_x = pos_x * ( editor.precanvas.clientWidth / (editor.precanvas.clientWidth * editor.zoom)) - (editor.precanvas.getBoundingClientRect().x * ( editor.precanvas.clientWidth / (editor.precanvas.clientWidth * editor.zoom)));
      pos_y = pos_y * ( editor.precanvas.clientHeight / (editor.precanvas.clientHeight * editor.zoom)) - (editor.precanvas.getBoundingClientRect().y * ( editor.precanvas.clientHeight / (editor.precanvas.clientHeight * editor.zoom)));
      if (editor.getNodesFromName(name).length >0)
      {
          return false;
      }
      for(i=0;i<obj.length;i++)
          if(name=="e"+obj[i].codigo)
            { 
                unodo={}
                unodo.codigo=obj[i].codigo;
                unodo.nombre=obj[i].nombre;
                unodo.xpos=pos_x;
                unodo.ypos=pos_y;
                addNodo(unodo)
            }
      
       
      
    }

  var transform = '';
  function showpopup(e) {
    e.target.closest(".drawflow-node").style.zIndex = "9999";
    e.target.children[0].style.display = "block";
    //document.getElementById("modalfix").style.display = "block";

    //e.target.children[0].style.transform = 'translate('+translate.x+'px, '+translate.y+'px)';
    transform = editor.precanvas.style.transform;
    editor.precanvas.style.transform = '';
    editor.precanvas.style.left = editor.canvas_x +'px';
    editor.precanvas.style.top = editor.canvas_y +'px';
    console.log(transform);

    //e.target.children[0].style.top  =  -editor.canvas_y - editor.container.offsetTop +'px';
    //e.target.children[0].style.left  =  -editor.canvas_x  - editor.container.offsetLeft +'px';
    editor.editor_mode = "fixed";

  }

   function closemodal(e) {
     e.target.closest(".drawflow-node").style.zIndex = "2";
     e.target.parentElement.parentElement.style.display  ="none";
     //document.getElementById("modalfix").style.display = "none";
     editor.precanvas.style.transform = transform;
       editor.precanvas.style.left = '0px';
       editor.precanvas.style.top = '0px';
      editor.editor_mode = "edit";
   }

    function changeModule(event) {
      var all = document.querySelectorAll(".menu ul li");
        for (var i = 0; i < all.length; i++) {
          all[i].classList.remove('selected');
        }
      event.target.classList.add('selected');
    }

    function changeMode(option) {

    //console.log(lock.id);
      if(option == 'lock') {
        lock.style.display = 'none';
        unlock.style.display = 'block';
      } else {
        lock.style.display = 'block';
        unlock.style.display = 'none';
      }

    }
    function buscaNombre(nodo)
    {
        exp=editor.export();
        var l=exp.drawflow.Home.data;
        var un;
        for(un in l){
            if(l[un].id==nodo)
            {
                return l[un].data.codigo;
            }
            
        }

    }
    function guardar() {
    exp = editor.export();
    var lista = exp.drawflow.Home.data;
    var lnodos = [];
    var tieneEstadoFinal = false;
    var todosTienenOrigen = true; // Nuevo: para verificar si todos los nodos tienen un origen

    for (var u in lista) {
        var unodo = {};
        unodo.codigo = lista[u].data.codigo;
        unodo.nombre = lista[u].data.nombre;
        unodo.xpos = lista[u].pos_x;
        unodo.ypos = lista[u].pos_y;

        // Obtener destinos
        unodo.destinos = [];
        for (var i = 0; i < lista[u].outputs.output_1.connections.length; i++) {
            unodo.destinos.push(buscaNombre(lista[u].outputs.output_1.connections[i].node));
        }

        // Validar si tiene entradas (origenes)
        var tieneOrigen = false;
        for (var v in lista) {
            for (var i = 0; i < lista[v].outputs.output_1.connections.length; i++) {
                if (lista[v].outputs.output_1.connections[i].node == u) {
                    tieneOrigen = true;
                    break;
                }
            }
            if (tieneOrigen) break; // Salir del bucle si se encuentra un origen
        }

        // Si no tiene un origen, marcar que no cumple la validación
        if (!tieneOrigen) {
            todosTienenOrigen = false;
        }

        lnodos.push(unodo);

        // Detectar estado final (nodo sin destinos)
        if (unodo.destinos.length === 0) {
            tieneEstadoFinal = true;
        }
    }

    // Validar antes de guardar
    if (tieneEstadoFinal && todosTienenOrigen) {
        console.log(JSON.stringify(lnodos));
        window.parent.document.getElementById("jsonGuardar").value = JSON.stringify(lnodos);
    } else {
        if (!tieneEstadoFinal) {
            alert("Error: Debe haber al menos un estado sin destinos para definir el estado final.");
        }
        if (!todosTienenOrigen) {
            alert("Error: Todos los estados deben tener al menos un origen.");
        }
    }
}
