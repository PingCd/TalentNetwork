package com.talentnetwork.mytask;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.talentnetwork.bean.User;
import com.talentnetwork.util.HttpClientUtil;
import com.talentnetwork.util.MyApplication;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;

public class RefreshTask extends AsyncTask<Void, Void, String>{
	
	private HttpClientUtil hcu=new HttpClientUtil();
	
	private Context context;
	
	private View progressBar;
	
	private Button btn;
	
	public RefreshTask(Context context,View progressBar,Button btn) {
		this.context=context;
		this.progressBar=progressBar;
		this.btn=btn;
	}

	@Override
	protected String doInBackground(Void... params) {
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e1) {
//			e1.printStackTrace();
//		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("uid", MyApplication.getInstance().getUser().getUid()+"");
		String str = null;
		try {
			str = hcu.getRequest(context, hcu.URL+ "Refresh?", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	@Override
	protected void onPostExecute(String result) {
		if(result==null||result.equals("")){
			hcu.getToast(context, "网络异常！");
		}else{
//			Log.i("aaaaaaaaaaaaaaaaaaaaaa2", result);
			try {
				JSONArray arr = new JSONArray(result);
				JSONObject o1 = (JSONObject) arr.get(0);
				String state = o1.getString("status");
				if(state.equals("1")){
					User user=new User();
					user.setUid(Integer.parseInt(o1.getString("uid")));
					user.setUserName(o1.getString("username"));
					user.setType(Integer.parseInt(o1.getString("type")));
					user.setParam1(o1.getString("personal1"));
					user.setParam2(o1.getString("personal2"));
					user.setParam3(o1.getString("personal3"));
					MyApplication.getInstance().setUser(user);//将用户信息放入全局中
					hcu.getToast(context, "刷新成功！");
				}else if(state.equals("0")){
					hcu.getToast(context, "服务超时,请重新登录！");
				}else{
					hcu.getToast(context, "服务器出现错误！");
				}
			} catch (JSONException e) {
				hcu.getToast(context, "网络异常！");
				e.printStackTrace();
			}
		}
		
		progressBar.setVisibility(View.GONE);
		btn.setEnabled(true);
	}

}
