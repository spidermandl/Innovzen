package com.innovzen.bluetooth;

import java.util.Arrays;
import java.util.HashMap;

import com.innovzen.utils.MyPreference;

import android.R.integer;
import android.content.Context;

/**
 * 蓝牙命令
 * 
 * @author desmond.duan
 * 
 */
public class BluetoothCommand {

	/* BluetoothService */

	// 启动机器
	public static final int START_MACHINE = 0xF0;
	// 安卓平板显示手控器
	public static final int ANDROID_TABLET = 0x83;
	// 结束字节
	public static final int END_MACHINE = 0xF1;
	public static final int POWER_ON_OFF = 0x01;
	public static final int BALANCE = 0x10;
	public static final int RELAX = 0x11;
	public static final int MY_SESSION = 0x12;
	public static final int PERFORMANCE = 0x13;
	public static final int PAUSE = 0x19;
	public static final int BREATH_UP = 0x15;
	public static final int BREATH_DOWM = 0x16;
	public static final int BACKREST_ADJUST_UP = 0x64;
	public static final int BACKREST_ADJUST_UP_STOP = 0x65;
	public static final int BACKREST_ADJUST_DOWN = 0x66;
	public static final int BACKREST_ADJUST_DOWN_STOP = 0x67;
	public static final int FOOT_ADJUST_UP = 0x68;
	public static final int FOOT_ADJUST_UP_STOP = 0x69;
	public static final int FOOT_ADJUST_DOWN = 0x6a;
	public static final int FOOT_ADJUST_DOWN_STOP = 0x6b;
	public static final int ZERO_GRAVITY = 0x1d;
	public static final int BLUETOOTH_ON_OFF = 0x17;
	public static final int TIME_MACHINE = 0x1e;
	public static final int SWING = 0x19;
	public static final int OXYGEN = 0x1a;
	public static final int LEDS = 0x1f;
	public static final int HEAT = 0x1b;
	public static final int BRGINNER = 0x26;
	public static final int INTERMEDIATE = 0x27;
	public static final int CAUSTOMISE = 0x28;
	public static final int PRO = 0x29;
	public static final int FIVE_MIN = 0x20;
	public static final int TEN_MIN = 0x21;
	public static final int FIFTEEN_MIN = 0x22;
	public static final int TWENTY_MIN = 0x23;
	public static final int TWENTY_FIVE_MIN = 0x24;
	public static final int THIRTY_MIN = 0x25;
	// 开机关机命令
	public static int START_MACHINE_VALUES[] = { START_MACHINE, ANDROID_TABLET,
			POWER_ON_OFF, 0x11, END_MACHINE };
	// 暂停
	public static int PAUSE_MACHINE_VALUES[] = { START_MACHINE, ANDROID_TABLET,
			PAUSE, 0x11, END_MACHINE };
	// Blance命令
	public static int BLANCE_MACHINE_VALUES[] = { START_MACHINE,
			ANDROID_TABLET, BALANCE, 0x11, END_MACHINE };
	// Relax命令
	public static int RELAX_MACHINE_VALUES[] = { START_MACHINE, ANDROID_TABLET,
			RELAX, 0x11, END_MACHINE };
	// Performance命令
	public static int PERFORMANCE_MACHINE_VALUES[] = { START_MACHINE,
			ANDROID_TABLET, PERFORMANCE, 0x11, END_MACHINE };
	// breathe +命令
	public static int BREATHE_UP_MACHINE_VALUES[] = { START_MACHINE,
			ANDROID_TABLET, BREATH_UP, 0x11, END_MACHINE };
	// breathe -命令
	public static int BREATHE_DOWN_MACHINE_VALUES[] = { START_MACHINE,
			ANDROID_TABLET, BREATH_DOWM, 0x11, END_MACHINE };
	// backrest up命令
	public static int BACK_REST_UP_MACHINE_VALUES[] = { START_MACHINE,
			ANDROID_TABLET, BACKREST_ADJUST_UP, 0x11, END_MACHINE };
	// backrest down命令
	public static int BACK_REST_DOWN_MACHINE_VALUES[] = { START_MACHINE,
			ANDROID_TABLET, BACKREST_ADJUST_DOWN, 0x11, END_MACHINE };
	// backrest up暂停命令
	public static int BACK_REST_UP_STOP_MACHINE_VALUES[] = { START_MACHINE,
			ANDROID_TABLET, BACKREST_ADJUST_UP_STOP, 0x11, END_MACHINE };
	// backrest down暂停命令
	public static int BACK_REST_DOWN_STOP_MACHINE_VALUES[] = { START_MACHINE,
			ANDROID_TABLET, BACKREST_ADJUST_DOWN_STOP, 0x11, END_MACHINE };
	// foot up命令
	public static int FOOT_UP_MACHINE_VALUES[] = { START_MACHINE,
			ANDROID_TABLET, FOOT_ADJUST_UP, 0x11, END_MACHINE };
	// foot down 命令
	public static int FOOT_DOWN_MACHINE_VALUES[] = { START_MACHINE,
			ANDROID_TABLET, FOOT_ADJUST_DOWN, 0x11, END_MACHINE };
	// foot up stop命令
	public static int FOOT_UP_STOP_MACHINE_VALUES[] = { START_MACHINE,
			ANDROID_TABLET, FOOT_ADJUST_UP_STOP, 0x11, END_MACHINE };
	// foot down stop 命令
	public static int FOOT_DOWN_STOP_MACHINE_VALUES[] = { START_MACHINE,
			ANDROID_TABLET, FOOT_ADJUST_DOWN_STOP, 0x11, END_MACHINE };
	// Zero gravity命令
	public static int ZERO_GRAVITY_MACHINE_VALUES[] = { START_MACHINE,
			ANDROID_TABLET, ZERO_GRAVITY, 0x11, END_MACHINE };
	// Oxygen命令
	public static int OXYGEN_MACHINE_VALUES[] = { START_MACHINE,
			ANDROID_TABLET, 0x1a, 0x11, END_MACHINE };
	// Heat命令
	public static int HEAT_MACHINE_VALUES[] = { START_MACHINE, ANDROID_TABLET,
			0x1b, 0x11, END_MACHINE };
	// Led命令
	public static int LED_MACHINE_VALUES[] = { START_MACHINE, ANDROID_TABLET,
			0x1f, 0x11, END_MACHINE };
	// Swing命令
	public static int SWING_MACHINE_VALUES[] = { START_MACHINE, ANDROID_TABLET,
			0x19, 0x11, END_MACHINE };
	
