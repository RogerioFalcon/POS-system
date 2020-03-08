/**
* Version: 1.3
*/

package com.citaq.aidlservice;

import com.citaq.aidlservice.IPosCallback;
import android.graphics.Bitmap;

interface IPosService
{
	/**
	* 1.0
	*/
	int getServiceVersionCode();
	
	/**
	* 1.0
	*/
	String getServiceVersionName();
	
	/**
	* 1.1 获取不同颜色的指示灯数量, 目前为2
	*/
	long getMaxLights();
	
	/** 
	* 1.2 开灯
	* lightNumber:		指示灯序号, 取值从1到getMaxLights
	* blinkOnCycle:		0 不闪烁, 正值循环闪烁, 表示持续亮灯的毫秒数, 不能为负数
	* blinkOffCycle:	0 不闪烁, 正值循环闪烁, 表示持续灭灯的毫秒数, 不能为负数
	*/
	boolean switchOn(int lightNumber, int blinkOnCycle, int blinkOffCycle);
	
	/**
	* 1.3 关灯
	* lightNumber:		指示灯序号, 取值从1到getMaxLights
	*/	
	boolean switchOff(int lightNumber);

	//////////////////////////////////////////////////////////////////
	
	/**
	* 2.1 打开钱柜
	*/
	void openDrawer();
	
	/**
	* 2.2 取钱柜累计打开次数
	*/		
	long getOpenDrawerTimes();
	
	/////////////////////////////////////////////////////////////////
	
	/**
	* 3.1 取打印机板系列号
	*/		
	String getPrinterSerialNo();
	
	/**
	* 3.2 取打印机主板版本号
	*/
	String getPrinterVersion();	
	
	/**
	* 3.3 取打印机型号
	*/		
	String getPrinterModal();
		
	/**
	* 3.4取切刀累计切纸次数
	*/
	long getCutPaperTimes();
	
	/**
	* 3.5 取打印头打印长度
	*/
	long getPrintedLength();

	/**
	* 3.6 打印机每行最大单字节字符数
	*/
	int getPrtLineChars();
	
	/**
	* 3.7 打印机是否支持切纸
	*/	
	boolean capCutPaper();
	
	/**
	* 3.8 打印机切纸
	*/
	void cutPaper();
	
	/**
	* 3.9 打印条码
	* data: 		条码数据
	* symbology: 	条码类型
	*    0 -- UPC-A
	*    1 -- UPC-E
	*    2 -- JAN13(EAN13)
	*    3 -- JAN8(EAN8)
	*    4 -- CODE39
	*    5 -- ITF
	*    6 -- CODABAR
	*    7 -- CODE93
	*    8 -- CODE128
	* height: 		条码高度, 取值1到255, 默认162
	* width: 		条码宽度, 取值2至6, 默认2
	* alignment:	条码对齐方式 0--居左 , 1--居中, 2--居右
	* textposition:	文字位置 0--不打印文字, 1--文字在条码上方, 2--文字在条码下方, 3--条码上下方均打印
	*/
	void printBarCode(String data, int symbology, int height, int width, int alignment, int textposition);
	
	/**
	* 3.10 打印图片
	* bitmap: 		图片
	* width: 		图片宽度
	* alignment:	对齐方式 0--居左 , 1--居中, 2--居右
	*/
	void printBitmap(in Bitmap bitmap, int width, int alignment);

	/**
	* 3.11 打印正常大小文本
	* data:			打印文字
	*/
	void printNormalText(String data);
	
	/**
	* 3.12 打印倍高倍宽文本(四倍正常大小)
	* data:			打印文字
	*/
	void printBigText(String data);	
	
	/**
	* 3.13 打印倍宽文本(字体宽度为正常的两倍, 高度不变)
	* data:			打印文字
	*/
	void printDoubleWidthText(String data);	

	/**
	* 3.14 打印倍高文本(字体高度为正常的两倍, 宽度不变)
	* data:			打印文字
	*/
	void printDoubleHeightText(String data);	

	/**
	* 3.15 自由放大字符打印
	* data:   打印的数据
	* zoom:　  放大因子(0-7) 
	*/
	void printZoomText(String data, int zoom);
		
	/**
	* 3.16 带指令打印
	* data			指令
	*/
	void printRAWData(in byte[] data);
			
	/**
	* 3.17 打印机自检
	*/
	void printerSelfChecking();
		
