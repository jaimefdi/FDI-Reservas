$(document).ready(function(){
	 	var espacio = {};
	 	var token = $("meta[name='_csrf']").attr("content");
	 	var header = $("meta[name='_csrf_header']").attr("content");
	 	var reqHeaders = [];
	 	reqHeaders[header] = token;
	 	
	 	$('td a').click(function(){
	 		
	 		
	 		espacio.id =  $(this).attr("data-id");
	 		var nombreEspacio = $(this).attr("name");
	 		var capacidad = $(this).attr("capacidad");
	 		var microfono = $(this).attr("micro");
	 		var proyector = $(this).attr("proye");
	 		var tipoEspacio = $(this).attr("tipo");
	 		var edificio = $(this).attr("edif");
	 		var eliminado = $(this).attr("act");

	 		//$('#modalEditarEspacio #idEspacio').text(espacio.id);
	 		$('#modalEditarEspacio #idNombre').text(nombreEspacio);
	 		$('#modalEditarEspacio #idCapa').text(capacidad);
	 		$('#modalEditarEspacio #idMicro').text(microfono);
	 		$('#modalEditarEspacio #idProy').text(proyector);
	 		$('#modalEditarEspacio #idTipo').text(tipoEspacio);
	 		$('#modalEditarEspacio #idEdificio').text(edificio);
	 		$('#modalEditarEspacio #idActivado').text(eliminado);
	 		$('#modalEditarEspacio #btn-editar').prop("href", baseURL + 'admin/administrar/espacio/editar/' + espacio.id);
	 		$('#modalEditarEspacio').modal('show');
	 		
	 	});
	 	
	 	$('#selec-busqueda').change(function(){
	 		$('#texto-busqueda').val("");
	 		if ($(this).val()=="nombre")
	 			$('#texto-busqueda').prop("placeholder", "Introduce nombre de usuario");
	 		else if ($(this).val()=="edificio")
	 			$('#texto-busqueda').prop("placeholder", "Introduce el edificio");
	 	});
	 	
	 	 $("#texto-busqueda").autocomplete({
	 		source:function(request, response){
	 			var tag = request.term;
	 			var info;
	 			if ($("#selec-busqueda").val()=="nombre")
	 			{
	 				console.log($("#selec-busqueda").val())
	 				response=autocompletarNombre(tag, response);
	 			}
	 			else if ($("#selec-busqueda").val() == "edificio")
	 			{	
	 				$("#selec-busqueda").val()
	 				response=autocompletarEdificio(tag, response);
	 			}
	 				
	 		},
	 		minLength: 2
	 	}).autocomplete("instance")._renderItem = function(ul,item){	
	 	 	var direccion;
	 	 	console.log("selector busqueda:" + $('#selec_busqueda').val());
	 		if ($('#selec-busqueda').val()=="nombre")
	 			direccion="nombre";
	 		else if ($('#selec-busqueda').val()=="edificio")
	 			direccion="edificio";
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
	 		var inner_html = '<a href="/reservas/admin/administrar/espacios/'+direccion+'/'+item.label+'/page/1">'+
	 						'<div class="media"><div class="media-left">' + 
	 				        '<img class="img-circle" src="http://placehold.it/50x50"/>' + 
	 				        '</div>' + 
	 				        '<div class="media-body">' + 
	 				        '<h5 class="media-heading">'+ item.value +'</h5>' + 
	 				        '</div></div></a>';
	         
	 	            return $("<li></li>")
	 	                    .data("item.autocomplete", item)
	 	                    .append(inner_html)
	 	                    .appendTo(ul);
	 		
	 	};
	 	
	 	$("#btn-eliminar").click(function(){
	 		$.ajax({
	 			url: baseURL + "espacio/" + espacio.id,
	 			type: 'DELETE',
	 			headers : reqHeaders,
	 			success : function(datos) {
	 				alert("Espacio eliminado");
	 				$('#modalEditarEspacio').modal('hide');
	 				$("#"+espacio.id).remove();
	 			},    
	 			error : function(xhr, status) {
	 				alert('Disculpe, existió un problema');
	 			}
	 		});
	 	});
	 
 });

function autocompletarNombre(tag, respuesta)
{
	$.ajax({
		url: '/reservas/admin/espacio/tag/' + tag,
		type: 'GET',
		async: false,
		contentType: 'application/json',
		success : function(datos) {				
			respuesta($.map(datos,function(item){
			
				var obj = new Object();
				obj.label = item.id; 
				obj.value = item.nombreEspacio;
				return obj;
			
			}))
						
		},    
		error : function(xhr, status) {
			alert('Disculpe, existió un problema');
		}
	});
	return item;
}

function autocompletarEdificio(tag, respuesta)
{
	$.ajax({
		url: '/reservas/admin/espacio/edificio/tag/' + tag,
		type: 'GET',
		async: false,
		contentType: 'application/json',
		success : function(datos) {				
			respuesta($.map(datos,function(item){
			
				var obj = new Object();
				obj.label = item.id; 
				obj.value = item.nombreEspacio;
				return obj;
			
			}))
						
		},    
		error : function(xhr, status) {
			alert('Disculpe, existió un problema');
		}
	});
	return item;
}