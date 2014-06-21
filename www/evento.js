var API_BASE_URL = "http://localhost:8080/AnakinKarts-api";
var lastFilename;




$(document).ready(function(){//Justo al cargarse la pagina
	console.log("HOLA, Aqui tines un evento!");
	getEventos();
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



$("#button_get_invitaciones").click(function(e) {
	e.preventDefault();
	getInvitaciones();
	console.log("Dintrissim");
});






$("#button_create").click(function(e) {//No funciona el create
	e.preventDefault();
	
	//var newEvent ='{"organizador":'+'"'+$("#organizator").val()+'"'+',"pista":'+'"'+$('input:radio[name=opciones]:checked').val()+'"'+',"fecha":'+'"'+$("#date").val()+'"'+',"numpersonas":'+'"'+$("#num_players").val()+'"'+'}';
	
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
	createInvite1(newInvite,  inv1);
	createInvite2(newInvite,  inv2);
	createInvite3(newInvite,  inv3);
	createInvite4(newInvite,  inv4);
	createInvite5(newInvite,  inv5);
	createInvite6(newInvite,  inv6);
});


$("#button_modify").click(function(e) {
	e.preventDefault();
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

		
		
		
		
		


	

		
	

});


function getEventos(){//Aun no se puee comprobar el getEventos
	var url= API_BASE_URL+'/events';
	
	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		contentType: 'application/vnd.AnakinKarts.api.evento+json'
	})
	.done(function (data, status, jqxhr) {
		console.log("Eventos Cargdos");
	})
    .fail(function (jqXHR, textStatus) {
		console.log("Fallo al cargar eventos");
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


function createInvite1(newInvite, inv1){
	
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
		alert("Evento creado!!!!");  
		
	}).fail(function(jqXHR, textStatus) {
		alert("("+textStatus+")"+"Fallo al invitar"); 
	});


}

function createInvite2(newInvite, inv2){
	
	
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
		alert("Evento creado!!!!");  
		
	}).fail(function(jqXHR, textStatus) {
		alert("("+textStatus+")"+"Fallo al invitar"); 
	});


}
function createInvite3(newInvite, inv3){
	
	
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
		alert("Evento creado!!!!");  
		
	}).fail(function(jqXHR, textStatus) {
		alert("("+textStatus+")"+"Fallo al invitar"); 
	});


}
function createInvite4(newInvite, inv4){
	

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
		alert("Evento creado!!!!");  
		
	}).fail(function(jqXHR, textStatus) {
		alert("("+textStatus+")"+"Fallo al invitar"); 
	});


}
function createInvite5(newInvite, inv5){
	

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
		alert("Evento creado!!!!");  
		
	}).fail(function(jqXHR, textStatus) {
		alert("("+textStatus+")"+"Fallo al invitar"); 
	});


}
function createInvite6(newInvite, inv6){
	
	
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
		alert("Evento creado!!!!");  
		
	}).fail(function(jqXHR, textStatus) {
		alert("("+textStatus+")"+"Fallo al invitar"); 
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
		alert("Evento Modificado!!!!");  
		
	}).fail(function(jqXHR, textStatus) {
		alert("("+textStatus+")"+"Fallo al modificar Evento"); 
	});


}




function getInvitaciones() {
	
	var url= API_BASE_URL+'/events/invitacion';

	$("#get_invitaciones_result").text('');
	
	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		contentType:'application/vnd.AnakinKarts.api.invitacion+json',
		dataType : 'json',
	}).done(function(data, status, jqxhr) {
				var invitacion = data;
				$.each(invitacion, function(i, v) {
					var invitacion = v;

					
					//$("#get_invitaciones").append(invitacion.nombreevento + '<br>');
					//$("#get_invitaciones").append(invitacion.nombre + '<br>');
					//$("#get_invitaciones").append(invitacion.invitacion + '<br>');
					$("#get_invitaciones").append(invitacion.username + '<br>');
					$('<div class="alert alert-danger"> <strong>PUUUUUUM!</strong> Everybody fucking jump!!!!! </div>').appendTo($("#get_invitaciones_result"));
				
				});
				
				
				

			}).fail(function() {
				$('<div class="alert alert-danger"> <strong>Oh!</strong> No tienes invitaciones pendientes </div>').appendTo($("#get_invitaciones_result"));
	});


}