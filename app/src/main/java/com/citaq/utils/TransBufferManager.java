package com.citaq.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.citaq.aidlservice.PosService;

public class TransBufferManager {
	private ByteArrayOutputStream buffer;
	private ExecutorService _pool;
	
	private PosService service;
	
	private static TransBufferManager _transBufferManager;
	private static boolean inTransMode = false;
	
	private static long count = 0;
	private Object synObj = new Object();
	
	private void plusCount(){
		synchronized(synObj){
			++count;
		}
	}
	private void minusCount(){
		synchronized(synObj){
			--count;
		}	
	}
	

	private void delay(long delaytime){
		long startTime = System.currentTimeMillis();
		while(System.currentTimeMillis() - startTime < delaytime);
	}
	
	
	public void setTransMode(boolean flag){
		synchronized(this){
			inTransMode = flag;
			if(!flag)count = 0;
		}
	}
	
	public boolean getTransMode(){
		synchronized(this){
			return inTransMode;
		}
		
	}
	
	private TransBufferManager(){
		buffer = new ByteArrayOutputStream();
		_pool = Executors.newSingleThreadExecutor();
	}
	
	public static synchronized TransBufferManager getInstance(){
		if(_transBufferManager == null){
			_transBufferManager = new TransBufferManager();
		}
		return _transBufferManager;
	}
	
	public synchronized void write(final byte[] bytes){
		plusCount();
		_pool.execute(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					buffer.write(bytes);
					delay(bytes.length/50);
					minusCount();
					successOperation();
				} catch (IOException e) {
					if(Constants.isPrintLog){
						e.printStackTrace();
					}
					onRaiseException(-1, e.toString());
				}				
			}});
	}
	
	public byte[] getBuffer(){
		while(count>0);
		buffer.write((byte)0x0A);
		setTransMode(false);
		return buffer.toByteArray();
	}

	public void reset(){
		setTransMode(false);
		buffer.reset();
	}
	
	public long size(){
		return buffer.size();
	}
	
	//操作成功
	private void successOperation(){
		if(this.service != null){
			this.service.onSucessOperation("sucess:sucessful opetation");
		}
	}
	
	//异常
	private void onRaiseException(int code, String msg){
		if(this.service != null){
			this.service.onRaiseException(-1, msg);
		}
	}
	
	public void setService(PosService service){
		this.service = service;
	}
}
