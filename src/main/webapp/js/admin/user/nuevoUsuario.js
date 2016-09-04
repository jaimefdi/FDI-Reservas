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
			//user.facultad = $("#idFacultad").val();
			user.imagen = $("#idAttachment").val();
			console.log(user);
	    	editarUsuario(user,reqHeaders);
		});
		
		$("#idFacultad").autocomplete({
			source:function(request, response){
					var tag = request.term;
					
					$.ajax({
						url: '/reservas/facultad/tag/' + tag,
						type: 'GET',
						contentType: 'application/json',
						success : function(datos) {
							console.log(datos);
							
							response($.map(datos,function(item){
								
									var obj = new Object();
									obj.label = item.id;
									obj.value = item.nombreFacultad; 
									return obj;
				
							}))
							
						},    
					    error : function(xhr, status) {
					        alert('Disculpe, existió un problema');
					    }
					});
			},
			select: function(event, ui){
				user.facultad = ui.item.label;
			},
			minLength: 3

		}).autocomplete("instance")._renderItem = function(ul,item){
			
				var inner_html = '<div class="media"><div class="media-left">' + 
				                  '</div>' + 
				                  '<div class="media-body">' + 
				                  '<h5 class="media-heading">'+ item.value +'</h5>' + 
				                  '</div></div>';
				                  
				        
				                  
		            return $('<li></li>')
		                    .data("item.autocomplete", item)
		                    .append(inner_html)
		                    .appendTo(ul);
			
		};
		
});	

function editarUsuario(user, reqHeaders){
	$.ajax({
			
			url: baseURL + 'admin/nuevoUsuario',
			//url: baseURL + 'admin/administrar/usuarios/editar/' + idUsuario ,
			type: 'POST',
			headers : reqHeaders,
			data: JSON.stringify(user),
			contentType: 'application/json',
			
			success : function(datos) {   
				 window.location = "/reservas/admin/administrar/usuarios/page/1";
			},    
			error : function(xhr, status) {
				
 			alert('Disculpe, existió un problema');
 			
			}
		});
	
}