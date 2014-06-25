var API_BASE_URL = "http://147.83.7.157:8080/AnakinKarts-api";


$(document).ready(function(){//Justo al cargarse la pagina
	console.log("HOLA!");

		var organizador_alquiler=$.cookie('alquiler_organizador');
		var pista_alquiler=$.cookie('alquiler_pista');
		var fecha_alquiler=$.cookie('alquiler_fecha');
		var numpersonas_alquiler=$.cookie('alquiler_numpersonas');
		var id=$.cookie('alquilerid');
		var foto = getPicture(pista_alquiler);
		var precio_alquiler=$.cookie('alquiler_precio');




	$("#organizador_alquiler").append(organizador_alquiler+'</br>');
	$("#fecha_alquiler").append(fecha_alquiler+'</br>');
	$("#precio_alquiler").append(precio_alquiler+'</br>');
	$("#id_alquiler").append(id+'</br>');
	$("#numpersonas_alquiler").append(numpersonas_alquiler+'</br>');
	$("#foto_pista_alquiler").append('<img src="'+foto+'">');
	$("#pista_alquiler").append(pista_alquiler+'</br>');


});




$("#button_alquiler").click(function(e) {//Incompleto

	var pasar = $("#organizator").val();



	if($("#organizator").val()=="" || $("#date").val()=="" || $("#num_players").val()==""){//Comprovamos que los campos no estén vacíos
		alert("No has rellenado los campos para alquilar"); 
	}else{
	
		var RegExPatternDate = /^\d{2,4}\-\d{1,2}\-\d{1,2}$/; //2007-01-01
		var errorMessage = 'Fecha Incorrecta.';
		if ((date.value.match(RegExPatternDate)) && (date.value!='')) {//Comprovamos que el formato de fecha sea correcto
			console.log("Alquilas");
			var numpersonas= $("#num_players").val();
			var RegExPatternNum = /^(?:\+|-)?\d+$/; //Numero entero
			if(isNaN(numpersonas)==true && num_players.value.match(RegExPatternNum)){//comprovamos que el valor del campo numperdsonas se un numero
				alert("Formato en numpersonas no numerico");
			}else{
				
				if(numpersonas <=6){
					//Rellenando la variable
					var newAlquiler= {
						"organizador":$("#organizator").val(),
						"pista":$('input:radio[name=opciones]:checked').val(),
						"fecha":$("#date").val(),
						"numpersonas":$("#num_players").val() 
					}
	
					var pista= $('input:radio[name=opciones]:checked').val();

					//rellenando cookies
					var organizador=$.cookie('alquiler_organizador', $("#organizator").val());
					var pistac=$.cookie('alquiler_pista', pista);
					var fecha=$.cookie('alquiler_fecha', $("#date").val());
					var numpersonas=$.cookie('alquiler_numpersonas', $("#num_players").val());
					var precio= $.cookie('alquiler_precio', getPrice(pista));
	
					console.log(organizador);
					console.log(pista);
					console.log(fecha);
					console.log(numpersonas);
					console.log(precio);

					Alquilar(newAlquiler);
				}else{
				alert("Demasiadas personas, tiene que ser menor o igual a 6");
				}
			}
		} else {
			alert(errorMessage);
			date.focus();
		} 
		
	}


});


function Alquilar(newAlquiler){//No Funciona el create
	
	var url= API_BASE_URL+'/events/alquiler';
	var data = JSON.stringify(newAlquiler);
	
	console.log(url);

	$.ajax({
		url:url,
		type:'POST',
		crossDomain: true,
		contentType:'application/vnd.AnakinKarts.api.alquiler+json',		
		data: data
	}).done(function(data, status, jqxhr) {
		var factura= data				
		console.log(factura);
		alert("Alquiler finalizado"); 					
		var alquilerid= $.cookie('alquilerid', factura.alquilerid);	

		window.location.replace("/prueba.html");
 
		
	}).fail(function(jqXHR, textStatus) {
		alert("("+textStatus+")"+"Fallo al crear alquilar"); 
	});

}

function getPicture(pista){

		var foto= '';

		if(pista==1){
			foto= 'pista1(450).jpg';
		}
		if(pista==2){
			foto= 'pista2(450).jpg';
		}
		if(pista==3){
			foto= 'pista3(450).jpg';
		}
		if(pista==4){
			foto= 'pista4(450).jpg';
		}
	
		return foto;
		
}

function getPrice(pista){

	var precio= '';

	if(pista==1){
		price= '40E';
	}
	if(pista==2){
		price= '55E';
	}
	if(pista==3){
		price= '45E';
	}
	if(pista==4){
		price= '35E';
	}
	
	return price;
		
}

function validarFecha(fecha){

    var RegExPattern = /^\d{2,4}\-\d{1,2}\-\d{1,2}$/; //2007-01-01
    var errorMessage = 'Fecha Incorrecta.';
    if ((date.value.match(RegExPattern)) && (date.value!='')) {
        alert('Fecha Correcta'); 
    } else {
        alert(errorMessage);
        date.focus();
    } 

}
