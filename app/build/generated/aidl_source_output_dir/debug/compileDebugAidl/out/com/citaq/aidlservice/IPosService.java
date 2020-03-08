/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\04_Work\\Android\\POS\\PosService\\app\\src\\main\\aidl\\com\\citaq\\aidlservice\\IPosService.aidl
 */
package com.citaq.aidlservice;
public interface IPosService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.citaq.aidlservice.IPosService
{
private static final java.lang.String DESCRIPTOR = "com.citaq.aidlservice.IPosService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.citaq.aidlservice.IPosService interface,
 * generating a proxy if needed.
 */
public static com.citaq.aidlservice.IPosService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.citaq.aidlservice.IPosService))) {
return ((com.citaq.aidlservice.IPosService)iin);
}
return new com.citaq.aidlservice.IPosService.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
java.lang.String descriptor = DESCRIPTOR;
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(descriptor);
return true;
}
case TRANSACTION_getServiceVersionCode:
{
data.enforceInterface(descriptor);
int _result = this.getServiceVersionCode();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getServiceVersionName:
{
data.enforceInterface(descriptor);
java.lang.String _result = this.getServiceVersionName();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getMaxLights:
{
data.enforceInterface(descriptor);
long _result = this.getMaxLights();
reply.writeNoException();
reply.writeLong(_result);
return true;
}
case TRANSACTION_switchOn:
{
data.enforceInterface(descriptor);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
boolean _result = this.switchOn(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_switchOff:
{
data.enforceInterface(descriptor);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.switchOff(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_openDrawer:
{
data.enforceInterface(descriptor);
this.openDrawer();
reply.writeNoException();
return true;
}
case TRANSACTION_getOpenDrawerTimes:
{
data.enforceInterface(descriptor);
long _result = this.getOpenDrawerTimes();
reply.writeNoException();
reply.writeLong(_result);
return true;
}
case TRANSACTION_getPrinterSerialNo:
{
data.enforceInterface(descriptor);
java.lang.String _result = this.getPrinterSerialNo();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getPrinterVersion:
{
data.enforceInterface(descriptor);
java.lang.String _result = this.getPrinterVersion();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getPrinterModal:
{
data.enforceInterface(descriptor);
java.lang.String _result = this.getPrinterModal();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getCutPaperTimes:
{
data.enforceInterface(descriptor);
long _result = this.getCutPaperTimes();
reply.writeNoException();
reply.writeLong(_result);
return true;
}
case TRANSACTION_getPrintedLength:
{
data.enforceInterface(descriptor);
long _result = this.getPrintedLength();
reply.writeNoException();
reply.writeLong(_result);
return true;
}
case TRANSACTION_getPrtLineChars:
{
data.enforceInterface(descriptor);
int _result = this.getPrtLineChars();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_capCutPaper:
{
data.enforceInterface(descriptor);
boolean _result = this.capCutPaper();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_cutPaper:
{
data.enforceInterface(descriptor);
this.cutPaper();
reply.writeNoException();
return true;
}
case TRANSACTION_printBarCode:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
int _arg3;
_arg3 = data.readInt();
int _arg4;
_arg4 = data.readInt();
int _arg5;
_arg5 = data.readInt();
this.printBarCode(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
reply.writeNoException();
return true;
}
case TRANSACTION_printBitmap:
{
data.enforceInterface(descriptor);
android.graphics.Bitmap _arg0;
if ((0!=data.readInt())) {
_arg0 = android.graphics.Bitmap.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
this.printBitmap(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
case TRANSACTION_printNormalText:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
this.printNormalText(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_printBigText:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
this.printBigText(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_printDoubleWidthText:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
this.printDoubleWidthText(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_printDoubleHeightText:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
this.printDoubleHeightText(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_printZoomText:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
this.printZoomText(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_printRAWData:
{
data.enforceInterface(descriptor);
byte[] _arg0;
_arg0 = data.createByteArray();
this.printRAWData(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_printerSelfChecking:
{
data.enforceInterface(descriptor);
this.printerSelfChecking();
reply.writeNoException();
return true;
}
case TRANSACTION_printQRCode:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
int _arg3;
_arg3 = data.readInt();
this.printQRCode(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
return true;
}
case TRANSACTION_beginTransactionPrint:
{
data.enforceInterface(descriptor);
boolean _result = this.beginTransactionPrint();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_cancelTransactionPrint:
{
data.enforceInterface(descriptor);
boolean _result = this.cancelTransactionPrint();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_endTransactionPrint:
{
data.enforceInterface(descriptor);
boolean _result = this.endTransactionPrint();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setPrintAlignment:
{
data.enforceInterface(descriptor);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.setPrintAlignment(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setPrintPos:
{
data.enforceInterface(descriptor);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.setPrintPos(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setBlackWhiteMode:
{
data.enforceInterface(descriptor);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.setBlackWhiteMode(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setUnderline:
{
data.enforceInterface(descriptor);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.setUnderline(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_trans_cutPaper:
{
data.enforceInterface(descriptor);
boolean _result = this.trans_cutPaper();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_trans_printBarCode:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
int _arg3;
_arg3 = data.readInt();
int _arg4;
_arg4 = data.readInt();
int _arg5;
_arg5 = data.readInt();
boolean _result = this.trans_printBarCode(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_trans_printBitmap:
{
data.enforceInterface(descriptor);
android.graphics.Bitmap _arg0;
if ((0!=data.readInt())) {
_arg0 = android.graphics.Bitmap.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
boolean _result = this.trans_printBitmap(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_trans_printNormalText:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _result = this.trans_printNormalText(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_trans_printBigText:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _result = this.trans_printBigText(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_trans_printDoubleWidthText:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _result = this.trans_printDoubleWidthText(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_trans_printDoubleHeightText:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _result = this.trans_printDoubleHeightText(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_trans_printRAWData:
{
data.enforceInterface(descriptor);
byte[] _arg0;
_arg0 = data.createByteArray();
boolean _result = this.trans_printRAWData(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_trans_printZoomText:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
boolean _result = this.trans_printZoomText(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_trans_printQRCode:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
int _arg3;
_arg3 = data.readInt();
boolean _result = this.trans_printQRCode(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_openLanDebug:
{
data.enforceInterface(descriptor);
boolean _result = this.openLanDebug();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_closeLanDebug:
{
data.enforceInterface(descriptor);
boolean _result = this.closeLanDebug();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_registerCallback:
{
data.enforceInterface(descriptor);
com.citaq.aidlservice.IPosCallback _arg0;
_arg0 = com.citaq.aidlservice.IPosCallback.Stub.asInterface(data.readStrongBinder());
this.registerCallback(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_unregisterCallback:
{
data.enforceInterface(descriptor);
com.citaq.aidlservice.IPosCallback _arg0;
_arg0 = com.citaq.aidlservice.IPosCallback.Stub.asInterface(data.readStrongBinder());
this.unregisterCallback(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_printZQRCode:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
boolean _result = this.printZQRCode(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_printDoubleZQRCode:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
boolean _result = this.printDoubleZQRCode(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_printZQRCodeImage:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
android.graphics.Bitmap _arg1;
if ((0!=data.readInt())) {
_arg1 = android.graphics.Bitmap.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
boolean _result = this.printZQRCodeImage(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_lineWrap:
{
data.enforceInterface(descriptor);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.lineWrap(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_trans_printZQRCode:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
boolean _result = this.trans_printZQRCode(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_trans_printDoubleZQRCode:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
boolean _result = this.trans_printDoubleZQRCode(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_trans_printZQRCodeImage:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
android.graphics.Bitmap _arg1;
if ((0!=data.readInt())) {
_arg1 = android.graphics.Bitmap.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
boolean _result = this.trans_printZQRCodeImage(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_trans_lineWrap:
{
data.enforceInterface(descriptor);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.trans_lineWrap(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_switchOnTimes:
{
data.enforceInterface(descriptor);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
int _arg3;
_arg3 = data.readInt();
boolean _result = this.switchOnTimes(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_printColumnsText:
{
data.enforceInterface(descriptor);
java.lang.String[] _arg0;
_arg0 = data.createStringArray();
int[] _arg1;
_arg1 = data.createIntArray();
int[] _arg2;
_arg2 = data.createIntArray();
boolean _result = this.printColumnsText(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_trans_printColumnsText:
{
data.enforceInterface(descriptor);
java.lang.String[] _arg0;
_arg0 = data.createStringArray();
int[] _arg1;
_arg1 = data.createIntArray();
int[] _arg2;
_arg2 = data.createIntArray();
boolean _result = this.trans_printColumnsText(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
default:
{
return super.onTransact(code, data, reply, flags);
}
}
}
private static class Proxy implements com.citaq.aidlservice.IPosService
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public int getServiceVersionCode() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getServiceVersionCode, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String getServiceVersionName() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getServiceVersionName, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	* 1.1 获取不同颜色的指示灯数量, 目前为2
	*/
@Override public long getMaxLights() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
long _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getMaxLights, _data, _reply, 0);
_reply.readException();
_result = _reply.readLong();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/** 
	* 1.2 开灯
	* lightNumber:		指示灯序号, 取值从1到getMaxLights
	* blinkOnCycle:		0 不闪烁, 正值循环闪烁, 表示持续亮灯的毫秒数, 不能为负数
	* blinkOffCycle:	0 不闪烁, 正值循环闪烁, 表示持续灭灯的毫秒数, 不能为负数
	*/
@Override public boolean switchOn(int lightNumber, int blinkOnCycle, int blinkOffCycle) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(lightNumber);
_data.writeInt(blinkOnCycle);
_data.writeInt(blinkOffCycle);
mRemote.transact(Stub.TRANSACTION_switchOn, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	* 1.3 关灯
	* lightNumber:		指示灯序号, 取值从1到getMaxLights
	*/
@Override public boolean switchOff(int lightNumber) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(lightNumber);
mRemote.transact(Stub.TRANSACTION_switchOff, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
//////////////////////////////////////////////////////////////////
/**
	* 2.1 打开钱柜
	*/
@Override public void openDrawer() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_openDrawer, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
	* 2.2 取钱柜累计打开次数
	*/
@Override public long getOpenDrawerTimes() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
long _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getOpenDrawerTimes, _data, _reply, 0);
_reply.readException();
_result = _reply.readLong();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String getPrinterSerialNo() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getPrinterSerialNo, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String getPrinterVersion() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getPrinterVersion, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String getPrinterModal() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getPrinterModal, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	* 3.4取切刀累计切纸次数
	*/
@Override public long getCutPaperTimes() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
long _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getCutPaperTimes, _data, _reply, 0);
_reply.readException();
_result = _reply.readLong();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	* 3.5 取打印头打印长度
	*/
@Override public long getPrintedLength() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
long _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getPrintedLength, _data, _reply, 0);
_reply.readException();
_result = _reply.readLong();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getPrtLineChars() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getPrtLineChars, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	* 3.7 打印机是否支持切纸
	*/
@Override public boolean capCutPaper() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_capCutPaper, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	* 3.8 打印机切纸
	*/
@Override public void cutPaper() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_cutPaper, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
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
@Override public void printBarCode(java.lang.String data, int symbology, int height, int width, int alignment, int textposition) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(data);
_data.writeInt(symbology);
_data.writeInt(height);
_data.writeInt(width);
_data.writeInt(alignment);
_data.writeInt(textposition);
mRemote.transact(Stub.TRANSACTION_printBarCode, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
	* 3.10 打印图片
	* bitmap: 		图片
	* width: 		图片宽度
	* alignment:	对齐方式 0--居左 , 1--居中, 2--居右
	*/
@Override public void printBitmap(android.graphics.Bitmap bitmap, int width, int alignment) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((bitmap!=null)) {
_data.writeInt(1);
bitmap.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeInt(width);
_data.writeInt(alignment);
mRemote.transact(Stub.TRANSACTION_printBitmap, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
	* 3.11 打印正常大小文本
	* data:			打印文字
	*/
@Override public void printNormalText(java.lang.String data) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(data);
mRemote.transact(Stub.TRANSACTION_printNormalText, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
	* 3.12 打印倍高倍宽文本(四倍正常大小)
	* data:			打印文字
	*/
@Override public void printBigText(java.lang.String data) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(data);
mRemote.transact(Stub.TRANSACTION_printBigText, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
	* 3.13 打印倍宽文本(字体宽度为正常的两倍, 高度不变)
	* data:			打印文字
	*/
@Override public void printDoubleWidthText(java.lang.String data) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(data);
mRemote.transact(Stub.TRANSACTION_printDoubleWidthText, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
	* 3.14 打印倍高文本(字体高度为正常的两倍, 宽度不变)
	* data:			打印文字
	*/
@Override public void printDoubleHeightText(java.lang.String data) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(data);
mRemote.transact(Stub.TRANSACTION_printDoubleHeightText, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
	* 3.15 自由放大字符打印
	* data:   打印的数据
	* zoom:　  放大因子(0-7) 
	*/
@Override public void printZoomText(java.lang.String data, int zoom) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(data);
_data.writeInt(zoom);
mRemote.transact(Stub.TRANSACTION_printZoomText, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
	* 3.16 带指令打印
	* data			指令
	*/
@Override public void printRAWData(byte[] data) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeByteArray(data);
mRemote.transact(Stub.TRANSACTION_printRAWData, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
	* 3.17 打印机自检
	*/
@Override public void printerSelfChecking() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_printerSelfChecking, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
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
@Override public void printQRCode(java.lang.String data, int modulesize, int errorlevel, int alignment) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(data);
_data.writeInt(modulesize);
_data.writeInt(errorlevel);
_data.writeInt(alignment);
mRemote.transact(Stub.TRANSACTION_printQRCode, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
////////////////////////////////////////////////////////////////////////////
/**
	* 用于事务打印的相关打印接口,不支持极盒N900
	/
	/**
	* 4.1 开启事务打印模式
	*/
@Override public boolean beginTransactionPrint() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_beginTransactionPrint, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	* 4.2 中断事务打印模式
	*/
@Override public boolean cancelTransactionPrint() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_cancelTransactionPrint, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	* 4.3 结束事务打印模式, 同时将事务中的命令集打印出来
	*/
@Override public boolean endTransactionPrint() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_endTransactionPrint, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	* 4.4 设置对齐模式, 仅用于事务打印模式
	* alignment:	对齐方式 1--居左  2--居中 3--居右
	*/
@Override public boolean setPrintAlignment(int alignment) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(alignment);
mRemote.transact(Stub.TRANSACTION_setPrintAlignment, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	* 4.5 设置文字的水平打印位置, 仅用于事务打印模式
	* pos:			开始打印文字的水平位置, 大于getPrtLineChars的POS值将被忽略
	*/
@Override public boolean setPrintPos(int pos) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(pos);
mRemote.transact(Stub.TRANSACTION_setPrintPos, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	* 4.6 设置黑白反显打印模式, 仅用于事务打印模式
	* mode:  0 恢复正常模式  1 选择反显模式
	*/
@Override public boolean setBlackWhiteMode(int mode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(mode);
mRemote.transact(Stub.TRANSACTION_setBlackWhiteMode, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	* 4.7 设置下划线打印模式, 仅用于事务打印模式
	* mode:  0 恢复到正常模式  1 选择下划线模式
	*/
@Override public boolean setUnderline(int mode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(mode);
mRemote.transact(Stub.TRANSACTION_setUnderline, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	* 4.8 打印机切纸, 仅用于事务打印模式
	*/
@Override public boolean trans_cutPaper() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_trans_cutPaper, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
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
@Override public boolean trans_printBarCode(java.lang.String data, int symbology, int height, int width, int alignment, int textposition) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(data);
_data.writeInt(symbology);
_data.writeInt(height);
_data.writeInt(width);
_data.writeInt(alignment);
_data.writeInt(textposition);
mRemote.transact(Stub.TRANSACTION_trans_printBarCode, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	* 4.10 打印图片, 仅用于事务打印模式
	* bitmap: 		图片
	* width: 		图片宽度
	* alignment:	对齐方式 0--居左 , 1--居中, 2--居右
	*/
@Override public boolean trans_printBitmap(android.graphics.Bitmap bitmap, int width, int alignment) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((bitmap!=null)) {
_data.writeInt(1);
bitmap.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeInt(width);
_data.writeInt(alignment);
mRemote.transact(Stub.TRANSACTION_trans_printBitmap, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	* 4.11 打印正常大小文本, 仅用于事务打印模式
	* data:			打印文字
	*/
@Override public boolean trans_printNormalText(java.lang.String data) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(data);
mRemote.transact(Stub.TRANSACTION_trans_printNormalText, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	* 4.12 打印倍高倍宽文本(四倍正常大小), 仅用于事务打印模式
	* data:			打印文字
	*/
@Override public boolean trans_printBigText(java.lang.String data) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(data);
mRemote.transact(Stub.TRANSACTION_trans_printBigText, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	* 4.13  打印倍宽文本(字体宽度为正常的两倍, 高度不变), 仅用于事务打印模式
	* data:			打印文字
	*/
@Override public boolean trans_printDoubleWidthText(java.lang.String data) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(data);
mRemote.transact(Stub.TRANSACTION_trans_printDoubleWidthText, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	* 4.14 打印倍高文本(字体高度为正常的两倍, 宽度不变), 仅用于事务打印模式
	* data:			打印文字
	*/
@Override public boolean trans_printDoubleHeightText(java.lang.String data) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(data);
mRemote.transact(Stub.TRANSACTION_trans_printDoubleHeightText, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	* 4.15 带指令打印, 仅用于事务打印模式
	* data			指令
	*/
@Override public boolean trans_printRAWData(byte[] data) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeByteArray(data);
mRemote.transact(Stub.TRANSACTION_trans_printRAWData, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	* 4.9 自由放大字符打印, 仅用于事务打印模式
	* data:   打印的数据
	* zoom:　  放大因子(0-7) 
	*/
@Override public boolean trans_printZoomText(java.lang.String data, int zoom) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(data);
_data.writeInt(zoom);
mRemote.transact(Stub.TRANSACTION_trans_printZoomText, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
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
@Override public boolean trans_printQRCode(java.lang.String data, int modulesize, int errorlevel, int alignment) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(data);
_data.writeInt(modulesize);
_data.writeInt(errorlevel);
_data.writeInt(alignment);
mRemote.transact(Stub.TRANSACTION_trans_printQRCode, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
//////////////////////////////////////////////////////////////////////////
/**
	* 0.1 打开局域网调试模式
	*/
@Override public boolean openLanDebug() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_openLanDebug, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	* 0.2 关闭局域网调试模式
	*/
@Override public boolean closeLanDebug() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_closeLanDebug, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	* 注册
	* cb:	实现IWoyouCallback接口的回调
	*/
@Override public void registerCallback(com.citaq.aidlservice.IPosCallback cb) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((cb!=null))?(cb.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_registerCallback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
	* 反注册
	* cb:	实现IWoyouCallback接口的回调
	*/
@Override public void unregisterCallback(com.citaq.aidlservice.IPosCallback cb) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((cb!=null))?(cb.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_unregisterCallback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
//////////////////////////////////////////////////////////////////////////
//1.2.3 add
/**
	 * 打印二维码
	 * @param url		二维码内容
	 * @param size		尺寸, 取值范围 1 至 5
	 * @param align		对齐 0--居左 , 1--居中, 2--居右
	 * @return
	 */
@Override public boolean printZQRCode(java.lang.String url, int size, int align) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(url);
_data.writeInt(size);
_data.writeInt(align);
mRemote.transact(Stub.TRANSACTION_printZQRCode, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * 并排打印二维码
	 * @param leftUrl
	 * @param rightUrl
	 * @return
	 */
@Override public boolean printDoubleZQRCode(java.lang.String leftUrl, java.lang.String rightUrl) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(leftUrl);
_data.writeString(rightUrl);
mRemote.transact(Stub.TRANSACTION_printDoubleZQRCode, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * 并排打印二维码及图片
	 * @param url
	 * @param bytes
	 * @return
	 */
@Override public boolean printZQRCodeImage(java.lang.String url, android.graphics.Bitmap bitmap) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(url);
if ((bitmap!=null)) {
_data.writeInt(1);
bitmap.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_printZQRCodeImage, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * 打印多个空白行
	 * @param lines
	 * @return
	 */
@Override public boolean lineWrap(int lines) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(lines);
mRemote.transact(Stub.TRANSACTION_lineWrap, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
//////////////////////////////////////////////////////////////////////////
//1.2.3 add 事务
/**
	 * 打印二维码
	 * @param url		二维码内容
	 * @param size		尺寸, 取值范围 1 至 5
	 * @param align		对齐 0--居左 , 1--居中, 2--居右
	 * @return
	 */
@Override public boolean trans_printZQRCode(java.lang.String url, int size, int align) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(url);
_data.writeInt(size);
_data.writeInt(align);
mRemote.transact(Stub.TRANSACTION_trans_printZQRCode, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * 并排打印二维码
	 * @param leftUrl
	 * @param rightUrl
	 * @return
	 */
@Override public boolean trans_printDoubleZQRCode(java.lang.String leftUrl, java.lang.String rightUrl) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(leftUrl);
_data.writeString(rightUrl);
mRemote.transact(Stub.TRANSACTION_trans_printDoubleZQRCode, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * 并排打印二维码及图片
	 * @param url
	 * @param bytes
	 * @return
	 */
@Override public boolean trans_printZQRCodeImage(java.lang.String url, android.graphics.Bitmap bitmap) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(url);
if ((bitmap!=null)) {
_data.writeInt(1);
bitmap.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_trans_printZQRCodeImage, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * 打印多个空白行
	 * @param lines
	 * @return
	 */
@Override public boolean trans_lineWrap(int lines) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(lines);
mRemote.transact(Stub.TRANSACTION_trans_lineWrap, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/** 
	* 第二种开灯方式
	* lightNumber:		指示灯序号, 取值从1到getMaxLights
	* blinkOnCycle:		0 不闪烁, 正值循环闪烁, 表示持续亮灯的毫秒数, 不能为负数
	* blinkOffCycle:	0 不闪烁, 正值循环闪烁, 表示持续灭灯的毫秒数, 不能为负数
	* times:			循环次数, 在闪烁情况下, 此参数有效, 0或负数为无限循环, 正数为循环次数
	*/
@Override public boolean switchOnTimes(int lightNumber, int blinkOnCycle, int blinkOffCycle, int times) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(lightNumber);
_data.writeInt(blinkOnCycle);
_data.writeInt(blinkOffCycle);
_data.writeInt(times);
mRemote.transact(Stub.TRANSACTION_switchOnTimes, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	* 列对齐方式打印
	* colsText	各列文本数据
	* colsWidth 各列宽度(以英文字符计算, 每个中文字符占两个英文字符, 每个宽度大于0, 总宽度不大于getPrtLineChars)
	* colsAlign	各列对齐方式(0居左, 1居中, 2居右)
	* 备注: 三个参数的数组长度应该一致, 如果colsText[i]的宽度大于colsWidth[i], 则文本将被截断
	*/
@Override public boolean printColumnsText(java.lang.String[] colsText, int[] colsWidth, int[] colsAlign) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStringArray(colsText);
_data.writeIntArray(colsWidth);
_data.writeIntArray(colsAlign);
mRemote.transact(Stub.TRANSACTION_printColumnsText, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	* 列对齐方式打印, 仅用于事务打印模式
	* colsText	各列文本数据
	* colsWidth 各列宽度(以英文字符计算, 每个中文字符占两个英文字符, 每个宽度大于0, 总宽度不大于getPrtLineChars)
	* colsAlign	各列对齐方式(0居左, 1居中, 2居右)
	* 备注: 三个参数的数组长度应该一致, 如果colsText[i]的宽度大于colsWidth[i], 则文本将被截断
	*/
@Override public boolean trans_printColumnsText(java.lang.String[] colsText, int[] colsWidth, int[] colsAlign) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStringArray(colsText);
_data.writeIntArray(colsWidth);
_data.writeIntArray(colsAlign);
mRemote.transact(Stub.TRANSACTION_trans_printColumnsText, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_getServiceVersionCode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_getServiceVersionName = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_getMaxLights = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_switchOn = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_switchOff = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_openDrawer = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_getOpenDrawerTimes = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_getPrinterSerialNo = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_getPrinterVersion = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_getPrinterModal = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_getCutPaperTimes = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_getPrintedLength = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_getPrtLineChars = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_capCutPaper = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
static final int TRANSACTION_cutPaper = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
static final int TRANSACTION_printBarCode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
static final int TRANSACTION_printBitmap = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
static final int TRANSACTION_printNormalText = (android.os.IBinder.FIRST_CALL_TRANSACTION + 17);
static final int TRANSACTION_printBigText = (android.os.IBinder.FIRST_CALL_TRANSACTION + 18);
static final int TRANSACTION_printDoubleWidthText = (android.os.IBinder.FIRST_CALL_TRANSACTION + 19);
static final int TRANSACTION_printDoubleHeightText = (android.os.IBinder.FIRST_CALL_TRANSACTION + 20);
static final int TRANSACTION_printZoomText = (android.os.IBinder.FIRST_CALL_TRANSACTION + 21);
static final int TRANSACTION_printRAWData = (android.os.IBinder.FIRST_CALL_TRANSACTION + 22);
static final int TRANSACTION_printerSelfChecking = (android.os.IBinder.FIRST_CALL_TRANSACTION + 23);
static final int TRANSACTION_printQRCode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 24);
static final int TRANSACTION_beginTransactionPrint = (android.os.IBinder.FIRST_CALL_TRANSACTION + 25);
static final int TRANSACTION_cancelTransactionPrint = (android.os.IBinder.FIRST_CALL_TRANSACTION + 26);
static final int TRANSACTION_endTransactionPrint = (android.os.IBinder.FIRST_CALL_TRANSACTION + 27);
static final int TRANSACTION_setPrintAlignment = (android.os.IBinder.FIRST_CALL_TRANSACTION + 28);
static final int TRANSACTION_setPrintPos = (android.os.IBinder.FIRST_CALL_TRANSACTION + 29);
static final int TRANSACTION_setBlackWhiteMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 30);
static final int TRANSACTION_setUnderline = (android.os.IBinder.FIRST_CALL_TRANSACTION + 31);
static final int TRANSACTION_trans_cutPaper = (android.os.IBinder.FIRST_CALL_TRANSACTION + 32);
static final int TRANSACTION_trans_printBarCode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 33);
static final int TRANSACTION_trans_printBitmap = (android.os.IBinder.FIRST_CALL_TRANSACTION + 34);
static final int TRANSACTION_trans_printNormalText = (android.os.IBinder.FIRST_CALL_TRANSACTION + 35);
static final int TRANSACTION_trans_printBigText = (android.os.IBinder.FIRST_CALL_TRANSACTION + 36);
static final int TRANSACTION_trans_printDoubleWidthText = (android.os.IBinder.FIRST_CALL_TRANSACTION + 37);
static final int TRANSACTION_trans_printDoubleHeightText = (android.os.IBinder.FIRST_CALL_TRANSACTION + 38);
static final int TRANSACTION_trans_printRAWData = (android.os.IBinder.FIRST_CALL_TRANSACTION + 39);
static final int TRANSACTION_trans_printZoomText = (android.os.IBinder.FIRST_CALL_TRANSACTION + 40);
static final int TRANSACTION_trans_printQRCode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 41);
static final int TRANSACTION_openLanDebug = (android.os.IBinder.FIRST_CALL_TRANSACTION + 42);
static final int TRANSACTION_closeLanDebug = (android.os.IBinder.FIRST_CALL_TRANSACTION + 43);
static final int TRANSACTION_registerCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 44);
static final int TRANSACTION_unregisterCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 45);
static final int TRANSACTION_printZQRCode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 46);
static final int TRANSACTION_printDoubleZQRCode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 47);
static final int TRANSACTION_printZQRCodeImage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 48);
static final int TRANSACTION_lineWrap = (android.os.IBinder.FIRST_CALL_TRANSACTION + 49);
static final int TRANSACTION_trans_printZQRCode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 50);
static final int TRANSACTION_trans_printDoubleZQRCode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 51);
static final int TRANSACTION_trans_printZQRCodeImage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 52);
static final int TRANSACTION_trans_lineWrap = (android.os.IBinder.FIRST_CALL_TRANSACTION + 53);
static final int TRANSACTION_switchOnTimes = (android.os.IBinder.FIRST_CALL_TRANSACTION + 54);
static final int TRANSACTION_printColumnsText = (android.os.IBinder.FIRST_CALL_TRANSACTION + 55);
static final int TRANSACTION_trans_printColumnsText = (android.os.IBinder.FIRST_CALL_TRANSACTION + 56);
}
public int getServiceVersionCode() throws android.os.RemoteException;
public java.lang.String getServiceVersionName() throws android.os.RemoteException;
/**
	* 1.1 获取不同颜色的指示灯数量, 目前为2
	*/
public long getMaxLights() throws android.os.RemoteException;
/** 
	* 1.2 开灯
	* lightNumber:		指示灯序号, 取值从1到getMaxLights
	* blinkOnCycle:		0 不闪烁, 正值循环闪烁, 表示持续亮灯的毫秒数, 不能为负数
	* blinkOffCycle:	0 不闪烁, 正值循环闪烁, 表示持续灭灯的毫秒数, 不能为负数
	*/
public boolean switchOn(int lightNumber, int blinkOnCycle, int blinkOffCycle) throws android.os.RemoteException;
/**
	* 1.3 关灯
	* lightNumber:		指示灯序号, 取值从1到getMaxLights
	*/
public boolean switchOff(int lightNumber) throws android.os.RemoteException;
//////////////////////////////////////////////////////////////////
/**
	* 2.1 打开钱柜
	*/
public void openDrawer() throws android.os.RemoteException;
/**
	* 2.2 取钱柜累计打开次数
	*/
public long getOpenDrawerTimes() throws android.os.RemoteException;
public java.lang.String getPrinterSerialNo() throws android.os.RemoteException;
public java.lang.String getPrinterVersion() throws android.os.RemoteException;
public java.lang.String getPrinterModal() throws android.os.RemoteException;
/**
	* 3.4取切刀累计切纸次数
	*/
public long getCutPaperTimes() throws android.os.RemoteException;
/**
	* 3.5 取打印头打印长度
	*/
public long getPrintedLength() throws android.os.RemoteException;
public int getPrtLineChars() throws android.os.RemoteException;
/**
	* 3.7 打印机是否支持切纸
	*/
public boolean capCutPaper() throws android.os.RemoteException;
/**
	* 3.8 打印机切纸
	*/
public void cutPaper() throws android.os.RemoteException;
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
public void printBarCode(java.lang.String data, int symbology, int height, int width, int alignment, int textposition) throws android.os.RemoteException;
/**
	* 3.10 打印图片
	* bitmap: 		图片
	* width: 		图片宽度
	* alignment:	对齐方式 0--居左 , 1--居中, 2--居右
	*/
public void printBitmap(android.graphics.Bitmap bitmap, int width, int alignment) throws android.os.RemoteException;
/**
	* 3.11 打印正常大小文本
	* data:			打印文字
	*/
public void printNormalText(java.lang.String data) throws android.os.RemoteException;
/**
	* 3.12 打印倍高倍宽文本(四倍正常大小)
	* data:			打印文字
	*/
public void printBigText(java.lang.String data) throws android.os.RemoteException;
/**
	* 3.13 打印倍宽文本(字体宽度为正常的两倍, 高度不变)
	* data:			打印文字
	*/
public void printDoubleWidthText(java.lang.String data) throws android.os.RemoteException;
/**
	* 3.14 打印倍高文本(字体高度为正常的两倍, 宽度不变)
	* data:			打印文字
	*/
public void printDoubleHeightText(java.lang.String data) throws android.os.RemoteException;
/**
	* 3.15 自由放大字符打印
	* data:   打印的数据
	* zoom:　  放大因子(0-7) 
	*/
public void printZoomText(java.lang.String data, int zoom) throws android.os.RemoteException;
/**
	* 3.16 带指令打印
	* data			指令
	*/
public void printRAWData(byte[] data) throws android.os.RemoteException;
/**
	* 3.17 打印机自检
	*/
public void printerSelfChecking() throws android.os.RemoteException;
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
public void printQRCode(java.lang.String data, int modulesize, int errorlevel, int alignment) throws android.os.RemoteException;
////////////////////////////////////////////////////////////////////////////
/**
	* 用于事务打印的相关打印接口,不支持极盒N900
	/
	/**
	* 4.1 开启事务打印模式
	*/
public boolean beginTransactionPrint() throws android.os.RemoteException;
/**
	* 4.2 中断事务打印模式
	*/
public boolean cancelTransactionPrint() throws android.os.RemoteException;
/**
	* 4.3 结束事务打印模式, 同时将事务中的命令集打印出来
	*/
public boolean endTransactionPrint() throws android.os.RemoteException;
/**
	* 4.4 设置对齐模式, 仅用于事务打印模式
	* alignment:	对齐方式 1--居左  2--居中 3--居右
	*/
public boolean setPrintAlignment(int alignment) throws android.os.RemoteException;
/**
	* 4.5 设置文字的水平打印位置, 仅用于事务打印模式
	* pos:			开始打印文字的水平位置, 大于getPrtLineChars的POS值将被忽略
	*/
public boolean setPrintPos(int pos) throws android.os.RemoteException;
/**
	* 4.6 设置黑白反显打印模式, 仅用于事务打印模式
	* mode:  0 恢复正常模式  1 选择反显模式
	*/
public boolean setBlackWhiteMode(int mode) throws android.os.RemoteException;
/**
	* 4.7 设置下划线打印模式, 仅用于事务打印模式
	* mode:  0 恢复到正常模式  1 选择下划线模式
	*/
public boolean setUnderline(int mode) throws android.os.RemoteException;
/**
	* 4.8 打印机切纸, 仅用于事务打印模式
	*/
public boolean trans_cutPaper() throws android.os.RemoteException;
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
public boolean trans_printBarCode(java.lang.String data, int symbology, int height, int width, int alignment, int textposition) throws android.os.RemoteException;
/**
	* 4.10 打印图片, 仅用于事务打印模式
	* bitmap: 		图片
	* width: 		图片宽度
	* alignment:	对齐方式 0--居左 , 1--居中, 2--居右
	*/
public boolean trans_printBitmap(android.graphics.Bitmap bitmap, int width, int alignment) throws android.os.RemoteException;
/**
	* 4.11 打印正常大小文本, 仅用于事务打印模式
	* data:			打印文字
	*/
public boolean trans_printNormalText(java.lang.String data) throws android.os.RemoteException;
/**
	* 4.12 打印倍高倍宽文本(四倍正常大小), 仅用于事务打印模式
	* data:			打印文字
	*/
public boolean trans_printBigText(java.lang.String data) throws android.os.RemoteException;
/**
	* 4.13  打印倍宽文本(字体宽度为正常的两倍, 高度不变), 仅用于事务打印模式
	* data:			打印文字
	*/
public boolean trans_printDoubleWidthText(java.lang.String data) throws android.os.RemoteException;
/**
	* 4.14 打印倍高文本(字体高度为正常的两倍, 宽度不变), 仅用于事务打印模式
	* data:			打印文字
	*/
public boolean trans_printDoubleHeightText(java.lang.String data) throws android.os.RemoteException;
/**
	* 4.15 带指令打印, 仅用于事务打印模式
	* data			指令
	*/
public boolean trans_printRAWData(byte[] data) throws android.os.RemoteException;
/**
	* 4.9 自由放大字符打印, 仅用于事务打印模式
	* data:   打印的数据
	* zoom:　  放大因子(0-7) 
	*/
public boolean trans_printZoomText(java.lang.String data, int zoom) throws android.os.RemoteException;
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
public boolean trans_printQRCode(java.lang.String data, int modulesize, int errorlevel, int alignment) throws android.os.RemoteException;
//////////////////////////////////////////////////////////////////////////
/**
	* 0.1 打开局域网调试模式
	*/
public boolean openLanDebug() throws android.os.RemoteException;
/**
	* 0.2 关闭局域网调试模式
	*/
public boolean closeLanDebug() throws android.os.RemoteException;
/**
	* 注册
	* cb:	实现IWoyouCallback接口的回调
	*/
public void registerCallback(com.citaq.aidlservice.IPosCallback cb) throws android.os.RemoteException;
/**
	* 反注册
	* cb:	实现IWoyouCallback接口的回调
	*/
public void unregisterCallback(com.citaq.aidlservice.IPosCallback cb) throws android.os.RemoteException;
//////////////////////////////////////////////////////////////////////////
//1.2.3 add
/**
	 * 打印二维码
	 * @param url		二维码内容
	 * @param size		尺寸, 取值范围 1 至 5
	 * @param align		对齐 0--居左 , 1--居中, 2--居右
	 * @return
	 */
public boolean printZQRCode(java.lang.String url, int size, int align) throws android.os.RemoteException;
/**
	 * 并排打印二维码
	 * @param leftUrl
	 * @param rightUrl
	 * @return
	 */
public boolean printDoubleZQRCode(java.lang.String leftUrl, java.lang.String rightUrl) throws android.os.RemoteException;
/**
	 * 并排打印二维码及图片
	 * @param url
	 * @param bytes
	 * @return
	 */
public boolean printZQRCodeImage(java.lang.String url, android.graphics.Bitmap bitmap) throws android.os.RemoteException;
/**
	 * 打印多个空白行
	 * @param lines
	 * @return
	 */
public boolean lineWrap(int lines) throws android.os.RemoteException;
//////////////////////////////////////////////////////////////////////////
//1.2.3 add 事务
/**
	 * 打印二维码
	 * @param url		二维码内容
	 * @param size		尺寸, 取值范围 1 至 5
	 * @param align		对齐 0--居左 , 1--居中, 2--居右
	 * @return
	 */
public boolean trans_printZQRCode(java.lang.String url, int size, int align) throws android.os.RemoteException;
/**
	 * 并排打印二维码
	 * @param leftUrl
	 * @param rightUrl
	 * @return
	 */
public boolean trans_printDoubleZQRCode(java.lang.String leftUrl, java.lang.String rightUrl) throws android.os.RemoteException;
/**
	 * 并排打印二维码及图片
	 * @param url
	 * @param bytes
	 * @return
	 */
public boolean trans_printZQRCodeImage(java.lang.String url, android.graphics.Bitmap bitmap) throws android.os.RemoteException;
/**
	 * 打印多个空白行
	 * @param lines
	 * @return
	 */
public boolean trans_lineWrap(int lines) throws android.os.RemoteException;
/** 
	* 第二种开灯方式
	* lightNumber:		指示灯序号, 取值从1到getMaxLights
	* blinkOnCycle:		0 不闪烁, 正值循环闪烁, 表示持续亮灯的毫秒数, 不能为负数
	* blinkOffCycle:	0 不闪烁, 正值循环闪烁, 表示持续灭灯的毫秒数, 不能为负数
	* times:			循环次数, 在闪烁情况下, 此参数有效, 0或负数为无限循环, 正数为循环次数
	*/
public boolean switchOnTimes(int lightNumber, int blinkOnCycle, int blinkOffCycle, int times) throws android.os.RemoteException;
/**
	* 列对齐方式打印
	* colsText	各列文本数据
	* colsWidth 各列宽度(以英文字符计算, 每个中文字符占两个英文字符, 每个宽度大于0, 总宽度不大于getPrtLineChars)
	* colsAlign	各列对齐方式(0居左, 1居中, 2居右)
	* 备注: 三个参数的数组长度应该一致, 如果colsText[i]的宽度大于colsWidth[i], 则文本将被截断
	*/
public boolean printColumnsText(java.lang.String[] colsText, int[] colsWidth, int[] colsAlign) throws android.os.RemoteException;
/**
	* 列对齐方式打印, 仅用于事务打印模式
	* colsText	各列文本数据
	* colsWidth 各列宽度(以英文字符计算, 每个中文字符占两个英文字符, 每个宽度大于0, 总宽度不大于getPrtLineChars)
	* colsAlign	各列对齐方式(0居左, 1居中, 2居右)
	* 备注: 三个参数的数组长度应该一致, 如果colsText[i]的宽度大于colsWidth[i], 则文本将被截断
	*/
public boolean trans_printColumnsText(java.lang.String[] colsText, int[] colsWidth, int[] colsAlign) throws android.os.RemoteException;
}
