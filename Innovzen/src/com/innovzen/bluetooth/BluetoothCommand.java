package com.innovzen.bluetooth;

import java.util.Arrays;

import com.innovzen.utils.MyPreference;

import android.content.Context;

/**
 * ��������
 * 
 * @author desmond.duan
 * 
 */
public class BluetoothCommand {

	/* BluetoothService */

	// ��������
	public static final int START_MACHINE = 0xF0;
	// ��׿ƽ����ʾ�ֿ���
	public static final int ANDROID_TABLET = 0x83;
	// �����ֽ�
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
	// �����ػ�����
	public static int START_MACHINE_VALUES[] = { START_MACHINE, ANDROID_TABLET,
			POWER_ON_OFF, 0x11, END_MACHINE };
	// ��ͣ
	public static int PAUSE_MACHINE_VALUES[] = { START_MACHINE, ANDROID_TABLET,
			PAUSE, 0x11, END_MACHINE };
	// Blance����
	public static int BLANCE_MACHINE_VALUES[] = { START_MACHINE,
			ANDROID_TABLET, BALANCE, 0x11, END_MACHINE };
	// Relax����
	public static int RELAX_MACHINE_VALUES[] = { START_MACHINE, ANDROID_TABLET,
			RELAX, 0x11, END_MACHINE };
	// Performance����
	public static int PERFORMANCE_MACHINE_VALUES[] = { START_MACHINE,
			ANDROID_TABLET, PERFORMANCE, 0x11, END_MACHINE };
	// breathe +����
	public static int BREATHE_UP_MACHINE_VALUES[] = { START_MACHINE,
			ANDROID_TABLET, BREATH_UP, 0x11, END_MACHINE };
	// breathe -����
	public static int BREATHE_DOWN_MACHINE_VALUES[] = { START_MACHINE,
			ANDROID_TABLET, BREATH_DOWM, 0x11, END_MACHINE };
	// backrest up����
	public static int BACK_REST_UP_MACHINE_VALUES[] = { START_MACHINE,
			ANDROID_TABLET, BACKREST_ADJUST_UP, 0x11, END_MACHINE };
	// backrest down����
	public static int BACK_REST_DOWN_MACHINE_VALUES[] = { START_MACHINE,
			ANDROID_TABLET, BACKREST_ADJUST_DOWN, 0x11, END_MACHINE };
	// backrest up��ͣ����
	public static int BACK_REST_UP_STOP_MACHINE_VALUES[] = { START_MACHINE,
			ANDROID_TABLET, BACKREST_ADJUST_UP_STOP, 0x11, END_MACHINE };
	// backrest down��ͣ����
	public static int BACK_REST_DOWN_STOP_MACHINE_VALUES[] = { START_MACHINE,
			ANDROID_TABLET, BACKREST_ADJUST_DOWN_STOP, 0x11, END_MACHINE };
	// foot up����
	public static int FOOT_UP_MACHINE_VALUES[] = { START_MACHINE,
			ANDROID_TABLET, FOOT_ADJUST_UP, 0x11, END_MACHINE };
	// foot down ����
	public static int FOOT_DOWN_MACHINE_VALUES[] = { START_MACHINE,
			ANDROID_TABLET, FOOT_ADJUST_DOWN, 0x11, END_MACHINE };
	// foot up stop����
	public static int FOOT_UP_STOP_MACHINE_VALUES[] = { START_MACHINE,
			ANDROID_TABLET, FOOT_ADJUST_UP_STOP, 0x11, END_MACHINE };
	// foot down stop ����
	public static int FOOT_DOWN_STOP_MACHINE_VALUES[] = { START_MACHINE,
			ANDROID_TABLET, FOOT_ADJUST_DOWN_STOP, 0x11, END_MACHINE };
	// Zero gravity����
	public static int ZERO_GRAVITY_MACHINE_VALUES[] = { START_MACHINE,
			ANDROID_TABLET, ZERO_GRAVITY, 0x11, END_MACHINE };
	// Oxygen����
	public static int OXYGEN_MACHINE_VALUES[] = { START_MACHINE,
			ANDROID_TABLET, 0x1a, 0x11, END_MACHINE };
	// Heat����
	public static int HEAT_MACHINE_VALUES[] = { START_MACHINE, ANDROID_TABLET,
			0x1b, 0x11, END_MACHINE };
	// Led����
	public static int LED_MACHINE_VALUES[] = { START_MACHINE, ANDROID_TABLET,
			0x1f, 0x11, END_MACHINE };
	// Swing����
	public static int SWING_MACHINE_VALUES[] = { START_MACHINE, ANDROID_TABLET,
			0x19, 0x11, END_MACHINE };
	/*
	 * �ֽ�1
	 */
	// ����
	public static final byte WAIT_BYTE = 00000000;
	public static final byte WAIT_BALANCE_BYTE = (byte) 00000010;
	public static final byte WAIT_RELAX_BYTE_ = (byte) 00000100;
	public static final byte WAIT_PERFORMANCE_BYTE_ = (byte) 00000110;
	public static final byte WAIT_MYSESSION1_BYTE_ = (byte) 00001000;
	public static final byte WAIT_MYSESSION2_BYTE_ = (byte) 00001010;
	public static final byte WAIT_MYSESSION3_BYTE_ = (byte) 00001100;
	public static final byte WAIT_MYSESSION4_BYTE_ = (byte) 00001110;
	// �ղ�״̬
	public static final byte COLLECT_BYTE = (byte) 00010000;
	public static final byte COLLECT_BALANCE_BYTE = (byte) 00010010;
	public static final byte COLLECT_RELAX_BYTE = (byte) 00010100;
	public static final byte COLLECT_RPERFORMANCE_BYTE = (byte) 00010110;
	public static final byte COLLECT_MYSESSION1_BYTE = (byte) 00011000;
	public static final byte COLLECT_MYSESSION2_BYTE = (byte) 00011010;
	public static final byte COLLECT_MYSESSION3_BYTE = (byte) 00011100;
	public static final byte COLLECT_MYSESSION4_BYTE = (byte) 00011110;
	// ����״̬
	public static final byte RUN_BYTE = (byte) 00100000;
	public static final byte RUN_BALANCE_BYTE = (byte) 00100010;
	public static final byte RUN_RELAX_BYTE = (byte) 00100100;
	public static final byte RUN_PERFORMANCE_BYTE = (byte) 00100110;
	public static final byte RUN_MYSESSION1_BYTE = (byte) 00101000;
	public static final byte RUN_MYSESSION2_BYTE = (byte) 00101010;
	public static final byte RUN_MYSESSION3_BYTE = (byte) 00101100;
	public static final byte RUN_MYSESSION4_BYTE = (byte) 00101110;
	// ��ͣ״̬
	public static final byte PAUSE_BYTE = (byte) 00110000;
	public static final byte PAUSE_BALANCE_BYTE = (byte) 00110010;
	public static final byte PAUSE_RELAX_BYTE = (byte) 00110100;
	public static final byte PAUSE_PERFORMANCE_BYTE = (byte) 00110110;
	public static final byte PAUSE_MYSESSION1_BYTE = (byte) 00111000;
	public static final byte PAUSE_MYSESSION2_BYTE = (byte) 00111010;
	public static final byte PAUSE_MYSESSION3_BYTE = (byte) 00111100;
	public static final byte PAUSE_MYSESSION4_BYTE = (byte) 00111110;
	// 04 05 06 07����
	/*
	 * �ֽ�2
	 */
	// �ް�����Ӧ
	// �������߷��򡢼�λ��ָʾ 00 0D 0E 0F ����
	// public static final byte WALK_BYTE2=(byte) 00000000;
	// λ��1
	public static final byte WALK_PLACE1_STOP_BYTE2 = (byte) 00000100;
	public static final byte WALK_PLACE1_UP_BYTE2 = (byte) 00000101;
	public static final byte WALK_PLACE1_DOWN_BYTE2 = (byte) 00000110;
	public static final byte WALK_PLACE1_retain_BYTE2 = (byte) 00000111;
	// λ��2
	public static final byte WALK_PLACE2_STOP_BYTE2 = (byte) 00001000;
	public static final byte WALK_PLACE2_UP_BYTE2 = (byte) 00001001;
	public static final byte WALK_PLACE2_DOWN_BYTE2 = (byte) 00001010;
	public static final byte WALK_PLACE2_retain_BYTE2 = (byte) 00001011;
	// λ��3
	public static final byte WALK_PLACE3_STOP_BYTE2 = (byte) 00001100;
	public static final byte WALK_PLACE3_UP_BYTE2 = (byte) 00001101;
	public static final byte WALK_PLACE3_DOWN_BYTE2 = (byte) 00001110;
	public static final byte WALK_PLACE3_retain_BYTE2 = (byte) 00001111;
	// λ��4
	public static final byte WALK_PLACE4_STOP_BYTE2 = (byte) 00010000;
	public static final byte WALK_PLACE4_UP_BYTE2 = (byte) 000100101;
	public static final byte WALK_PLACE4_DOWN_BYTE2 = (byte) 000100110;
	public static final byte WALK_PLACE4_retain_BYTE2 = (byte) 000100111;
	// λ��5
	public static final byte WALK_PLACE5_STOP_BYTE2 = (byte) 00010100;
	public static final byte WALK_PLACE5_UP_BYTE2 = (byte) 00010101;
	public static final byte WALK_PLACE5_DOWN_BYTE2 = (byte) 00010110;
	public static final byte WALK_PLACE5_retain_BYTE2 = (byte) 00010111;
	// λ��6
	public static final byte WALK_PLACE6_STOP_BYTE2 = (byte) 00011000;
	public static final byte WALK_PLACE6_UP_BYTE2 = (byte) 00011001;
	public static final byte WALK_PLACE6_DOWN_BYTE2 = (byte) 00011010;
	public static final byte WALK_PLACE6_retain_BYTE2 = (byte) 00011011;
	// λ��7
	public static final byte WALK_PLACE7_STOP_BYTE2 = (byte) 00011100;
	public static final byte WALK_PLACE7_UP_BYTE2 = (byte) 00011101;
	public static final byte WALK_PLACE7_DOWN_BYTE2 = (byte) 00011110;
	public static final byte WALK_PLACE7_retain_BYTE2 = (byte) 00011111;
	// λ��8
	public static final byte WALK_PLACE8_STOP_BYTE2 = (byte) 00100000;
	public static final byte WALK_PLACE8_UP_BYTE2 = (byte) 000100001;
	public static final byte WALK_PLACE8_DOWN_BYTE2 = (byte) 00100010;
	public static final byte WALK_PLACE8_retain_BYTE2 = (byte) 00100011;
	// λ��9
	public static final byte WALK_PLACE9_STOP_BYTE2 = (byte) 00100100;
	public static final byte WALK_PLACE9_UP_BYTE2 = (byte) 000100101;
	public static final byte WALK_PLACE9_DOWN_BYTE2 = (byte) 00100110;
	public static final byte WALK_PLACE9_retain_BYTE2 = (byte) 00100111;
	// λ��10
	public static final byte WALK_PLACE10_STOP_BYTE2 = (byte) 00101000;
	public static final byte WALK_PLACE10_UP_BYTE2 = (byte) 000101001;
	public static final byte WALK_PLACE10_DOWN_BYTE2 = (byte) 00101010;
	public static final byte WALK_PLACE10_retain_BYTE2 = (byte) 00101011;
	// λ��11
	public static final byte WALK_PLACE11_STOP_BYTE2 = (byte) 00101100;
	public static final byte WALK_PLACE11_UP_BYTE2 = (byte) 000101101;
	public static final byte WALK_PLACE11_DOWN_BYTE2 = (byte) 00101110;
	public static final byte WALK_PLACE11_retain_BYTE2 = (byte) 00101111;
	// λ��12
	public static final byte WALK_PLACE12_STOP_BYTE2 = (byte) 00110000;
	public static final byte WALK_PLACE12_UP_BYTE2 = (byte) 00110001;
	public static final byte WALK_PLACE12_DOWN_BYTE2 = (byte) 00110010;
	public static final byte WALK_PLACE12_retain_BYTE2 = (byte) 00110011;
	// �а�����Ӧ

