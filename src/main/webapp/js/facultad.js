$(document).ready(function(){
	 	var facultad = {};
	 	var token = $("meta[name='_csrf']").attr("content");
	 	var header = $("meta[name='_csrf_header']").attr("content");
	 	var reqHeaders = [];
	 	reqHeaders[header] = token;
	 	
	 	$('td a').click(function(){
	 		
	 		
	 		facultad.id =  $(this).attr("data-id");
	 		var nombreFacultad = $(this).attr("name");
			var webFacultad = $(this).attr("web");
			var deleted = $(this).attr("act");
			
	 		$('#modalEditarFacultad #idNombre').text(nombreFacultad);
	 		$('#modalEditarFacultad #idWeb').text(webFacultad);
	 		$('#modalEditarFacultad #idActivado').text(deleted);
	 		$('#modalEditarFacultad #btn-editar').prop("href");
	 		
	 		$('#modalEditarFacultad').modal('show');
	 	});
	 	
//	 	$("#btn-editar").click(function(){
//	 		facultad.nombreFacultad = $("#modalEditarFacultad #idNombre").val();
//	 		facultad.webFacultad = $("#modalEditarFacultad #idWeb").val();
//	 	
//
//	 		$.ajax({
//	 			url: baseURL + "facultad/" + facultad.id,
//	 			type: 'PUT',
//	 			headers : reqHeaders,
//	 			data: JSON.stringify(facultad),
//	 			contentType: 'application/json',
//	 			success : function(datos) {   
//	 				alert("Facultad actualizada");
//	 				$('#modalEditarFacultad').modal('hide');
//	 				
//	 				$("#"+facultad.id +" td:nth-child(1)").text(facultad.nombreFacultad);
//	 				$("#"+facultad.id +" td:nth-child(2)").text(facultad.webFacultad);
//	 			},    
//	 			error : function(xhr, status) {
//	     			alert('Disculpe, existió un problema');
//	 			}
//	 		});
//	 	});
	 
	 	$("#btn-eliminar").click(function(){
	 		$.ajax({
	 			url: baseURL + "facultad/" + facultad.id,
	 			type: 'DELETE',
	 			headers : reqHeaders,
	 			success : function(datos) {
	 				alert("Facultad eliminada");
	 				$('#modalEditarFacultad').modal('hide');
	 				
	 				$("#"+facultad.id).remove();
	 			},    
	 			error : function(xhr, status) {
	 				
	 				alert('Disculpe, existió un problema');
	 			}
	 		});
	 	});
	 
 });