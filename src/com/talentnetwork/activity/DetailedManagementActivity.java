package com.talentnetwork.activity;
import com.talentnetwork.bean.DetaileManagement;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
/**
 * 职位管理详情
 * @author Administrator
 *
 */
public class DetailedManagementActivity extends Activity {
	
public TextView  tv_11,tv_22,tv_33,tv_44,tv_55,tv_66,tv_77,tv_88,tv_99;
public TextView  tv2_11,tv2_22;
public TextView  tv3_11,tv3_22,tv3_33,tv3_44,tv3_55;

private Button dm_btn;//返回按钮

private DetaileManagement dm=new DetaileManagement();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_management);
        init();
        setView();
        
    }

    private void init() {
    	Intent intent=getIntent();
    	dm=(DetaileManagement) intent.getSerializableExtra("dm");
    	
    	
    	tv_11=(TextView)findViewById(R.id.tv_11);
    	tv_22=(TextView)findViewById(R.id.tv_22);
    	tv_33=(TextView)findViewById(R.id.tv_33);
    	tv_44=(TextView)findViewById(R.id.tv_44);
    	tv_55=(TextView)findViewById(R.id.tv_55);
    	tv_66=(TextView)findViewById(R.id.tv_66);
    	tv_77=(TextView)findViewById(R.id.tv_77);
    	tv_88=(TextView)findViewById(R.id.tv_88);
    	tv_99=(TextView)findViewById(R.id.tv_99);
    	
    	tv2_11=(TextView)findViewById(R.id.tv2_11);
    	tv2_22=(TextView)findViewById(R.id.tv2_22);
    	
    	 tv3_11=(TextView)findViewById(R.id.tv3_11);
    	 tv3_22=(TextView)findViewById(R.id.tv3_22);
    	 tv3_33=(TextView)findViewById(R.id.tv3_33);
    	 tv3_44=(TextView)findViewById(R.id.tv3_44);
    	 tv3_55=(TextView)findViewById(R.id.tv3_55);
    	 dm_btn=(Button) findViewById(R.id.cast_resume_btn_back);
    	 dm_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DetailedManagementActivity.this.finish();
			}
		});
	}
    
    private void setView() {
    	tv_11.setText(dm.getCompanyname());
    	tv_22.setText(dm.getCategory_cn());
    	tv_33.setText(dm.getJobs_name());
    	tv_44.setText(dm.getScale_cn());
    	tv_55.setText(dm.getTrade_cn());
    	tv_66.setText(dm.getNature_cn());
    	tv_77.setText(dm.getDistrict_cn());
    	tv_88.setText(dm.getDeadline());
    	tv_99.setText(dm.getContents());
    	
    	tv2_11.setText(dm.getWage_cn());
    	tv2_22.setText(dm.getTag());
    	
    	tv3_11.setText(dm.getSex_cn());
    	tv3_33.setText(dm.getEducation_cn());
    	tv3_44.setText(dm.getExperience_cn());
    	tv3_55.setText(dm.getAdd_mode());
    	
	}

@Override
	protected void onDestroy() {
	this.finish();
		super.onDestroy();
	}


}