	// λ��1
	public static final byte KEY_WALK_PLACE1_STOP_BYTE2 = (byte) 01000100;
	public static final byte KEY_WALK_PLACE1_UP_BYTE2 = (byte) 01000101;
	public static final byte KEY_WALK_PLACE1_DOWN_BYTE2 = (byte) 01000110;
	public static final byte KEY_WALK_PLACE1_retain_BYTE2 = (byte) 01000111;
	// λ��2
	public static final byte KEY_WALK_PLACE2_STOP_BYTE2 = (byte) 01001000;
	public static final byte KEY_WALK_PLACE2_UP_BYTE2 = (byte) 01001001;
	public static final byte KEY_WALK_PLACE2_DOWN_BYTE2 = (byte) 01001010;
	public static final byte KEY_WALK_PLACE2_retain_BYTE2 = (byte) 01001011;
	// λ��3
	public static final byte KEY_WALK_PLACE3_STOP_BYTE2 = (byte) 01001100;
	public static final byte KEY_WALK_PLACE3_UP_BYTE2 = (byte) 01001101;
	public static final byte KEY_WALK_PLACE3_DOWN_BYTE2 = (byte) 01001110;
	public static final byte KEY_WALK_PLACE3_retain_BYTE2 = (byte) 01001111;
	// λ��4
	public static final byte KEY_WALK_PLACE4_STOP_BYTE2 = (byte) 01010000;
	public static final byte KEY_WALK_PLACE4_UP_BYTE2 = (byte) 010100101;
	public static final byte KEY_WALK_PLACE4_DOWN_BYTE2 = (byte) 010100110;
	public static final byte KEY_WALK_PLACE4_retain_BYTE2 = (byte) 010100111;
	// λ��5
	public static final byte KEY_WALK_PLACE5_STOP_BYTE2 = (byte) 01010100;
	public static final byte KEY_WALK_PLACE5_UP_BYTE2 = (byte) 01010101;
	public static final byte KEY_WALK_PLACE5_DOWN_BYTE2 = (byte) 01010110;
	public static final byte KEY_WALK_PLACE5_retain_BYTE2 = (byte) 01010111;
	// λ��6
	public static final byte KEY_WALK_PLACE6_STOP_BYTE2 = (byte) 01011000;
	public static final byte KEY_WALK_PLACE6_UP_BYTE2 = (byte) 01011001;
	public static final byte KEY_WALK_PLACE6_DOWN_BYTE2 = (byte) 01011010;
	public static final byte KEY_WALK_PLACE6_retain_BYTE2 = (byte) 01011011;
	// λ��7
	public static final byte KEY_WALK_PLACE7_STOP_BYTE2 = (byte) 01011100;
	public static final byte KEY_WALK_PLACE7_UP_BYTE2 = (byte) 01011101;
	public static final byte KEY_WALK_PLACE7_DOWN_BYTE2 = (byte) 01011110;
	public static final byte KEY_WALK_PLACE7_retain_BYTE2 = (byte) 01011111;
	// λ��8
	public static final byte KEY_WALK_PLACE8_STOP_BYTE2 = (byte) 01100000;
	public static final byte KEY_WALK_PLACE8_UP_BYTE2 = (byte) 01100001;
	public static final byte KEY_WALK_PLACE8_DOWN_BYTE2 = (byte) 01100010;
	public static final byte KEY_WALK_PLACE8_retain_BYTE2 = (byte) 01100011;
	// λ��9
	public static final byte KEY_WALK_PLACE9_STOP_BYTE2 = (byte) 01100100;
	public static final byte KEY_WALK_PLACE9_UP_BYTE2 = (byte) 01100101;
	public static final byte KEY_WALK_PLACE9_DOWN_BYTE2 = (byte) 01100110;
	public static final byte KEY_WALK_PLACE9_retain_BYTE2 = (byte) 00100111;
	// λ��10
	public static final byte KEY_WALK_PLACE10_STOP_BYTE2 = (byte) 01101000;
	public static final byte KEY_WALK_PLACE10_UP_BYTE2 = (byte) 01101001;
	public static final byte KEY_WALK_PLACE10_DOWN_BYTE2 = (byte) 01101010;
	public static final byte KEY_WALK_PLACE10_retain_BYTE2 = (byte) 01101011;
	// λ��11
	public static final byte KEY_WALK_PLACE11_STOP_BYTE2 = (byte) 01101100;
	public static final byte KEY_WALK_PLACE11_UP_BYTE2 = (byte) 01101101;
	public static final byte KEY_WALK_PLACE11_DOWN_BYTE2 = (byte) 01101110;
	public static final byte KEY_WALK_PLACE11_retain_BYTE2 = (byte) 01101111;
	// λ��12
	public static final byte KEY_WALK_PLACE12_STOP_BYTE2 = (byte) 01110000;
	public static final byte KEY_WALK_PLACE12_UP_BYTE2 = (byte) 01110001;
	public static final byte KEY_WALK_PLACE12_DOWN_BYTE2 = (byte) 01110010;
	public static final byte KEY_WALK_PLACE12_retain_BYTE2 = (byte) 01110011;
	// ////////d0 de df����
	// �ֽ�3
	// ������С�ȵ綯��ָʾ
	// С�ȵ綯��ֹͣ �����綯��״̬ ����04 05 06 07
	public static final byte FOOT_STOP_BACK_STOP_BYTE3 = (byte) 00000000;
	public static final byte FOOT_STOP_BACK_UP_BYTE3 = (byte) 00000001;
	public static final byte FOOT_STOP_BACK_DOWN_BYTE3 = (byte) 00000010;
	public static final byte FOOT_STOP_BACK_REACH_BYTE3 = (byte) 00000011;
	// С�ȵ綯���� �����綯��״̬
	public static final byte FOOT_UP_BACK_STOP_BYTE3 = (byte) 00001000;
	public static final byte FOOT_UP_BACK_UP_BYTE3 = (byte) 00001001;
	public static final byte FOOT_UP_BACK_DOWN_BYTE3 = (byte) 00001010;
	public static final byte FOOT_UP_BACK_REACH_BYTE3 = (byte) 00001011;
	// С�ȵ綯�׽� �����綯��״̬
	public static final byte FOOT_DOWN_BACK_STOP_BYTE3 = (byte) 00010000;
	public static final byte FOOT_DOWN_BACK_UP_BYTE3 = (byte) 00010001;
	public static final byte FOOT_DOWN_BACK_DOWN_BYTE3 = (byte) 00010010;
	public static final byte FOOT_DOWN_BACK_REACH_BYTE3 = (byte) 00010011;
	// С�ȵ綯�׵���λ �����綯��״̬
	public static final byte FOOT_REACH_BACK_STOP_BYTE3 = (byte) 00011000;
	public static final byte FOOT_REACH_BACK_UP_BYTE3 = (byte) 00011001;
	public static final byte FOOT_REACH_BACK_DOWN_BYTE3 = (byte) 00011010;
	public static final byte FOOT_REACH_BACK_REACH_BYTE3 = (byte) 00011011;

