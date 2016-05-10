$(document).ready(function(){
		var grupo = {};
		var token = $("meta[name='_csrf']").attr("content");
	 	var header = $("meta[name='_csrf_header']").attr("content");
	 	var reqHeaders = [];
	 	reqHeaders[header] = token;
	 		
	 	console.log(idGrupo);
	 	
		$("#enlaceGuardar").click(function(){

			grupo.id = idGrupo;
			grupo.nombreCorto = $("#nombreCorto").val();
			grupo.nombreLargo = $("#nombreLargo").val();
	    	
	    	
	    	editarGrupo(grupo,reqHeaders);
  	
		});
			
});	

function editarGrupo(grupo, reqHeaders){
	
	$.ajax({
			url: baseURL + 'reserva/grupo/editar/' + idGrupo,
			type: 'PUT',
			headers : reqHeaders,
			data: JSON.stringify(grupo),
			contentType: 'application/json',
			success : function(datos) {   
				 window.location = "/reservas/mis-reservas";
			},    
			error : function(xhr, status) {
			
 			alert('Disculpe, existió un problema');
 			
			}
		});
	
}