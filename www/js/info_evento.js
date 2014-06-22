


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
	console.log (listajugadores);




	$("#organizador_evento").append('<strong>'+organizador+'</strong>');
	$("#fecha_evento").append('<strong>'+fecha+'</strong>');
	$("#ganador_evento").append('<strong>'+ganador+'</strong>');
	$("#mejorvuelta_evento").append('<strong>'+mejorvuelta+'</strong>');
	$("#numpersonas_evento").append('<strong>'+numpersonas+'</strong>');
	$("#foto_pista_evento").append('<img src="'+foto+'">');
	$("#jugadores_evento").append('<strong>'+listajugadores+'</strong>');



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