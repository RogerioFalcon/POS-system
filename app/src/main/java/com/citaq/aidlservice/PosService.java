package com.citaq.aidlservice;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

import com.citaq.utils.Constants;
import com.citaq.utils.LightManager;
import com.citaq.utils.LinkQueue;
import com.citaq.utils.PicFromPrintUtils;
import com.citaq.utils.PrintUtil;
import com.citaq.utils.SingleThreadPoolManager;
import com.citaq.utils.TransBufferManager;

/**
 * CTE-POS drivers API
 * @author
 *
 */
public class PosService extends Service {
	protected LinkQueue<Byte> linkQueue = new LinkQueue<Byte>();
	
	private SingleThreadPoolManager pool = SingleThreadPoolManager.getInstance();

	//回调注册者列表
	final RemoteCallbackList <IPosCallback>mCallbacks = new RemoteCallbackList <IPosCallback>();
    
	//串口读取流
    //private InputStream mInputStream = PrintUtil.getInputStream();
		
	//private ReadThread mreadThread = null;
	private boolean isStop = false;
	private PosServiceImpl serviceImpl;
	
	private boolean is_no_read = false;
	
	final int READ_TIMEOUT = 1000;
		
	private class ReadThread extends Thread {
		
		private LinkQueue<Byte> link;
		private InputStream mInputStream;
		private boolean isnoread = false;
		
		public ReadThread(LinkQueue<Byte> link){
			super();
			this.link = link;
		}
		
		public void noread(){
			isnoread = true;
			mInputStream = null;
		}
		public void clearBuffer(){
			link.clear();
		}

		@Override
		public void run() {
			super.run();
			try{
				while (!isStop && !isInterrupted()) {
					delay(5);
					if (is_no_read || isnoread){
						PrintUtil.closeSerialPort();
						mInputStream = null;
						break;
					}else{
						mInputStream = PrintUtil.getInputStream();
						if (mInputStream == null){
							break;
						}
					}
					int size;
					byte[] buffer = new byte[1];
						size = mInputStream.read(buffer); //阻塞
					
						if(Constants.isPrintLog){
							System.out.println("--read: " + size + " bytes..." + encodeBytes(buffer));
						}
						if (size > 0) {
							link.r_push_queue(buffer[0]);
							onDataReceived(buffer, size);
						}
				}
			} catch (Exception e) {
				if(Constants.isPrintLog){
					System.out.println("--read: Exception");
					e.printStackTrace();	
				}
			}finally{
				if(Constants.isPrintLog){
					System.out.println("read thread end.");
				}
			}

		}
		public void cancel(){
			interrupt();
		}
		public LinkQueue<Byte> getLinkQueue(){
			return link;
		}
	}
	
	private int getVersionCode(){
		try{
			String pkName = this.getPackageName();
			int versionCode = this.getPackageManager().getPackageInfo(pkName, 0).versionCode;
			return versionCode;
		}catch(Exception e){
		}
		return 0;
	}
	
	private String getVersionName(){
		try{
			String pkName = this.getPackageName();
			String versionName = this.getPackageManager().getPackageInfo(pkName, 0).versionName;
			return versionName;
		}catch(Exception e){
		}
		return null;
	}
	
	private void delay(long delaytime){
		long startTime = System.currentTimeMillis();
		while(System.currentTimeMillis() - startTime < delaytime);
	}
	
	//开启读取线程
	private ReadThread startread(){

		is_no_read = false;
		//if(mInputStream == null){
		//	mInputStream = PrintUtil.getInputStream();
		//}		
		//linkQueue.clear();
		/*
		if(mreadThread == null){
			mreadThread = new ReadThread();		
			mreadThread.start();
		}
		*/
		ReadThread tmp = new ReadThread(linkQueue);	
		tmp.start();
		return tmp;
	}
	
	private void noread(ReadThread read){
		//is_no_read = true;
		if(read != null){
			read.cancel();
			read = null;
		}
	}
	
