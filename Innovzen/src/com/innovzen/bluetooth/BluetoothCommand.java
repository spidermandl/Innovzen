package com.innovzen.bluetooth;

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

	private BluetoothService mBluetoothService = null;
	private Context context = null;

	// �ṩ˽�еĹ��췽��
	// �ṩ˽�еĹ��췽��
	public BluetoothCommand() {
	}

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
	 */
	public int getCommand(byte[] bytes, int offset) {
		int value;
		value = (int) ((bytes[offset] & 0xFF)
				| ((bytes[offset + 1] << 8) & 0xFF00)
				| ((bytes[offset + 2] << 16) & 0xFF0000) | ((bytes[offset + 3] << 24) & 0xFF000000));
		return value;
	}

}
