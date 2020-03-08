package com.citaq.aidlservice;

import com.citaq.aidlservice.IPosCallback;
import com.citaq.aidlservice.IPosService;
import com.citaq.aidlservice.RemoteCmd;
import com.citaq.utils.Constants;
import com.citaq.utils.PrintUtil;
import com.citaq.utils.SingleThreadPoolManager;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class RemoteActivity extends Activity {

	protected static final String TAG = "MainActivity";

	private IPosService posService = null;
	private boolean inThreading = false;
	private boolean inTransThreading = false;

	private SingleThreadPoolManager pool = SingleThreadPoolManager.getInstance();
	private void waiting(){
		waiting(false);
	}

	private void waiting(boolean isTrans){
		try{
			if(isTrans){
				while(inTransThreading){
					Thread.sleep(10);
				};
			}else{
				while(inThreading){
					Thread.sleep(10);
				};
			}
		}catch(Exception e){
			if(Constants.isPrintLog){
				e.printStackTrace();
			}
		}
	}

	private IPosCallback callback = new IPosCallback.Stub() {

		@Override
		public void onReturnValue(String val) throws RemoteException {
			// TODO Auto-generated method stub
			String s = val;
			Log.d(TAG, "ssss--->" +s); 
			
		}

		@Override
		public void onRaiseException(int code, String e) throws RemoteException {
			Log.d(TAG, "eee--->" + e); 
			
		}

		@Override
		public void onSuccessOperation(String msg) throws RemoteException {
			// TODO Auto-generated method stub
			
		}		
		
	};
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		 bindService(new Intent("com.citaq.aidlservice.IPosService"),
                 serviceConnection, Context.BIND_AUTO_CREATE);
		
		
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		
		
	}
	
	private void executeCmd(String arg0, String arg1, String arg2){
		if(arg0 == null){
			return;
		}
		int fuc =-1;
		try {
		    fuc = Integer.valueOf(arg0).intValue();
		} catch (NumberFormatException e) {
		    e.printStackTrace();
		}
		
		switch (fuc) {
		case -1:
			
			break;
		case 1001:
			if(arg1 != null){
				if(!"".equals(arg1)) {//Logo print
					try {

						File image = new File(arg1);
						if (image.exists()) {
							BitmapFactory.Options bmOptions = new BitmapFactory.Options();
							Bitmap logoBmp = BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions);
							int wd = logoBmp.getWidth();
							posService.printBitmap(logoBmp, wd, 1);
						} else {
							posService.printBigText(arg1 + ": doesn't exist!");
						}
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			if(arg2 != null){
				if(!"".equals(arg2)) {//Print Text array
					waiting();
					final String jrtext = arg2;
						pool.push(new Runnable(){
							@Override
							public void run() {
								try {
									try {
										JSONArray jr = new JSONArray(jrtext);
										PrintUtil.printCustomTextByJsonArray(jr);
									} catch (JSONException e) {
										e.printStackTrace();
									}
								}catch (RemoteException ex){
									ex.printStackTrace();
							}
							}});
//							JSONArray jr = new JSONArray(jrtext);
//							PrintUtil.printCustomTextByJsonArray(jr);
				}
			}else{//Print selfChecking
				try {
					posService.printerSelfChecking();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case RemoteCmd.CMD_switchOn_blue:
			// 1- 蓝灯
			// 2- 红灯
			try {
				posService.switchOn(1, 0, 0);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case RemoteCmd.CMD_switchOff_blue:
			try {
				posService.switchOff(1);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		default:
			break;
		}
	}
	
	private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
			try {
				posService.unregisterCallback(callback);
			} catch (RemoteException e) {
				Log.d(TAG, "unregisterCallback failed"); //注销回调函数
			}
			posService = null;

        }
        
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // TODO Auto-generated method stub
        	posService = IPosService.Stub.asInterface(service);
			try {
				posService.registerCallback(callback);   // 注册回调函数
				
			} catch (RemoteException e) {
				Log.d(TAG, "registerCallback failed.");
			}
			
			try {
				String s = posService.getPrinterSerialNo();
		//		 s = posService.getPrinterModal();
			//	 posService.printerSelfChecking();
				Log.d(TAG, "sss---->"+s);
				
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           
			if (posService == null){
				Log.d(TAG, "posService is null"); 
				
				
			}else{
				
				Uri uri = getIntent().getData();
				String arg0 = uri.getQueryParameter("arg0");
				String arg1 = uri.getQueryParameter("arg1");
				String arg2 = uri.getQueryParameter("arg2");
//				String arg3 = uri.getQueryParameter("arg3");
//				String arg4 = uri.getQueryParameter("arg4");
//				String arg5 = uri.getQueryParameter("arg5");
				Log.d(TAG, "arg0--->" + arg0); 
				Log.d(TAG, "arg1--->" + arg1);
				Log.d(TAG, "arg2--->" + arg2);
//				Log.d(TAG, "arg1--->" + arg3);
//				Log.d(TAG, "arg1--->" + arg4);
//				Log.d(TAG, "arg1--->" + arg5);

				executeCmd(arg0,arg1,arg2);
				
			}
			RemoteActivity.this.onStop();
        }
        
	};


}
