package com.example.administrator.mychart;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.administrator.mychart.PinnedHeader.PinnedHeaderExpandableListView;
import com.example.administrator.mychart.adapter.ContactListAdapter;
import com.example.administrator.mychart.model.Message_ListView_Item;

public class ContactFragment extends BaseFragment {

    private View rootView;
    private PinnedHeaderExpandableListView explistview;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String[][] childrenData = new String[10][10];
    private String[] groupData = new String[10];
    private int expandFlag = -1;//控制列表的展开
    private ContactListAdapter adapter;

    @Override
    public View setContentUI(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.fragment_contact, container, false);
        initView();
        initData();
        return rootView;
    }

    @Override
    public void init() {

    }

    @Override
    public void control() {

    }
    /**
     * 初始化VIEW
     */
    private void initView() {
        explistview = (PinnedHeaderExpandableListView)rootView.findViewById(R.id.explistview);
        swipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_container);
        //设置刷新时动画的颜色，可以设置4个
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 6000);
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        for(int i=0;i<10;i++){
            groupData[i] = "分组"+i;
        }
        for(int i=1;i<10;i++){
            for(int j=0;j<10;j++){
                childrenData[i][j] = "qq好友"+j;
            }
        }
        //设置悬浮头部VIEW
        explistview.setHeaderView(View.inflate(getActivity() ,R.layout.group,
                null));
        adapter = new ContactListAdapter(childrenData, groupData, getActivity().getApplicationContext(),explistview);
        explistview.setAdapter(adapter);
        explistview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Intent intent=new Intent(getActivity(),ChartActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity(), "分组:"+groupData[groupPosition]+",好友:"+childrenData[groupPosition][childPosition], Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}
