$(document).ready(function(){
		var facultad = {};
		var token = $("meta[name='_csrf']").attr("content");
	 	var header = $("meta[name='_csrf_header']").attr("content");
	 	var reqHeaders = [];
	 	reqHeaders[header] = token;
	 	
		
		
		
		$("#enlaceGuardar").click(function(){
			facultad.nombreFacultad = $("#idNombre").val();
			facultad.webFacultad = $("#idWeb").val();
			console.log(facultad);
	    	nuevaFacultad(facultad,reqHeaders);
  	
		});
		
});	

function nuevaFacultad(facultad, reqHeaders){
	
	$.ajax({
			url: baseURL + 'admin/nuevaFacultad',
			type: 'PUT',
			headers : reqHeaders,
			data: JSON.stringify(facultad),
			contentType: 'application/json',
			
			success : function(datos) {   
				 window.location = "/reservas/admin/administrar/facultad/page/1";
			},    
			error : function(xhr, status) {
				alert(baseURL),
 			alert('Disculpe, existió un problema');
 			
			}
		});
	
}