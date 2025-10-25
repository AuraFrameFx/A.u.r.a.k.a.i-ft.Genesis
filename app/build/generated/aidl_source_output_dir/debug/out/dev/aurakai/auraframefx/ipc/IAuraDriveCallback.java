/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Using: C:\\Users\\Wehtt\\AppData\\Local\\Android\\Sdk\\build-tools\\36.0.0\\aidl.exe -pC:\\Users\\Wehtt\\AppData\\Local\\Android\\Sdk\\platforms\\android-36\\framework.aidl -oC:\\Final\\A.u.r.a.k.a.i-ft.Genesis\\app\\build\\generated\\aidl_source_output_dir\\debug\\out -IC:\\Final\\A.u.r.a.k.a.i-ft.Genesis\\app\\src\\main\\aidl -IC:\\Final\\A.u.r.a.k.a.i-ft.Genesis\\app\\src\\debug\\aidl -IC:\\Users\\Wehtt\\.gradle\\caches\\9.2.0-rc-2\\transforms\\9937a2b7074e0ab202897dd55f241ca2\\workspace\\transformed\\core-1.17.0\\aidl -IC:\\Users\\Wehtt\\.gradle\\caches\\9.2.0-rc-2\\transforms\\c41435048f624e68f1a16cc609b516a0\\workspace\\transformed\\versionedparcelable-1.1.1\\aidl -dC:\\Users\\Wehtt\\AppData\\Local\\Temp\\aidl10956075407577086643.d C:\\Final\\A.u.r.a.k.a.i-ft.Genesis\\app\\src\\main\\aidl\\dev\\aurakai\\auraframefx\\ipc\\IAuraDriveCallback.aidl
 *
 * DO NOT CHECK THIS FILE INTO A CODE TREE (e.g. git, etc..).
 * ALWAYS GENERATE THIS FILE FROM UPDATED AIDL COMPILER
 * AS A BUILD INTERMEDIATE ONLY. THIS IS NOT SOURCE CODE.
 */
