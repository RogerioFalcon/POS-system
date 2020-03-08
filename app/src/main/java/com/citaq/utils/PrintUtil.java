package com.citaq.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;
import android.content.Context;
import android.os.RemoteException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android_serialport_api.SerialPort;

public class PrintUtil {
	final static int BUFFER_SIZE = 4096;
	private static boolean claimed = false;
	
	public static void delay(long delaytime){
		long startTime = System.currentTimeMillis();
		while(System.currentTimeMillis() - startTime < delaytime);
	}
	
	public static boolean claim(){
		if(claimed){
			//return false;
		}
		claimed = true;
		return true;
	}
	public static boolean release(){
		if(claimed){
			//claimed = false;
		}
		claimed = false;
		return !claimed;
	}

	// add by yidie
	public static boolean printBytes(byte[] printText) {
		boolean returnValue = true;
		try {
			OutputStream mOutputStream = getSerialPort().getOutputStream();
			mOutputStream.write(printText);
		} catch (Exception ex) {
			returnValue = false;
		}
		return returnValue;
	}

	// add by yidie
	public static boolean printString(String paramString) {
		return printBytes(getGbk(paramString));
	}

	/***************************************************************************
	 * add by yidie 2012-01-10 功能：设置打印绝对位置 参数： int 在当前行，定位光标位置，取值范围0至576点 说明：
	 * 在字体常规大小下，每汉字24点，英文字符12点 如位于第n个汉字后，则position=24*n
	 * 如位于第n个半角字符后，则position=12*n
	 ****************************************************************************/

	public static byte[] setCusorPosition(int position) {
		byte[] returnText = new byte[4]; // 当前行，设置绝对打印位置 ESC $ bL bH
		returnText[0] = 0x1B;
		returnText[1] = 0x24;
		returnText[2] = (byte) (position % 256);
		returnText[3] = (byte) (position / 256);
		return returnText;
	}

	public static byte[] setLineHeight(byte h) {
		byte[] returnText = new byte[] { 0x1B, 0x33, h }; // 行高； 1B 33 n
		return returnText;
	}

	public static byte[] setDefaultLineHeight() {
		byte[] returnText = new byte[] { 0x1B, 0x32 }; // 行高； 1B 32
		return returnText;
	}

	public static byte[] InputStreamTOByte(InputStream in) throws IOException {

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[BUFFER_SIZE];
		int count = -1;
		while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
			outStream.write(data, 0, count);

		data = null;
		return outStream.toByteArray();
	}

	public static void printLogo(Context c) {
		PrintUtil.printBytes(PrintUtil.setLineHeight((byte) 0));
		InputStream is = c.getClass().getResourceAsStream("/assets/bill.bin");
		byte[] b;
		try {
			b = InputStreamTOByte(is);
			PrintUtil.printBytes(b);
			PrintUtil.printBytes(PrintUtil.setDefaultLineHeight());
		} catch (Exception e) {
			if(Constants.isPrintLog){
				e.printStackTrace();
			}
		}
	}

	public static byte[] getLogo(Context c) {
		InputStream is = c.getClass().getResourceAsStream("/assets/bill.bin");
		byte[] b;
		try {
			b = InputStreamTOByte(is);
			return b;
		} catch (Exception e) {
			if(Constants.isPrintLog){
				e.printStackTrace();
			}
		}
		return null;
	}

	private static SerialPort mSerialPort = null;

	public static SerialPort getSerialPort() throws SecurityException,
			IOException, InvalidParameterException {
		if (mSerialPort == null) {
			String spFile = null;
			String model = MainBoardUtil.getModel(); // android.os.Build.MODEL.toLowerCase();
			if (model.contains(Constants.MAIN_BOARD_SMDKV210)) {
				spFile = "/dev/s3c2410_serial0";
			} else if (model.contains(Constants.MAIN_BOARD_RK30)) {
				spFile = "/dev/ttyS1";
			} else if (model.contains(Constants.MAIN_BOARD_C500)) {
				spFile = "/dev/ttyS1";
			} else {
				throw new IOException("unknow hardware!");
			}

			int baudrate = 115200;
			boolean flagCon = true;

			File myFile = new File(spFile);

			/* Open the serial port */
			mSerialPort = new SerialPort(myFile, baudrate, 0, flagCon);
		}
		return mSerialPort;
	}

	public static void closeSerialPort() {
		
		if (mSerialPort != null) {
			mSerialPort.close();
			mSerialPort = null;
		}
	}

