$(document).ready(function(){
		var user = {};
		var token = $("meta[name='_csrf']").attr("content");
	 	var header = $("meta[name='_csrf_header']").attr("content");
	 	var reqHeaders = [];
	 	reqHeaders[header] = token;
		
		$("#enlaceGuardar").click(function(){
			
			user.username = $("#idNombre").val();
			user.email = $("#idEmail").val();
			user.password = $("#idPassword").val();
	    	editarUsuario(user,reqHeaders);
		});
		
		
});	

function editarUsuario(user, reqHeaders){
	$.ajax({
			
			url: baseURL + 'nuevoUser',
			//url: baseURL + 'admin/administrar/usuarios/editar/' + idUsuario ,
			type: 'POST',
			headers : reqHeaders,
			data: JSON.stringify(user),
			contentType: 'application/json',
			
			success : function(datos) {   
				 window.location = "/reservas/login";
			},    
			error : function(xhr, status) {
				console.log(user);
 			alert('Disculpe, existió un problema');
 			
			}
		});
	
}