	/**
	* 3.18 打印二维码
	* data:			二维码数据
	* modulesize:	二维码块大小(单位:点, 取值 1 至 16 )
	* errorlevel:	二维码纠错等级(0 至 3)
	*                0 -- 纠错级别L ( 7%)
	*                1 -- 纠错级别M (15%) 
	*                2 -- 纠错级别Q (25%) 
	*                3 -- 纠错级别H (30%) 
	* alignment:	二维码对齐方式 
	*				 0 -- 居左 
	*				 1 -- 居中
	*				 2 -- 居右
	*/
	void printQRCode(String data, int modulesize, int errorlevel, int alignment);
	
	////////////////////////////////////////////////////////////////////////////
	/**
	* 用于事务打印的相关打印接口,不支持极盒N900
	/
	/**
	* 4.1 开启事务打印模式
	*/
	boolean beginTransactionPrint();
	
	/**
	* 4.2 中断事务打印模式
	*/
	boolean cancelTransactionPrint();	
	
	/**
	* 4.3 结束事务打印模式, 同时将事务中的命令集打印出来
	*/
	boolean endTransactionPrint();

	/**
	* 4.4 设置对齐模式, 仅用于事务打印模式
	* alignment:	对齐方式 1--居左  2--居中 3--居右
	*/
	boolean setPrintAlignment(int alignment);
	
	/**
	* 4.5 设置文字的水平打印位置, 仅用于事务打印模式
	* pos:			开始打印文字的水平位置, 大于getPrtLineChars的POS值将被忽略
	*/
	boolean setPrintPos(int pos);	
	
	/**
	* 4.6 设置黑白反显打印模式, 仅用于事务打印模式
	* mode:  0 恢复正常模式  1 选择反显模式
	*/
	boolean setBlackWhiteMode(int mode);
	
	/**
	* 4.7 设置下划线打印模式, 仅用于事务打印模式
	* mode:  0 恢复到正常模式  1 选择下划线模式
	*/
	boolean setUnderline(int mode);
		
	/**
	* 4.8 打印机切纸, 仅用于事务打印模式
	*/
	boolean trans_cutPaper();

	
	/**
	* 4.9 打印条码, 仅用于事务打印模式
	* data: 		条码数据
	* symbology: 	条码类型
	*    0 -- UPC-A
	*    1 -- UPC-E
	*    2 -- JAN13(EAN13)
	*    3 -- JAN8(EAN8)
	*    4 -- CODE39
	*    5 -- ITF
	*    6 -- CODABAR
	*    7 -- CODE93
	*    8 -- CODE128
	* height: 		条码高度, 取值1到255, 默认162
	* width: 		条码宽度, 取值2至6, 默认2
	* alignment:	条码对齐方式 0--居左 , 1--居中, 2--居右
	* textposition:	文字位置 0--不打印文字, 1--文字在条码上方, 2--文字在条码下方, 3--条码上下方均打印
	*/
	boolean trans_printBarCode(String data, int symbology, int height, int width, int alignment, int textposition);
	
	/**
	* 4.10 打印图片, 仅用于事务打印模式
	* bitmap: 		图片
	* width: 		图片宽度
	* alignment:	对齐方式 0--居左 , 1--居中, 2--居右
	*/
	boolean trans_printBitmap(in Bitmap bitmap, int width, int alignment);

	/**
	* 4.11 打印正常大小文本, 仅用于事务打印模式
	* data:			打印文字
	*/
	boolean trans_printNormalText(String data);
	
	/**
	* 4.12 打印倍高倍宽文本(四倍正常大小), 仅用于事务打印模式
	* data:			打印文字
	*/
	boolean trans_printBigText(String data);	
	
	/**
	* 4.13  打印倍宽文本(字体宽度为正常的两倍, 高度不变), 仅用于事务打印模式
	* data:			打印文字
	*/
	boolean trans_printDoubleWidthText(String data);	

	/**
	* 4.14 打印倍高文本(字体高度为正常的两倍, 宽度不变), 仅用于事务打印模式
	* data:			打印文字
	*/
	boolean  trans_printDoubleHeightText(String data);	
	
	/**
	* 4.15 带指令打印, 仅用于事务打印模式
	* data			指令
	*/
	boolean trans_printRAWData(in byte[] data);
	
	/**
	* 4.9 自由放大字符打印, 仅用于事务打印模式
	* data:   打印的数据
	* zoom:　  放大因子(0-7) 
	*/
	boolean trans_printZoomText(String data, int zoom);
		
