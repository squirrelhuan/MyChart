package com.example.administrator.mychart.popupwindow;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import com.example.administrator.mychart.BaseActy;
import com.example.administrator.mychart.MainActivity;
import com.example.administrator.mychart.MyselfApplication;
import com.example.administrator.mychart.R;
import com.example.administrator.mychart.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConversationPopupWindow {

	private PopupWindow pW_menu = null;
	private WindowManager windowManager;
	private GridView gridview_menu;
	private RelativeLayout rel_menu_bg,rel_menu_bg_02;
	private AnimationSet animationSet;
	private String[] Name = { "多人聊天", "加好友", "扫一扫", "面对面快传", "付款"};
	private int[] Images_ic = {R.drawable.conversation_options_multichat,R.drawable.conversation_options_addmember
			,R.drawable.conversation_options_qr,R.drawable.conversation_facetoface_qr,R.drawable.conversation_options_charge_icon};
//
	public ConversationPopupWindow() {

	}

	//菜单
	public void getView(final Context mContext ,View view ,View parentView) {
	//public void ShowMenuView(View anchor) {
		//View view = null;
		if (pW_menu == null) {
			LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = layoutInflater.inflate(R.layout.cell_menu, null);
			// 创建popWindow
			pW_menu = new PopupWindow(view, LayoutParams.FILL_PARENT,
					LayoutParams.FILL_PARENT);
		} else {
			view = pW_menu.getContentView();
		}
		gridview_menu=(GridView) view.findViewById(R.id.gridview_menu);
		rel_menu_bg=(RelativeLayout) view.findViewById(R.id.rel_menu_bg);
		rel_menu_bg_02=(RelativeLayout) view.findViewById(R.id.rel_menu_bg_02);
		rel_menu_bg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				pW_menu.dismiss();
			}
		});
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		//
		// 通过for循环将图片id和列表项文字放到Map中，并添加到list集合中
		for (int i = 0; i < Name.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("image", Images_ic[i]);
			map.put("name", Name[i]);
			listItems.add(map); // 将map对象添加到List集合中
		} // 通过for循环将图片id和列表项文字放到Map中，并添加到list集合中

		SimpleAdapter adapter = new SimpleAdapter(mContext, listItems, R.layout.cell_menu_item,
				new String[] { "name", "image" },
				new int[] { R.id.menu_item_name, R.id.menu_item_image });
		gridview_menu.setAdapter(adapter);
		gridview_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				switch (position){
					case 0:
						break;
					case 1:
						break;
					case 2://扫一扫
						//Intent intent = new Intent(mContext, CaptureActivity.class);
						animationSet.restrictDuration(500);
						animationSet.reset();
						pW_menu.dismiss();
						((MainActivity)mContext).scanf();
						break;
					case 3:
						break;
					case 4:
						break;
				}
			}
		});

		// 可以聚集 ，按返回键，先popWindow，不然popWindow和activity会同时消失，估计这既是Android焦点顺序的原理吧。
		pW_menu.setFocusable(true);
		// view.setFocusable(true); // 这个很重要
		// view.setFocusableInTouchMode(true);
		// 重写onKeyListener
		pW_menu.setOnDismissListener(new PopupWindow.OnDismissListener() {
			public void onDismiss() {
				// TODO Auto-generated method stub
				animationSet.reset();
				//pW_menu.dismiss();
			}
		});
		view.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					animationSet.reset();
					// pW_menu.dismiss();
					return true;
				}
				return false;
			}
		});
		pW_menu.setOutsideTouchable(true);
		// 为了按返回键消失和外部点击消失 ，不会影响背景
		// popWindow.setBackgroundDrawable(new BitmapDrawable());
		//pW_menu.setBackgroundDrawable(new ColorDrawable(0x80000000));
		// popWindow.setAnimationStyle(R.style.AnimBottom);
		windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		int xoffInPixels = windowManager.getDefaultDisplay().getWidth() / 2
				- pW_menu.getWidth() / 2;
		int xoffInDip = px2dip(mContext, xoffInPixels);
		// 默认位置(anchor翻译为“靠山”)
		// popWindow.showAsDropDown(anchor);

		// anchor的居中位置
		//pW_login.showAsDropDown(anchor, -xoffInDip, 0);

		// 偏移位置
		// popWindow.showAsDropDown(anchor,0,-50);
		pW_menu.showAtLocation(
				parentView,
				Gravity.TOP | Gravity.RIGHT, 30, 220);
		pW_menu.update();
		Animation_input(rel_menu_bg_02);
	}
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public void Animation_input(View v) {
		/** 设置缩放动画 */
		//说明:0.0表示完全透明,1.0表示完全不透明
		AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
		//初始化
		Animation scaleAnimation = new ScaleAnimation(0.1f, 1.0f,0.1f,1f,Animation.RELATIVE_TO_SELF, 1f,Animation.RELATIVE_TO_SELF, 0f);
		scaleAnimation.setDuration(500);
		alphaAnimation.setDuration(500);// 设置动画持续时间
		/** 常用方法 */
		// animation.setRepeatCount(int repeatCount);//设置重复次数
		alphaAnimation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
		//translateAnimation.setFillAfter(true);
		// scaleAnimation.setStartOffset(1000);//执行前的等待时间
		// translateAnimation.setStartOffset(1000);
        /*v.setAnimation(scaleAnimation);
        v.setAnimation(translateAnimation);*/
		animationSet = new AnimationSet(true);
		animationSet.addAnimation(alphaAnimation);
		animationSet.addAnimation(scaleAnimation);
		//animationSet.addAnimation(translateAnimation);
		animationSet.setFillAfter(true);
		animationSet.setStartOffset(0);
		v.startAnimation(animationSet);
	}
}
