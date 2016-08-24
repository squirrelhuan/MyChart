package com.example.administrator.mychart.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.mychart.R;
import com.example.administrator.mychart.popupwindow.SelectPhotoPopupWindow;

public abstract class BaseFragment extends Fragment{

	View rootView;
	Bundle savedInstanceState;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.savedInstanceState = savedInstanceState;
		if (rootView == null) {
			rootView = setContentUI(inflater, container);
		} else {
			if (rootView.getParent() != null) {
				((ViewGroup) rootView.getParent()).removeView(rootView);
			}
		}
		return rootView;
	}
	
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		this.savedInstanceState = savedInstanceState;
		super.onActivityCreated(savedInstanceState);
		init();
	}
	
    /* **/
	public void takePhoto(View pview){
		View view = getLayoutInflater(savedInstanceState).inflate(R.layout.item_popupwindows,null);
		SelectPhotoPopupWindow slePhotoPopupWindow = new SelectPhotoPopupWindow();
		slePhotoPopupWindow.getView(this.getActivity(), view,pview);
	}

	/** TODO 加载视图 */
	public abstract View setContentUI(LayoutInflater inflater, ViewGroup container);
	
	public abstract void init();
}
