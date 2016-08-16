$(document).ready(function(){
		var user = {};
		var token = $("meta[name='_csrf']").attr("content");
	 	var header = $("meta[name='_csrf_header']").attr("content");
	 	var reqHeaders = [];
	 	reqHeaders[header] = token;
	 	
	 	console.log("jjjjjj");
		console.log(roles);
		
		for(var i in roles){
			if(roles[i].role == "ROLE_USER"){
				$("#chkUser").prop("checked","true");
			}
			else if(roles[i].role == "ROLE_ADMIN"){
				$("#chkAdmin").prop("checked","true");
			}
			else{
				$("#chkSecre").prop("checked","true");
			}
		}
		
		$("#enlaceGuardar").click(function(){
			user.id = idUsuario;
			user.username = $("#idNombre").val();
			user.email = $("#idEmail").val();
			user.facultad = $("#idFacultad").val();
			alert(user.facultad);
			user.imagen = $("#idAttachment").val();
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
									obj.label = item.nombreFacultad; 
									obj.value = item.id;
									//obj.webFacultad = item.webFacultad;
									return obj;
				
							}))
							
						},    
					    error : function(xhr, status) {
					        alert('Disculpe, existió un problema');
					    }
					});
			},
			select: function(event, ui){
				var img = '<img class="img-circle" src="http://placehold.it/30x30" data-toggle="tooltip" data-placement="bottom" title="' + ui.item.value + '" />' ;
				$("#facultad").append(img);
				$('[data-toggle="tooltip"]').tooltip();
				
			},
			minLength: 3

		}).autocomplete("instance")._renderItem = function(ul,item){
			
				var inner_html = '<div class="media"><div class="media-left">' + 
				                  '<img class="img-circle" src="http://placehold.it/50x50"/>' + 
				                  '</div>' + 
				                  '<div class="media-body">' + 
				                  '<h5 class="media-heading">'+ item.nombreFacultad +'</h5>' + 
				                  '</div></div>';
				                  
				        
				                  
		            return $('<li></li>')
		                    .data("item.autocomplete", item)
		                    .append(inner_html)
		                    .appendTo(ul);
			
		};
		
});	

function editarUsuario(user, reqHeaders){
	
	var usuario = null;
	var admin = null;
	var gestor = null;
	var inputElements = document.getElementsByClassName('checkbox');
	for(var i=0; inputElements[i]; ++i){
	      if(inputElements[i].checked){
	    	  if (i == 1){
	           admin = inputElements[i].value;
//	           break;
	    	  }
	    	  else if (i == 3){
	           usuario = inputElements[i].value;
//	           break;
	    	  }
	    	  else if (i == 5){
	           gestor = inputElements[i].value;
//	           break;
	    	  }
	      }
	}
	
//	var facultad = document.getElementById("idFacultad").value;
	
	$.ajax({
			
			url: baseURL + 'admin/administrar/usuarios/editar/' + idUsuario + '/' + usuario + '/' + admin + '/' + gestor + "/" + user.imagen,
			//url: baseURL + 'admin/administrar/usuarios/editar/' + idUsuario ,
			type: 'PUT',
			headers : reqHeaders,
			data: JSON.stringify(user),
			contentType: 'application/json',
			
			success : function(datos) {   
				 window.location = "/reservas/admin/administrar/usuarios/1";
			},    
			error : function(xhr, status) {
				alert(usuario + admin + gestor),
 			alert('Disculpe, existió un problema');
 			
			}
		});
	
}