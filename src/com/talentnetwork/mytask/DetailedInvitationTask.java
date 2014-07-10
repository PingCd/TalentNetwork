package com.talentnetwork.mytask;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.talentnetwork.activity.DetailedInvitationActivity;
import com.talentnetwork.bean.DetailedInvitation;
import com.talentnetwork.util.HttpClientUtil;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;

public class DetailedInvitationTask extends AsyncTask<Void, Void, String>{
	
	private HttpClientUtil hcu=new HttpClientUtil();
	
	private Context context;
	
	private int id;
	
	private View pro;
	
	private ListView lv;
	
	public DetailedInvitationTask(Context context,int id,View pro,ListView lv) {
		this.context=context;
		this.id=id;
		this.pro=pro;
		this.lv=lv;
	}

	@Override
	protected String doInBackground(Void... params) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("did", id+"");
		String str=null;
		try {
			str = hcu.getRequest(context, hcu.URL + "CResumeServer?", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
		
	}
	@Override
	protected void onPostExecute(String result) {
		if(result==null||result.equals("")){
			hcu.getToast(context, "网络出现异常！");
		}else{
			try {
				JSONObject jo=new JSONObject(result);
				DetailedInvitation di=new DetailedInvitation();
				di.setJobs_name(jo.getString("jobs_name"));
				di.setCompanyname(jo.getString("companyname"));
				di.setInterview_addtime(jo.getString("interview_addtime"));
				di.setAddress(jo.getString("address"));
				di.setTelephone(jo.getString("telephone"));
				di.setNotes(jo.getString("notes"));
				
				Intent intent=new Intent(context,DetailedInvitationActivity.class);
				intent.putExtra("di", di);
				context.startActivity(intent);
			} catch (JSONException e) {
				hcu.getToast(context, "服务器异常！");
				e.printStackTrace();
			}
		}
		pro.setVisibility(View.GONE);
		lv.setEnabled(true);
	}

}
