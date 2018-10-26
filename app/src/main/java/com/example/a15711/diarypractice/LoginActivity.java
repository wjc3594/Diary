package com.example.a15711.diarypractice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.mainactivity.MainActivity;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.CellIdentityCdma;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity{

    private SharedPreferences pref;//定义一个SharedPreferences对象
    private SharedPreferences.Editor editor;//调用SharedPreferences对象的edit()方法来获取一个SharedPreferences.Editor对象，用以添加要保存的数据
    private Button login;//登录按钮
    private EditText adminEdit;//用户名输入框
    private EditText passwordEdit;//密码输入框
    private CheckBox savePassword;//是否保存密码复选框
    private CheckBox showPassword;//显示或隐藏密码复选框
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //将背景图与状态栏融合到一起，只支持Android5.0以上的版本
        if(Build.VERSION.SDK_INT>=21){
            View decorView=getWindow().getDecorView();
            //布局充满整个屏幕
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            //设置状态栏为透明
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_login);
        //获取各组件或对象的实例
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        login=(Button)findViewById(R.id.login_button);
        adminEdit=(EditText)findViewById(R.id.admin);
        passwordEdit=(EditText)findViewById(R.id.password);
        savePassword=(CheckBox)findViewById(R.id.save_password);
        showPassword=(CheckBox)findViewById(R.id.show_password);
        //获取当前“是否保存密码”的状态
        final boolean isSave=pref.getBoolean("save_password",false);
        //当“是否保存密码”勾选时，从SharedPreferences对象中读出保存的内容，并显示出来
        if(isSave){
            String account=pref.getString("account","");
            String password=pref.getString("password","");
            adminEdit.setText(account);
            passwordEdit.setText(password);
            //把光标移到文本末尾处
            adminEdit.setSelection(adminEdit.getText().length());
            passwordEdit.setSelection(passwordEdit.getText().length());
            savePassword.setChecked(true);
        }
        //用户点击登录时的处理事件
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //读出用户名和密码并判断是否正确
                String account=adminEdit.getText().toString();
                String password=passwordEdit.getText().toString();
                //用户名和密码正确
                if(account.equals("admin")&&password.equals("123456")){
                    editor=pref.edit();
                    //“是否保存密码”勾选
                    if(savePassword.isChecked()){
                        editor.putBoolean("save_password",true);
                        editor.putString("account",account);
                        editor.putString("password",password);
                    }
                    else{
                        editor.clear();
                    }
                    //提交完成数据存储
                    editor.apply();
                    //显示登录成功并跳转到主界面活动
                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    //结束当前活动
                    finish();
                }
                //用户名或密码错误
                else{
                    Toast.makeText(LoginActivity.this,"登录失败,请重新输入！",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //用户点击'显示密码'复选框
        showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showPassword.isChecked()){
                    showOrHide(passwordEdit,true);
                }else{
                    showOrHide(passwordEdit,false);
                }
            }
        });

    }

    //当用户离开活动时，检测是否勾选记住密码，若勾选则保存用户输入的用户名及密码
    @Override
    protected void onDestroy() {
        super.onDestroy();
        editor=pref.edit();
        String account=adminEdit.getText().toString();
        String password=passwordEdit.getText().toString();
        if(savePassword.isChecked()){
            editor.putBoolean("save_password",true);
            editor.putString("account",account);
            editor.putString("password",password);
        }else{
            editor.clear();
        }
        editor.apply();
    }
    //显示或隐藏密码
    private void showOrHide(EditText passwordEdit,boolean isShow){

        //记住光标开始的位置
        int pos = passwordEdit.getSelectionStart();
        if(isShow){
            passwordEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }else{
            passwordEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        passwordEdit.setSelection(pos);
    }

}
