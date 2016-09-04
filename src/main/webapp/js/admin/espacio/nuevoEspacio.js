$(document).ready(function(){
		var espacio = {};
		var token = $("meta[name='_csrf']").attr("content");
	 	var header = $("meta[name='_csrf_header']").attr("content");
	 	var reqHeaders = [];
	 	reqHeaders[header] = token;
	 	
		
		
		
		$("#enlaceGuardar").click(function(){
			espacio.nombreEspacio = $("#idNombre").val();
			espacio.capacidad = $("#idCapa").val();
			espacio.microfono = $("#idMicro").val();
			espacio.proyector = $("#idProy").val();
			espacio.tipoEspacio = $("#idTipo").val();
			espacio.imagen = $("#idAttachment").val();
			
			
	    	editarEspacio(espacio,reqHeaders);
  	
		});
		
		$("#idFacultad").change(function(){
			$("#idEdificio").autocomplete({
			source:function(request, response){
					var tag = request.term;
					var fac = $("#idFacultad").val();
					$.ajax({
						url: '/reservas/admin/edificio/tag/' + tag + '/' + fac,
						type: 'GET',
						contentType: 'application/json',
						success : function(datos) {
							console.log(datos);
							
							response($.map(datos,function(item){
								
									var obj = new Object();
									obj.label = item.id;
									obj.value = item.nombreEdificio; 
									return obj;
				
							}))
							
						},    
					    error : function(xhr, status) {
					        alert('Disculpe, existió un problema');
					    }
					});
			},
			select: function(event, ui){
				espacio.edificio = ui.item.label;
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
			
		};})
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
//		$("#idEdificio").autocomplete({
//			source:function(request, response){
//					var tag = request.term;
//					
//					$.ajax({
//						url: '/reservas/admin/edificio/tag/' + tag,
//						type: 'GET',
//						contentType: 'application/json',
//						success : function(datos) {
//							console.log(datos);
//							
//							response($.map(datos,function(item){
//								
//									var obj = new Object();
//									obj.label = item.id;
//									obj.value = item.nombreEdificio; 
//									//obj.webFacultad = item.webFacultad;
//									return obj;
//				
//							}))
//							
//						},    
//					    error : function(xhr, status) {
//					        alert('Disculpe, existió un problema');
//					    }
//					});
//			},
//			select: function(event, ui){
//				espacio.edificio = ui.item.label;
//				//console.log(idFacultad);
//				//$("#idFacultad").prop("name", idFacultad);
//			},
//			minLength: 3
//
//		}).autocomplete("instance")._renderItem = function(ul,item){
//			
//				var inner_html = '<div class="media"><div class="media-left">' + 
//				                  '</div>' + 
//				                  '<div class="media-body">' + 
//				                  '<h5 class="media-heading">'+ item.value +'</h5>' + 
//				                  '</div></div>';
//				                  
//				        
//				                  
//		            return $('<li></li>')
//		                    .data("item.autocomplete", item)
//		                    .append(inner_html)
//		                    .appendTo(ul);
//			
//		};
		
});	

function editarEspacio(espacio, reqHeaders){
	
	$.ajax({
			url: baseURL + 'admin/nuevoEspacio',
			type: 'POST',
			headers : reqHeaders,
			data: JSON.stringify(espacio),
			contentType: 'application/json',
			
			success : function(datos) {   
				 window.location = "/reservas/admin/administrar/espacios/1";
			},    
			error : function(xhr, status) {
 			alert('Disculpe, existió un problema');
 			
			}
		});
	
}