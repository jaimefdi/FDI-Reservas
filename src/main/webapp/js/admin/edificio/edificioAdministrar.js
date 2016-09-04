$(document).ready(function(){
	 	var edificio = {};
	 	var token = $("meta[name='_csrf']").attr("content");
	 	var header = $("meta[name='_csrf_header']").attr("content");
	 	var reqHeaders = [];
	 	reqHeaders[header] = token;
	 	
	 	$("#texto-busqueda").keyup(function(){
	 		var searchTerm = $('#texto-busqueda').val();
	 		tipoBusqueda = $('#selec-busqueda').val();
	 		
	 		var link = '/reservas/admin/administrar/edificios/' + tipoBusqueda + '/' + searchTerm + '/page/1';
	 		$("#busquedaEdificio").attr("href",link);
	 		
	 	});
	 	
	 	$('td a').click(function(){
	 		
	 		
	 		edificio.id =  $(this).attr("data-id");
	 		var nombreEdificio = $(this).attr("name");
	 		var direccion = $(this).attr("dir");
	 		var deleted = $(this).attr("act");
	 		var facultad = $(this).attr("fac");
	 		var imagen = "../../../.." + $(this).attr("img");
	 		var accion = $(this).attr("data-accion");
	 		var x = isEnabled(deleted);
	 		
	 		$('#modalEditarEdificio #idNombre').text(nombreEdificio);
	 		$('#modalEditarEdificio #idDir').text(direccion);
	 		$('#modalEditarEdificio #idFacul').text(facultad);
	 		$('#modalEditarEdificio #idActivado').text(x);
	 		$('#modalEditarEdificio #idAttachment').attr("src",imagen);
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
});
	 	
function modalEliminarEdificio(edificio, reqHeaders){
	 	
	 		$.ajax({
	 			url: baseURL + "edificio/" + edificio.id,
	 			type: 'DELETE',
	 			headers : reqHeaders,
	 			success : function(datos) {
	 				alert("Edificio eliminado");
	 				$('#modalEditarEdificio').modal('hide');
	 				$("#" + edificio.id).remove();
	 			
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