	public static byte[] getGbk(String stText) {
		byte[] returnText = null;
		try {
			returnText = stText.getBytes("GBK"); // 必须放在try内才可以
		} catch (Exception ex) {
			;
		}
		return returnText;
	}

	public static byte[] setWH(int mode) {
		byte[] returnText = new byte[3]; // GS ! 11H 倍宽倍高
		returnText[0] = 0x1D;
		returnText[1] = 0x21;

		switch (mode) // 1-无；2-倍宽；3-倍高； 4-倍宽倍高
		{
		case 2:
			returnText[2] = 0x10;
			break;
		case 3:
			returnText[2] = 0x01;
			break;
		case 4:
			returnText[2] = 0x11;
			break;
		default:
			returnText[2] = 0x00;
			break;
		}

		return returnText;
	}

	public static byte[] setAlignCenter(int align) {
		byte[] returnText = new byte[5]; // 对齐 ESC a
		returnText[0] = 0x20;
		returnText[1] = 0x0A;
		returnText[2] = 0x1B;
		returnText[3] = 0x61;

		switch (align) // 0-左对齐；1-居中对齐；2-右对齐
		{
		case 1:
			returnText[4] = 0x01;
			break;
		case 2:
			returnText[4] = 0x02;
			break;
		default:
			returnText[4] = 0x00;
			break;
		}
		return returnText;
	}

	public static byte[] setBold(boolean dist) {
		byte[] returnText = new byte[3]; // 加粗 ESC E
		returnText[0] = 0x1B;
		returnText[1] = 0x45;

		if (dist) {
			returnText[2] = 0x01; // 表示加粗
		} else {
			returnText[2] = 0x00;
		}
		return returnText;
	}

	public static byte[] PrintBarcode(String stBarcode) {
		int iLength = stBarcode.length() + 4;
		byte[] returnText = new byte[iLength];

		returnText[0] = 0x1D;
		returnText[1] = 'k';
		returnText[2] = 0x45;
		returnText[3] = (byte) stBarcode.length(); // 条码长度；

		System.arraycopy(stBarcode.getBytes(), 0, returnText, 4,
				stBarcode.getBytes().length);

		return returnText;
	}

	public static byte[] cutPaper() {
		byte[] returnText = new byte[] {0x20,0x0A, 0x1D, 0x56, 0x42, 0x00 }; // 切纸； GS V
																	// 66D 0D
		return returnText;
	}

    /**
     * 测试打印
     * @return
     */
    public static boolean printTest()
    {
		byte[] printText = new byte[4];		
		printText[0] = 0x1F;
		printText[1] = 0x1B;
		printText[2] = 0x1F;
		printText[3] = 0x53;
    	boolean rv = printBytes(printText);
    	closeSerialPort();
    	return rv;
    }
    
    public static InputStream getInputStream(){
    	InputStream in = null;
    	try {
			in = getSerialPort().getInputStream();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if(Constants.isPrintLog){
				e.printStackTrace();
			}
		}
    	return in;
    }
    
    public static OutputStream getOutputStream(){
    	OutputStream out = null;
    	try {
			out = getSerialPort().getOutputStream();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			if(Constants.isPrintLog){
				e.printStackTrace();
			}
		}
    	return out;
    }
    
    //设置黑白反显打印模式
    public static byte[] setBlackWhiteMode(int mode){
    	return new byte[]{0x1D,0x42,(byte)mode};
    }

    //设置下划线模式
    public static byte[] setUnderline(int mode){
    	byte[] rv = new byte[6];    	
    	rv[0] = 0x1B;			//英文字符
    	rv[1] = 0x21;    	
    	if((mode & 1)!=0){
    		rv[2] = (byte)0x80;    		
    	}else{
    		rv[2] = 0;
    	} 
    	rv[3] = 0x1C;			//汉字字符
    	rv[4] = 0x21;
    	rv[5] = rv[2];
    	return rv;
    }    
    
    //设置放大倍数 1至8 倍 (0-7)
    public static byte[] setZoom(int level){
    	byte[] rv = new byte[3];
    	rv[0] = 0x1D;
    	rv[1] = 0x21;
    	rv[2] = (byte)((level & 0x07)<<4 | (level & 0x07));   	
    	return rv;
    }
    
    //开关打印机板上蜂鸣器
    public static byte[] trunOnOffBuzzer(boolean on){
    	byte[] rv= new byte[5];
    	rv[0] = 0x1F;
    	rv[1] = 0x1B;
    	rv[2] = 0x1F;
    	rv[3] = 0x50;
    	if(on){
    		rv[4] = 0x40;
    	}else{
    		rv[4] = 0x42;
    	}
    	return rv;
    }
    