	public static final String MACHINE_RUN_STATUS="Machine_Run_Status";
	public static final String MACHINE_MASSAGE_STATUS="Machine_Run_Status";
	public static final String BUTTON_STATUS="Button";
	public static final String DIRECTION_STATUS="Direction";
	public static final String FOOT_STATUS="Foot";
	public static final String BACK_STATUS="Back";
	public static final String WALKING_POSITION_STATUS="Walking_Position";
	public static final String OXYGEN_STATUS="Oxygen";
	public static final String SWING_STATUS="Swing";
	public static final String PULSE_STATUS="Pulse";
	public static final String HEAT_STATUS="Heat";
	public static final String BLUETOOTH_STATUS="Bluetooth";
	public static final String ZERO_STATUS="Zero";
	public static final String LED_STATUS="Led";	
	public static final String INIT_POSITION_STATUS="Init_Position";
	public static final String PAUSE_STATUS="Pause";
	public static final String BUZZER_STATUS="Buzzer";
	public static final String BREATHE_STATUS="Breathe";
	
	
	private BluetoothService mBluetoothService = null;
	private Context context = null;

	/**
	 * 机器所有的状态值保存在machine_status中，每次（100ms）pad接收到机器穿过来的9个字节，都会更新这个hashmap，每个pad界面里功能按钮的状态都由machine_status决定
	 * 每一个key值代表功能按钮名称，如Oxygen，Swing，Pulse，Heat，Bluetooth，Zero 这些都定义成静态String
	 * 每一个value值代表当前功能按钮的状态，比如按摩椅运行状态(3bit)有8个状态，状态表示既是0,1...7 这些状态也都用静态int表示
	 *                              Pulse状态(1bit)有2个状态，状态表示既是0,1
	 */
	private HashMap<String, Integer> machine_status=new HashMap<String, Integer>(){
		/**
		 * 下面是状态的初始值,如
		 */
		{
			put("OXYGEN_STATUS", 0);
			put("SWING_STATUS", 0);
		}
	};
	// 提供私有的构造方法
	public BluetoothCommand(Context context, BluetoothService mBluetoothService) {
		this.context = context;
		this.mBluetoothService = mBluetoothService;
	}

	/**
	 * 对外提供的初始化方法
	 * 
	 * @return
	 */
	/*
	 * public static BluetoothCommand getInstance() { // 初始化自身对象 if
	 * (myBluetoothCommand == null) { myBluetoothCommand = new
	 * BluetoothCommand();
	 * 
	 * } return myBluetoothCommand; }
	 */