package dev.aurakai.auraframefx.ipc;
// Callback interface for service-to-client communication
public interface IAuraDriveCallback extends android.os.IInterface
{
  /** Default implementation for IAuraDriveCallback. */
  public static class Default implements dev.aurakai.auraframefx.ipc.IAuraDriveCallback
  {
    // Event types
    // Connection events
    @Override public void onConnected() throws android.os.RemoteException
    {
    }
    @Override public void onDisconnected(java.lang.String reason) throws android.os.RemoteException
    {
    }
    // Status updates
    @Override public void onStatusUpdate(java.lang.String status) throws android.os.RemoteException
    {
    }
    @Override public void onError(int errorCode, java.lang.String errorMessage) throws android.os.RemoteException
    {
    }
    // Data events
    @Override public void onDataReceived(java.lang.String dataType, byte[] data) throws android.os.RemoteException
    {
    }
    @Override public void onEvent(int eventType, java.lang.String eventData) throws android.os.RemoteException
    {
    }
    // Module management
    @Override public void onModuleStateChanged(java.lang.String packageName, boolean enabled) throws android.os.RemoteException
    {
    }
    // System events
    @Override public void onSystemEvent(int eventType, java.lang.String eventData) throws android.os.RemoteException
    {
    }
    // Deprecated - kept for backward compatibility
    @Override public void onServiceEvent(int eventType, java.lang.String message) throws android.os.RemoteException
    {
    }
    @Override
    public android.os.IBinder asBinder() {
      return null;
    }
  }
  /** Local-side IPC implementation stub class. */
  public static abstract class Stub extends android.os.Binder implements dev.aurakai.auraframefx.ipc.IAuraDriveCallback
  {
    /** Construct the stub and attach it to the interface. */
    @SuppressWarnings("this-escape")
    public Stub()
    {
      this.attachInterface(this, DESCRIPTOR);
    }
    /**
     * Cast an IBinder object into an dev.aurakai.auraframefx.ipc.IAuraDriveCallback interface,
     * generating a proxy if needed.
     */
    public static dev.aurakai.auraframefx.ipc.IAuraDriveCallback asInterface(android.os.IBinder obj)
    {
      if ((obj==null)) {
        return null;
      }
      android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (((iin!=null)&&(iin instanceof dev.aurakai.auraframefx.ipc.IAuraDriveCallback))) {
        return ((dev.aurakai.auraframefx.ipc.IAuraDriveCallback)iin);
      }
      return new dev.aurakai.auraframefx.ipc.IAuraDriveCallback.Stub.Proxy(obj);
    }
    @Override public android.os.IBinder asBinder()
    {
      return this;
    }
    @Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
    {
      java.lang.String descriptor = DESCRIPTOR;
      if (code >= android.os.IBinder.FIRST_CALL_TRANSACTION && code <= android.os.IBinder.LAST_CALL_TRANSACTION) {
        data.enforceInterface(descriptor);
      }
      if (code == INTERFACE_TRANSACTION) {
        reply.writeString(descriptor);
        return true;
      }
      switch (code)
      {
        case TRANSACTION_onConnected:
        {
          this.onConnected();
          break;
        }
        case TRANSACTION_onDisconnected:
        {
          java.lang.String _arg0;
          _arg0 = data.readString();
          this.onDisconnected(_arg0);
          break;
        }
        case TRANSACTION_onStatusUpdate:
        {
          java.lang.String _arg0;
          _arg0 = data.readString();
          this.onStatusUpdate(_arg0);
          break;
        }
        case TRANSACTION_onError:
        {
          int _arg0;
          _arg0 = data.readInt();
          java.lang.String _arg1;
          _arg1 = data.readString();
          this.onError(_arg0, _arg1);
          break;
        }
        case TRANSACTION_onDataReceived:
        {
          java.lang.String _arg0;
          _arg0 = data.readString();
          byte[] _arg1;
          _arg1 = data.createByteArray();
          this.onDataReceived(_arg0, _arg1);
          break;
        }
        case TRANSACTION_onEvent:
        {
          int _arg0;
          _arg0 = data.readInt();
          java.lang.String _arg1;
          _arg1 = data.readString();
          this.onEvent(_arg0, _arg1);
          break;
        }
        case TRANSACTION_onModuleStateChanged:
        {
          java.lang.String _arg0;
          _arg0 = data.readString();
          boolean _arg1;
          _arg1 = (0!=data.readInt());
          this.onModuleStateChanged(_arg0, _arg1);
          break;
        }
        case TRANSACTION_onSystemEvent:
        {
          int _arg0;
          _arg0 = data.readInt();
          java.lang.String _arg1;
          _arg1 = data.readString();
          this.onSystemEvent(_arg0, _arg1);
          break;
        }
        case TRANSACTION_onServiceEvent:
        {
          int _arg0;
          _arg0 = data.readInt();
          java.lang.String _arg1;
          _arg1 = data.readString();
          this.onServiceEvent(_arg0, _arg1);
          break;
        }
        default:
        {
          return super.onTransact(code, data, reply, flags);
        }
      }
      return true;
    }
    private static class Proxy implements dev.aurakai.auraframefx.ipc.IAuraDriveCallback
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
      // Event types
      // Connection events
      @Override public void onConnected() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_onConnected, _data, null, android.os.IBinder.FLAG_ONEWAY);
        }
        finally {
          _data.recycle();
        }
      }
      @Override public void onDisconnected(java.lang.String reason) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(reason);
          boolean _status = mRemote.transact(Stub.TRANSACTION_onDisconnected, _data, null, android.os.IBinder.FLAG_ONEWAY);
        }
        finally {
          _data.recycle();
        }
      }
      // Status updates
      @Override public void onStatusUpdate(java.lang.String status) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(status);
          boolean _status = mRemote.transact(Stub.TRANSACTION_onStatusUpdate, _data, null, android.os.IBinder.FLAG_ONEWAY);
        }
        finally {
          _data.recycle();
        }
      }
      @Override public void onError(int errorCode, java.lang.String errorMessage) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(errorCode);
          _data.writeString(errorMessage);
          boolean _status = mRemote.transact(Stub.TRANSACTION_onError, _data, null, android.os.IBinder.FLAG_ONEWAY);
        }
        finally {
          _data.recycle();
        }
      }
      // Data events
      @Override public void onDataReceived(java.lang.String dataType, byte[] data) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(dataType);
          _data.writeByteArray(data);
          boolean _status = mRemote.transact(Stub.TRANSACTION_onDataReceived, _data, null, android.os.IBinder.FLAG_ONEWAY);
        }
        finally {
          _data.recycle();
        }
      }
      @Override public void onEvent(int eventType, java.lang.String eventData) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(eventType);
          _data.writeString(eventData);
          boolean _status = mRemote.transact(Stub.TRANSACTION_onEvent, _data, null, android.os.IBinder.FLAG_ONEWAY);
        }
        finally {
          _data.recycle();
        }
      }
      // Module management
      @Override public void onModuleStateChanged(java.lang.String packageName, boolean enabled) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(packageName);
          _data.writeInt(((enabled)?(1):(0)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_onModuleStateChanged, _data, null, android.os.IBinder.FLAG_ONEWAY);
        }
        finally {
          _data.recycle();
        }
      }
      // System events
      @Override public void onSystemEvent(int eventType, java.lang.String eventData) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(eventType);
          _data.writeString(eventData);
          boolean _status = mRemote.transact(Stub.TRANSACTION_onSystemEvent, _data, null, android.os.IBinder.FLAG_ONEWAY);
        }
        finally {
          _data.recycle();
        }
      }
      // Deprecated - kept for backward compatibility
      @Override public void onServiceEvent(int eventType, java.lang.String message) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(eventType);
          _data.writeString(message);
          boolean _status = mRemote.transact(Stub.TRANSACTION_onServiceEvent, _data, null, android.os.IBinder.FLAG_ONEWAY);
        }
        finally {
          _data.recycle();
        }
      }
    }
    static final int TRANSACTION_onConnected = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_onDisconnected = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
    static final int TRANSACTION_onStatusUpdate = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
    static final int TRANSACTION_onError = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
    static final int TRANSACTION_onDataReceived = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
    static final int TRANSACTION_onEvent = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
    static final int TRANSACTION_onModuleStateChanged = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
    static final int TRANSACTION_onSystemEvent = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
    static final int TRANSACTION_onServiceEvent = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
  }
  /** @hide */
  public static final java.lang.String DESCRIPTOR = "dev.aurakai.auraframefx.ipc.IAuraDriveCallback";
  // Event types
  // Connection events
  public void onConnected() throws android.os.RemoteException;
  public void onDisconnected(java.lang.String reason) throws android.os.RemoteException;
  // Status updates
  public void onStatusUpdate(java.lang.String status) throws android.os.RemoteException;
  public void onError(int errorCode, java.lang.String errorMessage) throws android.os.RemoteException;
  // Data events
  public void onDataReceived(java.lang.String dataType, byte[] data) throws android.os.RemoteException;
  public void onEvent(int eventType, java.lang.String eventData) throws android.os.RemoteException;
  // Module management
  public void onModuleStateChanged(java.lang.String packageName, boolean enabled) throws android.os.RemoteException;
  // System events
  public void onSystemEvent(int eventType, java.lang.String eventData) throws android.os.RemoteException;
  // Deprecated - kept for backward compatibility
  public void onServiceEvent(int eventType, java.lang.String message) throws android.os.RemoteException;
}