	private String encodeBytes(byte[] bytes){
		String hexString = "0123456789ABCDEF";
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
			sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
		}
		return sb.toString();
	}
	
	public byte[] getBytes(int size, int timeout, ReadThread read){
    	long startTime = System.currentTimeMillis();
    	while(read.getLinkQueue().size()<size){
    		if (System.currentTimeMillis() - startTime >= timeout) {
    			if(Constants.isPrintLog){
    				System.out.println("link time out. ");
    			}
    			read.noread();
    			read.clearBuffer();
    			noread(read);
    			PrintUtil.printBytes(new byte[]{0x10,0x04,0x01});
    			return null;
    		}    		
    	}
    	read.noread();
     	byte[] rv = new byte[size];
    	LinkQueue<Byte> link = read.getLinkQueue();
    	if(Constants.isPrintLog){
    		System.out.println("link size: " + link.size());
    	}
    	for(int i=0; i<size; i++){
    		rv[i] = link.l_pop_queue();
    	}
    	read.clearBuffer();
    	noread(read);
     	delay(400);
    	return rv;		
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		serviceImpl = new PosServiceImpl(this);
		return serviceImpl;
	}
	
    @Override  
    public boolean onUnbind(Intent intent) { 		
		is_no_read = true;
		isStop = true;

    	if(serviceImpl != null){
    		serviceImpl.destroy();
    		serviceImpl = null;
    	}
        return super.onUnbind(intent); 

    }  
    
    @Override 
    public void onRebind(Intent intent) { 
        super.onRebind(intent);   
    } 
    
	@Override
	public void onDestroy() {
		is_no_read = true;
		isStop = true;
		
    	if(serviceImpl != null){
    		serviceImpl.destroy();
    		serviceImpl = null;
    	}
		super.onDestroy();
	}

	//异常回调
	//eg: 
	//code: -1
	//e: Function is not supported
    public void onRaiseException(int code, String msg) {  
    	if(serviceImpl != null)serviceImpl.reconnect();
    	
        try {        
        	final int N = mCallbacks.beginBroadcast();   
 
            for (int i=0; i<N; i++) {           	
            	mCallbacks.getBroadcastItem(i).onRaiseException(code,msg);  
            }
        }catch (Exception ex) {   
        	if(Constants.isPrintLog){
        		ex.printStackTrace();
        	}
        }finally{
        	try{
        		mCallbacks.finishBroadcast();  
        	}catch(Exception e){
        		if(Constants.isPrintLog){
        			e.printStackTrace();
        		}
        	}
        }        
        
    }
    
	//成功
	//eg: 
	//code: -1
	//e: Function is not supported
    public void onSucessOperation(String msg) {   
        try {        
        	final int N = mCallbacks.beginBroadcast();   
 
            for (int i=0; i<N; i++) {           	
            	mCallbacks.getBroadcastItem(i).onSuccessOperation(msg);
            }
        }catch (Exception ex) {   
        	if(Constants.isPrintLog){
        		ex.printStackTrace();
        	}
        }finally{
        	try{
        		mCallbacks.finishBroadcast();  
        	}catch(Exception e){
        		if(Constants.isPrintLog){
        			e.printStackTrace();
        		}
        	}
        }        
        
    }
    
    
    //结果回调
    public void onReturnValue(String val) {   
        try {         
        	final int N = mCallbacks.beginBroadcast();
            for (int i=0; i<N; i++) {           	
            		mCallbacks.getBroadcastItem(i).onReturnValue(val); 
            }
        }catch (Exception ex) {   
        	if(Constants.isPrintLog){
        		ex.printStackTrace();
        	}
        }finally{
        	try{
        		mCallbacks.finishBroadcast();  
        	}catch(Exception e){
        		if(Constants.isPrintLog){
        			e.printStackTrace();
        		}
        	}
        } 
         
    }
    //串口收到数据处理过程
    void onDataReceived(final byte[] buffer, final int size) {}
    
	private class PosServiceImpl extends IPosService.Stub{
		private TransBufferManager outBuffer = TransBufferManager.getInstance();
		
		private boolean claimed_light = false;		
		private boolean inThreading = false;
		private boolean inTransThreading = false;
		
		private boolean isNewPrinterBoard = false;
		
		public PosServiceImpl(PosService service){
			
			outBuffer.setService(service);
			initPrinterBoard();
		}
		
		public void reconnect(){

		}
		
		public void destroy() {
			initPrintBuffer();
			releaseLight();
		}
		
		private void checkLight() throws RemoteException{
			//
		}

		private void checkPrinter() throws RemoteException{
			//
		}
		
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
				
		private boolean claimLight(int timeout) {
			// TODO Auto-generated method stub
			
			if(claimed_light){
				return true;
			}
			long t = System.currentTimeMillis()+ timeout;
			boolean rv = LightManager.claim();
			while(!rv){
				rv = LightManager.claim();
				if(System.currentTimeMillis() >= t){
					break;
				}
			}
			claimed_light = rv;
			return rv;
		}
		
		private void releaseLight(){
			// TODO Auto-generated method stub
			
			boolean rv = LightManager.release();
			long t = System.currentTimeMillis() + 1000;
			while(!rv){
				rv = LightManager.release();
				if(System.currentTimeMillis() >= t){
					break;
				}								
			}
			claimed_light = false;
		}
				
		private void initPrintBuffer(){
			if(outBuffer != null){
				outBuffer.reset();
			}
			
			pool.push(new Runnable(){
				@Override
				public void run() {
					PrintUtil.init();						
				}
			});				
		}
		
		@Override
		public int getServiceVersionCode() throws RemoteException {
		
	 		return getVersionCode();
		}
		
		@Override
		public String getServiceVersionName() throws RemoteException {
		
	 		return getVersionName();
		}
				
		@Override
		public long getMaxLights() throws RemoteException {
		
			return 2;
		}

		@Override
		public boolean switchOn(int lightNumber, int blinkOnCycle,int blinkOffCycle) throws RemoteException {

			checkLight();
			boolean rv = claimLight(100);
			if(!rv)return false;
			LightManager.trunOffLight(lightNumber);
			LightManager.switchOn(lightNumber,blinkOnCycle,blinkOffCycle);
			return true;
		}

		@Override
		public boolean switchOff(int lightNumber) throws RemoteException {

			checkLight();
			LightManager.trunOffLight(lightNumber);
			releaseLight();
			return true;
		}

		@Override
		public void openDrawer() throws RemoteException {
			// TODO Auto-generated method stub

			byte[] cmds={0x1B,0x70,0x00,0x60,0x60};
			PrintUtil.printBytes(cmds);
		}

		@Override
		public long getOpenDrawerTimes() throws RemoteException {
			
			if(!isNewPrinterBoard){
				onRaiseException(-1,"Function is not supported.");
				//return -1;
			}
			
			ReadThread read = startread();			
			byte[] cmds ={0x1D,0x28,0x42,0x02,0x00,0x00,0x35};
			PrintUtil.printBytes(cmds);
			byte[] result = getBytes(4,READ_TIMEOUT, read);
			long rv = 0;
			if(result != null){
				rv = (long) ( ((result[0] & 0xFF)<<24)  
			            |((result[1] & 0xFF)<<16)  
			            |((result[2] & 0xFF)<<8)  
			            |(result[3] & 0xFF)); 		
				if(Constants.isPrintLog){
					System.out.println("read: "+result.length + " data: " + rv);
				}
			}
			PrintUtil.closeSerialPort();
			return rv;
		}
		
		@Override
		public String getPrinterSerialNo() throws RemoteException {
			// TODO Auto-generated method stub

			if(!isNewPrinterBoard){
				onRaiseException(-1,"Function is not supported.");
				//return "";
			}

			ReadThread read = startread();
			byte[] cmds ={0x1D,0x49,0x44};
			PrintUtil.printBytes(cmds);
			byte[] result = getBytes(12, READ_TIMEOUT, read);
			String rv = "";
			if(result != null){
				rv= encodeBytes(result);
				if(Constants.isPrintLog){
					System.out.println("read: "+result.length + " data: " + rv);
				}
			}
			PrintUtil.closeSerialPort();
			return rv;
		}

		@Override
		public String getPrinterVersion() throws RemoteException {
			// TODO Auto-generated method stub

			if(!isNewPrinterBoard){
				onRaiseException(-1,"Function is not supported.");
				//return "";
			}
			
			ReadThread read = startread();
			byte[] cmds ={0x1D,0x49,0x41};
			PrintUtil.printBytes(cmds);
			byte[] result = getBytes(12, READ_TIMEOUT,read);
			String rv = "";
			if(result != null){
				rv= new String(result);
				int i = rv.indexOf(10, 0);
				if(i > 0){
					rv = rv.substring(0, i);
				}
				if(Constants.isPrintLog){
					System.out.println("read: "+result.length + " data: " + rv);
				}
			}
			PrintUtil.closeSerialPort();
			return rv;
		}
		
		private void initPrinterBoard() {
			System.out.println("service varsion: "+ getVersionName());
				
			ReadThread read = startread();
			byte[] cmds ={0x1D,0x49,0x43};
			PrintUtil.printBytes(cmds);
			byte[] result = getBytes(12, READ_TIMEOUT,read);
			//String rv = "";
			if(result != null){
				isNewPrinterBoard = true;
			}else{
				isNewPrinterBoard = false;
			}
			PrintUtil.closeSerialPort();
		}
		
		@Override
		public String getPrinterModal() throws RemoteException {
			// TODO Auto-generated method stub

			if(!isNewPrinterBoard){
				onRaiseException(-1,"Function is not supported.");
				//return "";
			}
			
			ReadThread read = startread();
			byte[] cmds ={0x1D,0x49,0x43};
			PrintUtil.printBytes(cmds);
			byte[] result = getBytes(12, READ_TIMEOUT, read);
			String rv = "";
			if(result != null){
				rv= new String(result);
				int i = rv.indexOf(10, 0);
				if(i > 0){
					rv = rv.substring(0, i);
				}
				if(Constants.isPrintLog){
					System.out.println("read: "+result.length + " data: " + rv);
				}
			}
			PrintUtil.closeSerialPort();
			return rv;
		}

		@Override
		public boolean capCutPaper() throws RemoteException {
			// TODO Auto-generated method stub

			return true;
		}

		@Override
		public void cutPaper() throws RemoteException {
					
			waiting();			
			checkPrinter();			
			final byte[] cmds = PrintUtil.cutPaper();
			pool.push(new Runnable(){
				@Override
				public void run() {
					if(PrintUtil.printBytes(cmds)){
						delay(50);
						onSucessOperation("sucess:sucessful opetation");
					}else{
						onRaiseException(-1,"failure! device not prepare.");
					}
					PrintUtil.closeSerialPort();
				}});
		}

		@Override
		public long getCutPaperTimes() throws RemoteException {
			
			if(!isNewPrinterBoard){
				onRaiseException(-1,"Function is not supported.");
				//return -1;
			}
			
			byte[] cmds ={0x1D,0x28,0x42,0x02,0x00,0x00,0x07};
			ReadThread read = startread();
			PrintUtil.printBytes(cmds);
			byte[] result = getBytes(4, READ_TIMEOUT,read);
			PrintUtil.closeSerialPort();
			long rv = 0;
			if(result != null){
				rv = (long) ( ((result[0] & 0xFF)<<24)  
			            |((result[1] & 0xFF)<<16)  
			            |((result[2] & 0xFF)<<8)  
			            |(result[3] & 0xFF)); 		
				if(Constants.isPrintLog){
					System.out.println("read: "+result.length + " data: " + rv);
				}
			}
			return rv;
		}

		@Override
		public long getPrintedLength() throws RemoteException {
			// TODO Auto-generated method stub
			
			if(!isNewPrinterBoard){
				onRaiseException(-1,"Function is not supported.");
				//return -1;
			}
			
			ReadThread read = startread();
			byte[] cmds ={0x1D,0x28,0x42,0x02,0x00,0x00,0x36};
			PrintUtil.printBytes(cmds);
			byte[] result = getBytes(4, READ_TIMEOUT,read);
			PrintUtil.closeSerialPort();
			long rv = 0;
			if(result != null){
				rv = (long) ( ((result[0] & 0xFF)<<24)  
			            |((result[1] & 0xFF)<<16)  
			            |((result[2] & 0xFF)<<8)  
			            |(result[3] & 0xFF)); 		
				if(Constants.isPrintLog){
					System.out.println("read: "+result.length + " data: " + rv);
				}
			}
			return rv;
		}

		@Override
		public int getPrtLineChars() throws RemoteException {
			// TODO Auto-generated method stub
			
			return 48;
		}

		@Override
		public void printBarCode(String data, int symbology, int height,
				int width, int alignment, int textposition)
				throws RemoteException {
			// TODO Auto-generated method stub
			if(!isNewPrinterBoard){
				if(symbology != 4 && symbology !=8){
					onRaiseException(-1,"Function is not supported.");
					//return;
				}
			}

			waiting();
			final String[] na = new String[3];
			na[0] = " l";
			na[1] = " c";
			na[2] = " r";
						
			checkPrinter();
			if(alignment <0 || alignment > 3){
				alignment = 0;
			}		
			
			if(width < 2 || width > 6){
				width = 2;
			}
			if(textposition <0 || textposition > 3){
				textposition = 0;
			}
			if(height < 1 || height>255){
				height = 162;
			}
			final byte[] dalign = PrintUtil.setAlignCenter(alignment);	//对齐方式
			
			final byte[] dtmp = new byte[13];
			dtmp[0] = 0x1D;
			dtmp[1] = 0x66;
			dtmp[2] = 0x01;
			
			dtmp[3] = 0x1D;
			dtmp[4] = 0x48;
			dtmp[5] = (byte)textposition;
			
			dtmp[6] = 0x1D;
			dtmp[7] = 0x77;
			dtmp[8] = (byte)width;
			
			dtmp[9] = 0x1D;
			dtmp[10] = 0x68;
			dtmp[11] = (byte)height;
			
			dtmp[12]= 10;
			
			byte[] barcode = data.getBytes();
			int pos = 0;
			
			final byte[] databytes = new byte[barcode.length + 6];
			databytes[0] = 0x1D;
			databytes[1] = 0x6B;
			
			switch(symbology){
			case 0:
				databytes[2] = 0x41;
				databytes[3] = (byte)barcode.length;
				pos = 4;
				break;
			case 1:
				databytes[2] = 0x42;
				databytes[3] = (byte)barcode.length;
				pos = 4;

				break;
			case 2:
				databytes[2] = 0x43;
				databytes[3] = (byte)barcode.length;
				pos = 4;

				break;
			case 3:
				databytes[2] = 0x44;
				databytes[3] = (byte)barcode.length;
				pos = 4;

				break;
			case 4:
				databytes[2] = 0x45;
				databytes[3] = (byte)barcode.length;
				pos = 4;

				break;
			case 5:
				databytes[2] = 0x46;
				databytes[3] = (byte)barcode.length;
				pos = 4;

				break;
			case 6:
				databytes[2] = 0x47;
				databytes[3] = (byte)barcode.length;
				pos = 4;

				break;
			case 7:
				databytes[2] = 0x48;
				databytes[3] = (byte)barcode.length;
				pos = 4;

				break;
			case 8:
				databytes[2] = 0x49;
				databytes[3] = (byte)(barcode.length+2);
				databytes[4] = 0x7B;
				databytes[5] = 0x42;
				pos = 6;

				break;
			default:
				break;
			}
			System.arraycopy(barcode, 0, databytes, pos,
					barcode.length);
			
			pool.push(new Runnable(){
				@Override
				public void run() {
					boolean v = PrintUtil.printBytes(dalign);
					v &= PrintUtil.printBytes(dtmp);
					v &= PrintUtil.printBytes(databytes);
					if(v){
						//delay(100);
						onSucessOperation("sucess:sucessful opetation");
					}else{
						onRaiseException(-1,"failure! device not prepare.");
					}
					PrintUtil.closeSerialPort();
				}});								
			
		}

		@Override
		public void printBitmap(final Bitmap bitmap, final int width, final int alignment) throws RemoteException {
			// TODO Auto-generated method stub
			//读取图片
			checkPrinter();
			waiting();
			if(bitmap != null){
				inThreading = true;
				final Bitmap cpbmp = PicFromPrintUtils.compressPic(bitmap, width);
				
				final byte[] data = PicFromPrintUtils.rasterDataFromBitmap(cpbmp, alignment);
				pool.push(new Runnable(){
					@Override
					public void run() {
						if(PrintUtil.printBytes(data)){
							if(Constants.isPrintLog){
								System.out.println("bmp size:"+data.length);
							}
							//delay(20);
							onSucessOperation("sucess:sucessful opetation");
						}else{
							onRaiseException(-1,"failure! device not prepare.");
						}
						PrintUtil.closeSerialPort();
					}});				
				inThreading = false;
			}		
		}

		@Override
		public void printNormalText(String data) throws RemoteException {
			// TODO Auto-generated method stub
			waiting();
			final String d = data;
			
			checkPrinter();
			final byte[] cmds = PrintUtil.setWH(1);
			pool.push(new Runnable(){
				@Override
				public void run() {
					boolean v = PrintUtil.printBytes(cmds);
					v &= PrintUtil.printString(d+"\n");
					if(v){
						//delay(100);
						onSucessOperation("sucess:sucessful opetation");
					}else{
						onRaiseException(-1,"failure! device not prepare.");
					}
					PrintUtil.closeSerialPort();
				}});				
		}
		@Override
		public void printBigText(String data) throws RemoteException {
			// TODO Auto-generated method stub
			waiting();
			
			final String d = data;		
			
			checkPrinter();
			final byte[] cmds = PrintUtil.setWH(4);
			pool.push(new Runnable(){
				@Override
				public void run() {
					boolean v = PrintUtil.printBytes(cmds);
					v &= PrintUtil.printString(d+"\n");
					if(v){
						//delay(100);
						onSucessOperation("sucess:sucessful opetation");
					}else{
						onRaiseException(-1,"failure! device not prepare.");
					}
					PrintUtil.closeSerialPort();
				}});						
		}

		@Override
		public void printDoubleWidthText(String data) throws RemoteException {
			// TODO Auto-generated method stub
			waiting();
			final String d = data;
			
			checkPrinter();
			final byte[] cmds = PrintUtil.setWH(2);

			pool.push(new Runnable(){
				@Override
				public void run() {
					boolean v = PrintUtil.printBytes(cmds);
					v &= PrintUtil.printString(d+"\n");
					if(v){
						//delay(100);
						onSucessOperation("sucess:sucessful opetation");
					}else{
						onRaiseException(-1,"failure! device not prepare.");
					}
					PrintUtil.closeSerialPort();
				}});				
						
		}

		@Override
		public void printDoubleHeightText(String data) throws RemoteException {
			// TODO Auto-generated method stub
			waiting();
			final String d = data;
			
			checkPrinter();
			final byte[] cmds = PrintUtil.setWH(3);

			pool.push(new Runnable(){
				@Override
				public void run() {
					boolean v = PrintUtil.printBytes(cmds);
					v &= PrintUtil.printString(d+"\n");
					if(v){
						//delay(100);
						onSucessOperation("sucess:sucessful opetation");
					}else{
						onRaiseException(-1,"failure! device not prepare.");
					}
					PrintUtil.closeSerialPort();
				}});				
						
		}

		@Override
		public void printRAWData(final byte[] data) throws RemoteException {
			// TODO Auto-generated method stub
			waiting();

			checkPrinter();

			pool.push(new Runnable(){
				@Override
				public void run() {
					if(PrintUtil.printBytes(data)){
						//long t = data.length/8;
						//if(t<100)t=100;
						//delay(t);
						onSucessOperation("sucess:sucessful opetation");
					}else{
						onRaiseException(-1,"failure! device not prepare.");
					}
					PrintUtil.closeSerialPort();
				}});				
						
		}

		@Override
		public boolean openLanDebug() throws RemoteException {
			boolean b = execShell("setprop service.adb.tcp.port 5555");
			b = b & execShell("stop adbd");
			b = b & execShell("start adbd");
			return b;
		}

		@Override
		public boolean closeLanDebug() throws RemoteException {
			return execShell("stop adbd");
		}
		
		private boolean execShell(String str) {
			try {
				//取得root权限
				Process p = Runtime.getRuntime().exec("su");

				OutputStream outputStream = p.getOutputStream();
				DataOutputStream dataOutputStream = new DataOutputStream(
						outputStream);

				dataOutputStream.writeBytes(str);

				dataOutputStream.flush();

				dataOutputStream.close();
				outputStream.close();
			} catch (Throwable t) {
				if(Constants.isPrintLog){
					t.printStackTrace();
				}
				return false;
			}
			return true;
		}

		@Override
		public void printerSelfChecking() throws RemoteException {
			// TODO Auto-generated method stub

			waiting();

			checkPrinter();
			pool.push(new Runnable(){
				@Override
				public void run() {
					byte[] data ={0x1F, 0x1B, 0x1F, 0x53};
					if(PrintUtil.printBytes(data)){
						//delay(2500);
						onSucessOperation("sucess:sucessful opetation");
					}else{
						onRaiseException(-1,"failure! device not prepare.");
					}
					PrintUtil.closeSerialPort();
				}});				
			
		}

		@Override
		public void registerCallback(IPosCallback cb) throws RemoteException {
			if(Constants.isPrintLog){
				System.out.println("register...");
			}
            if (cb != null) { 
                mCallbacks.register(cb);
            }else{
            	if(Constants.isPrintLog){
            		System.out.println("register...null");
            	}
            }
		}

		@Override
		public void unregisterCallback(IPosCallback cb)
				throws RemoteException {
            if(cb != null) {  
                mCallbacks.unregister(cb);  
            } 			
		}

		@Override
		public void printQRCode(String data, int modulesize, int errorlevel,
				int alignment) throws RemoteException {
			
			checkPrinter();
			waiting();

			String[] na = new String[3];
			na[0] = " l";
			na[1] = " c";
			na[2] = " r";
			
			
			
			if(alignment <0 || alignment > 3){
				alignment = 0;
			}
			if(modulesize < 1 || modulesize > 16){
				modulesize = 4;
			}
			if(errorlevel <0 || errorlevel > 3){
				errorlevel = 0;
			}
			
			if(!isNewPrinterBoard){
				onRaiseException(-1,"Function is not supported.");
				return;
			}
			
			final byte[] dalign = PrintUtil.setAlignCenter(alignment);	//对齐方式
			
			byte[] barcode = data.getBytes();
			int length = barcode.length+3;		//length<= 7092
			if(length>7092){
				length = 7092;
			}
			
			final byte[] dtmp = new byte[length + 21];
			
			dtmp[0] = 0x1D;				//二维码块大小设置指令
			dtmp[1] = 0x28;
			dtmp[2] = 0x6B;
			dtmp[3] = 0x03;
			dtmp[4] = 0x00;
			dtmp[5] = 0x31;
			dtmp[6] = 0x43;
			dtmp[7] = (byte)modulesize;
			
			dtmp[8] = 0x1D;				//二维码纠错等级设置指令
			dtmp[9] = 0x28;
			dtmp[10] = 0x6B;
			dtmp[11] = 0x03;
			dtmp[12] = 0x00;
			dtmp[13] = 0x31;
			dtmp[14] = 0x45;
			dtmp[15] = (byte)(48+errorlevel);
			
			dtmp[16] = 0x1D;			//二维码存入指令
			dtmp[17] = 0x28;
			dtmp[18] = 0x6B;
			dtmp[19] = (byte)length;
			dtmp[20] = (byte)(length >> 8);
			dtmp[21] = 0x31;
			dtmp[22] = 0x50;
			dtmp[23] = 0x30;
			//此处接条码数据字节数组
			System.arraycopy(barcode, 0, dtmp, 24,
					length-3);
					
			final byte[] cmds = new byte[8];
			cmds[0] = 0x1D;				//打印已存入数据的二维码
			cmds[1] = 0x28;
			cmds[2] = 0x6B;
			cmds[3] = 0x03;
			cmds[4] = 0x00;
			cmds[5] = 0x31;
			cmds[6] = 0x51;
			cmds[7] = 0x30;			
			//cmds[8] = 0x0A;	
			
			
			final byte[] barcode2 = new byte[length + 5];
			System.arraycopy(dtmp, 16, barcode2, 0,
					length + 5);
			
			pool.push(new Runnable(){
				@Override
				public void run() {
					boolean v = PrintUtil.printBytes(dalign);
					v &= PrintUtil.printBytes(dtmp);
					//long t = dtmp.length/8;
					//if(t<100)t=100;
					//delay(t);
					v &= PrintUtil.printBytes(cmds);
					
					byte[] pos = new byte[4];
					pos[0] = 0x1B;
					pos[1] = 0x5C;
					pos[2] = 0x30;
					pos[3] = 0;
					
					v &= PrintUtil.printBytes(pos);
					
					v &= PrintUtil.printBytes(barcode2);
					v &= PrintUtil.printBytes(cmds);
					v &= PrintUtil.printString("\n");
					
					delay(20);
					if(v){
						onSucessOperation("sucess:sucessful opetation");
					}else{
						onRaiseException(-1,"failure! device not prepare.");
					}
					PrintUtil.closeSerialPort();
				}});				

		}

		@Override
		public void printZoomText(String data, int zoom)
				throws RemoteException {
			waiting();
			final String d = data;
			
			//if(!isNewPrinterBoard){
			//	if(zoom > 1)zoom=1;
			//}			
			checkPrinter();
			final byte[] cmds = PrintUtil.setZoom(zoom);
			pool.push(new Runnable(){
				@Override
				public void run() {
					boolean v = PrintUtil.printBytes(cmds);
					v &= PrintUtil.printString(d+" \n");
					byte[] tmp = PrintUtil.setWH(1);
					PrintUtil.printBytes(tmp);
					if(v){
						//delay(100);
						onSucessOperation("sucess:sucessful opetation");
					}else{
						onRaiseException(-1,"failure! device not prepare.");
					}
					PrintUtil.closeSerialPort();
				}});				
						
		}

		//事务打印接口

		@Override
		public boolean beginTransactionPrint() throws RemoteException {
			// TODO Auto-generated method stub
			
			if(outBuffer.getTransMode()){
				delay(2000);
			}
			checkPrinter();
			initPrintBuffer();
			outBuffer.setTransMode(true);
			return true;
		}

		@Override
		public boolean cancelTransactionPrint() throws RemoteException {
			// TODO Auto-generated method stub
			
			checkPrinter();
			initPrintBuffer();
			return true;
		}

		@Override
		public boolean endTransactionPrint() throws RemoteException {
			// TODO Auto-generated method stub
			
			checkPrinter();
			pool.push(new Runnable(){
				@Override
				public void run() {
					//long t =outBuffer.size();
					if(PrintUtil.printBytes(outBuffer.getBuffer())){
						PrintUtil.printBytes(new byte[]{32,10});
						outBuffer.setTransMode(false);
						//System.out.println("buffer size:" + t);
						//t = t/5;
						//if(t<100)t=100;	
						//delay(t);
						initPrintBuffer();
						onSucessOperation("sucess:sucessful opetation");
					}else{
						PrintUtil.printBytes(new byte[]{32,10});
						onRaiseException(-1,"failure! device not prepare.");
					}
					PrintUtil.closeSerialPort();
				}});				

			return true;
		}

		@Override
		public boolean setPrintAlignment(int alignment) throws RemoteException {
			// TODO Auto-generated method stub
			
			checkPrinter();
			if(outBuffer.getTransMode()){
				waiting(true);
					outBuffer.write(PrintUtil.setAlignCenter(alignment));
				return true;
			}				
			return false;
		}
		public boolean setBold(boolean bBold) throws RemoteException {
			// TODO Auto-generated method stub

			checkPrinter();
			if(outBuffer.getTransMode()){
				waiting(true);
				outBuffer.write(PrintUtil.setBold(bBold));
				return true;
			}
			return false;
		}
		@Override
		public boolean setPrintPos(int pos) throws RemoteException {
			// TODO Auto-generated method stub
			
			checkPrinter();
			if(outBuffer.getTransMode()){
				waiting(true);
					outBuffer.write(PrintUtil.setCusorPosition(pos * 12));
				return true;
			}
			return false;
		}

		@Override
		public boolean setBlackWhiteMode(int mode) throws RemoteException {
			
			checkPrinter();
			if(outBuffer.getTransMode()){
				waiting(true);
					outBuffer.write(PrintUtil.setBlackWhiteMode(mode));
				return true;
			}else{
				return false;
			}
		}		
		
		
		@Override
		public boolean setUnderline(int mode) throws RemoteException {
			// TODO Auto-generated method stub
			
			checkPrinter();
			if(outBuffer.getTransMode()){
				waiting(true);
				outBuffer.write(PrintUtil.setUnderline(mode));
				return true;
			}else{
				return false;
			}
		}
		
		@Override
		public boolean trans_cutPaper() throws RemoteException {
			
			checkPrinter();			
			if(outBuffer.getTransMode()){
				waiting(true);
				outBuffer.write(PrintUtil.cutPaper());
				return true;
			}
			return false;
		}

		@Override
		public boolean trans_printBarCode(String data, int symbology,
				int height, int width, int alignment, int textposition)
				throws RemoteException {
			if(!isNewPrinterBoard){
				if(symbology != 4 && symbology !=8){
					onRaiseException(-1,"Function is not supported.");
					//return false;
				}
			}
			waiting(true);
						
			checkPrinter();
			if(alignment <0 || alignment > 3){
				alignment = 0;
			}
			
			if(width < 2 || width > 6){
				width = 2;
			}
			if(textposition <0 || textposition > 3){
				textposition = 0;
			}
			if(height < 1 || height>255){
				height = 162;
			}
			final byte[] dalign = PrintUtil.setAlignCenter(alignment);	//对齐方式
			
			final byte[] dtmp = new byte[13];
			dtmp[0] = 0x1D;
			dtmp[1] = 0x66;
			dtmp[2] = 0x01;
			
			dtmp[3] = 0x1D;
			dtmp[4] = 0x48;
			dtmp[5] = (byte)textposition;
			
			dtmp[6] = 0x1D;
			dtmp[7] = 0x77;
			dtmp[8] = (byte)width;
			
			dtmp[9] = 0x1D;
			dtmp[10] = 0x68;
			dtmp[11] = (byte)height;
			
			dtmp[12]= 10;
			
			byte[] barcode = data.getBytes();
			int pos = 0;
			
			final byte[] databytes = new byte[barcode.length + 6];
			databytes[0] = 0x1D;
			databytes[1] = 0x6B;
			
			switch(symbology){
			case 0:
				databytes[2] = 0x41;
				databytes[3] = (byte)barcode.length;
				pos = 4;
				break;
			case 1:
				databytes[2] = 0x42;
				databytes[3] = (byte)barcode.length;
				pos = 4;

				break;
			case 2:
				databytes[2] = 0x43;
				databytes[3] = (byte)barcode.length;
				pos = 4;

				break;
			case 3:
				databytes[2] = 0x44;
				databytes[3] = (byte)barcode.length;
				pos = 4;

				break;
			case 4:
				databytes[2] = 0x45;
				databytes[3] = (byte)barcode.length;
				pos = 4;

				break;
			case 5:
				databytes[2] = 0x46;
				databytes[3] = (byte)barcode.length;
				pos = 4;

				break;
			case 6:
				databytes[2] = 0x47;
				databytes[3] = (byte)barcode.length;
				pos = 4;

				break;
			case 7:
				databytes[2] = 0x48;
				databytes[3] = (byte)barcode.length;
				pos = 4;

				break;
			case 8:
				databytes[2] = 0x49;
				databytes[3] = (byte)(barcode.length+2);
				databytes[4] = 0x7B;
				databytes[5] = 0x42;
				pos = 6;

				break;
			default:
				break;
			}
			System.arraycopy(barcode, 0, databytes, pos,
					barcode.length);

			if(outBuffer.getTransMode()){
					waiting(true);
					outBuffer.write(dalign);
					outBuffer.write(dtmp);
					outBuffer.write(databytes);
					return true;
			}			
			return false;
		}

		@Override
		public boolean trans_printBitmap(Bitmap bitmap, int width, int alignment)
				throws RemoteException {
			checkPrinter();
			waiting(true);
			if(bitmap != null){
				inTransThreading = true;
				final Bitmap cpbmp = PicFromPrintUtils.compressPic(bitmap, width);
				
				final byte[] data = PicFromPrintUtils.rasterDataFromBitmap(cpbmp, alignment);
				
				if(outBuffer.getTransMode()){							
						outBuffer.write(data);
						inTransThreading = false;
						return true;
				}
				inTransThreading = false;
			}		
			return false;
		}

		@Override
		public boolean trans_printNormalText(String data)
				throws RemoteException {
			waiting(true);
			
			checkPrinter();
			final byte[] cmds = PrintUtil.setWH(1);
			if(outBuffer.getTransMode()){
				outBuffer.write(cmds);
				outBuffer.write(PrintUtil.getGbk(data));
				return true;
			}			
			return false;
		}

		@Override
		public boolean trans_printBigText(String data) throws RemoteException {
			waiting(true);
			
			checkPrinter();
			final byte[] cmds = PrintUtil.setWH(4);
			if(outBuffer.getTransMode()){
				outBuffer.write(cmds);
				outBuffer.write(PrintUtil.getGbk(data));
				return true;
			}			
			return false;
		}

		@Override
		public boolean trans_printDoubleWidthText(String data)
				throws RemoteException {
			waiting(true);
			
			checkPrinter();
			final byte[] cmds = PrintUtil.setWH(2);
			if(outBuffer.getTransMode()){
				outBuffer.write(cmds);
				outBuffer.write(PrintUtil.getGbk(data));
				return true;
			}			
			return false;
		}

		@Override
		public boolean trans_printDoubleHeightText(String data)
				throws RemoteException {
			waiting(true);
			
			checkPrinter();
			final byte[] cmds = PrintUtil.setWH(3);
			if(outBuffer.getTransMode()){
				outBuffer.write(cmds);
				outBuffer.write(PrintUtil.getGbk(data));
				return true;
			}			
			return false;
		}

		@Override
		public boolean trans_printRAWData(byte[] data) throws RemoteException {
			waiting(true);

			checkPrinter();
			if(outBuffer.getTransMode()){
				waiting(true);
				outBuffer.write(data);
				return true;
			}			
			return false;
		}

		@Override
		public boolean trans_printZoomText(String data, int zoom)
				throws RemoteException {
			waiting(true);
						
			//if(!isNewPrinterBoard){
			//	if(zoom > 1)zoom=1;
			//}			
			checkPrinter();
			final byte[] cmds = PrintUtil.setZoom(zoom);
			if(outBuffer.getTransMode()){
				waiting(true);
					outBuffer.write(cmds);
					outBuffer.write(PrintUtil.getGbk(data));
					byte[] tmp = PrintUtil.setWH(1);
					outBuffer.write(tmp);
					return true;
			}				
			return false;
		}

		@Override
		public boolean trans_printQRCode(String data, int modulesize,
				int errorlevel, int alignment) throws RemoteException {
			if(!isNewPrinterBoard){
				onRaiseException(-1,"Function is not supported.");
				return false;
			}

			checkPrinter();
			waiting(true);

			if(alignment <0 || alignment > 3){
				alignment = 0;
			}
			if(modulesize < 1 || modulesize > 16){
				modulesize = 4;
			}
			if(errorlevel <0 || errorlevel > 3){
				errorlevel = 0;
			}
									
			final byte[] dalign = PrintUtil.setAlignCenter(alignment);	//对齐方式
			
			byte[] barcode = data.getBytes();
			int length = barcode.length+3;		//length<= 7092
			if(length>7092){
				length = 7092;
			}
			
			final byte[] dtmp = new byte[length + 21];
			
			dtmp[0] = 0x1D;				//二维码块大小设置指令
			dtmp[1] = 0x28;
			dtmp[2] = 0x6B;
			dtmp[3] = 0x03;
			dtmp[4] = 0x00;
			dtmp[5] = 0x31;
			dtmp[6] = 0x43;
			dtmp[7] = (byte)modulesize;
			
			dtmp[8] = 0x1D;				//二维码纠错等级设置指令
			dtmp[9] = 0x28;
			dtmp[10] = 0x6B;
			dtmp[11] = 0x03;
			dtmp[12] = 0x00;
			dtmp[13] = 0x31;
			dtmp[14] = 0x45;
			dtmp[15] = (byte)(48+errorlevel);
			
			dtmp[16] = 0x1D;			//二维码存入指令
			dtmp[17] = 0x28;
			dtmp[18] = 0x6B;
			dtmp[19] = (byte)length;
			dtmp[20] = (byte)(length >> 8);
			dtmp[21] = 0x31;
			dtmp[22] = 0x50;
			dtmp[23] = 0x30;
			//此处接条码数据字节数组
			System.arraycopy(barcode, 0, dtmp, 24,
					length-3);
					
			final byte[] cmds = new byte[9];
			cmds[0] = 0x1D;				//打印已存入数据的二维码
			cmds[1] = 0x28;
			cmds[2] = 0x6B;
			cmds[3] = 0x03;
			cmds[4] = 0x00;
			cmds[5] = 0x31;
			cmds[6] = 0x51;
			cmds[7] = 0x30;			
			cmds[8] = 0x0A;	
			
			if(outBuffer.getTransMode()){
				waiting(true);
				outBuffer.write(dalign);
				outBuffer.write(dtmp);
				outBuffer.write(cmds);					
				return true;
			}
			return false;
		}

		@Override
		public boolean printZQRCode(String url, int size, int align)
				throws RemoteException {

			waiting();			
			checkPrinter();			
			final byte[] data = PicFromPrintUtils.createRealQRBytes(url, size, align);
			pool.push(new Runnable(){
				@Override
				public void run() {
					if(PrintUtil.printBytes(data)){
						//delay(10);
						onSucessOperation("sucess:sucessful opetation");
					}else{
						onRaiseException(-1,"failure! device not prepare.");
					}
					PrintUtil.closeSerialPort();
				}});
			
			return true;
		}

		@Override
		public boolean printDoubleZQRCode(String leftUrl, String rightUrl)
				throws RemoteException {
			
			checkPrinter();	
			waiting();
			Bitmap bm1 = PicFromPrintUtils.createQRImage(leftUrl, 3);
			Bitmap bm2 = PicFromPrintUtils.createQRImage(rightUrl, 3);
			Bitmap bm = PicFromPrintUtils.doubleBitmap(bm1, bm2);
			final byte[] data = PicFromPrintUtils.rasterDataFromBitmap(bm, 1);
			pool.push(new Runnable(){
				@Override
				public void run() {
					if(PrintUtil.printBytes(data)){
						//delay(10);
						onSucessOperation("sucess:sucessful opetation");
					}else{
						onRaiseException(-1,"failure! device not prepare.");
					}
					PrintUtil.closeSerialPort();
				}});
			
			return false;
		}

		@Override
		public boolean printZQRCodeImage(String url, Bitmap bitmap)
				throws RemoteException {
			
			checkPrinter();	
			waiting();
			Bitmap bm1 = PicFromPrintUtils.createQRImage(url, 3);
			Bitmap bm = PicFromPrintUtils.doubleBitmap(bm1, bitmap);
			final byte[] data = PicFromPrintUtils.rasterDataFromBitmap(bm, 1);
			pool.push(new Runnable(){
				@Override
				public void run() {
					if(PrintUtil.printBytes(data)){
						//delay(10);
						onSucessOperation("sucess:sucessful opetation");
					}else{
						onRaiseException(-1,"failure! device not prepare.");
					}
					PrintUtil.closeSerialPort();
				}});
			
			return false;
		}

		@Override
		public boolean lineWrap(final int lines) throws RemoteException {
			waiting();
			
			checkPrinter();
			waiting();
			final byte[] cmds = PrintUtil.lineWrap(lines);
			pool.push(new Runnable(){
				@Override
				public void run() {
					boolean v = PrintUtil.printBytes(cmds);
					//delay(10);
					if(v){
						onSucessOperation("sucess:sucessful opetation");
					}else{
						onRaiseException(-1,"failure! device not prepare.");
					}
					PrintUtil.closeSerialPort();
				}});
			return true;
		}

		@Override
		public boolean trans_printZQRCode(String url, int size, int align)
				throws RemoteException {
			
			checkPrinter();	
			waiting();
			final byte[] data = PicFromPrintUtils.createRealQRBytes(url, size, align);
			if(outBuffer.getTransMode()){
				outBuffer.write(data);
				return true;
			}			
			return false;
		}

		@Override
		public boolean trans_printDoubleZQRCode(String leftUrl, String rightUrl)
				throws RemoteException {
			
			checkPrinter();
			waiting();
			Bitmap bm1 = PicFromPrintUtils.createQRImage(leftUrl, 3);
			Bitmap bm2 = PicFromPrintUtils.createQRImage(rightUrl, 3);
			Bitmap bm = PicFromPrintUtils.doubleBitmap(bm1, bm2);
			final byte[] data = PicFromPrintUtils.rasterDataFromBitmap(bm, 1);
			if(outBuffer.getTransMode()){
				outBuffer.write(data);
				return true;
			}			
			return false;
		}

		@Override
		public boolean trans_printZQRCodeImage(String url, Bitmap bitmap)
				throws RemoteException {
			
			checkPrinter();	
			waiting();
			Bitmap bm1 = PicFromPrintUtils.createQRImage(url, 3);
			Bitmap bm = PicFromPrintUtils.doubleBitmap(bm1, bitmap);
			final byte[] data = PicFromPrintUtils.rasterDataFromBitmap(bm, 1);
			if(outBuffer.getTransMode()){
				outBuffer.write(data);
				return true;
			}			
			return false;
		}

		@Override
		public boolean trans_lineWrap(int lines) throws RemoteException {
			waiting(true);
			
			checkPrinter();
			waiting();
			final byte[] cmds = PrintUtil.lineWrap(lines);
			if(outBuffer.getTransMode()){
				outBuffer.write(cmds);
				return true;
			}			
			return false;		
		}

		@Override
		public boolean switchOnTimes(int lightNumber, int blinkOnCycle,
				int blinkOffCycle, int times) throws RemoteException {

			checkLight();
			boolean rv = claimLight(100);
			if(!rv)return false;
			LightManager.trunOffLight(lightNumber);
			LightManager.switchOn(lightNumber,blinkOnCycle,blinkOffCycle,times);
			return true;
		}

		@Override
		public boolean printColumnsText(String[] colsText, int[] colsWidth,
				int[] colsAlign) throws RemoteException {

			if(colsText == null || colsWidth == null || colsAlign == null){
				return false;
			}
			if(colsText.length != colsWidth.length || colsText.length != colsAlign.length){
				return false;
			}
			int width = 0;
			for(int i=0; i<colsWidth.length; i++){
				if(colsWidth[i]<=0)return false;
				width += colsWidth[i];
			}
			if(width > getPrtLineChars()){
				return false;
			}
			final byte[] data = PrintUtil.getColumnsText(colsText, colsWidth, colsAlign);
			
			//System.out.println("data:"+encodeBytes(data));
			
			pool.push(new Runnable(){
				@Override
				public void run() {
					boolean v = PrintUtil.printBytes(data);
					//v &= PrintUtil.printString("\n");
					if(v){
						onSucessOperation("sucess:sucessful opetation");
					}else{
						onRaiseException(-1,"failure! device not prepare.");
					}
					PrintUtil.closeSerialPort();
				}});			
			return true;
		}

		@Override
		public boolean trans_printColumnsText(String[] colsText,
				int[] colsWidth, int[] colsAlign) throws RemoteException {

			if(colsText == null || colsWidth == null || colsAlign == null){
				return false;
			}
			if(colsText.length != colsWidth.length || colsText.length != colsAlign.length){
				return false;
			}
			int width = 0;
			for(int i=0; i<colsWidth.length; i++){
				if(colsWidth[i]<=0)return false;
				width += colsWidth[i];
			}
			if(width > getPrtLineChars()){
				return false;
			}
			final byte[] data = PrintUtil.getColumnsText(colsText, colsWidth, colsAlign);
			if(outBuffer.getTransMode()){
				waiting(true);
				outBuffer.write(data);
				return true;
			}			
			return false;
		}		
	}
}