	/**
	* 4.16 打印二维码, 仅用于事务打印模式
	* data:			二维码数据
	* modulesize:	二维码块大小(单位:点, 取值 1 至 16 )
	* errorlevel:	二维码纠错等级(0 至 3)
	*                0 -- 纠错级别L ( 7%)
	*                1 -- 纠错级别M (15%) 
	*                2 -- 纠错级别Q (25%) 
	*                3 -- 纠错级别H (30%) 
	* alignment:	二维码对齐方式 
	*				 0 -- 居左 
	*				 1 -- 居中
	*				 2 -- 居右
	*/
	boolean trans_printQRCode(String data, int modulesize, int errorlevel, int alignment);
	
	//////////////////////////////////////////////////////////////////////////
	
	/**
	* 0.1 打开局域网调试模式
	*/
	boolean openLanDebug();
	/**
	* 0.2 关闭局域网调试模式
	*/
	boolean closeLanDebug();
	
	/**
	* 注册
	* cb:	实现IWoyouCallback接口的回调
	*/
	void registerCallback(IPosCallback cb); 
	
	/**
	* 反注册
	* cb:	实现IWoyouCallback接口的回调
	*/
	 void unregisterCallback(IPosCallback cb);   	
	
	//////////////////////////////////////////////////////////////////////////
	
	//1.2.3 add
	
		/**
	 * 打印二维码
	 * @param url		二维码内容
	 * @param size		尺寸, 取值范围 1 至 5
	 * @param align		对齐 0--居左 , 1--居中, 2--居右
	 * @return
	 */
	boolean printZQRCode(String url, int size, int align);
	
	/**
	 * 并排打印二维码
	 * @param leftUrl
	 * @param rightUrl
	 * @return
	 */
	boolean printDoubleZQRCode(String leftUrl, String rightUrl);
	
	/**
	 * 并排打印二维码及图片
	 * @param url
	 * @param bytes
	 * @return
	 */
	boolean printZQRCodeImage(String url, in Bitmap bitmap);
		
	/**
	 * 打印多个空白行
	 * @param lines
	 * @return
	 */
	boolean lineWrap(int lines);
	
	//////////////////////////////////////////////////////////////////////////
	
	//1.2.3 add 事务
	
		/**
	 * 打印二维码
	 * @param url		二维码内容
	 * @param size		尺寸, 取值范围 1 至 5
	 * @param align		对齐 0--居左 , 1--居中, 2--居右
	 * @return
	 */
	boolean trans_printZQRCode(String url, int size, int align);
	
	/**
	 * 并排打印二维码
	 * @param leftUrl
	 * @param rightUrl
	 * @return
	 */
	boolean trans_printDoubleZQRCode(String leftUrl, String rightUrl);
	
	/**
	 * 并排打印二维码及图片
	 * @param url
	 * @param bytes
	 * @return
	 */
	boolean trans_printZQRCodeImage(String url, in Bitmap bitmap);
	
	
	/**
	 * 打印多个空白行
	 * @param lines
	 * @return
	 */
	boolean trans_lineWrap(int lines);	 
	
	/** 
	* 第二种开灯方式
	* lightNumber:		指示灯序号, 取值从1到getMaxLights
	* blinkOnCycle:		0 不闪烁, 正值循环闪烁, 表示持续亮灯的毫秒数, 不能为负数
	* blinkOffCycle:	0 不闪烁, 正值循环闪烁, 表示持续灭灯的毫秒数, 不能为负数
	* times:			循环次数, 在闪烁情况下, 此参数有效, 0或负数为无限循环, 正数为循环次数
	*/
	boolean switchOnTimes(int lightNumber, int blinkOnCycle, int blinkOffCycle, int times);	
	
	/**
	* 列对齐方式打印
	* colsText	各列文本数据
	* colsWidth 各列宽度(以英文字符计算, 每个中文字符占两个英文字符, 每个宽度大于0, 总宽度不大于getPrtLineChars)
	* colsAlign	各列对齐方式(0居左, 1居中, 2居右)
	* 备注: 三个参数的数组长度应该一致, 如果colsText[i]的宽度大于colsWidth[i], 则文本将被截断
	*/
	boolean printColumnsText(in String[] colsText, in int[] colsWidth, in int[] colsAlign);
	
	/**
	* 列对齐方式打印, 仅用于事务打印模式
	* colsText	各列文本数据
	* colsWidth 各列宽度(以英文字符计算, 每个中文字符占两个英文字符, 每个宽度大于0, 总宽度不大于getPrtLineChars)
	* colsAlign	各列对齐方式(0居左, 1居中, 2居右)
	* 备注: 三个参数的数组长度应该一致, 如果colsText[i]的宽度大于colsWidth[i], 则文本将被截断
	*/
	boolean trans_printColumnsText(in String[] colsText, in int[] colsWidth, in int[] colsAlign);	
}