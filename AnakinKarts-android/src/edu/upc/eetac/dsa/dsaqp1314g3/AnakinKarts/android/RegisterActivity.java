package edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.android;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

public class RegisterActivity extends Activity
{
	
	private final static String TAG = AnakinKartsMainActivity.class.getName();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
 
		SharedPreferences prefs = getSharedPreferences("AnakinKarts-api",
				Context.MODE_PRIVATE);
		//String username = prefs.getString("username", null);
		//String password = prefs.getString("password", null);
		// Uncomment the next two lines to test the application without login
		// each time
		// username = "alicia";
		// password = "alicia";
//		if ((username != null) && (password != null)) {
//			Intent intent = new Intent(this, HomeActivity.class);
//			startActivity(intent);
//			finish();
//		}
		setContentView(R.layout.register);
	}
 
	public void signIn(View v) {
		
 
		register();
	}
 
	
	
	private void register() {
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
		finish();
	}
 	
 

}
