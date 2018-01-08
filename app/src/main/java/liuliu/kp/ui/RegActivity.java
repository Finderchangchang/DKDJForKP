package liuliu.kp.ui;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import net.tsz.afinal.view.TitleBar;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import liuliu.kp.R;
import liuliu.kp.base.BaseActivity;
import liuliu.kp.listener.HBListener;
import liuliu.kp.method.HttpUtil;
import liuliu.kp.method.Utils;
import liuliu.kp.model.HBModel;
import liuliu.kp.model.UserModel;
import liuliu.kp.view.IHB;
import liuliu.kp.wxapi.MD5;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/11/28.
 */

public class RegActivity extends BaseActivity {
    @Bind(R.id.title_bar)
    TitleBar titleBar;
    @Bind(R.id.tel_et)
    EditText telEt;
    @Bind(R.id.send_code_btn)
    Button sendCodeBtn;
    @Bind(R.id.code_et)
    EditText codeEt;
    @Bind(R.id.pwd_et)
    EditText pwdEt;
    @Bind(R.id.pwd_confirm_et)
    EditText pwdConfirmEt;
    @Bind(R.id.login_btn)
    Button loginBtn;
    String code;//验证码
    int recLen = 60;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_reg);
        ButterKnife.bind(this);
    }

    @Override
    public void initEvents() {
        titleBar.setLeftClick(() -> finish());
        sendCodeBtn.setOnClickListener(v -> {
            recLen = 60;
            handler.postDelayed(runnable, 1000);
            String tel = telEt.getText().toString().trim();
            if (Utils.isMobileNo(tel)) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("phone", tel);
                map.put("type", "0");

                SimpleDateFormat newdate = new SimpleDateFormat("yyyyMMdd");
                String date = newdate.format(new java.util.Date());
                String sign = MD5.getMessageDigest((tel.trim() + "|key=" + date + "|yangjingfang").getBytes());
                map.put("sign", sign);
                HttpUtil.load()
                        .getCode(map)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(model -> {
                            if (("1").equals(model.getSuccess())) {
                                ToastShort("发送成功");
                                if (model.getData() != null) {
                                    code = model.getData().getCode();
                                }
                            } else {
                                recLen = 0;
                                ToastShort(model.getErrorMsg());
                            }
                        }, error -> {
                            recLen = 0;
                            ToastShort("请检查网络连接");
                        });
            }
        });
        loginBtn.setOnClickListener(v -> {
            if (("").equals(telEt.getText().toString().trim()) && !Utils.isMobileNo(telEt.getText().toString().trim())) {
                ToastShort("手机号码不能为空或格式不正确");
            } else if (("").equals(codeEt.getText().toString().trim())) {
                ToastShort("验证码不能为空");
            } else if (!(pwdConfirmEt.getText().toString().trim()).equals(pwdEt.getText().toString().trim()) && !("").equals(pwdEt.getText().toString().trim())) {
                ToastShort("前后密码不一致");
            } else if (!code.equals(codeEt.getText().toString().trim())) {
                ToastShort("验证码不正确");//15231287220
            } else {
                String username = telEt.getText().toString().trim();
                SimpleDateFormat newdate = new SimpleDateFormat("yyyyMMdd");
                String date = newdate.format(new java.util.Date());
                String sign = MD5.getMessageDigest((username.trim() + "|key=" + date + "|yangjingfang").getBytes());
                Map<String, String> map = new HashMap<String, String>();
                map.put("username", username);
                map.put("password", pwdEt.getText().toString().trim());
                map.put("source", "2");
                map.put("token", "");
                map.put("clientid", Utils.getCache("cid"));
                map.put("sign", sign);
                Log.e("cid：", Utils.getCache("cid"));
                HttpUtil.load()
                        .regUser(map)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(model -> {
                            if (("1").equals(model.getSuccess())) {
                                Utils.putCache("tel", username);
                                if (model.getData() != null) {
                                    String userid = model.getData().getUserId();//用户id
                                    String tel = model.getData().getUserTel();//用户电话
                                    Utils.putCache("UserId", userid);
                                    Utils.IntentPost(MainActivity.class, new Utils.putListener() {
                                        @Override
                                        public void put(Intent intent) {
                                            intent.putExtra("can_run", true);
                                        }
                                    });
                                    if (MainActivity.mInstails != null) {
                                        MainActivity.mInstails.finish();
                                    }
                                    LoginActivity.mIntail.finish();
                                    finish();
                                }
                            } else {
                                ToastShort(model.getErrorMsg());
                            }
                        }, error -> {
                            ToastShort("请检查网络连接" + error);
                        });
            }
        });

    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (recLen > 0) {
                recLen--;
                sendCodeBtn.setEnabled(false);
                sendCodeBtn.setText(recLen + "秒后重发");
                handler.postDelayed(this, 1000);
            } else {
                sendCodeBtn.setEnabled(true);
                sendCodeBtn.setText("重新发送");
            }
        }
    };
}
