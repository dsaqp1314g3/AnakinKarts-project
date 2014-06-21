var API_BASE_URL = "http://localhost:8080/AnakinKarts-api";


$("#button_alquiler").click(function(e) {//Incompleto

	var pasar = $("#organizator").val();
	location.href = "prueba.html?variable="+pasar; //Donde pone html2.html pon el nombre o ruta del html receptor 
	
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
				var factura= data
				var pista = $('input:radio[name=opciones]:checked').val();
				
		console.log(factura);
		alert("Alquiler finalizado"); 
		
			$("factura_result").text('');
			$('<strong> Organizador: </strong> ' + factura.organizador + '<br>').appendTo($('#factura_result'));
			$('<p>').appendTo($('#factura_result'));
			$('<strong> Fecha: </strong>'+ $("#date").val() + '<br>').appendTo($('#factura_result'));
			$('<strong> Pista: </strong>'+ pista+ '<br>').appendTo($('#factura_result'));
			$('<p>').appendTo($('#factura_result'));
			$('<img src= "pista3.jpg">').appendTo($('#factura_result'));
			$('<p>').appendTo($('#factura_result'));
			$('<strong> Precio: </strong> ' + factura.precio +' Euros <br>').appendTo($('#factura_result'));
			$('<strong> ID: </strong>'+ factura.alquilerid + '<br>').appendTo($('#factura_result'));
			$('</p>').appendTo($('#factura_result'));
 
		
	}).fail(function(jqXHR, textStatus) {
		alert("("+textStatus+")"+"Fallo al crear alquilar"); 
	});


}