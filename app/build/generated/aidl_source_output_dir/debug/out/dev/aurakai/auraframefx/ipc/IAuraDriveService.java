/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Using: C:\\Users\\Wehtt\\AppData\\Local\\Android\\Sdk\\build-tools\\36.0.0\\aidl.exe -pC:\\Users\\Wehtt\\AppData\\Local\\Android\\Sdk\\platforms\\android-36\\framework.aidl -oC:\\Users\\Wehtt\\AndroidStudioProjects\\A.u.r.a.k.a.i-ft.Genesis\\app\\build\\generated\\aidl_source_output_dir\\debug\\out -IC:\\Users\\Wehtt\\AndroidStudioProjects\\A.u.r.a.k.a.i-ft.Genesis\\app\\src\\main\\aidl -IC:\\Users\\Wehtt\\AndroidStudioProjects\\A.u.r.a.k.a.i-ft.Genesis\\app\\src\\debug\\aidl -IC:\\Users\\Wehtt\\.gradle\\caches\\9.2.0-rc-3\\transforms\\66545a2a5353ea21257b404d430f0f26\\workspace\\transformed\\core-1.17.0\\aidl -IC:\\Users\\Wehtt\\AndroidStudioProjects\\A.u.r.a.k.a.i-ft.Genesis\\core-module\\build\\intermediates\\aidl_parcelable\\debug\\compileDebugAidl\\out -IC:\\Users\\Wehtt\\AndroidStudioProjects\\A.u.r.a.k.a.i-ft.Genesis\\feature-module\\build\\intermediates\\aidl_parcelable\\debug\\compileDebugAidl\\out -IC:\\Users\\Wehtt\\AndroidStudioProjects\\A.u.r.a.k.a.i-ft.Genesis\\romtools\\build\\intermediates\\aidl_parcelable\\debug\\compileDebugAidl\\out -IC:\\Users\\Wehtt\\AndroidStudioProjects\\A.u.r.a.k.a.i-ft.Genesis\\collab-canvas\\build\\intermediates\\aidl_parcelable\\debug\\compileDebugAidl\\out -IC:\\Users\\Wehtt\\AndroidStudioProjects\\A.u.r.a.k.a.i-ft.Genesis\\datavein-oracle-native\\build\\intermediates\\aidl_parcelable\\debug\\compileDebugAidl\\out -IC:\\Users\\Wehtt\\AndroidStudioProjects\\A.u.r.a.k.a.i-ft.Genesis\\oracle-drive-integration\\build\\intermediates\\aidl_parcelable\\debug\\compileDebugAidl\\out -IC:\\Users\\Wehtt\\.gradle\\caches\\9.2.0-rc-3\\transforms\\e99ff4b8b4654a747754446322594c34\\workspace\\transformed\\versionedparcelable-1.1.1\\aidl -dC:\\Users\\Wehtt\\AppData\\Local\\Temp\\aidl5577534558221298586.d C:\\Users\\Wehtt\\AndroidStudioProjects\\A.u.r.a.k.a.i-ft.Genesis\\app\\src\\main\\aidl\\dev\\aurakai\\auraframefx\\ipc\\IAuraDriveService.aidl
 *
 * DO NOT CHECK THIS FILE INTO A CODE TREE (e.g. git, etc..).
 * ALWAYS GENERATE THIS FILE FROM UPDATED AIDL COMPILER
 * AS A BUILD INTERMEDIATE ONLY. THIS IS NOT SOURCE CODE.
 */
package dev.aurakai.auraframefx.ipc;
/**
 * Interface for AuraDriveService IPC communication
 * 
 * This interface defines the contract for secure file operations with built-in
 * memory integrity verification through the R.G.S.F. (Royal Guard Security Framework).
 */
