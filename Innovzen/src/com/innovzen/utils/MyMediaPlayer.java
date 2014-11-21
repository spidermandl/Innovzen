package com.innovzen.utils;

import android.content.Context;
import android.media.MediaPlayer;

import com.innovzen.o2chair.R;

public class MyMediaPlayer {
	public static final int AQUATIC = 1;
	public static final int BONDIBREATH = 2;
	public static final int ANGELICORGAN = 3;
	public static final int ITHACAVOX = 4;
	public static final int SILENCE = 5;
	private MediaPlayer player1, player2, player3, player4;
/*
 * ªπ√ª”–≤‚ ‘
 */
	

	public MyMediaPlayer() {
		super();
	}



	public void play(Context context, int musicName) {
		player1 = MediaPlayer.create(context, R.raw.innovzen_aquaticsunbeam_expir);
		player2 = MediaPlayer.create(context, R.raw.innovzen_bondibreath_expir);
		player3 = MediaPlayer
				.create(context, R.raw.innovzen_angelicorgan_expir);
		player4 = MediaPlayer.create(context, R.raw.innovzen_ithacavox_expir);
		

		switch (musicName) {
		case AQUATIC:
			player2.stop();
			player3.stop();
			player4.stop();
			player1.start();
			break;
		case BONDIBREATH:
			player1.stop();
			player3.stop();
			player4.stop();
			player2.start();
			break;
		case ANGELICORGAN:
			player1.stop();
			player2.stop();
			player4.stop();
			player3.start();
			break;
		case ITHACAVOX:
			player1.stop();
			player2.stop();
			player3.stop();
			player4.start();
			break;
		case SILENCE:
			player1.stop();
			player2.stop();
			player3.stop();
			player4.stop();
			break;

		default:
			break;
		}
	}
}
