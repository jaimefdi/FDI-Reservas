$(document).ready(function(){
		var espacio = {};
		var token = $("meta[name='_csrf']").attr("content");
	 	var header = $("meta[name='_csrf_header']").attr("content");
	 	var reqHeaders = [];
	 	reqHeaders[header] = token;
	 	
		
		
		
		$("#enlaceGuardar").click(function(){
			espacio.id = idEspacio;
			espacio.nombreEspacio = $("#idNombre").val();
			espacio.capacidad = $("#idCapa").val();
			espacio.microfono = $("#idMicro").val();
			espacio.proyector = $("#idProy").val();
			espacio.tipoEspacio = $("#idTipo").text();
			
			alert(espacio.tipoEspacio);
	    	editarEspacio(espacio,reqHeaders);
  	
		});
		
});	

function editarEspacio(espacio, reqHeaders){
	
	$.ajax({
			url: baseURL + 'admin/administrar/espacio/editar/' + idEspacio,
			type: 'PUT',
			headers : reqHeaders,
			data: JSON.stringify(espacio),
			contentType: 'application/json',
			
			success : function(datos) {   
				 window.location = "/reservas/admin/administrar/espacios/1";
			},    
			error : function(xhr, status) {
 			alert('Disculpe, existió un problema');
 			
			}
		});
	
}