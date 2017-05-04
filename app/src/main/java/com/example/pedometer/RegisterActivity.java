package com.example.pedometer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private EditText mAccount;                        //用户名编辑
    private EditText mPwd;                            //密码编辑
    private EditText mPwdCheck;                       //密码编辑
    private Button mSureButton;                       //确定按钮
    private Button mCancelButton;                     //取消按钮
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        addListener();


    }
    public void initView(){
        mAccount = (EditText) findViewById(R.id.resetpwd_edit_name);
        mPwd = (EditText) findViewById(R.id.resetpwd_edit_pwd_old);
        mPwdCheck = (EditText) findViewById(R.id.resetpwd_edit_pwd_new);

        mSureButton = (Button) findViewById(R.id.register_btn_sure);
        mCancelButton = (Button) findViewById(R.id.register_btn_cancel);
    }
    public void addListener(){
        mSureButton.setOnClickListener(m_register_Listener);
        mCancelButton.setOnClickListener(m_register_Listener);
    }

    View.OnClickListener m_register_Listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.register_btn_sure:
                    register_check();
                    break;
                case R.id.register_btn_cancel:
                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };
    public void  register_check(){
        if(isUserNameAndPwdValid()){
            String userName = mAccount.getText().toString().trim();
            String userPwd = mPwd.getText().toString().trim();
            String userPwdCheck = mPwdCheck.getText().toString().trim();
            if(isExistName(userName)){
                if(!userPwd.equals(userPwdCheck)){
                    Toast.makeText(this, "两次密码输入不一致",Toast.LENGTH_SHORT).show();
                    return ;
                }else{
                    PersonData personData = new PersonData();
                    personData.setUserName(userName);
                    personData.setUserPwd(userPwd);
                    personData.save();
                    Intent intent  = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }
    }
    public boolean isUserNameAndPwdValid() {
        if (mAccount.getText().toString().trim().equals("")) {
            Toast.makeText(this,"请输入用户名",Toast.LENGTH_SHORT).show();
            return false;
        } else if (mPwd.getText().toString().trim().equals("")) {
            Toast.makeText(this, "请输入密码",Toast.LENGTH_SHORT).show();
            return false;
        } else if(mPwdCheck.getText().toString().trim().equals("")){
            Toast.makeText(this,"请输入确认密码",Toast.LENGTH_SHORT).show();
        }
        return true;
    }
    public boolean isExistName(String userName){
        List<PersonData> personDatas = DataSupport.where("userName = ?",userName).find(PersonData.class);
        if(!personDatas.isEmpty()){
            Toast.makeText(this,"该用户名已存在",Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            Toast.makeText(this,"注册成功",Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}
