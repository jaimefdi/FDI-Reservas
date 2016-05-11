$(document).ready(function(){
		console.log("entra en ready");
	 	var reserva = {};
	 	var token = $("meta[name='_csrf']").attr("content");
	 	var header = $("meta[name='_csrf_header']").attr("content");
	 	var reqHeaders = [];
	 	reqHeaders[header] = token;
	 	
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
	 	
	 	$('#selec_busqueda').change(function(){
	 		console.log("entra");
	 		$('#texto_busqueda').val("");
	 	});

	 	$('#boton_busqueda').click(function(){
	 		console.log("entra");
	 		var valor="";	
	 		$('#texto_busqueda').val(" ");
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
	 
 
 



 $("#idUsuario").autocomplete({
		source:function(request, response){
				var tag = request.term;
				
				$.ajax({
					url: '/reservas/usuario/tag/' + tag,
					type: 'GET',
					contentType: 'application/json',
					success : function(datos) {
										
						response($.map(datos,function(item){
							
								var obj = new Object();
								obj.label = item.id; 
								obj.value = item.username;
								obj.mail = item.email;
								return obj;
			
						}))
						
					},    
				    error : function(xhr, status) {
				        alert('Disculpe, existió un problema');
				    }
				});
		},
		select: function(event, ui){
			
			$.ajax({
				url: '/reservas/usuario/' + ui.item.label,
				type: 'GET',
				contentType: 'application/json',
				success : function(datos) {
					
					$("#selec_edificios").empty();
					for(var i in datos){
						var text = datos[i].username;
						var value = datos[i].id;
						$("#selec_edificios").append(new Option(text, value));	
					}
				    
				},    
			    error : function(xhr, status) {
			        alert('Disculpe, existió un problema');
			    }
			});
		},
		minLength: 2

	}).autocomplete("instance")._renderItem = function(ul,item){
		
			var inner_html = '<div class="col-md-2" style="padding-top:3px;">' + 
			                  '<img class="media-object" src="http://placehold.it/50x50"/>' + 
			                  '</div>' + 
			                  '<div class="col-md-10">' + 
			                  '<p>'+ item.value +'</p>' + 
			                  '<p class="small text-muted">'+ item.email +'</p>' + 
			                  '</div>';
	            return $("<li></li>")
	                    .data("item.autocomplete", item)
	                    .append(inner_html)
	                    .appendTo(ul);
		
	};
		
		$("#selec_edificios").change(function(){
			var id_usuario = $("#selec_edificios").val();
			var link = 'edificio/' + id_usuario + '/espacios';
			$("#edificio_link").attr("href",link);
			
		});
});