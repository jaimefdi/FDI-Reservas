$(document).ready(function(){
	 	var espacio = {};
	 	var token = $("meta[name='_csrf']").attr("content");
	 	var header = $("meta[name='_csrf_header']").attr("content");
	 	var reqHeaders = [];
	 	reqHeaders[header] = token;
	 	
	 	$('td a').click(function(){
	 		
	 		
	 		espacio.id =  $(this).attr("data-id");
	 		var nombreEspacio = $(this).attr("name");
	 		var capacidad = $(this).attr("capacidad");
	 		var microfono = $(this).attr("micro");
	 		var proyector = $(this).attr("proye");
	 		var tipoEspacio = $(this).attr("tipo");

	 		//$('#modalEditarEspacio #idEspacio').text(espacio.id);
	 		$('#modalEditarEspacio #idNombre').text(nombreEspacio);
	 		$('#modalEditarEspacio #idCapa').text(capacidad);
	 		$('#modalEditarEspacio #idMicro').text(microfono);
	 		$('#modalEditarEspacio #idProy').text(proyector);
	 		$('#modalEditarEspacio #idTipo').text(tipoEspacio);
	 		$('#modalEditarEspacio #btn-editar').prop("href", baseURL + 'admin/administrar/espacio/editar/' + espacio.id);
	 		$('#modalEditarEspacio').modal('show');
	 		
	 	});
	 	
//	 	$("#btn-guardar").click(function(){
//	 		espacio.nombreEspacio = $("#modalEditarEspacio #idNombre").val();
//	 		espacio.capacidad = $('#modalEditarEspacio #idCapa').val();
//	 		espacio.microfono = $('#modalEditarEspacio #idMicro').val();
//	 		espacio.proyector = $('#modalEditarEspacio #idProy').val();
//	 		espacio.tipoEspacio = $('#modalEditarEspacio #idTipo').val();
//	 		
//	 		$.ajax({
//	 			url: baseURL + "espacio/" + espacio.id,
//	 			type: 'PUT',
//	 			headers : reqHeaders,
//	 			data: JSON.stringify(espacio),
//	 			contentType: 'application/json',
//	 			success : function(datos) {   
//	 				alert("Espacio actualizado");
//	 				$('#modalEditarEspacio').modal('hide');
//	 				//$("#"+espacio.id +" td:nth-child(1)").text(espacio.id);
//	 				$("#"+espacio.id +" td:nth-child(1)").text(espacio.nombreEspacio);
//	 				$("#"+espacio.id +" td:nth-child(2)").text(espacio.capacidad);
//	 				$("#"+espacio.id +" td:nth-child(3)").text(espacio.microfono);
//	 				$("#"+espacio.id +" td:nth-child(4)").text(espacio.proyector);
//	 				$("#"+espacio.id +" td:nth-child(5)").text(espacio.tipoEspacio);
//	 			},    
//	 			error : function(xhr, status) {
//	     			alert('Disculpe, existió un problema');
//	 			}
//	 		});
//	 	});
	 
	 	$("#btn-eliminar").click(function(){
	 		$.ajax({
	 			url: baseURL + "espacio/" + espacio.id,
	 			type: 'DELETE',
	 			headers : reqHeaders,
	 			success : function(datos) {
	 				alert("Espacio eliminado");
	 				$('#modalEditarEspacio').modal('hide');
	 				$("#"+espacio.id).remove();
	 			},    
	 			error : function(xhr, status) {
	 				alert('Disculpe, existió un problema');
	 			}
	 		});
	 	});
	 
 });