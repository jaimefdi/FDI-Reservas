$(document).ready(function(){
		var edificio = {};
		var token = $("meta[name='_csrf']").attr("content");
	 	var header = $("meta[name='_csrf_header']").attr("content");
	 	var reqHeaders = [];
	 	reqHeaders[header] = token;
	 	
		
		
		
		$("#enlaceGuardar").click(function(){
			edificio.id = idEdificio;
			edificio.nombreEdificio = $("#idNombre").val();
			edificio.direccion = $("#idDir").val();
			edificio.deleted = $("#idActivado").val();
			
	    	editarEdificio(edficio,reqHeaders);
  	
		});
		
});	

function editarEdificio(edificio, reqHeaders){
	
	$.ajax({
			url: baseURL + 'administrar/edificios/editar/' + idEdificio,
			type: 'PUT',
			headers : reqHeaders,
			data: JSON.stringify(edificio),
			contentType: 'application/json',
			
			success : function(datos) {   
				 window.location = "/reservas/administrar/edificios/1";
			},    
			error : function(xhr, status) {
				alert(baseURL),
 			alert('Disculpe, existió un problema');
 			
			}
		});
	
}