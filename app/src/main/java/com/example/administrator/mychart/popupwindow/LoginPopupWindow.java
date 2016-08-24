package com.example.administrator.mychart.popupwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.example.administrator.mychart.BaseActy;
import com.example.administrator.mychart.R;

public class LoginPopupWindow {

	private PopupWindow pop = null;
	private LinearLayout ll_popup;

	public LoginPopupWindow() {

	}

	/**
	 *
	 * @param mContext
	 * @param view
	 * @param parentView
     */
	public void getView(Context mContext ,View view ,View parentView) {
        final Context context;
        context  = mContext;
		pop = new PopupWindow(mContext);

		/*View view = getLayoutInflater().inflate(R.layout.item_popupwindows,
				null);*/
		
		ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);

		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.WRAP_CONTENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setContentView(view);

		RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
		//Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
		parent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pop.dismiss();
				//ll_popup.clearAnimation();
				ll_popup.startAnimation(AnimationUtils.loadAnimation(context, R.anim.bottom_down));
			}
		});
		/*bt1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				*//*photo();
				pop.dismiss();
				ll_popup.clearAnimation();*//*
			}
		});*/

		ImageView iv_loading = (ImageView)view.findViewById(R.id.iv_loading);
		Animation_Loading(iv_loading);
		pop.showAtLocation(parentView, Gravity.TOP, 0, 0);


		handler.postDelayed(runnable, TIME); //每隔15s执行

	}

	public void dismiss(){
		pop.dismiss();
		handler.removeCallbacks(runnable);
	}
	public void dealsuccess(){
		pop.dismiss();
		handler.removeCallbacks(runnable);
		BaseActy.ShowCustomToast("登陆成功。");
	}

	public void Animation_Loading(View v) {
		v.setVisibility(View.VISIBLE);

//第一个参数fromDegrees为动画起始时的旋转角度
//第二个参数toDegrees为动画旋转到的角度
//第三个参数pivotXType为动画在X轴相对于物件位置类型
//第四个参数pivotXValue为动画相对于物件的X坐标的开始位置
//第五个参数pivotXType为动画在Y轴相对于物件位置类型
//第六个参数pivotYValue为动画相对于物件的Y坐标的开始位置
		RotateAnimation myAnimation_Rotate=new RotateAnimation(0.0f, +359.0f,Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		myAnimation_Rotate.setDuration(1500);// 设置动画持续时间
		myAnimation_Rotate.setRepeatCount(-1);
		myAnimation_Rotate.setInterpolator(new LinearInterpolator());
		v.setAnimation(myAnimation_Rotate);
	}
	private int TIME = 15000;
	Handler handler = new Handler();
	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			// handler自带方法实现定时器
			try {
				handler.postDelayed(this, TIME);
				pop.dismiss();
				BaseActy.ShowCustomToast("登陆失败。");
				handler.removeCallbacks(runnable);
				System.out.println("do...");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("exception...");
			}

		}
	};
}
