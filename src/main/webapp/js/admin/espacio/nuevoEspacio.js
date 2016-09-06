$(document).ready(function(){
		var espacio = {};
		var token = $("meta[name='_csrf']").attr("content");
	 	var header = $("meta[name='_csrf_header']").attr("content");
	 	var reqHeaders = [];
	 	reqHeaders[header] = token;
	 	
		
		
		
		$("#enlaceCrear").click(function(){
			espacio.nombreEspacio = $("#idNombre").val();
			//espacio.capacidad = $("#idCapa").val();
			espacio.capacidad = 99;
			espacio.tipoEspacio = $("#idTipo").val();
			espacio.idEdificio =  $("#idEdificio").val();
			//espacio.imagen = $("#idAttachment").val();
			
			console.log(espacio);
			nuevoEspacio(espacio,reqHeaders);
  	
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
				espacio.facultad = ui.item.label;
				//metodo ajax que carga los edificios
				edificiosFacultad(espacio.facultad, reqHeaders);
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

function nuevoEspacio(espacio, reqHeaders){
	
	$.ajax({
			url: baseURL + 'admin/nuevoEspacio',
			type: 'POST',
			headers : reqHeaders,
			data: JSON.stringify(espacio),
			contentType: 'application/json',
			
			success : function(datos) {   
				 window.location = "/reservas/admin/administrar/espacios/page/1";
			},    
			error : function(xhr, status) {
 			alert('Disculpe, existió un problema');
 			
			}
		});
	
}


function edificiosFacultad(idFacultad, reqHeaders){
	
	$.ajax({
		url: baseURL + 'edificiosFacultad/' + idFacultad,
		type: 'GET',
		headers : reqHeaders,
		contentType: 'application/json',
		
		success : function(datos) {   
			 $("#idEdificio").empty();
			 
			 for(var i in datos){
				 var value = datos[i].id;
				 var text = datos[i].nombreEdificio;
				 
				 $("#idEdificio").append(new Option(text, value));
			 }
		},    
		error : function(xhr, status) {
			alert('Disculpe, existió un problema');
			
		}
	});
	
}