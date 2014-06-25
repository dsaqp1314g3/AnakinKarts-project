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
		private static String IP2= "10.89.89.42";
		private static String IP3 = "192.168.1.138";
		private static String IP4 = "147.83.7.157";
		private static AnakinAndroidApi instance = null;
		private URL url;
		 
		
		
		private final static String BASE_URL = "http://"+IP4+":8080/AnakinKarts-api/";
		

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
				if (response.getStatusLine().getStatusCode() != 200)
				 return null ;
				else
				return "okey";
			} catch (ClientProtocolException e) {
				System.out.println("noguay");
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			return null;
			
		
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
				//return eventos;//********
			
				
				
				
				
				
				
			
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
			
			
			
			
		
			
			
			
		}
		
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
			
				
				return eventos;
				
				
				
				
			
			} catch (ClientProtocolException e) {
				System.out.println("problema");
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(eventos.getEventosa().get(0).getNombre() + eventos.getEventosa().get(1).getNombre());
return null ;
		
}}