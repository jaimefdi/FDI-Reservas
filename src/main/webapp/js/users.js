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
	 		var enabled = $(this).attr("act");
	 		var accion = $(this).attr("data-accion");
	 		var imagen = $(this).attr("img")
	 		
	 		$('#modalEditarUsuario #idNombre').text(username);
	 		$('#modalEditarUsuario #idEmail').text(email);
	 		$('#modalEditarUsuario #idFacul').text(facultad);
	 		$('#modalEditarUsuario #idRoles').text(roles);
	 		$('#modalEditarUsuario #idActivado').text(enabled);
	 		$('#modalEditarEdificio #idAttachment').text(imagen);
	 		$('#modalEditarUsuario #btn-editar').prop("href", baseURL + "admin/administrar/usuarios/editar/" + user.id)
	 		
	 		if (accion == 'Eliminar'){
	 			
	 				modalEliminarUsuario(user, reqHeaders);	
	 			
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
	 	
	 	 $("#texto-busqueda").autocomplete({
	 		source:function(request, response){
	 			var tag = request.term;
	 			var info;
	 			if ($("#selec-busqueda").val()=="nombre")
	 			{
	 				console.log($("#selec-busqueda").val())
	 				response=autocompletarUser(tag, response);
	 			}
	 			else if ($("#selec-busqueda").val() == "email")
	 			{	
	 				$("#selec-busqueda").val()
	 				response=autocompletarEmail(tag, response);
	 			}
	 			else
	 			{	
	 				$("#selec-busqueda").val()
	 				response=autocompletarFacultad(tag, response);
	 			}
	 				
	 		},
	 		minLength: 2
	 	}).autocomplete("instance")._renderItem = function(ul,item){	
	 	 	var direccion;
	 	 	console.log("selector busqueda:" + $('#selec_busqueda').val());
	 		if ($('#selec-busqueda').val()=="nombre")
	 			direccion="nombre";
	 		else if ($('#selec-busqueda').val()=="email")
	 			direccion="email";
	 		else
	 			direccion="facultad";
	 		console.log("id:" + item.label);
	 		console.log("titulo:" + item.value);
	 		console.log("subtitulo:" + item.info);
	 		/*
	 			var inner_html =  '<a href="/reservas/gestor/gestion-reservas/'+direccion+'/'+item.label+'/page/1">'+
	 							  '<div class="col-md-2" style="padding-top:3px;">' +
	 			                  '<img class="media-object" src="http://placehold.it/50x50"/>' + 
	 			                  '</div>' + 
	 			                  '<div class="col-md-10">' + 
	 			                  '<p>'+ item.value +'</p>' + 
	 			                  '<p class="small text-muted">'+ item.info +'</p>'
	 			                  '</div></a>';
	 			                  */
	 		var inner_html = '<a href="/reservas/admin/administrar/user/'+direccion+'/'+item.label+'/page/1">'+
	 						'<div class="media"><div class="media-left">' + 
	 				        '<img class="img-circle" src="http://placehold.it/50x50"/>' + 
	 				        '</div>' + 
	 				        '<div class="media-body">' + 
	 				        '<h5 class="media-heading">'+ item.value +'</h5>' + 
	 				        '<p class="small text-muted">'+ item.info +'</p>' + 
	 				        '</div></div></a>';
	         
	 	            return $("<li></li>")
	 	                    .data("item.autocomplete", item)
	 	                    .append(inner_html)
	 	                    .appendTo(ul);
	 		
	 	};
 });

 function autocompletarUser(tag, respuesta)
	{
		$.ajax({
			url: '/reservas/admin/usuarios/tag/' + tag,
			type: 'GET',
			async: false,
			contentType: 'application/json',
			success : function(datos) {				
				respuesta($.map(datos,function(item){
				
					var obj = new Object();
					obj.label = item.id; 
					obj.value = item.username;
					obj.info = item.email;
					return obj;
				
				}))
							
			},    
			error : function(xhr, status) {
				alert('Disculpe, existió un problema');
			}
		});
		return item;
	}
 
 function autocompletarEmail(tag, respuesta)
	{
		$.ajax({
			url: '/reservas/admin/email/tag/' + tag,
			type: 'GET',
			async: false,
			contentType: 'application/json',
			success : function(datos) {				
				respuesta($.map(datos,function(item){
				
					var obj = new Object();
					obj.label = item.id; 
					obj.value = item.username;
					obj.info = item.email;
					return obj;
				
				}))
							
			},    
			error : function(xhr, status) {
				alert('Disculpe, existió un problema');
			}
		});
		return item;
	}
 
 function autocompletarFacultad(tag, respuesta)
	{
		$.ajax({
			url: '/reservas/admin/facultad/tag/' + tag,
			type: 'GET',
			async: false,
			contentType: 'application/json',
			success : function(datos) {				
				respuesta($.map(datos,function(item){
				
					var obj = new Object();
					obj.label = item.id; 
					obj.value = item.username;
					alert(obj.value);
					obj.info = item.email;
					return obj;
				
				}))
							
			},    
			error : function(xhr, status) {
				alert('Disculpe, existió un problema');
			}
		});
		return item;
	}
 
 function modalEliminarUsuario(user, reqHeaders){
	 	 	
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
	 
 }