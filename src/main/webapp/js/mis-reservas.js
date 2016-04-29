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

$("#idAsunto").bind('input',function(){
	
	removeModalFooterButtons();
	$("#modalEditarReserva .modal-footer").append('<a href="#" class="btn-rec gray">DESCARTAR</a>');
	$("#modalEditarReserva .modal-footer").append('<a href="#" class="btn-rec blue">GUARDAR</a>');
});

$('#modalEditarReserva').on('hidden.bs.modal', function (e) {
	
	removeModalFooterButtons();
	$("#modalEditarReserva .modal-footer").append('<a href="#" class="btn-rec gray">ELIMINAR</a>');
	$("#modalEditarReserva .modal-footer").append('<a href="#" class="btn-rec blue">EDITAR</a>');
});


$("#view-list").click(function(){
	$(this).addClass("red");
	$("#view-calendar").removeClass("red");
	$("#lista").show();
	$("#calendar").hide();
});


$("#view-calendar").click(function(){
	$(this).addClass("red");
	$("#view-list").removeClass("red");
	$("#lista").hide();
	loadCalendar();
	$("#calendar").show();
});


$('tr').click(function(){
	$('#modalEditarReserva').modal('show');
	
	var espacio = $(this).find("td").eq(1).html();
	reserva.id =  $(this).attr("data-id");
	reserva.start = $(this).attr("data-start");
	reserva.end = $(this).attr("data-end");
	reserva.idEspacio = $(this).attr("data-espacio");
	reserva.title = $(this).find("td").eq(0).html();

	$('#modalEditarReserva #idAsunto').val(reserva.title);
	$('#modalEditarReserva #datetimepicker1').val(es.ucm.fdi.dateUtils.fromIso8601(reserva.start));
	$('#modalEditarReserva #datetimepicker2').val(es.ucm.fdi.dateUtils.fromIso8601(reserva.end));
	$('#modalEditarReserva #nombreEspacio').text(espacio);
});

$("#btn-guardar").click(function(){
	reserva.title = $("#modalEditarReserva #idAsunto").val();
	reserva.start =	es.ucm.fdi.dateUtils.toIso8601($('#modalEditarReserva #datetimepicker1').val());
	reserva.end = es.ucm.fdi.dateUtils.toIso8601($('#modalEditarReserva #datetimepicker2').val());


	$.ajax({
		url: baseURL + 'reserva/' + reserva.id,
		type: 'PUT',
		headers : reqHeaders,
		data: JSON.stringify(reserva),
		contentType: 'application/json',
		success : function(datos) {   			
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
	
$('#toda_la_serie').click(function(){
	
	$.ajax({
		url: baseURL + 'reserva/' + reserva.id,
		type: 'DELETE',
		headers : reqHeaders,
		success : function(datos) {
			$("#modalRecurrente").modal('hide');	
			$("#" + reserva.id).remove();
			$("#calendar").fullCalendar('refetchEvents');
		},    
		error : function(xhr, status) {
			alert('Disculpe, existió un problema');
		}
	});
});
	

//fullcalendar
function loadCalendar(){
 	$('#calendar').fullCalendar({
		lang: 'es',
		timezone: 'local',
	    header: {
	        left: 'prev,next today',
	        center: 'title',
	        right: 'month,agendaWeek,agendaDay'
	    },
	    
	    eventClick : function(calEvent,jsEvent, view) {
	    	console.log(calEvent);
	    	reserva.id = calEvent.id;
	    	reserva.recurrenteId = calEvent.recurrenteId;
			$('#modalEditarReserva').modal('show');
			$('#modalEditarReserva #idAsunto').val(calEvent.title);
			$('#modalEditarReserva #datetimepicker1').val(calEvent.start.format("DD/MM/YYYY HH:mm"));
			$('#modalEditarReserva #datetimepicker2').val(calEvent.end.format("DD/MM/YYYY HH:mm"));
			$('#modalEditarReserva #nombreEspacio').text(calEvent.nombreEspacio);
		},
	    defaultDate: '2016-03-12',
	    editable: true,
	    eventLimit: true,
	    eventRender : function(event,element,view) {
	    
	    	if(view.name == 'month'){
				element.popover({
					
						placement : 'auto',
						html : true,
						trigger : 'hover',
						animation : 'true',
						container:'#calendar',
						content : "<b>" + event.nombreEspacio +
								  "</b><br/>" + event.start.format("HH:mm") + "-" + event.end.format("HH:mm") + 
								  "<br/>" + event.title
					});
	    	}
			
		},
		eventResize: function(event, delta, revertFunc, jsEvent) {

	        	reserva.id = event.id;
	        	reserva.title = event.title;
	        	reserva.start = event.start;
	        	reserva.end = event.end;
	        	reserva.idEspacio = event.idEspacio;
	        	reserva.recurrenteId = event.recurrenteId;
	        	
	        	$.ajax({
		 			url: baseURL + 'reserva/' + reserva.id,
		 			type: 'PUT',
		 			headers : reqHeaders,
		 			data: JSON.stringify(reserva),
		 			contentType: 'application/json',
		 			success : function(datos) {   
		 				
		 			},    
		 			error : function(xhr, status) {
		 				revertFunc();
		     			alert('Disculpe, existió un problema');
		     			
		 			}
		 		});

	    },
	    eventDrop: function(event, delta, revertFunc) {

	        	reserva.id = event.id;
	        	reserva.title = event.title;
	        	reserva.start = event.start;
	        	reserva.end = event.end;
	        	reserva.idEspacio = event.idEspacio;
	        	reserva.recurrenteId = event.recurrenteId;
	        	
	        	$.ajax({
		 			url: baseURL + 'reserva/' + reserva.id,
		 			type: 'PUT',
		 			headers : reqHeaders,
		 			data: JSON.stringify(reserva),
		 			contentType: 'application/json',
		 			success : function(datos) {   
		 				
		 			},    
		 			error : function(xhr, status) {
		 				revertFunc();
		     			alert('Disculpe, existió un problema');
		     			
		 			}
		 		});
	       
	    },
	    loading: function (bool) {
	        if (bool) {
	            $("#calendar").css({"visibility": "hidden"});		            
	        } else {
	            $("#calendar").css({"visibility": "visible"});		            
	        }
	    },
	    eventSources: [ 
		        {
		        	url: '/reservas/misEventos',							   
		            textColor: 'black'  
		        }	        
	    ]
	       	
	    
	});
}	

});

function removeModalFooterButtons(){
	$("#modalEditarReserva .modal-footer a").each(function(){
		$(this).remove();
	});
}

