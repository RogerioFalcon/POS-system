/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\04_Work\\Android\\POS\\PosService\\app\\src\\main\\aidl\\com\\citaq\\aidlservice\\IPosTeleCallback.aidl
 */
package com.citaq.aidlservice;
public interface IPosTeleCallback extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.citaq.aidlservice.IPosTeleCallback
{
private static final java.lang.String DESCRIPTOR = "com.citaq.aidlservice.IPosTeleCallback";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.citaq.aidlservice.IPosTeleCallback interface,
 * generating a proxy if needed.
 */
public static com.citaq.aidlservice.IPosTeleCallback asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.citaq.aidlservice.IPosTeleCallback))) {
return ((com.citaq.aidlservice.IPosTeleCallback)iin);
}
return new com.citaq.aidlservice.IPosTeleCallback.Stub.Proxy(obj);
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
case TRANSACTION_onCalling:
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
java.lang.String _arg4;
_arg4 = data.readString();
this.onCalling(_arg0, _arg1, _arg2, _arg3, _arg4);
reply.writeNoException();
return true;
}
default:
{
return super.onTransact(code, data, reply, flags);
}
}
}
private static class Proxy implements com.citaq.aidlservice.IPosTeleCallback
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
	* 来电的月日时分及电话号码, 如主叫方不同意显示的话,号码会显示成P
	*/
@Override public void onCalling(int month, int day, int hour, int minute, java.lang.String phone) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(month);
_data.writeInt(day);
_data.writeInt(hour);
_data.writeInt(minute);
_data.writeString(phone);
mRemote.transact(Stub.TRANSACTION_onCalling, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_onCalling = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
/**
	* 来电的月日时分及电话号码, 如主叫方不同意显示的话,号码会显示成P
	*/
public void onCalling(int month, int day, int hour, int minute, java.lang.String phone) throws android.os.RemoteException;
}
