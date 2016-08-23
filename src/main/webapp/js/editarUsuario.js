$(document).ready(function(){
		var user = {};
		var token = $("meta[name='_csrf']").attr("content");
	 	var header = $("meta[name='_csrf_header']").attr("content");
	 	var reqHeaders = [];
	 	reqHeaders[header] = token;
		
//		for(var i in roles){
//			if(roles[i].role == "ROLE_USER"){
//				$("#chkUser").prop("checked","true");
//			}
//			else if(roles[i].role == "ROLE_ADMIN"){
//				$("#chkAdmin").prop("checked","true");
//			}
//			else{
//				$("#chkSecre").prop("checked","true");
//			}
//		}
		
		$("#enlaceGuardar").click(function(){
			user.id = idUsuario;
			user.username = $("#idNombre").val();
			user.email = $("#idEmail").val();
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
				user.facultad = ui.item.label;
				//console.log(idFacultad);
				//$("#idFacultad").prop("name", idFacultad);
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
	
	var usuario = null;
	var admin = null;
	var gestor = null;
	var inputElements = document.getElementsByClassName('checkbox');
	for(var i=0; inputElements[i]; ++i){
	      if(inputElements[i].checked){
	    	  if ((i == 1) || (i == 3)){
	           admin = inputElements[i].value;
//	           break;
	    	  }
	    	  else if ((i == 5) || (i == 7)){
	           usuario = inputElements[i].value;
//	           break;
	    	  }
	    	  else if ((9 == 1) || (i == 11)){
	           gestor = inputElements[i].value;
//	           break;
	    	  }
	      }
	}
	
//	if ($("#chkAdmin").prop("checked") == true){
//		admin = "admin";
//	}
//	if ($("#chkUser").prop("checked") == true){
//		alert($("#chkUser").prop("checked"));
//		user = true;
//	}
//	if ($("#chkGestor").prop("checked") == true){
//		gestor = "gestor";
//	}
	
//	var facultad = document.getElementById("idFacultad").value;
	
	$.ajax({
			
			url: baseURL + 'admin/administrar/usuarios/editar/' + idUsuario + '/' + usuario + '/' + admin + '/' + gestor,
			//url: baseURL + 'admin/administrar/usuarios/editar/' + idUsuario ,
			type: 'PUT',
			headers : reqHeaders,
			data: JSON.stringify(user),
			contentType: 'application/json',
			
			success : function(datos) {   
				 window.location = "/reservas/admin/administrar/usuarios/page/1";
			},    
			error : function(xhr, status) {
				alert(usuario + admin + gestor),
 			alert('Disculpe, existió un problema');
 			
			}
		});
	
}