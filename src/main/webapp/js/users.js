 $(document).ready(function(){
	 	var user = {};
	 	var token = $("meta[name='_csrf']").attr("content");
	 	var header = $("meta[name='_csrf_header']").attr("content");
	 	var reqHeaders = [];
	 	reqHeaders[header] = token;
	 	
	 	$('td a').click(function(){
	 		
	 		user.id =  $(this).attr("data-id");
	 		var username = $(this).attr("name");
	 		var email = $(this).attr("email");
	 		var facultad = $(this).attr("facul");
	 		var roles = $(this).attr("roles");

	 		$('#modalEditarUsuario #idNombre').text(username);
	 		$('#modalEditarUsuario #idEmail').text(email);
	 		$('#modalEditarUsuario #idFacul').text(facultad);
	 		$('#modalEditarUsuario #idRoles').text(roles);
	 		
	 		$('#modalEditarUsuario #btn-editar').prop("href", baseURL + "admin/administrar/usuarios/editar/" + user.id)
	 		
	 		$('#modalEditarUsuario').modal('show');
	 		
	 	});

	 	 	
	 	$("#btn-eliminar").click(function(){
	 		$.ajax({
	 			url: baseURL + "user/" + user.id,
	 			type: 'DELETE',
	 			headers : reqHeaders,
	 			success : function(datos) {
	 				alert("Usuario eliminado");
	 				$('#modalEditarUsuario').modal('hide');
	 				$("#"+user.id).remove();
	 			},    
	 			error : function(xhr, status) {
	 				alert('Disculpe, existió un problema');
	 			}
	 		});
	 	});
	 
 });