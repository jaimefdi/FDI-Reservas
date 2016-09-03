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
			
			var x = isEnabled(deleted);
			
	 		$('#modalEditarFacultad #idNombre').text(nombreFacultad);
	 		$('#modalEditarFacultad #idWeb').text(webFacultad);
	 		$('#modalEditarFacultad #idActivado').text(x);
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

function isEnabled(deleted){
	 if(deleted == "true")
		 return "Sí";
	 else
		 return "No";
	
}