	// �ֽ�4
	// Oxygen��Swing��Pulse��Heat��Bluetooth��Zero�������ر�ָʾ
	// ������ Zero����
	public static final byte O_S_P_H_B_Z_BYTE4 = (byte) 00000000;
	public static final byte O_S_P_H_B_ZOPEN1_BYTE4 = (byte) 00000001;
	public static final byte O_S_P_H_B_ZOPEN2_BYTE4 = (byte) 00000010;
	public static final byte O_S_P_H_B_ZOPEN3_BYTE4 = (byte) 00000011;
	// Bluetooth����
	public static final byte O_S_P_H_BOPEN_Z_BYTE4 = (byte) 00000100;
	public static final byte O_S_P_H_BOPEN_ZOPEN1_BYTE4 = (byte) 00000101;
	public static final byte O_S_P_H_BOPEN_ZOPEN2_BYTE4 = (byte) 00000110;
	public static final byte O_S_P_H_BOPEN_ZOPEN3_BYTE4 = (byte) 00000111;
	// Heat����
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

	// �ṩ˽�еĹ��췽��

	public BluetoothCommand(Context context, BluetoothService mBluetoothService) {
		this.context = context;
		this.mBluetoothService = mBluetoothService;
	}

	/**
	 * �����ṩ�ĳ�ʼ������
	 * 
	 * @return
	 */
	/*
	 * public static BluetoothCommand getInstance() { // ��ʼ��������� if
	 * (myBluetoothCommand == null) { myBluetoothCommand = new
	 * BluetoothCommand();
	 * 
	 * } return myBluetoothCommand; }
	 */

