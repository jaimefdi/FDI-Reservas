$(document).ready(function(){
		var edificio = {};
		var token = $("meta[name='_csrf']").attr("content");
	 	var header = $("meta[name='_csrf_header']").attr("content");
	 	var reqHeaders = [];
	 	reqHeaders[header] = token;
	 	var img = "";
		
		
		
		$("#enlaceGuardar").click(function(){
			edificio.id = idEdificio;
			edificio.nombreEdificio = $("#idNombre").val();
			edificio.direccion = $("#idDir").val();
			edificio.facultad = $("#idFacultad").val();
			
			edificio.imagen = $("#idAttachment").val();
			img = edificio.imagen;
			alert(edificio.imagen);
	    	editarEdificio(edificio,reqHeaders);
	    	alert(img);
		});
		
});	

function editarEdificio(edificio, reqHeaders){
	
	$.ajax({
			url: baseURL + 'admin/administrar/edificios/editar/' + idEdificio + "/" + edificio.imagen,
			type: 'PUT',
			headers : reqHeaders,
			data: JSON.stringify(edificio),
			contentType: 'application/json',
			
			success : function(datos) {   
				 window.location = "/reservas/admin/administrar/edificios/1";
			},    
			error : function(xhr, status) {
				alert(baseURL),
 			alert('Disculpe, existió un problema');
 			
			}
		});
	
}