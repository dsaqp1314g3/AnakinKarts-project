package edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.android;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ContactActivity extends Activity {
	 private MapView mMapView;
	    private GoogleMap mMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact);
		setTitle("Ubicaciones Anakin Karts");
		  mMapView = (MapView) findViewById(R.id.map);
	      mMapView.onCreate(savedInstanceState);

	        setUpMapIfNeeded();
	    }

	    @Override
	    protected void onResume() {
	        super.onResume();
	        mMapView.onResume();

	        setUpMapIfNeeded();
	    }

	    private void setUpMapIfNeeded() {
	        if (mMap == null) {
	            mMap = ((MapView) findViewById(R.id.map)).getMap();
	            if (mMap != null) {
	                setUpMap();
	            }
	        }
	    }

	    private void setUpMap() {
	       
	        mMap.addMarker(new MarkerOptions().position(new LatLng(42.1086181, 3.1236142)).title("Anakin Karts"));
	    }

	    @Override
	    protected void onPause() {
	        mMapView.onPause();
	        super.onPause();
	    }

	    @Override
	    protected void onDestroy() {
	        mMapView.onDestroy();
	        super.onDestroy();
	    }

	    @Override
	    public void onLowMemory() {
	        super.onLowMemory();
	        mMapView.onLowMemory();
	    }

	    @Override
	    public void onSaveInstanceState(Bundle outState) {
	        super.onSaveInstanceState(outState);
	        mMapView.onSaveInstanceState(outState);
	    }
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

}
