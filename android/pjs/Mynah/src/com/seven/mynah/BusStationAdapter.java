package com.seven.mynah;

import java.util.ArrayList;
import java.util.List;

import com.seven.mynah.artifacts.BusStationInfo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class BusStationAdapter extends ArrayAdapter<BusStationInfo>{

	
	Context mContext;
	List<BusStationInfo> list_bsinfo;
	ArrayList<BusStationInfo> array_bsinfo;
	
	
	public BusStationAdapter(Context context, int resource,
			List<BusStationInfo> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		mContext = context;
		list_bsinfo = objects;
		array_bsinfo.addAll(list_bsinfo);
		
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return super.getView(position, convertView, parent);
	}
	
	@Override
	public int getCount() {
		return array_bsinfo.size();
	}
	
	@Override
	public BusStationInfo getItem(int position) {
		// TODO Auto-generated method stub
		//return super.getItem(position);
		return array_bsinfo.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		//return super.getItemId(position);
		return position;
	}
	
	
	
}
