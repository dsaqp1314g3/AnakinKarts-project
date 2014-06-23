var API_BASE_URL = "http://localhost:8080/AnakinKarts-api";
var URL = 'http://localhost:8080/AnakinKarts-api/images';
var lastFilename;

$(document).ready(function(){//Justo al cargarse la pagina
	console.log("HOLA, Aqui tines un evento!");
	getEventosPub();
	getEventosPriv();
});

$("#button_get_invitaciones").click(function(e) {//No funciona el create
	e.preventDefault();
	getInvitaciones();
	console.log("Dentro");
});


$('form').submit(function(e){
	e.preventDefault();
	$('progress').toggle();

	var formData = new FormData($('form')[0]);

	$.ajax({
		url: URL,
		type: 'POST',
		xhr: function() {  
	    	var myXhr = $.ajaxSettings.xhr();
	        if(myXhr.upload){ 
	            myXhr.upload.addEventListener('progress',progressHandlingFunction, false); // For handling the progress of the upload
	        }
	        return myXhr;
        },
		crossDomain : true,
		data: formData,
		cache: false,
		contentType: false,
        processData: false
	})
	.done(function (data, status, jqxhr) {
		var response = $.parseJSON(jqxhr.responseText);
		lastFilename = response.filename;
		$('#uploadedImage').attr('src', response.imageURL);
		$('progress').toggle();
		$('form')[0].reset();
	})
    .fail(function (jqXHR, textStatus) {
    	alert("KO");
		console.log(textStatus);
	});
});

function progressHandlingFunction(e){
    if(e.lengthComputable){
        $('progress').attr({value:e.loaded,max:e.total});
    }
}

$('#myCarousel').carousel({
  interval:false // remove interval for manual sliding
});

$('#uploadedImage').click(function(e){
	e.preventDefault();
	$.ajax({
		url: URL,
		type: 'GET',
		crossDomain : true
	})
	.done(function (data, status, jqxhr) {
		var response = $.parseJSON(jqxhr.responseText);
		$('.carousel-inner').empty();
		$.each(response.images, function(k,v){
			if(lastFilename == response.images[k].filename)
				$('.carousel-inner').append('<div class="item active"><img class="imgcenter" src="'+response.images[k].imageURL+'" class="img-responsive"><div class="carousel-caption"><h2 align="center">'+response.images[k].title+'</h2></div></div>');
			else
				$('.carousel-inner').append('<div class="item"><img class="imgcenter" src="'+response.images[k].imageURL+'" class="img-responsive"><div class="carousel-caption"><h2 align="center">'+response.images[k].title+'</h2></div></div>');
		});

		$('#carousel-modal').modal('toggle');
	})
    .fail(function (jqXHR, textStatus) {
    	alert("KO");
		console.log(textStatus);
	});
});




$("#button_create").click(function(e) {//No funciona el create
	e.preventDefault();
	
	//var newEvent ='{"organizador":'+'"'+$("#organizator").val()+'"'+',"pista":'+'"'+$('input:radio[name=opciones]:checked').val()+'"'+',"fecha":'+'"'+$("#date").val()+'"'+',"numpersonas":'+'"'+$("#num_players").val()+'"'+'}';
	
	var newEvent= {
			"nombre":$("nombre").val(),
		"organizador":$("#organizator").val(),
		"pista":$('input:radio[name=opciones]:checked').val(),
		"fecha":$("#date").val(),
		"numpersonas":$("#num_players").val() 
	}
	var newInvite= {
			"nombre":$("nombre").val(),
			"invitacion1":$("#invite1").val(),
			"invitacion2":$("#invite2").val(),
			"invitacion3":$("#invite3").val(),
			"invitacion4":$("#invite4").val(),
			"invitacion5":$("#invite5").val(),
			"invitacion6":$("#invite6").val()
			
	}
	var inv1 = $("#invite1").val();
	var inv2 = $("#invite2").val();
	var inv3 = $("#invite3").val();
	var inv4 = $("#invite4").val();
	var inv5 = $("#invite5").val();
	var inv6 = $("#invite6").val();
	console.log(newEvent);
	console.log(newInvite);
	createEvent(newEvent);
	createInvite1(newInvite, inv1);
	createInvite2(newInvite, inv2);
	createInvite3(newInvite, inv3);
	createInvite4(newInvite, inv4);
	createInvite5(newInvite, inv5);
	createInvite6(newInvite, inv6);
});

$("#button_modify").click(function(e) {//No funciona el create
	e.preventDefault();
	
	
	var modEvent= {
			"nombre":$("nombreevento").val(),
		"organizador":$("#organizatorevento").val(),
		"ganador":$("ganador").val(),
		"mejorvuelta":$("#mejorvuelta").val(),
		
	}
	var nom = $("#nombreevento").val();
	
	console.log(modEvent);
	console.log(nom);
	modifyEvent(modEvent, nom);
	
});

