/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\04_Work\\Android\\POS\\PosService\\app\\src\\main\\aidl\\com\\citaq\\aidlservice\\IPosCallback.aidl
 */
package com.citaq.aidlservice;
public interface IPosCallback extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.citaq.aidlservice.IPosCallback
{
private static final java.lang.String DESCRIPTOR = "com.citaq.aidlservice.IPosCallback";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.citaq.aidlservice.IPosCallback interface,
 * generating a proxy if needed.
 */
public static com.citaq.aidlservice.IPosCallback asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.citaq.aidlservice.IPosCallback))) {
return ((com.citaq.aidlservice.IPosCallback)iin);
}
return new com.citaq.aidlservice.IPosCallback.Stub.Proxy(obj);
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
case TRANSACTION_onReturnValue:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
this.onReturnValue(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onRaiseException:
{
data.enforceInterface(descriptor);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
this.onRaiseException(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_onSuccessOperation:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
this.onSuccessOperation(_arg0);
reply.writeNoException();
return true;
}
default:
{
return super.onTransact(code, data, reply, flags);
}
}
}
private static class Proxy implements com.citaq.aidlservice.IPosCallback
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
/**
	* 返回值, 如取打印机主板系列号, 由于是串口读取操作, 耗时较多, 为了不影响调用主进程, 采用得到结果后回调
	*/
@Override public void onReturnValue(java.lang.String val) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(val);
mRemote.transact(Stub.TRANSACTION_onReturnValue, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
	* 硬件如果有故障, 自动回调主进程, 传入错误代码及错误描述
	* msg: 结果信息
	*/
@Override public void onRaiseException(int code, java.lang.String e) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(code);
_data.writeString(e);
mRemote.transact(Stub.TRANSACTION_onRaiseException, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
	* 硬件指令操作成功回调函数
	* msg: 结果描述
	*/
@Override public void onSuccessOperation(java.lang.String msg) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(msg);
mRemote.transact(Stub.TRANSACTION_onSuccessOperation, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_onReturnValue = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_onRaiseException = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_onSuccessOperation = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
}
/**
	* 返回值, 如取打印机主板系列号, 由于是串口读取操作, 耗时较多, 为了不影响调用主进程, 采用得到结果后回调
	*/
public void onReturnValue(java.lang.String val) throws android.os.RemoteException;
/**
	* 硬件如果有故障, 自动回调主进程, 传入错误代码及错误描述
	* msg: 结果信息
	*/
public void onRaiseException(int code, java.lang.String e) throws android.os.RemoteException;
/**
	* 硬件指令操作成功回调函数
	* msg: 结果描述
	*/
public void onSuccessOperation(java.lang.String msg) throws android.os.RemoteException;
}
