package com.citaq.utils;

import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

/**
 * 将图片转化为二进制
 * @author nsz,yidie
 * 2015年6月26日
 */
public class PicFromPrintUtils {
	
	public static int sizes[] = {120,168,240,360,480};
		
	/*************************************************************************
	 * 我们的热敏打印机是RP-POS80S或RP-POS80P或RP-POS80CS或RP-POS80CP打印机
	 * 360*360的图片，8个字节（8个像素点）是一个二进制，将二进制转化为十进制数值
	 * y轴：24个像素点为一组，即360就是15组（0-14）
	 * x轴：360个像素点（0-359）
	 * 里面的每一组（24*360），每8个像素点为一个二进制，（每组有3个，3*8=24）
	 **************************************************************************/
	/**
	 * 把一张Bitmap图片转化为打印机可以打印的bit(图片高度调整为24的倍数)
	 * 效率很高
	 * @param bit
	 * @return
	 */	
	public static byte[] realDraw2PxPoint(Bitmap bit) { 
		int w = bit.getWidth();
		int h = bit.getHeight()/24;
		
		if( bit.getHeight() % 24 != 0){
			h++;
		}
				
		int max = h*(3*w+6)+1;
		byte wl = (byte)w;
		byte wh = (byte)(w >> 8);
		
		System.out.println("w="+w+",h="+h + ",wL="+wl+",WH="+wh);
		
		byte[] data = new byte[max];
		int k = 0;
		for (int j = 0; j < h; j++) {
			data[k++] = 0x1B;
			data[k++] = 0x2A;
			data[k++] = 33; // m=33时，选择24点双密度打印，分辨率达到200DPI。
			data[k++] = wl;
			data[k++] = wh;
			for (int i = 0; i < w; i++) {
				for (int m = 0; m < 3; m++) {
					for (int n = 0; n < 8; n++) {						
						byte b = px2Byte(i, j * 24 + m * 8 + n, bit);
						data[k] += data[k] + b;
					}
					k++;
				}
			}
			data[k++] = 10;
		}
		System.out.println("k=="+k+"===max=="+max);
		return data;
	}
	
	public static byte[] realDraw2PxPoint(Bitmap bit, int alignment) { 
		int w = bit.getWidth();
		int h = bit.getHeight()/24;
		
		if( bit.getHeight() % 24 != 0){
			h++;
		}
		
		alignment = alignment & 0x03;
		if(alignment == 3)alignment = 0;
				
		int max = h*(3*w+6)+12;
		byte wl = (byte)w;
		byte wh = (byte)(w >> 8);
		
		System.out.println("w="+w+",h="+h + ",wL="+wl+",WH="+wh);
		
		byte[] data = new byte[max];
		
		data[0] = 0x20;
		data[1] = 0x0A;
		data[2] = 0x1B;
		data[3] = 0x61;
		data[4] = (byte)alignment;
		
		data[5] = 0x1B;
		data[6] = 0x33;
		data[7] = 0;
		
		
		int k = 8;
		for (int j = 0; j < h; j++) {
			data[k++] = 0x1B;
			data[k++] = 0x2A;
			data[k++] = 33; // m=33时，选择24点双密度打印，分辨率达到200DPI。
			data[k++] = wl;
			data[k++] = wh;
			for (int i = 0; i < w; i++) {
				for (int m = 0; m < 3; m++) {
					for (int n = 0; n < 8; n++) {						
						byte b = px2Byte(i, j * 24 + m * 8 + n, bit);
						data[k] += data[k] + b;
					}
					k++;
				}
			}
			data[k++] = 10;
		}
		data[k++] = 0x1B;
		data[k++] = 0x32;
		data[k++] = 10;
		System.out.println("k=="+k+"===max=="+max);
		return data;
	}	
	
	/**
	 * 图片二值化，黑色是1，白色是0
	 * @param x  横坐标
	 * @param y	 纵坐标			
	 * @param bit 位图
	 * @return
	 */
	public static byte px2Byte(int x, int y, Bitmap bit) {
		if(y >= bit.getHeight() || x >= bit.getWidth()){
			return 0;
		}
		int pixel = bit.getPixel(x, y);
		int red = (pixel & 0x00ff0000) >> 16; // 取高两位
		int green = (pixel & 0x0000ff00) >> 8; // 取中两位
		int blue = pixel & 0x000000ff; // 取低两位
		int gray = RGB2Gray(red, green, blue);
		if ( gray < 128 ){
			return 1;
		} else {
			return 0;
		}
	}
	
