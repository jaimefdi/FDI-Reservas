$(document).ready(function(){
	var busquedaFecha = {};
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	var reqHeaders = [];
	reqHeaders[header] = token;
	var reservaDTO = {};
	
	
	
	
	$('#resultContent').on('click', 'button', function() {
		var idEspacio = $(this).attr("data-idEspacio");		
		var asunto = $("#asunto" + idEspacio).val();
		if(asunto == '')
			asunto = "Sin asunto";
		
		reservaDTO.idEspacio = idEspacio;
		reservaDTO.title = asunto;
		reservaDTO.reglasRecurrencia = [];
		reservaDTO.idGrupo = 0;
		
		nuevaReserva(reservaDTO, reqHeaders);
		
	});
	
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
		busquedaFecha.desde = es.ucm.fdi.dateUtils.toIso8601($('#datetimepicker1').val());
		busquedaFecha.hasta = es.ucm.fdi.dateUtils.toIso8601($('#datetimepicker2').val());
		
		reservaDTO.start = es.ucm.fdi.dateUtils.toIso8601($('#datetimepicker1').val());
		reservaDTO.end = es.ucm.fdi.dateUtils.toIso8601($('#datetimepicker2').val());
		
		
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
			$('#resultContent').empty();
			
			$('#resultContent').append(
				 $.map(datos, function (item, index) {
					 
					return '<div class="col-xs-10 col-xs-offset-1 resultSearch">' +
							'<div class="col-xs-12">' +
								'<p><b>Donde: </b><i>' + item.nombreEspacio + '</i></p>' +			                       
			                '</div>' +
			                '<div class="col-xs-8" style="padding:0px;">' +
			                	'<div class="form-group">' +
			                       '<label class="col-md-2 control-label" style="padding-right:0px;">Asunto: </label>' +
			                        '<div class="col-md-6" style="padding-right:0px;">' +
			                           '<input id=asunto'+ item.idEspacio +' type="text" class="form-control"/>' +
			                        '</div>' +
			                    '</div>' +
							'</div>' +
							'<div class="col-xs-4 text-right">' +
							'<button data-idEspacio='+ item.idEspacio +' class="btn-normal">Reservar</button>' +
						    '</div>'+
						   '</div>'; 
			}));
		},    
		error : function(xhr, status) {			
 			alert('Disculpe, existió un problema');			
		}
	});
}


function nuevaReserva(reservaDTO, reqHeaders){
	$.ajax({
		url: baseURL + 'nuevaReservaAJAX',
		headers : reqHeaders,
		type: 'POST',		 				 			
		data: JSON.stringify(reservaDTO),
		contentType: 'application/json',
		success : function(datos) {  
			window.location = "/reservas/mis-reservas";		
		},
		error: function(xhr, status){
			var x = JSON.parse(xhr.responseText);
			alert(x.msg);
		}
	});
}

