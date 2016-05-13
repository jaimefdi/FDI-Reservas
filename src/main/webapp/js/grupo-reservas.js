$(document).ready(function(){
	  
	 	var reserva = {};
	 	var token = $("meta[name='_csrf']").attr("content");
	 	var header = $("meta[name='_csrf_header']").attr("content");
	 	var reqHeaders = [];
	 	reqHeaders[header] = token;
	 	var idGrupo2 = $("#selec_C2_grupo").val();
	 	var newStart, newEnd;
	 	
		
	 	calendario($("#calendar11"), idGrupo);
	 	$("#2_calendario .selec_C1").hide();
	 	$("#2_calendario .selec_C2").hide();
	 	
	 	$("#selec_calen").change(function(){
	 		var numCalen = $("#selec_calen").val();
	 		if(numCalen == 1){
	 			$("#2_calendario").hide();	 			
	 			calendario($("#calendar11"), idGrupo);
	 			$("#1_calendario").show();
	 		}
	 		else{
	 			$("#1_calendario").hide();	 			
	 			calendario($("#calendar21"), idGrupo);
	 			calendario($("#calendar22"), idGrupo2);
	 			$("#2_calendario").show();
	 			$("#2_calendario .selec_C1").show();
	 		 	$("#2_calendario .selec_C2").show();
	 		}
	 		
	 		
	 	});
	 	
	 	$("#selec_C2_grupo").change(function(){
	 		idGrupo2 = $("#selec_C2_grupo").val();
	 		$("#calendar22").fullCalendar('removeEventSource', []);
	 		$("#calendar22").fullCalendar('destroy');
	 		calendario($("calendar22"),idGrupo2);
	 		$("#calendar22").fullCalendar('refetchEvents');
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
	 	
	 	///
	 	var calEventStatus = [];
 
	 
	 	////
	 	
	 	$("#btn-guardar").click(function(){
	 		reserva.title = $("#modalEditarReserva #idAsunto").val();
	 		reserva.start =	es.ucm.fdi.dateUtils.toIso8601($('#modalEditarReserva #datetimepicker1').val());
	 		reserva.end = es.ucm.fdi.dateUtils.toIso8601($('#modalEditarReserva #datetimepicker2').val());
	 	

	 		$.ajax({
	 			url: baseURL+'reserva/' + reserva.id,
	 			type: 'PUT',
	 			headers : reqHeaders,
	 			data: JSON.stringify(reserva),
	 			contentType: 'application/json',
	 			success : function(datos) {   
	 				alert("Reserva actualizada");
	 				$('#modalEditarReserva').modal('hide');
	 				$("#"+reserva.id +" td:nth-child(1)").text(reserva.title);
	 				$("#"+reserva.id +" td:nth-child(3)").text(es.ucm.fdi.dateUtils.fromIso8601(reserva.start));
	 				$("#"+reserva.id +" td:nth-child(4)").text(es.ucm.fdi.dateUtils.fromIso8601(reserva.end));
	 			},    
	 			error : function(xhr, status) {
	     			alert('Disculpe, existió un problema');
	 			}
	 		});
	 	});
	 
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
	 			 		
	 		$.ajax({
	 			url: baseURL + 'editarReserRecurrente',
	 			headers : reqHeaders,
	 			type: 'POST',		 				 			
	 			data: JSON.stringify(reserva),
	 			contentType: 'application/json',
	 			success : function(datos) {  
	 				$("#modalRecurrente").modal('hide');	 				
	 				$("#calendar").fullCalendar('refetchEvents');
	 				
	 			},    
	 			error : function(xhr, status) {
	 				
	     			alert('Disculpe, existió un problema');
	     			
	 			}
	 		});
	 		
	 	});

	 	//fullcalendar
	 	function calendario(calendar, idGrupo){
		 	calendar.fullCalendar({
				lang: 'es',
				timezone: 'local',
			    header: {
			        left: '',
			        center: '',
			        right: 'month,agendaWeek'
			    },
			    columnFormat: 'ddd',
			    //defaultView: 'agendaWeek',
			    defaultDate: '2016-03-12',
			    editable: true,
				droppable: true, 
				dragRevertDuration: 0,
				eventLimit: true, 
				eventRender: function(event, element){
					/*
					element.data('event',{
						id: event.id,
						title: event.title,
						start: event.start,
						end: event.end,
						idGrupo: event.idGrupo,
						idEspacio: event.idEspacio,
						nombreEspacio: event.nombreEspacio,
						recurrenteId: event.reccurenteId
					});
					*/
					element.data('event', event);
					
					element.draggable({
						zIndex: 999,
						revert: true,      
						revertDuration: 0
					});
					
					
				},				
				eventDragStart: function( event, jsEvent, ui, view ) {

					// Add dragging event in global var 
					calEventStatus['calendar'] = calendar;
					calEventStatus['event_id'] = event.id;					
					console.log('eventDragStart:');
					console.log(event);
					newEnd = event.end;
					
				},
				eventDragStop: function(event, jsEvent, ui, view) { 
					console.log('EventDragDrop');
					console.log(event);
					//newEnd = event.end;
					// Dirty fix to remove highlighted blue background
					$("td").removeClass("fc-highlight");
				},
				eventDrop: function(event, delta, revertFunc){
					editarReservaSimple(event, reqHeaders, event.id);
				},
				drop: function(date, jsEvent, ui) { 
					
 					// if event dropped from another calendar, remove from that calendar
// 					if (calEventStatus['calendar'] != 'undefined') {
// 						console.log('calendar 1 drop'); 
// 						calEventStatus['calendar'].fullCalendar('removeEvents', calEventStatus['event_id']);		
// 						//$(calEventStatus['calendar']).fullCalendar('refetchEvents');	
// 						calEventStatus = []; // Empty
// 					}
					newStart = date;
					console.log("Nuevo Comienzo");
					console.log(date);
				},
				eventReceive: function(event) {  
					console.log('calendar 1 eventReceive');
					
					event.start = newStart;
					event.end = newStart.add(1, 'hour');
					console.log(event);
					cambiarReservaDeCalendario(event, idGrupo2, reqHeaders);
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


function cambiarReservaDeCalendario(event, idGrupo2, reqHeaders){
	$.ajax({
		url: baseURL + 'cambiarReservaDeCalendario/' + idGrupo2,
		headers : reqHeaders,
		type: 'POST',		 				 			
		data: JSON.stringify(event),
		contentType: 'application/json',
		success : function(datos) {  
				$("#calendar21").fullCalendar('refetchEvents');	
				$("#calendar22").fullCalendar('refetchEvents');
		},    
		error : function(xhr, status) {			
 			alert('Disculpe, existió un problema');			
		}
	});
}

function editarReservaSimple(reserva, reqHeaders, idReserva){
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

