package com.prop.volunteerconnectduringdisaster;

import java.util.ArrayList;
import java.util.HashMap;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class MessageAdapter extends BaseAdapter {
	Context con;
	LayoutInflater layoutInflater;
	ArrayList<HashMap<String,String>> listvalue;

	public MessageAdapter(Message msgs,
			ArrayList<HashMap<String,String>> usersList) {
		// TODO Auto-generated constructor stub
		con = msgs;
		listvalue = usersList;
		layoutInflater = LayoutInflater.from(msgs);
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
			convertView = layoutInflater.inflate(R.layout.messsagelist, null);
			viewHolder = new ViewHolder();
			viewHolder.txtUsername = (TextView) convertView
					.findViewById(R.id.textView_name);
			viewHolder.txtnewuser = (TextView) convertView
					.findViewById(R.id.textView_nextuser);
			viewHolder.txtMessage = (TextView) convertView
					.findViewById(R.id.textView_phone);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.txtUsername.setText(listvalue.get(position).get("message")
				.toString());
		viewHolder.txtMessage.setText(listvalue.get(position)
				.get("sender").toString());
		viewHolder.txtnewuser.setText(listvalue.get(position)
				.get("receiver").toString());
		return convertView;

	}

	class ViewHolder {
		TextView txtUsername, txtnewuser, txtMessage;

	}
}
