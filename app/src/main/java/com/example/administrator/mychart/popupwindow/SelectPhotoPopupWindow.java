package com.example.administrator.mychart.popupwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.example.administrator.mychart.R;

public class SelectPhotoPopupWindow {

	private PopupWindow pop = null;
	private LinearLayout ll_popup;
	
	public SelectPhotoPopupWindow() {

	}
	
	

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
		Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
		Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
		Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
		parent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pop.dismiss();
				//ll_popup.clearAnimation();
				ll_popup.startAnimation(AnimationUtils.loadAnimation(context, R.anim.bottom_down));
			}
		});
		bt1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				/*photo();
				pop.dismiss();
				ll_popup.clearAnimation();*/
			}
		});
		bt2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				/*Intent intent = new Intent(MainActivity.this,
						AlbumActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.activity_translate_in,
						R.anim.activity_translate_out);
				pop.dismiss();
				ll_popup.clearAnimation();*/
			}
		});
		bt3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				/*pop.dismiss();
				ll_popup.clearAnimation();*/
			}
		});
		pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
		
		ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.bottom_up));
		//ll_popup.setVisibility(View.GONE);
		/*noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new GridAdapter(this);
		adapter.update();
		noScrollgridview.setAdapter(adapter);
		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == Bimp.tempSelectBitmap.size()) {
					Log.i("ddddddd", "----------");
					ll_popup.startAnimation(AnimationUtils.loadAnimation(
							MainActivity.this, R.anim.activity_translate_in));
					pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
				} else {
					Intent intent = new Intent(MainActivity.this,
							GalleryActivity.class);
					intent.putExtra("position", "1");
					intent.putExtra("ID", arg2);
					startActivity(intent);
				}
			}
		});*/

	}

}
