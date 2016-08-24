package com.example.administrator.mychart.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.mychart.R;
import com.example.administrator.mychart.adapter.ExpressionAdapter;
import com.example.administrator.mychart.adapter.MessageAdapter;
import com.example.administrator.mychart.model.ChatMessage;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class RegistActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       //* toolbar.setLogo(R.drawable.ic_launcher);//设置图标
        // toolbar.setTitle("My Title");//标题
       // toolbar.setSubtitle("Sub title");//副标题 */
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.imagebtn_back);//设置Navigation 图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //隐藏键盘
        getWindow().setSoftInputMode(   WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }

    @Override
    public void onClick(View view) {
       switch (view.getId()){
           case R.id.btn_login:
              // Intent intent = new Intent(RegistActivity,)
               break;
           case R.id.btn_regist:
               break;
       }
    }

}
