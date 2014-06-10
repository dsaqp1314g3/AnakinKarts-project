var API_BASE_URL = "http://localhost:8080/AnakinKarts-api";



$(document).ready(function(){//Justo al cargarse la pagina
	console.log("HOLA, Aqui tines un evento!");
	getEventos();
	getEventosPriv();
});


$("#button_signin").click(function(e) {//Incompleto
		if($("#inputUsernamel3").val()=="" || $("#inputPassword3").val()==""){
		alert("No has rellenado los campos para autenticarte"); 
	}else{
		console.log("Te logeas");
	}

});

$("#button_register").click(function(e) {//Incompleto

});



$("#button_create").click(function(e) {//No funciona el create
	e.preventDefault();
	
	//var newEvent ='{"organizador":'+'"'+$("#organizator").val()+'"'+',"pista":'+'"'+$('input:radio[name=opciones]:checked').val()+'"'+',"fecha":'+'"'+$("#date").val()+'"'+',"numpersonas":'+'"'+$("#num_players").val()+'"'+'}';
	
	var newEvent= {
		"organizador":$("#organizator").val(),
		"pista":$('input:radio[name=opciones]:checked').val(),
		"fecha":$("#date").val(),
		"numpersonas":$("#num_players").val() 
	}
	
	console.log(newEvent);
	createEvent(newEvent);
});


function getEventos(){//Aun no se puee comprobar el getEventos
	var url= API_BASE_URL+'/events';
	
	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType: 'json'
	})
	.done(function (data, status, jqxhr) {
		console.log("Eventos CargdosPUB");
	})
    .fail(function (jqXHR, textStatus) {
		console.log("Fallo al cargar eventosPUB");
	});
	

}

function getEventosPriv(){//Aun no se puee comprobar el getEventos
	var url= API_BASE_URL+'/events/ivan/priv';
	
	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType: 'json'
	})
	.done(function (data, status, jqxhr) {
		console.log("Eventos CargdosPRIV");
	})
    .fail(function (jqXHR, textStatus) {
		console.log("Fallo al cargar eventosPRIV");
	});
	

}



function createEvent(newEvent){//No Funciona el create
	
	var url= API_BASE_URL+'/events';
	var data = JSON.stringify(newEvent);
	
	$.ajax({
		url:url,
		type:'POST',
		crossDomain: true,
		contentType:'application/vnd.AnakinKarts.api.evento+json',		
		data: data,
	}).done(function(data, status, jqxhr) {
				var info= data;
		alert("Evento creado!!!!");  
		
	}).fail(function(jqXHR, textStatus) {
		alert("("+textStatus+")"+"Fallo al crear el evento"); 
	});


}