/* 
 *Contiene funciones o objetos para realizar operaciones en los mapas
 */

/************************************************CONSTANTES******************************************/
var Constantes = Constantes || {};
Constantes = {
    MOSTRAR_POPUP: function() {
        return true;
    },
    ICON_DEFAULT_MAPA: function() {
        return 'https://cdn4.iconfinder.com/data/icons/small-n-flat/24/map-marker-32.png';
    },
    ICON_SELECCIONADO: function() {
        return 'http://google-maps-icons.googlecode.com/files/oilpumpjack.png';
    }
}
var infoWindows = new google.maps.InfoWindow({maxWidth: 300, content: ''});

/************************************************ VARIABLES ******************************************/
var idCompaniaSeleccionada = 0;
var idBloqueSeleccionado = 0;
var idCampoSeleccionado = 0;
var idEstacionSeleccionada = 0;
var idModuloSatelitalSeleccionado = 0;
var idMacroperaSeleccionada = 0;
var idCml = 0;
var countPozo = 0;
//--Mapa
var map;
var markers_data = [];
var json;
var jsonAutocomplete = [];
;
var mapOptions = {
    zoom: 14,
    maxZoom: 17,
    backgroundColor: 'gray', //<<color de fondo al cargar la pagina
    //center: new google.maps.LatLng(24.8555627, -98.0064476),                
    center: new google.maps.LatLng(24.73843341943274, -98.02840495199455),
    mapTypeId: google.maps.MapTypeId.SATELLITE
            //mapTypeId: google.maps.MapTypeId.ROADMAP  
};
var opcionesPanelPopup = {
    keyboard: true
};


/******************** Objetos prototype **************************/
/*
 function Pozo(latitud, longitud, tituloGlobo, contenidoGlobo, map) {
 this.latitud = latitud;
 this.longitud = longitud;
 this.tituloGlobo = tituloGlobo;
 this.contenidoGlobo = contenidoGlobo;
 this.map = map;
 this.marker = crearMarker(this.latitud, this.longitud, this.contenidoGlobo, this.tituloGlobo, this.map);
 }
 */
var Pozo = Pozo || {};
var Panel = Panel || {};
var RecogerValor = RecogerValor || {};
var Popup = Popup || {};
var Bloquear = Bloquear || {};


/*NOTA IMPORTANTE: No realizar ninguna funcion de carga en el evento ready de jquery, estas operaciones afectan el funcionamiento
 * de componentes de primefaces
 $(document).ready(function() {
 });
 */
/************************************************ MAPA ******************************************/
//-cargar objeto mapa y consultar el servlet
window.onload = function() {
    map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
    obtenerListaPozo();
}

function obtenerListaPozo() {
    //ServiciosGenerales/AbrirArchivo?ZWZ2W=#{cont.id}&amp;ZWZ3W=#{cont.uuid}
    var companiaId = 1;
    var bloqueId = 1;
    var campoId = 1;
//    url: "/Siscop/jsonServlet?" + "COID=" + companiaId + "&amp;BID=" + bloqueId + "&amp;CAID=" + campoId,
    $.ajax({
        type: "GET",
        url: "/Siscop/jsonServlet?" + "COID=" + companiaId + "&amp;BID=" + bloqueId + "&amp;CAID=" + campoId,
        dataType: "json",
        success: function(data, textStatus, jqXHR) {
            serializarJson(data);
            cargarDatosEnCombo(markers_data, "comboPozosAutocomplete", "countPozoAutocomplete");
        },
        error: function(jqXHR, textStatus, errorThrown) {
            //error al cargar el json                        
            Panel.MostrarMensajePopup.error("Error al cargar el mapa", "No se pudo cargar el json...", jqXHR.responseText);
        },
        beforeSend: function(jqXHR, settings) {
            //Panel.MostrarMensajePopup.error("Error al cargar el mapa","No se pudo cargar el json...",jqXHR.responseText);
            //alert(jqXHR.responseText);
        },
        timeout: function() {
            Panel.MostrarMensajePopup.error("Error al cargar el mapa", "Terminó el tiempo de espera del servidor..", "");
        },
        parsererror: function() {
            Panel.MostrarMensajePopup.error("Error al cargar el mapa", "'Existió un error al parsear un dato, el formato del json no es el adecuado..", "");
        },
        complete: function(jqXHR, textStatus) {
            //se ejecuta siempre que se completa            
        }
    });
}
//--Convierte jSON en Objetos
function serializarJson(data) {
    markers_data = [];
    json = data;
    for (var i = 0; i < data.length; i++) {
        var item = data[i];
        if (item.latitudSuperficie != undefined && item.longitudSuperficie != undefined) {
            markers_data.push({
                index: i,
                pozoVo: item,
                marker: crearMarker(item.latitudSuperficie, item.longitudSuperficie, item.nombre, item.plantillaBurbuja, this.map),
                markerLabel: crearMarkerLabel(item.latitudSuperficie, item.longitudSuperficie, item.nombre, item.plantillaBurbuja, this.map),
                html: item.plantillaBurbuja
            });
        }
    }
}

