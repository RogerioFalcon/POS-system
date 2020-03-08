package com.citaq.utils;

import android.util.Log;

public class LightManager {
	private static NewPosCtrl mPosCtrlOp;
	// 灯泡控制全局变量
	private final static int BLUELIGHT = 1;// 蓝灯
	private final static int REDLIGHT = 2;// 红灯
	
    private static class LightThread extends Thread{
    	private int num;
    	
    	private boolean lightFlag = false;// 控制变量
    	
    	private long blinkOnCycle = 0;// 灯亮时间
    	private long blinkOffCycle = 0;// 灯关时间 
    	private int recycleTimes = 0;
    	
    	private boolean isLightFresh = false; //是否闪烁
    	private boolean isRecycleTimes = false;
  		private	long startTime;
  	 	
    	public LightThread(int num){
     		super();
     		this.num = num;
    	}
    	public void setFlag(boolean flag, long on, long off, int times){
    		lightFlag = flag;
    		blinkOnCycle = on;
    		blinkOffCycle = off;
    		recycleTimes = times;
    		
    		isLightFresh = lightFlag && (blinkOnCycle + blinkOffCycle > 0);
    		isRecycleTimes = isLightFresh && (recycleTimes > 0);
    	}
    	public void turnOnOff(boolean flag){
    		if(this.num == BLUELIGHT){
    			if(mPosCtrlOp.isYellowlightOn() != flag){
    				mPosCtrlOp.trunOnoffYellowlight(flag);
    			}
    		}else if(this.num == REDLIGHT){
    			if(mPosCtrlOp.isRedlightOn() != flag){
    				mPosCtrlOp.trunOnoffRedlight(flag);
    			}   			
    		}
    	}
    	
		@Override
		public void run() {
			try {
				while(lightFlag){
					if(isLightFresh){
						if(isRecycleTimes && recycleTimes<=0){
							turnOnOff(false);
							return;
						}else{
							--recycleTimes;
						}
						turnOnOff(true);
						startTime = System.currentTimeMillis();
						while(System.currentTimeMillis() - startTime < blinkOnCycle){
							if(!lightFlag){
								turnOnOff(false);
								return;										
							}
						};								
						turnOnOff(false);
						startTime = System.currentTimeMillis();
						while(System.currentTimeMillis() - startTime < blinkOffCycle){
							if(!lightFlag){
								turnOnOff(false);
								return;										
							}
						};								
					}else{
						turnOnOff(true);
					}
				}
				turnOnOff(false);

			} catch (Exception e) {
				Log.e("LightUtil", e.getMessage(), e);
			}
		}    	
    };
    
    private static LightThread blueLight = null;
    private static LightThread redLight = null;
	
	// 打开jni文件
	static {
		mPosCtrlOp = new NewPosCtrl();
	}
	
	private static boolean claimed = false;
	
	public static boolean claim(){
		if(claimed){
			return false;
		}
		claimed = true;
		return true;
	}
	public static boolean release(){
		if(claimed){
			claimed = false;
		}
		claimed = false;
		return !claimed;
	}
	/**
	 * 控制等开关
	 * 
	 * @param lightNumber
	 * @param blinkOnCycle
	 * @param blinkOffCycle
	 */
	public static void switchOn(int lightNumber, int blinkOnCycle,
			int blinkOffCycle) {
		switchOn(lightNumber, blinkOnCycle, blinkOffCycle, 0);
	}
	/**
	 * 控制等开关
	 * 
	 * @param lightNumber
	 * @param blinkOnCycle   0 不闪烁, 正值循环闪烁, 表示持续亮灯的毫秒数, 不能为负数
	 * @param blinkOffCycle  0 不闪烁, 正值循环闪烁, 表示持续灭灯的毫秒数, 不能为负数
	 * @param times			 循环次数, 在闪烁情况下, 此参数有效, 0或负数为无限循环, 正数为循环次数
	 */
	public static void switchOn(int lightNumber, int blinkOnCycle,
			int blinkOffCycle, int times) {
		switch (lightNumber) {
		case BLUELIGHT:
			if(blueLight != null){
				blueLight.setFlag(false, 0, 0, 0);
				blueLight = null;
			}			
			blueLight = new LightThread(BLUELIGHT);
			blueLight.setFlag(true, blinkOnCycle, blinkOffCycle, times);
			blueLight.start();
			break;
		case REDLIGHT:
			if(redLight != null){
				redLight.setFlag(false, 0, 0, 0);
				redLight = null;
			}			
			redLight = new LightThread(REDLIGHT);
			redLight.setFlag(true, blinkOnCycle, blinkOffCycle, times);
			redLight.start();
			break;
		default:
			break;
		}
	}
	/**
	 * 1:关闭蓝灯 2:关闭红灯 ,其它任意数:关闭红蓝灯
	 * 
	 * @param num
	 */
	public static void trunOffLight(int num) {
		if (num == BLUELIGHT) {
			if(blueLight != null){
				blueLight.setFlag(false, 0, 0, 0);
				blueLight = null;
			}
		} else if (num == REDLIGHT) {
			if(redLight != null){
				redLight.setFlag(false, 0, 0, 0);
				redLight = null;
			}
		} else {
			if(blueLight != null){
				blueLight.setFlag(false, 0, 0, 0);
				blueLight = null;
			}
			if(redLight != null){
				redLight.setFlag(false, 0, 0, 0);
				redLight = null;
			}
			
		}
	}

}