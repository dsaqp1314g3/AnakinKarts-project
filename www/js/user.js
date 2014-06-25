var API_BASE_URL = "http://147.83.7.157:8080/AnakinKarts-api"


$(document).ready(function(){//Justo al cargarse la pagina
	
	var usernamecok=$.cookie('username');
	var userpasscok=$.cookie('userpass');
	
	console.log("Miramos cookie: "+usernamecok);
	
	
	if(usernamecok ==null && userpasscok ==null){
	
		document.getElementById('button_signout').style.display = 'none';
		document.getElementById('button_register_modal').style.display = 'block';



	}else{
	
		document.getElementById('button_signin').style.display = 'none';
		document.getElementById('button_signout').style.display = 'block';
		document.getElementById('button_register_modal').style.display = 'none';

	
	}
	
	
});



$("#button_signin").click(function(e) {
	
	var usernamecok=$.cookie('username');
	var userpasscok=$.cookie('userpass');
	
	console.log("miramos las cookies al cargar pagina "+ usernamecok+" "+userpasscok);
	
	if($("#inputUsernamel3").val()=="" || $("#inputPassword3").val()==""){
		alert("No has rellenado los campos para autenticarte"); 
	}else{
		
		if(usernamecok==null && userpasscok==null){
			var RegExPatternUsername =/^[a-zA-Z0-9]*$/;
			if(inputUsernamel3.value.match(RegExPatternUsername)){
		
					var credenciales= {
						"username":$("#inputUsernamel3").val(),
						"userpass":$("#inputPassword3").val()
					}
					Login(credenciales);
			}else{
				alert("Formato de entrada en username incorrecto");
			}
		}else{
			alert("Ya estas logeado"); 
		}
	}

});

$("#button_signout").click(function(e) {//Lo que hacemos es borrar las cookies
	
	//console.log("Miramos el valor de las cookies: "+usernamecok+" "+userpasscok);
	
	$.removeCookie("username"); 
	$.removeCookie("userpass");	
	
	location.reload();
	
	//Ocultamos la info
	document.getElementById('button_register_modal').style.display = 'block';
	document.getElementById('button_signin').style.display = 'block';
	document.getElementById('button_signout').style.display = 'hidden';
	document.getElementById('eventos_priv').style.display = 'hidden';
	document.getElementById('crear_evento').style.display = 'hidden';
	document.getElementById('modificar_evento').style.display = 'hidden';

});


$("#button_register").click(function(e) {
	
	if($("#username").val()=="" || $("#password").val()=="" || $("#email").val()=="" || $("#name").val()==""){
		alert("No has rellenado los campos obligatorios para registrarte"); 
	}else{
		
		if($("#password").val()!=$("#cpassword").val()){
			alert("Los Passwords no coinciden"); 
		}else{
			var RegExPatternUsername =/^[a-zA-Z0-9]*$/;
			if(username.value.match(RegExPatternUsername)){
				var RegExPatternEmail = /[\w-\.]{3,}@([\w-]{2,}\.)*([\w-]{2,}\.)[\w-]{2,4}/; //Comprovamos formato Email
				if(email.value.match(RegExPatternEmail)){					
						var RegExPatternPhone = /^[0-9]{2,3}-? ?[0-9]{6,7}$/; //Comprovamos formato Phone
						if(phone.value.match(RegExPatternPhone) ||  $("#phone").val()=="" ){
							var RegExPatternDir = /[a-z]/;
							if(city.value.match(RegExPatternDir) || $("#city").val()==""){//Comprovamos formato ciudad
								var RegExPatternEntero = /^(?:\+|-)?\d+$/;
								if(portal.value.match(RegExPatternEntero) || $("#portal").val()==""){//Comprovamos formato portal
									if(door.value.match(RegExPatternEntero) || $("#door").val()==""){//Comprovamos ofrmato puerta
										var RegExPatternCP= /^([1-9]{2}|[0-9][1-9]|[1-9][0-9])[0-9]{3}$/;
										if(cp.value.match(RegExPatternCP) || $("#cp").val()==""){//Comprovamos formato CP
											var newUser= {
													"username":$("#username").val(),
													"userpass":$("#password").val(),
													"email":$("#password").val(),
													"name":$("#name").val(),
													"nphone":$("#phone").val(),
													"ciudad":$("#city").val(),
													"calle":$("#street").val(),
													"numportal":$("#portal").val(),
													"piso":$("#floor").val(),
													"numpuerta":$("#door").val(),
													"cp":$("#cp").val()
											}
									
											Register(newUser);
										}else{
											alert("CP no valido");
										}
									}else{
										alert("Puerta no valido");
									}
								}else{
									alert("Portal no valido");
								}
						
							}else{
								alert("Ciudad no valida");
							}
						}else{
							alert("Numero de telefono no valido");
						}
				}else{
					alert("El formato del email no es correcto");
				}
			}else{
				alert("Formato de username incorrecto");
			}
		}
	}

});



function Login(credenciales){
	var url= API_BASE_URL+'/users/login';
	var data = JSON.stringify(credenciales);
	
	$.ajax({
		url:url,
		type:'POST',
		crossDomain: true,
		contentType:'application/vnd.AnakinKarts.api.user+json',		
		data: data,
	}).done(function(data, status, jqxhr) {
		var info= data;
		var usernamecok=$.cookie('username',info.username);
		var userpasscok=$.cookie('userpass',info.userpass);
		console.log("miramoslas cookies: User: "+usernamecok+" Pass: "+userpasscok);
		console.log(data);
		
		//Visualizamos los elementos ocultos
		document.getElementById('button_signin').style.display = 'none';
		document.getElementById('button_signout').style.display = 'block';
		document.getElementById('eventos_priv').style.display = 'block';
		document.getElementById('crear_evento').style.display = 'block';
		document.getElementById('modificar_evento').style.display = 'block';
		
		location.reload();
		alert("Bienvenido "+info.username);   
		
	}).fail(function(jqXHR, textStatus) {
		console.log(data);
		alert("Username o Userpass incorrectos"); 
		
	});

}

function Register(newUser){
	var url= API_BASE_URL+'/users/register';
	var data = JSON.stringify(newUser);
	
	$.ajax({
		url:url,
		type:'POST',
		crossDomain: true,
		contentType:'application/vnd.AnakinKarts.api.user+json',		
		data: data,
	}).done(function(data, status, jqxhr) {
		var info= data;
		var usernamecok=$.cookie('username',info.username);
		var userpasscok=$.cookie('userpass',info.userpass);
		console.log("miramoslas cookies: User: "+usernamecok+" Pass: "+userpasscok);
		console.log(info);
		console.log("Miramos las cookies: "+usernamecok+" "+userpasscok);
		
		//Visualizamos los elementos ocultos
		document.getElementById('button_signin').style.display = 'none';
		document.getElementById('button_signout').style.display = 'block';
		document.getElementById('eventos_priv').style.display = 'block';
		document.getElementById('crear_evento').style.display = 'block';
		document.getElementById('modificar_evento').style.display = 'block';
		
		location.reload();
		
		alert("Bienvenido "+info.username);  
		
	}).fail(function(jqXHR, textStatus) {
		console.log(data);
		alert("Este usuario ya existe"); 
		
	});


}
 
