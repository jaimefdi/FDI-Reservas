$(document).ready(function(){
	  
	
	   $('#2_calendario .selec_C2 option:eq(1)').attr('selected', 'selected');
	   $('#2_calendario .selec_C3 option:eq(2)').attr('selected', 'selected');
	   $('#2_calendario .selec_C4 option:eq(3)').attr('selected', 'selected');
	
	 	var reserva = {};
	 	var token = $("meta[name='_csrf']").attr("content");
	 	var header = $("meta[name='_csrf_header']").attr("content");
	 	var reqHeaders = [];
	 	reqHeaders[header] = token;
	 	var idGrupo2 = $("#selec_C2_grupo").val();
	 	var idGrupo3 = $("#selec_C3_grupo").val();
	 	var idGrupo4 = $("#selec_C4_grupo").val();
	 	var newStart, newEnd, duration;
	 	
		// cargar calendario 1
	 	calendario($("#calendar11"), idGrupo);
	 	$("#2_calendario .selec_C1").hide();
	 	$("#2_calendario .selec_C2").hide();
	 	$("#2_calendario .selec_C3").hide();
	 	$("#2_calendario .selec_C4").hide();
	 	
	 	// Nº de calendarios
	 	$("#selec_calen").change(function(){
	 		var numCalen = $("#selec_calen").val();
	 		if(numCalen == 1){
	 			$("#2_calendario").hide();	 			
	 			calendario($("#calendar11"), idGrupo);
	 			$("#1_calendario").show();
	 		}
	 		else if(numCalen == 2){
	 			$("#1_calendario").hide();	 			
	 			calendario($("#calendar21"), idGrupo);
	 			calendario($("#calendar22"), idGrupo2);
	 			$("#2_calendario").show();
	 			$("#2_calendario .selec_C1").show();
	 		 	$("#2_calendario .selec_C2").show();
	 		 	$("#2_calendario .selec_C3").hide();
	 		 	$("#calendar23").hide();
	 		 	$("#2_calendario .selec_C4").hide();
	 			$("#calendar24").hide();
	 		}
	 		else if(numCalen == 3){
	 			$("#1_calendario").hide();	 			
	 			calendario($("#calendar21"), idGrupo);
	 			calendario($("#calendar22"), idGrupo2);
	 			calendario($("#calendar23"), idGrupo3);
	 			$("#2_calendario").show();
	 			$("#2_calendario .selec_C1").show();
	 		 	$("#2_calendario .selec_C2").show();
	 		 	$("#2_calendario .selec_C3").show();
	 		 	$("#calendar23").show();
	 		 	$("#2_calendario .selec_C4").hide();
	 		 	$("#calendar24").hide();
	 		}
	 		else if(numCalen == 4){
	 			$("#1_calendario").hide();	 			
	 			calendario($("#calendar21"), idGrupo);
	 			calendario($("#calendar22"), idGrupo2);
	 			calendario($("#calendar23"), idGrupo3);
	 			calendario($("#calendar24"), idGrupo4);
	 			$("#2_calendario").show();
	 			$("#2_calendario .selec_C1").show();
	 			$("#calendar21").show();
	 		 	$("#2_calendario .selec_C2").show();
	 		 	$("#calendar22").show();
	 		 	$("#2_calendario .selec_C3").show();
	 		 	$("#calendar23").show();
	 		 	$("#2_calendario .selec_C4").show();
	 		 	$("#calendar24").show();
	 		}
	 		
	 		
	 	});
	 	
	 	$("#selec_C2_grupo").change(function(){
	 		$("#calendar22").fullCalendar('destroy');
	 		idGrupo2 = $("#selec_C2_grupo").val();
	 		calendario($("#calendar22"),idGrupo2);
	 	});
	 	
	 	$("#selec_C3_grupo").change(function(){
	 		$("#calendar23").fullCalendar('destroy');
	 		idGrupo3 = $("#selec_C3_grupo").val();
	 		calendario($("#calendar23"),idGrupo3);
	 	});
	 	
	 	$("#selec_C4_grupo").change(function(){
	 		$("#calendar24").fullCalendar('destroy');
	 		idGrupo4 = $("#selec_C4_grupo").val();
	 		calendario($("#calendar24"),idGrupo4);
	 	});
	 	
	 	
	 	$("#aceptarEliminar").click(function(){
	 		eliminarReserva(reqHeaders, reserva.id);
	 	});
	 	
	 	$(".pull-right i").click(function(){
	 		$("#modalEliminarGrupo").modal('show');
	 	});
	 	
	 	$("#btn-elimGrupo").click(function(){
	 		$.ajax({
	 			url: baseURL + 'grupo/' + idGrupo,
	 			type: 'DELETE',
	 			headers : reqHeaders,
	 			contentType: 'application/json',
	 			success : function(datos) {   
	 				
	 				$('#modalEliminarGrupo').modal('hide');
	 				 window.location = "/reservas/mis-reservas";
	 			
	 			},    
	 			error : function(xhr, status) {
	     			alert('Disculpe, existió un problema');
	 			}
	 		});
	 		
	 		$("#modalEliminarGrupo").modal('hide');
	 	});
	 	
	 	////

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

	 	//fullcalendar
	 	function calendario(calendar, idGrupo){
		 	calendar.fullCalendar({
				lang: 'es',
				timezone: 'local',
			    header: {
			    	left: 'prev,next',
			        center: 'title',
			        right: 'month,agendaWeek'
			    },
			    columnFormat: 'ddd',
			    //defaultView: 'agendaWeek',
			    editable: true,
				droppable: true, 
				dragRevertDuration: 0,
				eventLimit: true,
				eventClick: function(event, jsEvent, view){
					$('[role="tooltip"]').popover('hide');
			    	reserva.id = event.id;
			    	reserva.recurrenteId = event.recurrenteId;
			    	
			    	var linkEliminar = "<div class='col-xs-6 text-left'><a role='button' onclick='modalEliminarReservaSimple()'>" + 'Eliminar' + "</a></div>";
			    	var linkEditar = "<div class='col-xs-6 text-right'><a href='/reservas/editar/" + event.id + "'>" + 'Editar' + "</a></div>";
			    	
			    	if(esRecurrente(reserva)){
			    		var w = reserva.recurrenteId.split("_");
			    		event.id = w[0];
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
			    	
			    	var cuerpo = divDonde + closePopover + divCuando + divAsunto;
						  
					
					if(event.editable){
						cuerpo +=  "<div class='row'>" + linkEliminar + linkEditar + "</div>";
					}
					
			    	$(this).popover({						
						placement : 'auto',
						html : true,
						animation : 'true',
						container: calendar,
						content : cuerpo
								  
					}).popover('show');
			    },
				eventRender: function(event, element){
					
					element.data('event', event);
					
					element.draggable({
						zIndex: 999,
						revert: true,      
						revertDuration: 0
					});
										
				},				
				eventDragStart: function( event, jsEvent, ui, view ) {
					// Cierro los popovers activos
					closePopover();
					if(esRecurrente(event)){		    
				    	var w = event.recurrenteId.split("_");
				    	var reservaPadre = w[0];
				    	var exDate = "EXDATE:VALUE=" + w[1];
				    	reserva.id = reservaPadre;
				    	var recurrencia = [];
				    	recurrencia.push(exDate);
				    	reserva.reglasRecurrencia = recurrencia;
			    	}
					//console.log('Calendar ' + idGrupo + ' eventDragStart');
					// Obtengo la duración en minutos de la reserva
					duration = event.end.diff(event.start,'minutes');
					
				},
				eventDragStop: function(event, jsEvent, ui, view) { 
					//console.log('Calendar ' + idGrupo + ' eventDragStop');					
					// Dirty fix to remove highlighted blue background
					$("td").removeClass("fc-highlight");
				},
				drop: function(date, jsEvent, ui) { 									
					//console.log('Calendar ' + idGrupo + ' drop');					
					// Obtengo el nuevo comienzo al soltar la reserva
					newStart = date;					
				},
				eventReceive: function(event) {  
					//console.log('Calendar ' + idGrupo + ' eventReceive');
					var y = newStart.get('year');
					var m = newStart.get('month');
					var d = newStart.get('date');					
					// Del nuevo comienzo solo me interesa la fecha, la hora se mantiene
				    event.start = event.start.set({'year': y, 'month': m, 'date':d});
				    // Al nuevo comienzo le sumo la duración para obtener el nuevo final
				    newEnd = event.start.clone().add(duration,'minutes');
					event.end = newEnd;
					
					if(esRecurrente(event)){
			    		reserva.title = event.title;
			    		reserva.start = event.start;
			    		reserva.end = event.end;
			    		reserva.idEspacio = event.idEspacio;
			    		reserva.idGrupo = idGrupo;
			    		reserva.color = event.color;
			    		//agregar exdate + nueva reserva	    		
			    		editarReservaRecurrente(reserva, reqHeaders);    		
			    	}
			    	else{
			    		event.idGrupo = idGrupo;
			    		editarReservaSimple(event, reqHeaders, event.id);
			    	}			
					
				},
				viewRender: function(view, element){
					closePopover();
			    },
			    eventSources: [ 
				        {
				        	url: '/reservas/' + idGrupo + '/reservasGrupo',							   
				            textColor: 'black'  
				        }	        
			    ]
			       	
			    
			});
	 	}	
	 	
 });



function editarReservaSimple(reserva, reqHeaders, idReserva){
	$.ajax({
			url: baseURL + 'reserva/editar/' + idReserva,
			type: 'PUT',
			headers : reqHeaders,
			data: JSON.stringify(reserva),
			contentType: 'application/json',
			success : function(datos) {				
				refreshCalendars();
			},
			error : function(xhr, status) {
				var x = JSON.parse(xhr.responseText);
				alert(x.msg);
				refreshCalendars();
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
				refreshCalendars();
			},    
			error : function(xhr, status) {
				alert('Disculpe, existió un problema');
			}
		});
}

function SoloEditarReservaRecurrente(reserva, reqHeaders){	 		
	$.ajax({
		url: baseURL + 'editarReserRecurrente',
		headers : reqHeaders,
		type: 'POST',		 				 			
		data: JSON.stringify(reserva),
		contentType: 'application/json',
		success: function(datos){
			refreshCalendars();
			$('#modalRecurrente').modal('hide');
		},
		error : function(xhr, status) {			
 			alert('Disculpe, existió un problema');			
		}
	});
}

//Agrega EXDATE y crea una nueva reserva
function editarReservaRecurrente(reserva, reqHeaders){	 		
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
				refreshCalendars();			
			},
			error: function(xhr, status){
				var x = JSON.parse(xhr.responseText);
				alert(x.msg);
				//borrar EXDATE
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
						
		},
		error: function(xhr, status){
			alert("Error de borrado de exdate");
		}
	});
}

function esRecurrente(reserva){
	return reserva.recurrenteId != null;
}

function modalEliminarReservaSimple(){
	$('[role="tooltip"]').popover('hide');
	$('#modalEliminarReservaSimple').modal('show');	
}

function modalEliminarReservaRecurrente(){
	$('[role="tooltip"]').popover('hide');
	$('#modalRecurrente').modal('show');	
}

function closePopover(){
	$('[role="tooltip"]').popover('hide');
}

function refreshCalendars(){
	$("#calendar11").fullCalendar('refetchEvents');	
	for(var i = 1; i < 5; i++){
		$("#calendar2" + i).fullCalendar('refetchEvents');	
	}
}
