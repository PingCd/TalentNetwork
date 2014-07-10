package com.talentnetwork.adapter;

import java.util.List;

import com.talentnetwork.activity.R;
import com.talentnetwork.bean.Education;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 * 教育经历item
 * @author Administrator
 *
 */
public class EducationAdapter extends BaseAdapter{
	
	private Context context;
	
	private List<Education> list=null;
	
	private int layId;
	
	public EducationAdapter(Context context,List<Education> list,int layId) {
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
			view=LayoutInflater.from(this.context).inflate(layId, parent,false);
		}
		TextView educationTime=(TextView) view.findViewById(R.id.education_item_time);
		TextView education=(TextView) view.findViewById(R.id.education_item_education);
		TextView school=(TextView) view.findViewById(R.id.education_item_school);
		TextView professional=(TextView) view.findViewById(R.id.education_item_professional);
		educationTime.setText(list.get(position).getEducationTime());
		education.setText(list.get(position).getEducation());
		school.setText(list.get(position).getSchool());
		professional.setText(list.get(position).getProfessional());
		return view;
	}


}
