package com.example.administrator.mychart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.mychart.PinnedHeader.PinnedHeaderExpandableListView;
import com.example.administrator.mychart.R;

/**
 * Created by Administrator on 2016/8/7.
 */
public class ContactListAdapter extends BaseExpandableListAdapter{
    public String[][] childrenData;
    public String[] groupData;
    private Context context;
    private PinnedHeaderExpandableListView listView;
    private LayoutInflater inflater;


    public ContactListAdapter(String[][] childrenData, String[] groupData
            , Context context, PinnedHeaderExpandableListView listView){
        this.groupData = groupData;
        this.childrenData = childrenData;
        this.context = context;
        this.listView = listView;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childrenData[groupPosition][childPosition];
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView != null) {
            view = convertView;
        } else {
            view = inflater.inflate(R.layout.child, null);
        }
        TextView text = (TextView)view.findViewById(R.id.childto);
        text.setText(childrenData[groupPosition][childPosition]);
        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(groupPosition<0)
            return 0;
        return childrenData[groupPosition].length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupData[groupPosition];
    }

    @Override
    public int getGroupCount() {
        return groupData.length;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        View view = null;
        //menu
        if(groupPosition==0)
        {
            view = inflater.inflate(R.layout.menu, null);
            view.setLayoutParams(new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            view.setTag(1);
            LinearLayout btn1=(LinearLayout)view.findViewById(R.id.ll_01);
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "新朋友", Toast.LENGTH_SHORT).show();
                }
            });
            LinearLayout btn2=(LinearLayout)view.findViewById(R.id.ll_02);
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "群聊", Toast.LENGTH_SHORT).show();
                }
            });
            LinearLayout btn3=(LinearLayout)view.findViewById(R.id.ll_03);
            btn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "公众号", Toast.LENGTH_SHORT).show();
                }
            });
            return view;
        }

        //特别关心和常用群聊
        if(groupPosition==3)
        {
            if (convertView != null&&(Integer)convertView.getTag()==2)
            {
                view = convertView;
            } else {
                view = inflater.inflate(R.layout.group2, null);
                view.setTag(2);
            }
            TextView text = (TextView)view.findViewById(R.id.groupto);
            TextView tv_count = (TextView)view.findViewById(R.id.tv_groutp_count);
            ImageView image = (ImageView)view.findViewById(R.id.iv_group);

            if (isExpanded) {
                image.setImageResource(R.mipmap.v5_expander_open_light);
                text.setText(/*"- "+*/groupData[groupPosition]+"");
                tv_count.setText("1/"+childrenData[groupPosition].length);
            }
            else{
                image.setImageResource(R.mipmap.v5_expander_close_light);
                //.setBackgroundResource(R.mipmap.v5_expander_close_light);
                text.setText(/*"+ "+*/groupData[groupPosition]);
                tv_count.setText("1/"+""+childrenData[groupPosition].length);
            }
            return view;
        }
        //手机通讯录和我的设备
        if(groupPosition==groupData.length-2)
        {
            if (convertView != null&&(Integer)convertView.getTag()==3)
            {
                view = convertView;
            } else {
                view = inflater.inflate(R.layout.group2, null);
                view.setTag(3);
            }
            TextView text = (TextView)view.findViewById(R.id.groupto);
            TextView tv_count = (TextView)view.findViewById(R.id.tv_groutp_count);
            ImageView image = (ImageView)view.findViewById(R.id.iv_group);

            if (isExpanded) {
                if(groupPosition==groupData.length){
                }
                image.setImageResource(R.mipmap.v5_expander_open_light);
                text.setText(/*"- "+*/groupData[groupPosition]+"");
                tv_count.setText("1/"+childrenData[groupPosition].length);
            }
            else{
                image.setImageResource(R.mipmap.v5_expander_close_light);
                //.setBackgroundResource(R.mipmap.v5_expander_close_light);
                text.setText(/*"+ "+*/groupData[groupPosition]);
                tv_count.setText("1/"+""+childrenData[groupPosition].length);
            }
            return view;
        }
        //普通分组
        if (convertView != null&&(Integer)convertView.getTag()==0)
        {
            view = convertView;
        } else {
            view = inflater.inflate(R.layout.group, null);
            view.setTag(0);
        }
        TextView text = (TextView)view.findViewById(R.id.groupto);
        TextView tv_count = (TextView)view.findViewById(R.id.tv_groutp_count);
        ImageView image = (ImageView)view.findViewById(R.id.iv_group);

        if (isExpanded) {
            image.setImageResource(R.mipmap.v5_expander_open_light);
            text.setText(/*"- "+*/groupData[groupPosition]+"");
            tv_count.setText("1/"+childrenData[groupPosition].length);
        }
        else{
            image.setImageResource(R.mipmap.v5_expander_close_light);
            //.setBackgroundResource(R.mipmap.v5_expander_close_light);
            text.setText(/*"+ "+*/groupData[groupPosition]);
            tv_count.setText("1/"+""+childrenData[groupPosition].length);
        }
        return view;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
