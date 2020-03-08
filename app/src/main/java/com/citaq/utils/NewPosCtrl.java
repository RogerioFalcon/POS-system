package com.citaq.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import android.annotation.SuppressLint;

public class NewPosCtrl {
	public static final String MAIN_BOARD_SMDKV210 = "smdkv210";	
	public static final String MAIN_BOARD_RK30 = "rk30sdk";		
	public static final String MAIN_BOARD_C500 = "c500";			
	public static final String MAIN_BOARD_UNKNOW = "unknow";	
	private static String RedLightFileName = "/sys/class/gpio/gpio190/value";
	private static String BlueLightFileName = "/sys/class/gpio/gpio172/value";
	
	private static com.example.android.skeletonapp.PosCtrl mPosCtrl1 = null;
	private static com.example.gpioled.PosCtrl mPosCtrl2 = null;
	private static String model = "";
	private static boolean isRk30Board = false;
	
	@SuppressLint("DefaultLocale")
	public NewPosCtrl() {
		model = MainBoardUtil.getModel(); // android.os.Build.MODEL.toLowerCase();
	      if(model.contains(MAIN_BOARD_SMDKV210)){
	    	  mPosCtrl1 = new com.example.android.skeletonapp.PosCtrl() ;
	      }else if(model.contains(MAIN_BOARD_RK30)){
	    	  isRk30Board = true;
	      }else if(model.contains(MAIN_BOARD_C500)){
	    	  mPosCtrl2 = new com.example.gpioled.PosCtrl();
	      }
	}
	
	public boolean isRedlightOn(){
		if(mPosCtrl1 != null){
			return mPosCtrl1.isRedlightOn();
		}
		if(mPosCtrl2 != null){
			return mPosCtrl2.isRedlightOn();	
		}
		if(isRk30Board){
			return isLightOn(RedLightFileName).contains("1");
		}
		return false;
	}
	
	public boolean isYellowlightOn(){		
		if(mPosCtrl1 != null){
			return mPosCtrl1.isYellowlightOn();
		}
		if(mPosCtrl2 != null){
			return mPosCtrl2.isYellowlightOn();  
		}
		
		if(isRk30Board){
			return isLightOn(BlueLightFileName).contains("1");
		}
		return false;
		
	}
	
    public  int trunOnoffRedlight(boolean onoff){
		if(mPosCtrl1 != null){
			return mPosCtrl1.trunOnoffRedlight(onoff);
		}
		if(mPosCtrl2 != null){
			return mPosCtrl2.trunOnoffRedlight(onoff);  
		}
		if(isRk30Board){
		    if(onoff){
		    	return TurnLedOnoff(RedLightFileName,"1");
		    }else{
		    	return TurnLedOnoff(RedLightFileName,"0");    		
		    }
		}
		return 0;
    }
    
    public  int trunOnoffYellowlight(boolean onoff){

		if(mPosCtrl1 != null){
			return mPosCtrl1.trunOnoffYellowlight(onoff);
		}
		if(mPosCtrl2 != null){
			return mPosCtrl2.trunOnoffYellowlight(onoff);  
		}    	
		if(isRk30Board){
		    if(onoff){
		    	return TurnLedOnoff(BlueLightFileName,"1");
		    }else{
		    	return TurnLedOnoff(BlueLightFileName,"0");    		
		    }			
		}
		return 0;
    }
		
	private static String isLightOn(String fileName)// new board
	{		
		String str = "";
		File f_led = new File(fileName);
		try {
			FileInputStream inputStream = new FileInputStream(f_led);//temp.openFileInput(fileName);  
            byte[] bytes = new byte[1024];  
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();  
            while (inputStream.read(bytes) != -1) {  
                arrayOutputStream.write(bytes, 0, bytes.length);  
            }  
            inputStream.close();  
            arrayOutputStream.close();  
            str = new String(arrayOutputStream.toByteArray());
		} catch (Exception e) {
			if(Constants.isPrintLog){
				e.printStackTrace();
			}
		} 
		
		return str;
	}	

	private static int TurnLedOnoff(String fileName, String onff)// new board
    {
    	File f_led = new File(fileName);

    	OutputStream outLed = null;

    	byte[] b = onff.getBytes();

    	int ret = 0;
    	try 
    	{
    		outLed = new FileOutputStream(f_led);

    		outLed.write(b);
    		outLed.flush();
        	
        	ret = 1;
    	} 
    	catch(Exception e)
    	{
    		if(Constants.isPrintLog){
    			e.printStackTrace();
    		}
    	} 
    	finally
    	{
    		try
    		{
    			outLed.close();
    		} 
    		catch(Exception e)
    		{
    			if(Constants.isPrintLog){
    				e.printStackTrace();
    			}
    		}
    	}
    	return ret;
    }	

}
