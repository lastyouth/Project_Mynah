package com.seven.mynah;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.seven.mynah.artifacts.SubwayStationInfo;
import com.seven.mynah.globalmanager.GlobalFunction;
import com.seven.mynah.globalmanager.GlobalVariable;

public class SubwayStationAdapter extends ArrayAdapter<SubwayStationInfo> {

	private ViewHolder viewHolder = null;
	private LayoutInflater inflater = null;
	private ArrayList<SubwayStationInfo> infoList = null;
	private Context mContext = null;
	
	private boolean isFirst = true;

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
		
		
		String code = getItem(position).line_num;
		String stationName = GlobalFunction.SubwayDecode(code);
		
		if(isFirst)
		{
			stationName += " 상행";
			isFirst = false;
		}
		else
		{
			stationName += " 하행";
			isFirst = true;
		}
		
		viewHolder.tvSubwayStationListRow.setText(stationName);
		viewHolder.subwayStationInfo = infoList.get(position);
		
		return v;
	}
	
}