var URL = 'http://147.83.7.157:8080/AnakinKarts-api/images';


$(document).ready(function(){//Justo al cargarse la pagina
	console.log("HOLA, Aqui tines un evento!");

	var eventoid=$.cookie('eventoid');
	console.log(eventoid);

	mostrarEvento();
});

function mostrarEvento(){// fecha_evento ganador_evento mejorvuelta_evento numpersonas_evento

	var organizador= $.cookie('organizador');
	var fecha= $.cookie('fecha');
	var ganador= $.cookie('ganador');
	var mejorvuelta= $.cookie('mejorvuelta');
	var numpersonas= $.cookie('numpersonas');
	var pista= $.cookie('pista');
	var foto = getPicture(pista);
	var jugadores= $.cookie('jugadores');
	var listajugadores= jugadores.split("%2C");
	var nombreevento=$.cookie('nombreevento');
	console.log (listajugadores);
	console.log(nombreevento);




	$("#organizador_evento").append('<strong>'+organizador+'</strong>');
	$("#fecha_evento").append('<strong>'+fecha+'</strong>');
	$("#ganador_evento").append('<strong>'+ganador+'</strong>');
	$("#mejorvuelta_evento").append('<strong>'+mejorvuelta+'</strong>');
	$("#numpersonas_evento").append('<strong>'+numpersonas+'</strong>');
	$("#foto_pista_evento").append('<img src="'+foto+'">');
	$("#jugadores_evento").append('<strong>'+listajugadores+'</strong>');

	getPicturesByEvent(nombreevento);


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

function getPicturesByEvent(nombreevento){
	var url= URL + '/'+ nombreevento;
	
	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType: 'json'
	}).done(function (data, status, jqxhr) {
		console.log("Recivimos: ");
		console.log(data);
		
		$.each(data, function (y,w){
			var foto= w;
			console.log("Seccionamos: ");
			console.log(foto);
			$.each(foto, function (i,s){
					var elemento = s;
					console.log("Miramos más");
					console.log(elemento);
					console.log("Pillamos el nombre: "+elemento.filename);
					

					$("#fotos_evento").append('<a href="#" class="thumbnail" >');					
					$("#fotos_evento").append('<a href="'+elemento.imageURL+'">');
					$("#fotos_evento").append('<img src="'+elemento.filename+'" height="200" width="200">');
					$("#fotos_evento").append('</a>');
					$("#fotos_evento").append('</a>');					
			});
		});	
		
	})
    .fail(function (jqXHR, textStatus) {
		alert("Fallo al cargar las fotos");
	});
	


}