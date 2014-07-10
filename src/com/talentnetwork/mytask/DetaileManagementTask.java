package com.talentnetwork.mytask;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.talentnetwork.activity.DetailedManagementActivity;
import com.talentnetwork.bean.DetaileManagement;
import com.talentnetwork.util.HttpClientUtil;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ListView;
/**
 * 职位管理详情
 * @author Administrator
 *
 */
public class DetaileManagementTask extends AsyncTask<Void, Void, String>{
	
	private HttpClientUtil hcu=new HttpClientUtil();
	
	private Context context=null;
	
	private int id;
	
	private ListView  listView;
	
	public DetaileManagementTask(Context context,int id,ListView  listView) {
		this.context=context;
		this.id=id;
		this.listView= listView;
	}

	@Override
	protected String doInBackground(Void... params) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id",this.id+"");
		String str=null;
		try {
			str = hcu.getRequest(context, hcu.URL
					+ "EJobServer?", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	
	@Override
	protected void onPostExecute(String result) {
		DetaileManagement dm=null;
//		Log.i("wwwwwwwwwwwww", result);
		if(result==null||result.equals("")){
			hcu.getToast(context, "网络连接错误！");
		}else{
			dm=new DetaileManagement();
			try {
				JSONObject jo=new JSONObject(result);
				dm.setCompanyname(jo.getString("companyname"));
				dm.setCategory_cn(jo.getString("category_cn"));
				dm.setJobs_name(jo.getString("jobs_name"));
				dm.setScale_cn(jo.getString("scale_cn"));
				dm.setTrade_cn(jo.getString("trade_cn"));
				dm.setNature_cn(jo.getString("nature_cn"));
				dm.setDistrict_cn(jo.getString("district_cn"));
				dm.setDeadline(jo.getString("deadline"));
				dm.setContents(jo.getString("contents"));
				
				dm.setWage_cn(jo.getString("wage_cn"));
				dm.setTag(jo.getString("tag"));
				
				dm.setSex_cn(jo.getString("sex_cn"));
				dm.setEducation_cn(jo.getString("education_cn"));
				dm.setExperience_cn(jo.getString("experience_cn"));
				dm.setAdd_mode(jo.getString("add_mode"));
				if(dm.getAdd_mode().equals("1")){
					dm.setAdd_mode("过滤");
				}else{
					dm.setAdd_mode("不过滤");
				}
				
			} catch (JSONException e) {
				hcu.getToast(context, "服务器出现异常");
				e.printStackTrace();
			}
		}
		if(dm!=null){
			Intent intent=new Intent(context,DetailedManagementActivity.class);
			intent.putExtra("dm", dm);
			context.startActivity(intent);
		}
		setlistViewEnabled();
	}
	
	//设置listview获取焦点
		public void setlistViewEnabled(){
			listView.setEnabled(true);
		}
}