	/**
	 * ����ָ��
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
	 * ��������
	 * 
	 * @return
	 */

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
	public boolean getCommand(byte[] bytes) {

		// ���ĸ��ֽ�
		byte[] array = new byte[8];
		for (int i = 7; i >= 0; i--) {
			array[i] = (byte) (bytes[7] & 1);
			bytes[4] = (byte) (bytes[7] >> 1);
		}
		if (array[1] == 1) {
			return true;
		}
		return false;
		/* else {
			MyPreference.getInstance(context).writeString(MyPreference.OXTGEN,
					MyPreference.OXTGEN_CLOSE);
		}
		if (array[2] == 1) {
			MyPreference.getInstance(context).writeString(MyPreference.SWING,
					MyPreference.SWING_OPEN);
		} else {
			MyPreference.getInstance(context).writeString(MyPreference.SWING,
					MyPreference.SWING_CLOSE);
		}
		if (array[3] == 1) {
			MyPreference.getInstance(context).writeString(MyPreference.LED,
					MyPreference.LED_OPEN);
		} else {
			MyPreference.getInstance(context).writeString(MyPreference.LED,
					MyPreference.LED_CLOSE);
		}

		if (array[4] == 1) {
			MyPreference.getInstance(context).writeString(MyPreference.HEAT,
					MyPreference.HEAT_OPEN);
		} else {
			MyPreference.getInstance(context).writeString(MyPreference.HEAT,
					MyPreference.HEAT_CLOSE);
		}
		if (array[5] == 1) {
			MyPreference.getInstance(context).writeString(
					MyPreference.BLUETOOTH, MyPreference.BLUETOOTH_OPEN);
		} else {
			MyPreference.getInstance(context).writeString(
					MyPreference.BLUETOOTH, MyPreference.BLUETOOTH_CLOSE);
		}
		if (array[6] == 0) {
			if (array[7] == 0) {
				MyPreference.getInstance(context).writeString(
						MyPreference.ZERO, MyPreference.ZERO_CLOSE);
			}
		} else {
			MyPreference.getInstance(context).writeString(MyPreference.ZERO,
					MyPreference.ZERO_OPEN);
		}*/
	}

