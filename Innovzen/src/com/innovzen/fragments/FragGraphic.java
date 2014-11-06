package com.innovzen.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.innovzen.fragments.base.FragBase;
import com.innovzen.handlers.ExerciseAnimationHandler;
import com.innovzen.o2chair.R;
import com.innovzen.utils.MyPreference;

/**
 * 动画图案选择
 * @author desmond.duan
 *
 */
public class FragGraphic extends FragBase implements OnClickListener{
	private ImageView exercise_graphic,relax_graphic,breath_graphic,summer_graphic;
	private LinearLayout left_mid;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_graphic, container, false);
        init(view);

        return view;
    }
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.exercise_graphic:
			exercise_graphic.setBackgroundResource(R.drawable.btn_1_activated);
			relax_graphic.setBackgroundResource(R.drawable.selector_btn_btn2);
			breath_graphic.setBackgroundResource(R.drawable.selector_btn_btn3);
			summer_graphic.setBackgroundResource(R.drawable.selector_btn_btn4);
			MyPreference.getInstance(this.getActivity()).
				writeString(MyPreference.GRAPHIC, ExerciseAnimationHandler.ANIMATION_GRADIENT);
			break;
		case R.id.relax_graphic:
			exercise_graphic.setBackgroundResource(R.drawable.selector_icon_btn1);
			relax_graphic.setBackgroundResource(R.drawable.btn_2_activated);
			breath_graphic.setBackgroundResource(R.drawable.selector_btn_btn3);
			summer_graphic.setBackgroundResource(R.drawable.selector_btn_btn4);
			MyPreference.getInstance(this.getActivity()).
				writeString(MyPreference.GRAPHIC, ExerciseAnimationHandler.ANIMATION_PETALS);	
			break;
		case R.id.breath_graphic:
			exercise_graphic.setBackgroundResource(R.drawable.selector_icon_btn1);
			relax_graphic.setBackgroundResource(R.drawable.selector_btn_btn2);
			breath_graphic.setBackgroundResource(R.drawable.btn_3_activated);
			summer_graphic.setBackgroundResource(R.drawable.selector_btn_btn4);
			MyPreference.getInstance(this.getActivity()).
				writeString(MyPreference.GRAPHIC, ExerciseAnimationHandler.ANIMATION_LUNGS);
			break;
		case R.id.summer_graphic:
			exercise_graphic.setBackgroundResource(R.drawable.selector_icon_btn1);
			relax_graphic.setBackgroundResource(R.drawable.selector_btn_btn2);
			breath_graphic.setBackgroundResource(R.drawable.selector_btn_btn3);
			summer_graphic.setBackgroundResource(R.drawable.btn_4_activated);
			MyPreference.getInstance(this.getActivity()).
				writeString(MyPreference.GRAPHIC, ExerciseAnimationHandler.ANIMATION_BEACH);
			break;
		case R.id.left_top:
			switch (MyPreference.getInstance(this.getActivity()).readInt(MyPreference.GRAPHIC)) {
			case ExerciseAnimationHandler.ANIMATION_GRADIENT:
				super.activityListener.fragGradientAnimationPicked();
				break;
			case ExerciseAnimationHandler.ANIMATION_PETALS:
				super.activityListener.fragPetalsAnimationPicked();
				break;
			case ExerciseAnimationHandler.ANIMATION_LUNGS:
				super.activityListener.fragLungsAnimationPicked();
				break;
			case ExerciseAnimationHandler.ANIMATION_BEACH:
				super.activityListener.fragBeachAnimationPicked();
				break;

			default:
				super.activityListener.fragGradientAnimationPicked();
				break;
			}
			
		default:
			break;
		}
		
	}

	@Override
	public void init(View view) {
		initLefter(view);
		getMyShareSharedPreferences("time");
		left_mid = (LinearLayout) view.findViewById(R.id.left_mid);
		left_mid.setBackgroundResource(R.drawable.banner_graphic);
		exercise_graphic = (ImageView) view.findViewById(R.id.exercise_graphic);
		exercise_graphic.setOnClickListener(this);
		relax_graphic = (ImageView) view.findViewById(R.id.relax_graphic);
		relax_graphic.setOnClickListener(this);
		breath_graphic =(ImageView) view.findViewById(R.id.breath_graphic);
		breath_graphic.setOnClickListener(this);
		summer_graphic =(ImageView) view.findViewById(R.id.summer_graphic);
		summer_graphic.setOnClickListener(this);
		leftTop.setOnClickListener(this);
		
		
		switch (MyPreference.getInstance(this.getActivity()).readInt(MyPreference.GRAPHIC)) {
		case ExerciseAnimationHandler.ANIMATION_GRADIENT:
			exercise_graphic.setBackgroundResource(R.drawable.btn_1_activated);
			break;
		case ExerciseAnimationHandler.ANIMATION_PETALS:
			relax_graphic.setBackgroundResource(R.drawable.btn_2_activated);
			break;
		case ExerciseAnimationHandler.ANIMATION_LUNGS:
			breath_graphic.setBackgroundResource(R.drawable.btn_3_activated);
			break;
		case ExerciseAnimationHandler.ANIMATION_BEACH:
			summer_graphic.setBackgroundResource(R.drawable.btn_4_activated);
			break;

		default:
			exercise_graphic.setBackgroundResource(R.drawable.btn_1_activated);
			break;
		}

	
	}

}
//package com.innovzen.fragments;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.content.SharedPreferences.Editor;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.View.OnClickListener;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//
//import com.innovzen.fragments.base.FragBase;
//import com.innovzen.o2chair.R;
//
//public class FragGraphic extends FragBase implements OnClickListener{
//	private String FILE = "saveGraphic";//用于保存SharedPreferences的文件
//	private SharedPreferences sp = null;//声明一个SharedPreferences存贮gtaphic图片样式
//	private ImageView exercise_graphic,relax_graphic,breath_graphic,summer_graphic;
//	private LinearLayout left_mid;
//	@Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_graphic, container, false);
//        sp = getActivity().getSharedPreferences(FILE,Context.MODE_PRIVATE);
//        init(view);
//
//        return view;
//    }
//	@Override
//	public void onClick(View v) {
//		Editor editor = sp.edit();
//		switch (v.getId()) {
//		case R.id.exercise_graphic:
//			exercise_graphic.setBackgroundResource(R.drawable.btn_1_activated);
//			relax_graphic.setBackgroundResource(R.drawable.selector_btn_btn2);
//			breath_graphic.setBackgroundResource(R.drawable.selector_btn_btn3);
//			summer_graphic.setBackgroundResource(R.drawable.selector_btn_btn4);
//			editor.putString("graphic","exercise");
//			editor.commit();
//			//super.activityListener.fragGradientAnimationPicked();
//			break;
//		case R.id.relax_graphic:
//			exercise_graphic.setBackgroundResource(R.drawable.selector_icon_btn1);
//			relax_graphic.setBackgroundResource(R.drawable.btn_2_activated);
//			breath_graphic.setBackgroundResource(R.drawable.selector_btn_btn3);
//			summer_graphic.setBackgroundResource(R.drawable.selector_btn_btn4);
//			editor.putString("graphic","relax");
//			editor.commit();
//			super.activityListener.fragPetalsAnimationPicked();
//			break;
//		case R.id.breath_graphic:
//			exercise_graphic.setBackgroundResource(R.drawable.selector_icon_btn1);
//			relax_graphic.setBackgroundResource(R.drawable.selector_btn_btn2);
//			breath_graphic.setBackgroundResource(R.drawable.btn_3_activated);
//			summer_graphic.setBackgroundResource(R.drawable.selector_btn_btn4);
//			editor.putString("graphic","breath");
//			editor.commit();
//			super.activityListener.fragLungsAnimationPicked();
//			break;
//		case R.id.summer_graphic:
//			exercise_graphic.setBackgroundResource(R.drawable.selector_icon_btn1);
//			relax_graphic.setBackgroundResource(R.drawable.selector_btn_btn2);
//			breath_graphic.setBackgroundResource(R.drawable.selector_btn_btn3);
//			summer_graphic.setBackgroundResource(R.drawable.btn_4_activated);
//			editor.putString("graphic","summer");
//			editor.commit();
//			super.activityListener.fragBeachAnimationPicked();
//			break;
//		default:
//			break;
//		}
//		
//	}
//
//	@Override
//	public void init(View view) {
//		initLefter(view);
//		getMyShareSharedPreferences("time");
//		left_mid = (LinearLayout) view.findViewById(R.id.left_mid);
//		left_mid.setBackgroundResource(R.drawable.banner_graphic);
//		exercise_graphic = (ImageView) view.findViewById(R.id.exercise_graphic);
//		exercise_graphic.setOnClickListener(this);
//		relax_graphic = (ImageView) view.findViewById(R.id.relax_graphic);
//		relax_graphic.setOnClickListener(this);
//		breath_graphic =(ImageView) view.findViewById(R.id.breath_graphic);
//		breath_graphic.setOnClickListener(this);
//		summer_graphic =(ImageView) view.findViewById(R.id.summer_graphic);
//		summer_graphic.setOnClickListener(this);
//		String myGraphic =sp.getString("graphic","");
//		exercise_graphic.setBackgroundResource(R.drawable.btn_1_activated);
//		if(myGraphic==null||myGraphic.equals("exercise")){
//			exercise_graphic.setBackgroundResource(R.drawable.btn_1_activated);
//		}else if(myGraphic.equals("relax")){
//			relax_graphic.setBackgroundResource(R.drawable.btn_2_activated);
//			exercise_graphic.setBackgroundResource(R.drawable.selector_icon_btn1);
//		}else if(myGraphic.equals("breath")){
//			breath_graphic.setBackgroundResource(R.drawable.btn_3_activated);
//			exercise_graphic.setBackgroundResource(R.drawable.selector_icon_btn1);
//			
//		}else if(myGraphic.equals("summer")){
//			summer_graphic.setBackgroundResource(R.drawable.btn_4_activated);
//			exercise_graphic.setBackgroundResource(R.drawable.selector_icon_btn1);
//		}
//	
//	}
//
//}
