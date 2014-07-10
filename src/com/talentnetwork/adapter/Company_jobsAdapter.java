package com.talentnetwork.adapter;

import java.util.ArrayList;
import java.util.List;

import com.talentnetwork.activity.R;
import com.talentnetwork.bean.CompanyIn_Jobs;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 * 企业简介中职位列表Adapter
 * @author Administrator
 *
 */
public class Company_jobsAdapter extends BaseAdapter{
	
	private Context context;
	
	private int layId;
	
	private List<CompanyIn_Jobs> list=new ArrayList<CompanyIn_Jobs>();
	
	public Company_jobsAdapter(Context c,List<CompanyIn_Jobs> list,int layId) {
		this.context=c;
		this.list=list;
		this.layId=layId;
		Log.i("大大大大", list.size()+"-----");
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
			view=LayoutInflater.from(context).inflate(layId, parent, false);
		}
		TextView tv=(TextView) view.findViewById(R.id.jobs_tv);
		view.setTag(list.get(position).getJobId());
		tv.setText(list.get(position).getJobName());
		return view;
	}

}
