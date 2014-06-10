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

public class HomeActivity extends Activity
{

	String username= null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		
		Bundle bundle = this.getIntent().getExtras();
		username = bundle.getString("username");
		
		setTitle("Bienvenido " + username);
	}
	
	
 

}
