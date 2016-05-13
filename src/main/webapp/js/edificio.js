$(document).ready(function(){
	 	var edificio = {};
	 	var token = $("meta[name='_csrf']").attr("content");
	 	var header = $("meta[name='_csrf_header']").attr("content");
	 	var reqHeaders = [];
	 	reqHeaders[header] = token;
	 	
	 	$('td a').click(function(){
	 		
	 		
	 		edificio.id =  $(this).attr("data-id");
	 		var nombreEdificio = $(this).attr("name");
	 		var direccion = $(this).attr("dir");
	 		var deleted = $(this).attr("act");
	 		var facultad = $(this).attr("fac")
	 		
	 		$('#modalEditarEdificio #idNombre').text(nombreEdificio);
	 		$('#modalEditarEdificio #idDir').text(direccion);
	 		$('#modalEditarEdificio #idFacul').text(deleted);
	 		$('#modalEditarEdificio #btn-editar').prop("href", baseURL + "admin/administrar/edificios/editar/" + edificio.id);
	 		
	 		$('#modalEditarEdificio').modal('show');
	 	});
	 	
//	 	$("#btn-guardar").click(function(){
//	 		edificio.nombreEdificio = $("#modalEditarEdificio #idNombre").val();
//	 		edificio.direccion = $('#modalEditarEdificio #idDir').val();
//	 		
//	 		$.ajax({
//	 			url: baseURL + "edificio/" + edificio.id,
//	 			type: 'PUT',
//	 			headers : reqHeaders,
//	 			data: JSON.stringify(edificio),
//	 			contentType: 'application/json',
//	 			success : function(datos) {   
//	 				alert("Edificio actualizado");
//	 				$('#modalEditarEdificio').modal('hide');
//	 				
//	 				$("#"+edificio.id +" td:nth-child(1)").text(edificio.nombreEdificio);
//	 				$("#"+edificio.id +" td:nth-child(2)").text(edificio.direccion);
//	 			},    
//	 			error : function(xhr, status) {
//	     			alert('Disculpe, existió un problema');
//	 			}
//	 		});
//	 	});
	 
	 	$("#btn-eliminar").click(function(){
	 		$.ajax({
	 			url: baseURL + "edificio/" + edificio.id,
	 			type: 'DELETE',
	 			headers : reqHeaders,
	 			success : function(datos) {
	 				alert("Edificio eliminado");
	 				$('#modalEditarEdificio').modal('hide');
	 				$("#"+edificio.id).remove();
	 			},    
	 			error : function(xhr, status) {
	 				alert('Disculpe, existió un problema');
	 			}
	 		});
	 	});
	 
 });