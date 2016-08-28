package com.example.administrator.mychart.adapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.example.administrator.mychart.R;
import com.example.administrator.mychart.model.Message;
import com.example.administrator.mychart.model.Message_ListView_Item;
import com.example.administrator.mychart.widgets.dragpoint.DraggableFlagView;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class SliderItemAdapter extends BaseAdapter implements DraggableFlagView.OnDraggableFlagViewListener{
	protected Activity mActivity;
	private Context context;
	private List<Message_ListView_Item> data;
	private DraggableFlagView draggableFlagView;
	public SliderItemAdapter(Context context, List<Message_ListView_Item> data){
		this.context=context;
		this.data=data;
		
	}
	
	@Override
	public int getCount() {
		
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view=null;
		ViewHolder holder=null;
		if(convertView==null){
			holder=new ViewHolder();
		
		view=View.inflate(context,R.layout.slider_item, null);
		holder.tv_username=(TextView) view.findViewById(R.id.tv_username);
		holder.tv_time=(TextView) view.findViewById(R.id.tv_time);
		holder.tv_content = (TextView) view.findViewById(R.id.tv_content);
		holder.draggableFlagView = (DraggableFlagView) view.findViewById(R.id.main_dfv);
		holder.draggableFlagView.setOnDraggableFlagViewListener(this);
		holder.draggableFlagView.setText("1"/*+Math.random()*10*/);
		
		
		view.setTag(holder);
		}else{
			view=convertView;
			holder=(ViewHolder) view.getTag();
		}
		Message_ListView_Item item =data.get(position);
		holder.tv_username.setText(item.getSendUser().getUsername());
		holder.tv_content.setText(item.getContent());
		if(item==null && item.getCount()==0) {
			holder.draggableFlagView.setVisibility(View.GONE);
		}else {
			holder.draggableFlagView.setText("" + item.getCount());
		}
		DateFormat shortDateFormat =DateFormat.getDateTimeInstance(
						DateFormat.SHORT,
						DateFormat.SHORT);
		holder.tv_time.setText(shortDateFormat.format(item.getTime()));
		return view;
	}
	private class ViewHolder{
		private TextView tv_username;
		private TextView tv_content;
		private TextView tv_time;
		DraggableFlagView draggableFlagView;
	}
	@Override
	public void onFlagDismiss(DraggableFlagView view) {
		Toast.makeText(context, "onFlagDismiss", Toast.LENGTH_SHORT).show();
	}

}
