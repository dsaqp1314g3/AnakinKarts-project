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
import android.widget.TextView;

public class HomeActivity extends Activity
{

	String username= null;
	private TextView text1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		
		Bundle bundle = this.getIntent().getExtras();
		username = bundle.getString("username");
		
		setTitle("Bienvenido " + username);

	
	
	


		 text1 = (TextView) findViewById(R.id.nom);
		 text1.setText(username);
		
	
	}

	public void startEventosActivity(View v){
		Intent intent = new Intent(this, EventosListActivity.class);
		startActivity(intent);
		
	}
	public void startHorarioActivity(View v){
		Intent intent = new Intent(this, HorariosActivity.class);
		startActivity(intent);
	
	}

	
	public void startMisEventosActivity(View v){
		Intent intent = new Intent(this, MiseventosActivity.class);
		System.out.println(text1.getText().toString());
		intent.putExtra ("nom", text1.getText().toString());
		startActivity(intent);
		
	}
	public void startContactoActivity(View v){
		Intent intent = new Intent(this, ContactActivity.class);
		startActivity(intent);
		
	}
	
	
	


	}
	
	