function verEvento(eventoid){
	var url= API_BASE_URL+'/events/'+eventoid;
	
	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType: 'json'
	}).done(function (data, status, jqxhr) {//eventoid, fecha, ganador, jugadores[], mejor vuelta, numpersonas, pista
		var evento = data;

		


		var valor  = $.cookie("name", "valor");
		var organizador=$.cookie('organizador', evento.organizador);
		var eventoid = $.cookie('eventoid', evento.eventoid);
		var fecha = $.cookie('fecha', evento.fecha);	
		var ganador = $.cookie('ganador', evento.ganador);
		var mejorvuelta = $.cookie('mejorvuelta', evento.mejorvuelta);
		var numpersonas = $.cookie('numpersonas', evento.numpersonas);
		var pista = $.cookie('pista', evento.pista);
		var jugadores = $.cookie('jugadores', evento.jugadores);

		console.log($.cookie());
		console.log(valor);
		console.log(organizador);
		console.log(eventoid);
		console.log(fecha);
		console.log(ganador);
		console.log(mejorvuelta);
		console.log(numpersonas);
		console.log(pista);
		console.log(jugadores);
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


function createInvite1(newInvite, inv1){//No Funciona el create
	
	var url= API_BASE_URL+'/events/invitacion/' + inv1;
	var data = JSON.stringify(newInvite);
	
	$.ajax({
		url:url,
		type:'POST',
		crossDomain: true,
		contentType:'application/vnd.AnakinKarts.api.invitacion+json',		
		data: data,
	}).done(function(data, status, jqxhr) {
				var info= data;
		alert("Jugadores Invitados!!!!");  
		
	}).fail(function(jqXHR, textStatus) {
		alert("("+textStatus+")"+"Fallo al invitar"); 
	});


}

function createInvite2(newInvite, inv2){//No Funciona el create
	
	var url= API_BASE_URL+'/events/invitacion/' + inv2;
	var data = JSON.stringify(newInvite);
	
	$.ajax({
		url:url,
		type:'POST',
		crossDomain: true,
		contentType:'application/vnd.AnakinKarts.api.invitacion+json',		
		data: data,
	}).done(function(data, status, jqxhr) {
				var info= data;
	
		
	}).fail(function(jqXHR, textStatus) {
		 
	});


}

function createInvite3(newInvite, inv3){//No Funciona el create
	
	var url= API_BASE_URL+'/events/invitacion/' + inv3;
	var data = JSON.stringify(newInvite);
	
	$.ajax({
		url:url,
		type:'POST',
		crossDomain: true,
		contentType:'application/vnd.AnakinKarts.api.invitacion+json',		
		data: data,
	}).done(function(data, status, jqxhr) {
				var info= data;
	
		
	}).fail(function(jqXHR, textStatus) {
		 
	});


}

function createInvite4(newInvite, inv4){//No Funciona el create
	
	var url= API_BASE_URL+'/events/invitacion/' + inv4;
	var data = JSON.stringify(newInvite);
	
	$.ajax({
		url:url,
		type:'POST',
		crossDomain: true,
		contentType:'application/vnd.AnakinKarts.api.invitacion+json',		
		data: data,
	}).done(function(data, status, jqxhr) {
				var info= data;
	
		
	}).fail(function(jqXHR, textStatus) {
		 
	});


}
function createInvite5(newInvite, inv5){//No Funciona el create
	
	var url= API_BASE_URL+'/events/invitacion/' + inv5;
	var data = JSON.stringify(newInvite);
	
	$.ajax({
		url:url,
		type:'POST',
		crossDomain: true,
		contentType:'application/vnd.AnakinKarts.api.invitacion+json',		
		data: data,
	}).done(function(data, status, jqxhr) {
				var info= data;
	
		
	}).fail(function(jqXHR, textStatus) {
		 
	});


}
function createInvite6(newInvite, inv6){//No Funciona el create
	
	var url= API_BASE_URL+'/events/invitacion/' + inv6;
	var data = JSON.stringify(newInvite);
	
	$.ajax({
		url:url,
		type:'POST',
		crossDomain: true,
		contentType:'application/vnd.AnakinKarts.api.invitacion+json',		
		data: data,
	}).done(function(data, status, jqxhr) {
				var info= data;
	
		
	}).fail(function(jqXHR, textStatus) {
		 
	});


}

function modifyEvent(modEvent, nom){//No Funciona el create
	
	var url= API_BASE_URL+'/events/' + nom;
	var data = JSON.stringify(modEvent);
	
	$.ajax({
		url:url,
		type:'PUT',
		crossDomain: true,
		contentType:'application/vnd.AnakinKarts.api.evento+json',		
		data: data,
	}).done(function(data, status, jqxhr) {
				var info= data;
				alert("Evento Modificado!!!!!");
	
		
	}).fail(function(jqXHR, textStatus) {
		alert("("+textStatus+")"+"Fallo al modificar Evento");
	});


}

function getInvitaciones(){//No Funciona el create
	
	var url= API_BASE_URL+'/events/invitacion';
	$("#get_invitaciones_result").Text('');
	
	
	$.ajax({
		url:url,
		type:'GET',
		crossDomain: true,
		contentType:'application/vnd.AnakinKarts.api.invitacion+json',		
		dataType: 'json',
	}).done(function(data, status, jqxhr) {
				var invitaciones= data;
				$.each(invitaciones, function(i, v)){
					var invitacion = v;
					console.log(invitacion);
					$("#get_invitaciones").append(invitacion + '<br>');
					
					
				});
			
	
		
	}).fail(function() {
		$('<div class="alert alert-danger"> <strong>Oh!</strong> No tienes invitaciones pendientes </div>').appendTo($("#get_invitaciones_result"));
	});


}