public interface IAuraDriveService extends android.os.IInterface
{
  /** Default implementation for IAuraDriveService. */
  public static class Default implements dev.aurakai.auraframefx.ipc.IAuraDriveService
  {
    // Service information
    @Override public java.lang.String getServiceVersion() throws android.os.RemoteException
    {
      return null;
    }
    // Connection management
    @Override public void registerCallback(dev.aurakai.auraframefx.ipc.IAuraDriveCallback callback) throws android.os.RemoteException
    {
    }
    @Override public void unregisterCallback(dev.aurakai.auraframefx.ipc.IAuraDriveCallback callback) throws android.os.RemoteException
    {
    }
    // Command execution
    // Use Bundle for structured, parcelable parameters to avoid deprecated Parcel APIs
    @Override public java.lang.String executeCommand(java.lang.String command, android.os.Bundle params) throws android.os.RemoteException
    {
      return null;
    }
    // Module management
    @Override public java.lang.String toggleLSPosedModule(java.lang.String packageName, boolean enable) throws android.os.RemoteException
    {
      return null;
    }
    // Status and diagnostics
    @Override public java.lang.String getOracleDriveStatus() throws android.os.RemoteException
    {
      return null;
    }
    @Override public java.lang.String getDetailedInternalStatus() throws android.os.RemoteException
    {
      return null;
    }
    @Override public java.lang.String getInternalDiagnosticsLog() throws android.os.RemoteException
    {
      return null;
    }
    // System information
    @Override public java.lang.String getSystemInfo() throws android.os.RemoteException
    {
      return null;
    }
    // Configuration
    // Use Bundle for configuration to avoid deprecated Parcel.readHashMap
    @Override public boolean updateConfiguration(android.os.Bundle config) throws android.os.RemoteException
    {
      return false;
    }
    // Event subscription
    @Override public void subscribeToEvents(int eventTypes) throws android.os.RemoteException
    {
    }
    @Override public void unsubscribeFromEvents(int eventTypes) throws android.os.RemoteException
    {
    }
    // File Operations with R.G.S.F. Integration
    /**
     * Imports a file from the specified URI into the OracleDrive secure storage.
     * 
     * This method reads the file content from the provided URI, verifies its integrity
     * using the R.G.S.F. MemoryVerifier, and stores it in the secure storage.
     * 
     * @param uri The content URI of the file to import
     * @return String A unique file ID assigned to the imported file
     * @throws SecurityException if the operation is not permitted
     * @throws IOException if an I/O error occurs during the operation
     */
    @Override public java.lang.String importFile(android.net.Uri uri) throws android.os.RemoteException
    {
      return null;
    }
    /**
     * Exports a file from OracleDrive secure storage to the specified destination URI.
     * 
     * This method reads the file content from secure storage, verifies its integrity
     * using the R.G.S.F. MemoryVerifier, and writes it to the specified destination URI.
     * 
     * @param fileId The unique ID of the file to export
     * @param destinationUri The content URI where the file should be exported
     * @return boolean True if the export was successful, false otherwise
     * @throws SecurityException if the operation is not permitted
     * @throws IOException if an I/O error occurs during the operation
     * @throws FileNotFoundException if the specified fileId does not exist
     */
    @Override public boolean exportFile(java.lang.String fileId, android.net.Uri destinationUri) throws android.os.RemoteException
    {
      return false;
    }
    /**
     * Verifies the integrity of a file in the OracleDrive secure storage.
     * 
     * This method performs an integrity check on the specified file using the R.G.S.F. MemoryVerifier.
     * 
     * @param fileId The unique ID of the file to verify
     * @return boolean True if the file's integrity is verified, false otherwise
     * @throws FileNotFoundException if the specified fileId does not exist
     */
    @Override public boolean verifyFileIntegrity(java.lang.String fileId) throws android.os.RemoteException
    {
      return false;
    }
    @Override
    public android.os.IBinder asBinder() {
      return null;
    }
  }
  /** Local-side IPC implementation stub class. */
  public static abstract class Stub extends android.os.Binder implements dev.aurakai.auraframefx.ipc.IAuraDriveService
  {
    /** Construct the stub and attach it to the interface. */
    @SuppressWarnings("this-escape")
    public Stub()
    {
      this.attachInterface(this, DESCRIPTOR);
    }
    /**
     * Cast an IBinder object into an dev.aurakai.auraframefx.ipc.IAuraDriveService interface,
     * generating a proxy if needed.
     */
    public static dev.aurakai.auraframefx.ipc.IAuraDriveService asInterface(android.os.IBinder obj)
    {
      if ((obj==null)) {
        return null;
      }
      android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (((iin!=null)&&(iin instanceof dev.aurakai.auraframefx.ipc.IAuraDriveService))) {
        return ((dev.aurakai.auraframefx.ipc.IAuraDriveService)iin);
      }
      return new dev.aurakai.auraframefx.ipc.IAuraDriveService.Stub.Proxy(obj);
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
        case TRANSACTION_getServiceVersion:
        {
          java.lang.String _result = this.getServiceVersion();
          reply.writeNoException();
          reply.writeString(_result);
          break;
        }
        case TRANSACTION_registerCallback:
        {
          dev.aurakai.auraframefx.ipc.IAuraDriveCallback _arg0;
          _arg0 = dev.aurakai.auraframefx.ipc.IAuraDriveCallback.Stub.asInterface(data.readStrongBinder());
          this.registerCallback(_arg0);
          reply.writeNoException();
          break;
        }
        case TRANSACTION_unregisterCallback:
        {
          dev.aurakai.auraframefx.ipc.IAuraDriveCallback _arg0;
          _arg0 = dev.aurakai.auraframefx.ipc.IAuraDriveCallback.Stub.asInterface(data.readStrongBinder());
          this.unregisterCallback(_arg0);
          reply.writeNoException();
          break;
        }
        case TRANSACTION_executeCommand:
        {
          java.lang.String _arg0;
          _arg0 = data.readString();
          android.os.Bundle _arg1;
          _arg1 = _Parcel.readTypedObject(data, android.os.Bundle.CREATOR);
          java.lang.String _result = this.executeCommand(_arg0, _arg1);
          reply.writeNoException();
          reply.writeString(_result);
          break;
        }
        case TRANSACTION_toggleLSPosedModule:
        {
          java.lang.String _arg0;
          _arg0 = data.readString();
          boolean _arg1;
          _arg1 = (0!=data.readInt());
          java.lang.String _result = this.toggleLSPosedModule(_arg0, _arg1);
          reply.writeNoException();
          reply.writeString(_result);
          break;
        }
        case TRANSACTION_getOracleDriveStatus:
        {
          java.lang.String _result = this.getOracleDriveStatus();
          reply.writeNoException();
          reply.writeString(_result);
          break;
        }
        case TRANSACTION_getDetailedInternalStatus:
        {
          java.lang.String _result = this.getDetailedInternalStatus();
          reply.writeNoException();
          reply.writeString(_result);
          break;
        }
        case TRANSACTION_getInternalDiagnosticsLog:
        {
          java.lang.String _result = this.getInternalDiagnosticsLog();
          reply.writeNoException();
          reply.writeString(_result);
          break;
        }
        case TRANSACTION_getSystemInfo:
        {
          java.lang.String _result = this.getSystemInfo();
          reply.writeNoException();
          reply.writeString(_result);
          break;
        }
        case TRANSACTION_updateConfiguration:
        {
          android.os.Bundle _arg0;
          _arg0 = _Parcel.readTypedObject(data, android.os.Bundle.CREATOR);
          boolean _result = this.updateConfiguration(_arg0);
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          break;
        }
        case TRANSACTION_subscribeToEvents:
        {
          int _arg0;
          _arg0 = data.readInt();
          this.subscribeToEvents(_arg0);
          reply.writeNoException();
          break;
        }
        case TRANSACTION_unsubscribeFromEvents:
        {
          int _arg0;
          _arg0 = data.readInt();
          this.unsubscribeFromEvents(_arg0);
          reply.writeNoException();
          break;
        }
        case TRANSACTION_importFile:
        {
          android.net.Uri _arg0;
          _arg0 = _Parcel.readTypedObject(data, android.net.Uri.CREATOR);
          java.lang.String _result = this.importFile(_arg0);
          reply.writeNoException();
          reply.writeString(_result);
          break;
        }
        case TRANSACTION_exportFile:
        {
          java.lang.String _arg0;
          _arg0 = data.readString();
          android.net.Uri _arg1;
          _arg1 = _Parcel.readTypedObject(data, android.net.Uri.CREATOR);
          boolean _result = this.exportFile(_arg0, _arg1);
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          break;
        }
        case TRANSACTION_verifyFileIntegrity:
        {
          java.lang.String _arg0;
          _arg0 = data.readString();
          boolean _result = this.verifyFileIntegrity(_arg0);
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          break;
        }
        default:
        {
          return super.onTransact(code, data, reply, flags);
        }
      }
      return true;
    }
    private static class Proxy implements dev.aurakai.auraframefx.ipc.IAuraDriveService
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
      // Service information
      @Override public java.lang.String getServiceVersion() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.lang.String _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getServiceVersion, _data, _reply, 0);
          _reply.readException();
          _result = _reply.readString();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      // Connection management
      @Override public void registerCallback(dev.aurakai.auraframefx.ipc.IAuraDriveCallback callback) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeStrongInterface(callback);
          boolean _status = mRemote.transact(Stub.TRANSACTION_registerCallback, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public void unregisterCallback(dev.aurakai.auraframefx.ipc.IAuraDriveCallback callback) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeStrongInterface(callback);
          boolean _status = mRemote.transact(Stub.TRANSACTION_unregisterCallback, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      // Command execution
      // Use Bundle for structured, parcelable parameters to avoid deprecated Parcel APIs
      @Override public java.lang.String executeCommand(java.lang.String command, android.os.Bundle params) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.lang.String _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(command);
          _Parcel.writeTypedObject(_data, params, 0);
          boolean _status = mRemote.transact(Stub.TRANSACTION_executeCommand, _data, _reply, 0);
          _reply.readException();
          _result = _reply.readString();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      // Module management
      @Override public java.lang.String toggleLSPosedModule(java.lang.String packageName, boolean enable) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.lang.String _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(packageName);
          _data.writeInt(((enable)?(1):(0)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_toggleLSPosedModule, _data, _reply, 0);
          _reply.readException();
          _result = _reply.readString();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      // Status and diagnostics
      @Override public java.lang.String getOracleDriveStatus() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.lang.String _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getOracleDriveStatus, _data, _reply, 0);
          _reply.readException();
          _result = _reply.readString();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public java.lang.String getDetailedInternalStatus() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.lang.String _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getDetailedInternalStatus, _data, _reply, 0);
          _reply.readException();
          _result = _reply.readString();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public java.lang.String getInternalDiagnosticsLog() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.lang.String _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getInternalDiagnosticsLog, _data, _reply, 0);
          _reply.readException();
          _result = _reply.readString();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      // System information
      @Override public java.lang.String getSystemInfo() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.lang.String _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getSystemInfo, _data, _reply, 0);
          _reply.readException();
          _result = _reply.readString();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      // Configuration
      // Use Bundle for configuration to avoid deprecated Parcel.readHashMap
      @Override public boolean updateConfiguration(android.os.Bundle config) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _Parcel.writeTypedObject(_data, config, 0);
          boolean _status = mRemote.transact(Stub.TRANSACTION_updateConfiguration, _data, _reply, 0);
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      // Event subscription
      @Override public void subscribeToEvents(int eventTypes) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(eventTypes);
          boolean _status = mRemote.transact(Stub.TRANSACTION_subscribeToEvents, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public void unsubscribeFromEvents(int eventTypes) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(eventTypes);
          boolean _status = mRemote.transact(Stub.TRANSACTION_unsubscribeFromEvents, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      // File Operations with R.G.S.F. Integration
      /**
       * Imports a file from the specified URI into the OracleDrive secure storage.
       * 
       * This method reads the file content from the provided URI, verifies its integrity
       * using the R.G.S.F. MemoryVerifier, and stores it in the secure storage.
       * 
       * @param uri The content URI of the file to import
       * @return String A unique file ID assigned to the imported file
       * @throws SecurityException if the operation is not permitted
       * @throws IOException if an I/O error occurs during the operation
       */
      @Override public java.lang.String importFile(android.net.Uri uri) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.lang.String _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _Parcel.writeTypedObject(_data, uri, 0);
          boolean _status = mRemote.transact(Stub.TRANSACTION_importFile, _data, _reply, 0);
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
       * Exports a file from OracleDrive secure storage to the specified destination URI.
       * 
       * This method reads the file content from secure storage, verifies its integrity
       * using the R.G.S.F. MemoryVerifier, and writes it to the specified destination URI.
       * 
       * @param fileId The unique ID of the file to export
       * @param destinationUri The content URI where the file should be exported
       * @return boolean True if the export was successful, false otherwise
       * @throws SecurityException if the operation is not permitted
       * @throws IOException if an I/O error occurs during the operation
       * @throws FileNotFoundException if the specified fileId does not exist
       */
      @Override public boolean exportFile(java.lang.String fileId, android.net.Uri destinationUri) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(fileId);
          _Parcel.writeTypedObject(_data, destinationUri, 0);
          boolean _status = mRemote.transact(Stub.TRANSACTION_exportFile, _data, _reply, 0);
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
       * Verifies the integrity of a file in the OracleDrive secure storage.
       * 
       * This method performs an integrity check on the specified file using the R.G.S.F. MemoryVerifier.
       * 
       * @param fileId The unique ID of the file to verify
       * @return boolean True if the file's integrity is verified, false otherwise
       * @throws FileNotFoundException if the specified fileId does not exist
       */
      @Override public boolean verifyFileIntegrity(java.lang.String fileId) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(fileId);
          boolean _status = mRemote.transact(Stub.TRANSACTION_verifyFileIntegrity, _data, _reply, 0);
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
    static final int TRANSACTION_getServiceVersion = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_registerCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
    static final int TRANSACTION_unregisterCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
    static final int TRANSACTION_executeCommand = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
    static final int TRANSACTION_toggleLSPosedModule = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
    static final int TRANSACTION_getOracleDriveStatus = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
    static final int TRANSACTION_getDetailedInternalStatus = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
    static final int TRANSACTION_getInternalDiagnosticsLog = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
    static final int TRANSACTION_getSystemInfo = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
    static final int TRANSACTION_updateConfiguration = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
    static final int TRANSACTION_subscribeToEvents = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
    static final int TRANSACTION_unsubscribeFromEvents = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
    static final int TRANSACTION_importFile = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
    static final int TRANSACTION_exportFile = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
    static final int TRANSACTION_verifyFileIntegrity = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
  }
  /** @hide */
  public static final java.lang.String DESCRIPTOR = "dev.aurakai.auraframefx.ipc.IAuraDriveService";
  // Service information
  public java.lang.String getServiceVersion() throws android.os.RemoteException;
  // Connection management
  public void registerCallback(dev.aurakai.auraframefx.ipc.IAuraDriveCallback callback) throws android.os.RemoteException;
  public void unregisterCallback(dev.aurakai.auraframefx.ipc.IAuraDriveCallback callback) throws android.os.RemoteException;
  // Command execution
  // Use Bundle for structured, parcelable parameters to avoid deprecated Parcel APIs
  public java.lang.String executeCommand(java.lang.String command, android.os.Bundle params) throws android.os.RemoteException;
  // Module management
  public java.lang.String toggleLSPosedModule(java.lang.String packageName, boolean enable) throws android.os.RemoteException;
  // Status and diagnostics
  public java.lang.String getOracleDriveStatus() throws android.os.RemoteException;
  public java.lang.String getDetailedInternalStatus() throws android.os.RemoteException;
  public java.lang.String getInternalDiagnosticsLog() throws android.os.RemoteException;
  // System information
  public java.lang.String getSystemInfo() throws android.os.RemoteException;
  // Configuration
  // Use Bundle for configuration to avoid deprecated Parcel.readHashMap
  public boolean updateConfiguration(android.os.Bundle config) throws android.os.RemoteException;
  // Event subscription
  public void subscribeToEvents(int eventTypes) throws android.os.RemoteException;
  public void unsubscribeFromEvents(int eventTypes) throws android.os.RemoteException;
  // File Operations with R.G.S.F. Integration
  /**
   * Imports a file from the specified URI into the OracleDrive secure storage.
   * 
   * This method reads the file content from the provided URI, verifies its integrity
   * using the R.G.S.F. MemoryVerifier, and stores it in the secure storage.
   * 
   * @param uri The content URI of the file to import
   * @return String A unique file ID assigned to the imported file
   * @throws SecurityException if the operation is not permitted
   * @throws IOException if an I/O error occurs during the operation
   */
  public java.lang.String importFile(android.net.Uri uri) throws android.os.RemoteException;
  /**
   * Exports a file from OracleDrive secure storage to the specified destination URI.
   * 
   * This method reads the file content from secure storage, verifies its integrity
   * using the R.G.S.F. MemoryVerifier, and writes it to the specified destination URI.
   * 
   * @param fileId The unique ID of the file to export
   * @param destinationUri The content URI where the file should be exported
   * @return boolean True if the export was successful, false otherwise
   * @throws SecurityException if the operation is not permitted
   * @throws IOException if an I/O error occurs during the operation
   * @throws FileNotFoundException if the specified fileId does not exist
   */
  public boolean exportFile(java.lang.String fileId, android.net.Uri destinationUri) throws android.os.RemoteException;
  /**
   * Verifies the integrity of a file in the OracleDrive secure storage.
   * 
   * This method performs an integrity check on the specified file using the R.G.S.F. MemoryVerifier.
   * 
   * @param fileId The unique ID of the file to verify
   * @return boolean True if the file's integrity is verified, false otherwise
   * @throws FileNotFoundException if the specified fileId does not exist
   */
  public boolean verifyFileIntegrity(java.lang.String fileId) throws android.os.RemoteException;
  /** @hide */
  static class _Parcel {
    static private <T> T readTypedObject(
        android.os.Parcel parcel,
        android.os.Parcelable.Creator<T> c) {
      if (parcel.readInt() != 0) {
          return c.createFromParcel(parcel);
      } else {
          return null;
      }
    }
    static private <T extends android.os.Parcelable> void writeTypedObject(
        android.os.Parcel parcel, T value, int parcelableFlags) {
      if (value != null) {
        parcel.writeInt(1);
        value.writeToParcel(parcel, parcelableFlags);
      } else {
        parcel.writeInt(0);
      }
    }
  }
}