	/**
	 * 图片灰度的转化
	 * @param r  
	 * @param g
	 * @param b
	 * @return
	 */
	private static int RGB2Gray(int r, int g, int b){
		int gray = (int) (0.29900 * r + 0.58700 * g + 0.11400 * b);  //灰度转化公式
		return  gray;
	}
	
	/**
	 * 对图片进行压缩（去除透明度）
	 * @param bitmapOrg
	 */
	public static Bitmap compressPic(Bitmap bitmapOrg, int newWidth) {
		int width = bitmapOrg.getWidth();
		int height = bitmapOrg.getHeight();
		int newHeight = height * newWidth/width;
		
		Bitmap targetBmp = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);  
		Canvas targetCanvas = new Canvas(targetBmp);
		targetCanvas.drawColor(0xffffffff);
		targetCanvas.drawBitmap(bitmapOrg, new Rect(0, 0, width, height), new Rect(0, 0, newWidth, newHeight), null);
		return targetBmp;
	}
	
	
	/**
	 * 对图片进行压缩(不去除透明度)
	 * @param bitmapOrg
	 */
	public static Bitmap compressBitmap(Bitmap bitmapOrg, int newWidth) {
		int width = bitmapOrg.getWidth();
		int height = bitmapOrg.getHeight();
		// 计算缩放率，新尺寸除原始尺寸
		float scaleWidth = ((float) newWidth) / width;		
		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleWidth);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, width,height, matrix, true);
		return resizedBitmap;
	}
	
	/**
	 * 打印光栅位图
	 * 
	 */
	public static byte[] rasterDataFromBitmap(Bitmap bm){
		int h = bm.getHeight();
		int w = (bm.getWidth() - 1)/8 + 1;
		
		byte[] data = new byte[h*w + 10];
		
		data[0] = 0x0A;
		data[1] = 0x1D;
		data[2] = 0x76;
		data[3] = 0x30;
		data[4] = 0x00;
		
		data[5] = (byte)w;//xL
		data[6] = (byte)(w >> 8);//xH
		data[7] = (byte)h;
		data[8] = (byte)(h >> 8);	
		
		int k = 9;
		for(int i=0; i<h; i++){
			for(int j=0; j<w; j++){
				for(int n=0; n<8; n++){
					byte b = px2Byte(j * 8 + n, i, bm);
					data[k] += data[k] + b;					
				}
				k++;
			}
		}
		data[k] = 0x0A;
		return data;
	}
	
	/**
	 * 打印光栅位图
	 * 
	 */
	public static byte[] rasterDataFromBitmap(Bitmap bm, int alignment){
		
		alignment = alignment & 0x03;
		if(alignment == 3)alignment = 0;
		
		int h = bm.getHeight();
		int w = (bm.getWidth() - 1)/8 + 1;
		
		byte[] data = new byte[h*w + 14];
		
		data[0] = 0x20;		
		data[1] = 0x0A;
		data[2] = 0x1B;
		data[3] = 0x61;
		data[4] = (byte)alignment;
		
		data[5] = 0x1D;
		data[6] = 0x76;
		data[7] = 0x30;
		data[8] = 0x00;
		
		data[9] = (byte)w;//xL
		data[10] = (byte)(w >> 8);//xH
		data[11] = (byte)h;
		data[12] = (byte)(h >> 8);	
		
		int k = 13;
		for(int i=0; i<h; i++){
			for(int j=0; j<w; j++){
				for(int n=0; n<8; n++){
					byte b = px2Byte(j * 8 + n, i, bm);
					data[k] += data[k] + b;					
				}
				k++;
			}
		}
		//data[k] = 0x0A;
		return data;
	}
	
	
	public  static Bitmap doubleBitmap(Bitmap bitmap1, Bitmap bitmap2) {
		int h1 = bitmap1.getHeight();
		int w1 = bitmap1.getWidth();
		
		int h2 = bitmap2.getHeight();
		int w2 = bitmap2.getWidth();
		
		int new_h = h1>h2 ? h1 : h2;
		int new_w = w1 + w2 + 10;
		
		Bitmap targetBmp = Bitmap.createBitmap(new_w, new_h, Bitmap.Config.ARGB_8888);  
		Canvas targetCanvas = new Canvas(targetBmp);
		targetCanvas.drawColor(0xffffffff);
		targetCanvas.drawBitmap(bitmap1, 0, (new_h-h1)/2, null);
		targetCanvas.drawBitmap(bitmap2, w1+10, (new_h-h2)/2, null);
		return targetBmp;
	}
	
    public static Bitmap createQRImage(String url, int size)
    {
    	--size;
    	if(size<0)size = 0;
    	if(size>4)size = 4;

    	try
        {
            //判断URL合法性
            if (url == null || "".equals(url) || url.length() < 1)
            {
                return null;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, sizes[size], sizes[size], hints);
            int[] pixels = new int[sizes[size] * sizes[size]];
            //下面这里按照二维码的算法，逐个生成二维码的图片，
            //两个for循环是图片横列扫描的结果
            for (int y = 0; y < sizes[size]; y++)
            {
                for (int x = 0; x < sizes[size]; x++)
                {
                    if (bitMatrix.get(x, y))
                    {
                        pixels[y * sizes[size] + x] = 0xff000000;
                    }
                    else
                    {
                        pixels[y * sizes[size] + x] = 0xffffffff;
                    }
                }
            }
            //生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(sizes[size], sizes[size], Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, sizes[size], 0, 0, sizes[size], sizes[size]);
            //显示到一个ImageView上面
            return bitmap;
        }
        catch (WriterException e)
        {
        	if(Constants.isPrintLog){
        		e.printStackTrace();
        	}
            return null;
        }
    }
    
    public static byte[] createQRBytes(String url, int size, int alignment)
    {
    	--size;
    	if(size<0)size = 0;
    	if(size>4)size = 4;
    	
		alignment = alignment & 0x03;
		if(alignment == 3)alignment = 0;
		
		int h = sizes[size];
		int w = sizes[size]/8;
   	   	
        try
        {
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, h, h, hints);
    		
    		byte[] data = new byte[h*w + 14];
    		
    		
    		data[0] = 0x20;
    		data[1] = 0x0A;
    		data[2] = 0x1B;
    		data[3] = 0x61;
    		data[4] = (byte)alignment;
    		
    		data[5] = 0x1D;
    		data[6] = 0x76;
    		data[7] = 0x30;
    		data[8] = 0x00;
    		
    		data[9] = (byte)w;//xL
    		data[10] = (byte)(w >> 8);//xH
    		data[11] = (byte)h;
    		data[12] = (byte)(h >> 8);	
    		
    		int k = 13;
    		for(int i=0; i<h; i++){
    			for(int j=0; j<w; j++){
    				for(int n=0; n<8; n++){
    					byte b = (byte)(bitMatrix.get(j * 8 + n, i) ? 1 : 0);
    					data[k] += data[k] + b;					
    				}
    				k++;
    			}
    		}
    		data[k] = 0x0A;
    		return data;
        }
        catch (WriterException e)
        {
        	if(Constants.isPrintLog){
        		e.printStackTrace();
        	}
            return null;
        }
    }  
    
    public static byte[] createRealQRBytes(String url, int size, int alignment)
    {
    	--size;
    	if(size<0)size = 0;
    	if(size>4)size = 4;
    	
		alignment = alignment & 0x03;
		if(alignment == 3)alignment = 0;
		
		int h = sizes[size]/24;
		int w = sizes[size];
   	   	
        try
        {
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, w, w, hints);
    		
    		int max = h*(3*w+6)+12;
    		byte wl = (byte)w;
    		byte wh = (byte)(w >> 8);
    		
    		System.out.println("w="+w+",h="+h + ",wL="+wl+",WH="+wh);
    		
    		byte[] data = new byte[max];
    		
    		data[0] = 0x20;
    		data[1] = 0x0A;
    		data[2] = 0x1B;
    		data[3] = 0x61;
    		data[4] = (byte)alignment;
    		
    		data[5] = 0x1B;
    		data[6] = 0x33;
    		data[7] = 0;
    		
    		
    		int k = 8;
    		for (int j = 0; j < h; j++) {
    			data[k++] = 0x1B;
    			data[k++] = 0x2A;
    			data[k++] = 33; // m=33时，选择24点双密度打印，分辨率达到200DPI。
    			data[k++] = wl;
    			data[k++] = wh;
    			for (int i = 0; i < w; i++) {
    				for (int m = 0; m < 3; m++) {
    					for (int n = 0; n < 8; n++) {	
    						byte b  = (byte)(bitMatrix.get(i, j * 24 + m * 8 + n) ? 1 : 0);
    						data[k] += data[k] + b;
    					}
    					k++;
    				}
    			}
    			data[k++] = 10;
    		}
    		data[k++] = 0x1B;
    		data[k++] = 0x32;
    		data[k++] = 10;
    		System.out.println("k=="+k+"===max=="+max);
    		return data;
        }
        catch (WriterException e)
        {
        	if(Constants.isPrintLog){
        		e.printStackTrace();
        	}
            return null;
        }
     }  	
}
