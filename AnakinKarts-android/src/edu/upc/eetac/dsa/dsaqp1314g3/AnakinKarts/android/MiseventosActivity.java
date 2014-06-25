package edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.android;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.android.model.AnakinAndroidApi;
import edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.android.model.AnakinKartsAndroidException;
import edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.android.model.Evento;
import edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.android.model.EventoCollection;
import edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.android.model.EventoCollectionAndroid;
import edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.android.model.Eventoandroid;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.ArrayList;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ListActivity;
import android.widget.ListAdapter;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;

public class MiseventosActivity extends Activity {
	
	
	String username;
	private ListView listview = null;
	ListView lv;
	
	private class FetchUsersTask extends AsyncTask<String, Void, EventoCollectionAndroid> {
		private ProgressDialog pd;
		
		@Override
		protected EventoCollectionAndroid doInBackground(String... params) {
		EventoCollectionAndroid array = new EventoCollectionAndroid();
		System.out.println("HOLAAAAAAAAAAA");
		
		try {
			
			AnakinAndroidApi api = new AnakinAndroidApi();
			array = api.ListMyEvents(params[0]);
			
		} catch (AnakinKartsAndroidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return array;
		}
		
		@Override
		protected void onPreExecute() {
			pd = new ProgressDialog(MiseventosActivity.this);
			pd.setTitle("Searching...");
			pd.setCancelable(false);
			pd.setIndeterminate(true);
			pd.show();
		}
		@Override
		protected void onPostExecute(EventoCollectionAndroid result) {
			
			
				
				
			
			
			addeventos(result);
			if (pd != null) {
				pd.dismiss();
			}
			if (result.getEventosa().isEmpty())
			{
				
				Toast toast = Toast.makeText(getApplicationContext(),"No tienes eventos propios", 
						   Toast.LENGTH_LONG);
				toast.show();
			}

			lv.setOnItemClickListener(new OnItemClickListener() {  
				
				//cuando pulsen sobre un evento mostrara perfil evento
			    @Override
			    public void onItemClick(AdapterView<?> a, View v, int position, long id) {
//			    	
					System.out.println("asasasas");
					Eventoandroid eve = eventolist.get(position);
					System.out.println(eve.toString());
					int eventid=eve.getEventoid();
			    	String nom = eve.getNombre();
					String fech= eve.getFecha();
					String gana = eve.getGanador();
					System.out.println(nom+fech+gana);
					Intent i = new Intent(getApplicationContext(), EventosActivity.class);
					
					i.putExtra("nom", nom );
					i.putExtra("ID", eventid);
					i.putExtra("data", fech);
					i.putExtra("ganador", gana);
					startActivity(i);
			    	
			    	
					System.out.println("Has pulsado un evento");
					
				
							 
			    }

			});
			
			
			
			
			
			
			
		}

		private void addeventos(EventoCollectionAndroid result) {
			
			eventolist.addAll(result.getEventosa());
			adapter.notifyDataSetChanged(); 
			
		}




	}
	
	
	
	
	
	
	
	
	private final static String TAG = "ListEventsActivity";
	private final static int ID_DIALOG_FETCHING = 0;
	private static final ArrayList<Evento> AdapterView = null;

	private String[] values;
	 Eventoandroid[] eventos;
	private String eventoid;
	

	private AnakinAdapter adapter;
	
	private ArrayList<Eventoandroid> eventolist;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_eventosprivados);
		System.out.println("estamos en list");
		
		Bundle bundle = this.getIntent().getExtras();
		username = bundle.getString("nom");
		
		setTitle("Tus eventos " +username);
	
	eventolist = new ArrayList<>();
	adapter = new AnakinAdapter(this, eventolist);
	System.out.println("HOOOOOOOOLITAAAAAS-------------");
    lv = (ListView)findViewById(R.id.list2);
	lv.setAdapter(adapter);

		(new FetchUsersTask()).execute(username);
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case ID_DIALOG_FETCHING:
			ProgressDialog loadingDialog = new ProgressDialog(this);
			loadingDialog.setMessage("Fetching ...");
			loadingDialog.setIndeterminate(true);
			loadingDialog.setCancelable(false);
			return loadingDialog;

		}
		return super.onCreateDialog(id);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_events, menu);
		return true;
	}

	



	}
	
	
	
