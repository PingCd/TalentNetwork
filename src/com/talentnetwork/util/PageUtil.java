package com.talentnetwork.util;

public class PageUtil {
	/**
	 * 计算最大页码数
	 */
	public static int getPage(int count,int limit){
		int maxPage=1;
		if(count!=0){
			if(count%limit==0){
				maxPage=count/limit;
			}else{
				maxPage=count/limit+1;
			}
		}else{
			maxPage=1;
		}
		return maxPage;
	}

}
