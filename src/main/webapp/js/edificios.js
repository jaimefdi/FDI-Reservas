$(document).ready(function(){
	 	var edificio = {};
	 	var token = $("meta[name='_csrf']").attr("content");
	 	var header = $("meta[name='_csrf_header']").attr("content");
	 	var reqHeaders = [];
	 	reqHeaders[header] = token;
	 	
	 	$("li>div").click(function(){
			var idEdificio = $(this).attr("data-id");
			
			$("li>div").each(function(){
				$(this).removeClass("selectedEdif");
			});
			
			$(this).addClass("selectedEdif");
			var link = 'edificio/' + idEdificio + '/espacios';
			$("#edificio_link").attr("href",link);
		});
		
		$("#edificio_link").click(function(){
			var link = $("#edificio_link").attr('href');
			if(link == '')
			   alert("Debes seleccionar un edificio.");
			
		});
	 
	 
 });