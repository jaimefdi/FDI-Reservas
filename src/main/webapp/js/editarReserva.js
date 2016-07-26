$(document).ready(function(){
		var reserva = {};
		var token = $("meta[name='_csrf']").attr("content");
	 	var header = $("meta[name='_csrf_header']").attr("content");
	 	var reqHeaders = [];
	 	reqHeaders[header] = token;
	 	
		$(function(){
			$('#cp2').colorpicker();		
		});
		
		/// Controla que la hora de fin no sea menor que la de inicio ///
		$("#datetimepicker1").change(function(){
			var comienzo = es.ucm.fdi.dateUtils.toIso8601($('#datetimepicker1').val());
			var m = new moment(comienzo);
			var fin = m.add(1,'hour');
			$('#datetimepicker2').val(fin.format("DD/MM/YYYY HH:mm"));	
		});

		$("#datetimepicker2").change(function(){
			var comienzo = es.ucm.fdi.dateUtils.toIso8601($('#datetimepicker1').val());
			var fin = es.ucm.fdi.dateUtils.toIso8601($('#datetimepicker2').val());
			var start = new moment(comienzo)
			var end = new moment(fin);
			if(end.isBefore(start)){
				$('#datetimepicker2').val(start.add(1,'hour').format("DD/MM/YYYY HH:mm"));	
			}
			else{
				$('#datetimepicker2').val(end.format("DD/MM/YYYY HH:mm"));	
			}	
		});
		
		
		
		$("#enlaceGuardar").click(function(){
			
			
			
			reserva.id = idReserva;
			reserva.title = $("#idAsunto").val();
	    	reserva.start = es.ucm.fdi.dateUtils.toIso8601($("#datetimepicker1").val());
	    	reserva.end = es.ucm.fdi.dateUtils.toIso8601($("#datetimepicker2").val());
	    	reserva.idGrupo = $("#selec_grupo").val();
	        reserva.color = $('#cp2').colorpicker('getValue',"");
	    	reserva.idEspacio = idEspacio;
	    	reserva.recurrenteId = recurrenteId;
	    	reserva.reglasRecurrencia = reglas;
	    	
	    	console.log(reserva);
	    	if(esRecurrente(reserva)){
	    		$("#modalRecurrente").modal("show");
	    	}
	    	else{
	    		editarReserva(reserva,reqHeaders);
	    	}

  	
		});
		
		// Editar solo una reserva de la serie
		$('#solo_esta').click(function(){
			var b = reserva.start;			
			var bb = new moment(b).format("DD/MM/YYYY");
			var exDate = "EXDATE:VALUE=" + bb;
			
			var recurrencia = [];
			recurrencia.push(exDate);
			reserva.reglasRecurrencia = recurrencia;
			//editRR2(reserva, reqHeaders);	
			editarReservaRecurrente(reserva, reqHeaders);	
		});
		
		// Editar todas las reservas de la serie
		$('#toda_la_serie').click(function(){
			editarReserva(reserva,reqHeaders);
		});
		
		
		$("#autoUsuarios").autocomplete({
			source:function(request, response){
					var tag = request.term;
					
					$.ajax({
						url: '/reservas/usuarios/tag/' + tag,
						type: 'GET',
						contentType: 'application/json',
						success : function(datos) {
							console.log(datos);
							
							response($.map(datos,function(item){
								
									var obj = new Object();
									obj.label = item.id; 
									obj.value = item.username;
									obj.email = item.email;
									return obj;
				
							}))
							
						},    
					    error : function(xhr, status) {
					        alert('Disculpe, existió un problema');
					    }
					});
			},
			select: function(event, ui){
				var img = '<img class="img-circle" src="http://placehold.it/30x30" data-toggle="tooltip" data-placement="bottom" title="' + ui.item.value + '" />' ;
				$("#asistencia").append(img);
				$('[data-toggle="tooltip"]').tooltip();
				
			},
			minLength: 3

		}).autocomplete("instance")._renderItem = function(ul,item){
			
				var inner_html = '<div class="media"><div class="media-left">' + 
				                  '<img class="img-circle" src="http://placehold.it/50x50"/>' + 
				                  '</div>' + 
				                  '<div class="media-body">' + 
				                  '<h5 class="media-heading">'+ item.value +'</h5>' + 
				                  '<p class="small text-muted">'+ item.email +'</p>' + 
				                  '</div></div>';
				                  
				        
				                  
		            return $('<li></li>')
		                    .data("item.autocomplete", item)
		                    .append(inner_html)
		                    .appendTo(ul);
			
		};
		
});	

function editarReserva(reserva, reqHeaders){
	
	$.ajax({
			url: baseURL + 'reserva/editar/' + idReserva,
			type: 'PUT',
			headers : reqHeaders,
			data: JSON.stringify(reserva),
			contentType: 'application/json',
			success : function(datos) {   
				 window.location = "/reservas/mis-reservas";
			},    
			error : function(xhr, status) {
			
 			alert('Disculpe, existió un problema');
 			
			}
		});
	
}


function editRR2(reserva, reqHeaders, revertFunc){
	var r = {};
	$.extend(true, r, reserva);// clona el objeto
	r.reglasRecurrencia = [];
	$.ajax({
		url: baseURL + 'nuevaReservaAJAX',
		headers : reqHeaders,
		type: 'POST',		 				 			
		data: JSON.stringify(r),
		contentType: 'application/json',
		error: function(xhr, status){
			var x = JSON.parse(xhr.responseText);
			alert(x.msg);
			revertFunc();
		}
	}).then(function(){
		editarReservaRecurrente(reserva,reqHeaders);
	});
}

function editarReservaRecurrente(reserva, reqHeaders){	 		
	$.ajax({
		url: baseURL + 'editarReserRecurrente',
		headers : reqHeaders,
		type: 'POST',		 				 			
		data: JSON.stringify(reserva),
		contentType: 'application/json',
		success : function(datos) {  
			
		},
		error : function(xhr, status) {			
 			alert('Disculpe, existió un problema');			
		}
	}).then(function(){
		reserva.reglasRecurrencia = [];
		nuevaReserva(reserva, reqHeaders);
	});
	
}

function esRecurrente(reserva){
	return reserva.reglasRecurrencia.length > 0;
}

function nuevaReserva(reserva, reqHeaders){
	$.ajax({
			url: baseURL + 'nuevaReservaAJAX',
			headers : reqHeaders,
			type: 'POST',		 				 			
			data: JSON.stringify(reserva),
			contentType: 'application/json',
			success : function(datos) {  
				 window.location = "/reservas/mis-reservas";
			},
			error: function(xhr, status){
				var x = JSON.parse(xhr.responseText);
				alert(x.msg);			
			}
		});
}
