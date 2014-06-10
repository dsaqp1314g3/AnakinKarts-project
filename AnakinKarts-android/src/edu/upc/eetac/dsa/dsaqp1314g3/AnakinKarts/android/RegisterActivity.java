package edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.android;


import org.json.JSONException;
import org.json.JSONObject;

import edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.android.AnakinKartsMainActivity.*;
import edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.android.model.AnakinAndroidApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity
{
private class registertask extends AsyncTask <String, Void, String> {
	
	String name1;
		@Override
		protected String doInBackground(String... params) {
		
		AnakinAndroidApi api = new AnakinAndroidApi();
			 name1 = params[0];
			String username1 = params[1];
			String password1 = params[2];
			String email1 = params[3];
			
			JSONObject user = new JSONObject();
			try {
				user.put("name",name1 );
				user.put("username", username1);
				user.put("userpass", password1);
				user.put("email", email1);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			String resp = api.registerUser(user);
		
			
			
			System.out.println("3");
			//System.out.println(resposta);
			return resp;
		}

		@Override
		protected void onPostExecute(String result) {
			
			
			
			
			if (result.equals("okey"))
			{
				Toast toast = Toast.makeText(getApplicationContext()," Bienvenido " + name1, 
						   Toast.LENGTH_LONG);
				toast.show();
				Intent intent = new Intent (getApplicationContext(), HomeActivity.class);
				intent.putExtra("username", name1);
				
				startActivity(intent);
				
				
			}
		}
		
	}
	
	private final static String TAG = AnakinKartsMainActivity.class.getName();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
 
		SharedPreferences prefs = getSharedPreferences("AnakinKarts-api",
				Context.MODE_PRIVATE);
		
		setContentView(R.layout.register);
	}
 

	
	
	public void register(View v) {
		EditText etUsername = (EditText) findViewById(R.id.username);
		EditText etPassword = (EditText) findViewById(R.id.password);
		EditText etEmail= (EditText) findViewById(R.id.email);
		EditText etName = (EditText) findViewById(R.id.name);
		 
		String username = etUsername.getText().toString();
		String password = etPassword.getText().toString();
		String name = etEmail.getText().toString();
		String email = etName.getText().toString();
		if (username.equals("")|| password.equals("")|| name.equals("") || email.equals("") )
		{
		
			Toast toast = Toast.makeText(getApplicationContext(),"Debe rellenar todos los campos", 
					   Toast.LENGTH_LONG);
			toast.show();
		
		}
		else
		{
			
			(new registertask()).execute(name, username, password, email);
			
		}
		
	}
 	
 

}
