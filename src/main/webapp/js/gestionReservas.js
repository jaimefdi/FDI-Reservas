$(document).ready(function(){
	 	var reserva = {};
	 	var token = $("meta[name='_csrf']").attr("content");
	 	var header = $("meta[name='_csrf_header']").attr("content");
	 	var reqHeaders = [];
	 	reqHeaders[header] = token;
	 	var item;
	 	
	 	$('tr').click(function(){
	 		$('#modalEditarReserva').modal('show');
	 		
	 		var espacio = $(this).find("td").eq(2).html();
	 		reserva.id =  $(this).attr("data-id");
	 		reserva.start = $(this).attr("data-start");
	 		reserva.end = $(this).attr("data-end");
	 		reserva.idEspacio = $(this).attr("data-espacio");
	 		reserva.estadoReserva= $(this).attr("data-estado");
	 		reserva.title = $(this).find("td").eq(1).html();
	 		reserva.username = $(this).find("td").eq(0).html();
	 		
	 		$('#modalEditarReserva #idAsunto').attr("value",reserva.title);
	 		$('#modalEditarReserva #idUsuario').attr("value",reserva.username);
	 		$('#modalEditarReserva #datetimepicker1').attr("value",es.ucm.fdi.dateUtils.fromIso8601(reserva.start));
	 		$('#modalEditarReserva #datetimepicker2').attr("value",es.ucm.fdi.dateUtils.fromIso8601(reserva.end));
	 		
	 		$('#modalEditarReserva #nombreEspacio').text(espacio);
	 		$("#selec_estadoReserva option").filter(function() {
	 		    //may want to use $.trim in here
	 		    return $(this).text() == reserva.estadoReserva; 
	 		}).prop("selected", true);
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
				/*		$.ajax({
					url: '/reservas/usuarios/tag/' + tag,
					type: 'GET',
					contentType: 'application/json',
					success : function(datos) {				
						response($.map(datos,function(item){
							
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
		},
		minLength: 2
 //}else{}
					url: '/reservas/espacios/tag/' + tag,
					type: 'GET',
					contentType: 'application/json',
					success : function(datos) {				
						response($.map(datos,function(item){
							
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
				});*/
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
			var inner_html =  '<a href="/reservas/gestor/gestion-reservas/'+direccion+'/'+item.label+'/page/1">'+
							  '<div class="col-md-2" style="padding-top:3px;">' +
			                  '<img class="media-object" src="http://placehold.it/50x50"/>' + 
			                  '</div>' + 
			                  '<div class="col-md-10">' + 
			                  '<p>'+ item.value +'</p>' + 
			                  '<p class="small text-muted">'+ item.info +'</p>'
			                  '</div></a>';
	            return $("<li></li>")
	                    .data("item.autocomplete", item)
	                    .append(inner_html)
	                    .appendTo(ul);
		
	};
		
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
	};
	
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
		
		$("#boton-busqueda").click(function(){
			var id_busqueda = $("#id-busqueda").val();
			var direccion;
			if ($('#selec_busqueda').val()=="user")
				direccion="user";
			else
				direccion="espacio";
			window.location = '/reservas/gestor/gestion-reservas/'+direccion+'/'+id_busqueda+'/page/1';
		});
});