$(document).ready(function() {
    // Configuración regional datepicker
    $.datepicker.regional['ca'] = {
            prevText: 'Anterior',
            nextText: 'Següent',
            currentText: 'Avui',
            monthNames: ['Gener','Febrer','Març','Abril','Maig','Juny',
            'Juliol','Agost','Setembre','Octubre','Novembre','Desembre'],
            monthNamesShort: ['gen','feb','març','abr','maig','juny',
            'jul','ag','set','oct','nov','des'],
            dayNames: ['Diumenge','Dilluns','Dimarts','Dimecres','Dijous','Divendres','Dissabte'],
            dayNamesShort: ['dg','dl','dt','dc','dj','dv','ds'],
            dayNamesMin: ['dg','dl','dt','dc','dj','dv','ds'],
            weekHeader: 'Set',
            dateFormat: 'dd/mm/yy',
            firstDay: 1,
            isRTL: false,
            showMonthAfterYear: false,
            yearSuffix: ''};
    $.datepicker.setDefaults($.datepicker.regional['ca']);
    $('.datepicker').datepicker();

    $.timepicker.regional['ca'] = {
        timeOnlyTitle: 'Escollir una hora',
        timeText: 'Hora',
        hourText: 'Hores',
        minuteText: 'Minuts',
        secondText: 'Segons',
        millisecText: 'Milisegons',
        microsecText: 'Microsegons',
        timezoneText: 'Fus horari',
        currentText: 'Ara',
        closeText: 'Tancar',
        timeFormat: 'HH:mm',
        amNames: ['AM', 'A'],
        pmNames: ['PM', 'P'],
        isRTL: false
    };
    $.timepicker.setDefaults($.timepicker.regional['ca']);

    $('.timepicker').datetimepicker({
        stepHour: 1,
        stepMinute: 5,
        stepSecond: 10
    });

    // Muestra los errores en los formularios
    $(".help-inline").parents(".control-group").each(function(){
        $(this).addClass("error");
    });

    // Activa las Pestañas

    $('#myTab a').click(function (e) {
        e.preventDefault();
        $(this).tab('show');
        });
    $('#myTab a:first').tab('show');



});

/**
 * Muestra el cuadro de confirmación para realizar una acción
 * @param url  url a la que se dirigirá
 * @param texto texto de confirmación a mostrar
 */
function confirm(url,texto) {
    var confirmModal = 
      $('<div class="modal hide fade">' +    
          '<div class="modal-header">' +
            '<a class="close" data-dismiss="modal" >&times;</a>' +
            '<h3>Confirmar acción</h3>' +
          '</div>' +

          '<div class="modal-body">' +
            '<p>'+texto+'</p>' +
          '</div>' +

          '<div class="modal-footer">' +
            '<a href="#" class="btn" data-dismiss="modal">No</a>' +
            '<a href="#" id="okButton" class="btn btn-primary">Si</a>' +
          '</div>' +
        '</div>');

    confirmModal.find('#okButton').click(function(event) {
        document.location.href = url;
        confirmModal.modal('hide');
    });

    confirmModal.modal('show');     
};

function confirmDescarga(url,texto) {

    var confirmModal =
        $('<div class="modal hide fade">' +
            '<div class="modal-header">' +
            '<a class="close" data-dismiss="modal" >&times;</a>' +
            '<h3>Confirmar acción</h3>' +
            '</div>' +

            '<div class="modal-body">' +
            '<p>'+texto+'</p>' +
            '</div>' +

            '<div class="modal-footer">' +
            '<a href="#" class="btn" data-dismiss="modal">No</a>' +
            '<a href="#" id="okButton" class="btn btn-primary">Si</a>' +
            '</div>' +
            '</div>');

    confirmModal.find('#okButton').click(function(event) {
        document.location.href = url;
        confirmModal.modal('hide');
        $('#modalSincro').modal('show');
    });

    confirmModal.modal('show');
};

function goTo(url) {
    document.location.href=url;
}


function editarSeccion(action, nombre, descripcion, notaMaxima) {
    $("#seccionForm input#nombre").val(nombre);
    $("#seccionForm input#descripcion").val(descripcion);
    $("#seccionForm input#notaMaxima").val(notaMaxima);

    $('#seccionForm').attr("action",action);
    $('#modal-nueva-seccion').modal('toggle');
}