	/**
	 * ��byteתΪ�ַ�����bit
	 */
	public byte[] getBooleanArray(byte b) {
		byte[] array = new byte[8];
		for (int i = 7; i >= 0; i--) {
			array[i] = (byte) (b & 1);
			b = (byte) (b >> 1);
		}

		return array;
	}

//	public boolean getCommand(byte[] bytes) {
//		// ���߸��ֽ�
//		byte[] array7 = new byte[8];
//		for (int j = 7; j >= 0; j--) {
//			array7[j] = (byte) (bytes[4] & 1);
//			bytes[4] = (byte) (bytes[4] >> 1);
//		}
//		if (array7[1] == 1) {
//			return true;
//
//		}
//		return false;
//		// ���ĸ��ֽ�
//		/*
//		 * byte[] array = new byte[8]; for (int i = 7; i >= 0; i--) { array[i] =
//		 * (byte)(bytes[4] & 1); bytes[4] = (byte) (bytes[4] >> 1); }
//		 * if(array[1]==1) {
//		 * MyPreference.getInstance(context).writeString(MyPreference.OXTGEN,
//		 * MyPreference.OXTGEN_OPEN); }else{
//		 * MyPreference.getInstance(context).writeString(MyPreference.OXTGEN,
//		 * MyPreference.OXTGEN_CLOSE); } if(array[2]==1){
//		 * MyPreference.getInstance(context).writeString(MyPreference.SWING,
//		 * MyPreference.SWING_OPEN); }else{
//		 * MyPreference.getInstance(context).writeString(MyPreference.SWING,
//		 * MyPreference.SWING_CLOSE); } if(array[3]==1){
//		 * MyPreference.getInstance(context).writeString(MyPreference.LED,
//		 * MyPreference.LED_OPEN); }else{
//		 * MyPreference.getInstance(context).writeString(MyPreference.LED,
//		 * MyPreference.LED_CLOSE); }
//		 * 
//		 * if(array[4]==1){
//		 * MyPreference.getInstance(context).writeString(MyPreference.HEAT,
//		 * MyPreference.HEAT_OPEN); }else{
//		 * MyPreference.getInstance(context).writeString(MyPreference.HEAT,
//		 * MyPreference.HEAT_CLOSE); } if(array[5]==1){
//		 * MyPreference.getInstance(context).writeString(MyPreference.BLUETOOTH,
//		 * MyPreference.BLUETOOTH_OPEN); }else{
//		 * MyPreference.getInstance(context).writeString(MyPreference.BLUETOOTH,
//		 * MyPreference.BLUETOOTH_CLOSE); } if(array[6]==0){ if(array[7]==0){
//		 * MyPreference.getInstance(context).writeString(MyPreference.ZERO,
//		 * MyPreference.ZERO_CLOSE); } }else{
//		 * MyPreference.getInstance(context).writeString(MyPreference.ZERO,
//		 * MyPreference.ZERO_OPEN); }
//		 */
//	}

}
