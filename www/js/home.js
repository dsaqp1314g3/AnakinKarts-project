var API_BASE_URL = "http://147.83.7.157:8080/dsagrades-api/";

$("#button_signin").click(function(e) {
	e.preventDefault();
	
	var login = new Object();
	login.username=$("#inputUsernamel3").val();
	login.userpass=$("#inputPassword3").val();
	Login(login);
});

function Login(login){
	var url= API_BASE_URL+'/users/login';
	var data = JSON.stringify(login);
	
	$.ajax({
		url:url,
		type:'POST',
		crossDomain: true,
		dataType:'json',
		data: data,
	}).done(function(data, status, jqxhr) {
				var info= data;
		$("#button_signin").popover({title: 'Bien!', content: "Has sido logeado!"});  

	}).fail(function() {
		$("#button_signin").popover({title: 'Error', content: "Prueba otra vez"});  
	});


}