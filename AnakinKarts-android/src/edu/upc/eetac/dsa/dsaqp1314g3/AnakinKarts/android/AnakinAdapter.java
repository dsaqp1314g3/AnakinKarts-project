package edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.android;

import java.text.SimpleDateFormat;
import java.util.ArrayList;




import edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.android.model.Eventoandroid;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AnakinAdapter extends BaseAdapter {
private ArrayList<Eventoandroid> data;
private LayoutInflater inflater;	

private static class ViewHolder {
	TextView tvSubject;
	TextView tvUsername;
	TextView tvDate;
}
	public AnakinAdapter(Context context, ArrayList<Eventoandroid> data) {
		super();
		inflater = LayoutInflater.from(context);
		this.data = data;
	}
	
	@Override
	public int getCount() {//devuelve numero total de filas que habria en la lista , numero de datos q tu muestras
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {//modelo, cada posicion de la lista tiene un modelo de la cual tiene una serie de datos
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {//devuelve valor unico para una determinada posicion
		// TODO Auto-generated method stub
		String enteroString = Integer.toString(((Eventoandroid)getItem(position)).getEventoid());

		return Long.parseLong(enteroString);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {//devueve ese layout qe hemos creado cn datos
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_row_evento, null);//listrowsting que habiamos creado
			viewHolder = new ViewHolder();
			viewHolder.tvSubject = (TextView) convertView
					.findViewById(R.id.tvSubject);
			viewHolder.tvUsername = (TextView) convertView
					.findViewById(R.id.tvUsername);
			viewHolder.tvDate = (TextView) convertView
					.findViewById(R.id.tvDate);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		String nom = data.get(position).getNombre();//recuperando valores de esa posicion
		int id = data.get(position).getEventoid();
		String date = data.get(position).getFecha();
		viewHolder.tvSubject.setText(nom);
		//viewHolder.tvUsername.setText(id);
		viewHolder.tvDate.setText(date);
		return convertView;
	}

}
