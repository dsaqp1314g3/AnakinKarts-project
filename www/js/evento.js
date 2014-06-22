var API_BASE_URL = "http://localhost:8080/AnakinKarts-api"


$(document).ready(function(){//Justo al cargarse la pagina
	console.log("HOLA, Aqui tines un evento!");
	getEventosPub();
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


function verEvento(eventoid){
	var url= API_BASE_URL+'/events/'+eventoid;
	
	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType: 'json'
	})
	.done(function (data, status, jqxhr) {//eventoid, fecha, ganador, jugadores[], mejor vuelta, numpersonas, pista
		var evento = data;

		


		var valor  = $.cookie("name", "valor");
		var organizador=$.cookie('organizador', evento.organizador);
		var eventoid = $.cookie('eventoid', evento.eventoid);
		var fecha = $.cookie('fecha', evento.fecha);	
		var ganador = $.cookie('ganador', evento.ganador);
		var mejorvuelta = $.cookie('mejorvuelta', evento.mejorvuelta);
		var numpersonas = $.cookie('numpersonas', evento.numpersonas);
		var pista = $.cookie('pista', evento.pista);

		console.log($.cookie());
		console.log(valor);
		console.log(organizador);
		console.log(eventoid);
		console.log(fecha);
		console.log(ganador);
		console.log(mejorvuelta);
		console.log(numpersonas);
		console.log(pista);
		console.log("Eventos CargdosPUB");


		console.log("Se puede ver el evento");

		window.location.replace("/anakin_evento.html");
	})
    .fail(function (jqXHR, textStatus) {
		console.log("Fallo al cargar evento");
	});

}

function getEventosPub(){//Aun no se puee comprobar el getEventos
	var url= API_BASE_URL+'/events';
	
	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType: 'json'
	})
	.done(function (data, status, jqxhr) {
		 var eventos = data;
		 
		 $.each(eventos, function (i,v){
			var evento = v;
			console.log(evento);
		 
			$.each(evento, function (y,w){
			
				var info= w;
				console.log(info);
				console.log(info.pista);
				var foto = getPicture(info.pista);
				console.log(foto);
				$("#get_events_pub").append('<div class="row">');
				$("#get_events_pub").append('<div class="col-sm-12">');
				$("#get_events_pub").append('"<div class="panel panel-default" id="panel_pub_'+y+'">');
				$("#get_events_pub").append('<div class="col-md-6">');
				$("#get_events_pub").append('<strong> Data: </strong> ' + info.fecha + '<br>');
				$("#get_events_pub").append('<strong> Numpers: </strong> ' + info.numpersonas + '<br>');
				$("#get_events_pub").append('<strong> EventoID: </strong> ' + info.eventoid + '<br>');
				$("#get_events_pub").append('</div>');
				$("#get_events_pub").append('<div class="col-md-6">');
				$("#get_events_pub").append('<img src="'+foto+'">');
				$("#get_events_pub").append('</div>');
				$("#get_events_pub").append('<button type="button" class="btn btn-primary" id=button_pub onClick="verEvento('+info.eventoid+')" >Ver</button>');				
				$("#get_events_pub").append('</div>');
				$("#get_events_pub").append('</div>');
				console.log("vector y = " +y)

						

			});	
		});	

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
		 var eventos = data;
		 
		 $.each(eventos, function (i,v){
			var evento = v;
			console.log(evento);
		 
			$.each(evento, function (y,w){
			
				var info= w;
				console.log(info);
				console.log(info.pista);
				var foto = getPicture(info.pista);
				console.log(foto);
				$("#get_events_priv").append('<div class="row">');
				$("#get_events_priv").append('<div class="col-sm-12">');
				$("#get_events_priv").append('<div class="panel panel-default id=panel_priv_"'+y+'">');
				$("#get_events_priv").append('<div class="col-md-6">');
				$("#get_events_priv").append('<strong> Data: </strong> ' + info.fecha + '<br>');
				$("#get_events_priv").append('<strong> Numpers: </strong> ' + info.numpersonas + '<br>');
				$("#get_events_priv").append('<strong> EventoID: </strong> ' + info.eventoid + '<br>');
				$("#get_events_priv").append('</div>');
				$("#get_events_priv").append('<div class="col-md-6">');
				$("#get_events_priv").append('<img src="'+foto+'">');
				$("#get_events_priv").append('</div>');
				$("#get_events_priv").append('<button type="button" class="btn btn-primary" id=button_priv onClick="verEvento('+info.eventoid+')" >Ver</button>');				
				$("#get_events_priv").append('</div>');
				$("#get_events_priv").append('</div>');
				console.log("vector y = " +y)
			});	
		});	
	
		console.log("Eventos CargdosPRIV");
		
	})
    .fail(function (jqXHR, textStatus) {
		console.log("Fallo al cargar eventosPRIV");
	});
	

}

function getPicture(pista){

	var foto= '';

	if(pista==1){
		foto= 'pista1.jpg';
	}
	if(pista==2){
		foto= 'pista2.jpg';
	}
	if(pista==3){
		foto= 'pista3.jpg';
	}
	if(pista==4){
		foto= 'pista4.jpg';
	}
	
	return foto;
		
	

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