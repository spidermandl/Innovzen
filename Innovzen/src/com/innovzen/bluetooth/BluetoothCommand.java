package com.innovzen.bluetooth;

import java.util.HashMap;

import android.content.Context;
import android.util.Log;

/**
 * ��������
 * 
 * @author desmond.duan
 * 
 */
public class BluetoothCommand {

	/* BluetoothService */
	public static BluetoothCommand instance;
    public static long SUBTIME=0;
	/**
	 * ������������ֽ�
	 */
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
	public static final int SWING = 0x1c;
	public static final int OXYGEN = 0x1a;
	public static final int LEDS = 0x17;
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
	// Blance����������������
	public static int BLANCE_MACHINE_VALUES[] = { START_MACHINE,
			ANDROID_TABLET, BALANCE, 0x11, END_MACHINE };
	// Relax����������������
	public static int RELAX_MACHINE_VALUES[] = { START_MACHINE, ANDROID_TABLET,
			RELAX, 0x11, END_MACHINE };
	// Performance����������������
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
		LEDS, 0x11, END_MACHINE };
	// Swing����
	public static int SWING_MACHINE_VALUES[] = { START_MACHINE, ANDROID_TABLET,
		SWING, 0x11, END_MACHINE };
	//5min����
	public static int TIME5_MACHINE_VALUES[] = { START_MACHINE, ANDROID_TABLET,
		FIVE_MIN, 0x11, END_MACHINE };
	//10min����
	public static int TIME10_MACHINE_VALUES[] = { START_MACHINE, ANDROID_TABLET,
		TEN_MIN, 0x11, END_MACHINE };
	//15min����
	public static int TIME15_MACHINE_VALUES[] = { START_MACHINE, ANDROID_TABLET,
		FIFTEEN_MIN, 0x11, END_MACHINE };
	//20min����
	public static int TIME20_MACHINE_VALUES[] = { START_MACHINE, ANDROID_TABLET,
		TWENTY_MIN, 0x11, END_MACHINE };
	//25min����
	public static int TIME25_MACHINE_VALUES[] = { START_MACHINE, ANDROID_TABLET,
		TWENTY_FIVE_MIN, 0x11, END_MACHINE };
	//30min����
	public static int TIME30_MACHINE_VALUES[] = { START_MACHINE, ANDROID_TABLET,
		THIRTY_MIN, 0x11, END_MACHINE };
	public static int BLUETOOTH_MACHINE_VALUES[] = { START_MACHINE, ANDROID_TABLET,
		BLUETOOTH_ON_OFF, 0x11, END_MACHINE };
	/**
	 * ����״̬
	 */
	//��Ħ������״̬
	public static final int MACHINE_RUN_STATUS=0xFFFF;
	//��Ħģʽ
	public static final int MACHINE_MASSAGE_STATUS=0xFFFE;
    //���ް�����Ӧ״̬
	public static final int BUTTON_STATUS=0xFFFD;
    //����״̬
	public static final int DIRECTION_STATUS=0xFFFC;
	//С�ȵ綯��״̬
	public static final int FOOT_STATUS=0xFFFB;
	//�����綯��״̬
	public static final int BACK_STATUS=0xFFFA;
	//����λ��״̬
	public static final int WALKING_POSITION_STATUS=0xFFF9;
	//Oxygen��״̬
	public static final int OXYGEN_STATUS=0xFFF8;
	//Swing��״̬
	public static final int SWING_STATUS=0xFFF7;
	//Pulse��״̬
	public static final int PULSE_STATUS=0xFFF6;
	//Heat��״̬
	public static final int HEAT_STATUS=0xFFF5;
	//Bluetooth��״̬
	public static final int BLUETOOTH_STATUS=0xFFF4;
	//Zero��״̬
	public static final int ZERO_STATUS=0xFFF3;
	//Led��״̬
	public static final int LED_STATUS=0xFFF2;
	//�Զ������ʼ�����״̬
	public static final int INIT_POSITION_STATUS=0xFFF1;
	//Pause״̬
	public static final int PAUSE_STATUS=0xFFF0;
	//Buzzer״̬
	public static final int BUZZER_STATUS=0xFFEF;
	//Breathe״̬
	public static final int BREATHE_STATUS=0xFFEE;
	//��Ħ������ ��Ӧ״̬
	public static final int MACHINE_RUN_STATUS_WAIT=0;
	public static final int MACHINE_RUN_STATUS_COLLECT=1;
	public static final int MACHINE_RUN_STATUS_RUNNING=2;
	public static final int MACHINE_RUN_STATUS_PAUSE=3;
	public static final int MACHINE_RUN_STATUS_RETAIN1=4;
	public static final int MACHINE_RUN_STATUS_RETAIN2=5;
	public static final int MACHINE_RUN_STATUS_RETAIN3=6;
	public static final int MACHINE_RUN_STATUS_RETAIN4=7;
	//��Ħģʽ ��Ӧ״̬
	public static final int MODE_STATUS_NONE=0;
	public static final int MODE_STATUS_BALANCE=1;
	public static final int MODE_STATUS_RELAX=2;
	public static final int MODE_STATUS_PERFORMANCE=3;
	public static final int MODE_STATUS_MYSESSION1=4;
	public static final int MODE_STATUS_MYSESSION2=5;
	public static final int MODE_STATUS_MYSESSION3=6;
	public static final int MODE_STATUS_MYSESSION4=7;
	//����λ�ü��� ��Ӧ״̬
	public static final int WALKING_POSITION_STATUS_RETAIN0=0;
	public static final int WALKING_POSITION_STATUS1=1;
	public static final int WALKING_POSITION_STATUS2=2;
	public static final int WALKING_POSITION_STATUS3=3;
	public static final int WALKING_POSITION_STATUS4=4;
	public static final int WALKING_POSITION_STATUS5=5;
	public static final int WALKING_POSITION_STATUS6=6;
	public static final int WALKING_POSITION_STATUS7=7;
	public static final int WALKING_POSITION_STATUS8=8;
	public static final int WALKING_POSITION_STATUS9=9;
	public static final int WALKING_POSITION_STATUS10=10;
	public static final int WALKING_POSITION_STATUS11=11;
	public static final int WALKING_POSITION_STATUS12=12;
	public static final int WALKING_POSITION_STATUS_RETAIN13=13;
	public static final int WALKING_POSITION_STATUS_RETAIN14=14;
	public static final int WALKING_POSITION_STATUS_RETAIN15=15;
	//�����Ӧ״̬
	public static final int DIRECTION_STATUS_STOP=0;
	public static final int DIRECTION_STATUS_UP=1;
	public static final int DIRECTION_STATUS_DOWN=2;
	public static final int DIRECTION_STATUS_RETAIN=3;
	//С�ȵ綯�� ��Ӧ״̬
	public static final int FOOT_STATUS_STOP=0;
	public static final int FOOT_STATUS_UP=1;
	public static final int FOOT_STATUS_DOWN=2;
	public static final int FOOT_STATUS_ARRIVE=3;
	public static final int FOOT_STATUS_RETAIN0=4;
	public static final int FOOT_STATUS_RETAIN1=5;
	public static final int FOOT_STATUS_RETAIN2=6;
	public static final int FOOT_STATUS_RETAIN3=7;
	//�����綯��״̬
	public static final int BACK_STATUS_STOP=0;
	public static final int BACK_STATUS_UP=1;
	public static final int BACK_STATUS_DOWN=2;
	public static final int BACK_STATUS_ARRIVE=3;
	public static final int BACK_STATUS_RETAIN0=4;
	public static final int BACK_STATUS_RETAIN1=5;
	public static final int BACK_STATUS_RETAIN2=6;
	public static final int BACK_STATUS_RETAIN3=7;
	//Zero������  ״̬
	public static final int ZERO_STATUS_CLOSE=0;
	public static final int ZERO_STATUS_POSITION1=1;
	public static final int ZERO_STATUS_POSITION2=2;
	public static final int ZERO_STATUS_RETAIN0=4;
	//Buzzer״̬
	public static final int BUZZER_STATUS_SIlENCE=0;
	public static final int BUZZER_STATUS_SLOWLY=1;
	public static final int BUZZER_STATUS_QUICKLY=2;
	public static final int BUZZER_STATUS_SINGLE=3;
	//Breathe״̬
	public static final int BREATHE_STATUS_RETAIN0=0;
	public static final int BREATHE_STATUS_VIGOR1=1;
	public static final int BREATHE_STATUS_VIGOR2=2;
	public static final int BREATHE_STATUS_VIGOR3=3;
	public static final int BREATHE_STATUS_VIGOR4=4;
	public static final int BREATHE_STATUS_VIGOR5=5;
	public static final int BREATHE_STATUS_RETAIN1=6;
	public static final int BREATHE_STATUS_RETAIN2=7;
	
	//������ʼ��״̬
	public static final int INIT_POSITION_STATUS_INVALID=0;
	public static final int INIT_POSITION_STATUS_VALID=1;
	//Oxygen��ʼ��״̬
	public static final int OXYGEN_STATUS_OFF=0;
	public static final int OXYGEN_STATUS_ON=1;
	//Led��ʼ��״̬
	public static final int LED_STATUS_OFF=0;
	public static final int LED_STATUS_ON=1;
	//Swing��ʼ��״̬
	public static final int SWING_STATUS_OFF=0;
	public static final int SWING_STATUS_ON=1;
	//Heat��ʼ��״̬
	public static final int HEAT_STATUS_OFF=0;
	public static final int HEAT_STATUS_ON=1;
	//Bluetooth��ʼ��״̬
	public static final int BLUETOOTH_STATUS_OFF=0;
	public static final int BLUETOOTH_STATUS_ON=1;
	//Pulse��ʼ��״̬
	public static final int PULSE_STATUS_OFF=0;
	public static final int PULSE_STATUS_ON=1;
	//Pause��ʼ��״̬
	public static final int PAUSE_STATUS_OFF=0;
	public static final int PAUSE_STATUS_ON=1;
	
	//ͬ��ʱ�����
	private long inhaleTimeError=0;
	private long exhaleTimeError=0;
	private BluetoothService mBluetoothService = null;
	private Context context = null;

	/**
	 * �������е�״ֵ̬������machine_status�У�ÿ�Σ�100ms��pad���յ�������������9���ֽڣ�����������hashmap��ÿ��pad�����﹦�ܰ�ť��״̬����machine_status����
	 * ÿһ��keyֵ�����ܰ�ť���ƣ���Oxygen��Swing��Pulse��Heat��Bluetooth��Zero ��Щ������ɾ�̬String
	 * ÿһ��valueֵ����ǰ���ܰ�ť��״̬�����簴Ħ������״̬(3bit)��8��״̬��״̬��ʾ����0,1...7 ��Щ״̬Ҳ���þ�̬int��ʾ
	 *                              Pulse״̬(1bit)��2��״̬��״̬��ʾ����0,1
	 */
	public HashMap<Integer, Integer> machine_status=new HashMap<Integer, Integer>(){
		/**
		 * ������״̬�ĳ�ʼֵ,��
		 */
		{   
			put(MACHINE_RUN_STATUS, MACHINE_RUN_STATUS_WAIT);
			put(MACHINE_MASSAGE_STATUS, MODE_STATUS_NONE);
			put(BUTTON_STATUS, 0);
			put(DIRECTION_STATUS, DIRECTION_STATUS_STOP);
			put(WALKING_POSITION_STATUS, WALKING_POSITION_STATUS1);
			put(FOOT_STATUS, FOOT_STATUS_STOP);
			put(BACK_STATUS, BACK_STATUS_STOP);
			put(INIT_POSITION_STATUS, INIT_POSITION_STATUS_INVALID);
			put(PAUSE_STATUS,PAUSE_STATUS_OFF);
			put(BUZZER_STATUS, BUZZER_STATUS_SIlENCE);
			put(BREATHE_STATUS, BREATHE_STATUS_VIGOR1);
			put(PULSE_STATUS, PULSE_STATUS_OFF);
			put(OXYGEN_STATUS, OXYGEN_STATUS_OFF);
			put(SWING_STATUS, SWING_STATUS_OFF);
			put(LED_STATUS, LED_STATUS_OFF);
			put(HEAT_STATUS, HEAT_STATUS_OFF);
			put(LED_STATUS, LED_STATUS_OFF);
			put(BLUETOOTH_STATUS, BLUETOOTH_STATUS_OFF);
			put(ZERO_STATUS, ZERO_STATUS_CLOSE);
			
		}
	};
	
	public static BluetoothCommand getInstance(){
		return instance;
	}
	
	// �ṩ˽�еĹ��췽��
	public BluetoothCommand(Context context, BluetoothService mBluetoothService) {
		this.context = context;
		this.mBluetoothService = mBluetoothService;
		instance=this;
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
	
	private String printHX(byte b){
		StringBuffer str=new StringBuffer();
		int temp=(b&0x80)>>7;
		str.append(temp);
		temp=(b&0x40)>>6;
		str.append(temp);
		temp=(b&0x20)>>5;
		str.append(temp);
		temp=(b&0x10)>>4;
		str.append(temp);
		temp=(b&0x08)>>3;
		str.append(temp);
		temp=(b&0x04)>>2;
		str.append(temp);
		temp=(b&0x02)>>1;
		str.append(temp);
		temp=(b&0x01)>>0;
		str.append(temp);
		
		return str.toString();
	}
	
	public void printCommand(byte[] bytes){
		StringBuffer print=new StringBuffer();
		for(int i=0;i<bytes.length;i++){
			print.append(printHX(bytes[i]))
			.append(" ");
		}
		print.append("\r\n");
		Log.e("����ָ��", print.toString());
	}
	/**
	 * ��������
	 * ����9���ֽڣ����Ұ�ÿ���ֽڵ�״̬д��machine_status
	 * @return
	 */
	public boolean parseCommand(byte[] bytes) {
        //��1�ֽ�	
		//��2�ֽ�
		/**
		 * �ֽ�1
		 */
		byte b1 = bytes[1];
		//ȡ��6 5 4λ��״̬
		machine_status.put(MACHINE_RUN_STATUS, (b1&0x70)>>4);
		//ȡ��3 2 1λ��״̬
		machine_status.put(MACHINE_MASSAGE_STATUS, (b1&0x0e)>>1);
		/**
		 * �ֽ�2
		 */
		byte b2 =bytes[2];
		//ȡ��6λ��״̬
		machine_status.put(BUTTON_STATUS,(b2&0x40)>>6);
		//ȡ��5 4 3 2λ��״̬
		machine_status.put(WALKING_POSITION_STATUS,(b2&0x3c)>>2);
		//ȡ��1 0λ��״̬
		machine_status.put(DIRECTION_STATUS,(b2&0x03)>>0);
		/**
		 * �ֽ�3
		 */
		byte b3 = bytes[3];
		//ȡ��5 4 3λ��״̬
		machine_status.put(FOOT_STATUS,(b3&0x38)>>3);
		//ȡ��2 1 0λ��״̬
		machine_status.put(BACK_STATUS,(b3&0x07)>>0);
		/**
		 * �ֽ�4
		 */
		byte b4 = bytes[4];
		//ȡ��6λ��״̬
		machine_status.put(OXYGEN_STATUS,(b4&0x40)>>6);
		//ȡ��5λ��״̬
		machine_status.put(SWING_STATUS,(b4&0x20)>>5);
		//ȡ��4λ��״̬
		machine_status.put(LED_STATUS,(b4&0x10)>>4);
		//ȡ��3λ��״̬
		machine_status.put(HEAT_STATUS,(b4&0x08)>>3);
		//ȡ��2λ��״̬
		machine_status.put(BLUETOOTH_STATUS,(b4&0x04)>>2);
		//ȡ��1 0λ��״̬
		machine_status.put(ZERO_STATUS,(b4&0x03)>>0);
		/**
		 * �ֽ�5
		 */
		byte b5 = bytes[5];
		//ȡ��6λ��״̬
		machine_status.put(PULSE_STATUS,(b5&0x40)>>6);
		/**
		 * �ֽ�6
		 */
		byte b6 = bytes[6];
		//ȡ��6λ��״̬
		machine_status.put(PULSE_STATUS,(b6&0x40)>>6);
		/**
		 * �ֽ�7
		 */
		byte b7 = bytes[7];
		//ȡ��6λ��״̬
		machine_status.put(INIT_POSITION_STATUS,(b7&0x40)>>6);
		//ȡ��5λ��״̬
		machine_status.put(PAUSE_STATUS,(b7&0x20)>>5);
		//ȡ��4 3λ��״̬
		machine_status.put(BUZZER_STATUS,(b7&0x18)>>3);
		//ȡ��2 1 0λ��״̬
		machine_status.put(BREATHE_STATUS,(b7&0x07));
		
		//Log.e("��99       8�ֽ�", printHX(b7));
		return true;
	}

	/*	//��3�ֽ�
		//��4�ֽ�
		//��5�ֽ�
		//��6�ֽ�
		//��7���ֽ�
		byte b = bytes[6];
		if((byte)(b&0x40)==(byte)0x40){
			*//**
			 * ���ｫ����д�ɰ�״̬λ��ֵд��machine_status
			 *//*

		//ȡ����λ��״̬
		machine_status.put("", (b&0x40)>>5);
        //ȡ����λ��״̬
		machine_status.put("", (b&0x20)>>4);
        //ȡ��������λ��״̬
		machine_status.put("", (b&0x18)>>2);


		//��8�ֽ�
		//��9�ֽ�
		 * }
*/	
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

	public long getInhaleTimeError() {
		return inhaleTimeError;
	}

	public void setInhaleTimeError(long inhaleTimeError) {
		this.inhaleTimeError = inhaleTimeError;
	}

	public long getExhaleTimeError() {
		return exhaleTimeError;
	}

	public void setExhaleTimeError(long exhaleTimeError) {
		this.exhaleTimeError = exhaleTimeError;
	}

}
