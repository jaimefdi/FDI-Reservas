$(document).ready(function(){
	var reserva = {};
 	var token = $("meta[name='_csrf']").attr("content");
 	var header = $("meta[name='_csrf_header']").attr("content");
 	var reqHeaders = [];
 	reqHeaders[header] = token;
 	
 	/// Controla que la hora de fin no sea menor que la de inicio ///
 	$("#modalCrearReserva #datetimepicker1").change(function(){
 		var comienzo = es.ucm.fdi.dateUtils.toIso8601($('#modalCrearReserva #datetimepicker1').val());
 		var m = new moment(comienzo);
 		var fin = m.add(1,'hour');
 		$('#modalCrearReserva #datetimepicker2').val(fin.format("DD/MM/YYYY HH:mm"));	
 	});

 	$("#modalCrearReserva #datetimepicker2").change(function(){
 		var comienzo = es.ucm.fdi.dateUtils.toIso8601($('#modalCrearReserva #datetimepicker1').val());
 		var fin = es.ucm.fdi.dateUtils.toIso8601($('#modalCrearReserva #datetimepicker2').val());
 		var start = new moment(comienzo)
 		var end = new moment(fin);
 		if(end.isBefore(start)){
 			$('#modalCrearReserva #datetimepicker2').val(start.add(1,'hour').format("DD/MM/YYYY HH:mm"));	
 		}
 		else{
 			$('#modalCrearReserva #datetimepicker2').val(end.format("DD/MM/YYYY HH:mm"));	
 		}
 		
 		
 	});

 	$('#solo_esta').click(function(){
 		var w = reserva.recurrenteId.split("_");
 		var reservaPadre = w[0];
 		var exDate = "EXDATE:VALUE=" + w[1];
 		reserva.id = reservaPadre;
 		var recurrencia = [];
 		recurrencia.push(exDate);
 		reserva.reglasRecurrencia = recurrencia;
 		editarReservaRecurrente(reserva, reqHeaders);	
 	});
 		
 	$('#toda_la_serie').click(function(){
 		var w = reserva.recurrenteId.split("_");
 		var idReserva = w[0];
 		eliminarReserva(reqHeaders, idReserva);
 	});
 	
 	////////
	$("#op_1").click(function(){
		$("#count_repet").val("");
		$("#datetimepicker3").val("");
	});
	
	$("#op_2").click(function(){				
		$("#datetimepicker3").val("");
	});
	
	$("#op_3").click(function(){
		$("#count_repet").val("");				
	});
 	/////////
		
	/// Inicialización del Colorpicker ///
 	$(function(){
 		$('#cp2').colorpicker();		
    });
		 	
	/// Mostrar y ocultar el div de reserva periódica ///	 	 	
 	$("#checkRepetir").change(function(){
		if(this.checked){
			$("#repetir").css("display","block");
		}
		else{
			$("#repetir").css("display","none");
		}
	});
		 	
		 	
 	$("#selec_frec").change(function(){
 		var t = $("#selec_frec option:selected").text();
 		console.log(t);
 		desmarcarCkecks();
 		
 		if(t == 'Todos los lunes, miercoles y viernes'){
 			$("#repetirCada").addClass("hidden");
 			$("#repetirCadaMes").addClass("hidden");
 			$("#diasSemana").addClass("hidden");
 			
 			var dow = ["L","X","V"];
 			marcarChecks(dow);
 			
 		}
 		else if(t == 'Todos los martes y jueves'){	 			
 			$("#repetirCada").addClass("hidden");
 			$("#repetirCadaMes").addClass("hidden");
 			$("#diasSemana").addClass("hidden");
 			
 			var dow = ["M","J"];
 			marcarChecks(dow);
 			
 		}
		else if(t == 'Todos los dias laborables (de lunes a viernes)'){				
 			$("#repetirCada").addClass("hidden");
 			$("#repetirCadaMes").addClass("hidden");
 			$("#diasSemana").addClass("hidden");
 			
 			var dow = ["L","M","X","J","V"];
 			marcarChecks(dow);
 			
 		}
		else if(t == 'Cada dia'){
			
			$("#repetirCada").removeClass("hidden");
			$("#repetirCadaMes").addClass("hidden");
			$("#diasSemana").addClass("hidden");
			var w = t.split(' ');
			$("#lb_repetirCada").text(w[1] + 's');
			 				   
 		}
		else if(t == 'Cada semana'){
			
			$("#repetirCada").removeClass("hidden");
			$("#repetirCadaMes").addClass("hidden");
			$("#diasSemana").removeClass("hidden");
			var w = t.split(' ');
			$("#lb_repetirCada").text(w[1] + 's');

 		}
		else if(t == 'Cada mes'){
			
			$("#repetirCada").removeClass("hidden");
			$("#repetirCadaMes").removeClass("hidden");
			$("#diasSemana").addClass("hidden");
			// añadir nuevo div
			var w = t.split(' ');
			$("#lb_repetirCada").text(w[1] + 'es');
	    }
		else{
			$("#repetirCadaMes").addClass("hidden");
			$("#diasSemana").addClass("hidden");
			var w = t.split(' ');
			$("#lb_repetirCada").text(w[1] + 's');
		}		 		
 		
 	});
		 	
		 	
 	/// Generara las reglas de recurrencia y las añade al objeto reservaAJAX ///		
 	$("#enlaceCrearReserva").click(function(){
 		var recurrencia = [];
 		var rrule = "RRULE:";
 		var freq = "FREQ=" + $("#selec_frec").val();
 		rrule += freq;
 		
 		/// INTERVAL ///
 		if($("#selec_inter").val() > 1){
 			var interval = "INTERVAL=" + $("#selec_inter").val();
 			rrule += ";" + interval;
 		}
 		
 		/// UNTIL Y COUNT ///
 		if($("#op_3").is(':checked')){
 			var u = es.ucm.fdi.dateUtils.toIso8601($('#modalCrearReserva #datetimepicker3').val());
	 		var until = "UNTIL=" + new moment(u).format("YYYY-MM-DD");
 			rrule += ";" + until;
 		}
 		else if($("#op_2").is(':checked')){
 			var count = "COUNT=" + $("#count_repet").val();
 			rrule += ";" +  count;
 		}
 		
 		/// BYDAY ///
 		if(daysChecked() != ""){
 			var byday = "BYDAY=" + daysChecked();
 			rrule += ";" + byday;
 		}
 		
 		/// BYMONTH ///
 		if($("#selec_frec").val() == "MONTHLY"){
 			var diaMes = $("#empieza_el").val();
 			var bymonth = "BYMONTH=" + diaMes;
 			rrule += ";" + bymonth;
 		}
 		
 		var rdate = "RDATE:";
 		var exdate = "EXDATE:";
 		
 		
 		if($("#checkRepetir").is(":checked")){
 			recurrencia.push(rrule);
 		}
 				 				
 		
 		var reservaAJAX = {};
 		reservaAJAX.title = $("#modalCrearReserva #idAsunto").val();
 		reservaAJAX.start = es.ucm.fdi.dateUtils.toIso8601($('#modalCrearReserva #datetimepicker1').val());				
		reservaAJAX.end = es.ucm.fdi.dateUtils.toIso8601($('#modalCrearReserva #datetimepicker2').val());
 		/// Calcular el nuevo comienzo que depende del byday ///	 		
 		var encontrado = false;
		var c = 0;
		var byday = daysChecked().split(',');
		if(byday != ""){
			while(c < byday.length && !encontrado){
				var nextDay = numeroSemana(byday[c]);
				
				var oldStart = es.ucm.fdi.dateUtils.toIso8601($('#modalCrearReserva #datetimepicker1').val());				
				var oldEnd = es.ucm.fdi.dateUtils.toIso8601($('#modalCrearReserva #datetimepicker2').val());
				
				var currentDay = new moment(oldStart).isoWeekday();
									
				if( currentDay <= nextDay ){
					encontrado = true;
					reservaAJAX.start = es.ucm.fdi.dateUtils.toIso8601(new moment(oldStart).add( nextDay-currentDay ,'d'));
					reservaAJAX.end = es.ucm.fdi.dateUtils.toIso8601(new moment(oldEnd).add( nextDay-currentDay ,'d'));
					
				}
				c++;
			}
		}
 				
 		reservaAJAX.idEspacio = $('#esp_hidden').val();;
 		reservaAJAX.color = $('#cp2').colorpicker('getValue',"");
 		reservaAJAX.reglasRecurrencia = recurrencia;
		reservaAJAX.idGrupo = $("#selec_grupo").val();
 			 					
 		crearReserva(reservaAJAX, reqHeaders);
 					
 		
});
 				
/// Inicialización del fullCalendar ///			
$('#calendar').fullCalendar({
	lang: 'es',
	timezone: 'local',
    header: {
        left: 'prev,next today',
        center: 'title',
        right: 'month,agendaWeek,agendaDay'
    },
    defaultDate: '2016-03-12',			    
    eventLimit: true,		    
    eventClick: function(event, jsEvent, view){
    	$('[role="tooltip"]').popover('hide');
    	reserva.id = event.id;
    	reserva.recurrenteId = event.recurrenteId;
    	
    	var divEliminar = "<div class='col-md-6 text-left'><a role='button' onclick='modalEliminarReservaSimple()'>" + 'Eliminar' + "</a></div>";
    	
    	if(esRecurrente(reserva)){
    		var w = reserva.recurrenteId.split("_");
    		event.id = w[0];
    		divEliminar = "<div class='col-md-6 text-left'><a role='button' onclick='modalEliminarReservaRecurrente()'>" + 'Eliminar' + "</a></div>";
    	}
    	
		var cuerpo = "<div>Donde: <b>" + event.nombreEspacio + "</b><br/>"+
			 "De " + event.start.format("HH:mm") + " a " + event.end.format("HH:mm") + 
			 "<br/>Asunto: " + event.title + "</div><br/>";
		
		if(event.editable){
			cuerpo +=  "<div class='row'>" + divEliminar +
			 		   "<div class='col-md-6 text-right'><a href='/reservas/editar/" + event.id + "'>" + 'Editar' + "</a></div>" +
			 		   "</div>";
		}
			

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
    		//agregar exdate
    		editarReservaRecurrente(reserva, reqHeaders);
    		console.log(event);	    		
    		crearReserva(event, reqHeaders);

    	}
    	else{	    		
    		editarReservaSimple(event, reqHeaders, event.id);
    	}
    },
    eventDrop: function(event, delta, revertFunc) {
    	if(esRecurrente(event)){
    		//agregar exdate
    		editarReservaRecurrente(reserva, reqHeaders);
    		console.log(event);
    		crearReserva(event, reqHeaders);
    	}
    	else{	 
    		console.log(event);
    		editarReservaSimple(event, reqHeaders, event.id);
    	}
    },
	selectable : true,
    select : function(start, end) {
    	$('[role="tooltip"]').popover('hide'); 
		$('#modalCrearReserva').modal('show');
		var d = new Date();
		var eventData = {
			start : start,
			end : end.subtract(1,'day'),
		};
		// redondeo de minutos
		var m = redondeoMinutos(d.getMinutes());
				
		// hora actual para start, +1 para end				
		eventData.start.hours(d.getHours());
		eventData.start.minutes(m);
		eventData.end.hours(d.getHours()).add(1,'hour');
		eventData.end.minutes(m);
		
		$('#modalCrearReserva #idAsunto').val("");
		$("#modalCrearReserva #datetimepicker1").val(eventData.start.format("DD/MM/YYYY HH:mm"));
		$("#modalCrearReserva #datetimepicker2").val(eventData.end.format("DD/MM/YYYY HH:mm"));
		
		$("#empieza_el").val(eventData.start.format("DD/MM/YYYY"));
		var array = [];
		var numDia = eventData.start.isoWeekday();
		array.push(letraSemana(numDia));
		marcarChecks(array);
		
	},
	viewRender: function(view, element){
    	$('[role="tooltip"]').popover('hide');
    },
    eventSources: [ 
	        {
	            url: '/reservas/' + idEspacio + '/eventos',   
	            textColor: 'black'  
	        }	        
    ]
       	
    
});

	/// Filtros de mañana y tarde de reservas /// 
	$("#checkMan").change(function(){
		filtrarReservas(this.checked, idEspacio, 'Man');	
	});
				
	$("#checkTar").change(function(){
		filtrarReservas(this.checked, idEspacio, 'Tar');
	});

	
	$("#aceptarEliminar").click(function(){
		eliminarReserva(reqHeaders, reserva.id);
	});
			
});
		
