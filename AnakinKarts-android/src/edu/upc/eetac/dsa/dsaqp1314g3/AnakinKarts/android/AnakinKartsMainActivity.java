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

public class AnakinKartsMainActivity extends Activity
{
	private final static String TAG = AnakinKartsMainActivity.class.getName();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
 
		SharedPreferences prefs = getSharedPreferences("AnakinKarts-api",
				Context.MODE_PRIVATE);
		String username = prefs.getString("username", null);
		String password = prefs.getString("password", null);
		// Uncomment the next two lines to test the application without login
		// each time
		// username = "alicia";
		// password = "alicia";
		if ((username != null) && (password != null)) {
			Intent intent = new Intent(this, HomeActivity.class);
			startActivity(intent);
			finish();
		}
		setContentView(R.layout.main);
	}
 
	public void signIn(View v) {
		EditText etUsername = (EditText) findViewById(R.id.username);
		EditText etPassword = (EditText) findViewById(R.id.password);
 
		String username = etUsername.getText().toString();
		String password = etPassword.getText().toString();
 
		// Launch a background task to check if credentials are correct
		// If correct, store username and password and start Beeter activity
		// else, handle error
 
		// I'll suppose that u/p are correct:
		SharedPreferences prefs = getSharedPreferences("beeter-profile",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.clear();
		editor.putString("username", username);
		editor.putString("password", password);
		boolean done = editor.commit();
		if (done)
			Log.d(TAG, "preferences set");
		else
			Log.d(TAG, "preferences not set. THIS A SEVERE PROBLEM");
 
		startHomeActivity();
	}
 
	private void startHomeActivity() {
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
		finish();
	}
	private void startRegisterActivity(View v){
		Intent intent = new Intent(this, RegisterActivity.class);
		startActivity(intent);
		finish();
	}
 

}
