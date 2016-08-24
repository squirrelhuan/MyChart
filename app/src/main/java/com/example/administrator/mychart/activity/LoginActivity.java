package com.example.administrator.mychart.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.mychart.BaseActy;
import com.example.administrator.mychart.MainActivity;
import com.example.administrator.mychart.MyselfApplication;
import com.example.administrator.mychart.R;
import com.example.administrator.mychart.adapter.EditView_withDeleteButton;
import com.example.administrator.mychart.event.myevent.DoorListener;
import com.example.administrator.mychart.event.myevent.MessageEvent;

public class LoginActivity extends BaseActy implements View.OnClickListener,DoorListener {

    private EditView_withDeleteButton et_username;
    private TextView tv_login_problem,tv_regist;
    private EditView_withDeleteButton et_password;
    private ImageView iv_user_head;
    private Button btn_login;
    private LinearLayout ll_input;
    private int width,height;

    protected static LoginActivity loginActivity = new LoginActivity();
    public static LoginActivity getInstance(){
        return loginActivity;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        WindowManager wm = (WindowManager) this .getSystemService(Context.WINDOW_SERVICE);

        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();
        ll_input = (LinearLayout) findViewById(R.id.ll_input);
        Animation_input(ll_input);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        et_username = (EditView_withDeleteButton) findViewById(R.id.et_username);
        et_password = (EditView_withDeleteButton) findViewById(R.id.et_password);
        tv_login_problem = (TextView) findViewById(R.id.tv_login_problem);
        tv_login_problem.setOnClickListener(this);
        tv_regist = (TextView) findViewById(R.id.tv_regist);
        tv_regist.setOnClickListener(this);
        iv_user_head = (ImageView) findViewById(R.id.iv_user_head);
        iv_user_head.setOnClickListener(this);
        //startService(new Intent(this, UdpService.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_user_head://测试使用
                Intent intent0 = new Intent(this, MainActivity.class);
                startActivity(intent0);
                break;
            case R.id.btn_login:
                if (et_username.getText()==null|| et_username.getText().toString().trim().equals("")){
                    ShowCustomToast("账号不能为空。");
                    return;
                }
                if (et_password.getText()==null|| et_password.getText().toString().trim().equals("")){
                    ShowCustomToast("密码不能为空。");
                    return;
                }
               if(!MyselfApplication.isNetworkConnected){
                    ShowCustomToast("当前网络不可用，请先检查网络。");
                    return;
                }
                udpService.Login(et_username.getText().toString(),et_password.getText().toString());
                ShowDialog_Login(v);
                break;
            case R.id.tv_login_problem:
                ShowDialog_SelectPhoto(v);
                break;
            case R.id.tv_regist:
                Intent intent1 = new Intent(this,RegistActivity.class);
                startActivity(intent1);
                break;
        }
    }

    public void Animation_input(View v) {
        /** 设置缩放动画 */
        //说明:0.0表示完全透明,1.0表示完全不透明
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        final TranslateAnimation translateAnimation = new TranslateAnimation(0,0,height/2,0);
        translateAnimation.setDuration(800);
        alphaAnimation.setDuration(1000);// 设置动画持续时间
        /** 常用方法 */
        // animation.setRepeatCount(int repeatCount);//设置重复次数
        alphaAnimation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        translateAnimation.setFillAfter(true);
        // scaleAnimation.setStartOffset(1000);//执行前的等待时间
        // translateAnimation.setStartOffset(1000);
        /*v.setAnimation(scaleAnimation);
        v.setAnimation(translateAnimation);*/
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(translateAnimation);
        animationSet.setFillAfter(true);
        animationSet.setStartOffset(0);
        v.startAnimation(animationSet);
    }

    @Override
    public void messageEvent(MessageEvent event) {

    }

    @Override
    public void logEvent(MessageEvent event) {
        if(event.getMessage().getContent().equals("login success")){
            Message message = new Message();
            message.obj = this;
            handler.sendMessage(message);
            service.save("isLogin","true");
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //showToast("登陆成功");
            loginPopupWindow.dealsuccess();
            udpService.SendHeartMessage();
            service.save("isLogin","true");

            //Toast.makeText(MyselfApplication.getApp(),"登陆成功",Toast.LENGTH_SHORT).show();
        }
    };
}