//-Retorna un marker completo con contenidoHTML, Eventos y asigando al mapa
function crearMarker(latitudSup, longitudSup, nombre, contenido, map) {
    //infoWindows = new google.maps.InfoWindow({maxWidth: 300, content: contenido});
    var marker = new google.maps.Marker({
        position: new google.maps.LatLng(latitudSup, longitudSup),
        map: map,
        title: 'Click aqui para ver el detalle del pozo ' + nombre
    });
    //Agregar el click para abrir el infowindows    
    google.maps.event.addListener(marker, 'mouseover', function() {
        infoWindows.setContent(contenido);
        //marker.setIcon(iconSeleccionado);
        infoWindows.open(map, marker);
    });

    google.maps.event.addListener(marker, 'click', function() {
        infoWindows.setContent(contenido);
        //marker.setIcon(iconSeleccionado);
        infoWindows.open(map, marker);
    });
    return marker;
}

function crearMarkerLabel(latitudSup, longitudSup, nombre, contenido, map) {
    var myOptions = {
        content: nombre
        , boxClass: "badge"  //--Estilo bootstrap btn-danger
        , disableAutoPan: true
        , pixelOffset: new google.maps.Size(-25, 0)
        , position: new google.maps.LatLng(latitudSup, longitudSup)
        , closeBoxURL: ""
        , isHidden: false
        , pane: "overlayImage"
        , enableEventPropagation: true
    };
    //, pane: "floatPane"
    var ibLabel = new InfoBox(myOptions);
    ibLabel.open(map);
    return ibLabel;
}

/**** PRUEBA crear areas de macroperas**/
function crearMacroperas() {
    var circluloMacropera = {
        strokeColor: "#00ff00",
        strokeOpacity: 0.8,
        strokeWeight: 2,
        fillColor: "#00ff00",
        fillOpacity: 0.45,
        map: map,
        center: new google.maps.LatLng(40.7457439, -3.5953355),
        radius: 90000
    };
    var circulo = new google.maps.Circle(circluloMacropera);
}

function cargarDatosEnCombo(data, idComponente, idCount) {
    var count = 0;
    $("#" + idComponente).empty();
    $(data).each(function(index, value) {
        $("<li><a href='javascript:mostrarInformacion(" + value.index + ")'>" + value.pozoVo.nombre + "</a></li>").appendTo("#" + idComponente);
//        $("<option value='" + (ind++) + "'>" + this.nombre + "</option>").appendTo("#comboPozos");
        count++;
    });
    $("#" + idCount).text(count);
}

//--Al seleccionar un valor del combo se ejecuta esta funcion y muestra la burbuja de informacion
function mostrarInformacion(index) {
    var markerSeleccionado = markers_data[index];
    infoWindows.setContent(markerSeleccionado.html);
    infoWindows.open(map, markerSeleccionado.marker);
    map.panTo(markerSeleccionado.marker.getPosition());
    map.setZoom(14);
}

/************************* EVENTO DE FILTRO EN PANEL DE MAPA ******************************/

Pozo.filtrar = {
    porCompania: function() {
        //idCompaniaSeleccionada = $("#formPrincipal\\:selectCompania_input option:selected").val();
        idCompaniaSeleccionada = $("#formPrincipal\\:radioCompania_input option:selected").val();
        noVisibleMarkers();
        if (idCompania > -1) {
            countPozo = 0;
            $.each(markers_data, function(index, value) {
                if ((value.pozoVo.idEstacionRec == idCompania)) {
                    value.marker.setVisible(true);
                    value.markerLabel.setVisible(true);
                    countPozo++;
                }
            });
        }
        informar();
    },
    porEstacion: function() {
        idEstacionSeleccionada = $("#formPrincipal\\:selectEstacion_input option:selected").val();
        noVisibleMarkers();
        if (idEstacionSeleccionada > -1) {
            countPozo = 0;
            $.each(markers_data, function(index, value) {
                if ((value.pozoVo.idEstacionRec == idEstacionSeleccionada)) {
                    value.marker.setVisible(true);
                    value.markerLabel.setVisible(true);
                    countPozo++;
                }
            });
        }
        informar();
    },
    porModuloSatelitalYEstacion: function() {
        idModuloSatelitalSeleccionado = $("#formPrincipal\\:selectModulo_input option:selected").val();
        noVisibleMarkers();
        if (idModuloSatelitalSeleccionado > -1 && idEstacionSeleccionada > -1) {
            $.each(markers_data, function(index, value) {
                //if ((value.pozoVo.idEstacionRec == idEstacionSeleccionada) && (value.pozoVo.idModuloSat == idModuloSatelitalSeleccionado)) {
                if (value.pozoVo.idModuloSat == idModuloSatelitalSeleccionado && value.pozoVo.idEstacionRec == idEstacionSeleccionada) {
                    value.marker.setVisible(true);
                    value.markerLabel.setVisible(true);
                }
            });
        }
        informar();
    },
    porMacropera: function() {
        idMacroperaSeleccionada = $("#formPrincipal\\:selectMacropera_input option:selected").val();
        noVisibleMarkers();
        if (idMacroperaSeleccionada > -1 && idModuloSatelitalSeleccionado > -1 && idEstacionSeleccionada > -1) {
            $.each(markers_data, function(index, value) {
                if ((value.pozoVo.idMacropera == idMacroperaSeleccionada)) {
                    value.marker.setVisible(true);
                    value.markerLabel.setVisible(true);
                }
            });
        }
        informar();
    }
}

