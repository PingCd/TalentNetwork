package com.talentnetwork.adapter;

import java.util.List;

import com.talentnetwork.activity.JobFragment.OnbrakClickListener;
import com.talentnetwork.activity.R;
import com.talentnetwork.bean.JobItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 * 工作职位item 
 * @author Administrator
 *
 */
public class JobItemAdapter extends BaseAdapter{
	
	private List<JobItem> list=null;
	
	private int layId=0;
	
	private Context context=null;
	
	private OnbrakClickListener on=null;
	
	public JobItemAdapter(Context c,List<JobItem> list,int id,OnbrakClickListener onb) {
		this.layId=id;
		this.list=list;
		this.context=c;
		if(onb!=null){
			this.on=onb;
		}
	}

	@Override
	public int getCount() {
		return list.size();
	}
	@Override
	public Object getItem(int position) {
		return list.get(position);
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
			TextView tv_job_name=(TextView) view.findViewById(R.id.tv_job_name);
			TextView tv_job_company=(TextView) view.findViewById(R.id.tv_job_company);
			TextView tv_job_time=(TextView) view.findViewById(R.id.tv_job_time);
			tv_job_name.setText(list.get(position).getJobName());
			tv_job_company.setText(list.get(position).getJob_company());
			tv_job_time.setText(list.get(position).getTime());
			
			view.setTag(list.get(position).getId());
			
//			view.setOnClickListener(new View.OnClickListener() {
//				@Override
//				public void onClick(View arg0) {
//					if(on!=null){
//						on.onback();
//					}else{
//						Intent intent=new Intent(context,ResumeActivity.class);
//						context.startActivity(intent);
//					}
//				}
//			});
		return view;
	}

}
