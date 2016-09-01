$(document).ready(function(){
	 	var facultad = {};
	 	var token = $("meta[name='_csrf']").attr("content");
	 	var header = $("meta[name='_csrf_header']").attr("content");
	 	var reqHeaders = [];
	 	reqHeaders[header] = token;
	 	
	 	$("#texto-busqueda").keyup(function(){
	 		var searchTerm = $('#texto-busqueda').val();
	 		tipoBusqueda = $('#selec-busqueda').val();
	 		
	 		var link = '/reservas/admin/administrar/facultad/' + tipoBusqueda + '/' + searchTerm + '/page/1';
	 		$("#busquedaFacultad").attr("href",link);
	 		
	 	});
	 	
	 	$('td a').click(function(){
	 		
	 		facultad.id =  $(this).attr("data-id");
	 		var nombreFacultad = $(this).attr("name");
			var webFacultad = $(this).attr("web");
			var deleted = $(this).attr("act");
			var accion = $(this).attr("data-accion");
			
	 		$('#modalEditarFacultad #idNombre').text(nombreFacultad);
	 		$('#modalEditarFacultad #idWeb').text(webFacultad);
	 		$('#modalEditarFacultad #idActivado').text(deleted);
	 		$('#modalEditarFacultad #btn-editar').prop("href", baseURL + "admin/administrar/facultad/editar/" + facultad.id);
	 		
	 		if (accion == 'Ver'){
	 			$('#modalEditarFacultad').modal('show');
	 		}else{
	 			modalEliminarFacultad(facultad, reqHeaders);
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
});
	 	
//	 	 $("#texto-busqueda").autocomplete({
//	 		source:function(request, response){
//	 			var tag = request.term;
//	 			var info;
//	 			if ($("#selec-busqueda").val()=="nombre")
//	 			{
//	 				console.log($("#selec-busqueda").val())
//	 				response=autocompletarNombre(tag, response);
//	 			}
//	 			else if ($("#selec-busqueda").val() == "web")
//	 			{	
//	 				$("#selec-busqueda").val()
//	 				response=autocompletarWeb(tag, response);
//	 			}
//	 				
//	 		},
//	 		minLength: 2
//	 	}).autocomplete("instance")._renderItem = function(ul,item){	
//	 	 	var direccion;
//	 	 	console.log("selector busqueda:" + $('#selec_busqueda').val());
//	 		if ($('#selec-busqueda').val()=="nombre")
//	 			direccion="nombre";
//	 		else if ($('#selec-busqueda').val()=="web")
//	 			direccion="web";
//	 		
//	 		console.log("id:" + item.label);
//	 		console.log("titulo:" + item.value);
//	 		console.log("subtitulo:" + item.info);
//	 		/*
//	 			var inner_html =  '<a href="/reservas/gestor/gestion-reservas/'+direccion+'/'+item.label+'/page/1">'+
//	 							  '<div class="col-md-2" style="padding-top:3px;">' +
//	 			                  '<img class="media-object" src="http://placehold.it/50x50"/>' + 
//	 			                  '</div>' + 
//	 			                  '<div class="col-md-10">' + 
//	 			                  '<p>'+ item.value +'</p>' + 
//	 			                  '<p class="small text-muted">'+ item.info +'</p>'
//	 			                  '</div></a>';
//	 			                  */
//	 		var inner_html = '<a href="/reservas/admin/administrar/facultad/'+direccion+'/'+item.label+'/page/1">'+ 
//	 				        '<div class="media-body">' + 
//	 				        '<h5 class="media-heading">'+ item.value +'</h5>' + 
//	 				        '<p class="small text-muted">'+ item.info +'</p>' + 
//	 				        '</div></div></a>';
//	         
//	 	            return $("<li></li>")
//	 	                    .data("item.autocomplete", item)
//	 	                    .append(inner_html)
//	 	                    .appendTo(ul);
//	 		
//	 	};
//	 	
//	 
// });
//
//function autocompletarNombre(tag, respuesta)
//{
//	$.ajax({
//		url: '/reservas/admin/facultad/nombre/tag/' + tag,
//		type: 'GET',
//		async: false,
//		contentType: 'application/json',
//		success : function(datos) {				
//			respuesta($.map(datos,function(item){
//			
//				var obj = new Object();
//				obj.label = item.id; 
//				obj.value = item.nombreFacultad;
//				obj.info = item.webFacultad;
//				return obj;
//			
//			}))
//						
//		},    
//		error : function(xhr, status) {
//			alert('Disculpe, existió un problema');
//		}
//	});
//	return item;
//}
//
//function autocompletarWeb(tag, respuesta)
//{
//	$.ajax({
//		url: '/reservas/admin/facultad/web/tag/' + tag,
//		type: 'GET',
//		async: false,
//		contentType: 'application/json',
//		success : function(datos) {				
//			respuesta($.map(datos,function(item){
//			
//				var obj = new Object();
//				obj.label = item.id; 
//				obj.value = item.nombreFacultad;
//				obj.info = item.webFacultad;
//				return obj;
//			
//			}))
//						
//		},    
//		error : function(xhr, status) {
//			alert('Disculpe, existió un problema');
//		}
//	});
//	return item;
//}
function modalEliminarFacultad(facultad, reqHeaders){

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
}