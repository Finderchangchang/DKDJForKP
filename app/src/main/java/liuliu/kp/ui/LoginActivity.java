package liuliu.kp.ui;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.tsz.afinal.view.TitleBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import liuliu.kp.R;
import liuliu.kp.base.BaseActivity;
import liuliu.kp.config.Key;
import liuliu.kp.listener.LoginListener;
import liuliu.kp.method.Utils;
import liuliu.kp.view.ILogin;
import liuliu.kp.view.IRequestCode;

/**
 * Created by Administrator on 2016/11/26.
 */

public class LoginActivity extends BaseActivity implements ILogin, IRequestCode {
    @Bind(R.id.code_login_tv)
    TextView codeLoginTv;
    @Bind(R.id.pwd_login_tv)
    TextView pwdLoginTv;
    @Bind(R.id.tel_et)
    EditText telEt;
    @Bind(R.id.pwd_et)
    EditText pwdEt;
    @Bind(R.id.login_btn)
    Button loginBtn;
    @Bind(R.id.reg_tv)
    TextView regTv;
    @Bind(R.id.forget_tv)
    TextView forgetTv;
    @Bind(R.id.reg_forget_ll)
    LinearLayout reg_forget_ll;
    boolean isCode = true;
    @Bind(R.id.title_bar)
    TitleBar title_bar;
    LoginListener mListener;
    public static LoginActivity mIntail;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mListener = new LoginListener(this,this);
        mIntail = this;
    }

    @Override
    public void initEvents() {
        title_bar.setLeftClick(() -> finish());
        codeLoginTv.setOnClickListener(v -> {
            codeLoginTv.setTextSize(25);
            pwdLoginTv.setTextSize(16);
            isCode = true;
            viewManage();
        });

        pwdLoginTv.setOnClickListener(v -> {
            codeLoginTv.setTextSize(16);
            pwdLoginTv.setTextSize(25);
            isCode = false;
            viewManage();
        });
        telEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 11) {
                    loginBtn.setEnabled(true);
                    loginBtn.setBackgroundResource(R.drawable.chongzhi_bg);
                } else {
                    loginBtn.setEnabled(false);
                    loginBtn.setBackgroundResource(R.drawable.normal_bg);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        loginBtn.setOnClickListener(v -> {
            if (!isCode) {
                if (true) {
                    mListener.reqCode(telEt.getText().toString().trim());
                    loginBtn.setEnabled(false);
                } else {
                    ToastShort("请输入正确的手机号码");
                    loginBtn.setEnabled(true);
                }
            } else {
                mListener.login(telEt.getText().toString().trim(), pwdEt.getText().toString().trim());
                loginBtn.setEnabled(false);
            }
        });
        regTv.setOnClickListener(v -> Utils.IntentPost(RegActivity.class));//跳转到注册页面
        forgetTv.setOnClickListener(v -> Utils.IntentPost(ForgetPwd_CheckCodeActivity.class));//跳转到忘记密码页面
        if (!("").equals(Utils.getCache(Key.KEY_UserId))) {
            Utils.IntentPost(MainActivity.class);
            this.finish();
        }
    }

    /**
     * 控制点击切换显示的内容
     */
    private void viewManage() {
        if (isCode) {
            pwdEt.setVisibility(View.GONE);
            loginBtn.setText("下一步");
            reg_forget_ll.setVisibility(View.GONE);
        } else {
            pwdEt.setVisibility(View.VISIBLE);
            loginBtn.setText("登录");
            reg_forget_ll.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 账号密码登录处理
     *
     * @param result 登录成功
     */
    @Override
    public void loginResult(boolean result, String errorMsg) {
        loginBtn.setEnabled(true);
        if (result) {//登录成功
            Utils.putCache("tel", telEt.getText().toString().trim());
            finish();
        } else {
            if (errorMsg == null) {
                ToastShort("账号或密码不正确请重新输入");
            } else {
                ToastShort(errorMsg);
            }
        }
    }

    @Override
    public void reqCodeResult(boolean result, String code) {
        loginBtn.setEnabled(true);
        if (result) {
            if (code != null) {
                Utils.IntentPost(ForgetPwd_CheckCodeActivity.class, listener -> {
                    listener.putExtra(Key.KEY_Code, code);
                    listener.putExtra(Key.KEY_TEL, telEt.getText().toString().trim());
                });
            } else {
                ToastShort("验证码不正确，请重新获取");
            }
        } else {
            if (code != null) {
                ToastShort(code);
            } else {
                ToastShort("请检查网络连接");
            }
        }
    }
}
