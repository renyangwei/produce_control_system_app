package com.papermanagement.activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.papermanagement.R;

/**
 * 登录界面
 */
public class LoginActivity extends BaseActivity {

    EditText etPhone, etFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etFactory = (EditText) findViewById(R.id.et_factory);
    }

    /**
     * 登录
     * @param view 视图
     */
    public void onLogin(View view) {
        String phone = etPhone.getText().toString().trim();
        String factory = etFactory.getText().toString().trim();
        //这里做网络传输，把厂名和手机号传过去
        //如果成功就保存厂名
        if (TextUtils.equals(phone, "18673298768")) {
            startActivity(new Intent(this, RealTimeDateActivity.class));
        } else {
            Snackbar.make(view, "您的手机号未被允许访问，请联系工作人员", Snackbar.LENGTH_SHORT).show();
        }
    }
}
