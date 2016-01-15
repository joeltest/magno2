

var validar = validar || {};

validar = {
    requieredCombo: function(form, idCombo) {
        var valorComponente = obtenerValorCombo(form, idCombo);
        //idEstacionSeleccionada = $("#formPrincipal\\:selectEstacion_input option:selected").val();        
        if (valorComponente == -1) {
            alert("Es necesario seleccionar un item de la lista");
            return false;
        }
    },
    requiered: function(thisComponent, btn) {
        var valueComponente = getValor(thisComponent);

        //idEstacionSeleccionada = $("#formPrincipal\\:selectEstacion_input option:selected").val();                
        if (valueComponente == '') {
            //mostrar mensaje                        
            //$("#"+msg).setText("Es necesarion el valor");
            return false;
        }
        return true;
    },
    textoRequerido: function(component) {
        //var valorComponente = obtenerValorText(form,idinput).val();
        var valorComponente = $(component).val();
        alert("valor" + valorComponente);
        if (valorComponente == "") {
            alert("Es necesario escribir un valor");
        }
    }
}

function validarForm(idform) {
    //$("#formPrincipal\\:selectEstacion_input option:selected").val();
    //$("#formPrincipal\\:selectEstacion_input option:selected").val();
    $("#" + idform).find(':input').each(function() {
        var elemento = this;
        if ($(elemento).attr("requiredMessage")) {
            alert("elemento.id=" + elemento.id + ", elemento.value=" + elemento.value);
        }
    });
}


function getValor(component) {
    return component.value;
}

function obtenerValorText(form, idcombo) {
    return $("#" + form + "\\" + idcombo + "_input").val();
}

function obtenerValorCombo(form, idcombo) {
    return $("#" + form + "\\" + idcombo + "_input option:selected").val();
}



/*Forma 1*/
function validarCompania() {
    var retorno = true;
    if (validateTextRequerido(wv_nombreCompania)) {
        retorno = false;
        alert("Por favor escribe el nombre de la compañia");
    }
    if (validateTextRequerido(wv_siglasCompania)) {
        retorno = false;
        alert("Por favor escribe la siglas de la compañia");
    }
    return retorno;
}


function validarAltaPozo() {
    //alert(WNombre.jqId);
    //alert();
    var vc = PrimeFaces.util.ValidationContext;

    if ($(WNombre.jqId).val()) {
        throw vc.getMessage("Requerido", vc.getLabel(WNombre));
    }
}

/*Forma 2*/
function requiered(widgetvars) {
    var re = true;
    $.each(widgetvars, function(ind, elem) {
        if (failTextRequerido(elem)) {
            re = false;
        }
    });
    return re;
}

function failTextRequerido(widgetVar) {
    var value = obtenerValorCombo(widgetVar.jq);
    if (value == '') {
        return true;
    }
}

function obtenerValorComboWidgetVar(widgetvar) {
    return $(widgetvar.jqId + "_input option:selected").val();
}


/***************************** VALIDACIONES PRIMEFACES***********************************************/
PrimeFaces.validator['valor.requerido'] = {
    validate: function(element, value) {
        alert(value);
        if(value == ""){
            alert("");
            throw {
                summary: 'Error de validación¡¡¡',
                detail: value + ' es requerido'
            }
        }
        //use element.data() toX access validation metadata, in this case there is none.
        if (value == null || value == -1) {            
            alert("");
            throw {
                summary: 'Error de validación¡¡',
                detail: value + ' es requerido'
            }
        }
    }
};