function nuevaSeccion(action) {
    $("#seccionForm input#nombre").val("");
    $("#seccionForm input#descripcion").val("");
    $("#seccionForm input#notaMaxima").val("");

    $('#seccionForm').attr("action",action);
}

/**
 * Carga de valores un Select dependiente de otro
 * @param url donde hacer la petición ajax
 * @param idSelect id del campo select donde cargar los datos obtenidos
 * @param seleccion valor seleccionado en el Select principal
 * @param valorSelected Valor seleccionado en el select dependiente, si es que lo hay. Sirve solo para las modificaciones.
 * @param todos Booleano para definir si incluir la opción de todos (...) en el Select
 */
function actualizarSelect(url, idSelect, seleccion, valorSelected, todos){

    var html = '';
    if(seleccion != '-1'){
        jQuery.ajax({
            async: false,
            url: url,
            type: 'GET',
            dataType: 'json',
            data: { id: seleccion },
            contentType: 'application/json',
            success: function(result) {
                if(todos){html = '<option value="-1">...</option>';}
                var len = result.length;
                var selected='';
                for ( var i = 0; i < len; i++) {
                    selected='';

                    if(valorSelected != null && result[i].id == valorSelected){
                        selected = 'selected="selected"';
                        }
                    html += '<option '+selected+' value="' + result[i].id + '">'
                        + result[i].descripcion + '</option>';
                }
                html += '</option>';

                if(len != 0){
                    $(idSelect).removeAttr("disabled","disabled");
                    $(idSelect).html(html);
                }else if(len==0){
                    $(idSelect).attr("disabled","disabled");
                    var html='';
                    $(idSelect).html(html);
                }


            }
        });

    }else{
        $(idSelect).attr("disabled","disabled");
        var html='';
        $(idSelect).html(html);
    }

}

/**
 * Carga de valores un Select dependiente de otro
 * @param url donde hacer la petición ajax
 * @param idSelect id del campo select donde cargar los datos obtenidos
 * @param seleccion valor seleccionado en el Select principal
 * @param valorSelected Valor seleccionado en el select dependiente, si es que lo hay. Sirve solo para las modificaciones.
 * @param todos Booleano para definir si incluir la opción de todos (...) en el Select
 */
function actualizarSelectTraduccion(url, idSelect, seleccion, valorSelected, todos){
    var html = '';
    if(seleccion != '-1'){
        jQuery.ajax({
            url: url,
            type: 'GET',
            dataType: 'json',
            data: { id: seleccion },
            contentType: 'application/json',
            success: function(result) {
                if(todos){html = '<option value="-1">...</option>';}
                var len = result.length;
                var selected='';
                for ( var i = 0; i < len; i++) {
                    selected='';
                    if(valorSelected != null && result[i].id == valorSelected){
                        selected = 'selected="selected"';
                    }
                    html += '<option '+selected+' value="' + result[i].id + '">'
                        + result[i].traduccion.nombre + '</option>';
                }
                html += '</option>';

                if(len != 0){
                    $(idSelect).removeAttr("disabled","disabled");
                    $(idSelect).html(html);
                }else if(len==0){
                    $(idSelect).attr("disabled","disabled");
                    var html='';
                    $(idSelect).html(html);
                }


            }
        });

    }else{
        $(idSelect).attr("disabled","disabled");
        var html='';
        $(idSelect).html(html);
    }

}

/* Funcion para validar que la una fecha sea inferior a otra
 * @param startDate  fecha de inicio
 * @param endDate fecha de fin
 */
function validateDates(startDate, endDate) {
  /* if($.datepicker.parseDate('dd/mm/yy', endDate.val()) != null && $.datepicker.parseDate('dd/mm/yy', startDate.val())==null){
        alert("Debe indicar también la fecha de inicio");
        return false;
   }*/
    if ($.datepicker.parseDate('dd/mm/yy', endDate.val()) != null) {
        if ($.datepicker.parseDate('dd/mm/yy', startDate.val()) > $.datepicker.parseDate('dd/mm/yy', endDate.val())) {
            alert("La Fecha de Inicio debe ser inferior a la Fecha Fin");
            return false;
    }
    }
}

