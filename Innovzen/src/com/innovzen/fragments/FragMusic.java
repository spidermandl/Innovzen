package com.innovzen.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.innovzen.fragments.base.FragBase;
import com.innovzen.o2chair.R;
import com.innovzen.utils.MyMediaPlayer;
import com.innovzen.utils.MyPreference;

public class FragMusic extends FragBase implements OnClickListener{

	private ImageView left_top,left_bottom;
	LinearLayout left_mid;
	private SharedPreferences sp = null;
	private ImageView music1;
	private ImageView music2;
	private ImageView music3;
	private ImageView music4;
	private ImageView music5;
	private TextView myMinutes;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        init(view);

        return view;
    }
	MyMediaPlayer mediaPlay = new MyMediaPlayer();
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.music1:
			music1.setBackgroundResource(R.drawable.btn_aquatic_music_activated);
			music2.setBackgroundResource(R.drawable.selector_music_bondi);
			music3.setBackgroundResource(R.drawable.selector_music_angelic);
			music4.setBackgroundResource(R.drawable.selector_music_ithaca);
			music5.setBackgroundResource(R.drawable.selector_music_silence); 
			   MyPreference.getInstance(this.getActivity()).writeString(MyPreference.MUSIC, MyPreference.SELECT_MUSIC1);	   			
			   mediaPlay.play(getActivity(), MyMediaPlayer.AQUATIC);
			   break;
		case R.id.music2:
			music1.setBackgroundResource(R.drawable.selector_music_aquatic);
			music2.setBackgroundResource(R.drawable.btn_bondi_music_activated);
			music3.setBackgroundResource(R.drawable.selector_music_angelic);
			music4.setBackgroundResource(R.drawable.selector_music_ithaca);
			music5.setBackgroundResource(R.drawable.selector_music_silence); 
			   MyPreference.getInstance(this.getActivity()).writeString(MyPreference.MUSIC, MyPreference.SELECT_MUSIC2);	   			
			   mediaPlay.play(getActivity(), MyMediaPlayer.BONDIBREATH);
			break;
		case R.id.music3:
			music1.setBackgroundResource(R.drawable.selector_music_aquatic);
			music2.setBackgroundResource(R.drawable.selector_music_bondi);
			music3.setBackgroundResource(R.drawable.btn_angelic_music_activated);
			music4.setBackgroundResource(R.drawable.selector_music_ithaca);
			music5.setBackgroundResource(R.drawable.selector_music_silence); 
			   MyPreference.getInstance(this.getActivity()).writeString(MyPreference.MUSIC, MyPreference.SELECT_MUSIC3);	   			
			   mediaPlay.play(getActivity(), MyMediaPlayer.ANGELICORGAN);
			break;
		case R.id.music4:
			music1.setBackgroundResource(R.drawable.selector_music_aquatic);
			music2.setBackgroundResource(R.drawable.selector_music_bondi);
			music3.setBackgroundResource(R.drawable.selector_music_angelic);
			music4.setBackgroundResource(R.drawable.btn_ithaca_music_activated);
			music5.setBackgroundResource(R.drawable.selector_music_silence); 
			   MyPreference.getInstance(this.getActivity()).writeString(MyPreference.MUSIC, MyPreference.SELECT_MUSIC4);	   			
			   mediaPlay.play(getActivity(), MyMediaPlayer.ITHACAVOX);
			break;
		case R.id.music5:
			music1.setBackgroundResource(R.drawable.selector_music_aquatic);
			music2.setBackgroundResource(R.drawable.selector_music_bondi);
			music3.setBackgroundResource(R.drawable.selector_music_angelic);
			music4.setBackgroundResource(R.drawable.selector_music_ithaca);
			music5.setBackgroundResource(R.drawable.btn_silence_music_activated); 
			   MyPreference.getInstance(this.getActivity()).writeString(MyPreference.MUSIC, MyPreference.SELECT_MUSIC5);	   			
			   mediaPlay.play(getActivity(), MyMediaPlayer.SILENCE);
			  
			break;
		case R.id.left_top:
			 mediaPlay.play(getActivity(), MyMediaPlayer.SILENCE);
			getActivity().onBackPressed();
			break;
		default:
			break;
		}
		
	}

	@Override
	public void init(View view) {
		initLefter(view);
		
		
		myMinutes = (TextView) view.findViewById(R.id.myMinutes);
		myMinutes.setText(MyPreference.getInstance(this.getActivity()).readString(MyPreference.TIME));
		left_top = (ImageView) view.findViewById(R.id.left_top);
		left_mid = (LinearLayout) view.findViewById(R.id.left_mid);
		left_top.setBackgroundResource(R.drawable.selector_btn_back);
		left_mid.setBackgroundResource(R.drawable.banner_music);
		music1 = (ImageView) view.findViewById(R.id.music1);		
		music2 = (ImageView) view.findViewById(R.id.music2);
		music3 = (ImageView) view.findViewById(R.id.music3);
		music4 = (ImageView) view.findViewById(R.id.music4);
		music5 = (ImageView) view.findViewById(R.id.music5);
		music1.setOnClickListener(this);
		music2.setOnClickListener(this);
		music3.setOnClickListener(this);
		music4.setOnClickListener(this);
		music5.setOnClickListener(this);
		
		music1.setBackgroundResource(R.drawable.btn_aquatic_music_activated);
		String myMusic =MyPreference.getInstance(this.getActivity()).readString(MyPreference.MUSIC);
		if(myMusic==null||myMusic.equals(MyPreference.SELECT_MUSIC1)){
			music1.setBackgroundResource(R.drawable.btn_aquatic_music_activated);
		}else if(myMusic.equals(MyPreference.SELECT_MUSIC2)){
			music2.setBackgroundResource(R.drawable.btn_bondi_music_activated);
			music1.setBackgroundResource(R.drawable.selector_music_aquatic);
		}else if(myMusic.equals(MyPreference.SELECT_MUSIC3)){
			music3.setBackgroundResource(R.drawable.btn_angelic_music_activated);
			music1.setBackgroundResource(R.drawable.selector_music_aquatic);
		}else if(myMusic.equals(MyPreference.SELECT_MUSIC4)){
			music4.setBackgroundResource(R.drawable.btn_ithaca_music_activated);
			music1.setBackgroundResource(R.drawable.selector_music_aquatic);
		}else if(myMusic.equals(MyPreference.SELECT_MUSIC5)){
			music5.setBackgroundResource(R.drawable.btn_silence_music_activated);
			music1.setBackgroundResource(R.drawable.selector_music_aquatic);
		
	}
	}

}
