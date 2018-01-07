package liuliu.kp.listener;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import liuliu.kp.config.Key;
import liuliu.kp.method.HttpUtil;
import liuliu.kp.method.Utils;
import liuliu.kp.view.ILogin;
import liuliu.kp.view.IRequestCode;
import liuliu.kp.wxapi.MD5;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/11/26.
 */

public class LoginListener implements ILoginMView {
    ILogin mView;
    IRequestCode requestCode;

    public LoginListener(ILogin mView, IRequestCode requestCode) {
        this.mView = mView;
        this.requestCode = requestCode;
    }

    public LoginListener(IRequestCode requestCode) {
        this.requestCode = requestCode;
    }

    @Override
    public void reqCode(String tel) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", tel);
        map.put("type", "2");
        SimpleDateFormat newdate=new SimpleDateFormat("yyyyMMdd");
        String date=newdate.format(new java.util.Date());
        String sign= MD5.getMessageDigest((tel.trim()+"|key="+date+"|yangjingfang").getBytes());

        map.put("sign",sign);
        HttpUtil.load()
                .getCode(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    if (("1").equals(model.getSuccess())) {
                        if (model.getData() != null) {
                            requestCode.reqCodeResult(true, model.getData().getCode());
                        } else {
                            requestCode.reqCodeResult(true, "");
                        }
                    } else {
                        requestCode.reqCodeResult(false, model.getErrorMsg());
                    }
                }, error -> {
                    requestCode.reqCodeResult(false, null);
                });
    }

    @Override
    public void login(String tel, String pwd) {
        Map<String, String> map = new HashMap<>();
        map.put("username", tel);
        map.put("password", pwd);
        map.put("type", "1");
        map.put("token", "");
        map.put("clientid", Utils.getCache("cid"));

        SimpleDateFormat newdate=new SimpleDateFormat("yyyyMMdd");
        String date=newdate.format(new java.util.Date());
        String sign= MD5.getMessageDigest((tel.trim()+"|key="+date+"|yangjingfang").getBytes());
        Log.e("sign>>>>",sign);

        map.put("sign",sign);
        HttpUtil.load()
                .login(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    if (("1").equals(model.getSuccess())) {
                        if (model.getData() != null) {
                            Utils.putCache(Key.KEY_UserId, model.getData().getUserId());
                        }
                        mView.loginResult(true, null);
                    } else {
                        mView.loginResult(false, model.getErrorMsg());
                    }
                }, error -> {
                    mView.loginResult(false, null);
                });

    }
}

interface ILoginMView {
    void reqCode(String code);//请求验证码

    void login(String tel, String pwd);//账号密码登录
}
