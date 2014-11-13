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
	/*
	 * 字节1
	 */
	// 待机
	public static final byte WAIT_BYTE = 00000000;
	public static final byte WAIT_BALANCE_BYTE = (byte) 00000010;
	public static final byte WAIT_RELAX_BYTE_ = (byte) 00000100;
	public static final byte WAIT_PERFORMANCE_BYTE_ = (byte) 00000110;
	public static final byte WAIT_MYSESSION1_BYTE_ = (byte) 00001000;
	public static final byte WAIT_MYSESSION2_BYTE_ = (byte) 00001010;
	public static final byte WAIT_MYSESSION3_BYTE_ = (byte) 00001100;
	public static final byte WAIT_MYSESSION4_BYTE_ = (byte) 00001110;
	// 收藏状态
	public static final byte COLLECT_BYTE = (byte) 00010000;
	public static final byte COLLECT_BALANCE_BYTE = (byte) 00010010;
	public static final byte COLLECT_RELAX_BYTE = (byte) 00010100;
	public static final byte COLLECT_RPERFORMANCE_BYTE = (byte) 00010110;
	public static final byte COLLECT_MYSESSION1_BYTE = (byte) 00011000;
	public static final byte COLLECT_MYSESSION2_BYTE = (byte) 00011010;
	public static final byte COLLECT_MYSESSION3_BYTE = (byte) 00011100;
	public static final byte COLLECT_MYSESSION4_BYTE = (byte) 00011110;
	// 运行状态
	public static final byte RUN_BYTE = (byte) 00100000;
	public static final byte RUN_BALANCE_BYTE = (byte) 00100010;
	public static final byte RUN_RELAX_BYTE = (byte) 00100100;
	public static final byte RUN_PERFORMANCE_BYTE = (byte) 00100110;
	public static final byte RUN_MYSESSION1_BYTE = (byte) 00101000;
	public static final byte RUN_MYSESSION2_BYTE = (byte) 00101010;
	public static final byte RUN_MYSESSION3_BYTE = (byte) 00101100;
	public static final byte RUN_MYSESSION4_BYTE = (byte) 00101110;
	// 暂停状态
	public static final byte PAUSE_BYTE = (byte) 00110000;
	public static final byte PAUSE_BALANCE_BYTE = (byte) 00110010;
	public static final byte PAUSE_RELAX_BYTE = (byte) 00110100;
	public static final byte PAUSE_PERFORMANCE_BYTE = (byte) 00110110;
	public static final byte PAUSE_MYSESSION1_BYTE = (byte) 00111000;
	public static final byte PAUSE_MYSESSION2_BYTE = (byte) 00111010;
	public static final byte PAUSE_MYSESSION3_BYTE = (byte) 00111100;
	public static final byte PAUSE_MYSESSION4_BYTE = (byte) 00111110;
	// 04 05 06 07保留
	/*
	 * 字节2
	 */
	// 无按键回应
	// 上下行走方向、及位置指示 00 0D 0E 0F 保留
	// public static final byte WALK_BYTE2=(byte) 00000000;
	// 位置1
	public static final byte WALK_PLACE1_STOP_BYTE2 = (byte) 00000100;
	public static final byte WALK_PLACE1_UP_BYTE2 = (byte) 00000101;
	public static final byte WALK_PLACE1_DOWN_BYTE2 = (byte) 00000110;
	public static final byte WALK_PLACE1_retain_BYTE2 = (byte) 00000111;
	// 位置2
	public static final byte WALK_PLACE2_STOP_BYTE2 = (byte) 00001000;
	public static final byte WALK_PLACE2_UP_BYTE2 = (byte) 00001001;
	public static final byte WALK_PLACE2_DOWN_BYTE2 = (byte) 00001010;
	public static final byte WALK_PLACE2_retain_BYTE2 = (byte) 00001011;
	// 位置3
	public static final byte WALK_PLACE3_STOP_BYTE2 = (byte) 00001100;
	public static final byte WALK_PLACE3_UP_BYTE2 = (byte) 00001101;
	public static final byte WALK_PLACE3_DOWN_BYTE2 = (byte) 00001110;
	public static final byte WALK_PLACE3_retain_BYTE2 = (byte) 00001111;
	// 位置4
	public static final byte WALK_PLACE4_STOP_BYTE2 = (byte) 00010000;
	public static final byte WALK_PLACE4_UP_BYTE2 = (byte) 000100101;
	public static final byte WALK_PLACE4_DOWN_BYTE2 = (byte) 000100110;
	public static final byte WALK_PLACE4_retain_BYTE2 = (byte) 000100111;
	// 位置5
	public static final byte WALK_PLACE5_STOP_BYTE2 = (byte) 00010100;
	public static final byte WALK_PLACE5_UP_BYTE2 = (byte) 00010101;
	public static final byte WALK_PLACE5_DOWN_BYTE2 = (byte) 00010110;
	public static final byte WALK_PLACE5_retain_BYTE2 = (byte) 00010111;
	// 位置6
	public static final byte WALK_PLACE6_STOP_BYTE2 = (byte) 00011000;
	public static final byte WALK_PLACE6_UP_BYTE2 = (byte) 00011001;
	public static final byte WALK_PLACE6_DOWN_BYTE2 = (byte) 00011010;
	public static final byte WALK_PLACE6_retain_BYTE2 = (byte) 00011011;
	// 位置7
	public static final byte WALK_PLACE7_STOP_BYTE2 = (byte) 00011100;
	public static final byte WALK_PLACE7_UP_BYTE2 = (byte) 00011101;
	public static final byte WALK_PLACE7_DOWN_BYTE2 = (byte) 00011110;
	public static final byte WALK_PLACE7_retain_BYTE2 = (byte) 00011111;
	// 位置8
	public static final byte WALK_PLACE8_STOP_BYTE2 = (byte) 00100000;
	public static final byte WALK_PLACE8_UP_BYTE2 = (byte) 000100001;
	public static final byte WALK_PLACE8_DOWN_BYTE2 = (byte) 00100010;
	public static final byte WALK_PLACE8_retain_BYTE2 = (byte) 00100011;
	// 位置9
	public static final byte WALK_PLACE9_STOP_BYTE2 = (byte) 00100100;
	public static final byte WALK_PLACE9_UP_BYTE2 = (byte) 000100101;
	public static final byte WALK_PLACE9_DOWN_BYTE2 = (byte) 00100110;
	public static final byte WALK_PLACE9_retain_BYTE2 = (byte) 00100111;
	// 位置10
	public static final byte WALK_PLACE10_STOP_BYTE2 = (byte) 00101000;
	public static final byte WALK_PLACE10_UP_BYTE2 = (byte) 000101001;
	public static final byte WALK_PLACE10_DOWN_BYTE2 = (byte) 00101010;
	public static final byte WALK_PLACE10_retain_BYTE2 = (byte) 00101011;
	// 位置11
	public static final byte WALK_PLACE11_STOP_BYTE2 = (byte) 00101100;
	public static final byte WALK_PLACE11_UP_BYTE2 = (byte) 000101101;
	public static final byte WALK_PLACE11_DOWN_BYTE2 = (byte) 00101110;
	public static final byte WALK_PLACE11_retain_BYTE2 = (byte) 00101111;
	// 位置12
	public static final byte WALK_PLACE12_STOP_BYTE2 = (byte) 00110000;
	public static final byte WALK_PLACE12_UP_BYTE2 = (byte) 00110001;
	public static final byte WALK_PLACE12_DOWN_BYTE2 = (byte) 00110010;
	public static final byte WALK_PLACE12_retain_BYTE2 = (byte) 00110011;
	// 有按键回应

	// 位置1
	public static final byte KEY_WALK_PLACE1_STOP_BYTE2 = (byte) 01000100;
	public static final byte KEY_WALK_PLACE1_UP_BYTE2 = (byte) 01000101;
	public static final byte KEY_WALK_PLACE1_DOWN_BYTE2 = (byte) 01000110;
	public static final byte KEY_WALK_PLACE1_retain_BYTE2 = (byte) 01000111;
	// 位置2
	public static final byte KEY_WALK_PLACE2_STOP_BYTE2 = (byte) 01001000;
	public static final byte KEY_WALK_PLACE2_UP_BYTE2 = (byte) 01001001;
	public static final byte KEY_WALK_PLACE2_DOWN_BYTE2 = (byte) 01001010;
	public static final byte KEY_WALK_PLACE2_retain_BYTE2 = (byte) 01001011;
	// 位置3
	public static final byte KEY_WALK_PLACE3_STOP_BYTE2 = (byte) 01001100;
	public static final byte KEY_WALK_PLACE3_UP_BYTE2 = (byte) 01001101;
	public static final byte KEY_WALK_PLACE3_DOWN_BYTE2 = (byte) 01001110;
	public static final byte KEY_WALK_PLACE3_retain_BYTE2 = (byte) 01001111;
	// 位置4
	public static final byte KEY_WALK_PLACE4_STOP_BYTE2 = (byte) 01010000;
	public static final byte KEY_WALK_PLACE4_UP_BYTE2 = (byte) 010100101;
	public static final byte KEY_WALK_PLACE4_DOWN_BYTE2 = (byte) 010100110;
	public static final byte KEY_WALK_PLACE4_retain_BYTE2 = (byte) 010100111;
	// 位置5
	public static final byte KEY_WALK_PLACE5_STOP_BYTE2 = (byte) 01010100;
	public static final byte KEY_WALK_PLACE5_UP_BYTE2 = (byte) 01010101;
	public static final byte KEY_WALK_PLACE5_DOWN_BYTE2 = (byte) 01010110;
	public static final byte KEY_WALK_PLACE5_retain_BYTE2 = (byte) 01010111;
	// 位置6
	public static final byte KEY_WALK_PLACE6_STOP_BYTE2 = (byte) 01011000;
	public static final byte KEY_WALK_PLACE6_UP_BYTE2 = (byte) 01011001;
	public static final byte KEY_WALK_PLACE6_DOWN_BYTE2 = (byte) 01011010;
	public static final byte KEY_WALK_PLACE6_retain_BYTE2 = (byte) 01011011;
	// 位置7
	public static final byte KEY_WALK_PLACE7_STOP_BYTE2 = (byte) 01011100;
	public static final byte KEY_WALK_PLACE7_UP_BYTE2 = (byte) 01011101;
	public static final byte KEY_WALK_PLACE7_DOWN_BYTE2 = (byte) 01011110;
	public static final byte KEY_WALK_PLACE7_retain_BYTE2 = (byte) 01011111;
	// 位置8
	public static final byte KEY_WALK_PLACE8_STOP_BYTE2 = (byte) 01100000;
	public static final byte KEY_WALK_PLACE8_UP_BYTE2 = (byte) 01100001;
	public static final byte KEY_WALK_PLACE8_DOWN_BYTE2 = (byte) 01100010;
	public static final byte KEY_WALK_PLACE8_retain_BYTE2 = (byte) 01100011;
	// 位置9
	public static final byte KEY_WALK_PLACE9_STOP_BYTE2 = (byte) 01100100;
	public static final byte KEY_WALK_PLACE9_UP_BYTE2 = (byte) 01100101;
	public static final byte KEY_WALK_PLACE9_DOWN_BYTE2 = (byte) 01100110;
	public static final byte KEY_WALK_PLACE9_retain_BYTE2 = (byte) 00100111;
	// 位置10
	public static final byte KEY_WALK_PLACE10_STOP_BYTE2 = (byte) 01101000;
	public static final byte KEY_WALK_PLACE10_UP_BYTE2 = (byte) 01101001;
	public static final byte KEY_WALK_PLACE10_DOWN_BYTE2 = (byte) 01101010;
	public static final byte KEY_WALK_PLACE10_retain_BYTE2 = (byte) 01101011;
	// 位置11
	public static final byte KEY_WALK_PLACE11_STOP_BYTE2 = (byte) 01101100;
	public static final byte KEY_WALK_PLACE11_UP_BYTE2 = (byte) 01101101;
	public static final byte KEY_WALK_PLACE11_DOWN_BYTE2 = (byte) 01101110;
	public static final byte KEY_WALK_PLACE11_retain_BYTE2 = (byte) 01101111;
	// 位置12
	public static final byte KEY_WALK_PLACE12_STOP_BYTE2 = (byte) 01110000;
	public static final byte KEY_WALK_PLACE12_UP_BYTE2 = (byte) 01110001;
	public static final byte KEY_WALK_PLACE12_DOWN_BYTE2 = (byte) 01110010;
	public static final byte KEY_WALK_PLACE12_retain_BYTE2 = (byte) 01110011;
	// ////////d0 de df保留
	// 字节3
	// 靠背，小腿电动缸指示
	// 小腿电动缸停止 靠背电动缸状态 保留04 05 06 07
	public static final byte FOOT_STOP_BACK_STOP_BYTE3 = (byte) 00000000;
	public static final byte FOOT_STOP_BACK_UP_BYTE3 = (byte) 00000001;
	public static final byte FOOT_STOP_BACK_DOWN_BYTE3 = (byte) 00000010;
	public static final byte FOOT_STOP_BACK_REACH_BYTE3 = (byte) 00000011;
	// 小腿电动缸升 靠背电动缸状态
	public static final byte FOOT_UP_BACK_STOP_BYTE3 = (byte) 00001000;
	public static final byte FOOT_UP_BACK_UP_BYTE3 = (byte) 00001001;
	public static final byte FOOT_UP_BACK_DOWN_BYTE3 = (byte) 00001010;
	public static final byte FOOT_UP_BACK_REACH_BYTE3 = (byte) 00001011;
	// 小腿电动缸降 靠背电动缸状态
	public static final byte FOOT_DOWN_BACK_STOP_BYTE3 = (byte) 00010000;
	public static final byte FOOT_DOWN_BACK_UP_BYTE3 = (byte) 00010001;
	public static final byte FOOT_DOWN_BACK_DOWN_BYTE3 = (byte) 00010010;
	public static final byte FOOT_DOWN_BACK_REACH_BYTE3 = (byte) 00010011;
	// 小腿电动缸到限位 靠背电动缸状态
	public static final byte FOOT_REACH_BACK_STOP_BYTE3 = (byte) 00011000;
	public static final byte FOOT_REACH_BACK_UP_BYTE3 = (byte) 00011001;
	public static final byte FOOT_REACH_BACK_DOWN_BYTE3 = (byte) 00011010;
	public static final byte FOOT_REACH_BACK_REACH_BYTE3 = (byte) 00011011;

	// 字节4
	// Oxygen，Swing，Pulse，Heat，Bluetooth，Zero开启，关闭指示
	// 其他关 Zero开启
	public static final byte O_S_P_H_B_Z_BYTE4 = (byte) 00000000;
	public static final byte O_S_P_H_B_ZOPEN1_BYTE4 = (byte) 00000001;
	public static final byte O_S_P_H_B_ZOPEN2_BYTE4 = (byte) 00000010;
	public static final byte O_S_P_H_B_ZOPEN3_BYTE4 = (byte) 00000011;
	// Bluetooth开启
	public static final byte O_S_P_H_BOPEN_Z_BYTE4 = (byte) 00000100;
	public static final byte O_S_P_H_BOPEN_ZOPEN1_BYTE4 = (byte) 00000101;
	public static final byte O_S_P_H_BOPEN_ZOPEN2_BYTE4 = (byte) 00000110;
	public static final byte O_S_P_H_BOPEN_ZOPEN3_BYTE4 = (byte) 00000111;
	// Heat开启
	public static final byte O_S_P_HOPEN_B_Z_BYTE4 = (byte) 00001000;
	public static final byte O_S_P_HOPEN_B_ZOPEN1_BYTE4 = (byte) 00001001;
	public static final byte O_S_P_HOPEN_B_ZOPEN2_BYTE4 = (byte) 00001010;
	public static final byte O_S_P_HOPEN_B_ZOPEN3_BYTE4 = (byte) 00001011;
	public static final byte O_S_P_HOPEN_BOPEN_Z_BYTE4 = (byte) 00000100;
	public static final byte O_S_P_HOPEN_BOPEN_ZOPEN1_BYTE4 = (byte) 00001101;
	public static final byte O_S_P_HOPEN_BOPEN_ZOPEN2_BYTE4 = (byte) 00001110;
	public static final byte O_S_P_HOPEN_BOPEN_ZOPEN3_BYTE4 = (byte) 00001111;
	// PULSE

	// ///
	public static final byte OOPEN_S_P_H_B_Z_BYTE4 = (byte) 01000000;
	// //
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
			put("Oxygen", 1);
			put("Swing", 0);
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
<<<<<<< HEAD

	/*
	 * public void getCommand(byte[] bytes) {
	 * 
	 * switch (bytes[1]) { case WAIT_BYTE: break;
	 * 
	 * default: break; } switch (bytes[2]) { case OOPEN_S_P_H_B_Z_BYTE4:
	 * 
	 * break;
	 * 
	 * default: break; } switch (bytes[3]) { case OOPEN_S_P_H_B_Z_BYTE4:
	 * 
	 * break;
	 * 
	 * default: break; } switch (bytes[4]) { case OOPEN_S_P_H_B_Z_BYTE4:
	 * MyPreference.getInstance(context).writeString(MyPreference.OXTGEN,
	 * MyPreference.OXTGEN_OPEN); break;
	 * 
	 * default: break; }
	 * 
	 * }
	 */
	byte bb=(byte)01000000;
	public boolean getCommand(byte[] bytes) {

		// 第7个字节
		byte b = bytes[7];
		if((byte)(b&0x40)!=(byte)0x40)
		
=======
	public boolean parseCommand(byte[] bytes) {
        //第1字节
		//第2字节
		//第3字节
		//第4字节
		//第5字节
		//第6字节
		//第7个字节
		byte b = bytes[6];
		if((byte)(b&0x40)==(byte)0x40){
			/**
			 * 这里将被改写成把状态位的值写到machine_status
			 */
// c88dfc18ebfa4a7581a765228c881c5917f22ed0
			return true;
		}
		else
			return false;

		//第8字节
		//第9字节
	}

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