    public static void init(){
		byte[] cmds = new byte[2];
		cmds[0] = 0x1B;
		cmds[1] = 0x40;
		printBytes(cmds);
		closeSerialPort();
    }
	//打印并走纸n行
	public static byte[] lineWrap(int n){
		byte[] cmds = new byte[3];
		cmds[0] = 0x1B;
		cmds[1] = 0x64;
		cmds[2] = (byte)n;
		return cmds;
	}  
	
	public static byte[] getColumnsText(String[] colsText, int[] colsWidth,
			int[] colsAlign){
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		try{
			int pos = 0;
			byte[] tmp1, tmp2;
			for(int i=0; i<colsText.length; i++){
				tmp1 = getGbk(colsText[i]);
				if(tmp1.length <= colsWidth[i]){
					tmp2 = tmp1;
				}else{
					tmp2 = new byte[colsWidth[i]];
					for(int j=0; j<colsWidth[i]; j++){
						tmp2[j] = tmp1[j];
					}
					tmp1 = null;
				}				
				switch(colsAlign[i]){
				case 1:			//居中对齐
					buffer.write(setCusorPosition((pos + (colsWidth[i] - tmp2.length)/2) * 12));
					break;
				case 2:			//右对齐
					buffer.write(setCusorPosition((pos + colsWidth[i] - tmp2.length) * 12));
					break;
				default:		//左对齐
					buffer.write(setCusorPosition(pos * 12));
					break;
				}
				buffer.write(tmp2);
				pos += colsWidth[i];
			}
			buffer.write((byte)0x0A);
		}catch(Exception e){
			e.printStackTrace();
		}
		return buffer.toByteArray();
	}
	public static void printCustomText(String data, int ns, int nb, int na) throws RemoteException {
		int iNum = 0;
		byte[] printText = new byte[2048];
		byte[] oldText;

		try {
			OutputStream mOutputStream = getSerialPort().getOutputStream();

			oldText = PrintUtil.setAlignCenter(na - 1);
			System.arraycopy(oldText, 0, printText, iNum, oldText.length);
			iNum += oldText.length;

			oldText = PrintUtil.setBold(nb == 1);
			System.arraycopy(oldText, 0, printText, iNum, oldText.length);
			iNum += oldText.length;

			oldText = PrintUtil.setWH(ns);
			System.arraycopy(oldText, 0, printText, iNum, oldText.length);
			iNum += oldText.length;

			/*
			oldText = PrintUtil.setDefaultLineHeight();
			System.arraycopy(oldText, 0, printText, iNum, oldText.length);
			iNum += oldText.length;
			*/

			//oldText = getGbk(data + '\n');
			oldText = getGbk(data);
			System.arraycopy(oldText, 0, printText, iNum, oldText.length);
			iNum += oldText.length;

			mOutputStream.write(printText);
			PrintUtil.closeSerialPort();
		} catch (Exception ex){

		}


	}
	public static void printCustomTextByJsonArray(JSONArray ja) throws RemoteException {
		int iNum = 0;
		byte[] printText = new byte[4096];
		byte[] oldText;

    	for(int i=0; i<ja.length(); i++){
    		try {
				JSONObject jo = (JSONObject) ja.getJSONObject(i);
				oldText = PrintUtil.setAlignCenter(jo.getInt("align") - 1);
				System.arraycopy(oldText, 0, printText, iNum, oldText.length);
				iNum += oldText.length;

				oldText = PrintUtil.setBold(jo.getBoolean("bold"));
				System.arraycopy(oldText, 0, printText, iNum, oldText.length);
				iNum += oldText.length;

				oldText = PrintUtil.setWH(jo.getInt("fontsize"));
				System.arraycopy(oldText, 0, printText, iNum, oldText.length);
				iNum += oldText.length;

				if(jo.getBoolean("linebreak"))
					oldText = getGbk(jo.getString("text") + '\n');
				else
					oldText = getGbk(jo.getString("text"));
				System.arraycopy(oldText, 0, printText, iNum, oldText.length);
				iNum += oldText.length;

			}catch (JSONException e){
    			e.printStackTrace();
			}
		}
		oldText = PrintUtil.cutPaper();
		System.arraycopy(oldText, 0, printText, iNum, oldText.length);
		iNum += oldText.length;

		try {
			OutputStream mOutputStream = getSerialPort().getOutputStream();
			mOutputStream.write(printText);
			PrintUtil.closeSerialPort();
		}catch (Exception e){

		}
	}
}
