$(document).ready(function(){
	 	var reserva = {};
	 	var token = $("meta[name='_csrf']").attr("content");
	 	var header = $("meta[name='_csrf_header']").attr("content");
	 	var reqHeaders = [];
	 	reqHeaders[header] = token;
	 	var item;
	 	
	 	$('td a').click(function(){
	 		
	 		reserva.id =  $(this).attr("data-id");
	 		reserva.recurrenteId = $(this).attr("data-recurrenteId");
	 		var asunto =  $(this).attr("data-asunto");
	 		var start = $(this).attr("data-start");
	 		var end = $(this).attr("data-end");
	 		var nombreEspacio = $(this).attr("data-espacio");
	 		var nombreGrupo = $(this).attr("data-grupo");
	 		var color = $(this).attr("data-reservaColor");
	 		var accion = $(this).attr("data-accion");
	 		var nombreUser = $(this).attr("data-user");
	 		
	 		console.log(reserva.recurrenteId);
	 		
	 		$('#modalEditarReserva #usuario').text(nombreUser);
	 		$('#modalEditarReserva #asunto').text(asunto);
	 		$('#modalEditarReserva #comienzo').text(es.ucm.fdi.dateUtils.fromIso8601(start));
	 		$('#modalEditarReserva #fin').text(es.ucm.fdi.dateUtils.fromIso8601(end));
	 		$('#modalEditarReserva #nombreEspacio').text(nombreEspacio);
	 		$('#modalEditarReserva #nombreGrupo').text(nombreGrupo);
	 		$('#modalEditarReserva #reservaColor').css("background",color);
	 		$('#modalEditarReserva #enlaceEditar').prop("href", baseURL + "gestor/editar/" + reserva.id)
	 		
	 		if(accion == 'Ver'){
	 			$('#modalEditarReserva').modal('show');
	 		}
	 		else if(accion == 'Eliminar'){
	 			modalEliminarReservaSimple();	
	 		}
	 		
	 		
	 	});
	 	
	 	$("#btn-guardar").click(function(){
	 		reserva.user = $("#modalEditarReserva #idUsuario").val();
	 		reserva.title = $("#modalEditarReserva #idAsunto").val();
	 		reserva.start =	es.ucm.fdi.dateUtils.toIso8601($('#modalEditarReserva #datetimepicker1').val());
	 		reserva.end = es.ucm.fdi.dateUtils.toIso8601($('#modalEditarReserva #datetimepicker2').val());
	 		reserva.estadoReserva = $("#modalEditarReserva #selec_estadoReserva").val();
			console.log(reserva);
	 		$.ajax({
	 			url: baseURL+'reserva/' + reserva.id,
	 			type: 'PUT',
	 			headers : reqHeaders,
	 			data: JSON.stringify(reserva),
	 			contentType: 'application/json',
	 			success : function(datos) {   
	 				//alert("Reserva actualizada");
	 				location.reload();
	 			},    
	 			error : function(xhr, status) {
	     			alert('Disculpe, existió un problema');
	 			}
	 		});
	 	});
	 	
	 	$('#selec-busqueda').change(function(){
	 		$('#texto-busqueda').val("");
	 		if ($(this).val()=="user")
	 			$('#texto-busqueda').prop("placeholder", "Introduce nombre de usuario");
	 		else
	 			$('#texto-busqueda').prop("placeholder", "Introduce nombre de espacio");
	 	});
	 	
	 	$("#btn-eliminar").click(function(){
	 		$.ajax({
	 			url: baseURL+'reserva/' + reserva.id,
	 			type: 'DELETE',
	 			headers : reqHeaders,
	 			success : function(datos) {
	 				alert("Reserva eliminada");
	 				$('#modalEditarReserva').modal('hide');
	 				$("#"+reserva.id).remove();
	 			},    
	 			error : function(xhr, status) {
	 				alert('Disculpe, existió un problema');
	 			}
	 		});
	 	});
	 
 
 



 $("#texto-busqueda").autocomplete({
		source:function(request, response){
			var tag = request.term;
			var info;
			if ($("#selec-busqueda").val()=="user")
			{
				console.log($("#selec-busqueda").val())
				response=autocompletarUser(tag, response);
			}
			else
			{	
				$("#selec-busqueda").val()
				response=autocompletarEspacio(tag, response);
			}
				
		},
		minLength: 2
	}).autocomplete("instance")._renderItem = function(ul,item){	
	 	var direccion;
	 	console.log("selector busqueda:" + $('#selec_busqueda').val());
		if ($('#selec-busqueda').val()=="user")
			direccion="user";
		else
			direccion="espacio";
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
		var inner_html = '<a href="/reservas/gestor/gestion-reservas/'+direccion+'/'+item.label+'/page/1">'+
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
			url: '/reservas/gestor/usuarios/tag/' + tag,
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
	
	function autocompletarEspacio(tag, respuesta)
	{
		$.ajax({
			url: '/reservas/espacios/tag/' + tag,
			type: 'GET',
			async: false,
			contentType: 'application/json',
			success : function(datos) {				
				respuesta($.map(datos,function(item){
					
						var obj = new Object();
						obj.label = item.id; 
						obj.value = item.nombreEspacio;
						obj.info = item.edificio;
						return obj;
	
				}))
				
			},    
		    error : function(xhr, status) {
		        alert('Disculpe, existió un problema');
		    }
		});
		return item;
	}
		
	function modalEliminarReservaSimple(){
		$('#modalEditarReserva').modal('hide');
		$('[role="tooltip"]').popover('hide');
		$('#modalEliminarReservaSimple').modal('show');	
	}

