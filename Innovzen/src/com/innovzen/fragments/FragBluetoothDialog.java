package com.innovzen.fragments;

import java.util.Set;

import com.innovzen.o2chair.R;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * Ѱ�������豸
 * ����ͨѶ����
 * @author desmond.duan
 *
 */
public class FragBluetoothDialog extends DialogFragment {
	/**
	 * Desmond �����豸������
	 */
	private BluetoothAdapter mBluetoothAdapter = null;
	/**
	 * �豸��Ϣ
	 */
	private ArrayAdapter<String> mPairedDevicesArrayAdapter;
	private ArrayAdapter<String> mNewDevicesArrayAdapter;
	
	
	 /*������1����ͨ��newInstance()����ʵ��������* */ 
    public static FragBluetoothDialog newInstance(String title,String message){
    	FragBluetoothDialog dg = new FragBluetoothDialog();
    	Bundle bundle = new Bundle(); 
        bundle.putString("title", title); 
        bundle.putString("alert-message", message); 
        dg.setArguments(bundle); 
        
    	return dg;
    }  
         
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
    	View view = inflater.inflate(R.layout.bluetooth_device_list, container, false);
    	ListView pairedListView = (ListView) view.findViewById(R.id.paired_devices);
		mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this.getActivity(),R.layout.bluetooth_device_name);
		mNewDevicesArrayAdapter = new ArrayAdapter<String>(this.getActivity(),R.layout.bluetooth_device_name);
		
		pairedListView.setAdapter(mPairedDevicesArrayAdapter);
		pairedListView.setOnItemClickListener(mDeviceClickListener);

		// Find and set up the ListView for newly discovered devices
		ListView newDevicesListView = (ListView) view.findViewById(R.id.new_devices);
		newDevicesListView.setAdapter(mNewDevicesArrayAdapter);
		newDevicesListView.setOnItemClickListener(mDeviceClickListener);

		// Register for broadcasts when a device is discovered
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		this.getActivity().registerReceiver(mReceiver, filter);

		// Register for broadcasts when discovery has finished
		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		this.getActivity().registerReceiver(mReceiver, filter);

		// ��ȡ�����豸������ʵ��
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		// Get a set of currently paired devices
		if(mBluetoothAdapter!=null){
			Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
	
			// If there are paired devices, add each one to the ArrayAdapter
			if (pairedDevices.size() > 0) {
				view.findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);
				for (BluetoothDevice device : pairedDevices) {
					mPairedDevicesArrayAdapter.add(device.getName() + "\n"
							+ device.getAddress());
				}
			} else {
				String noDevices = getResources().getText(R.string.none_paired).toString();
				mPairedDevicesArrayAdapter.add(noDevices);
			}
		}
		
		doDiscovery();
		
    	return view;
    }
    
    private OnItemClickListener mDeviceClickListener = new OnItemClickListener()
	{
		public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3)
		{
			// Cancel discovery because it's costly and we're about to connect
			mBluetoothAdapter.cancelDiscovery();

			// Get the device MAC address, which is the last 17 chars in the
			// View
			String info = ((TextView) v).getText().toString();
			String address = info.substring(info.length() - 17);
			
            // Get the BLuetoothDevice object
            BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
            // Attempt to connect to the device
            mChatService.connect(device);
		}
	};
    
    /**
	 * Start device discover with the BluetoothAdapter
	 */
	private void doDiscovery()
	{
		// If we're already discovering, stop it
		if (mBluetoothAdapter.isDiscovering())
		{
			mBluetoothAdapter.cancelDiscovery();
		}

		// Request discover from BluetoothAdapter
		mBluetoothAdapter.startDiscovery();
	}
	
    @Override
    public void dismiss() {
    	this.getActivity().unregisterReceiver(mReceiver);
    	super.dismiss();
    }
    
 // The BroadcastReceiver that listens for discovered devices and
 	// changes the title when discovery is finished
 	private final BroadcastReceiver mReceiver = new BroadcastReceiver()
 	{
 		@Override
 		public void onReceive(Context context, Intent intent)
 		{
 			String action = intent.getAction();

 			// When discovery finds a device
 			if (BluetoothDevice.ACTION_FOUND.equals(action))
 			{
 				// Get the BluetoothDevice object from the Intent
 				BluetoothDevice device = intent
 						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
 				// If it's already paired, skip it, because it's been listed
 				// already
 				if (device.getBondState() != BluetoothDevice.BOND_BONDED)
 				{
 					mNewDevicesArrayAdapter.add(device.getName() + "\n"
 							+ device.getAddress());
 				}
 				// When discovery is finished, change the Activity title
 			}
 			else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action))
 			{
 				if (mNewDevicesArrayAdapter.getCount() == 0)
 				{
 					String noDevices = getResources().getText(R.string.none_found).toString();
 					mNewDevicesArrayAdapter.add(noDevices);
 				}
 			}
 		}
 	};
    
    /* ������2������view����ͨ������;����һ��fragment�е�onCreateView()��
     * ����DialogFragment�е�onCreateDialog()��
     * ǰ���ʺ϶��Զ����layout�������ã����и��������� 
     * �������ʺ϶Լ�dialog���д�����������Dialog.Builderֱ�ӷ���Dialog���� 
     * ���������ڵ�˳����ԣ���ִ��onCreateDialog()����ִ��onCreateView()�����ǲ�Ӧͬʱʹ�����ߡ� 
     * */ 
//    @Override 
//    public Dialog onCreateDialog(Bundle savedInstanceState) {  
//        AlertDialog.Builder b = new AlertDialog.Builder(getActivity()) 
//                                    .setTitle(getTitle()) 
//                                    .setMessage(getMessage()) 
//                                    .setPositiveButton("OK", this)  //���ûص����� 
//                                    .setNegativeButton("Cancel",this); //���ûص�����
//        return b.create(); 
//   }  
//
//    @Override //���������Ļص�����
//    public void onClick(DialogInterface dialog, int which) {  
//        boolean isCancel = false; 
//        if(which == AlertDialog.BUTTON_NEGATIVE){ //�ж��û������μ� 
//            isCancel = true; 
//        }  
//        MyActivity act = (MyActivity) getActivity(); 
//        act.onDialogDone(getTag(), isCancel, "CLick OK, Alert dismissed"); 
//    } 
}
