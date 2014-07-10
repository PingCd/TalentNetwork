package com.talentnetwork.adapter;

import java.util.List;

import com.talentnetwork.activity.R;
import com.talentnetwork.bean.WorkExperience;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 工作经验
 * @author Administrator
 *
 */
public class WorkExperienceAdapter extends BaseAdapter{
	
	private Context context=null;
	
	private List<WorkExperience> welist=null;
	
	private int layId;
	
	public WorkExperienceAdapter(Context con,List<WorkExperience> list,int layId) {
		this.context=con;
		this.welist=list;
		this.layId=layId;
	}

	@Override
	public int getCount() {
		return welist.size();
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
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView=LayoutInflater.from(this.context).inflate(layId, parent,false);
		}
		TextView word_tv_time_value=(TextView) convertView.findViewById(R.id.word_tv_time_value);
		TextView word_tv_company_value=(TextView) convertView.findViewById(R.id.word_tv_company_value);
		TextView word_tv_Inposition_value=(TextView) convertView.findViewById(R.id.word_tv_Inposition_value);
		TextView word_tv_description_value=(TextView) convertView.findViewById(R.id.word_tv_description_value);
		word_tv_time_value.setText(welist.get(position).getTime());
		word_tv_company_value.setText(welist.get(position).getCompany());
		word_tv_Inposition_value.setText(welist.get(position).getInposition());
		word_tv_description_value.setText(welist.get(position).getDescription());
		return convertView;
		
	}
	

}
