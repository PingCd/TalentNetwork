package com.talentnetwork.activity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 *首页
 * 
 * @author
 */
@SuppressLint("ValidFragment")
public class HomeFragment extends Fragment{
	
	private Spinner search_spinner=null;
	
	
	//接口
		private OnHomeBrakClickListener onHome=null;
		
		public interface OnHomeBrakClickListener{
			public void onHomeBack(String str,int type);
		}
	
	
	private Button btn_search=null;
	
	private String search="";//搜索内容
	private int searchType=1;//搜索类型（如职位或公司）
	
	private EditText et_search;//搜索内容
	
	  @Override
	    public void onAttach(Activity activity) {
	        super.onAttach(activity);
	        try {
	        	onHome = (OnHomeBrakClickListener) activity;
	        } catch (ClassCastException e) {
	            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
	        }
	    }
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.homefragment, container, false);
		return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		search_spinner=(Spinner) getView().findViewById(R.id.home_spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource( getActivity(), R.array.search_arry, android.R.layout.simple_spinner_item); 
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
		search_spinner.setAdapter(adapter);
		initView();
		//选择职位or公司
		search_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int p, long arg3) {
				if(p==0){
					searchType=1;
				}else{
					searchType=2;
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
		//搜索
		btn_search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String text=et_search.getText().toString();
				et_search.setText("");
				//获取后台数据
				onHome.onHomeBack(text, searchType);
				
			}
		});
	}
	private void initView() {
		et_search=(EditText) getView().findViewById(R.id.et_search);
		btn_search=(Button) getView().findViewById(R.id.btn_search);
	}

}
