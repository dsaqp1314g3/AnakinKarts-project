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

public class TestingActivity extends Activity
{


	private final static String TAG = AnakinKartsMainActivity.class.getName();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		setContentView(R.layout.register);
	}
 



	
}
