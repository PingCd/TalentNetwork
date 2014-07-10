package com.talentnetwork.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import com.talentnetwork.activity.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;
import android.webkit.URLUtil;

public class MyAutoUpdate {
	    public Activity activity = null;      
	    private static final String TAG = "AutoUpdate";   
	    private String currentFilePath = "";   
	    private String currentTempFilePath = "";   
	    private String fileEx = "";   
	    private String fileNa = "";   
	    private String strURL = "";
	    private ProgressDialog dialog;   
	    
	  //构造方法
	    public MyAutoUpdate(Activity activity,String url) {   
	        this.activity = activity;
	        this.strURL = url;
	    }   
	    
	  //判断网络是不是连接
	    public void check() {   
	        if (isNetworkAvailable(this.activity) == false) {   
	            return;   
	        }   
	        if (true) {// Check version.   
	            showUpdateDialog();   
	        }  
	    }   
	    
	  //判断网络连接方法
	    public static boolean isNetworkAvailable(Context ctx) {   
	        try {   
	            ConnectivityManager cm = (ConnectivityManager) ctx   
	                    .getSystemService(Context.CONNECTIVITY_SERVICE);   
	            NetworkInfo info = cm.getActiveNetworkInfo();   
	            return (info != null && info.isConnected());   
	        } catch (Exception e) {   
	            e.printStackTrace();   
	            return false;   
	        }   
	    }   
	    
	  //询问是否更新
	    public void showUpdateDialog() {   
	        @SuppressWarnings("unused")   
	        AlertDialog alert = new AlertDialog.Builder(this.activity)   
	                .setTitle("Title")   
	                .setIcon(R.drawable.ic_launcher)   
	                .setMessage("Update or not?")   
	                .setPositiveButton("Update",   
	                        new DialogInterface.OnClickListener() {   
	                            public void onClick(DialogInterface dialog,   
	                                    int which) {   
	                                downloadTheFile(strURL);   
	                                showWaitDialog();   
	                            }   
	                        })   
	                .setNegativeButton("Cancel",   
	                        new DialogInterface.OnClickListener() {   
	                            public void onClick(DialogInterface dialog,   
	                                    int which) {   
	                                dialog.cancel();   
	                            }   
	                        }).show();   
	    }   
	    
	    //弹出等待更新框
	    public void showWaitDialog() {   
	        dialog = new ProgressDialog(activity);   
	        dialog.setMessage("Waitting for update...");   
	        dialog.setIndeterminate(true);   
	        dialog.setCancelable(true);   
	        dialog.show();   
	    }   
	    
	  //获取当前版本号码和名称
	    public PackageInfo getCurrentVersion() {   
	        
	    	PackageInfo info = null;
	    	try {   
	               info = activity.getPackageManager().getPackageInfo(   
	               activity.getPackageName(), 0);   
	               
	        } catch (NameNotFoundException e) {   
	            e.printStackTrace();   
	        }   
	        return info;
	    }
	    
	  //下载apk包
	    private void downloadTheFile(final String strPath) {   
	        fileEx = strURL.substring(strURL.lastIndexOf(".") + 1, strURL.length())   
	                .toLowerCase();   
	        fileNa = strURL.substring(strURL.lastIndexOf("/") + 1,   
	        		strURL.lastIndexOf("."));   
	        try {   
	            if (strPath.equals(currentFilePath)) {   
	                doDownloadTheFile(strPath);   
	            }   
	            currentFilePath = strPath;   
	            Runnable r = new Runnable() {   
	                public void run() {   
	                    try {   
	                        doDownloadTheFile(strPath);   
	                    } catch (Exception e) {   
	                        Log.e(TAG, e.getMessage(), e);   
	                    }   
	                }   
	            };   
	            new Thread(r).start();   
	        } catch (Exception e) {   
	            e.printStackTrace();   
	        }   
	    }   
	    
	  //下载
	    private void doDownloadTheFile(String strPath) throws Exception {   
	        Log.i(TAG, "getDataSource()");   
	        if (!URLUtil.isNetworkUrl(strPath)) {   
	            Log.i(TAG, "getDataSource() It's a wrong URL!");   
	        } else {   
	            URL myURL = new URL(strPath);   
	            URLConnection conn = myURL.openConnection();   
	            conn.connect();   
	            InputStream is = conn.getInputStream();   
	            if (is == null) {   
	                throw new RuntimeException("stream is null");   
	            }   
	            File myTempFile = File.createTempFile(fileNa, "." + fileEx);   
	            currentTempFilePath = myTempFile.getAbsolutePath();   
	            FileOutputStream fos = new FileOutputStream(myTempFile);   
	            byte buf[] = new byte[128];   
	            do {   
	                int numread = is.read(buf);   
	                if (numread <= 0) {   
	                    break;   
	                }   
	                fos.write(buf, 0, numread);   
	            } while (true);   
	            Log.i(TAG, "getDataSource() Download  ok...");   
	            dialog.cancel();   
	            dialog.dismiss();   
	            openFile(myTempFile);   
	            try {   
	                is.close();   
	            } catch (Exception ex) {   
	                Log.e(TAG, "getDataSource() error: " + ex.getMessage(), ex);   
	            }   
	        }   
	    }   
	    
	    //打开文件
	    private void openFile(File f) {   
	        Intent intent = new Intent();   
	        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
	        intent.setAction(android.content.Intent.ACTION_VIEW);   
	        String type = getMIMEType(f);   
	        intent.setDataAndType(Uri.fromFile(f), type);   
	        activity.startActivity(intent);   
	    }   
	    
	  //删除文件
	    public void delFile() {   
	        Log.i(TAG, "The TempFile(" + currentTempFilePath + ") was deleted.");   
	        File myFile = new File(currentTempFilePath);   
	        if (myFile.exists()) {   
	            myFile.delete();   
	        }   
	    }   
	    
	    //判断文件类型
	    private String getMIMEType(File f) {   
	        String type = "";   
	        String fName = f.getName();   
	        String end = fName   
	                .substring(fName.lastIndexOf(".") + 1, fName.length())   
	                .toLowerCase();   
	        if (end.equals("m4a") || end.equals("mp3") || end.equals("mid")   
	                || end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {   
	            type = "audio";   
	        } else if (end.equals("3gp") || end.equals("mp4")) {   
	            type = "video";   
	        } else if (end.equals("jpg") || end.equals("gif") || end.equals("png")   
	                || end.equals("jpeg") || end.equals("bmp")) {   
	            type = "image";   
	        } else if (end.equals("apk")) {   
	            type = "application/vnd.android.package-archive";   
	        } else {   
	            type = "*";   
	        }   
	        if (end.equals("apk")) {   
	        } else {   
	            type += "/*";   
	        }   
	        return type;   
	    }   

}
