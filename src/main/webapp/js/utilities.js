function redondeoMinutos(minutos){
	var m = minutos;
	var aux = m % 10;
	if(aux > 0 && aux < 6)
		m = m - aux;
	else
		m = m + (10-aux);
	
	return m;
}