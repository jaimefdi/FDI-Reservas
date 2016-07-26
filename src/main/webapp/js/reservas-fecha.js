$(document).ready(function(){
	var busquedaFecha = {};
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	var reqHeaders = [];
	reqHeaders[header] = token;
	/*
	var d = new Date();
	var fecha = d.getDate() + '/' + (d.getMonth() + 1) + '/' + d.getFullYear();
	var hora = d.getHours();
	var minutos = redondeoMinutos(d.getMinutes());
	var comienzo = hora + ':' + minutos;
	var fin = hora + 1 + ':' + minutos;
	var start = fecha + ' ' + comienzo;
	var end = fecha + ' ' + fin;
	*/
	
	
	var start = moment();
	var end = moment().add(1,'hour');
	var m = redondeoMinutos(start.minutes());	
	start.minute(m);	
	end.minutes(m);
	
	
	// inicialización de los datetimepickers
	$("#datetimepicker1").val(start.format("DD/MM/YYYY HH:mm"));
	$("#datetimepicker2").val(end.format("DD/MM/YYYY HH:mm"));
	
	
	/// Controla que la hora de fin no sea menor que la de inicio ///
 	$("#datetimepicker1").change(function(){
 		var comienzo = es.ucm.fdi.dateUtils.toIso8601($('#datetimepicker1').val());
 		var m = new moment(comienzo);
 		var fin = m.add(1,'hour');
 		$('#datetimepicker2').val(fin.format("DD/MM/YYYY HH:mm"));	
 	});

 	$("#datetimepicker2").change(function(){
 		var comienzo = es.ucm.fdi.dateUtils.toIso8601($('#datetimepicker1').val());
 		var fin = es.ucm.fdi.dateUtils.toIso8601($('#datetimepicker2').val());
 		var start = new moment(comienzo)
 		var end = new moment(fin);
 		if(end.isBefore(start)){
 			$('#datetimepicker2').val(start.add(1,'hour').format("DD/MM/YYYY HH:mm"));	
 		}
 		else{
 			$('#datetimepicker2').val(end.format("DD/MM/YYYY HH:mm"));	
 		}		
 	});
	
	
	$("#filtrarBusqueda").click(function(){
		busquedaFecha.idEdificio = $("#selecEdificios").val();
		busquedaFecha.start = es.ucm.fdi.dateUtils.toIso8601($('#datetimepicker1').val());
		busquedaFecha.end = es.ucm.fdi.dateUtils.toIso8601($('#datetimepicker2').val());
		
		console.log(busquedaFecha);
		
		buscarPorFecha(busquedaFecha, reqHeaders);
		
	});
	
});

function buscarPorFecha(busquedaFechaDTO, reqHeaders){
	$.ajax({
		url: baseURL + 'busquedaFecha',
		headers : reqHeaders,
		type: 'POST',		 				 			
		data: JSON.stringify(busquedaFechaDTO),
		contentType: 'application/json',
		success : function(datos) {  
			console.log(datos);	 							
		},    
		error : function(xhr, status) {			
 			alert('Disculpe, existió un problema');			
		}
	});
}



