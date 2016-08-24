$(document).ready(function(){
	 	var edificio = {};
	 	var token = $("meta[name='_csrf']").attr("content");
	 	var header = $("meta[name='_csrf_header']").attr("content");
	 	var reqHeaders = [];
	 	reqHeaders[header] = token;
	 	
	 	$('td a').click(function(){
	 		
	 		
	 		edificio.id =  $(this).attr("data-id");
	 		var nombreEdificio = $(this).attr("name");
	 		var direccion = $(this).attr("dir");
	 		var deleted = $(this).attr("act");
	 		var facultad = $(this).attr("fac");
	 		var imagen = $(this).attr("img")
	 		var accion = $(this).attr("data-accion");
	 		
	 		$('#modalEditarEdificio #idNombre').text(nombreEdificio);
	 		$('#modalEditarEdificio #idDir').text(direccion);
	 		$('#modalEditarEdificio #idFacul').text(facultad);
	 		$('#modalEditarEdificio #idActivado').text(deleted);
	 		$('#modalEditarEdificio #idAttachment').text(imagen);
	 		$('#modalEditarEdificio #btn-editar').prop("href", baseURL + "admin/administrar/edificios/editar/" + edificio.id);
	 		
	 		if (accion == 'Eliminar'){
	 			
 				modalEliminarEdificio(edificio, reqHeaders);	
 			
	 		}else if(accion == 'Ver'){
	 		
	 			$('#modalEditarEdificio').modal('show');
	 		}
	 	});
	 	
	 	$('#selec-busqueda').change(function(){
	 		$('#texto-busqueda').val("");
	 		if ($(this).val()=="nombre")
	 			$('#texto-busqueda').prop("placeholder", "Introduce nombre de edificio");
	 		else if ($(this).val()=="web")
	 			$('#texto-busqueda').prop("placeholder", "Introduce la dirección");
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
	 			else if ($("#selec-busqueda").val() == "direccion")
	 			{	
	 				$("#selec-busqueda").val()
	 				response=autocompletarDireccion(tag, response);
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
	 		else if ($('#selec-busqueda').val()=="direccion")
	 			direccion="direccion";
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
	 		var inner_html = '<a href="/reservas/admin/administrar/edificios/'+direccion+'/'+item.label+'/page/1">'+
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
		url: '/reservas/admin/edificio/usuarios/tag/' + tag,
		type: 'GET',
		async: false,
		contentType: 'application/json',
		success : function(datos) {				
			respuesta($.map(datos,function(item){
			
				var obj = new Object();
				obj.label = item.id; 
				obj.value = item.nombreEdificio;
				obj.info = item.direccion;
				return obj;
			
			}))
						
		},    
		error : function(xhr, status) {
			alert('Disculpe, existió un problema');
		}
	});
	return item;
}

function autocompletarDireccion(tag, respuesta)
{
	$.ajax({
		url: '/reservas/admin/edificio/direccion/tag/' + tag,
		type: 'GET',
		async: false,
		contentType: 'application/json',
		success : function(datos) {				
			respuesta($.map(datos,function(item){
			
				var obj = new Object();
				obj.label = item.id; 
				obj.value = item.nombreEdificio;
				obj.info = item.direccion;
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
		url: '/reservas/admin/edificio/facultad/tag/' + tag,
		type: 'GET',
		async: false,
		contentType: 'application/json',
		success : function(datos) {				
			respuesta($.map(datos,function(item){
			
				var obj = new Object();
				obj.label = item.id; 
				obj.value = item.nombreEdificio;
				obj.info = item.direccion;
				return obj;
			
			}))
						
		},    
		error : function(xhr, status) {
			console.log(obj);
			alert('Disculpe, existió un problema');
		}
	});
	return item;
}

function modalEliminarEdificio(edificio, reqHeaders){
	 	
	 		$.ajax({
	 			url: baseURL + "edificio/" + edificio.id,
	 			type: 'DELETE',
	 			headers : reqHeaders,
	 			success : function(datos) {
	 				alert("Edificio eliminado");
	 				$('#modalEditarEdificio').modal('hide');
	 				$("#"+edificio.id).remove();
	 			},    
	 			error : function(xhr, status) {
	 				alert('Disculpe, existió un problema');
	 			}
		 	});
		 
	 }
	 
