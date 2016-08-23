$(document).ready(function(){
		var user = {};
		var token = $("meta[name='_csrf']").attr("content");
	 	var header = $("meta[name='_csrf_header']").attr("content");
	 	var reqHeaders = [];
	 	reqHeaders[header] = token;
	 	
	 	// Ocultar el msg de error al hacer click
	 	$("#alertClose").click(function(){
	 		$(".alert").css("display","none");
	 	});
	 	
		$("#enlaceGuardar").click(function(){

			user.id = idUsuario;
			user.oldPassword = $("#oldPassword").val();
			user.username = $("#username").val();
			user.email = $("#email").val();
			
			if(user.oldPassword != ""){// quiero cambiar de contraseña
				if($("#newPassword1").val() != $("#newPassword2").val())
		    		alert("Las nuevas contraseñas deben coincidir");
		    	else{
		    		user.newPassword = $("#newPassword1").val();
		    		console.log(user);
					editarPerfil(user, reqHeaders);
		    	}
			}
			else{
				user.oldPassword = null;
				editarPerfil(user, reqHeaders);
			}
	    	
	
		});
			
});	

function editarPerfil(usuario, reqHeaders){
	
	$.ajax({
			url: baseURL + 'perfil/editar',
			type: 'PUT',
			headers : reqHeaders,
			data: JSON.stringify(usuario),
			contentType: 'application/json',
			success : function(datos) {   
				 window.location = "/reservas/perfil";
			},    
			error : function(xhr, status) {		
				var x = JSON.parse(xhr.responseText);
				showAlertMsg(x.msg);		
			}
		});
	
}

function showAlertMsg(msg){
	$(".alert").css("display","block");
	$("#alertMsg").text(msg);
}

