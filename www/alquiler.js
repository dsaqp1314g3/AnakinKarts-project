var API_BASE_URL = "http://localhost:8080/AnakinKarts-api";


$("#button_alquiler").click(function(e) {//Incompleto

	var newAlquiler= {
		"organizador":$("#organizator").val(),
		"pista":$('input:radio[name=opciones]:checked').val(),
		"fecha":$("#date").val(),
		"numpersonas":$("#num_players").val() 
	}


		if($("#organizator").val()=="" || $("#date").val()=="" || $("#num_players").val()==""){
		alert("No has rellenado los campos para alquilar"); 
	}else{
		
		console.log("Alquilas");
		Alquilar(newAlquiler);
	}

});


function Alquilar(newAlquiler){//No Funciona el create
	
	var url= API_BASE_URL+'/events/alquiler';
	var data = JSON.stringify(newAlquiler);
	
	$.ajax({
		url:url,
		type:'POST',
		crossDomain: true,
		contentType:'application/vnd.AnakinKarts.api.alquiler+json',		
		data: data,
	}).done(function(data, status, jqxhr) {
				var info= data;
		alert("Alquiler finalizado");  
		
	}).fail(function(jqXHR, textStatus) {
		alert("("+textStatus+")"+"Fallo al crear alquilar"); 
	});


}