
var API_BASE_URL = "http://147.83.7.157:8080/AnakinKarts-api";
var URL = 'http://147.83.7.157:8080/AnakinKarts-api/images';
var lastFilename;

$(document).ready(function(){//Justo al cargarse la pagina
	
	var usernamecok=$.cookie('username');
	var userpasscok=$.cookie('userpass');
	
	console.log("Miramos cookie: "+usernamecok);

	console.log("HOLA, Aqui tines un evento!");
	getEventosPub();	
	
	
	if(usernamecok ==null && userpasscok ==null){
	
		document.getElementById('eventos_priv').style.display = 'none';
		document.getElementById('crear_evento').style.display = 'none';
		document.getElementById('modificar_evento').style.display = 'none';
	}else{
		document.getElementById('eventos_priv').style.display = 'block';
		document.getElementById('crear_evento').style.display = 'block';
		document.getElementById('modificar_evento').style.display = 'block';
		getEventosPriv(usernamecok);
	}
});

$("#button_get_invitaciones").click(function(e) {//No funciona el create
	e.preventDefault();
	getInvitaciones();
	console.log("Dentro");
});

$('form#imageForm').submit(function(e){
	e.preventDefault();
	$('progress').toggle();

	var formData = new FormData($('form#imageForm')[0]);

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
	
	var numplayers=$("#num_players").val();
	
	if(isNaN(numplayers)==true || isNaN($("#num_players").val())==true ){
		alert("El valor metido en el numplayers no es un numero!");
	}else{	
		var RegExPatternDate = /^\d{2,4}\-\d{1,2}\-\d{1,2}$/; //2007-01-01
		if(date.value.match(RegExPatternDate)){
			if(numplayers <= 6){
				if($("#nombre").val()!="" || $("#organizator").val()!="" || $("#date").val()!="" || $("#num_players").val()!=""){
						
						console.log("Nombre del evento: "+$("#nombre").val());
						var newEvent= {
							
							"nombre":$("#nombre").val(),
							"organizador":$("#organizator").val(),
							"pista":$('input:radio[name=opciones]:checked').val(),
							"fecha":$("#date").val(),
							"numpersonas":$("#num_players").val() 
						}
						var newInvite= {
								"nombre":$("#nombre").val(),
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
				}else{
					alert("Rellena todos los campos");
				}
			}else{
				alert("Demasiadas personas, como maximo 6");
			}
		}else{
			alert("Formato de Fecha incorrecta");
			date.focus();
		}
	}
});

$("#button_modify").click(function(e) {//No funciona el create
	e.preventDefault();
	
	if($("#nombreevento").val()=="" || $("#organizatorevento").val()=="" || $("#mejorvuelta").val()==""){
		alert("Rellena todos los campos para modificar evento.");
	}else{
		var modEvent= {
		
			"nombre":$("#nombreevento").val(),
			"organizador":$("#organizatorevento").val(),
			"ganador":$("#ganador").val(),
			"mejorvuelta":$("#mejorvuelta").val()
			
		}
		var nom = $("#nombreevento").val();
		
		console.log(modEvent);
		console.log(nom);
		modifyEvent(modEvent, nom);
	}
	
});

$("#button_aceptar").click(function(e) {
	e.preventDefault();
	
	
	var acpInv= {
			"nombre":$("#nombreeventoinv").val()
			
		
		
	}
	var inf={
			"nombre":$("#nombreeventoinv").val(),
			"user":$.cookie('username'),
	        "estado":$('aceptada')
			
	}
	
	console.log(acpInv);
	
	aceptarInv(acpInv, inf);
	
});

$("#button_rechazar").click(function(e) {
	e.preventDefault();
	
	
	var rechInv= {
			"nombre":$("#nombreeventoinv").val()
		
		
		
	}
	
	
	console.log(rechInv);
	
	rechazarInv(rechInv);
	
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
		var nombreevento=$.cookie('nombreevento',evento.nombre);

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
		console.log(nombreevento);
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

function getEventosPriv(usernamecok){//Aun no se puee comprobar el getEventos
	var url= API_BASE_URL+'/events/'+usernamecok+'/priv';
	console.log(url);
	
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

function getInvitaciones(){
	var usernamecok=$.cookie('username');
	console.log(usernamecok);
	var url= API_BASE_URL+'/events/invitacion/'+ usernamecok;
	console.log(url);
	//$("#get_invitaciones_result").Text('');
	
	
	$.ajax({
		url:url,
		type:'GET',
		crossDomain: true,
		contentType:'application/vnd.AnakinKarts.api.invitacion+json',		
		dataType: 'json',
	}).done(function(data, status, jqxhr) {
				var invitaciones= data;
				$.each(invitaciones, function(i, v){
					var invitacion = v;
					console.log(invitacion);
					$("#listamod").append(' <li><a href="#" id="get_invitaciones" class="icon-ok" class="icon-remove"></a><i class="icon-ok"></i><i class="icon-remove"></i>'+invitacion+'</li>') ;
					//$("#get_invitaciones").append(invitacion + '<button type="button" class="btn btn-primary" id=button_pub onClick="verEvento('+info.eventoid+')" >Ver</button>');
					//$("#get_invitaciones").append('<button type="button" class="btn btn-primary" id=button_pub onClick="verEvento('+info.eventoid+')" >Ver</button>');
					
					
				});
			
	
		
	}).fail(function() {
		$('<div class="alert alert-danger"> <strong>Oh!</strong> No tienes invitaciones pendientes </div>').appendTo($("#get_invitaciones_result"));
	});


}

function aceptarInv(acpInv){
	var usernamecok=$.cookie('username');
	console.log(usernamecok);
	console.log(acpInv);
	var url= API_BASE_URL+'/events/invitacion/'+ acpInv + '/' + usernamecok + '/aceptada';
	console.log(url);
	
	
	
	
	var data = JSON.stringify(acpInv);
	
	$.ajax({
		url:url,
		type:'PUT',
		crossDomain: true,
		contentType:'application/vnd.AnakinKarts.api.invitacion+json',		
		data: data,
	}).done(function(data, status, jqxhr) {
				var info= data;
		alert("Invitacion Aceptada!!!!");  
		
	}).fail(function(jqXHR, textStatus) {
		alert("("+textStatus+")"+"Fallo al aceptar la invitacion"); 
	});
	
	
	
	
	
	
}
function rechazarInv(rechInv, inf){
	var usernamecok=$.cookie('username');
	console.log(usernamecok);
	//var url= API_BASE_URL+'/events/invitacion/'+ rechInv + '/' + usernamecok + '/rechazada';
	var url= API_BASE_URL+'/events/invitacion/DavidWiseDJ/' + usernamecok + '/rechazada';
	console.log(url);
	
	
	
	
	var data = JSON.stringify(inf);
	
	$.ajax({
		url:url,
		type:'PUT',
		crossDomain: true,
		contentType:'application/vnd.AnakinKarts.api.invitacion+json',		
		data: data,
	}).done(function(data, status, jqxhr) {
				var info= data;
		alert("Invitacion Rechazada!!!!");  
		
	}).fail(function(jqXHR, textStatus) {
		alert("("+textStatus+")"+"Fallo al rechazar la invitacion"); 
	});
	
	
	
	
	
	
}


