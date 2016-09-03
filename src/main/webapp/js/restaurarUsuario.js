$(document).ready(function(){
	var user = {};
 	var token = $("meta[name='_csrf']").attr("content");
 	var header = $("meta[name='_csrf_header']").attr("content");
 	var reqHeaders = [];
 	reqHeaders[header] = token;
 	
 	$("#texto-busqueda").keyup(function(){
 		var searchTerm = $('#texto-busqueda').val();
 		tipoBusqueda = $('#selec-busqueda').val();
 		
 		var link = '/reservas/admin/administrar/usuarios/restaurar/' + tipoBusqueda + '/' + searchTerm + '/page/'+1;
 		$("#busquedaUsuario").attr("href",link);
 		
 	});
 	
 	$('td a').click(function(){
 		
 		user.id =  $(this).attr("data-id");
 		var username = $(this).attr("name");
 		var email = $(this).attr("email");
 		var facultad = $(this).attr("facul");
 		var roles = $(this).attr("roles");
 		var enabled = $(this).attr("act");
 		var accion = $(this).attr("data-accion");
 		var imagen = "../../../.." + $(this).attr("img");
 		
 		$('#modalEditarUsuario #idNombre').text(username);
 		$('#modalEditarUsuario #idEmail').text(email);
 		$('#modalEditarUsuario #idFacul').text(facultad);
 		$('#modalEditarUsuario #idRoles').text(roles);
 		$('#modalEditarUsuario #idActivado').text(enabled);
 		$('#modalEditarUsuario #idAttachment').attr("src",imagen);
 		$('#modalEditarUsuario #btn-editar').prop("href", baseURL + "admin/administrar/usuarios/editar/" + user.id)
 		
 		if (accion == 'Restaurar'){
 			
 				modalRestaurarUsuario(user, reqHeaders);	
 			
 		}else if(accion == 'Ver'){
 		
 			$('#modalEditarUsuario').modal('show');
 		}
 	});
 	
 	$('#selec-busqueda').change(function(){
 		$('#texto-busqueda').val("");
 		if ($(this).val()=="nombre")
 			$('#texto-busqueda').prop("placeholder", "Introduce nombre de usuario");
 		else if ($(this).val()=="email")
 			$('#texto-busqueda').prop("placeholder", "Introduce el correo");
 		else
 			$('#texto-busqueda').prop("placeholder", "Introduce el nombre de la facultad");
 	});
});

function modalRestaurarUsuario(user, reqHeaders){
 	
		$.ajax({
			url: baseURL + "admin/administrar/usuarios/" + pag + "/restaurar/" + user.id,
			type: 'DELETE',
			headers : reqHeaders,
			success : function(datos) {
				alert("Usuario restaurado");
				$('#modalEditarUsuario').modal('hide');
				$("#" + user.id).remove();
			
			},    
			error : function(xhr, status) {
				alert('Disculpe, existió un problema');
			}
 	});
 
}