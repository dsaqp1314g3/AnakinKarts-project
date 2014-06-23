package edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewStub.OnInflateListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EventosActivity extends Activity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.evento_descripcion);
		Bundle bundle = this.getIntent().getExtras();
		
		String nom= bundle.get("nom").toString();
		TextView text1 = (TextView) findViewById(R.id.nombrevalue);
		text1.setText(nom);
		
		String fecha= bundle.get("data").toString();
		TextView textfecha = (TextView) findViewById(R.id.fechavalue);
		textfecha.setText(fecha);
		
		
		String id= bundle.get("ID").toString();
		TextView textid= (TextView) findViewById(R.id.idvalue);
		textfecha.setText(id);
		
	}




}