/**
 * Funció per mostrar missatge i barra de "Processar" amb missatge i color propi
 */
var waitingDialog = waitingDialog || (function ($) {
    'use strict';

    // Creating modal dialog's DOM
    var $dialog = $(
        '<div class="modal fade" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true" style="overflow-y:visible;">' +
        '<div class="modal-dialog modal-m">' +
        '<div class="modal-content">' +
        '<div class="modal-header"><h3 style="margin:0;text-align:center;"></h3></div>' +
        '<div class="modal-body">' +
        '<div class="progress progress-striped active" style="margin-bottom:0;"><div class="progress-bar" style="width: 100%"></div></div>' +
        '</div>' +
        '</div></div></div>');

    return {
        /**
         * Opens our dialog
         * @param message Custom message
         * @param options Custom options:
         * 				  options.dialogSize - bootstrap postfix for dialog size, e.g. "sm", "m";
         * 				  options.progressType - bootstrap postfix for progress bar type, e.g. "success", "warning".
         */
        show: function (message, options) {
            // Assigning defaults
            if (typeof options === 'undefined') {
                options = {};
            }
            if (typeof message === 'undefined') {
                message = 'Loading';
            }
            var settings = $.extend({
                dialogSize: 'm',
                progressType: '',
                onHide: null // This callback runs after the dialog was hidden
            }, options);

            // Configuring dialog
            $dialog.find('.modal-dialog').attr('class', 'modal-dialog').addClass('modal-' + settings.dialogSize);
            $dialog.find('.progress-bar').attr('class', 'progress-bar');
            if (settings.progressType) {
                $dialog.find('.progress-bar').addClass('progress-bar-' + settings.progressType);
            }
            $dialog.find('h3').text(message);
            // Adding callbacks
            if (typeof settings.onHide === 'function') {
                $dialog.off('hidden.bs.modal').on('hidden.bs.modal', function (e) {
                    settings.onHide.call($dialog);
                });
            }
            // Opening dialog
            $dialog.modal();
        },
        /**
         * Closes dialog
         */
        hide: function () {
            $dialog.modal('hide');
        }
    };

})(jQuery);


function amaga(numeroPrimer, numeroSegon, numeroTercer) {

    $('.tree li:has(ul > li)').addClass('parent_li').find(' > span > i').removeClass('fa fa-minus');

    for(var i=0;i<numeroTercer;i++){
        var variable = $("#tercerNivell" + i).parent('li.parent_li').find(' > ul > li');
        variable.hide('fast');
        if($("#tercerNivell" + i).parent('li.parent_li').find(' > ul > li').val() != undefined){
            $("#tercerNivell" + i).find(' > i').removeClass('fa fa-minus');
            $("#tercerNivell" + i).attr('title', 'Mostra la branca').find(' > i').addClass('fa fa-plus');
        }
    }

    for(var i=0;i<numeroSegon;i++){
        var variable = $("#segonNivell" + i).parent('li.parent_li').find(' > ul > li');
        variable.hide('fast');
        if($("#segonNivell" + i).parent('li.parent_li').find(' > ul > li').val() != undefined){
            $("#segonNivell" + i).find(' > i').removeClass('fa fa-minus');
            $("#segonNivell" + i).attr('title', 'Mostra la branca').find(' > i').addClass('fa fa-plus');
        }
    }

    for(var i=0;i<numeroPrimer;i++){
        var variable = $("#primerNivell" + i).parent('li.parent_li').find(' > ul > li');
        variable.hide('fast');
        if($("#primerNivell" + i).parent('li.parent_li').find(' > ul > li').val() != undefined){
            $("#primerNivell" + i).find(' > i').removeClass('fa fa-minus');
            $("#primerNivell" + i).attr('title', 'Mostra la branca').find(' > i').addClass('fa fa-plus');
        }
    }

    $("#zeroNivell").find(' > i').addClass('fa fa-minus');
    $("#zeroNivell").attr('title', 'Amaga la branca');
    $("#entidad").find(' > i').addClass('fa fa-minus');
    $("#entidad").attr('title', 'Amaga la branca');

}
