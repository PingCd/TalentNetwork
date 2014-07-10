package com.talentnetwork.mytask;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.talentnetwork.util.HttpClientUtil;
import com.talentnetwork.util.MyApplication;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
/**
 * 投递简历
 * @author Administrator
 *
 */
public class ApplyResumeTask extends AsyncTask<Void, Void, String>{
	
	private HttpClientUtil hcu=new HttpClientUtil();
	
	private Context context;
	
	private View pro;
	
	private int jobId;
	
	private Button btn;
	
	public ApplyResumeTask(Context context,View pro,Button btn,int jobId) {
		this.context=context;
		this.pro=pro;
		this.btn=btn;
		this.jobId=jobId;
	}

	@Override
	protected String doInBackground(Void... params) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("uid",MyApplication.getInstance().getUser().getUid()+"");
		map.put("jobs_id",jobId+"");
		String str=null;
		try {
			str = hcu.getRequest(context, hcu.URL
					+ "Add?", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	@Override
	protected void onPostExecute(String result) {
		Log.i("aaaaaaaaaaaaaaaaaaa", result+"-------uid"+MyApplication.getInstance().getUser().getUid()+"--jobid:"+jobId);
		if(result==null||result.equals("")){
			hcu.getToast(context, "网络出现异常");
		}else{
			try {
				JSONObject jo=new JSONObject(result);
				int state=Integer.parseInt(jo.getString("key"));
				if(state==1){
					hcu.getToast(context, "投递成功");
				}else if(state==2){
					hcu.getToast(context, "已投递此职位，不能重复投递！");
				}else{
					hcu.getToast(context, "请创建简历！");
				}
			} catch (JSONException e) {
				hcu.getToast(context, "网络出现异常");
				e.printStackTrace();
			}
		}
		btn.setEnabled(true);
		pro.setVisibility(View.GONE);
	}

}
