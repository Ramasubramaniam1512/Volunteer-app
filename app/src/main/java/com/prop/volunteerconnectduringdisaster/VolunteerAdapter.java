package com.prop.volunteerconnectduringdisaster;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class VolunteerAdapter extends BaseAdapter {
	Context con;
	LayoutInflater layoutInflater;
	ArrayList<HashMap<String,String>> listvalue;

	public VolunteerAdapter(ViewVolunteer listOfFriendsActivity,
						  ArrayList<HashMap<String,String>> usersList) {
		// TODO Auto-generated constructor stub
		con = listOfFriendsActivity;
		listvalue = usersList;
		layoutInflater = LayoutInflater.from(listOfFriendsActivity);
	}

	

	public int getCount() {
		// TODO Auto-generated method stub
		return listvalue.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listvalue.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.volunteerlistview, null);
			viewHolder = new ViewHolder();

			viewHolder.txt1 = (TextView) convertView
					.findViewById(R.id.t1);

			viewHolder.txt2 = (TextView) convertView
					.findViewById(R.id.t2);
			
			viewHolder.txt3 = (TextView) convertView
					.findViewById(R.id.t3);
			
			viewHolder.txt4 = (TextView) convertView
					.findViewById(R.id.t4);
		


			

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.txt1.setText(listvalue.get(position).get("username")
				.toString());

		viewHolder.txt2.setText(""+listvalue.get(position).get("name")
				.toString());
		
		viewHolder.txt3.setText(""+listvalue.get(position).get("district")
				.toString());
		
		viewHolder.txt4.setText(""+listvalue.get(position).get("mobile")
				.toString());
	

		return convertView;

	}

	class ViewHolder {
		TextView txt1,txt2,txt3,txt4;

	}

}
