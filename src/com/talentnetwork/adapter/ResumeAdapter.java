package com.talentnetwork.adapter;

import java.util.ArrayList;
import java.util.List;

import com.talentnetwork.activity.R;
import com.talentnetwork.bean.ResumeItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ResumeAdapter extends BaseAdapter{
	private Context context;
	
	private int layId;
	 
	private List<ResumeItem> list=new ArrayList<ResumeItem>();
	
	
	public ResumeAdapter(Context context,List<ResumeItem> list,int layId) {
		this.context=context;
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
			view=LayoutInflater.from(context).inflate(layId, parent,false);
		}
		TextView resume_item_resumeName=(TextView) view.findViewById(R.id.resume_item_resumeName);
		TextView resume_item_resumeTime=(TextView) view.findViewById(R.id.resume_item_resumeTime);
		resume_item_resumeName.setText(list.get(position).getResumeName());
		resume_item_resumeTime.setText(list.get(position).getTime());
		view.setTag(list.get(position).getId());
		return view;
	}
	

}
