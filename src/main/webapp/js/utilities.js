function redondeoMinutos(minutos){
	var m = minutos;
	var aux = m % 10;
	if(aux > 0 && aux < 6)
		m = m - aux;
	else
		m = m + (10-aux);
	
	return m;
}

function desmarcarCkecks(){
	var dow = ["L","M","X","J","V","S","D"];
	for(var i in dow){
		$("#check" + dow[i]).prop("checked",false);		 					
	}
}

function marcarChecks(array){
	for(var i in array){
		$("#check" + array[i]).prop("checked",true);		 					
	}
}

function daysChecked(){
	var dow = ["L","M","X","J","V","S","D"];
	var c = [];
	for(var i in dow){
		if($("#check" + dow[i]).is(':checked')){
			c.push(dow[i]);
		}
	}	
    return c.join(",");
}

function numeroSemana(letraDia){
	switch(letraDia){
		case "L": return 1; 
		case "M": return 2; 
		case "X": return 3; 
		case "J": return 4; 
		case "V": return 5; 
		case "S": return 6; 
		default: return 7;
	}
}

function letraSemana(numDia){
	switch(numDia){
		case 1: return "L"; 
		case 2: return "M"; 
		case 3: return "X"; 
		case 4: return "J"; 
		case 5: return "V"; 
		case 6: return "S"; 
		default: return "D";
	}
}
		 	

	 	