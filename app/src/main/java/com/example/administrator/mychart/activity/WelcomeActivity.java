package com.example.administrator.mychart.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.administrator.mychart.BaseActy;
import com.example.administrator.mychart.MainActivity;
import com.example.administrator.mychart.R;
import com.example.administrator.mychart.utils.PreferencesService;

public class WelcomeActivity extends BaseActy implements View.OnClickListener{

    private Button btn_login,btn_regist;
    private LinearLayout ll_bottom;
    private ImageView iv_bg;
    private int width,height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);
        WindowManager wm = (WindowManager) this .getSystemService(Context.WINDOW_SERVICE);

        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();
        ll_bottom = (LinearLayout) findViewById(R.id.ll_bottom);
        iv_bg = (ImageView) findViewById(R.id.iv_bg);
        Animation_bg(iv_bg);
        Animation_bottom(ll_bottom);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        btn_regist = (Button) findViewById(R.id.btn_regist);
        btn_regist.setOnClickListener(this);

        //
       // PreferencesService.preferences;
        if(service.getValue("isLogin","false").equals("true")){
            Intent intent1 = new Intent(this, MainActivity.class);
            startActivity(intent1);
        }

    }
    public void Animation_bottom(View v) {
        v.setVisibility(View.VISIBLE);
        /** 设置位移动画 */
        //fromx,tox,fromy,toy位置
        final TranslateAnimation translateAnimation = new TranslateAnimation(0,0,(int)(height*0.2),0);
        translateAnimation.setDuration(600);// 设置动画持续时间
        /** 常用方法 */
        // animation.setRepeatCount(int repeatCount);//设置重复次数
        translateAnimation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        translateAnimation.setStartOffset(1500);//执行前的等待时间
        v.setAnimation(translateAnimation);

    }
    public void Animation_bg(View v) {
        /** 设置缩放动画 */
        final ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.95f, 1.0f, 0.95f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        final TranslateAnimation translateAnimation = new TranslateAnimation(0,0,0,-(int)(height*0.10));
        translateAnimation.setDuration(600);
        scaleAnimation.setDuration(600);// 设置动画持续时间
        /** 常用方法 */
        // animation.setRepeatCount(int repeatCount);//设置重复次数
        scaleAnimation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        translateAnimation.setFillAfter(true);
       // scaleAnimation.setStartOffset(1000);//执行前的等待时间
       // translateAnimation.setStartOffset(1000);
        /*v.setAnimation(scaleAnimation);
        v.setAnimation(translateAnimation);*/
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(translateAnimation);
        animationSet.setFillAfter(true);
        animationSet.setStartOffset(1500);
        v.startAnimation(animationSet);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                Intent intent1 = new Intent(this, LoginActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_regist:
                Intent intent2 = new Intent(this, RegistActivity.class);
                startActivity(intent2);
                break;
        }
    }
}
