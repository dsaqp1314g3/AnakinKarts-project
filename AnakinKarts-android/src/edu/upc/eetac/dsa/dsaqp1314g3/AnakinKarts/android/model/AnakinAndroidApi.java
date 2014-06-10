package edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.android.model;


	import java.io.BufferedReader;
	import java.io.IOException;
	import java.io.InputStreamReader;
	import java.io.UnsupportedEncodingException;

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
	import android.graphics.Bitmap;
	import android.util.Log;

	public class AnakinAndroidApi {
		private static String IP="localhost";
		private static String IP2= "10.189.126.240";
		private static String IP3 = "192.168.1.142";
		 
		
		private final static String BASE_URL = "http://"+IP3+":8080/AnakinKarts-api/";
		
		
		
		
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
		
}