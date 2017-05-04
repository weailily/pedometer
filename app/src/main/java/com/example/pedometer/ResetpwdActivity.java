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

public class ResetpwdActivity extends AppCompatActivity {

    private EditText mAccount;                        //用户名编辑
    private EditText mPwd_old;                        //密码编辑
    private EditText mPwd_new;                        //密码编辑
    private EditText mPwdCheck;                       //密码编辑
    private Button mSureButton;                       //确定按钮
    private Button mCancelButton;

    //private String userNameValue;
    private String passwordValue;         //用户名和密码值

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpwd);
        initView();
        addListener();

    }

    public void initView(){
        mAccount = (EditText) findViewById(R.id.resetpwd_edit_name);
        mPwd_old = (EditText) findViewById(R.id.resetpwd_edit_pwd_old);
        mPwd_new = (EditText) findViewById(R.id.resetpwd_edit_pwd_new);
        mPwdCheck = (EditText) findViewById(R.id.resetpwd_edit_pwd_check);

        mSureButton = (Button) findViewById(R.id.resetpwd_btn_sure);
        mCancelButton = (Button) findViewById(R.id.resetpwd_btn_cancel);
    }
    public void addListener(){
        mSureButton.setOnClickListener(m_resetpwd_Listener);
        mCancelButton.setOnClickListener(m_resetpwd_Listener);
    }
    View.OnClickListener  m_resetpwd_Listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.resetpwd_btn_sure:
                    resetpwd_check();
                    break;
                case R.id.resetpwd_btn_cancel:
                    Intent intent = new Intent(ResetpwdActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                default:
                    break;
            }
        }
    };
    public void resetpwd_check(){
        if(isUserNameAndPwdValid()){
            String userName = mAccount.getText().toString().trim();
            String userPwd_old = mPwd_old.getText().toString().trim();
            String userPwd_new = mPwd_new.getText().toString().trim();
            String userPwdCheck = mPwdCheck.getText().toString().trim();
            if(isExistName(userName)){
                if(userPwd_old.equals(passwordValue)){
                    if(!userPwd_new.equals(userPwdCheck)){
                        Toast.makeText(this,"两次密码输入不一致",Toast.LENGTH_SHORT).show();
                    }else{
                        List<PersonData> personDatas1 = DataSupport.where("userName = ?",userName).find(PersonData.class);
                        PersonData personData1 =  personDatas1.get(0);
                        personData1.setUserPwd(userPwd_new);
                        personData1.save();
                        Intent intent = new Intent(ResetpwdActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }


        }

    }
    public boolean isUserNameAndPwdValid() {
        if (mAccount.getText().toString().trim().equals("")) {
            Toast.makeText(this,"请输入用户名",Toast.LENGTH_SHORT).show();
            return false;
        } else if (mPwd_old.getText().toString().trim().equals("")) {
            Toast.makeText(this, "请输入旧密码",Toast.LENGTH_SHORT).show();
            return false;
        }else if (mPwd_new.getText().toString().trim().equals("")) {
            Toast.makeText(this, "请输入新密码",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(mPwdCheck.getText().toString().trim().equals("")){
            Toast.makeText(this,"请输入确认密码",Toast.LENGTH_SHORT).show();
        }
        return true;
    }
    public boolean isExistName(String userName){
        List<PersonData> personDatas = DataSupport.where("userName = ?",userName).find(PersonData.class);
        if(!personDatas.isEmpty()){
            PersonData personData = personDatas.get(0);
            passwordValue = personData.getUserPwd();
            return true;
        }
        else{
            Toast.makeText(this,"用户名不存在",Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}
