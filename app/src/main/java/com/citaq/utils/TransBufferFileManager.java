package com.citaq.utils;

public class TransBufferFileManager {
	
/*	private String _filename;
	
	private FileOutputStream fileOutputStream;
	private BufferedOutputStream bufferedOutputStream;
	
	private FileInputStream fileInputStream;
	private BufferedInputStream bufferedInputStream;

	private static long count = 0;
	private Object synObj = new Object();
	private ExecutorService _pool;
	
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
	
	public TransBufferFileManager(String bufferFile){
		_filename = bufferFile;
		_pool = Executors.newSingleThreadExecutor();
	}
	
	public boolean initFile(){
		try {
			fileOutputStream = new FileOutputStream(_filename,true);
			bufferedOutputStream =new BufferedOutputStream(fileOutputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void write(final byte[] bytes){
		plusCount();
		_pool.execute(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					bufferedOutputStream.write(bytes);
					minusCount();
				} catch (IOException e) {
					e.printStackTrace();
				}				
			}});
	}*/
}
