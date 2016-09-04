$(document).ready(function(){
 	var espacio = {};
 	var token = $("meta[name='_csrf']").attr("content");
 	var header = $("meta[name='_csrf_header']").attr("content");
 	var reqHeaders = [];
 	reqHeaders[header] = token;
	
 	//console.log(numItems);
 	
	$(".owl-carousel").owlCarousel({		
		 	
	 		margin: 7,
	 		responsiveClass: true,
	 		responsive: {
	 			0:{
	 				items: 2
	 			},
	 			400:{
	 				items: 4
	 			},
	 			600:{
	 				items: 6
	 			}
	 		}
	 	});
 	
	// Agrego el click event a cada item
	addItemClickEvent(reqHeaders);
 	
	// Marco el primer item como seleccionado
	checkFirstItem();
 	
 	$("#selec_tipoEspacios").change(function(){
		var tipoEspacio = $("#selec_tipoEspacios").val();
		
		$.ajax({
			url: '/reservas/' + idEdificio + '/tipoEspacio/' + tipoEspacio,
			type: 'GET',
			contentType: 'application/json',
			success : function(datos) {
				
				// Vacio el carousel
				for(var i = 0; i < numItems; i++){
					$(".owl-carousel").trigger('remove.owl.carousel', i);
				}
				
				// Agrego los nuevos items
				for(var i in datos){
					var nuevoItem = '<div>' +
				  	 '<div class="thumbnail" role="button">' +
				      '<img src="../../img/aula_1.jpg"/>' +
				       '<h5 class="text-center">' + datos[i].nombreEspacio + '</h5> ' +
				       '<input type="hidden" value="' + datos[i].id + '" />' +
				      '</div>' +
				    '</div>';
					
					$(".owl-carousel").trigger('add.owl.carousel',nuevoItem);
				}
				
				// Actualiza los cambios
				$(".owl-carousel").trigger('refresh.owl.carousel');
				// Actualizo la cantidad de items
				numItems = datos.length;
				
				// Agrego el click event a cada item
				addItemClickEvent(reqHeaders);
				
				// Marco el primer item como seleccionado
				checkFirstItem();
				
				//Actualizar la info del item seleccionado
				getEspacioById(datos[0].id, reqHeaders);
			},    
		    error : function(xhr, status) {
		        alert('Disculpe, existió un problema');
		    }
		});
		
	});
	

	 
 });




function getEspacioById(idEspacio, reqHeaders){
	$.ajax({
		url: '/reservas/espacio/' + idEspacio,
		type: 'GET',
		contentType: 'application/json',
		success : function(dato) {
			
			$("#imgEspacio").empty();
			
			var imgEspacio = '<h4>'+ dato.nombreEspacio +'</h4>' +
			             '<img class="img-responsive" src="../../img/aula_1.jpg" />';
			
			$("#imgEspacio").append(imgEspacio);
			
			$("#carEspacio").empty();
			
			var tiene = '<i class="zmdi zmdi-check zmdi-hc-lg"> </i>';
			var noTiene = '<i class="zmdi zmdi-close zmdi-hc-lg"> </i>';
			
			var carEspacio = '<tr>' +
				  	         '<td><i class="zmdi zmdi-mic zmdi-hc-lg"> </i> Micrófono</td>' +
				  	         '<td>'+ (dato.microfono ? tiene : noTiene) +'</td>' +
				             '</tr>' +
				             '<tr>' +
				             '<td><i class="zmdi zmdi-laptop-chromebook zmdi-hc-lg"> </i> Ordenador</td>' +
				             '<td><i class="zmdi zmdi-close zmdi-hc-lg"> </i></td>' +
				             '</tr>' +
				             '<tr>' +
				             '<td><i class="zmdi zmdi-accounts zmdi-hc-lg"> </i> Aforo</td>' +
				  	         '<td>'+ dato.capacidad +'</td>' +
				  	         '</tr>' +
				             '<tr>' +
				  	         '<td>  <i class="zmdi zmdi-camera-alt zmdi-hc-lg"></i> Proyector</td>' +
				             '<td>'+ (dato.proyector ? tiene : noTiene) +'</td>' +
				             '</tr>';
				
			$("#carEspacio").append(carEspacio);
			
		},    
	    error : function(xhr, status) {
	        alert('No se pudo obtener el espacio');
	    }
	});
	

}

function addItemClickEvent(reqHeaders){
	$('.thumbnail').on('click', function(event){
 	    var $this = $(this);
 	    var idEspacio = $this.find('input').val();
 	    // Actualizo la url del boton siguiente
 	    var link = 'espacio/' + idEspacio;
		$("#espacio_link").attr("href",link);
		
 	    // Desmarco todos los items
 	   $(".owl-carousel div").each(function(){
			$(this).removeClass('clicked');
		});
 	    
 	    // Marco el seleccionado
 	    $this.addClass('clicked');
 	    
 	    //LLamada ajax para actualizar los datos
 	    getEspacioById(idEspacio, reqHeaders);
 	    
 	  });
}

function checkFirstItem(){
	$(".thumbnail:first").addClass('clicked');
}