function modalEliminar(idReserva){
	$('[role="tooltip"]').popover('hide'); 
	$("#modalEliminarReservaSimple").modal('show');
}

function eliminarReserva(reqHeaders, idReserva){
	$.ajax({
			url: baseURL + 'reserva/' + idReserva,
			type: 'DELETE',
			headers : reqHeaders,
			success : function(datos) {
				$('#modalEliminarReservaSimple').modal('hide');
				$("#calendar").fullCalendar('refetchEvents');
			},    
			error : function(xhr, status) {
				alert('Disculpe, existió un problema');
			}
		});
}

function crearReserva(reserva, reqHeaders){
	$.ajax({
			url: baseURL + 'nuevaReservaAJAX',
			headers : reqHeaders,
			type: 'POST',		 				 			
			data: JSON.stringify(reserva),
			contentType: 'application/json',
			success : function(datos) {  
				$("#modalCrearReserva").modal('hide');
				$("#calendar").fullCalendar('refetchEvents');				
			},    
			error : function(xhr, status) {
							
			}
			
		});
}

function filtrarReservas(checked,idEspacio, cuando){
	$("#calendar").fullCalendar('removeEventSource',{
		 url: '/reservas/' + idEspacio + '/eventos',  
        textColor: 'black' 
	});
	
	if(checked){
		$("#calendar").fullCalendar('addEventSource',{
			 url: '/reservas/' + idEspacio + '/eventos' + cuando,   
	         textColor: 'black'
		});
	}
	else{
		$("#calendar").fullCalendar('removeEventSource',{
			 url: '/reservas/' + idEspacio + '/eventos' + cuando,				             
	         textColor: 'black'
		});
	}
}

function esRecurrente(reserva){
	return reserva.recurrenteId != null;
}

function editarReservaRecurrente(reserva, reqHeaders){	 		
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
}

function editarReservaSimple(reserva, reqHeaders, idReserva){
	$.ajax({
			url: baseURL + 'reserva/editar/' + idReserva,
			type: 'PUT',
			headers : reqHeaders,
			data: JSON.stringify(reserva),
			contentType: 'application/json',
			success : function(datos) {				
				$("#calendar").fullCalendar('refetchEvents');
			},
			error : function(xhr, status) {
				revertFunc();
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