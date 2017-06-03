package com.example.pedometer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText mAccount;                        //用户名编辑
    private EditText mPwd;                            //密码编辑
    private Button mRegisterButton;                   //注册按钮
    private Button mLoginButton;                      //登录按钮
    private CheckBox mRememberCheck;

    private SharedPreferencesUtils login_sp;
    public  String userNameValue;
    private String passwordValue;         //用户名和密码值

    public  View loginView;                           //登录
    public  View loginSuccessView;                      //
    public  TextView loginSuccessShow;                  //登陆成功提示
    private TextView mChangepwdText;                   //修改密码
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        login_sp = new SharedPreferencesUtils(this);
        boolean choseRemember =(boolean)login_sp.getParam("mRememberCheck", false);
        //如果上次选了记住密码，那进入登录页面也自动勾选记住密码，并填上用户名和密码
        if(choseRemember){
            String name=(String)login_sp.getParam("USER_NAME", "");
            String pwd =(String)login_sp.getParam("PASSWORD", "");
            mAccount.setText(name);
            mPwd.setText(pwd);
            mRememberCheck.setChecked(true);
        }
        Connector.getDatabase();
        addListener();


    }
    public void initView(){
        mAccount = (EditText) findViewById(R.id.login_edit_account);
        mPwd = (EditText) findViewById(R.id.login_edit_pwd);
        mRegisterButton = (Button) findViewById(R.id.login_btn_register);
        mLoginButton = (Button) findViewById(R.id.login_btn_login);
        loginView=findViewById(R.id.login_view);
        loginSuccessView=findViewById(R.id.login_success_view);
        loginSuccessShow=(TextView) findViewById(R.id.login_success_show);

        mChangepwdText = (TextView) findViewById(R.id.login_text_change_pwd);

        mRememberCheck = (CheckBox) findViewById(R.id.Login_Remember);
    }
    public void addListener(){
        mRegisterButton.setOnClickListener(mListener);
        mLoginButton.setOnClickListener(mListener);
        mChangepwdText.setOnClickListener(mListener);
    }

    View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.login_btn_register:
                    Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                    startActivity(intent);
                    break;
                case R.id.login_btn_login:
                    login();
                    break;
                case R.id.login_text_change_pwd:
                    Intent intent1 = new Intent(LoginActivity.this,ResetpwdActivity.class);
                    startActivity(intent1);
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 登录函数
     * 检测用户名和密码是否在数据库中
     * 跳转到主页面
     */
    public void login(){
        //判断是否有输入用户名和密码
        if(isUserNameAndPwdValid()){
            String userName = mAccount.getText().toString().trim();
            String userPwd = mPwd.getText().toString().trim();
            //判断用户名是否存在
            if(isExistName(userName)){
                //判断密码是否正确
                if(userPwd.equals(passwordValue)){
                    login_sp.setParam("username",userName);

                    //是否记住密码
                    if(mRememberCheck.isChecked()){
                        login_sp.setParam("mRememberCheck", true);
                        //保存用户名和密码
                        login_sp.setParam("USER_NAME",userName);
                        login_sp.setParam("PASSWORD",userPwd);
                    }else {
                        login_sp.setParam("mRememberCheck", false);
                    }

                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(this,"密码错误",Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this,"用户名不存在",Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 判断用户名和密码框有没有输入，没有则提示
     */
    public boolean isUserNameAndPwdValid() {
        if (mAccount.getText().toString().trim().equals("")) {
            Toast.makeText(this,"请输入用户名",Toast.LENGTH_SHORT).show();
            return false;
        } else if (mPwd.getText().toString().trim().equals("")) {
            Toast.makeText(this, "请输入密码",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean isExistName(String userName){
        List<PersonData> personDatas = DataSupport.where("userName = ?",userName).find(PersonData.class);
        if(!personDatas.isEmpty()){
            PersonData personData = personDatas.get(0);
            userNameValue = personData.getUserName();
            passwordValue = personData.getUserPwd();
            return true;
        }
        else{
            return false;
        }
    }




}