function noVisibleMarkers() {
    $.each(markers_data, function(index, value) {
        value.marker.setVisible(false);
        value.markerLabel.setVisible(false);
    });
}

function informar() {
    countPozo = 0;
    var dataall = [];
    $.each(markers_data, function(index, value) {
        if (value.marker.getVisible()) {
            //$("<li><a href='javascript:mostrarInformacion("+index+")'>"+ value.pozoVo.nombre + "</a></li>").appendTo("#comboPozosVisibles");
            countPozo++;
            dataall.push(value);
        }
    });

    cargarDatosEnCombo(dataall, "comboPozosVisibles", "countPozosVisibles");
    //llenar combo visibles      
}


/******************************** PANEL POPUP******************************************/
//Descripcion :  Lanza un panelpopup de boootstrap con la informacion requerida
//Ejemplo : Panel.MostrarMensajePopup.error("Error al cargar el mapa","No se pudo cargar el json...",jqXHR.responseText);
Panel.MostrarMensajePopup = {
    informacion: function(title, bodyHTML, footer) {
//        setMensajesEnPanel("","","",botonCerrar);        
        setMensajesEnPanel(title, bodyHTML, footer);
        $('#miPanelPopup').modal(opcionesPanelPopup);
    },
    mensaje: function(title, bodyHTML, footer) {
        setMensajesEnPanel(title, bodyHTML, footer);
        $('#miPanelPopup').modal(opcionesPanelPopup);
    },
    error: function(title, bodyHTML, footer) {
        setMensajesEnPanel(title, bodyHTML, footer);
        $('#miPanelPopup').modal(opcionesPanelPopup);
    }
}

Popup = {
    show: function(title, bodyId) {
        var bodyHTML = $("#" + bodyId).html();
        setMensajesEnPanel(title, bodyHTML, 'default');
        $('#miPanelPopup').modal(opcionesPanelPopup);
    },
    hide: function() {
        $('#miPanelPopup').modal('hide');
        cleanHTMLPopup();
    }
}

function setMensajesEnPanel(title, bodyHTML, footer) {
    cleanHTMLPopup();
    $("#miPanelPopupTitulo").text(title);
    $("#miPanelPopupCuerpo").append("<div>" + bodyHTML + "</div>");
    if (footer == "default") {
        //$("#idPopupContent").append('<div id="idFooter" class="modal-footer"><button id="botonCerrarPopup" type="button" class="btn btn - success" data-dismiss="modal">Cerrar</button></div>');
    }
}

function cleanHTMLPopup() {
    //clean title,bodyHtml and footer
    $("#miPanelPopupTitulo").empty();
    $("#miPanelPopupCuerpo").empty();
    $("#idPopupContent").removeClass("modal-footer");

}

//---------------------PANEL

function prepararAutocomplete(componente) {
    //alert(json[0].nombre);    
    $.each(json, function(index, value) {
        jsonAutocomplete[index] = {label: value.nombre, value: value.id};
    });
    //alert(componente.id);
    $(componente).autocomplete({
        source: jsonAutocomplete
    });
}

function cambiarbloque(title, formid) {
//    var html = $("#formCambioBloque").html();
    Popup.show(title, formid);
//    Panel.MostrarMensajePopup.informacion("Cambio de bloque", html, "");
}

function showPopup(idComponent) {
    $("#" + idComponent).modal(opcionesPanelPopup);
    //$("#"+idComponent).data('bs.modal').$backdrop.css('background-color','green')
}
function hidePopup(idComponent) {
    $("#" + idComponent).modal('hide');
}


