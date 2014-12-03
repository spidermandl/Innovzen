package com.innovzen.handlers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Date;

import com.innovzen.application.InnovzenApplication;
import com.innovzen.bluetooth.BluetoothCommand;


import android.os.Environment;
import android.util.Log;

/**
 * 程序崩溃处理类
 * @author Desmond Duan
 *
 */
public class UEHandler implements Thread.UncaughtExceptionHandler {
	private InnovzenApplication iApp;
	private File fileErrorLog;

	public UEHandler(InnovzenApplication app) {
		iApp = app;
		String path=getLogFilePath();
		if(path!=null)
			fileErrorLog = new File(path);
	}

	//@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		BluetoothCommand mBC=BluetoothCommand.getInstance();
		if(mBC!=null){
			mBC.sendCommand(BluetoothCommand.START_MACHINE_VALUES);
		}
		// fetch Excpetion Info
		String info = null;
		ByteArrayOutputStream baos = null;
		PrintStream printStream = null;
		try {
			baos = new ByteArrayOutputStream();
			printStream = new PrintStream(baos);
			ex.printStackTrace(printStream);
			byte[] data = baos.toByteArray();
			info = new String(data);
			data = null;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (printStream != null) {
					printStream.close();
				}
				if (baos != null) {
					baos.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// print
		long threadId = thread.getId();
		Log.d("ANDROID_LAB", "Thread.getName()=" + thread.getName() + " id=" + threadId + " state=" + thread.getState());
		Log.d("ANDROID_LAB", "Error[" + info + "]");
		if (threadId != 1) {

		} else {
			write2ErrorLog(fileErrorLog, info);
		}
		android.os.Process.killProcess(android.os.Process.myPid()); 

		//SgcardApplication.getInstance().sendClickMessage(Constant.KILL_PROCESS, null);
	}

	private void write2ErrorLog(File file, String content) {
		FileOutputStream fos = null;
		try {
			if (file.exists()) {
				file.delete();
			} else {
				file.getParentFile().mkdirs();
			}
			file.createNewFile();
			fos = new FileOutputStream(file);
			fos.write(content.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private String getLogFilePath(){
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
			File f=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator + "Innovzen");
			if(!f.exists()){
				if(f.mkdirs())
					return f.getAbsolutePath()+File.separator+new Date().getTime();
			}
			else
				return f.getAbsolutePath()+File.separator+new Date().getTime();
		}
		return null;
	}
}