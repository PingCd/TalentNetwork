package com.talentnetwork.activity;

import java.util.List;

import com.talentnetwork.adapter.JobItemAdapter;
import com.talentnetwork.bean.JobItem;
import com.talentnetwork.util.Utility;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
/**
 * 收信箱
 * @author Administrator
 *
 */
public class InBoxActivity extends Activity implements OnClickListener{
	
	private ListView inboxListview;
	
	private JobItemAdapter adapter;
	
	private List<JobItem> list;
	
	private Button inbox_back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inbox);
		initView();
		adapter=new JobItemAdapter(this, list, R.layout.job_item, null);
		inboxListview.setAdapter(adapter);
		Utility.setListViewHeightBasedOnChildren(inboxListview);
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.inbox_back:
			this.finish();
			break;
		}
		
	}
	private void initView() {
		inboxListview=(ListView) findViewById(R.id.inbox_listview);
		inbox_back=(Button) findViewById(R.id.inbox_back);
		inbox_back.setOnClickListener(this);
//		initsuju();
	}
//	private void initsuju() {
//		list=new ArrayList<JobItem>();
//		JobItem job=new JobItem();
//		job.setId(1);
//		job.setJobName("面试邀请");
//		job.setJob_company("52123555.com");
//		job.setTime("2014-5-5");
//		list.add(job);
//		list.add(job);
//		list.add(job);
//		list.add(job);
//		list.add(job);
//		list.add(job);
//		list.add(job);
//		list.add(job);
//
//	}
	
	@Override
	protected void onDestroy() {
		this.finish();
		super.onDestroy();
	}

}
