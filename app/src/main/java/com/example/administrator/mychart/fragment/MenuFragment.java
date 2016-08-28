package com.example.administrator.mychart.fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.mychart.MainActivity;
import com.example.administrator.mychart.R;
import com.example.administrator.mychart.database.DatabaseUtil;
import com.example.administrator.mychart.utils.JxlUtil;


public class MenuFragment extends BaseFragment implements OnClickListener{
    
	//@ViewInject(R.id.userhead_img)
	ImageView iv_personal_icon;// 头像图标
	TextView tv_out_exel;
	public static MainActivity mainActivity;

	public MenuFragment() {
	}/*
	public MenuFragment(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}*/

	@Override
	public View setContentUI(LayoutInflater inflater, ViewGroup container) {

		return inflater.inflate(R.layout.fragment_menu, container, false);
	}

	@Override
	public void init(){
		iv_personal_icon = (ImageView) rootView.findViewById(R.id.iv_personal_icon);
		iv_personal_icon.setOnClickListener(this);
		tv_out_exel = (TextView) rootView.findViewById(R.id.tv_out_exel);
		tv_out_exel.setOnClickListener(this);
	   /* CircleBitmap circleBitmap = new CircleBitmap();
	    iv_personal_icon.setDrawingCacheEnabled(true);
	    Bitmap bitmap = iv_personal_icon.getDrawingCache(); 
	    iv_personal_icon.setDrawingCacheEnabled(false);
	    circleBitmap.toRoundBitmap(bitmap);
	    iv_personal_icon.setImageBitmap(bitmap);*/
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_personal_icon:
			takePhoto(v);
			break;
			case R.id.tv_out_exel:
				DatabaseUtil mUtil = new DatabaseUtil(mainActivity);
				JxlUtil jxl = new JxlUtil(mUtil.queryAll());
				if(jxl.toExcel()){
					Toast.makeText(mainActivity, "success", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(mainActivity, "failt", Toast.LENGTH_SHORT).show();
				}
				break;
		default:
			break;
		}
		
	}
	
	/**
	 * 显示修改头像的对话框
	 */
	protected void showChoosePicDialog() {
		Log.i("CGQ","showChoosePicDialog");
		/*AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("设置头像");
		String[] items = { "选择本地照片", "拍照" };
		builder.setNegativeButton("取消", null);
		builder.setItems(items, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case CHOOSE_PICTURE: // 选择本地照片
					Intent openAlbumIntent = new Intent(
							Intent.ACTION_GET_CONTENT);
					openAlbumIntent.setType("image/*");
					startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
					break;
				case TAKE_PICTURE: // 拍照
					Intent openCameraIntent = new Intent(
							MediaStore.ACTION_IMAGE_CAPTURE);
					tempUri = Uri.fromFile(new File(Environment
							.getExternalStorageDirectory(), "image.jpg"));
					// 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
					openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
					startActivityForResult(openCameraIntent, TAKE_PICTURE);
					break;
				}
			}
		});
		builder.create().show();*/
		takePhoto(rootView);
		
	}
	
}
