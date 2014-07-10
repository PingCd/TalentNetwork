package com.talentnetwork.adapter;

import java.util.ArrayList;
import java.util.List;

import com.talentnetwork.activity.R;
import com.talentnetwork.bean.PositionManagement;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 * 职位管理item
 * @author Administrator
 *
 */
public class PositionManagementAdapter extends  BaseAdapter{
	
	private Context context=null;
	
	private List<PositionManagement>  list=new ArrayList<PositionManagement>();
	
	private int layId;
	
	
	public PositionManagementAdapter(Context con,List<PositionManagement> list,int layId) {
		this.context=con;
		this.list=list;
		this.layId=layId;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		if(view==null){
			view=LayoutInflater.from(this.context).inflate(R.layout.position_management_tiem,parent,false);
		}
		TextView pmi_tv_positionName=(TextView) view.findViewById(R.id.pmi_tv_positionName);
		TextView pmi_tv_positionRelease_time=(TextView) view.findViewById(R.id.pmi_tv_positionRelease_time);
		TextView pmi_tv_positionby_time=(TextView) view.findViewById(R.id.pmi_tv_positionby_time);
		TextView pmi_tv_stateValue=(TextView) view.findViewById(R.id.pmi_tv_stateValue);
		pmi_tv_positionName.setText(list.get(position).getPositionName());
		pmi_tv_positionRelease_time.setText(list.get(position).getPositionRelease());
		pmi_tv_positionby_time.setText(list.get(position).getPositionBy());
		
		String str=list.get(position).getState();
		pmi_tv_stateValue.setText(str);
		if(str.equals("已审核")){
			pmi_tv_stateValue.setTextColor(Color.GREEN);
		}
		
		view.setTag(list.get(position).getId());
		
		return view;
	}

}
