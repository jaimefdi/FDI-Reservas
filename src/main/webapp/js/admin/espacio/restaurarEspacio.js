$(document).ready(function(){
	var espacio = {};
 	var tipoBusqueda;
 	var token = $("meta[name='_csrf']").attr("content");
 	var header = $("meta[name='_csrf_header']").attr("content");
 	var reqHeaders = [];
 	reqHeaders[header] = token;
 	
 	$("#texto-busqueda").keyup(function(){
 		var searchTerm = $('#texto-busqueda').val();
 		tipoBusqueda = $('#selec-busqueda').val();
 		
 		var link = '/reservas/admin/administrar/espacios/restaurar/' + tipoBusqueda + '/' + searchTerm + '/page/1';
 		$("#busquedaEspacio").attr("href",link);
 		
 	});
 	
 	$('td a').click(function(){
 		
 		espacio.id =  $(this).attr("data-id");
 		var nombreEspacio = $(this).attr("name");
 		var capacidad = $(this).attr("capacidad");
 		var microfono = $(this).attr("micro");
 		var proyector = $(this).attr("proye");
 		var tipoEspacio = $(this).attr("tipo");
 		var edificio = $(this).attr("edif");
 		var eliminado = $(this).attr("act");
 		var accion = $(this).attr("data-accion");
 		var imagen = "../../../../../" + $(this).attr("img");

 		var x = isEnabled(eliminado);
 		var pr = isEnabled(proyector);
 		var mic = isEnabled(microfono);
 	
 		$('#modalEditarEspacio #idNombre').text(nombreEspacio);
 		$('#modalEditarEspacio #idCapa').text(capacidad);
 		$('#modalEditarEspacio #idMicro').text(mic);
 		$('#modalEditarEspacio #idProy').text(pr);
 		$('#modalEditarEspacio #idTipo').text(tipoEspacio);
 		$('#modalEditarEspacio #idEdificio').text(edificio);
 		$('#modalEditarEspacio #idActivado').text(x);
 		$('#modalEditarEspacio #idAttachment').attr("src",imagen);
 		$('#modalEditarEspacio #btn-editar').prop("href", baseURL + 'admin/administrar/espacio/editar/' + espacio.id);
 	
 		if (accion == 'Restaurar'){
 			
				modalRestaurarEspacio(espacio, reqHeaders);	
			
 		}else if(accion == 'Ver'){
 		
 			$('#modalEditarEspacio').modal('show');
 		}
 	});
 	
 	$('#selec-busqueda').change(function(){
 		
 		$('#texto-busqueda').val("");
 		if ($(this).val()=="nombre")
 			$('#texto-busqueda').prop("placeholder", "Introduce el nombre del espacio");
 		else if ($(this).val()=="edificio")
 			$('#texto-busqueda').prop("placeholder", "Introduce el nombre del edificio");
 	});
 	
});	

function modalRestaurarEspacio(espacio, reqHeaders){
 	
		$.ajax({
			url: baseURL + "admin/administrar/restaurar/espacio/" + espacio.id,
			type: 'DELETE',
			headers : reqHeaders,
			success : function(datos) {
				alert("Espacio restaurado");
				$('#modalEditarEspacio').modal('hide');
				$("#" + espacio.id).remove();
				 window.location = "/reservas/admin/administrar/espacios/page/1";
			
			},    
			error : function(xhr, status) {
				alert('Disculpe, existió un problema');
			}
 	});
 
}

function isEnabled(deleted){
	 if(deleted == "true")
		 return "Sí";
	 else
		 return "No";
	
}