// Hacer una funcion que reciba un parametro del nombre de la forma para enviar al objeto del panel popup y poder usarlo generico

/******************************BLOCK UI*****************************************/

function redirectPrincipal() {
    //$("#menuform:idBtnSiscopPrincipal").click();
    $("#menuform:idBtnSiscopPrincipal").click();
    ajaxErrorHandler.hide();
}

function updateJsonMarkers(xhr, status, args) {
    if (status == 'success') {
        /***Saber si se ha expirado la sesion **/
        if (xhr.responseText == 'class javax.faces.application.ViewExpiredException') {
            Panel.MostrarMensajePopup.error("Terminó su sesión", "Mensaje : <br/>" + xhr.responseText + "<br/>Por favor comuniquese con el departamento de Siscop para atender este conflicto..", "");
        }
        /***************/
        
        serializarJson($.parseJSON(args.jsonMarkers));
        $("#combo").empty();
        $($.parseJSON(args.jsonMarkers)).each(function(index, value) {
            $("<option value='" + this.id + "'>" + this.nombre + "</option>").appendTo("#combo");
        });

    } else {
        Panel.MostrarMensajePopup.error("Error al cargar el JSON", status + "<br/>" + xhr.responseText + "<br/>Por favor comuniquese con el departamento de Siscop para atender este conflicto..", "");
    }
    //
}

//-- Recoger el valor de un SelectOneRadio con jQuery
//-- Se especifica el id de la form y el id del componente 
//-- Por ejemplo : recogerValorRadio("idFormulario","idRadio");
RecogerValor = {
    selectOneMenu: function(idForm, idComponent) {
        return jQuery("select[name='" + idForm + "\\:" + idComponent + "_input'] option:selected").attr("value");
    },
    selectOneRadio: function(idForm, idComponent) {
        return jQuery("input[name='" + idForm + "\\:" + idComponent + "']:checked").attr("value");
    },
    inputText: function(idForm, idComponent) {
        return jQuery(PrimeFaces.escapeClientId(idForm + ':' + idComponent)).attr("value");
    }

}


function filtrarPozos() {
    //idCompaniaSeleccionada = $("#formPrincipal\\:radioCompania_input option:selected").val();
    //idCompaniaSeleccionada = recogerValorRadio("formPrincipal", "radioCompania");
    idCompaniaSeleccionada = RecogerValor.selectOneRadio("formPrincipal", "radioCompania");
    idBloqueSeleccionado = RecogerValor.selectOneRadio("formPrincipal", "radioBloque");
    idCampoSeleccionado = RecogerValor.selectOneRadio("formPrincipal", "radioCampo");
    idCmlSeleccionado = RecogerValor.selectOneRadio("formPrincipal", "radioCml");
    idEstacionSeleccionada = RecogerValor.selectOneMenu("formPrincipal", "selectEstacion");
    idModuloSatelitalSeleccionado = RecogerValor.selectOneMenu("formPrincipal", "selectModulo");
    idMacroperaSeleccionada = RecogerValor.selectOneMenu("formPrincipal", "selectMacropera");

    /*alert(" compania = " + (idCompaniaSeleccionada == undefined ? 0 : idCompaniaSeleccionada) + " bloque= " +
     (idBloqueSeleccionado == undefined ? 0 : idBloqueSeleccionado) + " campo= " +
     (idCampoSeleccionado == undefined ? 0 : idCampoSeleccionado) + " estacion= " +
     (idEstacionSeleccionada == undefined ? 0 : idEstacionSeleccionada)+ " modulo= " +
     (idModuloSatelitalSeleccionado == undefined ? 0 : idModuloSatelitalSeleccionado) + " macropera= " +
     (idMacroperaSeleccionada == undefined ? 0 : idMacroperaSeleccionada));
     */
    noVisibleMarkers();
    getJson([{name: 'idCompaniaParam', value: idCompaniaSeleccionada == undefined ? 0 : idCompaniaSeleccionada},
        {name: 'idBloqueParam', value: idBloqueSeleccionado == undefined ? 0 : idBloqueSeleccionado},
        {name: 'idCmlParam', value: idCmlSeleccionado == undefined ? 0 : idCmlSeleccionado},
        {name: 'idEstacionParam', value: idEstacionSeleccionada == undefined ? 0 : idEstacionSeleccionada},
        {name: 'idModuloParam', value: idModuloSatelitalSeleccionado == undefined ? 0 : idModuloSatelitalSeleccionado},
        {name: 'idCampoParam', value: idCampoSeleccionado == undefined ? 0 : idCampoSeleccionado},
        {name: 'idMacroperaParam', value: idMacroperaSeleccionada == undefined ? 0 : idMacroperaSeleccionada}
    ]);

}
