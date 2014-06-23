var API_BASE_URL = "http://localhost:8080/AnakinKarts-api"

$("#button_signin").click(function(e) {
	
	var usernamecok=$.cookie('username');
	var userpasscok=$.cookie('userpass');
	
	console.log("miramos las cookies al cargar pagina "+ usernamecok+" "+userpasscok);
	
	if($("#inputUsernamel3").val()=="" || $("#inputPassword3").val()==""){
		alert("No has rellenado los campos para autenticarte"); 
	}else{
		
		if(usernamecok==null && userpasscok==null){
		
			var credenciales= {
				"username":$("#inputUsernamel3").val(),
				"userpass":$("#inputPassword3").val()
			}
			Login(credenciales);
		}else{
			alert("Ya estas logeado"); 
		}
	}

});

$("#button_signout").click(function(e) {//Lo que hacemos es borrar las cookies
	
	//console.log("Miramos el valor de las cookies: "+usernamecok+" "+userpasscok);
	
	$.removeCookie("username"); 
	$.removeCookie("userpass");	
	//console.log("Miramos el valor de las cookies: "+usernamecok+" "+userpasscok);

});


$("#button_register").click(function(e) {
	
	if($("#username").val()=="" || $("#password").val()=="" || $("#email").val()=="" || $("#name").val()==""){
		alert("No has rellenado los campos para registrarte"); 
	}else{
		
		if($("#password").val()!=$("#cpassword").val()){
			alert("Los Passwords no coinciden"); 
		}else{
		
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
		alert("Logeado!!!!");  
		
	}).fail(function(jqXHR, textStatus) {
		console.log(data);
		alert("("+textStatus+")"+"Fallo al logearte"); 
		
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
		alert("Registrado!!!!");  
		
	}).fail(function(jqXHR, textStatus) {
		console.log(data);
		alert("("+textStatus+")"+"Fallo al registrarte"); 
		
	});


}
