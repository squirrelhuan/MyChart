package com.example.administrator.mychart;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.example.administrator.mychart.SwipeMenuListView.SwipeMenu;
import com.example.administrator.mychart.SwipeMenuListView.SwipeMenuCreator;
import com.example.administrator.mychart.SwipeMenuListView.SwipeMenuItem;
import com.example.administrator.mychart.SwipeMenuListView.SwipeMenuListView;
import com.example.administrator.mychart.adapter.SliderItemAdapter;
import com.example.administrator.mychart.event.myevent.DoorListener;
import com.example.administrator.mychart.event.myevent.MessageEvent;
import com.example.administrator.mychart.model.Message_ListView_Item;
import com.example.administrator.mychart.pullrefresh.PullToRefreshLayout;
import com.example.administrator.mychart.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/8/3.
 */
public class ConversationFragment extends BaseFragment implements DoorListener {

    private Message_ListView_Item item;
    private SwipeMenuListView lv_data;
    private static SliderItemAdapter adapter;
    private PullToRefreshLayout mPullToRefreshLayout;
    private int currentindex;
    private View rootView;
    private static List<Message_ListView_Item> messgeList = new ArrayList<Message_ListView_Item>();

    @Override
    public View setContentUI(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.fragment_conversation, container, false);
        init();
        return rootView;
    }

    @Override
    public void control() {
        mPullToRefreshLayout = (PullToRefreshLayout) rootView.findViewById(R.id.refresh_view);
        mPullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                // TODO Auto-generated method stub
                /*pageNo = 1;
                getNetDate();*/
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                // TODO Auto-generated method stub
               /* pageNo++;
                getNetDate();*/
            }
        });
    }

    public void init(){
        lv_data=(SwipeMenuListView) rootView.findViewById(R.id.lv_data);

        lv_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(),ChartActivity.class);
                item=(Message_ListView_Item) adapter.getItem(position);
                startActivity(intent);
            }
        });

        lv_data.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {

            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                currentindex=position;
                switch (index) {
                    case 0:
                        editItem(position);
                        break;
                    case 1:
                        editItem(position);
                        break;
                    case 2:
                        deleteItem(position);
                        break;
                }
            }
            private void editItem(int position) {
                Intent intent=new Intent();
                item=(Message_ListView_Item) adapter.getItem(position);
            }
            private void deleteItem(int position) {
                AlertDialog.Builder  builder = new AlertDialog.Builder(ConversationFragment.this.getContext());
                builder.setTitle("确定删除吗？");
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //DelAsyncTask dAsyncTask=new DelAsyncTask();
                        //dAsyncTask.execute();
                    }
                });
                builder.setNegativeButton("cancel",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
                builder.show();
            }

        });

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem topItem = new SwipeMenuItem(ConversationFragment.this.getContext());
                // set item background
                topItem.setBackground(R.color.Button_grey);
                // set item width
                topItem.setWidth(DisplayUtil.dip2px(ConversationFragment.this.getContext(), 80));
                // set item title
                topItem.setTitle("置顶");
                // set item title fontsize
                topItem.setTitleSize(18);
                // set item title font color
                topItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(topItem);

                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(ConversationFragment.this.getContext());
                // set item background
                openItem.setBackground(R.color.Button_yellow);
                // set item width
                openItem.setWidth(DisplayUtil.dip2px(ConversationFragment.this.getContext(), 100));
                // set item title
                openItem.setTitle("标记已读");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(ConversationFragment.this.getContext());
                // set item background
                deleteItem.setBackground(R.color.Button_red);
                // set item width
                deleteItem.setWidth(DisplayUtil.dip2px(ConversationFragment.this.getContext(), 80));
                // set item title"删除"
                deleteItem.setTitle("删除");
                // set item title fontsize
                deleteItem.setTitleSize(18);
                // set item title font color
                deleteItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        lv_data.setDivider(new ColorDrawable(Color.GRAY));  //顺序不能错
        lv_data.setDividerHeight(1);
        lv_data.setMenuCreator(creator);

        adapter=new SliderItemAdapter(this.getContext(),messgeList);
        lv_data.setAdapter(adapter);
    }

    @Override
    public void logEvent(MessageEvent event) {
        if(event.getMessage().getContent().equals("login success")){
            showToast("登陆成功");
        }
    }

    @Override
    public void messageEvent(MessageEvent event) {
        Log.d("CGQ","result.size="+messgeList.size());
        //判断当前会话列表是否存在发送人的会话
        boolean isRecentContacts = false;
        for(Message_ListView_Item message : messgeList){
            if(event.getMessage().getSendUser().getId()==message.getSendUser().getId()){
                isRecentContacts = true;
                message.setContent(event.getMessage().getContent());
                message.setSendUser(event.getMessage().getSendUser());
                message.setTime(event.getMessage().getTime());
                message.setCount(message.getCount()+1);
            }
        }
        if (!isRecentContacts){
            Message_ListView_Item message_listView_item = new Message_ListView_Item();
            message_listView_item.setSendUser(event.getMessage().getSendUser());
            message_listView_item.setContent(event.getMessage().getContent());
            message_listView_item.setCount(1);
            message_listView_item.setTime(new Date());
            messgeList.add(message_listView_item);
        }
        handler.sendEmptyMessage(0);
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(android.os.Message msg) {
            adapter.notifyDataSetChanged();
        }
    };

}