$(document).ready(function(){
		var facultad = {};
		var token = $("meta[name='_csrf']").attr("content");
	 	var header = $("meta[name='_csrf_header']").attr("content");
	 	var reqHeaders = [];
	 	reqHeaders[header] = token;
	 	
		
		
		
		$("#enlaceGuardar").click(function(){
			facultad.id = idFacultad;
			facultad.nombreFacultad = $("#idNombre").val();
			facultad.webFacultad = $("#idWeb").val();
			facultad.deleted = $("#idActivado").val();
			
	    	editarFacultad(facultad,reqHeaders);
  	
		});
		
});	

function editarFacultad(facultad, reqHeaders){
	
	$.ajax({
			url: baseURL + 'administrar/facultad/editar/' + idFacultad,
			type: 'PUT',
			headers : reqHeaders,
			data: JSON.stringify(facultad),
			contentType: 'application/json',
			
			success : function(datos) {   
				 window.location = "/reservas/administrar/facultad/1";
			},    
			error : function(xhr, status) {
				alert(baseURL),
 			alert('Disculpe, existió un problema');
 			
			}
		});
	
}