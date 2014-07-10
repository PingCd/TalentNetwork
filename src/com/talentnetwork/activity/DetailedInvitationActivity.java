package com.talentnetwork.activity;

import com.talentnetwork.bean.DetailedInvitation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
/**
 * 面试邀请详情
 * @author Administrator
 *
 */
public class DetailedInvitationActivity extends Activity {
	
	private Button detailedIn_back;

	private TextView tv_jobName, tv_company, tv_time, tv_address, tv_telephone,
			tv_comment;
	
	private DetailedInvitation dv=new DetailedInvitation();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detailed_invitation);
		initView();
	}
	
	private void initView() {
		Intent intent=getIntent();
		dv=(DetailedInvitation) intent.getSerializableExtra("di");
		
		detailedIn_back=(Button) findViewById(R.id.detailedIn_back);
		detailedIn_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				onDestroy();
			}
		});
		
		tv_jobName=(TextView) findViewById(R.id.tv_jobName);
		tv_company=(TextView) findViewById(R.id.tv_company);
		tv_time=(TextView) findViewById(R.id.tv_time);
		tv_address=(TextView) findViewById(R.id.tv_address);
		tv_telephone=(TextView) findViewById(R.id.tv_telephone);
		tv_comment=(TextView) findViewById(R.id.tv_comment);
		tv_jobName.setText(dv.getJobs_name());
		tv_company.setText(dv.getCompanyname());
		tv_time.setText(dv.getInterview_addtime());
		tv_address.setText(dv.getAddress());
		tv_telephone.setText(dv.getTelephone());
		tv_comment.setText(dv.getNotes());
		
		
	}
	@Override
	protected void onDestroy() {
		this.finish();
		super.onDestroy();
	}

}
