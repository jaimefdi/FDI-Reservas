$(document).ready(function(){
	 	var facultad = {};
	 	var token = $("meta[name='_csrf']").attr("content");
	 	var header = $("meta[name='_csrf_header']").attr("content");
	 	var reqHeaders = [];
	 	reqHeaders[header] = token;
	 	
	 	$('td a').click(function(){
	 		
	 		
	 		facultad.id =  $(this).attr("data-id");
	 		var nombreFacultad = $(this).attr("name");
			var webFacultad = $(this).attr("web");
			var deleted = $(this).attr("act");
			
	 		$('#modalEditarFacultad #idNombre').text(nombreFacultad);
	 		$('#modalEditarFacultad #idWeb').text(webFacultad);
	 		$('#modalEditarFacultad #idActivado').text(deleted);
	 		$('#modalEditarFacultad #btn-editar').prop("href", baseURL + "admin/administrar/facultad/editar/" + facultad.id);
	 		
	 		$('#modalEditarFacultad').modal('show');
	 	});
	 	
//	 	$("#btn-editar").click(function(){
//	 		facultad.nombreFacultad = $("#modalEditarFacultad #idNombre").val();
//	 		facultad.webFacultad = $("#modalEditarFacultad #idWeb").val();
//	 	
//
//	 		$.ajax({
//	 			url: baseURL + "facultad/" + facultad.id,
//	 			type: 'PUT',
//	 			headers : reqHeaders,
//	 			data: JSON.stringify(facultad),
//	 			contentType: 'application/json',
//	 			success : function(datos) {   
//	 				alert("Facultad actualizada");
//	 				$('#modalEditarFacultad').modal('hide');
//	 				
//	 				$("#"+facultad.id +" td:nth-child(1)").text(facultad.nombreFacultad);
//	 				$("#"+facultad.id +" td:nth-child(2)").text(facultad.webFacultad);
//	 			},    
//	 			error : function(xhr, status) {
//	     			alert('Disculpe, existió un problema');
//	 			}
//	 		});
//	 	});
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
	 			else if ($("#selec-busqueda").val() == "web")
	 			{	
	 				$("#selec-busqueda").val()
	 				response=autocompletarWeb(tag, response);
	 			}
	 				
	 		},
	 		minLength: 2
	 	}).autocomplete("instance")._renderItem = function(ul,item){	
	 	 	var direccion;
	 	 	console.log("selector busqueda:" + $('#selec_busqueda').val());
	 		if ($('#selec-busqueda').val()=="nombre")
	 			direccion="nombre";
	 		else if ($('#selec-busqueda').val()=="web")
	 			direccion="web";
	 		
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
	 		var inner_html = '<a href="/reservas/admin/administrar/facultades/'+direccion+'/'+item.label+'/page/1">'+
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
	 	$("#btn-eliminar").click(function(){
	 		$.ajax({
	 			url: baseURL + "facultad/" + facultad.id,
	 			type: 'DELETE',
	 			headers : reqHeaders,
	 			success : function(datos) {
	 				alert("Facultad eliminada");
	 				$('#modalEditarFacultad').modal('hide');
	 				
	 				$("#"+facultad.id).remove();
	 			},    
	 			error : function(xhr, status) {
	 				
	 				alert('Disculpe, existió un problema');
	 			}
	 		});
	 	});
	 
 });

function autocompletarUser(tag, respuesta)
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

function autocompletarWeb(tag, respuesta)
{
	$.ajax({
		url: '/reservas/admin/web/tag/' + tag,
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