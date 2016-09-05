$(document).ready(function(){
	var reserva = {};
 	var token = $("meta[name='_csrf']").attr("content");
 	var header = $("meta[name='_csrf_header']").attr("content");
 	var reqHeaders = [];
 	reqHeaders[header] = token;
 	
 	// Ocultar el msg de error al hacer click
 	$("#alertClose").click(function(){
 		$(".alert").css("display","none");
 	});
 	
 	/// Controla que la hora de fin no sea menor que la de inicio ///
 	$("#modalCrearReserva #datetimepicker1").change(function(){
 		var comienzo = es.ucm.fdi.dateUtils.toIso8601($('#modalCrearReserva #datetimepicker1').val());
 		var m = new moment(comienzo);
 		$("#empieza_el").val(m.format("DD/MM/YYYY"));
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
 		
 		$("#empieza_el").val(start.format("DD/MM/YYYY"));
 		
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
 	
 	////////	
	$("#op_1").click(function(){				
		$("#datetimepicker3").val("");
	});
	
	$("#op_2").click(function(){
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
 		desmarcarCkecks();
 		
 		if(t == 'Todos los lunes, miercoles y viernes'){
 			$("#repetirCada").addClass("hidden");
 			$("#diasSemana").addClass("hidden");
 			
 			var dow = ["L","X","V"];
 			marcarChecks(dow);
 			
 		}
 		else if(t == 'Todos los martes y jueves'){	 			
 			$("#repetirCada").addClass("hidden");
 			$("#diasSemana").addClass("hidden");
 			
 			var dow = ["M","J"];
 			marcarChecks(dow);
 			
 		}
		else if(t == 'Todos los dias laborables (de lunes a viernes)'){				
 			$("#repetirCada").addClass("hidden");
 			$("#diasSemana").addClass("hidden");
 			
 			var dow = ["L","M","X","J","V"];
 			marcarChecks(dow);
 			
 		}
		else if(t == 'Cada dia'){
			
			$("#repetirCada").removeClass("hidden");
			$("#diasSemana").addClass("hidden");
			var w = t.split(' ');
			$("#lb_repetirCada").text(w[1] + 's');
			 				   
 		}
		else if(t == 'Cada semana'){
			
			$("#repetirCada").removeClass("hidden");
			$("#diasSemana").removeClass("hidden");
			var w = t.split(' ');
			$("#lb_repetirCada").text(w[1] + 's');

 		}
		else if(t == 'Cada mes'){
			
			$("#repetirCada").removeClass("hidden");
			$("#diasSemana").addClass("hidden");
	
			var w = t.split(' ');
			$("#lb_repetirCada").text(w[1] + 'es');
	    }
		else{
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
 		if($("#op_2").is(':checked')){
 			var u = es.ucm.fdi.dateUtils.toIso8601($('#modalCrearReserva #datetimepicker3').val());
	 		var until = "UNTIL=" + new moment(u).format("YYYY-MM-DD");
 			rrule += ";" + until;
 		}
 		else if($("#op_1").is(':checked')){
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
 		
 		//var rdate = "RDATE:";
 		var exdate = "EXDATE:";
 		
 		
 		if($("#checkRepetir").is(":checked")){
 			recurrencia.push(rrule);
 		}
 				 				
 		
 		var reservaAJAX = {};
 		var asunto = $("#modalCrearReserva #idAsunto").val();
 		
 		if(asunto.length === 0){
 			reservaAJAX.title = "Sin asunto";
 		}
 		else{
 			reservaAJAX.title = asunto;	
 		}
 		
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
 			 					
 		nuevaReserva(reservaAJAX, reqHeaders);
 					
 		
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
			container:'#calendar',
			content : cuerpo
					  
		}).popover('show');
    },
    eventDragStart: function(event, jsEvent, ui, view){
    	$('[role="tooltip"]').popover('hide');
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
    		editarReservaRecurrente(reserva, reqHeaders);
    		

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
    		console.log(event);
    		editarReservaSimple(event, reqHeaders, event.id, revertFunc);
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
				$('#modalRecurrente').modal('hide');
				$("#calendar").fullCalendar('refetchEvents');
			},    
			error : function(xhr, status) {
				alert('Disculpe, existió un problema');
			}
		});
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
				$("#modalCrearReserva").modal('hide');
			},
			error: function(xhr, status){
				$("#modalCrearReserva").modal('hide');
				var x = JSON.parse(xhr.responseText);
				showAlertMsg(x.msg);
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

function editarReservaSimple(reserva, reqHeaders, idReserva, revertFunc){
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
				var x = JSON.parse(xhr.responseText);
				alert(x.msg);
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

function closePopover(){
	$('[role="tooltip"]').popover('hide');
}

function showAlertMsg(msg){
	$(".alert").css("display","block");
	$("#alertMsg").text(msg);
}