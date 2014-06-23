package edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.android.model;


	import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

	import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;









import android.R.string;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.util.Log;

	public class AnakinAndroidApi {
		private static String IP="localhost";
		private static String IP2= "10.89.88.26";
		private static String IP3 = "192.168.1.139";
		private static AnakinAndroidApi instance = null;
		private URL url;
		 
		
		
		private final static String BASE_URL = "http://"+IP3+":8080/AnakinKarts-api/";
		
//		private AnakinAndroidApi(Context context) throws IOException,
//		AnakinKartsAndroidException {
//			super();
//
//			AssetManager assetManager = context.getAssets();
//			Properties config = new Properties();
//			config.load(assetManager.open("config.properties"));//carga fichero configuracion 
//			String serverAddress = config.getProperty("server.address");//obtiene los valores de es fichero
//			String serverPort = config.getProperty("server.port");
//			url = new URL("http://" + serverAddress + ":" + serverPort
//			+ "/AnakinKarts-api/"); //se qeda cn la base url esta si utilizamos hateoas nunca cambia
//
//	Log.d("LINKS", url.toString());
//	getRootAPI();
//}
//		
		
//private void getRootAPI() throws AnakinKartsAndroidException { //rea un modelo y ataka al servicio
//		//Log.d(TAG, "getRootAPI()");
//		rootAPI = new EventoRootAPI();
//		HttpURLConnection urlConnection = null;
//		try {
//			urlConnection = (HttpURLConnection) url.openConnection();
//			urlConnection.setRequestMethod("GET");
//			urlConnection.setDoInput(true);// true por defecto, significa que qiero leer
//			urlConnection.connect();
//		} catch (IOException e) {
//			throw new BookAndroidException(
//					"Can't connect to Beeter API Web Service");
//		}
// 
//		BufferedReader reader;
//		try {//lee json que le devuelve htps://localhost:8080/beeterapi
//			reader = new BufferedReader(new InputStreamReader(
//					urlConnection.getInputStream()));
//			StringBuilder sb = new StringBuilder();
//			String line = null;
//			while ((line = reader.readLine()) != null) {
//				sb.append(line);
//			}
// 
//			JSONObject jsonObject = new JSONObject(sb.toString());// aparti de un string y objeto json lo convierte
//			JSONArray jsonLinks = jsonObject.getJSONArray("links");//asi poder manipular y obtener get, arrays.. 
//			parseLinks(jsonLinks, rootAPI.getLinks());//lo proceso con el metodo priado de esta clase y lo guardas en el modelo rootAPI
//		} catch (IOException e) {
//			throw new BookAndroidException(
//					"Can't get response from Beeter API Web Service");
//		} catch (JSONException e) {
//			throw new BookAndroidException("Error parsing Beeter Root API");
//		}
// 
//	}
// 


		//		public String provaapi(String a) {
//			System.out.println("----------------API"+a);
//			
//			HttpClient httpclient = new DefaultHttpClient();
//			System.out.println(BASE_URL + "prova/"+a);
//			HttpGet httpget = new HttpGet(BASE_URL + "prova/"+a);
//			System.out.println(httpget);
//			httpget.setHeader("Content-Type", "application/json");
//		
//			try {
//				
//				HttpResponse response = httpclient.execute(httpget);
//				
//				BufferedReader reader = new BufferedReader(new InputStreamReader(
//						response.getEntity().getContent()));
//				String line = null;
//				StringBuilder sb = new StringBuilder();
//				System.out.println("13");
//				while ((line = reader.readLine()) != null)
//					sb.append(line);
//				
//				System.out.println("14");
//				String respuesta = sb.toString();
//				System.out.println("----------------hello");
//				System.out.println("----------------"+respuesta);
//				return respuesta;
//			
//			} catch (ClientProtocolException e) {
//				System.out.println("problema");
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			
//			
//			
//			
//			
//			
//			return null;
//			
//		}
	//	
		public JSONObject LoginUser(String username, String password) throws JSONException
		{
			
	        System.out.println("Login Api");
			HttpClient httpclient = new DefaultHttpClient();
			//String url = BASE_URL + 'verifyLogin?username=' + username + '&password=' + password;
			HttpGet httpget = new HttpGet(BASE_URL + "users/login?user="+username +"&pass="+password);
//			httpget.setHeader("Content-Type", "application/json");
//			httpget.setHeader("Accept", "application/json");
			//JSONArray array = null;
			JSONObject user = null;
		try {	
				
				HttpResponse response = httpclient.execute(httpget);
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(
						response.getEntity().getContent()));
				String line = null;
				StringBuilder sb = new StringBuilder();
				System.out.println("13");
				while ((line = reader.readLine()) != null)
					sb.append(line);
				
				System.out.println("14");
				String respuesta = sb.toString();
				user = new JSONObject(sb.toString());
				System.out.println("----------------hello");
				System.out.println("----------------"+respuesta);
				return user;
			
			} catch (ClientProtocolException e) {
				System.out.println("problema");
				// TODO Auto-generated catch block
				JSONObject obj = new JSONObject();
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
			
			
			
			return null;
			
		}
		
		public String registerUser(JSONObject jsonUser) {
			System.out.println(jsonUser);
			StringEntity entity = null;
			try {
				entity = new StringEntity(jsonUser.toString());
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("1");
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(BASE_URL + "/users/register");
			System.out.println("2");

			httppost.setHeader("Content-Type", "application/vnd.AnakinKarts.api.user+json");
			//httppost.setHeader("Accept", "application/json");
			httppost.setEntity(entity);
			System.out.println("3");

			HttpResponse response = null;
			try {
				response = httpclient.execute(httppost);
				System.out.println("guay");
				System.out.println(response);
			} catch (ClientProtocolException e) {
				System.out.println("noguay");
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
			return"okey" ;
			
		
		}
		
		public EventoCollectionAndroid ListEvents () throws AnakinKartsAndroidException, JSONException {
			System.out.println("api");
			EventoCollectionAndroid eventos = new EventoCollectionAndroid();
			HttpClient httpclient = new DefaultHttpClient();
			JSONArray jsonArray= null;
			JSONObject a;
			HttpGet httpget = new HttpGet(BASE_URL + "/events/android");



			httpget.setHeader("Content-Type", "application/vnd.AnakinKarts.api.evento.collection.android+json");
			System.out.println("3");

			
try {	
				
				HttpResponse response = httpclient.execute(httpget);
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(
						response.getEntity().getContent()));
				String line = null;
				StringBuilder sb = new StringBuilder();
				System.out.println("13");
				while ((line = reader.readLine()) != null)
					sb.append(line);
				
				System.out.println("14");
				String respuesta = sb.toString();
				System.out.println(respuesta);
				a= new JSONObject(sb.toString());
				//System.out.println(jsonArray);
				JSONArray jsonevents = a.getJSONArray("eventosa");
				
				for (int i = 0; i < jsonevents.length(); i++) {
					Eventoandroid event = new Eventoandroid();
					System.out.println("HOLA");
					JSONObject jsonevento = jsonevents.getJSONObject(i);// le doy valor a traves del array y lo añado a la coleccion qe es lo qe lo devuelves
					event.setNombre(jsonevento.getString("nombre"));
					System.out.println(jsonevento.getString("nombre"));

					event.setFecha(jsonevento.getString("fecha"));
					event.setEventoid(jsonevento.getInt("eventoid"));
					event.setGanador(jsonevento.getString("ganador"));
					event.setMejorvuelta(jsonevento.getInt("mejorvuelta"));
					
					eventos.getEventosa().add(event);
					
				}
			
				
				
				
				
				
				
			
			} catch (ClientProtocolException e) {
				System.out.println("problema");
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
System.out.println(eventos.getEventosa().get(0).getNombre() + eventos.getEventosa().get(1).getNombre());
return eventos ;
			
			
			
			
			
			
			
			
			
//			try {
//				response = httpclient.execute(httpget);
//				System.out.println(response.toString());
//			//	reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//				reader = new BufferedReader(new InputStreamReader((InputStream) response));
//				System.out.println(reader.toString());
//				StringBuilder sb = new StringBuilder();
//				String line = null;
//				while ((line = reader.readLine()) != null) {
//					sb.append(line);
//				}
//				System.out.println(sb.toString());
//				JSONObject jsonObject = new JSONObject(sb.toString());
//				System.out.println(jsonObject);
//				//JSONArray jsonLinks = jsonObject.getJSONArray("links");//atributoss
//				//parseLinks(jsonLinks, books.getLinks());
//	 
//				
//				JSONArray jsonEventos = jsonObject.getJSONArray("events");
//				for (int i = 0; i < jsonEventos.length(); i++) {
//					Evento evento = new Evento();
//					JSONObject jsonEvento = jsonEventos.getJSONObject(i);// le doy valor a traves del array y lo añado a la coleccion qe es lo qe lo devuelves
//					//evento.setEventoid(jsonEvento.optLong("eventoid"));
//					evento.setNombre(jsonEvento.getString ("nombre"));
//					evento.setFecha(jsonEvento.getString("fecha"));
//					
//					System.out.println("hola1");
//					eventos.getEventos().add(evento);
//
//					System.out.println("hola2");
//				}
//			} catch (IOException e) {
//				throw new AnakinKartsAndroidException(
//						"Can't get response from Anakin API Web Service");
//			} catch (JSONException e) {
//				throw new AnakinKartsAndroidException("Error parsing");
//			}
//			
//			return eventos;
			
		
		}
//		public final static AnakinAndroidApi getInstance(Context context)
//				throws AnakinKartsAndroidException {
//			if (instance == null)
//				try {
//					instance = new AnakinAndroidApi(context);//context es la actividad, para recuperar valores del fichero conf.
//				} catch (IOException e) {
//					throw new AnakinKartsAndroidException(
//							"Can't load configuration file");
//				}
//			return instance;
//		}
		
		public EventoCollectionAndroid ListMyEvents (String username) throws AnakinKartsAndroidException, JSONException {
			System.out.println("api");
			EventoCollectionAndroid eventos = new EventoCollectionAndroid();
			HttpClient httpclient = new DefaultHttpClient();
			JSONArray jsonArray= null;
			JSONObject a;
			HttpGet httpget = new HttpGet(BASE_URL + "/events/android/" + username);



			httpget.setHeader("Content-Type", "application/vnd.AnakinKarts.api.evento.collection.android+json");
			System.out.println("3");

			
try {	
				
				HttpResponse response = httpclient.execute(httpget);
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(
						response.getEntity().getContent()));
				String line = null;
				StringBuilder sb = new StringBuilder();
				System.out.println("13");
				while ((line = reader.readLine()) != null)
					sb.append(line);
				
				System.out.println("14");
				String respuesta = sb.toString();
				System.out.println(respuesta);
				a= new JSONObject(sb.toString());
				//System.out.println(jsonArray);
				JSONArray jsonevents = a.getJSONArray("eventosa");
				
				for (int i = 0; i < jsonevents.length(); i++) {
					Eventoandroid event = new Eventoandroid();
					System.out.println("HOLA");
					JSONObject jsonevento = jsonevents.getJSONObject(i);// le doy valor a traves del array y lo añado a la coleccion qe es lo qe lo devuelves
					event.setNombre(jsonevento.getString("nombre"));
					System.out.println(jsonevento.getString("nombre"));

					event.setFecha(jsonevento.getString("fecha"));
					event.setEventoid(jsonevento.getInt("eventoid"));
					event.setGanador(jsonevento.getString("ganador"));
					event.setMejorvuelta(jsonevento.getInt("mejorvuelta"));
					
					eventos.getEventosa().add(event);
					
					
					
					
					
					
					
				}
			
				
				
				
				
				
				
			
			} catch (ClientProtocolException e) {
				System.out.println("problema");
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(eventos.getEventosa().get(0).getNombre() + eventos.getEventosa().get(1).getNombre());
return eventos ;
		
}}