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
	 		 	$("#2_calendario .selec_C4").hide();
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
	 		 	$("#2_calendario .selec_C4").hide();
	 		}
	 		else if(numCalen == 4){
	 			$("#1_calendario").hide();	 			
	 			calendario($("#calendar21"), idGrupo);
	 			calendario($("#calendar22"), idGrupo2);
	 			calendario($("#calendar23"), idGrupo3);
	 			calendario($("#calendar24"), idGrupo4);
	 			$("#2_calendario").show();
	 			$("#2_calendario .selec_C1").show();
	 		 	$("#2_calendario .selec_C2").show();
	 		 	$("#2_calendario .selec_C3").show();
	 		 	$("#2_calendario .selec_C4").show();
	 		}
	 		
	 		
	 	});
	 	
	 	$("#selec_C2_grupo").change(function(){
	 		$("#calendar22").fullCalendar('removeEventSource',{
	 			url: '/reservas/' + idGrupo2 + '/reservasGrupo',							   
	            textColor: 'black'
	 		});
	 		
	 		idGrupo2 = $("#selec_C2_grupo").val();
	 		console.log(idGrupo2);
	 		$("#calendar22").fullCalendar('addEventSource',{
	 			url: '/reservas/' + idGrupo2 + '/reservasGrupo',							   
	            textColor: 'black'
	 		});
	 		//$("#calendar22").fullCalendar('destroy');
	 		//calendario($("calendar22"),idGrupo2);
	 		//$("#calendar22").fullCalendar('refetchEvents');
	 	});
	 	
	 	
	 	$(".pull-right i").click(function(){
	 		idGrupo = $(this).attr("data-idGrupo");
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
	 	
	 
	 	$("#btn-eliminar").click(function(){
	 		
	 		if(reserva.recurrenteId != null){
	 			$('#modalEditarReserva').modal('hide');
	 			$("#modalRecurrente").modal('show');
	 		}
	 		else{
	 			$.ajax({
		 			url: baseURL + 'reserva/' + reserva.id,
		 			type: 'DELETE',
		 			headers : reqHeaders,
		 			success : function(datos) {
		 				alert("Reserva eliminada");
		 				$('#modalEditarReserva').modal('hide');
		 				$("#"+reserva.id).remove();
		 				$("#calendar").fullCalendar('refetchEvents');
		 			},    
		 			error : function(xhr, status) {
		 				alert('Disculpe, existió un problema');
		 			}
		 		});
	 		}
	 		
	 	});
	 	
	 	
	 	$('#solo_esta').click(function(){
	 		
	 		var w = reserva.recurrenteId.split("_");
	 		var reservaPadre = w[0];
	 		var exDate = "EXDATE:";
	 		exDate += "VALUE=" + w[1]; 
	 		reserva.id = reservaPadre;
	 		var recurrencia = [];
	 		recurrencia.push(exDate);
	 		reserva.reglasRecurrencia = recurrencia;
	 			 		
	 		SoloEditarReservaRecurrente(reserva, reqHeaders);
	 		
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
					$('[role="tooltip"]').popover('hide');
					//console.log('Calendar ' + idGrupo + ' eventDragStart');
					// Obtengo la duración en minutos de la reserva
					duration = event.end.diff(event.start,'minutes');
					
				},
				eventDragStop: function(event, jsEvent, ui, view) { 
					//console.log('Calendar ' + idGrupo + ' eventDragStop');					
					// Dirty fix to remove highlighted blue background
					$("td").removeClass("fc-highlight");
				},
				eventDrop: function(event, delta, revertFunc){
					//console.log('Calendar ' + idGrupo + ' eventDrop');
					
					//editarReservaSimple(event, reqHeaders, event.id, revertFunc);
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
										
					cambiarReservaDeCalendario(event, idGrupo, reqHeaders);
				},
				viewRender: function(view, element){
			    	$('[role="tooltip"]').popover('hide');
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


function cambiarReservaDeCalendario(event, idGrupo, reqHeaders){
	$.ajax({
		url: baseURL + 'cambiarReservaDeCalendario/' + idGrupo,
		headers : reqHeaders,
		type: 'POST',		 				 			
		data: JSON.stringify(event),
		contentType: 'application/json',
		success : function(datos) {  
				$("#calendar21").fullCalendar('refetchEvents');	
				$("#calendar22").fullCalendar('refetchEvents');
				$("#calendar23").fullCalendar('refetchEvents');
				$("#calendar24").fullCalendar('refetchEvents');
		},    
		error : function(xhr, status) {			
 			alert('Disculpe, existió un problema');			
		}
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
				$("#calendar21").fullCalendar('refetchEvents');
			},
			error : function(xhr, status) {
				revertFunc();
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
			$("#calendar").fullCalendar('refetchEvents');
			$('#modalRecurrente').modal('hide');
		},
		error : function(xhr, status) {			
 			alert('Disculpe, existió un problema');			
		}
	});
}

function esRecurrente(reserva){
	return reserva.recurrenteId != null;
}

function closePopover(){
	$('[role="tooltip"]').popover('hide');
}
