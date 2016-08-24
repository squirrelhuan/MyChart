package com.example.administrator.mychart;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.mychart.event.myevent.DoorListener;
import com.example.administrator.mychart.event.myevent.MessageEvent;
import com.example.administrator.mychart.fragment.MenuFragment;
import com.example.administrator.mychart.popupwindow.ConversationPopupWindow;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class MainActivity extends SlidingFragmentActivity
        implements DoorListener ,View.OnClickListener {

    /** 侧边栏菜单 */
    private SlidingMenu slidingMenu;
    MenuFragment menuFragment;

    /** 侧边菜单之设备fragment */
    public static Fragment conversationFragment;
    private Fragment contactFragment;
    private Fragment pluginFragment;
    /** 底部三个imageView */
    private ImageView iv_conversation,iv_contact,iv_plugin;

   // @ViewInject(R.id.iv_left_button)
    public static ImageView iv_left_button;// 显示侧边栏
  //  @ViewInject(R.id.btn_right)
   // ImageView btn_right;// 显示侧边栏

    protected static LayoutInflater inflater_toast;
    protected static ConversationPopupWindow conversationPopupWindow;/** 上下文环境 */
    protected static Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        initSlidingMenu();
        initFragment();

        init();

        inflater_toast = getLayoutInflater();
        mContext = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        iv_left_button.setVisibility(View.VISIBLE);
    }

    /**
     * 初始化滑动菜单
     */
    private void initSlidingMenu() {
        // 设置滑动菜单的视图
        setBehindContentView(R.layout.fragment_sliding_menu);
        if (menuFragment == null) {
            menuFragment = new MenuFragment(/*this*/);
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, menuFragment).commit();

        // 实例化滑动菜单对象
        slidingMenu = getSlidingMenu();
        // 设置滑动阴影的宽度
        //slidingMenu.setShadowWidthRes(R.dimen.shadow_width);
        // 设置滑动阴影的图像资源
        //slidingMenu.setShadowDrawable(R.drawable.shadow);
        // 设置滑动菜单视图的宽度
        slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        // 设置渐入渐出效果的值
        slidingMenu.setFadeDegree(0.0f);
        // 设置触摸屏幕的模式
        //slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        slidingMenu.setOnClosedListener(new SlidingMenu.OnClosedListener() {
            @Override
            public void onClosed() {
               //iv_left_button.setVisibility(View.VISIBLE);
            }
        });
        slidingMenu.setOnOpenedListener(new SlidingMenu.OnOpenedListener() {
            @Override
            public void onOpened() {
               // iv_left_button.setVisibility(View.GONE);
            }
        });
        slidingMenu.setOnScrowViewListener(new SlidingMenu.OnScrowViewListener_m() {
            @Override
            public void onScrow(int position, float positionOffset, int positionOffsetPixels) {
                float x = 0;
                if(Math.abs(positionOffset)>=0) {
                    x = (1-(Math.abs(positionOffset) / Math.abs(positionOffsetPixels))) * 255;
                }
                iv_left_button.getBackground().setAlpha((int)x);
                Log.d("CGQ","position="+position+",positionOffset="+positionOffset+",positionOffsetPixels="+positionOffsetPixels);
            }
        });
    }

    /** 左侧按钮 */
    @OnClick(R.id.iv_left_button)
    public void onClickLeftButton(View v) {
        switch (v.getId()) {
            case R.id.iv_left_button:
               // v.setVisibility(View.GONE);
               // toggle();
                showMenu();
                break;

            default:
                break;
        }
    }

    /** 右侧按钮 */
    @OnClick(R.id.btn_right)
    public void onNewClick(View v) {
        ShowDialog_ConversationMenu(v);
      //  Intent intent = new Intent(MainActivity.this, ContentActivity.class);
       // startActivity(intent);
    }

    public void init(){
        iv_left_button = (ImageView) findViewById(R.id.iv_left_button);
        iv_conversation = (ImageView) findViewById(R.id.iv_conversation);
        iv_conversation.setOnClickListener(this);
        iv_contact = (ImageView) findViewById(R.id.iv_contact);
        iv_contact.setOnClickListener(this);
        iv_plugin = (ImageView) findViewById(R.id.iv_plugin);
        iv_plugin.setOnClickListener(this);

        initFragment();
        //发送心跳包
        MyselfApplication.udpService.SendHeartMessage();
    }

    private void initFragment() {
        if (conversationFragment == null) {
            addFragmentToStack(0);
            Toast.makeText(this, "第一次初始化", Toast.LENGTH_SHORT).show();
        }
    }

    /** TODO 添加fragment */
    private void addFragmentToStack(int index) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        hideFragments(transaction);
        switch (index) {
            case 0:
                /** 设备 */
                if (conversationFragment == null) {
                    conversationFragment = new ConversationFragment();
                    //Bundle bundle = new Bundle();
                    //bundle.putString("titleKey", mMenuTitles[index]);
                    //equipmentFragment.setArguments(bundle);
                    transaction.add(R.id.content_frame, conversationFragment);
                    Toast.makeText(this, "add : conversationFragment", Toast.LENGTH_SHORT).show();
                } else {
                    transaction.show(conversationFragment);
                    Toast.makeText(this, "show : conversationFragment", Toast.LENGTH_SHORT).show();
                }
                break;
            case 1:
                if (contactFragment == null) {
                    contactFragment = new ContactFragment();
                    transaction.add(R.id.content_frame, contactFragment);
                    Toast.makeText(this, "add : contactFragment", Toast.LENGTH_SHORT).show();
                } else {
                    transaction.show(contactFragment);
                    Toast.makeText(this, "show : contactFragment", Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                if (pluginFragment == null) {
                    pluginFragment = new PluginFragment();
                    transaction.add(R.id.content_frame, pluginFragment);
                    Toast.makeText(this, "add : pluginFragment", Toast.LENGTH_SHORT).show();
                } else {
                    transaction.show(pluginFragment);
                    Toast.makeText(this, "show : pluginFragment", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
            if (conversationFragment == null) {
                conversationFragment = new ConversationFragment();
                transaction.add(R.id.content_frame, conversationFragment);
            } else {
                transaction.show(conversationFragment);
            }
            break;
        }
        transaction.commit();
    }
    /** TODO 将所有的Fragment都置为隐藏状态。 */
    private void hideFragments(FragmentTransaction transaction) {
        if (conversationFragment != null) {
            transaction.hide(conversationFragment);
        }
        if (contactFragment != null) {
            transaction.hide(contactFragment);
        }
        if(pluginFragment != null){
            transaction.hide(pluginFragment);
        }

    }

    @Override
    public void logEvent(MessageEvent event) {

    }

    //收到新消息事件
    @Override
    public void messageEvent(MessageEvent event) {
        Log.i("CGQ","MainActivity:new message...");
        handler.sendEmptyMessage(0);
        //event.doMethod(this.getClass(),"sss",null);
        //Toast.makeText(this, "new message", Toast.LENGTH_SHORT).show();
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            MyselfApplication application = (MyselfApplication) MyselfApplication.getApp();
            application.getToastUtils().showToast("new message");
            super.handleMessage(msg);
        }
    };


    @Override
    public void onClick(View v) {
        hideImageButton();
        switch (v.getId()){
            case R.id.iv_conversation:
                addFragmentToStack(0);
                iv_conversation.setImageResource(R.drawable.skin_tab_icon_conversation_selected);
                break;
            case R.id.iv_contact:
                addFragmentToStack(1);
                iv_contact.setImageResource(R.drawable.skin_tab_icon_contact_selected);
                break;
            case R.id.iv_plugin:
                addFragmentToStack(2);
                iv_plugin.setImageResource(R.drawable.skin_tab_icon_plugin_selected);
                break;
        }
    }
    public void hideImageButton(){
        iv_conversation.setImageResource(R.drawable.skin_tab_icon_conversation_normal);
        iv_contact.setImageResource(R.drawable.skin_tab_icon_contact_normal);
        iv_plugin.setImageResource(R.drawable.skin_tab_icon_plugin_normal);
    }

    /** 会话菜单的dialog */
    public static void ShowDialog_ConversationMenu(View pview){
        View view = inflater_toast.inflate(R.layout.login_popupwindows,null);
        conversationPopupWindow = new ConversationPopupWindow();
        conversationPopupWindow.getView(mContext, view,pview);
    }
}
