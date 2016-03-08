'use strict';
// Requiere que esté disponible momentjs
var es = es || {};
es.ucm = es.ucm || {};
es.ucm.fdi = es.ucm.fdi || {};
es.ucm.fdi.dateUtils = (function (undefined){
	var DATE_FORMAT = 'DD/MM/YYYY HH:mm';
	
	function toIso8601(date) {
		return moment(date, DATE_FORMAT).format();
	}
	
	function fromIso8601(date) {
		return moment(date).format(DATE_FORMAT);
	}
	
	return {
		'toIso8601' : toIso8601,
		'fromIso8601' : fromIso8601
	}
})();