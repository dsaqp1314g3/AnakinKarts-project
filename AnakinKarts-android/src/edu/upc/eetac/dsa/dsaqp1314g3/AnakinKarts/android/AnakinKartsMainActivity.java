package edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.android;






import java.net.Authenticator;
import java.net.PasswordAuthentication;
>>>>>>> refs/remotes/origin/dsadevelopers
import org.json.JSONException;
import org.json.JSONObject;
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
public class AnakinKartsMainActivity extends Activity


	


{

	String username;



	private class logintask extends AsyncTask <String, Void, JSONObject> {
	
		
		@Override
		protected JSONObject doInBackground(String... params) {
		JSONObject user = null;
		AnakinAndroidApi api = new AnakinAndroidApi();
			String result = params[0];
			System.out.println(result);

			try {
				
				user = api.LoginUser(params[0], params[1]);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			System.out.println("3");
			//System.out.println(resposta);
			return user;
		}

		@Override
		protected void onPostExecute(JSONObject result) {
		
			
			String name = null;
			
			if (result==null)
			{

				Toast toast = Toast.makeText(getApplicationContext(),"El nombre o la contraseña son incorrectos", 
						   Toast.LENGTH_LONG);
				toast.show();
			}
			else{
			try {
				name = result.getString("username");
				
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}





			if (name.equals(username))


			{
				Toast toast = Toast.makeText(getApplicationContext()," Bienvenido " + name, 
						   Toast.LENGTH_LONG);
				toast.show();
				Intent intent = new Intent (getApplicationContext(), HomeActivity.class);
				intent.putExtra("username", name);
				
				startActivity(intent);
				
				

			}
			else 
			{
				Toast toast = Toast.makeText(getApplicationContext(),"El nombre o la contraseña son incorrectos", 
						   Toast.LENGTH_LONG);
				toast.show();

			}
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
		
		setContentView(R.layout.main);
		
		}
 
	public void startHomeActivity(View v) {
		EditText etUsername = (EditText) findViewById(R.id.username);
		EditText etPassword = (EditText) findViewById(R.id.password);

		
	 username = etUsername.getText().toString();
	 String password = etPassword.getText().toString();
		
		//String password = "edith";
		
		if (username.equals("")|| password.equals("") )
		{

			Toast toast = Toast.makeText(getApplicationContext(),"Debe rellenar todos los campos", 
					   Toast.LENGTH_LONG);
			toast.show();

		}
		else
		{
		(new logintask()).execute(username, password);}
		




	}
 
	
	public void startRegisterActivity(View v){
		Intent intent = new Intent(this, RegisterActivity.class);
		startActivity(intent);
		finish();
	}
 

}
