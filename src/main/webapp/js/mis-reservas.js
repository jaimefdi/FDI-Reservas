$(document).ready(function(){

var reserva = {};
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
var reqHeaders = [];
reqHeaders[header] = token;


$("#modalEditarReserva #datetimepicker1").change(function(){
	var comienzo = es.ucm.fdi.dateUtils.toIso8601($('#modalEditarReserva #datetimepicker1').val());
	var m = new moment(comienzo);
	var fin = m.add(1,'hour');
	$('#modalEditarReserva #datetimepicker2').val(fin.format("DD/MM/YYYY HH:mm"));	
});

$("#modalEditarReserva #datetimepicker2").change(function(){
	var comienzo = es.ucm.fdi.dateUtils.toIso8601($('#modalEditarReserva #datetimepicker1').val());
	var fin = es.ucm.fdi.dateUtils.toIso8601($('#modalEditarReserva #datetimepicker2').val());
	var start = new moment(comienzo)
	var end = new moment(fin);
	if(end.isBefore(start)){
		$('#modalEditarReserva #datetimepicker2').val(start.add(1,'hour').format("DD/MM/YYYY HH:mm"));	
	}
	else{
		$('#modalEditarReserva #datetimepicker2').val(end.format("DD/MM/YYYY HH:mm"));	
	}
	
	
});

/// Ver como Lista y Calendario ///
$("#iconoLista").click(function(){
	$(this).addClass("red");
	$("#iconoCalendario").removeClass("red");
	$("#vistaLista").show();
	$("#calendar").hide();
});

$("#iconoCalendario").click(function(){
	$(this).addClass("red");
	$("#iconoLista").removeClass("red");
	$("#vistaLista").hide();
	loadCalendar();
	$("#calendar").show();
});


$('td a').click(function(){
	
	reserva.id =  $(this).attr("data-id");
	reserva.recurrenteId = $(this).attr("data-recurrenteId");
	var usuario= $(this).attr("data-user");
	var asunto =  $(this).attr("data-asunto");
	var start = $(this).attr("data-start");
	var end = $(this).attr("data-end");
	var nombreEspacio = $(this).attr("data-espacio");
	var nombreGrupo = $(this).attr("data-grupo");
	var color = $(this).attr("data-reservaColor");
	var accion = $(this).attr("data-accion");
	
	console.log(reserva.recurrenteId);
	
	$('#modalEditarReserva #asunto').text(asunto);
	$('#modalEditarReserva #comienzo').text(es.ucm.fdi.dateUtils.fromIso8601(start));
	$('#modalEditarReserva #fin').text(es.ucm.fdi.dateUtils.fromIso8601(end));
	$('#modalEditarReserva #nombreEspacio').text(nombreEspacio);
	$('#modalEditarReserva #nombreGrupo').text(nombreGrupo);
	$('#modalEditarReserva #reservaColor').css("background",color);
	$('#modalEditarReserva #enlaceEditar').prop("href", baseURL + "editar/" + reserva.id)
	
	if(accion == 'Ver'){
		$('#modalEditarReserva').modal('show');
	}
	else if(accion == 'Eliminar'){
		modalEliminarReservaSimple();	
	}
	
	
});


$("#enlaceEliminar").click(function(){
	modalEliminarReservaSimple();	
});

$("#aceptarEliminar").click(function(){
	eliminarReserva(reqHeaders, reserva.id);
});


$('#solo_esta').click(function(){
	var w = reserva.recurrenteId.split("_");
	var reservaPadre = w[0];
	var exDate = "EXDATE:VALUE=" + w[1];
	reserva.id = reservaPadre;
	var recurrencia = [];
	recurrencia.push(exDate);
	reserva.reglasRecurrencia = recurrencia;
	SoloEditarReservaRecurrente(reserva, reqHeaders);	
});
	
$('#toda_la_serie').click(function(){
	var w = reserva.recurrenteId.split("_");
	var idReserva = w[0];
	eliminarReserva(reqHeaders, idReserva);
});
	

/// FullCalendar ///
function loadCalendar(){
 	$('#calendar').fullCalendar({
		lang: 'es',
		timezone: 'local',
	    header: {
	        left: 'prev,next today',
	        center: 'title',
	        right: 'month,agendaWeek,agendaDay'
	    },	    
	    defaultDate: '2016-03-12',
	    editable: true,
	    eventLimit: true,
	    eventClick: function(event, jsEvent, view){
	    	$('[role="tooltip"]').popover('hide');
	    	reserva.id = event.id;
	    	reserva.recurrenteId = event.recurrenteId;
	    	console.log(event);
	    	
	    	var linkEliminar = "<div class='col-xs-6 text-left'><a role='button' onclick='modalEliminarReservaSimple()'>" + 'Eliminar' + "</a></div>";
	    	var linkEditar = "<div class='col-xs-6 text-right'><a href='/reservas/editar/" + event.id + "'>" + 'Editar' + "</a></div>";
	    	
	    	if(esRecurrente(reserva)){
	    		var w = reserva.recurrenteId.split("_");
	    		event.id = w[0];
	    		console.log(w[1]);
	    		var nr = w[1].replace("/","-");
	    		nr = nr.replace("/","-");
	    		nr = w[0] + "_" + nr;
	    		linkEliminar = "<div class='col-xs-6 text-left'><a role='button' onclick='modalEliminarReservaRecurrente()'>" + 'Eliminar' + "</a></div>";
	    		linkEditar = "<div class='col-xs-6 text-right'><a href='/reservas/editar/" + event.id + "/" + nr + "'>" + 'Editar' + "</a></div>";
	    	}
	    	
	    	var closePopover = "<div class='col-xs-1 text-right' style='padding:0px;' onclick='closePopover();'><i class='zmdi zmdi-close' role='button'> </i></div>";
			
	    	var divDonde = "<div class='col-xs-11' style='padding:0px;'>Donde: <b>" + event.nombreEspacio + "</b> </div>";
	    	var divCuando = "<div class='col-xs-12' style='padding:0px;'>De " + event.start.format("HH:mm") + " a " + event.end.format("HH:mm") + "</div>";
    		var divAsunto = "<div class='col-xs-12' style='padding:0px;padding-bottom:10px;'>Asunto: " + event.title + "</div>";
	    	
	    	var cuerpo = divDonde + closePopover + divCuando + divAsunto +
 			 "<div class='row'>" + linkEliminar + linkEditar + "</div>";   		


	    	$(this).popover({						
				placement : 'auto',
				html : true,
				animation : 'true',
				container:'#calendar',
				content : cuerpo
						  
			}).popover('show');
	    },
	    eventDragStart: function(event, jsEvent, ui, view){
	    	if(esRecurrente(event)){		    
		    	var w = event.recurrenteId.split("_");
		    	var reservaPadre = w[0];
		    	var exDate = "EXDATE:VALUE=" + w[1];
		    	reserva.id = reservaPadre;
		    	var recurrencia = [];
		    	recurrencia.push(exDate);
		    	reserva.reglasRecurrencia = recurrencia;
	    	}
	    },
		eventResize: function(event, delta, revertFunc, jsEvent) {
			
			if(esRecurrente(event)){
				var w = event.recurrenteId.split("_");
		    	var reservaPadre = w[0];
		    	var exDate = "EXDATE:VALUE=" + w[1];
		    	reserva.id = reservaPadre;
		    	var recurrencia = [];
		    	recurrencia.push(exDate);
		    	reserva.reglasRecurrencia = recurrencia;
	    		//agregar exdate + nueva reserva
		    	reserva.title = event.title;
	    		reserva.start = event.start;
	    		reserva.end = event.end;
	    		reserva.idEspacio = event.idEspacio;
	    		reserva.idGrupo = event.idGrupo;
	    		reserva.color = event.color;
	    		editarReservaRecurrente(reserva, reqHeaders, revertFunc);
	    		
	
	    	}
	    	else{	    		
	    		editarReservaSimple(event, reqHeaders, event.id, revertFunc);
	    	}
	    },
	    eventDrop: function(event, delta, revertFunc) {
	    	if(esRecurrente(event)){
	    		reserva.title = event.title;
	    		reserva.start = event.start;
	    		reserva.end = event.end;
	    		reserva.idEspacio = event.idEspacio;
	    		reserva.idGrupo = event.idGrupo;
	    		reserva.color = event.color;
	    		//agregar exdate + nueva reserva	    		
	    		editarReservaRecurrente(reserva, reqHeaders, revertFunc);    		
	    	}
	    	else{	    		
	    		editarReservaSimple(event, reqHeaders, event.id, revertFunc);
	    	}
	    	
  
	    },
	    viewRender: function(view, element){
	    	$('[role="tooltip"]').popover('hide');
	    },
	    eventSources: [ 
		        {
		        	url: '/reservas/misEventos',							   
		            textColor: 'black'  
		        }	        
	    ]
	       	
	    
	});
}	



function editarReservaSimple(reserva, reqHeaders, idReserva, revertFunc){
	$.ajax({
			url: baseURL + 'reserva/editar/' + idReserva,
			type: 'PUT',
			headers : reqHeaders,
			data: JSON.stringify(reserva),
			contentType: 'application/json',
			success : function(datos) {
				$('#modalEliminarReservaSimple').modal('hide');
				$("#" + idReserva).remove();
				$("#calendar").fullCalendar('refetchEvents');
			},
			error : function(xhr, status) {
				var x = JSON.parse(xhr.responseText);
				alert(x.msg);
				revertFunc();
			}
		});
}

function eliminarReserva(reqHeaders, idReserva){
	$.ajax({
			url: baseURL + 'reserva/' + idReserva,
			type: 'DELETE',
			headers : reqHeaders,
			success : function(datos) {
				$('#modalEliminarReservaSimple').modal('hide');
				$("#" + idReserva).remove();
				$("#calendar").fullCalendar('refetchEvents');
			},    
			error : function(xhr, status) {
				alert('Disculpe, existió un problema');
			}
		});
}

function modalEliminarReservaSimple(){
	$('#modalEditarReserva').modal('hide');
	$('[role="tooltip"]').popover('hide');
	$('#modalEliminarReservaSimple').modal('show');	
}

function modalEliminarReservaRecurrente(){
	$('#modalEditarReserva').modal('hide');
	$('[role="tooltip"]').popover('hide');
	$('#modalRecurrente').modal('show');	
}

function nuevaReserva(reserva, reqHeaders){
	$.ajax({
			url: baseURL + 'nuevaReservaAJAX',
			headers : reqHeaders,
			type: 'POST',		 				 			
			data: JSON.stringify(reserva),
			contentType: 'application/json',
			success : function(datos) {  
				$("#calendar").fullCalendar('refetchEvents');			
			},
			error: function(xhr, status){
				var x = JSON.parse(xhr.responseText);
				console.log(x.msg);
				//borrar EXDATE
			}
		});
}

function esRecurrente(reserva){
	return reserva.recurrenteId != null;
}

function SoloEditarReservaRecurrente(reserva, reqHeaders){	 		
	$.ajax({
		url: baseURL + 'editarReserRecurrente',
		headers : reqHeaders,
		type: 'POST',		 				 			
		data: JSON.stringify(reserva),
		contentType: 'application/json',
		success: function(datos){
			$("#calendar").fullCalendar('refetchEvents');
			$('#modalRecurrente').modal('hide');
		},
		error : function(xhr, status) {			
 			alert('Disculpe, existió un problema');			
		}
	});
}

//Agrega EXDATE y crea una nueva reserva
function editarReservaRecurrente(reserva, reqHeaders, revertFunc){	 		
	$.ajax({
		url: baseURL + 'editarReserRecurrente',
		headers : reqHeaders,
		type: 'POST',		 				 			
		data: JSON.stringify(reserva),
		contentType: 'application/json',			
		error : function(xhr, status) {			
 			alert('Disculpe, existió un problema');			
		}
	}).then(function(){
		reserva.reglasRecurrencia = [];
		$.ajax({
			url: baseURL + 'nuevaReservaAJAX',
			headers : reqHeaders,
			type: 'POST',		 				 			
			data: JSON.stringify(reserva),
			contentType: 'application/json',
			success : function(datos) {  
				$("#calendar").fullCalendar('refetchEvents');			
			},
			error: function(xhr, status){
				var x = JSON.parse(xhr.responseText);
				alert(x.msg);
				//borrar EXDATE
				revertFunc();
				borrarEXDATE(reserva, reqHeaders);
			}
		});
	});
}

function borrarEXDATE(reserva, reqHeaders){
	$.ajax({
		url: baseURL + 'borrarExdate',
		headers : reqHeaders,
		type: 'POST',		 				 			
		data: JSON.stringify(reserva),
		contentType: 'application/json',
		success : function(datos) {  
			//$("#calendar").fullCalendar('refetchEvents');			
		},
		error: function(xhr, status){
			alert("Error de borrado de exdate");
		}
	});
}

function closePopover(){
	$('[role="tooltip"]').popover('hide');
}

