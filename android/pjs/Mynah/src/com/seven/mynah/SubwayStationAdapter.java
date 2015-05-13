package com.seven.mynah;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.seven.mynah.artifacts.SubwayStationInfo;
import com.seven.mynah.globalmanager.GlobalVariable;

public class SubwayStationAdapter extends ArrayAdapter<SubwayStationInfo> {

	private ViewHolder viewHolder = null;
	private LayoutInflater inflater = null;
	private ArrayList<SubwayStationInfo> infoList = null;
	private Context mContext = null;

	public SubwayStationAdapter(Context c, int textViewResourceId, 
			ArrayList<SubwayStationInfo> arrays) {
		super(c, textViewResourceId, arrays);
		this.inflater = LayoutInflater.from(c);
		this.mContext = c;
		infoList = arrays;
	}

	@Override
	public int getCount() {
		return super.getCount();
	}

	@Override
	public SubwayStationInfo getItem(int position) {
		return super.getItem(position);
	}

	@Override
	public long getItemId(int position) {
		return super.getItemId(position);
	}

	@Override
	public View getView(int position, View convertview, ViewGroup parent) {

		View v = convertview;

		if (v == null) {
			viewHolder = new ViewHolder();
			v = inflater.inflate(R.layout.list_row, null);
			viewHolder.tvSubwayStationListRow = (TextView) v.findViewById(R.id.tvListRow);

			v.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) v.getTag();
		}
		//GlobalVariable.SubwayConstant.K
		
		
		viewHolder.tvSubwayStationListRow.setText(getItem(position).line_num);
		viewHolder.subwayStationInfo = infoList.get(position);
		
		return v;
	}
	/*
	public String getStationName(String str)
	{
		switch(str)
		{
			case:K
			
		}
		
			
		
		return str;
	}*/
	
}