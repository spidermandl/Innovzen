package com.innovzen.bluetooth;

import java.util.HashMap;

import android.content.Context;
import android.util.Log;

/**
 * 蓝牙命令
 * 
 * @author desmond.duan
 * 
 */
public class BluetoothCommand {

	/* BluetoothService */
	public static BluetoothCommand instance;
    public static long SUBTIME=0;
	/**
	 * 发送命令相关字节
	 */
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
	// 开机关机命令
	public static int START_MACHINE_VALUES[] = { START_MACHINE, ANDROID_TABLET,
			POWER_ON_OFF, 0x11, END_MACHINE };
	// 暂停
	public static int PAUSE_MACHINE_VALUES[] = { START_MACHINE, ANDROID_TABLET,
			PAUSE, 0x11, END_MACHINE };
	// Blance发送启动动画命令
	public static int BLANCE_MACHINE_VALUES[] = { START_MACHINE,
			ANDROID_TABLET, BALANCE, 0x11, END_MACHINE };
	// Relax发送启动动画命令
	public static int RELAX_MACHINE_VALUES[] = { START_MACHINE, ANDROID_TABLET,
			RELAX, 0x11, END_MACHINE };
	// Performance发送启动动画命令
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
		LEDS, 0x11, END_MACHINE };
	// Swing命令
	public static int SWING_MACHINE_VALUES[] = { START_MACHINE, ANDROID_TABLET,
		SWING, 0x11, END_MACHINE };
	//5min命令
	public static int TIME5_MACHINE_VALUES[] = { START_MACHINE, ANDROID_TABLET,
		FIVE_MIN, 0x11, END_MACHINE };
	//10min命令
	public static int TIME10_MACHINE_VALUES[] = { START_MACHINE, ANDROID_TABLET,
		TEN_MIN, 0x11, END_MACHINE };
	//15min命令
	public static int TIME15_MACHINE_VALUES[] = { START_MACHINE, ANDROID_TABLET,
		FIFTEEN_MIN, 0x11, END_MACHINE };
	//20min命令
	public static int TIME20_MACHINE_VALUES[] = { START_MACHINE, ANDROID_TABLET,
		TWENTY_MIN, 0x11, END_MACHINE };
	//25min命令
	public static int TIME25_MACHINE_VALUES[] = { START_MACHINE, ANDROID_TABLET,
		TWENTY_FIVE_MIN, 0x11, END_MACHINE };
	//30min命令
	public static int TIME30_MACHINE_VALUES[] = { START_MACHINE, ANDROID_TABLET,
		THIRTY_MIN, 0x11, END_MACHINE };
	public static int BLUETOOTH_MACHINE_VALUES[] = { START_MACHINE, ANDROID_TABLET,
		BLUETOOTH_ON_OFF, 0x11, END_MACHINE };
	/**
	 * 接受状态
	 */
	//按摩椅运行状态
	public static final int MACHINE_RUN_STATUS=0xFFFF;
	//按摩模式
	public static final int MACHINE_MASSAGE_STATUS=0xFFFE;
    //有无按键回应状态
	public static final int BUTTON_STATUS=0xFFFD;
    //方向状态
	public static final int DIRECTION_STATUS=0xFFFC;
	//小腿电动缸状态
	public static final int FOOT_STATUS=0xFFFB;
	//靠背电动缸状态
	public static final int BACK_STATUS=0xFFFA;
	//行走位置状态
	public static final int WALKING_POSITION_STATUS=0xFFF9;
	//Oxygen键状态
	public static final int OXYGEN_STATUS=0xFFF8;
	//Swing键状态
	public static final int SWING_STATUS=0xFFF7;
	//Pulse键状态
	public static final int PULSE_STATUS=0xFFF6;
	//Heat键状态
	public static final int HEAT_STATUS=0xFFF5;
	//Bluetooth键状态
	public static final int BLUETOOTH_STATUS=0xFFF4;
	//Zero键状态
	public static final int ZERO_STATUS=0xFFF3;
	//Led键状态
	public static final int LED_STATUS=0xFFF2;
	//自动程序初始化完成状态
	public static final int INIT_POSITION_STATUS=0xFFF1;
	//Pause状态
	public static final int PAUSE_STATUS=0xFFF0;
	//Buzzer状态
	public static final int BUZZER_STATUS=0xFFEF;
	//Breathe状态
	public static final int BREATHE_STATUS=0xFFEE;
	//按摩椅运行 对应状态
	public static final int MACHINE_RUN_STATUS_WAIT=0;
	public static final int MACHINE_RUN_STATUS_COLLECT=1;
	public static final int MACHINE_RUN_STATUS_RUNNING=2;
	public static final int MACHINE_RUN_STATUS_PAUSE=3;
	public static final int MACHINE_RUN_STATUS_RETAIN1=4;
	public static final int MACHINE_RUN_STATUS_RETAIN2=5;
	public static final int MACHINE_RUN_STATUS_RETAIN3=6;
	public static final int MACHINE_RUN_STATUS_RETAIN4=7;
	//按摩模式 对应状态
	public static final int MODE_STATUS_NONE=0;
	public static final int MODE_STATUS_BALANCE=1;
	public static final int MODE_STATUS_RELAX=2;
	public static final int MODE_STATUS_PERFORMANCE=3;
	public static final int MODE_STATUS_MYSESSION1=4;
	public static final int MODE_STATUS_MYSESSION2=5;
	public static final int MODE_STATUS_MYSESSION3=6;
	public static final int MODE_STATUS_MYSESSION4=7;
	//行走位置计数 对应状态
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
	//方向对应状态
	public static final int DIRECTION_STATUS_STOP=0;
	public static final int DIRECTION_STATUS_UP=1;
	public static final int DIRECTION_STATUS_DOWN=2;
	public static final int DIRECTION_STATUS_RETAIN=3;
	//小腿电动缸 对应状态
	public static final int FOOT_STATUS_STOP=0;
	public static final int FOOT_STATUS_UP=1;
	public static final int FOOT_STATUS_DOWN=2;
	public static final int FOOT_STATUS_ARRIVE=3;
	public static final int FOOT_STATUS_RETAIN0=4;
	public static final int FOOT_STATUS_RETAIN1=5;
	public static final int FOOT_STATUS_RETAIN2=6;
	public static final int FOOT_STATUS_RETAIN3=7;
	//靠背电动缸状态
	public static final int BACK_STATUS_STOP=0;
	public static final int BACK_STATUS_UP=1;
	public static final int BACK_STATUS_DOWN=2;
	public static final int BACK_STATUS_ARRIVE=3;
	public static final int BACK_STATUS_RETAIN0=4;
	public static final int BACK_STATUS_RETAIN1=5;
	public static final int BACK_STATUS_RETAIN2=6;
	public static final int BACK_STATUS_RETAIN3=7;
	//Zero零重力  状态
	public static final int ZERO_STATUS_CLOSE=0;
	public static final int ZERO_STATUS_POSITION1=1;
	public static final int ZERO_STATUS_POSITION2=2;
	public static final int ZERO_STATUS_RETAIN0=4;
	//Buzzer状态
	public static final int BUZZER_STATUS_SIlENCE=0;
	public static final int BUZZER_STATUS_SLOWLY=1;
	public static final int BUZZER_STATUS_QUICKLY=2;
	public static final int BUZZER_STATUS_SINGLE=3;
	//Breathe状态
	public static final int BREATHE_STATUS_RETAIN0=0;
	public static final int BREATHE_STATUS_VIGOR1=1;
	public static final int BREATHE_STATUS_VIGOR2=2;
	public static final int BREATHE_STATUS_VIGOR3=3;
	public static final int BREATHE_STATUS_VIGOR4=4;
	public static final int BREATHE_STATUS_VIGOR5=5;
	public static final int BREATHE_STATUS_RETAIN1=6;
	public static final int BREATHE_STATUS_RETAIN2=7;
	
	//机器初始化状态
	public static final int INIT_POSITION_STATUS_INVALID=0;
	public static final int INIT_POSITION_STATUS_VALID=1;
	//Oxygen初始化状态
	public static final int OXYGEN_STATUS_OFF=0;
	public static final int OXYGEN_STATUS_ON=1;
	//Led初始化状态
	public static final int LED_STATUS_OFF=0;
	public static final int LED_STATUS_ON=1;
	//Swing初始化状态
	public static final int SWING_STATUS_OFF=0;
	public static final int SWING_STATUS_ON=1;
	//Heat初始化状态
	public static final int HEAT_STATUS_OFF=0;
	public static final int HEAT_STATUS_ON=1;
	//Bluetooth初始化状态
	public static final int BLUETOOTH_STATUS_OFF=0;
	public static final int BLUETOOTH_STATUS_ON=1;
	//Pulse初始化状态
	public static final int PULSE_STATUS_OFF=0;
	public static final int PULSE_STATUS_ON=1;
	//Pause初始化状态
	public static final int PAUSE_STATUS_OFF=0;
	public static final int PAUSE_STATUS_ON=1;
	
	//同步时间误差
	private long inhaleTimeError=0;
	private long exhaleTimeError=0;
	private BluetoothService mBluetoothService = null;
	private Context context = null;

	/**
	 * 机器所有的状态值保存在machine_status中，每次（100ms）pad接收到机器穿过来的9个字节，都会更新这个hashmap，每个pad界面里功能按钮的状态都由machine_status决定
	 * 每一个key值代表功能按钮名称，如Oxygen，Swing，Pulse，Heat，Bluetooth，Zero 这些都定义成静态String
	 * 每一个value值代表当前功能按钮的状态，比如按摩椅运行状态(3bit)有8个状态，状态表示既是0,1...7 这些状态也都用静态int表示
	 *                              Pulse状态(1bit)有2个状态，状态表示既是0,1
	 */
	public HashMap<Integer, Integer> machine_status=new HashMap<Integer, Integer>(){
		/**
		 * 下面是状态的初始值,如
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
	
	// 提供私有的构造方法
	public BluetoothCommand(Context context, BluetoothService mBluetoothService) {
		this.context = context;
		this.mBluetoothService = mBluetoothService;
		instance=this;
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
		Log.e("返回指令", print.toString());
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
		machine_status.put(MACHINE_RUN_STATUS, (b1&0x70)>>4);
		//取第3 2 1位的状态
		machine_status.put(MACHINE_MASSAGE_STATUS, (b1&0x0e)>>1);
		/**
		 * 字节2
		 */
		byte b2 =bytes[2];
		//取第6位的状态
		machine_status.put(BUTTON_STATUS,(b2&0x40)>>6);
		//取第5 4 3 2位的状态
		machine_status.put(WALKING_POSITION_STATUS,(b2&0x3c)>>2);
		//取第1 0位的状态
		machine_status.put(DIRECTION_STATUS,(b2&0x03)>>0);
		/**
		 * 字节3
		 */
		byte b3 = bytes[3];
		//取第5 4 3位的状态
		machine_status.put(FOOT_STATUS,(b3&0x38)>>3);
		//取第2 1 0位的状态
		machine_status.put(BACK_STATUS,(b3&0x07)>>0);
		/**
		 * 字节4
		 */
		byte b4 = bytes[4];
		//取第6位的状态
		machine_status.put(OXYGEN_STATUS,(b4&0x40)>>6);
		//取第5位的状态
		machine_status.put(SWING_STATUS,(b4&0x20)>>5);
		//取第4位的状态
		machine_status.put(LED_STATUS,(b4&0x10)>>4);
		//取第3位的状态
		machine_status.put(HEAT_STATUS,(b4&0x08)>>3);
		//取第2位的状态
		machine_status.put(BLUETOOTH_STATUS,(b4&0x04)>>2);
		//取第1 0位的状态
		machine_status.put(ZERO_STATUS,(b4&0x03)>>0);
		/**
		 * 字节5
		 */
		byte b5 = bytes[5];
		//取第6位的状态
		machine_status.put(PULSE_STATUS,(b5&0x40)>>6);
		/**
		 * 字节6
		 */
		byte b6 = bytes[6];
		//取第6位的状态
		machine_status.put(PULSE_STATUS,(b6&0x40)>>6);
		/**
		 * 字节7
		 */
		byte b7 = bytes[7];
		//取第6位的状态
		machine_status.put(INIT_POSITION_STATUS,(b7&0x40)>>6);
		//取第5位的状态
		machine_status.put(PAUSE_STATUS,(b7&0x20)>>5);
		//取第4 3位的状态
		machine_status.put(BUZZER_STATUS,(b7&0x18)>>3);
		//取第2 1 0位的状态
		machine_status.put(BREATHE_STATUS,(b7&0x07));
		
		//Log.e("第99       8字节", printHX(b7));
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