	/**
	 * 发送指令
	 * 
	 * @param value
	 */
	public void sendCommand(int[] values) {
		byte[] src = new byte[values.length];
		for (int i = 0; i < values.length; i++) {
			byte[] temp = new byte[4];
			temp[3] = (byte) ((values[i] >> 24) & 0xFF);
			temp[2] = (byte) ((values[i] >> 16) & 0xFF);
			temp[1] = (byte) ((values[i] >> 8) & 0xFF);
			temp[0] = (byte) (values[i] & 0xFF);
			src[i] = temp[0];
		}
		mBluetoothService.write(src);
	}
	/**
	 * 解析命令
	 * 解析9个字节，并且把每个字节的状态写到machine_status
	 * @return
	 */
	public boolean parseCommand(byte[] bytes) {
        //第1字节	
		//第2字节
		/**
		 * 字节1
		 */
		byte b1 = bytes[1];
		//取第6 5 4位的状态
		machine_status.put(MACHINE_RUN_STATUS, (b1&0x70)>>3);
		//取第3 2 1位的状态
		machine_status.put(MACHINE_MASSAGE_STATUS, (b1&0x0e)>>0);
		/**
		 * 字节2
		 */
		byte b2 =bytes[2];
		//取第6位的状态
		machine_status.put(BUTTON_STATUS,(b2&0x40)>>5);
		//取第5 4 3 2位的状态
		machine_status.put(WALKING_POSITION_STATUS,(b2&0x3c)>>1);
		//取第1 0位的状态
		machine_status.put(DIRECTION_STATUS,(b2&0x03)>>0);
		/**
		 * 字节3
		 */
		byte b3 = bytes[3];
		//取第5 4 3位的状态
		machine_status.put(FOOT_STATUS,(b3&0x38)>>2);
		//取第2 1 0位的状态
		machine_status.put(BACK_STATUS,(b3&0x07)>>2);
		/**
		 * 字节4
		 */
		byte b4 = bytes[4];
		//取第6位的状态
		machine_status.put(OXYGEN_STATUS,(b4&0x40)>>5);
		//取第5位的状态
		machine_status.put(SWING_STATUS,(b4&0x20)>>4);
		//取第4位的状态
		machine_status.put(LED_STATUS,(b4&0x10)>>3);
		//取第3位的状态
		machine_status.put(HEAT_STATUS,(b4&0x08)>>2);
		//取第2位的状态
		machine_status.put(BLUETOOTH_STATUS,(b4&0x04)>>1);
		//取第1 0位的状态
		machine_status.put(ZERO_STATUS,(b4&0x03)>>0);
		/**
		 * 字节5
		 */
		byte b5 = bytes[5];
		//取第6位的状态
		machine_status.put(PULSE_STATUS,(b5&0x40)>>5);
		/**
		 * 字节6
		 */
		byte b6 = bytes[6];
		//取第6位的状态
		machine_status.put(PULSE_STATUS,(b6&0x40)>>5);
		/**
		 * 字节7
		 */
		byte b7 = bytes[7];
		//取第6位的状态
		machine_status.put(INIT_POSITION_STATUS,(b7&0x40)>>5);
		//取第5位的状态
		machine_status.put(PAUSE_STATUS,(b7&0x20)>>4);
		//取第4 3位的状态
		machine_status.put(BUZZER_STATUS,(b7&0x18)>>2);
		//取第2 1 0位的状态
		machine_status.put(BREATHE_STATUS,(b7&0x07));
		
		return true;
	}

	/*	//第3字节
		//第4字节
		//第5字节
		//第6字节
		//第7个字节
		byte b = bytes[6];
		if((byte)(b&0x40)==(byte)0x40){
			*//**
			 * 这里将被改写成把状态位的值写到machine_status
			 *//*

		//取第六位的状态
		machine_status.put("", (b&0x40)>>5);
        //取第五位的状态
		machine_status.put("", (b&0x20)>>4);
        //取第三和四位的状态
		machine_status.put("", (b&0x18)>>2);


		//第8字节
		//第9字节
		 * }
*/	
	/**
	 * 把byte转为字符串的bit
	 */
	public byte[] getBooleanArray(byte b) {
		byte[] array = new byte[8];
		for (int i = 7; i >= 0; i--) {
			array[i] = (byte) (b & 1);
			b = (byte) (b >> 1);
		}

		return array;
	}

}
